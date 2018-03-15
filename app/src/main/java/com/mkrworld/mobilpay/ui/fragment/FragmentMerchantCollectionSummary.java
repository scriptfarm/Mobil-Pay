package com.mkrworld.mobilpay.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mkrworld.androidlib.BuildConfig;
import com.mkrworld.androidlib.callback.OnBaseActivityListener;
import com.mkrworld.androidlib.callback.OnBaseFragmentListener;
import com.mkrworld.androidlib.ui.adapter.BaseAdapter;
import com.mkrworld.androidlib.ui.adapter.BaseAdapterItem;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.R;
import com.mkrworld.mobilpay.dto.DTOMerchantHomeTab;
import com.mkrworld.mobilpay.dto.DTOSummaryConsolidateData;
import com.mkrworld.mobilpay.ui.adapter.AdapterItemHandler;
import com.mkrworld.mobilpay.ui.adapter.GridSpacingItemDecoration;

import java.util.ArrayList;

/**
 * Created by mkr on 15/3/18.
 */

public class FragmentMerchantCollectionSummary extends Fragment implements OnBaseFragmentListener, View.OnClickListener {
    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentMerchantCollectionSummary";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_merchant_collection_summary, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Tracer.debug(TAG, "onViewCreated: ");
        setTitle();
        init();
    }

    @Override
    public boolean onBackPressed() {
        Tracer.debug(TAG, "onBackPressed: ");
        return false;
    }

    @Override
    public void onPopFromBackStack() {
        Tracer.debug(TAG, "onPopFromBackStack: ");
        setTitle();
    }

    @Override
    public void onRefresh() {
        Tracer.debug(TAG, "onRefresh: ");
    }

    @Override
    public void onClick(View view) {
        Tracer.debug(TAG, "onClick: ");
        switch (view.getId()) {

        }
    }

    /**
     * Method to set the Activity Title
     */
    private void setTitle() {
        Tracer.debug(TAG, "setTitle: ");
        if (getActivity() instanceof OnBaseActivityListener) {
            ((OnBaseActivityListener) getActivity()).onBaseActivitySetScreenTitle(getString(R.string.screen_title_collection_summary));
        }
    }

    /**
     * Method to initialize the Fragment
     */
    private void init() {
        Tracer.debug(TAG, "init: ");
        if (getView() == null) {
            return;
        }
        // INIT the RecyclerView
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.fragment_merchant_collection_summary_recycler_view_consolidate_data);
        BaseAdapter baseAdapter = new BaseAdapter(new AdapterItemHandler());
        recyclerView.setAdapter(baseAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        baseAdapter.updateAdapterItemList(getSummaryConsolidateDataList());
        int colorDivider = ContextCompat.getColor(getActivity(), R.color.divider_color);
        GridSpacingItemDecoration gridSpacingItemDecoration = new GridSpacingItemDecoration(1, getResources().getDimensionPixelOffset(R.dimen.divider_size), colorDivider, false);
        recyclerView.addItemDecoration(gridSpacingItemDecoration);
    }

    /**
     * Method to get the List of the Home Tab
     *
     * @return
     */
    private ArrayList<BaseAdapterItem> getSummaryConsolidateDataList() {
        Tracer.debug(TAG, "getSummaryConsolidateDataList: ");
        ArrayList<BaseAdapterItem> baseAdapterItemList = new ArrayList<>();
        int adapterViewType = AdapterItemHandler.AdapterItemViewType.SUMMARY_CONSOLIDATE_DATA.ordinal();
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryConsolidateData(DTOSummaryConsolidateData.RowType.TITLE, getString(R.string.mode_caps), getString(R.string.txns_caps), getString(R.string.amount_caps))));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryConsolidateData(DTOSummaryConsolidateData.RowType.TEXT, getString(R.string.aeps_caps), "10", "1,00,000")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryConsolidateData(DTOSummaryConsolidateData.RowType.TEXT, getString(R.string.upi_caps), "20", "20,000")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryConsolidateData(DTOSummaryConsolidateData.RowType.TEXT, getString(R.string.qr_code_caps), "30", "30,000")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryConsolidateData(DTOSummaryConsolidateData.RowType.TITLE, getString(R.string.total_caps), "60", "1,50,000")));
        return baseAdapterItemList;
    }
}
