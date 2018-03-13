package com.mkrworld.androidlib.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.mkrworld.androidlib.BuildConfig;
import com.mkrworld.androidlib.utils.Tracer;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by A1ZFKXA3 on 8/31/2017.
 */

public class ConnectivityInfoUtils {
    private static final String TAG = BuildConfig.BASE_TAG + ".ConnectivityInfoUtils";

    /**
     * Get the Information of network
     *
     * @param context
     * @return
     */
    private static NetworkInfo getNetworkInfo(Context context) {
        Tracer.debug(TAG, "ConnectivityInfo.getNetworkInfo()");
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    }

    /**
     * Method to check weather the device is connected to the network or not.
     *
     * @param context
     * @return TRUE if connected with network, else return FALSE
     */
    public static boolean isConnected(Context context) {
        Tracer.debug(TAG, "ConnectivityInfo.isConnected()");
        NetworkInfo info = ConnectivityInfoUtils.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    /**
     * Method to check weather the device is actually connected to the network or not.<br>
     * Method Will take time to response.
     *
     * @param context
     * @param onConnectivityInfoUtilsListener Callback to notifyTaskResponse the Connection state.
     */
    public static void isConnected(Context context, final OnConnectivityInfoUtilsListener onConnectivityInfoUtilsListener) {
        Tracer.debug(TAG, "ConnectivityInfo.isConnected()");
        if (isConnected(context)) {
            new AsyncTask<Void, Void, Boolean>() {

                @Override
                protected Boolean doInBackground(Void... voids) {
                    Tracer.debug(TAG, "doInBackground: ");
                    HttpURLConnection urlConnection = null;
                    try {
                        URL url = new URL("https://www.google.com/");
                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(3000);
                        urlConnection.setConnectTimeout(3000);
                        int responseCode = urlConnection.getResponseCode();
                        Tracer.error(TAG, "doInBackground: " + responseCode);
                        switch (responseCode) {
                            case HttpURLConnection.HTTP_OK:
                            case HttpURLConnection.HTTP_ACCEPTED:
                            case HttpURLConnection.HTTP_CREATED:
                            case HttpURLConnection.HTTP_NO_CONTENT:
                                Tracer.error(TAG, "doInBackground: SUCCESS : ");
                                return true;
                            default:
                                Tracer.error(TAG, "doInBackground: FAILED : ");
                        }
                    } catch (Exception e) {
                        Tracer.error(TAG, "doInBackground: FAILED : " + e.getMessage());
                    } finally {
                        if (urlConnection != null) {
                            urlConnection.disconnect();
                        }
                    }
                    return false;
                }

                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    super.onPostExecute(aBoolean);
                    Tracer.debug(TAG, "onPostExecute: " + aBoolean);
                    if (aBoolean) {
                        onConnectivityInfoUtilsListener.onConnectivityInfoUtilsNetworkConnected();
                    } else {
                        onConnectivityInfoUtilsListener.onConnectivityInfoUtilsNetworkDisconnected();
                    }
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            onConnectivityInfoUtilsListener.onConnectivityInfoUtilsNetworkDisconnected();
        }
    }

    /**
     * Callback to listen the Network Connectivity Event
     */
    public static interface OnConnectivityInfoUtilsListener {

        /**
         * Method to notifyTaskResponse that user connected with network
         */
        public void onConnectivityInfoUtilsNetworkConnected();

        /**
         * Method to notifyTaskResponse that user dis-connected with network
         */
        public void onConnectivityInfoUtilsNetworkDisconnected();

    }
}
