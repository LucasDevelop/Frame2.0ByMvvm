package com.luan.core.base

import androidx.lifecycle.LiveData

/**
 * @package    com.luan.core.base
 * @author     luan
 * @date       2020/9/16
 * @des
 */
open class BaseLive<T> :LiveData<T>(){

    override fun postValue(value: T) {
        super.postValue(value)
    }

    override fun setValue(value: T) {
        super.setValue(value)
    }
}