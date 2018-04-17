package com.mkrworld.androidlib.network

import android.os.AsyncTask
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by mkr on 3/4/18.
 */
class NetworkRequest {
    companion object {
        private val MAX_THREAD_COUNT : Int = 4
        private var mNetworkRequestList : Vector<NetworkRequest> = Vector<NetworkRequest>()
        private var mThreadCount : Int = 0;
        private var mWatcher : Watcher? = null;

        /**
         * Method to add the Network request in the QUEUE
         *
         * @param requestType
         * @param URL
         * @param jsonObject
         * @param headers
         * @param onNetworkRequestListener
         * @param timeOut
         * @param retryCount
         */
        fun addToRequestQueue(requestType : NetworkConstants.RequestType, URL : String, jsonObject : JSONObject, headers : Map<String, String>, onNetworkRequestListener : OnNetworkRequestListener, timeOut : Long, retryCount : Int) {
            val networkRequest = NetworkRequest(requestType, URL, jsonObject, headers, onNetworkRequestListener, timeOut, retryCount)
            mNetworkRequestList?.add(networkRequest)
            initiateWatcher()
        }

        /**
         * Method to initiate the Request Queue Watcher
         */
        private fun initiateWatcher() {
            if (mNetworkRequestList !!.size > 0 && (mWatcher == null || ! mWatcher !!.isWatching())) {
                mWatcher = Watcher()
                mWatcher !!.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            }
        }

        /**
         * Method to send Post Request
         *
         * @param requestType
         * @param strURL
         * @param jsonObject
         * @param headers
         * @param timeOut
         * @return
         */
        private fun executeRequest(requestType : NetworkConstants.RequestType, strURL : String, jsonObject : JSONObject, headers : Map<String, String>?, timeOut : Long) : Any {
            var urlConnection : HttpURLConnection? = null
            try {
                var url : URL? = null
                when (requestType) {
                    NetworkConstants.RequestType.GET -> url = URL(getGETUrlWithParam(strURL, jsonObject))
                    else -> url = URL(strURL)
                }
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = getRequestType(requestType)
                if (headers != null) {
                    val keys = headers.keys
                    for (key in keys) {
                        urlConnection.setRequestProperty(key, headers[key])
                    }
                }
                urlConnection.readTimeout = timeOut.toInt()
                urlConnection.connectTimeout = timeOut.toInt()
                when (requestType) {
                    NetworkConstants.RequestType.GET -> {
                    }
                    else -> {
                        val wr = DataOutputStream(urlConnection.outputStream)
                        wr.writeBytes(jsonObject.toString())
                        wr.flush()
                        wr.close()
                    }
                }
                val responseCode = urlConnection.responseCode
                when (responseCode) {
                    HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_ACCEPTED, HttpURLConnection.HTTP_CREATED, HttpURLConnection.HTTP_NO_CONTENT -> try {
                        val inputStream = urlConnection.inputStream
                        val data = ByteArray(128)
                        val byteArrayOutputStream = ByteArrayOutputStream()
                        var l = 0
                        while (true) {
                            l = inputStream.read(data)
                            if (l <= 0) {
                                break
                            }
                            byteArrayOutputStream.write(data, 0, l)
                        }
                        val resp = String(byteArrayOutputStream.toByteArray())
                        return JSONObject(resp)
                    } catch (e : Exception) {
                        e.printStackTrace()
                        return Error(2, "JSON Parsing Error")
                    }

                }
            } catch (e : UnsupportedEncodingException) {
                e.printStackTrace()
                Error(- 1, "Network error : " + e.message)
            } catch (e : MalformedURLException) {
                e.printStackTrace()
                Error(- 1, "Network error : " + e.message)
            } catch (e : IOException) {
                e.printStackTrace()
                Error(- 1, "Network error : " + e.message)
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect()
                }
            }
            return Error(- 1, "Network error")
        }

        /**
         * Method to get the Request Type
         *
         * @param requestType
         * @return
         */
        private fun getRequestType(requestType : NetworkConstants.RequestType) : String {
            when (requestType) {
                NetworkConstants.RequestType.DELETE -> return "DELETE"
                NetworkConstants.RequestType.GET -> return "GET"
                NetworkConstants.RequestType.POST -> return "POST"
                else -> return "GET"
            }
        }

        /**
         * Method to get the URL based on Param
         *
         * @param url
         * @param jsonObject
         * @return
         */
        private fun getGETUrlWithParam(url : String, jsonObject : JSONObject?) : String {
            var url = url
            if (jsonObject == null) {
                return url
            }
            var param = ""
            val keys = jsonObject.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                val s = jsonObject.optString(key).trim()
                if (! s.isEmpty()) {
                    try {
                        param += "&" + key + "=" + URLEncoder.encode(s, "UTF-8")
                    } catch (e : UnsupportedEncodingException) {
                        e.printStackTrace()
                    }

                }
            }
            param = param.trim()
            if (param.length > 0) {
                param = param.substring(1).trim()
                url += "?$param"
            }
            return url
        }

        /**
         * Watcher is used to check weather the request Queue is empty or not
         */
        private class Watcher() : AsyncTask<Void, NetworkRequest, Void>() {
            private var mIsWatching : Boolean? = null

            init {
                mIsWatching = true
            }

            override fun doInBackground(vararg voids : Void) : Void? {
                setWatching(true)
                while (mNetworkRequestList !!.size > 0 || mThreadCount > 0) {
                    try {
                        Thread.sleep(100)
                    } catch (e : InterruptedException) {
                        e.printStackTrace()
                    }

                    if (mThreadCount < MAX_THREAD_COUNT && mNetworkRequestList !!.size > 0) {
                        val networkRequest = mNetworkRequestList !!.get(0)
                        mNetworkRequestList !!.removeAt(0)
                        publishProgress(networkRequest)
                        mThreadCount ++
                    }
                }
                setWatching(false)
                return null
            }

            override fun onProgressUpdate(vararg values : NetworkRequest) {
                super.onProgressUpdate(*values)
                Worker(values[0]).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            }

            protected override fun onPostExecute(aVoid : Void?) {
                super.onPostExecute(aVoid)
                setWatching(false)
                initiateWatcher()
            }

            /**
             * Method to check weather the watcher is watching or not
             *
             * @return
             */
            @Synchronized
            fun isWatching() : Boolean {
                return if (mIsWatching != null) mIsWatching !! else false
            }

            /**
             * Method to set the Watching state
             *
             * @param isWatching
             */
            @Synchronized
            private fun setWatching(isWatching : Boolean) {
                mIsWatching = isWatching
            }
        }

        /**
         * Worker class to perform the API Calling Operation
         */
        private class Worker() : AsyncTask<Void, Void, Any>() {
            private var mNetworkRequest : NetworkRequest? = null

            /**
             * Constructor
             */
            constructor(networkRequest : NetworkRequest) : this() {
                mNetworkRequest = networkRequest
            }

            override fun doInBackground(vararg params : Void) : Any? {
                try {
                    var i = 0
                    while (i < mNetworkRequest !!.mRetryCount) {
                        val requestServer = executeRequest(mNetworkRequest !!.mRequestType !!, mNetworkRequest !!.mURL !!, mNetworkRequest !!.mJsonObject !!, mNetworkRequest !!.mHeaders, mNetworkRequest !!.mTimeOut)
                        if (requestServer is JSONObject) {
                            return requestServer
                        }
                        i ++
                    }
                } catch (e : Exception) {
                    e.printStackTrace()
                }

                return null
            }

            override fun onPostExecute(result : Any?) {
                super.onPostExecute(result)
                mThreadCount --
                if (result is JSONObject) {
                    mNetworkRequest?.mOnNetworkRequestListener?.onNetworkRequestCompleted(result)
                } else if (result is Error) {
                    mNetworkRequest?.mOnNetworkRequestListener?.onNetworkRequestFailed(result)
                } else {
                    mNetworkRequest?.mOnNetworkRequestListener?.onNetworkRequestFailed(Error(- 1, "Unknown Error"))
                }
            }

        }

        /**
         * Class to hold the error data
         */
        class Error {
            private var mErrorMessage : String? = null;
            private var mErrorCode : Int? = null;

            /**
             * Constructor
             *
             * @param errorCode
             * @param errorMessage
             */
            constructor(errorCode : Int, errorMessage : String) {
                mErrorCode = errorCode
                mErrorMessage = errorMessage
            }

            /**
             * Method to get the Error Code
             *
             * @return
             */
            fun getErrorCode() : Int {
                return mErrorCode !!
            }

            /**
             * Method to get the error message
             *
             * @return
             */
            fun getErrorMessage() : String {
                return mErrorMessage !!
            }
        }

        /**
         * This listener is used to get the status at the time of made any request
         * to the server<br></br>
         * If the request is successfully made to the server then
         * **onNetworkRequestCompleted(JSONObject json)** is invoke, where the
         * **json** is the response send by the server in respect of the request.<br></br>
         * If there is an error occur such as No-Network-Connection then
         * **onNetworkRequestFailed(Error error)** is invoke.<br></br>
         * If there is an warning occur such as Slow-Network-Connection then
         * **onNetworkRequestWarning(Warning warning)** is invoke.<br></br>
         * Error stop the requesting process but Warning doesn't. But user must
         * aware of warnings.
         */
        interface OnNetworkRequestListener {

            /**
             * Callback to notifyTaskResponse that the request is successfully granted by the
             * server and response is returned from the server
             *
             * @param json response return from the server in the form of json
             */
            fun onNetworkRequestCompleted(json : JSONObject)

            /**
             * Callback to notifyTaskResponse that there is some sort of error occur at the time
             * of requesting to the server
             *
             * @param error Error occur at the time of sending request to server
             */
            fun onNetworkRequestFailed(error : Error)
        }
    }

    private var mRequestType : NetworkConstants.RequestType? = null
    private var mURL : String? = null
    private var mJsonObject : JSONObject? = null
    private var mHeaders : HashMap<String, String>? = null
    private var mOnNetworkRequestListener : OnNetworkRequestListener? = null
    private var mTimeOut : Long = 60000;
    private var mRetryCount : Int = 3;


    /**
     * Constructor
     *
     * @param requestType
     * @param url
     * @param jsonObject
     * @param headers
     * @param onNetworkRequestListener
     * @param timeOut
     * @param retryCount
     */
    private constructor(requestType : NetworkConstants.RequestType, url : String, jsonObject : JSONObject, headers : Map<String, String>?, onNetworkRequestListener : OnNetworkRequestListener, timeOut : Long, retryCount : Int) {
        mRequestType = requestType
        mURL = url
        mJsonObject = jsonObject
        mHeaders = HashMap<String, String>()
        if (headers != null) {
            mHeaders !!.putAll(headers)
        }
        mOnNetworkRequestListener = onNetworkRequestListener
        mTimeOut = timeOut
        mRequestType = requestType
    }
}