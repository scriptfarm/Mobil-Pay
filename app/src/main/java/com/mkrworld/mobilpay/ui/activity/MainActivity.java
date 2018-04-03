package com.mkrworld.mobilpay.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mkrworld.androidlib.callback.OnBaseActivityListener;
import com.mkrworld.androidlib.callback.OnBaseFragmentListener;
import com.mkrworld.androidlib.network.NetworkCallBack;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;
import com.mkrworld.mobilpay.dto.merchantlogout.DTOMerchantLogoutRequest;
import com.mkrworld.mobilpay.dto.merchantlogout.DTOMerchantLogoutResponse;
import com.mkrworld.mobilpay.provider.fragment.FragmentProvider;
import com.mkrworld.mobilpay.provider.fragment.FragmentTag;
import com.mkrworld.mobilpay.provider.network.MerchantNetworkTaskProvider;
import com.mkrworld.mobilpay.utils.PreferenceData;
import com.mkrworld.mobilpay.utils.Utils;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnBaseActivityListener, View.OnClickListener {
    private static final String TAG = BuildConfig.BASE_TAG + ".MainActivity";
    private MerchantNetworkTaskProvider mMerchantNetworkTaskProvider;
    private NetworkCallBack<DTOMerchantLogoutResponse> mMerchantLogoutResponseNetworkCallBack = new NetworkCallBack<DTOMerchantLogoutResponse>() {
        @Override
        public void onSuccess(DTOMerchantLogoutResponse dtoMerchantLogoutResponse) {
            Tracer.debug(TAG, "onSuccess : ");
            Utils.dismissLoadingDialog();
            try {
                if (dtoMerchantLogoutResponse == null) {
                    Tracer.showSnack(findViewById(R.id.activity_main_parent), R.string.no_data_fetch_from_server);
                    return;
                }
                Tracer.showSnack(findViewById(R.id.activity_main_parent), dtoMerchantLogoutResponse.getMessage());
                PreferenceData.clearStore(getApplicationContext());
                // MOVE TO LOGIN FRAGMENT
                Fragment fragment = FragmentProvider.getFragment(FragmentTag.MERCHANT_LOGIN);
                onBaseActivityReplaceFragment(fragment, null, FragmentTag.MERCHANT_LOGIN);
            } catch (Exception e) {
                Tracer.error(TAG, "onSuccess : " + e.getMessage());
            }
        }

        @Override
        public void onError(String errorMessage, int errorCode) {
            Tracer.debug(TAG, "onError : ");
            Utils.dismissLoadingDialog();
            try {
                Tracer.showSnack(findViewById(R.id.activity_main_parent), errorMessage);
            } catch (Exception e) {
                Tracer.error(TAG, "onError : " + e.getMessage());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Tracer.debug(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        init();
        onBaseActivityAddFragment(FragmentProvider.getFragment(FragmentTag.MERCHANT_LOGIN), null, false, FragmentTag.MERCHANT_LOGIN);
    }

    @Override
    protected void onDestroy() {
        if (mMerchantNetworkTaskProvider != null) {
            mMerchantNetworkTaskProvider.detachProvider();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Tracer.debug(TAG, "onBackPressed: ");
        if (isDrawerVisible()) {
            hideDrawer();
            return;
        }
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_fragment_container);
        if (fragment != null && fragment instanceof OnBaseFragmentListener && ((OnBaseFragmentListener) fragment).onBackPressed()) {
            return;
        }
        super.onBackPressed();
        fragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_fragment_container);
        if (fragment != null && fragment instanceof OnBaseFragmentListener) {
            ((OnBaseFragmentListener) fragment).onPopFromBackStack();
        }
    }

    @Override
    public void onClick(View view) {
        Tracer.debug(TAG, "onClick: ");
        switch (view.getId()) {
            case R.id.activity_main_sliding_layout_option_password:
                Fragment fragment = FragmentProvider.getFragment(FragmentTag.CHANGE_PASSWORD);
                onBaseActivityAddFragment(fragment, null, true, FragmentTag.CHANGE_PASSWORD);
                break;
            case R.id.activity_main_sliding_layout_option_contact_mobil_pay:
                break;
            case R.id.activity_main_sliding_layout_option_faq:
                break;
            case R.id.activity_main_sliding_layout_option_home:
                break;
            case R.id.activity_main_sliding_layout_option_language:
                break;
            case R.id.activity_main_sliding_layout_option_logout:
                logoutMerchant();
                break;
            case R.id.activity_main_sliding_layout_option_term_and_condition:
                break;
        }
        if (isDrawerVisible()) {
            hideDrawer();
        }
    }

    @Override
    public void onBaseActivitySetScreenTitle(String title) {
        Tracer.debug(TAG, "onBaseActivitySetScreenTitle: ");
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBaseActivityShowInterstitialAd() {
        Tracer.debug(TAG, "onBaseActivityShowInterstitialAd: ");
    }

    @Override
    public void onBaseActivityShowBannerAd() {
        Tracer.debug(TAG, "onBaseActivityShowBannerAd: ");
    }

    @Override
    public void onBaseActivityReplaceFragment(Fragment fragment, Bundle bundle, String tag) {
        Tracer.debug(TAG, "onBaseActivityReplaceFragment: ");
        onBaseActivityReplaceFragment(R.id.activity_main_fragment_container, fragment, bundle, tag);
    }

    @Override
    public void onBaseActivityReplaceFragment(int containerId, Fragment fragment, Bundle bundle, String tag) {
        Tracer.debug(TAG, "onBaseActivityReplaceFragment: ");
        Utils.hideKeyboard(this);
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerId, fragment, tag);
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        fragmentTransaction.commit();
        setNavigationDrawerSwipeState(tag);
    }

    @Override
    public void onBaseActivityAddFragment(Fragment fragment, Bundle bundle, boolean isAddToBackStack, String tag) {
        Tracer.debug(TAG, "onBaseActivityAddFragment: ");
        onBaseActivityAddFragment(R.id.activity_main_fragment_container, fragment, bundle, isAddToBackStack, tag);
    }

    @Override
    public void onBaseActivityAddFragment(int containerId, Fragment fragment, Bundle bundle, boolean isAddToBackStack, String tag) {
        Tracer.debug(TAG, "onBaseActivityAddFragment: ");
        Utils.hideKeyboard(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerId, fragment, tag);
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        fragmentTransaction.commit();
        setNavigationDrawerSwipeState(tag);
    }

    /**
     * Method to init activity
     */
    private void init() {
        Tracer.debug(TAG, "init: ");
        mMerchantNetworkTaskProvider = new MerchantNetworkTaskProvider();
        mMerchantNetworkTaskProvider.attachProvider();
        findViewById(R.id.activity_main_sliding_layout_option_password).setOnClickListener(this);
        findViewById(R.id.activity_main_sliding_layout_option_contact_mobil_pay).setOnClickListener(this);
        findViewById(R.id.activity_main_sliding_layout_option_faq).setOnClickListener(this);
        findViewById(R.id.activity_main_sliding_layout_option_home).setOnClickListener(this);
        findViewById(R.id.activity_main_sliding_layout_option_language).setOnClickListener(this);
        findViewById(R.id.activity_main_sliding_layout_option_logout).setOnClickListener(this);
        findViewById(R.id.activity_main_sliding_layout_option_term_and_condition).setOnClickListener(this);
    }

    /**
     * Method to check weather the drawer View is visible or not
     *
     * @return TRUE if drawer is visible, else FALSE
     */
    private boolean isDrawerVisible() {
        Tracer.debug(TAG, "isDrawerVisible: ");
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        return drawerLayout != null && drawerLayout.isDrawerVisible(findViewById(R.id.activity_main_hide_layout));
    }

    /**
     * Method to hide the Drawer
     */
    private void hideDrawer() {
        Tracer.debug(TAG, "hideDrawer: ");
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        drawerLayout.closeDrawer(findViewById(R.id.activity_main_hide_layout));
    }

    /**
     * Method to set the state of the navigation drawer swipe gesture
     *
     * @param currentFragmentTag
     */
    private void setNavigationDrawerSwipeState(String currentFragmentTag) {
        if (currentFragmentTag != null && currentFragmentTag.trim().equalsIgnoreCase(FragmentTag.MERCHANT_LOGIN)) {
            lockDrawerSwipe();
        } else {
            unlockDrawerSwipe();
        }
    }

    /**
     * Method to lock Drawer Swipe Gesture
     */
    private void lockDrawerSwipe() {
        Tracer.debug(TAG, "lockDrawerSwipe: ");
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayout.closeDrawer(findViewById(R.id.activity_main_hide_layout));
    }

    /**
     * Method to unlock Drawer Swipe Gesture
     */
    private void unlockDrawerSwipe() {
        Tracer.debug(TAG, "unlockDrawerSwipe: ");
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    /**
     * Method to logout the Merchant
     */
    private void logoutMerchant() {
        Tracer.debug(TAG, "logoutMerchant : ");
        String merchantNupayId = PreferenceData.getMerchantNupayId(this);
        Date date = new Date();
        String timeStamp = Utils.getDateTimeFormate(date, Utils.DATE_FORMAT);
        String token = Utils.createToken(this, getString(R.string.endpoint_merchant_logout), date);
        String publicKey = getString(R.string.public_key);
        String pushId = "123";
        String gcmId = "123";
        DTOMerchantLogoutRequest dtoMerchantLogoutRequest = new DTOMerchantLogoutRequest(token, timeStamp, publicKey, merchantNupayId);
        Utils.showLoadingDialog(this);
        mMerchantNetworkTaskProvider.merchantLogoutTask(this, dtoMerchantLogoutRequest, mMerchantLogoutResponseNetworkCallBack);
    }
}
