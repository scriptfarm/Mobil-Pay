package com.mkrworld.androidlib.network

import android.content.Context
import android.text.TextUtils
import com.mkrworld.androidlib.BuildConfig
import com.mkrworld.androidlib.utils.Tracer
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


/**
 * Created by MKR on 8/28/15.
 */
class FileHelper {

    companion object {

        private val TAG = BuildConfig.BASE_TAG + ".FileHelper"

        fun getAssetFileJson(context : Context, fileName : String) : JSONObject? {
            Tracer.debug(TAG, "getAssetFileJson: $fileName")
            if (fileName==null || TextUtils.isEmpty(fileName)) return null
            try {
                return parseInputStreamToJson(InputStreamReader(context.assets.open(fileName)))
            } catch (e : IOException) {
                Tracer.error(TAG, "getAssetFileJson: " + e.message)
            }

            return null
        }

        /**
         * Method to parse the Input stream to the Json
         *
         * @param isr
         * @return
         */
        internal fun parseInputStreamToJson(isr : InputStreamReader?) : JSONObject? {
            Tracer.debug(TAG, "parseInputStreamToJson: ")
            var data : JSONObject? = null
            if (isr == null) return data

            var reader : BufferedReader? = null
            try {
                reader = BufferedReader(isr)
                var line : String
                val builder = StringBuilder()
                while (true) {
                    line = reader.readLine()
                    if (line == null) {
                        break
                    }
                    builder.append(line)
                }
                try {
                    data = JSONObject(builder.toString())
                } catch (e : JSONException) {
                    Tracer.error(TAG, "parseInputStreamToJson: " + e.message)
                }

                return data

            } catch (e : IOException) {
                return data

            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (e : IOException) {
                        Tracer.error(TAG, "parseInputStreamToJson: " + e.message)
                    }

                }
            }
        }
    }
}