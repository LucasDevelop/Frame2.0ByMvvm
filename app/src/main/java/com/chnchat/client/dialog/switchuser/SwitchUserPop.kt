package com.chnchat.client.dialog.switchuser

import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SizeUtils
import com.luan.base.design.popup.BasePopup
import com.chnchat.client.R
import com.luan.base.design.popup.BaseVMPopup
import com.luan.base.ext.bindView
import com.luan.base.ext.requestContext
import com.luan.core.ext.addClick
import com.luan.core.ext.inflateView

/**
 * @package    com.chnchat.client.dialog
 * @author     luan
 * @date       2020/9/29
 * @des        切换用户
 */
class SwitchUserPop(host: Any) : BaseVMPopup<SwitchUserViewModel>(host) {

    override val layoutId: Int = R.layout.pop_switch_user

    val listView by bindView<RecyclerView>(R.id.v_list)
    val adapter = SwitchUserAdapter()

    override fun initView() {
        listView.layoutManager = LinearLayoutManager(requestContext(host))
        listView.adapter = adapter
        adapter.setNewInstance(List(3) { it }.toMutableList())
        requestContext(host).inflateView(R.layout.footer_add_user).let {
            adapter.addFooterView(it)
            it.addClick {//添加用户
                adapter.addData(1)
                listView.smoothScrollToPosition(adapter.itemCount-1)
            }
        }
    }

    override fun initData() {
    }

    fun show(view: View) {
        showAsDropDown(view, -SizeUtils.dp2px(75f), -SizeUtils.dp2px(45f))
    }
}