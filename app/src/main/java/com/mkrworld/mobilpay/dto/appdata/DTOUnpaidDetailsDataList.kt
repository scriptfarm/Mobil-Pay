package com.mkrworld.mobilpay.dto.appdata

import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import java.util.*

class DTOUnpaidDetailsDataList {
    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOUnpaidDetailsDataList"
    }

    val dtoUnpaidDetailsConsolidatedList: ArrayList<DTOUnpaidDetailsData>

    init {
        Tracer.debug(TAG, "DTOUnpaidDetailsDataList: ")
        dtoUnpaidDetailsConsolidatedList = ArrayList()
    }

    /**
     * Method to add consolidate mData Item
     *
     * @param dtoUnpaidDetailsData
     */
    fun addConsolidateData(dtoUnpaidDetailsData: DTOUnpaidDetailsData) {
        Tracer.debug(TAG, "addConsolidateData: $dtoUnpaidDetailsData")
        dtoUnpaidDetailsConsolidatedList.add(dtoUnpaidDetailsData)
    }

    /**
     * Method to add consolidate mData Item List
     *
     * @param dtoUnpaidDetailsDataList
     */
    fun addConsolidateDataList(dtoUnpaidDetailsDataList: ArrayList<DTOUnpaidDetailsData>?) {
        Tracer.debug(TAG, "addData: " + dtoUnpaidDetailsDataList!!)
        if (dtoUnpaidDetailsDataList != null) {
            this.dtoUnpaidDetailsConsolidatedList.addAll(dtoUnpaidDetailsDataList)
        }
    }
}