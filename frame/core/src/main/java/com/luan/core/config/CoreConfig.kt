package com.luan.core.config

import com.luan.core.CoreApp
import okhttp3.Cache
import okhttp3.Interceptor
import java.io.File

/**
 * @package    com.luan.core.config
 * @author     luan
 * @date       2020/9/14
 * @des        框架配置
 */
interface CoreConfig {

    object Net {
        var connTimeout = 1000 * 60L //链接超时时间
        var readTimeout = 1000 * 60L //读取超时时间
        var writeTimeout = 1000 * 60L //写入超时时间
        var cache = Cache(File(CoreApp.instance.filesDir, "netCache"), 10 * 1024 * 1024)//缓存

        var responseSuccessCode = 200//请求成功状态码
        var responseLoginExpiredCode = 200//登陆过期状态码
        var responseCodeName = "code"//响应结构体的code字端命名
        var responseMsgName = "msg"//响应结构体的msg字端命名
        var responseBodyName = "body"//响应结构体的body字端命名
    }

    object UI {
        var page = 1//分页初始页码
        var pageCount = 20//每页数量
    }
}