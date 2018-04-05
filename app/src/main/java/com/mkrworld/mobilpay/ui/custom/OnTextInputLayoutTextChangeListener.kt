package com.mkrworld.mobilpay.ui.custom

import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 15/3/18.
 */

class OnTextInputLayoutTextChangeListener : TextWatcher {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".OnTextInputLayoutTextChangeListener"
    }

    private var mTextInputLayout : TextInputLayout? = null

    constructor() {}

    /**
     * Constructor
     *
     * @param textInputLayout Input Layout of the EditText
     */
    constructor(textInputLayout : TextInputLayout) {
        Tracer.debug(TAG, "OnTextInputLayoutTextChangeListener: $textInputLayout")
        mTextInputLayout = textInputLayout
    }

    override fun beforeTextChanged(charSequence : CharSequence, i : Int, i1 : Int, i2 : Int) {

    }

    override fun onTextChanged(charSequence : CharSequence, i : Int, i1 : Int, i2 : Int) {
        mTextInputLayout?.isErrorEnabled = false
        mTextInputLayout?.error = ""
    }

    override fun afterTextChanged(editable : Editable) {

    }
}
