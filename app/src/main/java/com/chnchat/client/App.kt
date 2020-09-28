package com.chnchat.client

import BaseApp
import com.chnchat.client.ext.api
import com.chnchat.client.helper.ImHelper
import com.chnchat.client.net.Api
import com.luan.core.CoreApp
import com.luan.core.config.CoreConfig
import com.luan.core.net.NetModule

/**
 * @package    com.chnchat.client
 * @author     luan
 * @date       2020/9/23
 * @des
 */
class App : BaseApp() {
    override fun onInitConfig() {
        super.onInitConfig()
        initSDK()
        NetModule.initModule(arrayOf(Api::class))
        CoreConfig.Net.responseCodeName = "errcode"
        CoreConfig.Net.responseMsgName = "errmsg"
        CoreConfig.Net.responseBodyName  = "returnMap"
        CoreConfig.Net.responseSuccessCode = 0
    }

    private fun initSDK() {
        ImHelper.initIm(this)
    }
}