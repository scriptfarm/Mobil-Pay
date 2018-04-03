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
import com.mkrworld.mobilpay.dto.merchantaddsendbill.DTOMerchantSendBillRequest;
import com.mkrworld.mobilpay.dto.merchantaddsendbill.DTOMerchantSendBillResponse;
import com.mkrworld.mobilpay.provider.network.MerchantNetworkTaskProvider;
import com.mkrworld.mobilpay.ui.custom.OnTextInputLayoutTextChangeListener;
import com.mkrworld.mobilpay.utils.Constants;
import com.mkrworld.mobilpay.utils.PreferenceData;
import com.mkrworld.mobilpay.utils.Utils;

import java.util.Date;

/**
 * Created by mkr on 13/3/18.
 */

public class FragmentMerchantSendBill extends Fragment implements OnBaseFragmentListener, View.OnClickListener {
    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentMerchantSendBill";
    private TextInputLayout mTextInputLayoutCustomerNumberOrId;
    private TextInputLayout mTextInputLayoutBillNumber;
    private TextInputLayout mTextInputLayoutBillDescription;
    private TextInputLayout mTextInputLayoutBillAmount;
    private EditText mEditTextCustomerNumberOrId;
    private EditText mEditTextBillNumber;
    private EditText mEditTextBillDescription;
    private EditText mEditTextBillAmount;
    private MerchantNetworkTaskProvider mMerchantNetworkTaskProvider;
    private NetworkCallBack<DTOMerchantSendBillResponse> mMerchantSendBillResponseNetworkCallBack = new NetworkCallBack<DTOMerchantSendBillResponse>() {
        @Override
        public void onSuccess(DTOMerchantSendBillResponse dtoMerchantSendBillResponse) {
            Tracer.debug(TAG, "onSuccess : ");
            Utils.dismissLoadingDialog();
            if (getView() == null) {
                return;
            }
            if (dtoMerchantSendBillResponse == null || dtoMerchantSendBillResponse.getData() == null) {
                Tracer.showSnack(getView(), R.string.no_data_fetch_from_server);
                return;
            }
            Tracer.showSnack(getView(), dtoMerchantSendBillResponse.getMessage() + "  " + dtoMerchantSendBillResponse.getData().getRefTransactionId());
            getActivity().onBackPressed();
        }

        @Override
        public void onError(String errorMessage, int errorCode) {
            Utils.dismissLoadingDialog();
            Tracer.debug(TAG, "onError : ");
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
        return inflater.inflate(R.layout.fragment_merchant_send_bill, container, false);
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
            case R.id.fragment_merchant_send_bill_textView_cancel:
                getActivity().onBackPressed();
                break;
            case R.id.fragment_merchant_send_bill_textView_send:
                startSendBillProcess();
                break;

        }
    }

    /**
     * Method to set the Activity Title
     */
    private void setTitle() {
        Tracer.debug(TAG, "setTitle: ");
        if (getActivity() instanceof OnBaseActivityListener) {
            ((OnBaseActivityListener) getActivity()).onBaseActivitySetScreenTitle(getString(R.string.screen_title_send_bill));
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

        getView().findViewById(R.id.fragment_merchant_send_bill_textView_send).setOnClickListener(this);
        getView().findViewById(R.id.fragment_merchant_send_bill_textView_cancel).setOnClickListener(this);

        mTextInputLayoutCustomerNumberOrId = (TextInputLayout) getView().findViewById(R.id.fragment_merchant_send_bill_textInputLayout_customer_id_mobile_number);
        mEditTextCustomerNumberOrId = (EditText) getView().findViewById(R.id.fragment_merchant_send_bill_editText_customer_id_mobile_number);
        mTextInputLayoutBillNumber = (TextInputLayout) getView().findViewById(R.id.fragment_merchant_send_bill_textInputLayout_bill_number);
        mEditTextBillNumber = (EditText) getView().findViewById(R.id.fragment_merchant_send_bill_editText_bill_number);
        mTextInputLayoutBillDescription = (TextInputLayout) getView().findViewById(R.id.fragment_merchant_send_bill_textInputLayout_bill_description);
        mEditTextBillDescription = (EditText) getView().findViewById(R.id.fragment_merchant_send_bill_editText_bill_description);
        mTextInputLayoutBillAmount = (TextInputLayout) getView().findViewById(R.id.fragment_merchant_send_bill_textInputLayout_bill_amount);
        mEditTextBillAmount = (EditText) getView().findViewById(R.id.fragment_merchant_send_bill_editText_bill_amount);

        // ADD TEXT CHANGE LISTENER
        mEditTextCustomerNumberOrId.addTextChangedListener(new OnTextInputLayoutTextChangeListener(mTextInputLayoutCustomerNumberOrId));
        mEditTextBillNumber.addTextChangedListener(new OnTextInputLayoutTextChangeListener(mTextInputLayoutBillNumber));
        mEditTextBillDescription.addTextChangedListener(new OnTextInputLayoutTextChangeListener(mTextInputLayoutBillDescription));
        mEditTextBillAmount.addTextChangedListener(new OnTextInputLayoutTextChangeListener(mTextInputLayoutBillAmount));
    }

    /**
     * Method to initiate the Send Bill Process
     */
    private void startSendBillProcess() {
        Tracer.debug(TAG, "startSendBillProcess: ");
        Utils.hideKeyboard(getActivity(), getView());
        if (!isBillDetailValid()) {
            return;
        }

        String customerNumberOrId = mEditTextCustomerNumberOrId.getText().toString();
        String billNumber = mEditTextBillNumber.getText().toString();
        String billDescription = mEditTextBillDescription.getText().toString();
        String billAmount = mEditTextBillAmount.getText().toString();

        Date date = new Date();
        String timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT);
        String token = Utils.createToken(getActivity(), getString(R.string.endpoint_send_bill), date);
        String publicKey = getString(R.string.public_key);
        String merchantNupayId = PreferenceData.getMerchantNupayId(getActivity());
        DTOMerchantSendBillRequest dtoMerchantSendBillRequest = new DTOMerchantSendBillRequest(token, timeStamp, publicKey, merchantNupayId, billAmount, billDescription, billNumber, customerNumberOrId, customerNumberOrId);
        Utils.showLoadingDialog(getActivity());
        mMerchantNetworkTaskProvider.merchantSendBillTask(getActivity(), dtoMerchantSendBillRequest, mMerchantSendBillResponseNetworkCallBack);
    }

    /**
     * Method to check weather the QR-Code generation detail insert by merchant is valid or not
     *
     * @return
     */
    private boolean isBillDetailValid() {
        Tracer.debug(TAG, "isBillDetailValid: ");
        if (getView() == null) {
            return false;
        }

        String customerNumberOrId = mEditTextCustomerNumberOrId.getText().toString();
        String billNumber = mEditTextBillNumber.getText().toString();
        String billDescription = mEditTextBillDescription.getText().toString();
        String billAmount = mEditTextBillAmount.getText().toString();

        // Validate Customer Number
        if (Utils.isStringEmpty(customerNumberOrId)) {
            showTextInputError(mTextInputLayoutCustomerNumberOrId, getString(R.string.field_should_not_be_empty_caps));
            return false;
        }

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
