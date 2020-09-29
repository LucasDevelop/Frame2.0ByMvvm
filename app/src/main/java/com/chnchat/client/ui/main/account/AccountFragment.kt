package com.chnchat.client.ui.main.account

import androidx.lifecycle.ViewModelProviders
import com.chnchat.client.R
import com.chnchat.client.ui.main.MainViewModel
import com.luan.base.design.ui.fragment.BaseVMFragment
import com.luan.core.ext.loadCircleImg
import kotlinx.android.synthetic.main.fragment_account.*
/**
 * @package    AccountFragment.kt
 * @author     luan
 * @date       2020/9/29
 * @des        我的
 */
class AccountFragment : BaseVMFragment<AccountViewModel>() {
    companion object {
        fun getInstance(): AccountFragment {
            return AccountFragment()
        }
    }

    override val layoutId: Int = R.layout.fragment_account
    val mainViewModel by lazy { ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java) }
    override val isShowTitle: Boolean = false
    override fun initView() {
        mainViewModel.userInfoLive.observer {
            it?.let {
                v_user_icon.loadCircleImg(it.userInfo.avatarUrl)
                v_username.text = it.userInfo.nickName
            }
        }
    }

    override fun initData() {
    }
}