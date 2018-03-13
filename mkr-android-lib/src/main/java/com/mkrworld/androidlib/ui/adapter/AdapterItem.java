package com.mkrworld.androidlib.ui.adapter;

public class AdapterItem<MKR> {
    private int mViewType;
    private MKR mMkr;

    /**
     * Constructor
     *
     * @param viewType
     * @param d         Object Data pass to the BaseViewHolder
     */
    public AdapterItem(int viewType, MKR d) {
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
            if (obj instanceof AdapterItem) {
                AdapterItem adapterItem = (AdapterItem) obj;
                if (adapterItem.mViewType == mViewType && adapterItem.mMkr.equals(mMkr)) {
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
