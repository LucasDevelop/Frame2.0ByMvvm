package com.luan.core.exception

import com.luan.core.net.request.NetBean

/**
 * @package    com.luan.core.exception
 * @author     luan
 * @date       2020/9/25
 * @des
 */
class NullBodyException:NullPointerException {
    var data: NetBean<*>?=null

    constructor() : super()
    constructor(dataBean: NetBean<*>?, message: String?) : super(message) {
        this.data = dataBean
    }

}