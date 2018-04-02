package com.mkrworld.mobilpay.dto.merchantdetails;

import com.google.gson.annotations.SerializedName;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;

/**
 * Created by mkr on 27/3/18.
 */

public class DTOMerchantDetailByNupayIdResponse {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOMerchantDetailByNupayIdResponse";

    @SerializedName("message")
    private String mMessage;

    @SerializedName("data")
    private Data mData;

    /**
     * Method to get the API Message
     *
     * @return
     */
    public String getessage() {
        return mMessage != null ? mMessage : "";
    }

    /**
     * Method to get the API Data
     *
     * @return
     */
    public Data getData() {
        return mData;
    }

    /**
     * Class to hold the Response Data
     */
    public class Data {

        @SerializedName("id")
        private String mId;

        @SerializedName("name")
        private String mName;

        @SerializedName("username")
        private String mUserName;

        @SerializedName("phone")
        private String mPhone;

        @SerializedName("nupay_id")
        private String mNupayId;

        @SerializedName("password")
        private String mPassword;

        @SerializedName("parent_id")
        private String mParentId;

        @SerializedName("Person_Designation")
        private String mPersonDesignation;

        @SerializedName("Contact_Person_Name")
        private String mContactPersonName;

        @SerializedName("merchant_logo")
        private String mMerchantLogo;

        @SerializedName("created_on")
        private String mCreatedOn;

        @SerializedName("updated_on")
        private String mUpdateOn;

        @SerializedName("status")
        private String mStatus;

        @SerializedName("loggedin")
        private String mLoggedIn;

        @SerializedName("jobno")
        private String mJobno;

        @SerializedName("gender")
        private String mGender;

        @SerializedName("qr_code")
        private String mQrCode;

        @SerializedName("bill_duration")
        private String mBillDuration;

        @SerializedName("is_merchant_active")
        private String mIsMerchantActive;

        @SerializedName("is_atom_pay")
        private String mIsAtomPay;

        @SerializedName("role_id")
        private String mRoleId;

        @SerializedName("active_payment_modes")
        private String mActivePaymentMode;

        @SerializedName("state_id")
        private String mStateId;

        @SerializedName("address")
        private String mAddress;

        /**
         * Method to get the UserName of the Merchant
         *
         * @return
         */
        public String getUserName() {
            Tracer.debug(TAG, "getUserName : ");
            return getString(mUserName);
        }

        /**
         * Method to get the Phone of the Merchant
         *
         * @return
         */
        public String getPhone() {
            Tracer.debug(TAG, "getPhone : ");
            return getString(mPhone);
        }

        /**
         * Method to get the Nupay Id of the Merchant
         *
         * @return
         */
        public String getNupayId() {
            Tracer.debug(TAG, "getNupayId : ");
            return getString(mNupayId);
        }

        /**
         * Method to get the Name of the Merchant
         *
         * @return
         */
        public String getName() {
            Tracer.debug(TAG, "getName : ");
            return getString(mName);
        }

        /**
         * Method to get the Merchant Logo
         *
         * @return
         */
        public String getMerchantLogo() {
            Tracer.debug(TAG, "getMerchantLogo : ");
            return getString(mMerchantLogo);
        }

        /**
         * Method to check weather the Merchant is Active or not
         *
         * @return
         */
        public String getIsMerchantActive() {
            Tracer.debug(TAG, "getIsMerchantActive : ");
            return getString(mIsMerchantActive);
        }

        /**
         * Method to get the Active Payment Mode of the Merchant
         *
         * @return
         */
        public String getActivePaymentMode() {
            Tracer.debug(TAG, "getActivePaymentMode : ");
            return getString(mActivePaymentMode);
        }

        /**
         * Method to get the Address of the Merchant
         *
         * @return
         */
        public String getAddress() {
            Tracer.debug(TAG, "getAddress : ");
            return getString(mAddress);
        }

        /**
         * Method to get the Bill duration
         *
         * @return
         */
        public String getBillDuration() {
            Tracer.debug(TAG, "getBillDuration : ");
            return getString(mBillDuration);
        }

        /**
         * Method to get the Contact Person of the Merchant
         *
         * @return
         */
        public String getContactPersonName() {
            Tracer.debug(TAG, "getContactPersonName : ");
            return getString(mContactPersonName);
        }

        /**
         * Method to get the Creation date of the Merchant
         *
         * @return
         */
        public String getCreatedOn() {
            Tracer.debug(TAG, "getCreatedOn : ");
            return getString(mCreatedOn);
        }

        /**
         * Method to get the Gender of the Merchant
         *
         * @return
         */
        public String getGender() {
            Tracer.debug(TAG, "getGender : ");
            return getString(mGender);
        }

        /**
         * Method to get the Id of the Merchant
         *
         * @return
         */
        public String getId() {
            Tracer.debug(TAG, "getId : ");
            return getString(mId);
        }

        /**
         * Method to know weather te Pay is ATOM pay or not of the Merchant
         *
         * @return
         */
        public String getIsAtomPay() {
            Tracer.debug(TAG, "getIsAtomPay : ");
            return getString(mIsAtomPay);
        }

        /**
         * Method to get the Jobno of the Merchant
         *
         * @return
         */
        public String getJobno() {
            Tracer.debug(TAG, "getJobno : ");
            return getString(mJobno);
        }

        /**
         * Method to get the Loggin status of the Merchant
         *
         * @return
         */
        public String getLoggedIn() {
            Tracer.debug(TAG, "getLoggedIn : ");
            return getString(mLoggedIn);
        }

        /**
         * Method to get the ParentId of the Merchant
         *
         * @return
         */
        public String getParentId() {
            Tracer.debug(TAG, "getParentId : ");
            return getString(mParentId);
        }

        /**
         * Method to get the Password of the Merchant
         *
         * @return
         */
        public String getPassword() {
            Tracer.debug(TAG, "getPassword : ");
            return getString(mPassword);
        }

        /**
         * Method to get the Designation of the Merchant
         *
         * @return
         */
        public String getPersonDesignation() {
            Tracer.debug(TAG, "getPersonDesignation : ");
            return getString(mPersonDesignation);
        }

        /**
         * Method to get the QRCode of the Merchant
         *
         * @return
         */
        public String getQrCode() {
            Tracer.debug(TAG, "getQrCode : ");
            return getString(mQrCode);
        }

        /**
         * Method to get the RoleId of the Merchant
         *
         * @return
         */
        public String getRoleId() {
            Tracer.debug(TAG, "getRoleId : ");
            return getString(mRoleId);
        }

        /**
         * Method to get the status id of the Merchant
         *
         * @return
         */
        public String getStateId() {
            Tracer.debug(TAG, "getStateId : ");
            return getString(mStateId);
        }

        /**
         * Method to get the status of the Merchant
         *
         * @return
         */
        public String getStatus() {
            Tracer.debug(TAG, "getStatus : ");
            return getString(mStatus);
        }

        /**
         * Method to get the Update date of the Merchant
         *
         * @return
         */
        public String getUpdateOn() {
            Tracer.debug(TAG, "getUpdateOn : ");
            return getString(mUpdateOn);
        }

        /**
         * Metho dto return empty string if this string is null
         *
         * @param s
         * @return
         */
        private String getString(String s) {
            return s != null ? s : "";
        }
    }
}
