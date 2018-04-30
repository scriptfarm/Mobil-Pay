package com.mkrworld.mobilpay.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.mkrworld.androidlib.utils.Tracer
import com.mkrworld.mobilpay.BuildConfig
import com.mkrworld.mobilpay.dto.appdata.DTODropdownArrayAdapter


/**
 * Created by mkr on 17/4/18.
 */
class PopupArrayAdapter : ArrayAdapter<DTODropdownArrayAdapter> {

    companion object {
        val TAG : String = BuildConfig.BASE_TAG + ".PopupArrayAdapter"
    }

    private var mListFilter = ListFilter()
    private var mDataList : ArrayList<DTODropdownArrayAdapter>
    private var mDataListFiltered : ArrayList<DTODropdownArrayAdapter>
    private var mResId : Int? = null
    private var mTextViewResId : Int? = null

    /**
     * Constructor
     */
    constructor(context : Context?, resource : Int, textViesResourceId : Int, objects : ArrayList<DTODropdownArrayAdapter>?) : super(context, resource, textViesResourceId, objects) {
        Tracer.debug(TAG, "Constructor : ")
        mDataList = ArrayList<DTODropdownArrayAdapter>()
        mDataListFiltered = ArrayList<DTODropdownArrayAdapter>()
        if (objects != null) {
            mDataList !!.addAll(objects)
        }
        mDataListFiltered !!.addAll(mDataList !!)
        mResId = resource
        mTextViewResId = textViesResourceId
    }

    override fun getCount() : Int {
        return mDataListFiltered !!.size
    }

    override fun getItem(position : Int) : DTODropdownArrayAdapter {
        return mDataListFiltered !![position]
    }

    override fun getView(position : Int, convertView : View?, parent : ViewGroup?) : View {
        var parentView = convertView
        if (parentView == null) {
            parentView = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(mResId !!, null)
        }
        parentView !!.findViewById<TextView>(mTextViewResId !!).setText("" + getDoubleDigitValue(position + 1) + ". " + getItem(position).name)
        return parentView !!
    }

    private fun getDoubleDigitValue(position : Int) : String {
        return if (position < 10) {
            "0$position"
        } else {
            "" + position
        }
    }

    override fun getFilter() : Filter {
        return mListFilter
    }

    inner class ListFilter : Filter() {
        private val lock = Any()

        override fun convertResultToString(resultValue : Any) : String? {
            return (resultValue as DTODropdownArrayAdapter).name
        }

        override fun performFiltering(prefix : CharSequence?) : FilterResults {
            val results = Filter.FilterResults()
            if (prefix == null || prefix.trim().length == 0) {
                synchronized(lock) {
                    results.values = mDataList
                    results.count = mDataList !!.size
                }
            } else {
                val searchStrLowerCase = prefix.toString().trim().toLowerCase()
                val matchValues = ArrayList<DTODropdownArrayAdapter>()
                for (dataItem in mDataList !!) {
                    if (dataItem.name !!.trim().toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem)
                    }
                }
                results.values = matchValues
                results.count = matchValues.size
            }
            return results
        }

        override fun publishResults(constraint : CharSequence?, results : FilterResults?) {
            mDataListFiltered.clear()
            if (results !!.values != null) {
                mDataListFiltered.addAll(results !!.values as ArrayList<DTODropdownArrayAdapter>)
            } else {
                mDataListFiltered.addAll(mDataList)
            }
            if (mDataListFiltered.size > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }

    }

}