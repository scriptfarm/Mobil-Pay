package com.mkrworld.mobilpay.task

import android.content.Context

import com.google.gson.Gson
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.network.NetworkConstants
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.mobilenumberstatus.DTOMobileNumberStatusResponse
import com.mkrworld.mobilpay.utils.UrlUtils

import org.json.JSONObject

import java.lang.reflect.Constructor
import java.util.HashMap

/**
 * Created by mkr on 27/3/18.
 */
class MobileNumberStatusTask : MobilPayBaseTask<DTOMobileNumberStatusResponse> {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".MobileNumberStatusTask"
    }

    /**
     * Constructor
     *
     * @param context
     * @param requestJson
     * @param networkCallBack
     */
    constructor(context : Context, requestJson : JSONObject, networkCallBack : NetworkCallBack<DTOMobileNumberStatusResponse>) : super(context, requestJson, networkCallBack) {}

    public override fun parseNetworkResponse(jsonObject : JSONObject) : DTOMobileNumberStatusResponse {
        return Gson().fromJson(jsonObject.toString(), DTOMobileNumberStatusResponse::class.java !!)
    }

    override fun getUrl() : String {
        return UrlUtils.getUrl(getContext(), R.string.url_mobile_number_status)
    }

    public override fun getRequestType() : NetworkConstants.RequestType {
        return NetworkConstants.RequestType.POST
    }
}