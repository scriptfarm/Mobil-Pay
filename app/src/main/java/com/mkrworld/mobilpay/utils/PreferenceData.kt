package com.mkrworld.mobilpay.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig

/**
 * Created by mkr on 30/3/18.
 */

class PreferenceData {
    companion object {

        private val TAG = BuildConfig.BASE_TAG + ".PreferenceData"
        private val STORE = "STORE_8"
        private val FINGER_PRINT_CONSENT = "FINGER_PRINT_CONSENT"
        private val THUMB_MERCHANT_LOGIN_ID = "THUMB_MERCHANT_LOGIN_ID"
        private val THUMB_AGENT_LOGIN_ID = "THUMB_AGENT_LOGIN_ID"
        private val THUMB_USER_TYPE = "THUMB_USER_TYPE"
        private val THUMB_LOGIN_PASSWORD = "THUMB_LOGIN_PASSWORD"
        private val MERCHANT_LOGIN_ID = "MERCHANT_LOGIN_ID"
        private val AGENT_LOGIN_ID = "AGENT_LOGIN_ID"
        private val USER_TYPE = "USER_TYPE"
        private val LOGIN_TIME = "LOGIN_TIME"
        private val SMS_READ_TIME = "SMS_READ_TIME"

        //==================================================================================================================
        //==================================================================================================================
        //==================================================================================================================


        /**
         * Method to know weather the merchant allowed to use finger print or not
         *
         * @param context
         * @return TRUE if allowed else FALSE
         */
        fun isHaveFingerPrintConsent(context : Context) : Boolean {
            Tracer.debug(TAG, "isHaveFingerPrintConsent() ")
            return getShearedPreference(context).getBoolean(FINGER_PRINT_CONSENT, false)
        }

        /**
         * Method to set that merchant allowed to use finger print
         *
         * @param context
         */
        fun setHaveFingerPrintConsent(context : Context) {
            Tracer.debug(TAG, "setHaveFingerPrintConsent() ")
            getShearedPreferenceEditor(context).putBoolean(FINGER_PRINT_CONSENT, true).commit()
        }

        /**
         * Method to get the Thumb Login Merchant Id
         *
         * @param context
         * @return id of the agent
         */
        fun getThumbLoginMerchantId(context : Context) : String {
            Tracer.debug(TAG, "getThumbLoginMerchantId : ")
            return getShearedPreference(context).getString(THUMB_MERCHANT_LOGIN_ID, "") !!.trim { it <= ' ' }
        }

        /**
         * Method to set the Thumb Login Merchant Id
         *
         * @param context
         * @param id
         */
        fun setThumbLoginMerchantId(context : Context, id : String) {
            Tracer.debug(TAG, "setThumbLoginMerchantId : ")
            getShearedPreferenceEditor(context).putString(THUMB_MERCHANT_LOGIN_ID, id).commit()
        }

        /**
         * Method to get the Thumb Login Agent Id
         *
         * @param context
         * @return id of the agent
         */
        fun getThumbLoginAgentId(context : Context) : String {
            Tracer.debug(TAG, "getThumbLoginAgentId : ")
            return getShearedPreference(context).getString(THUMB_AGENT_LOGIN_ID, "") !!.trim { it <= ' ' }
        }

        /**
         * Method to set the Thumb Login Agent Id
         *
         * @param context
         * @param id
         */
        fun setThumbLoginAgentId(context : Context, id : String) {
            Tracer.debug(TAG, "setThumbLoginAgentId : ")
            getShearedPreferenceEditor(context).putString(THUMB_AGENT_LOGIN_ID, id).commit()
        }

        /**
         * Method to get the Thumb Login Password
         *
         * @param context
         * @return Login Password of the agent
         */
        fun getThumbLoginPassword(context : Context) : String {
            Tracer.debug(TAG, "getThumbLoginPassword : ")
            return getShearedPreference(context).getString(THUMB_LOGIN_PASSWORD, "") !!.trim { it <= ' ' }
        }

        /**
         * Method to set the Thumb Login Password
         *
         * @param context
         * @param loginPassword
         */
        fun setThumbLoginPassword(context : Context, loginPassword : String) {
            Tracer.debug(TAG, "setThumbLoginPassword : ")
            getShearedPreferenceEditor(context).putString(THUMB_LOGIN_PASSWORD, loginPassword).commit()
        }

        /**
         * Method to get the Thumb Login User Type
         *
         * @param context
         * @return user type
         */
        fun getThumbLoginUserType(context : Context) : String {
            Tracer.debug(TAG, "getThumbLoginUserType : ")
            return getShearedPreference(context).getString(THUMB_USER_TYPE, "") !!.trim { it <= ' ' }
        }

        /**
         * Method to set the Thumb Login User Type
         *
         * @param context
         * @param userType
         */
        fun setThumbLoginUserType(context : Context, userType : String) {
            Tracer.debug(TAG, "setThumbLoginUserType : ")
            getShearedPreferenceEditor(context).putString(THUMB_USER_TYPE, userType).commit()
        }

        /**
         * Method to get the Login Merchant Id
         *
         * @param context
         * @return id of the agent
         */
        fun getLoginMerchantId(context : Context) : String {
            Tracer.debug(TAG, "getLoginMerchantId : ")
            return getShearedPreference(context).getString(MERCHANT_LOGIN_ID, "") !!.trim { it <= ' ' }
        }

        /**
         * Method to set the Login Merchant Id
         *
         * @param context
         * @param id
         */
        fun setLoginMerchantId(context : Context, id : String) {
            Tracer.debug(TAG, "setLoginMerchantId : ")
            getShearedPreferenceEditor(context).putString(MERCHANT_LOGIN_ID, id).commit()
        }

        /**
         * Method to get the Login Agent Id
         *
         * @param context
         * @return id of the agent
         */
        fun getLoginAgentId(context : Context) : String {
            Tracer.debug(TAG, "getLoginAgentId : ")
            return getShearedPreference(context).getString(AGENT_LOGIN_ID, "") !!.trim { it <= ' ' }
        }

        /**
         * Method to set the Login Agent Id
         *
         * @param context
         * @param id
         */
        fun setLoginAgentId(context : Context, id : String) {
            Tracer.debug(TAG, "setLoginAgentId : ")
            getShearedPreferenceEditor(context).putString(AGENT_LOGIN_ID, id).commit()
        }

        /**
         * Method to get the user type
         *
         * @param context
         * @return User Type
         */
        fun getUserType(context : Context) : String {
            Tracer.debug(TAG, "getUserType : ")
            return getShearedPreference(context).getString(USER_TYPE, "") !!.trim { it <= ' ' }
        }

        /**
         * Method to set the user type
         *
         * @param context
         * @param userType
         */
        fun setUserType(context : Context, userType : String) {
            Tracer.debug(TAG, "setUserType : ")
            getShearedPreferenceEditor(context).putString(USER_TYPE, userType).commit()
        }

        /**
         * Method to get the time when user login in the app
         *
         * @param context
         * @return
         */
        fun getLoginTime(context : Context) : Long {
            Tracer.debug(TAG, "getLoginTime : ")
            return getShearedPreference(context).getLong(LOGIN_TIME, 0)
        }

        /**
         * Method to set the time when user login in the app
         *
         * @param context
         * @param loginTime
         */
        fun setLoginTime(context : Context, loginTime : Long) {
            Tracer.debug(TAG, "setLoginTime : ")
            getShearedPreferenceEditor(context).putLong(LOGIN_TIME, loginTime).commit()
        }

        /**
         * Method to get the time when sms read from inbox
         *
         * @param context
         * @return
         */
        fun getSmsReadTime(context : Context) : Long {
            Tracer.debug(TAG, "getSmsReadTime : ")
            val lastSmsReadTime = getShearedPreference(context).getLong(SMS_READ_TIME, 0)
            return if (System.currentTimeMillis() - lastSmsReadTime > Constants.MAX_SMS_READ_INTERVAL) {
                System.currentTimeMillis() - Constants.MAX_SMS_READ_INTERVAL;
            } else {
                return lastSmsReadTime
            }
        }

        /**
         * Method to set the time when sms read from inbox
         *
         * @param context
         * @param loginTime
         */
        fun setSmsReadTime(context : Context, loginTime : Long) {
            Tracer.debug(TAG, "setSmsReadTime : ")
            getShearedPreferenceEditor(context).putLong(SMS_READ_TIME, loginTime).commit()
        }

        //==================================================================================================================
        //==================================================================================================================
        //==================================================================================================================

        /**
         * Method to clear the Data Store
         *
         * @param context
         */
        fun clearStore(context : Context) {
            Tracer.debug(TAG, "clearStore()")
            getShearedPreferenceEditor(context).clear().commit()
        }

        /**
         * Method to return the Data Store Prefference
         *
         * @param context
         * @return
         */
        private fun getShearedPreference(context : Context) : SharedPreferences {
            return context.getSharedPreferences(STORE, Context.MODE_PRIVATE)
        }

        /**
         * caller to commit this editor
         *
         * @param context
         * @return Editor
         */
        private fun getShearedPreferenceEditor(context : Context) : Editor {
            return getShearedPreference(context).edit()
        }
    }
}
