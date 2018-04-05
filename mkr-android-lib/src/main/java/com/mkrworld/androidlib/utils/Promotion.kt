package com.mkrworld.androidlib.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

import com.mkrworld.androidlib.BuildConfig

class Promotion {
    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".Promotion"

        /**
         * Method to send review
         *
         * @param context
         */
        fun sendReview(context : Context) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.packageName))
            try {
                context.startActivity(intent)
            } catch (e : Exception) {
                e.printStackTrace()
                Tracer.error(TAG, "sendReview() " + e.message)
            }

        }

        /**
         * Method to open downloading
         *
         * @param context
         * @param url
         */
        fun openDownloadUrl(context : Context, url : String) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            try {
                context.startActivity(intent)
            } catch (e : Exception) {
                e.printStackTrace()
                Tracer.error(TAG, "sendReview() " + e.message)
            }

        }

        /**
         * Method to get more apps
         *
         * @param context
         */
        fun getMoreApps(context : Context) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:MKR WORLD"))
            try {
                context.startActivity(intent)
            } catch (e : Exception) {
                e.printStackTrace()
                Tracer.error(TAG, "getMoreApps() " + e.message)
            }

        }

        /**
         * Method top send feedback
         *
         * @param context
         * @param appName
         */
        fun sendFeedback(context : Context, appName : String) {
            try {
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("mkrworldapps@gmail.com"))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, appName)
                emailIntent.putExtra(Intent.EXTRA_TEXT, "")
                emailIntent.type = "message/rfc822"
                emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(emailIntent)
            } catch (e : Exception) {
                e.printStackTrace()
                Tracer.error(TAG, "sendFeedback() " + e.message)
            }

        }

        /**
         * Method to share application
         *
         * @param context
         * @param appName
         * @param shareMessage
         * @param shareUrl
         */
        @JvmOverloads
        fun shareApp(context : Context, appName : String, shareMessage : String, shareUrl : String = "http://play.google.com/store/apps/details?id=" + context.packageName) {
            val message = shareMessage + "\n\n\n\n" + shareUrl
            val sentIntent = Intent(Intent.ACTION_SEND)
            sentIntent.type = "text/plain"
            sentIntent.putExtra(Intent.EXTRA_SUBJECT, appName)
            sentIntent.putExtra(Intent.EXTRA_TEXT, message)
            val sendIntent = Intent.createChooser(sentIntent, "Share File")
            sendIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(sendIntent)
        }

        /**
         * Method to share text
         *
         * @param context
         * @param appName
         * @param shareMessage
         * @param infotext
         */
        fun shareText(context : Context, appName : String, shareMessage : String, infotext : String) {
            shareText(context, appName, shareMessage, infotext, "http://play.google.com/store/apps/details?id=" + context.packageName)
        }

        /**
         * Method to share text
         *
         * @param appName
         * @param shareMessage
         * @param infoText
         * @param shareUrl
         */
        fun shareText(context : Context, appName : String, shareMessage : String, infoText : String?, shareUrl : String) {
            val message = shareMessage + shareUrl + if (infoText != null && ! infoText.trim { it <= ' ' }.isEmpty()) "\n\n\n" + infoText else ""
            shareApp(context, appName, message, shareUrl)
        }
    }
}