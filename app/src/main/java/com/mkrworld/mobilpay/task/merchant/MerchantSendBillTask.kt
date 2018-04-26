package com.mkrworld.mobilpay.task.merchant

import android.content.Context
import com.google.gson.Gson
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.network.NetworkConstants
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.merchant.merchantsendbill.DTOMerchantSendBillResponse
import com.mkrworld.mobilpay.task.MobilPayBaseTask
import com.mkrworld.mobilpay.utils.UrlUtils
import org.json.JSONObject

/**
 * Created by mkr on 27/3/18.
 */
class MerchantSendBillTask : MobilPayBaseTask<DTOMerchantSendBillResponse> {

    /**
     * Constructor
     *
     * @param context
     * @param requestJson
     * @param networkCallBack
     */
    constructor(context : Context, requestJson : JSONObject, networkCallBack : NetworkCallBack<DTOMerchantSendBillResponse>) : super(context, requestJson, networkCallBack) {}

    public override fun parseNetworkResponse(jsonObject : JSONObject) : DTOMerchantSendBillResponse {
        return Gson().fromJson(jsonObject.toString(), DTOMerchantSendBillResponse::class.java !!)
    }

    override fun getUrl() : String {
        return UrlUtils.getUrl(getContext(), R.string.url_send_bill)
    }

    public override fun getRequestType() : NetworkConstants.RequestType {
        return NetworkConstants.RequestType.POST
    }
}