package com.luan.base.design.ui.activity

import androidx.recyclerview.widget.RecyclerView
import com.luan.core.base.BaseViewModel
import com.luan.core.ui.RefreshAction
import com.luan.core.ui.refresh.RefreshListProxy

/**
 * @package    com.luan.base.design.ui.activity
 * @author     luan
 * @date       2020/9/21
 * @des        列表界面
 */
abstract class BaseListActivity<VM : BaseViewModel>:BaseVMActivity<VM>(),RefreshAction<RecyclerView> by RefreshListProxy(){
    override var isEnableLoadMore: Boolean = true
    override var isEnableRefresh: Boolean = true

}