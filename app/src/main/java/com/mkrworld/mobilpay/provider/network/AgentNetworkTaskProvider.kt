package com.mkrworld.mobilpay.provider.network

import android.content.Context
import com.google.gson.Gson
import com.mkrworld.androidlib.network.BaseTaskProvider
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.agentchangepassword.DTOAgentChangePasswordRequest
import com.mkrworld.mobilpay.dto.agentchangepassword.DTOAgentChangePasswordResponse
import com.mkrworld.mobilpay.dto.agentfetchbill.DTOAgentFetchBillRequest
import com.mkrworld.mobilpay.dto.agentfetchbill.DTOAgentFetchBillResponse
import com.mkrworld.mobilpay.dto.agentforgotpassword.DTOAgentForgotPasswordRequest
import com.mkrworld.mobilpay.dto.agentforgotpassword.DTOAgentForgotPasswordResponse
import com.mkrworld.mobilpay.dto.agentlogin.DTOAgentLoginRequest
import com.mkrworld.mobilpay.dto.agentlogin.DTOAgentLoginResponse
import com.mkrworld.mobilpay.dto.agentlogout.DTOAgentLogoutRequest
import com.mkrworld.mobilpay.dto.agentlogout.DTOAgentLogoutResponse
import com.mkrworld.mobilpay.dto.agentsendforgotpasswordotp.DTOAgentSendForgotPasswordOtpRequest
import com.mkrworld.mobilpay.dto.agentsendforgotpasswordotp.DTOAgentSendForgotPasswordOtpResponse
import com.mkrworld.mobilpay.dto.agentdetails.DTOAgentDetailRequest
import com.mkrworld.mobilpay.dto.agentdetails.DTOAgentDetailResponse
import com.mkrworld.mobilpay.dto.agentqrcodegenarator.DTOAgentQRCodeGeneratorRequest
import com.mkrworld.mobilpay.dto.agentqrcodegenarator.DTOAgentQRCodeGeneratorResponse
import com.mkrworld.mobilpay.dto.agentsendbill.DTOAgentSendBillRequest
import com.mkrworld.mobilpay.dto.agentsendbill.DTOAgentSendBillResponse
import com.mkrworld.mobilpay.dto.mobilenumberstatus.DTOMobileNumberStatusRequest
import com.mkrworld.mobilpay.dto.mobilenumberstatus.DTOMobileNumberStatusResponse
import com.mkrworld.mobilpay.task.*
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by mkr on 27/3/18.
 */

class AgentNetworkTaskProvider : BaseTaskProvider() {

    /**
     * Method called to login the agent
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun agentLoginTask(context : Context, request : DTOAgentLoginRequest, networkCallBack : NetworkCallBack<DTOAgentLoginResponse>) {
        Tracer.debug(TAG, "userDetailTask : ")
        val requestJson = parseDtoToJson(request, DTOAgentLoginRequest::class.java, networkCallBack)
                ?: return
        val task = AgentLoginTask(context, requestJson, object : NetworkCallBack<DTOAgentLoginResponse> {

            override fun onSuccess(networkResponse : DTOAgentLoginResponse) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called to generate Agent qr code
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun agentQRCodeGeneratorTask(context : Context, request : DTOAgentQRCodeGeneratorRequest, networkCallBack : NetworkCallBack<DTOAgentQRCodeGeneratorResponse>) {
        Tracer.debug(TAG, "agentQRCodeGeneratorTask : ")
        val requestJson = parseDtoToJson(request, DTOAgentQRCodeGeneratorRequest::class.java, networkCallBack)
                ?: return
        val task = AgentQRCodeGeneratorTask(context, requestJson, object : NetworkCallBack<DTOAgentQRCodeGeneratorResponse> {

            override fun onSuccess(networkResponse : DTOAgentQRCodeGeneratorResponse) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called when agent fetch the bill of a user
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun agentFetchBillTask(context : Context, request : DTOAgentFetchBillRequest, networkCallBack : NetworkCallBack<DTOAgentFetchBillResponse>) {
        Tracer.debug(TAG, "agentFetchBillTask : ")
        val requestJson = parseDtoToJson(request, DTOAgentFetchBillRequest::class.java, networkCallBack)
                ?: return
        val task = AgentFetchBillTask(context, requestJson, object : NetworkCallBack<DTOAgentFetchBillResponse> {

            override fun onSuccess(networkResponse : DTOAgentFetchBillResponse) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called when agent send the bill of a user
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun agentSendBillTask(context : Context, request : DTOAgentSendBillRequest, networkCallBack : NetworkCallBack<DTOAgentSendBillResponse>) {
        Tracer.debug(TAG, "agentSendBillTask : ")
        val requestJson = parseDtoToJson(request, DTOAgentSendBillRequest::class.java, networkCallBack)
                ?: return
        val task = AgentSendBillTask(context, requestJson, object : NetworkCallBack<DTOAgentSendBillResponse> {

            override fun onSuccess(networkResponse : DTOAgentSendBillResponse) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called to change Agent Password
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun agentChangePasswordTask(context : Context, request : DTOAgentChangePasswordRequest, networkCallBack : NetworkCallBack<DTOAgentChangePasswordResponse>) {
        Tracer.debug(TAG, "agentChangePasswordTask : ")
        val requestJson = parseDtoToJson(request, DTOAgentChangePasswordRequest::class.java, networkCallBack)
                ?: return
        val task = AgentChangePasswordTask(context, requestJson, object : NetworkCallBack<DTOAgentChangePasswordResponse> {

            override fun onSuccess(networkResponse : DTOAgentChangePasswordResponse) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called to get Agent Detail
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun agentDetailTask(context : Context, request : DTOAgentDetailRequest, networkCallBack : NetworkCallBack<DTOAgentDetailResponse>) {
        Tracer.debug(TAG, "agentDetailTask : ")
        val requestJson = parseDtoToJson(request, DTOAgentDetailRequest::class.java, networkCallBack)
                ?: return
        val task = AgentDetailTask(context, requestJson, object : NetworkCallBack<DTOAgentDetailResponse> {

            override fun onSuccess(networkResponse : DTOAgentDetailResponse) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called to Logout Agent
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun agentLogoutTask(context : Context, request : DTOAgentLogoutRequest, networkCallBack : NetworkCallBack<DTOAgentLogoutResponse>) {
        Tracer.debug(TAG, "agentLogoutTask : ")
        val requestJson = parseDtoToJson(request, DTOAgentLogoutRequest::class.java, networkCallBack)
                ?: return
        val task = AgentLogoutTask(context, requestJson, object : NetworkCallBack<DTOAgentLogoutResponse> {

            override fun onSuccess(networkResponse : DTOAgentLogoutResponse) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, errorMessage, errorCode)
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
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, errorMessage, errorCode)
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
    fun agentSendForgotPasswordOtpTask(context : Context, request : DTOAgentSendForgotPasswordOtpRequest, networkCallBack : NetworkCallBack<DTOAgentSendForgotPasswordOtpResponse>) {
        Tracer.debug(TAG, "agentSendForgotPasswordOtpTask : ")
        val requestJson = parseDtoToJson(request, DTOAgentSendForgotPasswordOtpRequest::class.java, networkCallBack)
                ?: return
        val task = AgentSendForgotPasswordOtpTask(context, requestJson, object : NetworkCallBack<DTOAgentSendForgotPasswordOtpResponse> {

            override fun onSuccess(networkResponse : DTOAgentSendForgotPasswordOtpResponse) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, errorMessage, errorCode)
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
    fun agentForgotPasswordTask(context : Context, request : DTOAgentForgotPasswordRequest, networkCallBack : NetworkCallBack<DTOAgentForgotPasswordResponse>) {
        Tracer.debug(TAG, "agentForgotPasswordTask : ")
        val requestJson = parseDtoToJson(request, DTOAgentForgotPasswordRequest::class.java, networkCallBack)
                ?: return
        val task = AgentForgotPasswordTask(context, requestJson, object : NetworkCallBack<DTOAgentForgotPasswordResponse> {

            override fun onSuccess(networkResponse : DTOAgentForgotPasswordResponse) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, errorMessage, errorCode)
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
            notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, "Request JSON : " + e.message, - 1)
        }

        return null
    }

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".AgentNetworkTaskProvider"
    }
}
