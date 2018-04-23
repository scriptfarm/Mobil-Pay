package com.mkrworld.mobilpay.provider.fragment

/**
 * Created by mkr on 13/3/18.
 * Interface to hold the tag of the Fragment
 */
interface FragmentTag {
    companion object {
        val LOGIN = "Login"

        val MERCHANT_HOME = "FragmentMerchantHome"
        val MERCHANT_SEND_BILL = "FragmentMerchantSendBill"

        val AGENT_HOME = "FragmentAgentHome"
        val AGENT_SEND_BILL = "FragmentAgentSendBill"
        val AGENT_QR_CODE_GENERATOR = "FragmentAgentQrCodeGenerator"
        val AGENT_AEPS_COLLECT = "FragmentAgentAEPSCollect"
        val AGENT_COLLECTION_SUMMARY = "FragmentAgentCollectionSummary"
        val AGENT_QR_CODE = "FragmentAgentQrCode"

        val FORGOT_PASSWORD = "FragmentForgotPassword"
        val CHANGE_PASSWORD = "FragmentChangePassword"
        val CHANGE_PASSWORD_BY_OTP = "FragmentChangePasswordByOtp"
    }
}
