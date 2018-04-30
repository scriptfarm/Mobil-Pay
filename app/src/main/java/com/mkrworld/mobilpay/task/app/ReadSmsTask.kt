package com.mkrworld.mobilpay.task.app

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.provider.Telephony
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.appdata.MessageData
import com.mkrworld.mobilpay.utils.PreferenceData
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by mkr on 30/4/18.
 */
class ReadSmsTask : AsyncTask<Void, Void, ArrayList<MessageData>> {

    companion object {
        val TAG : String = BuildConfig.BASE_TAG + ".ReadSmsTask"
    }

    private val mOnMessageFilteredListener : OnMessageFilteredListener
    private val mContext : Context

    /**
     * Constructor
     * @param context
     * @param onMessageFilteredListener
     */
    constructor(context : Context, onMessageFilteredListener : OnMessageFilteredListener) {
        mContext = context
        mOnMessageFilteredListener = onMessageFilteredListener
    }


    override fun doInBackground(vararg p0 : Void?) : ArrayList<MessageData> {
        var messageDataList : ArrayList<MessageData> = ArrayList<MessageData>()
        try {
            val cursor = mContext.contentResolver.query(Uri.parse("content://sms/inbox"), null, "date>=?", arrayOf("" + PreferenceData.getSmsReadTime(mContext)), "date ASC")
            Tracer.debug(TAG, "doInBackground() " + cursor.count)
            if (cursor.count > 0) {
                try {
                    val bodyId = cursor.getColumnIndex(Telephony.Sms.BODY)
                    val originateAddressId = cursor.getColumnIndex(Telephony.Sms.ADDRESS)
                    val id = cursor.getColumnIndex(Telephony.Sms._ID)
                    while (cursor.moveToNext()) {
                        try {
                            val locale = Locale.getDefault()
                            var sender = cursor.getString(originateAddressId)
                            if (sender != null && sender.trim().toUpperCase(locale).contains("TESTIN")) {
                                val body = cursor.getString(bodyId).replace("\n", "").replace("\r", "")
                                if (body.toUpperCase(locale).contains("CONFIGURABLE")) {
                                    val id = cursor.getString(id)
                                    messageDataList.add(MessageData(id, body))
                                }
                            }
                        } catch (e : Exception) {
                            e.printStackTrace()
                            Tracer.error(TAG, "doInBackground(1) " + e.message)
                        }

                    }
                } catch (e : Exception) {
                    e.printStackTrace()
                    Tracer.error(TAG, "doInBackground(2) " + e.message)
                }

            }
            cursor.close()
        } catch (e : Exception) {
            e.printStackTrace()
            Tracer.error(TAG, "doInBackground(3) " + e.message)
        }
        return messageDataList
    }

    override fun onPostExecute(result : ArrayList<MessageData>?) {
        super.onPostExecute(result)
        mOnMessageFilteredListener?.onMessageFiltered(result ?: ArrayList<MessageData>())
    }

    /**
     * This listener is used to get the status at the time of extracting the
     * valid sms from user inbox<br></br>
     * When all the valid sms are extracted, then
     * **onMessageFiltered(ArrayList<MessageData> mssageDataList)</MessageData>** is
     * invoked, where **mssageDataList** is the list of all the valid sms
     */
    interface OnMessageFilteredListener {

        /**
         * Method to notify that all the valid sms that are in user inbox is
         * extracted and listed in a sequence.
         *
         * @param mssageDataList Map of all valid sms from the user inbox correspond to the
         * vendor name
         */
        fun onMessageFiltered(messageDataList : ArrayList<MessageData>)
    }
}