package com.mkrworld.mobilpay.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.mkrworld.androidlib.BuildConfig
import com.mkrworld.androidlib.callback.OnBaseActivityListener
import com.mkrworld.androidlib.callback.OnBaseFragmentListener
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.ui.adapter.BaseAdapter
import com.mkrworld.androidlib.ui.adapter.BaseAdapterItem
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTOSendNotificationData
import com.mkrworld.mobilpay.dto.comms.sendnotification.DTOSendNotificationRequest
import com.mkrworld.mobilpay.dto.comms.sendnotification.DTOSendNotificationResponse
import com.mkrworld.mobilpay.provider.network.AppNetworkTaskProvider
import com.mkrworld.mobilpay.ui.adapter.AdapterItemHandler
import com.mkrworld.mobilpay.ui.fragment.agent.FragmentAgentHome
import com.mkrworld.mobilpay.utils.Constants
import com.mkrworld.mobilpay.utils.PreferenceData
import com.mkrworld.mobilpay.utils.Utils
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by mkr on 15/3/18.
 */

class FragmentSendNotification : Fragment(), OnBaseFragmentListener, View.OnClickListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentAgentSendNotification"
    }

    private var mBaseAdapter : BaseAdapter? = null
    private var mAppNetworkTaskProvider : AppNetworkTaskProvider? = null
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
        return inflater !!.inflate(R.layout.fragment_send_notification, container, false)
    }

    override fun onViewCreated(view : View?, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Tracer.debug(TAG, "onViewCreated: ")
        setHasOptionsMenu(true)
        setTitle()
        init()
    }

    override fun onCreateOptionsMenu(menu : Menu?, inflater : MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.menu_nothing, menu);
        super.onCreateOptionsMenu(menu, inflater)
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
            R.id.fragment_send_notification_textView_cancel -> {
                activity?.onBackPressed()
            }
            R.id.fragment_send_notification_textView_send -> {
                var paymentOptionList : ArrayList<String> = ArrayList<String>()
                for (index : Int in 0 .. (mBaseAdapter !!.itemCount - 1)) {
                    val item : BaseAdapterItem<*> = mBaseAdapter !!.getItem(index)
                    if (item.getData() is DTOSendNotificationData) {
                        var data = item.getData() as DTOSendNotificationData
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
        mAppNetworkTaskProvider = AppNetworkTaskProvider()
        mAppNetworkTaskProvider?.attachProvider()
        if (view == null) {
            return
        }
        view !!.findViewById<View>(R.id.fragment_send_notification_textView_cancel).setOnClickListener(this)
        view !!.findViewById<View>(R.id.fragment_send_notification_textView_send).setOnClickListener(this)
        val recyclerViewUserData = view !!.findViewById<View>(R.id.fragment_send_notification_recycler_view) as RecyclerView
        mBaseAdapter = BaseAdapter(AdapterItemHandler())
        recyclerViewUserData.adapter = mBaseAdapter
        recyclerViewUserData.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        // SET OPTION DATA
        val baseAdapterItemArrayList = ArrayList<BaseAdapterItem<*>>()
        if (PreferenceData.getUserType(activity).equals(Constants.USER_TYPE_MERCHANT)) {
            baseAdapterItemArrayList.add(BaseAdapterItem(AdapterItemHandler.AdapterItemViewType.SEND_NOTIFICATION_DATA_ITEM.ordinal, DTOSendNotificationData(- 1, "AGENT")))
        }
        baseAdapterItemArrayList.add(BaseAdapterItem(AdapterItemHandler.AdapterItemViewType.SEND_NOTIFICATION_DATA_ITEM.ordinal, DTOSendNotificationData(0, "UNPAID")))
        baseAdapterItemArrayList.add(BaseAdapterItem(AdapterItemHandler.AdapterItemViewType.SEND_NOTIFICATION_DATA_ITEM.ordinal, DTOSendNotificationData(3, "PARTIAL-PAID")))
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
        mAppNetworkTaskProvider !!.sendNotificationTaskTask(activity, dtoSendNotificationRequest, mNotificationResponseNetworkCallBack)
    }
}
