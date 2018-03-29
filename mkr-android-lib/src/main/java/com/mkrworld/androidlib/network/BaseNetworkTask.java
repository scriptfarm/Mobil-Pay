package com.mkrworld.androidlib.network;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by A1ZFKXA3 on 1/30/2018.
 */

public abstract class BaseNetworkTask<MKR> {
    private NetworkCallBack mNetworkCallBack;
    private JSONObject mRequestJsonObject;
    private Context mContext;
    private HashMap<String, String> mHeader;

    /**
     * Constructor
     *
     * @param context
     * @param requestJsonObject
     * @param networkCallBack
     */
    public BaseNetworkTask(Context context, JSONObject requestJsonObject, NetworkCallBack networkCallBack) {
        mContext = context;
        mNetworkCallBack = networkCallBack;
        mRequestJsonObject = requestJsonObject;
        mHeader = new HashMap<>();
    }

    /**
     * Method to get the Context
     *
     * @return
     */
    protected Context getContext() {
        return mContext;
    }

    /**
     * Method to Execute the network call.<br>This Method is run on back thread
     */
    public final void executeTask() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                preExecute();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                BaseNetworkTask.this.execute();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * Method to Execute the network call.<br>This Method is run on back thread
     */
    private void execute() {
        if (isUsedLocalResponse()) {
            if (mNetworkCallBack != null) {
                JSONObject jsonObject = FileHelper.getAssetFileJson(mContext, getLocalResponseJsonPath());
                mNetworkCallBack.onSuccess(parseNetworkResponse(jsonObject));
            }
            return;
        }

        if (isActualNetworkConnected()) {
            ConnectivityInfoUtils.isConnected(mContext, new ConnectivityInfoUtils.OnConnectivityInfoUtilsListener() {
                @Override
                public void onConnectivityInfoUtilsNetworkConnected() {
                    callApi();
                }

                @Override
                public void onConnectivityInfoUtilsNetworkDisconnected() {
                    if (mNetworkCallBack != null) {
                        mNetworkCallBack.onError("No Network Connection", -1);
                    }
                }
            });
        } else if (ConnectivityInfoUtils.isConnected(mContext)) {
            callApi();
        } else {
            mNetworkCallBack.onError("No Network Connection", -1);
        }

    }

    /**
     * Method to call the API
     */
    private void callApi() {
        NetworkRequest.addToRequestQueue(getRequestType(), getUrl(), mRequestJsonObject, getHeader(), new NetworkRequest.OnNetworkRequestListener() {
            @Override
            public void onNetworkRequestCompleted(final JSONObject json) {
                if (mNetworkCallBack != null) {
                    if (!isBusinessResponseSuccess(json)) {
                        mNetworkCallBack.onError(getBusinessResponseErrorMessage(json), -1);
                        return;
                    }
                    new AsyncTask<Void, Void, MKR>() {

                        @Override
                        protected MKR doInBackground(Void... voids) {
                            return parseNetworkResponse(json);
                        }

                        @Override
                        protected void onPostExecute(MKR mkr) {
                            super.onPostExecute(mkr);
                            mNetworkCallBack.onSuccess(mkr);
                        }
                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }

            @Override
            public void onNetworkRequestFailed(NetworkRequest.Error error) {
                if (mNetworkCallBack != null) {
                    mNetworkCallBack.onError(error.getErrorMessage(), error.getErrorCode());
                }
            }
        }, getTimeOut(), getRetryCount());
    }

    /**
     * Method to get the timeout time
     */
    protected long getTimeOut() {
        return NetworkConstants.SOCKET_TIME_OUT;
    }

    /**
     * Method to get the retry count
     */
    protected int getRetryCount() {
        return 3;
    }

    /**
     * Method to check weather the task fetch response from local storage or not
     *
     * @return
     */
    protected boolean isUsedLocalResponse() {
        return false;
    }


    /**
     * Method to check the network connectivity of adapter or actual network
     *
     * @return TRUE to check actual network connectivity, FALSE check Adapter connectivity
     */
    protected boolean isActualNetworkConnected() {
        return false;
    }

    /**
     * Method to get the Requested JSON Object
     *
     * @return
     */
    public JSONObject getRequestJsonObject() {
        return mRequestJsonObject;
    }

    /**
     * Method to Execute before the network call.<br>This Method is run on back thread
     */
    protected void preExecute() {
        // Do whatever you want to
    }

    /**
     * Method to Get the Header<br> Caller override this method to add more value in the header<br>Caller MUST CALL THE SUPER Implementation
     *
     * @return Header of the
     */
    private Map<String, String> getHeader() {
        mHeader.put("User-Agent", "Mozilla/5.0(macintosh;U;Intel Mac 05 X104; en-us; rv:1.9.2.2) Gecko/20100316 Firefox/19.0");
        mHeader.put("Content-Type", "application/json");
        HashMap<String, String> customHeader = getCustomHeader();
        if (customHeader != null) {
            mHeader.putAll(customHeader);
        }
        return mHeader;
    }

    /**
     * Method to parse the API Response in the required Model
     *
     * @param jsonObject
     * @return
     */
    protected abstract MKR parseNetworkResponse(JSONObject jsonObject);


    /**
     * Method to check weather the Business Response is successful or not<br>
     * Ex : In response JSON code = 0 means success, AND code = 1 means failed
     *
     * @param jsonObject
     * @return
     */
    protected abstract boolean isBusinessResponseSuccess(JSONObject jsonObject);

    /**
     * Method to get Business Response error message<br>
     *
     * @param jsonObject
     * @return
     */
    protected abstract String getBusinessResponseErrorMessage(JSONObject jsonObject);

    /**
     * Method to get the API URL
     *
     * @return
     */
    public abstract String getUrl();

    /**
     * Method to get the local response JSON path<br>Json should be save in ASSETS folder
     *
     * @return
     */
    protected abstract String getLocalResponseJsonPath();

    /**
     * Method to get the Header required to call API<br>Default Header Add in API call
     * <OL>
     * <LI>User-Agent->Mozilla/5.0(macintosh;U;Intel Mac 05 X104; en-us; rv:1.9.2.2) Gecko/20100316 Firefox/19.0</LI>
     * <LI>Content-Type->application/json</LI>
     * </OL>
     *
     * @return NULL if not pass any header
     */
    protected abstract HashMap<String, String> getCustomHeader();

    /**
     * Method to get the Request Type
     *
     * @return
     */
    protected abstract NetworkConstants.RequestType getRequestType();

    /**
     * Method to get the JSONObject from HAshMap
     *
     * @param params
     * @return
     */
    protected static JSONObject getParamJSOBObject(HashMap<String, String> params) {
        JSONObject jsonObject = new JSONObject();
        if (params == null) {
            return jsonObject;
        }
        Set<String> keys = params.keySet();
        for (String key : keys) {
            try {
                jsonObject.put(key, params.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }
}
