package com.mkrworld.mobilpay.dto.agent.agentlogout

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 27/3/18.
 */

class DTOAgentLogoutRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOAgentLogoutRequest"
    }

    @SerializedName("agent_id")
    private var mAagentId : String?=null

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param agentId
     */
    constructor(token : String, timeStamp : String, publicKey : String, agentId : String) : super(token, timeStamp, publicKey) {
        Tracer.debug(TAG, "DTOAgentLogoutRequest : ")
        mAagentId = agentId
    }
}
