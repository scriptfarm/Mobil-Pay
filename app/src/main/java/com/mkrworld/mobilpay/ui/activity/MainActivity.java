package com.mkrworld.mobilpay.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mkrworld.androidlib.callback.OnBaseActivityListener;
import com.mkrworld.androidlib.callback.OnBaseFragmentListener;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;
import com.mkrworld.mobilpay.provider.fragment.FragmentProvider;
import com.mkrworld.mobilpay.provider.fragment.FragmentTag;

public class MainActivity extends AppCompatActivity implements OnBaseActivityListener {
    private static final String TAG = BuildConfig.BASE_TAG + ".MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Tracer.debug(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        onBaseActivityAddFragment(FragmentProvider.getFragment(FragmentTag.MERCHANT_HOME), null, false, FragmentTag.MERCHANT_HOME);
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
    }

    @Override
    public void onBaseActivityReplaceFragment(int containerId, Fragment fragment, Bundle bundle, String tag) {
        Tracer.debug(TAG, "onBaseActivityReplaceFragment: ");
    }

    @Override
    public void onBaseActivityAddFragment(Fragment fragment, Bundle bundle, boolean isAddToBackStack, String tag) {
        Tracer.debug(TAG, "onBaseActivityAddFragment: ");
        onBaseActivityAddFragment(R.id.activity_main_fragment_container, fragment, bundle, isAddToBackStack, tag);
    }

    @Override
    public void onBaseActivityAddFragment(int containerId, Fragment fragment, Bundle bundle, boolean isAddToBackStack, String tag) {
        Tracer.debug(TAG, "onBaseActivityAddFragment: ");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerId, fragment, tag);
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        fragmentTransaction.commit();
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
}
