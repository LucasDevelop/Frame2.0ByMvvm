package com.chnchat.client.ui.auth.login

import androidx.lifecycle.LifecycleOwner
import com.chnchat.client.ext.api
import com.chnchat.client.net.bean.UuidBean
import com.chnchat.client.net.param.LoginEmailParam
import com.chnchat.client.net.param.LoginMobileParam
import com.chnchat.client.net.param.SmsParam
import com.luan.core.base.BaseViewModel
import com.luan.core.net.request.Request

/**
 * @package    com.chnchat.client.ui.auth.login
 * @author     luan
 * @date       2020/9/21
 * @des
 */
class LoginViewModel : BaseViewModel() {

    val loginEmailLive = Request<UuidBean>()
    val loginMobileLive = Request<UuidBean>()
    val sendSmsCodeLive = Request<Any>()

    fun loginEmail(owner: LifecycleOwner, email: String, pwd: String) {
        loginEmailLive.bindLifecycle(owner)
            .call(api.loginEmail(LoginEmailParam(email, pwd))).showToast(true)
    }

    fun loginMobile(owner: LifecycleOwner, mobile: String, code: String) {
        loginMobileLive.call(owner, api.loginMobile(LoginMobileParam(mobile, code))).showToast(true)
    }

    fun sendSmsCode(owner: LifecycleOwner, mobile: String) {
        sendSmsCodeLive.call(owner, api.sendSmsCode(SmsParam(mobile))).showToast(true)
    }

}