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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;


/**
 * Created by mkr on 10/19/2016.
 */

public class NetworkRequest {
    private static final String TAG = BuildConfig.BASE_TAG + ".NetworkRequest";
    private static final int MAX_THREAD_COUNT = 4;
    private static Vector<NetworkRequest> mNetworkRequestList = new Vector<>();
    private static int mThreadCount = 0;
    private static Watcher mWatcher;
    private NetworkConstants.RequestType mRequestType;
    private String mURL;
    private JSONObject mJsonObject;
    private Map<String, String> mHeaders;
    private OnNetworkRequestListener mOnNetworkRequestListener;
    private long mTimeOut = 60000;
    private int mRetryCount = 3;

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
    private NetworkRequest(NetworkConstants.RequestType requestType, String url, JSONObject jsonObject, Map<String, String> headers, OnNetworkRequestListener onNetworkRequestListener, long timeOut, int retryCount) {
        mRequestType = requestType;
        mURL = url;
        mJsonObject = jsonObject;
        mHeaders = new HashMap<>();
        if (headers != null) {
            mHeaders.putAll(headers);
        }
        mOnNetworkRequestListener = onNetworkRequestListener;
        mTimeOut = timeOut;
        mRequestType = requestType;
    }

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
    public static void addToRequestQueue(final NetworkConstants.RequestType requestType, final String URL, final JSONObject jsonObject, final Map<String, String> headers, final OnNetworkRequestListener onNetworkRequestListener, final long timeOut, final int retryCount) {
        Tracer.debug(TAG, "NetworkRequest.addToRequestQueue() RequestType: " + requestType.name() + " URL: " + URL);
        NetworkRequest networkRequest = new NetworkRequest(requestType, URL, jsonObject, headers, onNetworkRequestListener, timeOut, retryCount);
        mNetworkRequestList.add(networkRequest);
        initiateWatcher();
    }

    /**
     * Method to initiate the Request Queue Watcher
     */
    private static void initiateWatcher() {
        Tracer.debug(TAG, "initiateWatcher : ");
        if (mNetworkRequestList.size() > 0 && (mWatcher == null || !mWatcher.isWatching())) {
            mWatcher = new Watcher();
            mWatcher.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
    private static Object executeRequest(NetworkConstants.RequestType requestType, String strURL, JSONObject jsonObject, Map<String, String> headers, long timeOut) {
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
            if (headers != null) {
                Set<String> keys = headers.keySet();
                for (String key : keys) {
                    urlConnection.setRequestProperty(key, headers.get(key));
                }
            }
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
            new Error(-1, "Network error : " + e.getMessage());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            new Error(-1, "Network error : " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            new Error(-1, "Network error : " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return new Error(-1, "Network error");
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

    /**
     * Class to watch the Performance
     */
    private static class Watcher extends AsyncTask<Void, NetworkRequest, Void> {
        private Boolean mIsWatching;

        /**
         * Constructor
         */
        public Watcher() {
            setWatching(true);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            setWatching(true);
            while (mNetworkRequestList.size() > 0 || mThreadCount > 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (mThreadCount < MAX_THREAD_COUNT && mNetworkRequestList.size() > 0) {
                    NetworkRequest networkRequest = mNetworkRequestList.get(0);
                    mNetworkRequestList.remove(0);
                    publishProgress(networkRequest);
                    mThreadCount++;
                }
            }
            setWatching(false);
            return null;
        }

        @Override
        protected void onProgressUpdate(NetworkRequest... values) {
            super.onProgressUpdate(values);
            new Worker(values[0]).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setWatching(false);
            initiateWatcher();
        }

        /**
         * Method to set the Watching state
         *
         * @param isWatching
         */
        private synchronized void setWatching(boolean isWatching) {
            mIsWatching = isWatching;
        }

        /**
         * Method to check weather the watcher is watching or not
         *
         * @return
         */
        public synchronized boolean isWatching() {
            return mIsWatching != null ? mIsWatching : false;
        }
    }

    /**
     * Worker class to perform the API Calling Operation
     */
    private static class Worker extends AsyncTask<Void, Void, Object> {
        private NetworkRequest mNetworkRequest;

        public Worker(NetworkRequest networkRequest) {
            mNetworkRequest = networkRequest;
        }

        @Override
        protected Object doInBackground(Void... params) {
            try {
                int i = 0;
                while (i < mNetworkRequest.mRetryCount) {
                    Object requestServer = executeRequest(mNetworkRequest.mRequestType, mNetworkRequest.mURL, mNetworkRequest.mJsonObject, mNetworkRequest.mHeaders, mNetworkRequest.mTimeOut);
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
            mThreadCount--;
            if (mNetworkRequest.mOnNetworkRequestListener != null) {
                if (result instanceof JSONObject) {
                    Tracer.debug(TAG, "onPostExecute(...).new AsyncTask() {...}.onPostExecute(JSON)");
                    mNetworkRequest.mOnNetworkRequestListener.onNetworkRequestCompleted((JSONObject) result);
                } else if (result instanceof Error) {
                    Tracer.debug(TAG, ".onPostExecute(...).new AsyncTask() {...}.onPostExecute(ERROR)");
                    mNetworkRequest.mOnNetworkRequestListener.onNetworkRequestFailed((Error) result);
                } else {
                    Tracer.debug(TAG, ".onPostExecute(...).new AsyncTask() {...}.onPostExecute(UNKNOWN)");
                    mNetworkRequest.mOnNetworkRequestListener.onNetworkRequestFailed(new Error(-1, "Unknown Error"));
                }
            }
        }

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
