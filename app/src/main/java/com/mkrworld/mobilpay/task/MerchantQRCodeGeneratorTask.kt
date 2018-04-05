package com.mkrworld.mobilpay.task

import android.content.Context

import com.google.gson.Gson
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.network.NetworkConstants
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.merchantqrcodegenarator.DTOMerchantQRCodeGeneratorResponse
import com.mkrworld.mobilpay.utils.UrlUtils

import org.json.JSONObject

import java.util.HashMap

/**
 * Created by mkr on 27/3/18.
 */
class MerchantQRCodeGeneratorTask : MobilPayBaseTask<DTOMerchantQRCodeGeneratorResponse> {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".MerchantQRCodeGeneratorTask"
    }

    /**
     * Constructor
     *
     * @param context
     * @param requestJson
     * @param networkCallBack
     */
    constructor(context : Context, requestJson : JSONObject, networkCallBack : NetworkCallBack<DTOMerchantQRCodeGeneratorResponse>) : super(context, requestJson, networkCallBack) {}

    public override fun parseNetworkResponse(jsonObject : JSONObject) : DTOMerchantQRCodeGeneratorResponse {
        return Gson().fromJson(jsonObject.toString(), DTOMerchantQRCodeGeneratorResponse::class.java !!)
    }

    override fun getUrl() : String {
        return UrlUtils.getUrl(getContext(), R.string.url_generate_qr_code_token)
    }

    public override fun getRequestType() : NetworkConstants.RequestType {
        return NetworkConstants.RequestType.POST
    }
}
