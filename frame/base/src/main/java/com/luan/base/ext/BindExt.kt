package com.luan.base.ext

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.widget.PopupWindow
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.luan.base.design.dialog.BaseDialog
import com.luan.base.design.ui.fragment.BaseFragment
import com.luan.core.ext.linkSource
import java.lang.IllegalArgumentException
import kotlin.reflect.KProperty

/**
 * @package    com.luan.core.ext
 * @author     luan
 * @date       2020/9/29
 * @des
 */

//用于类似butterKnif 绑定ID与View
fun <V : View> PopupWindow.bindView(@IdRes id: Int) = FindIdDelegate<V>(this, id)
fun <V : View> Activity.bindView(@IdRes id: Int) = FindIdDelegate<V>(this, id)
fun <V : View> BaseFragment.bindView(@IdRes id: Int) = FindIdDelegate<V>(this, id)
fun <V : View> BaseDialog.bindView(@IdRes id: Int) = FindIdDelegate<V>(this, id)
fun <V : View> View.bindView(@IdRes id: Int) = FindIdDelegate<V>(this, id)

class FindIdDelegate<V : View>(val target: Any, val id: Int) {
    var view: View? = null
    operator fun getValue(thisRef: Any, property: KProperty<*>): V {
        if (view != null) return view as V//房子重复查找
        when (target) {
            is PopupWindow -> view = target.contentView.findViewById<V>(id)
            is Activity -> view = target.findViewById<V>(id)
            is View -> view = target.findViewById<V>(id)
            is BaseFragment -> view = target.decorView.findViewById<V>(id)
            is BaseDialog -> view = target.rootView.findViewById<V>(id)
        }
        if (view != null) return view as V
        throw IllegalArgumentException("字段 ${property.name} 未找到控件,请检查ID是否存在")
    }
}