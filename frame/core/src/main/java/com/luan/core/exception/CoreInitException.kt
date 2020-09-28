package com.luan.core.exception

import java.lang.RuntimeException

/**
 * @package    com.luan.core.exception
 * @author     luan
 * @date       2020/9/14
 * @des       初始化异常
 */
class CoreInitException:RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}