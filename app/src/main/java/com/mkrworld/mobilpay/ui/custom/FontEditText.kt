package com.mkrworld.mobilpay.ui.custom

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.widget.EditText
import android.widget.TextView

/**
 * Created by mkr on 9/5/18.
 */

class FontEditText : EditText {
    constructor(context : Context) : super(context) {
        init()
    }

    constructor(context : Context, attrs : AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context : Context, attrs : AttributeSet?, defStyleAttr : Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) constructor(context : Context, attrs : AttributeSet?, defStyleAttr : Int, defStyleRes : Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    fun init() {
        typeface = Typeface.createFromAsset(context.assets, "fonts/montserrat_ultralight.ttf");
    }
}
