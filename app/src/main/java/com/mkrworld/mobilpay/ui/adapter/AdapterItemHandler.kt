package com.mkrworld.mobilpay.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout

import com.mkrworld.androidlib.ui.adapter.BaseAdapterItemHandler
import com.mkrworld.androidlib.ui.adapter.BaseViewHolder
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.ui.adapter.viewholder.*

/**
 * Created by mkr on 14/3/18.
 */

class AdapterItemHandler : BaseAdapterItemHandler() {
    /**
     * Type of view hold by adapter
     */
    enum class AdapterItemViewType {
        NONE, HOME_TAB, SUMMARY_CONSOLIDATE_DATA_LIST, SUMMARY_CONSOLIDATE_DATA, SUMMARY_USER_DATA, STATUS_CONSOLIDATE_DATA_LIST, STATUS_CONSOLIDATE_DATA, MULTI_SELECTION_ITEM
    }

    override fun createHolder(inflater : LayoutInflater, parent : ViewGroup, viewType : Int) : BaseViewHolder<*> {
        when (getItemViewType(viewType)) {
            AdapterItemHandler.AdapterItemViewType.HOME_TAB -> return HomeTabVH(inflater.inflate(R.layout.item_home_tab, parent, false))
            AdapterItemHandler.AdapterItemViewType.SUMMARY_CONSOLIDATE_DATA_LIST -> return CollectionSummaryDataItemListVH(inflater.inflate(R.layout.item_card_recycler_view, parent, false))
            AdapterItemHandler.AdapterItemViewType.SUMMARY_CONSOLIDATE_DATA -> return CollectionSummaryDataItemVH(inflater.inflate(R.layout.item_collection_summary_data, parent, false))
            AdapterItemHandler.AdapterItemViewType.STATUS_CONSOLIDATE_DATA_LIST -> return CollectionStatusDataItemListVH(inflater.inflate(R.layout.item_card_recycler_view, parent, false))
            AdapterItemHandler.AdapterItemViewType.STATUS_CONSOLIDATE_DATA -> return CollectionStatusDataItemVH(inflater.inflate(R.layout.item_collection_status_data, parent, false))
            AdapterItemHandler.AdapterItemViewType.SUMMARY_USER_DATA -> return SummaryUserDataVH(inflater.inflate(R.layout.item_collection_summary_user_data, parent, false))
            AdapterItemHandler.AdapterItemViewType.MULTI_SELECTION_ITEM -> return MultiSelectionDataVH(inflater.inflate(R.layout.item_multi_selection_item, parent, false))
            else -> return object : BaseViewHolder<Any>(FrameLayout(inflater.context)) {
                protected override fun bindData(o : Any) {

                }
            }
        }
    }

    /**
     * Method to get the ENUM as per viewType
     *
     * @return
     */
    private fun getItemViewType(viewType : Int) : AdapterItemViewType {
        return AdapterItemViewType.values()[viewType]
    }
}
