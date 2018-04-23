package com.mkrworld.mobilpay.utils

import android.content.Context

import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R

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
            when (urlResId) {
                R.string.url_login -> return context.getString(R.string.url_login)
                R.string.url_agent_logo -> return context.getString(R.string.url_agent_logo)
                R.string.url_generate_qr_code_token -> return context.getString(R.string.url_generate_qr_code_token)
                R.string.url_send_bill -> return context.getString(R.string.url_send_bill)
                R.string.url_fetch_bill -> return context.getString(R.string.url_fetch_bill)
                R.string.url_mobile_number_status -> return context.getString(R.string.url_mobile_number_status)
                R.string.url_agent_logout -> return context.getString(R.string.url_agent_logout)
                R.string.url_change_password -> return context.getString(R.string.url_change_password)
                R.string.url_agent_details -> return context.getString(R.string.url_agent_details)
                R.string.url_send_forgot_password_otp -> return context.getString(R.string.url_send_forgot_password_otp)
                R.string.url_forgot_password -> return context.getString(R.string.url_forgot_password)
                R.string.url_get_user_details -> return context.getString(R.string.url_get_user_details)
            }
            return ""
        }
    }

}
