/**
 * 
 */
package com.seniorproject.stocksign.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.os.AsyncTask;

import com.seniorproject.stocksign.activity.MainActivity;
import com.seniorproject.stocksign.debugging.Debugger;


/**
 * Manages fetching the price data in a separate async thread
 * @author Sean
 * @since 1.0
 */

public class DownloadPriceDataTask extends AsyncTask<String, Integer, String>{
	static Date date = new Date();
	static Calendar cal = Calendar.getInstance();
	
	/**
	 * Create url to get .csv file of price data form yahoo
	 * @param ticker
	 * @return url
	 */
	public static String createPriceURL(String ticker){
		
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DATE);
		int year = cal.get(Calendar.YEAR);
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("http://ichart.finance.yahoo.com/table.csv?");
		sb.append("s=" + ticker);
		sb.append("&d="+ String.valueOf(month));
		sb.append("&e="+ String.valueOf(day));
		sb.append("&f="+ String.valueOf(year));
		sb.append("&g=d");
		sb.append("&a=8");
		sb.append("&b=10");
		sb.append("&c=2013");
		sb.append("&ignore=.csv");
		//
		

		Debugger.info("DownloadData: url= ", sb.toString());
		
		Debugger.info("DownloadData: Date", "Month: "+ String.valueOf(month) + " Day: "+String.valueOf(day)+ " Year: "+String.valueOf(year));
		//http://ichart.finance.yahoo.com/table.csv?s=GOOG&d=9&e=28&f=2013r&g=d&a=8&b=10&c=2013&ignore=.csv
		
		return sb.toString();
	}

	
	protected void onProgressUpdate() {
        
    }
	
    protected void onPostExecute() {

	        
	    
    }


	
	protected String doInBackground(String... params){
		String ticker = params[0];
		
		String url = createPriceURL(ticker);
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet(url);
		
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpGet, localContext);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		CSVReader csvreader = new CSVReader(reader);
		
		Debugger.info("bg", "Got here");
		
	    String [] nextLine;
	    PriceDataSource datasource  = MainActivity.pdatasource;
	    Debugger.info("dl price data", "open db");
	    datasource.open();
	    try {
	    	
	    	
			while ((nextLine = csvreader.readNext()) != null) {
			    // nextLine[] is an array of values from the line
			    System.out.println(nextLine.length);
			    Stock stock = new Stock();
			    stock.setDate(nextLine[0]);
			    stock.setOpen(nextLine[1]);
			    stock.setHigh(nextLine[2]);
			    stock.setLow(nextLine[3]);
			    stock.setClose(nextLine[4]);
			    stock.setVolume(nextLine[5]);
			    stock.setAdjclose(nextLine[6]);
			    
			    datasource.createStock(stock);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
	    datasource.open();
	    Debugger.info("dl price data", "db closed");
		return null;
	}


	





	
	

}
