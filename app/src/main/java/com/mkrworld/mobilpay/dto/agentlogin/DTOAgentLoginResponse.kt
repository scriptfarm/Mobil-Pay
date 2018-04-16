package com.mkrworld.mobilpay.dto.agentlogin

import com.google.gson.annotations.SerializedName
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 27/3/18.
 */

class DTOAgentLoginResponse {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOAgentLoginResponse"
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
    fun getData() : Data? {
        return mData
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
         * Method to get the Agent Id
         *
         * @return
         */
        @SerializedName("username")
        var agentId : String? = null
            get() = agentId ?: ""

        /**
         * Method to get the Email Id
         *
         * @return
         */
        @SerializedName("email")
        var email : String? = null
            get() = email ?: ""

        /**
         * Method to get the Name
         *
         * @return
         */
        @SerializedName("name")
        var name : String? = null
            get() = name ?: ""

        /**
         * Method to get the Mobile Number
         *
         * @return
         */
        @SerializedName("mobile_number")
        var mobileNumber : String? = null
            get() = mobileNumber ?: ""

        /**
         * Method to get the Agent Designation
         *
         * @return
         */
        @SerializedName("designation")
        var designation : String? = null
            get() = designation ?: ""

        /**
         * Method to get the Gender
         *
         * @return
         */
        @SerializedName("gender")
        var gender : String? = null
            get() = gender ?: ""

        /**
         * Method to get the Agent logo
         *
         * @return
         */
        @SerializedName("logo")
        var logo : String? = null
            get() = logo ?: ""

        /**
         * Method to get the Agent Address line 1
         *
         * @return
         */
        @SerializedName("address_line_1")
        var addressLine1 : String? = null
            get() = addressLine1 ?: ""

        /**
         * Method to get the Agent Address line 2
         *
         * @return
         */
        @SerializedName("address_line_2")
        var addressLine2 : String? = null
            get() = addressLine2 ?: ""

        /**
         * Method to get the Agent City
         *
         * @return
         */
        @SerializedName("city")
        var city : String? = null
            get() = city ?: ""

        /**
         * Method to get the Agent State
         *
         * @return
         */
        @SerializedName("state")
        var state : String? = null
            get() = state ?: ""

        /**
         * Method to get the Agent pincode
         *
         * @return
         */
        @SerializedName("pincode")
        var pincode : String? = null
            get() = pincode ?: ""

        /**
         * Method to get the Agent Correspondance Address
         *
         * @return
         */
        @SerializedName("correspondance_address")
        var correspondanceAddress : String? = null
            get() = correspondanceAddress ?: ""

        /**
         * Method to get the Agent Status
         *
         * @return
         */
        @SerializedName("status")
        var status : String? = null
            get() = status ?: ""

    }
}
