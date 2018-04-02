package com.mkrworld.mobilpay.task;

import android.content.Context;

import com.google.gson.Gson;
import com.mkrworld.androidlib.network.NetworkCallBack;
import com.mkrworld.androidlib.network.NetworkConstants;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;
import com.mkrworld.mobilpay.dto.merchantqrcodegenarator.DTOMerchantQRCodeGeneratorResponse;
import com.mkrworld.mobilpay.utils.UrlUtils;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by mkr on 27/3/18.
 */
public class MerchantQRCodeGeneratorTask extends MobilPayBaseTask<DTOMerchantQRCodeGeneratorResponse> {
    private static final String TAG = BuildConfig.BASE_TAG + ".MerchantQRCodeGeneratorTask";

    /**
     * Constructor
     *
     * @param context
     * @param requestJson
     * @param networkCallBack
     */
    public MerchantQRCodeGeneratorTask(Context context, JSONObject requestJson, NetworkCallBack networkCallBack) {
        super(context, requestJson, networkCallBack);
    }

    @Override
    public DTOMerchantQRCodeGeneratorResponse parseNetworkResponse(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), DTOMerchantQRCodeGeneratorResponse.class);
    }

    @Override
    public String getUrl() {
        return UrlUtils.getUrl(getContext(), R.string.url_generate_qr_code_token);
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
