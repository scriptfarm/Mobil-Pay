package com.mkrworld.mobilpay.task.network

import android.content.Context
import com.google.gson.Gson
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.network.NetworkConstants
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.network.cashcollect.DTOCashCollectResponse
import com.mkrworld.mobilpay.utils.UrlUtils
import org.json.JSONObject

/**
 * Created by mkr on 27/3/18.
 */
class AgentCashCollectTask : MobilPayBaseTask<DTOCashCollectResponse> {

    /**
     * Constructor
     *
     * @param context
     * @param requestJson
     * @param networkCallBack
     */
    constructor(context : Context, requestJson : JSONObject, networkCallBack : NetworkCallBack<DTOCashCollectResponse>) : super(context, requestJson, networkCallBack) {}

    public override fun parseNetworkResponse(jsonObject : JSONObject) : DTOCashCollectResponse {
        return Gson().fromJson(jsonObject.toString(), DTOCashCollectResponse::class.java !!)
    }

    override fun getUrl() : String {
        return UrlUtils.getUrl(getContext(), R.string.url_pay_cash)
    }

    public override fun getRequestType() : NetworkConstants.RequestType {
        return NetworkConstants.RequestType.POST
    }
}
