package com.mkrworld.mobilpay.provider.fragment

import android.support.v4.app.Fragment
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.ui.fragment.*
import com.mkrworld.mobilpay.ui.fragment.agent.*
import com.mkrworld.mobilpay.ui.fragment.agent.FragmentAgentAEPSCollect
import com.mkrworld.mobilpay.ui.fragment.agent.FragmentAgentQrCode
import com.mkrworld.mobilpay.ui.fragment.merchant.*

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
                FragmentTag.MERCHANT_HOME -> return FragmentMerchantHome()
                FragmentTag.MERCHANT_SEND_BILL -> return FragmentMerchantSendBill()

                FragmentTag.AGENT_HOME -> return FragmentAgentHome()
                FragmentTag.AGENT_QR_CODE -> return FragmentAgentQrCode()
                FragmentTag.AGENT_QR_CODE_GENERATOR -> return FragmentAgentQrCodeGenerator()
                FragmentTag.AGENT_AEPS_COLLECT -> return FragmentAgentAEPSCollect()
                FragmentTag.AGENT_SEND_BILL -> return FragmentAgentSendBill()

                FragmentTag.FORGOT_PASSWORD -> return FragmentForgotPassword()
                FragmentTag.CHANGE_PASSWORD -> return FragmentChangePassword()
                FragmentTag.CHANGE_PASSWORD_BY_OTP -> return FragmentChangePasswordByOtp()
                FragmentTag.COLLECTION_STATUS -> return FragmentCollectionStatus()
                FragmentTag.SEND_NOTIFICATION -> return FragmentSendNotification()
                FragmentTag.LOGIN -> return FragmentLogin()
                FragmentTag.COLLECTION_SUMMARY -> return FragmentCollectionSummary()
                else -> return null
            }
        }
    }
}
