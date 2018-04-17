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
        private val AGENT_ID = "AGENT_ID"
        private val AGENT_PASSWORD = "AGENT_PASSWORD"

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
         * Method to get the agent Id
         *
         * @param context
         * @return id of the agent
         */
        fun getAgentId(context : Context) : String {
            Tracer.debug(TAG, "getUserId : ")
            return getShearedPreference(context).getString(AGENT_ID, "") !!.trim { it <= ' ' }
        }

        /**
         * Method to set the agent Id
         *
         * @param context
         * @param id
         */
        fun setAgentId(context : Context, id : String) {
            Tracer.debug(TAG, "setUserId : ")
            getShearedPreferenceEditor(context).putString(AGENT_ID, id).commit()
        }

        /**
         * Method to get the agent Login Password
         *
         * @param context
         * @return Login Password of the agent
         */
        fun getAgentLoginPassword(context : Context) : String {
            Tracer.debug(TAG, "getAgentLoginPassword : ")
            return getShearedPreference(context).getString(AGENT_PASSWORD, "") !!.trim { it <= ' ' }
        }

        /**
         * Method to set the agent Login Password
         *
         * @param context
         * @param loginPassword
         */
        fun setAgentLoginPassword(context : Context, loginPassword : String) {
            Tracer.debug(TAG, "setAgentLoginPassword : ")
            getShearedPreferenceEditor(context).putString(AGENT_PASSWORD, loginPassword).commit()
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
