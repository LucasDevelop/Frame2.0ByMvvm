package com.chnchat.client.wxapi


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.chnchat.client.BuildConfig

import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory

//微信支付界面
class WXPayEntryActivity : Activity(), IWXAPIEventHandler {

    val api: IWXAPI by lazy { WXAPIFactory.createWXAPI(this, BuildConfig.WX_APP_ID) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api.handleIntent(intent, this)
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        api.handleIntent(intent, this)
    }

    override fun onReq(req: BaseReq) {
    }

    override fun onResp(resp: BaseResp) {
//        AppBus.post(resp)
        finish()
    }

}