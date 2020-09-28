package com.chnchat.client.net

import com.chnchat.client.BuildConfig
import com.chnchat.client.ext.p2MLivePrefix
import com.chnchat.client.ext.p2pHantokPrefix
import com.chnchat.client.net.bean.ImSignBean
import com.chnchat.client.net.bean.UuidBean
import com.chnchat.client.net.interceptor.ParamInterceptor
import com.chnchat.client.net.param.*
import com.chnchat.client.user.UserBean
import com.luan.annotations.net.Host
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @package    com.chnchat.client.net
 * @author     luan
 * @date       2020/9/23
 * @des
 */
@Host(host = BuildConfig.host, interceptors = [ParamInterceptor::class])
interface Api {

    //邮箱登陆
    @POST("${p2pHantokPrefix}loginCenter/emailLogin")
    fun loginEmail(@Body param: LoginEmailParam): Observable<UuidBean>

    //发送手机验证码
    @POST("${p2pHantokPrefix}mobileCenter/sendSms")
    fun sendSmsCode(@Body param: SmsParam): Observable<Any>

    //发送邮箱验证码
    @POST("${p2pHantokPrefix}emailCenter/getAuthCode")
    fun sendEmailCode(@Body param: EmailCodeParam): Observable<Any>

    //手机号登陆
    @POST("${p2pHantokPrefix}loginCenter/mobileLogin")
    fun loginMobile(@Body param: LoginMobileParam): Observable<UuidBean>

    //邮箱注册
    @POST("${p2pHantokPrefix}loginCenter/register")
    fun registerEmail(@Body param:RegisterEmailParam): Observable<UuidBean>

    //google 登陆
    @POST("${p2pHantokPrefix}loginCenter/googleLogin")
    fun loginGoogle(@Body param: LoginGoogleParam): Observable<UuidBean>

    //facebook登陆
    @POST("${p2pHantokPrefix}loginCenter/facebookLogin")
    fun loginFB(@Body param: LoginFBParam): Observable<UuidBean>

    //微信登陆
    @POST("${p2pHantokPrefix}loginCenter/wechatAppLogin")
    fun loginWeChat(@Body param: LoginWXParam): Observable<UuidBean>

    //获取IM登陆参数
    @POST("${p2MLivePrefix}business/tencentIMCenter/genSig")
    fun getImSign():Observable<ImSignBean>

    //获取用户信息
    @POST("${p2pHantokPrefix}myCenter/getUserInfo")
    fun getUserInfo(@Body param:UuidParam): Observable<UserBean>
}