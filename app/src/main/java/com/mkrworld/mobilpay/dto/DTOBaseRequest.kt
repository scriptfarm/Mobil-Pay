package com.mkrworld.mobilpay.dto

import com.google.gson.annotations.SerializedName
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 27/3/18.
 */

open class DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOBaseRequest"
    }

    @SerializedName("token")
    private var mToken : String? = null

    @SerializedName("timestamp")
    private var mTimeStamp : String? = null

    @SerializedName("public_key")
    private var mPublicKey : String? = null

    @SerializedName("user_type")
    private var mUserType : String? = null

    @SerializedName("agent_id")
    private var mAgentId : String? = null

    @SerializedName("merchant_id")
    private var mMerchantId : String? = null

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
    constructor(token : String, timeStamp : String, publicKey : String, userType : String, merchantId : String, agentId : String) {
        mToken = token
        mTimeStamp = timeStamp
        mPublicKey = publicKey
        mUserType = userType
        mMerchantId = merchantId
        mAgentId = agentId
    }
}
