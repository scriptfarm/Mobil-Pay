package com.mkrworld.mobilpay.dto.merchantaddsendbill

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.merchantdetails.DTOMerchantDetailByNupayIdResponse

/**
 * Created by mkr on 27/3/18.
 */

class DTOMerchantSendBillResponse {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOMerchantSendBillResponse"
    }

    @SerializedName("message")
    private var mMessage : String? = null

    @SerializedName("data")
    private var mData : DTOMerchantSendBillResponse.Data? = null

    /**
     * Method to get the API Data
     *
     * @return
     */
    fun getData() : DTOMerchantSendBillResponse.Data {
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

        @SerializedName("user_nupay_id")
        private var mUserNupayId : String? = null

        @SerializedName("bill_number")
        private var mBillNumber : String? = null

        @SerializedName("amount")
        private var mAmount : String? = null

        @SerializedName("due_date")
        private var mDueDate : String? = null

        @SerializedName("bill_id")
        private var mBillId : String? = null

        @SerializedName("ref_transaction_id")
        private var mRefTransactionId : String? = null


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
         * Method to get the Bill Id
         *
         * @return
         */
        val billId : String
            get() {
                Tracer.debug(TAG, "getBillId : ")
                return mBillId ?: ""
            }

        /**
         * Method to get the due date
         *
         * @return
         */
        val dueDate : String
            get() {
                Tracer.debug(TAG, "getDueDate : ")
                return mDueDate ?: ""
            }

        /**
         * Method to get the Ref Transaction Id
         *
         * @return
         */
        val refTransactionId : String
            get() = mRefTransactionId ?: ""

        /**
         * Method to get the User New Pay Id
         *
         * @return
         */
        val userNupayId : String
            get() {
                Tracer.debug(TAG, "getUserNupayId : ")
                return mUserNupayId ?: ""
            }
    }
}
