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
		//everything they have
		/*return "http://finviz.com/export.ashx?v=151&c=0,1,2,3,4,5,6,7,8,9,10," +
				"11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30," +
				"31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50," +
				"51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68";*/
		
		//what we need
		return "http://finviz.com/export.ashx?v=151&c=0,1,2,3,4,5,7,8,9,10," +
		"11,12,13,14,15,16,18,19,20,21,22,23,26,27,28,29,30," +
		"31,32,33,34,35,36,37,38,39,40,41,59";
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
		
	    String [] nextLine = new String[40];
	     
	    
	    
	    StockDataSource datasource  = MainActivity.datasource;
	    
	    Debugger.info("dl ratio data", "open db");
	    datasource.open();
	    
	    
	    
		try {
			while ((nextLine = csvreader.readNext()) != null) {
			    
				Stock stock = new Stock();
				stock.setId(Integer.valueOf(nextLine[0]));
				stock.setTicker(nextLine[1]);
				stock.setCompany(nextLine[2]);
				stock.setSector(nextLine[3]);
				stock.setIndustry(nextLine[4]);
				stock.setCountry(nextLine[5]);
				stock.setPe(nextLine[6]);
				stock.setForward_pe(nextLine[7]);
				stock.setPeg(nextLine[8]);
				stock.setPs(nextLine[9]);
				stock.setPb(nextLine[10]);
				//System.out.println(csvreader.readNext().length);
				
				stock.setPc(nextLine[11]);
				stock.setPriceFreeCashFlow(nextLine[12]);
				stock.setDividendYield(nextLine[13]);
				stock.setPayoutRatio(nextLine[14]);
		
				stock.setEpsgThisYear(nextLine[15]);
				//nextline[16] eps growth next year
				stock.setEpsgPast5Years(nextLine[17]);
				stock.setEpsgNext5Years(nextLine[18]);
				stock.setSalesgPast5Years(nextLine[19]);
				stock.setEpsg(nextLine[20]);
				stock.setSalesg(nextLine[21]);
				stock.setInsiderOwnership(nextLine[22]);
				//nextline[23] insider transaction
				stock.setInstitutionalTransactions(nextLine[24]);
				stock.setFloatShort(nextLine[25]);
				stock.setShortRatio(nextLine[26]);
		//
				
				stock.setReturnOnAssets(nextLine[27]);
				stock.setReturnOnEquity(nextLine[28]);
				stock.setReturnOnInvestment(nextLine[29]);
				stock.setCurrentRatio(nextLine[30]);
				stock.setQuickRatio(nextLine[31]);
				stock.setLtDebtEquity(nextLine[32]);
				stock.setGrossMargin(nextLine[33]);
				stock.setOperatingMargin(nextLine[34]);
				//Profit Margin 35
				stock.setRsi(nextLine[36]);
				
				
				
				datasource.createStock(stock);
				
				//Debugger.info("database", "entered");
				// nextLine[] is an array of values from the line
			    //System.out.println(nextLine[0] + nextLine[1] + "etc...");
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    datasource.close();
	    Debugger.info("dl ratio data", "db closed");
		return null;
	}
	
	   protected void onPostExecute() {

		  
		    
	    }
}
