package com.mkrworld.mobilpay.ui.fragment

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import com.mkrworld.androidlib.callback.OnBaseActivityListener
import com.mkrworld.androidlib.callback.OnBaseFragmentListener
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.sendforgotpasswordotp.DTOSendForgotPasswordOtpRequest
import com.mkrworld.mobilpay.dto.sendforgotpasswordotp.DTOSendForgotPasswordOtpResponse
import com.mkrworld.mobilpay.provider.fragment.FragmentProvider
import com.mkrworld.mobilpay.provider.fragment.FragmentTag
import com.mkrworld.mobilpay.provider.network.AgentNetworkTaskProvider
import com.mkrworld.mobilpay.ui.custom.OnTextInputLayoutTextChangeListener
import com.mkrworld.mobilpay.utils.Constants
import com.mkrworld.mobilpay.utils.Utils
import java.util.*

/**
 * Created by mkr on 13/3/18.
 */

class FragmentForgotPassword : Fragment(), OnBaseFragmentListener, View.OnClickListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentForgotPassword"
    }

    private var mTextInputLayoutMobileNumber : TextInputLayout? = null
    private var mEditTextMobileNumber : EditText? = null
    private var mRadioButtonMerchant : RadioButton? = null
    private var mRadioButtonAgent : RadioButton? = null
    private var mIsMerchant : Boolean = true
    private var mLoginMerchantId : String = ""

    private var mAgentNetworkTaskProvider : AgentNetworkTaskProvider? = null
    private val mAgentSendForgotPasswordOtpResponseNetworkCallBack = object : NetworkCallBack<DTOSendForgotPasswordOtpResponse> {
        override fun onSuccess(dtoSendForgotPasswordOtpResponse : DTOSendForgotPasswordOtpResponse) {
            Tracer.debug(TAG, "onSuccess : ")
            Utils.dismissLoadingDialog()
            if (view == null) {
                return
            }
            if (dtoSendForgotPasswordOtpResponse == null || dtoSendForgotPasswordOtpResponse.getData() == null) {
                Tracer.showSnack(view !!, R.string.no_data_fetch_from_server)
                return
            }
            Tracer.showSnack(view !!, dtoSendForgotPasswordOtpResponse.getMessage())
            if (activity is OnBaseActivityListener) {
                val bundle = Bundle()
                var userType = ""
                var agentId = ""
                var merchantId = ""
                if (mIsMerchant) {
                    merchantId = mEditTextMobileNumber !!.text.toString()
                    userType = Constants.USER_TYPE_MERCHANT
                } else {
                    merchantId = mLoginMerchantId
                    agentId = mEditTextMobileNumber !!.text.toString()
                    userType = Constants.USER_TYPE_MERCHANT
                }
                bundle.putString(FragmentChangePasswordByOtp.EXTRA_USER_TYPE, userType)
                bundle.putString(FragmentChangePasswordByOtp.EXTRA_LOGIN_MERCHANT_ID, merchantId)
                bundle.putString(FragmentChangePasswordByOtp.EXTRA_LOGIN_AGENT_ID, agentId)
                val fragment = FragmentProvider.getFragment(FragmentTag.CHANGE_PASSWORD_BY_OTP)
                (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment !!, bundle, true, FragmentTag.CHANGE_PASSWORD_BY_OTP)
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
     * Method to check weather the OTP generation detail insert by agent is valid or not
     *
     * @return
     */
    private val isGenerateOtpDetailValid : Boolean
        get() {
            Tracer.debug(TAG, "isGenerateOtpDetailValid: ")
            if (view == null) {
                return false
            }

            val mobileNumber = mEditTextMobileNumber !!.text.toString()
            if (Utils.isStringEmpty(mobileNumber)) {
                showTextInputError(mTextInputLayoutMobileNumber, getString(R.string.field_should_not_be_empty_caps))
                return false
            }
            return true
        }

    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        Tracer.debug(TAG, "onCreateView: ")
        return inflater !!.inflate(R.layout.fragment_forgot_password, container, false)
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
            R.id.fragment_forgot_password_textView_submit -> startSendOtpProcess()
            R.id.fragment_forgot_password_radio_agent -> {
                mRadioButtonAgent !!.isChecked = true
                mRadioButtonMerchant !!.isChecked = false
                mIsMerchant = false
            }
            R.id.fragment_forgot_password_radio_merchant -> {
                mRadioButtonAgent !!.isChecked = false
                mRadioButtonMerchant !!.isChecked = true
                mIsMerchant = true
            }
        }
    }

    /**
     * Method to set the Activity Title
     */
    private fun setTitle() {
        Tracer.debug(TAG, "setTitle: ")
        if (activity is OnBaseActivityListener) {
            (activity as OnBaseActivityListener).onBaseActivitySetScreenTitle(getString(R.string.screen_title_forgot_password))
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

        mTextInputLayoutMobileNumber = view !!.findViewById<View>(R.id.fragment_forgot_password_textInputLayout_mobile_number) as TextInputLayout
        mEditTextMobileNumber = view !!.findViewById<View>(R.id.fragment_forgot_password_editText_mobile_number) as EditText
        mRadioButtonAgent = view !!.findViewById<View>(R.id.fragment_forgot_password_radio_agent) as RadioButton
        mRadioButtonMerchant = view !!.findViewById<View>(R.id.fragment_forgot_password_radio_merchant) as RadioButton
        // ADD CLICK LISTENER
        mRadioButtonAgent !!.setOnClickListener(this)
        mRadioButtonMerchant !!.setOnClickListener(this)
        view !!.findViewById<View>(R.id.fragment_forgot_password_textView_submit).setOnClickListener(this)
        // ADD TEXT WATCHER
        mEditTextMobileNumber !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutMobileNumber !!))

        // INIT UI
        if (mIsMerchant) {
            mRadioButtonMerchant !!.isChecked = true
            mRadioButtonAgent !!.isChecked = false
        } else {
            mRadioButtonMerchant !!.isChecked = false
            mRadioButtonAgent !!.isChecked = true
        }
    }

    /**
     * Method to initiate the Send OTP Process
     */
    private fun startSendOtpProcess() {
        Tracer.debug(TAG, "startSendOtpProcess: ")
        Utils.hideKeyboard(activity, view)
        if (! isGenerateOtpDetailValid) {
            return
        }
        var userType = ""
        var agentId = ""
        var merchantId = ""
        if (mIsMerchant) {
            merchantId = mEditTextMobileNumber !!.text.toString()
            userType = Constants.USER_TYPE_MERCHANT
        } else {
            merchantId = mLoginMerchantId
            agentId = mEditTextMobileNumber !!.text.toString()
            userType = Constants.USER_TYPE_MERCHANT
        }
        Tracer.debug(TAG, "startSendOtpProcess : ")
        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(activity, getString(R.string.endpoint_send_forgot_password_otp), date)
        val publicKey = getString(R.string.public_key)
        val dtoAgentSendForgotPasswordOtpRequest = DTOSendForgotPasswordOtpRequest(token !!, timeStamp, publicKey, userType, merchantId, agentId)
        Utils.showLoadingDialog(activity)
        mAgentNetworkTaskProvider !!.sendForgotPasswordOtpTask(activity, dtoAgentSendForgotPasswordOtpRequest, mAgentSendForgotPasswordOtpResponseNetworkCallBack)
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
