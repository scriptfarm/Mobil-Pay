package com.mkrworld.mobilpay.dto.appdata

import android.content.ContentValues.TAG
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

    val dtoSummaryConsolidateDataList : ArrayList<DTOSummaryConsolidateData>

    init {
        Tracer.debug(TAG, "DTOSummaryConsolidateDataList: ")
        dtoSummaryConsolidateDataList = ArrayList()
    }

    /**
     * Method to add consolidate mData Item
     *
     * @param dtoSummaryConsolidateData
     */
    fun addConsolidateData(dtoSummaryConsolidateData : DTOSummaryConsolidateData) {
        Tracer.debug(TAG, "addConsolidateData: $dtoSummaryConsolidateData")
        dtoSummaryConsolidateDataList.add(dtoSummaryConsolidateData)
    }

    /**
     * Method to add consolidate mData Item List
     *
     * @param dtoSummaryConsolidateDataList
     */
    fun addConsolidateDataList(dtoSummaryConsolidateDataList : ArrayList<DTOSummaryConsolidateData>?) {
        Tracer.debug(TAG, "addData: " + dtoSummaryConsolidateDataList !!)
        if (dtoSummaryConsolidateDataList != null) {
            this.dtoSummaryConsolidateDataList.addAll(dtoSummaryConsolidateDataList)
        }
    }
}
