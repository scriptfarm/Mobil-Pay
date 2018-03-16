package com.mkrworld.mobilpay.ui.adapter.viewholder;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mkrworld.androidlib.ui.adapter.BaseAdapter;
import com.mkrworld.androidlib.ui.adapter.BaseAdapterItem;
import com.mkrworld.androidlib.ui.adapter.BaseViewHolder;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;
import com.mkrworld.mobilpay.dto.DTOSummaryConsolidateData;
import com.mkrworld.mobilpay.dto.DTOSummaryConsolidateDataList;
import com.mkrworld.mobilpay.ui.adapter.AdapterItemHandler;
import com.mkrworld.mobilpay.ui.adapter.GridSpacingItemDecoration;

import java.util.ArrayList;

/**
 * Created by mkr on 16/3/18.
 */

public class SummaryConsolidateDataListVH extends BaseViewHolder<DTOSummaryConsolidateDataList> {
    private static final String TAG = BuildConfig.BASE_TAG + ".SummaryConsolidateDataListVH";
    private RecyclerView mRecyclerView;
    private BaseAdapter mBaseAdapter;

    /**
     * Constructor
     *
     * @param itemView
     */
    public SummaryConsolidateDataListVH(View itemView) {
        super(itemView);
        Tracer.debug(TAG, "SummaryConsolidateDataListVH: ");
        int colorDivider = ContextCompat.getColor(getContext(), R.color.divider_color);
        int dividerHeight = getContext().getResources().getDimensionPixelOffset(R.dimen.divider_size);
        mRecyclerView = (RecyclerView) itemView.findViewById(R.id.item_card_recycler_view);
        mBaseAdapter = new BaseAdapter(new AdapterItemHandler());
        mRecyclerView.setAdapter(mBaseAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dividerHeight, colorDivider, false));
    }

    @Override
    protected void bindData(DTOSummaryConsolidateDataList dtoSummaryConsolidateDataList) {
        Tracer.debug(TAG, "bindData: " + dtoSummaryConsolidateDataList);
        if (dtoSummaryConsolidateDataList == null) {
            return;
        }
        mBaseAdapter.updateAdapterItemList(getSummaryConsolidateDataList(dtoSummaryConsolidateDataList));
    }

    /**
     * Method to get the List of the Consolidate Data
     *
     * @return
     */
    private ArrayList<BaseAdapterItem> getSummaryConsolidateDataList(DTOSummaryConsolidateDataList dtoSummaryConsolidateDataList) {
        Tracer.debug(TAG, "getSummaryConsolidateDataList: ");
        ArrayList<BaseAdapterItem> baseAdapterItemList = new ArrayList<>();
        int adapterViewType = AdapterItemHandler.AdapterItemViewType.SUMMARY_CONSOLIDATE_DATA.ordinal();
        for (DTOSummaryConsolidateData dtoSummaryConsolidateData : dtoSummaryConsolidateDataList.getDtoSummaryConsolidateDataList()) {
            baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, dtoSummaryConsolidateData));
        }
        return baseAdapterItemList;
    }
}
