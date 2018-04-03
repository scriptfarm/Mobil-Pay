package com.mkrworld.mobilpay.dto.merchantaddsendbill;

import com.google.gson.annotations.SerializedName;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.dto.DTOBaseRequest;

/**
 * Created by mkr on 2/4/18.
 */

public class DTOMerchantSendBillRequest extends DTOBaseRequest {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOMerchantSendBillRequest";

    @SerializedName("merchant_id")
    private String mMerchantNupayId;

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
     * @param nupayId
     * @param amount
     * @param billDetails
     * @param billNumber
     * @param customerId
     * @param customerMobile
     */
    public DTOMerchantSendBillRequest(String token, String timeStamp, String publicKey, String nupayId, String amount, String billDetails, String billNumber, String customerId, String customerMobile) {
        super(token, timeStamp, publicKey);
        Tracer.debug(TAG, "DTOMerchantSendBillRequest : ");
        mMerchantNupayId = nupayId;
        mAmount = amount;
        mBillDetails = billDetails;
        mBillNumber = billNumber;
        mCustomerId = customerId;
        mCustomerMobile = customerMobile;
    }
}
