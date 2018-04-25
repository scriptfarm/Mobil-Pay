package com.mkrworld.mobilpay.task

import android.content.Context

import com.google.gson.Gson
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.network.NetworkConstants
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.collectionsummary.DTOCollectionSummaryResponse
import com.mkrworld.mobilpay.dto.login.DTOLoginResponse
import com.mkrworld.mobilpay.utils.UrlUtils

import org.json.JSONObject

/**
 * Created by mkr on 27/3/18.
 */
class CollectionSummaryTask : MobilPayBaseTask<DTOCollectionSummaryResponse> {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".LoginTask"
    }

    /**
     * Constructor
     *
     * @param context
     * @param requestJson
     * @param networkCallBack
     */
    constructor(context : Context, requestJson : JSONObject, networkCallBack : NetworkCallBack<DTOCollectionSummaryResponse>) : super(context, requestJson, networkCallBack) {}

    public override fun parseNetworkResponse(jsonObject : JSONObject) : DTOCollectionSummaryResponse {
        return Gson().fromJson(jsonObject.toString(), DTOCollectionSummaryResponse::class.java !!)
    }

    override fun getUrl() : String {
        return UrlUtils.getUrl(getContext(), R.string.url_collection_summary)
    }

    public override fun getRequestType() : NetworkConstants.RequestType {
        return NetworkConstants.RequestType.POST
    }
}
