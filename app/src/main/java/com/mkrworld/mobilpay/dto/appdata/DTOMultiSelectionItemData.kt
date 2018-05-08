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

    var id : String? = null
        get() {
            return field?.trim() ?: ""
        }

    var selectionButtonText : String? = null
        get() {
            return field?.trim() ?: ""
        }

    var isChecked : Boolean = false

    var isShowMessage : Boolean = false

    var label : String? = null
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

    var childOptionList : ArrayList<DTOMultiSelectionItemData> = ArrayList<DTOMultiSelectionItemData>()

    var selectedChildOptionList : ArrayList<DTOMultiSelectionItemData> = ArrayList<DTOMultiSelectionItemData>()

    /**
     * Constructor
     */
    constructor(id : String, name : String, isChecked : Boolean) {
        Tracer.debug(TAG, "Constructor : ")
        this.label = name
        this.id = id
        this.isChecked = isChecked
    }

    /**
     * Constructor
     */
    constructor(id : String, name : String, isChecked : Boolean, childOptionList : ArrayList<DTOMultiSelectionItemData>, selectionButtonText : String) : this(id, name, isChecked) {
        Tracer.debug(TAG, "Constructor : ")
        if (childOptionList != null) {
            this.childOptionList.addAll(childOptionList)
        }
        this.selectionButtonText = selectionButtonText
    }
}