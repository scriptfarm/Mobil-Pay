package com.mkrworld.mobilpay.provider.network;

import android.content.Context;

import com.google.gson.Gson;
import com.mkrworld.androidlib.network.BaseTaskProvider;
import com.mkrworld.androidlib.network.NetworkCallBack;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.dto.merchantlogin.DTOMerchantLoginRequest;
import com.mkrworld.mobilpay.dto.merchantlogin.DTOMerchantLoginResponse;
import com.mkrworld.mobilpay.task.MerchantLoginTask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mkr on 27/3/18.
 */

public class MerchantNetworkTaskProvider extends BaseTaskProvider {
    private static final String TAG = BuildConfig.BASE_TAG + ".MerchantNetworkTaskProvider";

    /**
     * Method called to login the merchant
     *
     * @param context
     * @param dtoMerchantLoginRequest
     * @param networkCallBack
     */
    public void merchantLoginTask(Context context, DTOMerchantLoginRequest dtoMerchantLoginRequest, final NetworkCallBack<DTOMerchantLoginResponse> networkCallBack) {
        JSONObject requestJson = parseDtoToJson(dtoMerchantLoginRequest, DTOMerchantLoginRequest.class, networkCallBack);
        if (requestJson == null) {
            return;
        }
        MerchantLoginTask merchantLoginTask = new MerchantLoginTask(context, requestJson, new NetworkCallBack<DTOMerchantLoginResponse>() {

            @Override
            public void onSuccess(DTOMerchantLoginResponse dtoMerchantLoginResponse) {
                notifyTaskResponse(networkCallBack, dtoMerchantLoginResponse);
            }

            @Override
            public void onError(String errorMessage, int errorCode) {
                notifyTaskResponse(networkCallBack, errorMessage, errorCode);
            }
        });
        merchantLoginTask.executeTask();
    }

    /**
     * Method to parse the POJO into JSONObject
     *
     * @param object
     * @param refClass
     * @param networkCallBack
     * @return
     */
    private JSONObject parseDtoToJson(Object object, Class refClass, NetworkCallBack networkCallBack) {
        try {
            return new JSONObject(new Gson().toJson(object, refClass));
        } catch (JSONException e) {
            e.printStackTrace();
            notifyTaskResponse(networkCallBack, "Request JSON : " + e.getMessage(), -1);
        }
        return null;
    }
}
