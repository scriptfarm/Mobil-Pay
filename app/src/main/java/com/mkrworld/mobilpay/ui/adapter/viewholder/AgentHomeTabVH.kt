package com.mkrworld.mobilpay.ui.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.mkrworld.androidlib.ui.adapter.BaseViewHolder
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTOAgentHomeTab

/**
 * Created by mkr on 14/3/18.
 */

class AgentHomeTabVH : BaseViewHolder<DTOAgentHomeTab> {

    companion object {
        private val TAG = BuildConfig.BASE_TAG + ".AgentHomeTabVH"
    }

    private var mImageViewIcon : ImageView?=null
    private var mTextViewLabel : TextView?=null

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

    override fun bindData(dtoAgentHomeTab : DTOAgentHomeTab) {
        Tracer.debug(TAG, "bindData: " + dtoAgentHomeTab !!)
        if (dtoAgentHomeTab == null) {
            return
        }
        getParent().tag = dtoAgentHomeTab
        mImageViewIcon?.setImageResource(dtoAgentHomeTab.iconResId)
        mTextViewLabel?.text = dtoAgentHomeTab.label
    }
}
