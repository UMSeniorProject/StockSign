package com.seniorproject.stocksign.activity;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.widget.Toast;

public class Utilities {

	public static float round(float d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}

	public static void displayToast(Context context, String toastMessage,
			String ticker) {
		int duration = Toast.LENGTH_SHORT;
		toastMessage = toastMessage + " " + ticker;
		Toast.makeText(context, toastMessage, duration).show();
	}

	public static int calculateWorkDays(Date startDate, Date endDate) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);

		int workDays = 0;

		if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
			startCal.setTime(endDate);
			endCal.setTime(startDate);
		}

		do {
			startCal.add(Calendar.DAY_OF_MONTH, 1);
			if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
					&& startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				workDays++;
			}
		} while (startCal.getTimeInMillis() <= endCal.getTimeInMillis());

		return workDays;
	}
}
