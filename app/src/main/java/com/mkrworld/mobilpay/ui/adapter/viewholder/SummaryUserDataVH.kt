package com.mkrworld.mobilpay.ui.adapter.viewholder

import android.view.View
import android.widget.TextView

import com.mkrworld.androidlib.ui.adapter.BaseViewHolder
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTOSummaryUserData

/**
 * Created by mkr on 16/3/18.
 */

class SummaryUserDataVH : BaseViewHolder<DTOSummaryUserData> {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".SummaryUserDataVH"
    }

    private var mTextViewNumber : TextView?=null
    private var mTextViewAmount : TextView?=null
    private var mTextViewDate : TextView?=null
    private var mTextViewTxnNumber : TextView?=null
    private var mTextViewMode : TextView?=null
    private var mTextViewTxnStatus : TextView?=null

    /**
     * Constructor
     *
     * @param itemView
     */
    constructor(itemView : View) : super(itemView) {
        Tracer.debug(TAG, "SummaryUserDataVH: ")
        mTextViewNumber = itemView.findViewById<View>(R.id.item_summary_user_data_textView_number) as TextView
        mTextViewAmount = itemView.findViewById<View>(R.id.item_summary_user_data_textView_amount) as TextView
        mTextViewDate = itemView.findViewById<View>(R.id.item_summary_user_data_textView_date) as TextView
        mTextViewTxnNumber = itemView.findViewById<View>(R.id.item_summary_user_data_textView_txn_number) as TextView
        mTextViewMode = itemView.findViewById<View>(R.id.item_summary_user_data_textView_mode) as TextView
        mTextViewTxnStatus = itemView.findViewById<View>(R.id.item_summary_user_data_textView_txn_status) as TextView
    }

    override fun bindData(dto : DTOSummaryUserData) {
        Tracer.debug(TAG, "bindData: " + dto !!)
        if (dto == null) {
            return
        }
        mTextViewNumber!!.text = dto.number
        mTextViewAmount!!.text = getContext().getString(R.string.symbol_rs) + dto.amount
        mTextViewDate!!.text = dto.date
        mTextViewTxnNumber!!.text = dto.txnNumber
        mTextViewMode!!.text = dto.mode
        mTextViewTxnStatus!!.text = dto.txnStatus
    }
}
