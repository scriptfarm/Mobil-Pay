package com.mkrworld.mobilpay.task

import android.content.Context

import com.google.gson.Gson
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.network.NetworkConstants
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.agentdetails.DTOAgentDetailResponse
import com.mkrworld.mobilpay.dto.userdetail.DTOUserDetailResponse
import com.mkrworld.mobilpay.utils.UrlUtils

import org.json.JSONObject

/**
 * Created by mkr on 27/3/18.
 */
class UserListTask : MobilPayBaseTask<DTOUserDetailResponse> {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".UserListTask"
    }

    /**
     * Constructor
     *
     * @param context
     * @param requestJson
     * @param networkCallBack
     */
    constructor(context : Context, requestJson : JSONObject, networkCallBack : NetworkCallBack<DTOUserDetailResponse>) : super(context, requestJson, networkCallBack) {}

    public override fun parseNetworkResponse(jsonObject : JSONObject) : DTOUserDetailResponse {
        return Gson().fromJson(jsonObject.toString(), DTOUserDetailResponse::class.java !!)
    }

    override fun getUrl() : String {
        return UrlUtils.getUrl(getContext(), R.string.url_get_user_details)
    }

    public override fun getRequestType() : NetworkConstants.RequestType {
        return NetworkConstants.RequestType.POST
    }
}
