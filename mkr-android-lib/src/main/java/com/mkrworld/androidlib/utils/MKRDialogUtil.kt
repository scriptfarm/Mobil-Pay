package com.mkrworld.androidlib.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import com.mkrworld.androidlib.BuildConfig
import com.mkrworld.androidlib.ui.view.ProgressView


/**
 * Dialog class for showing Progress dialog
 *
 *
 * Created by Sunny on 3/8/2016.
 */
class MKRDialogUtil {
    companion object {
        val TAG : String = BuildConfig.BASE_TAG + ".MKRDialogUtil"
        private var alertDialog : Dialog? = null

        /**
         * Method to show Ok Dialog, Dialog dismiss on button click
         *
         * @param context
         * @param iconId
         * @param title
         * @param message
         * @param okText
         * @param onOkClickListener
         * @param cancellable
         * @return
         */
        fun showOKDialog(context : Context, iconId : Int, title : String, message : String, okText : String, onOkClickListener : DialogInterface.OnClickListener?, cancellable : Boolean) : Dialog {
            if (alertDialog != null && alertDialog !!.isShowing) {
                alertDialog !!.dismiss()
                alertDialog = null
            }
            val alertDialog : AlertDialog.Builder
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                alertDialog = AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert)
            } else {
                alertDialog = AlertDialog.Builder(context)
            }
            alertDialog.setIcon(iconId)
            alertDialog.setTitle(title)
            alertDialog.setMessage(message)
            alertDialog.setPositiveButton(okText) { dialog, which ->
                dialog.dismiss()
                onOkClickListener?.onClick(dialog, which)
            }
            alertDialog.setCancelable(cancellable)
            return alertDialog.show()
        }

        /**
         * Method to show Ok Dialog, Dialog dismiss on button click
         *
         * @param context
         * @param iconId
         * @param title
         * @param message
         * @param okText
         * @param onOkClickListener
         * @param cancelText
         * @param onCancelClickListener
         * @param cancellable
         * @return
         */
        fun showOKCancelDialog(context : Context, iconId : Int, title : String, message : String, okText : String, onOkClickListener : DialogInterface.OnClickListener?, cancelText : String, onCancelClickListener : DialogInterface.OnClickListener?, cancellable : Boolean) : Dialog {
            if (alertDialog != null && alertDialog !!.isShowing) {
                alertDialog !!.dismiss()
                alertDialog = null
            }
            val alertDialog : AlertDialog.Builder
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                alertDialog = AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert)
            } else {
                alertDialog = AlertDialog.Builder(context)
            }
            alertDialog.setIcon(iconId)
            alertDialog.setTitle(title)
            alertDialog.setMessage(message)
            alertDialog.setPositiveButton(okText) { dialog, which ->
                dialog.dismiss()
                onOkClickListener?.onClick(dialog, which)
            }
            alertDialog.setNegativeButton(cancelText) { dialog, which ->
                dialog.dismiss()
                onCancelClickListener?.onClick(dialog, which)
            }
            alertDialog.setCancelable(cancellable)
            return alertDialog.show()
        }

        /**
         * Method to show Ok Dialog, Dialog dismiss on button click
         *
         * @param context
         * @param iconId
         * @param title
         * @param message
         * @param onOkClickListener
         * @param cancellable
         * @return
         */
        fun showOKDialog(context : Context, iconId : Int, title : String, message : String, onOkClickListener : DialogInterface.OnClickListener?, cancellable : Boolean) : Dialog {
            return showOKDialog(context, iconId, title, message, "OK", onOkClickListener, cancellable)
        }

        /**
         * Method to show Ok Dialog, Dialog dismiss on button click
         *
         * @param context
         * @param iconId
         * @param title
         * @param message
         * @param onOkClickListener
         * @param onCancelClickListener
         * @param cancellable
         * @return
         */
        fun showOKCancelDialog(context : Context, iconId : Int, title : String, message : String, onOkClickListener : DialogInterface.OnClickListener?, onCancelClickListener : DialogInterface.OnClickListener?, cancellable : Boolean) : Dialog {
            return showOKCancelDialog(context, iconId, title, message, "OK", onOkClickListener, "CANCEL", onCancelClickListener, cancellable)
        }

        /***
         * To dismiss the dialog
         */
        fun dismissLoadingDialog() {
            if (alertDialog != null && alertDialog !!.isShowing) {
                alertDialog !!.dismiss()
                alertDialog !!.cancel()
            }
            alertDialog = null
            Tracer.debug(TAG, "dismissLoadingDialog: ")
        }

        fun showLoadingDialog(context : Context) {
            if (alertDialog != null && alertDialog !!.isShowing) {
                return
            }
            alertDialog = Dialog(context)
            alertDialog !!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            val relativeLayout = RelativeLayout(context)
            val paddingBottom = (context.resources.displayMetrics.heightPixels.toFloat() * 0.2f).toInt()
            relativeLayout.setPadding(0, 0, 0, paddingBottom)
            val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            relativeLayout.layoutParams = layoutParams
            val progressView = ProgressView(context)
            val relativeLayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
            relativeLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
            relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            relativeLayout.addView(progressView, relativeLayoutParams)
            alertDialog !!.setContentView(relativeLayout)
            alertDialog !!.setCanceledOnTouchOutside(false)
            alertDialog !!.setCancelable(false)
            alertDialog !!.window !!.setBackgroundDrawable(ColorDrawable(0))
            alertDialog !!.show()
        }
    }
}
