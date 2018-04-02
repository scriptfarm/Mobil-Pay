package com.mkrworld.mobilpay.dto.merchantqrcodegenarator;

import com.google.gson.annotations.SerializedName;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.dto.DTOBaseRequest;

/**
 * Created by mkr on 27/3/18.
 */

public class DTOMerchantQRCodeGeneratorRequest extends DTOBaseRequest{
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOMerchantQRCodeGeneratorRequest";

    @SerializedName("amount")
    private String mAmount;

    @SerializedName("bill_number")
    private String mBillNumber;

    @SerializedName("details")
    private String mDetails;

    @SerializedName("nupay_id")
    private String mNupayId;

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
    public DTOMerchantQRCodeGeneratorRequest(String token, String timeStamp, String publicKey, String amount, String billNumber, String details, String nupayId) {
        super(token, timeStamp, publicKey);
        mAmount = amount;
        mBillNumber = billNumber;
        mDetails = details;
        mNupayId = nupayId;
    }
}
