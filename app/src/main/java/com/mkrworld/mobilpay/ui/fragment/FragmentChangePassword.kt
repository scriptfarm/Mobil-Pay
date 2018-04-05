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
import com.mkrworld.mobilpay.dto.merchantchangepassword.DTOMerchantChangePasswordRequest
import com.mkrworld.mobilpay.dto.merchantchangepassword.DTOMerchantChangePasswordResponse
import com.mkrworld.mobilpay.provider.fragment.FragmentProvider
import com.mkrworld.mobilpay.provider.fragment.FragmentTag
import com.mkrworld.mobilpay.provider.network.MerchantNetworkTaskProvider
import com.mkrworld.mobilpay.ui.custom.OnTextInputLayoutTextChangeListener
import com.mkrworld.mobilpay.utils.PreferenceData
import com.mkrworld.mobilpay.utils.Utils

import java.util.Date

/**
 * Created by mkr on 13/3/18.
 */

class FragmentChangePassword : Fragment(), OnBaseFragmentListener, View.OnClickListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentChangePassword"
    }

    private var mTextInputLayoutOldPassword : TextInputLayout? = null
    private var mTextInputLayoutNewPassword : TextInputLayout? = null
    private var mTextInputLayoutConfirmPassword : TextInputLayout? = null
    private var mEditTextOldPassword : EditText? = null
    private var mEditTextNewPassword : EditText? = null
    private var mEditTextConfirmPassword : EditText? = null
    private var mMerchantNetworkTaskProvider : MerchantNetworkTaskProvider? = null
    private val mMerchantChangePasswordResponseNetworkCallBack = object : NetworkCallBack<DTOMerchantChangePasswordResponse> {
        override fun onSuccess(dtoMerchantChangePasswordResponse : DTOMerchantChangePasswordResponse?) {
            Tracer.debug(TAG, "onSuccess : ")
            Utils.dismissLoadingDialog()
            if (view == null) {
                return
            }
            if (dtoMerchantChangePasswordResponse == null || dtoMerchantChangePasswordResponse.getData() == null) {
                Tracer.showSnack(view, R.string.no_data_fetch_from_server)
                return
            }
            PreferenceData.setMerchantLoginPassword(activity, mEditTextConfirmPassword !!.text.toString().trim { it <= ' ' })
            Tracer.showSnack(view, dtoMerchantChangePasswordResponse.getMessage())
            if (activity is OnBaseActivityListener) {
                val fragment = FragmentProvider.getFragment(FragmentTag.MERCHANT_HOME)
                (activity as OnBaseActivityListener).onBaseActivityReplaceFragment(fragment, null, FragmentTag.MERCHANT_HOME)
            }
        }

        override fun onError(errorMessage : String, errorCode : Int) {
            Tracer.debug(TAG, "onError : ")
            Utils.dismissLoadingDialog()
            if (view == null) {
                return
            }
            Tracer.showSnack(view, errorMessage)
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

            val oldPassword = mEditTextOldPassword !!.text.toString()
            val newPassword = mEditTextNewPassword !!.text.toString()
            val confirmPassword = mEditTextConfirmPassword !!.text.toString()
            if (Utils.isStringEmpty(oldPassword)) {
                showTextInputError(mTextInputLayoutOldPassword, getString(R.string.field_should_not_be_empty_caps))
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
        return inflater !!.inflate(R.layout.fragment_change_password, container, false)
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
            R.id.fragment_change_password_textView_forgot_password -> if (activity is OnBaseActivityListener) {
                val fragment = FragmentProvider.getFragment(FragmentTag.FORGOT_PASSWORD)
                (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment, null, true, FragmentTag.FORGOT_PASSWORD)
            }
            R.id.fragment_change_password_textView_submit -> startSendOtpProcess()
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
        view !!.findViewById<View>(R.id.fragment_change_password_textView_forgot_password).setOnClickListener(this)
        view !!.findViewById<View>(R.id.fragment_change_password_textView_submit).setOnClickListener(this)

        mTextInputLayoutOldPassword = view !!.findViewById<View>(R.id.fragment_change_password_textInputLayout_old_password) as TextInputLayout
        mEditTextOldPassword = view !!.findViewById<View>(R.id.fragment_change_password_editText_old_password) as EditText
        mTextInputLayoutNewPassword = view !!.findViewById<View>(R.id.fragment_change_password_textInputLayout_new_password) as TextInputLayout
        mEditTextNewPassword = view !!.findViewById<View>(R.id.fragment_change_password_editText_new_password) as EditText
        mTextInputLayoutConfirmPassword = view !!.findViewById<View>(R.id.fragment_change_password_textInputLayout_confirm_password) as TextInputLayout
        mEditTextConfirmPassword = view !!.findViewById<View>(R.id.fragment_change_password_editText_confirm_password) as EditText

        // ADD TEXT CHANGE LISTENER
        mEditTextOldPassword !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutOldPassword!!))
        mEditTextNewPassword !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutNewPassword!!))
        mEditTextConfirmPassword !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutConfirmPassword!!))
    }

    /**
     * Method to initiate the Send OTP Process
     */
    private fun startSendOtpProcess() {
        Tracer.debug(TAG, "startSendOtpProcess: ")
        Utils.hideKeyboard(activity, view)
        if (! isUserDetailValid) {
            return
        }

        val oldPassword = mEditTextOldPassword !!.text.toString().trim { it <= ' ' }
        val confirmPassword = mEditTextConfirmPassword !!.text.toString().trim { it <= ' ' }

        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(activity, getString(R.string.endpoint_change_password), date)
        val publicKey = getString(R.string.public_key)
        val dtoMerchantChangePasswordRequest = DTOMerchantChangePasswordRequest(token!!, timeStamp, publicKey, PreferenceData.getMerchantNupayId(activity), oldPassword, confirmPassword)
        Utils.showLoadingDialog(activity)
        mMerchantNetworkTaskProvider !!.merchantChangePasswordTask(activity, dtoMerchantChangePasswordRequest, mMerchantChangePasswordResponseNetworkCallBack)
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