package com.luan.base.exception

import java.lang.RuntimeException

/**
 * @package    com.luan.base.exception
 * @author     luan
 * @date       2020/9/16
 * @des
 */
class FrameException:RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}