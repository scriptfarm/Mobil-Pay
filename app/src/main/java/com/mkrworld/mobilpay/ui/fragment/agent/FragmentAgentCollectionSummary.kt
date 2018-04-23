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
import com.mkrworld.androidlib.ui.adapter.BaseAdapter
import com.mkrworld.androidlib.ui.adapter.BaseAdapterItem
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTOSummaryConsolidateData
import com.mkrworld.mobilpay.dto.appdata.DTOSummaryConsolidateDataList
import com.mkrworld.mobilpay.dto.appdata.DTOSummaryUserData
import com.mkrworld.mobilpay.ui.adapter.AdapterItemHandler

import java.util.ArrayList

/**
 * Created by mkr on 15/3/18.
 */

class FragmentAgentCollectionSummary : Fragment(), OnBaseFragmentListener, View.OnClickListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentAgentCollectionSummary"
    }

    /**
     * Method to get the List of the Consolidate Data
     *
     * @return
     */
    private val summaryConsolidateDataList : DTOSummaryConsolidateDataList
        get() {
            Tracer.debug(TAG, "getSummaryConsolidateDataList: ")
            val dtoSummaryConsolidateDataList = DTOSummaryConsolidateDataList()
            dtoSummaryConsolidateDataList.addConsolidateData(DTOSummaryConsolidateData(DTOSummaryConsolidateData.RowType.TITLE, getString(R.string.mode_caps), getString(R.string.txns_caps), getString(R.string.amount_caps)))
            dtoSummaryConsolidateDataList.addConsolidateData(DTOSummaryConsolidateData(DTOSummaryConsolidateData.RowType.TEXT, getString(R.string.aeps_caps), "10", "1,00,000"))
            dtoSummaryConsolidateDataList.addConsolidateData(DTOSummaryConsolidateData(DTOSummaryConsolidateData.RowType.TEXT, getString(R.string.upi_caps), "20", "20,000"))
            dtoSummaryConsolidateDataList.addConsolidateData(DTOSummaryConsolidateData(DTOSummaryConsolidateData.RowType.TEXT, getString(R.string.qr_code_caps), "30", "30,000"))
            dtoSummaryConsolidateDataList.addConsolidateData(DTOSummaryConsolidateData(DTOSummaryConsolidateData.RowType.TITLE, getString(R.string.total_caps), "60", "1,50,000"))
            return dtoSummaryConsolidateDataList
        }

    /**
     * Method to get the List of the User Data
     *
     * @return
     */
    private val summaryUserDataList : ArrayList<BaseAdapterItem<*>>
        get() {
            Tracer.debug(TAG, "getSummaryUserDataList: ")
            val baseAdapterItemList = ArrayList<BaseAdapterItem<*>>()
            val adapterViewType = AdapterItemHandler.AdapterItemViewType.SUMMARY_USER_DATA.ordinal
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9582XXXX85", "7640", "2018/03/11", "72368726", "AEPS", "SUCCESS")))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("8282XXXX98", "17640", "2018/03/11", "72368726", "AEPS", "SUCCESS")))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9152XXXX56", "27640", "2018/03/11", "72368726", "UPI", "SUCCESS")))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9182XXXX11", "37640", "2018/03/11", "72368726", "QR-CODE", "FAILED")))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9282XXXX44", "76340", "2018/03/11", "72368726", "UPI", "SUCCESS")))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9872XXXX92", "76240", "2018/03/11", "72368726", "QR-CODE", "SUCCESS")))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9992XXXX89", "76340", "2018/03/11", "72368726", "AEPS", "FAILED")))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9512XXXX85", "7640", "2018/03/11", "72368726", "AEPS", "SUCCESS")))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("8212XXXX98", "17640", "2018/03/11", "72368726", "AEPS", "SUCCESS")))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9112XXXX56", "27640", "2018/03/11", "72368726", "UPI", "SUCCESS")))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9112XXXX11", "37640", "2018/03/11", "72368726", "QR-CODE", "FAILED")))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9212XXXX44", "76340", "2018/03/11", "72368726", "UPI", "SUCCESS")))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9812XXXX92", "76240", "2018/03/11", "72368726", "QR-CODE", "SUCCESS")))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9912XXXX89", "76340", "2018/03/11", "72368726", "AEPS", "FAILED")))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9522XXXX85", "7640", "2018/03/11", "72368726", "AEPS", "SUCCESS")))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("8222XXXX98", "17640", "2018/03/11", "72368726", "AEPS", "SUCCESS")))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9122XXXX56", "27640", "2018/03/11", "72368726", "UPI", "SUCCESS")))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9122XXXX11", "37640", "2018/03/11", "72368726", "QR-CODE", "FAILED")))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9222XXXX44", "76340", "2018/03/11", "72368726", "UPI", "SUCCESS")))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9822XXXX92", "76240", "2018/03/11", "72368726", "QR-CODE", "SUCCESS")))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOSummaryUserData("9922XXXX89", "76340", "2018/03/11", "72368726", "AEPS", "FAILED")))
            return baseAdapterItemList
        }

    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        return inflater !!.inflate(R.layout.fragment_agent_collection_summary, container, false)
    }

    override fun onViewCreated(view : View?, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Tracer.debug(TAG, "onViewCreated: ")
        setTitle()
        init()
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
        if (view == null) {
            return
        }
        val recyclerViewUserData = view !!.findViewById<View>(R.id.fragment_agent_collection_summary_recycler_view) as RecyclerView
        val baseAdapterUserData = BaseAdapter(AdapterItemHandler())
        recyclerViewUserData.adapter = baseAdapterUserData
        recyclerViewUserData.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        // RECYCLER VIEW DATA
        val baseAdapterItemArrayList = ArrayList<BaseAdapterItem<*>>()
        baseAdapterItemArrayList.add(BaseAdapterItem(AdapterItemHandler.AdapterItemViewType.SUMMARY_CONSOLIDATE_DATA_LIST.ordinal, summaryConsolidateDataList))
        baseAdapterItemArrayList.addAll(summaryUserDataList)
        baseAdapterUserData.updateAdapterItemList(baseAdapterItemArrayList)
    }
}
