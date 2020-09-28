package com.chnchat.client.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chnchat.client.R
import com.chnchat.client.ext.bindNav
import com.chnchat.client.ui.main.MainViewModel
import com.chnchat.client.ui.main.home.record.RecordClassListActivity
import com.chnchat.client.ui.main.message.MessageFragment
import com.luan.annotations.event.Click
import com.luan.base.design.ui.fragment.BaseVMFragment
import com.luan.core.ext.loadCircleImg
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @package    HomeFragment.kt
 * @author     luan
 * @date       2020/9/27
 * @des        首页
 */
class HomeFragment : BaseVMFragment<HomeViewModel>() {
    companion object {
        fun getInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override val layoutId: Int = R.layout.fragment_home
    override val isShowTitle: Boolean = false
    val mainViewModel by lazy { ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java) }
    val upComingClassAdapter = UpComingClassAdapter()
    val recordClassAdapter = RecordClassAdapter()
    val bannerAdapter = HomeBannerAdapter()

    override fun initView() {
        mainViewModel.userInfoLive.observer {
            it?.let {
                v_user_icon.loadCircleImg(it.userInfo.avatarUrl)
            }
        }
        v_upcoming_class_pager.adapter = upComingClassAdapter
        upComingClassAdapter.setNewInstance(List(10) { it }.toMutableList())
        v_upcoming_class_pager.bindNav(v_upcoming_class_nav_left, v_upcoming_class_nav_right)
        v_recorded_classes_pager.adapter = recordClassAdapter
        recordClassAdapter.setNewInstance(List(10) { it }.toMutableList())
        v_recorded_classes_pager.bindNav(v_recorded_classes_nav_left, v_recorded_classes_nav_right)
        initBanner()

//        RecordClassListActivity.launch(requireContext())
    }

    private fun initBanner() {
        v_banner.addBannerLifecycleObserver(this)
            .setAdapter(bannerAdapter)
            .setIndicator(CircleIndicator(requireContext()))
        bannerAdapter.setDatas(List(5){it.toString()})
        bannerAdapter.notifyDataSetChanged()
    }

    override fun initData() {
    }


}