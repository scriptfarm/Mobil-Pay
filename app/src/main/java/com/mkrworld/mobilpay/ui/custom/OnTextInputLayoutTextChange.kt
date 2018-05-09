package com.mkrworld.mobilpay.ui.custom

import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 15/3/18.
 */

class OnTextInputLayoutTextChange : TextWatcher {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".OnTextInputLayoutTextChange"
    }

    private var mTextInputLayout : TextInputLayout? = null
    private var mOnTextInputLayoutTextChangeListener : OnTextInputLayoutTextChangeListener? = null

    constructor(textInputLayout : TextInputLayout, onTextInputLayoutTextChangeListener : OnTextInputLayoutTextChangeListener?) {
        Tracer.debug(TAG, "OnTextInputLayoutTextChange: $textInputLayout     $onTextInputLayoutTextChangeListener")
        mTextInputLayout = textInputLayout
        mOnTextInputLayoutTextChangeListener = onTextInputLayoutTextChangeListener
    }

    /**
     * Constructor
     *
     * @param textInputLayout Input Layout of the EditText
     */
    constructor(textInputLayout : TextInputLayout) : this(textInputLayout, null) {
    }

    override fun beforeTextChanged(charSequence : CharSequence, i : Int, i1 : Int, i2 : Int) {

    }

    override fun onTextChanged(charSequence : CharSequence, i : Int, i1 : Int, i2 : Int) {
        mTextInputLayout?.isErrorEnabled = false
        mTextInputLayout?.error = ""
        mOnTextInputLayoutTextChangeListener?.onTextChanged(charSequence, i, i1, i2)
    }

    override fun afterTextChanged(editable : Editable) {

    }

    interface OnTextInputLayoutTextChangeListener {
        fun onTextChanged(charSequence : CharSequence, i : Int, i1 : Int, i2 : Int)
    }
}
