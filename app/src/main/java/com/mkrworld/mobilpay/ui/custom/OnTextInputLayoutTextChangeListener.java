package com.mkrworld.mobilpay.ui.custom;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;

/**
 * Created by mkr on 15/3/18.
 */

public class OnTextInputLayoutTextChangeListener implements TextWatcher {
    private static final String TAG = BuildConfig.BASE_TAG + ".OnTextInputLayoutTextChangeListener";
    private TextInputLayout mTextInputLayout;

    /**
     * Constructor
     *
     * @param textInputLayout Input Layout of the EditText
     */
    public OnTextInputLayoutTextChangeListener(TextInputLayout textInputLayout) {
        Tracer.debug(TAG, "OnTextInputLayoutTextChangeListener: " + textInputLayout);
        mTextInputLayout = textInputLayout;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mTextInputLayout.setErrorEnabled(false);
        mTextInputLayout.setError("");
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
