package com.mkrworld.mobilpay.task

import android.content.Context

import com.google.gson.Gson
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.network.NetworkConstants
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.sendforgotpasswordotp.DTOSendForgotPasswordOtpResponse
import com.mkrworld.mobilpay.utils.UrlUtils

import org.json.JSONObject

/**
 * Created by mkr on 27/3/18.
 */
class SendForgotPasswordOtpTask : MobilPayBaseTask<DTOSendForgotPasswordOtpResponse> {

    /**
     * Constructor
     *
     * @param context
     * @param requestJson
     * @param networkCallBack
     */
    constructor(context : Context, requestJson : JSONObject, networkCallBack : NetworkCallBack<DTOSendForgotPasswordOtpResponse>) : super(context, requestJson, networkCallBack) {}

    public override fun parseNetworkResponse(jsonObject : JSONObject) : DTOSendForgotPasswordOtpResponse {
        return Gson().fromJson(jsonObject.toString(), DTOSendForgotPasswordOtpResponse::class.java !!)
    }

    override fun getUrl() : String {
        return UrlUtils.getUrl(getContext(), R.string.url_send_forgot_password_otp)
    }

    public override fun getRequestType() : NetworkConstants.RequestType {
        return NetworkConstants.RequestType.POST
    }
}