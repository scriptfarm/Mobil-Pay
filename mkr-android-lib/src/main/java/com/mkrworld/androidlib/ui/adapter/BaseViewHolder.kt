package com.mkrworld.androidlib.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import javax.xml.transform.Source

/**
 * Created by mkr on 3/4/18.
 */
abstract class BaseViewHolder<MKR> : RecyclerView.ViewHolder, View.OnClickListener, View.OnLongClickListener {
    protected var mParent : View? = null
    private var mClickCallback : VHClickable? = null
    private var mLongClickCallback : VHLongClickable? = null
    private var mBaseAdapterItem : BaseAdapterItem<*>? = null

    /**
     * Constructor
     *
     * @param itemView
     */
    constructor(itemView : View) : super(itemView) {
        mParent = itemView
    }

    fun getParent() : View {
        return mParent !!
    }

    fun getContext() : Context {
        return mParent !!.getContext()
    }

    fun bindFeedItem(baseAdapterItem : BaseAdapterItem<*>) {
        mBaseAdapterItem = baseAdapterItem
        bindData((baseAdapterItem as BaseAdapterItem<MKR>).getData())
    }

    /**
     * Method called, whenever view bind with Recycler View
     * @param mkr
     */
    protected abstract fun bindData(mkr : MKR)

    fun getAdapterItem() : BaseAdapterItem<*> {
        return mBaseAdapterItem !!
    }

    override fun onClick(v : View) {
        mClickCallback?.onViewHolderClicked(this, v)
    }

    override fun onLongClick(v : View) : Boolean {
        if (mLongClickCallback != null) {
            mLongClickCallback !!.onViewHolderLongClicked(this, v)
            return true
        }
        return false
    }

    /**
     * Method to set the VHClickCallback
     *
     * @param callback
     */
    fun setVHClickCallback(callback : VHClickable) {
        mClickCallback = callback
    }

    /**
     * Method to set the VHLongClickCallback
     *
     * @param callback
     */
    fun setVHLongClickCallback(callback : VHLongClickable) {
        mLongClickCallback = callback
    }


    /**
     * Callback to notifyTaskResponse that user clicked the viewItem
     */
    interface VHClickable {
        /**
         * Method called when user click NewsAdapterItemHandler view in adapter Item
         *
         * @param holder
         * @param view
         */
        fun onViewHolderClicked(holder : BaseViewHolder<*>, view : View)
    }

    /**
     * Callback to notifyTaskResponse that user long-clicked the viewItem
     */
    interface VHLongClickable {
        /**
         * Method called when user long-click NewsAdapterItemHandler view in adapter Item
         *
         * @param holder
         * @param view
         */
        fun onViewHolderLongClicked(holder : BaseViewHolder<*>, view : View)
    }
}