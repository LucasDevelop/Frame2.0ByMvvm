package com.chnchat.client.helper

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.ToastUtils
import com.chnchat.client.BuildConfig
import com.chnchat.client.R
import com.chnchat.client.ext.api
import com.chnchat.client.helper.bean.FacebookUserBean
import com.chnchat.client.net.bean.UuidBean
import com.chnchat.client.net.bean.WXUserInfoBean
import com.chnchat.client.net.param.LoginFBParam
import com.chnchat.client.net.param.LoginGoogleParam
import com.chnchat.client.net.param.LoginWXParam
import com.chnchat.client.wxapi.WXEntryActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.luan.core.ext.syncLifecycle
import com.luan.core.ext.toast
import com.luan.core.helper.RxBus
import com.luan.core.net.request.Request
import com.luan.core.net.request.request
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import java.util.*

object ThirdLoginHelper {
    private var firebaseAuth: FirebaseAuth? = null
    private var listener: OnAuthListener? = null
    var manager: CallbackManager? = null
    val RC_SIGN_IN = 0x62

    fun loginWx(
        activity: FragmentActivity,
        onSuccess: (bean: UuidBean) -> Unit,
        onFail: (errMsg: String?) -> Unit = {}
    ) {
        val wxapi = WXAPIFactory.createWXAPI(activity, null)
        wxapi?.registerApp(BuildConfig.WX_APP_ID)
        if (wxapi != null && wxapi.isWXAppInstalled) {
            val req = SendAuth.Req()
            req.scope = "snsapi_userinfo"
            req.state = "wechat_sdk_chnchat"
            wxapi.sendReq(req)
            RxBus.toFlow<WXEntryActivity.Event>().subscribe {
                it.wxUserInfoBean?.let { wxparam ->
                    val param = LoginWXParam(
                        wxparam.openid,
                        wxparam.unionid,
                        wxparam.nickname,
                        wxparam.headimgurl,
                        0
                    )
                    Request<UuidBean>()
                        .bindLifecycle(activity)
                        .call(api.loginWeChat(param))
                        .observer {
                            if (it != null)
                                onSuccess(it!!)
                        }.error {
                            it.msg.toast()
                            onFail(it.msg)
                        }
                }
            }.syncLifecycle(activity)
        } else {
            ToastUtils.showShort("用户未安装微信")
        }
    }

    fun loginFacebook(
        activity: FragmentActivity,
        onSuccess: (bean: UuidBean) -> Unit,
        onFail: (errMsg: String?) -> Unit = {}
    ) {
        authFacebook(activity, object : OnAuthListener {
            override fun onSuccess(user: Any?) {
                if (user is FacebookUserBean) {
                    Request<UuidBean>()
                        .bindLifecycle(activity)
                        .call(api.loginFB(LoginFBParam(user.id, user.name, user.picture, 0)))
                        .observer { onSuccess(it) }.error {
                            it.msg.toast()
                            onFail(it.msg)
                        }
                }
            }

            override fun onFail(errMsg: String?) {}
        })
    }

    fun loginGoogle(
        activity: FragmentActivity,
        onSuccess: (bean: UuidBean) -> Unit,
        onFail: (errMsg: String?) -> Unit = {}
    ) {
        authGoogle(activity, object : OnAuthListener {
            override fun onSuccess(user: Any?) {
                if (user is FirebaseUser) {
                    Request<UuidBean>()
                        .bindLifecycle(activity)
                        .call(
                            api.loginGoogle(
                                LoginGoogleParam(
                                    user.getUid(),
                                    user.getDisplayName(),
                                    user.toString(),
                                    0
                                )
                            )
                        )
                        .observer { onSuccess(it) }.error {
                            it.msg.toast()
                            onFail(it.msg)
                        }
                }
            }

            override fun onFail(errMsg: String?) {}
        })
    }

    fun authFacebook(context: Activity?, listener: OnAuthListener) {
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired
        if (isLoggedIn) { //已登陆
            LoginManager.getInstance()
                .logInWithReadPermissions(context, Arrays.asList("public_profile"))
            return
        }
        manager = CallbackManager.Factory.create()
        LoginManager.getInstance()
            .registerCallback(manager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d("lucas", "login fb " + loginResult.accessToken)
                    val parameters = Bundle()
                    parameters.putString("fields", "id,name,gender,picture")
                    val graphRequest =
                        GraphRequest.newMeRequest(loginResult.accessToken) { `object`, response -> //这里获取的头像是50*50
                            try {
                                if (`object` != null) {
                                    val facebookUserBean = FacebookUserBean()
                                    facebookUserBean.id = `object`.optString("id")
                                    facebookUserBean.name = `object`.optString("name")
                                    facebookUserBean.gender = `object`.optString("gender")
                                    var picture = ""
                                    val jsonObject = `object`.optJSONObject("picture")
                                    if (jsonObject != null) {
                                        val data = jsonObject.optJSONObject("data")
                                        if (data != null) {
                                            picture = data.optString("url")
                                        }
                                    }
                                    facebookUserBean.picture = picture
                                    listener.onSuccess(facebookUserBean)
                                } else {
                                    listener.onFail("获取用户信息失败")
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                listener.onFail("获取用户信息失败")
                            }
                        }
                    graphRequest.parameters = parameters
                    graphRequest.executeAsync()
                }

                override fun onCancel() {}
                override fun onError(error: FacebookException) {
                    error.printStackTrace()
                }
            })
        LoginManager.getInstance().logIn(context, Arrays.asList("public_profile"))
    }

    fun authGoogle(context: Activity, listener: OnAuthListener?) {
        this.listener = listener
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.resources.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        //检查用户是否登陆
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth!!.currentUser
        if (currentUser == null) {
            val signInIntent = googleSignInClient.signInIntent
            context.startActivityForResult(signInIntent, RC_SIGN_IN)
        } else {
            listener?.onSuccess(currentUser)
            Log.d("lucas", "google login name:" + currentUser.displayName)
        }
    }

    fun logoutGoogle() {
        FirebaseAuth.getInstance().signOut()
    }

    //Google登陆需要在Activity调用该方法
    fun onActivityResult(context: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
        if (manager != null) manager!!.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                Log.d("lucas", "firebaseAuthWithGoogle:" + account!!.id)
                firebaseAuthWithGoogle(context, account.idToken)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("lucas", "Google sign in failed", e)
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(context: Activity, idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(context) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("lucas", "signInWithCredential:success")
                    val user = firebaseAuth!!.currentUser
                    if (listener != null) {
                        listener!!.onSuccess(user)
                    }
                } else {
                    if (listener != null) listener!!.onFail("授权失败")
                    // If sign in fails, display a message to the user.登陆失败
                    Log.w("lucas", "signInWithCredential:failure", task.exception)
                }

                // ...
            }
    }

    interface OnAuthListener {
        fun onSuccess(user: Any?)
        fun onFail(errMsg: String?)
    }


}