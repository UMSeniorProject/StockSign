/**
 * 
 */
package com.seniorproject.stocksign.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.kinvey.java.query.AbstractQuery.SortOrder;
import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.activity.ActivityConstants;
import com.seniorproject.stocksign.database.Stock;
import com.seniorproject.stocksign.debugging.Debugger;
import com.seniorproject.stocksign.display.DisplayStockRatioData;
import com.seniorproject.stocksign.kinveyconnection.KinveyConnectionSingleton;
import com.seniorproject.stocksign.searching.SearchStockActivity;

/**
 * @author Sean
 *
 */
public class HotStocksSectionFragment extends Fragment {
	

	Client mKinveyClient = null;
	String StockDataTableName = "StockRatioDataTable";
	
	static Stock[] stocks = null;
	int maxNamesDisplayed = 3;
	
	/**Should not be instantiated, empty constructor */
	public HotStocksSectionFragment() {
	}

	/**
	 * Called when fragment is starting, where most inistialization should go
	 * @param 
	 * @return rootView The view to be displayed
	 * */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_hotstocks,
				container, false);
		
		mKinveyClient = KinveyConnectionSingleton.getKinveyClient();
		kinveyDataFetcher(rootView);
		
		TextView hotTextView = (TextView) rootView.findViewById(R.id.section_label);
		hotTextView.setText("Top Rated Stocks");
		
		

		//System.out.println("STOCKS IS: " + stocks.length);
		


		return rootView;
	}
	
	
	//method to fetch specific Query from Kinvey
		private void kinveyFetchQuery(Query fetchQuery, final View rv) {
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
							//displayAutoComplete();
							System.out.println("STOCKS IS: " + stocks[0].getCompany());
							
							TextView stock1 = (TextView) rv.findViewById(R.id.stock1);
							TextView stock2 = (TextView) rv.findViewById(R.id.stock2);
							TextView stock3 = (TextView) rv.findViewById(R.id.stock3);
							
							TextView grade1 = (TextView) rv.findViewById(R.id.stock1_grade);
							TextView grade2 = (TextView) rv.findViewById(R.id.stock2_grade);
							TextView grade3 = (TextView) rv.findViewById(R.id.stock3_grade);
							
							stock1.setText(stocks[0].getTicker());
							stock2.setText(stocks[1].getTicker());
							stock3.setText(stocks[2].getTicker());
							
							grade1.setText(stocks[0].getRsi());
							grade2.setText(stocks[1].getRsi());
							grade3.setText(stocks[2].getRsi());
							
							stock1.setOnClickListener(new OnClickListener(){

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									
									
									
									String[] values  = stocks[0].values().toString().split(",");
									String[] keySet = stocks[0].keySet().toString().split(",");
									
									Intent i = new Intent(getActivity(), DisplayStockRatioData.class);
									Bundle b = new Bundle();
									b.putStringArray("values", values);
									b.putStringArray("keySet", keySet);
									i.putExtra("ratioData", b);
									i.putExtra("activityID", ActivityConstants.SearchStockActivity); //send the activity id					
									startActivity(i);
									
									
								}
								
							});
							
							stock2.setOnClickListener(new OnClickListener(){

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									
									
									
									String[] values  = stocks[1].values().toString().split(",");
									String[] keySet = stocks[1].keySet().toString().split(",");
									
									Intent i = new Intent(getActivity(), DisplayStockRatioData.class);
									Bundle b = new Bundle();
									b.putStringArray("values", values);
									b.putStringArray("keySet", keySet);
									i.putExtra("ratioData", b);
									i.putExtra("activityID", ActivityConstants.SearchStockActivity); //send the activity id					
									startActivity(i);
									
									
								}
								
							});
							
							stock3.setOnClickListener(new OnClickListener(){

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									
									
									
									String[] values  = stocks[2].values().toString().split(",");
									String[] keySet = stocks[2].keySet().toString().split(",");
									
									Intent i = new Intent(getActivity(), DisplayStockRatioData.class);
									Bundle b = new Bundle();
									b.putStringArray("values", values);
									b.putStringArray("keySet", keySet);
									i.putExtra("ratioData", b);
									i.putExtra("activityID", ActivityConstants.SearchStockActivity); //send the activity id					
									startActivity(i);
									
									
								}
								
							});
							
						}					
					}
					@Override
					public void onFailure(Throwable error) { 
						Log.d("fail", "failed to fetchByFilterCriteria: "+error.getCause().getMessage());
					}

				});
				
		}
	
		
	
	
		//fetch data
		private void kinveyDataFetcher(View rv) {
			Query fetchRank = mKinveyClient.query();
			Query fetchLastLetter = new Query(); //mKinveyClient.query();
			
			
			//QUERY MUST BE CHANGED
			//fetchRank.startsWith("PE", "9");
			fetchLastLetter.startsWith("Ticker", "M");
			
			fetchRank.setLimit(maxNamesDisplayed);
			fetchLastLetter.setLimit(maxNamesDisplayed);

			kinveyFetchQuery(fetchLastLetter,rv);
			
		}
		
		
		
		
}