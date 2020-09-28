package com.chnchat.client.ui.main.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chnchat.client.R
import com.luan.core.base.BaseViewModel
import com.luan.core.ext.LParam
import com.luan.core.ext.createViewGroupLP

/**
 * @package    com.chnchat.client.ui.main.home
 * @author     luan
 * @date       2020/9/28
 * @des
 */
class UpComingClassAdapter:BaseQuickAdapter<Int,BaseViewHolder>(R.layout.item_home_upcoming_class) {
    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val viewHolder = super.onCreateDefViewHolder(parent, viewType)
        viewHolder.itemView.layoutParams = createViewGroupLP(LParam.MM)
        return viewHolder
    }

    override fun convert(holder: BaseViewHolder, item: Int) {
    }
}