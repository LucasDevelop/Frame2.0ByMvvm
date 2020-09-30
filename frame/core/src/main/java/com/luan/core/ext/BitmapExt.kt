package com.luan.core.ext

import android.graphics.*

/**
 * @package    com.luan.core.ext
 * @author     luan
 * @date       2020/9/30
 * @des
 */

//圆角裁剪
fun Bitmap.roundedCorp(
    newBitmap: Bitmap? = null,//新的Bitmap规格，可参考方法里的result创建方式
    topLeft: Float = 0f,//左上角
    topRight: Float = 0f,//右上
    bottomLeft: Float = 0f,//左下
    bottomRight: Float = 0f//右下
): Bitmap {
    var result: Bitmap? = newBitmap
    if (result == null) {
        result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    }
    //圆角不能大于图片的大小
    if (width / 2 < topLeft || width / 2 < topRight || width / 2 < bottomLeft || width / 2 < bottomRight) {
        return this
    }
    if (height / 2 < topLeft || height / 2 < topRight || height / 2 < bottomLeft || height / 2 < bottomRight) {
        return this
    }
    if (result == null) return this
    Canvas(result).apply {
        val paint = Paint()
        paint.isAntiAlias = true
        val rectF = Rect(0, 0, width, height)
        //用贝塞尔曲线绘制圆角路径
        val path = Path()
        val startPoint = PointF()//起始点
        if (topLeft > 0f) {//左上角
            startPoint.set(0f, topLeft)
            path.moveTo(0f, topLeft)
            path.quadTo(0f, 0f, topLeft, 0f)
        } else {
            startPoint.set(0f, 0f)
            path.moveTo(0f, 0f)
        }
        if (topRight > 0) {//右上
            path.lineTo(width.toFloat() - topRight, 0f)
            path.quadTo(width.toFloat(), 0f, width.toFloat(), topRight)
        } else {
            path.lineTo(width.toFloat(), 0f)
        }
        if (bottomRight > 0) {//右下
            path.lineTo(width.toFloat(), height.toFloat() - bottomRight)
            path.quadTo(width.toFloat(), height.toFloat(), width.toFloat() - bottomRight, height.toFloat())
        } else {
            path.lineTo(width.toFloat(), height.toFloat())
        }
        if (bottomLeft > 0) {//左下
            path.lineTo(bottomLeft, height.toFloat())
            path.quadTo(0f, height.toFloat(), 0f, height.toFloat() - bottomLeft)
        } else {
            path.lineTo(0f, height.toFloat())
        }
        //结束
        path.lineTo(startPoint.x, startPoint.y)
        drawPath(path, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)//裁剪方式
        drawBitmap(this@roundedCorp, rectF, rectF, paint)
    }
    return result!!
}