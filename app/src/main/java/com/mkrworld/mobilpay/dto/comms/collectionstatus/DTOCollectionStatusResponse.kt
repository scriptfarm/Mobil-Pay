package com.mkrworld.mobilpay.dto.comms.collectionstatus

import com.google.gson.annotations.SerializedName
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 27/3/18.
 */

class DTOCollectionStatusResponse {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOCollectionStatusResponse"
    }

    @SerializedName("message")
    private var mMessage : String? = null

    @SerializedName("data")
    private var mData : ArrayList<Data>? = null

    /**
     * Method to get the API Data
     *
     * @return
     */
    fun getData() : ArrayList<Data> {
        return mData ?: ArrayList<Data>()
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
         * Method to get the Label
         *
         * @return
         */
        @SerializedName("label")
        var label : String? = null
            get() {
                return field ?: ""
            }

        /**
         * Method to get the count
         *
         * @return
         */
        @SerializedName("count")
        var count : String? = null
            get() {
                return field ?: "0"
            }

        /**
         * Method to get the Mode
         *
         * @return
         */
        @SerializedName("mode")
        var mode : String? = null
            get() {
                return field ?: ""
            }
    }
}
