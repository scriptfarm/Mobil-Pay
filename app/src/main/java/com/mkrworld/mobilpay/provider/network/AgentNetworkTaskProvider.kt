package com.mkrworld.mobilpay.provider.network

import android.content.Context
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.agent.agentdetails.DTOAgentDetailRequest
import com.mkrworld.mobilpay.dto.agent.agentdetails.DTOAgentDetailResponse
import com.mkrworld.mobilpay.dto.agent.agentfcm.DTOAgentFCMRequest
import com.mkrworld.mobilpay.dto.agent.agentfcm.DTOAgentFCMResponse
import com.mkrworld.mobilpay.dto.agent.agentfetchbill.DTOAgentFetchBillRequest
import com.mkrworld.mobilpay.dto.agent.agentfetchbill.DTOAgentFetchBillResponse
import com.mkrworld.mobilpay.dto.agent.agentmerchantlist.DTOAgentMerchantListRequest
import com.mkrworld.mobilpay.dto.agent.agentmerchantlist.DTOAgentMerchantListResponse
import com.mkrworld.mobilpay.dto.agent.agentqrcodegenarator.DTOAgentQRCodeGeneratorRequest
import com.mkrworld.mobilpay.dto.agent.agentqrcodegenarator.DTOAgentQRCodeGeneratorResponse
import com.mkrworld.mobilpay.dto.agent.agentsendbill.DTOAgentPayCashRequest
import com.mkrworld.mobilpay.dto.agent.agentsendbill.DTOAgentPayCashResponse
import com.mkrworld.mobilpay.dto.merchant.mobilenumberstatus.DTOMobileNumberStatusRequest
import com.mkrworld.mobilpay.dto.merchant.mobilenumberstatus.DTOMobileNumberStatusResponse
import com.mkrworld.mobilpay.task.agent.*
import com.mkrworld.mobilpay.task.merchant.MobileNumberStatusTask

/**
 * Created by mkr on 27/3/18.
 */

class AgentNetworkTaskProvider : AppNetworkTaskProvider() {

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

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".AgentNetworkTaskProvider"
    }
}
