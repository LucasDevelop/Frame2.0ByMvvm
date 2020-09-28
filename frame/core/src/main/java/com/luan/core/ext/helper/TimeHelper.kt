package com.luan.core.ext.helper

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.luan.core.ext.syncLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @package    com.luan.core.ext.helper
 * @author     luan
 * @date       2020/9/22
 * @des
 */
object TimeHelper {

    //倒计时
    fun countDown(
        owner: LifecycleOwner,
        block: (Long) -> Unit,
        duration: Long = 60,
        period: Long = 1,
        unit: TimeUnit = TimeUnit.SECONDS
    ): () -> Unit {
        val interval = Observable.interval(period, unit)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        var subscribe: Disposable? = null
        val start: () -> Unit = {
            subscribe = interval.subscribe({
                block(duration - it)
                if (it == duration) {
                    subscribe?.dispose()
                    return@subscribe
                }
            },{
                it.printStackTrace()
            },{})
        }
        subscribe?.syncLifecycle(owner)
        return start
    }
}