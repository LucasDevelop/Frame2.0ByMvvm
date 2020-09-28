package com.chnchat.client.ui.auth.register

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.View
import com.blankj.utilcode.util.RegexUtils
import com.chnchat.client.R
import com.chnchat.client.config.Constants
import com.chnchat.client.ui.main.MainActivity
import com.chnchat.client.ui.util.WebActivity
import com.chnchat.client.user.User
import com.luan.annotations.event.Click
import com.luan.base.design.ui.activity.BaseVMActivity
import com.luan.core.ext.*
import com.luan.core.ext.helper.TimeHelper
import kotlinx.android.synthetic.main.activity_register.*

/**
 * @package    RegisterActivity.kt
 * @author     luan
 * @date       2020/9/27
 * @des        注册
 */
class RegisterActivity : BaseVMActivity<RegisterViewModel>() {
    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, RegisterActivity::class.java))
        }
    }

    override val layoutId: Int = R.layout.activity_register
    override val isShowTitle = false

    override fun initView() {
        v_protocol.text.clearStyle()
            .setSpanColor(getString(R.string.privacy_policy), colorRes(R.color.color_ff6fcfeb))
            .setClick(getString(R.string.privacy_policy)) {
                WebActivity.launch(
                    this,
                    Constants.PROTOCOL_PRIVACY
                )
            }
            .setSpanColor(getString(R.string.terms_of_service), colorRes(R.color.color_ff6fcfeb))
            .setClick(getString(R.string.terms_of_service)) {
                WebActivity.launch(
                    this,
                    Constants.PROTOCOL_USER
                )
            }
            .intoAndClickable(v_protocol)
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
        vm.sendEmailCodeLive.observer {
            TimeHelper.countDown(this, {
                if (it == 0L) {
                    v_send_email_code.isEnabled = true
                    v_send_email_code.setText(R.string.get_the_code_again)
                    return@countDown
                }
                v_send_email_code.text = getString(R.string.re_send_code).format(it.toInt())
            }).invoke()
        }
        vm.registerEmailLive.observer {
            it?.let {
                loginSuccess(it.uuid)
            }
        }
        vm.loginMobileLive.observer {
            it?.let {
                loginSuccess(it.uuid)
            }
        }
    }

    private fun loginSuccess(uuid: String) {
        User.refreshUuid(uuid)
        MainActivity.launch(this)
    }

    @Click(
        ids = [R.id.v_tab_mobile_login, R.id.v_tab_email_login, R.id.v_county_code,
            R.id.v_send_code, R.id.v_register, R.id.v_eyes, R.id.v_send_email_code]
    )
    fun onClick(v: View) {
        when (v) {
            v_tab_mobile_login -> {
                v_tab_mobile_login.typeface = Typeface.DEFAULT_BOLD
                v_tab_email_login.typeface = Typeface.DEFAULT
                v_ids_email.hide()
                v_ids_email.isEnabled = false
                v_ids_mobile.show()
            }
            v_tab_email_login -> {
                v_tab_mobile_login.typeface = Typeface.DEFAULT
                v_tab_email_login.typeface = Typeface.DEFAULT_BOLD
                v_ids_email.show()
                v_ids_email.isEnabled = true
                v_ids_mobile.gone()
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
            v_send_email_code -> {
                mapOf(
                    getString(R.string.please_inout_email) to v_email.isEmpty(),
                    getString(R.string.email_format_fail) to !RegexUtils.isEmail(v_email.toTextString())
                ).fromCheckBool {
                    v_send_email_code.isEnabled = false
                    vm.sendEmailCode(this, v_email.toTextString())
                }
            }
            v_register -> {
                register()
            }
            v_eyes -> {
                v_email_pwd.togglePasswordVisible()
            }
        }
    }

    //注册
    private fun register() {
        if (!v_protocol_check.isChecked) {
            getString(R.string.please_select_protocol).toast()
            return
        }
        if (v_ids_email.isEnabled) {//邮箱注册
            mapOf(
                getString(R.string.please_inout_email) to v_email.isEmpty(),
                getString(R.string.email_format_fail) to !RegexUtils.isEmail(v_email.text.toString()),
                getString(R.string.please_input_code) to v_email_code.isEmpty(),
                getString(R.string.please_input_pwd) to v_email_pwd.isEmpty(),
            ).fromCheckBool {
                vm.registerEmail(
                    this,
                    v_email.toTextString(),
                    v_email_code.toTextString(),
                    v_email_pwd.toTextString()
                )
            }
        } else {//手机注册
            mapOf(
                getString(R.string.plese_input_mobile) to v_mobile.isEmpty(),
                getString(R.string.mobile_formart_fail) to !RegexUtils.isMobileSimple(v_mobile.toTextString()),
                getString(R.string.please_input_code) to v_code.isEmpty()
            ).fromCheckBool {
                vm.loginMobile(this, v_mobile.toTextString(), v_code.toTextString())
            }
        }
    }
}