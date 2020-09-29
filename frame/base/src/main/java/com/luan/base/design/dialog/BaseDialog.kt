package com.luan.base.design.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.luan.base.R

/**
 * @package    com.cj.impl.base.dialog
 * @author     luan
 * @date       2020/7/8
 * @des        弹窗基础类
 */
abstract class BaseDialog(val ctx: Context, style: Int = R.style.base_dialog_style) : Dialog(ctx, style) {

    abstract fun layoutId(): Int

    abstract fun initView()
    val rootView: View

    init {
        rootView = layoutInflater.inflate(layoutId(), null, false) as ViewGroup
        setContentView(rootView)
        if (ctx is AppCompatActivity) {
            //防止窗口泄漏
            ctx.lifecycle.addObserver(object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        if (isShowing)
                            dismiss()
                        ctx.lifecycle.removeObserver(this)
                    }
                }
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initWindowStyle().apply {
            resetWindowStyle()
        }
        setCanceledOnTouchOutside(true)
    }

    private fun WindowStyle.resetWindowStyle() {
        if (backgroundColor > 0)
            window?.setBackgroundDrawableResource(backgroundColor)
        val attributes = window?.attributes
        attributes?.width = this.width
        attributes?.height = this.height
        attributes?.gravity = this.gravity
        window?.attributes = attributes
        if (anim > 0)
            window?.setWindowAnimations(anim)
    }


    open fun initWindowStyle(): WindowStyle = WindowStyle()

    override fun show() {
        if (isShowing) return
        super.show()
    }

    class WindowStyle {
        var width = WindowManager.LayoutParams.MATCH_PARENT
        var height = WindowManager.LayoutParams.WRAP_CONTENT
        var gravity = Gravity.BOTTOM
        var backgroundColor = android.R.color.transparent
        var anim = R.style.base_dialog_anim
    }
}