package com.mkrworld.mobilpay.dto.merchantsendforgotpasswordotp;

import com.google.gson.annotations.SerializedName;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;

/**
 * Created by mkr on 27/3/18.
 */

public class DTOMerchantSendForgotPasswordOtpResponse {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOMerchantSendForgotPasswordOtpResponse";

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
    public class Data {

        @SerializedName("nupay_id")
        private String mNupayId;

        /**
         * Method to get the Nupay Id
         *
         * @return
         */
        public String getNupayId() {
            Tracer.debug(TAG, "getNupayId : ");
            return mNupayId != null ? mNupayId : "0";
        }
    }
}
