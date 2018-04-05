package com.mkrworld.androidlib.chromium

/**
 * Created by a1zfkxa3 on 2/6/2018.
 */
// Copyright 2015 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.customtabs.CustomTabsIntent
import android.text.TextUtils
import android.util.Log
import com.mkrworld.androidlib.BuildConfig
import java.util.*

/**
 * Helper class for Custom Tabs.
 */
class CustomWebHelper {
    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".CustomWebHelper"
        private val STABLE_PACKAGE = "com.android.chrome"
        private val BETA_PACKAGE = "com.chrome.beta"
        private val DEV_PACKAGE = "com.chrome.dev"
        private val LOCAL_PACKAGE = "com.google.android.apps.chrome"
        private val EXTRA_CUSTOM_TABS_KEEP_ALIVE = "android.support.customtabs.extra.KEEP_ALIVE"
        private val ACTION_CUSTOM_TABS_CONNECTION = "android.support.customtabs.action.CustomTabsService"
        private var sPackageNameToUse : String? = null

        /**
         * |
         * Method to keep alive the Connection
         *
         * @param context
         * @param intent
         */
        fun addKeepAliveExtra(context : Context, intent : Intent) {
            val keepAliveIntent = Intent().setClassName(context.packageName, KeepAliveService::class.qualifiedName)
            intent?.putExtra(EXTRA_CUSTOM_TABS_KEEP_ALIVE, keepAliveIntent)
        }

        /**
         * @return All possible chrome package names that provide custom tabs feature.
         */
        val packages : Array<String>
            get() = arrayOf("", STABLE_PACKAGE, BETA_PACKAGE, DEV_PACKAGE, LOCAL_PACKAGE)


        /**
         * Goes through all apps that handle VIEW intents and have a warmup service. Picks
         * the one chosen by the user if there is one, otherwise makes a best effort to return a
         * valid package name.
         *
         *
         * This is **not** threadsafe.
         *
         * @param context [Context] to use for accessing [PackageManager].
         * @return The package name recommended to use for connecting to custom tabs related components.
         */
        fun getPackageNameToUse(context : Context) : String? {
            if (sPackageNameToUse != null) return sPackageNameToUse

            val pm = context.packageManager
            // Get default VIEW intent handler.
            val activityIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.example.com"))
            val defaultViewHandlerInfo = pm.resolveActivity(activityIntent, 0)
            var defaultViewHandlerPackageName : String? = null
            if (defaultViewHandlerInfo != null) {
                defaultViewHandlerPackageName = defaultViewHandlerInfo.activityInfo.packageName
            }

            // Get all apps that can handle VIEW intents.
            val resolvedActivityList = pm.queryIntentActivities(activityIntent, 0)
            val packagesSupportingCustomTabs = ArrayList<String>()
            for (info in resolvedActivityList) {
                val serviceIntent = Intent()
                serviceIntent.action = ACTION_CUSTOM_TABS_CONNECTION
                serviceIntent.`package` = info.activityInfo.packageName
                if (pm.resolveService(serviceIntent, 0) != null) {
                    packagesSupportingCustomTabs.add(info.activityInfo.packageName)
                }
            }

            // Now packagesSupportingCustomTabs contains all apps that can handle both VIEW intents
            // and service calls.
            if (packagesSupportingCustomTabs.isEmpty()) {
                sPackageNameToUse = null
            } else if (packagesSupportingCustomTabs.size == 1) {
                sPackageNameToUse = packagesSupportingCustomTabs[0]
            } else if (! TextUtils.isEmpty(defaultViewHandlerPackageName) && ! hasSpecializedHandlerIntents(context, activityIntent) && packagesSupportingCustomTabs.contains(defaultViewHandlerPackageName!!)) {
                sPackageNameToUse = defaultViewHandlerPackageName
            } else if (packagesSupportingCustomTabs.contains(STABLE_PACKAGE)) {
                sPackageNameToUse = STABLE_PACKAGE
            } else if (packagesSupportingCustomTabs.contains(BETA_PACKAGE)) {
                sPackageNameToUse = BETA_PACKAGE
            } else if (packagesSupportingCustomTabs.contains(DEV_PACKAGE)) {
                sPackageNameToUse = DEV_PACKAGE
            } else if (packagesSupportingCustomTabs.contains(LOCAL_PACKAGE)) {
                sPackageNameToUse = LOCAL_PACKAGE
            }
            return sPackageNameToUse
        }

        /**
         * Used to check whether there is a specialized handler for a given intent.
         *
         * @param intent The intent to check with.
         * @return Whether there is a specialized handler for the given intent.
         */
        private fun hasSpecializedHandlerIntents(context : Context, intent : Intent) : Boolean {
            try {
                val pm = context.packageManager
                val handlers = pm.queryIntentActivities(intent, PackageManager.GET_RESOLVED_FILTER)
                if (handlers == null || handlers.size == 0) {
                    return false
                }
                for (resolveInfo in handlers) {
                    val filter = resolveInfo.filter ?: continue
                    if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) continue
                    if (resolveInfo.activityInfo == null) continue
                    return true
                }
            } catch (e : RuntimeException) {
                Log.e(TAG, "Runtime exception while getting specialized handlers")
            }

            return false
        }


        /**
         * Opens the URL on a Custom FireBaseNewsCategory if possible. Otherwise fallsback to opening it on a WebView
         *
         * @param context          The host activity
         * @param customTabsIntent a CustomTabsIntent to be used if Custom Tabs is available
         * @param uri              the Uri to be opened
         * @param fallback         a CustomWebFallback to be used if Custom Tabs is not available
         */
        fun openCustomWeb(context : Activity, customTabsIntent : CustomTabsIntent, uri : Uri, fallback : CustomWebFallback?) {
            val packageName = CustomWebHelper.getPackageNameToUse(context)
            if (packageName == null) {
                fallback?.openUri(context, uri)
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    customTabsIntent.intent.putExtra(Intent.EXTRA_REFERRER, Uri.parse(Intent.URI_ANDROID_APP_SCHEME.toString() + "//" + context.packageName))
                }
                customTabsIntent.intent.`package` = packageName
                customTabsIntent.launchUrl(context, uri)
            }
        }

        /**
         * method to open custom chrome
         *
         * @param context
         * @param url     pass the url which need to be open
         */
        fun openCustomChrome(context : Context, url : String) {
            // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
            val builder = CustomTabsIntent.Builder()
            // set toolbar color and/or setting custom actions before invoking build()
            // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
            val customTabsIntent = builder.build()
            // and launch the desired Url with CustomTabsIntent.launchUrl()
            customTabsIntent.launchUrl(context, Uri.parse(url))
        }
    }

    /**
     * To be used as a fallback to open the Uri when Custom Tabs is not available
     */
    interface CustomWebFallback {
        /**
         * @param context The Activity that wants to open the Uri
         * @param uri     The uri to be opened by the fallback
         */
        fun openUri(context : Context, uri : Uri)
    }

    /**
     * Empty service used by the custom tab to bind to, raising the application's importance.
     */
    class KeepAliveService : Service() {

        override fun onBind(intent : Intent) : IBinder? {
            return sBinder
        }

        companion object {
            private val sBinder = Binder()
        }
    }
}