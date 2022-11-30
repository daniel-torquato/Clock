package com.example.testingcanvasconcepts.views

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import java.lang.Float.min
import kotlin.math.cos
import kotlin.math.sin


class Clock(context: Context, attrs: AttributeSet): View(context, attrs) {

    private var mRadius : Float = 0f
    private var mCenterX : Float = 0f
    private var mCenterY : Float = 0f
    private var mClockRadius : Float = 0f
    private var mPinSize : Float = 0f
    private var mPinPosAngle : Float = 30f
        set(angle) {
            field = angle
            invalidate()
        }
    private var mPinPosRadius : Float = 0f


    private val animator: ObjectAnimator = ObjectAnimator.ofFloat(this, "mPinPosAngle", 0f, 360f).apply {
        duration = 3000
        interpolator = LinearInterpolator()
        repeatCount = ObjectAnimator.INFINITE
    }

    init {
        animator.start()
    }

    private val outerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = outerPaintWidth
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.e("MyTag", "$w x $h <- $oldw x $oldh")
        mRadius = 0.5f * min(w.toFloat(), h.toFloat())
        mCenterX = w.toFloat() / 2f
        mCenterY = h.toFloat() / 2f

        mClockRadius = clockRadiusFactor * mRadius
        mPinSize = pinSizeFactor * mRadius
        mPinPosRadius = pinPosRadiusFactor * mClockRadius
            }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.e("MyTag", "onLayout")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.e("MyTag", "onMeasure")
    }

    override fun onDraw(canvas: Canvas) {




        drawOuterLimits(canvas)
        drawPin(canvas)
    }

    private fun drawPin(canvas: Canvas) {
        val startX: Float = mCenterX + mPinPosRadius * cos(mPinPosAngle.toRad())
        val startY = mCenterY + mPinPosRadius * sin(mPinPosAngle.toRad())
        val stopX = mCenterX + (mPinPosRadius + mPinSize) * cos(mPinPosAngle.toRad())
        val stopY = mCenterY + (mPinPosRadius + mPinSize) * sin(mPinPosAngle.toRad())

        canvas.drawLine(startX, startY, stopX, stopY, outerPaint)
    }

    private fun drawOuterLimits(canvas: Canvas) {
        canvas.drawCircle(mCenterX, mCenterY, mClockRadius, outerPaint)
    }

    companion object {
        const val outerPaintWidth = 10f
        const val pinSizeFactor = 0.1f
        const val clockRadiusFactor = 0.65f
        const val pinPosRadiusFactor = 0.8f
    }
}