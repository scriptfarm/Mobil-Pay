package com.mkrworld.mobilpay.dto.merchantchangepassword

import com.google.gson.annotations.SerializedName
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 27/3/18.
 */

class DTOMerchantChangePasswordResponse {


    @SerializedName("message")
    private var mMessage : String? = null

    @SerializedName("data")
    private var mData : Data? = null

    /**
     * Method to get the API Data
     *
     * @return
     */
    fun getData() : Data {
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

    /**
     * Class to hold the Response Data
     */
    inner class Data // EMPTY CLASS

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOMerchantChangePasswordResponse"
    }

}
