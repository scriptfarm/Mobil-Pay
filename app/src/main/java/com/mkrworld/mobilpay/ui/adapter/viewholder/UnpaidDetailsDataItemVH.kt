package com.mkrworld.mobilpay.ui.adapter.viewholder

import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import com.mkrworld.androidlib.ui.adapter.BaseViewHolder
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTOUnpaidDetailsData

class UnpaidDetailsDataItemVH : BaseViewHolder<DTOUnpaidDetailsData> {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".AgentHomeTabVH"
    }

    private var mTextViewUserId: TextView? = null
    private var mTextViewUserName: TextView? = null
    private var mTextViewAmount: TextView? = null
    private var mTextViewDate: TextView? = null

    /**
     * Constructor
     *
     * @param itemView
     */
    constructor(itemView: View) : super(itemView) {
        Tracer.debug(TAG, "UnpaidDetailsDataItemVH: ")
        getParent().setOnClickListener(this)
        mTextViewUserId = itemView.findViewById(R.id.item_unpaid_details_data_textView_user_id)
        mTextViewUserName = itemView.findViewById(R.id.item_unpaid_details_data_textView_name)
        mTextViewAmount = itemView.findViewById(R.id.item_unpaid_details_data_textView_amount)
        mTextViewDate = itemView.findViewById(R.id.item_unpaid_details_data_textView_date)
    }

    override fun bindData(dto: DTOUnpaidDetailsData) {
        Tracer.debug(TAG, "bindData: " + dto!!)
        if (dto == null) {
            return
        }
        if (dto.rowType == DTOUnpaidDetailsData.RowType.TITLE) {
            mTextViewUserId!!.setTypeface(mTextViewUserId!!.typeface, Typeface.BOLD_ITALIC)
            mTextViewUserName!!.setTypeface(mTextViewUserName!!.typeface, Typeface.BOLD_ITALIC)
            mTextViewAmount!!.setTypeface(mTextViewAmount!!.typeface, Typeface.BOLD_ITALIC)
            mTextViewDate!!.setTypeface(mTextViewAmount!!.typeface, Typeface.BOLD_ITALIC)
        } else {
            mTextViewUserId!!.setTypeface(mTextViewUserId!!.typeface, Typeface.NORMAL)
            mTextViewUserName!!.setTypeface(mTextViewUserName!!.typeface, Typeface.NORMAL)
            mTextViewAmount!!.setTypeface(mTextViewAmount!!.typeface, Typeface.NORMAL)
            mTextViewDate!!.setTypeface(mTextViewAmount!!.typeface, Typeface.NORMAL)
        }

        mTextViewUserId!!.text = dto.userId
        mTextViewUserName!!.text = dto.userName
        mTextViewAmount!!.text = dto.amount
        mTextViewDate!!.text = dto.date
    }
}
