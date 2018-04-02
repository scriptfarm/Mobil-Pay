package com.mkrworld.mobilpay.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mkrworld.androidlib.callback.OnBaseActivityListener;
import com.mkrworld.androidlib.callback.OnBaseFragmentListener;
import com.mkrworld.androidlib.network.NetworkCallBack;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;
import com.mkrworld.mobilpay.dto.merchantforgotpassword.DTOMerchantForgotPasswordRequest;
import com.mkrworld.mobilpay.dto.merchantforgotpassword.DTOMerchantForgotPasswordResponse;
import com.mkrworld.mobilpay.provider.fragment.FragmentProvider;
import com.mkrworld.mobilpay.provider.fragment.FragmentTag;
import com.mkrworld.mobilpay.provider.network.MerchantNetworkTaskProvider;
import com.mkrworld.mobilpay.ui.custom.OnTextInputLayoutTextChangeListener;
import com.mkrworld.mobilpay.utils.PreferenceData;
import com.mkrworld.mobilpay.utils.Utils;

import java.util.Date;

/**
 * Created by mkr on 15/3/18.
 */

public class FragmentChangePasswordByOtp extends Fragment implements OnBaseFragmentListener, View.OnClickListener {
    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentChangePasswordByOtp";
    private TextInputLayout mTextInputLayoutOtp;
    private TextInputLayout mTextInputLayoutNewPassword;
    private TextInputLayout mTextInputLayoutConfirmPassword;
    private EditText mEditTextOtp;
    private EditText mEditTextNewPassword;
    private EditText mEditTextConfirmPassword;
    private MerchantNetworkTaskProvider mMerchantNetworkTaskProvider;
    private NetworkCallBack<DTOMerchantForgotPasswordResponse> mMerchantForgotPasswordResponseNetworkCallBack = new NetworkCallBack<DTOMerchantForgotPasswordResponse>() {
        @Override
        public void onSuccess(DTOMerchantForgotPasswordResponse dtoMerchantForgotPasswordResponse) {
            Tracer.debug(TAG, "onSuccess : ");
            Utils.dismissLoadingDialog();
            if (getView() == null) {
                return;
            }
            if (dtoMerchantForgotPasswordResponse == null || dtoMerchantForgotPasswordResponse.getData() == null) {
                Tracer.showSnack(getView(), R.string.no_data_fetch_from_server);
                return;
            }
            PreferenceData.setMerchantLoginPassword(getActivity(), mEditTextConfirmPassword.getText().toString().trim());
            Tracer.showSnack(getView(), dtoMerchantForgotPasswordResponse.getMessage());
            if (getActivity() instanceof OnBaseActivityListener) {
                Fragment fragment = FragmentProvider.getFragment(FragmentTag.MERCHANT_HOME);
                ((OnBaseActivityListener) getActivity()).onBaseActivityReplaceFragment(fragment, null, FragmentTag.MERCHANT_HOME);
            }
        }

        @Override
        public void onError(String errorMessage, int errorCode) {
            Tracer.debug(TAG, "onError : ");
            Utils.dismissLoadingDialog();
            if (getView() == null) {
                return;
            }
            Tracer.showSnack(getView(), errorMessage);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.fragment_change_password_otp, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        setTitle();
        init();
    }

    @Override
    public void onDestroyView() {
        if (mMerchantNetworkTaskProvider != null) {
            mMerchantNetworkTaskProvider.detachProvider();
        }
        super.onDestroyView();
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
    }

    @Override
    public void onRefresh() {
        Tracer.debug(TAG, "onRefresh: ");
    }

    @Override
    public void onClick(View view) {
        Tracer.debug(TAG, "onClick: ");
        switch (view.getId()) {
            case R.id.fragment_change_password_otp_textView_submit:
                startSetNewPasswordProcess();
                break;
        }
    }

    /**
     * Method to set the Activity Title
     */
    private void setTitle() {
        Tracer.debug(TAG, "setTitle: ");
        if (getActivity() instanceof OnBaseActivityListener) {
            ((OnBaseActivityListener) getActivity()).onBaseActivitySetScreenTitle(getString(R.string.screen_title_change_password));
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
        mMerchantNetworkTaskProvider = new MerchantNetworkTaskProvider();
        mMerchantNetworkTaskProvider.attachProvider();
        getView().findViewById(R.id.fragment_change_password_otp_textView_submit).setOnClickListener(this);

        mTextInputLayoutOtp = (TextInputLayout) getView().findViewById(R.id.fragment_change_password_otp_textInputLayout_otp);
        mEditTextOtp = (EditText) getView().findViewById(R.id.fragment_change_password_otp_editText_otp);
        mTextInputLayoutNewPassword = (TextInputLayout) getView().findViewById(R.id.fragment_change_password_otp_textInputLayout_new_password);
        mEditTextNewPassword = (EditText) getView().findViewById(R.id.fragment_change_password_otp_editText_new_password);
        mTextInputLayoutConfirmPassword = (TextInputLayout) getView().findViewById(R.id.fragment_change_password_otp_textInputLayout_confirm_password);
        mEditTextConfirmPassword = (EditText) getView().findViewById(R.id.fragment_change_password_otp_editText_confirm_password);

        // ADD TEXT CHANGE LISTENER
        mEditTextOtp.addTextChangedListener(new OnTextInputLayoutTextChangeListener(mTextInputLayoutOtp));
        mEditTextNewPassword.addTextChangedListener(new OnTextInputLayoutTextChangeListener(mTextInputLayoutNewPassword));
        mEditTextConfirmPassword.addTextChangedListener(new OnTextInputLayoutTextChangeListener(mTextInputLayoutConfirmPassword));
    }

    /**
     * Method to initiate the Set New Password Process
     */
    private void startSetNewPasswordProcess() {
        Tracer.debug(TAG, "startSetNewPasswordProcess: ");
        Utils.hideKeyboard(getActivity(), getView());
        if (!isUserDetailValid()) {
            return;
        }
        String otp = mEditTextOtp.getText().toString().trim();
        String confirmPassword = mEditTextConfirmPassword.getText().toString().trim();
        Date date = new Date();
        String timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT);
        String token = Utils.createToken(getActivity(), getString(R.string.endpoint_forgot_password), date);
        String publicKey = getString(R.string.public_key);
        DTOMerchantForgotPasswordRequest dtoMerchantForgotPasswordRequest = new DTOMerchantForgotPasswordRequest(token, timeStamp, publicKey, PreferenceData.getMerchantNupayId(getActivity()), confirmPassword, otp, "123");
        Utils.showLoadingDialog(getActivity());
        mMerchantNetworkTaskProvider.merchantForgotPasswordTask(getActivity(), dtoMerchantForgotPasswordRequest, mMerchantForgotPasswordResponseNetworkCallBack);
    }

    /**
     * Method to check weather the user detail insert by merchant is valid or not
     *
     * @return
     */
    private boolean isUserDetailValid() {
        Tracer.debug(TAG, "isUserDetailValid: ");
        if (getView() == null) {
            return false;
        }

        String otp = mEditTextOtp.getText().toString();
        String newPassword = mEditTextNewPassword.getText().toString();
        String confirmPassword = mEditTextConfirmPassword.getText().toString();

        // Validate old password
        if (Utils.isStringEmpty(otp)) {
            showTextInputError(mTextInputLayoutOtp, getString(R.string.field_should_not_be_empty_caps));
            return false;
        }

        // Validate New password
        if (Utils.isStringEmpty(newPassword)) {
            showTextInputError(mTextInputLayoutNewPassword, getString(R.string.field_should_not_be_empty_caps));
            return false;
        }

        // Validate confirm password
        if (Utils.isStringEmpty(confirmPassword)) {
            showTextInputError(mTextInputLayoutConfirmPassword, getString(R.string.field_should_not_be_empty_caps));
            return false;
        }

        // Validate confirm password
        if (!confirmPassword.trim().equals(newPassword.trim())) {
            showTextInputError(mTextInputLayoutConfirmPassword, getString(R.string.password_does_not_match_with_new_password));
            return false;
        }

        return true;
    }

    /**
     * Method to show the error in textInputLayout
     *
     * @param textInputLayout
     * @param errorMessage
     */
    private void showTextInputError(TextInputLayout textInputLayout, String errorMessage) {
        Tracer.debug(TAG, "showTextInputError: " + textInputLayout + "    " + errorMessage);
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(errorMessage);
    }
}
