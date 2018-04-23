package com.mkrworld.mobilpay.ui.fragment.agent

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.mkrworld.androidlib.callback.OnBaseActivityListener
import com.mkrworld.androidlib.callback.OnBaseFragmentListener
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.ui.custom.OnTextInputLayoutTextChangeListener
import com.mkrworld.mobilpay.utils.Constants
import com.mkrworld.mobilpay.utils.Utils

/**
 * Created by mkr on 13/3/18.
 */

class FragmentAgentAEPSCollect : Fragment(), OnBaseFragmentListener, View.OnClickListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentAgentAEPSCollect"
    }

    private var mTextInputLayoutCustomerName : TextInputLayout? = null
    private var mTextInputLayoutBankName : TextInputLayout? = null
    private var mTextInputLayoutTransactionAmount : TextInputLayout? = null
    private var mTextInputLayoutMobileNumber : TextInputLayout? = null
    private var mTextInputLayoutAadharNumber : TextInputLayout? = null
    private var mEditTextCustomerName : EditText? = null
    private var mEditTextBankName : EditText? = null
    private var mEditTextTransactionAmount : EditText? = null
    private var mEditTextMobileNumber : EditText? = null
    private var mEditTextAadharNumber : EditText? = null

    /**
     * Method to check weather the AEPS Transaction detail insert by merchant is valid or not
     *
     * @return
     */
    private // Validate Customer Name
    // Validate Bank Name
    // Validate Transaction Amount
    // Validate Customer Mobile Number
    // Validate Aadhar Number
    // VALIDATE MIN AMOUNT
    val isAEPSTransactionDetailValid : Boolean
        get() {
            Tracer.debug(TAG, "isAEPSTransactionDetailValid: ")
            if (view == null) {
                return false
            }
            val customerName = mEditTextCustomerName !!.text.toString()
            val bankName = mEditTextBankName !!.text.toString()
            val transactionAmount = mEditTextTransactionAmount !!.text.toString()
            val mobileNumber = mEditTextMobileNumber !!.text.toString()
            val aadharNumber = mEditTextAadharNumber !!.text.toString()
            if (Utils.isStringEmpty(customerName)) {
                showTextInputError(mTextInputLayoutCustomerName, getString(R.string.field_should_not_be_empty_caps))
                return false
            }
            if (Utils.isStringEmpty(bankName)) {
                showTextInputError(mTextInputLayoutBankName, getString(R.string.field_should_not_be_empty_caps))
                return false
            }
            if (Utils.isStringEmpty(transactionAmount)) {
                showTextInputError(mTextInputLayoutTransactionAmount, getString(R.string.field_should_not_be_empty_caps))
                return false
            }
            if (Utils.isStringEmpty(mobileNumber)) {
                showTextInputError(mTextInputLayoutMobileNumber, getString(R.string.field_should_not_be_empty_caps))
                return false
            }
            if (Utils.isStringEmpty(aadharNumber)) {
                showTextInputError(mTextInputLayoutAadharNumber, getString(R.string.field_should_not_be_empty_caps))
                return false
            }
            try {
                val amount = Integer.parseInt(transactionAmount.trim { it <= ' ' })
                if (amount < Constants.MIN_TRANSACTION_AMOUNT) {
                    showTextInputError(mTextInputLayoutTransactionAmount, getString(R.string.amount_should_be_greater_then_caps) + " " + Constants.MIN_TRANSACTION_AMOUNT)
                    return false
                }
            } catch (e : Exception) {
                Tracer.error(TAG, "isAEPSTransactionDetailValid: " + e.message + "  " + e.message)
                return false
            }

            return true
        }

    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        Tracer.debug(TAG, "onCreateView: ")
        return inflater !!.inflate(R.layout.fragment_agent_aeps_collect, container, false)
    }

    override fun onViewCreated(view : View?, savedInstanceState : Bundle?) {
        Tracer.debug(TAG, "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
        setTitle()
        init()
    }

    override fun onBackPressed() : Boolean {
        Tracer.debug(TAG, "onBackPressed: ")
        return false
    }

    override fun onPopFromBackStack() {
        Tracer.debug(TAG, "onPopFromBackStack: ")
        setTitle()
    }

    override fun onRefresh() {
        Tracer.debug(TAG, "onRefresh: ")
    }

    /**
     * Method to set the Activity Title
     */
    private fun setTitle() {
        Tracer.debug(TAG, "setTitle: ")
        if (activity is OnBaseActivityListener) {
            (activity as OnBaseActivityListener).onBaseActivitySetScreenTitle(getString(R.string.screen_title_aeps_collect))
        }
    }

    override fun onClick(view : View) {
        Tracer.debug(TAG, "onClick: $view")
        when (view.id) {
            R.id.fragment_agent_aeps_collect_textView_aeps -> startAEPSTransactionProcess()
        }
    }

    /**
     * Method to initialize the Fragment
     */
    private fun init() {
        Tracer.debug(TAG, "init: ")
        if (view == null) {
            return
        }
        view !!.findViewById<View>(R.id.fragment_agent_aeps_collect_textView_aeps).setOnClickListener(this)

        mTextInputLayoutCustomerName = view !!.findViewById<View>(R.id.fragment_agent_aeps_collect_textInputLayout_customer_name) as TextInputLayout
        mEditTextCustomerName = view !!.findViewById<View>(R.id.fragment_agent_aeps_collect_editText_customer_name) as EditText
        mTextInputLayoutBankName = view !!.findViewById<View>(R.id.fragment_agent_aeps_collect_textInputLayout_bank_name) as TextInputLayout
        mEditTextBankName = view !!.findViewById<View>(R.id.fragment_agent_aeps_collect_editText_bank_name) as EditText
        mTextInputLayoutTransactionAmount = view !!.findViewById<View>(R.id.fragment_agent_aeps_collect_textInputLayout_transaction_amount) as TextInputLayout
        mEditTextTransactionAmount = view !!.findViewById<View>(R.id.fragment_agent_aeps_collect_editText_transaction_amount) as EditText
        mTextInputLayoutMobileNumber = view !!.findViewById<View>(R.id.fragment_agent_aeps_collect_textInputLayout_mobile_number) as TextInputLayout
        mEditTextMobileNumber = view !!.findViewById<View>(R.id.fragment_agent_aeps_collect_editText_mobile_number) as EditText
        mTextInputLayoutAadharNumber = view !!.findViewById<View>(R.id.fragment_agent_aeps_collect_textInputLayout_aadhar_number) as TextInputLayout
        mEditTextAadharNumber = view !!.findViewById<View>(R.id.fragment_agent_aeps_collect_editText_aadhar_number) as EditText

        // ADD TEXT CHANGE LISTENER
        mEditTextCustomerName !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutCustomerName !!))
        mEditTextBankName !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutBankName !!))
        mEditTextTransactionAmount !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutTransactionAmount !!))
        mEditTextMobileNumber !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutMobileNumber !!))
        mEditTextAadharNumber !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutAadharNumber !!))
    }

    /**
     * Method to initiate the AEPS Transaction Process
     */
    private fun startAEPSTransactionProcess() {
        Tracer.debug(TAG, "startAEPSTransactionProcess: ")
        Utils.hideKeyboard(activity, view)
        if (! isAEPSTransactionDetailValid) {
            return
        }
    }

    /**
     * Method to show the error in textInputLayout
     *
     * @param textInputLayout
     * @param errorMessage
     */
    private fun showTextInputError(textInputLayout : TextInputLayout?, errorMessage : String) {
        Tracer.debug(TAG, "showTextInputError: $textInputLayout    $errorMessage")
        textInputLayout !!.isErrorEnabled = true
        textInputLayout.error = errorMessage
    }
}
