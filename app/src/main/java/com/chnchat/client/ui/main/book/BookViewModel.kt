package com.chnchat.client.ui.main.book

import com.luan.core.base.BaseViewModel
import com.luan.core.net.request.Request

/**
 * @package    com.chnchat.client.ui.main.book
 * @author     luan
 * @date       2020/9/27
 * @des
 */
class BookViewModel: BaseViewModel() {
    val teacherListLive = Request<Int>()

}