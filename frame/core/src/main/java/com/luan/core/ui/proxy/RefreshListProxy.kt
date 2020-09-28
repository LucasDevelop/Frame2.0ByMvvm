package com.luan.core.ui.proxy

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luan.core.config.CoreConfig
import com.luan.core.ui.RefreshAction
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * @package    com.luan.core.ui.proxy
 * @author     luan
 * @date       2020/9/21
 * @des         刷新委托实现
 */
class RefreshListProxy : RefreshAction<RecyclerView> {

    override var isEnableLoadMore: Boolean = true
    override var isEnableRefresh: Boolean = true

    lateinit var smartRefreshLayout: SmartRefreshLayout
    var page = CoreConfig.UI.page
    var pageCount = CoreConfig.UI.pageCount

    override fun applyRefreshView(parent: ViewGroup, position: Int) {
        smartRefreshLayout = SmartRefreshLayout(parent.context)
        smartRefreshLayout.setRefreshHeader(ClassicsHeader(parent.context))
        smartRefreshLayout.setRefreshFooter(ClassicsFooter(parent.context))
        val recyclerView = RecyclerView(parent.context)
        smartRefreshLayout.setRefreshContent(recyclerView)
        smartRefreshLayout.setEnableRefresh(isEnableRefresh)
        smartRefreshLayout.setEnableLoadMore(isEnableLoadMore)
        initRefreshContent(recyclerView)
    }

    private fun setRefreshEvent(block:()->Unit) :RefreshListProxy{
        smartRefreshLayout.setOnRefreshListener {
            block()
        }
        return this
    }

    private fun setLoadMoreEvent(block:()->Unit) :RefreshListProxy{
        smartRefreshLayout.setOnLoadMoreListener {
            block()
        }
        return this
    }

    override fun initRefreshContent(contentView: RecyclerView) {
    }

}