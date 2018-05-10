package com.mkrworld.mobilpay.ui.activity

import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import com.mkrworld.androidlib.callback.OnBaseActivityListener
import com.mkrworld.androidlib.callback.OnBaseFragmentListener
import com.mkrworld.androidlib.controller.AppPermissionController
import com.mkrworld.androidlib.network.NetworkCallBack
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.network.logout.DTOLogoutRequest
import com.mkrworld.mobilpay.dto.network.logout.DTOLogoutResponse
import com.mkrworld.mobilpay.provider.fragment.FragmentProvider
import com.mkrworld.mobilpay.provider.fragment.FragmentTag
import com.mkrworld.mobilpay.provider.network.AppNetworkTaskProvider
import com.mkrworld.mobilpay.utils.PreferenceData
import com.mkrworld.mobilpay.utils.Utils
import java.util.*

class MainActivity : AppCompatActivity(), OnBaseActivityListener, View.OnClickListener, AppPermissionController.OnAppPermissionControllerListener {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".MainActivity"
    }

    private var mAppPermissionController : AppPermissionController? = null
    private var mAppNetworkTaskProvider : AppNetworkTaskProvider? = null
    private val mAgentLogoutResponseNetworkCallBack = object : NetworkCallBack<DTOLogoutResponse> {
        override fun onSuccess(dto : DTOLogoutResponse) {
            Tracer.debug(TAG, "onSuccess : ")
            Utils.dismissLoadingDialog()
            try {
                if (dto == null) {
                    Tracer.showSnack(findViewById(R.id.activity_main_parent), R.string.no_data_fetch_from_server)
                    return
                }
                Tracer.showSnack(findViewById(R.id.activity_main_parent), dto.getMessage())
                PreferenceData.clearStore(applicationContext)
                // MOVE TO LOGIN FRAGMENT
                val fragment = FragmentProvider.getFragment(FragmentTag.LOGIN)
                onBaseActivityReplaceFragment(fragment !!, null, FragmentTag.LOGIN)
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

    override fun onCreate(savedInstanceState : Bundle?) {
        Tracer.debug(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.activity_main_toolbar) as Toolbar
        setSupportActionBar(toolbar)
        init()
    }

    override fun onDestroy() {
        if (mAppNetworkTaskProvider != null) {
            mAppNetworkTaskProvider !!.detachProvider()
        }
        super.onDestroy()
    }

    override fun onBackPressed() {
        Tracer.debug(TAG, "onBackPressed: ")
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

    override fun onAppPermissionControllerListenerHaveAllRequiredPermission() {
        Tracer.debug(TAG, "onAppPermissionControllerListenerHaveAllRequiredPermission : ")
        onBaseActivityAddFragment(FragmentProvider.getFragment(FragmentTag.LOGIN) !!, null, false, FragmentTag.LOGIN)
    }

    override fun onClick(view : View) {
        Tracer.debug(TAG, "onClick: ")
        when (view.id) {
            R.id.activity_main_sliding_layout_option_password -> {
                val fragment = FragmentProvider.getFragment(FragmentTag.CHANGE_PASSWORD)
                onBaseActivityAddFragment(fragment !!, null, true, FragmentTag.CHANGE_PASSWORD)
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

    override fun onBaseActivityReplaceFragment(fragment : Fragment, bundle : Bundle?, tag : String) {
        Tracer.debug(TAG, "onBaseActivityReplaceFragment: ")
        onBaseActivityReplaceFragment(R.id.activity_main_fragment_container, fragment, bundle, tag)
    }

    override fun onBaseActivityReplaceFragment(containerId : Int, fragment : Fragment, bundle : Bundle?, tag : String) {
        Tracer.debug(TAG, "onBaseActivityReplaceFragment: ")
        Utils.hideKeyboard(this)
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val enterAnim1 = R.anim.slide_in_right
        val exitAnim1 = R.anim.slide_out_left
        val enterAnim2 = R.anim.slide_in_left
        val exitAnim2 = R.anim.slide_out_right
        fragmentTransaction.setCustomAnimations(enterAnim1, exitAnim1, enterAnim2, exitAnim2);
        fragmentTransaction.replace(containerId, fragment, tag)
        if (bundle != null) {
            fragment !!.arguments = bundle
        }
        fragmentTransaction.commit()
    }

    override fun onBaseActivityAddFragment(fragment : Fragment, bundle : Bundle?, isAddToBackStack : Boolean, tag : String) {
        Tracer.debug(TAG, "onBaseActivityAddFragment: ")
        onBaseActivityAddFragment(R.id.activity_main_fragment_container, fragment, bundle, isAddToBackStack, tag)
    }

    override fun onBaseActivityAddFragment(containerId : Int, fragment : Fragment, bundle : Bundle?, isAddToBackStack : Boolean, tag : String) {
        Tracer.debug(TAG, "onBaseActivityAddFragment: ")
        Utils.hideKeyboard(this)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val enterAnim1 = R.anim.slide_in_right
        val exitAnim1 = R.anim.slide_out_left
        val enterAnim2 = R.anim.slide_in_left
        val exitAnim2 = R.anim.slide_out_right
        fragmentTransaction.setCustomAnimations(enterAnim1, exitAnim1, enterAnim2, exitAnim2);
        fragmentTransaction.add(containerId, fragment, tag)
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(tag)
        }
        if (bundle != null) {
            fragment !!.arguments = bundle
        }
        fragmentTransaction.commit()
    }

    /**
     * Method to init activity
     */
    private fun init() {
        Tracer.debug(TAG, "init: ")
        var permissions : Array<String> = arrayOf(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.USE_FINGERPRINT, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)
        mAppPermissionController = AppPermissionController(this, permissions, this)
        mAppPermissionController?.initializedAppPermission()
        mAppNetworkTaskProvider = AppNetworkTaskProvider()
        mAppNetworkTaskProvider !!.attachProvider()
    }

    override fun onRequestPermissionsResult(requestCode : Int, permissions : Array<String>, grantResults : IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mAppPermissionController?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * Method to logout the Merchant
     */
    private fun logoutMerchant() {
        Tracer.debug(TAG, "logoutMerchant : ")
        val date = Date()
        val timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT)
        val token = Utils.createToken(this, getString(R.string.endpoint_logout), date)
        val publicKey = getString(R.string.public_key)
        val dtoAgentLogoutRequest = DTOLogoutRequest(token !!, timeStamp, publicKey, PreferenceData.getUserType(this), PreferenceData.getLoginMerchantId(this), PreferenceData.getLoginAgentId(this))
        Utils.showLoadingDialog(this)
        mAppNetworkTaskProvider !!.logoutTask(this, dtoAgentLogoutRequest, mAgentLogoutResponseNetworkCallBack)
    }
}
