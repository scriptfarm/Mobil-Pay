package com.mkrworld.mobilpay.utils;

import android.content.Context;

import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;

/**
 * Created by mkr on 27/3/18.
 */

public class UrlUtils {
    private static final String TAG = BuildConfig.BASE_TAG + ".UrlUtils";

    /**
     * Method to get the Base Url
     *
     * @param context
     * @param urlResId
     * @return
     */
    public static String getUrl(Context context, int urlResId) {
        Tracer.debug(TAG, "getUrl : " + urlResId);
        return BuildConfig.BASE_URL + getStringUrl(context, urlResId);
    }

    /**
     * Method to get the String Equvalent of the URL
     *
     * @param context
     * @param urlResId
     * @return
     */
    private static String getStringUrl(Context context, int urlResId) {
        Tracer.debug(TAG, "getStringUrl : " + urlResId);
        switch (urlResId) {
            case R.string.url_merchant_login:
                return context.getString(R.string.url_merchant_login);
        }
        return "";
    }

}
