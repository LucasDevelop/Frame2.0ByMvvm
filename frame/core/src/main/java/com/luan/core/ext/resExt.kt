package com.luan.core.ext

import android.content.Context
import androidx.annotation.ColorRes

/**
 * @package    com.luan.core.ext
 * @author     luan
 * @date       2020/9/16
 * @des        资源类的扩展
 */

fun <T:Context> T.colorRes(@ColorRes color:Int)= resources.getColor(color)

//获取状态栏的高度
fun <T:Context> T.getStatusBarHeight()=
    resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height","dimen","android"))