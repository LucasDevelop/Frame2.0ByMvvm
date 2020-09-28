package com.luan.core.custom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.luan.core.R

/**
 * @package    com.luan.core.custom.adapter
 * @author     luan
 * @date       2020/9/7
 * @des        加载更多样式
 */
class CustomLoadMoreView : BaseLoadMoreView() {
    override fun getLoadComplete(holder: BaseViewHolder): View  = holder.getView(R.id.v_load_complete)

    override fun getLoadEndView(holder: BaseViewHolder): View  = holder.getView(R.id.v_load_more_end)

    override fun getLoadFailView(holder: BaseViewHolder): View  = holder.getView(R.id.v_load_error)

    override fun getLoadingView(holder: BaseViewHolder): View = holder.getView(R.id.v_loading)

    override fun getRootView(parent: ViewGroup): View =
        LayoutInflater.from(parent.context).inflate(R.layout.core_custom_load_more_view,parent)
}