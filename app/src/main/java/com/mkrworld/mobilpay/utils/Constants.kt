package com.mkrworld.mobilpay.utils

/**
 * Created by mkr on 15/3/18.
 */

interface Constants {
    companion object {
        val MIN_TRANSACTION_AMOUNT = 10
        val PAYMENT_TYPE_FULL = "FULL"
        val PAYMENT_TYPE_PARTIAL = "PARTIAL"

        val USER_TYPE_MERCHANT = "M"
        val USER_TYPE_AGENT = "A"
    }
}
