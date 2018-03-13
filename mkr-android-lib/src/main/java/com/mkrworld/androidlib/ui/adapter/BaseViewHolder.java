package com.mkrworld.androidlib.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by himanshu on 08/09/15.
 */
public abstract class BaseViewHolder<V> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    protected View mParent;
    private VHClickable mClickCallback;
    private VHLongClickable mLongClickCallback;
    private AdapterItem mAdapterItem;

    /**
     * Constructor
     *
     * @param itemView
     */
    public BaseViewHolder(View itemView) {
        super(itemView);
        mParent = itemView;
    }

    public View getParent() {
        return mParent;
    }

    public Context getContext() {
        return mParent.getContext();
    }

    public void bindFeedItem(AdapterItem<V> adapterItem) {
        mAdapterItem = adapterItem;
        bindData(adapterItem.getBindingData());
    }

    /**
     * Method called, whenever view bind with Recycler View
     * @param v
     */
    protected abstract void bindData(V v);

    public AdapterItem getAdapterItem() {
        return mAdapterItem;
    }

    @Override
    public void onClick(View v) {
        if (mClickCallback != null) mClickCallback.onViewHolderClicked(this, v);
    }

    @Override
    public boolean onLongClick(View v) {
        if (mLongClickCallback != null) {
            mLongClickCallback.onViewHolderLongClicked(this, v);
            return true;
        }
        return false;
    }

    /**
     * Method to set the VHClickCallback
     *
     * @param callback
     */
    public void setVHClickCallback(VHClickable callback) {
        mClickCallback = callback;
    }

    /**
     * Method to set the VHLongClickCallback
     *
     * @param callback
     */
    public void setVHLongClickCallback(VHLongClickable callback) {
        mLongClickCallback = callback;
    }

    /**
     * Callback to notifyTaskResponse that user clicked the viewItem
     */
    public interface VHClickable {
        /**
         * Method called when user click NewsAdapterItemHandler view in adapter Item
         *
         * @param holder
         * @param view
         */
        void onViewHolderClicked(BaseViewHolder holder, View view);
    }

    /**
     * Callback to notifyTaskResponse that user long-clicked the viewItem
     */
    public interface VHLongClickable {
        /**
         * Method called when user long-click NewsAdapterItemHandler view in adapter Item
         *
         * @param holder
         * @param view
         */
        void onViewHolderLongClicked(BaseViewHolder holder, View view);
    }
}
