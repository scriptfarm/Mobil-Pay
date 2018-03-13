package com.mkrworld.androidlib.network;

/**
 * Created by A1ZFKXA3 on 1/30/2018.
 */

public interface NetworkCallBack<MKR> {
    /**
     * Method to notifyTaskResponse the Successful Result
     *
     * @param mkr
     */
    public void onSuccess(MKR mkr);

    /**
     * Method to notifyTaskResponse that there may be some sort of error occur at the time of calling
     *
     * @param errorMessage
     * @param errorCode
     */
    public void onError(String errorMessage, int errorCode);
}
