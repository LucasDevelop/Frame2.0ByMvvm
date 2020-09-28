package com.chnchat.client.ui.main.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chnchat.client.R
import com.luan.base.design.ui.fragment.BaseVMFragment

class BookFragment : BaseVMFragment<BookViewModel>() {
    companion object{
        fun getInstance(): BookFragment {
            return BookFragment()
        }
    }

    override val layoutId: Int = R.layout.fragment_book
    override val isShowTitle: Boolean = false
    override fun initView() {
    }

    override fun initData() {
    }

}