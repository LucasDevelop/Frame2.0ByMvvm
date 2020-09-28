package com.chnchat.client.ui.auth.register

import androidx.lifecycle.LifecycleOwner
import com.chnchat.client.ext.api
import com.chnchat.client.net.bean.UuidBean
import com.chnchat.client.net.param.EmailCodeParam
import com.chnchat.client.net.param.LoginMobileParam
import com.chnchat.client.net.param.RegisterEmailParam
import com.chnchat.client.net.param.SmsParam
import com.luan.core.base.BaseViewModel
import com.luan.core.net.request.Request

/**
 * @package    com.chnchat.client.ui.auth.register
 * @author     luan
 * @date       2020/9/27
 * @des
 */
class RegisterViewModel : BaseViewModel() {
    val sendSmsCodeLive = Request<Any>()
    val sendEmailCodeLive = Request<Any>()
    val registerEmailLive = Request<UuidBean>()
    val loginMobileLive = Request<UuidBean>()

    fun registerEmail(owner: LifecycleOwner, email: String, code: String, pwd: String) {
        registerEmailLive.call(owner, api.registerEmail(RegisterEmailParam(email, code, pwd)))
            .showToast(true)
    }

    fun loginMobile(owner: LifecycleOwner,mobile:String,code:String){
        loginMobileLive.call(owner, api.loginMobile(LoginMobileParam(mobile,code))).showToast(true)
    }

    fun sendSmsCode(owner: LifecycleOwner, mobile: String) {
        sendSmsCodeLive.call(owner, api.sendSmsCode(SmsParam(mobile))).showToast(true)
    }

    fun sendEmailCode(owner: LifecycleOwner, email: String) {
        sendEmailCodeLive.call(owner, api.sendEmailCode(EmailCodeParam(email, 1))).showToast(true)
    }
}