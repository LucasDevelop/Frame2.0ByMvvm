package com.luan.core.utils

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import com.luan.annotations.event.Click
import com.luan.core.R
import java.lang.IllegalArgumentException
import java.lang.reflect.Method
import kotlin.math.abs

/**
 * @package    com.luan.core.utils
 * @author     luan
 * @date       2020/9/25
 * @des        注解解析相关
 */
object AnnotationUtil {

    /**
     * 解析点击注解
     * @param host Any 需要将这些事件绑定到哪个对象上
     * @param target Any 从哪个对象中去找ids中的View Activity\View
     */
    fun parseClickAnnotation(host: Any, target: Any) {
        //判断是否使用注解
        host::class.java.methods.forEach { method ->
            method.getAnnotation(Click::class.java)?.let { click ->
                if (target is Activity) {
                    click.ids.forEach { id ->
                        val findViewById = target.findViewById<View>(id)
                        findViewById.setOnClickListener {
                            fastClick(it, click, method, host)
                        }
                    }
                } else if (target is ViewGroup) {
                    click.ids.forEach { id ->
                        val findViewById = target.findViewById<View>(id)
                        findViewById.setOnClickListener {
                            fastClick(it, click, method, host)
                        }
                    }
                }
            }
        }
    }

    /**
     * 解析长点击注解
     * @param host Any 需要将这些事件绑定到哪个对象上
     * @param target Any 从哪个对象中去找ids中的View Activity\View
     */
    fun parseLongClickAnnotation(host: Any, target: Any) {
        //判断是否使用注解
        host::class.java.methods.forEach { method ->
            method.getAnnotation(Click::class.java)?.let { click ->
                if (target is Activity) {
                    click.ids.forEach { id ->
                        val findViewById = target.findViewById<View>(id)
                        findViewById.setOnLongClickListener {
                            fastClick(it, click, method, host)
                            return@setOnLongClickListener true
                        }
                    }
                } else if (target is ViewGroup) {
                    click.ids.forEach { id ->
                        val findViewById = target.findViewById<View>(id)
                        findViewById.setOnLongClickListener {
                            fastClick(it, click, method, host)
                            return@setOnLongClickListener true
                        }
                    }
                }
            }
        }
    }

    private fun fastClick(
        view: View,
        click: Click,
        method: Method,
        host: Any
    ) {
        val time = view.getTag(R.id.core_fast_click_id) as? Long
        val currentTimeMillis = System.currentTimeMillis()
        if (time == null || abs(currentTimeMillis - time) > click.fastClickTime) {
            method.invoke(host, view)
        }
    }
}