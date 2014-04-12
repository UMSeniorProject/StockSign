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
import com.seniorproject.stocksign.activity.Prefs;
import com.seniorproject.stocksign.debugging.Debugger;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Manages fetching the ratio data in a separate async thread
 * @author Sean
 * @since 1.0
 */

public class DownloadRatioDataTask extends AsyncTask<String, Integer, String>{
	
	private Context currContext;
	ProgressDialog pd;
	
	public DownloadRatioDataTask(Context context){
		currContext = context;
	}
/**
 * Creates the finviz url to download the ratio data.
 * 
 * @return The url for the ratio request
 */
	public static String createRatioURL(){
		//everything they have
		/*return "http://finviz.com/export.ashx?v=151&c=0,1,2,3,4,5,6,7,8,9,10," +
				"11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30," +
				"31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50," +
				"51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68";*/
		
		//what we need
		return "http://finviz.com/export.ashx?v=151&c=0,1,2,3,4,5,7,8,9,10," +
		"11,12,13,14,15,17,18,19,20,21,22,23,26,27,28,29,30," +
		"31,32,33,34,35,36,37,39,40,41,59";
	}
	
	
	
	@Override
	protected void onProgressUpdate(Integer... progress) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(progress);
	
		pd.incrementProgressBy(progress[0]);
	}



	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		pd = new ProgressDialog(currContext);
		pd.setMessage("Downloading stock data...");
		pd.setProgressNumberFormat(null);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setCancelable(false);
		pd.setMax(6668);
		pd.show();
	}
	protected String doInBackground(String... params){
		
		
		String url = createRatioURL();
		
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
	    StockDataSource.open();
	    

	    
		try {
			while ((nextLine = csvreader.readNext()) != null) {
			    
				Stock stock = new Stock();
				
				//System.out.println(nextLine.length);
				stock.setId(nextLine[0]);
				stock.setTicker(nextLine[1]);
				stock.setCompany(nextLine[2]);
				stock.setSector(nextLine[3]);
				stock.setIndustry(nextLine[4]);
				stock.setCountry(nextLine[5]);
				stock.setPe(Float.valueOf(nextLine[6]));
				stock.setFpe(Float.valueOf(nextLine[7]));
				stock.setPeg(Float.valueOf(nextLine[8]));
				stock.setPs(Float.valueOf(nextLine[9]));
				stock.setPb(Float.valueOf(nextLine[10]));
				
				
				stock.setPc(Float.valueOf(nextLine[11]));
				stock.setPfcf(Float.valueOf(nextLine[12]));
				stock.setDyield(Float.valueOf(nextLine[13]));
				stock.setPo(Float.valueOf(nextLine[14]));
		
				stock.setEpsthisyr(Float.valueOf(nextLine[15]));
				//nextline[16] eps growth next year
				stock.setEpspast5yr(Float.valueOf(nextLine[17]));
				stock.setEpsnext5yr(Float.valueOf(nextLine[18]));
				stock.setSalespast5yr(Float.valueOf(nextLine[19]));
				stock.setEpsg_qoq(Float.valueOf(nextLine[20]));
				stock.setSalesg_qoq(Float.valueOf(nextLine[21]));
				stock.setInsiown(Float.valueOf(nextLine[22]));
				//nextline[23] insider transaction
				//Institutional Ownership nextLine[24]
				stock.setInstown(Float.valueOf(nextLine[25]));
				stock.setSfloat(Float.valueOf(nextLine[26]));
				stock.setSr(Float.valueOf(nextLine[27]));
		//
				
				stock.setRoa(Float.valueOf(nextLine[28]));
				stock.setRoe(Float.valueOf(nextLine[29]));
				stock.setRoi(Float.valueOf(nextLine[30]));
				stock.setCr(Float.valueOf(nextLine[31]));
				
				stock.setQr(Float.valueOf(nextLine[32]));
				stock.setLtde(Float.valueOf(nextLine[33]));
				stock.setGrossMargin(Float.valueOf(nextLine[34]));
				stock.setOperatingMargin(Float.valueOf(nextLine[35]));
				//Profit Margin 36
				stock.setRsi(Float.valueOf(nextLine[37]));
				
				
				
				datasource.createStock(stock);
				publishProgress(1);
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

	    StockDataSource.close();
	    Debugger.info("dl ratio data", "db closed");
	    pd.dismiss();
		return null;
	}
	
	   protected void onPostExecute() {

		  
		    
	    }
}
