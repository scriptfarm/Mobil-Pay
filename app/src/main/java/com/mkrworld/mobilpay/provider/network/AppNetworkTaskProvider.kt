package com.mkrworld.mobilpay.provider.network

import android.content.Context
import com.google.gson.Gson
import com.mkrworld.androidlib.network.BaseTaskProvider
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.agent.agentdetails.DTOAgentDetailRequest
import com.mkrworld.mobilpay.dto.agent.agentdetails.DTOAgentDetailResponse
import com.mkrworld.mobilpay.dto.agent.agentfcm.DTOAgentFCMRequest
import com.mkrworld.mobilpay.dto.agent.agentfcm.DTOAgentFCMResponse
import com.mkrworld.mobilpay.dto.agent.agentfetchbill.DTOFetchBillRequest
import com.mkrworld.mobilpay.dto.agent.agentfetchbill.DTOFetchBillResponse
import com.mkrworld.mobilpay.dto.agent.agentmerchantlist.DTOAgentMerchantListRequest
import com.mkrworld.mobilpay.dto.agent.agentmerchantlist.DTOAgentMerchantListResponse
import com.mkrworld.mobilpay.dto.agent.agentqrcodegenarator.DTOAgentQRCodeGeneratorRequest
import com.mkrworld.mobilpay.dto.agent.agentqrcodegenarator.DTOAgentQRCodeGeneratorResponse
import com.mkrworld.mobilpay.dto.agent.agentsendbill.DTOAgentPayCashRequest
import com.mkrworld.mobilpay.dto.agent.agentsendbill.DTOAgentPayCashResponse
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
import com.mkrworld.mobilpay.dto.merchant.getagent.DTOMerchantAgentListRequest
import com.mkrworld.mobilpay.dto.merchant.getagent.DTOMerchantAgentListResponse
import com.mkrworld.mobilpay.dto.merchant.mobilenumberstatus.DTOMobileNumberStatusRequest
import com.mkrworld.mobilpay.dto.merchant.mobilenumberstatus.DTOMobileNumberStatusResponse
import com.mkrworld.mobilpay.dto.user.userdetail.DTOUserDetailRequest
import com.mkrworld.mobilpay.dto.user.userdetail.DTOUserDetailResponse
import com.mkrworld.mobilpay.task.agent.*
import com.mkrworld.mobilpay.task.comms.*
import com.mkrworld.mobilpay.task.merchant.MerchantAgentListTask
import com.mkrworld.mobilpay.task.merchant.MobileNumberStatusTask
import com.mkrworld.mobilpay.task.user.UserListTask
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
        Tracer.debug(TAG, "sendBillTask : ")
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
     * Method called when agent fetch the bill of a user
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun fetchBillTask(context : Context, request : DTOFetchBillRequest, networkCallBack : NetworkCallBack<DTOFetchBillResponse>) {
        Tracer.debug(TAG, "fetchBillTask : ")
        val requestJson = parseDtoToJson(request, DTOFetchBillRequest::class.java, networkCallBack)
                ?: return
        val task = FetchBillTask(context, requestJson, object : NetworkCallBack<DTOFetchBillResponse> {

            override fun onSuccess(networkResponse : DTOFetchBillResponse) {
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
     * Method called to save new FCM id correspond to the agent
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun setFcmId(context : Context, request : DTOAgentFCMRequest, networkCallBack : NetworkCallBack<DTOAgentFCMResponse>) {
        Tracer.debug(TAG, "setFcmId : ")
        val requestJson = parseDtoToJson(request, DTOAgentFCMRequest::class.java, networkCallBack)
                ?: return
        val task = AgentFcmTask(context, requestJson, object : NetworkCallBack<DTOAgentFCMResponse> {

            override fun onSuccess(networkResponse : DTOAgentFCMResponse) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called when agent payed by the cash
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun agentPayCashTask(context : Context, request : DTOAgentPayCashRequest, networkCallBack : NetworkCallBack<DTOAgentPayCashResponse>) {
        Tracer.debug(TAG, "agentPayCashTask : ")
        val requestJson = parseDtoToJson(request, DTOAgentPayCashRequest::class.java, networkCallBack)
                ?: return
        val task = AgentPayCashTask(context, requestJson, object : NetworkCallBack<DTOAgentPayCashResponse> {

            override fun onSuccess(networkResponse : DTOAgentPayCashResponse) {
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
     * Method called to get the list of register merchant with agent
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun agentMerchantsList(context : Context, request : DTOAgentMerchantListRequest, networkCallBack : NetworkCallBack<DTOAgentMerchantListResponse>) {
        Tracer.debug(TAG, "mobileNumberStatusTask : ")
        val requestJson = parseDtoToJson(request, DTOAgentMerchantListRequest::class.java, networkCallBack)
                ?: return
        val task = AgentMerchantsListTask(context, requestJson, object : NetworkCallBack<DTOAgentMerchantListResponse> {

            override fun onSuccess(networkResponse : DTOAgentMerchantListResponse) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called to get the list of the Agent
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun agentListTask(context : Context, request : DTOMerchantAgentListRequest, networkCallBack : NetworkCallBack<DTOMerchantAgentListResponse>) {
        Tracer.debug(TAG, "agentListTask : ")
        val requestJson = parseDtoToJson(request, DTOMerchantAgentListRequest::class.java, networkCallBack)
                ?: return
        val task = MerchantAgentListTask(context, requestJson, object : NetworkCallBack<DTOMerchantAgentListResponse> {

            override fun onSuccess(networkResponse : DTOMerchantAgentListResponse) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }

    /**
     * Method called to get the list of selected user correspond to the agent
     *
     * @param context
     * @param request
     * @param networkCallBack
     */
    fun userDetailTask(context : Context, request : DTOUserDetailRequest, networkCallBack : NetworkCallBack<DTOUserDetailResponse>) {
        Tracer.debug(TAG, "userDetailTask : ")
        val requestJson = parseDtoToJson(request, DTOUserDetailRequest::class.java, networkCallBack)
                ?: return
        val task = UserListTask(context, requestJson, object : NetworkCallBack<DTOUserDetailResponse> {

            override fun onSuccess(networkResponse : DTOUserDetailResponse) {
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
