package com.luan.core.net.request

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.NetworkUtils
import com.luan.core.CoreApp
import com.luan.core.config.Constant
import com.luan.core.config.CoreConfig
import com.luan.core.custom.dialog.INetLoadingDialog
import com.luan.core.exception.LoginExpiredException
import com.luan.core.exception.NullBodyException
import com.luan.core.exception.RequestException
import com.luan.core.ext.logE
import com.luan.core.ui.widget.StatusView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @package    com.luan.core.net.request
 * @author     luan
 * @date       2020/9/16
 * @des        网络请求
 */

//请求发送DSL形式
fun <T : Any> request(init: Request<T>.() -> Unit): Request<T> {
    val request = Request<T>()
    init(request)
    return request
}

class Request<T : Any> : LifecycleEventObserver, NetLive<T?>() {
    var bindView: StatusView? = null
    private var subscribe: Disposable? = null
    var loadingDialog: INetLoadingDialog? = null

    fun bindLifecycle(owner: LifecycleOwner?): Request<T> {
        this.owner = owner
        return this
    }

    fun call(owner: LifecycleOwner, observable: Observable<T>): NetLive<T?> {
        this.owner = owner
        return call(observable)
    }

    fun call(observable: Observable<T>): NetLive<T?> {
        beginRequest()
        subscribe = observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                bindView?.changeStatus(StatusView.Status.MASTER)
                setValue(NetBean(CoreConfig.Net.responseSuccessCode, "请求成功", it))
            }, {
//                it.printStackTrace()
                "net log:${it.message}".logE()
                try {
                    if (it is NullBodyException) {//请求成功，但是body=null
                        bindView?.changeStatus(StatusView.Status.MASTER)
                        setValue(it.data as NetBean<T?>)
                    }
                    if (it is LoginExpiredException) {//登陆过期
                        bindView?.changeStatus(StatusView.Status.MASTER)
                        setValue(it.data as NetBean<T?>)
                    } else if (it is RequestException) {//其他服务器自定义错误
                        setValue(it.data as NetBean<T?>)
                    } else {//其他错误
                        bindView?.changeStatus(StatusView.Status.SERVER_ERROR)
                        setValue(NetBean(Constant.OTHER_NET_ERROR, "其他错误", null))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                loadingDialog?.hideDialog()
                owner?.lifecycle?.removeObserver(this)
            }, {
                loadingDialog?.hideDialog()
                owner?.lifecycle?.removeObserver(this)
            })
        return this
    }

    @SuppressLint("MissingPermission")
    private fun beginRequest() {
        //绑定生命周期
        owner?.lifecycle?.addObserver(this)
        bindView?.changeStatus(StatusView.Status.LOADING)
        //检查网络
        if (!NetworkUtils.isConnected()) {//无网络
            subscribe?.dispose()
            bindView?.changeStatus(StatusView.Status.NOT_NETWORK)
        }
        if (isShowLoading) {
            if (loadingDialog == null && requestContent() != null)
                loadingDialog = CoreApp.instance.createNetDialog(requestContent()!!)
            loadingDialog?.showDialog()
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_DESTROY -> {
                if (loadingDialog?.isShowing() == true)
                    loadingDialog?.hideDialog()
                loadingDialog = null
                subscribe?.dispose()
            }
        }
    }

    fun requestContent(): Context? {
        if (owner is Activity) return owner as Activity
        else if (owner is Fragment) return (owner as Fragment).requireContext()
        else return null
    }
}