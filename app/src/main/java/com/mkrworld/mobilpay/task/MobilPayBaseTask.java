package com.mkrworld.mobilpay.task;

import android.content.Context;

import com.mkrworld.androidlib.network.BaseNetworkTask;
import com.mkrworld.androidlib.network.NetworkCallBack;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;

import org.json.JSONObject;

/**
 * Created by mkr on 28/3/18.
 */

public abstract class MobilPayBaseTask<MKR> extends BaseNetworkTask<MKR> {
    private static final String TAG = BuildConfig.BASE_TAG + ".MobilPayBaseTask";
    private static final String KEY_ERROR = "error";
    private static final String KEY_MESSAGE = "message";

    /**
     * Constructor
     *
     * @param context
     * @param requestJsonObject
     * @param networkCallBack
     */
    public MobilPayBaseTask(Context context, JSONObject requestJsonObject, NetworkCallBack networkCallBack) {
        super(context, requestJsonObject, networkCallBack);
    }

    @Override
    public boolean isBusinessResponseSuccess(JSONObject jsonObject) {
        Tracer.debug(TAG, "isBusinessResponseSuccess : " + jsonObject);
        if (jsonObject == null) {
            return false;
        }
        return jsonObject.has(KEY_ERROR) ? !jsonObject.optBoolean(KEY_ERROR, true) : false;
    }

    @Override
    public String getBusinessResponseErrorMessage(JSONObject jsonObject) {
        Tracer.debug(TAG, "getBusinessResponseErrorMessage : " + jsonObject);
        if (jsonObject == null) {
            return "Unknown error";
        }
        return jsonObject.has(KEY_MESSAGE) ? jsonObject.optString(KEY_MESSAGE, "Unknown error") : "Unknown error";
    }

    @Override
    protected boolean isActualNetworkConnected() {
        return true;
    }
}
