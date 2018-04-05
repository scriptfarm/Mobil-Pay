package com.mkrworld.mobilpay.provider.network

import android.content.Context

import com.google.gson.Gson
import com.mkrworld.androidlib.network.BaseTaskProvider
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.merchantaddsendbill.DTOMerchantSendBillRequest
import com.mkrworld.mobilpay.dto.merchantaddsendbill.DTOMerchantSendBillResponse
import com.mkrworld.mobilpay.dto.merchantchangepassword.DTOMerchantChangePasswordRequest
import com.mkrworld.mobilpay.dto.merchantchangepassword.DTOMerchantChangePasswordResponse
import com.mkrworld.mobilpay.dto.merchantdetails.DTOMerchantDetailByNewpayIdRequest
import com.mkrworld.mobilpay.dto.merchantdetails.DTOMerchantDetailByNupayIdResponse
import com.mkrworld.mobilpay.dto.merchantforgotpassword.DTOMerchantForgotPasswordRequest
import com.mkrworld.mobilpay.dto.merchantforgotpassword.DTOMerchantForgotPasswordResponse
import com.mkrworld.mobilpay.dto.merchantlogin.DTOMerchantLoginRequest
import com.mkrworld.mobilpay.dto.merchantlogin.DTOMerchantLoginResponse
import com.mkrworld.mobilpay.dto.merchantlogout.DTOMerchantLogoutRequest
import com.mkrworld.mobilpay.dto.merchantlogout.DTOMerchantLogoutResponse
import com.mkrworld.mobilpay.dto.merchantqrcodegenarator.DTOMerchantQRCodeGeneratorRequest
import com.mkrworld.mobilpay.dto.merchantqrcodegenarator.DTOMerchantQRCodeGeneratorResponse
import com.mkrworld.mobilpay.dto.merchantsendforgotpasswordotp.DTOMerchantSendForgotPasswordOtpRequest
import com.mkrworld.mobilpay.dto.merchantsendforgotpasswordotp.DTOMerchantSendForgotPasswordOtpResponse
import com.mkrworld.mobilpay.dto.mobilenumberstatus.DTOMobileNumberStatusRequest
import com.mkrworld.mobilpay.dto.mobilenumberstatus.DTOMobileNumberStatusResponse
import com.mkrworld.mobilpay.task.MerchantSendBillTask
import com.mkrworld.mobilpay.task.MerchantChangePasswordTask
import com.mkrworld.mobilpay.task.MerchantDetailByNupayIdTask
import com.mkrworld.mobilpay.task.MerchantForgotPasswordTask
import com.mkrworld.mobilpay.task.MerchantLoginTask
import com.mkrworld.mobilpay.task.MerchantLogoutTask
import com.mkrworld.mobilpay.task.MerchantQRCodeGeneratorTask
import com.mkrworld.mobilpay.task.MerchantSendForgotPasswordOtpTask
import com.mkrworld.mobilpay.task.MobileNumberStatusTask

import org.json.JSONException
import org.json.JSONObject

/**
 * Created by mkr on 27/3/18.
 */

class MerchantNetworkTaskProvider : BaseTaskProvider() {

    /**
     * Method called to login the merchant
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun merchantLoginTask(context : Context, request : DTOMerchantLoginRequest, networkCallBack : NetworkCallBack<DTOMerchantLoginResponse>) {
        Tracer.debug(TAG, "merchantLoginTask : ")
        val requestJson = parseDtoToJson(request, DTOMerchantLoginRequest::class.java, networkCallBack)
                ?: return
        val task = MerchantLoginTask(context, requestJson, object : NetworkCallBack<DTOMerchantLoginResponse> {

            override fun onSuccess(networkResponse : DTOMerchantLoginResponse) {
                notifyTaskResponse(networkCallBack as  NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as  NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called to generate merchant qr code
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun merchantQRCodeGeneratorTask(context : Context, request : DTOMerchantQRCodeGeneratorRequest, networkCallBack : NetworkCallBack<DTOMerchantQRCodeGeneratorResponse>) {
        Tracer.debug(TAG, "merchantQrCodeGeneratorTask : ")
        val requestJson = parseDtoToJson(request, DTOMerchantQRCodeGeneratorRequest::class.java, networkCallBack)
                ?: return
        val task = MerchantQRCodeGeneratorTask(context, requestJson, object : NetworkCallBack<DTOMerchantQRCodeGeneratorResponse> {

            override fun onSuccess(networkResponse : DTOMerchantQRCodeGeneratorResponse) {
                notifyTaskResponse(networkCallBack as  NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as  NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called merchant Add Merchant Future Bill
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun merchantSendBillTask(context : Context, request : DTOMerchantSendBillRequest, networkCallBack : NetworkCallBack<DTOMerchantSendBillResponse>) {
        Tracer.debug(TAG, "merchantSendBillTask : ")
        val requestJson = parseDtoToJson(request, DTOMerchantSendBillRequest::class.java, networkCallBack)
                ?: return
        val task = MerchantSendBillTask(context, requestJson, object : NetworkCallBack<DTOMerchantSendBillResponse> {

            override fun onSuccess(networkResponse : DTOMerchantSendBillResponse) {
                notifyTaskResponse(networkCallBack as  NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as  NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called to change Merchant Password
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun merchantChangePasswordTask(context : Context, request : DTOMerchantChangePasswordRequest, networkCallBack : NetworkCallBack<DTOMerchantChangePasswordResponse>) {
        Tracer.debug(TAG, "merchantChangePasswordTask : ")
        val requestJson = parseDtoToJson(request, DTOMerchantChangePasswordRequest::class.java, networkCallBack)
                ?: return
        val task = MerchantChangePasswordTask(context, requestJson, object : NetworkCallBack<DTOMerchantChangePasswordResponse> {

            override fun onSuccess(networkResponse : DTOMerchantChangePasswordResponse) {
                notifyTaskResponse(networkCallBack as  NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as  NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called to get Merchant Detail BY Nupay Id
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun merchantDetailByNupayIdTask(context : Context, request : DTOMerchantDetailByNewpayIdRequest, networkCallBack : NetworkCallBack<DTOMerchantDetailByNupayIdResponse>) {
        Tracer.debug(TAG, "merchantDetailByNupayIdTask : ")
        val requestJson = parseDtoToJson(request, DTOMerchantDetailByNewpayIdRequest::class.java, networkCallBack)
                ?: return
        val task = MerchantDetailByNupayIdTask(context, requestJson, object : NetworkCallBack<DTOMerchantDetailByNupayIdResponse> {

            override fun onSuccess(networkResponse : DTOMerchantDetailByNupayIdResponse) {
                notifyTaskResponse(networkCallBack as  NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as  NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called to Logout Merchant
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun merchantLogoutTask(context : Context, request : DTOMerchantLogoutRequest, networkCallBack : NetworkCallBack<DTOMerchantLogoutResponse>) {
        Tracer.debug(TAG, "merchantLogoutTask : ")
        val requestJson = parseDtoToJson(request, DTOMerchantLogoutRequest::class.java, networkCallBack)
                ?: return
        val task = MerchantLogoutTask(context, requestJson, object : NetworkCallBack<DTOMerchantLogoutResponse> {

            override fun onSuccess(networkResponse : DTOMerchantLogoutResponse) {
                notifyTaskResponse(networkCallBack as  NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as  NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called to get the Mobile Number Status
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun mobileNumberStatusTask(context : Context, request : DTOMobileNumberStatusRequest, networkCallBack : NetworkCallBack<DTOMobileNumberStatusResponse>) {
        Tracer.debug(TAG, "mobileNumberStatusTask : ")
        val requestJson = parseDtoToJson(request, DTOMobileNumberStatusRequest::class.java, networkCallBack)
                ?: return
        val task = MobileNumberStatusTask(context, requestJson, object : NetworkCallBack<DTOMobileNumberStatusResponse> {

            override fun onSuccess(networkResponse : DTOMobileNumberStatusResponse) {
                notifyTaskResponse(networkCallBack as  NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as  NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called to Send to OTP at the time of forgot password
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun merchantSendForgotPasswordOtpTask(context : Context, request : DTOMerchantSendForgotPasswordOtpRequest, networkCallBack : NetworkCallBack<DTOMerchantSendForgotPasswordOtpResponse>) {
        Tracer.debug(TAG, "merchantSendForgotPasswordOtpTask : ")
        val requestJson = parseDtoToJson(request, DTOMerchantSendForgotPasswordOtpRequest::class.java, networkCallBack)
                ?: return
        val task = MerchantSendForgotPasswordOtpTask(context, requestJson, object : NetworkCallBack<DTOMerchantSendForgotPasswordOtpResponse> {

            override fun onSuccess(networkResponse : DTOMerchantSendForgotPasswordOtpResponse) {
                notifyTaskResponse(networkCallBack as  NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as  NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called to change password at the time of forgot password
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun merchantForgotPasswordTask(context : Context, request : DTOMerchantForgotPasswordRequest, networkCallBack : NetworkCallBack<DTOMerchantForgotPasswordResponse>) {
        Tracer.debug(TAG, "merchantForgotPasswordTask : ")
        val requestJson = parseDtoToJson(request, DTOMerchantForgotPasswordRequest::class.java, networkCallBack)
                ?: return
        val task = MerchantForgotPasswordTask(context, requestJson, object : NetworkCallBack<DTOMerchantForgotPasswordResponse> {

            override fun onSuccess(networkResponse : DTOMerchantForgotPasswordResponse) {
                notifyTaskResponse(networkCallBack as  NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as  NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method to parse the POJO into JSONObject
     *
     * @param object
     * @param refClass
     * @param networkCallBack
     * @return
     */
    private fun parseDtoToJson(`object` : Any, refClass : Class<*>, networkCallBack : NetworkCallBack<*>) : JSONObject? {
        try {
            return JSONObject(Gson().toJson(`object`, refClass))
        } catch (e : JSONException) {
            e.printStackTrace()
            notifyTaskResponse(networkCallBack as  NetworkCallBack<Any>, "Request JSON : " + e.message, - 1)
        }

        return null
    }

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".MerchantNetworkTaskProvider"
    }
}
