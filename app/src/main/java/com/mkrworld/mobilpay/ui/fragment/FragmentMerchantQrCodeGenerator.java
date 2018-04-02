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
import com.mkrworld.androidlib.utils.MKRDialogUtil;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;
import com.mkrworld.mobilpay.dto.merchantqrcodegenarator.DTOMerchantQRCodeGeneratorRequest;
import com.mkrworld.mobilpay.dto.merchantqrcodegenarator.DTOMerchantQRCodeGeneratorResponse;
import com.mkrworld.mobilpay.provider.fragment.FragmentProvider;
import com.mkrworld.mobilpay.provider.fragment.FragmentTag;
import com.mkrworld.mobilpay.provider.network.MerchantNetworkTaskProvider;
import com.mkrworld.mobilpay.ui.custom.OnTextInputLayoutTextChangeListener;
import com.mkrworld.mobilpay.utils.Constants;
import com.mkrworld.mobilpay.utils.PreferenceData;
import com.mkrworld.mobilpay.utils.Utils;

import java.util.Date;

/**
 * Created by mkr on 13/3/18.
 */

public class FragmentMerchantQrCodeGenerator extends Fragment implements OnBaseFragmentListener, View.OnClickListener {
    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentMerchantQrCodeGenerator";
    private TextInputLayout mTextInputLayoutBillNumber;
    private TextInputLayout mTextInputLayoutBillDescription;
    private TextInputLayout mTextInputLayoutBillAmount;
    private EditText mEditTextBillNumber;
    private EditText mEditTextBillDescription;
    private EditText mEditTextBillAmount;
    private MerchantNetworkTaskProvider mMerchantNetworkTaskProvider;
    private NetworkCallBack<DTOMerchantQRCodeGeneratorResponse> mQRCodeGeneratorResponseNetworkCallBack = new NetworkCallBack<DTOMerchantQRCodeGeneratorResponse>() {
        @Override
        public void onSuccess(DTOMerchantQRCodeGeneratorResponse dtoQRCodeGeneratorResponse) {
            Tracer.debug(TAG, "onSuccess : ");
            MKRDialogUtil.dismissLoadingDialog();
            if(dtoQRCodeGeneratorResponse==null || dtoQRCodeGeneratorResponse.getData()==null){
                Tracer.showSnack(getView(), R.string.no_data_fetch_from_server);
                return;
            }
            DTOMerchantQRCodeGeneratorResponse.Data data = dtoQRCodeGeneratorResponse.getData();
            if (getActivity() instanceof OnBaseActivityListener) {
                Bundle bundle = new Bundle();
                bundle.putString(FragmentMerchantQrCode.EXTRA_QR_CODE_TITLE, PreferenceData.getMerchantLoginId(getActivity()));
                bundle.putBoolean(FragmentMerchantQrCode.EXTRA_IS_DYNAMIC_QR_CODE, true);
                bundle.putString(FragmentMerchantQrCode.EXTRA_BILL_NUMBER, data.getBillNumber());
                bundle.putString(FragmentMerchantQrCode.EXTRA_BILL_AMOUNT, data.getAmount());
                bundle.putString(FragmentMerchantQrCode.EXTRA_QR_CODE_TOKEN, data.getQrCodeToken());
                Fragment fragment = FragmentProvider.getFragment(FragmentTag.MERCHANT_QR_CODE);
                ((OnBaseActivityListener) getActivity()).onBaseActivityAddFragment(fragment, bundle, true, FragmentTag.MERCHANT_QR_CODE);
            }
        }

        @Override
        public void onError(String errorMessage, int errorCode) {
            Tracer.debug(TAG, "onError : ");
            MKRDialogUtil.dismissLoadingDialog();
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
        return inflater.inflate(R.layout.fragment_merchant_qrcode_generator, container, false);
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
            case R.id.fragment_merchant_qrcode_generator_textView_cancel:
                getActivity().onBackPressed();
                break;
            case R.id.fragment_merchant_qrcode_generator_textView_generate:
                startQRCodeGeneratingProcess();
                break;
        }
    }

    /**
     * Method to set the Activity Title
     */
    private void setTitle() {
        Tracer.debug(TAG, "setTitle: ");
        if (getActivity() instanceof OnBaseActivityListener) {
            ((OnBaseActivityListener) getActivity()).onBaseActivitySetScreenTitle(getString(R.string.screen_title_merchant_qrcode));
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
        getView().findViewById(R.id.fragment_merchant_qrcode_generator_textView_generate).setOnClickListener(this);
        getView().findViewById(R.id.fragment_merchant_qrcode_generator_textView_cancel).setOnClickListener(this);
        mTextInputLayoutBillNumber = (TextInputLayout) getView().findViewById(R.id.fragment_merchant_qrcode_generator_textInputLayout_bill_number);
        mEditTextBillNumber = (EditText) getView().findViewById(R.id.fragment_merchant_qrcode_generator_editText_bill_number);
        mTextInputLayoutBillDescription = (TextInputLayout) getView().findViewById(R.id.fragment_merchant_qrcode_generator_textInputLayout_bill_description);
        mEditTextBillDescription = (EditText) getView().findViewById(R.id.fragment_merchant_qrcode_generator_editText_bill_description);
        mTextInputLayoutBillAmount = (TextInputLayout) getView().findViewById(R.id.fragment_merchant_qrcode_generator_textInputLayout_bill_amount);
        mEditTextBillAmount = (EditText) getView().findViewById(R.id.fragment_merchant_qrcode_generator_editText_bill_amount);

        // ADD TEXT CHANGE LISTENER
        mEditTextBillNumber.addTextChangedListener(new OnTextInputLayoutTextChangeListener(mTextInputLayoutBillNumber));
        mEditTextBillDescription.addTextChangedListener(new OnTextInputLayoutTextChangeListener(mTextInputLayoutBillDescription));
        mEditTextBillAmount.addTextChangedListener(new OnTextInputLayoutTextChangeListener(mTextInputLayoutBillAmount));
    }

    /**
     * Method to initiate the Qr-Code Generation Process
     */
    private void startQRCodeGeneratingProcess() {
        Tracer.debug(TAG, "startQRCodeGeneratingProcess: ");
        Utils.hideKeyboard(getActivity(), getView());
        if (!isQrCodeDetailValid()) {
            return;
        }
        String billNumber = mEditTextBillNumber.getText().toString();
        String billDescription = mEditTextBillDescription.getText().toString();
        String billAmount = mEditTextBillAmount.getText().toString();
        Date date = new Date();
        String timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT);
        String token = Utils.createToken(getActivity(), getString(R.string.endpoint_generate_qr_code_token), date);
        String publicKey = getString(R.string.public_key);
        String nupayId = PreferenceData.getMerchantNupayId(getActivity());
        DTOMerchantQRCodeGeneratorRequest dtoQRCodeGeneratorRequest = new DTOMerchantQRCodeGeneratorRequest(token, timeStamp, publicKey, billAmount, billNumber, billDescription, nupayId);
        Utils.showLoadingDialog(getActivity());
        mMerchantNetworkTaskProvider.merchantQRCodeGeneratorTask(getActivity(), dtoQRCodeGeneratorRequest, mQRCodeGeneratorResponseNetworkCallBack);
    }

    /**
     * Method to check weather the QR-Code generation detail insert by merchant is valid or not
     *
     * @return
     */
    private boolean isQrCodeDetailValid() {
        Tracer.debug(TAG, "isQrCodeDetailValid: ");
        if (getView() == null) {
            return false;
        }
        String billNumber = mEditTextBillNumber.getText().toString();
        String billDescription = mEditTextBillDescription.getText().toString();
        String billAmount = mEditTextBillAmount.getText().toString();

        // Validate Bill Number
        if (Utils.isStringEmpty(billNumber)) {
            showTextInputError(mTextInputLayoutBillNumber, getString(R.string.field_should_not_be_empty_caps));
            return false;
        }

        // Validate Bill Description
        if (Utils.isStringEmpty(billDescription)) {
            showTextInputError(mTextInputLayoutBillDescription, getString(R.string.field_should_not_be_empty_caps));
            return false;
        }

        // Validate Bill Amount
        if (Utils.isStringEmpty(billAmount)) {
            showTextInputError(mTextInputLayoutBillAmount, getString(R.string.field_should_not_be_empty_caps));
            return false;
        }

        // VALIDATE MIN AMOUNT
        try {
            int amount = Integer.parseInt(billAmount.trim());
            if (amount < Constants.MIN_TRANSACTION_AMOUNT) {
                showTextInputError(mTextInputLayoutBillAmount, getString(R.string.amount_should_be_greater_then_caps) + " " + Constants.MIN_TRANSACTION_AMOUNT);
                return false;
            }
        } catch (Exception e) {
            Tracer.error(TAG, "isQrCodeDetailValid: " + e.getMessage() + "  " + e.getMessage());
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
