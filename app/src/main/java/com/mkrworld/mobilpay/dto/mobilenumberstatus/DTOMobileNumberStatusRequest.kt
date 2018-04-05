package com.mkrworld.mobilpay.dto.mobilenumberstatus

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 2/4/18.
 */

class DTOMobileNumberStatusRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOMobileNumberStatusRequest"
    }

    @SerializedName("user_id")
    private var mUserId : String?=null

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param userId
     */
    constructor(token : String, timeStamp : String, publicKey : String, userId : String) : super(token, timeStamp, publicKey) {
        Tracer.debug(TAG, "DTOMobileNumberStatusRequest : ")
        mUserId = userId
    }
}
