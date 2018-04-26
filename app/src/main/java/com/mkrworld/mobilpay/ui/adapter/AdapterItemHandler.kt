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
        NONE, AGENT_HOME_TAB, MERCHANT_HOME_TAB, SUMMARY_CONSOLIDATE_DATA_LIST, SUMMARY_CONSOLIDATE_DATA, SUMMARY_USER_DATA, AGENT_NOTIFICATION_DATA_ITEM, MERCHANT_NOTIFICATION_DATA_ITEM
    }

    override fun createHolder(inflater : LayoutInflater, parent : ViewGroup, viewType : Int) : BaseViewHolder<*> {
        when (getItemViewType(viewType)) {
            AdapterItemHandler.AdapterItemViewType.MERCHANT_HOME_TAB -> return MerchantHomeTabVH(inflater.inflate(R.layout.item_home_tab, parent, false))
            AdapterItemHandler.AdapterItemViewType.AGENT_HOME_TAB -> return AgentHomeTabVH(inflater.inflate(R.layout.item_home_tab, parent, false))
            AdapterItemHandler.AdapterItemViewType.SUMMARY_CONSOLIDATE_DATA_LIST -> return SummaryConsolidateDataListVH(inflater.inflate(R.layout.item_card_recycler_view, parent, false))
            AdapterItemHandler.AdapterItemViewType.SUMMARY_CONSOLIDATE_DATA -> return SummaryConsolidateDataVH(inflater.inflate(R.layout.item_summary_consolidate_data, parent, false))
            AdapterItemHandler.AdapterItemViewType.SUMMARY_USER_DATA -> return SummaryUserDataVH(inflater.inflate(R.layout.item_summary_user_data, parent, false))
            AdapterItemHandler.AdapterItemViewType.AGENT_NOTIFICATION_DATA_ITEM -> return AgentNotificationVH(inflater.inflate(R.layout.item_notification_data, parent, false))
            AdapterItemHandler.AdapterItemViewType.MERCHANT_NOTIFICATION_DATA_ITEM -> return MerchantNotificationVH(inflater.inflate(R.layout.item_notification_data, parent, false))
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
