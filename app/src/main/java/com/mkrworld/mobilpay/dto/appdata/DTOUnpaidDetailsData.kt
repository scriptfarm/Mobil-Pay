package com.mkrworld.mobilpay.dto.appdata

import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig

class DTOUnpaidDetailsData {
    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOUnpaidDetailsData"
    }

    private var mRowType: RowType? = null
    private var mUserId: String? = null
    private var mUserName: String? = null
    private var mAmount: String? = null
    private var mDate: String? = null

    /**
     * Constructor
     *
     * @param rowType
     * @param userId
     * @param userName
     * @param amount
     */
    constructor(rowType: RowType, userId: String, userName: String, amount: String, date: String) {
        Tracer.debug(TAG, "DTOUnpaidDetailsData: $rowType  $userId $userName $amount $date")
        mRowType = rowType
        mUserId = userId
        mUserName = userName
        mAmount = amount
        mDate = date
    }

    /**
     * Method to get the user name
     *
     * @return
     */
    val userId: String
        get() = mUserId ?: ""

    /**
     * Method to get the user name
     *
     * @return
     */
    val userName: String
        get() = mUserName ?: ""

    /**
     * Method to get the amount
     *
     * @return
     */
    val amount: String
        get() = mAmount ?: "0"

    /**
     * Method to get the date
     *
     * @return
     */
    val date: String
        get() = mDate ?: ""

    /**
     * Method to get the RowType
     *
     * @return
     */
    val rowType: RowType
        get() = mRowType ?: RowType.NONE

    /**
     * TYPE OF THE TAB ON THE HOME SCREEN
     */
    enum class RowType {
        NONE, TITLE, TEXT
    }

    override fun equals(obj: Any?): Boolean {
        if (obj is DTOUnpaidDetailsData) {
            val dtoMerchantHomeTab = obj as DTOUnpaidDetailsData?
            if (dtoMerchantHomeTab!!.mRowType == mRowType && dtoMerchantHomeTab.mUserId!!.equals(mUserId!!, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    override fun hashCode(): Int {
        return mUserId!!.hashCode()
    }
}