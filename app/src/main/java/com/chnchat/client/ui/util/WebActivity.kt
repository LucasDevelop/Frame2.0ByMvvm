package com.chnchat.client.ui.util

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chnchat.client.R
import com.luan.base.design.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : BaseActivity() {
    companion object {
        fun launch(context: Context, url: String) {
            val intent = Intent()
            intent.putExtra("url", url)
            context.startActivity(intent)
        }
    }

    override val layoutId: Int = R.layout.activity_web
    val url by lazy { intent.getStringExtra("url") }

    override fun initView() {
        v_web.settings.let {
            it.javaScriptEnabled = true
            it.useWideViewPort = true
            it.loadWithOverviewMode = true
        }
        v_web.loadUrl(url)
    }

    override fun initData() {
    }
}