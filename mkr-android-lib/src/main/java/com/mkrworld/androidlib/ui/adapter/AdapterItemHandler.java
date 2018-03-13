package com.mkrworld.androidlib.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 *
 */
public abstract class AdapterItemHandler {

    /**
     * Method to get the Adapter View
     *
     * @param inflater
     * @param parent
     * @param viewType
     * @return
     */
    public abstract BaseViewHolder createHolder(LayoutInflater inflater, ViewGroup parent, int viewType);
}
