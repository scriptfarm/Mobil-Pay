package com.mkrworld.mobilpay.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
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
import com.mkrworld.androidlib.utils.MKRDialogUtil;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;
import com.mkrworld.mobilpay.dto.merchantlogin.DTOMerchantLoginRequest;
import com.mkrworld.mobilpay.dto.merchantlogin.DTOMerchantLoginResponse;
import com.mkrworld.mobilpay.fingerprintauth.FingerPrintAuthHelper;
import com.mkrworld.mobilpay.fingerprintauth.OnFingerPrintAuthCallback;
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

public class FragmentMerchantLogin extends Fragment implements OnBaseFragmentListener, View.OnClickListener, OnFingerPrintAuthCallback {
    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentMerchantLogin";
    private TextInputLayout mTextInputLayoutMerchantIdMobileNumber;
    private TextInputLayout mTextInputLayoutPassword;
    private EditText mEditTextMerchantIdMobileNumber;
    private EditText mEditTextPassword;
    private boolean mIsFingerPrintDeviceWorkingFine;
    private FingerPrintAuthHelper mFingerPrintAuthHelper;
    private MerchantNetworkTaskProvider mMerchantNetworkTaskProvider;
    private NetworkCallBack<DTOMerchantLoginResponse> mMerchantLoginResponseNetworkCallBack = new NetworkCallBack<DTOMerchantLoginResponse>() {
        @Override
        public void onSuccess(DTOMerchantLoginResponse dtoMerchantLoginResponse) {
            Tracer.debug(TAG, "onSuccess : " + dtoMerchantLoginResponse);
            Utils.dismissLoadingDialog();
            if (dtoMerchantLoginResponse == null && dtoMerchantLoginResponse.getData() == null) {
                Tracer.showSnack(getView(), R.string.no_data_fetch_from_server);
                return;
            }
            Tracer.showSnack(getView(), dtoMerchantLoginResponse.getMessage());
            PreferenceData.setMerchantNupayId(getActivity(), dtoMerchantLoginResponse.getData().getNupayId());
            PreferenceData.setMerchantId(getActivity(), dtoMerchantLoginResponse.getData().getUserId());
            String userId = mEditTextMerchantIdMobileNumber.getText().toString();
            String password = mEditTextPassword.getText().toString();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!PreferenceData.isHaveFingerPrintConsent(getActivity()) && mIsFingerPrintDeviceWorkingFine) {
                    showEnableFingerPrintDialog(userId, password);
                    return;
                } else if (PreferenceData.isHaveFingerPrintConsent(getActivity()) && (!PreferenceData.getMerchantLoginId(getActivity()).equalsIgnoreCase(userId.trim()) || !PreferenceData.getMerchantLoginPassword(getActivity()).equalsIgnoreCase(password.trim()))) {
                    showUpdateFingerLoginDetailDialog(userId, password);
                    return;
                }
            }
            goToSuccessScreen();
        }

        @Override
        public void onError(String errorMessage, int errorCode) {
            Tracer.debug(TAG, "onError : " + errorMessage);
            Utils.dismissLoadingDialog();
            Tracer.showSnack(getView(), errorMessage);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.fragment_merchant_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        mMerchantNetworkTaskProvider = new MerchantNetworkTaskProvider();
        mMerchantNetworkTaskProvider.attachProvider();
        setTitle();
        init();
    }

    @Override
    public void onDestroyView() {
        mFingerPrintAuthHelper.stopAuth();
        mMerchantNetworkTaskProvider.detachProvider();
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
            case R.id.fragment_merchant_login_textView_sign_in:
                startSignInProcess();
                break;
            case R.id.fragment_merchant_login_textView_forgot_password:
                if (getActivity() instanceof OnBaseActivityListener) {
                    Fragment fragment = FragmentProvider.getFragment(FragmentTag.FORGOT_PASSWORD);
                    ((OnBaseActivityListener) getActivity()).onBaseActivityAddFragment(fragment, null, true, FragmentTag.FORGOT_PASSWORD);
                }
                break;
        }
    }

    @Override
    public void onFingerPrintAuthNoFingerPrintHardwareFound() {
        Tracer.debug(TAG, "onFingerPrintAuthNoFingerPrintHardwareFound : ");
        mIsFingerPrintDeviceWorkingFine = false;
        if (getView() == null) {
            return;
        }
        Tracer.showSnack(getView(), R.string.no_finger_print_device_not_detected);
    }

    @Override
    public void onFingerPrintAuthNoFingerPrintRegistered() {
        Tracer.debug(TAG, "onFingerPrintAuthNoFingerPrintRegistered : ");
        mIsFingerPrintDeviceWorkingFine = false;
        if (getView() == null) {
            return;
        }
        Tracer.showSnack(getView(), R.string.no_finger_print_register);
    }

    @Override
    public void onFingerPrintAuthBelowMarshmallow() {
        Tracer.debug(TAG, "onFingerPrintAuthBelowMarshmallow : ");
    }

    @Override
    public void onFingerPrintAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        Tracer.debug(TAG, "onFingerPrintAuthSuccess : ");
        String merchantLoginPassword = PreferenceData.getMerchantLoginPassword(getActivity());
        String merchantLoginId = PreferenceData.getMerchantLoginId(getActivity());
        if (merchantLoginId.trim().isEmpty() || merchantLoginPassword.trim().isEmpty()) {
            if (getView() == null) {
                return;
            }
            Tracer.showSnack(getView(), R.string.plz_login_manually);
            return;
        }
        mEditTextPassword.setText(merchantLoginPassword);
        mEditTextMerchantIdMobileNumber.setText(merchantLoginId);
        startSignInProcess();
    }

    @Override
    public void onFingerPrintAuthFailed(int errorCode, String errorMessage) {
        Tracer.debug(TAG, "onFingerPrintAuthFailed : ");
        if (getView() == null) {
            return;
        }
        Tracer.showSnack(getView(), R.string.error_scanning_finger_not_recognized);
    }

    /**
     * Method to set the Activity Title
     */
    private void setTitle() {
        Tracer.debug(TAG, "setTitle: ");
        if (getActivity() instanceof OnBaseActivityListener) {
            ((OnBaseActivityListener) getActivity()).onBaseActivitySetScreenTitle(getString(R.string.screen_title_merchant_login));
        }
    }

    /**
     * Method to init the Fragment
     */
    private void init() {
        Tracer.debug(TAG, "init: ");
        if (getView() == null) {
            return;
        }
        mIsFingerPrintDeviceWorkingFine = true;
        mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(getActivity(), this);
        mFingerPrintAuthHelper.startAuth();
        getView().findViewById(R.id.fragment_merchant_login_textView_sign_in).setOnClickListener(this);
        getView().findViewById(R.id.fragment_merchant_login_textView_forgot_password).setOnClickListener(this);
        mTextInputLayoutMerchantIdMobileNumber = (TextInputLayout) getView().findViewById(R.id.fragment_merchant_login_textInputLayout_merchant_id_mobile_number);
        mEditTextMerchantIdMobileNumber = (EditText) getView().findViewById(R.id.fragment_merchant_login_editText_merchant_id_mobile_number);
        mTextInputLayoutPassword = (TextInputLayout) getView().findViewById(R.id.fragment_merchant_login_textInputLayout_password);
        mEditTextPassword = (EditText) getView().findViewById(R.id.fragment_merchant_login_editText_password);

        // ADD TEXT CHANGE LISTENER
        mEditTextMerchantIdMobileNumber.addTextChangedListener(new OnTextInputLayoutTextChangeListener(mTextInputLayoutMerchantIdMobileNumber));
        mEditTextPassword.addTextChangedListener(new OnTextInputLayoutTextChangeListener(mTextInputLayoutPassword));
    }

    /**
     * Method to initiate the User Sign In Process
     */
    private void startSignInProcess() {
        Tracer.debug(TAG, "startSignInProcess: ");
        Utils.hideKeyboard(getActivity(), getView());
        if (!isLoginDetailValid()) {
            return;
        }
        String userId = mEditTextMerchantIdMobileNumber.getText().toString();
        String password = mEditTextPassword.getText().toString();
        Date date = new Date();
        String timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT);
        String token = Utils.createToken(getActivity(), getString(R.string.endpoint_merchant_login), date);
        String publicKey = getString(R.string.public_key);
        String pushId = "123";
        String gcmId = "123";
        DTOMerchantLoginRequest dtoMerchantLoginRequest = new DTOMerchantLoginRequest(token, timeStamp, publicKey, userId, password, pushId, gcmId);
        Utils.showLoadingDialog(getActivity());
        mMerchantNetworkTaskProvider.merchantLoginTask(getActivity(), dtoMerchantLoginRequest, mMerchantLoginResponseNetworkCallBack);
    }

    /**
     * Method to check weather the Login detail insert by merchant is valid or not
     *
     * @return
     */
    private boolean isLoginDetailValid() {
        Tracer.debug(TAG, "isLoginDetailValid: ");
        if (getView() == null) {
            return false;
        }
        String merchantIdMobileNumber = mEditTextMerchantIdMobileNumber.getText().toString();
        String password = mEditTextPassword.getText().toString();

        // Validate Merchant-Id/Mobile-Number
        if (Utils.isStringEmpty(merchantIdMobileNumber)) {
            showTextInputError(mTextInputLayoutMerchantIdMobileNumber, getString(R.string.field_should_not_be_empty_caps));
            return false;
        }

        // Validate Password
        if (Utils.isStringEmpty(password)) {
            showTextInputError(mTextInputLayoutPassword, getString(R.string.field_should_not_be_empty_caps));
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

    /**
     * Method to show dialog to enable the finger print
     */
    private void showEnableFingerPrintDialog(final String loginId, final String loginPassword) {
        Tracer.debug(TAG, "showEnableFingerPrintDialog : ");
        Context context = getActivity();
        int iconId = R.drawable.ic_fingerprint_normal;
        String title = getString(R.string.enable_finger_print);
        String message = getString(R.string.are_you_sure_enable_finger_print_login);
        DialogInterface.OnClickListener onOkClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                PreferenceData.setHaveFingerPrintConsent(getActivity());
                PreferenceData.setMerchantLoginId(getActivity(), loginId);
                PreferenceData.setMerchantLoginPassword(getActivity(), loginPassword);
                goToSuccessScreen();
            }
        };
        DialogInterface.OnClickListener onCancelClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                goToSuccessScreen();
            }
        };
        boolean cancellable = false;
        MKRDialogUtil.showOKCancelDialog(context, iconId, title, message, onOkClickListener, onCancelClickListener, cancellable);
    }

    /**
     * Method to show dialog to update fingerprint login detail
     */
    private void showUpdateFingerLoginDetailDialog(final String loginId, final String loginPassword) {
        Tracer.debug(TAG, "showUpdateFingerLoginDetailDialog : ");
        Context context = getActivity();
        int iconId = R.drawable.ic_fingerprint_normal;
        String title = getString(R.string.update_finger_print);
        String message = getString(R.string.are_you_sure_to_update_the_login_detail);
        DialogInterface.OnClickListener onOkClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                PreferenceData.setMerchantLoginId(getActivity(), loginId);
                PreferenceData.setMerchantLoginPassword(getActivity(), loginPassword);
                goToSuccessScreen();
            }
        };
        DialogInterface.OnClickListener onCancelClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                goToSuccessScreen();
            }
        };
        boolean cancellable = false;
        MKRDialogUtil.showOKCancelDialog(context, iconId, title, message, onOkClickListener, onCancelClickListener, cancellable);
    }

    /**
     * Method to move user from this screen to the success screen
     */
    private void goToSuccessScreen() {
        Tracer.debug(TAG, "goToSuccessScreen : ");
        if (getActivity() instanceof OnBaseActivityListener) {
            Fragment fragment = FragmentProvider.getFragment(FragmentTag.MERCHANT_HOME);
            ((OnBaseActivityListener) getActivity()).onBaseActivityReplaceFragment(fragment, null, FragmentTag.MERCHANT_HOME);
        }
    }
}
