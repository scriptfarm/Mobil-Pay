package com.mkrworld.mobilpay.provider.network

import android.content.Context
import com.google.gson.Gson
import com.mkrworld.androidlib.network.BaseTaskProvider
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.userdetail.DTOUserDetailRequest
import com.mkrworld.mobilpay.dto.userdetail.DTOUserDetailResponse
import com.mkrworld.mobilpay.task.UserListTask
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by mkr on 27/3/18.
 */

class UserNetworkTaskProvider : BaseTaskProvider() {

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
        private val TAG = BuildConfig.BASE_TAG + ".UserNetworkTaskProvider"
    }
}