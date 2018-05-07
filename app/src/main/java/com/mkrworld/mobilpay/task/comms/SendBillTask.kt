package com.mkrworld.mobilpay.task.comms

import android.content.Context
import com.google.gson.Gson
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.network.NetworkConstants
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.comms.sendbill.DTOSendBillResponse
import com.mkrworld.mobilpay.utils.UrlUtils
import org.json.JSONObject

/**
 * Created by mkr on 27/3/18.
 */
class SendBillTask : MobilPayBaseTask<DTOSendBillResponse> {

    /**
     * Constructor
     *
     * @param context
     * @param requestJson
     * @param networkCallBack
     */
    constructor(context : Context, requestJson : JSONObject, networkCallBack : NetworkCallBack<DTOSendBillResponse>) : super(context, requestJson, networkCallBack) {}

    public override fun parseNetworkResponse(jsonObject : JSONObject) : DTOSendBillResponse {
        return Gson().fromJson(jsonObject.toString(), DTOSendBillResponse::class.java !!)
    }

    override fun getUrl() : String {
        return UrlUtils.getUrl(getContext(), R.string.url_send_bill)
    }

    public override fun getRequestType() : NetworkConstants.RequestType {
        return NetworkConstants.RequestType.POST
    }
}
