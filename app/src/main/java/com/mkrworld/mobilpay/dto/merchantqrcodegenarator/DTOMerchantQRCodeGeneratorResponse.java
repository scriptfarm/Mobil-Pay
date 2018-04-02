package com.mkrworld.mobilpay.dto.merchantqrcodegenarator;

import com.google.gson.annotations.SerializedName;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;

/**
 * Created by mkr on 27/3/18.
 */

public class DTOMerchantQRCodeGeneratorResponse {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOMerchantQRCodeGeneratorResponse";

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

        @SerializedName("amount")
        private String mAmount;

        @SerializedName("details")
        private String mDetails;

        @SerializedName("bill_number")
        private String mBillNumber;

        @SerializedName("qr_code_token")
        private String mQrCodeToken;

        /**
         * Method to get the Bill Amount
         *
         * @return
         */
        public String getAmount() {
            Tracer.debug(TAG, "getAmount : ");
            return mAmount != null ? mAmount : "0";
        }

        /**
         * Metho dto get the Bill Number
         *
         * @return
         */
        public String getBillNumber() {
            Tracer.debug(TAG, "getBillNumber : ");
            return mBillNumber != null ? mBillNumber : "";
        }

        /**
         * Method to get the Bill Details
         *
         * @return
         */
        public String getDetails() {
            Tracer.debug(TAG, "getDetails : ");
            return mDetails != null ? mDetails : "";
        }

        /**
         * Method to get the QR Code Token
         *
         * @return
         */
        public String getQrCodeToken() {
            Tracer.debug(TAG, "getQrCodeToken : ");
            return mQrCodeToken != null ? mQrCodeToken : "";
        }
    }
}
