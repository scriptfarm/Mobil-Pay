package com.mkrworld.mobilpay.ui.adapter.viewholder

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.mkrworld.androidlib.ui.adapter.BaseViewHolder
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTOMultiSelectionItemData
import com.mkrworld.mobilpay.ui.dialog.DialogMultiSelection


/**
 * Created by mkr on 14/3/18.
 */

class MultiSelectionDataVH : BaseViewHolder<DTOMultiSelectionItemData>, DialogMultiSelection.OnDialogMultiSelectionListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".MultiSelectionDataVH"
    }

    private var mCheckBox : CheckBox? = null
    private var mTextViewLabel : TextView? = null
    private var mDTOMultiSelectionItemData : DTOMultiSelectionItemData? = null

    /**
     * Constructor
     *
     * @param itemView
     */
    constructor(itemView : View) : super(itemView) {
        Tracer.debug(TAG, "MultiSelectionDataVH: ")
        getParent().setOnClickListener(this)
        mCheckBox = itemView.findViewById(R.id.item_multi_selection_item_data_checkBox)
        mTextViewLabel = itemView.findViewById(R.id.item_multi_selection_item_textView_label)
        mCheckBox !!.setOnClickListener(this)
    }

    override fun bindData(dtoMultiSelectionItemData : DTOMultiSelectionItemData) {
        Tracer.debug(TAG, "bindData: " + dtoMultiSelectionItemData !!)
        mDTOMultiSelectionItemData = dtoMultiSelectionItemData
        if (dtoMultiSelectionItemData == null) {
            return
        }
        mTextViewLabel?.text = dtoMultiSelectionItemData.label
        mCheckBox !!.isChecked = dtoMultiSelectionItemData.isChecked
    }

    override fun onClick(v : View) {
        when (v.id) {
            R.id.item_multi_selection_item_data_checkBox -> {
                if (mDTOMultiSelectionItemData == null) {
                    return
                }
                if (mDTOMultiSelectionItemData !!.isChecked) {
                    mDTOMultiSelectionItemData !!.isChecked = false
                    mCheckBox !!.isChecked = false
                } else {
                    mDTOMultiSelectionItemData !!.isChecked = true
                    mCheckBox !!.isChecked = true
                }
            }
        }
        super.onClick(v)
    }

    override fun onDialogMultiSelectionSelecetItemList(multiSelectionItemDataList : ArrayList<DTOMultiSelectionItemData>) {
        Tracer.debug(TAG, "onDialogMultiSelectionSelecetItemList : ")
        mDTOMultiSelectionItemData?.selectedChildOptionList = multiSelectionItemDataList
    }

    override fun onDialogMultiSelectionCancel() {
        Tracer.debug(TAG, "onDialogMultiSelectionCancel : ")
        mDTOMultiSelectionItemData?.selectedChildOptionList = ArrayList()
    }
}
