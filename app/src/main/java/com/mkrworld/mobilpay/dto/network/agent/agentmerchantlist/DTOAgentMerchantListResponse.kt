package com.mkrworld.mobilpay.dto.network.agent.agentmerchantlist

import com.google.gson.annotations.SerializedName
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 27/3/18.
 */

class DTOAgentMerchantListResponse {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOAgentDetailResponse"
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
         * Method to get the Merchant Id
         *
         * @return
         */
        @SerializedName("merchant_id")
        var merchantId : String? = null
            get() {
                return field ?: ""
            }

        /**
         * Method to get the Merchant Name
         *
         * @return
         */
        @SerializedName("merchantName")
        var merchantName : String? = null
            get() {
                return field ?: ""
            }
    }
}
