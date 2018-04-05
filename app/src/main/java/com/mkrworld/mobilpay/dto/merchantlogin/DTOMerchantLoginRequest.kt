package com.mkrworld.mobilpay.dto.merchantlogin

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 27/3/18.
 */

class DTOMerchantLoginRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOMerchantLoginResponse"
    }

    @SerializedName("user_id")
    private var mUserId : String?=null

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
     * @param userId
     * @param password
     * @param pushId
     * @param gcmId
     */
    constructor(token : String, timeStamp : String, publicKey : String, userId : String, password : String, pushId : String, gcmId : String) : super(token, timeStamp, publicKey) {
        Tracer.debug(TAG, "DTOMerchantLoginRequest : ")
        mUserId = userId
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
