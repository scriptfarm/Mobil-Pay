package com.mkrworld.androidlib.network

import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask

import com.mkrworld.androidlib.BuildConfig
import com.mkrworld.androidlib.utils.Tracer

import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by A1ZFKXA3 on 8/31/2017.
 */

class ConnectivityInfoUtils {
    private val TAG = BuildConfig.BASE_TAG + ".ConnectivityInfoUtils"

    companion object {

        /**
         * Get the Information of network
         *
         * @param context
         * @return
         */
        private fun getNetworkInfo(context : Context) : NetworkInfo? {
            Tracer.debug(TAG, "ConnectivityInfo.getNetworkInfo()")
            return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        }

        /**
         * Method to check weather the device is connected to the network or not.
         *
         * @param context
         * @return TRUE if connected with network, else return FALSE
         */
        fun isConnected(context : Context) : Boolean {
            Tracer.debug(TAG, "ConnectivityInfo.isConnected()")
            val info = ConnectivityInfoUtils.getNetworkInfo(context)
            return info != null && info.isConnected
        }

        /**
         * Method to check weather the device is actually connected to the network or not.<br></br>
         * Method Will take time to response.
         *
         * @param context
         * @param onConnectivityInfoUtilsListener Callback to notifyTaskResponse the Connection state.
         */
        fun isConnected(context : Context, onConnectivityInfoUtilsListener : OnConnectivityInfoUtilsListener) {
            Tracer.debug(TAG, "ConnectivityInfo.isConnected()")
            if (isConnected(context)) {
                object : AsyncTask<Void, Void, Boolean>() {

                    override fun doInBackground(vararg voids : Void) : Boolean? {
                        Tracer.debug(TAG, "doInBackground: ")
                        var urlConnection : HttpURLConnection? = null
                        try {
                            val url = URL("https://www.google.com/")
                            urlConnection = url.openConnection() as HttpURLConnection
                            urlConnection.requestMethod = "GET"
                            urlConnection.readTimeout = 3000
                            urlConnection.connectTimeout = 3000
                            val responseCode = urlConnection.responseCode
                            Tracer.error(TAG, "doInBackground: $responseCode")
                            when (responseCode) {
                                HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_ACCEPTED, HttpURLConnection.HTTP_CREATED, HttpURLConnection.HTTP_NO_CONTENT -> {
                                    Tracer.error(TAG, "doInBackground: SUCCESS : ")
                                    return true
                                }
                                else -> Tracer.error(TAG, "doInBackground: FAILED : ")
                            }
                        } catch (e : Exception) {
                            Tracer.error(TAG, "doInBackground: FAILED : " + e.message)
                        } finally {
                            if (urlConnection != null) {
                                urlConnection.disconnect()
                            }
                        }
                        return false
                    }

                    override fun onPostExecute(aBoolean : Boolean?) {
                        super.onPostExecute(aBoolean)
                        Tracer.debug(TAG, "onPostExecute: " + aBoolean !!)
                        if (aBoolean) {
                            onConnectivityInfoUtilsListener?.onConnectivityInfoUtilsNetworkConnected()
                        } else {
                            onConnectivityInfoUtilsListener?.onConnectivityInfoUtilsNetworkDisconnected()
                        }
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            } else {
                onConnectivityInfoUtilsListener?.onConnectivityInfoUtilsNetworkDisconnected()
            }
        }
    }

    /**
     * Callback to listen the Network Connectivity Event
     */
    interface OnConnectivityInfoUtilsListener {

        /**
         * Method to notifyTaskResponse that user connected with network
         */
        fun onConnectivityInfoUtilsNetworkConnected()

        /**
         * Method to notifyTaskResponse that user dis-connected with network
         */
        fun onConnectivityInfoUtilsNetworkDisconnected()

    }
}
