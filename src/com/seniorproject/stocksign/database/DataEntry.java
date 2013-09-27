/**
 * 
 */
package com.seniorproject.stocksign.database;

import java.util.Calendar;
import java.util.Date;
import com.seniorproject.stocksign.debugging.Debugger;


/**
 * Manages fetching the data and entering into database
 * @author Sean
 * @since 1.0
 */
public class DataEntry {
	static Date date = new Date();
	static Calendar cal = Calendar.getInstance();
	
	public static String createURL(String ticker){
		
		
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DATE);
		int year = cal.get(Calendar.YEAR);
		
		
		Debugger.info("Date", "Month: "+ String.valueOf(month) + " Day: "+String.valueOf(day)+ " Year: "+String.valueOf(year));
		
		return null;
	}
	

}
