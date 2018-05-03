package com.mkrworld.mobilpay.ui.fragment.merchant

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.*
import android.widget.EditText
import com.mkrworld.androidlib.callback.OnBaseActivityListener
import com.mkrworld.androidlib.callback.OnBaseFragmentListener
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.merchant.merchantsendbill.DTOMerchantSendBillRequest
import com.mkrworld.mobilpay.dto.merchant.merchantsendbill.DTOMerchantSendBillResponse
import com.mkrworld.mobilpay.provider.network.MerchantNetworkTaskProvider
import com.mkrworld.mobilpay.ui.custom.OnTextInputLayoutTextChangeListener
import com.mkrworld.mobilpay.utils.Constants
import com.mkrworld.mobilpay.utils.PreferenceData
import com.mkrworld.mobilpay.utils.Utils
import java.util.*

/**
 * Created by mkr on 13/3/18.
 */

class FragmentMerchantSendBill : Fragment(), OnBaseFragmentListener, View.OnClickListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentMerchantSendBill"
    }

    private var mTextInputLayoutMobileNumber : TextInputLayout? = null
    private var mEditTextMobileNumber : EditText? = null
    private var mTextInputLayoutBillNumber : TextInputLayout? = null
    private var mEditTextBillNumber : EditText? = null
    private var mTextInputLayoutBillDescription : TextInputLayout? = null
    private var mEditTextBillDescription : EditText? = null
    private var mTextInputLayoutBillAmount : TextInputLayout? = null
    private var mEditTextBillAmount : EditText? = null

    private var mMerchantNetworkTaskProvider : MerchantNetworkTaskProvider? = null
    private val mMerchantSendBillResponseNetworkCallBack = object : NetworkCallBack<DTOMerchantSendBillResponse> {
        override fun onSuccess(dtoMerchantSendBillResponse : DTOMerchantSendBillResponse) {
            Tracer.debug(TAG, "onSuccess : ")
            Utils.dismissLoadingDialog()
            if (view == null) {
                return
            }
            if (dtoMerchantSendBillResponse == null || dtoMerchantSendBillResponse.getData() == null) {
                Tracer.showSnack(view !!, R.string.no_data_fetch_from_server)
                return
            }
            Tracer.showSnack(view !!, dtoMerchantSendBillResponse.getMessage())
            activity.onBackPressed()
        }

        override fun onError(errorMessage : String, errorCode : Int) {
            Utils.dismissLoadingDialog()
            Tracer.debug(TAG, "onError : ")
            if (view == null) {
                return
            }
            Tracer.showSnack(view !!, errorMessage)
        }
    }

    /**
     * Method to check weather detail insert by agent is valid or not
     *
     * @return
     */
    private val isBillDetailValid : Boolean
        get() {
            Tracer.debug(TAG, "isBillDetailValid: ")
            val mobileNumber = mEditTextMobileNumber !!.text.toString()
            val billNumber = mEditTextBillNumber !!.text.toString()
            val billDescription = mEditTextBillDescription !!.text.toString()
            val billAmount = mEditTextBillAmount !!.text.toString()

            if (mobileNumber.trim().length != 10 || (! Utils.isValidPhone(mobileNumber.trim()))) {
                showTextInputError(mTextInputLayoutMobileNumber, getString(R.string.enter_valid_mobile_number))
                return false
            }

            if (billNumber.trim().isEmpty()) {
                showTextInputError(mTextInputLayoutBillNumber, getString(R.string.field_should_not_be_empty_caps))
                return false
            }

            if (billDescription.trim().isEmpty()) {
                showTextInputError(mTextInputLayoutBillDescription, getString(R.string.field_should_not_be_empty_caps))
                return false
            }

            try {
                val amount = Integer.parseInt(billAmount.trim())
                if (amount < Constants.MIN_TRANSACTION_AMOUNT) {
                    showTextInputError(mTextInputLayoutBillAmount, getString(R.string.amount_should_be_greater_then_caps) + " " + Constants.MIN_TRANSACTION_AMOUNT)
                    return false
                }
            } catch (e : Exception) {
                Tracer.error(TAG, "isBillDetailValid: " + e.message + "  " + e.message)
                return false
            }

            return true
        }

    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        Tracer.debug(TAG, "onCreateView: ")
        return inflater !!.inflate(R.layout.fragment_merchant_send_bill, container, false)
    }

    override fun onViewCreated(view : View?, savedInstanceState : Bundle?) {
        Tracer.debug(TAG, "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setTitle()
        init()
    }

    override fun onCreateOptionsMenu(menu : Menu?, inflater : MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.menu_nothing, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        if (mMerchantNetworkTaskProvider != null) {
            mMerchantNetworkTaskProvider !!.detachProvider()
        }
        super.onDestroyView()
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

    override fun onClick(view : View) {
        Tracer.debug(TAG, "onClick: ")
        when (view.id) {
            R.id.fragment_merchant_send_bill_textView_cancel -> activity.onBackPressed()
            R.id.fragment_merchant_send_bill_textView_send -> startSendBillProcess()
        }
    }

    /**
     * Method to set the Activity Title
     */
    private fun setTitle() {
        Tracer.debug(TAG, "setTitle: ")
        if (activity is OnBaseActivityListener) {
            (activity as OnBaseActivityListener).onBaseActivitySetScreenTitle(getString(R.string.screen_title_send_bill))
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

        mMerchantNetworkTaskProvider = MerchantNetworkTaskProvider()
        mMerchantNetworkTaskProvider !!.attachProvider()

        view !!.findViewById<View>(R.id.fragment_merchant_send_bill_textView_send).setOnClickListener(this)
        view !!.findViewById<View>(R.id.fragment_merchant_send_bill_textView_cancel).setOnClickListener(this)

        mTextInputLayoutMobileNumber = view !!.findViewById<View>(R.id.fragment_merchant_send_bill_textInputLayout_mobile_number) as TextInputLayout
        mEditTextMobileNumber = view !!.findViewById<View>(R.id.fragment_merchant_send_bill_editText_mobile_number) as EditText
        mTextInputLayoutBillNumber = view !!.findViewById<View>(R.id.fragment_merchant_send_bill_textInputLayout_bill_number) as TextInputLayout
        mEditTextBillNumber = view !!.findViewById<View>(R.id.fragment_merchant_send_bill_editText_bill_number) as EditText
        mTextInputLayoutBillDescription = view !!.findViewById<View>(R.id.fragment_merchant_send_bill_textInputLayout_bill_description) as TextInputLayout
        mEditTextBillDescription = view !!.findViewById<View>(R.id.fragment_merchant_send_bill_editText_bill_description) as EditText
        mTextInputLayoutBillAmount = view !!.findViewById<View>(R.id.fragment_merchant_send_bill_textInputLayout_bill_amount) as TextInputLayout
        mEditTextBillAmount = view !!.findViewById<View>(R.id.fragment_merchant_send_bill_editText_bill_amount) as EditText

        // ADD TEXT CHANGE LISTENER
        mEditTextMobileNumber !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutMobileNumber !!))
        mEditTextBillNumber !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutBillNumber !!))
        mEditTextBillDescription !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutBillDescription !!))
        mEditTextBillAmount !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutBillAmount !!))
    }

    /**
     * Method to initiate the Send Bill Process
     */
    private fun startSendBillProcess() {
        Tracer.debug(TAG, "startSendBillProcess: ")
        Utils.hideKeyboard(activity, view)
        if (! isBillDetailValid) {
            return
        }
        val mobileNumber = mEditTextMobileNumber !!.text.toString()
        val billNumber = mEditTextBillNumber !!.text.toString()
        val billDescription = mEditTextBillDescription !!.text.toString()
        val billAmount = mEditTextBillAmount !!.text.toString()
        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(activity, getString(R.string.endpoint_send_bill), date)
        val publicKey = getString(R.string.public_key)
        val dtoMerchantSendBillRequest = DTOMerchantSendBillRequest(token !!, timeStamp, publicKey, PreferenceData.getUserType(activity), PreferenceData.getLoginMerchantId(activity), PreferenceData.getLoginAgentId(activity), mobileNumber, mobileNumber, billNumber, billDescription, billAmount, "1")
        Utils.showLoadingDialog(activity)
        mMerchantNetworkTaskProvider !!.merchantSendBillTask(activity, dtoMerchantSendBillRequest, mMerchantSendBillResponseNetworkCallBack)
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
