package com.seniorproject.stocksign.kinveyconnection;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.android.callback.KinveyPingCallback;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.Query;
import com.kinvey.java.User;
import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.activity.ActivityConstants;
import com.seniorproject.stocksign.database.Stock;
import com.seniorproject.stocksign.display.DisplayStockRatioData;

public class ConnectToKinveyTask implements ActivityConstants{
		
	Stock[] stocks;
	Client mKinveyClient;
	Activity callingActivity;
	boolean conn_success;
	String StockDataTableName = "StockRatioDataTable";
	
	public ConnectToKinveyTask() {
		// TODO Auto-generated constructor stub
		  mKinveyClient = KinveyConnectionSingleton.getKinveyClient();
	}	  
	
	public void setCallingActivity(Activity currentCallingActivity) {		
		  callingActivity =  currentCallingActivity;
	}

	public boolean testKinveyService() {
		 // call `myClient.user().logout().execute() first -or- check `myClient.user().isUserLoggedIn()` before attempting to login again
	    	
		  boolean login = mKinveyClient.user().isUserLoggedIn();
			
		  	if(login) {
		  		//ping (test) the connection to Kinvey
				mKinveyClient.ping(new KinveyPingCallback() {
				    public void onFailure(Throwable t) {
				        Log.e("KinveyPing", "Kinvey Ping Failed", t);
				        conn_success = false;
				    }
				    public void onSuccess(Boolean b) {
				        Log.d("KinveyPing", "Kinvey Ping Success");
				        conn_success = true;
				    }
				});
		  	}
		  	else {	    
		  		//login the explicit user
		  		mKinveyClient.user().login(new KinveyUserCallback() {
		    	    @Override
		    	    public void onFailure(Throwable error) {
		    	        Log.e("KinveyLogin", "Login Failure: "+error.getCause().getMessage(), error);
		    	        conn_success = false;
		    	    }
		    	    
		    	    @Override
		    	    public void onSuccess(User result) {
		    	        Log.i("KinveyLogin","Logged in successfully as " + result.getId());
		    	        mKinveyClient.ping(new KinveyPingCallback() {
		    	            @Override
		    	            public void onSuccess(Boolean result) {
		    	                Toast.makeText(callingActivity.getApplicationContext(), "kinvey ping success!",
		    	                        Toast.LENGTH_LONG).show();
		    	                conn_success = true;
		    	            }

		    	            @Override
		    	            public void onFailure(Throwable error) {
		    	                Toast.makeText(callingActivity.getApplicationContext(),
		    	                        "kinvey ping failed!",
		    	                        Toast.LENGTH_LONG).show();
		    	                conn_success = false;
		    	            }
		    	        });
		    	    }
		    	});
		  	}
		  	return conn_success;
		  }		
	
	//method to fetch from Kinvey
	private void kinveyFetchAll() {
		AsyncAppData<Stock> myData = mKinveyClient.appData(StockDataTableName, Stock.class);
			myData.get(new KinveyListCallback<Stock>() {
				@Override
				public void onSuccess(Stock[] arg0) {
					// TODO Auto-generated method stub
					Log.i("success","got: "+arg0.toString());
					stocks = arg0;
				}
				@Override
				public void onFailure(Throwable error) { 
					Log.d("fail", "failed to fetchByFilterCriteria: "+error.getCause().getMessage());
				}

			});
	}
	
	//connect
	public void kinveyAllDataFetcher() {
		kinveyFetchAll();
	}
	
	public Stock[] getStockData() {
		return stocks;
	}
}


