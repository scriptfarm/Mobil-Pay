package com.mkrworld.mobilpay.dto.agent.agentfcm

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 27/3/18.
 */

class DTOAgentFCMRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOAgentFCMRequest"
    }

    @SerializedName("fcm_id")
    private var mFCMKey : String

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     */
    constructor(token : String, timeStamp : String, publicKey : String, userType : String, merchantId : String, agentId : String, fcmKey : String) : super(token, timeStamp, publicKey, userType, merchantId, agentId) {
        Tracer.debug(TAG, "DTOAgentDetailRequest : ")
        mFCMKey = fcmKey
    }
}
