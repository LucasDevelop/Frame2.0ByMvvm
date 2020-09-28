package com.luan.core.net

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import kotlin.reflect.KClass

/**
 * @package    com.luan.core.net
 * @author     luan
 * @date       2020/9/15
 * @des
 */
class ApiServer {
    var host: String = ""
    var interceptors: List<Interceptor>? = null
    var okHttpClient: OkHttpClient? = null
    var apiClass: KClass<*>? = null
    var apiProxy: Any? = null

    override fun toString(): String {
        return "ApiServer(host='$host', interceptors=$interceptors, okHttpClient=$okHttpClient, apiClass=$apiClass, apiProxy=$apiProxy)"
    }


}