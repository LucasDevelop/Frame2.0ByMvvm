package com.chnchat.client.net.param

/**
 * @package    com.chnchat.client.net.param
 * @author     luan
 * @date       2020/9/27
 * @des  type 1注册 2忘记密码 3绑定邮箱
 */
class EmailCodeParam(val email:String, val type:Int) {
}