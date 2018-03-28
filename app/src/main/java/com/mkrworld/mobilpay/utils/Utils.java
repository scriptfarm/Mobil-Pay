package com.mkrworld.mobilpay.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by mkr on 14/3/18.
 */

public class Utils {
    private static final String TAG = BuildConfig.BASE_TAG + ".Utils";
    public static String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    /**
     * Method to hide the soft keyboard
     *
     * @param activity
     */
    public static final void hideKeyboard(Activity activity) {
        Tracer.debug(TAG, "hideKeyboard: " + activity);
        if (activity == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Method to hide the soft keyboard
     *
     * @param view
     */
    public static final void hideKeyboard(Context context, View view) {
        Tracer.debug(TAG, "hideKeyboard: " + view);
        if (view != null) {
            view.requestFocus();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Method to show the soft keyboard
     *
     * @param context
     * @param view    the action view
     */
    public static final void showKeyboard(Context context, View view) {
        Tracer.debug(TAG, "showKeyboard: " + context + "   " + view);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * Method to check weather the String is empty or not
     *
     * @param str
     * @return
     */
    public static boolean isStringEmpty(String str) {
        Tracer.debug(TAG, "isStringEmpty: " + str);
        return str == null || str.trim().isEmpty();
    }

    /**
     * Method to get the Date in UTC Format
     *
     * @param date
     * @param dateFormat
     * @return
     */
    public static String getDateTimeFormate(Date date, String dateFormat) {
        final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setTimeZone(TimeZone.getDefault());
        final String utcTime = sdf.format(date);
        return utcTime;
    }

    /**
     * Method to create the token
     *
     * @param context
     * @param url
     * @param date
     * @return
     */
    public static String createToken(Context context, String url, Date date) {
        long unixTime = date.getTime() / 1000;
        String md5Format = (""+unixTime).trim() + ":" + context.getString(R.string.private_key).trim() + ":" + context.getString(R.string.public_key).trim() + ":" + url.trim();// + ":" + context.getString(R.string.salt);
        String md5Token = md5(md5Format.toLowerCase());
        return md5Token;
    }

    /**
     * Method to encrypt String in md5 Hash
     *
     * @param md5
     * @return
     */
    public static String md5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
