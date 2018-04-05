package com.mkrworld.androidlib.ui.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

/**
 * Created by A1ZFKXA3 on 8/21/2017.
 */

class MKRRecyclerView : RecyclerView {
    private var mOldOnScrollListener : RecyclerView.OnScrollListener? = null

    constructor(context : Context) : super(context) {}

    constructor(context : Context, attrs : AttributeSet?) : super(context, attrs) {}

    constructor(context : Context, attrs : AttributeSet?, defStyle : Int) : super(context, attrs, defStyle) {}

    override fun addOnScrollListener(listener : RecyclerView.OnScrollListener?) {
        removeOnScrollListener(mOldOnScrollListener)
        mOldOnScrollListener = listener
        super.addOnScrollListener(mOldOnScrollListener)
    }
}
