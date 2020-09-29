package com.luan.base.design.popup

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.PopupWindow
import com.luan.base.ext.requestContext
import com.luan.core.ext.inflateView
import com.luan.core.ui.ViewAction

/**
 * @package    com.luan.base.design.popup
 * @author     luan
 * @date       2020/9/29
 * @des
 */
abstract class BasePopup(host: Any) : PopupWindow(requestContext(host)), ViewAction {
    private var onPreShow: ((anchor: View) -> Unit)? = null//准备显示事件
    var isInitView = false

    init {
        isOutsideTouchable = true
        onPreShow = {
            if (!isInitView) {
                isInitView = true
                contentView = requestContext(host).inflateView(layoutId)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                init()
            }
        }
    }

    override fun showAsDropDown(anchor: View) {
        onPreShow?.invoke(anchor)
        super.showAsDropDown(anchor)
    }

    override fun showAsDropDown(anchor: View, xoff: Int, yoff: Int) {
        onPreShow?.invoke(anchor)
        super.showAsDropDown(anchor, xoff, yoff)
    }

    override fun showAsDropDown(anchor: View, xoff: Int, yoff: Int, gravity: Int) {
        onPreShow?.invoke(anchor)
        super.showAsDropDown(anchor, xoff, yoff, gravity)
    }

    override fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        onPreShow?.invoke(parent)
        super.showAtLocation(parent, gravity, x, y)
    }
}