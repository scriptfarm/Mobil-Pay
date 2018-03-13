package com.mkrworld.androidlib.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by A1ZFKXA3 on 8/21/2017.
 */

public class MKRViewPager extends ViewPager {
    private OnPageChangeListener mOnPageChangeListener;

    public MKRViewPager(Context context) {
        super(context);
    }

    public MKRViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        if (mOnPageChangeListener != null) {
            removeOnPageChangeListener(mOnPageChangeListener);
        }
        super.addOnPageChangeListener(mOnPageChangeListener = listener);
    }
}
