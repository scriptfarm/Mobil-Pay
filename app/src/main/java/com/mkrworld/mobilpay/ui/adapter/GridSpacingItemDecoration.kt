package com.mkrworld.mobilpay.ui.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View

import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 14/3/18.
 */

class GridSpacingItemDecoration : RecyclerView.ItemDecoration {
    private var mPaint : Paint?=null
    private var mSpanCount : Int?=null
    private var mSpacing : Int?=null
    private var mIsIncludeEdge : Boolean?=null

    /**
     * Constructor
     *
     * @param spanCount
     * @param spacing
     * @param dividerColor
     * @param isIncludeEdge
     */
    constructor(spanCount : Int, spacing : Int, dividerColor : Int, isIncludeEdge : Boolean) {
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.isFilterBitmap = true
        mPaint!!.color = dividerColor
        mPaint!!.strokeWidth = spacing.toFloat()
        mSpanCount = spanCount
        mSpacing = spacing
        mIsIncludeEdge = isIncludeEdge
    }

    override fun getItemOffsets(outRect : Rect, view : View, parent : RecyclerView, state : RecyclerView.State?) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % mSpanCount!! // item column
        if (mIsIncludeEdge!!) {
            outRect.left = mSpacing!! - column * mSpacing!! / mSpanCount!! // mSpacing - column * ((1f / mSpanCount) * mSpacing)
            outRect.right = (column + 1) * mSpacing!! / mSpanCount!! // (column + 1) * ((1f / mSpanCount) * mSpacing)

            if (position < mSpanCount!!) { // top edge
                outRect.top = mSpacing!!
            }
            outRect.bottom = mSpacing!! // item bottom
        } else {
            outRect.left = column * mSpacing!! / mSpanCount!! // column * ((1f / mSpanCount) * mSpacing)
            outRect.right = mSpacing!! - (column + 1) * mSpacing!! / mSpanCount!! // mSpacing - (column + 1) * ((1f /    mSpanCount) * mSpacing)
            if (position >= mSpanCount!!) {
                outRect.top = mSpacing!! // item top
            }
        }
    }

    override fun onDraw(c : Canvas, parent : RecyclerView, state : RecyclerView.State?) {
        super.onDraw(c, parent, state)
        val hX1 = parent.paddingLeft
        val hX2 = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val y = child.bottom + params.bottomMargin + (mSpacing!! shr 1)
            c.drawLine(hX1.toFloat(), y.toFloat(), hX2.toFloat(), y.toFloat(), mPaint)
            if ((i + 1) % mSpanCount!! != 0) {
                val vX = child.right - params.rightMargin + (mSpacing!! shr 1)
                val vY1 = child.top - params.topMargin
                val vY2 = child.bottom - params.bottomMargin
                c.drawLine(vX.toFloat(), vY1.toFloat(), vX.toFloat(), vY2.toFloat(), mPaint)
            }
        }
    }

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".ItemOffsetDecoration"
    }
}
