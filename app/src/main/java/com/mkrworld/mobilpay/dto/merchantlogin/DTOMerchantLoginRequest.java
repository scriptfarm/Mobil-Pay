package com.mkrworld.mobilpay.dto.merchantlogin;

import com.google.gson.annotations.SerializedName;
import com.mkrworld.mobilpay.BuildConfig;

/**
 * Created by mkr on 27/3/18.
 */

public class DTOMerchantLoginRequest {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOMerchantLoginResponse";

    @SerializedName("token")
    private String mToken;

    @SerializedName("timestamp")
    private String mTimeStamp;

    @SerializedName("user_id")
    private String mUserId;

    @SerializedName("password")
    private String mPassword;

    @SerializedName("public_key")
    private String mPublicKey;

    @SerializedName("extra")
    private Extra mExtra;

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param userId
     * @param password
     * @param publicKey
     * @param pushId
     * @param gcmId
     */
    public DTOMerchantLoginRequest(String token, String timeStamp, String userId, String password, String publicKey, String pushId, String gcmId) {
        mToken = token;
        mTimeStamp = timeStamp;
        mUserId = userId;
        mPassword = password;
        mPublicKey = publicKey;
        mExtra = new Extra(pushId, gcmId);
    }


    private class Extra {

        @SerializedName("push_id")
        private String mPushId;

        @SerializedName("gcm_id")
        private String mGcmId;

        /**
         * Constructor
         *
         * @param pushId
         * @param gcmId
         */
        public Extra(String pushId, String gcmId) {
            mPushId = pushId;
            mGcmId = gcmId;
        }

    }

}
