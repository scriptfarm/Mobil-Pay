package com.mkrworld.mobilpay.dto.agent.agentlogout

import com.google.gson.annotations.SerializedName
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 27/3/18.
 */

class DTOAgentLogoutResponse {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOLoginResponse"
    }

    @SerializedName("message")
    private var mMessage : String? = null

    @SerializedName("data")
    private var mData : Boolean? = null

    /**
     * Method to get the API Data
     *
     * @return
     */
    fun getData() : Boolean {
        return mData ?: false
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
