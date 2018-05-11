package com.mkrworld.mobilpay.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mkrworld.androidlib.BuildConfig
import com.mkrworld.androidlib.callback.OnBaseFragmentListener
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.ui.adapter.BaseAdapter
import com.mkrworld.androidlib.ui.adapter.BaseAdapterItem
import com.mkrworld.androidlib.utils.MKRDialogUtil
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTOMultiSelectionItemData
import com.mkrworld.mobilpay.dto.network.sendnotification.DTOSendNotificationRequest
import com.mkrworld.mobilpay.dto.network.sendnotification.DTOSendNotificationResponse
import com.mkrworld.mobilpay.provider.network.AppNetworkTaskProvider
import com.mkrworld.mobilpay.ui.adapter.AdapterItemHandler
import com.mkrworld.mobilpay.ui.adapter.GridSpacingItemDecoration
import com.mkrworld.mobilpay.utils.Constants
import com.mkrworld.mobilpay.utils.PreferenceData
import com.mkrworld.mobilpay.utils.Utils
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by mkr on 15/3/18.
 */

class FragmentNotificationTabAll : Fragment(), OnBaseFragmentListener, View.OnClickListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentNotificationTabAll"
    }

    private var mBaseAdapter : BaseAdapter? = null
    private var mAppNetworkTaskProvider : AppNetworkTaskProvider? = null
    private val mNotificationResponseNetworkCallBack = object : NetworkCallBack<DTOSendNotificationResponse> {
        override fun onSuccess(dto : DTOSendNotificationResponse) {
            Utils.dismissLoadingDialog()
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
            activity.onBackPressed()
        }
    }

    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        return inflater !!.inflate(R.layout.fragment_notification_tab_all, container, false)
    }

    override fun onViewCreated(view : View?, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Tracer.debug(TAG, "onViewCreated: ")
        setTitle()
        init()
    }

    override fun onPopFromBackStack() {
        Tracer.debug(TAG, "onPopFromBackStack: ")
        setTitle()
    }

    override fun onResume() {
        super.onResume()
        mAppNetworkTaskProvider?.attachProvider()
    }

    override fun onDestroyView() {
        mAppNetworkTaskProvider?.detachProvider()
        super.onDestroyView()
    }

    override fun onBackPressed() : Boolean {
        Tracer.debug(TAG, "onBackPressed: ")
        return false
    }

    override fun onRefresh() {
        Tracer.debug(TAG, "onRefresh: ")
    }

    override fun onClick(view : View) {
        Tracer.debug(TAG, "onClick: ")
        when (view.id) {
            R.id.fragment_notification_tab_all_textView_cancel -> {
                activity?.onBackPressed()
            }
            R.id.fragment_notification_tab_all_textView_send -> {
                showConfirmationDialog()
            }
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
        mAppNetworkTaskProvider = AppNetworkTaskProvider()
        mAppNetworkTaskProvider?.attachProvider()
        if (view == null) {
            return
        }
        view !!.findViewById<View>(R.id.fragment_notification_tab_all_textView_cancel).setOnClickListener(this)
        view !!.findViewById<View>(R.id.fragment_notification_tab_all_textView_send).setOnClickListener(this)
        val recyclerView = view !!.findViewById<View>(R.id.fragment_send_notification_tab_all_recycler_view) as RecyclerView
        mBaseAdapter = BaseAdapter(AdapterItemHandler())
        recyclerView.adapter = mBaseAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        // ADD HORIZONTAL LINE
        val colorDivider = ContextCompat.getColor(activity, R.color.divider_color)
        val gridSpacingItemDecoration = GridSpacingItemDecoration(1, resources.getDimensionPixelOffset(R.dimen.divider_size), colorDivider, false)
        recyclerView.addItemDecoration(gridSpacingItemDecoration)
        // SET OPTION DATA
        val baseAdapterItemArrayList = ArrayList<BaseAdapterItem<*>>()
        if (Utils.isMerchant(activity)) {
            baseAdapterItemArrayList.add(BaseAdapterItem(AdapterItemHandler.AdapterItemViewType.MULTI_SELECTION_ITEM.ordinal, DTOMultiSelectionItemData(Constants.Companion.NotificationType.AGENT.name, getString(R.string.all_agent), false, true)))
        }
        baseAdapterItemArrayList.add(BaseAdapterItem(AdapterItemHandler.AdapterItemViewType.MULTI_SELECTION_ITEM.ordinal, DTOMultiSelectionItemData(Constants.Companion.NotificationType.UNPAID_CUSTOMER.name, getString(R.string.all_unpaid_customer), false, false)))
        baseAdapterItemArrayList.add(BaseAdapterItem(AdapterItemHandler.AdapterItemViewType.MULTI_SELECTION_ITEM.ordinal, DTOMultiSelectionItemData(Constants.Companion.NotificationType.PARTIAL_PAID_CUSTOMER.name, getString(R.string.all_partial_paid_customer), false, false)))
        mBaseAdapter !!.updateAdapterItemList(baseAdapterItemArrayList)
    }

    /**
     * Method to show dialog to get confirmation weather to send the notification or not
     * @param userType
     * @param merchantId
     * @param agentId
     * @param password
     */
    private fun showConfirmationDialog() {
        Tracer.debug(TAG, "showEnableFingerPrintDialog : ")
        val context = activity
        val iconId = R.mipmap.ic_launcher
        val title = getString(R.string.message_confirmation)
        val message = getString(R.string.are_you_sure_to_send_the_reminder_to_all)
        val onOkClickListener = DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.dismiss()
            sendNotification()
        }
        val onCancelClickListener = DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.dismiss()
        }
        val cancellable = false
        MKRDialogUtil.showOKCancelDialog(context, iconId, title, message, onOkClickListener, onCancelClickListener, cancellable)
    }

    /**
     * Method to send notification
     */
    private fun sendNotification() {
        var isAgentSelected = false
        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(activity, getString(R.string.endpoint_send_notification), date)
        val publicKey = getString(R.string.public_key)
        var agentList : ArrayList<String> = ArrayList<String>()
        var agentMessage : String = ""
        var unpaidId : ArrayList<String> = ArrayList<String>()
        var partialPaidId : ArrayList<String> = ArrayList<String>()

        var checkCount : Int = 0
        for (index : Int in 0 .. (mBaseAdapter !!.itemCount - 1)) {
            val item : BaseAdapterItem<*> = mBaseAdapter !!.getItem(index)
            if (item.getData() is DTOMultiSelectionItemData) {
                var data = item.getData() as DTOMultiSelectionItemData
                when (data.id) {
                    Constants.Companion.NotificationType.AGENT.name -> {
                        if (data.isChecked) {
                            isAgentSelected = true
                            checkCount ++
                            agentList.add("0")
                            agentMessage = data.message
                        } else {
                            agentList.add("-1")
                        }
                    }
                    Constants.Companion.NotificationType.UNPAID_CUSTOMER.name -> {
                        if (data.isChecked) {
                            checkCount ++
                            unpaidId.add("0")
                        } else {
                            unpaidId.add("-1")
                        }
                    }
                    Constants.Companion.NotificationType.PARTIAL_PAID_CUSTOMER.name -> {
                        if (data.isChecked) {
                            checkCount ++
                            partialPaidId.add("0")
                        } else {
                            partialPaidId.add("-1")
                        }
                    }
                }
            }
        }
        if (checkCount == 0) {
            Tracer.showSnack(view !!, R.string.nothing_selected)
            return
        }
        if (isAgentSelected && agentMessage.trim().isEmpty()) {
            Tracer.showSnack(view !!, R.string.write_a_valid_message_for_agent)
            return
        }
        val dtoSendNotificationRequest = DTOSendNotificationRequest(token !!, timeStamp, publicKey, PreferenceData.getUserType(activity), PreferenceData.getLoginMerchantId(activity), PreferenceData.getLoginAgentId(activity), agentList, agentMessage, unpaidId, partialPaidId)
        Utils.showLoadingDialog(activity)
        mAppNetworkTaskProvider !!.sendNotificationTaskTask(activity, dtoSendNotificationRequest, mNotificationResponseNetworkCallBack)
    }
}
