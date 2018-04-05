package com.mkrworld.mobilpay.ui.custom

import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.support.v7.widget.AppCompatAutoCompleteTextView

import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 14/3/18.
 */

class EditTextDropDown : AppCompatAutoCompleteTextView {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".EditTextDropDown"
    }

    private var mPaint : Paint? = null
    private var mPath : Path? = null
    private var mArrowSize : Int = 0

    constructor(context : Context) : super(context) {
        init()
    }

    constructor(context : Context, attrs : AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context : Context, attrs : AttributeSet, defStyleAttr : Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onSizeChanged(w : Int, h : Int, oldw : Int, oldh : Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mArrowSize = (lineHeight.toFloat() * 0.4f).toInt()
        if (mArrowSize % 2 != 0) {
            mArrowSize ++
        }
        val paddingRight = paddingRight
        setPadding(paddingLeft, paddingTop, paddingRight + 2 * mArrowSize, paddingBottom)
    }

    /**
     * Method to set the color of the Dropdown Icon
     */
    fun setDropdownIconColor(color : Int) {
        Tracer.debug(TAG, "setDropdownIconColor: $color")
        mPaint !!.color = color
    }

    override fun onDraw(canvas : Canvas) {
        super.onDraw(canvas)
        var width = 0
        val measureText = paint.measureText(text.toString()).toInt()
        val writableWidth = getWidth() - paddingRight - paddingLeft
        if (measureText > writableWidth) {
            width = paddingLeft + measureText
        } else {
            width = paddingLeft + writableWidth
        }
        mPath !!.reset()
        val x1 = width + (mArrowSize shr 1)
        val x2 = x1 + mArrowSize
        val x3 = x1 + (x2 - x1 shr 1)
        val y1 = height - mArrowSize shr 1
        val y2 = y1 + mArrowSize
        mPath !!.moveTo(x1.toFloat(), y1.toFloat())
        mPath !!.lineTo(x2.toFloat(), y1.toFloat())
        mPath !!.lineTo(x3.toFloat(), y2.toFloat())
        mPath !!.lineTo(x1.toFloat(), y1.toFloat())
        canvas.drawPath(mPath !!, mPaint !!)
    }

    /**
     * Method to init the EditText
     */
    private fun init() {
        Tracer.debug(TAG, "init: ")
        maxLines = 1
        mPaint = Paint()
        mPaint !!.isAntiAlias = true
        mPaint !!.isFilterBitmap = true
        mPaint !!.style = Paint.Style.FILL
        mPaint !!.color = currentHintTextColor
        mPath = Path()
    }
}
