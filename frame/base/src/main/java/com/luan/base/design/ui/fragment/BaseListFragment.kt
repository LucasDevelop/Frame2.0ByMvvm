package com.luan.base.design.ui.fragment

import androidx.recyclerview.widget.RecyclerView
import com.luan.core.base.BaseViewModel
import com.luan.core.ui.RefreshAction
import com.luan.core.ui.refresh.RefreshListProxy

/**
 * @package    com.luan.base.design.ui.fragment
 * @author     luan
 * @date       2020/9/28
 * @des
 */
abstract class BaseListFragment<VM : BaseViewModel> : BaseVMFragment<VM>(),
    RefreshAction<RecyclerView> by RefreshListProxy() {
    override var isEnableLoadMore: Boolean = true
    override var isEnableRefresh: Boolean = true
}