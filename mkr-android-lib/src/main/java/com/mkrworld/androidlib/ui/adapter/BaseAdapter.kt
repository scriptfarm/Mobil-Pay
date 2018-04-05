package com.mkrworld.androidlib.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*

/**
 * Created by mkr on 3/4/18.
 */
class BaseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>, BaseViewHolder.VHClickable, BaseViewHolder.VHLongClickable {
    private var mBaseAdapterItemList : ArrayList<BaseAdapterItem<*>>? = null
    private var mLongClickCallback : BaseViewHolder.VHLongClickable? = null
    private var mClickCallback : BaseViewHolder.VHClickable? = null
    private var mIsUpdatingList : Boolean = false
    private var mBaseAdapterItemHandler : BaseAdapterItemHandler? = null

    /**
     * Constructor
     *
     * @param baseAdapterItemHandler
     */
    constructor(baseAdapterItemHandler : BaseAdapterItemHandler) {
        mBaseAdapterItemList = ArrayList()
        mBaseAdapterItemHandler = baseAdapterItemHandler
    }

    /**
     * Set Load more litener
     *
     * @param recyclerView           Pass to listen the Last Item Visible Count
     * @param onLoadMoreItemListener Callback to listen the load even
     */
    fun setOnLoadMoreListener(recyclerView : RecyclerView, onLoadMoreItemListener : OnLoadMoreItemListener) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView : RecyclerView?, newState : Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView : RecyclerView?, dx : Int, dy : Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (! mIsUpdatingList) {
                    if (recyclerView !!.childCount > 1) {
                        val childAdapterPosition = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(recyclerView.childCount - 1))
                        if (childAdapterPosition >= mBaseAdapterItemList !!.size - 1) {
                            mIsUpdatingList = true
                            onLoadMoreItemListener.onLoadMoreItemListener()
                        }
                    }
                }
            }
        })
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : RecyclerView.ViewHolder {
        val viewHolder = mBaseAdapterItemHandler !!.createHolder(LayoutInflater.from(parent.context), parent, viewType)
        viewHolder.setVHClickCallback(this)
        viewHolder.setVHLongClickCallback(this)
        return viewHolder
    }

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder, position : Int) {
        if (holder !is BaseViewHolder<*>) return
        var baseAdapterItem : BaseAdapterItem<*> = getItem(position)
        holder.bindFeedItem(baseAdapterItem)
    }

    override fun getItemViewType(position : Int) : Int {
        return getItem(position).getViewType()
    }

    override fun getItemCount() : Int {
        return mBaseAdapterItemList !!.size
    }

    override fun onViewHolderClicked(holder : BaseViewHolder<*>, view : View) {
        mClickCallback?.onViewHolderClicked(holder, view)
    }

    override fun onViewHolderLongClicked(holder : BaseViewHolder<*>, view : View) {
        mLongClickCallback?.onViewHolderLongClicked(holder, view)
    }

    /**
     * Method to get the BaseAdapterItem
     *
     * @param position
     * @return
     */
    fun getItem(position : Int) : BaseAdapterItem<*> {
        return mBaseAdapterItemList !!.get(position)
    }

    /**
     * Method to update Adapter Item
     *
     * @param list
     */
    fun updateAdapterItemList(list : ArrayList<BaseAdapterItem<*>>?) {
        mBaseAdapterItemList !!.clear()
        if (list != null) {
            mBaseAdapterItemList !!.addAll(list)
        }
        mIsUpdatingList = false
        notifyDataSetChanged()
    }

    /**
     * Method to append Adapter Item
     *
     * @param list
     */
    fun appendAdapterItemList(list : ArrayList<BaseAdapterItem<*>>?) {
        if (list != null) {
            mBaseAdapterItemList !!.addAll(list)
        }
        mIsUpdatingList = false
        notifyDataSetChanged()
    }

    /**
     * Method to set the VHLongClickCallback
     *
     * @param longClickCallback
     */
    fun setVHLongClickCallback(longClickCallback : BaseViewHolder.VHLongClickable) {
        mLongClickCallback = longClickCallback
    }

    /**
     * Method to set the VHClickCallback
     *
     * @param clickCallback
     */
    fun setVHClickCallback(clickCallback : BaseViewHolder.VHClickable) {
        mClickCallback = clickCallback
    }

    /**
     * Callback to notifyTaskResponse weather there is NewsAdapterItemHandler need to load more item or not
     */
    interface OnLoadMoreItemListener {
        /**
         * Method to notifyTaskResponse that there is NewsAdapterItemHandler need to load more items
         */
        fun onLoadMoreItemListener()
    }

}