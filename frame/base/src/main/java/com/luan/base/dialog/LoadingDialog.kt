package com.luan.base.dialog

import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.luan.base.R
import com.luan.base.design.dialog.BaseDialog
import com.luan.core.custom.dialog.INetLoadingDialog
import kotlinx.android.synthetic.main.base_dialog_loading.*

/**
 * @package    com.luan.base.dialog
 * @author     luan
 * @date       2020/9/25
 * @des        加载中
 */
class LoadingDialog(context: Context) : BaseDialog(context), INetLoadingDialog {
    override fun layoutId(): Int = R.layout.base_dialog_loading

    override fun initWindowStyle() = WindowStyle().apply {
        width = WindowManager.LayoutParams.WRAP_CONTENT
        anim = 0
        backgroundColor = 0
        gravity = Gravity.CENTER
    }

    override fun initView() {
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        v_icon.startAnimation(AnimationUtils.loadAnimation(context, R.anim.base_anim_loading))
    }

    fun changeMsg(msg: String) {
        msg.let { v_tips.text = it }
    }

    override fun showDialog() {
        show()
    }

    override fun hideDialog() {
        dismiss()
    }

}