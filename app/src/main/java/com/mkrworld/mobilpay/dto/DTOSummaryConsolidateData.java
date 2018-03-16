package com.mkrworld.mobilpay.dto;

import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;

/**
 * Created by mkr on 14/3/18.
 * Class to hold the data of the Merchant Summary Consolidate Data
 */
public class DTOSummaryConsolidateData {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOSummaryConsolidateData";

    /**
     * TYPE OF THE TAB ON THE HOME SCREEN
     */
    public enum RowType {
        NONE, TITLE, TEXT
    }

    private RowType mRowType;
    private String mMode;
    private String mCount;
    private String mAmount;

    /**
     * Constructor
     *
     * @param rowType
     * @param mode
     * @param count
     * @param amount
     */
    public DTOSummaryConsolidateData(RowType rowType, String mode, String count, String amount) {
        Tracer.debug(TAG, "DTOSummaryConsolidateData: " + rowType + "  " + mode);
        mRowType = rowType;
        mMode = mode;
        mCount = count;
        mAmount = amount;
    }

    /**
     * Method to get the mode
     *
     * @return
     */
    public String getMode() {
        return mMode != null ? mMode : "";
    }

    /**
     * Method to get the txn count
     *
     * @return
     */
    public String getCount() {
        return mCount != null ? mCount : "0";
    }

    /**
     * Method to get the amount
     *
     * @return
     */
    public String getAmount() {
        return mAmount != null ? mAmount : "0";
    }

    /**
     * Method to get the RowType
     *
     * @return
     */
    public RowType getRowType() {
        return mRowType != null ? mRowType : RowType.NONE;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DTOSummaryConsolidateData) {
            DTOSummaryConsolidateData dtoMerchantHomeTab = (DTOSummaryConsolidateData) obj;
            if (dtoMerchantHomeTab.mRowType == mRowType && dtoMerchantHomeTab.mMode.equalsIgnoreCase(mMode)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return mMode.hashCode();
    }
}
