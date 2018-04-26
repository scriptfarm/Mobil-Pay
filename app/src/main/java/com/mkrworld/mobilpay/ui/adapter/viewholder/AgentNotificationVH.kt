package com.mkrworld.mobilpay.ui.adapter.viewholder

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.mkrworld.androidlib.ui.adapter.BaseViewHolder
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTOAgentNotificationData

/**
 * Created by mkr on 14/3/18.
 */

class AgentNotificationVH : BaseViewHolder<DTOAgentNotificationData> {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".AgentNotificationVH"
    }

    private var mCheckBox : CheckBox? = null
    private var mTextViewLabel : TextView? = null
    private var mDTOAgentNotificationData : DTOAgentNotificationData? = null

    /**
     * Constructor
     *
     * @param itemView
     */
    constructor(itemView : View) : super(itemView) {
        Tracer.debug(TAG, "AgentHomeTabVH: ")
        getParent().setOnClickListener(this)
        mCheckBox = itemView.findViewById(R.id.item_notification_data_checkBox)
        mTextViewLabel = itemView.findViewById(R.id.item_notification_data_textView_type)
        mCheckBox !!.setOnClickListener(this)
    }

    override fun bindData(dtoAgentNotificationData : DTOAgentNotificationData) {
        Tracer.debug(TAG, "bindData: " + dtoAgentNotificationData !!)
        mDTOAgentNotificationData = dtoAgentNotificationData
        if (dtoAgentNotificationData == null) {
            return
        }
        mTextViewLabel?.text = dtoAgentNotificationData.label
        mCheckBox !!.isChecked = dtoAgentNotificationData.isChecked
    }

    override fun onClick(v : View) {
        if (v.id == mCheckBox !!.id) {
            if (mDTOAgentNotificationData == null) {
                return
            }
            if (mDTOAgentNotificationData !!.isChecked) {
                mDTOAgentNotificationData !!.isChecked = false
                mCheckBox !!.isChecked = false
            } else {
                mDTOAgentNotificationData !!.isChecked = true
                mCheckBox !!.isChecked = true
            }
        } else {
            super.onClick(v)
        }
    }
}
