package com.seniorproject.stocksign.activity;

import com.seniorproject.stocksign.R;

import android.app.ProgressDialog;
import android.content.Context;

public class MySpinner  {

	private ProgressDialog progress;

	public void showLoadingDialog(Context context, String title, String msg) {

	    if (progress == null) {
	        progress = new ProgressDialog(context);
	        progress.setTitle(title);
	        progress.setMessage(msg);
	    }
	    progress.show();
	}

	public void dismissLoadingDialog() {

	    if (progress != null && progress.isShowing()) {
	        progress.dismiss();
	    }
	}
	
}
