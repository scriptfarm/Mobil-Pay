package com.mkrworld.mobilpay.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.*
import android.widget.EditText
import android.widget.RadioButton
import com.mkrworld.androidlib.callback.OnBaseActivityListener
import com.mkrworld.androidlib.callback.OnBaseFragmentListener
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.utils.MKRDialogUtil
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.agent.agentmerchantlist.DTOAgentMerchantListRequest
import com.mkrworld.mobilpay.dto.agent.agentmerchantlist.DTOAgentMerchantListResponse
import com.mkrworld.mobilpay.dto.comms.login.DTOLoginRequest
import com.mkrworld.mobilpay.dto.comms.login.DTOLoginResponse
import com.mkrworld.mobilpay.fingerprintauth.FingerPrintAuthHelper
import com.mkrworld.mobilpay.fingerprintauth.OnFingerPrintAuthCallback
import com.mkrworld.mobilpay.provider.fragment.FragmentProvider
import com.mkrworld.mobilpay.provider.fragment.FragmentTag
import com.mkrworld.mobilpay.provider.network.AppNetworkTaskProvider
import com.mkrworld.mobilpay.ui.custom.OnTextInputLayoutTextChange
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
    private var mEditTextId : EditText? = null
    private var mTextInputLayoutPassword : TextInputLayout? = null
    private var mEditTextPassword : EditText? = null
    private var mRadioButtonMerchant : RadioButton? = null
    private var mRadioButtonAgent : RadioButton? = null
    private var mIsMerchant : Boolean = true
    private var mLoginMerchantId : String = ""

    private var mIsFingerPrintDeviceWorkingFine : Boolean = false
    private var mFingerPrintAuthHelper : FingerPrintAuthHelper? = null
    private var mAppNetworkTaskProvider : AppNetworkTaskProvider? = null
    private val mLoginResponseNetworkCallBack = object : NetworkCallBack<DTOLoginResponse> {
        override fun onSuccess(dto : DTOLoginResponse) {
            Tracer.debug(TAG, "onSuccess : " + dto !!)
            Utils.dismissLoadingDialog()
            if (dto == null || dto.getData() == null) {
                Tracer.showSnack(view !!, R.string.no_data_fetch_from_server)
                return
            }
            Tracer.showSnack(view !!, dto.getMessage())
            PreferenceData.setLoginTime(activity, System.currentTimeMillis())
            // TEMP SAVE LOGIN DATA
            if (mIsMerchant) {
                PreferenceData.setUserType(activity, Constants.USER_TYPE_MERCHANT)
                PreferenceData.setLoginMerchantId(activity, mEditTextId !!.text.toString())
            } else {
                PreferenceData.setUserType(activity, Constants.USER_TYPE_AGENT)
                PreferenceData.setLoginMerchantId(activity, mLoginMerchantId)
                PreferenceData.setLoginAgentId(activity, mEditTextId !!.text.toString())
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (! PreferenceData.isHaveFingerPrintConsent(activity) && mIsFingerPrintDeviceWorkingFine) {
                    showEnableFingerPrintDialog(PreferenceData.getUserType(activity), PreferenceData.getLoginMerchantId(activity), PreferenceData.getLoginAgentId(activity), mEditTextPassword !!.text.toString().trim())
                    return
                } else if (PreferenceData.isHaveFingerPrintConsent(activity) && PreferenceData.getThumbLoginUserType(activity).equals(PreferenceData.getUserType(activity)) && PreferenceData.getThumbLoginMerchantId(activity).equals(PreferenceData.getLoginMerchantId(activity)) && PreferenceData.getThumbLoginAgentId(activity).equals(PreferenceData.getLoginAgentId(activity)) && ! PreferenceData.getThumbLoginPassword(activity).equals(mEditTextPassword !!.text.toString().trim())) {
                    showUpdateFingerLoginDetailDialog(mEditTextPassword !!.text.toString().trim())
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

    private val mAgentMerchantListNetworkCallBack = object : NetworkCallBack<DTOAgentMerchantListResponse> {
        override fun onSuccess(dto : DTOAgentMerchantListResponse) {
            Tracer.debug(TAG, "onSuccess : " + dto !!)
            Utils.dismissLoadingDialog()
            if (dto == null || dto.getData().size <= 0) {
                Tracer.showSnack(view !!, R.string.no_data_fetch_from_server)
                return
            }
            if (dto.getData().size == 1) {
                mLoginMerchantId = dto.getData()[0].merchantId !!
                startSignInProcess()
            } else {
                showSelectionDialog(dto.getData())
            }
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
            val id = mEditTextId !!.text.toString()
            val password = mEditTextPassword !!.text.toString()
            if (Utils.isStringEmpty(id)) {
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
        setHasOptionsMenu(true)
        if ((PreferenceData.getLoginTime(activity) + Constants.AUTO_LOGOUT_INTERVAL) > System.currentTimeMillis()) {
            goToSuccessScreen()
            return
        }
        PreferenceData.setUserType(activity, "")
        PreferenceData.setLoginMerchantId(activity, "")
        PreferenceData.setLoginAgentId(activity, "")
        mAppNetworkTaskProvider = AppNetworkTaskProvider()
        mAppNetworkTaskProvider?.attachProvider()
        setTitle()
        init()
    }

    override fun onCreateOptionsMenu(menu : Menu?, inflater : MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.menu_nothing, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        mFingerPrintAuthHelper?.stopAuth()
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
            R.id.fragment_login_textView_sign_in -> loginButtonClicked()
            R.id.fragment_login_textView_forgot_password -> if (activity is OnBaseActivityListener) {
                val fragment = FragmentProvider.getFragment(FragmentTag.FORGOT_PASSWORD)
                (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment !!, null, true, FragmentTag.FORGOT_PASSWORD)
            }
            R.id.fragment_login_radio_agent -> {
                mRadioButtonAgent !!.isChecked = true
                mRadioButtonMerchant !!.isChecked = false
                mIsMerchant = false
            }
            R.id.fragment_login_radio_merchant -> {
                mRadioButtonAgent !!.isChecked = false
                mRadioButtonMerchant !!.isChecked = true
                mIsMerchant = true
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
        if (! PreferenceData.isHaveFingerPrintConsent(activity)) {
            return
        }
        mEditTextPassword !!.setText(PreferenceData.getThumbLoginPassword(activity))
        mIsMerchant = PreferenceData.getThumbLoginUserType(activity).equals(Constants.USER_TYPE_MERCHANT)
        if (mIsMerchant) {
            mRadioButtonMerchant !!.isChecked = true
            mRadioButtonAgent !!.isChecked = false
            mEditTextId !!.setText(PreferenceData.getThumbLoginMerchantId(activity))
        } else {
            mRadioButtonMerchant !!.isChecked = false
            mRadioButtonAgent !!.isChecked = true
            mLoginMerchantId = PreferenceData.getThumbLoginMerchantId(activity)
            mEditTextId !!.setText(PreferenceData.getThumbLoginAgentId(activity))
        }
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
        mTextInputLayoutId = view !!.findViewById<View>(R.id.fragment_login_textInputLayout_merchant_id_mobile_number) as TextInputLayout
        mEditTextId = view !!.findViewById<View>(R.id.fragment_login_editText_merchant_id_mobile_number) as EditText
        mTextInputLayoutPassword = view !!.findViewById<View>(R.id.fragment_login_textInputLayout_password) as TextInputLayout
        mEditTextPassword = view !!.findViewById<View>(R.id.fragment_login_editText_password) as EditText
        mRadioButtonAgent = view !!.findViewById<View>(R.id.fragment_login_radio_agent) as RadioButton
        mRadioButtonMerchant = view !!.findViewById<View>(R.id.fragment_login_radio_merchant) as RadioButton
        // ADD CLICK LISTENER
        mRadioButtonAgent !!.setOnClickListener(this)
        mRadioButtonMerchant !!.setOnClickListener(this)
        view !!.findViewById<View>(R.id.fragment_login_textView_sign_in).setOnClickListener(this)
        view !!.findViewById<View>(R.id.fragment_login_textView_forgot_password).setOnClickListener(this)
        // ADD TEXT CHANGE LISTENER
        mEditTextId !!.addTextChangedListener(OnTextInputLayoutTextChange(mTextInputLayoutId !!))
        mEditTextPassword !!.addTextChangedListener(OnTextInputLayoutTextChange(mTextInputLayoutPassword !!))
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
     * Method called when user clicked the login button
     */
    private fun loginButtonClicked() {
        Utils.hideKeyboard(activity, view)
        if (! isLoginDetailValid) {
            return
        }
        if (mIsMerchant) {
            startSignInProcess()
        } else {
            fetchMerchantList()
        }
    }

    /**
     * Method to initiate the User Sign In Process
     */
    private fun startSignInProcess() {
        Tracer.debug(TAG, "startSignInProcess: ")
        Utils.hideKeyboard(activity, view)
        var userType = ""
        var agentId = ""
        var merchantId = ""
        if (mIsMerchant) {
            merchantId = mEditTextId !!.text.toString()
            userType = Constants.USER_TYPE_MERCHANT
        } else {
            merchantId = mLoginMerchantId
            agentId = mEditTextId !!.text.toString()
            userType = Constants.USER_TYPE_AGENT
        }
        val password = mEditTextPassword !!.text.toString()
        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(activity, getString(R.string.endpoint_login), date)
        val publicKey = getString(R.string.public_key)
        val pushId = "123"
        val gcmId = "123"
        val dtoAgentLoginRequest = DTOLoginRequest(token !!, timeStamp, publicKey, userType, merchantId, agentId, password, pushId, gcmId)
        Utils.showLoadingDialog(activity)
        mAppNetworkTaskProvider !!.loginTask(activity, dtoAgentLoginRequest, mLoginResponseNetworkCallBack)
    }

    /**
     * Method to fetch the Merchant list
     */
    private fun fetchMerchantList() {
        Tracer.debug(TAG, "startSignInProcess: ")
        Utils.hideKeyboard(activity, view)
        if (! isLoginDetailValid) {
            return
        }
        var userType = Constants.USER_TYPE_AGENT
        var agentId = mEditTextId !!.text.toString()
        var merchantId = ""
        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(activity, getString(R.string.endpoint_agent_merchant_list), date)
        val publicKey = getString(R.string.public_key)
        val dtoAgentMerchantListRequest = DTOAgentMerchantListRequest(token !!, timeStamp, publicKey, userType, merchantId, agentId)
        Utils.showLoadingDialog(activity)
        mAppNetworkTaskProvider?.agentMerchantsList(activity, dtoAgentMerchantListRequest, mAgentMerchantListNetworkCallBack)
    }

    /**
     * Method to show the Selction dialog
     */
    private fun showSelectionDialog(dataList : ArrayList<DTOAgentMerchantListResponse.Data>) {
        Tracer.debug(TAG, "showSelectionDialog : ")
        var mBuilder : AlertDialog.Builder? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBuilder = AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert)
        } else {
            mBuilder = AlertDialog.Builder(context)
        }
        mBuilder.setTitle(getString(R.string.choose_merchant))
        var nameList : Array<String?> = arrayOfNulls<String>(dataList.size)
        for (index : Int in 0 .. dataList.size - 1) {
            nameList.set(index, dataList[index].merchantName)
        }
        mBuilder.setSingleChoiceItems(nameList, 1, object : DialogInterface.OnClickListener {
            override fun onClick(dialogInterface : DialogInterface?, which : Int) {
                dialogInterface !!.dismiss()
                mLoginMerchantId = dataList[which].merchantId !!
                startSignInProcess()
            }
        })
        val mDialog = mBuilder.create()
        mDialog.show()
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
     * @param userType
     * @param merchantId
     * @param agentId
     * @param password
     */
    private fun showEnableFingerPrintDialog(userType : String, merchantId : String, agentId : String, password : String) {
        Tracer.debug(TAG, "showEnableFingerPrintDialog : ")
        val context = activity
        val iconId = R.drawable.ic_fingerprint_normal
        val title = getString(R.string.enable_finger_print)
        val message = getString(R.string.are_you_sure_enable_finger_print_login)
        val onOkClickListener = DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.dismiss()
            PreferenceData.setHaveFingerPrintConsent(activity)
            PreferenceData.setThumbLoginUserType(context, userType)
            PreferenceData.setThumbLoginMerchantId(context, merchantId)
            PreferenceData.setThumbLoginAgentId(context, agentId)
            PreferenceData.setThumbLoginPassword(context, password)
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
            PreferenceData.setThumbLoginPassword(activity, loginPassword)
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
