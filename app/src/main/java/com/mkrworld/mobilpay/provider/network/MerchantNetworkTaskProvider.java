package com.mkrworld.mobilpay.provider.network;

import android.content.Context;

import com.google.gson.Gson;
import com.mkrworld.androidlib.network.BaseTaskProvider;
import com.mkrworld.androidlib.network.NetworkCallBack;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.dto.merchantaddfuturebill.DTOMerchantAddFutureBillRequest;
import com.mkrworld.mobilpay.dto.merchantaddfuturebill.DTOMerchantAddFutureBillResponse;
import com.mkrworld.mobilpay.dto.merchantchangepassword.DTOMerchantChangePasswordRequest;
import com.mkrworld.mobilpay.dto.merchantchangepassword.DTOMerchantChangePasswordResponse;
import com.mkrworld.mobilpay.dto.merchantdetails.DTOMerchantDetailByNewpayIdRequest;
import com.mkrworld.mobilpay.dto.merchantdetails.DTOMerchantDetailByNupayIdResponse;
import com.mkrworld.mobilpay.dto.merchantlogin.DTOMerchantLoginRequest;
import com.mkrworld.mobilpay.dto.merchantlogin.DTOMerchantLoginResponse;
import com.mkrworld.mobilpay.dto.merchantlogout.DTOMerchantLogoutRequest;
import com.mkrworld.mobilpay.dto.merchantlogout.DTOMerchantLogoutResponse;
import com.mkrworld.mobilpay.dto.merchantqrcodegenarator.DTOMerchantQRCodeGeneratorRequest;
import com.mkrworld.mobilpay.dto.merchantqrcodegenarator.DTOMerchantQRCodeGeneratorResponse;
import com.mkrworld.mobilpay.dto.mobilenumberstatus.DTOMobileNumberStatusRequest;
import com.mkrworld.mobilpay.dto.mobilenumberstatus.DTOMobileNumberStatusResponse;
import com.mkrworld.mobilpay.task.MerchantAddFutureBillTask;
import com.mkrworld.mobilpay.task.MerchantChangePasswordTask;
import com.mkrworld.mobilpay.task.MerchantDetailByNupayIdTask;
import com.mkrworld.mobilpay.task.MerchantLoginTask;
import com.mkrworld.mobilpay.task.MerchantLogoutTask;
import com.mkrworld.mobilpay.task.MerchantQRCodeGeneratorTask;
import com.mkrworld.mobilpay.task.MobileNumberStatusTask;

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
        Tracer.debug(TAG, "merchantLoginTask : ");
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
     * Method called to generate merchant qr code
     *
     * @param context
     * @param dtoQRCodeGeneratorRequest
     * @param networkCallBack
     */
    public void merchantQRCodeGeneratorTask(Context context, DTOMerchantQRCodeGeneratorRequest dtoQRCodeGeneratorRequest, final NetworkCallBack<DTOMerchantQRCodeGeneratorResponse> networkCallBack) {
        Tracer.debug(TAG, "merchantQrCodeGeneratorTask : ");
        JSONObject requestJson = parseDtoToJson(dtoQRCodeGeneratorRequest, DTOMerchantQRCodeGeneratorRequest.class, networkCallBack);
        if (requestJson == null) {
            return;
        }
        MerchantQRCodeGeneratorTask merchantQrCodeGeneratorTask = new MerchantQRCodeGeneratorTask(context, requestJson, new NetworkCallBack<DTOMerchantQRCodeGeneratorResponse>() {

            @Override
            public void onSuccess(DTOMerchantQRCodeGeneratorResponse dtoQRCodeGeneratorResponse) {
                notifyTaskResponse(networkCallBack, dtoQRCodeGeneratorResponse);
            }

            @Override
            public void onError(String errorMessage, int errorCode) {
                notifyTaskResponse(networkCallBack, errorMessage, errorCode);
            }
        });
        merchantQrCodeGeneratorTask.executeTask();
    }

    /**
     * Method called merchant Add Merchant Future Bill
     *
     * @param context
     * @param dtoMerchantAddFutureBillRequest
     * @param networkCallBack
     */
    public void merchantAddFutureBillTask(Context context, DTOMerchantAddFutureBillRequest dtoMerchantAddFutureBillRequest, final NetworkCallBack<DTOMerchantAddFutureBillResponse> networkCallBack) {
        Tracer.debug(TAG, "merchantAddFutureBillTask : ");
        JSONObject requestJson = parseDtoToJson(dtoMerchantAddFutureBillRequest, DTOMerchantAddFutureBillRequest.class, networkCallBack);
        if (requestJson == null) {
            return;
        }
        MerchantAddFutureBillTask merchantAddFutureBillTask = new MerchantAddFutureBillTask(context, requestJson, new NetworkCallBack<DTOMerchantAddFutureBillResponse>() {

            @Override
            public void onSuccess(DTOMerchantAddFutureBillResponse dtoMerchantAddFutureBillResponse) {
                notifyTaskResponse(networkCallBack, dtoMerchantAddFutureBillResponse);
            }

            @Override
            public void onError(String errorMessage, int errorCode) {
                notifyTaskResponse(networkCallBack, errorMessage, errorCode);
            }
        });
        merchantAddFutureBillTask.executeTask();
    }

    /**
     * Method called to change Merchant Password
     *
     * @param context
     * @param dtoMerchantChangePasswordRequest
     * @param networkCallBack
     */
    public void merchantChangePasswordTask(Context context, DTOMerchantChangePasswordRequest dtoMerchantChangePasswordRequest, final NetworkCallBack<DTOMerchantChangePasswordResponse> networkCallBack) {
        Tracer.debug(TAG, "merchantChangePasswordTask : ");
        JSONObject requestJson = parseDtoToJson(dtoMerchantChangePasswordRequest, DTOMerchantChangePasswordRequest.class, networkCallBack);
        if (requestJson == null) {
            return;
        }
        MerchantChangePasswordTask merchantChangePasswordTask = new MerchantChangePasswordTask(context, requestJson, new NetworkCallBack<DTOMerchantChangePasswordResponse>() {

            @Override
            public void onSuccess(DTOMerchantChangePasswordResponse dtoMerchantChangePasswordResponse) {
                notifyTaskResponse(networkCallBack, dtoMerchantChangePasswordResponse);
            }

            @Override
            public void onError(String errorMessage, int errorCode) {
                notifyTaskResponse(networkCallBack, errorMessage, errorCode);
            }
        });
        merchantChangePasswordTask.executeTask();
    }

    /**
     * Method called to get Merchant Detail BY Nupay Id
     *
     * @param context
     * @param dtoMerchantDetailByNewpayIdRequest
     * @param networkCallBack
     */
    public void merchantDetailByNupayIdTask(Context context, DTOMerchantDetailByNewpayIdRequest dtoMerchantDetailByNewpayIdRequest, final NetworkCallBack<DTOMerchantDetailByNupayIdResponse> networkCallBack) {
        Tracer.debug(TAG, "merchantDetailByNupayIdTask : ");
        JSONObject requestJson = parseDtoToJson(dtoMerchantDetailByNewpayIdRequest, DTOMerchantDetailByNewpayIdRequest.class, networkCallBack);
        if (requestJson == null) {
            return;
        }
        MerchantDetailByNupayIdTask merchantDetailByNupayIdTask = new MerchantDetailByNupayIdTask(context, requestJson, new NetworkCallBack<DTOMerchantDetailByNupayIdResponse>() {

            @Override
            public void onSuccess(DTOMerchantDetailByNupayIdResponse dtoMerchantDetailByNupayIdResponse) {
                notifyTaskResponse(networkCallBack, dtoMerchantDetailByNupayIdResponse);
            }

            @Override
            public void onError(String errorMessage, int errorCode) {
                notifyTaskResponse(networkCallBack, errorMessage, errorCode);
            }
        });
        merchantDetailByNupayIdTask.executeTask();
    }

    /**
     * Method called to Logout Merchant
     *
     * @param context
     * @param dtoMerchantLogoutRequest
     * @param networkCallBack
     */
    public void merchantLogoutTask(Context context, DTOMerchantLogoutRequest dtoMerchantLogoutRequest, final NetworkCallBack<DTOMerchantLogoutResponse> networkCallBack) {
        Tracer.debug(TAG, "merchantLogoutTask : ");
        JSONObject requestJson = parseDtoToJson(dtoMerchantLogoutRequest, DTOMerchantLogoutRequest.class, networkCallBack);
        if (requestJson == null) {
            return;
        }
        MerchantLogoutTask merchantLogoutTask = new MerchantLogoutTask(context, requestJson, new NetworkCallBack<DTOMerchantLogoutResponse>() {

            @Override
            public void onSuccess(DTOMerchantLogoutResponse dtoMerchantLogoutResponse) {
                notifyTaskResponse(networkCallBack, dtoMerchantLogoutResponse);
            }

            @Override
            public void onError(String errorMessage, int errorCode) {
                notifyTaskResponse(networkCallBack, errorMessage, errorCode);
            }
        });
        merchantLogoutTask.executeTask();
    }

    /**
     * Method called to get the Mobile Number Status
     *
     * @param context
     * @param dtoMobileNumberStatusRequest
     * @param networkCallBack
     */
    public void mobileNumberStatusTask(Context context, DTOMobileNumberStatusRequest dtoMobileNumberStatusRequest, final NetworkCallBack<DTOMobileNumberStatusResponse> networkCallBack) {
        Tracer.debug(TAG, "mobileNumberStatusTask : ");
        JSONObject requestJson = parseDtoToJson(dtoMobileNumberStatusRequest, DTOMobileNumberStatusRequest.class, networkCallBack);
        if (requestJson == null) {
            return;
        }
        MobileNumberStatusTask mobileNumberStatusTask = new MobileNumberStatusTask(context, requestJson, new NetworkCallBack<DTOMobileNumberStatusResponse>() {

            @Override
            public void onSuccess(DTOMobileNumberStatusResponse dtoMobileNumberStatusResponse) {
                notifyTaskResponse(networkCallBack, dtoMobileNumberStatusResponse);
            }

            @Override
            public void onError(String errorMessage, int errorCode) {
                notifyTaskResponse(networkCallBack, errorMessage, errorCode);
            }
        });
        mobileNumberStatusTask.executeTask();
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
