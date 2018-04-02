package com.mkrworld.mobilpay.task;

import android.content.Context;

import com.google.gson.Gson;
import com.mkrworld.androidlib.network.NetworkCallBack;
import com.mkrworld.androidlib.network.NetworkConstants;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;
import com.mkrworld.mobilpay.dto.merchantaddfuturebill.DTOMerchantAddFutureBillRequest;
import com.mkrworld.mobilpay.dto.merchantaddfuturebill.DTOMerchantAddFutureBillResponse;
import com.mkrworld.mobilpay.dto.merchantlogin.DTOMerchantLoginResponse;
import com.mkrworld.mobilpay.utils.UrlUtils;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by mkr on 27/3/18.
 */
public class MerchantAddFutureBillTask extends MobilPayBaseTask<DTOMerchantAddFutureBillResponse> {
    private static final String TAG = BuildConfig.BASE_TAG + ".MerchantAddFutureBillTask";

    /**
     * Constructor
     *
     * @param context
     * @param requestJson
     * @param networkCallBack
     */
    public MerchantAddFutureBillTask(Context context, JSONObject requestJson, NetworkCallBack networkCallBack) {
        super(context, requestJson, networkCallBack);
    }

    @Override
    public DTOMerchantAddFutureBillResponse parseNetworkResponse(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), DTOMerchantAddFutureBillResponse.class);
    }

    @Override
    public String getUrl() {
        return UrlUtils.getUrl(getContext(), R.string.url_add_future_bill);
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
