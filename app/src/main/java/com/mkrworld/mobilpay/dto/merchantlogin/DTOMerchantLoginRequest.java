package com.mkrworld.mobilpay.dto.merchantlogin;

import com.google.gson.annotations.SerializedName;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.dto.DTOBaseRequest;

/**
 * Created by mkr on 27/3/18.
 */

public class DTOMerchantLoginRequest extends DTOBaseRequest {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOMerchantLoginResponse";

    @SerializedName("user_id")
    private String mUserId;

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
     * @param userId
     * @param password
     * @param pushId
     * @param gcmId
     */
    public DTOMerchantLoginRequest(String token, String timeStamp, String publicKey, String userId, String password, String pushId, String gcmId) {
        super(token, timeStamp, publicKey);
        mUserId = userId;
        mPassword = password;
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
