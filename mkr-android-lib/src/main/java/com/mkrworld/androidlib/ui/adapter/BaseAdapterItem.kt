package com.mkrworld.androidlib.ui.adapter

class BaseAdapterItem<MKR> {
    private var mViewType : Int? = null
    private var mMkr : MKR? = null

    /**
     * Constructor
     *
     * @param viewType
     * @param mkr         Object Data pass to the BaseViewHolder
     */
    constructor(viewType : Int, mkr : MKR) {
        mViewType = viewType
        mMkr = mkr
    }

    fun getViewType() : Int {
        return mViewType!!
    }

    fun getData() : MKR{
        return mMkr!!
    }

    override fun equals(obj : Any?) : Boolean {
        try {
            if (obj is BaseAdapterItem<*>) {
                val adapterItem = obj as BaseAdapterItem<*>?
                if (adapterItem !!.mViewType == mViewType && adapterItem.mMkr == mMkr) {
                    return true
                }
            }
        } catch (e : Exception) {
            // Any exception occur at the time of equals operation
        }

        return false
    }

    override fun hashCode() : Int {
        return mMkr!!.hashCode()
    }
}
