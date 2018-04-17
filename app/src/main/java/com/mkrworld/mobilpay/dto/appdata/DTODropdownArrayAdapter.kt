package com.mkrworld.mobilpay.dto.appdata

import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 17/4/18.
 */
class DTODropdownArrayAdapter {

    companion object {
        val TAG : String = BuildConfig.BASE_TAG + ".DTODropdownArrayAdapter"
    }

    var id : String? = null
        get() {
            return field?.trim() ?: ""
        }

    var name : String? = null
        get() {
            return field?.trim() ?: ""
        }

    /**
     * Constructor
     */
    constructor(id : String, name : String) {
        Tracer.debug(TAG, "Constructor : ")
        this.name = name
        this.id = id
    }
}