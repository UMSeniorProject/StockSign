package com.seniorproject.stocksign.debugging;

import android.util.Log;
/**
 * Used for Debugging and writing messages to LogCat
 * 
 * @author Sean
 * @since 1.0
 */
public class Debugger {
		/**Writes an info message to LogCat @param tag The tag of message @param message The message text*/
	    public final static  void info (String TAG, String MESSAGE){
	        Log.i(TAG, MESSAGE);
	}
	    /**Writes a warning message to LogCat @param tag The tag of message @param message The message text*/
	    public final static  void warning (String TAG, String MESSAGE){
	        Log.w(TAG, MESSAGE);
	}
	    /**Writes an error message to LogCat @param tag The tag of message @param message The message text*/
	    public final static  void error (String TAG, String MESSAGE){
	        Log.e(TAG, MESSAGE);
	}
	    /**Writes a debug message to LogCat @param tag The tag of message @param message The message text*/
	    public final static  void debug (String TAG, String MESSAGE){
	        Log.d(TAG, MESSAGE);
	}
	    /**Writes a verbose message to LogCat @param tag The tag of message @param message The message text*/
	    public final static  void verbose (String TAG, String MESSAGE){
	        Log.v(TAG, MESSAGE);
	}

}
