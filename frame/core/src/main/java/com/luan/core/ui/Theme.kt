package com.luan.core.ui

import android.graphics.Color
import android.graphics.PixelFormat
import androidx.annotation.ColorRes
import androidx.annotation.IntRange
import androidx.appcompat.app.AppCompatActivity
import com.jaeger.library.StatusBarUtil
import com.luan.core.ext.colorRes

/**
 * @package    com.luan.base.design.ui
 * @author     luan
 * @date       2020/9/16
 * @des        主题
 */
interface Theme {

    //是否沉浸式
    val isUseImmersive: Boolean

    //是否使用status view
    val isUseStatusView:Boolean

    //状态栏颜色
    @get:ColorRes
    val statusBarColor: Int

    //界面背景色
    @get:ColorRes
    val backgroundColor: Int


}