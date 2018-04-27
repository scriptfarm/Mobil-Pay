package com.mkrworld.mobilpay.ui.adapter.viewholder

import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import com.mkrworld.androidlib.ui.adapter.BaseAdapter
import com.mkrworld.androidlib.ui.adapter.BaseAdapterItem
import com.mkrworld.androidlib.ui.adapter.BaseViewHolder
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTOSummaryConsolidateDataList
import com.mkrworld.mobilpay.ui.adapter.AdapterItemHandler
import com.mkrworld.mobilpay.ui.adapter.GridSpacingItemDecoration

import java.util.ArrayList

/**
 * Created by mkr on 16/3/18.
 */

class CollectionSummaryDataItemListVH : BaseViewHolder<DTOSummaryConsolidateDataList> {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".CollectionSummaryDataItemListVH"
    }

    private var mRecyclerView : RecyclerView?=null
    private var mBaseAdapter : BaseAdapter?=null

    /**
     * Constructor
     *
     * @param itemView
     */
    constructor(itemView : View) : super(itemView) {
        Tracer.debug(TAG, "CollectionSummaryDataItemListVH: ")
        val colorDivider = ContextCompat.getColor(getContext(), R.color.divider_color)
        val dividerHeight = getContext().resources.getDimensionPixelOffset(R.dimen.divider_size)
        mRecyclerView = itemView.findViewById<View>(R.id.item_card_recycler_view) as RecyclerView
        mBaseAdapter = BaseAdapter(AdapterItemHandler())
        mRecyclerView!!.adapter = mBaseAdapter
        mRecyclerView!!.layoutManager = GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false)
        mRecyclerView!!.addItemDecoration(GridSpacingItemDecoration(1, dividerHeight, colorDivider, false))
    }

    override fun bindData(dtoSummaryConsolidateDataList : DTOSummaryConsolidateDataList) {
        Tracer.debug(TAG, "bindData: " + dtoSummaryConsolidateDataList !!)
        if (dtoSummaryConsolidateDataList == null) {
            return
        }
        mBaseAdapter!!.updateAdapterItemList(getSummaryConsolidateDataList(dtoSummaryConsolidateDataList))
    }

    /**
     * Method to get the List of the Consolidate Data
     *
     * @return
     */
    private fun getSummaryConsolidateDataList(dtoSummaryConsolidateDataList : DTOSummaryConsolidateDataList) : ArrayList<BaseAdapterItem<*>> {
        Tracer.debug(TAG, "getSummaryConsolidateDataList: ")
        val baseAdapterItemList = ArrayList<BaseAdapterItem<*>>()
        val adapterViewType = AdapterItemHandler.AdapterItemViewType.SUMMARY_CONSOLIDATE_DATA.ordinal
        for (dtoSummaryConsolidateData in dtoSummaryConsolidateDataList.dtoCollectionSummaryConsolidateDataList) {
            baseAdapterItemList.add(BaseAdapterItem(adapterViewType, dtoSummaryConsolidateData))
        }
        return baseAdapterItemList
    }
}
