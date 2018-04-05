package com.mkrworld.androidlib.network

/**
 * Created by A1ZFKXA3 on 1/30/2018.
 */

open class BaseTaskProvider {
    private var mIsAttach : Boolean = false

    /**
     * Method to attach network provider
     */
    fun attachProvider() {
        mIsAttach = true
    }

    /**
     * Method to detach network provider
     */
    fun detachProvider() {
        mIsAttach = false
    }

    /**
     * Method to Notify Caller
     *
     * @param networkCallBack
     */
    fun notifyTaskResponse(networkCallBack : NetworkCallBack<Any>, o : Any) {
        if (mIsAttach) {
            networkCallBack?.onSuccess(o)
        }
    }

    /**
     * Method to Notify Caller
     *
     * @param networkCallBack
     */
    fun notifyTaskResponse(networkCallBack : NetworkCallBack<Any>, errorMessage : String, errorCode : Int) {
        if (mIsAttach) {
            networkCallBack?.onError(errorMessage, errorCode)
        }
    }
}
