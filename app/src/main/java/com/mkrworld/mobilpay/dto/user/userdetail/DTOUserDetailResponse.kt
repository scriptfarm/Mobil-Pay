package com.mkrworld.mobilpay.dto.user.userdetail

import com.google.gson.annotations.SerializedName
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 27/3/18.
 */

class DTOUserDetailResponse {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOUserDetailResponse"
    }

    @SerializedName("searchText")
    var searchText : String? = null
        get() {
            return field?.trim() ?: ""
        }

    @SerializedName("message")
    private var mMessage : String? = null

    @SerializedName("data")
    private var mDataList : ArrayList<Data>? = null

    /**
     * Method to get the API Data
     *
     * @return
     */
    fun getData() : ArrayList<Data> {
        return mDataList ?: ArrayList<Data>()
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
         * Method to get the User Id
         *
         * @return
         */
        @SerializedName("user_id")
        var userId : String? = null
            get() {
                return field ?: ""
            }

        /**
         * Method to get the First Name
         *
         * @return
         */
        @SerializedName("first_name")
        var firstName : String? = null
            get() {
                return field ?: ""
            }

        /**
         * Method to get the Middle Name
         *
         * @return
         */
        @SerializedName("middle_name")
        var middleName : String? = null
            get() {
                return field ?: ""
            }

        /**
         * Method to get the Last Name
         *
         * @return
         */
        @SerializedName("last_name")
        var lastName : String? = null
            get() {
                return field ?: ""
            }


        /**
         * Method to get the Gender
         *
         * @return
         */
        @SerializedName("gender")
        var gender : String? = null
            get() {
                return field ?: ""
            }

        /**
         * Method to get the Agent Address line 1
         *
         * @return
         */
        @SerializedName("address_line_1")
        var addressLine1 : String? = null
            get() {
                return field ?: ""
            }

        /**
         * Method to get the Agent Address line 2
         *
         * @return
         */
        @SerializedName("address_line_2")
        var addressLine2 : String? = null
            get() {
                return field ?: ""
            }

        /**
         * Method to get the Agent City
         *
         * @return
         */
        @SerializedName("city")
        var city : String? = null
            get() {
                return field ?: ""
            }

        /**
         * Method to get the Agent State
         *
         * @return
         */
        @SerializedName("state")
        var state : String? = null
            get() {
                return field ?: ""
            }

        /**
         * Method to get the Agent pincode
         *
         * @return
         */
        @SerializedName("pincode")
        var pincode : String? = null
            get() {
                return field ?: ""
            }

        /**
         * Method to get the Agent Correspondance Address
         *
         * @return
         */
        @SerializedName("correspondance_address")
        var correspondanceAddress : String? = null
            get() {
                return field ?: ""
            }


        /**
         * Method to get the Email Id
         *
         * @return
         */
        @SerializedName("email")
        var email : String? = null
            get() {
                return field ?: ""
            }

        /**
         * Method to get the Mobile Number
         *
         * @return
         */
        @SerializedName("mobile")
        var mobileNumber : String? = null
            get() {
                return field ?: ""
            }

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
         * Method to get the Plan Type
         *
         * @return
         */
        @SerializedName("plan_type")
        var planType : String? = null
            get() {
                return field ?: ""
            }


        /**
         * Method to get the Plan Name
         *
         * @return
         */
        @SerializedName("plan_name")
        var planName : String? = null
            get() {
                return field ?: ""
            }


        /**
         * Method to get the Plan Id
         *
         * @return
         */
        @SerializedName("plan_id")
        var planId : String? = null
            get() {
                return field ?: ""
            }

        /**
         * Method to get the Bill Id
         *
         * @return
         */
        @SerializedName("bill_id")
        var billId : String? = null
            get() {
                return field ?: ""
            }

    }
}
