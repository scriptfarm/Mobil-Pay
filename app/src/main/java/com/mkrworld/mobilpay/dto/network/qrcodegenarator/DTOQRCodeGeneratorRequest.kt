package com.mkrworld.mobilpay.dto.network.qrcodegenarator

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 27/3/18.
 */

class DTOQRCodeGeneratorRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOQRCodeGeneratorRequest"
    }

    @SerializedName("amount")
    private var mAmount : String? = null

    @SerializedName("bill_number")
    private var mBillNumber : String? = null

    @SerializedName("details")
    private var mDetails : String? = null

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param userType
     * @param merchantId
     * @param agentId
     * @param amount
     * @param billNumber
     * @param details
     */
    constructor(token : String, timeStamp : String, publicKey : String, userType : String, merchantId : String, agentId : String, amount : String, billNumber : String, details : String) : super(token, timeStamp, publicKey, userType, merchantId, agentId) {
        Tracer.debug(TAG, "DTOQRCodeGeneratorRequest : ")
        mAmount = amount
        mBillNumber = billNumber
        mDetails = details
    }
}
