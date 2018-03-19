package com.mkrworld.androidlib.network;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
    public Context getContext() {
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
        NetworkRequest.sendAsyncRequest(getRequestType(), getUrl(), mRequestJsonObject, getHeader(), new NetworkRequest.OnNetworkRequestListener() {
            @Override
            public void onNetworkRequestCompleted(final JSONObject json) {
                if (mNetworkCallBack != null) {
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
    public long getTimeOut() {
        return NetworkConstants.SOCKET_TIME_OUT;
    }

    /**
     * Method to get the retry count
     */
    public int getRetryCount() {
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
    public abstract MKR parseNetworkResponse(JSONObject jsonObject);

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
    public abstract String getLocalResponseJsonPath();

    /**
     * Method to get the Header required to call API<br>Default Header Add in API call
     * <OL>
     * <LI>User-Agent->Mozilla/5.0(macintosh;U;Intel Mac 05 X104; en-us; rv:1.9.2.2) Gecko/20100316 Firefox/19.0</LI>
     * <LI>Content-Type->application/json</LI>
     * </OL>
     *
     * @return NULL if not pass any header
     */
    public abstract HashMap<String, String> getCustomHeader();

    /**
     * Method to get the Request Type
     *
     * @return
     */
    public abstract NetworkConstants.RequestType getRequestType();
}
