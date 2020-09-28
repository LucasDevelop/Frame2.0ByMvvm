package com.chnchat.client.ui.main

import com.chnchat.client.ext.api
import com.chnchat.client.helper.ImHelper
import com.chnchat.client.net.bean.ImSignBean
import com.chnchat.client.net.param.UuidParam
import com.chnchat.client.user.User
import com.chnchat.client.user.UserBean
import com.luan.core.base.BaseViewModel
import com.luan.core.net.request.Request

/**
 * @package    com.chnchat.client.ui.main
 * @author     luan
 * @date       2020/9/27
 * @des
 */
class MainViewModel: BaseViewModel() {
    val imSignLive = Request<ImSignBean>()
    val userInfoLive = Request<UserBean>()

    fun loginIm(){
        imSignLive.call(api.getImSign()).observer {
            it?.let {
                ImHelper.loginIm(it.identifier,it.usersig,{},{module, errCode, errMsg ->  })
            }
        }
    }

    fun getUserInfo(){
        userInfoLive.call(api.getUserInfo(UuidParam(User.userData.uuid)))
            .observer {
                User.refreshUserData(it)
            }
    }
}