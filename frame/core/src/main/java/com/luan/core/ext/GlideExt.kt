package com.luan.core.ext

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.luan.core.R
import com.luan.core.ext.transform.RoundedCorp

/**
 * @package    com.luan.core.ext
 * @author     luan
 * @date       2020/9/27
 * @des
 */

//圆形图片
fun ImageView.loadCircleImg(url: String, @DrawableRes defImg: Int = R.drawable.core_shape_def_img) {
    Glide.with(this)
        .asBitmap()
        .apply(RequestOptions().transform(CircleCrop()).error(defImg))
        .load(url)
        .into(this)
}

//圆角图片
fun ImageView.loadCornerImg(url: String, radius: Int, @DrawableRes defImg: Int = R.drawable.core_shape_def_img) {
    Glide.with(this)
        .asBitmap()
        .apply(RequestOptions().transform(RoundedCorners(radius)).error(defImg))
        .load(url)
        .into(this)
}

//指定圆角图片
fun ImageView.loadCornerImg(
    url: String,
    topLeft: Int = 0,
    topRight: Int = 0,
    bottomLeft: Int = 0,
    bottomRight: Int = 0,
    @DrawableRes defImg: Int = R.mipmap.core_icon_404
) {
    Glide.with(this)
        .asBitmap()
        .apply(
            RequestOptions().transform(CenterCrop(),RoundedCorp(topLeft, topRight, bottomRight, bottomLeft)).error(defImg)
        )
        .load(url)
        .into(this)
}