package com.luan.core

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.blankj.utilcode.util.Utils
import com.luan.core.custom.dialog.INetLoadingDialog

/**
 * @package    com.luan.core
 * @author     luan
 * @date       2020/9/14
 * @des        框架入口
 */
abstract class CoreApp : Application() {

    companion object{
        lateinit var instance:CoreApp
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
        coreInit()
        onInitConfig()
    }

    private fun coreInit() {
        Utils.init(this)
    }

    //配置初始化
    abstract fun onInitConfig()

    //配置网络加载弹窗
    abstract fun createNetDialog(context: Context):INetLoadingDialog

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}