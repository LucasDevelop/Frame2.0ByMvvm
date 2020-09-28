package com.luan.core.custom.dialog

/**
 * @package    com.luan.core.custom.dialog
 * @author     luan
 * @date       2020/9/25
 * @des         网络加载弹窗
 */
interface INetLoadingDialog {

    fun showDialog()

    fun hideDialog()

    fun isShowing(): Boolean
}