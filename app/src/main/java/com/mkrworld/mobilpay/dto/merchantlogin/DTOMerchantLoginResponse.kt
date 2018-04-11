package com.mkrworld.mobilpay.dto.merchantlogin

import com.google.gson.annotations.SerializedName
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 27/3/18.
 */

class DTOMerchantLoginResponse {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOMerchantLoginResponse"
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

        @SerializedName("user_id")
        private val mUserId : String? = null

        @SerializedName("nupay_id")
        private val mNupayId : String? = null

        @SerializedName("name")
        private val mName : String? = null

        @SerializedName("email")
        private val mEmail : String? = null

        @SerializedName("phone")
        private val mPhone : String? = null

        @SerializedName("merchant_logo")
        private val mMerchantLogo : String? = null

        @SerializedName("is_merchant_active")
        private val mIsMerchantActive : String? = null

        /**
         * Method to get the Email Id
         *
         * @return
         */
        val email : String
            get() = mEmail ?: ""

        /**
         * Method to check weather the Merchant is active or not
         *
         * @return
         */
        val isMerchantActive : Boolean
            get() = mIsMerchantActive?.trim { it <= ' ' }?.endsWith("1") ?: false

        /**
         * Method to get the Logo
         *
         * @return
         */
        val merchantLogo : String
            get() = mMerchantLogo ?: ""

        /**
         * Method to get the Name
         *
         * @return
         */
        val name : String
            get() = mName ?: ""

        /**
         * Method to get the Nupay Id
         *
         * @return
         */
        val nupayId : String
            get() = mNupayId ?: ""

        /**
         * Method to get the Phone Number
         *
         * @return
         */
        val phone : String
            get() = mPhone ?: ""

        /**
         * Method to get the User Id
         *
         * @return
         */
        val userId : String
            get() = mUserId ?: ""
    }
}
