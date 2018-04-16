package com.mkrworld.mobilpay.dto.agentchangepassword

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 27/3/18.
 */

class DTOAgentChangePasswordRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOAgentChangePasswordRequest"
    }

    @SerializedName("agent_id")
    private var mAgentId : String? = null

    @SerializedName("old_password")
    private var mOldPassword : String? = null

    @SerializedName("new_password")
    private var mNewPassword : String? = null

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param agentId
     * @param oldPassword
     * @param newPassword
     */
    constructor(token : String, timeStamp : String, publicKey : String, agentId : String, oldPassword : String, newPassword : String) : super(token, timeStamp, publicKey) {
        Tracer.debug(TAG, "DTOAgentChangePasswordRequest : ")
        mAgentId = agentId
        mOldPassword = oldPassword
        mNewPassword = newPassword
    }
}
