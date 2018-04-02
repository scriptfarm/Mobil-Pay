package com.mkrworld.androidlib.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mkrworld.androidlib.R;
import com.mkrworld.androidlib.network.NetworkRequest;

/**
 * Created by mkr on 30/3/18.
 */

public class CircularImageView extends View {
    private static final int PROGRESS_START_ANGLE_INC = 8;
    private static final int PROGRESS_SWIPE_ANGLE_DEC = 5;
    private static final int PROGRESS_MAX_ANGLE_DIFF = 270;
    private static final int PROGRESS_MIN_ANGLE_DIFF = 30;
    private Bitmap mCircularBitmap;
    private int mProgressColor = Color.BLUE;
    private int mProgressWidth;
    private int mProgressImageRef;
    private int mProgressStartAngle;
    private int mProgressSwipeAngle;
    private boolean mIsProgressMoveUp;
    private RectF mProgressRectF;
    private Paint mPaintProgress;
    private Invalidator mInvalidator;

    public CircularImageView(Context context) {
        super(context);
        init(null);
    }

    public CircularImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CircularImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircularImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getSize(widthMeasureSpec) > MeasureSpec.getSize(heightMeasureSpec)) {
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        } else {
            super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mInvalidator == null || !mInvalidator.isRunning()) {
            mInvalidator = new Invalidator();
            mInvalidator.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mInvalidator != null) {
            mInvalidator.setRunning(false);
        }
        mInvalidator = null;
    }

    /**
     * Method to init View
     *
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CircularImageView, 0, 0);
            mProgressColor = a.getColor(R.styleable.CircularImageView_progress_color, Color.BLUE);
            mProgressWidth = a.getDimensionPixelOffset(R.styleable.CircularImageView_progress_width, (int) ((float) getResources().getDisplayMetrics().widthPixels * 0.05F));
            mProgressImageRef = a.getResourceId(R.styleable.CircularImageView_progress_image, -1);
            a.recycle();
        }
        mPaintProgress = new Paint();
        mPaintProgress.setAntiAlias(true);
        mPaintProgress.setFilterBitmap(true);
        mPaintProgress.setColor(mProgressColor);
        mPaintProgress.setStyle(Paint.Style.FILL_AND_STROKE);
        mProgressRectF = new RectF();
        mProgressStartAngle = 0;
        mProgressSwipeAngle = PROGRESS_MIN_ANGLE_DIFF;
        mIsProgressMoveUp = true;
    }

    /**
     * Method to change the Image
     *
     * @param imgResId
     */
    public void setImageRes(int imgResId) {
        mProgressImageRef = imgResId;
        recreateProgress();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        recreateProgress();
    }

    /**
     * Method to set the Progress Color
     *
     * @param progressColor
     */
    public void setProgressColor(int progressColor) {
        mProgressColor = progressColor;
        mPaintProgress.setColor(mProgressColor);
        invalidate();
    }

    /**
     * Method to set the Progress Width
     *
     * @param progressWidth
     */
    public void setProgressWidth(int progressWidth) {
        mProgressWidth = progressWidth;
        recreateProgress();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawProgress(canvas);
        drawBitmap(canvas);
    }

    /**
     * Method to draw the bitmap
     *
     * @param canvas
     */
    private void drawBitmap(Canvas canvas) {
        if (mCircularBitmap != null && !mCircularBitmap.isRecycled()) {
            int rectWidth = getWidth() - (mProgressWidth << 1);
            int l = (getWidth() - rectWidth) >> 1;
            int t = (getWidth() - rectWidth) >> 1;
            RectF rectF = new RectF(l, t, l + rectWidth, t + rectWidth);
            mPaintProgress.setStyle(Paint.Style.FILL);
            mPaintProgress.setColor(Color.WHITE);
            canvas.drawArc(rectF, 0, 360, true, mPaintProgress);
            canvas.drawBitmap(mCircularBitmap, mProgressWidth, mProgressWidth, mPaintProgress);
        } else {
            recreateBitmap();
        }
    }

    /**
     * Method to draw the progress
     *
     * @param canvas
     */
    private void drawProgress(Canvas canvas) {
        mPaintProgress.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintProgress.setColor(mProgressColor);
        canvas.drawArc(mProgressRectF, mProgressStartAngle, mProgressSwipeAngle, true, mPaintProgress);
        mProgressStartAngle += PROGRESS_START_ANGLE_INC;
        if (mProgressSwipeAngle >= PROGRESS_MAX_ANGLE_DIFF) {
            mIsProgressMoveUp = false;
        } else if (mProgressSwipeAngle <= PROGRESS_MIN_ANGLE_DIFF) {
            mIsProgressMoveUp = true;
        }
        if (mIsProgressMoveUp) {
            mProgressSwipeAngle += PROGRESS_START_ANGLE_INC;
        } else {
            mProgressSwipeAngle -= PROGRESS_SWIPE_ANGLE_DEC;
        }
    }

    /**
     * Method to recreate the Bitmap
     */
    private void recreateBitmap() {
        if (mCircularBitmap != null) {
            mCircularBitmap.recycle();
        }
        Bitmap bitmapTemp = BitmapFactory.decodeResource(getResources(), mProgressImageRef);
        float destDim = (float) (getWidth() - (mProgressWidth << 1));
        float scaleFactor = 1;
        float tempWidth = bitmapTemp.getWidth();
        float tempHeight = bitmapTemp.getHeight();
        if (tempWidth > destDim && tempHeight > destDim) {
            if ((tempHeight / destDim) > (tempWidth / destDim)) {
                scaleFactor = destDim / tempWidth;
            } else {
                scaleFactor = destDim / tempHeight;
            }
        } else if (tempWidth < destDim && tempHeight < destDim) {
            float tempMinDimension = tempWidth;
            if (tempWidth > tempHeight) {
                tempMinDimension = tempHeight;
            }
            scaleFactor = destDim / tempMinDimension;
        } else if (tempHeight < destDim) {
            scaleFactor = destDim / tempHeight;
        } else if (tempWidth < destDim) {
            scaleFactor = destDim / tempWidth;
        }
        int sourceImageWidth = (int) (tempWidth * scaleFactor);
        int sourceImageHeight = (int) (tempHeight * scaleFactor);

        Bitmap bitmapImage = Bitmap.createScaledBitmap(bitmapTemp, sourceImageWidth, sourceImageHeight, true);
        if (!bitmapImage.equals(bitmapTemp)) {
            bitmapTemp.recycle();
        }

        Bitmap bitmapFinal = Bitmap.createBitmap((int) destDim, (int) destDim, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapFinal);
        canvas.drawColor(Color.argb(0, 0, 0, 0));
        RectF rectF = new RectF(0, 0, (int) destDim, (int) destDim);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        canvas.drawArc(new RectF(0, 0, (int) destDim, (int) destDim), 0, 360, true, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        int l = ((int) destDim - sourceImageWidth) >> 1;
        int t = ((int) destDim - sourceImageHeight) >> 1;
        canvas.drawBitmap(bitmapImage, l, t, paint);
        // RECYCLE BITMAP
        bitmapImage.recycle();
        mCircularBitmap = bitmapFinal;
    }

    /**
     * Method to recreate the Progress
     */
    private void recreateProgress() {
        if (getWidth() > 0 && getHeight() > 0) {
            recreateBitmap();
            mProgressRectF.set(0, 0, getWidth(), getHeight());
            invalidate();
        }
    }

    /**
     * Class to watch the Performance
     */
    private class Invalidator extends AsyncTask<Void, NetworkRequest, Void> {
        private Boolean mIsRunning;

        /**
         * Constructor
         */
        public Invalidator() {
            setRunning(true);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            setRunning(true);
            while (isRunning()) {
                publishProgress();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            setRunning(false);
            return null;
        }

        @Override
        protected void onProgressUpdate(NetworkRequest... values) {
            super.onProgressUpdate(values);
            try {
                invalidate();
            } catch (Exception e) {
                Log.e("MKR", "CircularImageView.Invalidator.onProgressUpdate : " + e.getMessage());
            }
        }

        /**
         * Method to set the Watching state
         *
         * @param isWatching
         */
        private synchronized void setRunning(boolean isWatching) {
            mIsRunning = isWatching;
        }

        /**
         * Method to check weather the watcher is watching or not
         *
         * @return
         */
        public synchronized boolean isRunning() {
            return mIsRunning != null ? mIsRunning : false;
        }
    }
}
