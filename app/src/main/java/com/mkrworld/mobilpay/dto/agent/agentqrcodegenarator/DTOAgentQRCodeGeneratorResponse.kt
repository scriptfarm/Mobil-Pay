package com.mkrworld.mobilpay.dto.agent.agentqrcodegenarator

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 27/3/18.
 */

class DTOAgentQRCodeGeneratorResponse {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOAgentQRCodeGeneratorResponse"
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
         * Method to get the Bill Amount
         *
         * @return
         */
        @SerializedName("amount")
        var amount : String? = null
            get() {
                Tracer.debug(TAG, "getAmount : ")
                return field ?: "0"
            }

        /**
         * Metho dto get the Bill Number
         *
         * @return
         */
        @SerializedName("bill_number")
        var billNumber : String? = null
            get() {
                Tracer.debug(TAG, "getBillNumber : ")
                return field ?: ""
            }

        /**
         * Method to get the Bill Details
         *
         * @return
         */
        @SerializedName("details")
        var details : String? = null
            get() {
                Tracer.debug(TAG, "getDetails : ")
                return field ?: ""
            }

        /**
         * Method to get the QR Code Token
         *
         * @return
         */
        @SerializedName("qr_code_token")
        var qrCodeToken : String? = null
            get() {
                Tracer.debug(TAG, "getQrCodeToken : ")
                return field ?: ""
            }
    }
}
