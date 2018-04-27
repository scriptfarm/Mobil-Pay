package com.mkrworld.mobilpay.ui.adapter.viewholder

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.mkrworld.androidlib.ui.adapter.BaseViewHolder
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTOSendNotificationData

/**
 * Created by mkr on 14/3/18.
 */

class SendNotificationVH : BaseViewHolder<DTOSendNotificationData> {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".AgentNotificationVH"
    }

    private var mCheckBox : CheckBox? = null
    private var mTextViewLabel : TextView? = null
    private var mDTOSendNotificationData : DTOSendNotificationData? = null

    /**
     * Constructor
     *
     * @param itemView
     */
    constructor(itemView : View) : super(itemView) {
        Tracer.debug(TAG, "AgentHomeTabVH: ")
        getParent().setOnClickListener(this)
        mCheckBox = itemView.findViewById(R.id.item_send_notification_data_checkBox)
        mTextViewLabel = itemView.findViewById(R.id.item_send_notification_data_textView_type)
        mCheckBox !!.setOnClickListener(this)
    }

    override fun bindData(dtoSendNotificationData : DTOSendNotificationData) {
        Tracer.debug(TAG, "bindData: " + dtoSendNotificationData !!)
        mDTOSendNotificationData = dtoSendNotificationData
        if (dtoSendNotificationData == null) {
            return
        }
        mTextViewLabel?.text = dtoSendNotificationData.label
        mCheckBox !!.isChecked = dtoSendNotificationData.isChecked
    }

    override fun onClick(v : View) {
        if (v.id == mCheckBox !!.id) {
            if (mDTOSendNotificationData == null) {
                return
            }
            if (mDTOSendNotificationData !!.isChecked) {
                mDTOSendNotificationData !!.isChecked = false
                mCheckBox !!.isChecked = false
            } else {
                mDTOSendNotificationData !!.isChecked = true
                mCheckBox !!.isChecked = true
            }
        } else {
            super.onClick(v)
        }
    }
}
