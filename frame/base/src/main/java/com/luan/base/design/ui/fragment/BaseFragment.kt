package com.luan.base.design.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.luan.base.R
import com.luan.core.ext.log
import com.luan.core.ui.ViewAction
import com.luan.core.ui.widget.DecorView
import com.luan.core.ui.Theme
import com.luan.core.utils.AnnotationUtil

/**
 * @package    com.luan.base.design.ui.fragment
 * @author     luan
 * @date       2020/9/17
 * @des
 */
abstract class BaseFragment : Fragment(), ViewAction, Theme {
    override val isUseImmersive: Boolean = true
    override val titleLayoutId: Int = R.layout.core_view_title
    lateinit var decorView: DecorView
    override val statusBarColor: Int = R.color.base_transparent
    override val backgroundColor: Int = R.color.base_white
    override val isUseStatusView: Boolean = true
    override val isShowTitle: Boolean = true
    var isInit = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initBase()
        return decorView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }
    //基础初始化
    private fun initBase() {
        decorView = DecorView(this,requireContext(), this, this)
        AnnotationUtil.parseClickAnnotation(this, decorView)
        AnnotationUtil.parseLongClickAnnotation(this, decorView)
    }
//
//    fun onVisible() {
//        if (!isInit) {
//            isInit = true
//            init()
//        }
//    }
//
//    fun onInvisible() {
//    }
//
//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        val change = isVisibleToUser != userVisibleHint
//        super.setUserVisibleHint(isVisibleToUser)
//        if (isResumed && change) {
//            onVisible()
//        } else {
//            onInvisible()
//        }
//    }
//
//    override fun onHiddenChanged(hidden: Boolean) {
//        super.onHiddenChanged(hidden)
//        if (hidden) {
//            onInvisible()
//        } else {
//            onVisible()
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if (userVisibleHint && isHidden) {
//            onVisible()
//        }
//    }


}