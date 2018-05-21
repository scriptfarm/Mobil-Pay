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
import com.mkrworld.mobilpay.dto.appdata.DTOUnpaidDetailsData
import com.mkrworld.mobilpay.dto.appdata.DTOUnpaidDetailsDataList
import com.mkrworld.mobilpay.dto.network.unpaiddetails.DTOUnpaidDetailsRequest
import com.mkrworld.mobilpay.dto.network.unpaiddetails.DTOUnpaidDetailsResponse
import com.mkrworld.mobilpay.provider.network.AppNetworkTaskProvider
import com.mkrworld.mobilpay.ui.adapter.AdapterItemHandler
import com.mkrworld.mobilpay.utils.PreferenceData
import com.mkrworld.mobilpay.utils.Utils
import java.util.*

class FragmentUnPaidDetails : Fragment(), OnBaseFragmentListener, View.OnClickListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentUnPaidDetails"
    }

    private var mBaseAdapter: BaseAdapter? = null
    private var mAppNetworkTaskProvider: AppNetworkTaskProvider? = null
    private val mUnpaidDetailsNetworkCallBack = object : NetworkCallBack<DTOUnpaidDetailsResponse> {
        override fun onSuccess(dto: DTOUnpaidDetailsResponse) {
            Utils.dismissLoadingDialog()
            if (view == null) {
                return
            }
            if (dto == null || dto.getData() == null || dto.getData()!!.size <= 0) {
                Tracer.showSnack(view!!, R.string.no_data_fetch_from_server)
                return
            }
            // RECYCLER VIEW DATA
            val baseAdapterItemArrayList = ArrayList<BaseAdapterItem<*>>()
            val dtoUnpaidDetailsDataList = DTOUnpaidDetailsDataList()
            val dataList: ArrayList<DTOUnpaidDetailsResponse.Data> = dto.getData()
            dtoUnpaidDetailsDataList.addConsolidateData(DTOUnpaidDetailsData(DTOUnpaidDetailsData.RowType.TITLE, getString(R.string.user_id),
                    getString(R.string.user_name), getString(R.string.amount_caps), getString(R.string.date)))
            for (data: DTOUnpaidDetailsResponse.Data in dataList) {
                dtoUnpaidDetailsDataList.addConsolidateData(DTOUnpaidDetailsData(DTOUnpaidDetailsData.RowType.TEXT, data.userId!!,
                        data.firstName!! + data.lastName!!, data.billAmount!!, data.billDueDate!!))
            }
            baseAdapterItemArrayList.add(BaseAdapterItem(AdapterItemHandler.AdapterItemViewType.UNPAID_DETAILS_DATA_LIST.ordinal, dtoUnpaidDetailsDataList))
            mBaseAdapter!!.updateAdapterItemList(baseAdapterItemArrayList)
        }

        override fun onError(errorMessage: String, errorCode: Int) {
            Utils.dismissLoadingDialog()
            Tracer.debug(TAG, "onError : ")
            if (view == null) {
                return
            }
            Tracer.showSnack(view!!, errorMessage)
            activity.onBackPressed()
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_unpaid_details, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Tracer.debug(TAG, "onViewCreated: ")
        setHasOptionsMenu(true)
        setTitle()
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
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

    override fun onBackPressed(): Boolean {
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

    override fun onClick(view: View) {
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
            (activity as OnBaseActivityListener).onBaseActivitySetScreenTitle(getString(R.string.screen_title_unpaid_details_summary))
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
        val recyclerViewUserData = view!!.findViewById<View>(R.id.fragment_unpaid_details_recycler_view) as RecyclerView
        mBaseAdapter = BaseAdapter(AdapterItemHandler())
        recyclerViewUserData.adapter = mBaseAdapter
        recyclerViewUserData.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        fetchUnpaidDetailsSummary()
    }

    /**
     * Method to fetch collection summary
     */
    private fun fetchUnpaidDetailsSummary() {
        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(activity, getString(R.string.url_unpaid_details), date)
        val publicKey = getString(R.string.public_key)
        val dtoUnpaidDetailsRequest = DTOUnpaidDetailsRequest(token!!, timeStamp, publicKey, PreferenceData.getUserType(activity), PreferenceData.getLoginMerchantId(activity),
                PreferenceData.getLoginAgentId(activity))
        Utils.showLoadingDialog(activity)
        mAppNetworkTaskProvider!!.unpaidDetailsTask(activity, dtoUnpaidDetailsRequest, mUnpaidDetailsNetworkCallBack)
    }
}
