package com.mkrworld.mobilpay.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mkrworld.androidlib.callback.OnBaseActivityListener;
import com.mkrworld.androidlib.callback.OnBaseFragmentListener;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;
import com.mkrworld.mobilpay.provider.fragment.FragmentProvider;
import com.mkrworld.mobilpay.provider.fragment.FragmentTag;

/**
 * Created by mkr on 13/3/18.
 */

public class FragmentForgotPassword extends Fragment implements OnBaseFragmentListener, View.OnClickListener {
    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentForgotPassword";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        setTitle();
        init();
    }

    @Override
    public boolean onBackPressed() {
        Tracer.debug(TAG, "onBackPressed: ");
        return false;
    }

    @Override
    public void onPopFromBackStack() {
        Tracer.debug(TAG, "onPopFromBackStack: ");
        setTitle();
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onRefresh() {
        Tracer.debug(TAG, "onRefresh: ");
    }

    @Override
    public void onClick(View view) {
        Tracer.debug(TAG, "onClick: ");
        switch (view.getId()) {
            case R.id.fragment_forgot_password_textView_submit:
                if (getActivity() instanceof OnBaseActivityListener) {
                    Fragment fragment = FragmentProvider.getFragment(FragmentTag.CHANGE_PASSWORD_BY_OTP);
                    ((OnBaseActivityListener) getActivity()).onBaseActivityAddFragment(fragment, null, true, FragmentTag.CHANGE_PASSWORD_BY_OTP);
                }
                break;
        }
    }

    /**
     * Method to set the Activity Title
     */
    private void setTitle() {
        Tracer.debug(TAG, "setTitle: ");
        if (getActivity() instanceof OnBaseActivityListener) {
            ((OnBaseActivityListener) getActivity()).onBaseActivitySetScreenTitle(getString(R.string.screen_title_forgot_password));
        }
    }

    /**
     * Method to initialize the Fragment
     */
    private void init() {
        Tracer.debug(TAG, "init: ");
        if (getView() == null) {
            return;
        }
        getView().findViewById(R.id.fragment_forgot_password_textView_submit).setOnClickListener(this);
    }
}