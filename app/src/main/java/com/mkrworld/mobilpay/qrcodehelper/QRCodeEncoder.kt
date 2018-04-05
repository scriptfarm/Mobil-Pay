package com.mkrworld.mobilpay.qrcodehelper

import android.graphics.Bitmap
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.PhoneNumberUtils

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.util.EnumMap
import java.util.HashSet

class QRCodeEncoder {

    private var dimension = Integer.MIN_VALUE
    var contents : String? = null
        private set
    var displayContents : String? = null
        private set
    var title : String? = null
        private set
    private var format : BarcodeFormat? = null
    private var encoded = false

    constructor() {}

    constructor(data : String, bundle : Bundle, type : String, format : String, dimension : Int) {
        this.dimension = dimension
        encoded = encodeContents(data, bundle, type, format)
    }

    private fun encodeContents(data : String?, bundle : Bundle, type : String, formatString : String?) : Boolean {
        // Default to QR_CODE if no format given.
        format = null
        if (formatString != null) {
            try {
                format = BarcodeFormat.valueOf(formatString)
            } catch (iae : IllegalArgumentException) {
                // Ignore it then
            }

        }
        if (format == null || format == BarcodeFormat.QR_CODE) {
            this.format = BarcodeFormat.QR_CODE
            encodeQRCodeContents(data, bundle, type)
        } else if (data != null && data.length > 0) {
            contents = data
            displayContents = data
            title = "Text"
        }
        return contents != null && contents !!.length > 0
    }

    private fun encodeQRCodeContents(data : String?, bundle : Bundle?, type : String) {
        var data = data
        if (type == Contents.Type.TEXT) {
            if (data != null && data.length > 0) {
                contents = data
                displayContents = data
                title = "Text"
            }
        } else if (type == Contents.Type.EMAIL) {
            data = trim(data)
            if (data != null) {
                contents = "mailto:$data"
                displayContents = data
                title = "E-Mail"
            }
        } else if (type == Contents.Type.PHONE) {
            data = trim(data)
            if (data != null) {
                contents = "tel:$data"
                displayContents = PhoneNumberUtils.formatNumber(data)
                title = "Phone"
            }
        } else if (type == Contents.Type.SMS) {
            data = trim(data)
            if (data != null) {
                contents = "sms:$data"
                displayContents = PhoneNumberUtils.formatNumber(data)
                title = "SMS"
            }
        } else if (type == Contents.Type.CONTACT) {
            if (bundle != null) {
                val newContents = StringBuilder(100)
                val newDisplayContents = StringBuilder(100)

                newContents.append("MECARD:")

                val name = trim(bundle.getString(ContactsContract.Intents.Insert.NAME))
                if (name != null) {
                    newContents.append("N:").append(escapeMECARD(name)).append(';')
                    newDisplayContents.append(name)
                }

                val address = trim(bundle.getString(ContactsContract.Intents.Insert.POSTAL))
                if (address != null) {
                    newContents.append("ADR:").append(escapeMECARD(address)).append(';')
                    newDisplayContents.append('\n').append(address)
                }

                val uniquePhones = HashSet<String>(Contents.PHONE_KEYS.size)
                for (x in Contents.PHONE_KEYS.indices) {
                    val phone = trim(bundle.getString(Contents.PHONE_KEYS[x]))
                    if (phone != null) {
                        uniquePhones.add(phone)
                    }
                }
                for (phone in uniquePhones) {
                    newContents.append("TEL:").append(escapeMECARD(phone)).append(';')
                    newDisplayContents.append('\n').append(PhoneNumberUtils.formatNumber(phone))
                }

                val uniqueEmails = HashSet<String>(Contents.EMAIL_KEYS.size)
                for (x in Contents.EMAIL_KEYS.indices) {
                    val email = trim(bundle.getString(Contents.EMAIL_KEYS[x]))
                    if (email != null) {
                        uniqueEmails.add(email)
                    }
                }
                for (email in uniqueEmails) {
                    newContents.append("EMAIL:").append(escapeMECARD(email)).append(';')
                    newDisplayContents.append('\n').append(email)
                }

                val url = trim(bundle.getString(Contents.URL_KEY))
                if (url != null) {
                    // escapeMECARD(url) -> wrong escape e.g. http\://zxing.google.com
                    newContents.append("URL:").append(url).append(';')
                    newDisplayContents.append('\n').append(url)
                }

                val note = trim(bundle.getString(Contents.NOTE_KEY))
                if (note != null) {
                    newContents.append("NOTE:").append(escapeMECARD(note)).append(';')
                    newDisplayContents.append('\n').append(note)
                }

                // Make sure we've encoded at least one field.
                if (newDisplayContents.length > 0) {
                    newContents.append(';')
                    contents = newContents.toString()
                    displayContents = newDisplayContents.toString()
                    title = "Contact"
                } else {
                    contents = null
                    displayContents = null
                }

            }
        } else if (type == Contents.Type.LOCATION) {
            if (bundle != null) {
                // These must use Bundle.getFloat(), not getDouble(), it's part of the API.
                val latitude = bundle.getFloat("LAT", java.lang.Float.MAX_VALUE)
                val longitude = bundle.getFloat("LONG", java.lang.Float.MAX_VALUE)
                if (latitude != java.lang.Float.MAX_VALUE && longitude != java.lang.Float.MAX_VALUE) {
                    contents = "geo:" + latitude + ','.toString() + longitude
                    displayContents = latitude.toString() + "," + longitude
                    title = "Location"
                }
            }
        }
    }

    @Throws(WriterException::class)
    fun encodeAsBitmap() : Bitmap? {
        if (! encoded) return null

        var hints : MutableMap<EncodeHintType, Any>? = null
        val encoding = guessAppropriateEncoding(contents)
        if (encoding != null) {
            hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
            hints !![EncodeHintType.CHARACTER_SET] = encoding
        }
        val writer = MultiFormatWriter()
        val result = writer.encode(contents, format !!, dimension, dimension, hints)
        val width = result.width
        val height = result.height
        val pixels = IntArray(width * height)
        // All are 0, or black, by default
        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (result.get(x, y)) BLACK else WHITE
            }
        }

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }

    companion object {
        private val WHITE = - 0x1
        private val BLACK = - 0x1000000

        private fun guessAppropriateEncoding(contents : CharSequence?) : String? {
            // Very crude at the moment
            for (i in 0 until contents !!.length) {
                if (contents !![i].toInt() > 0xFF) {
                    return "UTF-8"
                }
            }
            return null
        }

        private fun trim(s : String?) : String? {
            if (s == null) {
                return null
            }
            val result = s.trim { it <= ' ' }
            return if (result.length == 0) null else result
        }

        private fun escapeMECARD(input : String?) : String? {
            if (input == null || input.indexOf(':') < 0 && input.indexOf(';') < 0) {
                return input
            }
            val length = input.length
            val result = StringBuilder(length)
            for (i in 0 until length) {
                val c = input[i]
                if (c == ':' || c == ';') {
                    result.append('\\')
                }
                result.append(c)
            }
            return result.toString()
        }
    }
}
