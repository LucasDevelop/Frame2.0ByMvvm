package com.chnchat.client.ext

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.BaseQuickAdapter
import com.luan.core.ext.hide
import com.luan.core.ext.log
import com.luan.core.ext.show

/**
 * @package    com.chnchat.client.ext
 * @author     luan
 * @date       2020/9/28
 * @des
 */

//viewPager2与手动翻页绑定
fun ViewPager2.bindNav(leftNav: View, rightNav: View) {
    leftNav.setOnClickListener {
        if (adapter==null)return@setOnClickListener
        if (currentItem > 0) {
            currentItem -= 1
        }
    }
    rightNav.setOnClickListener {
        if (adapter==null)return@setOnClickListener
        if (currentItem < adapter!!.itemCount - 1) {
            currentItem += 1
        }
    }
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            if (adapter==null)return
            if (position == 0) {
                leftNav.hide()
            } else {
                leftNav.show()
            }
            if (position == adapter!!.itemCount - 1) {
                rightNav.hide()
            } else {
                rightNav.show()
            }
        }
    })
}