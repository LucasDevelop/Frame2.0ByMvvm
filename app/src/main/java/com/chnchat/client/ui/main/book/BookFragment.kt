package com.chnchat.client.ui.main.book

import androidx.lifecycle.ViewModelProviders
import com.chnchat.client.R
import com.chnchat.client.ui.main.MainViewModel
import com.luan.base.design.ui.fragment.BaseListFragment
import com.luan.core.config.CoreConfig
import com.luan.core.ext.loadCircleImg
import com.luan.core.net.request.NetBean
import kotlinx.android.synthetic.main.fragment_book.*
import kotlinx.android.synthetic.main.include_home_title.*

/**
 * @package    BookFragment.kt
 * @author     luan
 * @date       2020/9/29
 * @des        book
 */
class BookFragment : BaseListFragment<BookViewModel>() {

    companion object {
        fun getInstance(): BookFragment {
            return BookFragment()
        }
    }

    override val layoutId: Int = R.layout.fragment_book
    override val isShowTitle: Boolean = false
    val mainViewModel by lazy { ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java) }

    override fun initView() {
        mainViewModel.userInfoLive.observer {
            it?.let {
                v_user_icon.loadCircleImg(it.userInfo.avatarUrl)
                v_username.text = it.userInfo.nickName
            }
        }
        bindRefreshView(v_list_container)
            .bindRequest(vm.teacherListLive, BookTeacherAdapter()) {
                return@bindRequest List(CoreConfig.UI.pageCount) { it }
            }
            .setRefreshEvent {
                vm.teacherListLive.setValue(NetBean(CoreConfig.Net.responseSuccessCode,"",1))
            }
            .setLoadMoreEvent {
                vm.teacherListLive.setValue(NetBean(CoreConfig.Net.responseSuccessCode,"",1))
            }

    }

    override fun initData() {
    }

}