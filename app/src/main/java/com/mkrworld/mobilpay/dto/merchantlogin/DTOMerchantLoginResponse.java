package com.mkrworld.mobilpay.dto.merchantlogin;

import com.google.gson.annotations.SerializedName;
import com.mkrworld.mobilpay.BuildConfig;

/**
 * Created by mkr on 27/3/18.
 */

public class DTOMerchantLoginResponse {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOMerchantLoginResponse";

    @SerializedName("message")
    private String mMessage;

    @SerializedName("data")
    private Data mData;

    /**
     * Method to get the API Message
     *
     * @return
     */
    public String getMessage() {
        return mMessage;
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
    private class Data {

        @SerializedName("user_id")
        private String mUserId;

        @SerializedName("nupay_id")
        private String mNupayId;

        @SerializedName("name")
        private String mName;

        @SerializedName("email")
        private String mEmail;

        @SerializedName("phone")
        private String mPhone;

        @SerializedName("merchant_logo")
        private String mMerchantLogo;

        @SerializedName("is_merchant_active")
        private String mIsMerchantActive;

        /**
         * Method to get the Email Id
         *
         * @return
         */
        public String getEmail() {
            return mEmail;
        }

        /**
         * Method to check weather the Merchant is active or not
         *
         * @return
         */
        public boolean isMerchantActive() {
            if (mIsMerchantActive == null) {
                return false;
            }
            return mIsMerchantActive.trim().endsWith("1");
        }

        /**
         * Method to get the Logo
         *
         * @return
         */
        public String getMerchantLogo() {
            return mMerchantLogo;
        }

        /**
         * Method to get the Name
         *
         * @return
         */
        public String getName() {
            return mName;
        }

        /**
         * Method to get the Nupay Id
         *
         * @return
         */
        public String getNupayId() {
            return mNupayId;
        }

        /**
         * Method to get the Phone Number
         *
         * @return
         */
        public String getPhone() {
            return mPhone;
        }

        /**
         * Method to get the User Id
         *
         * @return
         */
        public String getUserId() {
            return mUserId;
        }
    }
}
