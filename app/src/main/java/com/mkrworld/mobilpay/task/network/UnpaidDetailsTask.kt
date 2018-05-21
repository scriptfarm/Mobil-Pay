package com.mkrworld.mobilpay.task.network

import android.content.Context
import com.google.gson.Gson
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.network.NetworkConstants
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.network.unpaiddetails.DTOUnpaidDetailsResponse
import com.mkrworld.mobilpay.utils.UrlUtils
import org.json.JSONObject

class UnpaidDetailsTask : MobilPayBaseTask<DTOUnpaidDetailsResponse> {

    /**
     * Constructor
     *
     * @param context
     * @param requestJson
     * @param networkCallBack
     */
    constructor(context: Context, requestJson: JSONObject, networkCallBack: NetworkCallBack<DTOUnpaidDetailsResponse>) : super(context, requestJson, networkCallBack) {}

    public override fun parseNetworkResponse(jsonObject: JSONObject): DTOUnpaidDetailsResponse {
        return Gson().fromJson(jsonObject.toString(), DTOUnpaidDetailsResponse::class.java!!)
    }

    override fun getUrl(): String {
        return UrlUtils.getUrl(getContext(), R.string.url_unpaid_details)
    }

    public override fun getRequestType(): NetworkConstants.RequestType {
        return NetworkConstants.RequestType.POST
    }
}