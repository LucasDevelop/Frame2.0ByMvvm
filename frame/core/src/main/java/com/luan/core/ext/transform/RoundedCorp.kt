package com.luan.core.ext.transform

import android.graphics.Bitmap
import androidx.annotation.IntRange
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.util.Util
import com.luan.core.ext.roundedCorp
import java.nio.ByteBuffer
import java.security.MessageDigest

/**
 * @package    com.luan.core.ext.transform
 * @author     luan
 * @date       2020/9/30
 * @des        glide 圆角bitmap 转换器
 */
class RoundedCorp(
    @IntRange(from = 0) val topLeft: Int = 0,//左上角
    @IntRange(from = 0) val topRight: Int = 0,//右上
    @IntRange(from = 0) val bottomLeft: Int = 0,//左下
    @IntRange(from = 0) val bottomRight: Int = 0//右下
) : BitmapTransformation() {
    private val ID = "com.luan.core.ext.transform.RoundedCorp"
    private val ID_BYTES = ID.toByteArray(CHARSET)

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
        val radiusData =
            ByteBuffer.allocate(4 * 4).putInt(topLeft).putInt(topRight).putInt(bottomLeft).putInt(bottomRight).array()
        messageDigest.update(radiusData)
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val newBitmap = pool.get(toTransform.width, toTransform.height, toTransform.config)
        val roundedCorp = toTransform.roundedCorp(
            newBitmap,
            topLeft.toFloat(),
            topRight.toFloat(),
            bottomLeft.toFloat(),
            bottomRight.toFloat()
        )
        return roundedCorp
    }

    override fun equals(o: Any?): Boolean {
        if (o is RoundedCorp) {
            return topLeft == o.topLeft
                    &&topRight == o.topRight
                    &&bottomLeft == o.bottomLeft
                    &&bottomRight == o.bottomRight
        }
        return false
    }

    override fun hashCode(): Int {
        var hashCode = Util.hashCode(ID.hashCode(), Util.hashCode(topLeft))
        hashCode = Util.hashCode(topRight, hashCode)
        hashCode = Util.hashCode(bottomRight, hashCode)
        return Util.hashCode(bottomLeft, hashCode)
    }
}