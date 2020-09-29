package com.luan.base.ext

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.view.View
import androidx.activity.ComponentActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

/**
 * @package    com.luan.base.ext
 * @author     luan
 * @date       2020/9/29
 * @des
 */

//从某个对象中获取上下文
fun requestContext(host: Any): Context {
    when (host) {
        is Activity -> return host
        is Fragment -> return host.requireContext()
        is View -> return host.context
        is Dialog -> return host.context
        is DialogFragment -> return host.requireContext()
        is Application -> return host.applicationContext
    }
    throw IllegalArgumentException("host 无法获取 Context")
}

fun requestOwner(host: Any): LifecycleOwner {
    when (host) {
        is ComponentActivity -> return host
        is Fragment -> return host
    }
    throw IllegalArgumentException("host 无法获取 LifecycleOwner")
}