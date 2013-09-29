package com.seniorproject.stocksign.database;

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

import com.seniorproject.stocksign.activity.MainActivity;
import com.seniorproject.stocksign.debugging.Debugger;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Manages fetching the price data in a separate async thread
 * @author Sean
 * @since 1.0
 */

public class DownloadRatioDataTask extends AsyncTask<String, Integer, String>{
	
	

	public static String createRatioURL(String ticker){
		
		return "http://finviz.com/export.ashx?v=152&ft=4";
	}
	
	protected String doInBackground(String... params){
		String ticker = params[0];
		
		String url = createRatioURL(ticker);
		
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
	    
	    
	    StockDataSource datasource  = MainActivity.datasource;
	    Debugger.info("dl ratio data", "open db");
	    datasource.open();
	    
	    try {
	    	
	    	
	    	
	    	//read first entry
	    	csvreader.readNext();
			while ((nextLine = csvreader.readNext()) != null) {
			    
				Stock stock = new Stock();
				stock.setId(Integer.valueOf(nextLine[0]));
				stock.setTicker(nextLine[1]);
				stock.setCompany(nextLine[2]);
				stock.setSector(nextLine[3]);
				stock.setIndustry(nextLine[4]);
				stock.setCountry(nextLine[5]);
				/*stock.setPe(Float.valueOf(nextLine[6]));
				stock.setForward_pe(Float.valueOf(nextLine[7]));
				stock.setPeg(Float.valueOf(nextLine[8]));
				stock.setPs(Float.valueOf(nextLine[9]));
				stock.setPb(Float.valueOf(nextLine[10]));
				stock.setPc(Float.valueOf(nextLine[11]));
				stock.setPriceFreeCashFlow(Float.valueOf(nextLine[12]));
				stock.setDividendYield(Float.valueOf(nextLine[13]));
				//nextline[14] may not need eps ttm
				stock.setEpsgThisYear(Float.valueOf(nextLine[15]));
				//nextline[16] eps growth next year
				stock.setEpsgPast5Years(Float.valueOf(nextLine[17]));
				stock.setEpsgNext5Years(Float.valueOf(nextLine[18]));
				stock.setSalesgPast5Years(Float.valueOf(nextLine[19]));
				stock.setEpsg(Float.valueOf(nextLine[20]));
				stock.setSalesg(Float.valueOf(nextLine[21]));
				stock.setInsiderOwnership(Float.valueOf(nextLine[22]));
				//nextline[23] institutional transaction
				stock.setFloatShort(Float.valueOf(nextLine[24]));
				stock.setReturnOnAssets(Float.valueOf(nextLine[25]));
				stock.setReturnOnEquity(Float.valueOf(nextLine[26]));
				stock.setReturnOnInvestment(Float.valueOf(nextLine[27]));
				stock.setCurrentRatio(Float.valueOf(nextLine[28]));
				stock.setQuickRatio(Float.valueOf(nextLine[29]));
				stock.setLtDebtEquity(Float.valueOf(nextLine[30]));
				stock.setGrossMargin(Float.valueOf(nextLine[31]));
				stock.setOperatingMargin(Float.valueOf(nextLine[32]));
				//Profit Margin 33
				//Relative Strength Index 34
				//Price 35
				//Change36
				stock.setVolume(Float.valueOf(nextLine[37]));
				*/
				
				datasource.createStock(stock);
				
				Debugger.info("database", "entered");
				// nextLine[] is an array of values from the line
			    //System.out.println(nextLine[0] + nextLine[1] + "etc...");
			}
			//datasource.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
	    datasource.close();
	    Debugger.info("dl ratio data", "db closed");
		return null;
	}
	
	   protected void onPostExecute() {

		  
		    
	    }
}
