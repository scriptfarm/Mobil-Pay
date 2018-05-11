package com.mkrworld.mobilpay.dto.appdata

import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 14/3/18.
 * Class to hold the mData of the Merchant Home Tab
 */
class DTONotificationTab {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTONotificationTab"
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
    constructor(tabType : TabType, label : String) {
        Tracer.debug(TAG, "Constructor: $tabType  $label")
        this.tabType = tabType
        this.label = label
    }

    /**
     * TYPE OF THE TAB ON THE HOME SCREEN
     */
    enum class TabType {
        NONE, SEND_TO_ALL, SEND_TO_AGENTS, SEND_TO_CUSTOMER
    }

    override fun equals(obj : Any?) : Boolean {
        if (obj is DTONotificationTab) {
            val dto = obj as DTONotificationTab?
            if (dto !!.tabType == tabType && dto.label !!.equals(label !!, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    override fun hashCode() : Int {
        return tabType !!.hashCode()
    }
}
