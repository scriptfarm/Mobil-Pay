package com.mkrworld.mobilpay.ui.fragment.agent

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import com.mkrworld.androidlib.callback.OnBaseActivityListener
import com.mkrworld.androidlib.callback.OnBaseFragmentListener
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.agentfetchbill.DTOAgentFetchBillRequest
import com.mkrworld.mobilpay.dto.agentfetchbill.DTOAgentFetchBillResponse
import com.mkrworld.mobilpay.dto.agentsendbill.DTOAgentSendBillRequest
import com.mkrworld.mobilpay.dto.agentsendbill.DTOAgentSendBillResponse
import com.mkrworld.mobilpay.dto.appdata.DTODropdownArrayAdapter
import com.mkrworld.mobilpay.dto.userdetail.DTOUserDetailRequest
import com.mkrworld.mobilpay.dto.userdetail.DTOUserDetailResponse
import com.mkrworld.mobilpay.provider.network.AgentNetworkTaskProvider
import com.mkrworld.mobilpay.provider.network.UserNetworkTaskProvider
import com.mkrworld.mobilpay.ui.adapter.DropdownArrayAdapter
import com.mkrworld.mobilpay.ui.custom.EditTextDropDown
import com.mkrworld.mobilpay.ui.custom.OnTextInputLayoutTextChangeListener
import com.mkrworld.mobilpay.utils.Constants
import com.mkrworld.mobilpay.utils.PreferenceData
import com.mkrworld.mobilpay.utils.Utils
import java.util.*
import kotlin.collections.ArrayList

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
    private var mEditTextDropDownUserName : EditTextDropDown? = null
    private var mEditTextBillNumber : EditText? = null
    private var mEditTextBillDescription : EditText? = null
    private var mEditTextBillAmount : EditText? = null
    private var mDTOSelectedUserBillData : DTOAgentFetchBillResponse.Data? = null
    private var mUserNetworkTaskProvider : UserNetworkTaskProvider? = null
    private val mUserDetailResponseNetworkCallBack = object : NetworkCallBack<DTOUserDetailResponse> {
        override fun onSuccess(dtoAgentFetchBillResponse : DTOUserDetailResponse) {
            Utils.dismissLoadingDialog()
            if (view == null) {
                return
            }
            if (dtoAgentFetchBillResponse == null || dtoAgentFetchBillResponse.getData() == null || dtoAgentFetchBillResponse.getData() !!.size <= 0) {
                Tracer.showSnack(view !!, R.string.no_data_fetch_from_server)
                return
            }
            var dropDownOptionList : ArrayList<DTODropdownArrayAdapter> = ArrayList<DTODropdownArrayAdapter>()
            for (data in dtoAgentFetchBillResponse.getData() !!) {
                dropDownOptionList.add(DTODropdownArrayAdapter(data.userId !!, data.firstName + "(" + data.userId + ")"))
            }
            var adapter : DropdownArrayAdapter = DropdownArrayAdapter(activity, R.layout.item_dropdown_array_adapter, R.id.item_dropdown_array_adapter_textView, dropDownOptionList)
            mEditTextDropDownUserName !!.setAdapter(adapter)
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
    private var mAgentNetworkTaskProvider : AgentNetworkTaskProvider? = null
    private val mAgentFetchBillResponseNetworkCallBack = object : NetworkCallBack<DTOAgentFetchBillResponse> {
        override fun onSuccess(dtoAgentFetchBillResponse : DTOAgentFetchBillResponse) {
            Tracer.debug(TAG, "onSuccess : ")
            Utils.dismissLoadingDialog()
            mDTOSelectedUserBillData = null
            if (view == null) {
                return
            }
            if (dtoAgentFetchBillResponse == null || dtoAgentFetchBillResponse.getData() == null) {
                Tracer.showSnack(view !!, R.string.no_data_fetch_from_server)
                return
            }
            mDTOSelectedUserBillData = dtoAgentFetchBillResponse.getData()
            mEditTextBillNumber !!.setText(mDTOSelectedUserBillData !!.billNumber)
            mEditTextBillDescription !!.setText(mDTOSelectedUserBillData !!.billDetail)
            mEditTextBillAmount !!.setText(mDTOSelectedUserBillData !!.billAmount)
            mEditTextBillAmount !!.isEnabled = ! mDTOSelectedUserBillData !!.paymentType !!.trim().equals(Constants.PAYMENT_TYPE_FULL, true)
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

    private val mAgentSendBillResponseNetworkCallBack = object : NetworkCallBack<DTOAgentSendBillResponse> {
        override fun onSuccess(dtoAgentSendBillResponse : DTOAgentSendBillResponse) {
            Tracer.debug(TAG, "onSuccess : ")
            Utils.dismissLoadingDialog()
            mDTOSelectedUserBillData = null
            if (view == null) {
                return
            }
            if (dtoAgentSendBillResponse == null || dtoAgentSendBillResponse.getData() == null) {
                Tracer.showSnack(view !!, R.string.no_data_fetch_from_server)
                return
            }
            Tracer.showSnack(view !!, dtoAgentSendBillResponse.getMessage())
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
            if (mDTOSelectedUserBillData == null || view == null) {
                return false
            }
            val billAmount = mEditTextBillAmount !!.text.toString()
            if (! mDTOSelectedUserBillData !!.paymentType !!.trim().equals(Constants.PAYMENT_TYPE_FULL, true)) {
                try {
                    val amount = Integer.parseInt(billAmount.trim())
                    if (amount < Integer.parseInt(mDTOSelectedUserBillData !!.minPayment)) {
                        showTextInputError(mTextInputLayoutBillAmount, getString(R.string.amount_should_be_greater_then_caps) + " " + mDTOSelectedUserBillData !!.minPayment)
                        return false
                    }
                } catch (e : Exception) {
                    Tracer.error(TAG, "isBillDetailValid: " + e.message + "  " + e.message)
                    return false
                }

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
            R.id.fragment_agent_send_bill_editText_customer_id -> showOptionList()
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

        mUserNetworkTaskProvider = UserNetworkTaskProvider()
        mUserNetworkTaskProvider !!.attachProvider()

        mAgentNetworkTaskProvider = AgentNetworkTaskProvider()
        mAgentNetworkTaskProvider !!.attachProvider()

        view !!.findViewById<View>(R.id.fragment_agent_send_bill_textView_send).setOnClickListener(this)
        view !!.findViewById<View>(R.id.fragment_agent_send_bill_textView_cancel).setOnClickListener(this)
        view !!.findViewById<View>(R.id.fragment_agent_send_bill_editText_customer_id).setOnClickListener(this)

        mTextInputLayoutUserName = view !!.findViewById<View>(R.id.fragment_agent_send_bill_textInputLayout_customer_id) as TextInputLayout
        mEditTextDropDownUserName = view !!.findViewById<View>(R.id.fragment_agent_send_bill_editText_customer_id) as EditTextDropDown
        mTextInputLayoutBillNumber = view !!.findViewById<View>(R.id.fragment_agent_send_bill_textInputLayout_bill_number) as TextInputLayout
        mEditTextBillNumber = view !!.findViewById<View>(R.id.fragment_agent_send_bill_editText_bill_number) as EditText
        mTextInputLayoutBillDescription = view !!.findViewById<View>(R.id.fragment_agent_send_bill_textInputLayout_bill_description) as TextInputLayout
        mEditTextBillDescription = view !!.findViewById<View>(R.id.fragment_agent_send_bill_editText_bill_description) as EditText
        mTextInputLayoutBillAmount = view !!.findViewById<View>(R.id.fragment_agent_send_bill_textInputLayout_bill_amount) as TextInputLayout
        mEditTextBillAmount = view !!.findViewById<View>(R.id.fragment_agent_send_bill_editText_bill_amount) as EditText

        // ADD TEXT CHANGE LISTENER
        mEditTextDropDownUserName !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutUserName !!))
        mEditTextBillNumber !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutBillNumber !!))
        mEditTextBillDescription !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutBillDescription !!))
        mEditTextBillAmount !!.addTextChangedListener(OnTextInputLayoutTextChangeListener(mTextInputLayoutBillAmount !!))

        // INIT STATUS
        mEditTextBillDescription !!.isEnabled = false
        mEditTextBillNumber !!.isEnabled = false
        mEditTextDropDownUserName !!.threshold = 0
        startFetchUserListProcess()
        mEditTextDropDownUserName !!.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView : AdapterView<*>?, view : View?, position : Int, id : Long) {
                if (adapterView !!.adapter == null || adapterView.adapter.count < position || ! (adapterView.adapter.getItem(position) is DTODropdownArrayAdapter)) {
                    return
                }
                var item : DTODropdownArrayAdapter = adapterView.adapter.getItem(position) as DTODropdownArrayAdapter
                startFetchBillProcess(item.id !!)
            }
        }
    }

    /**
     * Method to Fetch the User list
     */
    private fun startFetchUserListProcess() {
        Tracer.debug(TAG, "startSendBillProcess: ")
        Utils.hideKeyboard(activity, view)
        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(activity, getString(R.string.endpoint_get_user_details), date)
        val publicKey = getString(R.string.public_key)
        val AgentId = PreferenceData.getLoginId(activity)
        val dtoUserDetailRequest = DTOUserDetailRequest(token !!, timeStamp, publicKey, AgentId)
        Utils.showLoadingDialog(activity)
        mUserNetworkTaskProvider !!.userDetailTask(activity, dtoUserDetailRequest, mUserDetailResponseNetworkCallBack)
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
        val AgentId = PreferenceData.getLoginId(activity)
        val dtoFetchBillRequest = DTOAgentFetchBillRequest(token !!, timeStamp, publicKey, AgentId, userId)
        Utils.showLoadingDialog(activity)
        mAgentNetworkTaskProvider !!.agentFetchBillTask(activity, dtoFetchBillRequest, mAgentFetchBillResponseNetworkCallBack)
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
        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(activity, getString(R.string.endpoint_send_bill), date)
        val publicKey = getString(R.string.public_key)
        val AgentId = PreferenceData.getLoginId(activity)
        val dtoAgentSendBillRequest = DTOAgentSendBillRequest(token !!, timeStamp, publicKey, AgentId, mDTOSelectedUserBillData !!.userId !!, mDTOSelectedUserBillData !!.billNumber !!)
        Utils.showLoadingDialog(activity)
        mAgentNetworkTaskProvider !!.agentSendBillTask(activity, dtoAgentSendBillRequest, mAgentSendBillResponseNetworkCallBack)
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
     * Method to show the Option List
     */
    private fun showOptionList() {
        Tracer.debug(TAG, "showOptionList : ")
        if (mEditTextDropDownUserName !!.adapter == null || mEditTextDropDownUserName !!.adapter.count <= 0) {
            startFetchUserListProcess()
            return
        }
        mEditTextDropDownUserName !!.showDropDown()
    }
}
