package com.mkrworld.androidlib.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by himanshu on 05/09/15.
 */
public class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements BaseViewHolder.VHClickable, BaseViewHolder.VHLongClickable {
    private ArrayList<AdapterItem> mAdapterItemList;
    private BaseViewHolder.VHLongClickable longClickCallback;
    private BaseViewHolder.VHClickable clickCallback;
    private boolean mIsUpdatingList;
    private AdapterItemHandler mAdapterItemHandler;

    /**
     * Constructor
     *
     * @param adapterItemHandler
     */
    public BaseAdapter(AdapterItemHandler adapterItemHandler) {
        mAdapterItemList = new ArrayList<>();
        mAdapterItemHandler = adapterItemHandler;
    }

    /**
     * Constructor
     *
     * @param recyclerView           Pass to listen the Last Item Visible Count
     * @param onLoadMoreItemListener Callback to listen the load even
     */
    public void setOnLoadMoreListener(final RecyclerView recyclerView, final OnLoadMoreItemListener onLoadMoreItemListener) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!mIsUpdatingList) {
                    if (recyclerView.getChildCount() > 1) {
                        int childAdapterPosition = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(recyclerView.getChildCount() - 1));
                        if (!(childAdapterPosition < (mAdapterItemList.size() - 1))) {
                            mIsUpdatingList = true;
                            onLoadMoreItemListener.onLoadMoreItemListener();
                        }
                    }
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = mAdapterItemHandler.createHolder(LayoutInflater.from(parent.getContext()), parent, viewType);
        viewHolder.setVHClickCallback(this);
        viewHolder.setVHLongClickCallback(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!(holder instanceof BaseViewHolder)) return;
        BaseViewHolder baseViewHolder = (BaseViewHolder) holder;
        AdapterItem adapterItem = getItem(position);
        baseViewHolder.bindFeedItem(adapterItem);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getmViewType();
    }

    @Override
    public int getItemCount() {
        return mAdapterItemList.size();
    }

    @Override
    public void onViewHolderClicked(BaseViewHolder holder, View view) {
        if (clickCallback != null) clickCallback.onViewHolderClicked(holder, view);
    }

    @Override
    public void onViewHolderLongClicked(BaseViewHolder holder, View view) {
        if (longClickCallback != null) {
            longClickCallback.onViewHolderLongClicked(holder, view);
        }
    }

    /**
     * Method to get the AdapterItem
     *
     * @param position
     * @return
     */
    public AdapterItem getItem(int position) {
        return mAdapterItemList.get(position);
    }

    /**
     * Method to update Adapter Item
     *
     * @param list
     */
    public void updateAdapterItemList(ArrayList<AdapterItem> list) {
        mAdapterItemList.clear();
        if (list != null) {
            mAdapterItemList.addAll(list);
        }
        mIsUpdatingList = false;
        notifyDataSetChanged();
    }

    /**
     * Method to append Adapter Item
     *
     * @param list
     */
    public void appendAdapterItemList(ArrayList<AdapterItem> list) {
        if (list != null) {
            mAdapterItemList.addAll(list);
        }
        mIsUpdatingList = false;
        notifyDataSetChanged();
    }

    /**
     * Method to set the VHLongClickCallback
     *
     * @param longClickCallback
     */
    public void setVHLongClickCallback(BaseViewHolder.VHLongClickable longClickCallback) {
        this.longClickCallback = longClickCallback;
    }

    /**
     * Method to set the VHClickCallback
     *
     * @param clickCallback
     */
    public void setVHClickCallback(BaseViewHolder.VHClickable clickCallback) {
        this.clickCallback = clickCallback;
    }

    /**
     * Callback to notifyTaskResponse weather there is NewsAdapterItemHandler need to load more item or not
     */
    public interface OnLoadMoreItemListener {
        /**
         * Method to notifyTaskResponse that there is NewsAdapterItemHandler need to load more items
         */
        public void onLoadMoreItemListener();
    }
}
