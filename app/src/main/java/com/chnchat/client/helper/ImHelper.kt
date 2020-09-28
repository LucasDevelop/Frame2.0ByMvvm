package com.chnchat.client.helper

import android.content.Context
import com.luan.core.ext.log
import com.luan.core.ext.logE
import com.tencent.imsdk.v2.V2TIMSDKConfig
import com.tencent.qcloud.tim.uikit.TUIKit
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack
import com.tencent.qcloud.tim.uikit.config.CustomFaceConfig
import com.tencent.qcloud.tim.uikit.config.GeneralConfig

/**
 * @package    com.chnchat.client.helper
 * @author     luan
 * @date       2020/9/25
 * @des
 */
object ImHelper {

    var isInit = false
    var isInitSuccess = false
    private var initSuccess: (data: Any?) -> Unit = {}
    private var initFail: (module: String?, errCode: Int, errMsg: String?) -> Unit = { module, errCode, errMsg -> }

    fun initIm(context: Context) {
        val config = TUIKit.getConfigs().apply {
            sdkConfig = V2TIMSDKConfig()
            customFaceConfig = CustomFaceConfig()
            generalConfig = GeneralConfig()
        }
        TUIKit.init(context, 1400369965, config)
    }

    fun loginIm(
        userId: String,
        userSig: String,
        success: (data: Any?) -> Unit,
        fail: (module: String?, errCode: Int, errMsg: String?) -> Unit
    ) {
        TUIKit.login(userId, userSig, object : IUIKitCallBack {
            override fun onSuccess(data: Any?) {
                "Im login success".log()
                isInit = true
                isInitSuccess = true
                success(data)
                initSuccess(data)
            }

            override fun onError(module: String?, errCode: Int, errMsg: String?) {
                "Im login fail".logE()
                isInit = true
                isInitSuccess = false
                fail(module, errCode, errMsg)
                initFail(module, errCode, errMsg)
            }
        })
    }

    fun setInitListener(block: (isInitSuccess: Boolean) -> Unit) {
        if (isInit) {
            block(isInitSuccess)
        } else {
            initSuccess = {
                block(isInitSuccess)
            }
            initFail = { module, errCode, errMsg ->
                block(isInitSuccess)
            }
        }
    }

    fun logoutIm(
        success: (data: Any?) -> Unit,
        fail: (module: String?, errCode: Int, errMsg: String?) -> Unit
    ) {
        TUIKit.logout(object : IUIKitCallBack {
            override fun onSuccess(data: Any?) {
                success(data)
            }

            override fun onError(module: String?, errCode: Int, errMsg: String?) {
                fail(module, errCode, errMsg)
            }
        })
    }
}