package com.mkrworld.biometric.callback

import com.mkrworld.biometric.data.PIDBean


/**
 * Callback used for knowing the status of the Bio-Scanning
 */
interface CallBackInterface {
    /**
     * Callback notify the success of Bio Scanning
     * @param paramPIDBean
     * @param paramInt
     * @param paramString
     */
    fun pidData(paramPIDBean : PIDBean, paramInt : Int, paramString : String)
}