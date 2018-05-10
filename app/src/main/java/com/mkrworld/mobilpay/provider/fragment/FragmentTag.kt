package com.mkrworld.mobilpay.provider.fragment

/**
 * Created by mkr on 13/3/18.
 * Interface to hold the tag of the Fragment
 */
interface FragmentTag {
    companion object {
        val LOGIN = "Login"
        val HOME = "FragmentHome"
        val CASH_COLLECT = "FragmentCashCollect"
        val QR_CODE_GENERATOR = "FragmentQrCodeGenerator"
        val AEPS_COLLECT = "FragmentAEPSCollect"
        val SEND_BILL = "FragmentSendBill"
        val QR_CODE = "FragmentQrCode"
        val COLLECTION_STATUS = "FragmentCollectionStatus"
        val FORGOT_PASSWORD = "FragmentForgotPassword"
        val SEND_NOTIFICATION = "FragmentSendNotification"
        val CHANGE_PASSWORD = "FragmentChangePassword"
        val CHANGE_PASSWORD_BY_OTP = "FragmentChangePasswordByOtp"
        val COLLECTION_SUMMARY = "FragmentCollectionSummary"
    }
}
