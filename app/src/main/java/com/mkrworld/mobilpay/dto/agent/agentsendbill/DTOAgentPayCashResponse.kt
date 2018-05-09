package com.mkrworld.mobilpay.dto.agent.agentsendbill

import com.google.gson.annotations.SerializedName
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 27/3/18.
 */

class DTOAgentPayCashResponse {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOFetchBillResponse"
    }

    @SerializedName("message")
    private var mMessage : String? = null

    @SerializedName("data")
    private var mData : DTOAgentPayCashResponse.Data? = null

    /**
     * Method to get the API Data
     *
     * @return
     */
    fun getData() : DTOAgentPayCashResponse.Data {
        return mData ?: Data()
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
        // DO NOTHING
    }
}
