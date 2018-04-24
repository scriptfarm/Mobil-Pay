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
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.agent.agentqrcodegenarator.DTOAgentQRCodeGeneratorRequest
import com.mkrworld.mobilpay.dto.agent.agentqrcodegenarator.DTOAgentQRCodeGeneratorResponse
import com.mkrworld.mobilpay.provider.fragment.FragmentProvider
import com.mkrworld.mobilpay.provider.fragment.FragmentTag
import com.mkrworld.mobilpay.provider.network.AgentNetworkTaskProvider
import com.mkrworld.mobilpay.ui.custom.OnTextInputLayoutTextChangeListener
import com.mkrworld.mobilpay.utils.Constants
import com.mkrworld.mobilpay.utils.PreferenceData
import com.mkrworld.mobilpay.utils.Utils
import java.util.*

/**
 * Created by mkr on 13/3/18.
 */

class FragmentAgentQrCodeGenerator : Fragment(), OnBaseFragmentListener, View.OnClickListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentAgentQrCodeGenerator"
    }

    private var mTextInputLayoutBillNumber : TextInputLayout? = null
    private var mTextInputLayoutBillDescription : TextInputLayout? = null
    private var mTextInputLayoutBillAmount : TextInputLayout? = null
    private var mEditTextBillNumber : EditText? = null
    private var mEditTextBillDescription : EditText? = null
    private var mEditTextBillAmount : EditText? = null
    private var mAgentNetworkTaskProvider : AgentNetworkTaskProvider? = null
    private val mQRCodeGeneratorResponseNetworkCallBack = object : NetworkCallBack<DTOAgentQRCodeGeneratorResponse> {
        override fun onSuccess(dtoQRCodeGeneratorResponse : DTOAgentQRCodeGeneratorResponse) {
            Tracer.debug(TAG, "onSuccess : ")
            Utils.dismissLoadingDialog()
            if (dtoQRCodeGeneratorResponse == null || dtoQRCodeGeneratorResponse.getData() == null) {
                Tracer.showSnack(view !!, R.string.no_data_fetch_from_server)
                return
            }
            val data = dtoQRCodeGeneratorResponse.getData()
            if (activity is OnBaseActivityListener) {
                val bundle = Bundle()
                bundle.putString(FragmentAgentQrCode.EXTRA_QR_CODE_TITLE, PreferenceData.getLoginId(activity))
                bundle.putBoolean(FragmentAgentQrCode.EXTRA_IS_DYNAMIC_QR_CODE, true)
                bundle.putString(FragmentAgentQrCode.EXTRA_BILL_NUMBER, data !!.billNumber)
                bundle.putString(FragmentAgentQrCode.EXTRA_BILL_AMOUNT, data.amount)
                bundle.putString(FragmentAgentQrCode.EXTRA_QR_CODE_TOKEN, data.qrCodeToken)
                val fragment = FragmentProvider.getFragment(FragmentTag.AGENT_QR_CODE)
                (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment !!, bundle, true, FragmentTag.AGENT_QR_CODE)
            }
        }

        override fun onError(errorMessage : String, errorCode : Int) {
            Tracer.debug(TAG, "onError : ")
            Utils.dismissLoadingDialog()
            if (view == null) {
                return
            }
            Tracer.showSnack(view !!, errorMessage)
        }
    }

    /**
     * Method to check weather the QR-Code generation detail insert by agent is valid or not
     *
     * @return
     */
    private val isQrCodeDetailValid : Boolean
        get() {
            Tracer.debug(TAG, "isQrCodeDetailValid: ")
            if (view == null) {
                return false
            }
            val billNumber = mEditTextBillNumber !!.text.toString()
            val billDescription = mEditTextBillDescription !!.text.toString()
            val billAmount = mEditTextBillAmount !!.text.toString()
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
        return inflater !!.inflate(R.layout.fragment_agent_qrcode_generator, container, false)
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
        if (activity != null) {
            activity.onBackPressed()
        }
    }

    override fun onRefresh() {
        Tracer.debug(TAG, "onRefresh: ")
    }

    override fun onClick(view : View) {
        Tracer.debug(TAG, "onClick: ")
        when (view.id) {
            R.id.fragment_agent_qrcode_generator_textView_cancel -> activity.onBackPressed()
            R.id.fragment_agent_qrcode_generator_textView_generate -> startQRCodeGeneratingProcess()
        }
    }

    /**
     * Method to set the Activity Title
     */
    private fun setTitle() {
        Tracer.debug(TAG, "setTitle: ")
        if (activity is OnBaseActivityListener) {
            (activity as OnBaseActivityListener).onBaseActivitySetScreenTitle(getString(R.string.screen_title_agent_qrcode))
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
        view !!.findViewById<View>(R.id.fragment_agent_qrcode_generator_textView_generate).setOnClickListener(this)
        view !!.findViewById<View>(R.id.fragment_agent_qrcode_generator_textView_cancel).setOnClickListener(this)
        mTextInputLayoutBillNumber = view !!.findViewById<View>(R.id.fragment_agent_qrcode_generator_textInputLayout_bill_number) as TextInputLayout
        mEditTextBillNumber = view !!.findViewById<View>(R.id.fragment_agent_qrcode_generator_editText_bill_number) as EditText
        mTextInputLayoutBillDescription = view !!.findViewById<View>(R.id.fragment_agent_qrcode_generator_textInputLayout_bill_description) as TextInputLayout
        mEditTextBillDescription = view !!.findViewById<View>(R.id.fragment_agent_qrcode_generator_editText_bill_description) as EditText
        mTextInputLayoutBillAmount = view !!.findViewById<View>(R.id.fragment_agent_qrcode_generator_textInputLayout_bill_amount) as TextInputLayout
        mEditTextBillAmount = view !!.findViewById<View>(R.id.fragment_agent_qrcode_generator_editText_bill_amount) as EditText

        // ADD TEXT CHANGE LISTENER
        mEditTextBillNumber !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutBillNumber !!))
        mEditTextBillDescription !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutBillDescription !!))
        mEditTextBillAmount !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutBillAmount !!))
    }

    /**
     * Method to initiate the Qr-Code Generation Process
     */
    private fun startQRCodeGeneratingProcess() {
        Tracer.debug(TAG, "startQRCodeGeneratingProcess: ")
        Utils.hideKeyboard(activity, view)
        if (! isQrCodeDetailValid) {
            return
        }
        val billNumber = mEditTextBillNumber !!.text.toString()
        val billDescription = mEditTextBillDescription !!.text.toString()
        val billAmount = mEditTextBillAmount !!.text.toString()
        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(activity, getString(R.string.endpoint_generate_qr_code_token), date)
        val publicKey = getString(R.string.public_key)
        val agentId = PreferenceData.getLoginId(activity)
        val dtoQRCodeGeneratorRequest = DTOAgentQRCodeGeneratorRequest(token !!, timeStamp, publicKey, billAmount, billNumber, billDescription, agentId)
        Utils.showLoadingDialog(activity)
        mAgentNetworkTaskProvider !!.agentQRCodeGeneratorTask(activity, dtoQRCodeGeneratorRequest, mQRCodeGeneratorResponseNetworkCallBack)
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
