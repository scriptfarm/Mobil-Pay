package com.mkrworld.mobilpay.ui.fragment

import android.content.ContentValues.TAG
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
import com.mkrworld.mobilpay.dto.merchantforgotpassword.DTOMerchantForgotPasswordRequest
import com.mkrworld.mobilpay.dto.merchantforgotpassword.DTOMerchantForgotPasswordResponse
import com.mkrworld.mobilpay.provider.fragment.FragmentProvider
import com.mkrworld.mobilpay.provider.fragment.FragmentTag
import com.mkrworld.mobilpay.provider.network.MerchantNetworkTaskProvider
import com.mkrworld.mobilpay.ui.custom.OnTextInputLayoutTextChangeListener
import com.mkrworld.mobilpay.utils.PreferenceData
import com.mkrworld.mobilpay.utils.Utils

import java.util.Date

/**
 * Created by mkr on 15/3/18.
 */

class FragmentChangePasswordByOtp : Fragment(), OnBaseFragmentListener, View.OnClickListener {

    companion object {
        val EXTRA_LOGIN_ID = "EXTRA_LOGIN_ID"
        private val TAG = BuildConfig.BASE_TAG + ".FragmentChangePasswordByOtp"
    }

    private var mTextInputLayoutOtp : TextInputLayout? = null
    private var mTextInputLayoutNewPassword : TextInputLayout? = null
    private var mTextInputLayoutConfirmPassword : TextInputLayout? = null
    private var mEditTextOtp : EditText? = null
    private var mEditTextNewPassword : EditText? = null
    private var mEditTextConfirmPassword : EditText? = null
    private var mMerchantNetworkTaskProvider : MerchantNetworkTaskProvider? = null
    private val mMerchantForgotPasswordResponseNetworkCallBack = object : NetworkCallBack<DTOMerchantForgotPasswordResponse> {
        override fun onSuccess(dtoMerchantForgotPasswordResponse : DTOMerchantForgotPasswordResponse) {
            Tracer.debug(TAG, "onSuccess : ")
            Utils.dismissLoadingDialog()
            if (view == null) {
                return
            }
            if (dtoMerchantForgotPasswordResponse == null || dtoMerchantForgotPasswordResponse.getData() == null) {
                Tracer.showSnack(view!!, R.string.no_data_fetch_from_server)
                return
            }
            val prefMerchantId = PreferenceData.getMerchantLoginId(context).trim { it <= ' ' }
            if (arguments != null && ! prefMerchantId.isEmpty() && arguments.getString(EXTRA_LOGIN_ID, "").trim { it <= ' ' } == prefMerchantId) {
                PreferenceData.setMerchantLoginPassword(activity, mEditTextConfirmPassword !!.text.toString().trim { it <= ' ' })
            }
            Tracer.showSnack(view!!, dtoMerchantForgotPasswordResponse.getMessage())
            if (activity is OnBaseActivityListener) {
                val fragment = FragmentProvider.getFragment(FragmentTag.MERCHANT_LOGIN)
                (activity as OnBaseActivityListener).onBaseActivityReplaceFragment(fragment!!, null, FragmentTag.MERCHANT_LOGIN)
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
     * Method to check weather the user detail insert by merchant is valid or not
     *
     * @return
     */
    private // Validate old password
    // Validate New password
    // Validate confirm password
    // Validate confirm password
    val isUserDetailValid : Boolean
        get() {
            Tracer.debug(TAG, "isUserDetailValid: ")
            if (view == null) {
                return false
            }

            val otp = mEditTextOtp !!.text.toString()
            val newPassword = mEditTextNewPassword !!.text.toString()
            val confirmPassword = mEditTextConfirmPassword !!.text.toString()
            if (Utils.isStringEmpty(otp)) {
                showTextInputError(mTextInputLayoutOtp, getString(R.string.field_should_not_be_empty_caps))
                return false
            }
            if (Utils.isStringEmpty(newPassword)) {
                showTextInputError(mTextInputLayoutNewPassword, getString(R.string.field_should_not_be_empty_caps))
                return false
            }
            if (Utils.isStringEmpty(confirmPassword)) {
                showTextInputError(mTextInputLayoutConfirmPassword, getString(R.string.field_should_not_be_empty_caps))
                return false
            }
            if (confirmPassword.trim { it <= ' ' } != newPassword.trim { it <= ' ' }) {
                showTextInputError(mTextInputLayoutConfirmPassword, getString(R.string.password_does_not_match_with_new_password))
                return false
            }

            return true
        }

    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        Tracer.debug(TAG, "onCreateView: ")
        return inflater !!.inflate(R.layout.fragment_change_password_otp, container, false)
    }

    override fun onViewCreated(view : View?, savedInstanceState : Bundle?) {
        Tracer.debug(TAG, "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
        setTitle()
        init()
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
            R.id.fragment_change_password_otp_textView_submit -> startSetNewPasswordProcess()
        }
    }

    /**
     * Method to set the Activity Title
     */
    private fun setTitle() {
        Tracer.debug(TAG, "setTitle: ")
        if (activity is OnBaseActivityListener) {
            (activity as OnBaseActivityListener).onBaseActivitySetScreenTitle(getString(R.string.screen_title_change_password))
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
        view !!.findViewById<View>(R.id.fragment_change_password_otp_textView_submit).setOnClickListener(this)

        mTextInputLayoutOtp = view !!.findViewById<View>(R.id.fragment_change_password_otp_textInputLayout_otp) as TextInputLayout
        mEditTextOtp = view !!.findViewById<View>(R.id.fragment_change_password_otp_editText_otp) as EditText
        mTextInputLayoutNewPassword = view !!.findViewById<View>(R.id.fragment_change_password_otp_textInputLayout_new_password) as TextInputLayout
        mEditTextNewPassword = view !!.findViewById<View>(R.id.fragment_change_password_otp_editText_new_password) as EditText
        mTextInputLayoutConfirmPassword = view !!.findViewById<View>(R.id.fragment_change_password_otp_textInputLayout_confirm_password) as TextInputLayout
        mEditTextConfirmPassword = view !!.findViewById<View>(R.id.fragment_change_password_otp_editText_confirm_password) as EditText

        // ADD TEXT CHANGE LISTENER
        mEditTextOtp !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutOtp!!))
        mEditTextNewPassword !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutNewPassword!!))
        mEditTextConfirmPassword !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutConfirmPassword!!))
    }

    /**
     * Method to initiate the Set New Password Process
     */
    private fun startSetNewPasswordProcess() {
        Tracer.debug(TAG, "startSetNewPasswordProcess: ")
        Utils.hideKeyboard(activity, view)
        if (! isUserDetailValid) {
            return
        }
        val otp = mEditTextOtp !!.text.toString().trim { it <= ' ' }
        val confirmPassword = mEditTextConfirmPassword !!.text.toString().trim { it <= ' ' }
        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(activity, getString(R.string.endpoint_forgot_password), date)
        val publicKey = getString(R.string.public_key)
        val dtoMerchantForgotPasswordRequest = DTOMerchantForgotPasswordRequest(token!!, timeStamp, publicKey, PreferenceData.getMerchantNupayId(activity), confirmPassword, otp, "123")
        Utils.showLoadingDialog(activity)
        mMerchantNetworkTaskProvider !!.merchantForgotPasswordTask(activity, dtoMerchantForgotPasswordRequest, mMerchantForgotPasswordResponseNetworkCallBack)
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
