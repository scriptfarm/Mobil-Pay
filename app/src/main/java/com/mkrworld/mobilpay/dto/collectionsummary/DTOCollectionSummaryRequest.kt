package com.mkrworld.mobilpay.dto.collectionsummary

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 27/3/18.
 */

class DTOCollectionSummaryRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOCollectionSummaryRequest"
    }

    @SerializedName("id")
    private var mId : String? = null

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param id
     */
    constructor(token : String, timeStamp : String, publicKey : String, id : String) : super(token, timeStamp, publicKey) {
        Tracer.debug(TAG, "DTOCollectionSummaryRequest : ")
        mId = id
    }
}
