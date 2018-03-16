package com.mkrworld.mobilpay.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mkrworld.androidlib.ui.adapter.BaseAdapterItemHandler;
import com.mkrworld.androidlib.ui.adapter.BaseViewHolder;
import com.mkrworld.mobilpay.R;
import com.mkrworld.mobilpay.ui.adapter.viewholder.MerchantHomeTabVH;
import com.mkrworld.mobilpay.ui.adapter.viewholder.SummaryConsolidateDataListVH;
import com.mkrworld.mobilpay.ui.adapter.viewholder.SummaryConsolidateDataVH;
import com.mkrworld.mobilpay.ui.adapter.viewholder.SummaryUserDataVH;

/**
 * Created by mkr on 14/3/18.
 */

public class AdapterItemHandler extends BaseAdapterItemHandler {
    /**
     * Type of view hold by adapter
     */
    public enum AdapterItemViewType {
        NONE, MERCHANT_HOME_TAB, SUMMARY_CONSOLIDATE_DATA_LIST, SUMMARY_CONSOLIDATE_DATA, SUMMARY_USER_DATA
    }

    @Override
    public BaseViewHolder createHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        switch (getItemViewType(viewType)) {
            case MERCHANT_HOME_TAB:
                return new MerchantHomeTabVH(inflater.inflate(R.layout.item_merchant_home_tab, parent, false));
            case SUMMARY_CONSOLIDATE_DATA_LIST:
                return new SummaryConsolidateDataListVH(inflater.inflate(R.layout.item_card_recycler_view, parent, false));
            case SUMMARY_CONSOLIDATE_DATA:
                return new SummaryConsolidateDataVH(inflater.inflate(R.layout.item_summary_consolidate_data, parent, false));
            case SUMMARY_USER_DATA:
                return new SummaryUserDataVH(inflater.inflate(R.layout.item_summary_user_data, parent, false));
            default:
                return new BaseViewHolder(new FrameLayout(inflater.getContext())) {
                    @Override
                    protected void bindData(Object o) {
                    }
                };
        }
    }

    /**
     * Method to get the ENUM as per viewType
     *
     * @return
     */
    private AdapterItemViewType getItemViewType(int viewType) {
        return AdapterItemViewType.values()[viewType];
    }
}
