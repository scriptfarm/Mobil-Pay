package com.mkrworld.mobilpay.provider.fragment

import com.mkrworld.mobilpay.ui.fragment.FragmentForgotPassword

/**
 * Created by mkr on 13/3/18.
 * Interface to hold the tag of the Fragment
 */
interface FragmentTag {
    companion object {
        val MERCHANT_LOGIN = "FragmentMerchantLogin"
        val MERCHANT_HOME = "FragmentMerchantHome"
        val MERCHANT_QR_CODE = "FragmentMerchantQrCode"
        val MERCHANT_QR_CODE_GENERATOR = "FragmentMerchantQrCodeGenerator"
        val MERCHANT_AEPS_COLLECT = "FragmentMerchantAEPSCollect"
        val MERCHANT_SEND_BILL = "FragmentMerchantSendBill"
        val MERCHANT_COLLECTION_SUMMARY = "FragmentMerchantCollectionSummary"
        val FORGOT_PASSWORD = "FragmentForgotPassword"
        val CHANGE_PASSWORD = "FragmentChangePassword"
        val CHANGE_PASSWORD_BY_OTP = "FragmentChangePasswordByOtp"
    }
}
