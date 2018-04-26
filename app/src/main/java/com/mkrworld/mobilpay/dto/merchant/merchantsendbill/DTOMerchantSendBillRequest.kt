package com.mkrworld.mobilpay.dto.merchant.merchantsendbill

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

    @SerializedName("user_id")
    private var mUserId : String? = null

    @SerializedName("bill_no")
    private var mBillNo : String? = null

    @SerializedName("description")
    private var mBillDescription : String? = null

    @SerializedName("bill_amount")
    private var mBillAmount : String? = null

    @SerializedName("mobile_no")
    private var mMobileNumber : String? = null

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param userType
     * @param merchantId
     * @param agentId
     * @param userId
     * @param mobileNumber
     * @param billNo
     * @param description
     * @param billAmount
     */
    constructor(token : String, timeStamp : String, publicKey : String, userType : String, merchantId : String, agentId : String, userId : String, mobileNumber : String, billNo : String, description : String, billAmount : String) : super(token, timeStamp, publicKey, userType, merchantId, agentId) {
        Tracer.debug(TAG, "DTOMerchantSendBillRequest : ")
        mUserId = userId
        mMobileNumber = mobileNumber
        mBillNo = billNo
        mBillDescription = description
        mBillAmount = billAmount
    }
}
