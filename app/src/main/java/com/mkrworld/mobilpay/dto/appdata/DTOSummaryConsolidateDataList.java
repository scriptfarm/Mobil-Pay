package com.mkrworld.mobilpay.dto.appdata;

import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;

import java.util.ArrayList;

/**
 * Created by mkr on 14/3/18.
 * Class to hold the data of the Merchant Summary Consolidate Data
 */
public class DTOSummaryConsolidateDataList {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOSummaryConsolidateDataList";
    private ArrayList<DTOSummaryConsolidateData> mDtoSummaryConsolidateDataList;

    /**
     * Constructor
     */
    public DTOSummaryConsolidateDataList() {
        Tracer.debug(TAG, "DTOSummaryConsolidateDataList: "
        );
        mDtoSummaryConsolidateDataList = new ArrayList<>();
    }

    /**
     * Method to add consolidate data Item
     *
     * @param dtoSummaryConsolidateData
     */
    public void addConsolidateData(DTOSummaryConsolidateData dtoSummaryConsolidateData) {
        Tracer.debug(TAG, "addConsolidateData: " + dtoSummaryConsolidateData);
        mDtoSummaryConsolidateDataList.add(dtoSummaryConsolidateData);
    }

    /**
     * Method to add consolidate data Item List
     *
     * @param dtoSummaryConsolidateDataList
     */
    public void addConsolidateDataList(ArrayList<DTOSummaryConsolidateData> dtoSummaryConsolidateDataList) {
        Tracer.debug(TAG, "addData: " + dtoSummaryConsolidateDataList);
        if (dtoSummaryConsolidateDataList != null) {
            mDtoSummaryConsolidateDataList.addAll(dtoSummaryConsolidateDataList);
        }
    }

    /**
     * Method to get the List of the Consolidate Data
     *
     * @return
     */
    public ArrayList<DTOSummaryConsolidateData> getDtoSummaryConsolidateDataList() {
        return mDtoSummaryConsolidateDataList;
    }
}
