package com.mkrworld.mobilpay.dto.network.mobilenumberstatus

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 27/3/18.
 */

class DTOMobileNumberStatusResponse {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOMobileNumberStatusResponse"
    }

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
        return mData !!
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
    inner class Data {

        /**
         * Method to get Status
         *
         * @return
         */
        @SerializedName("status")
        var status : String? = null
            get() {
                Tracer.debug(TAG, "getStatus : ")
                return field ?: "0"
            }
    }
}
