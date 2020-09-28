package com.chnchat.client.ui.main.message

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chnchat.client.R
import com.chnchat.client.helper.ImHelper
import com.chnchat.client.ui.main.book.BookFragment
import com.chnchat.client.ui.main.message.chat.ChatActivity
import com.luan.base.design.ui.fragment.BaseVMFragment
import com.luan.core.ext.gone
import com.luan.core.ext.hide
import com.luan.core.ext.log
import com.luan.core.ext.logE
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo
import kotlinx.android.synthetic.main.fragment_message.*
/**
 * @package    MessageFragment.kt
 * @author     luan
 * @date       2020/9/27
 * @des        会话列表
 */
class MessageFragment : BaseVMFragment<MessageViewModel>() {
    companion object {
        fun getInstance(): MessageFragment {
            return MessageFragment()
        }
    }

    override val layoutId: Int = R.layout.fragment_message
    override val isShowTitle: Boolean = false
    override fun initView() {
        v_conversations.findViewById<View>(R.id.conversation_title).gone()
        ImHelper.setInitListener {
            if (it) {
                initIm()
                "会话列表显示".log()
            } else {
                "会话列表显示失败，Im 初始化失败或者未初始化".logE()
            }
        }
    }

    private fun initIm() {
        v_conversations.initDefault()
        v_conversations.conversationList.setOnItemClickListener { view, position, messageInfo ->
            ChatInfo().let {
                it.type = messageInfo.type
                it.id = messageInfo.id
                it.chatName = messageInfo.title
                ChatActivity.launch(requireContext(),it)
            }
        }
    }

    override fun initData() {
    }
}