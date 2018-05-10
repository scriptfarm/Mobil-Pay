package com.mkrworld.mobilpay.dto.network.sendnotification

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 27/3/18.
 */

class DTOSendNotificationRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOSendNotificationRequest"
    }

    @SerializedName("agentList")
    private var mAgentList : ArrayList<String>

    @SerializedName("agentMessage")
    private var mAgentMessage : String? = null

    @SerializedName("unPaidID")
    private var mUnpaidId : ArrayList<String>

    @SerializedName("partialPaidID")
    private var mPartialPaidId : ArrayList<String>

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param userType
     * @param merchantId
     * @param agentId
     * @param agentList
     * @param agentMessage
     * @param unpaidId
     * @param partialPaidId
     */
    constructor(token : String, timeStamp : String, publicKey : String, userType : String, merchantId : String, agentId : String, agentList : ArrayList<String>, agentMessage : String, unpaidId : ArrayList<String>, partialPaidId : ArrayList<String>) : super(token, timeStamp, publicKey, userType, merchantId, agentId) {
        Tracer.debug(TAG, "DTOAgentDetailRequest : ")
        mAgentList = agentList
        mAgentMessage = agentMessage
        mUnpaidId = unpaidId
        mPartialPaidId = partialPaidId
    }
}
