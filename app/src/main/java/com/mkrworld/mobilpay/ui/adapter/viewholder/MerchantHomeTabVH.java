package com.mkrworld.mobilpay.ui.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mkrworld.androidlib.ui.adapter.BaseViewHolder;
import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;
import com.mkrworld.mobilpay.R;
import com.mkrworld.mobilpay.dto.DTOMerchantHomeTab;

/**
 * Created by mkr on 14/3/18.
 */

public class MerchantHomeTabVH extends BaseViewHolder<DTOMerchantHomeTab> {
    private static final String TAG = BuildConfig.BASE_TAG + ".MerchantHomeTabVH";
    private ImageView mImageViewIcon;
    private TextView mTextViewLabel;

    /**
     * Constructor
     *
     * @param itemView
     */
    public MerchantHomeTabVH(View itemView) {
        super(itemView);
        Tracer.debug(TAG, "MerchantHomeTabVH: ");
        getParent().setOnClickListener(this);
        mImageViewIcon = itemView.findViewById(R.id.item_merchant_home_imageView_icon);
        mTextViewLabel = itemView.findViewById(R.id.item_merchant_home_textView_label);
    }

    @Override
    protected void bindData(DTOMerchantHomeTab dtoMerchantHomeTab) {
        Tracer.debug(TAG, "bindData: " + dtoMerchantHomeTab);
        if (dtoMerchantHomeTab == null) {
            return;
        }
        getParent().setTag(dtoMerchantHomeTab);
        mImageViewIcon.setImageResource(dtoMerchantHomeTab.getIconResId());
        mTextViewLabel.setText(dtoMerchantHomeTab.getLabel());
    }
}
