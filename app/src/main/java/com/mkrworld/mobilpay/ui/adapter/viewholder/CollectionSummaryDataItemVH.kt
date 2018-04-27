package com.mkrworld.mobilpay.ui.adapter.viewholder

import android.graphics.Typeface
import android.view.View
import android.widget.TextView

import com.mkrworld.androidlib.ui.adapter.BaseViewHolder
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTOCollectionSummaryConsolidateData

/**
 * Created by mkr on 14/3/18.
 */

class CollectionSummaryDataItemVH : BaseViewHolder<DTOCollectionSummaryConsolidateData> {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".AgentHomeTabVH"
    }

    private var mTextViewMode : TextView?=null
    private var mTextViewCount : TextView?=null
    private var mTextViewAmount : TextView?=null

    /**
     * Constructor
     *
     * @param itemView
     */
    constructor(itemView : View) : super(itemView) {
        Tracer.debug(TAG, "AgentHomeTabVH: ")
        getParent().setOnClickListener(this)
        mTextViewMode = itemView.findViewById(R.id.item_collection_summary_consolidate_data_textView_mode)
        mTextViewCount = itemView.findViewById(R.id.item_collection_summary_consolidate_data_textView_count)
        mTextViewAmount = itemView.findViewById(R.id.item_collection_summary_consolidate_data_textView_amount)
    }

    override fun bindData(dtoCollectionSummaryConsolidateData : DTOCollectionSummaryConsolidateData) {
        Tracer.debug(TAG, "bindData: " + dtoCollectionSummaryConsolidateData !!)
        if (dtoCollectionSummaryConsolidateData == null) {
            return
        }
        if (dtoCollectionSummaryConsolidateData.rowType == DTOCollectionSummaryConsolidateData.RowType.TITLE) {
            mTextViewMode!!.setTypeface(mTextViewMode!!.typeface, Typeface.BOLD_ITALIC)
            mTextViewCount!!.setTypeface(mTextViewCount!!.typeface, Typeface.BOLD_ITALIC)
            mTextViewAmount!!.setTypeface(mTextViewAmount!!.typeface, Typeface.BOLD_ITALIC)
        } else {
            mTextViewMode!!.setTypeface(mTextViewMode!!.typeface, Typeface.NORMAL)
            mTextViewCount!!.setTypeface(mTextViewCount!!.typeface, Typeface.NORMAL)
            mTextViewAmount!!.setTypeface(mTextViewAmount!!.typeface, Typeface.NORMAL)
        }

        mTextViewMode!!.text = dtoCollectionSummaryConsolidateData.mode
        mTextViewCount!!.text = dtoCollectionSummaryConsolidateData.count
        mTextViewAmount!!.text = dtoCollectionSummaryConsolidateData.amount
    }
}
