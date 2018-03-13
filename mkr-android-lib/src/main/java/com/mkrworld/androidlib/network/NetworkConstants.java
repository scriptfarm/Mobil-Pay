package com.mkrworld.androidlib.network;

/**
 * Created by A1ZFKXA3 on 1/30/2018.
 */

public interface NetworkConstants {

    /**
     * ENUM to hold the Type of API Call
     */
    enum RequestType {
        GET, POST, DELETE
    }

    long SOCKET_TIME_OUT = 60000;

    int MAX_RETRY = 3;
}
