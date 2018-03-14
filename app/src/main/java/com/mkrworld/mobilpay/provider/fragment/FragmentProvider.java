package com.mkrworld.mobilpay.provider.fragment;

import android.support.v4.app.Fragment;

import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.ui.fragment.FragmentMerchantHome;
import com.mkrworld.mobilpay.ui.fragment.FragmentMerchantLogin;

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
            default:
                return null;
        }
    }
}
