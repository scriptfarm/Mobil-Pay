package com.mkrworld.mobilpay.ui.fragment

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
import com.mkrworld.mobilpay.dto.appdata.DTOCollectionSummaryConsolidateData
import com.mkrworld.mobilpay.dto.appdata.DTOSummaryConsolidateDataList
import com.mkrworld.mobilpay.dto.comms.collectionsummary.DTOCollectionSummaryRequest
import com.mkrworld.mobilpay.dto.comms.collectionsummary.DTOCollectionSummaryResponse
import com.mkrworld.mobilpay.provider.network.AppNetworkTaskProvider
import com.mkrworld.mobilpay.ui.adapter.AdapterItemHandler
import com.mkrworld.mobilpay.utils.PreferenceData
import com.mkrworld.mobilpay.utils.Utils
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by mkr on 15/3/18.
 */

class FragmentCollectionSummary : Fragment(), OnBaseFragmentListener, View.OnClickListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentMerchantCollectionSummary"
    }

    private var mBaseAdapter : BaseAdapter? = null
    private var mAppNetworkTaskProvider : AppNetworkTaskProvider? = null
    private val mCollectionSummaryNetworkCallBack = object : NetworkCallBack<DTOCollectionSummaryResponse> {
        override fun onSuccess(dtoCollectionSummaryResponse : DTOCollectionSummaryResponse) {
            Utils.dismissLoadingDialog()
            if (view == null) {
                return
            }
            if (dtoCollectionSummaryResponse == null || dtoCollectionSummaryResponse.getData() == null || dtoCollectionSummaryResponse.getData() !!.size <= 0) {
                Tracer.showSnack(view !!, R.string.no_data_fetch_from_server)
                return
            }
            // RECYCLER VIEW DATA
            val baseAdapterItemArrayList = ArrayList<BaseAdapterItem<*>>()
            val dtoSummaryConsolidateDataList = DTOSummaryConsolidateDataList()
            val dataList : ArrayList<DTOCollectionSummaryResponse.Data> = dtoCollectionSummaryResponse.getData()
            dtoSummaryConsolidateDataList.addConsolidateData(DTOCollectionSummaryConsolidateData(DTOCollectionSummaryConsolidateData.RowType.TITLE, getString(R.string.mode_caps), getString(R.string.txns_caps), getString(R.string.amount_caps)))
            for (data : DTOCollectionSummaryResponse.Data in dataList) {
                dtoSummaryConsolidateDataList.addConsolidateData(DTOCollectionSummaryConsolidateData(DTOCollectionSummaryConsolidateData.RowType.TEXT, data.label !!, data.count !!, data.amount !!))
            }
            baseAdapterItemArrayList.add(BaseAdapterItem(AdapterItemHandler.AdapterItemViewType.SUMMARY_CONSOLIDATE_DATA_LIST.ordinal, dtoSummaryConsolidateDataList))
            mBaseAdapter !!.updateAdapterItemList(baseAdapterItemArrayList)
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


    //    /**
    //     * Method to get the List of the User Data
    //     *
    //     * @return
    //     */
    //    private val summaryUserDataList : ArrayList<BaseAdapterItem<*>>
    //        get() {
    //            Tracer.debug(TAG, "getSummaryUserDataList: ")
    //            val baseAdapterItemList = ArrayList<BaseAdapterItem<*>>()
    //            val adapterViewType = AdapterItemHandler.AdapterItemViewType.SUMMARY_USER_DATA.ordinal
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9582XXXX85", "7640", "2018/03/11", "72368726", "AEPS", "SUCCESS")))
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("8282XXXX98", "17640", "2018/03/11", "72368726", "AEPS", "SUCCESS")))
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9152XXXX56", "27640", "2018/03/11", "72368726", "UPI", "SUCCESS")))
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9182XXXX11", "37640", "2018/03/11", "72368726", "QR-CODE", "FAILED")))
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9282XXXX44", "76340", "2018/03/11", "72368726", "UPI", "SUCCESS")))
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9872XXXX92", "76240", "2018/03/11", "72368726", "QR-CODE", "SUCCESS")))
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9992XXXX89", "76340", "2018/03/11", "72368726", "AEPS", "FAILED")))
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9512XXXX85", "7640", "2018/03/11", "72368726", "AEPS", "SUCCESS")))
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("8212XXXX98", "17640", "2018/03/11", "72368726", "AEPS", "SUCCESS")))
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9112XXXX56", "27640", "2018/03/11", "72368726", "UPI", "SUCCESS")))
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9112XXXX11", "37640", "2018/03/11", "72368726", "QR-CODE", "FAILED")))
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9212XXXX44", "76340", "2018/03/11", "72368726", "UPI", "SUCCESS")))
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9812XXXX92", "76240", "2018/03/11", "72368726", "QR-CODE", "SUCCESS")))
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9912XXXX89", "76340", "2018/03/11", "72368726", "AEPS", "FAILED")))
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9522XXXX85", "7640", "2018/03/11", "72368726", "AEPS", "SUCCESS")))
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("8222XXXX98", "17640", "2018/03/11", "72368726", "AEPS", "SUCCESS")))
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9122XXXX56", "27640", "2018/03/11", "72368726", "UPI", "SUCCESS")))
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9122XXXX11", "37640", "2018/03/11", "72368726", "QR-CODE", "FAILED")))
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9222XXXX44", "76340", "2018/03/11", "72368726", "UPI", "SUCCESS")))
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9822XXXX92", "76240", "2018/03/11", "72368726", "QR-CODE", "SUCCESS")))
    //            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9922XXXX89", "76340", "2018/03/11", "72368726", "AEPS", "FAILED")))
    //            return baseAdapterItemList
    //        }

    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        return inflater !!.inflate(R.layout.fragment_collection_summary, container, false)
    }

    override fun onViewCreated(view : View?, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Tracer.debug(TAG, "onViewCreated: ")
        setTitle()
        init()
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

        }
    }

    /**
     * Method to set the Activity Title
     */
    private fun setTitle() {
        Tracer.debug(TAG, "setTitle: ")
        if (activity is OnBaseActivityListener) {
            (activity as OnBaseActivityListener).onBaseActivitySetScreenTitle(getString(R.string.screen_title_collection_summary))
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
        val recyclerViewUserData = view !!.findViewById<View>(R.id.fragment_collection_summary_recycler_view) as RecyclerView
        mBaseAdapter = BaseAdapter(AdapterItemHandler())
        recyclerViewUserData.adapter = mBaseAdapter
        recyclerViewUserData.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        fetchCollectionSummary()
    }

    /**
     * Method to fetch collection summary
     */
    private fun fetchCollectionSummary() {
        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(activity, getString(R.string.endpoint_collection_summary), date)
        val publicKey = getString(R.string.public_key)
        val dtoCollectionSummaryRequest = DTOCollectionSummaryRequest(token !!, timeStamp, publicKey, PreferenceData.getUserType(activity), PreferenceData.getLoginMerchantId(activity), PreferenceData.getLoginAgentId(activity))
        Utils.showLoadingDialog(activity)
        mAppNetworkTaskProvider !!.collectionSummaryTask(activity, dtoCollectionSummaryRequest, mCollectionSummaryNetworkCallBack)
    }
}
