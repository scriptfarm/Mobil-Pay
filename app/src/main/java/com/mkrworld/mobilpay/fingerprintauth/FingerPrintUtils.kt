package com.mkrworld.mobilpay.fingerprintauth

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat

class FingerPrintUtils {
    companion object {

        /**
         * Open the Security settings screen.
         *
         * @param context instance of the caller.
         */
        fun openSecuritySettings(context : Context) {
            val intent = Intent(Settings.ACTION_SECURITY_SETTINGS)
            context.startActivity(intent)
        }

        /**
         * Check if the device have supported hardware fir the finger print scanner.
         *
         * @param context instance of the caller.
         * @return true if device have the hardware.
         */
        fun isSupportedHardware(context : Context) : Boolean {
            val fingerprintManager = FingerprintManagerCompat.from(context)
            return fingerprintManager.isHardwareDetected
        }
    }
}
