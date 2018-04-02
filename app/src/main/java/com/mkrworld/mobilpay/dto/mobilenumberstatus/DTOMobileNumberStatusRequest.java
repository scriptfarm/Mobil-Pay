package com.mkrworld.mobilpay.dto.mobilenumberstatus;

import com.google.gson.annotations.SerializedName;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.dto.DTOBaseRequest;

/**
 * Created by mkr on 2/4/18.
 */

public class DTOMobileNumberStatusRequest extends DTOBaseRequest {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOMobileNumberStatusRequest";

    @SerializedName("user_id")
    private String mUserId;

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param userId
     */
    public DTOMobileNumberStatusRequest(String token, String timeStamp, String publicKey, String userId) {
        super(token, timeStamp, publicKey);
        Tracer.debug(TAG, "DTOMobileNumberStatusRequest : ");
        mUserId = userId;
    }
}
