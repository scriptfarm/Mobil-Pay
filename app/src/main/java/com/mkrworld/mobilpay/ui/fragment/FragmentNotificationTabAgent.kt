package com.mkrworld.mobilpay.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.mkrworld.androidlib.BuildConfig
import com.mkrworld.androidlib.callback.OnBaseFragmentListener
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.ui.adapter.BaseAdapter
import com.mkrworld.androidlib.ui.adapter.BaseAdapterItem
import com.mkrworld.androidlib.ui.adapter.BaseViewHolder
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTOMultiSelectionItemData
import com.mkrworld.mobilpay.dto.network.merchantagentlist.DTOMerchantAgentListRequest
import com.mkrworld.mobilpay.dto.network.merchantagentlist.DTOMerchantAgentListResponse
import com.mkrworld.mobilpay.dto.network.sendnotification.DTOSendNotificationRequest
import com.mkrworld.mobilpay.dto.network.sendnotification.DTOSendNotificationResponse
import com.mkrworld.mobilpay.provider.network.AppNetworkTaskProvider
import com.mkrworld.mobilpay.ui.adapter.AdapterItemHandler
import com.mkrworld.mobilpay.ui.adapter.GridSpacingItemDecoration
import com.mkrworld.mobilpay.utils.PreferenceData
import com.mkrworld.mobilpay.utils.Utils
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by mkr on 15/3/18.
 */

class FragmentNotificationTabAgent : Fragment(), OnBaseFragmentListener, View.OnClickListener, BaseViewHolder.VHClickable {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentNotificationTabAll"
    }

    private var mIsAllSelected : Boolean = false
    private var mMultiSelectionItemDataList : ArrayList<DTOMultiSelectionItemData> = ArrayList<DTOMultiSelectionItemData>()
    private var mBaseAdapter : BaseAdapter? = null
    private var mAppNetworkTaskProvider : AppNetworkTaskProvider? = null
    private val mAgentListResponseNetworkCallBack = object : NetworkCallBack<DTOMerchantAgentListResponse> {
        override fun onSuccess(dto : DTOMerchantAgentListResponse) {
            Utils.dismissLoadingDialog()
            if (view == null) {
                return
            }
            if (dto == null || dto.getData().size <= 0) {
                Tracer.showSnack(view !!, R.string.no_data_fetch_from_server)
                activity.onBackPressed()
                return
            }

            val baseAdapterItemArrayList = ArrayList<BaseAdapterItem<*>>()
            mMultiSelectionItemDataList.clear()
            mIsAllSelected = true
            getView() !!.findViewById<CheckBox>(R.id.fragment_send_notification_tab_agent_radio_all).isChecked = mIsAllSelected
            for (data : DTOMerchantAgentListResponse.Data in dto.getData()) {
                val dtoMultiSelectionItemData = DTOMultiSelectionItemData(data.userName, data.name + "(" + data.mobileNumber + ")", mIsAllSelected, false)
                mMultiSelectionItemDataList.add(dtoMultiSelectionItemData)
                baseAdapterItemArrayList.add(BaseAdapterItem(AdapterItemHandler.AdapterItemViewType.MULTI_SELECTION_ITEM.ordinal, dtoMultiSelectionItemData))
            }
            mBaseAdapter !!.updateAdapterItemList(baseAdapterItemArrayList)
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
        return inflater !!.inflate(R.layout.fragment_notification_tab_agent, container, false)
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
            R.id.fragment_notification_tab_agent_textView_cancel -> {
                activity?.onBackPressed()
            }
            R.id.fragment_notification_tab_agent_textView_send -> {
                sendNotification()
            }
            R.id.fragment_send_notification_tab_agent_radio_all -> {
                toggleSelectAll()
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
        view !!.findViewById<View>(R.id.fragment_notification_tab_agent_textView_cancel).setOnClickListener(this)
        view !!.findViewById<View>(R.id.fragment_notification_tab_agent_textView_send).setOnClickListener(this)
        view !!.findViewById<View>(R.id.fragment_send_notification_tab_agent_radio_all).setOnClickListener(this)
        val recyclerView = view !!.findViewById<View>(R.id.fragment_send_notification_tab_agent_recycler_view) as RecyclerView
        mBaseAdapter = BaseAdapter(AdapterItemHandler())
        mBaseAdapter !!.setVHClickCallback(this)
        recyclerView.adapter = mBaseAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        // ADD HORIZONTAL LINE
        val colorDivider = ContextCompat.getColor(activity, R.color.divider_color)
        val gridSpacingItemDecoration = GridSpacingItemDecoration(1, resources.getDimensionPixelOffset(R.dimen.divider_size), colorDivider, false)
        recyclerView.addItemDecoration(gridSpacingItemDecoration)
        fetchAgent()
    }

    /**
     * Method to toggle selcet All state
     */
    private fun toggleSelectAll() {
        mIsAllSelected = ! mIsAllSelected
        getView() !!.findViewById<CheckBox>(R.id.fragment_send_notification_tab_agent_radio_all).isChecked = mIsAllSelected
        for (index : Int in 0 .. (mBaseAdapter !!.itemCount - 1)) {
            val item : BaseAdapterItem<*> = mBaseAdapter !!.getItem(index)
            if (item.getData() is DTOMultiSelectionItemData) {
                var data = item.getData() as DTOMultiSelectionItemData
                data.isChecked = mIsAllSelected
            }
        }
        mBaseAdapter !!.notifyDataSetChanged()
    }

    override fun onViewHolderClicked(holder : BaseViewHolder<*>, view : View) {
        when (view.id) {
            R.id.item_multi_selection_item_data_radio -> {
                mIsAllSelected = true
                for (index : Int in 0 .. (mBaseAdapter !!.itemCount - 1)) {
                    val item : BaseAdapterItem<*> = mBaseAdapter !!.getItem(index)
                    if (item.getData() is DTOMultiSelectionItemData) {
                        var data = item.getData() as DTOMultiSelectionItemData
                        if (! data.isChecked) {
                            mIsAllSelected = false
                            break
                        }
                    }
                }
                getView() !!.findViewById<CheckBox>(R.id.fragment_send_notification_tab_agent_radio_all).isChecked = mIsAllSelected
            }
        }
    }

    /**
     * Method to send notification
     */
    private fun sendNotification() {
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
                if (data.isChecked) {
                    agentList.add(data.id !!)
                    checkCount ++
                }
            }
        }
        if (checkCount == 0) {
            Tracer.showSnack(view !!, R.string.nothing_selected)
            return
        }
        val dtoSendNotificationRequest = DTOSendNotificationRequest(token !!, timeStamp, publicKey, PreferenceData.getUserType(activity), PreferenceData.getLoginMerchantId(activity), PreferenceData.getLoginAgentId(activity), agentList, agentMessage, unpaidId, partialPaidId)
        Utils.showLoadingDialog(activity)
        mAppNetworkTaskProvider !!.sendNotificationTaskTask(activity, dtoSendNotificationRequest, mNotificationResponseNetworkCallBack)
    }

    /**
     * Method to send notification
     */
    private fun fetchAgent() {
        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(activity, getString(R.string.endpoint_merchant_agent_list), date)
        val publicKey = getString(R.string.public_key)
        val dtoAgentMerchantListRequest = DTOMerchantAgentListRequest(token !!, timeStamp, publicKey, PreferenceData.getUserType(activity), PreferenceData.getLoginMerchantId(activity), PreferenceData.getLoginAgentId(activity))
        Utils.showLoadingDialog(activity)
        mAppNetworkTaskProvider !!.agentListTask(activity, dtoAgentMerchantListRequest, mAgentListResponseNetworkCallBack)
    }
}
