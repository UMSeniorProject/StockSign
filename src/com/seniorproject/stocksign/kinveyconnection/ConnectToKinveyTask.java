package com.seniorproject.stocksign.kinveyconnection;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.android.callback.KinveyPingCallback;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.Query;
import com.kinvey.java.User;
import com.kinvey.java.query.AbstractQuery.SortOrder;
import com.seniorproject.stocksign.activity.ActivityConstants;
import com.seniorproject.stocksign.activity.ApplicationConstants;
import com.seniorproject.stocksign.database.PriceData;
import com.seniorproject.stocksign.database.PriceDataStorage;
import com.seniorproject.stocksign.database.SectorData;
import com.seniorproject.stocksign.database.Stock;

public class ConnectToKinveyTask implements ActivityConstants{
		
	//private static Stock[] stocks;
	//private static PriceData[] priceData;
	private static Client mKinveyClient;
	private static Activity callingActivity;
	private static boolean conn_success;
	
	/*public ConnectToKinveyTask() {
		// TODO Auto-generated constructor stub
		  mKinveyClient = KinveyConnectionSingleton.getKinveyClient();
	}*/	  
	
	public static void setCallingActivity(Activity currentCallingActivity) {		
		  callingActivity =  currentCallingActivity;
	}

	public static boolean testKinveyService() {
		 // call `myClient.user().logout().execute() first -or- check `myClient.user().isUserLoggedIn()` before attempting to login again
	    
		  mKinveyClient = KinveyConnectionSingleton.getKinveyClient();
		
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
		    	        //Log.e("KinveyLogin", "Login Failure: "+error.getCause().getMessage(), error);
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
	public static void kinveyFetchAllStocks(String tableName, final Activity caller) {
		AsyncAppData<Stock> myData = mKinveyClient.appData(tableName, Stock.class);
			myData.get(new KinveyListCallback<Stock>() {
				@Override
				public void onSuccess(Stock[] arg0) {
					// TODO Auto-generated method stub
					Log.i("success","got: "+arg0.toString());
					//stocks = arg0;
					KinveyCaller.callAppropriateActivityMethod(caller, arg0);
				}
				@Override
				public void onFailure(Throwable error) { 
					Log.d("fail", "failed to fetchByFilterCriteria: "+error.getCause().getMessage());
				}

			});
	}
	
	//method to fetch Stock Query from Kinvey
	public static void kinveyFetchStockQuery(String tableName, Query fetchQuery, final Activity caller) {
		AsyncAppData<Stock> myData = mKinveyClient.appData(tableName, Stock.class);
			myData.get(fetchQuery, new KinveyListCallback<Stock>() {
				@Override
				public void onSuccess(Stock[] arg0) {
					// TODO Auto-generated method stub
					Log.i("success","got: "+arg0.toString());
					Log.i("caller", "StockQuery by " + caller.getLocalClassName());
					if(arg0.length==0) {
						//stocks = null;
						arg0 = null;
					}
					else { 
						//stocks = arg0;
					}					
					KinveyCaller.callAppropriateActivityMethod(caller, arg0);
				}
				@Override
				public void onFailure(Throwable error) { 
					Log.d("fail", "failed to fetchByFilterCriteria: "+error.getCause().getMessage());
				}

			});
	}
	
	public static void kinveyFetchPriceQuery(String tableName, int startDate, final Activity caller) {
		/*if(PriceDataStorage.currentStock != null && tableName.compareTo(PriceDataStorage.currentStock) == 0) {
			Log.i("KINVEYPRICE","already have price data for " + tableName);
			return;
		}*/
		Query getQuery = mKinveyClient.query();
		getQuery.setLimit(ApplicationConstants.PRICE_DATA_SECTION_LIMIT);
		if(startDate != 0) {
			getQuery.greaterThanEqualTo("Date", startDate).addSort("Date", SortOrder.ASC);
		}
		AsyncAppData<PriceData> myData = mKinveyClient.appData(tableName, PriceData.class);
			myData.get(getQuery, new KinveyListCallback<PriceData>() {
				@Override
				public void onSuccess(PriceData[] arg0) {
					// TODO Auto-generated method stub
					Log.i("caller", "PriceQuery by " + caller.getLocalClassName());
					if(arg0.length==0) {
						//priceData = null;
						arg0 = null;
						Log.i("KINVEYPRICE","got: null");
					}
					else { 
						Log.i("KINVEYPRICE","got: "+arg0.toString());
						//priceData = arg0;
						KinveyCaller.callAppropriateActivityMethod(caller, arg0);
					}					
				}
				@Override
				public void onFailure(Throwable error) { 
					Throwable cause = error.getCause();
					if(cause != null) {
						Log.d("KinveyFail", "failed to fetchByFilterCriteria: "+error.getCause().getMessage());
					} else {
						Log.d("KinveyFail", "failed to fetchByFilterCriteria: "+error.getCause());
					}
				}

			});
	}
	
	// method to fetch specific Query from Kinvey
	public static void kinveyFetchFragmentQuery(final Fragment fragment, Query fetchQuery, final View rv, final String scoreType) {
		AsyncAppData<Stock> myData = mKinveyClient.appData(ApplicationConstants.RATIO_TABLE,
				Stock.class);
		myData.get(fetchQuery, new KinveyListCallback<Stock>() {
			@Override
			public void onSuccess(Stock[] arg0) {
				// TODO Auto-generated method stub

				Log.e("success", "got: " + arg0.toString());
				Log.i("caller", "FragmentQuery" + fragment.getClass().getSimpleName());
				if (arg0.length == 0) {
					//stocks = null;
					arg0 = null;
				} else {
					//stocks = arg0;
				}
				KinveyCaller.callAppropriateFragmentMethod(fragment, arg0, rv, scoreType);
			}

			@Override
			public void onFailure(Throwable error) {
				Log.d("fail", "failed to fetchByFilterCriteria: "
						+ error.getCause().getMessage());
			}

		});
	}
	
	//method to fetch Stock Query from Kinvey
	public static void kinveyFetchSectorData() {
		AsyncAppData<Stock> myData = mKinveyClient.appData(
				ApplicationConstants.SECTOR_TABLE, Stock.class);
			myData.get(new KinveyListCallback<Stock>() {
				@Override
				public void onSuccess(Stock[] arg0) {
					// TODO Auto-generated method stub
					if(arg0.length==0) {
						//stocks = null;
						arg0 = null;
						Log.i("error","got: null");
					}
					else { 
						//stocks = arg0;
						Log.i("success","got: "+arg0.toString());
					}					
					SectorData.setData(arg0);
				}
				@Override
				public void onFailure(Throwable error) { 
					Log.d("fail", "failed to fetchByFilterCriteria: "+error.getCause().getMessage());
				}

			});
	}
}


