package com.mkrworld.mobilpay.provider.fragment;

import android.support.v4.app.Fragment;

import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.ui.fragment.FragmentChangePassword;
import com.mkrworld.mobilpay.ui.fragment.FragmentChangePasswordByOtp;
import com.mkrworld.mobilpay.ui.fragment.FragmentForgotPassword;
import com.mkrworld.mobilpay.ui.fragment.FragmentMerchantAEPSCollect;
import com.mkrworld.mobilpay.ui.fragment.FragmentMerchantCollectionSummary;
import com.mkrworld.mobilpay.ui.fragment.FragmentMerchantHome;
import com.mkrworld.mobilpay.ui.fragment.FragmentMerchantLogin;
import com.mkrworld.mobilpay.ui.fragment.FragmentMerchantQrCode;
import com.mkrworld.mobilpay.ui.fragment.FragmentMerchantQrCodeGenerator;
import com.mkrworld.mobilpay.ui.fragment.FragmentMerchantSendBill;

/**
 * Created by mkr on 13/3/18.
 */

public class FragmentProvider {
    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentProvider";

    /**
     * Method to get the Fragment by tag
     *
     * @param fragmentTag
     * @return
     */
    public static final Fragment getFragment(String fragmentTag) {
        Tracer.debug(TAG, "getFragment: " + fragmentTag);
        switch (fragmentTag) {
            case FragmentTag.MERCHANT_LOGIN:
                return new FragmentMerchantLogin();
            case FragmentTag.MERCHANT_HOME:
                return new FragmentMerchantHome();
            case FragmentTag.MERCHANT_QR_CODE:
                return new FragmentMerchantQrCode();
            case FragmentTag.MERCHANT_QR_CODE_GENERATOR:
                return new FragmentMerchantQrCodeGenerator();
            case FragmentTag.MERCHANT_AEPS_COLLECT:
                return new FragmentMerchantAEPSCollect();
            case FragmentTag.MERCHANT_SEND_BILL:
                return new FragmentMerchantSendBill();
            case FragmentTag.FORGOT_PASSWORD:
                return new FragmentForgotPassword();
            case FragmentTag.CHANGE_PASSWORD:
                return new FragmentChangePassword();
            case FragmentTag.CHANGE_PASSWORD_BY_OTP:
                return new FragmentChangePasswordByOtp();
            case FragmentTag.MERCHANT_COLLECTION_SUMMARY:
                return new FragmentMerchantCollectionSummary();
            default:
                return null;
        }
    }
}
