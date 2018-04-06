package com.mkrworld.biometric.data

/**
 * Created by mkr on 6/4/18.
 */
class PIDBean {
    var skey : String? = null
        get() = field ?: ""
        set(value) {
            field = value ?: ""
        }

    var pid : String? = null
        get() = field ?: ""
        set(value) {
            field = value ?: ""
        }

    var hmac : String? = null
        get() = field ?: ""
        set(value) {
            field = value ?: ""
        }

    var udc : String? = null
        get() = field ?: ""
        set(value) {
            field = value ?: ""
        }

    var pidTs : String? = null
        get() = field ?: ""
        set(value) {
            field = value ?: ""
        }

    var ci : String? = null
        get() = field ?: ""
        set(value) {
            field = value ?: ""
        }

    var serviceType : String? = "1"
        get() = field ?: ""
        set(value) {
            field = value ?: ""
        }

    var registeredDeviceProviderCode : String? = ""
        get() = field ?: ""
        set(value) {
            field = value ?: ""
        }

    var registeredDeviceServiceID : String? = ""
        get() = field ?: ""
        set(value) {
            field = value ?: ""
        }

    var registeredDeviceServiceVersion : String? = ""
        get() = field ?: ""
        set(value) {
            field = value ?: ""
        }

    var registeredDeviceCode : String? = ""
        get() = field ?: ""
        set(value) {
            field = value ?: ""
        }

    var registeredDevicePublicKeyCertificate : String? = ""
        get() = field ?: ""
        set(value) {
            field = value ?: ""
        }

    var registeredDeviceModelID : String? = ""
        get() = field ?: ""
        set(value) {
            field = value ?: ""
        }

    override fun toString() : String {
        return "PIDBean{mSkey='$skey', mPid='$pid', mHmac='$hmac', mUdc='$udc', mRegisteredDeviceProviderCode='$registeredDeviceProviderCode', mRegisteredDeviceServiceID='$registeredDeviceServiceID', mRegisteredDeviceServiceVersion='$registeredDeviceServiceVersion', mRegisteredDeviceCode='$registeredDeviceCode', mRegisteredDevicePublicKeyCertificate='$registeredDevicePublicKeyCertificate', registeredDeviceModelID='$registeredDeviceModelID', mCi='$ci', mServiceType='$serviceType', mPidTs='$pidTs'}"
    }
}