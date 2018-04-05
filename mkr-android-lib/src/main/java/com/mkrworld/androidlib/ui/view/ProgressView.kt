package com.mkrworld.androidlib.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.util.AttributeSet
import android.view.View

import com.mkrworld.androidlib.BuildConfig
import com.mkrworld.androidlib.utils.Tracer


/**
 * Progress Dialog View
 *
 *
 * Created by Sunny on 3/8/2016.
 */
class ProgressView : View {
    private var mRadius : Float = 0.toFloat()
    private val mPaintFill = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPaintText = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mHandler = Handler()
    private var mIndex = 0f
    private var mMargin = 0f
    private var mDotCount = 4
    private var mStep = 1
    private var mTestSize : Float = 0.toFloat()
    private var mTextMessage = "Processing"
    private var mIsRunning : Boolean = false

    private val mRunnable = object : Runnable {

        override fun run() {
            mIndex += mStep.toFloat()
            if (mIndex < 0) {
                mIndex = 1f
                mStep = 1
            } else if (mIndex > mDotCount - 1) {
                if (mDotCount - 2 >= 0) {
                    mIndex = (mDotCount - 2).toFloat()
                    mStep = - 1
                } else {
                    mIndex = 0f
                    mStep = 1
                }
            }
            try {
                invalidate()
                if (mIsRunning) {
                    mHandler.postDelayed(this, 200)
                }
            } catch (e : Exception) {
                Tracer.error(TAG, "run: " + e.message)
            }

        }
    }

    constructor(context : Context) : super(context) {
        init(context)
    }

    constructor(context : Context, attrs : AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context : Context, attrs : AttributeSet, defStyle : Int) : super(context, attrs, defStyle) {
        init(context)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    private fun init(context : Context) {
        val width = context.resources.displayMetrics.widthPixels.toFloat()
        mMargin = width * 0.005f
        mRadius = width * 0.02f
        mTestSize = width * 0.05f
        // dot fill color
        mPaintFill.style = Paint.Style.FILL
        mPaintFill.color = Color.WHITE
        // dot background color
        mPaint.style = Paint.Style.FILL
        mPaint.color = 0x44000000
        // Text paint
        mPaintText.style = Paint.Style.FILL
        mPaintText.color = Color.WHITE
        mPaintText.textSize = mTestSize
    }

    fun setDotsCount(count : Int) {
        mDotCount = count
    }

    fun setTextMessage(textMessage : String) {
        mTextMessage = textMessage
    }

    fun start() {
        mIsRunning = true
        mIndex = - 1f
        mHandler.removeCallbacks(mRunnable)
        mHandler.post(mRunnable)
    }

    fun stop() {
        mIsRunning = false
        mHandler.removeCallbacks(mRunnable)
    }

    override fun onMeasure(widthMeasureSpec : Int, heightMeasureSpec : Int) {
        var heightMeasureSpec = heightMeasureSpec
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(((mRadius + mTestSize) * 2f).toInt(), View.MeasureSpec.EXACTLY)
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onDraw(canvas : Canvas) {
        super.onDraw(canvas)
        mPaintText.textSize = mTestSize
        val textSize = mPaintText.measureText(mTextMessage)
        var textDx = width.toFloat() * 0.5f - textSize * 0.5f
        if (textSize > width) {
            textDx = 0f
        }
        canvas.drawText(mTextMessage, textDx, mTestSize, mPaintText)
        val requiredWidth = (mDotCount - 1).toFloat() * mRadius * 2f + (mDotCount - 1).toFloat() * mMargin
        var dX = width.toFloat() * 0.5f - requiredWidth * 0.5f
        val dY = height - mRadius * 2f
        for (i in 0..mDotCount) {
            if (i.toFloat() == mIndex) {
                canvas.drawCircle(dX, dY, mRadius, mPaintFill)
            } else {
                canvas.drawCircle(dX, dY, mRadius, mPaint)
            }
            dX = dX + (2f * mRadius + mMargin)
        }
    }

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".ProgressView"
    }
}


