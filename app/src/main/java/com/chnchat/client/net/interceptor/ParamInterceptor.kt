package com.chnchat.client.net.interceptor

import com.blankj.utilcode.util.DeviceUtils
import com.chnchat.client.BuildConfig
import com.chnchat.client.ext.p2pHantokPrefix
import com.chnchat.client.user.User
import com.luan.core.ext.log
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import okio.Buffer
import org.json.JSONObject
import java.lang.Exception
import java.nio.charset.Charset
import java.util.*

/**
 * @package    com.chnchat.client.net.interceptor
 * @author     luan
 * @date       2020/9/23
 * @des
 */
class ParamInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = request.url.newBuilder().scheme(request.url.scheme).host(request.url.host).build()
        val newRequest = request.newBuilder().also { build ->
            //添加公共参数
            build.addHeader("uuid", User.userData.uuid?:"")
            build.addHeader("version", BuildConfig.VERSION_NAME)
            build.addHeader("mac", DeviceUtils.getMacAddress())
            build.addHeader("imei", UUID.randomUUID().toString())
//            "request.url.toUrl():${request.url.toUrl().toString()}".log()
            if (request.url.toUrl().toString().contains(p2pHantokPrefix)){
                if (request.body is FormBody){
                    build.method(request.method, request.body)
                }else if (request.body is MultipartBody){
                    build.method(request.method, request.body)
                }else{
                    request.body?.let {body->
                        val buffer = Buffer()
                        body.writeTo(buffer)
                        val charset = Charset.forName("UTF-8")
                        if (body.contentType()!=null){
                            body.contentType()!!.charset(charset)?.let {cs->
                                val originParam = buffer.readString(cs)
                                val jsonObject = JSONObject()
                                jsonObject.put("app",JSONObject().apply {
                                    put("appId","paas")
                                    put("timeStamp","TIMESTAMP")
                                    put("nonce","NONCE")
                                    put("signature","21aa0011472249b4292e81504f3917bd")
                                })
                                jsonObject.put("body",JSONObject(originParam))
                                build.method(request.method, jsonObject.toString().toRequestBody(body.contentType()))
                            }
                        }
                    }
                }
            }else{
                build.method(request.method, request.body)
            }
            build.url(newUrl)
        }.build()
        try {
            return chain.proceed(newRequest)
        }catch (e:Exception){
            e.printStackTrace()
        }
       return chain.proceed(request)
    }
}