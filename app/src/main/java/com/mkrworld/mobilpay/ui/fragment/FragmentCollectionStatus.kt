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
import com.mkrworld.mobilpay.dto.appdata.DTOCollectionStatusConsolidateData
import com.mkrworld.mobilpay.dto.appdata.DTOStatusConsolidateDataList
import com.mkrworld.mobilpay.dto.network.collectionstatus.DTOCollectionStatusRequest
import com.mkrworld.mobilpay.dto.network.collectionstatus.DTOCollectionStatusResponse
import com.mkrworld.mobilpay.provider.network.AppNetworkTaskProvider
import com.mkrworld.mobilpay.ui.adapter.AdapterItemHandler
import com.mkrworld.mobilpay.utils.PreferenceData
import com.mkrworld.mobilpay.utils.Utils
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by mkr on 15/3/18.
 */

class FragmentCollectionStatus : Fragment(), OnBaseFragmentListener, View.OnClickListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentAgentCollectionStatus"
    }

    private var mBaseAdapter : BaseAdapter? = null
    private var mAppNetworkTaskProvider : AppNetworkTaskProvider? = null
    private val mCollectionStatusNetworkCallBack = object : NetworkCallBack<DTOCollectionStatusResponse> {
        override fun onSuccess(dto : DTOCollectionStatusResponse) {
            Utils.dismissLoadingDialog()
            if (view == null) {
                return
            }
            if (dto == null || dto.getData() == null || dto.getData() !!.size <= 0) {
                Tracer.showSnack(view !!, R.string.no_data_fetch_from_server)
                return
            }
            // RECYCLER VIEW DATA
            val baseAdapterItemArrayList = ArrayList<BaseAdapterItem<*>>()
            val dtoStatusConsolidateDataList = DTOStatusConsolidateDataList()
            val dataList : ArrayList<DTOCollectionStatusResponse.Data> = dto.getData()
            var count : Int = 0
            for (data : DTOCollectionStatusResponse.Data in dataList) {
                try {
                    count += data.count !!.toInt()
                } catch (e : Exception) {
                    Tracer.error(TAG, "onSuccess : " + e.message)
                }
                dtoStatusConsolidateDataList.addConsolidateData(DTOCollectionStatusConsolidateData(DTOCollectionStatusConsolidateData.RowType.TEXT, data.label !!, data.count !!))
            }
            dtoStatusConsolidateDataList.addConsolidateData(0, DTOCollectionStatusConsolidateData(DTOCollectionStatusConsolidateData.RowType.TITLE, getString(R.string.total_bill_sent), "" + count))
            baseAdapterItemArrayList.add(BaseAdapterItem(AdapterItemHandler.AdapterItemViewType.STATUS_CONSOLIDATE_DATA_LIST.ordinal, dtoStatusConsolidateDataList))
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

    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        return inflater !!.inflate(R.layout.fragment_collection_status, container, false)
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
            (activity as OnBaseActivityListener).onBaseActivitySetScreenTitle(getString(R.string.screen_title_collection_status))
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
        val recyclerViewUserData = view !!.findViewById<View>(R.id.fragment_collection_status_recycler_view) as RecyclerView
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
        val token = Utils.createToken(activity, getString(R.string.endpoint_collection_status), date)
        val publicKey = getString(R.string.public_key)
        val dtoCollectionStatusRequest = DTOCollectionStatusRequest(token !!, timeStamp, publicKey, PreferenceData.getUserType(activity), PreferenceData.getLoginMerchantId(activity), PreferenceData.getLoginAgentId(activity))
        Utils.showLoadingDialog(activity)
        mAppNetworkTaskProvider !!.collectionStatusTask(activity, dtoCollectionStatusRequest, mCollectionStatusNetworkCallBack)
    }
}
