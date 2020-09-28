package com.luan.core.net.converter

import android.net.ParseException
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.luan.core.config.CoreConfig
import com.luan.core.exception.LoginExpiredException
import com.luan.core.exception.NullBodyException
import com.luan.core.exception.RequestException
import com.luan.core.ext.logE
import com.luan.core.net.request.NetBean
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Converter
import java.lang.reflect.Type

/**
 * @package    com.luan.core.net.converter
 * @author     luan
 * @date       2020/9/16
 * @des        通用数据解析
 */
class CommentConverter<T>(val gson: Gson, val adapter: TypeAdapter<out Any>, val type: Type) :
    Converter<ResponseBody, T> {

    override fun convert(response: ResponseBody): T? {
        val content = String(response.bytes(), Charsets.UTF_8)
        try {
            val jsonObject = JSONObject(content)
            if (jsonObject.has(CoreConfig.Net.responseCodeName) && jsonObject.has(CoreConfig.Net.responseMsgName)) {
                val code = jsonObject.getInt(CoreConfig.Net.responseCodeName)
                val msg = jsonObject.getString(CoreConfig.Net.responseMsgName)
                //解析内层数据
                val body = if (jsonObject.has(CoreConfig.Net.responseBodyName))
                    jsonObject.getString(CoreConfig.Net.responseBodyName)
                else
                    null
                val data = NetBean<T>()
                data.code = code
                data.msg = msg
                if (type.isBase()) {//基本类型直接强转
                    data.data = body as? T
                } else {
                    data.data = if (body == null) null else gson.fromJson<T>(body, type)
                }
                if (code == CoreConfig.Net.responseSuccessCode) {
                    if (!jsonObject.has(CoreConfig.Net.responseBodyName)) {//如果不存在body字段，将最外层数据进行T类型解析
                        data.data = gson.fromJson<T>(content, type)
                        return data.data
                    } else if (data.data == null) {
                        throw NullBodyException(data, "body 为空")
                    } else {
                        return data.data
                    }
                } else {//服务器自定义错误
                    if (code == CoreConfig.Net.responseLoginExpiredCode) {
                        throw LoginExpiredException(data, "登陆过期")
                    } else {
                        throw RequestException(data, "其他服务器自定义错误错误")
                    }
                }
            } else {
                //外层json格式不是CoreConfig中配置的名称
                "外层json格式不是CoreConfig中配置的名称".logE()
                val jsonReader = gson.newJsonReader(response.charStream())
                return parse(jsonReader)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            //非json数据
        } finally {
            response.close()
        }
        return null
    }

    //是否是基本数据类型
    private fun Type?.isBase(): Boolean {
        when (this) {
            String::class.java,
            Int::class.java,
            Char::class.java,
            Short::class.java,
            Float::class.java,
            Double::class.java,
            Long::class.java,
            Boolean::class.java,
            Byte::class.java -> return true
            else -> return false
        }
    }

    private fun parse(jsonReader: JsonReader): T {
        try {
            val result = adapter.read(jsonReader)
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw JsonIOException("JSON document was not fully consumed.")
            }
            return result as T
        } finally {

        }
    }
}