package com.mkrworld.mobilpay.dto.agentdetails

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 27/3/18.
 */

class DTOAgentDetailRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOAgentDetailRequest"
    }

    @SerializedName("agent_id")
    private var mAgentId : String? = null

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param agentId
     */
    constructor(token : String, timeStamp : String, publicKey : String, agentId : String) : super(token, timeStamp, publicKey) {
        Tracer.debug(TAG, "DTOAgentDetailRequest : ")
        mAgentId = agentId
    }
}
