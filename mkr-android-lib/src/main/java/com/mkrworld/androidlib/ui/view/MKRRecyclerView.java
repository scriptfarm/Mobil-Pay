package com.mkrworld.androidlib.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by A1ZFKXA3 on 8/21/2017.
 */

public class MKRRecyclerView extends RecyclerView {
    private OnScrollListener mOldOnScrollListener;

    public MKRRecyclerView(Context context) {
        super(context);
    }

    public MKRRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MKRRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void addOnScrollListener(OnScrollListener listener) {
        if (mOldOnScrollListener != null) {
            removeOnScrollListener(mOldOnScrollListener);
        }
        super.addOnScrollListener(mOldOnScrollListener = listener);
    }
}
