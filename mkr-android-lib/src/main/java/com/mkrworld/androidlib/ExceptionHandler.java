package com.mkrworld.androidlib;

import android.content.Context;

import com.mkrworld.androidlib.utils.Tracer;


/**
 * Created by A1ZFKXA3 on 1/24/2017.
 */

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = BuildConfig.BASE_TAG + ".ExceptionHandler";
    private Object mObject;
    private ExceptionHandlerListener mExceptionHandlerListener;

    private ExceptionHandler(Object object, ExceptionHandlerListener exceptionHandlerListener) {
        mObject = object;
        mExceptionHandlerListener = exceptionHandlerListener;
    }

    /**
     * Method to attach the Exception Handler
     *
     * @param caller
     * @param exceptionHandlerListener
     */
    public static final void attachExceptionHandler(Object caller, ExceptionHandlerListener exceptionHandlerListener) {
        Tracer.debug(TAG, "attachExceptionHandler: ");
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(caller, exceptionHandlerListener));
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        throwable.printStackTrace();
        if (mObject instanceof Context) {
            try {
                Tracer.error(TAG, "Exception Handler : " + ((Context) mObject).getPackageName() + " : " + throwable.getMessage());
            } catch (Exception e) {
                Tracer.error(TAG, "uncaughtException: Context: " + e.getMessage());
            }
        }
        if (mExceptionHandlerListener != null) {
            mExceptionHandlerListener.uncaughtException(throwable);
        }
        System.exit(0);
    }

    /**
     * Callback to notifyTaskResponse the Exception
     */
    public interface ExceptionHandlerListener {

        /**
         * Method to notifyTaskResponse the exception
         *
         * @param throwable
         */
        public void uncaughtException(Throwable throwable);
    }
}
