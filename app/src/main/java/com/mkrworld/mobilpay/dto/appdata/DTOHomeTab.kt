package com.mkrworld.mobilpay.dto.appdata

import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R

/**
 * Created by mkr on 14/3/18.
 * Class to hold the mData of the Merchant Home Tab
 */
class DTOHomeTab {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOHomeTab"
    }

    private var mTabType : TabType?=null
    private var mIconResId : Int?=null
    private var mLabel : String?=null

    /**
     * Constructor
     *
     * @param tabType
     * @param iconResId
     * @param label
     */
    constructor(tabType : TabType, iconResId : Int, label : String) {
        Tracer.debug(TAG, "DTOAgentHomeTab: $tabType  $label")
        mTabType = tabType
        mIconResId = iconResId
        mLabel = label
    }

    /**
     * Method to get the label of the code
     *
     * @return
     */
    val label : String
        get() = mLabel ?: ""

    /**
     * Method to get the icon id
     *
     * @return
     */
    val iconResId : Int
        get() = mIconResId ?: R.mipmap.ic_launcher

    /**
     * Method to get the RowType
     *
     * @return
     */
    val tabType : TabType
        get() = mTabType ?: TabType.NONE

    /**
     * TYPE OF THE TAB ON THE HOME SCREEN
     */
    enum class TabType {
        NONE, STATIC_QR_CODE, DYNAMIC_QR_CODE, UPI_COLLECT, AEPS_COLLECT, SEND_BILL, COLLECTION_SUMMARY, SEND_NOTIFICATION, COLLECTION_STATUS, PAY_CASH
    }

    override fun equals(obj : Any?) : Boolean {
        if (obj is DTOHomeTab) {
            val dtoMerchantHomeTab = obj as DTOHomeTab?
            if (dtoMerchantHomeTab !!.mTabType == mTabType && dtoMerchantHomeTab.mLabel !!.equals(mLabel !!, ignoreCase = true) && dtoMerchantHomeTab.mIconResId == mIconResId) {
                return true
            }
        }
        return false
    }

    override fun hashCode() : Int {
        return mTabType !!.hashCode()
    }
}
