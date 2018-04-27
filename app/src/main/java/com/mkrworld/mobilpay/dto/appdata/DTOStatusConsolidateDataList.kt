package com.mkrworld.mobilpay.dto.appdata

import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import java.util.*

/**
 * Created by mkr on 14/3/18.
 * Class to hold the mData of the Merchant Summary Consolidate Data
 */
class DTOStatusConsolidateDataList {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOStatusConsolidateDataList"
    }

    val dtoCollectionStatusConsolidateDataList : ArrayList<DTOCollectionStatusConsolidateData>

    init {
        Tracer.debug(TAG, "DTOSummaryConsolidateDataList: ")
        dtoCollectionStatusConsolidateDataList = ArrayList()
    }

    /**
     * Method to add consolidate mData Item
     *
     * @param dtoCollectionStatusConsolidateData
     */
    fun addConsolidateData(dtoCollectionStatusConsolidateData : DTOCollectionStatusConsolidateData) {
        Tracer.debug(TAG, "addConsolidateData: $dtoCollectionStatusConsolidateData")
        dtoCollectionStatusConsolidateDataList.add(dtoCollectionStatusConsolidateData)
    }

    /**
     * Method to add consolidate mData Item
     *@param position
     * @param dtoCollectionStatusConsolidateData
     */
    fun addConsolidateData(position : Int, dtoCollectionStatusConsolidateData : DTOCollectionStatusConsolidateData) {
        Tracer.debug(TAG, "addConsolidateData: $dtoCollectionStatusConsolidateData")
        dtoCollectionStatusConsolidateDataList.add(position, dtoCollectionStatusConsolidateData)
    }

    /**
     * Method to add consolidate mData Item List
     *
     * @param dtoCollectionStatusConsolidateDataList
     */
    fun addConsolidateDataList(dtoCollectionStatusConsolidateDataList : ArrayList<DTOCollectionStatusConsolidateData>?) {
        Tracer.debug(TAG, "addData: " + dtoCollectionStatusConsolidateDataList !!)
        if (dtoCollectionStatusConsolidateDataList != null) {
            this.dtoCollectionStatusConsolidateDataList.addAll(dtoCollectionStatusConsolidateDataList)
        }
    }
}
