package com.mkrworld.androidlib.callback

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by a1zfkxa3 on 11/28/2017.
 */

interface OnBaseActivityListener {

    /**
     * Method to notifyTaskResponse that there is need to show the Ad
     */
    fun onBaseActivityShowInterstitialAd()

    /**
     * Method to notifyTaskResponse that there is need to show the Ad
     */
    fun onBaseActivityShowBannerAd()

    /**
     * Method to replace fragment
     *
     * @param fragment Fragment to be loaded
     * @param bundle   Bundle sat as argument
     * @param tag      Fragment Tag
     */
    fun onBaseActivityReplaceFragment(fragment : Fragment, bundle : Bundle?, tag : String)

    /**
     * Method to replace fragment
     *
     * @param containerId fragment container Id
     * @param fragment    Fragment to be loaded
     * @param bundle      Bundle sat as argument
     * @param tag         Fragment Tag
     */
    fun onBaseActivityReplaceFragment(containerId : Int, fragment : Fragment, bundle : Bundle?, tag : String)

    /**
     * Method to add fragment
     *
     * @param fragment         Fragment to be loaded
     * @param bundle           Bundle sat as argument
     * @param isAddToBackStack TRUE if need to save fragment in back stack
     * @param tag              Fragment Tag
     */
    fun onBaseActivityAddFragment(fragment : Fragment, bundle : Bundle?, isAddToBackStack : Boolean, tag : String)

    /**
     * Method to add fragment
     *
     * @param containerId      Fragment container Id
     * @param fragment         Fragment to be loaded
     * @param bundle           Bundle sat as argument
     * @param isAddToBackStack TRUE if need to save fragment in back stack
     * @param tag              Fragment Tag
     */
    fun onBaseActivityAddFragment(containerId : Int, fragment : Fragment, bundle : Bundle?, isAddToBackStack : Boolean, tag : String)

    /**
     * Method to set the Title of the Screen
     */
    fun onBaseActivitySetScreenTitle(title : String)

}
