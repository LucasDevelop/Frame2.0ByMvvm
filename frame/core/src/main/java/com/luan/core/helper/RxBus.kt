package com.luan.core.helper

import io.reactivex.Observable
import io.reactivex.processors.PublishProcessor

/**
 * @package    com.luan.core.helper
 * @author     luan
 * @date       2020/9/25
 * @des
 */
object RxBus {
    val bus = PublishProcessor.create<Any>().toSerialized()

    fun post(data:Any){
        bus.onNext(data)
    }

    inline fun <reified T> toFlow() = bus.ofType(T::class.java)

}