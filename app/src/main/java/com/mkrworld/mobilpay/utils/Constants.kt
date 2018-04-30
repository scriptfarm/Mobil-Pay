package com.mkrworld.mobilpay.utils

import android.app.AlarmManager

/**
 * Created by mkr on 15/3/18.
 */

interface Constants {
    companion object {
        val CHANNEL_ID = "com.mkrworld.mobilpay"
        val AUTO_LOGOUT_INTERVAL = AlarmManager.INTERVAL_HALF_DAY
        val MAX_SMS_READ_INTERVAL = AlarmManager.INTERVAL_DAY * 2
        val MIN_TRANSACTION_AMOUNT = 10
        val PAYMENT_TYPE_FULL = "FULL"
        val PAYMENT_TYPE_PARTIAL = "PARTIAL"

        val USER_TYPE_MERCHANT = "M"
        val USER_TYPE_AGENT = "A"
    }
}
