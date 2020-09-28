package com.luan.core.ext

import com.blankj.utilcode.util.ToastUtils

/**
 * @package    com.luan.core.ext
 * @author     luan
 * @date       2020/9/16
 * @des
 */

fun String?.toast(){
    ToastUtils.showShort(this?:"null")
}