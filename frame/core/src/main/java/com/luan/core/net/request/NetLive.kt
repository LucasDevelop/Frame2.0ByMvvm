package com.luan.core.net.request

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.luan.core.config.CoreConfig
import com.luan.core.ext.toast

/**
 * @package    com.luan.core.net.request
 * @author     luan
 * @date       2020/9/16
 * @des
 */

open class NetLive<B> : LiveData<NetBean<B>>() {
    var owner: LifecycleOwner?=null
    private var isShowToast = false
    var isShowLoading = true

    public override fun setValue(value: NetBean<B>?) {
        super.setValue(value)
    }

    override fun getValue(): NetBean<B>? {
        return super.getValue()
    }

    fun observer(block: (B?) -> Unit): NetLive<B> {
        if (owner!=null){
            observe(owner!!, {
                if (isShowToast && it.msg.isNotEmpty()) {
                    it.msg.toast()
                }
                if (it.code == CoreConfig.Net.responseSuccessCode){
                    block(it.data)
                }
            })
        }else{
            observeForever {
                if (isShowToast && it.msg.isNotEmpty()) {
                    it.msg.toast()
                }
                if (it.code == CoreConfig.Net.responseSuccessCode){
                    block(it.data)
                }
            }
        }
        return this
    }

    fun error(block: (NetBean<B>) -> Unit): NetLive<B> {
        if (owner!=null){
            observe(owner!!, {
                if (isShowToast && it.msg.isNotEmpty()) {
                    it.msg.toast()
                }
                if (it.code != CoreConfig.Net.responseSuccessCode){
                    block(it)
                }
            })
        }else{
            observeForever {
                if (isShowToast && it.msg.isNotEmpty()) {
                    it.msg.toast()
                }
                if (it.code != CoreConfig.Net.responseSuccessCode){
                    block(it)
                }
            }
        }

        return this
    }

    fun showLoading(isShowLoading: Boolean): NetLive<B> {
        this.isShowLoading = isShowLoading
        return this
    }

    fun showToast(isShow: Boolean): NetLive<B> {
        isShowToast = isShow
        return this
    }

}