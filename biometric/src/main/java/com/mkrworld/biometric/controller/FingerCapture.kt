package com.mkrworld.biometric.controller

import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.widget.Toast
import com.mkrworld.biometric.b
import com.mkrworld.biometric.callback.CallBackInterface
import com.mkrworld.biometric.data.PIDBean
import com.mkrworld.biometric.ui.FingerPrintActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by mkr on 6/4/18.
 */
class FingerCapture {
    companion object {
        private val TAG = "fingerlibrary >> Finger"
        private var fingerCapture : FingerCapture? = null

        /**
         * Method to get the Instance of this Class
         */
        fun getInstance() : FingerCapture {
            if (fingerCapture == null) {
                fingerCapture = FingerCapture()
            }
            return fingerCapture !!
        }
    }

    var version = "2.0"
    var wadh = "rhVuL7SnJi2W2UmsyukVqY7c93JWyL9O/kVKgdNMfv8="
    var environment = "P"
    var isKYC = false
    var mCallBackInterface : CallBackInterface? = null
    private var mManager : UsbManager? = null
    private var mContext : Context? = null
    private var mShowClassLog = true

    /**
     * Method to capture the Bio Metric of the User
     */
    fun capture(paramContext : Context, paramCallBackInterface : CallBackInterface?, paramString : String, paramBoolean : Boolean) {
        mCallBackInterface = paramCallBackInterface;
        mContext = paramContext;
        this.environment = paramString;
        this.isKYC = paramBoolean;
        try {
            if (b.c) {
                var localFile = File("/data/local/tmp", "zLg3iULYpHOPYbYn6DG9");
                if (localFile.exists()) {
                    try {
                        mCallBackInterface?.pidData(convertHashMapToPIDBean(), 0, "80");
                        return;
                    } catch (e : Exception) {
                        Toast.makeText(paramContext, "FingerPrint capture 1 : " + e.message, Toast.LENGTH_LONG).show()
                        return;
                    }
                }
            }
        } catch (e : Exception) {
            Toast.makeText(paramContext, "FingerPrint capture 2 : " + e.message, Toast.LENGTH_LONG).show()
        }
        if (this.mManager == null) {
            this.mManager = mContext !!.getSystemService(Context.USB_SERVICE) as UsbManager;
        }
        try {
            findDevice(paramContext);
            var intent = Intent(paramContext, FingerPrintActivity::class.java)
            intent.setFlags(67108864 !!)
            paramContext.startActivity(intent)
        } catch (e : Exception) {
            Toast.makeText(paramContext, "FingerPrint capture 3 : " + e.message, Toast.LENGTH_LONG).show()
        }
    }

    fun convertHashMapToPIDBean() : PIDBean {
        val localPIDBean = PIDBean()
        localPIDBean.serviceType = "2"
        localPIDBean.hmac = "dummyhmac"
        localPIDBean.skey = "dummyskey"
        localPIDBean.pid = "dummyPID"
        localPIDBean.udc = "dummyUDC"
        localPIDBean.ci = "dummyci"
        localPIDBean.registeredDeviceModelID = "dummymi"
        localPIDBean.registeredDeviceProviderCode = "dummy"
        localPIDBean.registeredDevicePublicKeyCertificate = "dummy"
        localPIDBean.registeredDeviceServiceID = "dummy"
        localPIDBean.registeredDeviceServiceVersion = "dummy"
        localPIDBean.registeredDeviceCode = "dummy"
        val localSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val l = System.currentTimeMillis()
        val localDate = Date(l)
        localPIDBean.pidTs = localSimpleDateFormat.format(localDate).replace(" ", "T")
        return localPIDBean
    }

    private fun findDevice(context : Context) {
        Toast.makeText(context, "FingerPrint Find 1 Product ", Toast.LENGTH_LONG).show()
        try {
            val localHashMap = this.mManager !!.getDeviceList()
            val keys = localHashMap.keys
            for (key in keys) {
                if (localHashMap.get(key) is UsbDevice) {
                    var device : UsbDevice? = localHashMap.get(key)
                    val i = device?.productId
                    val j = device?.vendorId
                    Toast.makeText(context, "FingerPrint Find 2 Product Id : " + i + "    Vendor Id : " + j, Toast.LENGTH_LONG).show()
                }
            }
        } catch (localException : Exception) {
            Toast.makeText(context, "FingerPrint Find 1 Product Error " + localException.message, Toast.LENGTH_LONG).show()
        }

    }
}