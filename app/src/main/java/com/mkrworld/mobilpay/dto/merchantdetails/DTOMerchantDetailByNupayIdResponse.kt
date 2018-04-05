package com.mkrworld.mobilpay.dto.merchantdetails

import android.content.ContentValues.TAG
import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 27/3/18.
 */

class DTOMerchantDetailByNupayIdResponse {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOMerchantDetailByNupayIdResponse"
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

        @SerializedName("id")
        private val mId : String? = null

        @SerializedName("name")
        private val mName : String? = null

        @SerializedName("username")
        private val mUserName : String? = null

        @SerializedName("phone")
        private val mPhone : String? = null

        @SerializedName("nupay_id")
        private val mNupayId : String? = null

        @SerializedName("password")
        private val mPassword : String? = null

        @SerializedName("parent_id")
        private val mParentId : String? = null

        @SerializedName("Person_Designation")
        private val mPersonDesignation : String? = null

        @SerializedName("Contact_Person_Name")
        private val mContactPersonName : String? = null

        @SerializedName("merchant_logo")
        private val mMerchantLogo : String? = null

        @SerializedName("created_on")
        private val mCreatedOn : String? = null

        @SerializedName("updated_on")
        private val mUpdateOn : String? = null

        @SerializedName("status")
        private val mStatus : String? = null

        @SerializedName("loggedin")
        private val mLoggedIn : String? = null

        @SerializedName("jobno")
        private val mJobno : String? = null

        @SerializedName("gender")
        private val mGender : String? = null

        @SerializedName("qr_code")
        private val mQrCode : String? = null

        @SerializedName("bill_duration")
        private val mBillDuration : String? = null

        @SerializedName("is_merchant_active")
        private val mIsMerchantActive : String? = null

        @SerializedName("is_atom_pay")
        private val mIsAtomPay : String? = null

        @SerializedName("role_id")
        private val mRoleId : String? = null

        @SerializedName("active_payment_modes")
        private val mActivePaymentMode : String? = null

        @SerializedName("state_id")
        private val mStateId : String? = null

        @SerializedName("address")
        private val mAddress : String? = null

        /**
         * Method to get the UserName of the Merchant
         *
         * @return
         */
        val userName : String
            get() {
                Tracer.debug(TAG, "getUserName : ")
                return getString(mUserName)
            }

        /**
         * Method to get the Phone of the Merchant
         *
         * @return
         */
        val phone : String
            get() {
                Tracer.debug(TAG, "getPhone : ")
                return getString(mPhone)
            }

        /**
         * Method to get the Nupay Id of the Merchant
         *
         * @return
         */
        val nupayId : String
            get() {
                Tracer.debug(TAG, "getNupayId : ")
                return getString(mNupayId)
            }

        /**
         * Method to get the Name of the Merchant
         *
         * @return
         */
        val name : String
            get() {
                Tracer.debug(TAG, "getName : ")
                return getString(mName)
            }

        /**
         * Method to get the Merchant Logo
         *
         * @return
         */
        val merchantLogo : String
            get() {
                Tracer.debug(TAG, "getMerchantLogo : ")
                return getString(mMerchantLogo)
            }

        /**
         * Method to check weather the Merchant is Active or not
         *
         * @return
         */
        val isMerchantActive : String
            get() {
                Tracer.debug(TAG, "getIsMerchantActive : ")
                return getString(mIsMerchantActive)
            }

        /**
         * Method to get the Active Payment Mode of the Merchant
         *
         * @return
         */
        val activePaymentMode : String
            get() {
                Tracer.debug(TAG, "getActivePaymentMode : ")
                return getString(mActivePaymentMode)
            }

        /**
         * Method to get the Address of the Merchant
         *
         * @return
         */
        val address : String
            get() {
                Tracer.debug(TAG, "getAddress : ")
                return getString(mAddress)
            }

        /**
         * Method to get the Bill duration
         *
         * @return
         */
        val billDuration : String
            get() {
                Tracer.debug(TAG, "getBillDuration : ")
                return getString(mBillDuration)
            }

        /**
         * Method to get the Contact Person of the Merchant
         *
         * @return
         */
        val contactPersonName : String
            get() {
                Tracer.debug(TAG, "getContactPersonName : ")
                return getString(mContactPersonName)
            }

        /**
         * Method to get the Creation date of the Merchant
         *
         * @return
         */
        val createdOn : String
            get() {
                Tracer.debug(TAG, "getCreatedOn : ")
                return getString(mCreatedOn)
            }

        /**
         * Method to get the Gender of the Merchant
         *
         * @return
         */
        val gender : String
            get() {
                Tracer.debug(TAG, "getGender : ")
                return getString(mGender)
            }

        /**
         * Method to get the Id of the Merchant
         *
         * @return
         */
        val id : String
            get() {
                Tracer.debug(TAG, "getId : ")
                return getString(mId)
            }

        /**
         * Method to know weather te Pay is ATOM pay or not of the Merchant
         *
         * @return
         */
        val isAtomPay : String
            get() {
                Tracer.debug(TAG, "getIsAtomPay : ")
                return getString(mIsAtomPay)
            }

        /**
         * Method to get the Jobno of the Merchant
         *
         * @return
         */
        val jobno : String
            get() {
                Tracer.debug(TAG, "getJobno : ")
                return getString(mJobno)
            }

        /**
         * Method to get the Loggin status of the Merchant
         *
         * @return
         */
        val loggedIn : String
            get() {
                Tracer.debug(TAG, "getLoggedIn : ")
                return getString(mLoggedIn)
            }

        /**
         * Method to get the ParentId of the Merchant
         *
         * @return
         */
        val parentId : String
            get() {
                Tracer.debug(TAG, "getParentId : ")
                return getString(mParentId)
            }

        /**
         * Method to get the Password of the Merchant
         *
         * @return
         */
        val password : String
            get() {
                Tracer.debug(TAG, "getPassword : ")
                return getString(mPassword)
            }

        /**
         * Method to get the Designation of the Merchant
         *
         * @return
         */
        val personDesignation : String
            get() {
                Tracer.debug(TAG, "getPersonDesignation : ")
                return getString(mPersonDesignation)
            }

        /**
         * Method to get the QRCode of the Merchant
         *
         * @return
         */
        val qrCode : String
            get() {
                Tracer.debug(TAG, "getQrCode : ")
                return getString(mQrCode)
            }

        /**
         * Method to get the RoleId of the Merchant
         *
         * @return
         */
        val roleId : String
            get() {
                Tracer.debug(TAG, "getRoleId : ")
                return getString(mRoleId)
            }

        /**
         * Method to get the status id of the Merchant
         *
         * @return
         */
        val stateId : String
            get() {
                Tracer.debug(TAG, "getStateId : ")
                return getString(mStateId)
            }

        /**
         * Method to get the status of the Merchant
         *
         * @return
         */
        val status : String
            get() {
                Tracer.debug(TAG, "getStatus : ")
                return getString(mStatus)
            }

        /**
         * Method to get the Update date of the Merchant
         *
         * @return
         */
        val updateOn : String
            get() {
                Tracer.debug(TAG, "getUpdateOn : ")
                return getString(mUpdateOn)
            }

        /**
         * Metho dto return empty string if this string is null
         *
         * @param s
         * @return
         */
        private fun getString(s : String?) : String {
            return s ?: ""
        }
    }
}
