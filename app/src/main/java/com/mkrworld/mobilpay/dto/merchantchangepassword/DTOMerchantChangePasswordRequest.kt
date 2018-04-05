package com.mkrworld.mobilpay.dto.merchantchangepassword

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 27/3/18.
 */

class DTOMerchantChangePasswordRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOMerchantChangePasswordRequest"
    }

    @SerializedName("nupay_id")
    private var mNepayId : String? = null

    @SerializedName("old_password")
    private var mOldPassword : String? = null

    @SerializedName("new_password")
    private var mNewPassword : String? = null

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
    constructor(token : String, timeStamp : String, publicKey : String, nupayId : String, oldPassword : String, newPassword : String) : super(token, timeStamp, publicKey) {
        Tracer.debug(TAG, "DTOMerchantChangePasswordRequest : ")
        mNepayId = nupayId
        mOldPassword = oldPassword
        mNewPassword = newPassword
    }
}
