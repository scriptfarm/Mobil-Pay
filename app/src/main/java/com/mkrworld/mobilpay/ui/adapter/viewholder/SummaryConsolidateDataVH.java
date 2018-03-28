package com.mkrworld.mobilpay.ui.adapter.viewholder;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.mkrworld.androidlib.ui.adapter.BaseViewHolder;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;
import com.mkrworld.mobilpay.dto.appdata.DTOSummaryConsolidateData;

/**
 * Created by mkr on 14/3/18.
 */

public class SummaryConsolidateDataVH extends BaseViewHolder<DTOSummaryConsolidateData> {
    private static final String TAG = BuildConfig.BASE_TAG + ".MerchantHomeTabVH";
    private TextView mTextViewMode;
    private TextView mTextViewCount;
    private TextView mTextViewAmount;

    /**
     * Constructor
     *
     * @param itemView
     */
    public SummaryConsolidateDataVH(View itemView) {
        super(itemView);
        Tracer.debug(TAG, "MerchantHomeTabVH: ");
        getParent().setOnClickListener(this);
        mTextViewMode = itemView.findViewById(R.id.item_collection_summary_consolidate_data_textView_mode);
        mTextViewCount = itemView.findViewById(R.id.item_collection_summary_consolidate_data_textView_count);
        mTextViewAmount = itemView.findViewById(R.id.item_collection_summary_consolidate_data_textView_amount);
    }

    @Override
    protected void bindData(DTOSummaryConsolidateData dtoSummaryConsolidateData) {
        Tracer.debug(TAG, "bindData: " + dtoSummaryConsolidateData);
        if (dtoSummaryConsolidateData == null) {
            return;
        }
        if (dtoSummaryConsolidateData.getRowType().equals(DTOSummaryConsolidateData.RowType.TITLE)) {
            mTextViewMode.setTypeface(mTextViewMode.getTypeface(), Typeface.BOLD_ITALIC);
            mTextViewCount.setTypeface(mTextViewCount.getTypeface(), Typeface.BOLD_ITALIC);
            mTextViewAmount.setTypeface(mTextViewAmount.getTypeface(), Typeface.BOLD_ITALIC);
        } else {
            mTextViewMode.setTypeface(mTextViewMode.getTypeface(), Typeface.NORMAL);
            mTextViewCount.setTypeface(mTextViewCount.getTypeface(), Typeface.NORMAL);
            mTextViewAmount.setTypeface(mTextViewAmount.getTypeface(), Typeface.NORMAL);
        }

        mTextViewMode.setText(dtoSummaryConsolidateData.getMode());
        mTextViewCount.setText(dtoSummaryConsolidateData.getCount());
        mTextViewAmount.setText(dtoSummaryConsolidateData.getAmount());
    }
}
