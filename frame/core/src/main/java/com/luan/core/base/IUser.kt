package com.luan.core.base

/**
 * @package    com.luan.core.base
 * @author     luan
 * @date       2020/9/25
 * @des        用户数据
 */
interface IUser<T> {
    fun getUserBean():T
    //是否登陆
    fun isLogin(): Boolean

    fun logout()
}