package com.luan.core.ext

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * @package    com.luan.core.ext
 * @author     luan
 * @date       2020/9/21
 * @des
 */

fun Disposable.syncLifecycle(owner: LifecycleOwner) {
    owner.lifecycle.addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                if (!isDisposed)
                    this@syncLifecycle.dispose()
            }
        }
    })
}

fun LifecycleOwner.bindLifecycle(
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    block: () -> Unit
) {
    lifecycle.addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, e: Lifecycle.Event) {
            if (event == e) {
                lifecycle.removeObserver(this)
            }
            block()
        }
    })
}