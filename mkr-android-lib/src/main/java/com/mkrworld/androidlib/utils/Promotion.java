package com.mkrworld.androidlib.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.mkrworld.androidlib.BuildConfig;

public class Promotion {
    private static final String TAG = BuildConfig.BASE_TAG + ".Promotion";

    /**
     * Method to send review
     *
     * @param context
     */
    public static void sendReview(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName()));
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Tracer.error(TAG, "sendReview() " + e.getMessage());
        }
    }

    /**
     * Method to open downloading
     *
     * @param context
     * @param url
     */
    public static void openDownloadUrl(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Tracer.error(TAG, "sendReview() " + e.getMessage());
        }
    }

    /**
     * Method to get more apps
     *
     * @param context
     */
    public static void getMoreApps(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:MKR WORLD"));
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Tracer.error(TAG, "getMoreApps() " + e.getMessage());
        }
    }

    /**
     * Method top send feedback
     *
     * @param context
     * @param appName
     */
    public static void sendFeedback(Context context, String appName) {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mkrworldapps@gmail.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, appName);
            emailIntent.putExtra(Intent.EXTRA_TEXT, "");
            emailIntent.setType("message/rfc822");
            emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(emailIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Tracer.error(TAG, "sendFeedback() " + e.getMessage());
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
    public static void shareApp(Context context, String appName, String shareMessage, String shareUrl) {
        String message = shareMessage + "\n\n\n\n" + shareUrl;
        Intent sentIntent = new Intent(Intent.ACTION_SEND);
        sentIntent.setType("text/plain");
        sentIntent.putExtra(Intent.EXTRA_SUBJECT, appName);
        sentIntent.putExtra(Intent.EXTRA_TEXT, message);
        Intent sendIntent = Intent.createChooser(sentIntent, "Share File");
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(sendIntent);
    }

    /**
     * Method to share application
     *
     * @param context
     * @param appName
     * @param shareMessage
     */
    public static void shareApp(Context context, String appName, String shareMessage) {
        shareApp(context, appName, shareMessage, "http://play.google.com/store/apps/details?id=" + context.getPackageName());
    }

    /**
     * Method to share text
     *
     * @param context
     * @param appName
     * @param shareMessage
     * @param infotext
     */
    public static void shareText(Context context, String appName, String shareMessage, String infotext) {
        shareText(context, appName, shareMessage, infotext, "http://play.google.com/store/apps/details?id=" + context.getPackageName());
    }

    /**
     * Method to share text
     *
     * @param appName
     * @param shareMessage
     * @param infoText
     * @param shareUrl
     */
    public static void shareText(Context context, String appName, String shareMessage, String infoText, String shareUrl) {
        String message = shareMessage + shareUrl + ((infoText != null && !infoText.trim().isEmpty()) ? "\n\n\n" + infoText : "");
        shareApp(context, appName, message, shareUrl);
    }
}
