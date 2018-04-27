package com.mkrworld.mobilpay.dto.appdata

import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 14/3/18.
 * Class to hold the mData of the Merchant Summary Consolidate Data
 */
class DTOCollectionStatusConsolidateData {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOCollectionStatusConsolidateData"
    }

    private var mRowType : RowType? = null
    private var mMode : String? = null
    private var mCount : String? = null

    /**
     * Constructor
     *
     * @param rowType
     * @param mode
     * @param count
     */
    constructor(rowType : RowType, mode : String, count : String) {
        Tracer.debug(TAG, "DTOCollectionSummaryConsolidateData: $rowType  $mode")
        mRowType = rowType
        mMode = mode
        mCount = count
    }

    /**
     * Method to get the mode
     *
     * @return
     */
    val mode : String
        get() = mMode ?: ""

    /**
     * Method to get the txn count
     *
     * @return
     */
    val count : String
        get() = mCount ?: "0"

    /**
     * Method to get the RowType
     *
     * @return
     */
    val rowType : RowType
        get() = mRowType ?: RowType.NONE

    /**
     * TYPE OF THE TAB ON THE HOME SCREEN
     */
    enum class RowType {
        NONE, TITLE, TEXT
    }

    override fun equals(obj : Any?) : Boolean {
        if (obj is DTOCollectionStatusConsolidateData) {
            val dtoMerchantHomeTab = obj as DTOCollectionStatusConsolidateData?
            if (dtoMerchantHomeTab !!.mRowType == mRowType && dtoMerchantHomeTab.mMode !!.equals(mMode !!, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    override fun hashCode() : Int {
        return mMode !!.hashCode()
    }
}
