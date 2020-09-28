package com.chnchat.client.ui.main.home

import android.view.ViewGroup
import android.widget.ImageView
import com.chnchat.client.R
import com.luan.core.ext.LParam
import com.luan.core.ext.createViewGroupLP
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.holder.BannerImageHolder

/**
 * @package    com.chnchat.client.ui.main.home
 * @author     luan
 * @date       2020/9/28
 * @des
 */
class HomeBannerAdapter : BannerAdapter<String, BannerImageHolder>(ArrayList<String>()) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerImageHolder {
        return BannerImageHolder(ImageView(parent.context).apply {
            setImageResource(R.drawable.shape_def_img_c8)
            layoutParams = createViewGroupLP(LParam.MM)
            scaleType = ImageView.ScaleType.CENTER_CROP
        })
    }

    override fun onBindView(holder: BannerImageHolder, data: String, position: Int, size: Int) {
    }
}