package com.mkrworld.mobilpay.dto.appdata

import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig

import java.util.ArrayList

/**
 * Created by mkr on 14/3/18.
 * Class to hold the mData of the Merchant Summary Consolidate Data
 */
class DTOSummaryConsolidateDataList {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOSummaryConsolidateDataList"
    }

    val dtoCollectionSummaryConsolidateDataList : ArrayList<DTOCollectionSummaryConsolidateData>

    init {
        Tracer.debug(TAG, "DTOSummaryConsolidateDataList: ")
        dtoCollectionSummaryConsolidateDataList = ArrayList()
    }

    /**
     * Method to add consolidate mData Item
     *
     * @param dtoCollectionSummaryConsolidateData
     */
    fun addConsolidateData(dtoCollectionSummaryConsolidateData : DTOCollectionSummaryConsolidateData) {
        Tracer.debug(TAG, "addConsolidateData: $dtoCollectionSummaryConsolidateData")
        dtoCollectionSummaryConsolidateDataList.add(dtoCollectionSummaryConsolidateData)
    }

    /**
     * Method to add consolidate mData Item List
     *
     * @param dtoCollectionSummaryConsolidateDataList
     */
    fun addConsolidateDataList(dtoCollectionSummaryConsolidateDataList : ArrayList<DTOCollectionSummaryConsolidateData>?) {
        Tracer.debug(TAG, "addData: " + dtoCollectionSummaryConsolidateDataList !!)
        if (dtoCollectionSummaryConsolidateDataList != null) {
            this.dtoCollectionSummaryConsolidateDataList.addAll(dtoCollectionSummaryConsolidateDataList)
        }
    }
}
