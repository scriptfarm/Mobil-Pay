package com.mkrworld.androidlib.callback;

/**
 * Created by a1zfkxa3 on 11/28/2017.
 */

public interface OnBaseFragmentListener {

    /**
     * Method to notifyTaskResponse that user pressed the back button
     *
     * @return TRUE if fragment handel the back pressed event, else return FALSE
     */
    public boolean onBackPressed();

    /**
     * Method to notifyTaskResponse that this fragment is popped from back stack and now visible to the user
     */
    public void onPopFromBackStack();

    /**
     * Method to notifyTaskResponse the there is a need to forcefully refresh the fragment view
     */
    public void onRefresh();

}
