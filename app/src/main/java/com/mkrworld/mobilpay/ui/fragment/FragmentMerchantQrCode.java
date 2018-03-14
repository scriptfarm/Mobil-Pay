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

import com.mkrworld.androidlib.callback.OnBaseActivityListener;
import com.mkrworld.androidlib.callback.OnBaseFragmentListener;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;

import net.glxn.qrgen.android.QRCode;

/**
 * Created by mkr on 13/3/18.
 */

public class FragmentMerchantQrCode extends Fragment implements OnBaseFragmentListener {
    public static final String EXTRA_QR_CODE_TITLE = "EXTRA_QR_CODE_TITLE";
    public static final String EXTRA_QR_CODE_TEXT = "EXTRA_QR_CODE_TEXT";
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
        String qrCodeText = bundle.getString(EXTRA_QR_CODE_TEXT, "");
        String qrCodeTitle = bundle.getString(EXTRA_QR_CODE_TITLE, "");
        ((TextView) getView().findViewById(R.id.fragment_merchant_qrcode_textView_name)).setText(qrCodeTitle);
        // DRAW QR-CODE
        int qrCodeSize = getResources().getDimensionPixelSize(R.dimen.fragment_merchant_qrcode_qrcode_dimen);
        Bitmap bitmapQRCode = QRCode.from(qrCodeText).withSize(qrCodeSize, qrCodeSize).bitmap();
        if (bitmapQRCode != null && !bitmapQRCode.isRecycled()) {
            ((ImageView) getView().findViewById(R.id.fragment_merchant_qrcode_imageView_qrcode)).setImageBitmap(bitmapQRCode);
        }
    }
}
