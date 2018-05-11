package com.mkrworld.mobilpay.ui.adapter.viewholder

import android.os.Handler
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import com.mkrworld.androidlib.ui.adapter.BaseViewHolder
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTOMultiSelectionItemData
import com.mkrworld.mobilpay.utils.Utils


/**
 * Created by mkr on 14/3/18.
 */

class MultiSelectionDataVH : BaseViewHolder<DTOMultiSelectionItemData> {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".MultiSelectionDataVH"
    }

    private var mRadio : RadioButton? = null
    private var mTextViewLabel : TextView? = null
    private var mDTO : DTOMultiSelectionItemData? = null
    private var mTextInputLayout : TextInputLayout? = null
    private var mEditText : EditText? = null

    /**
     * Constructor
     *
     * @param itemView
     */
    constructor(itemView : View) : super(itemView) {
        Tracer.debug(TAG, "MultiSelectionDataVH: ")
        getParent().setOnClickListener(this)
        mRadio = itemView.findViewById(R.id.item_multi_selection_item_data_radio)
        mTextViewLabel = itemView.findViewById(R.id.item_multi_selection_item_textView_label)
        mRadio !!.setOnClickListener(this)
        mTextInputLayout = itemView.findViewById(R.id.item_multi_selection_item_textInputLayout_message)
        mEditText = itemView.findViewById(R.id.item_multi_selection_item_editText_message)
    }

    override fun bindData(dto : DTOMultiSelectionItemData) {
        Tracer.debug(TAG, "bindData: " + dto !!)
        mDTO = dto
        if (dto == null) {
            return
        }
        mTextViewLabel?.text = dto.label
        mRadio !!.isChecked = dto.isChecked
        if (mDTO !!.isShowMessage) {
            mEditText !!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0 : Editable?) {

                }

                override fun beforeTextChanged(p0 : CharSequence?, p1 : Int, p2 : Int, p3 : Int) {

                }

                override fun onTextChanged(p0 : CharSequence?, p1 : Int, p2 : Int, p3 : Int) {
                    mDTO !!.message = p0.toString().trim()
                }
            })
        }
    }

    override fun onClick(v : View) {
        when (v.id) {
            R.id.item_multi_selection_item_data_radio -> {
                if (mDTO == null) {
                    return
                }
                if (mDTO !!.isChecked) {
                    mDTO !!.isChecked = false
                    mRadio !!.isChecked = false
                    mTextInputLayout !!.visibility = View.GONE
                    if (mDTO !!.isShowMessage) {
                        Utils.hideKeyboard(getContext(), mEditText)
                    }
                } else {
                    mDTO !!.isChecked = true
                    mRadio !!.isChecked = true
                    if (mDTO !!.isShowMessage) {
                        mTextInputLayout !!.visibility = View.VISIBLE
                        Handler().postDelayed(object : Runnable {
                            override fun run() {
                                mEditText !!.requestFocus()
                                Utils.showKeyboard(getContext(), mEditText)
                            }
                        }, 50)
                    } else {
                        mTextInputLayout !!.visibility = View.GONE
                    }
                }
            }
        }
        super.onClick(v)
    }
}
