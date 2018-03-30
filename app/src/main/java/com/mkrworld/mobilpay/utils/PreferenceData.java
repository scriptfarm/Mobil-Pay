package com.mkrworld.mobilpay.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;

/**
 * Created by mkr on 30/3/18.
 */

public class PreferenceData {
    private static final String TAG = BuildConfig.BASE_TAG + ".PreferenceData";
    private static final String STORE = "STORE_8";
    private static String FINGER_PRINT_CONSENT = "FINGER_PRINT_CONSENT";
    private static String MERCHANT_LOGIN_ID = "MERCHANT_LOGIN_ID";
    private static String MERCHANT_LOGIN_PASSWORD = "MERCHANT_LOGIN_PASSWORD";

    //==================================================================================================================
    //==================================================================================================================
    //==================================================================================================================


    /**
     * Method to know weather the merchant allowed to use finger print or not
     *
     * @param context
     * @return TRUE if allowed else FALSE
     */
    public static boolean isHaveFingerPrintConsent(Context context) {
        Tracer.debug(TAG, "isHaveFingerPrintConsent() ");
        return getShearedPreference(context).getBoolean(FINGER_PRINT_CONSENT, false);
    }

    /**
     * Method to set that merchant allowed to use finger print
     *
     * @param context
     */
    public static void setHaveFingerPrintConsent(Context context) {
        Tracer.debug(TAG, "setHaveFingerPrintConsent() ");
        getShearedPreferenceEditor(context).putBoolean(FINGER_PRINT_CONSENT, true).commit();
    }

    /**
     * Method to get the Merchant Login Id
     *
     * @param context
     * @return Login id of the Merchant
     */
    public static String getMerchantLoginId(Context context) {
        Tracer.debug(TAG, "getMerchantLoginId : ");
        return getShearedPreference(context).getString(MERCHANT_LOGIN_ID, "");
    }

    /**
     * Method to set the Merchant Login Id
     *
     * @param context
     * @param loginId
     */
    public static void setMerchantLoginId(Context context, String loginId) {
        Tracer.debug(TAG, "setMerchantLoginId : ");
        getShearedPreferenceEditor(context).putString(MERCHANT_LOGIN_ID, loginId).commit();
    }

    /**
     * Method to get the Merchant Login Password
     *
     * @param context
     * @return Login Password of the Merchant
     */
    public static String getMerchantLoginPassword(Context context) {
        Tracer.debug(TAG, "getMerchantLoginPassword : ");
        return getShearedPreference(context).getString(MERCHANT_LOGIN_PASSWORD, "");
    }

    /**
     * Method to set the Merchant Login Password
     *
     * @param context
     * @param loginPassword
     */
    public static void setMerchantLoginPassword(Context context, String loginPassword) {
        Tracer.debug(TAG, "setMerchantLoginPassword : ");
        getShearedPreferenceEditor(context).putString(MERCHANT_LOGIN_PASSWORD, loginPassword).commit();
    }

    //==================================================================================================================
    //==================================================================================================================
    //==================================================================================================================

    /**
     * Method to clear the Data Store
     *
     * @param context
     */
    public static void clearStore(Context context) {
        Tracer.debug(TAG, "clearStore()");
        getShearedPreferenceEditor(context).clear().commit();
    }

    /**
     * Method to return the Data Store Prefference
     *
     * @param context
     * @return
     */
    private static SharedPreferences getShearedPreference(Context context) {
        return context.getSharedPreferences(STORE, Context.MODE_PRIVATE);
    }

    /**
     * caller to commit this editor
     *
     * @param context
     * @return Editor
     */
    private static Editor getShearedPreferenceEditor(Context context) {
        return getShearedPreference(context).edit();
    }
}
