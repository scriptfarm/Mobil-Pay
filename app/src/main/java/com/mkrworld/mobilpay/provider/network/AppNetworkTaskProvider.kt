package com.mkrworld.mobilpay.provider.network

import android.content.Context
import com.google.gson.Gson
import com.mkrworld.androidlib.network.BaseTaskProvider
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.comms.changepassword.DTOChangePasswordRequest
import com.mkrworld.mobilpay.dto.comms.changepassword.DTOChangePasswordResponse
import com.mkrworld.mobilpay.dto.comms.collectionstatus.DTOCollectionStatusRequest
import com.mkrworld.mobilpay.dto.comms.collectionstatus.DTOCollectionStatusResponse
import com.mkrworld.mobilpay.dto.comms.collectionsummary.DTOCollectionSummaryRequest
import com.mkrworld.mobilpay.dto.comms.collectionsummary.DTOCollectionSummaryResponse
import com.mkrworld.mobilpay.dto.comms.forgotpassword.DTOForgotPasswordRequest
import com.mkrworld.mobilpay.dto.comms.forgotpassword.DTOForgotPasswordResponse
import com.mkrworld.mobilpay.dto.comms.login.DTOLoginRequest
import com.mkrworld.mobilpay.dto.comms.login.DTOLoginResponse
import com.mkrworld.mobilpay.dto.comms.logout.DTOLogoutRequest
import com.mkrworld.mobilpay.dto.comms.logout.DTOLogoutResponse
import com.mkrworld.mobilpay.dto.comms.sendbill.DTOSendBillRequest
import com.mkrworld.mobilpay.dto.comms.sendbill.DTOSendBillResponse
import com.mkrworld.mobilpay.dto.comms.sendforgotpasswordotp.DTOSendForgotPasswordOtpRequest
import com.mkrworld.mobilpay.dto.comms.sendforgotpasswordotp.DTOSendForgotPasswordOtpResponse
import com.mkrworld.mobilpay.dto.comms.sendnotification.DTOSendNotificationRequest
import com.mkrworld.mobilpay.dto.comms.sendnotification.DTOSendNotificationResponse
import com.mkrworld.mobilpay.task.comms.*
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by mkr on 27/3/18.
 */

open class AppNetworkTaskProvider : BaseTaskProvider() {

    /**
     * Method called to login
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun loginTask(context : Context, request : DTOLoginRequest, networkCallBack : NetworkCallBack<DTOLoginResponse>) {
        Tracer.debug(TAG, "loginTask : ")
        val requestJson = parseDtoToJson(request, DTOLoginRequest::class.java, networkCallBack)
                ?: return
        val task = LoginTask(context, requestJson, object : NetworkCallBack<DTOLoginResponse> {

            override fun onSuccess(networkResponse : DTOLoginResponse) {
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
    fun logoutTask(context : Context, request : DTOLogoutRequest, networkCallBack : NetworkCallBack<DTOLogoutResponse>) {
        Tracer.debug(TAG, "logoutTask : ")
        val requestJson = parseDtoToJson(request, DTOLogoutRequest::class.java, networkCallBack)
                ?: return
        val task = LogoutTask(context, requestJson, object : NetworkCallBack<DTOLogoutResponse> {

            override fun onSuccess(networkResponse : DTOLogoutResponse) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called to change Password
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun changePasswordTask(context : Context, request : DTOChangePasswordRequest, networkCallBack : NetworkCallBack<DTOChangePasswordResponse>) {
        Tracer.debug(TAG, "changePasswordTask : ")
        val requestJson = parseDtoToJson(request, DTOChangePasswordRequest::class.java, networkCallBack)
                ?: return
        val task = ChangePasswordTask(context, requestJson, object : NetworkCallBack<DTOChangePasswordResponse> {

            override fun onSuccess(networkResponse : DTOChangePasswordResponse) {
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
    fun sendForgotPasswordOtpTask(context : Context, request : DTOSendForgotPasswordOtpRequest, networkCallBack : NetworkCallBack<DTOSendForgotPasswordOtpResponse>) {
        Tracer.debug(TAG, "sendForgotPasswordOtpTask : ")
        val requestJson = parseDtoToJson(request, DTOSendForgotPasswordOtpRequest::class.java, networkCallBack)
                ?: return
        val task = SendForgotPasswordOtpTask(context, requestJson, object : NetworkCallBack<DTOSendForgotPasswordOtpResponse> {

            override fun onSuccess(networkResponse : DTOSendForgotPasswordOtpResponse) {
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
    fun forgotPasswordTask(context : Context, request : DTOForgotPasswordRequest, networkCallBack : NetworkCallBack<DTOForgotPasswordResponse>) {
        Tracer.debug(TAG, "forgotPasswordTask : ")
        val requestJson = parseDtoToJson(request, DTOForgotPasswordRequest::class.java, networkCallBack)
                ?: return
        val task = ForgotPasswordTask(context, requestJson, object : NetworkCallBack<DTOForgotPasswordResponse> {

            override fun onSuccess(networkResponse : DTOForgotPasswordResponse) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called to get the collection summary
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun collectionSummaryTask(context : Context, request : DTOCollectionSummaryRequest, networkCallBack : NetworkCallBack<DTOCollectionSummaryResponse>) {
        Tracer.debug(TAG, "loginTask : ")
        val requestJson = parseDtoToJson(request, DTOCollectionSummaryRequest::class.java, networkCallBack)
                ?: return
        val task = CollectionSummaryTask(context, requestJson, object : NetworkCallBack<DTOCollectionSummaryResponse> {

            override fun onSuccess(networkResponse : DTOCollectionSummaryResponse) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called to get the collection summary
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun collectionStatusTask(context : Context, request : DTOCollectionStatusRequest, networkCallBack : NetworkCallBack<DTOCollectionStatusResponse>) {
        Tracer.debug(TAG, "loginTask : ")
        val requestJson = parseDtoToJson(request, DTOCollectionStatusRequest::class.java, networkCallBack)
                ?: return
        val task = CollectionStatusTask(context, requestJson, object : NetworkCallBack<DTOCollectionStatusResponse> {

            override fun onSuccess(networkResponse : DTOCollectionStatusResponse) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }


    /**
     * Method called to when agent send notification to the User
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun sendNotificationTaskTask(context : Context, request : DTOSendNotificationRequest, networkCallBack : NetworkCallBack<DTOSendNotificationResponse>) {
        Tracer.debug(TAG, "sendNotificationTaskTask : ")
        val requestJson = parseDtoToJson(request, DTOSendNotificationRequest::class.java, networkCallBack)
                ?: return
        val task = SendNotificationTask(context, requestJson, object : NetworkCallBack<DTOSendNotificationResponse> {

            override fun onSuccess(networkResponse : DTOSendNotificationResponse) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called when merchant send the bill of a user
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun sendBillTask(context : Context, request : DTOSendBillRequest, networkCallBack : NetworkCallBack<DTOSendBillResponse>) {
        Tracer.debug(MerchantNetworkTaskProvider.TAG, "sendBillTask : ")
        val requestJson = parseDtoToJson(request, DTOSendBillRequest::class.java, networkCallBack)
                ?: return
        val task = SendBillTask(context, requestJson, object : NetworkCallBack<DTOSendBillResponse> {

            override fun onSuccess(networkResponse : DTOSendBillResponse) {
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
    protected fun parseDtoToJson(`object` : Any, refClass : Class<*>, networkCallBack : NetworkCallBack<*>) : JSONObject? {
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
