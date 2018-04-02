package com.mkrworld.mobilpay.dto.merchantaddfuturebill;

import com.google.gson.annotations.SerializedName;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.dto.DTOBaseRequest;

/**
 * Created by mkr on 2/4/18.
 */

public class DTOMerchantAddFutureBillRequest extends DTOBaseRequest {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOMerchantAddFutureBillRequest";

    @SerializedName("Merchant_Id")
    private String mMerchantId;

    @SerializedName("amount")
    private String mAmount;

    @SerializedName("bill_details")
    private String mBillDetails;

    @SerializedName("bill_number")
    private String mBillNumber;

    @SerializedName("customer_id")
    private String mCustomerId;

    @SerializedName("customer_mobile")
    private String mCustomerMobile;

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     * @param merchantId
     * @param amount
     * @param billDetails
     * @param billNumber
     * @param customerId
     * @param customerMobile
     */
    public DTOMerchantAddFutureBillRequest(String token, String timeStamp, String publicKey, String merchantId, String amount, String billDetails, String billNumber, String customerId, String customerMobile) {
        super(token, timeStamp, publicKey);
        Tracer.debug(TAG, "DTOMerchantAddFutureBillRequest : ");
        mMerchantId = merchantId;
        mAmount = amount;
        mBillDetails = billDetails;
        mBillNumber = billNumber;
        mCustomerId = customerId;
        mCustomerMobile = customerMobile;
    }
}
