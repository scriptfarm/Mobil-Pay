package com.mkrworld.mobilpay.dto.comms.sendbill

import com.google.gson.annotations.SerializedName
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 2/4/18.
 */

class DTOSendBillRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOSendBillRequest"
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

    @SerializedName("single_bill")
    private var mSingleBill : String = "0"

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
        Tracer.debug(TAG, "DTOSendBillRequest : ")
        mUserId = userId
        mMobileNumber = mobileNumber
        mBillNo = billNo
        mBillDescription = description
        mBillAmount = billAmount
    }

    /**
     * Constructor Called When Merchant Create Single Bill
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
     *@param singleBillCode
     */
    constructor(token : String, timeStamp : String, publicKey : String, userType : String, merchantId : String, agentId : String, userId : String, mobileNumber : String, billNo : String, description : String, billAmount : String, singleBillCode : String) : this(token, timeStamp, publicKey, userType, merchantId, agentId, userId, mobileNumber, billNo, description, billAmount) {
        mSingleBill = singleBillCode
    }

}
