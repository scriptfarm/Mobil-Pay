package com.mkrworld.mobilpay.task

import android.content.Context

import com.google.gson.Gson
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.network.NetworkConstants
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.merchantchangepassword.DTOMerchantChangePasswordResponse
import com.mkrworld.mobilpay.utils.UrlUtils

import org.json.JSONObject

import java.util.HashMap

/**
 * Created by mkr on 27/3/18.
 */
class MerchantChangePasswordTask : MobilPayBaseTask<DTOMerchantChangePasswordResponse> {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".MerchantChangePasswordTask"
    }

    /**
     * Constructor
     *
     * @param context
     * @param requestJson
     * @param networkCallBack
     */
    constructor(context : Context, requestJson : JSONObject, networkCallBack : NetworkCallBack<DTOMerchantChangePasswordResponse>) : super(context, requestJson, networkCallBack) {}

    public override fun parseNetworkResponse(jsonObject : JSONObject) : DTOMerchantChangePasswordResponse {
        return Gson().fromJson(jsonObject.toString(), DTOMerchantChangePasswordResponse::class.java !!)
    }

    override fun getUrl() : String {
        return UrlUtils.getUrl(getContext(), R.string.url_change_password)
    }

    public override fun getLocalResponseJsonPath() : String? {
        return null
    }

    public override fun getRequestType() : NetworkConstants.RequestType {
        return NetworkConstants.RequestType.POST
    }
}
