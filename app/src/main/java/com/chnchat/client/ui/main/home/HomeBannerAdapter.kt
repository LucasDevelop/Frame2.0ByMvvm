package com.chnchat.client.ui.main.home

import android.view.ViewGroup
import android.widget.ImageView
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.chnchat.client.R
import com.luan.core.ext.LParam
import com.luan.core.ext.createViewGroupLP
import com.luan.core.ext.loadCornerImg
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.holder.BannerImageHolder

/**
 * @package    com.chnchat.client.ui.main.home
 * @author     luan
 * @date       2020/9/28
 * @des
 */
class HomeBannerAdapter : BannerAdapter<String, BannerImageHolder>(ArrayList<String>()) {
    val radius = SizeUtils.dp2px(15f)

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerImageHolder {
        return BannerImageHolder(ImageView(parent.context).apply {
            layoutParams = createViewGroupLP(LParam.MM)
//            scaleType = ImageView.ScaleType.CENTER_CROP
        })
    }

    override fun onBindView(holder: BannerImageHolder, data: String, position: Int, size: Int) {
        holder.imageView.loadCornerImg("https://pic4.zhimg.com/v2-30253c279faba2e77120862dd54d49d4_1440w.jpg?source=172ae18b", topLeft = radius, topRight = radius)
    }
}