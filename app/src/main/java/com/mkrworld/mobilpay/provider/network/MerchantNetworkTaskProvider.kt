package com.mkrworld.mobilpay.provider.network

import android.content.Context
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.comms.sendbill.DTOSendBillRequest
import com.mkrworld.mobilpay.dto.comms.sendbill.DTOSendBillResponse
import com.mkrworld.mobilpay.task.comms.SendBillTask

/**
 * Created by mkr on 27/3/18.
 */

class MerchantNetworkTaskProvider : AppNetworkTaskProvider() {

    companion object {
        val TAG : String = BuildConfig.BASE_TAG + ".MerchantNetworkTaskProvider"
    }
}
