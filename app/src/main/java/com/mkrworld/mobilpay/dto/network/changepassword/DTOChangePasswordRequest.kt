package com.mkrworld.mobilpay.dto.network.changepassword

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 27/3/18.
 */

class DTOChangePasswordRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOChangePasswordRequest"
    }

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
     * @param userType
     * @param merchantId
     * @param agentId
     * @param oldPassword
     * @param newPassword
     */
    constructor(token : String, timeStamp : String, publicKey : String, userType : String, merchantId : String, agentId : String, oldPassword : String, newPassword : String) : super(token, timeStamp, publicKey, userType, merchantId, agentId) {
        Tracer.debug(TAG, "DTOChangePasswordRequest : ")
        mOldPassword = oldPassword
        mNewPassword = newPassword
    }
}
