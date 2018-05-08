package com.mkrworld.mobilpay.provider.network

import android.content.Context
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.comms.login.DTOLoginRequest
import com.mkrworld.mobilpay.dto.comms.login.DTOLoginResponse
import com.mkrworld.mobilpay.dto.merchant.getagent.DTOMerchantAgentListRequest
import com.mkrworld.mobilpay.dto.merchant.getagent.DTOMerchantAgentListResponse
import com.mkrworld.mobilpay.task.comms.LoginTask
import com.mkrworld.mobilpay.task.merchant.MerchantAgentListTask

/**
 * Created by mkr on 27/3/18.
 */

class MerchantNetworkTaskProvider : AppNetworkTaskProvider() {

    companion object {
        val TAG : String = BuildConfig.BASE_TAG + ".MerchantNetworkTaskProvider"
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
}
