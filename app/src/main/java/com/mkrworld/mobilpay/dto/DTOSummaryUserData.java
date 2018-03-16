package com.mkrworld.mobilpay.dto;

import com.mkrworld.androidlib.BuildConfig;
import com.mkrworld.androidlib.utils.Tracer;

/**
 * Created by mkr on 14/3/18.
 * Class to hold the data of the Merchant Summary Consolidate Data
 */
public class DTOSummaryUserData {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOSummaryUserData";

    private String mNumber;
    private String mAmount;
    private String mDate;
    private String mTxnNumber;
    private String mMode;
    private String mTxnStatus;

    /**
     * Constructor
     *
     * @param number
     * @param amount
     * @param date
     * @param txnNumber
     * @param mode
     * @param txnStatus
     */
    public DTOSummaryUserData(String number, String amount, String date, String txnNumber, String mode, String txnStatus) {
        Tracer.debug(TAG, "DTOSummaryUserData: " + number + "  " + mode + "  " + amount);
        mNumber = number;
        mAmount = amount;
        mDate = date;
        mTxnNumber = txnNumber;
        mMode = mode;
        mTxnStatus = txnStatus;
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
     * Method to get the amount
     *
     * @return
     */
    public String getAmount() {
        return mAmount != null ? mAmount : "0";
    }

    /**
     * Method to get the txn date
     *
     * @return
     */
    public String getDate() {
        return mDate != null ? mDate : "";
    }

    /**
     * Method to get the customer number
     *
     * @return
     */
    public String getNumber() {
        return mNumber != null ? mNumber : "XXXXXXXXXX";
    }

    /**
     * Method to get the tnx number
     *
     * @return
     */
    public String getTxnNumber() {
        return mTxnNumber != null ? mTxnNumber : "";
    }

    /**
     * Method to get the txn status
     *
     * @return
     */
    public String getTxnStatus() {
        return mTxnStatus != null ? mTxnStatus : "SUCCESS";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DTOSummaryUserData) {
            DTOSummaryUserData dtoMerchantHomeTab = (DTOSummaryUserData) obj;
            if (dtoMerchantHomeTab.mNumber.equalsIgnoreCase(mNumber) && dtoMerchantHomeTab.mMode.equalsIgnoreCase(mMode)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return mNumber.hashCode();
    }
}
