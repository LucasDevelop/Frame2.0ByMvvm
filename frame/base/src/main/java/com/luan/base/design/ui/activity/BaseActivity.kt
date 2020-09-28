package com.luan.base.design.ui.activity

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jaeger.library.StatusBarUtil
import com.luan.core.ui.ViewAction
import com.luan.core.ui.Theme
import com.luan.core.ui.widget.DecorView
import com.luan.base.R
import com.luan.core.ext.colorRes
import com.luan.core.utils.AnnotationUtil

/**
 * @package    com.luan.base
 * @author     luan
 * @date       2020/9/16
 * @des        简单的封装
 */
abstract class BaseActivity : AppCompatActivity(), ViewAction, Theme {
    override val isUseImmersive: Boolean = true
    override val titleLayoutId: Int = R.layout.core_view_title
    lateinit var decorView: DecorView
    override val statusBarColor: Int = R.color.base_transparent
    override val backgroundColor: Int = R.color.base_white
    override val isUseStatusView: Boolean = true
    override val isShowTitle: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        initTheme(this)
        super.onCreate(savedInstanceState)
        initBase()
        init()
    }

    private fun initTheme(activity: AppCompatActivity) {
        StatusBarUtil.setColor(activity, activity.colorRes(statusBarColor), 0)
        //根据状态栏颜色切换状态栏文字颜色
        if (activity.colorRes(statusBarColor) == Color.WHITE)
            StatusBarUtil.setLightMode(activity)
        else
            StatusBarUtil.setDarkMode(activity)
        activity.supportActionBar?.hide()//隐藏标题
        activity.window.setFormat(PixelFormat.TRANSLUCENT)//背景透明
    }

    //基础初始化
    private fun initBase() {
        decorView = DecorView(this,this, this, this)
        setContentView(decorView)
        AnnotationUtil.parseClickAnnotation(this,this)
        AnnotationUtil.parseLongClickAnnotation(this,this)
    }

    //禁止APP字体跟随系统字体大小改变
    override fun getResources(): Resources {
        val resources = super.getResources()
        val configuration = Configuration()
        configuration.setToDefaults()
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return resources
    }
}