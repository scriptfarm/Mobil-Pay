package com.mkrworld.mobilpay.dto.agent.agentqrcodegenarator

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 27/3/18.
 */

class DTOAgentQRCodeGeneratorRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOAgentQRCodeGeneratorRequest"
    }

    @SerializedName("amount")
    private var mAmount : String?=null

    @SerializedName("bill_number")
    private var mBillNumber : String?=null

    @SerializedName("details")
    private var mDetails : String?=null

    @SerializedName("agent_id")
    private var mAgentId : String?=null

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param amount
     * @param billNumber
     * @param details
     * @param agentId
     */
    constructor(token : String, timeStamp : String, publicKey : String, amount : String, billNumber : String, details : String, agentId : String) : super(token, timeStamp, publicKey) {
        Tracer.debug(TAG, "DTOAgentQRCodeGeneratorRequest : ")
        mAmount = amount
        mBillNumber = billNumber
        mDetails = details
        mAgentId = agentId
    }
}
