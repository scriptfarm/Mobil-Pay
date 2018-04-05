package com.mkrworld.androidlib.ui.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet

/**
 * Created by A1ZFKXA3 on 8/21/2017.
 */

class MKRViewPager : ViewPager {
    private var mOnPageChangeListener : ViewPager.OnPageChangeListener? = null

    constructor(context : Context) : super(context) {}

    constructor(context : Context, attrs : AttributeSet) : super(context, attrs) {}

    override fun addOnPageChangeListener(listener : ViewPager.OnPageChangeListener?) {
        removeOnPageChangeListener(mOnPageChangeListener)
        mOnPageChangeListener = listener
        super.addOnPageChangeListener(mOnPageChangeListener)
    }
}
