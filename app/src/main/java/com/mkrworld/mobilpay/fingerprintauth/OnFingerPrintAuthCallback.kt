package com.mkrworld.mobilpay.fingerprintauth

import android.content.Context
import android.hardware.fingerprint.FingerprintManager


interface OnFingerPrintAuthCallback {

    /**
     * This method will notify the user whenever there is no finger print hardware found on the device.
     * Developer should use any other way of authenticating the user, like pin or password to authenticate the user.
     */
    fun onFingerPrintAuthNoFingerPrintHardwareFound()

    /**
     * This method will execute whenever device supports finger print authentication but does not
     * have any finger print registered.
     * Developer should notify user to add finger prints in the settings by opening security settings
     * by using [FingerPrintUtils.openSecuritySettings].
     */
    fun onFingerPrintAuthNoFingerPrintRegistered()

    /**
     * This method will be called if the device is running on android below API 23. As starting from the
     * API 23, android officially got the finger print hardware support, for below marshmallow devices
     * developer should authenticate user by other ways like pin, password etc.
     */
    fun onFingerPrintAuthBelowMarshmallow()

    /**
     * This method will occur whenever  user authentication is successful.
     *
     * @param cryptoObject [FingerprintManager.CryptoObject] associated with the scanned finger print.
     */
    fun onFingerPrintAuthSuccess(cryptoObject : FingerprintManager.CryptoObject)

    /**
     * This method will execute whenever any error occurs during the authentication.
     *
     * @param errorCode    Error code for the error occurred.
     * @param errorMessage A human-readable error string that can be shown in UI
     */
    fun onFingerPrintAuthFailed(errorCode : Int, errorMessage : String)

    companion object {

        /**
         * Called when a recoverable error has been encountered during authentication.
         * The help string is provided to give the user guidance for what went wrong, such as "Sensor dirty, please clean it."
         * This error can be fixed by the user. Developer should display the error message to the screen to guide
         * user how to fix the error.
         *
         * See:'https://developer.android.com/reference/android/hardware/fingerprint/FingerprintManager.AuthenticationCallback.html#onAuthenticationHelp(int, java.lang.CharSequence)'
         */
        val RECOVERABLE_ERROR = 843

        /**
         * Called when an unrecoverable error has been encountered and the operation is complete.
         * No further callbacks will be made on this object.
         * Developer can stop the finger print scanning whenever this error occur and display the message received in callback.
         * Developer should use any other way of authenticating the user, like pin or password to authenticate the user.
         *
         * See:'https://developer.android.com/reference/android/hardware/fingerprint/FingerprintManager.AuthenticationCallback.html#onAuthenticationError(int, java.lang.CharSequence)'
         */
        val NON_RECOVERABLE_ERROR = 566

        /**
         * Called when a fingerprint is valid but not recognized.
         *
         * See:'https://developer.android.com/reference/android/hardware/fingerprint/FingerprintManager.AuthenticationCallback.html#onAuthenticationError(int, java.lang.CharSequence)'
         */
        val CANNOT_RECOGNIZE_ERROR = 456
    }
}
