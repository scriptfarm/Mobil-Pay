package com.mkrworld.mobilpay.dto.merchantaddsendbill

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 2/4/18.
 */

class DTOMerchantSendBillRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOMerchantSendBillRequest"
    }

    @SerializedName("merchant_id")
    private var mMerchantNupayId : String? = null

    @SerializedName("amount")
    private var mAmount : String?= null

    @SerializedName("bill_details")
    private var mBillDetails : String?= null

    @SerializedName("bill_number")
    private var mBillNumber : String?= null

    @SerializedName("customer_id")
    private var mCustomerId : String?= null

    @SerializedName("customer_mobile")
    private var mCustomerMobile : String?= null

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param nupayId
     * @param amount
     * @param billDetails
     * @param billNumber
     * @param customerId
     * @param customerMobile
     */
    constructor(token : String, timeStamp : String, publicKey : String, nupayId : String, amount : String, billDetails : String, billNumber : String, customerId : String, customerMobile : String) : super(token, timeStamp, publicKey) {
        Tracer.debug(TAG, "DTOMerchantSendBillRequest : ")
        mMerchantNupayId = nupayId
        mAmount = amount
        mBillDetails = billDetails
        mBillNumber = billNumber
        mCustomerId = customerId
        mCustomerMobile = customerMobile
    }
}
