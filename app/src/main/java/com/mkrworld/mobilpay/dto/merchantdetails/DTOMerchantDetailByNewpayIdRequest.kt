package com.mkrworld.mobilpay.dto.merchantdetails

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 27/3/18.
 */

class DTOMerchantDetailByNewpayIdRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOMerchantDetailByNewpayIdRequest"
    }

    @SerializedName("merchant_nupay_id")
    private var mNupayId : String? = null

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param nupayId
     */
    constructor(token : String, timeStamp : String, publicKey : String, nupayId : String) : super(token, timeStamp, publicKey) {
        Tracer.debug(TAG, "DTOMerchantDetailByNewpayIdRequest : ")
        mNupayId = nupayId
    }
}
