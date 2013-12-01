package com.seniorproject.stocksign.searching;


import com.kinvey.android.Client;
import com.kinvey.java.Query;
import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.database.Stock;
import com.seniorproject.stocksign.kinveyconnection.ConnectToKinveyTask;
import com.seniorproject.stocksign.kinveyconnection.KinveyConnectionSingleton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchStockActivity extends Activity implements OnClickListener{
	
	ConnectToKinveyTask conn_kinvey = null;;
	Client mKinveyClient = null;
	
	EditText searchId;
	EditText searchTerm;
	Button searchAction;
	
	Stock[] stocks = null;
	
	String searchData;
	String searchResult;
	
	/*public void doSearch() {
		//String searchId = "Ticker";
		//String searchTerm = "GOOG";
		
		String[] sb = new String[2];
		sb[0] = searchId;
		sb[1] = searchTerm;

		Bundle sendData = new Bundle();
		sendData.putStringArray("search-action", sb);
		Intent doSearch = new Intent(SearchStockActivity.this,ConnectToKinveyTask.class);
		doSearch.putExtras(sendData);
		startActivityForResult(doSearch, 1);
	}*/
	
	private void initializeListeners() {
		// TODO Auto-generated method stub
		searchTerm = (EditText) findViewById(R.id.etSearchTerm);
		searchAction = (Button) findViewById(R.id.bSearchAction);
		searchAction.setOnClickListener(this);		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mKinveyClient = KinveyConnectionSingleton.getKinveyClient();
		conn_kinvey = new ConnectToKinveyTask();
		conn_kinvey.setCallingActivity(this);
		setContentView(R.layout.search);
		initializeListeners();
	
		//Bundle gotData = getIntent().getExtras();	
		//searchData = gotData.getStringArray("search-action");
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		//Intent returnData = new Intent();			
		//Bundle searchResultBundle = new Bundle();
		
		searchData = searchTerm.getText().toString().toUpperCase();
		conn_kinvey.kinveyDataFetcher(searchData,"Ticker");		
		
		//searchResultBundle.putStringArray("search-result", searchResult);
		//returnData.putExtras(searchResult);
		//setResult(RESULT_OK, returnData);			
		//finish();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		stocks = conn_kinvey.getStockData();
	}
}
