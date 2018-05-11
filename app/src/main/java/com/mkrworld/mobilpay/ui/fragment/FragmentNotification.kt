package com.mkrworld.mobilpay.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.*
import com.mkrworld.androidlib.BuildConfig
import com.mkrworld.androidlib.callback.OnBaseActivityListener
import com.mkrworld.androidlib.callback.OnBaseFragmentListener
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTONotificationTab
import com.mkrworld.mobilpay.ui.adapter.NotificationTabAdapter
import com.mkrworld.mobilpay.utils.Utils

/**
 * Created by mkr on 15/3/18.
 */

class FragmentNotification : Fragment(), OnBaseFragmentListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".FragmentNotification"
    }

    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        return inflater !!.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view : View?, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Tracer.debug(TAG, "onViewCreated: ")
        setHasOptionsMenu(true)
        setTitle()
        init()
    }

    override fun onCreateOptionsMenu(menu : Menu?, inflater : MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.menu_nothing, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPopFromBackStack() {
        Tracer.debug(TAG, "onPopFromBackStack: ")
        setTitle()
    }

    override fun onBackPressed() : Boolean {
        Tracer.debug(TAG, "onBackPressed: ")
        return false
    }

    override fun onRefresh() {
        Tracer.debug(TAG, "onRefresh: ")
    }

    /**
     * Method to set the Activity Title
     */
    private fun setTitle() {
        Tracer.debug(TAG, "setTitle: ")
        if (activity is OnBaseActivityListener) {
            (activity as OnBaseActivityListener).onBaseActivitySetScreenTitle(getString(R.string.screen_title_send_notification))
        }
    }

    /**
     * Method to initialize the Fragment
     */
    private fun init() {
        Tracer.debug(TAG, "init: ")
        val viewpager : ViewPager = view!!.findViewById(R.id.fragment_notification_view_pager)
        var tabList : ArrayList<DTONotificationTab> = ArrayList<DTONotificationTab>()
        tabList.add(DTONotificationTab(DTONotificationTab.TabType.SEND_TO_ALL, getString(R.string.all_caps)))
        if (Utils.isMerchant(activity)) {
            tabList.add(DTONotificationTab(DTONotificationTab.TabType.SEND_TO_AGENTS, getString(R.string.agent_caps)))
        }
        tabList.add(DTONotificationTab(DTONotificationTab.TabType.SEND_TO_CUSTOMER, getString(R.string.customer_caps)))
        viewpager.adapter = NotificationTabAdapter(activity.supportFragmentManager, tabList)
    }
}
