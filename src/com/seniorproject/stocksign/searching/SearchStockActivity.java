package com.seniorproject.stocksign.searching;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.kinvey.java.query.AbstractQuery.SortOrder;
import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.activity.ActivityConstants;
import com.seniorproject.stocksign.activity.ApplicationConstants;
import com.seniorproject.stocksign.database.Companies;
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
import android.content.pm.ActivityInfo;
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
import android.view.Gravity;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
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

public class SearchStockActivity extends Activity {

	private Activity caller;
	private AutoCompleteTextView searchTerm;
	private List<String> companiesList;
	private ListView searchList;
	private SearchAdapter searchAdapter;

	private String searchData;

	/*
	 * private class ProgressTask extends AsyncTask <void,void,Void>{
	 * 
	 * @Override protected void onPreExecute(){
	 * loadingCircle.setVisibility(View.VISIBLE); }
	 * 
	 * @Override protected void doInBackground(Void... arg0) { //my stuff is
	 * here }
	 * 
	 * @Override protected void onPostExecute(Void result) {
	 * loadingCircle.setVisibility(View.GONE); } }
	 */

	private void initializeXML() {
		searchTerm = (AutoCompleteTextView) findViewById(R.id.tvAutocomplete);
		searchList = (ListView) findViewById(R.id.lvStockList);
	}

	private void initializeListeners() {
	
		
		searchTerm.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.toString().length() != 0) {
					searchData = s.toString().toUpperCase(Locale.ENGLISH);// searchTerm.getText().toString().toUpperCase();
					companiesList = TickerTrie.getMatches(searchData,
							ApplicationConstants.AUTOCOMPLETE_ROW_LIMIT);

				} else {
					companiesList.clear();
					//MAYBE SET INVISIBLE??
				}
				displayAutoComplete();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}

		});
		
		searchList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				
				String ticker = companiesList.get(position);
				Log.d("SEARCH", ticker);

				Intent intent = new Intent(SearchStockActivity.this,
						DisplayStockRatioData.class);
				Bundle b = new Bundle();
				b.putString(ApplicationConstants.TICKER_SINGLE, ticker);
				intent.putExtra(ApplicationConstants.RATIO_BUNDLE, b);
				// i.putExtra("activityID",
				// ActivityConstants.SearchStockActivity); //send the activity
				// id
				startActivity(intent);
				
			}
			
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		caller = this;
		setContentView(R.layout.search);
		initializeXML();
		initializeListeners();
	}

	private void displayAutoComplete() {
		searchAdapter = new SearchAdapter(this, companiesList);
		searchList.setAdapter(searchAdapter);	
	}
}
