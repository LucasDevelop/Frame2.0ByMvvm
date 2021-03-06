package com.luan.annotations.event

import javax.swing.text.View

/**
 * @package    com.luan.annotations.event
 * @author     luan
 * @date       2020/9/25
 * @des
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class LongClick(vararg val ids: Int, val fastClickTime: Int = 300)