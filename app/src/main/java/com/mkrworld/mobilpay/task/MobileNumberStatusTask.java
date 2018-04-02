package com.mkrworld.mobilpay.task;

import android.content.Context;

import com.google.gson.Gson;
import com.mkrworld.androidlib.network.NetworkCallBack;
import com.mkrworld.androidlib.network.NetworkConstants;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;
import com.mkrworld.mobilpay.dto.merchantaddfuturebill.DTOMerchantAddFutureBillResponse;
import com.mkrworld.mobilpay.dto.mobilenumberstatus.DTOMobileNumberStatusResponse;
import com.mkrworld.mobilpay.utils.UrlUtils;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by mkr on 27/3/18.
 */
public class MobileNumberStatusTask extends MobilPayBaseTask<DTOMobileNumberStatusResponse> {
    private static final String TAG = BuildConfig.BASE_TAG + ".MobileNumberStatusTask";

    /**
     * Constructor
     *
     * @param context
     * @param requestJson
     * @param networkCallBack
     */
    public MobileNumberStatusTask(Context context, JSONObject requestJson, NetworkCallBack networkCallBack) {
        super(context, requestJson, networkCallBack);
    }

    @Override
    public DTOMobileNumberStatusResponse parseNetworkResponse(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), DTOMobileNumberStatusResponse.class);
    }

    @Override
    public String getUrl() {
        return UrlUtils.getUrl(getContext(), R.string.url_mobile_number_status);
    }

    @Override
    public String getLocalResponseJsonPath() {
        return null;
    }

    @Override
    public HashMap<String, String> getCustomHeader() {
        return null;
    }

    @Override
    public NetworkConstants.RequestType getRequestType() {
        return NetworkConstants.RequestType.POST;
    }
}
