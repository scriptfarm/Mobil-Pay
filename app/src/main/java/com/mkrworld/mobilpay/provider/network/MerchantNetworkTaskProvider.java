package com.mkrworld.mobilpay.provider.network;

import android.content.Context;

import com.google.gson.Gson;
import com.mkrworld.androidlib.network.BaseTaskProvider;
import com.mkrworld.androidlib.network.NetworkCallBack;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.dto.merchantaddsendbill.DTOMerchantSendBillRequest;
import com.mkrworld.mobilpay.dto.merchantaddsendbill.DTOMerchantSendBillResponse;
import com.mkrworld.mobilpay.dto.merchantchangepassword.DTOMerchantChangePasswordRequest;
import com.mkrworld.mobilpay.dto.merchantchangepassword.DTOMerchantChangePasswordResponse;
import com.mkrworld.mobilpay.dto.merchantdetails.DTOMerchantDetailByNewpayIdRequest;
import com.mkrworld.mobilpay.dto.merchantdetails.DTOMerchantDetailByNupayIdResponse;
import com.mkrworld.mobilpay.dto.merchantforgotpassword.DTOMerchantForgotPasswordRequest;
import com.mkrworld.mobilpay.dto.merchantforgotpassword.DTOMerchantForgotPasswordResponse;
import com.mkrworld.mobilpay.dto.merchantlogin.DTOMerchantLoginRequest;
import com.mkrworld.mobilpay.dto.merchantlogin.DTOMerchantLoginResponse;
import com.mkrworld.mobilpay.dto.merchantlogout.DTOMerchantLogoutRequest;
import com.mkrworld.mobilpay.dto.merchantlogout.DTOMerchantLogoutResponse;
import com.mkrworld.mobilpay.dto.merchantqrcodegenarator.DTOMerchantQRCodeGeneratorRequest;
import com.mkrworld.mobilpay.dto.merchantqrcodegenarator.DTOMerchantQRCodeGeneratorResponse;
import com.mkrworld.mobilpay.dto.merchantsendforgotpasswordotp.DTOMerchantSendForgotPasswordOtpRequest;
import com.mkrworld.mobilpay.dto.merchantsendforgotpasswordotp.DTOMerchantSendForgotPasswordOtpResponse;
import com.mkrworld.mobilpay.dto.mobilenumberstatus.DTOMobileNumberStatusRequest;
import com.mkrworld.mobilpay.dto.mobilenumberstatus.DTOMobileNumberStatusResponse;
import com.mkrworld.mobilpay.task.MerchantSendBillTask;
import com.mkrworld.mobilpay.task.MerchantChangePasswordTask;
import com.mkrworld.mobilpay.task.MerchantDetailByNupayIdTask;
import com.mkrworld.mobilpay.task.MerchantForgotPasswordTask;
import com.mkrworld.mobilpay.task.MerchantLoginTask;
import com.mkrworld.mobilpay.task.MerchantLogoutTask;
import com.mkrworld.mobilpay.task.MerchantQRCodeGeneratorTask;
import com.mkrworld.mobilpay.task.MerchantSendForgotPasswordOtpTask;
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
     * @param request
     * @param networkCallBack
     */
    public void merchantLoginTask(Context context, DTOMerchantLoginRequest request, final NetworkCallBack<DTOMerchantLoginResponse> networkCallBack) {
        Tracer.debug(TAG, "merchantLoginTask : ");
        JSONObject requestJson = parseDtoToJson(request, DTOMerchantLoginRequest.class, networkCallBack);
        if (requestJson == null) {
            return;
        }
        MerchantLoginTask task = new MerchantLoginTask(context, requestJson, new NetworkCallBack<DTOMerchantLoginResponse>() {

            @Override
            public void onSuccess(DTOMerchantLoginResponse networkResponse) {
                notifyTaskResponse(networkCallBack, networkResponse);
            }

            @Override
            public void onError(String errorMessage, int errorCode) {
                notifyTaskResponse(networkCallBack, errorMessage, errorCode);
            }
        });
        task.executeTask();
    }

    /**
     * Method called to generate merchant qr code
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    public void merchantQRCodeGeneratorTask(Context context, DTOMerchantQRCodeGeneratorRequest request, final NetworkCallBack<DTOMerchantQRCodeGeneratorResponse> networkCallBack) {
        Tracer.debug(TAG, "merchantQrCodeGeneratorTask : ");
        JSONObject requestJson = parseDtoToJson(request, DTOMerchantQRCodeGeneratorRequest.class, networkCallBack);
        if (requestJson == null) {
            return;
        }
        MerchantQRCodeGeneratorTask task = new MerchantQRCodeGeneratorTask(context, requestJson, new NetworkCallBack<DTOMerchantQRCodeGeneratorResponse>() {

            @Override
            public void onSuccess(DTOMerchantQRCodeGeneratorResponse networkResponse) {
                notifyTaskResponse(networkCallBack, networkResponse);
            }

            @Override
            public void onError(String errorMessage, int errorCode) {
                notifyTaskResponse(networkCallBack, errorMessage, errorCode);
            }
        });
        task.executeTask();
    }

    /**
     * Method called merchant Add Merchant Future Bill
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    public void merchantSendBillTask(Context context, DTOMerchantSendBillRequest request, final NetworkCallBack<DTOMerchantSendBillResponse> networkCallBack) {
        Tracer.debug(TAG, "merchantSendBillTask : ");
        JSONObject requestJson = parseDtoToJson(request, DTOMerchantSendBillRequest.class, networkCallBack);
        if (requestJson == null) {
            return;
        }
        MerchantSendBillTask task = new MerchantSendBillTask(context, requestJson, new NetworkCallBack<DTOMerchantSendBillResponse>() {

            @Override
            public void onSuccess(DTOMerchantSendBillResponse networkResponse) {
                notifyTaskResponse(networkCallBack, networkResponse);
            }

            @Override
            public void onError(String errorMessage, int errorCode) {
                notifyTaskResponse(networkCallBack, errorMessage, errorCode);
            }
        });
        task.executeTask();
    }

    /**
     * Method called to change Merchant Password
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    public void merchantChangePasswordTask(Context context, DTOMerchantChangePasswordRequest request, final NetworkCallBack<DTOMerchantChangePasswordResponse> networkCallBack) {
        Tracer.debug(TAG, "merchantChangePasswordTask : ");
        JSONObject requestJson = parseDtoToJson(request, DTOMerchantChangePasswordRequest.class, networkCallBack);
        if (requestJson == null) {
            return;
        }
        MerchantChangePasswordTask task = new MerchantChangePasswordTask(context, requestJson, new NetworkCallBack<DTOMerchantChangePasswordResponse>() {

            @Override
            public void onSuccess(DTOMerchantChangePasswordResponse networkResponse) {
                notifyTaskResponse(networkCallBack, networkResponse);
            }

            @Override
            public void onError(String errorMessage, int errorCode) {
                notifyTaskResponse(networkCallBack, errorMessage, errorCode);
            }
        });
        task.executeTask();
    }

    /**
     * Method called to get Merchant Detail BY Nupay Id
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    public void merchantDetailByNupayIdTask(Context context, DTOMerchantDetailByNewpayIdRequest request, final NetworkCallBack<DTOMerchantDetailByNupayIdResponse> networkCallBack) {
        Tracer.debug(TAG, "merchantDetailByNupayIdTask : ");
        JSONObject requestJson = parseDtoToJson(request, DTOMerchantDetailByNewpayIdRequest.class, networkCallBack);
        if (requestJson == null) {
            return;
        }
        MerchantDetailByNupayIdTask task = new MerchantDetailByNupayIdTask(context, requestJson, new NetworkCallBack<DTOMerchantDetailByNupayIdResponse>() {

            @Override
            public void onSuccess(DTOMerchantDetailByNupayIdResponse networkResponse) {
                notifyTaskResponse(networkCallBack, networkResponse);
            }

            @Override
            public void onError(String errorMessage, int errorCode) {
                notifyTaskResponse(networkCallBack, errorMessage, errorCode);
            }
        });
        task.executeTask();
    }

    /**
     * Method called to Logout Merchant
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    public void merchantLogoutTask(Context context, DTOMerchantLogoutRequest request, final NetworkCallBack<DTOMerchantLogoutResponse> networkCallBack) {
        Tracer.debug(TAG, "merchantLogoutTask : ");
        JSONObject requestJson = parseDtoToJson(request, DTOMerchantLogoutRequest.class, networkCallBack);
        if (requestJson == null) {
            return;
        }
        MerchantLogoutTask task = new MerchantLogoutTask(context, requestJson, new NetworkCallBack<DTOMerchantLogoutResponse>() {

            @Override
            public void onSuccess(DTOMerchantLogoutResponse networkResponse) {
                notifyTaskResponse(networkCallBack, networkResponse);
            }

            @Override
            public void onError(String errorMessage, int errorCode) {
                notifyTaskResponse(networkCallBack, errorMessage, errorCode);
            }
        });
        task.executeTask();
    }

    /**
     * Method called to get the Mobile Number Status
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    public void mobileNumberStatusTask(Context context, DTOMobileNumberStatusRequest request, final NetworkCallBack<DTOMobileNumberStatusResponse> networkCallBack) {
        Tracer.debug(TAG, "mobileNumberStatusTask : ");
        JSONObject requestJson = parseDtoToJson(request, DTOMobileNumberStatusRequest.class, networkCallBack);
        if (requestJson == null) {
            return;
        }
        MobileNumberStatusTask task = new MobileNumberStatusTask(context, requestJson, new NetworkCallBack<DTOMobileNumberStatusResponse>() {

            @Override
            public void onSuccess(DTOMobileNumberStatusResponse networkResponse) {
                notifyTaskResponse(networkCallBack, networkResponse);
            }

            @Override
            public void onError(String errorMessage, int errorCode) {
                notifyTaskResponse(networkCallBack, errorMessage, errorCode);
            }
        });
        task.executeTask();
    }

    /**
     * Method called to Send to OTP at the time of forgot password
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    public void merchantSendForgotPasswordOtpTask(Context context, DTOMerchantSendForgotPasswordOtpRequest request, final NetworkCallBack<DTOMerchantSendForgotPasswordOtpResponse> networkCallBack) {
        Tracer.debug(TAG, "merchantSendForgotPasswordOtpTask : ");
        JSONObject requestJson = parseDtoToJson(request, DTOMerchantSendForgotPasswordOtpRequest.class, networkCallBack);
        if (requestJson == null) {
            return;
        }
        MerchantSendForgotPasswordOtpTask task = new MerchantSendForgotPasswordOtpTask(context, requestJson, new NetworkCallBack<DTOMerchantSendForgotPasswordOtpResponse>() {

            @Override
            public void onSuccess(DTOMerchantSendForgotPasswordOtpResponse networkResponse) {
                notifyTaskResponse(networkCallBack, networkResponse);
            }

            @Override
            public void onError(String errorMessage, int errorCode) {
                notifyTaskResponse(networkCallBack, errorMessage, errorCode);
            }
        });
        task.executeTask();
    }

    /**
     * Method called to change password at the time of forgot password
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    public void merchantForgotPasswordTask(Context context, DTOMerchantForgotPasswordRequest request, final NetworkCallBack<DTOMerchantForgotPasswordResponse> networkCallBack) {
        Tracer.debug(TAG, "merchantForgotPasswordTask : ");
        JSONObject requestJson = parseDtoToJson(request, DTOMerchantForgotPasswordRequest.class, networkCallBack);
        if (requestJson == null) {
            return;
        }
        MerchantForgotPasswordTask task = new MerchantForgotPasswordTask(context, requestJson, new NetworkCallBack<DTOMerchantForgotPasswordResponse>() {

            @Override
            public void onSuccess(DTOMerchantForgotPasswordResponse networkResponse) {
                notifyTaskResponse(networkCallBack, networkResponse);
            }

            @Override
            public void onError(String errorMessage, int errorCode) {
                notifyTaskResponse(networkCallBack, errorMessage, errorCode);
            }
        });
        task.executeTask();
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
