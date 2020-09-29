package com.luan.annotations.event

import javax.swing.text.View

/**
 * @package    com.luan.annotations.event
 * @author     luan
 * @date       2020/9/25
 * @des         点击事件注解
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Click(vararg val ids: Int, val fastClickTime: Long = 300L)