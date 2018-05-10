package com.mkrworld.mobilpay.dto.network.merchantagentlist

import com.google.gson.annotations.SerializedName
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 27/3/18.
 */

class DTOMerchantAgentListResponse {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOMobileNumberStatusResponse"
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

        @SerializedName("username")
        var userName : String = ""
            get() {
                return field ?: ""
            }

        @SerializedName("mobile_number")
        var mobileNumber : String = ""
            get() {
                return field ?: ""
            }

        @SerializedName("name")
        var name : String = ""
            get() {
                return field ?: ""
            }
    }
}
