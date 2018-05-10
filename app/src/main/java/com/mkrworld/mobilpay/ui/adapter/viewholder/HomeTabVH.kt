package com.mkrworld.mobilpay.ui.adapter.viewholder

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.mkrworld.androidlib.ui.adapter.BaseViewHolder
import com.mkrworld.androidlib.ui.view.CircularImageView
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTOHomeTab

/**
 * Created by mkr on 14/3/18.
 */

class HomeTabVH : BaseViewHolder<DTOHomeTab> {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".AgentHomeTabVH"
    }

    private var mImageViewIcon : CircularImageView? = null
    private var mTextViewLabel : TextView? = null

    /**
     * Constructor
     *
     * @param itemView
     */
    constructor(itemView : View) : super(itemView) {
        Tracer.debug(TAG, "AgentHomeTabVH: ")
        getParent().setOnClickListener(this)
        mImageViewIcon = itemView.findViewById(R.id.item_home_imageView_icon)
        mTextViewLabel = itemView.findViewById(R.id.item_home_textView_label)
    }

    override fun bindData(dto : DTOHomeTab) {
        Tracer.debug(TAG, "bindData: " + dto !!)
        if (dto == null) {
            return
        }
        getParent().tag = dto
        mImageViewIcon?.setImageRes(dto.iconResId)
        mTextViewLabel?.text = dto.label
        mImageViewIcon?.setProgressColor(ContextCompat.getColor(getContext(), R.color.circular_progress_color))
    }
}
