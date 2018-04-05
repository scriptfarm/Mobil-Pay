package com.mkrworld.mobilpay.ui.adapter.viewholder

import android.content.ContentValues.TAG
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.mkrworld.androidlib.ui.adapter.BaseViewHolder
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTOMerchantHomeTab

/**
 * Created by mkr on 14/3/18.
 */

class MerchantHomeTabVH : BaseViewHolder<DTOMerchantHomeTab> {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".MerchantHomeTabVH"
    }

    private var mImageViewIcon : ImageView?=null
    private var mTextViewLabel : TextView?=null

    /**
     * Constructor
     *
     * @param itemView
     */
    constructor(itemView : View) : super(itemView) {
        Tracer.debug(TAG, "MerchantHomeTabVH: ")
        getParent().setOnClickListener(this)
        mImageViewIcon = itemView.findViewById(R.id.item_merchant_home_imageView_icon)
        mTextViewLabel = itemView.findViewById(R.id.item_merchant_home_textView_label)
    }

    override fun bindData(dtoMerchantHomeTab : DTOMerchantHomeTab) {
        Tracer.debug(TAG, "bindData: " + dtoMerchantHomeTab !!)
        if (dtoMerchantHomeTab == null) {
            return
        }
        getParent().tag = dtoMerchantHomeTab
        mImageViewIcon?.setImageResource(dtoMerchantHomeTab.iconResId)
        mTextViewLabel?.text = dtoMerchantHomeTab.label
    }
}
