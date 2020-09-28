package com.luan.core.exception

import com.luan.core.net.request.NetBean
import java.lang.Exception

/**
 * @package    com.luan.core.exception
 * @author     luan
 * @date       2020/9/16
 * @des
 */
class  LoginExpiredException:Exception {
    var data: NetBean<*>?=null

    constructor() : super()
    constructor(dataBean: NetBean<*>?, message: String?) : super(message) {
        this.data = dataBean
    }
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}