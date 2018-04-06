package com.mkrworld.biometric.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.widget.Toast


/**
 * Created by mkr on 6/4/18.
 */
class USBConnectionReceiver : BroadcastReceiver() {

    override fun onReceive(context : Context?, intentReceived : Intent?) {
        if(intentReceived==null){
            return
        }
        try{
            if (intentReceived.getAction().equals("android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
                Toast.makeText(context, "USB success : Connect : 1", Toast.LENGTH_LONG).show()
                Toast.makeText(context, "USB success : Connect : 2 "+intentReceived.getParcelableExtra("device"), Toast.LENGTH_LONG).show()
                val localUsbDevice = intentReceived.getParcelableExtra("device") as UsbDevice
                Toast.makeText(context, "USB success : Connect : 3", Toast.LENGTH_LONG).show()
            } else if (intentReceived.getAction().equals("android.hardware.usb.action.USB_DEVICE_DETACHED")) {
                val localUsbDevice = intentReceived.getParcelableExtra("device") as UsbDevice
            }
        }catch (e:Exception){
            Toast.makeText(context, "USB error : ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

}