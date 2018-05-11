package com.mkrworld.mobilpay.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
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
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.network.fetchbill.DTOFetchBillRequest
import com.mkrworld.mobilpay.dto.network.fetchbill.DTOFetchBillResponse
import com.mkrworld.mobilpay.dto.network.sendbill.DTOSendBillRequest
import com.mkrworld.mobilpay.dto.network.sendbill.DTOSendBillResponse
import com.mkrworld.mobilpay.dto.user.userdetail.DTOUserDetailRequest
import com.mkrworld.mobilpay.dto.user.userdetail.DTOUserDetailResponse
import com.mkrworld.mobilpay.provider.network.AppNetworkTaskProvider
import com.mkrworld.mobilpay.ui.custom.OnTextInputLayoutTextChange
import com.mkrworld.mobilpay.utils.Constants
import com.mkrworld.mobilpay.utils.PreferenceData
import com.mkrworld.mobilpay.utils.Utils
import java.util.*

/**
 * Created by mkr on 13/3/18.
 */

class FragmentNotificationTabCustomer : Fragment(), OnBaseFragmentListener, View.OnClickListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentCashCollect"
    }

    private var mTextInputLayoutMobileNumber : TextInputLayout? = null
    private var mTextInputLayoutBillNumber : TextInputLayout? = null
    private var mTextInputLayoutBillDescription : TextInputLayout? = null
    private var mTextInputLayoutBillAmount : TextInputLayout? = null
    private var mEditTextDropDownMobileNumber : EditText? = null
    private var mEditTextBillNumber : EditText? = null
    private var mEditTextBillDescription : EditText? = null
    private var mEditTextBillAmount : EditText? = null
    private var mDTOSelectedUserBillData : DTOFetchBillResponse.Data? = null
    private var mAppNetworkTaskProvider : AppNetworkTaskProvider? = null
    private val mUserDetailResponseNetworkCallBack = object : NetworkCallBack<DTOUserDetailResponse> {
        override fun onSuccess(dto : DTOUserDetailResponse) {
            Utils.dismissLoadingDialog()
            if (view == null) {
                return
            }
            if (dto == null || dto.getData() == null || dto.getData() !!.size <= 0) {
                Tracer.showSnack(view !!, R.string.no_data_fetch_from_server)
                return
            }
            if (dto.getData().size > 1) {
                showSelectionDialog(dto.getData())
            } else {
                startFetchBillProcess(dto.getData().get(0) !!.userId !!)
            }
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
    private val mFetchBillResponseNetworkCallBack = object : NetworkCallBack<DTOFetchBillResponse> {
        override fun onSuccess(dto : DTOFetchBillResponse) {
            Tracer.debug(TAG, "onSuccess : ")
            Utils.dismissLoadingDialog()
            mDTOSelectedUserBillData = null
            if (view == null) {
                return
            }
            if (dto == null || dto.getData() == null) {
                Tracer.showSnack(view !!, R.string.no_data_fetch_from_server)
                return
            }
            mDTOSelectedUserBillData = dto.getData()
            mEditTextBillNumber !!.setText(mDTOSelectedUserBillData !!.billNumber)
            mEditTextBillDescription !!.setText(mDTOSelectedUserBillData !!.billDetail)
            mEditTextBillAmount !!.setText(mDTOSelectedUserBillData !!.amountPending)
            //mEditTextBillAmount !!.isEnabled = ! mDTOSelectedUserBillData !!.paymentType !!.trim().equals(Constants.PAYMENT_TYPE_FULL, true)
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

    private val mSendBillResponseNetworkCallBack = object : NetworkCallBack<DTOSendBillResponse> {
        override fun onSuccess(dto : DTOSendBillResponse) {
            Tracer.debug(TAG, "onSuccess : ")
            Utils.dismissLoadingDialog()
            mDTOSelectedUserBillData = null
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
     * Method to show the Selction dialog
     */
    private fun showSelectionDialog(dataList : java.util.ArrayList<DTOUserDetailResponse.Data>) {
        Tracer.debug(TAG, "showSelectionDialog : ")
        var mBuilder : AlertDialog.Builder? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBuilder = AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert)
        } else {
            mBuilder = AlertDialog.Builder(context)
        }
        mBuilder.setTitle(getString(R.string.choose_merchant))
        var nameList : Array<String?> = arrayOfNulls<String>(dataList.size)
        for (index : Int in 0 .. (dataList.size - 1)) {
            nameList.set(index, dataList[index].userId)
        }
        mBuilder.setSingleChoiceItems(nameList, 1, object : DialogInterface.OnClickListener {
            override fun onClick(dialogInterface : DialogInterface?, which : Int) {
                dialogInterface !!.dismiss()
                startFetchBillProcess(dataList[which].userId !!)
            }
        })
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    /**
     * Method to check weather detail insert by agent is valid or not
     *
     * @return
     */
    private val isBillDetailValid : Boolean
        get() {
            Tracer.debug(TAG, "isBillDetailValid: ")
            if (mDTOSelectedUserBillData == null || view == null) {
                showTextInputError(mTextInputLayoutMobileNumber, getString(R.string.no_bill_data_fetched_caps))
                return false
            }
            return true
        }

    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        Tracer.debug(TAG, "onCreateView: ")
        return inflater !!.inflate(R.layout.fragment_notification_tab_customer, container, false)
    }

    override fun onViewCreated(view : View?, savedInstanceState : Bundle?) {
        Tracer.debug(TAG, "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
        setTitle()
        init()
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
            R.id.fragment_notification_tab_customer_textView_cancel -> activity.onBackPressed()
            R.id.fragment_notification_tab_customer_textView_send -> startPayCashProcess()
        }
    }

    /**
     * Method to set the Activity Title
     */
    private fun setTitle() {
        Tracer.debug(TAG, "setTitle: ")
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

        view !!.findViewById<View>(R.id.fragment_notification_tab_customer_textView_send).setOnClickListener(this)
        view !!.findViewById<View>(R.id.fragment_notification_tab_customer_textView_cancel).setOnClickListener(this)

        mTextInputLayoutMobileNumber = view !!.findViewById<View>(R.id.fragment_notification_tab_customer_textInputLayout_mobile_number) as TextInputLayout
        mEditTextDropDownMobileNumber = view !!.findViewById<View>(R.id.fragment_notification_tab_customer_editText_mobile_number) as EditText
        mTextInputLayoutBillNumber = view !!.findViewById<View>(R.id.fragment_notification_tab_customer_textInputLayout_bill_number) as TextInputLayout
        mEditTextBillNumber = view !!.findViewById<View>(R.id.fragment_notification_tab_customer_editText_bill_number) as EditText
        mTextInputLayoutBillDescription = view !!.findViewById<View>(R.id.fragment_notification_tab_customer_textInputLayout_bill_description) as TextInputLayout
        mEditTextBillDescription = view !!.findViewById<View>(R.id.fragment_notification_tab_customer_editText_bill_description) as EditText
        mTextInputLayoutBillAmount = view !!.findViewById<View>(R.id.fragment_notification_tab_customer_textInputLayout_bill_amount) as TextInputLayout
        mEditTextBillAmount = view !!.findViewById<View>(R.id.fragment_notification_tab_customer_editText_bill_amount) as EditText

        // ADD TEXT CHANGE LISTENER
        mEditTextDropDownMobileNumber !!.addTextChangedListener(OnTextInputLayoutTextChange(mTextInputLayoutMobileNumber !!, object : OnTextInputLayoutTextChange.OnTextInputLayoutTextChangeListener {
            override fun onTextChanged(charSequence : CharSequence, i : Int, i1 : Int, i2 : Int) {
                mEditTextBillDescription !!.setText("")
                mEditTextBillNumber !!.setText("")
                mEditTextBillAmount !!.setText("")
                mEditTextBillAmount !!.isEnabled = false
                if (charSequence.toString().trim().length == 10) {
                    startFetchUserListProcess(charSequence.toString().trim())
                }
            }
        }))
        mEditTextBillNumber !!.addTextChangedListener(OnTextInputLayoutTextChange(mTextInputLayoutBillNumber !!))
        mEditTextBillDescription !!.addTextChangedListener(OnTextInputLayoutTextChange(mTextInputLayoutBillDescription !!))
        mEditTextBillAmount !!.addTextChangedListener(OnTextInputLayoutTextChange(mTextInputLayoutBillAmount !!))
        mEditTextBillDescription !!.isEnabled = false
        mEditTextBillNumber !!.isEnabled = false
        mEditTextBillAmount !!.isEnabled = false
    }

    /**
     * Method to Fetch the User list
     */
    private fun startFetchUserListProcess(mobileNumber : String) {
        Tracer.debug(TAG, "startPayCashProcess: ")
        Utils.hideKeyboard(activity, view)
        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(activity, getString(R.string.endpoint_get_user_details), date)
        val publicKey = getString(R.string.public_key)
        val dtoUserDetailRequest = DTOUserDetailRequest(token !!, timeStamp, publicKey, PreferenceData.getUserType(activity), PreferenceData.getLoginMerchantId(activity), PreferenceData.getLoginAgentId(activity), mobileNumber)
        Utils.showLoadingDialog(activity)
        mAppNetworkTaskProvider !!.userDetailTask(activity, dtoUserDetailRequest, mUserDetailResponseNetworkCallBack)
    }

    /**
     * Method to initiate the Fetch Bill Process
     */
    private fun startFetchBillProcess(userId : String) {
        Tracer.debug(TAG, "startFetchBillProcess: ")
        Utils.hideKeyboard(activity, view)
        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(activity, getString(R.string.endpoint_fetch_bill), date)
        val publicKey = getString(R.string.public_key)
        val dtoFetchBillRequest = DTOFetchBillRequest(token !!, timeStamp, publicKey, PreferenceData.getUserType(activity), PreferenceData.getLoginMerchantId(activity), PreferenceData.getLoginAgentId(activity), userId)
        Utils.showLoadingDialog(activity)
        mAppNetworkTaskProvider?.fetchBillTask(activity, dtoFetchBillRequest, mFetchBillResponseNetworkCallBack)
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
        val userId = mDTOSelectedUserBillData !!.userId
        val billNumber = mDTOSelectedUserBillData !!.billNumber
        val billDescription = mDTOSelectedUserBillData !!.billDetail
        val billAmount = mDTOSelectedUserBillData !!.billAmount
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
