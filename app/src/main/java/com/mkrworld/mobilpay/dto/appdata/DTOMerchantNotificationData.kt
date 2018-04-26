package com.mkrworld.mobilpay.dto.appdata

import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 14/3/18.
 * Class to hold the mData of the Merchant Home Tab
 */
class DTOMerchantNotificationData {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOMerchantNotificationData"
    }

    private var mIsChecked : Boolean? = null
    private var mId : Int? = null
    private var mLabel : String? = null

    /**
     * Constructor
     *
     * @param id
     * @param label
     */
    constructor(id : Int, label : String) {
        Tracer.debug(TAG, "DTOMerchantNotificationData: $label")
        mId = id
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
    val id : Int
        get() = mId ?: 0

    /**
     * Method to set the Checked State
     */
    var isChecked : Boolean
        get() = mIsChecked ?: false
        set(value) {
            mIsChecked = value
        }


    override fun equals(obj : Any?) : Boolean {
        if (obj is DTOMerchantNotificationData) {
            val dtoMerchantHomeTab = obj as DTOMerchantNotificationData?
            if (dtoMerchantHomeTab !!.mId == mId) {
                return true
            }
        }
        return false
    }

    override fun hashCode() : Int {
        return mLabel !!.hashCode()
    }
}
