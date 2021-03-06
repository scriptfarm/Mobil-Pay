package com.mkrworld.mobilpay.ui.fragment.merchant

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.mkrworld.androidlib.callback.OnBaseActivityListener
import com.mkrworld.androidlib.callback.OnBaseFragmentListener
import com.mkrworld.androidlib.ui.adapter.BaseAdapter
import com.mkrworld.androidlib.ui.adapter.BaseAdapterItem
import com.mkrworld.androidlib.ui.adapter.BaseViewHolder
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTOHomeTab
import com.mkrworld.mobilpay.provider.fragment.FragmentProvider
import com.mkrworld.mobilpay.provider.fragment.FragmentTag
import com.mkrworld.mobilpay.ui.adapter.AdapterItemHandler
import com.mkrworld.mobilpay.ui.adapter.GridSpacingItemDecoration
import java.util.*

/**
 * Created by mkr on 13/3/18.
 */

class FragmentMerchantHome : Fragment(), OnBaseFragmentListener, BaseViewHolder.VHClickable {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentAgentHome"
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
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOHomeTab(DTOHomeTab.TabType.SEND_BILL, R.drawable.ic_send_bill, getString(R.string.send_bill))))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOHomeTab(DTOHomeTab.TabType.AEPS_COLLECT, R.drawable.ic_aeps_collect, getString(R.string.aeps_collect))))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOHomeTab(DTOHomeTab.TabType.UPI_COLLECT, R.drawable.ic_upi_collect, getString(R.string.upi_collect))))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOHomeTab(DTOHomeTab.TabType.SEND_NOTIFICATION, R.drawable.ic_static_qr_code, getString(R.string.send_notification))))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOHomeTab(DTOHomeTab.TabType.DYNAMIC_QR_CODE, R.drawable.ic_qr_code, getString(R.string.dynamic_qr_code))))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOHomeTab(DTOHomeTab.TabType.COLLECTION_SUMMARY, R.drawable.ic_collection_summary, getString(R.string.collection_summary))))
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, DTOHomeTab(DTOHomeTab.TabType.COLLECTION_STATUS, R.drawable.ic_static_qr_code, getString(R.string.collection_status))))
            return baseAdapterItemList
        }

    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        Tracer.debug(TAG, "onCreateView: ")
        return inflater !!.inflate(R.layout.fragment_merchant_home, container, false)
    }

    override fun onViewCreated(view : View?, savedInstanceState : Bundle?) {
        Tracer.debug(TAG, "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setTitle()
        init()
    }

    override fun onCreateOptionsMenu(menu : Menu?, inflater : MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.menu_nothing, menu);
        super.onCreateOptionsMenu(menu, inflater)
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
                        //                        val bundle = Bundle()
                        //                        bundle.putString(FragmentAgentQrCode.EXTRA_QR_CODE_TITLE, PreferenceData.getUserType(activity))
                        //                        val fragment = FragmentProvider.getFragment(FragmentTag.AGENT_QR_CODE)
                        //                        (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment!!, bundle, true, FragmentTag.AGENT_QR_CODE)
                    }
                    DTOHomeTab.TabType.DYNAMIC_QR_CODE -> if (activity is OnBaseActivityListener) {
                        //                        val fragment = FragmentProvider.getFragment(FragmentTag.AGENT_QR_CODE_GENERATOR)
                        //                        (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment !!, null, true, FragmentTag.AGENT_QR_CODE_GENERATOR)
                    }
                    DTOHomeTab.TabType.SEND_NOTIFICATION -> if (activity is OnBaseActivityListener) {
                        val fragment = FragmentProvider.getFragment(FragmentTag.SEND_NOTIFICATION)
                        (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment !!, null, true, FragmentTag.SEND_NOTIFICATION)
                    }
                    DTOHomeTab.TabType.SEND_BILL -> if (activity is OnBaseActivityListener) {
                        val fragment = FragmentProvider.getFragment(FragmentTag.MERCHANT_SEND_BILL)
                        (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment !!, null, true, FragmentTag.MERCHANT_SEND_BILL)
                    }
                    DTOHomeTab.TabType.UPI_COLLECT -> {
                    }
                    DTOHomeTab.TabType.AEPS_COLLECT -> if (activity is OnBaseActivityListener) {
                        //                        val fragment = FragmentProvider.getFragment(FragmentTag.AGENT_AEPS_COLLECT)
                        //                        (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment !!, null, true, FragmentTag.AGENT_AEPS_COLLECT)
                    }
                    DTOHomeTab.TabType.COLLECTION_SUMMARY -> if (activity is OnBaseActivityListener) {
                        val fragment = FragmentProvider.getFragment(FragmentTag.COLLECTION_SUMMARY)
                        (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment !!, null, true, FragmentTag.COLLECTION_SUMMARY)
                    }
                    DTOHomeTab.TabType.COLLECTION_STATUS -> if (activity is OnBaseActivityListener) {
                        val fragment = FragmentProvider.getFragment(FragmentTag.COLLECTION_STATUS)
                        (activity as OnBaseActivityListener).onBaseActivityAddFragment(fragment !!, null, true, FragmentTag.COLLECTION_STATUS)
                    }
                }
            }
        }
    }

    /**
     * Method to set the Activity Title
     */
    private fun setTitle() {
        Tracer.debug(TAG, "setTitle: ")
        if (activity is OnBaseActivityListener) {
            (activity as OnBaseActivityListener).onBaseActivitySetScreenTitle(getString(R.string.screen_title_merchant_home))
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
        // INIT the RecyclerView
        val recyclerView = view !!.findViewById<View>(R.id.fragment_merchant_home_recycler_view) as RecyclerView
        val baseAdapter = BaseAdapter(AdapterItemHandler())
        recyclerView.adapter = baseAdapter
        val gridLayoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = gridLayoutManager
        baseAdapter.updateAdapterItemList(homeTabList)
        val colorDivider = ContextCompat.getColor(activity, R.color.divider_color)
        val gridSpacingItemDecoration = GridSpacingItemDecoration(2, resources.getDimensionPixelOffset(R.dimen.divider_size), colorDivider, false)
        recyclerView.addItemDecoration(gridSpacingItemDecoration)
        baseAdapter.setVHClickCallback(this)
    }
}
