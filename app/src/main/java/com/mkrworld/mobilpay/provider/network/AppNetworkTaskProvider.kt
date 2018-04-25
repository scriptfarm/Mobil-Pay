package com.mkrworld.mobilpay.provider.network

import android.content.Context
import com.google.gson.Gson
import com.mkrworld.androidlib.network.BaseTaskProvider
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.collectionsummary.DTOCollectionSummaryRequest
import com.mkrworld.mobilpay.dto.collectionsummary.DTOCollectionSummaryResponse
import com.mkrworld.mobilpay.dto.login.DTOLoginRequest
import com.mkrworld.mobilpay.dto.login.DTOLoginResponse
import com.mkrworld.mobilpay.task.CollectionSummaryTask
import com.mkrworld.mobilpay.task.LoginTask
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
