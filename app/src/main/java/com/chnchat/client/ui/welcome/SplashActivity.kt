package com.chnchat.client.ui.welcome

import android.media.MediaPlayer
import android.net.Uri
import android.view.View
import com.chnchat.client.R
import com.chnchat.client.ui.auth.login.LoginActivity
import com.chnchat.client.ui.auth.register.RegisterActivity
import com.chnchat.client.ui.main.MainActivity
import com.chnchat.client.user.User
import com.luan.base.design.ui.activity.BaseVMActivity
import com.luan.core.ext.addClicks
import com.luan.core.ext.log
import kotlinx.android.synthetic.main.activity_splash.*
import java.io.File

/**
 * @package    SplashActivity.kt
 * @author     luan
 * @date       2020/9/16
 * @des        启动界面
 */
class SplashActivity : BaseVMActivity<SplashViewModel>(), View.OnClickListener {
    override val layoutId: Int
        get() = R.layout.activity_splash

    override val isShowTitle: Boolean = false

    override fun initView() {
        if (User.isLogin()){
            MainActivity.launch(this)
        }else{
            initVideo()
        }
    }

    override fun initEvent() {
        super.initEvent()
        arrayOf(v_register,v_login).addClicks(this)
    }

    override fun initData() {
    }


    override fun onClick(v: View) {
        when (v) {
            v_login -> {
                LoginActivity.launch(this)
            }
            v_register -> {
                RegisterActivity.launch(this)
            }
        }
    }


    private fun initVideo() {
        v_video.setVideoURI(Uri.parse("android.resource://".plus(packageName).plus(File.separator).plus(R.raw.video)))
        v_video.setOnPreparedListener {
            it.isLooping = true
            it.start()
        }
        v_video.setOnErrorListener(object :MediaPlayer.OnErrorListener{
            override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
                "what:$what,extra:$extra".log()
                return true
            }
        })
        v_video.requestFocus()
    }

}