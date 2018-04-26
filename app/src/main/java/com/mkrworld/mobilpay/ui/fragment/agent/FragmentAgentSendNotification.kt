package com.mkrworld.mobilpay.ui.fragment.agent

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mkrworld.androidlib.BuildConfig
import com.mkrworld.androidlib.callback.OnBaseActivityListener
import com.mkrworld.androidlib.callback.OnBaseFragmentListener
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.ui.adapter.BaseAdapter
import com.mkrworld.androidlib.ui.adapter.BaseAdapterItem
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTOAgentNotificationData
import com.mkrworld.mobilpay.dto.sendnotification.DTOSendNotificationRequest
import com.mkrworld.mobilpay.dto.sendnotification.DTOSendNotificationResponse
import com.mkrworld.mobilpay.provider.network.AgentNetworkTaskProvider
import com.mkrworld.mobilpay.ui.adapter.AdapterItemHandler
import com.mkrworld.mobilpay.utils.PreferenceData
import com.mkrworld.mobilpay.utils.Utils
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by mkr on 15/3/18.
 */

class FragmentAgentSendNotification : Fragment(), OnBaseFragmentListener, View.OnClickListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentAgentSendNotification"
    }

    private var mBaseAdapter : BaseAdapter? = null
    private var mAgentNetworkTaskProvider : AgentNetworkTaskProvider? = null
    private val mNotificationResponseNetworkCallBack = object : NetworkCallBack<DTOSendNotificationResponse> {
        override fun onSuccess(dtoSendNotificationResponse : DTOSendNotificationResponse) {
            Utils.dismissLoadingDialog()
            if (view == null) {
                return
            }
            if (dtoSendNotificationResponse == null || dtoSendNotificationResponse.getData() == null) {
                Tracer.showSnack(view !!, R.string.no_data_fetch_from_server)
                return
            }
            Tracer.showSnack(view !!, dtoSendNotificationResponse.getMessage())
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
        return inflater !!.inflate(R.layout.fragment_agent_send_notification, container, false)
    }

    override fun onViewCreated(view : View?, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Tracer.debug(TAG, "onViewCreated: ")
        setTitle()
        init()
    }

    override fun onResume() {
        super.onResume()
        mAgentNetworkTaskProvider?.attachProvider()
    }

    override fun onDestroyView() {
        mAgentNetworkTaskProvider?.detachProvider()
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
            R.id.fragment_agent_send_notification_textView_cancel -> {
                activity?.onBackPressed()
            }
            R.id.fragment_agent_send_notification_textView_send -> {
                var paymentOptionList : ArrayList<String> = ArrayList<String>()
                for (index : Int in 0 .. (mBaseAdapter !!.itemCount - 1)) {
                    val item : BaseAdapterItem<*> = mBaseAdapter !!.getItem(index)
                    if (item.getData() is DTOAgentNotificationData) {
                        var data = item.getData() as DTOAgentNotificationData
                        if (data.isChecked) {
                            paymentOptionList.add("" + data.id)
                        }
                    }
                }
                if (paymentOptionList.size <= 0) {
                    Tracer.showSnack(view !!, R.string.nothing_selected)
                    return
                }
                sendNotification(paymentOptionList)
            }
        }
    }

    /**
     * Method to set the Activity Title
     */
    private fun setTitle() {
        Tracer.debug(TAG, "setTitle: ")
        if (activity is OnBaseActivityListener) {
            (activity as OnBaseActivityListener).onBaseActivitySetScreenTitle(getString(R.string.screen_title_send_notification))
        }
    }

    /**
     * Method to initialize the Fragment
     */
    private fun init() {
        Tracer.debug(TAG, "init: ")
        mAgentNetworkTaskProvider = AgentNetworkTaskProvider()
        mAgentNetworkTaskProvider?.attachProvider()
        if (view == null) {
            return
        }
        view !!.findViewById<View>(R.id.fragment_agent_send_notification_textView_cancel).setOnClickListener(this)
        view !!.findViewById<View>(R.id.fragment_agent_send_notification_textView_send).setOnClickListener(this)
        val recyclerViewUserData = view !!.findViewById<View>(R.id.fragment_agent_send_notification_recycler_view) as RecyclerView
        mBaseAdapter = BaseAdapter(AdapterItemHandler())
        recyclerViewUserData.adapter = mBaseAdapter
        recyclerViewUserData.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        // SET OPTION DATA
        val baseAdapterItemArrayList = ArrayList<BaseAdapterItem<*>>()
        baseAdapterItemArrayList.add(BaseAdapterItem(AdapterItemHandler.AdapterItemViewType.AGENT_NOTIFICATION_DATA_ITEM.ordinal, DTOAgentNotificationData(1, "PAID")))
        baseAdapterItemArrayList.add(BaseAdapterItem(AdapterItemHandler.AdapterItemViewType.AGENT_NOTIFICATION_DATA_ITEM.ordinal, DTOAgentNotificationData(0, "UNPAID")))
        baseAdapterItemArrayList.add(BaseAdapterItem(AdapterItemHandler.AdapterItemViewType.AGENT_NOTIFICATION_DATA_ITEM.ordinal, DTOAgentNotificationData(3, "PARTIAL-PAID")))
        mBaseAdapter !!.updateAdapterItemList(baseAdapterItemArrayList)
    }

    /**
     * Method to send notification
     */
    private fun sendNotification(list : ArrayList<String>) {
        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(activity, getString(R.string.endpoint_send_notification), date)
        val publicKey = getString(R.string.public_key)
        val dtoSendNotificationRequest = DTOSendNotificationRequest(token !!, timeStamp, publicKey, PreferenceData.getUserType(activity), PreferenceData.getLoginMerchantId(activity), PreferenceData.getLoginAgentId(activity), list)
        Utils.showLoadingDialog(activity)
        mAgentNetworkTaskProvider !!.sendNotificationTaskTask(activity, dtoSendNotificationRequest, mNotificationResponseNetworkCallBack)
    }
}
