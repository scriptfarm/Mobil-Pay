package com.mkrworld.androidlib.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.mkrworld.androidlib.BuildConfig;
import com.mkrworld.androidlib.utils.Tracer;


/**
 * Progress Dialog View
 * <p/>
 * Created by Sunny on 3/8/2016.
 */
public class ProgressView extends View {
    private static final String TAG = BuildConfig.BASE_TAG + ".ProgressView";
    private float mRadius;
    private Paint mPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Handler mHandler = new Handler();
    private float mIndex = 0;
    private float mMargin = 0;
    private int mDotCount = 4;
    private int mStep = 1;
    private float mTestSize;
    private String mTextMessage = "Processing";
    private boolean mIsRunning;

    public ProgressView(Context context) {
        super(context);
        init(context);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    private void init(Context context) {
        float width = (float) context.getResources().getDisplayMetrics().widthPixels;
        mMargin = width * 0.005F;
        mRadius = width * 0.02F;
        mTestSize = width * 0.05F;
        // dot fill color
        mPaintFill.setStyle(Paint.Style.FILL);
        mPaintFill.setColor(Color.WHITE);
        // dot background color
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0x44000000);
        // Text paint
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setColor(Color.WHITE);
        mPaintText.setTextSize(mTestSize);
    }

    public void setDotsCount(int count) {
        mDotCount = count;
    }

    public void setTextMessage(String textMessage) {
        mTextMessage = textMessage;
    }

    public void start() {
        mIsRunning = true;
        mIndex = -1;
        mHandler.removeCallbacks(mRunnable);
        mHandler.post(mRunnable);
    }

    public void stop() {
        mIsRunning = false;
        mHandler.removeCallbacks(mRunnable);
    }

    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            mIndex += mStep;
            if (mIndex < 0) {
                mIndex = 1;
                mStep = 1;
            } else if (mIndex > (mDotCount - 1)) {
                if ((mDotCount - 2) >= 0) {
                    mIndex = mDotCount - 2;
                    mStep = -1;
                } else {
                    mIndex = 0;
                    mStep = 1;
                }
            }
            try {
                invalidate();
                if (mIsRunning) {
                    mHandler.postDelayed(mRunnable, 200);
                }
            } catch (Exception e) {
                Tracer.error(TAG, "run: " + e.getMessage());
            }
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) ((mRadius + mTestSize) * 2F), MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaintText.setTextSize(mTestSize);
        float textSize = mPaintText.measureText(mTextMessage);
        float textDx = ((float) getWidth() * 0.5F) - (textSize * 0.5F);
        if (textSize > getWidth()) {
            textDx = 0;
        }
        canvas.drawText(mTextMessage, textDx, mTestSize, mPaintText);
        float requiredWidth = ((float) (mDotCount - 1) * mRadius * 2.F) + ((float) (mDotCount - 1) * mMargin);
        float dX = ((float) getWidth() * 0.5F) - (requiredWidth * 0.5F);
        float dY = getHeight() - (mRadius * 2.F);
        for (int i = 0; i < mDotCount; i++) {
            if (i == mIndex) {
                canvas.drawCircle(dX, dY, mRadius, mPaintFill);
            } else {
                canvas.drawCircle(dX, dY, mRadius, mPaint);
            }
            dX = dX + ((2F * mRadius) + mMargin);
        }
    }
}


