package com.mkrworld.mobilpay.dto.network.login

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 27/3/18.
 */

class DTOLoginRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOLoginRequest"
    }

    @SerializedName("password")
    private var mPassword : String? = null

    @SerializedName("extra")
    private var mExtra : Extra? = null

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param userType
     * @param merchantId
     * @param agentId
     * @param password
     * @param pushId
     * @param gcmId
     */
    constructor(token : String, timeStamp : String, publicKey : String, userType : String, merchantId : String, agentId : String, password : String, pushId : String, gcmId : String) : super(token, timeStamp, publicKey, userType, merchantId, agentId) {
        Tracer.debug(TAG, "DTOLoginRequest : ")
        mPassword = password
        mExtra = Extra(pushId, gcmId)
    }


    private inner class Extra {

        @SerializedName("push_id")
        private var mPushId : String? = null

        @SerializedName("gcm_id")
        private var mGcmId : String? = null

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
