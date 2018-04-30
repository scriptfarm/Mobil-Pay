package com.mkrworld.mobilpay.service

import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Created by mkr on 30/4/18.
 */
class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        super.onTokenRefresh()
    }

}