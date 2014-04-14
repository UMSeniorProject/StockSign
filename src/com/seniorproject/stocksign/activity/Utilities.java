package com.seniorproject.stocksign.activity;

import java.math.BigDecimal;

import android.content.Context;
import android.widget.Toast;

public class Utilities {
	   
		public static float round(float d, int decimalPlace) {
	        BigDecimal bd = new BigDecimal(Float.toString(d));
	        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
	        return bd.floatValue();
	    }
		
		public static void displayToast(Context context, String toastMessage, String ticker) {
			int duration = Toast.LENGTH_SHORT;
			toastMessage = toastMessage + " " + ticker;
			Toast.makeText(context, toastMessage, duration).show();
		}
}
