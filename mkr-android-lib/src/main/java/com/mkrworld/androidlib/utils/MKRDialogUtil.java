package com.mkrworld.androidlib.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import com.mkrworld.androidlib.BuildConfig;
import com.mkrworld.androidlib.ui.view.ProgressView;


/**
 * Dialog class for showing Progress dialog
 * <p/>
 * Created by Sunny on 3/8/2016.
 */
public class MKRDialogUtil {
    private static final String TAG = BuildConfig.BASE_TAG + ".MKRDialogUtil";
    private static Dialog alertDialog;

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
    public static Dialog showOKDialog(Context context, int iconId, String title, String message, String okText, final DialogInterface.OnClickListener onOkClickListener, boolean cancellable) {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }
        AlertDialog.Builder alertDialog;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialog = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            alertDialog = new AlertDialog.Builder(context);
        }
        alertDialog.setIcon(iconId);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(okText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (onOkClickListener != null) {
                    onOkClickListener.onClick(dialog, which);
                }
            }
        });
        alertDialog.setCancelable(cancellable);
        return alertDialog.show();
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
    public static Dialog showOKCancelDialog(Context context, int iconId, String title, String message, String okText, final DialogInterface.OnClickListener onOkClickListener, String cancelText, final DialogInterface.OnClickListener onCancelClickListener, boolean cancellable) {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }
        AlertDialog.Builder alertDialog;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialog = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            alertDialog = new AlertDialog.Builder(context);
        }
        alertDialog.setIcon(iconId);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(okText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (onOkClickListener != null) {
                    onOkClickListener.onClick(dialog, which);
                }
            }
        });
        alertDialog.setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (onCancelClickListener != null) {
                    onCancelClickListener.onClick(dialog, which);
                }
            }
        });
        alertDialog.setCancelable(cancellable);
        return alertDialog.show();
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
    public static Dialog showOKDialog(Context context, int iconId, String title, String message, DialogInterface.OnClickListener onOkClickListener, boolean cancellable) {
        return showOKDialog(context, iconId, title, message, "OK", onOkClickListener, cancellable);
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
    public static Dialog showOKCancelDialog(Context context, int iconId, String title, String message, DialogInterface.OnClickListener onOkClickListener, DialogInterface.OnClickListener onCancelClickListener, boolean cancellable) {
        return showOKCancelDialog(context, iconId, title, message, "OK", onOkClickListener, "CANCEL", onCancelClickListener, cancellable);
    }

    /***
     * To dismiss the dialog
     */
    public static void dismissLoadingDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog.cancel();
        }
        alertDialog = null;
        Tracer.debug(TAG, "dismissLoadingDialog: ");
    }

    public static void showLoadingDialog(Context context) {
        if (alertDialog != null && alertDialog.isShowing()) {
            return;
        }
        alertDialog = new Dialog(context);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        RelativeLayout relativeLayout = new RelativeLayout(context);
        int paddingBottom = (int) ((float) context.getResources().getDisplayMetrics().heightPixels * 0.2F);
        relativeLayout.setPadding(0, 0, 0, paddingBottom);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(layoutParams);
        ProgressView progressView = new ProgressView(context);
        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        relativeLayout.addView(progressView, relativeLayoutParams);
        alertDialog.setContentView(relativeLayout);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        alertDialog.show();
    }
}
