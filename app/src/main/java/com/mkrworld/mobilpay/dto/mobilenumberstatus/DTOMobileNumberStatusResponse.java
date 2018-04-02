package com.mkrworld.mobilpay.dto.mobilenumberstatus;

import com.google.gson.annotations.SerializedName;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;

/**
 * Created by mkr on 27/3/18.
 */

public class DTOMobileNumberStatusResponse {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOMobileNumberStatusResponse";

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
        return mData;
    }

    /**
     * Class to hold the Response Data
     */
    public class Data {

        @SerializedName("status")
        private String mStatus;

        /**
         * Method to get Status
         *
         * @return
         */
        public String getStatus() {
            Tracer.debug(TAG, "getStatus : ");
            return mStatus != null ? mStatus : "0";
        }
    }
}
