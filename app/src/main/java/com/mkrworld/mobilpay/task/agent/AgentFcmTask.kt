package com.mkrworld.mobilpay.task.agent

import android.content.Context

import com.google.gson.Gson
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.network.NetworkConstants
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.agent.agentdetails.DTOAgentDetailResponse
import com.mkrworld.mobilpay.dto.agent.agentfcm.DTOAgentFCMResponse
import com.mkrworld.mobilpay.task.comms.MobilPayBaseTask
import com.mkrworld.mobilpay.utils.UrlUtils

import org.json.JSONObject

/**
 * Created by mkr on 27/3/18.
 */
class AgentFcmTask : MobilPayBaseTask<DTOAgentFCMResponse> {

    /**
     * Constructor
     *
     * @param context
     * @param requestJson
     * @param networkCallBack
     */
    constructor(context : Context, requestJson : JSONObject, networkCallBack : NetworkCallBack<DTOAgentFCMResponse>) : super(context, requestJson, networkCallBack) {}

    public override fun parseNetworkResponse(jsonObject : JSONObject) : DTOAgentFCMResponse {
        return Gson().fromJson(jsonObject.toString(), DTOAgentFCMResponse::class.java !!)
    }

    override fun getUrl() : String {
        return UrlUtils.getUrl(getContext(), R.string.url_agent_fcm)
    }

    public override fun getRequestType() : NetworkConstants.RequestType {
        return NetworkConstants.RequestType.POST
    }
}
