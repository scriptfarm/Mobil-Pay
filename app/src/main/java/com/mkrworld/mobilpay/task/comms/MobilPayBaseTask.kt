package com.mkrworld.mobilpay.task.comms

import android.accounts.AccountManager.KEY_ERROR_CODE
import android.content.Context

import com.mkrworld.androidlib.network.BaseNetworkTask
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig

import org.json.JSONObject

import java.util.HashMap

/**
 * Created by mkr on 28/3/18.
 */

abstract class MobilPayBaseTask<MKR> : BaseNetworkTask<MKR> {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".MobilPayBaseTask"
        private val KEY_ERROR = "error"
        private val KEY_ERROR_CODE = "error_code"
        private val KEY_MESSAGE = "message"
    }

    /**
     * Constructor
     *
     * @param context
     * @param requestJsonObject
     * @param networkCallBack
     */
    constructor(context : Context, requestJsonObject : JSONObject, networkCallBack : NetworkCallBack<MKR>) : super(context, requestJsonObject, networkCallBack) {}

    override fun isBusinessResponseSuccess(jsonObject : JSONObject) : Boolean {
        Tracer.debug(TAG, "isBusinessResponseSuccess : " + jsonObject !!)
        if (jsonObject == null) {
            return false
        }
        return if (jsonObject.has(KEY_ERROR)) ! jsonObject.optBoolean(KEY_ERROR, true) else false
    }

    public override fun getBusinessResponseErrorMessage(jsonObject : JSONObject) : String {
        Tracer.debug(TAG, "getBusinessResponseErrorMessage : " + jsonObject !!)
        if (jsonObject == null) {
            return "Unknown error"
        }
        return if (jsonObject.has(KEY_MESSAGE)) jsonObject.optString(KEY_MESSAGE, "Unknown error") else "Unknown error"
    }

    public override fun getBusinessResponseErrorCode(jsonObject : JSONObject) : Int {
        Tracer.debug(TAG, "getBusinessResponseErrorCode : " + jsonObject !!)
        if (jsonObject == null) {
            return - 1
        }
        return if (jsonObject.has(KEY_ERROR_CODE)) jsonObject.optInt(KEY_ERROR_CODE, - 1) else - 1
    }

    override fun isActualNetworkConnected() : Boolean {
        return true
    }

    public override fun getLocalResponseJsonPath() : String? {
        return null
    }

    public override fun getCustomHeader() : HashMap<String, String>? {
        return null
    }
}
