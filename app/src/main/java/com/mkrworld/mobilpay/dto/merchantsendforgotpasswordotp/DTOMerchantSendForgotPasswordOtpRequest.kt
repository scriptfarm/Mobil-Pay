package com.mkrworld.mobilpay.dto.merchantsendforgotpasswordotp

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 2/4/18.
 */

class DTOMerchantSendForgotPasswordOtpRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOMerchantSendForgotPasswordOtpRequest"
    }

    @SerializedName("user_id")
    private var mUserId : String? = null

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param userId
     */
    constructor(token : String, timeStamp : String, publicKey : String, userId : String) : super(token, timeStamp, publicKey) {
        Tracer.debug(TAG, "DTOMerchantSendForgotPasswordOtpRequest : ")
        mUserId = userId
    }
}
