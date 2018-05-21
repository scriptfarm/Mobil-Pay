package com.mkrworld.mobilpay.provider.fragment

import android.support.v4.app.Fragment
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.ui.fragment.*

/**
 * Created by mkr on 13/3/18.
 */

class FragmentProvider {
    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentProvider"

        /**
         * Method to get the Fragment by tag
         *
         * @param fragmentTag
         * @return
         */
        fun getFragment(fragmentTag: String): Fragment? {
            Tracer.debug(TAG, "getFragment: $fragmentTag")
            when (fragmentTag) {
                FragmentTag.AEPS_COLLECT -> return FragmentAEPSCollect()
                FragmentTag.CASH_COLLECT -> return FragmentCashCollect()
                FragmentTag.SEND_BILL -> return FragmentSendBill()
                FragmentTag.QR_CODE -> return FragmentQrCode()
                FragmentTag.QR_CODE_GENERATOR -> return FragmentQrCodeGenerator()
                FragmentTag.FORGOT_PASSWORD -> return FragmentForgotPassword()
                FragmentTag.CHANGE_PASSWORD -> return FragmentChangePassword()
                FragmentTag.CHANGE_PASSWORD_BY_OTP -> return FragmentChangePasswordByOtp()
                FragmentTag.COLLECTION_STATUS -> return FragmentCollectionStatus()
                FragmentTag.NOTIFICATION -> return FragmentNotification()
                FragmentTag.NOTIFICATION_TAB_ALL -> return FragmentNotificationTabAll()
                FragmentTag.NOTIFICATION_TAB_AGENT -> return FragmentNotificationTabAgent()
                FragmentTag.NOTIFICATION_TAB_CUSTOMER -> return FragmentNotificationTabCustomer()
                FragmentTag.LOGIN -> return FragmentLogin()
                FragmentTag.HOME -> return FragmentHome()
                FragmentTag.COLLECTION_SUMMARY -> return FragmentCollectionSummary()
                FragmentTag.UNPAID_DETAILS -> return FragmentUnPaidDetails()
                else -> return null
            }
        }
    }
}
