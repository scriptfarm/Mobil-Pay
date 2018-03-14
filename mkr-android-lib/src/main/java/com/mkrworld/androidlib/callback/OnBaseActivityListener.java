package com.mkrworld.androidlib.callback;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by a1zfkxa3 on 11/28/2017.
 */

public interface OnBaseActivityListener {

    /**
     * Method to set the Title of the Screen
     *
     * @param title Title to set at the top of the Screen
     */
    public void onBaseActivitySetScreenTitle(String title);

    /**
     * Method to notifyTaskResponse that there is need to show the Ad
     */
    public void onBaseActivityShowInterstitialAd();

    /**
     * Method to notifyTaskResponse that there is need to show the Ad
     */
    public void onBaseActivityShowBannerAd();

    /**
     * Method to replace fragment
     *
     * @param fragment Fragment to be loaded
     * @param bundle   Bundle sat as argument
     * @param tag      Fragment Tag
     */
    public void onBaseActivityReplaceFragment(Fragment fragment, Bundle bundle, String tag);

    /**
     * Method to replace fragment
     *
     * @param containerId fragment container Id
     * @param fragment    Fragment to be loaded
     * @param bundle      Bundle sat as argument
     * @param tag         Fragment Tag
     */
    public void onBaseActivityReplaceFragment(int containerId, Fragment fragment, Bundle bundle, String tag);

    /**
     * Method to add fragment
     *
     * @param fragment         Fragment to be loaded
     * @param bundle           Bundle sat as argument
     * @param isAddToBackStack TRUE if need to save fragment in back stack
     * @param tag              Fragment Tag
     */
    public void onBaseActivityAddFragment(Fragment fragment, Bundle bundle, boolean isAddToBackStack, String tag);

    /**
     * Method to add fragment
     *
     * @param containerId      Fragment container Id
     * @param fragment         Fragment to be loaded
     * @param bundle           Bundle sat as argument
     * @param isAddToBackStack TRUE if need to save fragment in back stack
     * @param tag              Fragment Tag
     */
    public void onBaseActivityAddFragment(int containerId, Fragment fragment, Bundle bundle, boolean isAddToBackStack, String tag);

}
