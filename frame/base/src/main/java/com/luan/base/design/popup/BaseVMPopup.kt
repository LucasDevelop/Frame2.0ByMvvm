package com.luan.base.design.popup

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.luan.core.base.BaseViewModel
import com.luan.core.utils.GenericUtil
import kotlin.reflect.KClass

/**
 * @package    com.luan.base.design.popup
 * @author     luan
 * @date       2020/9/29
 * @des        可内部发送请求的pop
 */
abstract class BaseVMPopup<VM : BaseViewModel>(val host: Any) : BasePopup(host) {
    val vm by lazy {
        if (host is FragmentActivity) {
            ViewModelProviders.of(host).get((GenericUtil.getClassType(this, ViewModel::class) as KClass<VM>).java)
        } else if (host is Fragment) {
            ViewModelProviders.of(host).get((GenericUtil.getClassType(this, ViewModel::class) as KClass<VM>).java)
        } else {
            throw IllegalArgumentException("host 类型错误，无法完成ViewModel注入")
        }
    }
}