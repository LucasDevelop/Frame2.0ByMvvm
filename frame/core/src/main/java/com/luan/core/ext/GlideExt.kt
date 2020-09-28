package com.luan.core.ext

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.luan.core.R

/**
 * @package    com.luan.core.ext
 * @author     luan
 * @date       2020/9/27
 * @des
 */

fun ImageView.loadCircleImg(url: String,@DrawableRes defImg:Int = R.mipmap.core_icon_404) {
    Glide.with(this)
        .asBitmap()
        .apply(RequestOptions().transform(CircleCrop()).error(defImg))
        .load(url)
        .into(this)
}