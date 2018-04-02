package com.mkrworld.mobilpay.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
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
import com.mkrworld.androidlib.utils.MKRDialogUtil;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;
import com.mkrworld.mobilpay.dto.merchantsendforgotpasswordotp.DTOMerchantSendForgotPasswordOtpRequest;
import com.mkrworld.mobilpay.dto.merchantsendforgotpasswordotp.DTOMerchantSendForgotPasswordOtpResponse;
import com.mkrworld.mobilpay.provider.fragment.FragmentProvider;
import com.mkrworld.mobilpay.provider.fragment.FragmentTag;
import com.mkrworld.mobilpay.provider.network.MerchantNetworkTaskProvider;
import com.mkrworld.mobilpay.ui.custom.OnTextInputLayoutTextChangeListener;
import com.mkrworld.mobilpay.utils.PreferenceData;
import com.mkrworld.mobilpay.utils.Utils;

import java.util.Date;

/**
 * Created by mkr on 13/3/18.
 */

public class FragmentForgotPassword extends Fragment implements OnBaseFragmentListener, View.OnClickListener {
    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentForgotPassword";
    private TextInputLayout mTextInputLayoutMobileNumber;
    private TextInputLayout mTextInputLayoutEmail;
    private EditText mEditTextMobileNumber;
    private EditText mEditTextEmail;
    private MerchantNetworkTaskProvider mMerchantNetworkTaskProvider;
    private NetworkCallBack<DTOMerchantSendForgotPasswordOtpResponse> mMerchantSendForgotPasswordOtpResponseNetworkCallBack = new NetworkCallBack<DTOMerchantSendForgotPasswordOtpResponse>() {
        @Override
        public void onSuccess(DTOMerchantSendForgotPasswordOtpResponse dtoMerchantSendForgotPasswordOtpResponse) {
            Tracer.debug(TAG, "onSuccess : ");
            Utils.dismissLoadingDialog();
            if (getView() == null) {
                return;
            }
            if (dtoMerchantSendForgotPasswordOtpResponse == null || dtoMerchantSendForgotPasswordOtpResponse.getData() == null) {
                Tracer.showSnack(getView(), R.string.no_data_fetch_from_server);
                return;
            }
            Tracer.showSnack(getView(), dtoMerchantSendForgotPasswordOtpResponse.getMessage());
            if (getActivity() instanceof OnBaseActivityListener) {
                Fragment fragment = FragmentProvider.getFragment(FragmentTag.CHANGE_PASSWORD_BY_OTP);
                ((OnBaseActivityListener) getActivity()).onBaseActivityReplaceFragment(fragment, null, FragmentTag.CHANGE_PASSWORD_BY_OTP);
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
                startSendOtpProcess();
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
        mMerchantNetworkTaskProvider = new MerchantNetworkTaskProvider();
        mMerchantNetworkTaskProvider.attachProvider();
        getView().findViewById(R.id.fragment_forgot_password_textView_submit).setOnClickListener(this);

        mTextInputLayoutMobileNumber = (TextInputLayout) getView().findViewById(R.id.fragment_forgot_password_textInputLayout_mobile_number);
        mEditTextMobileNumber = (EditText) getView().findViewById(R.id.fragment_forgot_password_editText_mobile_number);
        mTextInputLayoutEmail = (TextInputLayout) getView().findViewById(R.id.fragment_forgot_password_textInputLayout_email);
        mEditTextEmail = (EditText) getView().findViewById(R.id.fragment_forgot_password_editText_email);

        // ADD TEXT WATCHER
        mEditTextMobileNumber.addTextChangedListener(new OnTextInputLayoutTextChangeListener(mTextInputLayoutMobileNumber));
        mEditTextEmail.addTextChangedListener(new OnTextInputLayoutTextChangeListener(mTextInputLayoutEmail));
    }

    /**
     * Method to initiate the Send OTP Process
     */
    private void startSendOtpProcess() {
        Tracer.debug(TAG, "startSendOtpProcess: ");
        Utils.hideKeyboard(getActivity(), getView());
        if (!isGenerateOtpDetailValid()) {
            return;
        }
        String mobileNumber = mEditTextMobileNumber.getText().toString();
        String email = mEditTextEmail.getText().toString();
        Tracer.debug(TAG, "startSendOtpProcess : ");
        Date date = new Date();
        String timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT);
        String token = Utils.createToken(getActivity(), getString(R.string.endpoint_change_password), date);
        String publicKey = getString(R.string.public_key);
        DTOMerchantSendForgotPasswordOtpRequest dtoMerchantSendForgotPasswordOtpRequest = new DTOMerchantSendForgotPasswordOtpRequest(token, timeStamp, publicKey, PreferenceData.getMerchantId(getActivity()));
        Utils.showLoadingDialog(getActivity());
        mMerchantNetworkTaskProvider.merchantSendForgotPasswordOtpTask(getActivity(), dtoMerchantSendForgotPasswordOtpRequest, mMerchantSendForgotPasswordOtpResponseNetworkCallBack);
    }

    /**
     * Method to check weather the OTP generation detail insert by merchant is valid or not
     *
     * @return
     */
    private boolean isGenerateOtpDetailValid() {
        Tracer.debug(TAG, "isGenerateOtpDetailValid: ");
        if (getView() == null) {
            return false;
        }

        String mobileNumber = mEditTextMobileNumber.getText().toString();
        String email = mEditTextEmail.getText().toString();

        // Validate Mobile Number
        if (Utils.isStringEmpty(mobileNumber)) {
            showTextInputError(mTextInputLayoutMobileNumber, getString(R.string.field_should_not_be_empty_caps));
            return false;
        }

        // Validate Email
        if (Utils.isStringEmpty(email)) {
            showTextInputError(mTextInputLayoutEmail, getString(R.string.field_should_not_be_empty_caps));
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
