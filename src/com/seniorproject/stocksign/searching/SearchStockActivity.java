package com.seniorproject.stocksign.searching;


import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.kinvey.java.query.AbstractQuery.SortOrder;
import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.activity.ActivityConstants;
import com.seniorproject.stocksign.activity.ApplicationConstants;
import com.seniorproject.stocksign.database.PriceData;
import com.seniorproject.stocksign.database.Stock;
import com.seniorproject.stocksign.database.TickerTrie;
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
import android.widget.Toast;

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
	Activity caller;
	ArrayAdapter<String> adapter;
	ProgressBar loadingCircle;

	AutoCompleteTextView searchTerm;
	TextWatcher watcher;
	String[] companies;
	
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
					  	if(s.toString().length()!=0) {		
					  		searchData = s.toString().toUpperCase(Locale.ENGLISH);//searchTerm.getText().toString().toUpperCase();
					  		Object[] objects = TickerTrie.getMatches(searchData, 
					  				ApplicationConstants.AUTOCOMPLETE_ROW_LIMIT).toArray();
					  		companies = Arrays.copyOf(objects, objects.length, String[].class);
					  		displayAutoComplete();
					  	}
		            }
	
		            @Override
		            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		            }
	
		            @Override
		            public void afterTextChanged(Editable s) {

		            }

			});
			searchTerm.setOnItemClickListener(new OnItemClickListener() {
				
				/*
				parent		The AdapterView where the click happened.
				view		The view within the AdapterView that was clicked (this will be a view provided by the adapter)
				position	The position of the view in the adapter.
				id			The row id of the item that was clicked. 
				 */
				
				//OnClickListener for the AutoSuggested Dropdown list
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					//AutoCompleteTextView tickerView = (AutoCompleteTextView) view;
					//String ticker = tickerView.getText().toString();
					String ticker = companies[position];
					
					//get the price data for this ticker
					//THIS PART PREPARES FOR GRAPHING SECTION
					//ConnectToKinveyTask.kinveyFetchPriceQuery(ticker, 0, caller);
					
					Intent intent = new Intent(SearchStockActivity.this, DisplayStockRatioData.class);
					Bundle b = new Bundle();
					b.putString(ApplicationConstants.TICKER_SINGLE, ticker);
					intent.putExtra(ApplicationConstants.RATIO_BUNDLE, b);
					//i.putExtra("activityID", ActivityConstants.SearchStockActivity); //send the activity id					
					startActivity(intent);
					//
					/*Stock clickedStock = stocks[position];			
					searchTerm.setText(clickedStock.getCompany());
					String[] values  = clickedStock.values().toString().split(",");
					String[] keySet = clickedStock.keySet().toString().split(",");
					
					Intent i = new Intent(SearchStockActivity.this, DisplayStockRatioData.class);
					Bundle b = new Bundle();
					b.putStringArray("values", values);
					b.putStringArray("keySet", keySet);
					i.putExtra("ratioData", b);
					i.putExtra("activityID", ActivityConstants.SearchStockActivity); //send the activity id					
					startActivity(i); 
					*/
				}
				
			});
	}		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mKinveyClient = KinveyConnectionSingleton.getKinveyClient();
		caller = this;
		setContentView(R.layout.search);
		initializeListeners();
	}			
	
	private void displayAutoComplete() {
		adapter = 
			new ArrayAdapter<String>(SearchStockActivity.this, android.R.layout.simple_dropdown_item_1line, companies);
		//adapter.addAll(companyNames);
		searchTerm.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
}
