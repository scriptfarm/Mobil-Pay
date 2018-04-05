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
        private val MERCHANT_ID = "MERCHANT_ID"
        private val MERCHANT_NUPAY_ID = "MERCHANT_NUPAY_ID"
        private val MERCHANT_LOGIN_ID = "MERCHANT_LOGIN_ID"
        private val MERCHANT_LOGIN_PASSWORD = "MERCHANT_LOGIN_PASSWORD"

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
         * Method to get the Merchant Id
         *
         * @param context
         * @return id of the Merchant
         */
        fun getMerchantId(context : Context) : String {
            Tracer.debug(TAG, "getMerchantId : ")
            return getShearedPreference(context).getString(MERCHANT_ID, "") !!.trim { it <= ' ' }
        }

        /**
         * Method to set the Merchant Id
         *
         * @param context
         * @param id
         */
        fun setMerchantId(context : Context, id : String) {
            Tracer.debug(TAG, "setMerchantId : ")
            getShearedPreferenceEditor(context).putString(MERCHANT_ID, id).commit()
        }

        /**
         * Method to get the Merchant Nupay Id
         *
         * @param context
         * @return Nupay id of the Merchant
         */
        fun getMerchantNupayId(context : Context) : String {
            Tracer.debug(TAG, "getMerchantNupayId : ")
            return getShearedPreference(context).getString(MERCHANT_NUPAY_ID, "") !!.trim { it <= ' ' }
        }

        /**
         * Method to set the Merchant Nupay Id
         *
         * @param context
         * @param nupayId
         */
        fun setMerchantNupayId(context : Context, nupayId : String) {
            Tracer.debug(TAG, "setMerchantNupayId : ")
            getShearedPreferenceEditor(context).putString(MERCHANT_NUPAY_ID, nupayId).commit()
        }

        /**
         * Method to get the Merchant Login Id
         *
         * @param context
         * @return Login id of the Merchant
         */
        fun getMerchantLoginId(context : Context) : String {
            Tracer.debug(TAG, "getMerchantLoginId : ")
            return getShearedPreference(context).getString(MERCHANT_LOGIN_ID, "") !!.trim { it <= ' ' }
        }

        /**
         * Method to set the Merchant Login Id
         *
         * @param context
         * @param loginId
         */
        fun setMerchantLoginId(context : Context, loginId : String) {
            Tracer.debug(TAG, "setMerchantLoginId : ")
            getShearedPreferenceEditor(context).putString(MERCHANT_LOGIN_ID, loginId).commit()
        }

        /**
         * Method to get the Merchant Login Password
         *
         * @param context
         * @return Login Password of the Merchant
         */
        fun getMerchantLoginPassword(context : Context) : String {
            Tracer.debug(TAG, "getMerchantLoginPassword : ")
            return getShearedPreference(context).getString(MERCHANT_LOGIN_PASSWORD, "") !!.trim { it <= ' ' }
        }

        /**
         * Method to set the Merchant Login Password
         *
         * @param context
         * @param loginPassword
         */
        fun setMerchantLoginPassword(context : Context, loginPassword : String) {
            Tracer.debug(TAG, "setMerchantLoginPassword : ")
            getShearedPreferenceEditor(context).putString(MERCHANT_LOGIN_PASSWORD, loginPassword).commit()
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
