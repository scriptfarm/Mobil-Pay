package com.mkrworld.mobilpay.dto.merchantdetails;

import com.google.gson.annotations.SerializedName;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.dto.DTOBaseRequest;

/**
 * Created by mkr on 27/3/18.
 */

public class DTOMerchantDetailByNewpayIdRequest extends DTOBaseRequest {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOMerchantDetailByNewpayIdRequest";

    @SerializedName("merchant_nupay_id")
    private String mNupayId;

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param nupayId
     */
    public DTOMerchantDetailByNewpayIdRequest(String token, String timeStamp, String publicKey, String nupayId) {
        super(token, timeStamp, publicKey);
        mNupayId = nupayId;
    }
}
