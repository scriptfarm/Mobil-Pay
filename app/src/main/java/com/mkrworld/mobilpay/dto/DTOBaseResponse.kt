package com.mkrworld.mobilpay.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by mkr on 27/3/18.
 */

open class DTOBaseResponse {

    @SerializedName("message")
    var message : String? = null

}
