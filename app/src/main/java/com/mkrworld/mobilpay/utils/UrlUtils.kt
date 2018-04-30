package com.mkrworld.mobilpay.utils

import android.content.Context
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 27/3/18.
 */

class UrlUtils {
    companion object {

        private val TAG = BuildConfig.BASE_TAG + ".UrlUtils"

        /**
         * Method to get the Base Url
         *
         * @param context
         * @param urlResId
         * @return
         */
        fun getUrl(context : Context, urlResId : Int) : String {
            Tracer.debug(TAG, "getUrl : $urlResId")
            return BuildConfig.BASE_URL + getStringUrl(context, urlResId)
        }

        /**
         * Method to get the String Equvalent of the URL
         *
         * @param context
         * @param urlResId
         * @return
         */
        private fun getStringUrl(context : Context, urlResId : Int) : String {
            Tracer.debug(TAG, "getStringUrl : $urlResId")
            return return context.getString(urlResId)
        }
    }

}
