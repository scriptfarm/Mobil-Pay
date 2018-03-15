package com.mkrworld.mobilpay.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;

import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;

/**
 * Created by mkr on 15/3/18.
 */

public class FragmentChangePasswordByOtp extends FragmentChangePassword {
    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentChangePasswordByOtp";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        ((TextInputLayout) view.findViewById(R.id.fragment_change_password_textInputLayout_old_password)).setHint(getString(R.string.code_received_via_sms));
        view.findViewById(R.id.fragment_change_password_textView_forgot_password).setVisibility(View.GONE);
    }
}
