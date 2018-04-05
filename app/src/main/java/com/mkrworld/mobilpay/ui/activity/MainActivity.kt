package com.mkrworld.mobilpay.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

import com.mkrworld.androidlib.callback.OnBaseActivityListener
import com.mkrworld.androidlib.callback.OnBaseFragmentListener
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.merchantlogout.DTOMerchantLogoutRequest
import com.mkrworld.mobilpay.dto.merchantlogout.DTOMerchantLogoutResponse
import com.mkrworld.mobilpay.provider.fragment.FragmentProvider
import com.mkrworld.mobilpay.provider.fragment.FragmentTag
import com.mkrworld.mobilpay.provider.network.MerchantNetworkTaskProvider
import com.mkrworld.mobilpay.utils.PreferenceData
import com.mkrworld.mobilpay.utils.Utils

import java.util.Date

class MainActivity : AppCompatActivity(), OnBaseActivityListener, View.OnClickListener {
    private var mMerchantNetworkTaskProvider : MerchantNetworkTaskProvider? = null
    private val mMerchantLogoutResponseNetworkCallBack = object : NetworkCallBack<DTOMerchantLogoutResponse> {
        override fun onSuccess(dtoMerchantLogoutResponse : DTOMerchantLogoutResponse?) {
            Tracer.debug(TAG, "onSuccess : ")
            Utils.dismissLoadingDialog()
            try {
                if (dtoMerchantLogoutResponse == null) {
                    Tracer.showSnack(findViewById(R.id.activity_main_parent), R.string.no_data_fetch_from_server)
                    return
                }
                Tracer.showSnack(findViewById(R.id.activity_main_parent), dtoMerchantLogoutResponse.getMessage())
                PreferenceData.clearStore(applicationContext)
                // MOVE TO LOGIN FRAGMENT
                val fragment = FragmentProvider.getFragment(FragmentTag.MERCHANT_LOGIN)
                onBaseActivityReplaceFragment(fragment, null, FragmentTag.MERCHANT_LOGIN)
            } catch (e : Exception) {
                Tracer.error(TAG, "onSuccess : " + e.message)
            }

        }

        override fun onError(errorMessage : String, errorCode : Int) {
            Tracer.debug(TAG, "onError : ")
            Utils.dismissLoadingDialog()
            try {
                Tracer.showSnack(findViewById(R.id.activity_main_parent), errorMessage)
            } catch (e : Exception) {
                Tracer.error(TAG, "onError : " + e.message)
            }

        }
    }

    /**
     * Method to check weather the drawer View is visible or not
     *
     * @return TRUE if drawer is visible, else FALSE
     */
    private val isDrawerVisible : Boolean
        get() {
            Tracer.debug(TAG, "isDrawerVisible: ")
            val drawerLayout = findViewById<View>(R.id.activity_main_drawer_layout) as DrawerLayout
            return drawerLayout != null && drawerLayout.isDrawerVisible(findViewById<View>(R.id.activity_main_hide_layout))
        }

    override fun onCreate(savedInstanceState : Bundle?) {
        Tracer.debug(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.activity_main_toolbar) as Toolbar
        setSupportActionBar(toolbar)
        init()
        onBaseActivityAddFragment(FragmentProvider.getFragment(FragmentTag.MERCHANT_LOGIN), null, false, FragmentTag.MERCHANT_LOGIN)
    }

    override fun onDestroy() {
        if (mMerchantNetworkTaskProvider != null) {
            mMerchantNetworkTaskProvider !!.detachProvider()
        }
        super.onDestroy()
    }

    override fun onBackPressed() {
        Tracer.debug(TAG, "onBackPressed: ")
        if (isDrawerVisible) {
            hideDrawer()
            return
        }
        var fragment : Fragment? = supportFragmentManager.findFragmentById(R.id.activity_main_fragment_container)
        if (fragment != null && fragment is OnBaseFragmentListener && (fragment as OnBaseFragmentListener).onBackPressed()) {
            return
        }
        super.onBackPressed()
        fragment = supportFragmentManager.findFragmentById(R.id.activity_main_fragment_container)
        if (fragment != null && fragment is OnBaseFragmentListener) {
            (fragment as OnBaseFragmentListener).onPopFromBackStack()
        }
    }

    override fun onClick(view : View) {
        Tracer.debug(TAG, "onClick: ")
        when (view.id) {
            R.id.activity_main_sliding_layout_option_password -> {
                val fragment = FragmentProvider.getFragment(FragmentTag.CHANGE_PASSWORD)
                onBaseActivityAddFragment(fragment, null, true, FragmentTag.CHANGE_PASSWORD)
            }
            R.id.activity_main_sliding_layout_option_contact_mobil_pay -> {
            }
            R.id.activity_main_sliding_layout_option_faq -> {
            }
            R.id.activity_main_sliding_layout_option_home -> {
            }
            R.id.activity_main_sliding_layout_option_language -> {
            }
            R.id.activity_main_sliding_layout_option_logout -> logoutMerchant()
            R.id.activity_main_sliding_layout_option_term_and_condition -> {
            }
        }
        if (isDrawerVisible) {
            hideDrawer()
        }
    }

    override fun onBaseActivitySetScreenTitle(title : String) {
        Tracer.debug(TAG, "onBaseActivitySetScreenTitle: ")
        supportActionBar !!.title = title
    }

    override fun onBaseActivityShowInterstitialAd() {
        Tracer.debug(TAG, "onBaseActivityShowInterstitialAd: ")
    }

    override fun onBaseActivityShowBannerAd() {
        Tracer.debug(TAG, "onBaseActivityShowBannerAd: ")
    }

    override fun onBaseActivityReplaceFragment(fragment : Fragment?, bundle : Bundle?, tag : String) {
        Tracer.debug(TAG, "onBaseActivityReplaceFragment: ")
        onBaseActivityReplaceFragment(R.id.activity_main_fragment_container, fragment, bundle, tag)
    }

    override fun onBaseActivityReplaceFragment(containerId : Int, fragment : Fragment?, bundle : Bundle?, tag : String) {
        Tracer.debug(TAG, "onBaseActivityReplaceFragment: ")
        Utils.hideKeyboard(this)
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(containerId, fragment, tag)
        if (bundle != null) {
            fragment !!.arguments = bundle
        }
        fragmentTransaction.commit()
        setNavigationDrawerSwipeState(tag)
    }

    override fun onBaseActivityAddFragment(fragment : Fragment?, bundle : Bundle?, isAddToBackStack : Boolean, tag : String) {
        Tracer.debug(TAG, "onBaseActivityAddFragment: ")
        onBaseActivityAddFragment(R.id.activity_main_fragment_container, fragment, bundle, isAddToBackStack, tag)
    }

    override fun onBaseActivityAddFragment(containerId : Int, fragment : Fragment?, bundle : Bundle?, isAddToBackStack : Boolean, tag : String) {
        Tracer.debug(TAG, "onBaseActivityAddFragment: ")
        Utils.hideKeyboard(this)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(containerId, fragment, tag)
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(tag)
        }
        if (bundle != null) {
            fragment !!.arguments = bundle
        }
        fragmentTransaction.commit()
        setNavigationDrawerSwipeState(tag)
    }

    /**
     * Method to init activity
     */
    private fun init() {
        Tracer.debug(TAG, "init: ")
        mMerchantNetworkTaskProvider = MerchantNetworkTaskProvider()
        mMerchantNetworkTaskProvider !!.attachProvider()
        findViewById<View>(R.id.activity_main_sliding_layout_option_password).setOnClickListener(this)
        findViewById<View>(R.id.activity_main_sliding_layout_option_contact_mobil_pay).setOnClickListener(this)
        findViewById<View>(R.id.activity_main_sliding_layout_option_faq).setOnClickListener(this)
        findViewById<View>(R.id.activity_main_sliding_layout_option_home).setOnClickListener(this)
        findViewById<View>(R.id.activity_main_sliding_layout_option_language).setOnClickListener(this)
        findViewById<View>(R.id.activity_main_sliding_layout_option_logout).setOnClickListener(this)
        findViewById<View>(R.id.activity_main_sliding_layout_option_term_and_condition).setOnClickListener(this)
    }

    /**
     * Method to hide the Drawer
     */
    private fun hideDrawer() {
        Tracer.debug(TAG, "hideDrawer: ")
        val drawerLayout = findViewById<View>(R.id.activity_main_drawer_layout) as DrawerLayout
        drawerLayout.closeDrawer(findViewById<View>(R.id.activity_main_hide_layout))
    }

    /**
     * Method to set the state of the navigation drawer swipe gesture
     *
     * @param currentFragmentTag
     */
    private fun setNavigationDrawerSwipeState(currentFragmentTag : String?) {
        if (currentFragmentTag != null && currentFragmentTag.trim { it <= ' ' }.equals(FragmentTag.MERCHANT_LOGIN, ignoreCase = true)) {
            lockDrawerSwipe()
        } else {
            unlockDrawerSwipe()
        }
    }

    /**
     * Method to lock Drawer Swipe Gesture
     */
    private fun lockDrawerSwipe() {
        Tracer.debug(TAG, "lockDrawerSwipe: ")
        val drawerLayout = findViewById<View>(R.id.activity_main_drawer_layout) as DrawerLayout
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        drawerLayout.closeDrawer(findViewById<View>(R.id.activity_main_hide_layout))
    }

    /**
     * Method to unlock Drawer Swipe Gesture
     */
    private fun unlockDrawerSwipe() {
        Tracer.debug(TAG, "unlockDrawerSwipe: ")
        val drawerLayout = findViewById<View>(R.id.activity_main_drawer_layout) as DrawerLayout
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    /**
     * Method to logout the Merchant
     */
    private fun logoutMerchant() {
        Tracer.debug(TAG, "logoutMerchant : ")
        val merchantNupayId = PreferenceData.getMerchantNupayId(this)
        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(this, getString(R.string.endpoint_merchant_logout), date)
        val publicKey = getString(R.string.public_key)
        val pushId = "123"
        val gcmId = "123"
        val dtoMerchantLogoutRequest = DTOMerchantLogoutRequest(token!!, timeStamp, publicKey, merchantNupayId)
        Utils.showLoadingDialog(this)
        mMerchantNetworkTaskProvider !!.merchantLogoutTask(this, dtoMerchantLogoutRequest, mMerchantLogoutResponseNetworkCallBack)
    }

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".MainActivity"
    }
}