package com.mkrworld.mobilpay.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.mkrworld.androidlib.callback.OnBaseActivityListener;
import com.mkrworld.androidlib.callback.OnBaseFragmentListener;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;
import com.mkrworld.mobilpay.qrcodehelper.Contents;
import com.mkrworld.mobilpay.qrcodehelper.QRCodeEncoder;
import com.mkrworld.mobilpay.utils.PreferenceData;
import com.mkrworld.mobilpay.utils.UrlUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by mkr on 13/3/18.
 */

public class FragmentMerchantQrCode extends Fragment implements OnBaseFragmentListener {
    public static final String EXTRA_QR_CODE_TITLE = "EXTRA_QR_CODE_TITLE";
    public static final String EXTRA_IS_DYNAMIC_QR_CODE = "EXTRA_IS_DYNAMIC_QR_CODE";
    public static final String EXTRA_BILL_AMOUNT = "EXTRA_BILL_AMOUNT";
    public static final String EXTRA_BILL_NUMBER = "EXTRA_BILL_NUMBER";
    public static final String EXTRA_QR_CODE_TOKEN = "EXTRA_QR_CODE_TOKEN";
    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentMerchantQrCode";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.fragment_merchant_qrcode, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        setTitle();
        init();
    }

    @Override
    public boolean onBackPressed() {
        Tracer.debug(TAG, "onBackPressed: ");
        return false;
    }

    @Override
    public void onPopFromBackStack() {
        Tracer.debug(TAG, "onPopFromBackStack: ");
        setTitle();
    }

    @Override
    public void onRefresh() {
        Tracer.debug(TAG, "onRefresh: ");
    }

    /**
     * Method to set the Activity Title
     */
    private void setTitle() {
        Tracer.debug(TAG, "setTitle: ");
        if (getActivity() instanceof OnBaseActivityListener) {
            ((OnBaseActivityListener) getActivity()).onBaseActivitySetScreenTitle(getString(R.string.screen_title_merchant_qrcode));
        }
    }

    /**
     * Method to initialize the Fragment
     */
    private void init() {
        Tracer.debug(TAG, "init: ");
        Bundle bundle = getArguments();
        if (getView() == null || bundle == null) {
            return;
        }
        String qrCodeTitle = bundle.getString(EXTRA_QR_CODE_TITLE, "");
        ((TextView) getView().findViewById(R.id.fragment_merchant_qrcode_textView_name)).setText(qrCodeTitle);
        if (bundle.getBoolean(EXTRA_IS_DYNAMIC_QR_CODE, false)) {
            generateDynamicQRCode(bundle);
        } else {
            generateStaticQRCode(bundle);
        }
    }

    /**
     * Method to generate static QR Code
     *
     * @param bundle Argument bundle
     */
    private void generateStaticQRCode(Bundle bundle) {
        Tracer.debug(TAG, "generateStaticQRCode : ");
        String url = UrlUtils.getUrl(getActivity(), R.string.url_merchant_logo) + PreferenceData.getMerchantId(getActivity()) + ".png";
        // System.out.println("======url=========="+url);
        Picasso.with(getActivity()).load(url).placeholder(R.mipmap.ic_launcher).into(((ImageView) getView().findViewById(R.id.fragment_merchant_qrcode_imageView_qrcode)));
    }

    /**
     * Method to generate Dynamic QR Code
     *
     * @param bundle Argument Bundel
     */
    private void generateDynamicQRCode(Bundle bundle) {
        Tracer.debug(TAG, "generateDynamicQRCode : ");
        String qrCodeToken = bundle.getString(EXTRA_QR_CODE_TOKEN, "");
        int qrCodeSize = getResources().getDimensionPixelSize(R.dimen.fragment_merchant_qrcode_qrcode_dimen);
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrCodeToken, null, Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeSize);
        try {
            Bitmap bitmapQRCode = qrCodeEncoder.encodeAsBitmap();
            if (bitmapQRCode != null && !bitmapQRCode.isRecycled()) {
                ((ImageView) getView().findViewById(R.id.fragment_merchant_qrcode_imageView_qrcode)).setImageBitmap(bitmapQRCode);
            }
        } catch (WriterException e) {
            Tracer.showSnack(getView(), R.string.unable_to_generate_qr_code);
            e.printStackTrace();
        }
    }
}