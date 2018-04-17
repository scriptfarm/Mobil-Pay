package com.mkrworld.mobilpay.ui.fragment

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.mkrworld.androidlib.callback.OnBaseActivityListener
import com.mkrworld.androidlib.callback.OnBaseFragmentListener
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.agentfetchbill.DTOAgentFetchBillRequest
import com.mkrworld.mobilpay.dto.agentfetchbill.DTOAgentFetchBillResponse
import com.mkrworld.mobilpay.provider.network.AgentNetworkTaskProvider
import com.mkrworld.mobilpay.ui.custom.OnTextInputLayoutTextChangeListener
import com.mkrworld.mobilpay.utils.Constants
import com.mkrworld.mobilpay.utils.PreferenceData
import com.mkrworld.mobilpay.utils.Utils
import java.util.*

/**
 * Created by mkr on 13/3/18.
 */

class FragmentAgentSendBill : Fragment(), OnBaseFragmentListener, View.OnClickListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentAgentSendBill"
    }

    private var mTextInputLayoutUserName : TextInputLayout? = null
    private var mTextInputLayoutBillNumber : TextInputLayout? = null
    private var mTextInputLayoutBillDescription : TextInputLayout? = null
    private var mTextInputLayoutBillAmount : TextInputLayout? = null
    private var mEditTextUserName : EditText? = null
    private var mEditTextBillNumber : EditText? = null
    private var mEditTextBillDescription : EditText? = null
    private var mEditTextBillAmount : EditText? = null
    private var mAgentNetworkTaskProvider : AgentNetworkTaskProvider? = null
    private val mAgentFetchBillResponseNetworkCallBack = object : NetworkCallBack<DTOAgentFetchBillResponse> {
        override fun onSuccess(dtoAgentFetchBillResponse : DTOAgentFetchBillResponse) {
            Tracer.debug(TAG, "onSuccess : ")
            Utils.dismissLoadingDialog()
            if (view == null) {
                return
            }
            if (dtoAgentFetchBillResponse == null || dtoAgentFetchBillResponse.getData() == null) {
                Tracer.showSnack(view !!, R.string.no_data_fetch_from_server)
                return
            }
            Tracer.showSnack(view !!, dtoAgentFetchBillResponse.getMessage() + "  " + dtoAgentFetchBillResponse.getData() !!.billAmount)
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
            if (view == null) {
                return false
            }

            val customerNumberOrId = mEditTextUserName !!.text.toString()
            val billNumber = mEditTextBillNumber !!.text.toString()
            val billDescription = mEditTextBillDescription !!.text.toString()
            val billAmount = mEditTextBillAmount !!.text.toString()
            if (Utils.isStringEmpty(customerNumberOrId)) {
                showTextInputError(mTextInputLayoutUserName, getString(R.string.field_should_not_be_empty_caps))
                return false
            }
            if (Utils.isStringEmpty(billNumber)) {
                showTextInputError(mTextInputLayoutBillNumber, getString(R.string.field_should_not_be_empty_caps))
                return false
            }
            if (Utils.isStringEmpty(billDescription)) {
                showTextInputError(mTextInputLayoutBillDescription, getString(R.string.field_should_not_be_empty_caps))
                return false
            }
            if (Utils.isStringEmpty(billAmount)) {
                showTextInputError(mTextInputLayoutBillAmount, getString(R.string.field_should_not_be_empty_caps))
                return false
            }
            try {
                val amount = Integer.parseInt(billAmount.trim { it <= ' ' })
                if (amount < Constants.MIN_TRANSACTION_AMOUNT) {
                    showTextInputError(mTextInputLayoutBillAmount, getString(R.string.amount_should_be_greater_then_caps) + " " + Constants.MIN_TRANSACTION_AMOUNT)
                    return false
                }
            } catch (e : Exception) {
                Tracer.error(TAG, "isQrCodeDetailValid: " + e.message + "  " + e.message)
                return false
            }

            return true
        }

    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        Tracer.debug(TAG, "onCreateView: ")
        return inflater !!.inflate(R.layout.fragment_agent_send_bill, container, false)
    }

    override fun onViewCreated(view : View?, savedInstanceState : Bundle?) {
        Tracer.debug(TAG, "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
        setTitle()
        init()
    }

    override fun onDestroyView() {
        if (mAgentNetworkTaskProvider != null) {
            mAgentNetworkTaskProvider !!.detachProvider()
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
            R.id.fragment_agent_send_bill_textView_cancel -> activity.onBackPressed()
            R.id.fragment_agent_send_bill_textView_send -> startSendBillProcess()
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

        mAgentNetworkTaskProvider = AgentNetworkTaskProvider()
        mAgentNetworkTaskProvider !!.attachProvider()

        view !!.findViewById<View>(R.id.fragment_agent_send_bill_textView_send).setOnClickListener(this)
        view !!.findViewById<View>(R.id.fragment_agent_send_bill_textView_cancel).setOnClickListener(this)

        mTextInputLayoutUserName = view !!.findViewById<View>(R.id.fragment_agent_send_bill_textInputLayout_customer_id_mobile_number) as TextInputLayout
        mEditTextUserName = view !!.findViewById<View>(R.id.fragment_agent_send_bill_editText_customer_id_mobile_number) as EditText
        mTextInputLayoutBillNumber = view !!.findViewById<View>(R.id.fragment_agent_send_bill_textInputLayout_bill_number) as TextInputLayout
        mEditTextBillNumber = view !!.findViewById<View>(R.id.fragment_agent_send_bill_editText_bill_number) as EditText
        mTextInputLayoutBillDescription = view !!.findViewById<View>(R.id.fragment_agent_send_bill_textInputLayout_bill_description) as TextInputLayout
        mEditTextBillDescription = view !!.findViewById<View>(R.id.fragment_agent_send_bill_editText_bill_description) as EditText
        mTextInputLayoutBillAmount = view !!.findViewById<View>(R.id.fragment_agent_send_bill_textInputLayout_bill_amount) as TextInputLayout
        mEditTextBillAmount = view !!.findViewById<View>(R.id.fragment_agent_send_bill_editText_bill_amount) as EditText

        // ADD TEXT CHANGE LISTENER
        mEditTextUserName !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutUserName !!))
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

        val userName = mEditTextUserName !!.text.toString()
        val billNumber = mEditTextBillNumber !!.text.toString()
        val billDescription = mEditTextBillDescription !!.text.toString()
        val billAmount = mEditTextBillAmount !!.text.toString()

        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(activity, getString(R.string.endpoint_send_bill), date)
        val publicKey = getString(R.string.public_key)
        val AgentId = PreferenceData.getAgentId(activity)
        val dtoFetchBillRequest = DTOAgentFetchBillRequest(token !!, timeStamp, publicKey, AgentId, "userId")
        Utils.showLoadingDialog(activity)
        mAgentNetworkTaskProvider !!.agentFetchBillTask(activity, dtoFetchBillRequest, mAgentFetchBillResponseNetworkCallBack)
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
