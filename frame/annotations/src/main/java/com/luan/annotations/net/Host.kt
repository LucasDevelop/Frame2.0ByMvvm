package com.luan.annotations.net

import org.omg.PortableInterceptor.Interceptor
import kotlin.reflect.KClass

/**
 * @package    com.luan.core.annotation
 * @author     luan
 * @date       2020/9/14
 * @des        服务器域名,用于和api文件进行域名绑定
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Host(
    val host: String,
    val interceptors: Array<KClass<*>> = []
)