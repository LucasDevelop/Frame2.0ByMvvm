package com.chnchat.client.ui.main.message.chat

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chnchat.client.R
import com.luan.base.design.ui.activity.BaseVMActivity
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo
import kotlinx.android.synthetic.main.activity_chat.*

/**
 * @package    ChatActivity.kt
 * @author     luan
 * @date       2020/9/27
 * @des        会话
 */
class ChatActivity : BaseVMActivity<ChatViewModel>() {
    companion object {
        fun launch(context: Context, chatInfo: ChatInfo) {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("chatInfo", chatInfo)
            context.startActivity(intent)
        }
    }

    override val layoutId: Int = R.layout.activity_chat
    override val isShowTitle: Boolean = false
    val chatInfo by lazy { intent.getSerializableExtra("chatInfo") as ChatInfo }

    override fun initView() {
        v_chat.initDefault()
        v_chat.chatInfo = chatInfo
    }

    override fun initData() {
    }
}