package com.luan.core.ext

import android.util.Log

/**
 * @package    com.luan.core.ext
 * @author     luan
 * @date       2020/9/15
 * @des        日志
 */
private val TAG = "lucas"

fun Any?.log() {
    Log.d(TAG, this?.toString() ?: "")
}

fun Any?.logE() {
    Log.e(TAG, this?.toString() ?: "")
}