package com.mkrworld.mobilpay.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import com.mkrworld.mobilpay.dto.appdata.DTONotificationTab
import com.mkrworld.mobilpay.provider.fragment.FragmentProvider
import com.mkrworld.mobilpay.provider.fragment.FragmentTag

/**
 * Created by mkr on 10/5/18.
 */

class NotificationTabAdapter : FragmentStatePagerAdapter {

    private var mNotificationTabList : ArrayList<DTONotificationTab>

    /**
     * Constructor
     * @param fm
     * @param notificationTabList
     */
    constructor(fm : FragmentManager, notificationTabList : ArrayList<DTONotificationTab>) : super(fm) {
        mNotificationTabList = ArrayList<DTONotificationTab>()
        if (notificationTabList != null) {
            mNotificationTabList.addAll(notificationTabList)
        }
    }

    override fun getItem(position : Int) : Fragment? {
        when (mNotificationTabList[position].tabType) {
            DTONotificationTab.TabType.SEND_TO_ALL -> {
                return FragmentProvider.getFragment(FragmentTag.NOTIFICATION_TAB_ALL)
            }
            DTONotificationTab.TabType.SEND_TO_AGENTS -> {
                return FragmentProvider.getFragment(FragmentTag.NOTIFICATION_TAB_AGENT)
            }
            DTONotificationTab.TabType.SEND_TO_CUSTOMER -> {
                return FragmentProvider.getFragment(FragmentTag.NOTIFICATION_TAB_CUSTOMER)
            }
        }
        return null
    }

    override fun getCount() : Int {
        return mNotificationTabList?.size
    }

    override fun getPageTitle(position : Int) : CharSequence {
        return mNotificationTabList[position].label
    }
}