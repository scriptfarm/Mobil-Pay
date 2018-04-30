package com.mkrworld.mobilpay.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.ui.activity.MainActivity
import com.mkrworld.mobilpay.utils.Constants


/**
 * Created by mkr on 30/4/18.
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        val TAG : String = BuildConfig.BASE_TAG + ".MyFirebaseMessagingService"
    }

    override fun onMessageReceived(remoteMessage : RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        Tracer.debug(TAG, "onMessageReceived : ")
        if (remoteMessage !!.getNotification() != null) {
            sendNotification(remoteMessage !!.notification !!.title, remoteMessage !!.notification !!.body);
        }
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private fun sendNotification(title : String?, messageBody : String?) {
        Tracer.debug(TAG, "sendNotification: $messageBody")
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, Constants.CHANNEL_ID).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title
                ?: getString(R.string.app_name)).setContentText(messageBody
                ?: "MESSAGE IS NULL").setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }

}