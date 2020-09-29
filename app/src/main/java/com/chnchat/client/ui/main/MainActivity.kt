package com.chnchat.client.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chnchat.client.R
import com.chnchat.client.ui.main.account.AccountFragment
import com.chnchat.client.ui.main.book.BookFragment
import com.chnchat.client.ui.main.home.HomeFragment
import com.chnchat.client.ui.main.message.MessageFragment
import com.google.android.material.tabs.TabLayout
import com.luan.base.design.ui.activity.BaseVMActivity
import com.luan.base.ext.bindView
import com.luan.core.ext.colorRes
import kotlinx.android.synthetic.main.activity_main.*
import com.luan.base.ext.bindView
/**
 * @package    MainActivity.kt
 * @author     luan
 * @date       2020/9/16
 * @des        主页
 */
class MainActivity : BaseVMActivity<MainViewModel>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override val layoutId: Int = R.layout.activity_main

    override val isShowTitle: Boolean = false
    override val statusBarColor: Int = R.color.white
    override val backgroundColor: Int = R.color.color_page

    val fragmentsByStudent = arrayOf(
        HomeFragment.getInstance(),
        BookFragment.getInstance(),
        MessageFragment.getInstance(),
        AccountFragment.getInstance()
    )

    val tabNamesByStudent = mapOf(
        "Home" to R.mipmap.ic_home,
        "Book" to R.mipmap.ic_book,
        "Message" to R.mipmap.ic_message,
        "Account" to R.mipmap.ic_account
    )

    override fun initView() {
        initTabs(tabNamesByStudent)
        switchToPage(0)
        vm.loginIm()
        vm.getUserInfo()
    }

    //切换界面
    private fun switchToPage(index: Int) {
        supportFragmentManager.let { manager ->
            manager.beginTransaction().let { transaction ->
                val tag = tabNamesByStudent.keys.toMutableList()[index]
                var cacheFragment = manager.findFragmentByTag(tag)
                if (cacheFragment == null) {
                    transaction.add(R.id.v_content, fragmentsByStudent[index], tag)
                } else {
                    transaction.show(cacheFragment)
                }
                //隐藏其他的fragment
                manager.fragments.forEach {
                    if (it != cacheFragment)
                        transaction.hide(it)
                }
                transaction.commitNowAllowingStateLoss()
            }
        }
    }

    private fun initTabs(
        tabs: Map<String, Int>
    ) {
        tabs.forEach { tabRes ->
            v_tabs.newTab().let { tabView ->
                val tab =
                    LayoutInflater.from(this).inflate(R.layout.tab_view, null, false) as ViewGroup
                tab.findViewById<ImageView>(R.id.v_icon).setImageResource(tabRes.value)
                tab.findViewById<TextView>(R.id.v_text).text = tabRes.key
                tabView.customView = tab
                v_tabs.addTab(tabView)
            }
        }
        v_tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                switchToPage(tab.position)
                (tab.customView as ViewGroup).findViewById<ImageView>(R.id.v_icon).setColorFilter(colorRes(R.color.color_5da1ff))
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                (tab.customView as ViewGroup).findViewById<ImageView>(R.id.v_icon).setColorFilter(colorRes(R.color.color_tab_unselect))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    override fun initData() {
    }

}