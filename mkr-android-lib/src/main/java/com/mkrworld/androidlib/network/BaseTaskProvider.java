package com.mkrworld.androidlib.network;

/**
 * Created by A1ZFKXA3 on 1/30/2018.
 */

public class BaseTaskProvider {
    private boolean mIsAttach;

    /**
     * Method to attach network provider
     */
    public final void attachProvider() {
        mIsAttach = true;
    }

    /**
     * Method to detach network provider
     */
    public final void detachProvider() {
        mIsAttach = false;
    }

    /**
     * Method to Notify Caller
     *
     * @param networkCallBack
     */
    public final void notifyTaskResponse(NetworkCallBack networkCallBack, Object o) {
        if (mIsAttach) {
            networkCallBack.onSuccess(o);
        }
    }

    /**
     * Method to Notify Caller
     *
     * @param networkCallBack
     */
    public final void notifyTaskResponse(NetworkCallBack networkCallBack, String errorMessage, int errorCode) {
        if (mIsAttach) {
            networkCallBack.onError(errorMessage, errorCode);
        }
    }




}
