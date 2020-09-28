package com.chnchat.client.ui.main.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chnchat.client.R
import com.chnchat.client.ui.main.book.BookFragment
import com.luan.base.design.ui.fragment.BaseVMFragment

class AccountFragment : BaseVMFragment<AccountViewModel>() {
    companion object {
        fun getInstance(): AccountFragment {
            return AccountFragment()
        }
    }

    override val layoutId: Int = R.layout.fragment_account
    override val isShowTitle: Boolean = false
    override fun initView() {
    }

    override fun initData() {
    }
}