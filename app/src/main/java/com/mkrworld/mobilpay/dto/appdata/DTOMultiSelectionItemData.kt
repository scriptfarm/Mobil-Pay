package com.mkrworld.mobilpay.dto.appdata

import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 17/4/18.
 */
class DTOMultiSelectionItemData {

    companion object {
        val TAG : String = BuildConfig.BASE_TAG + ".DTOMultiSelectionItemData"
    }

    var id : String = ""
        get() {
            return field?.trim() ?: ""
        }

    var isChecked : Boolean = false

    var isShowMessage : Boolean = false

    var label : String = ""
        get() {
            return field?.trim() ?: ""
        }

    var message : String = ""
        get() {
            return field?.trim() ?: ""
        }
        set(value) {
            field = value.trim()
        }

    /**
     * Constructor
     * @param id
     * @param name
     * @param isChecked
     * @param isShowMessage
     */
    constructor(id : String, name : String, isChecked : Boolean, isShowMessage : Boolean) {
        Tracer.debug(TAG, "Constructor : ")
        this.id = id
        this.label = name
        this.isChecked = isChecked
        this.isShowMessage = isShowMessage
    }
}