package com.luan.base.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.FrameLayout
import com.luan.base.R

/**
 * @package    com.cj.customwidget.widget
 * @author     luan
 * @date       2020/9/28
 * @des         阴影容器
 */
class ShadowGroup : FrameLayout {
    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    val shadowPaint = Paint()
    val shadowRectF = RectF()
    val defPadding = 15f
    var shadowDx = 2f
    var shadowDy = 5f
    var shadowRadius = 15f
    var shadowColor = Color.parseColor("#33000000")

    private fun initView(context: Context, attrs: AttributeSet?) {
        attrs?.apply {
            val obtain = context.obtainStyledAttributes(attrs, R.styleable.ShadowGroup)
            shadowDx = obtain.getDimension(R.styleable.ShadowGroup_shadowDX, shadowDx)
            shadowDy = obtain.getDimension(R.styleable.ShadowGroup_shadowDY, shadowDy)
            shadowRadius = obtain.getDimension(R.styleable.ShadowGroup_shadowRadius, shadowRadius)
            shadowColor = obtain.getColor(R.styleable.ShadowGroup_shadowColor, shadowColor)
            obtain.recycle()
        }
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        shadowPaint.isAntiAlias = true
        shadowPaint.style = Paint.Style.FILL
        shadowPaint.color = Color.WHITE
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        shadowRectF.left = if (paddingLeft > 0) paddingLeft.toFloat() else defPadding
        shadowRectF.right = width.toFloat() - if (paddingRight > 0) paddingRight.toFloat() else defPadding
        shadowRectF.top = if (paddingTop > 0) paddingTop.toFloat() else defPadding
        shadowRectF.bottom = height.toFloat() - if (paddingBottom > 0) paddingBottom.toFloat() else defPadding
    }

    override fun dispatchDraw(canvas: Canvas) {
        shadowPaint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor)
        canvas.drawRoundRect(shadowRectF, shadowRadius, shadowRadius, shadowPaint)
        canvas.save()
        super.dispatchDraw(canvas)
    }

}