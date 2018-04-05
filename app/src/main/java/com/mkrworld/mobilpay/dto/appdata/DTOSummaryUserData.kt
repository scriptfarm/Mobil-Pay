package com.mkrworld.mobilpay.dto.appdata

import android.content.ContentValues.TAG
import com.mkrworld.androidlib.BuildConfig
import com.mkrworld.androidlib.utils.Tracer

/**
 * Created by mkr on 14/3/18.
 * Class to hold the mData of the Merchant Summary Consolidate Data
 */
class DTOSummaryUserData {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOSummaryUserData"
    }

    private var mNumber : String?=null
    private var mAmount : String?=null
    private var mDate : String?=null
    private var mTxnNumber : String?=null
    private var mMode : String?=null
    private var mTxnStatus : String?=null

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
    constructor(number : String, amount : String, date : String, txnNumber : String, mode : String, txnStatus : String) {
        Tracer.debug(TAG, "DTOSummaryUserData: $number  $mode  $amount")
        mNumber = number
        mAmount = amount
        mDate = date
        mTxnNumber = txnNumber
        mMode = mode
        mTxnStatus = txnStatus
    }

    /**
     * Method to get the mode
     *
     * @return
     */
    val mode : String
        get() = mMode ?: ""

    /**
     * Method to get the amount
     *
     * @return
     */
    val amount : String
        get() = mAmount ?: "0"

    /**
     * Method to get the txn date
     *
     * @return
     */
    val date : String
        get() = mDate ?: ""

    /**
     * Method to get the customer number
     *
     * @return
     */
    val number : String
        get() = mNumber ?: "XXXXXXXXXX"

    /**
     * Method to get the tnx number
     *
     * @return
     */
    val txnNumber : String
        get() = mTxnNumber ?: ""

    /**
     * Method to get the txn status
     *
     * @return
     */
    val txnStatus : String
        get() = mTxnStatus ?: "SUCCESS"

    override fun equals(obj : Any?) : Boolean {
        if (obj is DTOSummaryUserData) {
            val dtoMerchantHomeTab = obj as DTOSummaryUserData?
            if (dtoMerchantHomeTab !!.mNumber !!.equals(mNumber !!, ignoreCase = true) && dtoMerchantHomeTab.mMode !!.equals(mMode !!, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    override fun hashCode() : Int {
        return mNumber !!.hashCode()
    }
}
