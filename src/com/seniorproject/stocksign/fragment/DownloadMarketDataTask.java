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
import com.seniorproject.stocksign.activity.MainActivity;
import com.seniorproject.stocksign.database.CSVReader;
import com.seniorproject.stocksign.debugging.Debugger;


import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

/**
 * @author Sean
 *
 */
public class DownloadMarketDataTask extends AsyncTask<String, Integer, String[]> {

	static String close = "Unavailiabe" ;
	static String change = close;
	static String ticker;
	static Activity callingActivity;
	static View rootView;
	static int position;
	 DownloadMarketDataTask(Activity current_callingActivity, View rview, int pos){
		 callingActivity = current_callingActivity;
		 rootView = rview;
		 position = pos;
	 }

	


	@Override
	protected String[] doInBackground(String... params) {
		// TODO Auto-generated method stub
		 ticker = params[0];

		
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
			Debugger.info("Result of "+ticker+" download", nextLine[0] + " " + nextLine[1]);
			   
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nextLine;
	}
	
	  protected void onPostExecute(String[] result) {
	      
	    //System.out.println("lookatthis"+result[0]);
	      
		 change = result[0];
		 close = result[1];
		 if(ticker == "IXIC"){
	        	TextView nasChangeView = (TextView) rootView.findViewById(R.id.nasdaq_change);
	        	System.out.println(close+" case 1");
	        	nasChangeView.setText(close);
	        
	        	TextView nasCloseView = (TextView) rootView.findViewById(R.id.nasdaq_close);
	        	nasCloseView.setText(change);
		 }
		 else if (ticker == "GSPC"){
	        	TextView spChangeView = (TextView) rootView.findViewById(R.id.sp_change);
	        	System.out.println(close+" case 2");
	        	spChangeView.setText(close);
	        
	        	TextView spCloseView = (TextView) rootView.findViewById(R.id.sp_close);
	        	spCloseView.setText(change);
		 }
		 else{}
	       
		 /*switch(position){
	        
	        case 2 :
	        	
	        	
	        	TextView nasChangeView = (TextView) rootView.findViewById(R.id.nasdaq_change);
	        	System.out.println(close+" case 1");
	        	nasChangeView.setText(close);
	        
	        	TextView nasCloseView = (TextView) rootView.findViewById(R.id.nasdaq_close);
	        	nasCloseView.setText(change);
	        	break;
	        
	        case 1:

	        	TextView spChangeView = (TextView) rootView.findViewById(R.id.sp_change);
	        	System.out.println(close+" case 2");
	        	spChangeView.setText(close);
	        
	        	TextView spCloseView = (TextView) rootView.findViewById(R.id.sp_close);
	        	spCloseView.setText(change);
	        	break;
	    	
	    	default:
	    		
	    	break;
	        }*/
	  
	  }

	public static String createURL(String ticker){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("http://finance.yahoo.com/d/quotes.csv?");
		sb.append("s=%5E" + ticker);
		sb.append("&f=cp");
		
		//http://finance.yahoo.com/d/quotes.csv?s=DJI&f=cp
		
		Debugger.info("DownloadData: url= ", sb.toString());
		return sb.toString();
	
	}




}
