package com.luan.core.ui.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.luan.core.R
import com.luan.core.ext.*
import com.luan.core.ui.Theme
import com.luan.core.ui.ViewAction

/**
 * @package    com.luan.core.ui.widget
 * @author     luan
 * @date       2020/9/16
 * @des        界面根布局
 */
@SuppressLint("ViewConstructor")
class DecorView(val host:Any,context: Context, val theme: Theme, val viewAction: ViewAction) :
    RelativeLayout(context) {
    lateinit var statusView: StatusView
    lateinit var titleView: View

    init {
        fitsSystemWindows = !theme.isUseImmersive
        setNewLayoutParams(LParam.MM)
        setBackgroundColor(context.colorRes(theme.backgroundColor))
        initTitle()
        initContent()
    }

    //初始化标题
    private fun initTitle() {
        if (theme.isUseImmersive && host is Activity) {//添加status bar
            FrameLayout(context).let {
                it.id = R.id.core_status_bar_id
                this@DecorView.addView(it)
                it.setNewLayoutParams(LParam.MS, heightDp = context.getStatusBarHeight())
            }
        }
        if (theme.isShowTitle && theme.titleLayoutId > 0) {//添加标题
            titleView =
                LayoutInflater.from(context)
                    .inflate(theme.titleLayoutId, this, false) as ViewGroup
            titleView.id = R.id.core_title_id
            this@DecorView.addView(titleView)
            if (this@DecorView.childCount > 1) {
                titleView.layoutParams = (titleView.layoutParams as LayoutParams).also { lp ->
                    lp.addRule(BELOW, this@DecorView.getChildAt(this@DecorView.childCount - 1).id)
                }
            }
        }
    }

    //初始化内容
    private fun initContent() {
        if (theme.isUseStatusView) {//添加 status view
            statusView = StatusView(context).apply {
                layoutParams = createRelativeLP(LParam.MM).also { lp ->
                    if (this@DecorView.childCount > 0) {
                        lp.addRule(BELOW, this@DecorView.getChildAt(this@DecorView.childCount - 1).id)
                    }
                    lp.addRule(ALIGN_PARENT_BOTTOM)
                }
            }
            this@DecorView.addView(statusView)
            statusView.setMasterView(context.inflateView(viewAction.layoutId))
        } else {
            this@DecorView.addView(context.inflateView(viewAction.layoutId).apply {
                layoutParams = createRelativeLP(LParam.MM).also { lp ->
                    if (this@DecorView.childCount > 0) {
                        lp.addRule(BELOW, this@DecorView.getChildAt(this@DecorView.childCount - 1).id)
                    }
                    lp.addRule(ALIGN_PARENT_BOTTOM)
                }
            })
        }
    }

}