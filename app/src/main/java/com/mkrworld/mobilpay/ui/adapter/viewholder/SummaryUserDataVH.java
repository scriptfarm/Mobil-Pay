package com.mkrworld.mobilpay.ui.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.mkrworld.androidlib.ui.adapter.BaseViewHolder;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;
import com.mkrworld.mobilpay.dto.appdata.DTOSummaryUserData;

/**
 * Created by mkr on 16/3/18.
 */

public class SummaryUserDataVH extends BaseViewHolder<DTOSummaryUserData> {
    private static final String TAG = BuildConfig.BASE_TAG + ".SummaryUserDataVH";
    private TextView mTextViewNumber;
    private TextView mTextViewAmount;
    private TextView mTextViewDate;
    private TextView mTextViewTxnNumber;
    private TextView mTextViewMode;
    private TextView mTextViewTxnStatus;


    /**
     * Constructor
     *
     * @param itemView
     */
    public SummaryUserDataVH(View itemView) {
        super(itemView);
        Tracer.debug(TAG, "SummaryUserDataVH: ");
        mTextViewNumber = (TextView) itemView.findViewById(R.id.item_summary_user_data_textView_number);
        mTextViewAmount = (TextView) itemView.findViewById(R.id.item_summary_user_data_textView_amount);
        mTextViewDate = (TextView) itemView.findViewById(R.id.item_summary_user_data_textView_date);
        mTextViewTxnNumber = (TextView) itemView.findViewById(R.id.item_summary_user_data_textView_txn_number);
        mTextViewMode = (TextView) itemView.findViewById(R.id.item_summary_user_data_textView_mode);
        mTextViewTxnStatus = (TextView) itemView.findViewById(R.id.item_summary_user_data_textView_txn_status);
    }

    @Override
    protected void bindData(DTOSummaryUserData dtoSummaryUserData) {
        Tracer.debug(TAG, "bindData: " + dtoSummaryUserData);
        if (dtoSummaryUserData == null) {
            return;
        }
        mTextViewNumber.setText(dtoSummaryUserData.getNumber());
        mTextViewAmount.setText(getContext().getString(R.string.symbol_rs) + dtoSummaryUserData.getAmount());
        mTextViewDate.setText(dtoSummaryUserData.getDate());
        mTextViewTxnNumber.setText(dtoSummaryUserData.getTxnNumber());
        mTextViewMode.setText(dtoSummaryUserData.getMode());
        mTextViewTxnStatus.setText(dtoSummaryUserData.getTxnStatus());
    }
}
