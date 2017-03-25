package com.android.prowarenesstestapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.android.prowarenesstestapp.R;


public class Utils {


	/**
	 * check internet connectivity
	 *
	 * @param context
	 * @return
	 */
	public static boolean isNetWorkAvailable(Context context) {

		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		try {
			if (connMgr == null) {
				return false;
			} else if (connMgr.getActiveNetworkInfo() != null && connMgr.getActiveNetworkInfo().isAvailable() && connMgr.getActiveNetworkInfo().isConnected()) {
				return true;
			} else {

				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}


	/**
	 * display progress dialog
	 *
	 * @param mContext
	 * @return
	 */
	public static Dialog showProgressDialog(Context mContext)
	{
		Dialog progressDialog = new Dialog(mContext);
		progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		progressDialog.setContentView(R.layout.progress_layout);
		progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		progressDialog.setCancelable(false);
		progressDialog.show();
		return progressDialog;
	}




	/**
	 * cancel progress dialog
	 * @param progressDialog
	 */
	public static void cancelProgressDialog(Dialog progressDialog)
	{
		if (progressDialog != null && progressDialog.isShowing())
		{
			progressDialog.dismiss();
			progressDialog = null;
		}
	}


	/**
	 * Show Snack Bar
	 * @param parentLayout
	 * @param msg
	 */
	public static void showSnackBar(View parentLayout,String msg){

		Snackbar snack = Snackbar.make(parentLayout, msg, Snackbar.LENGTH_LONG);
		View view = snack.getView();
		FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
		params.gravity = Gravity.BOTTOM;
		view.setLayoutParams(params);
		snack.show();
	}

}