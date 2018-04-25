package com.mkrworld.mobilpay.ui.fragment

import android.content.DialogInterface
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
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
import com.mkrworld.androidlib.utils.MKRDialogUtil
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.login.DTOLoginRequest
import com.mkrworld.mobilpay.dto.login.DTOLoginResponse
import com.mkrworld.mobilpay.fingerprintauth.FingerPrintAuthHelper
import com.mkrworld.mobilpay.fingerprintauth.OnFingerPrintAuthCallback
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

class FragmentLogin : Fragment(), OnBaseFragmentListener, View.OnClickListener, OnFingerPrintAuthCallback {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentLogin"
    }

    private var mTextInputLayoutId : TextInputLayout? = null
    private var mTextInputLayoutPassword : TextInputLayout? = null
    private var mEditTextId : EditText? = null
    private var mEditTextPassword : EditText? = null
    private var mIsFingerPrintDeviceWorkingFine : Boolean = false
    private var mFingerPrintAuthHelper : FingerPrintAuthHelper? = null
    private var mAgentNetworkTaskProvider : AgentNetworkTaskProvider? = null
    private val mLoginResponseNetworkCallBack = object : NetworkCallBack<DTOLoginResponse> {
        override fun onSuccess(dtoLoginResponse : DTOLoginResponse) {
            Tracer.debug(TAG, "onSuccess : " + dtoLoginResponse !!)
            Utils.dismissLoadingDialog()
            if (dtoLoginResponse == null || dtoLoginResponse.getData() == null) {
                Tracer.showSnack(view !!, R.string.no_data_fetch_from_server)
                return
            }
            Tracer.showSnack(view !!, dtoLoginResponse.getMessage())
            val userId = mEditTextId !!.text.toString()
            val password = mEditTextPassword !!.text.toString()
            PreferenceData.setUserType(activity, dtoLoginResponse.getData() !!.userType !!)
            PreferenceData.setLoginId(activity, userId)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (! PreferenceData.isHaveFingerPrintConsent(activity) && mIsFingerPrintDeviceWorkingFine) {
                    showEnableFingerPrintDialog(userId, password)
                    return
                } else if (PreferenceData.isHaveFingerPrintConsent(activity) && PreferenceData.getLoginId(activity).equals(userId.trim(), true) && (! PreferenceData.getLoginPassword(activity).equals(password.trim(), true))) {
                    showUpdateFingerLoginDetailDialog(password)
                    return
                }
            }
            goToSuccessScreen()
        }

        override fun onError(errorMessage : String, errorCode : Int) {
            Tracer.debug(TAG, "onError : $errorMessage")
            Utils.dismissLoadingDialog()
            Tracer.showSnack(view !!, errorMessage)
        }
    }

    /**
     * Method to check weather the Login detail insert by agent is valid or not
     *
     * @return
     */
    private val isLoginDetailValid : Boolean
        get() {
            Tracer.debug(TAG, "isLoginDetailValid: ")
            if (view == null) {
                return false
            }
            val agentId = mEditTextId !!.text.toString()
            val password = mEditTextPassword !!.text.toString()
            if (Utils.isStringEmpty(agentId)) {
                showTextInputError(mTextInputLayoutId, getString(R.string.field_should_not_be_empty_caps))
                return false
            }
            if (Utils.isStringEmpty(password)) {
                showTextInputError(mTextInputLayoutPassword, getString(R.string.field_should_not_be_empty_caps))
                return false
            }
            return true
        }

    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        Tracer.debug(TAG, "onCreateView: ")
        return inflater !!.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view : View?, savedInstanceState : Bundle?) {
        Tracer.debug(TAG, "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
        mAgentNetworkTaskProvider = AgentNetworkTaskProvider()
        mAgentNetworkTaskProvider !!.attachProvider()
        setTitle()
        init()
    }

    override fun onDestroyView() {
        mFingerPrintAuthHelper !!.stopAuth()
        mAgentNetworkTaskProvider !!.detachProvider()
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
            R.id.fragment_login_textView_sign_in -> startSignInProcess() //FingerCapture.getInstance().capture(activity, null, "P", true)
            R.id.fragment_login_textView_forgot_password -> if (activity is OnBaseActivityListener) {
                val fragment = FragmentProvider.getFragment(FragmentTag.FORGOT_PASSWORD)
                (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment !!, null, true, FragmentTag.FORGOT_PASSWORD)
            }
        }
    }

    override fun onFingerPrintAuthNoFingerPrintHardwareFound() {
        Tracer.debug(TAG, "onFingerPrintAuthNoFingerPrintHardwareFound : ")
        mIsFingerPrintDeviceWorkingFine = false
        if (view == null) {
            return
        }
        Tracer.showSnack(view !!, R.string.no_finger_print_device_not_detected)
    }

    override fun onFingerPrintAuthNoFingerPrintRegistered() {
        Tracer.debug(TAG, "onFingerPrintAuthNoFingerPrintRegistered : ")
        mIsFingerPrintDeviceWorkingFine = false
        if (view == null) {
            return
        }
        Tracer.showSnack(view !!, R.string.no_finger_print_register)
    }

    override fun onFingerPrintAuthBelowMarshmallow() {
        Tracer.debug(TAG, "onFingerPrintAuthBelowMarshmallow : ")
    }

    override fun onFingerPrintAuthSuccess(cryptoObject : FingerprintManager.CryptoObject) {
        Tracer.debug(TAG, "onFingerPrintAuthSuccess : ")
        val agentLoginPassword = PreferenceData.getLoginPassword(activity)
        val agentId = PreferenceData.getLoginId(activity)
        if (agentId.trim().isEmpty() || agentLoginPassword.trim().isEmpty()) {
            if (view == null) {
                return
            }
            Tracer.showSnack(view !!, R.string.plz_login_manually)
            return
        }
        mEditTextPassword !!.setText(agentLoginPassword)
        mEditTextId !!.setText(agentId)
        startSignInProcess()
    }

    override fun onFingerPrintAuthFailed(errorCode : Int, errorMessage : String) {
        Tracer.debug(TAG, "onFingerPrintAuthFailed : ")
        if (view == null) {
            return
        }
        Tracer.showSnack(view !!, R.string.error_scanning_finger_not_recognized)
    }

    /**
     * Method to set the Activity Title
     */
    private fun setTitle() {
        Tracer.debug(TAG, "setTitle: ")
        if (activity is OnBaseActivityListener) {
            (activity as OnBaseActivityListener).onBaseActivitySetScreenTitle(getString(R.string.screen_title_login))
        }
    }

    /**
     * Method to init the Fragment
     */
    private fun init() {
        Tracer.debug(TAG, "init: ")
        if (view == null) {
            return
        }
        mIsFingerPrintDeviceWorkingFine = true
        mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(activity, this)
        mFingerPrintAuthHelper !!.startAuth()
        view !!.findViewById<View>(R.id.fragment_login_textView_sign_in).setOnClickListener(this)
        view !!.findViewById<View>(R.id.fragment_login_textView_forgot_password).setOnClickListener(this)
        mTextInputLayoutId = view !!.findViewById<View>(R.id.fragment_login_textInputLayout_merchant_id_mobile_number) as TextInputLayout
        mEditTextId = view !!.findViewById<View>(R.id.fragment_login_editText_merchant_id_mobile_number) as EditText
        mTextInputLayoutPassword = view !!.findViewById<View>(R.id.fragment_login_textInputLayout_password) as TextInputLayout
        mEditTextPassword = view !!.findViewById<View>(R.id.fragment_login_editText_password) as EditText

        // ADD TEXT CHANGE LISTENER
        mEditTextId !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutId !!))
        mEditTextPassword !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutPassword !!))
    }

    /**
     * Method to initiate the User Sign In Process
     */
    private fun startSignInProcess() {
        Tracer.debug(TAG, "startSignInProcess: ")
        Utils.hideKeyboard(activity, view)
        if (! isLoginDetailValid) {
            return
        }
        val userId = mEditTextId !!.text.toString()
        val password = mEditTextPassword !!.text.toString()
        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(activity, getString(R.string.endpoint_login), date)
        val publicKey = getString(R.string.public_key)
        val pushId = "123"
        val gcmId = "123"
        val dtoAgentLoginRequest = DTOLoginRequest(token !!, timeStamp, publicKey, userId, password, pushId, gcmId)
        Utils.showLoadingDialog(activity)
        mAgentNetworkTaskProvider !!.loginTask(activity, dtoAgentLoginRequest, mLoginResponseNetworkCallBack)
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

    /**
     * Method to show dialog to enable the finger print
     */
    private fun showEnableFingerPrintDialog(loginId : String, loginPassword : String) {
        Tracer.debug(TAG, "showEnableFingerPrintDialog : ")
        val context = activity
        val iconId = R.drawable.ic_fingerprint_normal
        val title = getString(R.string.enable_finger_print)
        val message = getString(R.string.are_you_sure_enable_finger_print_login)
        val onOkClickListener = DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.dismiss()
            PreferenceData.setHaveFingerPrintConsent(activity)
            PreferenceData.setLoginId(activity, loginId)
            PreferenceData.setLoginPassword(activity, loginPassword)
            goToSuccessScreen()
        }
        val onCancelClickListener = DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.dismiss()
            goToSuccessScreen()
        }
        val cancellable = false
        MKRDialogUtil.showOKCancelDialog(context, iconId, title, message, onOkClickListener, onCancelClickListener, cancellable)
    }

    /**
     * Method to show dialog to update fingerprint login detail
     */
    private fun showUpdateFingerLoginDetailDialog(loginPassword : String) {
        Tracer.debug(TAG, "showUpdateFingerLoginDetailDialog : ")
        val context = activity
        val iconId = R.drawable.ic_fingerprint_normal
        val title = getString(R.string.update_finger_print)
        val message = getString(R.string.are_you_sure_to_update_the_login_detail)
        val onOkClickListener = DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.dismiss()
            PreferenceData.setLoginPassword(activity, loginPassword)
            goToSuccessScreen()
        }
        val onCancelClickListener = DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.dismiss()
            goToSuccessScreen()
        }
        val cancellable = false
        MKRDialogUtil.showOKCancelDialog(context, iconId, title, message, onOkClickListener, onCancelClickListener, cancellable)
    }

    /**
     * Method to move user from this screen to the success screen
     */
    private fun goToSuccessScreen() {
        Tracer.debug(TAG, "goToSuccessScreen : ")
        if (activity is OnBaseActivityListener) {
            if (PreferenceData.getUserType(activity).equals(Constants.USER_TYPE_MERCHANT)) {
                val fragment = FragmentProvider.getFragment(FragmentTag.MERCHANT_HOME)
                (activity as OnBaseActivityListener).onBaseActivityReplaceFragment(fragment !!, null, FragmentTag.MERCHANT_HOME)
            } else {
                val fragment = FragmentProvider.getFragment(FragmentTag.AGENT_HOME)
                (activity as OnBaseActivityListener).onBaseActivityReplaceFragment(fragment !!, null, FragmentTag.AGENT_HOME)
            }
        }
    }
}
