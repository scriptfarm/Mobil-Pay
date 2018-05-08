package com.mkrworld.mobilpay.ui.adapter.viewholder

import android.os.Build
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.Spanned
import android.text.TextWatcher
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.mkrworld.androidlib.ui.adapter.BaseViewHolder
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTOMultiSelectionItemData
import com.mkrworld.mobilpay.ui.dialog.DialogMultiSelection
import com.mkrworld.mobilpay.utils.Utils


/**
 * Created by mkr on 14/3/18.
 */

class MultiSelectionDataWithMessageVH : BaseViewHolder<DTOMultiSelectionItemData>, DialogMultiSelection.OnDialogMultiSelectionListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".MultiSelectionDataWithMessageVH"
    }

    private var mCheckBox : CheckBox? = null
    private var mTextViewLabel : TextView? = null
    private var mTextViewEdit : TextView? = null
    private var mDTOMultiSelectionItemData : DTOMultiSelectionItemData? = null
    private var mTextInputLayoutMessage : TextInputLayout? = null
    private var mEditTextMessage : EditText? = null

    /**
     * Constructor
     *
     * @param itemView
     */
    constructor(itemView : View) : super(itemView) {
        Tracer.debug(TAG, "MultiSelectionDataWithMessageVH: ")
        getParent().setOnClickListener(this)
        mCheckBox = itemView.findViewById(R.id.item_multi_selection_item_with_message_data_checkBox)
        mTextViewLabel = itemView.findViewById(R.id.item_multi_selection_item_with_message_textView_label)
        mTextViewEdit = itemView.findViewById(R.id.item_multi_selection_item_with_message_textView_edit)
        mTextInputLayoutMessage = itemView.findViewById(R.id.item_multi_selection_item_with_message_textInputLayout_message)
        mEditTextMessage = itemView.findViewById(R.id.item_multi_selection_item_with_message_editText_message)
        mCheckBox !!.setOnClickListener(this)
        mTextViewEdit !!.setOnClickListener(this)
    }

    override fun bindData(dtoMultiSelectionItemData : DTOMultiSelectionItemData) {
        Tracer.debug(TAG, "bindData: " + dtoMultiSelectionItemData !!)
        mDTOMultiSelectionItemData = dtoMultiSelectionItemData
        if (dtoMultiSelectionItemData == null) {
            return
        }
        val fromHtml : Spanned = Utils.fromHtml("<HTML><BODY><U>" + dtoMultiSelectionItemData.selectionButtonText !! + "</U></BODY></HTML")
        mTextViewEdit?.text = fromHtml
        mTextViewLabel?.text = dtoMultiSelectionItemData.label
        mCheckBox !!.isChecked = dtoMultiSelectionItemData.isChecked
        setEditMessageVisibility()
        mEditTextMessage !!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0 : Editable?) {
                // DO NOTHING
            }

            override fun beforeTextChanged(p0 : CharSequence?, p1 : Int, p2 : Int, p3 : Int) {
                // DO NOTHING
            }

            override fun onTextChanged(p0 : CharSequence?, p1 : Int, p2 : Int, p3 : Int) {
                mDTOMultiSelectionItemData?.message = p0.toString().trim()
            }
        })
    }

    override fun onClick(v : View) {
        when (v.id) {
            R.id.item_multi_selection_item_with_message_data_checkBox -> {
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
                setEditMessageVisibility()
            }
            R.id.item_multi_selection_item_with_message_textView_edit -> {
                if (mDTOMultiSelectionItemData !!.childOptionList.size > 0) {
                    mDTOMultiSelectionItemData !!.selectedChildOptionList = mDTOMultiSelectionItemData !!.childOptionList
                    var dialogMultiSelection : DialogMultiSelection
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        dialogMultiSelection = DialogMultiSelection(getContext(), android.R.style.Theme_Material_Light_NoActionBar, mDTOMultiSelectionItemData !!.childOptionList, this, true)
                    } else {
                        dialogMultiSelection = DialogMultiSelection(getContext(), mDTOMultiSelectionItemData !!.childOptionList, this, true)
                    }
                    dialogMultiSelection.setCancelable(false)
                    dialogMultiSelection.show()
                }
            }
        }
        super.onClick(v)
    }

    /**
     * Methid to set the Visibility of edit/message layout
     */
    private fun setEditMessageVisibility() {
        mEditTextMessage !!.setText("")
        if (mDTOMultiSelectionItemData !!.isChecked) {
            mTextViewEdit !!.visibility = if (mDTOMultiSelectionItemData !!.childOptionList.size > 0) {
                View.VISIBLE
            } else {
                View.GONE
            }
            if (mDTOMultiSelectionItemData !!.isShowMessage) {
                mTextInputLayoutMessage !!.visibility = View.VISIBLE
                mEditTextMessage!!.requestFocus()
                Utils.showKeyboard(getContext(), mEditTextMessage)
            } else {
                mTextInputLayoutMessage !!.visibility = View.GONE
            }
        } else {
            mTextViewEdit !!.visibility = View.GONE
            mTextInputLayoutMessage !!.visibility = View.GONE
        }
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
