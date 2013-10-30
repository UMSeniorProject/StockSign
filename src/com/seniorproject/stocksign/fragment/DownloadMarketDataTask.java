/**
 * 
 */
package com.seniorproject.stocksign.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.database.CSVReader;
import com.seniorproject.stocksign.debugging.Debugger;


import android.os.AsyncTask;
import android.widget.TextView;

/**
 * @author Sean
 *
 */
public class DownloadMarketDataTask extends AsyncTask<String, Integer, String[]> {

	
	/* DownloadMarketDataTask(){
		 
	 }*/

	


	@Override
	protected String[] doInBackground(String... params) {
		// TODO Auto-generated method stub
		String ticker = params[0];

		
		String url = createURL(ticker);
		
		
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
		String [] nextLine = null;
		
	    try {

			nextLine = csvreader.readNext();
			    // nextLine[] is an array of values from the line
			    System.out.println(nextLine[0] + " " + nextLine[1]);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nextLine;
	}
	
	  protected void onPostExecute(String[] result) {
	      
	    //System.out.println(result[0]);
	      
		MarketSectionFragment.change = result[0];
		MarketSectionFragment.close = result[1];
	  
	  }

	public static String createURL(String ticker){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("http://finance.yahoo.com/d/quotes.csv?");
		sb.append("s=" + ticker);
		sb.append("&f=cp");
		
		//http://finance.yahoo.com/d/quotes.csv?s=DJI&f=cp
		
		Debugger.info("DownloadData: url= ", sb.toString());
		return sb.toString();
	
	}



}
