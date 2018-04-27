package com.mkrworld.mobilpay.dto.comms.logout

import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.DTOBaseRequest

/**
 * Created by mkr on 27/3/18.
 */

class DTOLogoutRequest : DTOBaseRequest {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".DTOLogoutRequest"
    }

    /**
     * Constructor
     *
     * @param token
     * @param timeStamp
     * @param publicKey
     */
    constructor(token : String, timeStamp : String, publicKey : String, userType : String, merchantId : String, agentId : String) : super(token, timeStamp, publicKey, userType, merchantId, agentId) {
        Tracer.debug(TAG, "DTOLogoutRequest : ")
    }
}
