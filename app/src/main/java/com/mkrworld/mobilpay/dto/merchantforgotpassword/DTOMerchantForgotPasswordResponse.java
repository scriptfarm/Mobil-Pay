package com.mkrworld.mobilpay.dto.merchantforgotpassword;

import com.google.gson.annotations.SerializedName;
import com.mkrworld.mobilpay.BuildConfig;

/**
 * Created by mkr on 27/3/18.
 */

public class DTOMerchantForgotPasswordResponse {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOMerchantForgotPasswordResponse";

    @SerializedName("message")
    private String mMessage;

    @SerializedName("data")
    private Data mData;

    /**
     * Method to get the API Message
     *
     * @return
     */
    public String getMessage() {
        return mMessage;
    }

    /**
     * Method to get the API Data
     *
     * @return
     */
    public Data getData() {
        return mData != null ? mData : new Data();
    }

    /**
     * Class to hold the Response Data
     */
    public class Data {
        // NO DATA
    }
}
