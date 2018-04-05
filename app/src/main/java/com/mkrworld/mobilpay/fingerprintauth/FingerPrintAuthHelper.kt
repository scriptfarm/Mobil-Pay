package com.mkrworld.mobilpay.fingerprintauth

import android.annotation.TargetApi
import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.support.annotation.RequiresApi
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat

import java.io.IOException
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.UnrecoverableKeyException
import java.security.cert.CertificateException
import java.util.UUID

import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey


class FingerPrintAuthHelper {

    private var mKeyStore : KeyStore? = null
    private var mCipher : Cipher? = null

    /**
     * Instance of the caller class.
     */
    private var mContext : Context?=null

    /**
     * [OnFingerPrintAuthCallback] to notify the parent caller about the authentication status.
     */
    private var mCallback : OnFingerPrintAuthCallback?=null

    /**
     * [CancellationSignal] for finger print authentication.
     */
    private var mCancellationSignal : CancellationSignal? = null

    /**
     * Boolean to know if the finger print scanning is currently enabled.
     */
    /**
     * @return true if currently listening the for the finger print.
     */
    var isScanning : Boolean = false
        private set

    private val cryptoObject : FingerprintManager.CryptoObject?
        @TargetApi(23) get() = if (cipherInit()) FingerprintManager.CryptoObject(mCipher !!) else null

    /**
     * Private constructor.
     */
    private constructor(context : Context, callback : OnFingerPrintAuthCallback) {
        mCallback = callback
        mContext = context
    }

    /**
     * Private constructor.
     */
    private constructor() {
        throw RuntimeException("Use getHelper() to initialize FingerPrintAuthHelper.")
    }

    /**
     * Check if the finger print hardware is available.
     *
     * @param context instance of the caller.
     * @return true if finger print authentication is supported.
     */
    private fun checkFingerPrintAvailability(context : Context) : Boolean {
        // Check if we're running on Android 6.0 (M) or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //Fingerprint API only available on from Android 6.0 (M)
            val fingerprintManager = FingerprintManagerCompat.from(context)

            if (! fingerprintManager.isHardwareDetected) {

                // Device doesn't support fingerprint authentication
                mCallback!!.onFingerPrintAuthNoFingerPrintHardwareFound()
                return false
            } else if (! fingerprintManager.hasEnrolledFingerprints()) {

                // User hasn't enrolled any fingerprints to authenticate with
                mCallback!!.onFingerPrintAuthNoFingerPrintRegistered()
                return false
            }
            return true
        } else {
            mCallback!!.onFingerPrintAuthBelowMarshmallow()
            return false
        }
    }

    /**
     * Generate authentication key.
     *
     * @return true if the key generated successfully.
     */
    @TargetApi(23)
    private fun generateKey() : Boolean {
        mKeyStore = null
        val keyGenerator : KeyGenerator

        //Get the instance of the key store.
        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore")
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        } catch (e : NoSuchAlgorithmException) {
            return false
        } catch (e : NoSuchProviderException) {
            return false
        } catch (e : KeyStoreException) {
            return false
        }

        //generate key.
        try {
            mKeyStore !!.load(null)
            keyGenerator.init(KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_CBC).setUserAuthenticationRequired(true).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build())
            keyGenerator.generateKey()

            return true
        } catch (e : NoSuchAlgorithmException) {
            return false
        } catch (e : InvalidAlgorithmParameterException) {
            return false
        } catch (e : CertificateException) {
            return false
        } catch (e : IOException) {
            return false
        }

    }

    /**
     * Initialize the cipher.
     *
     * @return true if the initialization is successful.
     */
    @TargetApi(23)
    private fun cipherInit() : Boolean {
        val isKeyGenerated = generateKey()

        if (! isKeyGenerated) {
            mCallback!!.onFingerPrintAuthFailed(OnFingerPrintAuthCallback.NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_GENERATE_KEY)
            return false
        }

        try {
            mCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7)
        } catch (e : NoSuchAlgorithmException) {
            mCallback!!.onFingerPrintAuthFailed(OnFingerPrintAuthCallback.NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_GENERATE_KEY)
            return false
        } catch (e : NoSuchPaddingException) {
            mCallback!!.onFingerPrintAuthFailed(OnFingerPrintAuthCallback.NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_GENERATE_KEY)
            return false
        }

        try {
            mKeyStore !!.load(null)
            val key = mKeyStore !!.getKey(KEY_NAME, null) as SecretKey
            mCipher !!.init(Cipher.ENCRYPT_MODE, key)
            return true
        } catch (e : KeyPermanentlyInvalidatedException) {
            mCallback!!.onFingerPrintAuthFailed(OnFingerPrintAuthCallback.NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_INIT_CHIPPER)
            return false
        } catch (e : KeyStoreException) {
            mCallback!!.onFingerPrintAuthFailed(OnFingerPrintAuthCallback.NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_INIT_CHIPPER)
            return false
        } catch (e : CertificateException) {
            mCallback!!.onFingerPrintAuthFailed(OnFingerPrintAuthCallback.NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_INIT_CHIPPER)
            return false
        } catch (e : UnrecoverableKeyException) {
            mCallback!!.onFingerPrintAuthFailed(OnFingerPrintAuthCallback.NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_INIT_CHIPPER)
            return false
        } catch (e : IOException) {
            mCallback!!.onFingerPrintAuthFailed(OnFingerPrintAuthCallback.NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_INIT_CHIPPER)
            return false
        } catch (e : NoSuchAlgorithmException) {
            mCallback!!.onFingerPrintAuthFailed(OnFingerPrintAuthCallback.NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_INIT_CHIPPER)
            return false
        } catch (e : InvalidKeyException) {
            mCallback!!.onFingerPrintAuthFailed(OnFingerPrintAuthCallback.NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_INIT_CHIPPER)
            return false
        }

    }

    /**
     * Start the finger print authentication by enabling the finger print sensor.
     * Note: Use this function in the onResume() of the activity/fragment. Never forget to call [.stopAuth]
     * in onPause() of the activity/fragment.
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun startAuth() {
        if (isScanning) stopAuth()

        //check if the device supports the finger print hardware?
        if (! checkFingerPrintAvailability(mContext!!)) return

        val fingerprintManager = mContext!!.getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager

        val cryptoObject = cryptoObject
        if (cryptoObject == null) {
            mCallback!!.onFingerPrintAuthFailed(OnFingerPrintAuthCallback.NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_INIT_CHIPPER)
        } else {
            mCancellationSignal = CancellationSignal()

            fingerprintManager.authenticate(cryptoObject, mCancellationSignal, 0, object : FingerprintManager.AuthenticationCallback() {
                override fun onAuthenticationError(errMsgId : Int, errString : CharSequence) {
                    mCallback!!.onFingerPrintAuthFailed(OnFingerPrintAuthCallback.NON_RECOVERABLE_ERROR, errString.toString())
                }

                override fun onAuthenticationHelp(helpMsgId : Int, helpString : CharSequence) {
                    mCallback!!.onFingerPrintAuthFailed(OnFingerPrintAuthCallback.RECOVERABLE_ERROR, helpString.toString())
                }

                override fun onAuthenticationFailed() {
                    mCallback!!.onFingerPrintAuthFailed(OnFingerPrintAuthCallback.CANNOT_RECOGNIZE_ERROR, "")
                }

                override fun onAuthenticationSucceeded(result : FingerprintManager.AuthenticationResult) {
                    mCallback!!.onFingerPrintAuthSuccess(result.cryptoObject)
                }
            }, null)
        }
    }

    /**
     * Stop the finger print authentication.
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    fun stopAuth() {
        if (mCancellationSignal != null) {
            isScanning = true
            mCancellationSignal !!.cancel()
            mCancellationSignal = null
        }
    }

    companion object {
        private val KEY_NAME = UUID.randomUUID().toString()

        //error messages
        private val ERROR_FAILED_TO_GENERATE_KEY = "15080811-E0DC-4F3E-B826-C799D3477967"
        private val ERROR_FAILED_TO_INIT_CHIPPER = "Nupaysvi6uEWaiqjHvx8B3lhnLuZBaDyEEAO1QEo4UboTRam4y0LPfdGk0oHjatgjKed9oxgrROXq03RaKs9WNoExVcrM5aXvnMwBaroQ7acCLGyPbfrfOJnnmhxwjrCplMtTXMZ2v6XAh2gZqligqM16wMpJQ7t0QxemsQIqs9OUvAdza7hMMfjwjwjXPjpuA55sbzsUdldWPI5hBxFUyGoRDTDEnOqhvJhMaKOIjq2X1SpuQ7Fxslj3ZuPfGgSNupay"

        /**
         * Get the [FingerPrintAuthHelper]
         *
         * @param context  instance of the caller.
         * @param callback [OnFingerPrintAuthCallback] to get notify whenever authentication success/fails.
         * @return [FingerPrintAuthHelper]
         */
        fun getHelper(context : Context, callback : OnFingerPrintAuthCallback) : FingerPrintAuthHelper {
            if (context == null) {
                throw IllegalArgumentException("Context cannot be null.")
            } else if (callback == null) {
                throw IllegalArgumentException("OnFingerPrintAuthCallback cannot be null.")
            }

            return FingerPrintAuthHelper(context, callback)
        }
    }
}
