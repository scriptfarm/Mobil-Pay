package com.mkrworld.mobilpay.task.agent

import android.content.Context

import com.google.gson.Gson
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.network.NetworkConstants
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.agent.agentsendforgotpasswordotp.DTOAgentSendForgotPasswordOtpResponse
import com.mkrworld.mobilpay.task.MobilPayBaseTask
import com.mkrworld.mobilpay.utils.UrlUtils

import org.json.JSONObject

/**
 * Created by mkr on 27/3/18.
 */
class AgentSendForgotPasswordOtpTask : MobilPayBaseTask<DTOAgentSendForgotPasswordOtpResponse> {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".AgentSendForgotPasswordOtpTask"
    }

    /**
     * Constructor
     *
     * @param context
     * @param requestJson
     * @param networkCallBack
     */
    constructor(context : Context, requestJson : JSONObject, networkCallBack : NetworkCallBack<DTOAgentSendForgotPasswordOtpResponse>) : super(context, requestJson, networkCallBack) {}

    public override fun parseNetworkResponse(jsonObject : JSONObject) : DTOAgentSendForgotPasswordOtpResponse {
        return Gson().fromJson(jsonObject.toString(), DTOAgentSendForgotPasswordOtpResponse::class.java !!)
    }

    override fun getUrl() : String {
        return UrlUtils.getUrl(getContext(), R.string.url_send_forgot_password_otp)
    }

    public override fun getRequestType() : NetworkConstants.RequestType {
        return NetworkConstants.RequestType.POST
    }
}
