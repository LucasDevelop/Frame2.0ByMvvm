package com.luan.core.ui.refresh

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.luan.core.config.CoreConfig
import com.luan.core.ext.LParam
import com.luan.core.ext.createViewGroupLP
import com.luan.core.ext.logE
import com.luan.core.ext.setNewLayoutParams
import com.luan.core.net.request.Request
import com.luan.core.ui.RefreshAction
import com.luan.core.ui.widget.StatusView
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

    //是否使用状态布局
    var isUseStatusView: Boolean = true
    var statusView: StatusView? = null

    //是否自动加载第一页
    var isAutoLoadFirstPage = true

    lateinit var smartRefreshLayout: SmartRefreshLayout
    lateinit var recyclerView: RecyclerView
    var adapter: BaseQuickAdapter<*, *>? = null

    //当前页码
    var page = CoreConfig.UI.page

    //每页数量
    var pageCount = CoreConfig.UI.pageCount

    override fun bindRefreshView(parent: ViewGroup, position: Int): RefreshListProxy {
        smartRefreshLayout = SmartRefreshLayout(parent.context)
        smartRefreshLayout.setRefreshHeader(ClassicsHeader(parent.context))
        smartRefreshLayout.setRefreshFooter(ClassicsFooter(parent.context))
        smartRefreshLayout.setEnableRefresh(isEnableRefresh)
        smartRefreshLayout.setEnableLoadMore(isEnableLoadMore)
        smartRefreshLayout.setEnableAutoLoadMore(true)
        recyclerView = RecyclerView(parent.context)
        if (isUseStatusView) {//嵌套status view
            statusView = StatusView(parent.context)
            statusView!!.setMasterView(recyclerView)
            statusView!!.setNewLayoutParams(LParam.MM)
            smartRefreshLayout.setRefreshContent(statusView!!)
        } else {
            smartRefreshLayout.setRefreshContent(recyclerView)
        }
        smartRefreshLayout.setNewLayoutParams(LParam.MM)
        recyclerView.setNewLayoutParams(LParam.MM)
        parent.addView(smartRefreshLayout, position)
        initRefreshContent(recyclerView)
        return this
    }

    /**
     * 将请求到的数据绑定到RefreshView 并提供数据转换方式
     * @param request Request<B>
     * @param transform Function1<B?, List<T>?>
     */
    override fun <B : Any, T> bindRequest(
        request: Request<B>,
        adapter: BaseQuickAdapter<T, *>,
        transform: (B?) -> List<T>?
    ): RefreshListProxy {
        this.adapter = adapter
        recyclerView.adapter = adapter

        request.observer {
            val datas = transform.invoke(it)
            if (page == CoreConfig.UI.page) {//刷新
                if (datas.isNullOrEmpty()) {
                    statusView?.changeStatus(StatusView.Status.EMPTY)
                    smartRefreshLayout.finishRefreshWithNoMoreData()
                    page = CoreConfig.UI.page
                } else {
                    smartRefreshLayout.finishRefresh(true)
                    statusView?.changeStatus(StatusView.Status.MASTER)
                    adapter.setNewInstance(datas.toMutableList())
                    page++
                }
            } else {//加载更多
                if (datas.isNullOrEmpty()) {//没有更多数据
                    smartRefreshLayout.finishLoadMoreWithNoMoreData()
                } else if (datas.size < pageCount) {
                    adapter.addData(datas)
                    smartRefreshLayout.finishLoadMoreWithNoMoreData()
                } else {
                    adapter.addData(datas)
                    smartRefreshLayout.finishLoadMore(true)
                }
                page++
            }
        }.error {
            if (page == CoreConfig.UI.page) {//刷新失败
                smartRefreshLayout.finishRefresh(false)
            } else {//加载更多失败
                smartRefreshLayout.finishLoadMore(false)
            }
        }
        return this
    }

    override fun setRefreshEvent(block: (page: Int) -> Unit): RefreshListProxy {
        smartRefreshLayout.setOnRefreshListener {
            page = CoreConfig.UI.page
            block(page)
        }
        if (isAutoLoadFirstPage && page == CoreConfig.UI.page) {
            smartRefreshLayout.autoRefresh()
        }
        return this
    }

    override fun setLoadMoreEvent(block: (Rpage: Int) -> Unit): RefreshListProxy {
        smartRefreshLayout.setOnLoadMoreListener {
            block(page)
        }
        return this
    }

    override fun initRefreshContent(contentView: RecyclerView) {
        contentView.layoutManager = LinearLayoutManager(smartRefreshLayout.context)
    }

}