package com.mkrworld.mobilpay.dto.merchantlogout;

import com.google.gson.annotations.SerializedName;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.dto.DTOBaseRequest;

/**
 * Created by mkr on 27/3/18.
 */

public class DTOMerchantLogoutRequest extends DTOBaseRequest {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOMerchantLogoutRequest";

    @SerializedName("nupay_id")
    private String mNupayId;

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param nupayId
     */
    public DTOMerchantLogoutRequest(String token, String timeStamp, String publicKey, String nupayId) {
        super(token, timeStamp, publicKey);
        mNupayId = nupayId;
    }
}
