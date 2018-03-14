package com.mkrworld.mobilpay.ui.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mkrworld.mobilpay.BuildConfig;

/**
 * Created by mkr on 14/3/18.
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = BuildConfig.BASE_TAG + ".ItemOffsetDecoration";
    private Paint mPaint;
    private int mSpanCount;
    private int mSpacing;
    private boolean mIsIncludeEdge;

    /**
     * Constructor
     *
     * @param spanCount
     * @param spacing
     * @param dividerColor
     * @param isIncludeEdge
     */
    public GridSpacingItemDecoration(int spanCount, int spacing, int dividerColor, boolean isIncludeEdge) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mPaint.setColor(dividerColor);
        mPaint.setStrokeWidth(spacing);
        mSpanCount = spanCount;
        mSpacing = spacing;
        mIsIncludeEdge = isIncludeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % mSpanCount; // item column
        if (mIsIncludeEdge) {
            outRect.left = mSpacing - column * mSpacing / mSpanCount; // mSpacing - column * ((1f / mSpanCount) * mSpacing)
            outRect.right = (column + 1) * mSpacing / mSpanCount; // (column + 1) * ((1f / mSpanCount) * mSpacing)

            if (position < mSpanCount) { // top edge
                outRect.top = mSpacing;
            }
            outRect.bottom = mSpacing; // item bottom
        } else {
            outRect.left = column * mSpacing / mSpanCount; // column * ((1f / mSpanCount) * mSpacing)
            outRect.right = mSpacing - (column + 1) * mSpacing / mSpanCount; // mSpacing - (column + 1) * ((1f /    mSpanCount) * mSpacing)
            if (position >= mSpanCount) {
                outRect.top = mSpacing; // item top
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int hX1 = parent.getPaddingLeft();
        int hX2 = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int y = child.getBottom() + params.bottomMargin + (mSpacing >> 1);
            c.drawLine(hX1, y, hX2, y, mPaint);
            if (!((i + 1) % mSpanCount == 0)) {
                int vX = child.getRight() - params.rightMargin + (mSpacing >> 1);
                int vY1 = child.getTop() - params.topMargin;
                int vY2 = child.getBottom() - params.bottomMargin;
                c.drawLine(vX, vY1, vX, vY2, mPaint);
            }
        }
    }
}
