package com.mkrworld.mobilpay.dto.agent.agentfetchbill

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 2/4/18.
 */

class DTOAgentFetchBillRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOAgentFetchBillRequest"
    }

    @SerializedName("id")
    private var mAgentId : String? = null

    @SerializedName("user_id")
    private var mUserId : String?= null

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param agentId
     * @param userId
     */
    constructor(token : String, timeStamp : String, publicKey : String, agentId : String, userId : String) : super(token, timeStamp, publicKey) {
        Tracer.debug(TAG, "DTOAgentFetchBillRequest : ")
        mAgentId = agentId
        mUserId = userId
    }
}
