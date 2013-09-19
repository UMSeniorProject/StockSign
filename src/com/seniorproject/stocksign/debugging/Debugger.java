package com.seniorproject.stocksign.debugging;

import android.util.Log;

public class Debugger {

	    public final static  void info (String TAG, String MESSAGE){
	        Log.i(TAG, MESSAGE);
	}
	    public final static  void warning (String TAG, String MESSAGE){
	        Log.w(TAG, MESSAGE);
	}
	    public final static  void error (String TAG, String MESSAGE){
	        Log.e(TAG, MESSAGE);
	}
	    public final static  void debug (String TAG, String MESSAGE){
	        Log.d(TAG, MESSAGE);
	}
	    public final static  void verbose (String TAG, String MESSAGE){
	        Log.v(TAG, MESSAGE);
	}

}
