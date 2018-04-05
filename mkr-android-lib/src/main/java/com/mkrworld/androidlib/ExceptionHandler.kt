package com.mkrworld.androidlib

import android.content.Context

import com.mkrworld.androidlib.utils.Tracer


/**
 * Created by A1ZFKXA3 on 1/24/2017.
 */

class ExceptionHandler : Thread.UncaughtExceptionHandler {
    private var mAny : Any? = null
    private var mExceptionHandlerListener : ExceptionHandlerListener? = null

    /**
     * Constructor
     * @param any
     * @param exceptionHandlerListener
     */
    private constructor(any : Any, exceptionHandlerListener : ExceptionHandlerListener?) {
        mAny = any
        mExceptionHandlerListener = exceptionHandlerListener
    }

    override fun uncaughtException(thread : Thread, throwable : Throwable) {
        throwable.printStackTrace()
        if (mAny is Context) {
            try {
                Tracer.error(TAG, "Exception Handler : " + (mAny as Context).packageName + " : " + throwable.message)
            } catch (e : Exception) {
                Tracer.error(TAG, "uncaughtException: Context: " + e.message)
            }

        }
        mExceptionHandlerListener?.uncaughtException(throwable)
        System.exit(0)
    }

    /**
     * Callback to notifyTaskResponse the Exception
     */
    interface ExceptionHandlerListener {

        /**
         * Method to notifyTaskResponse the exception
         *
         * @param throwable
         */
        fun uncaughtException(throwable : Throwable)
    }

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".ExceptionHandler"

        /**
         * Method to attach the Exception Handler
         *
         * @param caller
         * @param exceptionHandlerListener
         */
        fun attachExceptionHandler(caller : Any, exceptionHandlerListener : ExceptionHandlerListener) {
            Tracer.debug(TAG, "attachExceptionHandler: ")
            Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler(caller, exceptionHandlerListener))
        }
    }
}
