package com.mkrworld.androidlib.network

/**
 * Created by A1ZFKXA3 on 1/30/2018.
 */

interface NetworkCallBack<MKR> {
    /**
     * Method to notifyTaskResponse the Successful Result
     *
     * @param mkr
     */
    fun onSuccess(mkr : MKR)

    /**
     * Method to notifyTaskResponse that there may be some sort of error occur at the time of calling
     *
     * @param errorMessage
     * @param errorCode
     */
    fun onError(errorMessage : String, errorCode : Int)
}
