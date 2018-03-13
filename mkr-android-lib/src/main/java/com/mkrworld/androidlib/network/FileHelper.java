package com.mkrworld.androidlib.network;

import android.content.Context;
import android.text.TextUtils;

import com.mkrworld.androidlib.BuildConfig;
import com.mkrworld.androidlib.utils.Tracer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by MKR on 8/28/15.
 */
public class FileHelper {
    private static final String TAG = BuildConfig.BASE_TAG + ".FileHelper";

    public static JSONObject getAssetFileJson(Context context, String fileName) {
        Tracer.debug(TAG, "getAssetFileJson: " + fileName);
        if (TextUtils.isEmpty(fileName)) return null;
        try {
            JSONObject assetJson = parseInputStreamToJson(new InputStreamReader(context.getAssets().open(fileName)));
            return assetJson;

        } catch (IOException e) {
            Tracer.error(TAG, "getAssetFileJson: " + e.getMessage());
        }
        return null;
    }

    /**
     * Method to parse the Input stream to the Json
     *
     * @param isr
     * @return
     */
    static JSONObject parseInputStreamToJson(InputStreamReader isr) {
        Tracer.debug(TAG, "parseInputStreamToJson: ");
        JSONObject data = null;
        if (isr == null) return data;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            try {
                data = new JSONObject(builder.toString());
            } catch (JSONException e) {
                Tracer.error(TAG, "parseInputStreamToJson: " + e.getMessage());
            }

            return data;

        } catch (IOException e) {
            return data;

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Tracer.error(TAG, "parseInputStreamToJson: " + e.getMessage());
                }
            }
        }
    }
}