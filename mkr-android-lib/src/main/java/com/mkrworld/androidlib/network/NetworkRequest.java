package com.mkrworld.androidlib.network;

import android.os.AsyncTask;

import com.mkrworld.androidlib.BuildConfig;
import com.mkrworld.androidlib.utils.Tracer;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;


/**
 * Created by mkr on 10/19/2016.
 */

public class NetworkRequest {
    private static final String TAG = BuildConfig.BASE_TAG + ".NetworkRequest";

    /**
     * Method to send Async Request to the server
     *
     * @param requestType
     * @param URL
     * @param jsonObject
     * @param onNetworkRequestListener
     * @param timeOut
     * @param retryCount
     */
    public static void sendAsyncRequest(final NetworkConstants.RequestType requestType, final String URL, final JSONObject jsonObject, final OnNetworkRequestListener onNetworkRequestListener, final long timeOut, final int retryCount) {
        Tracer.debug(TAG, "NetworkRequest.sendAsyncRequest() RequestType: " + requestType.name() + " URL: " + URL);
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... params) {
                try {
                    int i = 0;
                    while (i < retryCount) {
                        Object requestServer = executeRequest(requestType, URL, jsonObject, timeOut);
                        if (requestServer instanceof JSONObject) {
                            return requestServer;
                        }
                        i++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Tracer.error(TAG, "sendAsyncRequest(...).new AsyncTask() {...}.doInBackground() " + e.getMessage());
                }
                return null;
            }

            protected void onPostExecute(Object result) {
                super.onPostExecute(result);
                if (onNetworkRequestListener != null) {
                    if (result instanceof JSONObject) {
                        Tracer.debug(TAG, "onPostExecute(...).new AsyncTask() {...}.onPostExecute(JSON)");
                        onNetworkRequestListener.onNetworkRequestCompleted((JSONObject) result);
                    } else if (result instanceof Error) {
                        Tracer.debug(TAG, ".onPostExecute(...).new AsyncTask() {...}.onPostExecute(ERROR)");
                        onNetworkRequestListener.onNetworkRequestFailed((Error) result);
                    } else {
                        Tracer.debug(TAG, ".onPostExecute(...).new AsyncTask() {...}.onPostExecute(UNKNOWN)");
                        onNetworkRequestListener.onNetworkRequestFailed(new Error(3, "Unknown Error"));
                    }
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * Method to send Post Request
     *
     * @param strURL
     * @param jsonObject
     * @return
     */
    private static Object executeRequest(NetworkConstants.RequestType requestType, String strURL, JSONObject jsonObject, long timeOut) {
        Tracer.debug(TAG, "executeRequest: " + strURL);
        HttpURLConnection urlConnection = null;
        try {
            URL url = null;
            switch (requestType) {
                case GET:
                    url = new URL(getGETUrlWithParam(strURL, jsonObject));
                    break;
                default:
                    url = new URL(strURL);
            }
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(getRequestType(requestType));
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0(macintosh;U;Intel Mac 05 X104; en-us; rv:1.9.2.2) Gecko/20100316 Firefox/19.0");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setReadTimeout((int) timeOut);
            urlConnection.setConnectTimeout((int) timeOut);
            switch (requestType) {
                case GET:
                    break;
                default:
                    DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                    wr.writeBytes(jsonObject.toString());
                    wr.flush();
                    wr.close();
            }
            int responseCode = urlConnection.getResponseCode();
            switch (responseCode) {
                case HttpURLConnection.HTTP_OK:
                case HttpURLConnection.HTTP_ACCEPTED:
                case HttpURLConnection.HTTP_CREATED:
                case HttpURLConnection.HTTP_NO_CONTENT:
                    try {
                        InputStream inputStream = urlConnection.getInputStream();
                        byte data[] = new byte[128];
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        int l = 0;
                        while ((l = inputStream.read(data)) > 0) {
                            byteArrayOutputStream.write(data, 0, l);
                        }
                        String resp = new String(byteArrayOutputStream.toByteArray());
                        return new JSONObject(resp);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Tracer.error(TAG, "excutePost(Parse Json) " + e.getMessage());
                        return new Error(2, "JSON Parsing Error");
                    }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return new Error(1, "Network error");
    }

    /**
     * Method to get the Request Type
     *
     * @param requestType
     * @return
     */
    private static String getRequestType(NetworkConstants.RequestType requestType) {
        Tracer.debug(TAG, "getRequestType: " + requestType.name());
        switch (requestType) {
            case DELETE:
                return "DELETE";
            case GET:
                return "GET";
            case POST:
                return "POST";
            default:
                return "GET";
        }
    }

    /**
     * Method to get the URL based on Param
     *
     * @param url
     * @param jsonObject
     * @return
     */
    private static String getGETUrlWithParam(String url, JSONObject jsonObject) {
        Tracer.debug(TAG, "getGETUrlWithParam: " + url);
        if (jsonObject == null) {
            return url;
        }
        String param = "";
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            String s = jsonObject.optString(key).trim();
            if (!s.isEmpty()) {
                try {
                    param += "&" + key + "=" + URLEncoder.encode(s, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        param = param.trim();
        if (param.length() > 0) {
            param = param.substring(1).trim();
            url += "?" + param;
        }
        return url;
    }

    // ==========================================================================================================
    // ==========================================================================================================
    // ==========================================================================================================

    /**
     * This listener is used to get the status at the time of made any request
     * to the server<br>
     * If the request is successfully made to the server then
     * <b>onNetworkRequestCompleted(JSONObject json)</b> is invoke, where the
     * <b>json</b> is the response send by the server in respect of the request.<br>
     * If there is an error occur such as No-Network-Connection then
     * <b>onNetworkRequestFailed(Error error)</b> is invoke.<br>
     * If there is an warning occur such as Slow-Network-Connection then
     * <b>onNetworkRequestWarning(Warning warning)</b> is invoke.<br>
     * Error stop the requesting process but Warning doesn't. But user must
     * aware of warnings.
     **/
    public static interface OnNetworkRequestListener {

        /**
         * Callback to notifyTaskResponse that the request is successfully granted by the
         * server and response is returned from the server
         *
         * @param json response return from the server in the form of json
         */
        public void onNetworkRequestCompleted(JSONObject json);

        /**
         * Callback to notifyTaskResponse that there is some sort of error occur at the time
         * of requesting to the server
         *
         * @param error Error occur at the time of sending request to server
         */
        public void onNetworkRequestFailed(Error error);
    }

    /**
     * Class to hold the error data
     */
    public static class Error {
        private String mErrorMessage;
        private int mErrorCode;

        /**
         * Constructor
         *
         * @param errorCode
         * @param errorMessage
         */
        public Error(int errorCode, String errorMessage) {
            mErrorCode = errorCode;
            mErrorMessage = errorMessage;
        }

        /**
         * Method to get the Error Code
         *
         * @return
         */
        public int getErrorCode() {
            return mErrorCode;
        }

        /**
         * Method to get the error message
         *
         * @return
         */
        public String getErrorMessage() {
            return mErrorMessage;
        }
    }
}