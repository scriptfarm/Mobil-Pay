package com.mkrworld.mobilpay.ui.fragment

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
import com.mkrworld.mobilpay.dto.network.sendbill.DTOSendBillRequest
import com.mkrworld.mobilpay.dto.network.sendbill.DTOSendBillResponse
import com.mkrworld.mobilpay.provider.network.AppNetworkTaskProvider
import com.mkrworld.mobilpay.ui.custom.OnTextInputLayoutTextChange
import com.mkrworld.mobilpay.utils.PreferenceData
import com.mkrworld.mobilpay.utils.Utils
import java.util.*

/**
 * Created by mkr on 13/3/18.
 */

class FragmentSendBill : Fragment(), OnBaseFragmentListener, View.OnClickListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentCashCollect"
    }

    private var mTextInputLayoutMobileNumber : TextInputLayout? = null
    private var mTextInputLayoutBillNumber : TextInputLayout? = null
    private var mTextInputLayoutBillDescription : TextInputLayout? = null
    private var mTextInputLayoutBillAmount : TextInputLayout? = null
    private var mEditTextMobileNumber : EditText? = null
    private var mEditTextBillNumber : EditText? = null
    private var mEditTextBillDescription : EditText? = null
    private var mEditTextBillAmount : EditText? = null
    private var mAppNetworkTaskProvider : AppNetworkTaskProvider? = null

    private val mSendBillResponseNetworkCallBack = object : NetworkCallBack<DTOSendBillResponse> {
        override fun onSuccess(dto : DTOSendBillResponse) {
            Tracer.debug(TAG, "onSuccess : ")
            Utils.dismissLoadingDialog()
            if (view == null) {
                return
            }
            if (dto == null || dto.getData() == null) {
                Tracer.showSnack(view !!, R.string.no_data_fetch_from_server)
                return
            }
            Tracer.showSnack(view !!, dto.getMessage())
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
            val userId = mEditTextMobileNumber!!.text.toString().trim()
            val billNumber = mEditTextBillNumber!!.text.toString().trim()
            val billDescription = mEditTextBillDescription!!.text.toString().trim()
            val billAmount  = mEditTextBillAmount!!.text.toString().trim()

            if (userId.length!=10) {
                showTextInputError(mTextInputLayoutMobileNumber, getString(R.string.enter_valid_mobile_number))
                return false
            }
            if (billNumber.length<=0) {
                showTextInputError(mTextInputLayoutBillNumber, getString(R.string.field_should_not_be_empty_caps))
                return false
            }
            if (billDescription.length<=0) {
                showTextInputError(mTextInputLayoutBillDescription, getString(R.string.field_should_not_be_empty_caps))
                return false
            }
            if (billAmount.length<=0) {
                showTextInputError(mTextInputLayoutBillAmount, getString(R.string.field_should_not_be_empty_caps))
                return false
            }
            return true
        }

    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        Tracer.debug(TAG, "onCreateView: ")
        return inflater !!.inflate(R.layout.fragment_send_bill, container, false)
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
        mAppNetworkTaskProvider?.detachProvider()
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
            R.id.fragment_send_bill_textView_cancel -> activity.onBackPressed()
            R.id.fragment_send_bill_textView_send -> startPayCashProcess()
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

        mAppNetworkTaskProvider = AppNetworkTaskProvider()
        mAppNetworkTaskProvider !!.attachProvider()

        view !!.findViewById<View>(R.id.fragment_send_bill_textView_send).setOnClickListener(this)
        view !!.findViewById<View>(R.id.fragment_send_bill_textView_cancel).setOnClickListener(this)

        mTextInputLayoutMobileNumber = view !!.findViewById<View>(R.id.fragment_send_bill_textInputLayout_mobile_number) as TextInputLayout
        mEditTextMobileNumber = view !!.findViewById<View>(R.id.fragment_send_bill_editText_mobile_number) as EditText
        mTextInputLayoutBillNumber = view !!.findViewById<View>(R.id.fragment_send_bill_textInputLayout_bill_number) as TextInputLayout
        mEditTextBillNumber = view !!.findViewById<View>(R.id.fragment_send_bill_editText_bill_number) as EditText
        mTextInputLayoutBillDescription = view !!.findViewById<View>(R.id.fragment_send_bill_textInputLayout_bill_description) as TextInputLayout
        mEditTextBillDescription = view !!.findViewById<View>(R.id.fragment_send_bill_editText_bill_description) as EditText
        mTextInputLayoutBillAmount = view !!.findViewById<View>(R.id.fragment_send_bill_textInputLayout_bill_amount) as TextInputLayout
        mEditTextBillAmount = view !!.findViewById<View>(R.id.fragment_send_bill_editText_bill_amount) as EditText

        // ADD TEXT CHANGE LISTENER
        mEditTextMobileNumber !!.addTextChangedListener(OnTextInputLayoutTextChange(mTextInputLayoutMobileNumber !!))
        mEditTextBillNumber !!.addTextChangedListener(OnTextInputLayoutTextChange(mTextInputLayoutBillNumber !!))
        mEditTextBillDescription !!.addTextChangedListener(OnTextInputLayoutTextChange(mTextInputLayoutBillDescription !!))
        mEditTextBillAmount !!.addTextChangedListener(OnTextInputLayoutTextChange(mTextInputLayoutBillAmount !!))
    }

    /**
     * Method to initiate the Send Bill Process
     */
    private fun startPayCashProcess() {
        Tracer.debug(TAG, "startPayCashProcess: ")
        Utils.hideKeyboard(activity, view)
        if (! isBillDetailValid) {
            return
        }
        val userId = mEditTextMobileNumber!!.text.toString().trim()
        val billNumber = mEditTextBillNumber!!.text.toString().trim()
        val billDescription = mEditTextBillDescription!!.text.toString().trim()
        val billAmount  = mEditTextBillAmount!!.text.toString().trim()
        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(activity, getString(R.string.endpoint_send_bill), date)
        val publicKey = getString(R.string.public_key)
        val dtoMerchantSendBillRequest = DTOSendBillRequest(token !!, timeStamp, publicKey, PreferenceData.getUserType(activity), PreferenceData.getLoginMerchantId(activity), PreferenceData.getLoginAgentId(activity), userId !!, userId !!, billNumber !!, billDescription !!, billAmount !!)
        Utils.showLoadingDialog(activity)
        mAppNetworkTaskProvider?.sendBillTask(activity, dtoMerchantSendBillRequest, mSendBillResponseNetworkCallBack)
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
