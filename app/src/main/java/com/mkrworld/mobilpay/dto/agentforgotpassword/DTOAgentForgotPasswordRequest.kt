package com.mkrworld.mobilpay.dto.agentforgotpassword

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 2/4/18.
 */

class DTOAgentForgotPasswordRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOAgentForgotPasswordRequest"
    }

    @SerializedName("agent_id")
    private var mAgentId : String? = null

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
     * @param agentId
     * @param password
     * @param otp
     * @param imei
     */
    constructor(token : String, timeStamp : String, publicKey : String, agentId : String, password : String, otp : String, imei : String) : super(token, timeStamp, publicKey) {
        Tracer.debug(TAG, "DTOAgentForgotPasswordRequest : ")
        mAgentId = agentId
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
