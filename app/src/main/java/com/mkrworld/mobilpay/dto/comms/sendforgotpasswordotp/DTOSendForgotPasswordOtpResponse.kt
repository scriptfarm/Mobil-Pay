package com.mkrworld.mobilpay.dto.comms.sendforgotpasswordotp

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 27/3/18.
 */

class DTOSendForgotPasswordOtpResponse {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOSendForgotPasswordOtpResponse"
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
        return mData?:Data()
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
        @SerializedName("agent_id")
        var agentId : String? = null
            get() {
                Tracer.debug(TAG, "getUserId : ")
                return field ?: "0"
            }
    }
}
