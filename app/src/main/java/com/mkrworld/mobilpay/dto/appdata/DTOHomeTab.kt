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

    /**
     * Method to get the label of the code
     *
     * @return
     */
    var label : String = ""
        get() {
            return field?.trim() ?: ""
        }

    /**
     * Method to get the icon id
     *
     * @return
     */
    var iconResId : Int = R.mipmap.ic_launcher
        get() {
            return field ?: R.mipmap.ic_launcher
        }

    /**
     * Method to get the RowType
     *
     * @return
     */
    var tabType : TabType = TabType.NONE
        get() {
            return field ?: TabType.NONE
        }

    /**
     * Method to get the RowType
     *
     * @return
     */
    var isFocused : Boolean = false

    /**
     * Constructor
     *
     * @param tabType
     * @param iconResId
     * @param label
     */
    constructor(tabType : TabType, iconResId : Int, label : String) {
        Tracer.debug(TAG, "DTOAgentHomeTab: $tabType  $label")
        this.tabType = tabType
        this.iconResId = iconResId
        this.label = label
    }

    /**
     * TYPE OF THE TAB ON THE HOME SCREEN
     */
    enum class TabType {
        NONE, STATIC_QR_CODE, DYNAMIC_QR_CODE, UPI_COLLECT, AEPS_COLLECT, SEND_BILL, COLLECTION_SUMMARY, SEND_NOTIFICATION, COLLECTION_STATUS, CASH_COLLECT
    }

    override fun equals(obj : Any?) : Boolean {
        if (obj is DTOHomeTab) {
            val dtoMerchantHomeTab = obj as DTOHomeTab?
            if (dtoMerchantHomeTab !!.tabType == tabType && dtoMerchantHomeTab.label !!.equals(label !!, ignoreCase = true) && dtoMerchantHomeTab.iconResId == iconResId) {
                return true
            }
        }
        return false
    }

    override fun hashCode() : Int {
        return tabType !!.hashCode()
    }
}
