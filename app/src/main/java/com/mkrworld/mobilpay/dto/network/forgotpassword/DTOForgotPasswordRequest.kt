package com.mkrworld.mobilpay.dto.network.forgotpassword

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 2/4/18.
 */

class DTOForgotPasswordRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOForgotPasswordRequest"
    }

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
     * @param password
     * @param otp
     * @param imei
     */
    constructor(token : String, timeStamp : String, publicKey : String, userType : String, merchantId : String, agentId : String, password : String, otp : String, imei : String) : super(token, timeStamp, publicKey, userType, merchantId, agentId) {
        Tracer.debug(TAG, "DTOForgotPasswordRequest : ")
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
