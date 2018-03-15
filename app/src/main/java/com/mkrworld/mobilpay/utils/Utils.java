package com.mkrworld.mobilpay.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;

/**
 * Created by mkr on 14/3/18.
 */

public class Utils {
    private static final String TAG = BuildConfig.BASE_TAG + ".Utils";

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
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
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

}
