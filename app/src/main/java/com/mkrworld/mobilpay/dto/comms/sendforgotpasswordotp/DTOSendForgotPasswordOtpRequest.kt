package com.mkrworld.mobilpay.dto.comms.sendforgotpasswordotp

import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 2/4/18.
 */

class DTOSendForgotPasswordOtpRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOSendForgotPasswordOtpRequest"
    }

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param userType
     * @param merchantId
     * @param agentId
     */
    constructor(token : String, timeStamp : String, publicKey : String, userType : String, merchantId : String, agentId : String) : super(token, timeStamp, publicKey, userType, merchantId, agentId) {
        Tracer.debug(TAG, "DTOSendForgotPasswordOtpRequest : ")
    }
}
