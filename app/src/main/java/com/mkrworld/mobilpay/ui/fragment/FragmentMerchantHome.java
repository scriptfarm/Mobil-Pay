package com.mkrworld.mobilpay.ui.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mkrworld.androidlib.callback.OnBaseFragmentListener;
import com.mkrworld.androidlib.ui.adapter.BaseAdapter;
import com.mkrworld.androidlib.ui.adapter.BaseAdapterItem;
import com.mkrworld.androidlib.ui.adapter.BaseViewHolder;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;
import com.mkrworld.mobilpay.dto.DTOMerchantHomeTab;
import com.mkrworld.mobilpay.ui.adapter.AdapterItemHandler;
import com.mkrworld.mobilpay.ui.adapter.GridSpacingItemDecoration;

import java.util.ArrayList;

/**
 * Created by mkr on 13/3/18.
 */

public class FragmentMerchantHome extends Fragment implements OnBaseFragmentListener, BaseViewHolder.VHClickable {
    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentMerchantHome";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.fragment_merchant_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
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
    }

    @Override
    public void onRefresh() {
        Tracer.debug(TAG, "onRefresh: ");
    }

    @Override
    public void onViewHolderClicked(BaseViewHolder holder, View view) {
        Tracer.debug(TAG, "onViewHolderClicked: ");
        switch (view.getId()) {
            case R.id.item_merchant_home_parent:
                if (view.getTag() instanceof DTOMerchantHomeTab) {
                    DTOMerchantHomeTab dtoMerchantHomeTab = (DTOMerchantHomeTab) view.getTag();
                    // TO DO
                }
                break;
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
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.fragment_merchant_home_recycler_view);
        BaseAdapter baseAdapter = new BaseAdapter(new AdapterItemHandler());
        recyclerView.setAdapter(baseAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        baseAdapter.updateAdapterItemList(getHomeTabList());
        int colorDivider = ContextCompat.getColor(getActivity(), R.color.divider_color);
        GridSpacingItemDecoration gridSpacingItemDecoration = new GridSpacingItemDecoration(2, getResources().getDimensionPixelOffset(R.dimen.divider_size), colorDivider, false);
        recyclerView.addItemDecoration(gridSpacingItemDecoration);
        baseAdapter.setVHClickCallback(this);
    }

    /**
     * Method to get the List of the Home Tab
     *
     * @return
     */
    private ArrayList<BaseAdapterItem> getHomeTabList() {
        Tracer.debug(TAG, "getHomeTabList: ");
        ArrayList<BaseAdapterItem> baseAdapterItemList = new ArrayList<>();
        int adapterViewType = AdapterItemHandler.AdapterItemViewType.MERCHANT_HOME_TAB.ordinal();
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOMerchantHomeTab(DTOMerchantHomeTab.TabType.STATIC_QR_CODE, R.drawable.ic_thumb, getString(R.string.static_qr_code))));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOMerchantHomeTab(DTOMerchantHomeTab.TabType.DYNAMIC_QR_CODE, R.drawable.ic_thumb, getString(R.string.dynamic_qr_code))));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOMerchantHomeTab(DTOMerchantHomeTab.TabType.UPI_COLLECT, R.drawable.ic_thumb, getString(R.string.upi_collect))));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOMerchantHomeTab(DTOMerchantHomeTab.TabType.AEPS_COLLECT, R.drawable.ic_thumb, getString(R.string.aeps_collect))));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOMerchantHomeTab(DTOMerchantHomeTab.TabType.SEND_BILL, R.drawable.ic_thumb, getString(R.string.send_bill))));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOMerchantHomeTab(DTOMerchantHomeTab.TabType.COLLECTION_SUMMARY, R.drawable.ic_thumb, getString(R.string.collection_summary))));
        return baseAdapterItemList;
    }
}
