package com.luan.core.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.luan.core.net.request.Request
import com.luan.core.ui.refresh.RefreshListProxy
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

    var isEnableRefresh: Boolean//是否开启下拉刷新

    var isEnableLoadMore: Boolean//是否开启上拉加载

    //给刷新控件绑定到一个父容器中
    fun bindRefreshView(parent: ViewGroup, position: Int = 0): RefreshAction<CONTENT_TYPE>

    //绑定请求接口和数据适配器
    fun <B : Any, T> bindRequest(
        request: Request<B>,
        adapter: BaseQuickAdapter<T, *>,
        transform: (B?) -> List<T>?
    ): RefreshAction<CONTENT_TYPE>

    //设置刷新事件
    fun setRefreshEvent(block: (page:Int) -> Unit): RefreshAction<CONTENT_TYPE>
    //设置加载事件
    fun setLoadMoreEvent(block: (page:Int) -> Unit): RefreshAction<CONTENT_TYPE>
    //初始化刷新控件
    fun initRefreshContent(contentView: CONTENT_TYPE)
}