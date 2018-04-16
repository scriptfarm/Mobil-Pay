package com.mkrworld.mobilpay.dto.agentlogin

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 27/3/18.
 */

class DTOAgentLoginRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOAgentLoginRequest"
    }

    @SerializedName("agent_id")
    private var mAgentId : String?=null

    @SerializedName("password")
    private var mPassword : String?=null

    @SerializedName("extra")
    private var mExtra : Extra?=null

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param agentId
     * @param password
     * @param pushId
     * @param gcmId
     */
    constructor(token : String, timeStamp : String, publicKey : String, agentId : String, password : String, pushId : String, gcmId : String) : super(token, timeStamp, publicKey) {
        Tracer.debug(TAG, "DTOAgentLoginRequest : ")
        mAgentId = agentId
        mPassword = password
        mExtra = Extra(pushId, gcmId)
    }


    private inner class Extra {

        @SerializedName("push_id")
        private var mPushId : String?=null

        @SerializedName("gcm_id")
        private var mGcmId : String?=null

        /**
         * Constructor
         *
         * @param pushId
         * @param gcmId
         */
        constructor(pushId : String, gcmId : String) {
            mPushId = pushId
            mGcmId = gcmId
        }

    }
}
