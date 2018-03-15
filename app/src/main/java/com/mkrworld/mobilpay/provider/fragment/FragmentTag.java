package com.mkrworld.mobilpay.provider.fragment;

import com.mkrworld.mobilpay.ui.fragment.FragmentForgotPassword;

/**
 * Created by mkr on 13/3/18.
 * Interface to hold the tag of the Fragment
 */
public interface FragmentTag {
    String MERCHANT_LOGIN = "FragmentMerchantLogin";
    String MERCHANT_HOME = "FragmentMerchantHome";
    String MERCHANT_QR_CODE = "FragmentMerchantQrCode";
    String MERCHANT_QR_CODE_GENERATOR = "FragmentMerchantQrCodeGenerator";
    String MERCHANT_AEPS_COLLECT = "FragmentMerchantAEPSCollect";
    String MERCHANT_SEND_BILL = "FragmentMerchantSendBill";
    String MERCHANT_COLLECTION_SUMMARY = "FragmentMerchantCollectionSummary";
    String FORGOT_PASSWORD = "FragmentForgotPassword";
    String CHANGE_PASSWORD = "FragmentChangePassword";
    String CHANGE_PASSWORD_BY_OTP = "FragmentChangePasswordByOtp";
}
