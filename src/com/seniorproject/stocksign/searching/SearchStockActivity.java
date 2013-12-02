package com.seniorproject.stocksign.searching;


import java.io.IOException;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.kinvey.java.query.AbstractQuery.SortOrder;
import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.activity.ActivityConstants;
import com.seniorproject.stocksign.database.Stock;
import com.seniorproject.stocksign.display.CommonStockClass;
import com.seniorproject.stocksign.display.DisplayStockRatioData;
import com.seniorproject.stocksign.kinveyconnection.ConnectToKinveyTask;
import com.seniorproject.stocksign.kinveyconnection.KinveyConnectionSingleton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/*
You can perform textual searches on fields using Regular Expressions. 
This can be done with Query.regex. For example, to filter a table view
by event name using a search bar:
	EditText searchBar = (EditText) findViewById(R.id.search_bar);
	Query query = new Query();
	query.regEx("name","searchText");
	AsyncAppData<EventEntity> searchedEvents = mKinveyClient.appData("events", EventEntity.class);
	searchedEvents.get(query, new KinveyListCallback<EventEntity>() {
		@Override
		public void onSuccess(EventEntity[] event) { ... }
	});
 */

public class SearchStockActivity extends Activity{

	Client mKinveyClient = null;
	String StockDataTableName = "StockRatioDataTable";
	ArrayAdapter<String> adapter;
	
	ProgressBar loadingCircle;
	
	long type1time = 0, type2time = 0, currtypetime;
	int switchtime = 1;
	
	AutoCompleteTextView searchTerm;
	TextWatcher watcher;
	int maxNamesDisplayed = 8;
	String[] companyNames;
	
	Stock[] stocks = null;
	
	String searchData;	
	
	/*
	private class ProgressTask extends AsyncTask <void,void,Void>{
	    @Override
	    protected void onPreExecute(){
	        loadingCircle.setVisibility(View.VISIBLE);
	    }

	    @Override
	    protected void doInBackground(Void... arg0) {   
	           //my stuff is here
	    }

	    @Override
	    protected void onPostExecute(Void result) {
	          loadingCircle.setVisibility(View.GONE);
	    }
	}*/
	

	
	private void initializeListeners() {
		// TODO Auto-generated method stub
		loadingCircle = (ProgressBar) findViewById(R.id.progress);
		searchTerm = (AutoCompleteTextView) findViewById(R.id.tvAutocomplete);
		searchTerm.setThreshold(1);	
		searchTerm.addTextChangedListener(new TextWatcher() {
				  @Override
		            public void onTextChanged(CharSequence s, int start, int before, int count) {
					  	if(s.length()!=0) {		  		
					  		/*if(switchtime==1) {
					  			type1time = System.currentTimeMillis();
					  			switchtime=2;
					  		}
					  		else {
					  			type2time = System.currentTimeMillis();
					  			switchtime=1;
					  		}*/
					  		
					  		//if(Math.abs(type1time-type2time)>100) {
					  		//One issues is that autocomplete has to be case-sensitive
					  		searchData = s.toString();//.toUpperCase();//searchTerm.getText().toString().toUpperCase();
					  		kinveyDataFetcher(searchData);
					  		//}
					  	}
		            }
	
		            @Override
		            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	
		                // TODO Auto-generated method stub
		            }
	
		            @Override
		            public void afterTextChanged(Editable s) {
		            		int checkWhenThisIsHappening;
		            		checkWhenThisIsHappening = 1;
		            }

			});
			searchTerm.setOnItemClickListener(new OnItemClickListener() {
				
				/*
				parent		The AdapterView where the click happened.
				view		The view within the AdapterView that was clicked (this will be a view provided by the adapter)
				position	The position of the view in the adapter.
				id			The row id of the item that was clicked. 
				 */
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Stock clickedStock = stocks[position];	
					String[] values  = clickedStock.values().toString().split(",");
					String[] keySet = clickedStock.keySet().toString().split(",");
					
					Intent i = new Intent(SearchStockActivity.this, DisplayStockRatioData.class);
					Bundle b = new Bundle();
					b.putStringArray("values", values);
					b.putStringArray("keySet", keySet);
					i.putExtra("ratioData", b);
					i.putExtra("activityID", ActivityConstants.SearchStockActivity); //send the activity id					
					startActivity(i);
				}
				
			});
	}		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mKinveyClient = KinveyConnectionSingleton.getKinveyClient();
		setContentView(R.layout.search);
		initializeListeners();
	}			
	
	private void displayAutoComplete() {
		int listLength = maxNamesDisplayed;
		/*if(!adapter.isEmpty())
			adapter.clear();	//delete all previously stored data*/
		if(stocks.length<maxNamesDisplayed)
			listLength = stocks.length;
		companyNames = new String[listLength];
		for(int i=0;i<listLength;i++) {
			companyNames[i] = stocks[i].getCompany().toString();
		}
		adapter = 
			new ArrayAdapter<String>(SearchStockActivity.this, android.R.layout.simple_dropdown_item_1line, companyNames);		
		//adapter.addAll(companyNames);
		searchTerm.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	
	//method to fetch specific Query from Kinvey
	private void kinveyFetchQuery(Query fetchQuery) {
		AsyncAppData<Stock> myData = mKinveyClient.appData(StockDataTableName, Stock.class);
			myData.get(fetchQuery, new KinveyListCallback<Stock>() {
				@Override
				public void onSuccess(Stock[] arg0) {
					// TODO Auto-generated method stub
					Log.i("success","got: "+arg0.toString());
					if(arg0.length==0) {
						stocks = null;
					}
					else { 
						stocks = arg0;
						displayAutoComplete();
					}					
				}
				@Override
				public void onFailure(Throwable error) { 
					Log.d("fail", "failed to fetchByFilterCriteria: "+error.getCause().getMessage());
				}

			});
	}
	
	//fetch data
	private void kinveyDataFetcher(String searchString) {
		Query fetchCompany = mKinveyClient.query();
		Query fetchTicker = new Query(); //mKinveyClient.query();
		
		fetchCompany.startsWith("Company", searchString);
		fetchTicker.startsWith("Ticker", searchString);
		
		fetchCompany.setLimit(maxNamesDisplayed);
		fetchTicker.setLimit(maxNamesDisplayed);
		
		fetchCompany.addSort("Company", SortOrder.ASC);
		fetchTicker.addSort("Ticker", SortOrder.ASC);
		
		/*fetchQuery.startsWith(searchCategory, searchString);
		fetchQuery.setLimit(maxNamesDisplayed);
		fetchQuery.addSort(searchCategory, SortOrder.ASC);
		kinveyFetchQuery(fetchQuery);*/
		kinveyFetchQuery(fetchCompany.or(fetchTicker));
		//kinveyFetchQuery(fetchCompany);
	}
}
