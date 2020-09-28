package com.luan.base.design.ui.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.luan.core.base.BaseViewModel
import com.luan.core.utils.GenericUtil
import kotlin.reflect.KClass

/**
 * @package    com.luan.base.design.ui.fragment
 * @author     luan
 * @date       2020/9/17
 * @des
 */
abstract class BaseVMFragment<VM : BaseViewModel> : BaseFragment(){
    val vm:VM by lazy {
        ViewModelProvider(ViewModelStore(), ViewModelProvider.AndroidViewModelFactory(requireActivity().application))
            .get((GenericUtil.getClassType(this, ViewModel::class) as KClass<VM>).java)
    }
}