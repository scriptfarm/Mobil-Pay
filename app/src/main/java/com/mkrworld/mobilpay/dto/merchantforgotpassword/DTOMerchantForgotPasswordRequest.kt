package com.mkrworld.mobilpay.dto.merchantforgotpassword

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 2/4/18.
 */

class DTOMerchantForgotPasswordRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOMerchantForgotPasswordRequest"
    }

    @SerializedName("nupay_id")
    private var mNupayId : String? = null

    @SerializedName("otp")
    private var mOTP : String? = null

    @SerializedName("password")
    private var mPassword : String? = null

    @SerializedName("extra")
    private var mExtra : Extra? = null

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
    constructor(token : String, timeStamp : String, publicKey : String, nupayId : String, password : String, otp : String, imei : String) : super(token, timeStamp, publicKey) {
        Tracer.debug(TAG, "DTOMerchantForgotPasswordRequest : ")
        mNupayId = nupayId
        mPassword = password
        mOTP = otp
        mExtra = Extra(imei)
    }

    /**
     * Inner class Data
     */
    private inner class Extra {

        @SerializedName("imei")
        private var mImei : String? = null

        constructor(imei : String) {
            mImei = imei
        }
    }
}
