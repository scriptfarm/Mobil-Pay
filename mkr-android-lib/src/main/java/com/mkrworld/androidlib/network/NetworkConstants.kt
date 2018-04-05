package com.mkrworld.androidlib.network

/**
 * Created by A1ZFKXA3 on 1/30/2018.
 */

interface NetworkConstants {

    /**
     * ENUM to hold the Type of API Call
     */
    enum class RequestType {
        GET, POST, DELETE
    }

    companion object {

        val SOCKET_TIME_OUT : Long = 60000

        val MAX_RETRY = 3
    }
}
