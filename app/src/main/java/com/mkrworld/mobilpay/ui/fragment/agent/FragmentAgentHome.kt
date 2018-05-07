package com.mkrworld.mobilpay.ui.fragment.agent

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.AdapterView
import android.widget.ListView
import android.widget.PopupWindow
import com.google.firebase.iid.FirebaseInstanceId
import com.mkrworld.androidlib.callback.OnBaseActivityListener
import com.mkrworld.androidlib.callback.OnBaseFragmentListener
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.ui.adapter.BaseAdapter
import com.mkrworld.androidlib.ui.adapter.BaseAdapterItem
import com.mkrworld.androidlib.ui.adapter.BaseViewHolder
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.agent.agentfcm.DTOAgentFCMRequest
import com.mkrworld.mobilpay.dto.agent.agentfcm.DTOAgentFCMResponse
import com.mkrworld.mobilpay.dto.appdata.DTODropdownArrayAdapter
import com.mkrworld.mobilpay.dto.appdata.DTOHomeTab
import com.mkrworld.mobilpay.dto.appdata.MessageData
import com.mkrworld.mobilpay.provider.fragment.FragmentProvider
import com.mkrworld.mobilpay.provider.fragment.FragmentTag
import com.mkrworld.mobilpay.provider.network.AgentNetworkTaskProvider
import com.mkrworld.mobilpay.task.app.ReadSmsTask
import com.mkrworld.mobilpay.ui.adapter.AdapterItemHandler
import com.mkrworld.mobilpay.ui.adapter.GridSpacingItemDecoration
import com.mkrworld.mobilpay.ui.adapter.PopupArrayAdapter
import com.mkrworld.mobilpay.utils.Constants
import com.mkrworld.mobilpay.utils.PreferenceData
import com.mkrworld.mobilpay.utils.Utils
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by mkr on 13/3/18.
 */

class FragmentAgentHome : Fragment(), OnBaseFragmentListener, BaseViewHolder.VHClickable, ReadSmsTask.OnMessageFilteredListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentAgentHome"
    }

    private var mMessageDataList : ArrayList<MessageData> = ArrayList<MessageData>()
    private var mAgentNetworkTaskProvider : AgentNetworkTaskProvider? = null;
    private val mAgentFCMResponseNetworkCallBack = object : NetworkCallBack<DTOAgentFCMResponse> {
        override fun onSuccess(dtoAgentFCMResponse : DTOAgentFCMResponse) {
            Tracer.debug(TAG, "onSuccess : ")
            if (view == null) {
                return
            }
            if (dtoAgentFCMResponse == null || dtoAgentFCMResponse.getData() == null) {
                Tracer.showSnack(view !!, R.string.no_data_fetch_from_server)
                return
            }
        }

        override fun onError(errorMessage : String, errorCode : Int) {
            Tracer.debug(TAG, "onError : ")
            if (view == null) {
                return
            }
            Tracer.showSnack(view !!, errorMessage)
        }
    }

    /**
     * Method to get the List of the Home Tab
     *
     * @return
     */
    private val homeTabList : ArrayList<BaseAdapterItem<*>>
        get() {
            Tracer.debug(TAG, "getHomeTabList: ")
            val baseAdapterItemList = ArrayList<BaseAdapterItem<*>>()
            val adapterViewType = AdapterItemHandler.AdapterItemViewType.HOME_TAB.ordinal
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOHomeTab(DTOHomeTab.TabType.SEND_NOTIFICATION, R.drawable.ic_static_qr_code, getString(R.string.send_notification))))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOHomeTab(DTOHomeTab.TabType.STATIC_QR_CODE, R.drawable.ic_static_qr_code, getString(R.string.static_qr_code))))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOHomeTab(DTOHomeTab.TabType.DYNAMIC_QR_CODE, R.drawable.ic_qr_code, getString(R.string.dynamic_qr_code))))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOHomeTab(DTOHomeTab.TabType.UPI_COLLECT, R.drawable.ic_upi_collect, getString(R.string.upi_collect))))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOHomeTab(DTOHomeTab.TabType.AEPS_COLLECT, R.drawable.ic_aeps_collect, getString(R.string.aeps_collect))))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOHomeTab(DTOHomeTab.TabType.SEND_BILL, R.drawable.ic_send_bill, getString(R.string.send_bill))))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOHomeTab(DTOHomeTab.TabType.PAY_CASH, R.drawable.ic_send_bill, getString(R.string.collect_cash))))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOHomeTab(DTOHomeTab.TabType.COLLECTION_SUMMARY, R.drawable.ic_collection_summary, getString(R.string.collection_summary))))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOHomeTab(DTOHomeTab.TabType.COLLECTION_STATUS, R.drawable.ic_static_qr_code, getString(R.string.collection_status))))
            return baseAdapterItemList
        }

    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        Tracer.debug(TAG, "onCreateView: ")
        return inflater !!.inflate(R.layout.fragment_agent_home, container, false)
    }

    override fun onViewCreated(view : View?, savedInstanceState : Bundle?) {
        Tracer.debug(TAG, "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setTitle()
        init()
    }

    override fun onDestroyView() {
        mAgentNetworkTaskProvider?.detachProvider()
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu : Menu?, inflater : MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.menu_agent, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item : MenuItem?) : Boolean {
        when (item?.itemId ?: - 1) {
            R.id.menu_agent_bell -> {
                if (mMessageDataList.size > 0) {
                    PreferenceData.setSmsReadTime(activity, System.currentTimeMillis())
                    showMessagePopup(view !!)
                } else {
                    Tracer.showSnack(view !!, getString(R.string.no_pending_alerts_caps))
                }
            }
        }
        return super.onOptionsItemSelected(item)
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

    override fun onViewHolderClicked(holder : BaseViewHolder<*>, view : View) {
        Tracer.debug(TAG, "onViewHolderClicked: ")
        when (view.id) {
            R.id.item_home_parent -> if (view.tag != null && view.tag is DTOHomeTab) {
                val dtoMerchantHomeTab = view.tag as DTOHomeTab
                when (dtoMerchantHomeTab.tabType) {
                    DTOHomeTab.TabType.STATIC_QR_CODE -> if (activity is OnBaseActivityListener) {
                        val bundle = Bundle()
                        bundle.putString(FragmentAgentQrCode.EXTRA_QR_CODE_TITLE, if (PreferenceData.getUserType(activity).equals(Constants.USER_TYPE_MERCHANT)) {
                            PreferenceData.getLoginMerchantId(activity)
                        } else {
                            PreferenceData.getLoginAgentId(activity)
                        })
                        val fragment = FragmentProvider.getFragment(FragmentTag.AGENT_QR_CODE)
                        (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment !!, bundle, true, FragmentTag.AGENT_QR_CODE)
                    }
                    DTOHomeTab.TabType.SEND_NOTIFICATION -> if (activity is OnBaseActivityListener) {
                        val fragment = FragmentProvider.getFragment(FragmentTag.SEND_NOTIFICATION)
                        (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment !!, null, true, FragmentTag.SEND_NOTIFICATION)
                    }
                    DTOHomeTab.TabType.DYNAMIC_QR_CODE -> if (activity is OnBaseActivityListener) {
                        val fragment = FragmentProvider.getFragment(FragmentTag.AGENT_QR_CODE_GENERATOR)
                        (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment !!, null, true, FragmentTag.AGENT_QR_CODE_GENERATOR)
                    }
                    DTOHomeTab.TabType.PAY_CASH -> if (activity is OnBaseActivityListener) {
                        val fragment = FragmentProvider.getFragment(FragmentTag.AGENT_PAY_CASH)
                        (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment !!, null, true, FragmentTag.AGENT_PAY_CASH)
                    }
                    DTOHomeTab.TabType.UPI_COLLECT -> {
                    }
                    DTOHomeTab.TabType.AEPS_COLLECT -> if (activity is OnBaseActivityListener) {
                        val fragment = FragmentProvider.getFragment(FragmentTag.AGENT_AEPS_COLLECT)
                        (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment !!, null, true, FragmentTag.AGENT_AEPS_COLLECT)
                    }
                    DTOHomeTab.TabType.COLLECTION_SUMMARY -> if (activity is OnBaseActivityListener) {
                        val fragment = FragmentProvider.getFragment(FragmentTag.COLLECTION_SUMMARY)
                        (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment !!, null, true, FragmentTag.COLLECTION_SUMMARY)
                    }
                    DTOHomeTab.TabType.COLLECTION_STATUS -> if (activity is OnBaseActivityListener) {
                        val fragment = FragmentProvider.getFragment(FragmentTag.COLLECTION_STATUS)
                        (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment !!, null, true, FragmentTag.COLLECTION_STATUS)
                    }
                    DTOHomeTab.TabType.SEND_BILL -> if (activity is OnBaseActivityListener) {
                        val fragment = FragmentProvider.getFragment(FragmentTag.AGENT_SEND_BILL)
                        (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment !!, null, true, FragmentTag.AGENT_SEND_BILL)
                    }
                }
            }
        }
    }

    override fun onMessageFiltered(messageDataList : ArrayList<MessageData>) {
        Tracer.debug(TAG, "onMessageFiltered : ")
        Utils.dismissLoadingDialog()
        mMessageDataList.clear()
        mMessageDataList.addAll(messageDataList ?: ArrayList<MessageData>())
    }

    /**
     * Method to set the Activity Title
     */
    private fun setTitle() {
        Tracer.debug(TAG, "setTitle: ")
        if (activity is OnBaseActivityListener) {
            (activity as OnBaseActivityListener).onBaseActivitySetScreenTitle(getString(R.string.screen_title_agent_home))
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
        mAgentNetworkTaskProvider = AgentNetworkTaskProvider()
        mAgentNetworkTaskProvider?.attachProvider()

        // INIT the RecyclerView
        val recyclerView = view !!.findViewById<View>(R.id.fragment_agent_home_recycler_view) as RecyclerView
        val baseAdapter = BaseAdapter(AdapterItemHandler())
        recyclerView.adapter = baseAdapter
        val gridLayoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = gridLayoutManager
        baseAdapter.updateAdapterItemList(homeTabList)
        val colorDivider = ContextCompat.getColor(activity, R.color.divider_color)
        val gridSpacingItemDecoration = GridSpacingItemDecoration(2, resources.getDimensionPixelOffset(R.dimen.divider_size), colorDivider, false)
        recyclerView.addItemDecoration(gridSpacingItemDecoration)
        baseAdapter.setVHClickCallback(this)
        registerAgentFCM()
        filterSms()
    }

    /**
     * Method to register FCM
     */
    private fun registerAgentFCM() {
        Tracer.debug(TAG, "registerAgentFCM : ")
        object : AsyncTask<Void, Void, String>() {

            override fun doInBackground(vararg voids : Void) : String? {
                Tracer.debug(TAG, "registerAgentFCM : doInBackground: ")
                val refreshedToken = FirebaseInstanceId.getInstance().token
                Tracer.debug(TAG, "registerAgentFCM : doInBackground: " + refreshedToken !!)
                return refreshedToken ?: ""
            }

            override fun onPostExecute(result : String?) {
                super.onPostExecute(result)
                Tracer.debug(TAG, "registerAgentFCM : onPostExecute : " + result)
                if (result == null || result.trim().isEmpty()) {
                    return
                }
                val date = Date()
                val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
                val token = Utils.createToken(activity, getString(R.string.endpoint_agent_fcm), date)
                val publicKey = getString(R.string.public_key)
                val dtoAgentFCMRequest = DTOAgentFCMRequest(token !!, timeStamp, publicKey, PreferenceData.getUserType(activity), PreferenceData.getLoginMerchantId(activity), PreferenceData.getLoginAgentId(activity), result.trim())
                mAgentNetworkTaskProvider !!.setFcmId(activity, dtoAgentFCMRequest, mAgentFCMResponseNetworkCallBack)
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    /**
     * Method to filter the Sms List
     */
    private fun filterSms() {
        Tracer.debug(TAG, "filterSms : ")
        Utils.showLoadingDialog(activity)
        ReadSmsTask(activity, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    /**
     * Method to show the Message Popup
     */
    private fun showMessagePopup(actionView : View) {
        // INIT POPUP WINDOW
        var dropDownOptionList : ArrayList<DTODropdownArrayAdapter> = ArrayList<DTODropdownArrayAdapter>()
        for (data : MessageData in mMessageDataList !!) {
            dropDownOptionList.add(DTODropdownArrayAdapter(data.id, data.body))
        }
        var adapter : PopupArrayAdapter = PopupArrayAdapter(activity, R.layout.item_popup_array_adapter_solid, R.id.item_popup_array_adapter_solid_textView, dropDownOptionList)
        var popupWindow : PopupWindow = PopupWindow(context)
        val listViewSort = ListView(context)
        listViewSort.setAdapter(adapter)
        popupWindow.setFocusable(true)
        popupWindow.setContentView(listViewSort)
        popupWindow.width = (resources.displayMetrics.widthPixels.toFloat() * 0.7F).toInt()
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        listViewSort.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent : AdapterView<*>?, view : View?, position : Int, id : Long) {
                popupWindow.dismiss()
            }
        }
        popupWindow.showAsDropDown(actionView, view !!.width, - view !!.height)
    }
}
