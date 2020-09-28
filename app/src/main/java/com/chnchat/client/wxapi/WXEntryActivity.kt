package com.chnchat.client.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.chnchat.client.BuildConfig
import com.chnchat.client.net.bean.WXUserInfoBean

import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory

class WXEntryActivity : Activity(), IWXAPIEventHandler {
    private var api: IWXAPI? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        api = WXAPIFactory.createWXAPI(this, BuildConfig.WX_APP_ID, false)
        try {
            val intent = intent
            api!!.handleIntent(intent, this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        api!!.handleIntent(intent, this)
    }

    override fun onReq(req: BaseReq) {
        when (req.type) {
            ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX -> {
            }
            ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX -> {
            }
            else -> {
            }
        }
        finish()
    }

    override fun onResp(resp: BaseResp) {
        when (resp.errCode) {
            BaseResp.ErrCode.ERR_OK -> {//成功
//                AppBus.post(resp)
                val sendResp = resp as SendAuth.Resp
                getUserInfo(sendResp.code)
                return
            }
            BaseResp.ErrCode.ERR_COMM, BaseResp.ErrCode.ERR_USER_CANCEL -> {//取消
            }
            BaseResp.ErrCode.ERR_AUTH_DENIED -> {//拒绝授权
                ToastUtils.showLong("拒绝授权")
            }
            else -> {//其他错误
                ToastUtils.showLong("未知错误")
            }
        }
        finish()
    }

    fun getUserInfo(code: String) {
//        ApiP2M.getWxOpenId(code).request(this, {
//            ApiP2M.getWxUserInfo(it.access_token, it.openid).request(this, {
//                RxBus.get().post(Event(Event.SUCCESS, it))
//            }, {
//                RxBus.get().post(Event.FAIL)
//                finish()
//            })
//        }, {
//            RxBus.get().post(Event.FAIL)
//            finish()
//        })
    }

    companion object {
        private val TAG = "MicroMsg.WXEntryActivity"
    }

    public class Event(val status: Int, val wxUserInfoBean: WXUserInfoBean? = null) {
        companion object {
            const val SUCCESS = 1
            const val FAIL = 2
        }
    }
}