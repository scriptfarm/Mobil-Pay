package com.mkrworld.mobilpay.dto.agent.agentfetchbill

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 27/3/18.
 */

class DTOAgentFetchBillResponse {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOAgentFetchBillResponse"
    }

    @SerializedName("message")
    private var mMessage : String? = null

    @SerializedName("data")
    private var mData : DTOAgentFetchBillResponse.Data? = null

    /**
     * Method to get the API Data
     *
     * @return
     */
    fun getData() : DTOAgentFetchBillResponse.Data {
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
         * Method to get the Agent Id
         *
         * @return
         */
        @SerializedName("agent_id")
        var agentId : String? = null
            get() {
                Tracer.debug(TAG, "getUserId : ")
                return field ?: ""
            }

        /**
         * Method to get the User Id
         *
         * @return
         */
        @SerializedName("user_id")
        var userId : String? = null
            get() {
                Tracer.debug(TAG, "getUserId : ")
                return field ?: ""
            }

        /**
         * Method to get the Bill Amount
         *
         * @return
         */
        @SerializedName("bill_amount")
        var billAmount : String? = null
            get() {
                Tracer.debug(TAG, "getBillAmount : ")
                return field ?: "0"
            }

        /**
         * Method to get the Bill Number
         *
         * @return
         */
        @SerializedName("bill_no")
        var billNumber : String? = null
            get() {
                Tracer.debug(TAG, "getBillNumber : ")
                return field ?: "0"
            }

        /**
         * Method to get the Bill Number
         *
         * @return
         */
        @SerializedName("bill_detail")
        var billDetail : String? = null
            get() {
                Tracer.debug(TAG, "getBillNumber : ")
                return field ?: ""
            }

        /**
         * Method to get the Bill Creation Date
         *
         * @return
         */
        @SerializedName("bill_creation_date")
        var billCreationDate : String? = null
            get() {
                Tracer.debug(TAG, "getBillCreationDate : ")
                return field ?: ""
            }

        /**
         * Method to get the Bill Due Date
         *
         * @return
         */
        @SerializedName("bill_due_date")
        var billDueDate : String? = null
            get() {
                Tracer.debug(TAG, "getBillDueDate : ")
                return field ?: ""
            }

        /**
         * Method to get the Method to get the Payment Type
         *
         * @return
         */
        @SerializedName("payment_type")
        var paymentType : String? = null
            get() {
                Tracer.debug(TAG, "getPaymentType : ")
                return field ?: "FULL"
            }

        /**
         * Method to get the Minimum Payment
         *
         * @return
         */
        @SerializedName("min_payment")
        var minPayment : String? = null
            get() {
                Tracer.debug(TAG, "getMinPayment : ")
                return field ?: "0"
            }

        /**
         * Var to hold the actual amount should be paid by the Customer
         */
        var amountPending : String? = null
            get() {
                try {
                    return "" + (billAmount !!.toInt() - minPayment !!.toInt())
                } catch (e : Exception) {
                    return billAmount
                }
            }
    }
}
