package com.chnchat.client.ui.auth.login

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.View
import com.blankj.utilcode.util.RegexUtils
import com.chnchat.client.R
import com.chnchat.client.helper.ThirdLoginHelper
import com.chnchat.client.ui.auth.register.RegisterActivity
import com.chnchat.client.ui.main.MainActivity
import com.chnchat.client.user.User
import com.luan.annotations.event.Click
import com.luan.base.design.ui.activity.BaseVMActivity
import com.luan.core.ext.*
import com.luan.core.ext.helper.TimeHelper
import kotlinx.android.synthetic.main.activity_login.*

/**
 * @package    LoginActivity.kt
 * @author     luan
 * @date       2020/9/21
 * @des        登陆
 */
class LoginActivity : BaseVMActivity<LoginViewModel>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override val layoutId: Int
        get() = R.layout.activity_login

    override val isShowTitle: Boolean
        get() = false

    override fun initView() {
        v_ids_email.isEnabled = false
    }

    @Click(
        R.id.v_tab_mobile_login, R.id.v_tab_email_login, R.id.v_county_code,
        R.id.v_send_code, R.id.v_register, R.id.v_facebook, R.id.v_google, R.id.v_twitter,
        R.id.v_wechat, R.id.v_login, R.id.v_eyes, R.id.v_forget_pwd
    )
    fun onClick(v: View) {
        when (v) {
            v_tab_mobile_login -> {
                v_tab_mobile_login.typeface = Typeface.DEFAULT_BOLD
                v_tab_email_login.typeface = Typeface.DEFAULT
                v_ids_email.hide()
                v_ids_email.isEnabled = false
                v_ids_mobile.show()
                v_forget_pwd.gone()
            }
            v_tab_email_login -> {
                v_tab_mobile_login.typeface = Typeface.DEFAULT
                v_tab_email_login.typeface = Typeface.DEFAULT_BOLD
                v_ids_email.show()
                v_ids_email.isEnabled = true
                v_ids_mobile.gone()
                v_forget_pwd.show()
            }
            v_county_code -> {
            }
            v_send_code -> {
                val mobile = v_mobile.text.toString()
                mapOf(
                    getString(R.string.plese_input_mobile) to v_mobile.isEmpty(),
                    getString(R.string.mobile_formart_fail) to !RegexUtils.isMobileSimple(mobile)
                ).fromCheckBool {
                    v_send_code.isEnabled = false
                    val countyCode = v_county_code.text.toString()
                    vm.sendSmsCode(this, countyCode.plus("-").plus(mobile))
                }
            }
            v_login -> {
                login()
            }
            v_facebook -> {
                ThirdLoginHelper.loginFacebook(this, {
                    loginSuccess(it.uuid)
                })
            }
            v_google -> {
                ThirdLoginHelper.loginGoogle(this, {
                    loginSuccess(it.uuid)
                })
            }
            v_twitter -> {
            }
            v_wechat -> {
                ThirdLoginHelper.loginWx(this, {
                    loginSuccess(it.uuid)
                })
            }
            v_register -> {
                RegisterActivity.launch(this)
            }
            v_eyes -> {
                v_email_pwd.togglePasswordVisible()
            }
            v_forget_pwd -> {
            }
        }
    }

    private fun loginSuccess(uuid: String) {
        User.refreshUuid(uuid)
        MainActivity.launch(this)
    }

    private fun login() {
        if (v_ids_email.isEnabled) {//邮箱登陆
            mapOf(
                getString(R.string.please_inout_email) to v_email.isEmpty(),
                getString(R.string.email_format_fail) to !RegexUtils.isEmail(v_email.text.toString()),
                getString(R.string.please_input_pwd) to v_email_pwd.isEmpty(),
            ).fromCheckBool {
                vm.loginEmail(this, v_email.toTextString(), v_email_pwd.toTextString())
            }
        } else {//手机登陆
            val mobile = v_mobile.toTextString()
            mapOf(
                getString(R.string.plese_input_mobile) to v_mobile.isEmpty(),
                getString(R.string.mobile_formart_fail) to !RegexUtils.isMobileSimple(mobile),
                getString(R.string.please_input_code) to v_code.isEmpty()
            ).fromCheckBool {
                vm.loginMobile(
                    this,
                    v_county_code.toTextString().plus("-").plus(mobile),
                    v_code.toTextString()
                )
            }
        }
    }

    override fun initData() {
        vm.sendSmsCodeLive.observer {
            TimeHelper.countDown(this, {
                if (it == 0L) {
                    v_send_code.isEnabled = true
                    v_send_code.setText(R.string.get_the_code_again)
                    return@countDown
                }
                v_send_code.text = getString(R.string.re_send_code).format(it.toInt())
            }).invoke()
        }
        vm.loginEmailLive.observer {
            it?.let {
                loginSuccess(it.uuid)
            }
        }
        vm.loginEmailLive.observer {
            it?.let {
                loginSuccess(it.uuid)
            }
        }
    }


}