package com.mkrworld.mobilpay.provider.fragment

import android.support.v4.app.Fragment

import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.ui.fragment.FragmentChangePassword
import com.mkrworld.mobilpay.ui.fragment.FragmentChangePasswordByOtp
import com.mkrworld.mobilpay.ui.fragment.FragmentForgotPassword
import com.mkrworld.mobilpay.ui.fragment.FragmentMerchantAEPSCollect
import com.mkrworld.mobilpay.ui.fragment.FragmentMerchantCollectionSummary
import com.mkrworld.mobilpay.ui.fragment.FragmentMerchantHome
import com.mkrworld.mobilpay.ui.fragment.FragmentAgentLogin
import com.mkrworld.mobilpay.ui.fragment.FragmentMerchantQrCode
import com.mkrworld.mobilpay.ui.fragment.FragmentAgentQrCodeGenerator
import com.mkrworld.mobilpay.ui.fragment.FragmentAgentSendBill

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
        fun getFragment(fragmentTag : String) : Fragment? {
            Tracer.debug(TAG, "getFragment: $fragmentTag")
            when (fragmentTag) {
                FragmentTag.MERCHANT_LOGIN -> return FragmentAgentLogin()
                FragmentTag.MERCHANT_HOME -> return FragmentMerchantHome()
                FragmentTag.MERCHANT_QR_CODE -> return FragmentMerchantQrCode()
                FragmentTag.MERCHANT_QR_CODE_GENERATOR -> return FragmentAgentQrCodeGenerator()
                FragmentTag.MERCHANT_AEPS_COLLECT -> return FragmentMerchantAEPSCollect()
                FragmentTag.MERCHANT_SEND_BILL -> return FragmentAgentSendBill()
                FragmentTag.FORGOT_PASSWORD -> return FragmentForgotPassword()
                FragmentTag.CHANGE_PASSWORD -> return FragmentChangePassword()
                FragmentTag.CHANGE_PASSWORD_BY_OTP -> return FragmentChangePasswordByOtp()
                FragmentTag.MERCHANT_COLLECTION_SUMMARY -> return FragmentMerchantCollectionSummary()
                else -> return null
            }
        }
    }
}
