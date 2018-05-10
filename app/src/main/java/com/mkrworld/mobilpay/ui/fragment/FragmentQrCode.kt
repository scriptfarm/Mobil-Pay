package com.mkrworld.mobilpay.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.mkrworld.androidlib.callback.OnBaseActivityListener
import com.mkrworld.androidlib.callback.OnBaseFragmentListener
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.qrcodehelper.Contents
import com.mkrworld.mobilpay.qrcodehelper.QRCodeEncoder
import com.mkrworld.mobilpay.utils.Constants
import com.mkrworld.mobilpay.utils.PreferenceData
import com.mkrworld.mobilpay.utils.UrlUtils
import com.squareup.picasso.Picasso

/**
 * Created by mkr on 13/3/18.
 */

class FragmentQrCode : Fragment(), OnBaseFragmentListener {

    companion object {
        val EXTRA_QR_CODE_TITLE = "EXTRA_QR_CODE_TITLE"
        val EXTRA_IS_DYNAMIC_QR_CODE = "EXTRA_IS_DYNAMIC_QR_CODE"
        val EXTRA_BILL_AMOUNT = "EXTRA_BILL_AMOUNT"
        val EXTRA_BILL_NUMBER = "EXTRA_BILL_NUMBER"
        val EXTRA_QR_CODE_TOKEN = "EXTRA_QR_CODE_TOKEN"
        private val TAG = BuildConfig.BASE_TAG + ".FragmentQrCode"
    }

    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        Tracer.debug(TAG, "onCreateView: ")
        return inflater !!.inflate(R.layout.fragment_qrcode, container, false)
    }

    override fun onViewCreated(view : View?, savedInstanceState : Bundle?) {
        Tracer.debug(TAG, "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
        setTitle()
        init()
    }

    override fun onBackPressed() : Boolean {
        Tracer.debug(TAG, "onBackPressed: ")
        return false
    }

    override fun onPopFromBackStack() {
        Tracer.debug(TAG, "onPopFromBackStack: ")
        setTitle()
    }

    override fun onRefresh() {
        Tracer.debug(TAG, "onRefresh: ")
    }

    /**
     * Method to set the Activity Title
     */
    private fun setTitle() {
        Tracer.debug(TAG, "setTitle: ")
        if (activity is OnBaseActivityListener) {
            (activity as OnBaseActivityListener).onBaseActivitySetScreenTitle(getString(R.string.screen_title_agent_qrcode))
        }
    }

    /**
     * Method to initialize the Fragment
     */
    private fun init() {
        Tracer.debug(TAG, "init: ")
        val bundle = arguments
        if (view == null || bundle == null) {
            return
        }
        val qrCodeTitle = bundle.getString(EXTRA_QR_CODE_TITLE, "")
        (view !!.findViewById<View>(R.id.fragment_qrcode_textView_name) as TextView).text = qrCodeTitle
        if (bundle.getBoolean(EXTRA_IS_DYNAMIC_QR_CODE, false)) {
            generateDynamicQRCode(bundle)
        } else {
            generateStaticQRCode(bundle)
        }
    }

    /**
     * Method to generate static QR Code
     *
     * @param bundle Argument bundle
     */
    private fun generateStaticQRCode(bundle : Bundle) {
        Tracer.debug(TAG, "generateStaticQRCode : ")
        val url = UrlUtils.getUrl(activity, R.string.url_agent_logo) + if (PreferenceData.getUserType(activity).equals(Constants.USER_TYPE_MERCHANT)) {
            PreferenceData.getLoginMerchantId(activity)
        } else {
            PreferenceData.getLoginAgentId(activity)
        } + ".png"
        Picasso.with(activity).load(url).placeholder(R.mipmap.ic_launcher).into(view !!.findViewById<View>(R.id.fragment_qrcode_imageView_qrcode) as ImageView)
    }

    /**
     * Method to generate Dynamic QR Code
     *
     * @param bundle Argument Bundel
     */
    private fun generateDynamicQRCode(bundle : Bundle) {
        Tracer.debug(TAG, "generateDynamicQRCode : ")
        val qrCodeToken = bundle.getString(EXTRA_QR_CODE_TOKEN, "")
        val qrCodeSize = resources.getDimensionPixelSize(R.dimen.fragment_qrcode_qrcode_dimen)
        val qrCodeEncoder = QRCodeEncoder(qrCodeToken, null !!, Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeSize)
        try {
            val bitmapQRCode = qrCodeEncoder.encodeAsBitmap()
            if (bitmapQRCode != null && ! bitmapQRCode.isRecycled) {
                (view !!.findViewById<View>(R.id.fragment_qrcode_imageView_qrcode) as ImageView).setImageBitmap(bitmapQRCode)
            }
        } catch (e : WriterException) {
            Tracer.showSnack(view !!, R.string.unable_to_generate_qr_code)
            e.printStackTrace()
        }

    }
}