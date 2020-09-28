package com.luan.core.net

import com.luan.annotations.net.Host
import com.luan.core.config.CoreConfig
import com.luan.core.exception.CoreInitException
import com.luan.core.net.converter.CommentConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

/**
 * @package    com.luan.core.net
 * @author     luan
 * @date       2020/9/14
 * @des        网络基本组建
 */
object NetModule {
    private val apiServers = ArrayList<ApiServer>()

    fun initModule(apis: Array<KClass<*>>) {
        apiServers.clear()
        initHost(apis)
        initOkHttp()
        initRetrofit()
    }

    private fun initHost(apis: Array<KClass<*>>) {
        //从注解上提取参数
        apis.forEach { clazz ->
            if (clazz.annotations.find { it is Host } == null) {
                throw CoreInitException("$clazz 文件缺少Host注解")
            }
            clazz.annotations.forEach { annotation ->
                if (annotation is Host) {
                    apiServers.add(ApiServer().apply {
                        host = annotation.host
                        apiClass = clazz
                        //反射创建拦截器
                        interceptors = annotation.interceptors.map {
                            it.java.getConstructor().newInstance() as Interceptor
                        }.toList()
                    })
                }
            }
        }
    }

    fun <T : Any> getApiInstance(apiClass: KClass<T>): T {
        return requireNotNull(apiServers.find { it.apiClass == apiClass }?.apiProxy as T)
    }

    private fun initOkHttp() {
        apiServers.forEach { api ->
            api.okHttpClient = OkHttpClient.Builder().apply {
                api.let {
                    it.interceptors?.forEach {
                        addInterceptor(it)
                    }
                }
            }.addInterceptor(HttpLoggingInterceptor { message ->
                Platform.get().log(message, Platform.INFO, null)
            }.setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(CoreConfig.Net.connTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(CoreConfig.Net.readTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(CoreConfig.Net.writeTimeout, TimeUnit.MILLISECONDS)
                .cache(CoreConfig.Net.cache)
                .build()
        }

    }

    private fun initRetrofit() {
        apiServers.forEach {
            it.apiProxy = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(CommentConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .client(requireNotNull(it.okHttpClient))
                .baseUrl(it.host)
                .build()
                .create(it.apiClass?.java)
        }
    }
}