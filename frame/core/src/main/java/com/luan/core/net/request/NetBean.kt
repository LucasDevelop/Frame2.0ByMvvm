package com.luan.core.net.request

/**
 * @package    com.luan.core.net.request
 * @author     luan
 * @date       2020/9/16
 * @des
 */
open class NetBean<T> {
    var code = -1
    var msg = ""
    var data: T? = null
    constructor()

    constructor(code: Int, msg: String, data: T?) {
        this.code = code
        this.msg = msg
        this.data = data
    }

    constructor(data: T?) {
        this.data = data
    }
}