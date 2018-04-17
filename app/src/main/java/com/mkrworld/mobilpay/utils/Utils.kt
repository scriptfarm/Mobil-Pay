package com.mkrworld.mobilpay.utils

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.mkrworld.androidlib.utils.MKRDialogUtil
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by mkr on 14/3/18.
 */

class Utils {
    companion object {

        private val TAG = BuildConfig.BASE_TAG + ".Utils"
        var DATE_FORMAT = "dd-MM-yyyy HH:mm:ss"

        /**
         * Method to hide the soft keyboard
         *
         * @param activity
         */
        fun hideKeyboard(activity : Activity?) {
            Tracer.debug(TAG, "hideKeyboard: " + activity !!)
            if (activity == null) {
                return
            }
            val view = activity.currentFocus
            if (view != null) {
                val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        /**
         * Method to hide the soft keyboard
         *
         * @param view
         */
        fun hideKeyboard(context : Context, view : View?) {
            Tracer.debug(TAG, "hideKeyboard: " + view !!)
            if (view != null) {
                view.requestFocus()
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        /**
         * Method to show the soft keyboard
         *
         * @param context
         * @param view    the action view
         */
        fun showKeyboard(context : Context, view : View?) {
            Tracer.debug(TAG, "showKeyboard: $context   $view")
            if (view != null) {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
            }
        }

        /**
         * Method to check weather the String is empty or not
         *
         * @param str
         * @return
         */
        fun isStringEmpty(str : String?) : Boolean {
            Tracer.debug(TAG, "isStringEmpty: " + str !!)
            var s : String = "MKR"
            return str == null || str.isEmpty()
        }

        /**
         * Method to get the Date in UTC Format
         *
         * @param date
         * @param dateFormat
         * @return
         */
        fun getDateTimeFormate(date : Date, dateFormat : String) : String {
            val sdf = SimpleDateFormat(dateFormat)
            sdf.timeZone = TimeZone.getDefault()
            val utcTime = sdf.format(date)
            return sdf.format(date)
        }

        /**
         * Method to create the token
         *
         * @param context
         * @param url
         * @param date
         * @return
         */
        fun createToken(context : Context, url : String, date : Date) : String? {
            val unixTime = date.time / 1000
            val md5Format : String = ("" + unixTime.toString()) + ":" + context.getString(R.string.private_key) + ":" + context.getString(R.string.public_key) + ":" + url // + ":" + context.getString(R.string.salt);
            val md5Token = md5(md5Format.toLowerCase())
            return md5(md5Format.toLowerCase())
        }

        /**
         * Method to encrypt String in md5 Hash
         *
         * @param md5
         * @return
         */
        fun md5(md5 : String) : String? {
            try {
                val md = java.security.MessageDigest.getInstance("MD5")
                val array = md.digest(md5.toByteArray())
                val sb = StringBuffer()
                for (i in array.indices) {
                    sb.append(Integer.toHexString(array[i].toInt() and 0xFF or 0x100).substring(1, 3))
                }
                return sb.toString()
            } catch (e : java.security.NoSuchAlgorithmException) {
            }

            return null
        }

        /**
         * Method to show the Loading Dialog
         *
         * @param context
         */
        fun showLoadingDialog(context : Context) {
            Tracer.debug(TAG, "showLoadingDialog : ")
            var layoutInflater : LayoutInflater = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) !! as LayoutInflater);
            var view : View = layoutInflater !!.inflate(R.layout.dialog_progress, null)
            MKRDialogUtil.showLoadingDialog(context, view !!)
        }

        /**
         * Method to dismiss the Loading Dialog
         */
        fun dismissLoadingDialog() {
            Tracer.debug(TAG, "dismissLoadingDialog : ")
            MKRDialogUtil.dismissLoadingDialog()
        }

        /**
         * Method to convert String into Editable
         */
        fun getEditable(text : String) : Editable {
            return Editable.Factory.getInstance().newEditable(text)
        }
    }
}
