package com.mkrworld.mobilpay.dto.agent.agentsendbill

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 2/4/18.
 */

class DTOAgentSendBillRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOAgentSendBillRequest"
    }

    @SerializedName("user_id")
    private var mUserId : String? = null

    @SerializedName("bill_no")
    private var mBillNo : String? = null

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param userType
     * @param merchantId
     * @param agentId
     * @param userId
     * @param billNo
     */
    constructor(token : String, timeStamp : String, publicKey : String, userType : String, merchantId : String, agentId : String, userId : String, billNo : String) : super(token, timeStamp, publicKey, userType, merchantId, agentId) {
        Tracer.debug(TAG, "DTOAgentFetchBillRequest : ")
        mUserId = userId
        mBillNo = billNo
    }
}
