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
class DropdownArrayAdapter : ArrayAdapter<DTODropdownArrayAdapter> {

    companion object {
        val TAG : String = BuildConfig.BASE_TAG + ".DropdownArrayAdapter"
    }

    private var mListFilter = ListFilter()
    private var mDataListAllItems : ArrayList<DTODropdownArrayAdapter>? = null
    private var mDataList : ArrayList<DTODropdownArrayAdapter>? = null
    private var mResId : Int? = null
    private var mTextViewResId : Int? = null

    /**
     * Constructor
     */
    constructor(context : Context?, resource : Int, textViesResourceId : Int, objects : ArrayList<DTODropdownArrayAdapter>?) : super(context, resource, textViesResourceId, objects) {
        Tracer.debug(TAG, "Constructor : ")
        mDataList = objects
        mResId = resource
        mTextViewResId = textViesResourceId
    }

    override fun getView(position : Int, convertView : View?, parent : ViewGroup?) : View {
        var parentView = convertView
        if (parentView == null) {
            parentView = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(mResId !!, null)
        }
        parentView !!.findViewById<TextView>(mTextViewResId !!).setText(getItem(position).name)
        return parentView !!
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
            if (mDataListAllItems == null) {
                synchronized(lock) {
                    mDataListAllItems = ArrayList<DTODropdownArrayAdapter>(mDataList)
                }
            }

            if (prefix == null || prefix.trim().length === 0) {
                synchronized(lock) {
                    results.values = mDataListAllItems
                    results.count = mDataListAllItems !!.size
                }
            } else {
                val searchStrLowerCase = prefix.toString().toLowerCase()

                val matchValues = ArrayList<DTODropdownArrayAdapter>()

                for (dataItem in mDataListAllItems !!) {
                    if (dataItem.name !!.toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem)
                    }
                }

                results.values = matchValues
                results.count = matchValues.size
            }

            return results
        }

        override fun publishResults(constraint : CharSequence?, results : FilterResults?) {
            if (results !!.values != null) {
                mDataList = results !!.values as ArrayList<DTODropdownArrayAdapter>
            } else {
                mDataList = null
            }
            if (results.count > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }

    }

}