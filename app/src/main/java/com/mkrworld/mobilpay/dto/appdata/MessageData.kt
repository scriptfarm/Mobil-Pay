package com.mkrworld.mobilpay.dto.appdata

import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 30/4/18.
 */
class MessageData {
    companion object {
        val TAG : String = BuildConfig.BASE_TAG + ".MessageData"
    }

    var id : String
    var body : String

    constructor(id : String, body : String) {
        this.id = id
        this.body = body
    }
}