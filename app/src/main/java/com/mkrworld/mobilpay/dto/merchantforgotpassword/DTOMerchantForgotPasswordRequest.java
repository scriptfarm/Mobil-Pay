package com.mkrworld.mobilpay.dto.merchantforgotpassword;

import com.google.gson.annotations.SerializedName;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.dto.DTOBaseRequest;

/**
 * Created by mkr on 2/4/18.
 */

public class DTOMerchantForgotPasswordRequest extends DTOBaseRequest {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOMerchantForgotPasswordRequest";

    @SerializedName("nupay_id")
    private String mNupayId;

    @SerializedName("otp")
    private String mOTP;

    @SerializedName("password")
    private String mPassword;

    @SerializedName("extra")
    private Extra mExtra;

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param nupayId
     * @param password
     * @param otp
     * @param imei
     */
    public DTOMerchantForgotPasswordRequest(String token, String timeStamp, String publicKey, String nupayId, String password, String otp, String imei) {
        super(token, timeStamp, publicKey);
        Tracer.debug(TAG, "DTOMerchantForgotPasswordRequest : ");
        mNupayId = nupayId;
        mPassword = password;
        mOTP = otp;
        mExtra = new Extra(imei);
    }

    /**
     * Inner class Data
     */
    private class Extra {

        @SerializedName("imei")
        private String mImei;

        public Extra(String imei) {
            mImei = imei;
        }
    }
}
