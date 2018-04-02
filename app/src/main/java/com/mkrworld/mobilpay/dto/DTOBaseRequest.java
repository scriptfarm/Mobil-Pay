package com.mkrworld.mobilpay.dto;

import com.google.gson.annotations.SerializedName;
import com.mkrworld.mobilpay.BuildConfig;

/**
 * Created by mkr on 27/3/18.
 */

public class DTOBaseRequest {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOBaseRequest";

    @SerializedName("token")
    private String mToken;

    @SerializedName("timestamp")
    private String mTimeStamp;

    @SerializedName("public_key")
    private String mPublicKey;

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     */
    public DTOBaseRequest(String token, String timeStamp, String publicKey) {
        mToken = token;
        mTimeStamp = timeStamp;
        mPublicKey = publicKey;
    }
}
