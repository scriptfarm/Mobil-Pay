package com.mkrworld.androidlib.network

import android.content.Context
import android.os.AsyncTask
import org.json.JSONException
import org.json.JSONObject
import java.util.*

/**
 * Created by mkr on 3/4/18.
 */
abstract class BaseNetworkTask<MKR> {
    private var mNetworkCallBack : NetworkCallBack<MKR>? = null
    private var mRequestJsonObject : JSONObject? = null
    private var mContext : Context? = null
    private var mHeader : HashMap<String, String>? = null

    /**
     * Constructor
     *
     * @param context
     * @param requestJsonObject
     * @param networkCallBack
     */
    constructor(context : Context, requestJsonObject : JSONObject, networkCallBack : NetworkCallBack<MKR>?) {
        mContext = context;
        mNetworkCallBack = networkCallBack;
        mRequestJsonObject = requestJsonObject;
        mHeader = HashMap<String, String>();
    }

    /**
     * Method to get the Context
     *
     * @return
     */
    protected fun getContext() : Context {
        return mContext !!
    }

    /**
     * Method to Execute the network call.<br></br>This Method is run on back thread
     */
    fun executeTask() {
        object : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg voids : Void) : Void? {
                preExecute()
                return null
            }

            override fun onPostExecute(aVoid : Void?) {
                super.onPostExecute(aVoid)
                this@BaseNetworkTask.execute()
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    /**
     * Method to Execute the network call.<br></br>This Method is run on back thread
     */
    private fun execute() {
        if (isUsedLocalResponse()) {
            val jsonObject = FileHelper.getAssetFileJson(mContext !!, getLocalResponseJsonPath()!!)
            mNetworkCallBack?.onSuccess(parseNetworkResponse(jsonObject !!))
            return
        }

        if (isActualNetworkConnected()) {
            ConnectivityInfoUtils.isConnected(mContext !!, object : ConnectivityInfoUtils.OnConnectivityInfoUtilsListener {
                override fun onConnectivityInfoUtilsNetworkConnected() {
                    callApi()
                }

                override fun onConnectivityInfoUtilsNetworkDisconnected() {
                    mNetworkCallBack?.onError("No Network Connection", - 1)
                }
            })
        } else if (ConnectivityInfoUtils.isConnected(mContext !!)) {
            callApi()
        } else {
            mNetworkCallBack?.onError("No Network Connection", - 1)
        }

    }

    /**
     * Method to call the API
     */
    private fun callApi() {
        NetworkRequest.addToRequestQueue(getRequestType(), getUrl(), mRequestJsonObject !!, getHeader(), object : NetworkRequest.Companion.OnNetworkRequestListener {
            override fun onNetworkRequestCompleted(json : JSONObject) {
                if (! isBusinessResponseSuccess(json)) {
                    mNetworkCallBack?.onError(getBusinessResponseErrorMessage(json), - 1)
                    return
                }
                object : AsyncTask<Void, Void, MKR>() {

                    override fun doInBackground(vararg voids : Void) : MKR {
                        return parseNetworkResponse(json)
                    }

                    override fun onPostExecute(mkr : MKR) {
                        super.onPostExecute(mkr)
                        mNetworkCallBack?.onSuccess(mkr)
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            }

            override fun onNetworkRequestFailed(error : NetworkRequest.Companion.Error) {
                mNetworkCallBack?.onError(error.getErrorMessage(), error.getErrorCode() !!)
            }
        }, getTimeOut(), getRetryCount())
    }

    /**
     * Method to get the timeout time
     */
    protected fun getTimeOut() : Long {
        return NetworkConstants.SOCKET_TIME_OUT
    }

    /**
     * Method to get the retry count
     */
    protected fun getRetryCount() : Int {
        return 3
    }

    /**
     * Method to check weather the task fetch response from local storage or not
     *
     * @return
     */
    protected fun isUsedLocalResponse() : Boolean {
        return false
    }


    /**
     * Method to check the network connectivity of adapter or actual network
     *
     * @return TRUE to check actual network connectivity, FALSE check Adapter connectivity
     */
    open protected fun isActualNetworkConnected() : Boolean {
        return false
    }

    /**
     * Method to get the Requested JSON Object
     *
     * @return
     */
    fun getRequestJsonObject() : JSONObject {
        return mRequestJsonObject !!
    }

    /**
     * Method to Execute before the network call.<br></br>This Method is run on back thread
     */
    protected fun preExecute() {
        // Do whatever you want to
    }

    /**
     * Method to Get the Header<br></br> Caller override this method to add more value in the header<br></br>Caller MUST CALL THE SUPER Implementation
     *
     * @return Header of the
     */
    private fun getHeader() : Map<String, String> {
        mHeader !!.put("User-Agent", "Mozilla/5.0(macintosh;U;Intel Mac 05 X104; en-us; rv:1.9.2.2) Gecko/20100316 Firefox/19.0")
        mHeader !!.put("Content-Type", "application/json")
        val customHeader = getCustomHeader()
        if (customHeader != null) {
            mHeader !!.putAll(customHeader)
        }
        return mHeader !!
    }

    /**
     * Method to parse the API Response in the required Model
     *
     * @param jsonObject
     * @return
     */
    protected abstract fun parseNetworkResponse(jsonObject : JSONObject) : MKR


    /**
     * Method to check weather the Business Response is successful or not<br></br>
     * Ex : In response JSON code = 0 means success, AND code = 1 means failed
     *
     * @param jsonObject
     * @return
     */
    protected abstract fun isBusinessResponseSuccess(jsonObject : JSONObject) : Boolean

    /**
     * Method to get Business Response error message<br></br>
     *
     * @param jsonObject
     * @return
     */
    protected abstract fun getBusinessResponseErrorMessage(jsonObject : JSONObject) : String

    /**
     * Method to get the API URL
     *
     * @return
     */
    abstract fun getUrl() : String

    /**
     * Method to get the local response JSON path<br></br>Json should be save in ASSETS folder
     *
     * @return
     */
    protected abstract fun getLocalResponseJsonPath() : String?

    /**
     * Method to get the Header required to call API<br></br>Default Header Add in API call
     * <OL>
     * <LI>User-Agent->Mozilla/5.0(macintosh;U;Intel Mac 05 X104; en-us; rv:1.9.2.2) Gecko/20100316 Firefox/19.0</LI>
     * <LI>Content-Type->application/json</LI>
    </OL> *
     *
     * @return NULL if not pass any header
     */
    protected abstract fun getCustomHeader() : HashMap<String, String>?

    /**
     * Method to get the Request Type
     *
     * @return
     */
    protected abstract fun getRequestType() : NetworkConstants.RequestType

    /**
     * Method to get the JSONObject from HAshMap
     *
     * @param params
     * @return
     */
    protected fun getParamJSOBObject(params : HashMap<String, String>?) : JSONObject {
        val jsonObject = JSONObject()
        if (params == null) {
            return jsonObject
        }
        val keys = params.keys
        for (key in keys) {
            try {
                jsonObject.put(key, params[key])
            } catch (e : JSONException) {
                e.printStackTrace()
            }

        }
        return jsonObject
    }
}