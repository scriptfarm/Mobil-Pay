package com.mkrworld.mobilpay.dto.merchantchangepassword;

import com.google.gson.annotations.SerializedName;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.dto.DTOBaseRequest;

/**
 * Created by mkr on 27/3/18.
 */

public class DTOMerchantChangePasswordRequest extends DTOBaseRequest {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOMerchantChangePasswordRequest";

    @SerializedName("nupay_id")
    private String mNepayId;

    @SerializedName("old_password")
    private String mOldPassword;

    @SerializedName("new_password")
    private String mNewPassword;

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param nupayId
     * @param oldPassword
     * @param newPassword
     */
    public DTOMerchantChangePasswordRequest(String token, String timeStamp, String publicKey, String nupayId, String oldPassword, String newPassword) {
        super(token, timeStamp, publicKey);
        mNepayId = nupayId;
        mOldPassword = oldPassword;
        mNewPassword = newPassword;
    }
}
