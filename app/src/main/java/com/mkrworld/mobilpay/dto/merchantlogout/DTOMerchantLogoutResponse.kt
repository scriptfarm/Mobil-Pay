package com.mkrworld.mobilpay.dto.merchantlogout

import com.google.gson.annotations.SerializedName
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 27/3/18.
 */

class DTOMerchantLogoutResponse {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOMerchantLoginResponse"
    }

    @SerializedName("message")
    private var mMessage : String? = null

    @SerializedName("mData")
    private var mData : Boolean? = null

    /**
     * Method to get the API Data
     *
     * @return
     */
    fun getData() : Boolean {
        return mData!!
    }

    /**
     * Method to get the API Message
     *
     * @return
     */
    fun getMessage() : String {
        return mMessage ?: ""
    }
}
