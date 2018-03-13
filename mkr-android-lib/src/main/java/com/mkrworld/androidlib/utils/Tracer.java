package com.mkrworld.androidlib.utils;

import android.content.Context;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.mkrworld.androidlib.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by delhivery on 21/3/16.
 */
public class Tracer {
    private static final boolean LOG_ENABLE = BuildConfig.SHOW_LOG;

    /**
     * Method to print debug log<br>
     * {@link Log} Information
     *
     * @param TAG
     * @param message
     */
    public static void debug(String TAG, String message) {
        if (LOG_ENABLE) {
            Log.d(TAG, message);
        }
    }

    /**
     * Method to write data in Disk
     *
     * @param TAG
     * @param num
     * @param caller
     * @param data
     */
    public static void writeOnDisk(String TAG, int num, String caller, String data) {
        if (LOG_ENABLE) {
            Tracer.debug(TAG, "Tracer.writeOnDisk() " + caller);
            File fileDir = new File(Environment.getExternalStorageDirectory(), "CTA");
            Tracer.debug(TAG, "Tracer.writeOnDisk() " + fileDir.getAbsolutePath());
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            File file = new File(fileDir.getAbsolutePath(), "" + caller + "_" + num + "_" + System.currentTimeMillis() + ".txt");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            BufferedWriter bufferedWriter = null;
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.write(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Method to print error log<br>
     * {@link Log} Error
     *
     * @param TAG
     * @param message
     */
    public static void error(String TAG, String message) {
        if (LOG_ENABLE) {
            Log.e(TAG, message);
        }
    }

    /**
     * Method to print information log<br>
     * {@link Log} Debug
     *
     * @param TAG
     * @param message
     */
    public static void info(String TAG, String message) {
        if (LOG_ENABLE) {
            Log.i(TAG, message);
        }
    }

    /**
     * Show TOAST<br>
     *
     * @param context activity context.
     * @param text    The text to show.  Can be formatted text.
     */
    public static void showToast(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * Show SNACK<br>
     *
     * @param view The view to find NewsAdapterItemHandler mParent from.
     * @param text The text to show.  Can be formatted text.
     */
    public static void showSnack(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Show SNACK<br>
     *
     * @param view      The view to find NewsAdapterItemHandler mParent from.
     * @param textResId The text Res Id to show.
     */
    public static void showSnack(View view, int textResId) {
        Snackbar.make(view, textResId, Snackbar.LENGTH_LONG).show();
    }
}
