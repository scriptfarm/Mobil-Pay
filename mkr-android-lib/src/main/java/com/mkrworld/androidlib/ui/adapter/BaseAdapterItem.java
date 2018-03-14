package com.mkrworld.androidlib.ui.adapter;

public class BaseAdapterItem<MKR> {
    private int mViewType;
    private MKR mMkr;

    /**
     * Constructor
     *
     * @param viewType
     * @param d         Object Data pass to the BaseViewHolder
     */
    public BaseAdapterItem(int viewType, MKR d) {
        mViewType = viewType;
        mMkr = d;
    }

    public MKR getBindingData() {
        return mMkr;
    }

    public int getmViewType() {
        return mViewType;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            if (obj instanceof BaseAdapterItem) {
                BaseAdapterItem baseAdapterItem = (BaseAdapterItem) obj;
                if (baseAdapterItem.mViewType == mViewType && baseAdapterItem.mMkr.equals(mMkr)) {
                    return true;
                }
            }
        } catch (Exception e) {
            // Any exception occur at the time of equals operation
        }
        return false;
    }

    @Override
    public int hashCode() {
        return mMkr.hashCode();
    }
}
