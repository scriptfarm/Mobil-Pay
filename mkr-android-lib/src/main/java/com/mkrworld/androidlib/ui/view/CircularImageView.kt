package com.mkrworld.androidlib.ui.view

import android.content.Context
import android.graphics.*
import android.os.AsyncTask
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.mkrworld.androidlib.R

/**
 * Created by mkr on 30/3/18.
 */

class CircularImageView : View {

    companion object {
        private val PROGRESS_START_ANGLE_INC = 8
        private val PROGRESS_SWIPE_ANGLE_DEC = 5
        private val PROGRESS_MAX_ANGLE_DIFF = 270
        private val PROGRESS_MIN_ANGLE_DIFF = 30
    }

    private var mCircularBitmap : Bitmap? = null
    private var mProgressColor = Color.BLUE
    private var mProgressWidth : Int = 0
    private var mProgressImageRef : Int = 0
    private var mProgressStartAngle : Int = 0
    private var mProgressSwipeAngle : Int = 0
    private var mIsProgressMoveUp : Boolean = false
    private var mProgressRectF : RectF? = null
    private var mPaintProgress : Paint? = null
    private var mInvalidator : Invalidator? = null

    constructor(context : Context) : super(context) {
        init(null)
    }

    constructor(context : Context, attrs : AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context : Context, attrs : AttributeSet?, defStyleAttr : Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) constructor(context : Context, attrs : AttributeSet?, defStyleAttr : Int, defStyleRes : Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs)
    }

    override fun onMeasure(widthMeasureSpec : Int, heightMeasureSpec : Int) {
        if (View.MeasureSpec.getSize(widthMeasureSpec) > View.MeasureSpec.getSize(heightMeasureSpec)) {
            super.onMeasure(heightMeasureSpec, heightMeasureSpec)
        } else {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (mInvalidator == null || ! mInvalidator !!.isRunning) {
            mInvalidator = Invalidator()
            mInvalidator !!.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (mInvalidator != null) {
            mInvalidator !!.isRunning = false
        }
        mInvalidator = null
    }

    /**
     * Method to init View
     *
     * @param attrs
     */
    private fun init(attrs : AttributeSet?) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircularImageView, 0, 0)
            mProgressColor = a.getColor(R.styleable.CircularImageView_progress_color, Color.BLUE)
            mProgressWidth = a.getDimensionPixelOffset(R.styleable.CircularImageView_progress_width, (resources.displayMetrics.widthPixels.toFloat() * 0.05f).toInt())
            mProgressImageRef = a.getResourceId(R.styleable.CircularImageView_progress_image, - 1)
            a.recycle()
        }
        mPaintProgress = Paint()
        mPaintProgress !!.isAntiAlias = true
        mPaintProgress !!.isFilterBitmap = true
        mPaintProgress !!.color = mProgressColor
        mPaintProgress !!.style = Paint.Style.FILL_AND_STROKE
        mProgressRectF = RectF()
        mProgressStartAngle = 0
        mProgressSwipeAngle = PROGRESS_MIN_ANGLE_DIFF
        mIsProgressMoveUp = true
    }

    /**
     * Method to change the Image
     *
     * @param imgResId
     */
    fun setImageRes(imgResId : Int) {
        mProgressImageRef = imgResId
        recreateProgress()
    }

    override fun onSizeChanged(w : Int, h : Int, oldw : Int, oldh : Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        recreateProgress()
    }

    /**
     * Method to set the Progress Color
     *
     * @param progressColor
     */
    fun setProgressColor(progressColor : Int) {
        mProgressColor = progressColor
        mPaintProgress !!.color = mProgressColor
        invalidate()
    }

    /**
     * Method to set the Progress Width
     *
     * @param progressWidth
     */
    fun setProgressWidth(progressWidth : Int) {
        mProgressWidth = progressWidth
        recreateProgress()
    }

    override fun onDraw(canvas : Canvas) {
        super.onDraw(canvas)
        drawProgress(canvas)
        drawBitmap(canvas)
    }

    /**
     * Method to draw the bitmap
     *
     * @param canvas
     */
    private fun drawBitmap(canvas : Canvas) {
        if (mCircularBitmap != null && ! mCircularBitmap !!.isRecycled) {
            val rectWidth = width - (mProgressWidth shl 1)
            val l = width - rectWidth shr 1
            val t = width - rectWidth shr 1
            val rectF = RectF(l.toFloat(), t.toFloat(), (l + rectWidth).toFloat(), (t + rectWidth).toFloat())
            mPaintProgress !!.style = Paint.Style.FILL
            mPaintProgress !!.color = Color.WHITE
            canvas.drawArc(rectF, 0f, 360f, true, mPaintProgress !!)
            canvas.drawBitmap(mCircularBitmap !!, mProgressWidth.toFloat(), mProgressWidth.toFloat(), mPaintProgress)
        } else {
            recreateBitmap()
        }
    }

    /**
     * Method to draw the progress
     *
     * @param canvas
     */
    private fun drawProgress(canvas : Canvas) {
        mPaintProgress !!.style = Paint.Style.FILL_AND_STROKE
        mPaintProgress !!.color = mProgressColor
        canvas.drawArc(mProgressRectF !!, mProgressStartAngle.toFloat(), mProgressSwipeAngle.toFloat(), true, mPaintProgress !!)
        mProgressStartAngle += PROGRESS_START_ANGLE_INC
        if (mProgressSwipeAngle >= PROGRESS_MAX_ANGLE_DIFF) {
            mIsProgressMoveUp = false
        } else if (mProgressSwipeAngle <= PROGRESS_MIN_ANGLE_DIFF) {
            mIsProgressMoveUp = true
        }
        if (mIsProgressMoveUp) {
            mProgressSwipeAngle += PROGRESS_START_ANGLE_INC
        } else {
            mProgressSwipeAngle -= PROGRESS_SWIPE_ANGLE_DEC
        }
    }

    /**
     * Method to recreate the Bitmap
     */
    private fun recreateBitmap() {
        if (mProgressImageRef == - 1) {
            return
        }
        mCircularBitmap?.recycle()

        val bitmapTemp = BitmapFactory.decodeResource(resources, mProgressImageRef)
        val destDim = (width - (mProgressWidth shl 1)).toFloat()
        var scaleFactor = 1f
        val tempWidth = bitmapTemp.width.toFloat()
        val tempHeight = bitmapTemp.height.toFloat()
        if (tempWidth > destDim && tempHeight > destDim) {
            if (tempHeight / destDim > tempWidth / destDim) {
                scaleFactor = destDim / tempWidth
            } else {
                scaleFactor = destDim / tempHeight
            }
        } else if (tempWidth < destDim && tempHeight < destDim) {
            var tempMinDimension = tempWidth
            if (tempWidth > tempHeight) {
                tempMinDimension = tempHeight
            }
            scaleFactor = destDim / tempMinDimension
        } else if (tempHeight < destDim) {
            scaleFactor = destDim / tempHeight
        } else if (tempWidth < destDim) {
            scaleFactor = destDim / tempWidth
        }
        val sourceImageWidth = (tempWidth * scaleFactor).toInt()
        val sourceImageHeight = (tempHeight * scaleFactor).toInt()

        val bitmapImage = Bitmap.createScaledBitmap(bitmapTemp, sourceImageWidth, sourceImageHeight, true)
        if (bitmapImage != bitmapTemp) {
            bitmapTemp.recycle()
        }

        val bitmapFinal = Bitmap.createBitmap(destDim.toInt(), destDim.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmapFinal)
        canvas.drawColor(Color.argb(0, 0, 0, 0))
        val rectF = RectF(0f, 0f, destDim.toInt().toFloat(), destDim.toInt().toFloat())
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        canvas.drawArc(RectF(0f, 0f, destDim.toInt().toFloat(), destDim.toInt().toFloat()), 0f, 360f, true, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        val l = destDim.toInt() - sourceImageWidth shr 1
        val t = destDim.toInt() - sourceImageHeight shr 1
        canvas.drawBitmap(bitmapImage, l.toFloat(), t.toFloat(), paint)
        // RECYCLE BITMAP
        bitmapImage.recycle()
        mCircularBitmap = bitmapFinal
    }

    /**
     * Method to recreate the Progress
     */
    private fun recreateProgress() {
        if (width > 0 && height > 0) {
            recreateBitmap()
            mProgressRectF !!.set(0f, 0f, width.toFloat(), height.toFloat())
            invalidate()
        }
    }

    /**
     * Class to watch the Performance
     */
    private inner class Invalidator : AsyncTask<Void, Void, Void>() {
        private var mIsRunning : Boolean? = null

        /**
         * Method to check weather the watcher is watching or not
         *
         * @return
         */
        /**
         * Method to set the Watching state
         *
         * @param isWatching
         */
        var isRunning : Boolean
            @Synchronized get() = if (mIsRunning != null) mIsRunning !! else false
            @Synchronized set(isRunning) {
                mIsRunning = isRunning
            }

        /**
         * Constructor
         */
        init {
            isRunning = true
        }

        override fun doInBackground(vararg voids : Void) : Void? {
            isRunning = true
            while (isRunning) {
                publishProgress()
                try {
                    Thread.sleep(5)
                } catch (e : InterruptedException) {
                    e.printStackTrace()
                }

            }
            isRunning = false
            return null
        }

        override fun onProgressUpdate(vararg values : Void) {
            super.onProgressUpdate(*values)
            try {
                invalidate()
            } catch (e : Exception) {
                Log.e("MKR", "CircularImageView.Invalidator.onProgressUpdate : " + e.message)
            }

        }
    }
}
