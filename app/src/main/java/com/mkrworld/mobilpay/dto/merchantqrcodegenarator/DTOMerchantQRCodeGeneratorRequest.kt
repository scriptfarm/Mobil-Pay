package com.mkrworld.mobilpay.dto.merchantqrcodegenarator

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 27/3/18.
 */

class DTOMerchantQRCodeGeneratorRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOMerchantQRCodeGeneratorRequest"
    }

    @SerializedName("amount")
    private var mAmount : String?=null

    @SerializedName("bill_number")
    private var mBillNumber : String?=null

    @SerializedName("details")
    private var mDetails : String?=null

    @SerializedName("nupay_id")
    private var mNupayId : String?=null

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param amount
     * @param billNumber
     * @param details
     * @param nupayId
     */
    constructor(token : String, timeStamp : String, publicKey : String, amount : String, billNumber : String, details : String, nupayId : String) : super(token, timeStamp, publicKey) {
        Tracer.debug(TAG, "DTOMerchantQRCodeGeneratorRequest : ")
        mAmount = amount
        mBillNumber = billNumber
        mDetails = details
        mNupayId = nupayId
    }
}
