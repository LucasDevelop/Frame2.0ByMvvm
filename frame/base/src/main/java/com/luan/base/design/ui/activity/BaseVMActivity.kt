package com.luan.base.design.ui.activity

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.luan.core.base.BaseViewModel
import com.luan.core.utils.GenericUtil
import kotlin.reflect.KClass

/**
 * @package    com.luan.base.design.ui.activity
 * @author     luan
 * @date       2020/9/16
 * @des
 */
abstract class BaseVMActivity<VM : BaseViewModel> : BaseActivity() {

    val vm:VM by lazy {
        ViewModelProvider(ViewModelStore(),ViewModelProvider.AndroidViewModelFactory(application))
            .get((GenericUtil.getClassType(this, ViewModel::class) as KClass<VM>).java)
    }

}