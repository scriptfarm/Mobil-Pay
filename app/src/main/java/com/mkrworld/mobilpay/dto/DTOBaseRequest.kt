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
    private var mToken : String?=null

    @SerializedName("timestamp")
    private var mTimeStamp : String?=null

    @SerializedName("public_key")
    private var mPublicKey : String?=null

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     */
    constructor(token : String, timeStamp : String, publicKey : String) {
        mToken = token
        mTimeStamp = timeStamp
        mPublicKey = publicKey
    }
}
