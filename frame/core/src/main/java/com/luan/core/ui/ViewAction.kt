package com.luan.core.ui

import androidx.annotation.LayoutRes

/**
 * @package    com.luan.base.design
 * @author     luan
 * @date       2020/9/16
 * @des        视图层的基本构造
 */
interface ViewAction {

    //布局ID
    @get:LayoutRes
    val layoutId: Int

    //标题布局ID
    @get:LayoutRes
    val titleLayoutId: Int

    val isShowTitle:Boolean

    //控件初始化
    fun initView()

    //数据初始化
    fun initData()

    //事件初始化
    fun initEvent() {}


    fun init() {
        initView()
        initData()
        initEvent()
    }
}