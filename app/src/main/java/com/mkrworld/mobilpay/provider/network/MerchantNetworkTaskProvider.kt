package com.mkrworld.mobilpay.provider.network

import android.content.Context
import com.google.gson.Gson
import com.mkrworld.androidlib.network.BaseTaskProvider
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.merchant.merchantsendbill.DTOMerchantSendBillRequest
import com.mkrworld.mobilpay.dto.merchant.merchantsendbill.DTOMerchantSendBillResponse
import com.mkrworld.mobilpay.task.merchant.MerchantSendBillTask
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by mkr on 27/3/18.
 */

class MerchantNetworkTaskProvider : AppNetworkTaskProvider() {

    companion object {
        val TAG : String = BuildConfig.BASE_TAG + ".MerchantNetworkTaskProvider"
    }

    /**
     * Method called when merchant send the bill of a user
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
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, networkResponse)
            }

            override fun onError(errorMessage : String, errorCode : Int) {
                notifyTaskResponse(networkCallBack as NetworkCallBack<Any>, errorMessage, errorCode)
            }
        })
        task.executeTask()
    }
}
