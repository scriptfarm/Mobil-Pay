package com.mkrworld.mobilpay.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.WindowManager
import android.widget.CheckBox
import com.mkrworld.androidlib.ui.adapter.BaseAdapter
import com.mkrworld.androidlib.ui.adapter.BaseAdapterItem
import com.mkrworld.androidlib.ui.adapter.BaseViewHolder
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.R
import com.mkrworld.mobilpay.dto.appdata.DTOMultiSelectionItemData
import com.mkrworld.mobilpay.ui.adapter.AdapterItemHandler
import com.mkrworld.mobilpay.ui.adapter.GridSpacingItemDecoration


/**
 * Created by mkr on 7/5/18.
 */
class DialogMultiSelection : Dialog, View.OnClickListener, BaseViewHolder.VHClickable {
    companion object {
        val TAG : String = BuildConfig.BASE_TAG + ".DialogMultiSelection"
    }

    private var mMultiSelectionItemDataList : ArrayList<DTOMultiSelectionItemData>? = null
    private var mBaseAdapter : BaseAdapter? = null
    private var mIsEnableAllSelection : Boolean = false
    private var mIsAllSelected : Boolean = false
    private var mOnDialogMultiSelectionListener : OnDialogMultiSelectionListener? = null

    constructor(context : Context, multiSelectionItemDataList : ArrayList<DTOMultiSelectionItemData>, onDialogMultiSelectionListener : OnDialogMultiSelectionListener, isEnableAllSelection : Boolean) : super(context) {
        Tracer.debug(TAG, "Constractor : 2")
        init(multiSelectionItemDataList, onDialogMultiSelectionListener, isEnableAllSelection)
    }

    constructor(context : Context, themeResId : Int, multiSelectionItemDataList : ArrayList<DTOMultiSelectionItemData>, onDialogMultiSelectionListener : OnDialogMultiSelectionListener, isEnableAllSelection : Boolean) : super(context, themeResId) {
        Tracer.debug(TAG, "Constractor : 3")
        init(multiSelectionItemDataList, onDialogMultiSelectionListener, isEnableAllSelection)
    }

    /**
     * Method to init the
     */
    fun init(multiSelectionItemDataList : ArrayList<DTOMultiSelectionItemData>, onDialogMultiSelectionListener : OnDialogMultiSelectionListener, isEnableAllSelection : Boolean) {
        Tracer.debug(TAG, "init : ")
        mIsEnableAllSelection = isEnableAllSelection
        mOnDialogMultiSelectionListener = onDialogMultiSelectionListener
        mBaseAdapter = BaseAdapter(AdapterItemHandler())
        mMultiSelectionItemDataList = ArrayList<DTOMultiSelectionItemData>()
        if (multiSelectionItemDataList != null) {
            mMultiSelectionItemDataList?.addAll(multiSelectionItemDataList)
        }
    }

    override fun onAttachedToWindow() {
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(window !!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT //context.resources.displayMetrics.widthPixels
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        window !!.attributes = lp
        super.onAttachedToWindow()
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_multi_selection)
        val recyclerView = findViewById<View>(R.id.dialog_multi_selection_recyclerView) as RecyclerView
        recyclerView.adapter = mBaseAdapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        // ADD HORIZONTAL LINE
        val colorDivider = ContextCompat.getColor(context, R.color.divider_color)
        val gridSpacingItemDecoration = GridSpacingItemDecoration(1, context.resources.getDimensionPixelOffset(R.dimen.divider_size), colorDivider, false)
        recyclerView.addItemDecoration(gridSpacingItemDecoration)

        findViewById<View>(R.id.dialog_multi_selection_textView_submit).setOnClickListener(this)
        findViewById<View>(R.id.dialog_multi_selection_textView_cancel).setOnClickListener(this)
        findViewById<View>(R.id.dialog_multi_selection_checkBox_all).setOnClickListener(this)
        mBaseAdapter !!.setVHClickCallback(this)
    }

    override fun onStart() {
        super.onStart()
        if (mMultiSelectionItemDataList !!.size <= 0) {
            dismiss()
            mOnDialogMultiSelectionListener?.onDialogMultiSelectionCancel()
            return
        }
        if (mIsEnableAllSelection) {
            findViewById<View>(R.id.dialog_multi_selection_layout_all).visibility = View.VISIBLE
            mIsAllSelected = true
        } else {
            findViewById<View>(R.id.dialog_multi_selection_layout_all).visibility = View.GONE
            mIsAllSelected = false
        }
        findViewById<CheckBox>(R.id.dialog_multi_selection_checkBox_all).isChecked = mIsAllSelected
        // SET OPTION DATA
        val baseAdapterItemArrayList = ArrayList<BaseAdapterItem<*>>()
        for (multiSelectionItemData : DTOMultiSelectionItemData in mMultiSelectionItemDataList !!) {
            if (mIsEnableAllSelection) {
                multiSelectionItemData.isChecked = true
            }
            baseAdapterItemArrayList.add(BaseAdapterItem(AdapterItemHandler.AdapterItemViewType.MULTI_SELECTION_ITEM.ordinal, multiSelectionItemData))
        }
        mBaseAdapter !!.updateAdapterItemList(baseAdapterItemArrayList)
    }

    override fun onClick(view : View?) {
        when (view !!.id) {
            R.id.dialog_multi_selection_textView_cancel -> {
                dismiss()
                mOnDialogMultiSelectionListener?.onDialogMultiSelectionCancel()
            }
            R.id.dialog_multi_selection_textView_submit -> {
                var multiSelectionItemDataList : ArrayList<DTOMultiSelectionItemData> = ArrayList<DTOMultiSelectionItemData>()
                for (index : Int in 0 .. (mBaseAdapter !!.itemCount - 1)) {
                    val item : BaseAdapterItem<*> = mBaseAdapter !!.getItem(index)
                    if (item.getData() is DTOMultiSelectionItemData) {
                        var data = item.getData() as DTOMultiSelectionItemData
                        if (data.isChecked) {
                            multiSelectionItemDataList.add(data)
                        }
                    }
                }
                dismiss()
                if (multiSelectionItemDataList.size <= 0) {
                    mOnDialogMultiSelectionListener?.onDialogMultiSelectionCancel()
                } else {
                    mOnDialogMultiSelectionListener?.onDialogMultiSelectionSelecetItemList(multiSelectionItemDataList)
                }
            }
            R.id.dialog_multi_selection_checkBox_all -> {
                mIsAllSelected = ! mIsAllSelected
                findViewById<CheckBox>(R.id.dialog_multi_selection_checkBox_all).isChecked = mIsAllSelected
                for (index : Int in 0 .. (mBaseAdapter !!.itemCount - 1)) {
                    val item : BaseAdapterItem<*> = mBaseAdapter !!.getItem(index)
                    if (item.getData() is DTOMultiSelectionItemData) {
                        var data = item.getData() as DTOMultiSelectionItemData
                        data.isChecked = mIsAllSelected
                    }
                }
                mBaseAdapter !!.notifyDataSetChanged()
            }
        }
    }

    override fun onViewHolderClicked(holder : BaseViewHolder<*>, view : View) {
        when (view.id) {
            R.id.item_multi_selection_item_data_checkBox -> {
                mIsAllSelected = true
                for (index : Int in 0 .. (mBaseAdapter !!.itemCount - 1)) {
                    val item : BaseAdapterItem<*> = mBaseAdapter !!.getItem(index)
                    if (item.getData() is DTOMultiSelectionItemData) {
                        var data = item.getData() as DTOMultiSelectionItemData
                        if (! data.isChecked) {
                            mIsAllSelected = false
                            break
                        }
                    }
                }
                findViewById<CheckBox>(R.id.dialog_multi_selection_checkBox_all).isChecked = mIsAllSelected
            }
        }
    }

    /**
     * Callback to listen the Event occur inside the DialogMultiSelection
     */
    interface OnDialogMultiSelectionListener {

        /**
         * Method to notify that user select some options
         */
        fun onDialogMultiSelectionSelecetItemList(multiSelectionItemDataList : ArrayList<DTOMultiSelectionItemData>)

        /**
         * Method to notify that user cancel the selection
         */
        fun onDialogMultiSelectionCancel()
    }
}