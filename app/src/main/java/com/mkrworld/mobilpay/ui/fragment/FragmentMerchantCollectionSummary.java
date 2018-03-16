package com.mkrworld.mobilpay.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.mkrworld.mobilpay.dto.DTOSummaryConsolidateDataList;
import com.mkrworld.mobilpay.dto.DTOSummaryUserData;
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
        RecyclerView recyclerViewUserData = (RecyclerView) getView().findViewById(R.id.fragment_merchant_collection_summary_recycler_view);
        BaseAdapter baseAdapterUserData = new BaseAdapter(new AdapterItemHandler());
        recyclerViewUserData.setAdapter(baseAdapterUserData);
        recyclerViewUserData.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        // RECYCLER VIEW DATA
        ArrayList<BaseAdapterItem> baseAdapterItemArrayList = new ArrayList<>();
        baseAdapterItemArrayList.add(new BaseAdapterItem(AdapterItemHandler.AdapterItemViewType.SUMMARY_CONSOLIDATE_DATA_LIST.ordinal(), getSummaryConsolidateDataList()));
        baseAdapterItemArrayList.addAll(getSummaryUserDataList());
        baseAdapterUserData.updateAdapterItemList(baseAdapterItemArrayList);
    }

    /**
     * Method to get the List of the Consolidate Data
     *
     * @return
     */
    private DTOSummaryConsolidateDataList getSummaryConsolidateDataList() {
        Tracer.debug(TAG, "getSummaryConsolidateDataList: ");
        DTOSummaryConsolidateDataList dtoSummaryConsolidateDataList = new DTOSummaryConsolidateDataList();
        dtoSummaryConsolidateDataList.addConsolidateData(new DTOSummaryConsolidateData(DTOSummaryConsolidateData.RowType.TITLE, getString(R.string.mode_caps), getString(R.string.txns_caps), getString(R.string.amount_caps)));
        dtoSummaryConsolidateDataList.addConsolidateData(new DTOSummaryConsolidateData(DTOSummaryConsolidateData.RowType.TEXT, getString(R.string.aeps_caps), "10", "1,00,000"));
        dtoSummaryConsolidateDataList.addConsolidateData(new DTOSummaryConsolidateData(DTOSummaryConsolidateData.RowType.TEXT, getString(R.string.upi_caps), "20", "20,000"));
        dtoSummaryConsolidateDataList.addConsolidateData(new DTOSummaryConsolidateData(DTOSummaryConsolidateData.RowType.TEXT, getString(R.string.qr_code_caps), "30", "30,000"));
        dtoSummaryConsolidateDataList.addConsolidateData(new DTOSummaryConsolidateData(DTOSummaryConsolidateData.RowType.TITLE, getString(R.string.total_caps), "60", "1,50,000"));
        return dtoSummaryConsolidateDataList;
    }

    /**
     * Method to get the List of the User Data
     *
     * @return
     */
    private ArrayList<BaseAdapterItem> getSummaryUserDataList() {
        Tracer.debug(TAG, "getSummaryUserDataList: ");
        ArrayList<BaseAdapterItem> baseAdapterItemList = new ArrayList<>();
        int adapterViewType = AdapterItemHandler.AdapterItemViewType.SUMMARY_USER_DATA.ordinal();
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("9582XXXX85", "7640", "2018/03/11", "72368726", "AEPS", "SUCCESS")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("8282XXXX98", "17640", "2018/03/11", "72368726", "AEPS", "SUCCESS")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("9152XXXX56", "27640", "2018/03/11", "72368726", "UPI", "SUCCESS")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("9182XXXX11", "37640", "2018/03/11", "72368726", "QR-CODE", "FAILED")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("9282XXXX44", "76340", "2018/03/11", "72368726", "UPI", "SUCCESS")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("9872XXXX92", "76240", "2018/03/11", "72368726", "QR-CODE", "SUCCESS")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("9992XXXX89", "76340", "2018/03/11", "72368726", "AEPS", "FAILED")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("9512XXXX85", "7640", "2018/03/11", "72368726", "AEPS", "SUCCESS")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("8212XXXX98", "17640", "2018/03/11", "72368726", "AEPS", "SUCCESS")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("9112XXXX56", "27640", "2018/03/11", "72368726", "UPI", "SUCCESS")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("9112XXXX11", "37640", "2018/03/11", "72368726", "QR-CODE", "FAILED")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("9212XXXX44", "76340", "2018/03/11", "72368726", "UPI", "SUCCESS")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("9812XXXX92", "76240", "2018/03/11", "72368726", "QR-CODE", "SUCCESS")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("9912XXXX89", "76340", "2018/03/11", "72368726", "AEPS", "FAILED")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("9522XXXX85", "7640", "2018/03/11", "72368726", "AEPS", "SUCCESS")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("8222XXXX98", "17640", "2018/03/11", "72368726", "AEPS", "SUCCESS")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("9122XXXX56", "27640", "2018/03/11", "72368726", "UPI", "SUCCESS")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("9122XXXX11", "37640", "2018/03/11", "72368726", "QR-CODE", "FAILED")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("9222XXXX44", "76340", "2018/03/11", "72368726", "UPI", "SUCCESS")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("9822XXXX92", "76240", "2018/03/11", "72368726", "QR-CODE", "SUCCESS")));
        baseAdapterItemList.add(new BaseAdapterItem(adapterViewType, new DTOSummaryUserData("9922XXXX89", "76340", "2018/03/11", "72368726", "AEPS", "FAILED")));
        return baseAdapterItemList;
    }
}
