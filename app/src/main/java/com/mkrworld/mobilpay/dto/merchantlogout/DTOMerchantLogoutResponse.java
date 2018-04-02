package com.mkrworld.mobilpay.dto.merchantlogout;

import com.google.gson.annotations.SerializedName;
import com.mkrworld.mobilpay.BuildConfig;

/**
 * Created by mkr on 27/3/18.
 */

public class DTOMerchantLogoutResponse {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOMerchantLoginResponse";

    @SerializedName("message")
    private String mMessage;

    @SerializedName("data")
    private Boolean mData;

    /**
     * Method to get the API Message
     *
     * @return
     */
    public String getMessage() {
        return mMessage != null ? mMessage : "";
    }

    /**
     * Method to know merchant logout status
     *
     * @return TRUE is successful, Else Return False
     */
    public boolean getData() {
        return mData != null ? mData : false;
    }
}
