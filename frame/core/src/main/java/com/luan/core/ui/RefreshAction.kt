package com.luan.core.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout

/**
 * @package    com.luan.core.ui
 * @author     luan
 * @date       2020/9/18
 * @des        刷新加载配置
 */
interface RefreshAction<CONTENT_TYPE : View> {

    var isEnableRefresh: Boolean

    var isEnableLoadMore: Boolean

    fun applyRefreshView(parent: ViewGroup, position: Int = 0)

    fun initRefreshContent(contentView: CONTENT_TYPE)
}