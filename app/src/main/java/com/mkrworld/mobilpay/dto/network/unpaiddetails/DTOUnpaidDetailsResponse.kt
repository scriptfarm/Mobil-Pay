package com.mkrworld.mobilpay.dto.network.unpaiddetails

import com.google.gson.annotations.SerializedName
import com.mkrworld.mobilpay.BuildConfig

class DTOUnpaidDetailsResponse {
    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOUnpaidDetailsResponse"
    }

    @SerializedName("message")
    private var mMessage: String? = null

    @SerializedName("data")
    private var mData: ArrayList<Data>? = null

    /**
     * Method to get the API Data
     *
     * @return
     */
    fun getData(): ArrayList<Data> {
        return mData ?: ArrayList<Data>()
    }

    /**
     * Method to get the API Message
     *
     * @return
     */
    fun getMessage(): String {
        return mMessage ?: ""
    }

    /**
     * Class to hold the Response Data
     */
    inner class Data {

        @SerializedName("bill_amount")
        var billAmount: String? = null
            get() {
                return field ?: ""
            }

        @SerializedName("first_name")
        var firstName: String? = null
            get() {
                return field ?: ""
            }

        @SerializedName("middle_name")
        var middleName: String? = null
            get() {
                return field ?: ""
            }

        @SerializedName("last_name")
        var lastName: String? = null
            get() {
                return field ?: ""
            }

        @SerializedName("mobile")
        var mobile: String? = null
            get() {
                return field ?: ""
            }

        @SerializedName("user_id")
        var userId: String? = null
            get() {
                return field ?: ""
            }
    }
}