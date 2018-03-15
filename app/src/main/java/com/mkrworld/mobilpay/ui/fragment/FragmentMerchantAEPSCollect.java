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
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;
import com.mkrworld.mobilpay.ui.custom.OnTextInputLayoutTextChangeListener;
import com.mkrworld.mobilpay.utils.Constants;
import com.mkrworld.mobilpay.utils.Utils;

/**
 * Created by mkr on 13/3/18.
 */

public class FragmentMerchantAEPSCollect extends Fragment implements OnBaseFragmentListener, View.OnClickListener {
    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentMerchantAEPSCollect";
    private TextInputLayout mTextInputLayoutCustomerName;
    private TextInputLayout mTextInputLayoutBankName;
    private TextInputLayout mTextInputLayoutTransactionAmount;
    private TextInputLayout mTextInputLayoutMobileNumber;
    private TextInputLayout mTextInputLayoutAadharNumber;
    private EditText mEditTextCustomerName;
    private EditText mEditTextBankName;
    private EditText mEditTextTransactionAmount;
    private EditText mEditTextMobileNumber;
    private EditText mEditTextAadharNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.fragment_aeps_collect, container, false);
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
    }

    @Override
    public void onRefresh() {
        Tracer.debug(TAG, "onRefresh: ");
    }

    /**
     * Method to set the Activity Title
     */
    private void setTitle() {
        Tracer.debug(TAG, "setTitle: ");
        if (getActivity() instanceof OnBaseActivityListener) {
            ((OnBaseActivityListener) getActivity()).onBaseActivitySetScreenTitle(getString(R.string.screen_title_aeps_collect));
        }
    }

    @Override
    public void onClick(View view) {
        Tracer.debug(TAG, "onClick: " + view);
        switch (view.getId()) {
            case R.id.fragment_aeps_collect_textView_aeps:
                startAEPSTransactionProcess();
                break;
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
        getView().findViewById(R.id.fragment_aeps_collect_textView_aeps).setOnClickListener(this);

        mTextInputLayoutCustomerName = (TextInputLayout) getView().findViewById(R.id.fragment_aeps_collect_textInputLayout_customer_name);
        mEditTextCustomerName = (EditText) getView().findViewById(R.id.fragment_aeps_collect_editText_customer_name);
        mTextInputLayoutBankName = (TextInputLayout) getView().findViewById(R.id.fragment_aeps_collect_textInputLayout_bank_name);
        mEditTextBankName = (EditText) getView().findViewById(R.id.fragment_aeps_collect_editText_bank_name);
        mTextInputLayoutTransactionAmount = (TextInputLayout) getView().findViewById(R.id.fragment_aeps_collect_textInputLayout_transaction_amount);
        mEditTextTransactionAmount = (EditText) getView().findViewById(R.id.fragment_aeps_collect_editText_transaction_amount);
        mTextInputLayoutMobileNumber = (TextInputLayout) getView().findViewById(R.id.fragment_aeps_collect_textInputLayout_mobile_number);
        mEditTextMobileNumber = (EditText) getView().findViewById(R.id.fragment_aeps_collect_editText_mobile_number);
        mTextInputLayoutAadharNumber = (TextInputLayout) getView().findViewById(R.id.fragment_aeps_collect_textInputLayout_aadhar_number);
        mEditTextAadharNumber = (EditText) getView().findViewById(R.id.fragment_aeps_collect_editText_aadhar_number);

        // ADD TEXT CHANGE LISTENER
        mEditTextCustomerName.addTextChangedListener(new OnTextInputLayoutTextChangeListener(mTextInputLayoutCustomerName));
        mEditTextBankName.addTextChangedListener(new OnTextInputLayoutTextChangeListener(mTextInputLayoutBankName));
        mEditTextTransactionAmount.addTextChangedListener(new OnTextInputLayoutTextChangeListener(mTextInputLayoutTransactionAmount));
        mEditTextMobileNumber.addTextChangedListener(new OnTextInputLayoutTextChangeListener(mTextInputLayoutMobileNumber));
        mEditTextAadharNumber.addTextChangedListener(new OnTextInputLayoutTextChangeListener(mTextInputLayoutAadharNumber));
    }

    /**
     * Method to initiate the AEPS Transaction Process
     */
    private void startAEPSTransactionProcess() {
        Tracer.debug(TAG, "startAEPSTransactionProcess: ");
        Utils.hideKeyboard(getActivity(), getView());
        if (!isAEPSTransactionDetailValid()) {
            return;
        }
    }

    /**
     * Method to check weather the AEPS Transaction detail insert by merchant is valid or not
     *
     * @return
     */
    private boolean isAEPSTransactionDetailValid() {
        Tracer.debug(TAG, "isAEPSTransactionDetailValid: ");
        if (getView() == null) {
            return false;
        }
        String customerName = mEditTextCustomerName.getText().toString();
        String bankName = mEditTextBankName.getText().toString();
        String transactionAmount = mEditTextTransactionAmount.getText().toString();
        String mobileNumber = mEditTextMobileNumber.getText().toString();
        String aadharNumber = mEditTextAadharNumber.getText().toString();

        // Validate Customer Name
        if (Utils.isStringEmpty(customerName)) {
            showTextInputError(mTextInputLayoutCustomerName, getString(R.string.field_should_not_be_empty_caps));
            return false;
        }

        // Validate Bank Name
        if (Utils.isStringEmpty(bankName)) {
            showTextInputError(mTextInputLayoutBankName, getString(R.string.field_should_not_be_empty_caps));
            return false;
        }

        // Validate Transaction Amount
        if (Utils.isStringEmpty(transactionAmount)) {
            showTextInputError(mTextInputLayoutTransactionAmount, getString(R.string.field_should_not_be_empty_caps));
            return false;
        }

        // Validate Customer Mobile Number
        if (Utils.isStringEmpty(mobileNumber)) {
            showTextInputError(mTextInputLayoutMobileNumber, getString(R.string.field_should_not_be_empty_caps));
            return false;
        }

        // Validate Aadhar Number
        if (Utils.isStringEmpty(aadharNumber)) {
            showTextInputError(mTextInputLayoutAadharNumber, getString(R.string.field_should_not_be_empty_caps));
            return false;
        }

        // VALIDATE MIN AMOUNT
        try {
            int amount = Integer.parseInt(transactionAmount.trim());
            if (amount < Constants.MIN_TRANSACTION_AMOUNT) {
                showTextInputError(mTextInputLayoutTransactionAmount, getString(R.string.amount_should_be_greater_then_caps) + " " + Constants.MIN_TRANSACTION_AMOUNT);
                return false;
            }
        } catch (Exception e) {
            Tracer.error(TAG, "isAEPSTransactionDetailValid: " + e.getMessage() + "  " + e.getMessage());
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
