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
import com.mkrworld.mobilpay.dto.merchantsendforgotpasswordotp.DTOMerchantSendForgotPasswordOtpRequest
import com.mkrworld.mobilpay.dto.merchantsendforgotpasswordotp.DTOMerchantSendForgotPasswordOtpResponse
import com.mkrworld.mobilpay.provider.fragment.FragmentProvider
import com.mkrworld.mobilpay.provider.fragment.FragmentTag
import com.mkrworld.mobilpay.provider.network.AgentNetworkTaskProvider
import com.mkrworld.mobilpay.ui.custom.OnTextInputLayoutTextChangeListener
import com.mkrworld.mobilpay.utils.Utils

import java.util.Date

/**
 * Created by mkr on 13/3/18.
 */

class FragmentForgotPassword : Fragment(), OnBaseFragmentListener, View.OnClickListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentForgotPassword"
    }

    private var mTextInputLayoutMobileNumber : TextInputLayout? = null
    private var mTextInputLayoutMerchantId : TextInputLayout? = null
    private var mEditTextMobileNumber : EditText? = null
    private var mEditTextMerchantId : EditText? = null
    private var mAgentNetworkTaskProvider : AgentNetworkTaskProvider? = null
    private val mMerchantSendForgotPasswordOtpResponseNetworkCallBack = object : NetworkCallBack<DTOMerchantSendForgotPasswordOtpResponse> {
        override fun onSuccess(dtoMerchantSendForgotPasswordOtpResponse : DTOMerchantSendForgotPasswordOtpResponse) {
            Tracer.debug(TAG, "onSuccess : ")
            Utils.dismissLoadingDialog()
            if (view == null) {
                return
            }
            if (dtoMerchantSendForgotPasswordOtpResponse == null || dtoMerchantSendForgotPasswordOtpResponse.getData() == null) {
                Tracer.showSnack(view!!, R.string.no_data_fetch_from_server)
                return
            }
            Tracer.showSnack(view!!, dtoMerchantSendForgotPasswordOtpResponse.getMessage())
            if (activity is OnBaseActivityListener) {
                val bundle = Bundle()
                val merchantId = mEditTextMerchantId !!.text.toString()
                bundle.putString(FragmentChangePasswordByOtp.EXTRA_LOGIN_ID, merchantId)
                val fragment = FragmentProvider.getFragment(FragmentTag.CHANGE_PASSWORD_BY_OTP)
                (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment!!, bundle, true, FragmentTag.CHANGE_PASSWORD_BY_OTP)
            }
        }

        override fun onError(errorMessage : String, errorCode : Int) {
            Tracer.debug(TAG, "onError : ")
            Utils.dismissLoadingDialog()
            if (view == null) {
                return
            }
            Tracer.showSnack(view!!, errorMessage)
        }
    }

    /**
     * Method to check weather the OTP generation detail insert by merchant is valid or not
     *
     * @return
     */
    private // Validate Mobile Number
    // Validate Email
    val isGenerateOtpDetailValid : Boolean
        get() {
            Tracer.debug(TAG, "isGenerateOtpDetailValid: ")
            if (view == null) {
                return false
            }

            val mobileNumber = mEditTextMobileNumber !!.text.toString()
            val merchantId = mEditTextMerchantId !!.text.toString()
            if (Utils.isStringEmpty(mobileNumber)) {
                showTextInputError(mTextInputLayoutMobileNumber, getString(R.string.field_should_not_be_empty_caps))
                return false
            }
            if (Utils.isStringEmpty(merchantId)) {
                showTextInputError(mTextInputLayoutMerchantId, getString(R.string.field_should_not_be_empty_caps))
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
        view !!.findViewById<View>(R.id.fragment_forgot_password_textView_submit).setOnClickListener(this)

        mTextInputLayoutMobileNumber = view !!.findViewById<View>(R.id.fragment_forgot_password_textInputLayout_mobile_number) as TextInputLayout
        mEditTextMobileNumber = view !!.findViewById<View>(R.id.fragment_forgot_password_editText_mobile_number) as EditText
        mTextInputLayoutMerchantId = view !!.findViewById<View>(R.id.fragment_forgot_password_textInputLayout_merchant_id) as TextInputLayout
        mEditTextMerchantId = view !!.findViewById<View>(R.id.fragment_forgot_password_editText_merchant_id) as EditText

        // ADD TEXT WATCHER
        mEditTextMobileNumber !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutMobileNumber!!))
        mEditTextMerchantId !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutMerchantId!!))
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
        val mobileNumber = mEditTextMobileNumber !!.text.toString()
        val merchantId = mEditTextMerchantId !!.text.toString()
        Tracer.debug(TAG, "startSendOtpProcess : ")
        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(activity, getString(R.string.endpoint_send_forgot_password_otp), date)
        val publicKey = getString(R.string.public_key)
        val dtoMerchantSendForgotPasswordOtpRequest = DTOMerchantSendForgotPasswordOtpRequest(token!!, timeStamp, publicKey, merchantId)
        Utils.showLoadingDialog(activity)
        mAgentNetworkTaskProvider !!.merchantSendForgotPasswordOtpTask(activity, dtoMerchantSendForgotPasswordOtpRequest, mMerchantSendForgotPasswordOtpResponseNetworkCallBack)
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
