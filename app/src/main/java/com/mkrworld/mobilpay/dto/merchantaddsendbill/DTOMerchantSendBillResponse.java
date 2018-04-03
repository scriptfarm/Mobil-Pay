package com.mkrworld.mobilpay.dto.merchantaddsendbill;

import com.google.gson.annotations.SerializedName;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;

/**
 * Created by mkr on 27/3/18.
 */

public class DTOMerchantSendBillResponse {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOMerchantSendBillResponse";

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

        @SerializedName("user_nupay_id")
        private String mUserNupayId;

        @SerializedName("bill_number")
        private String mBillNumber;

        @SerializedName("amount")
        private String mAmount;

        @SerializedName("due_date")
        private String mDueDate;

        @SerializedName("bill_id")
        private String mBillId;

        @SerializedName("ref_transaction_id")
        private String mRefTransactionId;


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
         * Method to get the Bill Id
         *
         * @return
         */
        public String getBillId() {
            Tracer.debug(TAG, "getBillId : ");
            return mBillId != null ? mBillId : "";
        }

        /**
         * Method to get the due date
         *
         * @return
         */
        public String getDueDate() {
            Tracer.debug(TAG, "getDueDate : ");
            return mDueDate != null ? mDueDate : "";
        }

        /**
         * Method to get the Ref Transaction Id
         *
         * @return
         */
        public String getRefTransactionId() {
            return mRefTransactionId != null ? mRefTransactionId : "";
        }

        /**
         * Method to get the User New Pay Id
         *
         * @return
         */
        public String getUserNupayId() {
            Tracer.debug(TAG, "getUserNupayId : ");
            return mUserNupayId != null ? mUserNupayId : "";
        }
    }
}
