package com.mkrworld.mobilpay.dto.merchantqrcodegenarator

import android.content.ContentValues.TAG
import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 27/3/18.
 */

class DTOMerchantQRCodeGeneratorResponse {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOMerchantQRCodeGeneratorResponse"
    }

    @SerializedName("message")
    private var mMessage : String? = null

    @SerializedName("mData")
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
    inner class Data {

        @SerializedName("amount")
        private val mAmount : String? = null

        @SerializedName("details")
        private val mDetails : String? = null

        @SerializedName("bill_number")
        private val mBillNumber : String? = null

        @SerializedName("qr_code_token")
        private val mQrCodeToken : String? = null

        /**
         * Method to get the Bill Amount
         *
         * @return
         */
        val amount : String
            get() {
                Tracer.debug(TAG, "getAmount : ")
                return mAmount ?: "0"
            }

        /**
         * Metho dto get the Bill Number
         *
         * @return
         */
        val billNumber : String
            get() {
                Tracer.debug(TAG, "getBillNumber : ")
                return mBillNumber ?: ""
            }

        /**
         * Method to get the Bill Details
         *
         * @return
         */
        val details : String
            get() {
                Tracer.debug(TAG, "getDetails : ")
                return mDetails ?: ""
            }

        /**
         * Method to get the QR Code Token
         *
         * @return
         */
        val qrCodeToken : String
            get() {
                Tracer.debug(TAG, "getQrCodeToken : ")
                return mQrCodeToken ?: ""
            }
    }
}
