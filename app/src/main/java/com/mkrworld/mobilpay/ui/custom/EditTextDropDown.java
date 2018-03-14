package com.mkrworld.mobilpay.ui.custom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatAutoCompleteTextView;

import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;

/**
 * Created by mkr on 14/3/18.
 */

public class EditTextDropDown extends AppCompatAutoCompleteTextView {
    private static final String TAG = BuildConfig.BASE_TAG + ".EditTextDropDown";
    private Paint mPaint;
    private Path mPath;
    private int mArrowSize;

    public EditTextDropDown(Context context) {
        super(context);
        init();
    }

    public EditTextDropDown(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextDropDown(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mArrowSize = (int) (((float) getLineHeight()) * 0.4F);
        if (mArrowSize % 2 != 0) {
            mArrowSize++;
        }
        int paddingRight = getPaddingRight();
        setPadding(getPaddingLeft(), getPaddingTop(), paddingRight + (2 * mArrowSize), getPaddingBottom());
    }

    /**
     * Method to set the color of the Dropdown Icon
     */
    public void setDropdownIconColor(int color) {
        Tracer.debug(TAG, "setDropdownIconColor: " + color);
        mPaint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = 0;
        int measureText = (int) getPaint().measureText(getText().toString());
        int writableWidth = getWidth() - getPaddingRight() - getPaddingLeft();
        if (measureText > writableWidth) {
            width = getPaddingLeft() + measureText;
        } else {
            width = getPaddingLeft() + writableWidth;
        }
        mPath.reset();
        int x1 = width + (mArrowSize >> 1);
        int x2 = x1 + mArrowSize;
        int x3 = x1 + ((x2 - x1) >> 1);
        int y1 = (getHeight() - mArrowSize) >> 1;
        int y2 = y1 + mArrowSize;
        mPath.moveTo(x1, y1);
        mPath.lineTo(x2, y1);
        mPath.lineTo(x3, y2);
        mPath.lineTo(x1, y1);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * Method to init the EditText
     */
    private void init() {
        Tracer.debug(TAG, "init: ");
        setMaxLines(1);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getCurrentHintTextColor());
        mPath = new Path();
    }


}
