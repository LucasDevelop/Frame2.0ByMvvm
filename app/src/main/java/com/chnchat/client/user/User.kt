package com.chnchat.client.user

import com.blankj.utilcode.util.SPUtils
import com.luan.core.base.IUser
import com.luan.core.helper.CoreGson.gson

/**
 * @package    com.chnchat.client.user
 * @author     luan
 * @date       2020/9/25
 * @des
 */
object User : IUser<UserBean> {
    var userData: UserBean = UserBean()
    private val USER_DATA_KEY = "user_data_key"

    init {
        SPUtils.getInstance().getString(USER_DATA_KEY)?.let {
            userData = gson.fromJson(it, UserBean::class.java)
        }
    }

    override fun getUserBean(): UserBean = userData

    override fun isLogin(): Boolean = !userData.uuid.isNullOrEmpty()

    //退出登陆
    override fun logout() {
        userData = UserBean()
        SPUtils.getInstance().remove(USER_DATA_KEY)
    }

    //更新用户信息
    fun refreshUserData(userBean: UserBean?) {
        if (userBean == null) return
        this.userData = userBean.apply { uuid = this@User.userData.uuid }
        SPUtils.getInstance().put(USER_DATA_KEY, gson.toJson(userData))
    }

    fun refreshUuid(uuid: String) {
        this.userData.uuid = uuid
        SPUtils.getInstance().put(USER_DATA_KEY, gson.toJson(userData))
    }
}