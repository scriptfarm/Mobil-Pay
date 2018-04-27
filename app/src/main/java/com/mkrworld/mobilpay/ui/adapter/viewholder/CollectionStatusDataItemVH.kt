package com.mkrworld.mobilpay.ui.adapter.viewholder

import android.graphics.Typeface
import android.view.View
import android.widget.TextView

import com.mkrworld.androidlib.ui.adapter.BaseViewHolder
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTOCollectionStatusConsolidateData

/**
 * Created by mkr on 14/3/18.
 */

class CollectionStatusDataItemVH : BaseViewHolder<DTOCollectionStatusConsolidateData> {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".AgentHomeTabVH"
    }

    private var mTextViewMode : TextView? = null
    private var mTextViewCount : TextView? = null

    /**
     * Constructor
     *
     * @param itemView
     */
    constructor(itemView : View) : super(itemView) {
        Tracer.debug(TAG, "AgentHomeTabVH: ")
        getParent().setOnClickListener(this)
        mTextViewMode = itemView.findViewById(R.id.item_collection_status_data_textView_mode)
        mTextViewCount = itemView.findViewById(R.id.item_collection_status_data_textView_count)
    }

    override fun bindData(dtoCollectionStatusConsolidateData : DTOCollectionStatusConsolidateData) {
        Tracer.debug(TAG, "bindData: " + dtoCollectionStatusConsolidateData !!)
        if (dtoCollectionStatusConsolidateData == null) {
            return
        }
        if (dtoCollectionStatusConsolidateData.rowType == DTOCollectionStatusConsolidateData.RowType.TITLE) {
            mTextViewMode !!.setTypeface(mTextViewMode !!.typeface, Typeface.BOLD_ITALIC)
            mTextViewCount !!.setTypeface(mTextViewCount !!.typeface, Typeface.BOLD_ITALIC)
        } else {
            mTextViewMode !!.setTypeface(mTextViewMode !!.typeface, Typeface.NORMAL)
            mTextViewCount !!.setTypeface(mTextViewCount !!.typeface, Typeface.NORMAL)
        }

        mTextViewMode !!.text = dtoCollectionStatusConsolidateData.mode
        mTextViewCount !!.text = dtoCollectionStatusConsolidateData.count
    }
}
