package com.chnchat.client.ext

import com.chnchat.client.net.Api
import com.luan.core.net.NetModule

/**
 * @package    com.chnchat.client.ext
 * @author     luan
 * @date       2020/9/23
 * @des        模块对象
 */

const val p2pHantokPrefix = "hantok-center/"
const val p2MLivePrefix = "live-center/"
val api = NetModule.getApiInstance(Api::class)