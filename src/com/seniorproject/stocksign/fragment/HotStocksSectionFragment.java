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
import android.widget.TextView;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.activity.ActivityConstants;
import com.seniorproject.stocksign.database.Stock;
import com.seniorproject.stocksign.display.DisplayStockRatioData;
import com.seniorproject.stocksign.display.DividendScore;
import com.seniorproject.stocksign.display.GrowthScore;
import com.seniorproject.stocksign.display.TotalScore;
import com.seniorproject.stocksign.kinveyconnection.KinveyConnectionSingleton;

/**
 * @author Sean
 * 
 */
public class HotStocksSectionFragment extends Fragment {

	Client mKinveyClient = null;
	String StockDataTableName = "StockDataTable";

	static Stock[] stocks = null;
	int maxNamesDisplayed = 3;

	/** Should not be instantiated, empty constructor */
	public HotStocksSectionFragment() {
	}

	/**
	 * Called when fragment is starting, where most inistialization should go
	 * 
	 * @param
	 * @return rootView The view to be displayed
	 * */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_hotstocks,
				container, false);

		mKinveyClient = KinveyConnectionSingleton.getKinveyClient();
		kinveyDataFetcher(rootView);

		TextView hotTextView = (TextView) rootView
				.findViewById(R.id.section_label);
		hotTextView.setText("Top Rated Stocks");

		TextView gscore = (TextView) rootView.findViewById(R.id.growthscore);
		TextView tscore = (TextView) rootView.findViewById(R.id.totalscore);
		TextView dscore = (TextView) rootView.findViewById(R.id.dividendscore);

		gscore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), GrowthScore.class);
				startActivity(i);
			}
		});
		tscore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), TotalScore.class);
				startActivity(i);
			}
		});
		dscore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), DividendScore.class);
				startActivity(i);
			}
		});

		// System.out.println("STOCKS IS: " + stocks.length);

		return rootView;
	}

	// method to fetch specific Query from Kinvey
	private void kinveyFetchTotalQuery(Query fetchQuery, final View rv) {
		AsyncAppData<Stock> myData = mKinveyClient.appData(StockDataTableName,
				Stock.class);
		myData.get(fetchQuery, new KinveyListCallback<Stock>() {
			@Override
			public void onSuccess(Stock[] arg0) {
				// TODO Auto-generated method stub
				Log.i("success", "got: " + arg0.toString());
				if (arg0.length == 0) {
					stocks = null;
				} else {
					stocks = arg0;
					// displayAutoComplete();
					System.out.println("STOCKS IS: " + stocks[0].getCompany());
					// total score table
					TextView tstock1 = (TextView) rv
							.findViewById(R.id.totalstock1);
					TextView tstock2 = (TextView) rv
							.findViewById(R.id.totalstock2);
					TextView tstock3 = (TextView) rv
							.findViewById(R.id.totalstock3);

					TextView tgrade1 = (TextView) rv
							.findViewById(R.id.stock1_total);
					TextView tgrade2 = (TextView) rv
							.findViewById(R.id.stock2_total);
					TextView tgrade3 = (TextView) rv
							.findViewById(R.id.stock3_total);

					tstock1.setText(stocks[0].getTicker());
					tstock2.setText(stocks[1].getTicker());
					tstock3.setText(stocks[2].getTicker());

					tgrade1.setText(String.valueOf(stocks[0].getTotalScore()));
					tgrade2.setText(String.valueOf(stocks[1].getTotalScore()));
					tgrade3.setText(String.valueOf(stocks[2].getTotalScore()));

					addClick(tstock1, stocks[0]);
					addClick(tstock2, stocks[1]);
					addClick(tstock3, stocks[2]);

				}
			}

			@Override
			public void onFailure(Throwable error) {
				Log.d("fail", "failed to fetchByFilterCriteria: "
						+ error.getCause().getMessage());
			}

		});

	}

	// method to fetch specific Query from Kinvey
	private void kinveyFetchDivQuery(Query fetchQuery, final View rv) {
		AsyncAppData<Stock> myData = mKinveyClient.appData(StockDataTableName,
				Stock.class);
		myData.get(fetchQuery, new KinveyListCallback<Stock>() {
			@Override
			public void onSuccess(Stock[] arg0) {
				// TODO Auto-generated method stub
				Log.i("success", "got: " + arg0.toString());
				if (arg0.length == 0) {
					stocks = null;
				} else {
					stocks = arg0;
					// displayAutoComplete();
					System.out.println("STOCKS IS: " + stocks[0].getCompany());
					// div score table
					TextView divstock1 = (TextView) rv
							.findViewById(R.id.divstock1);
					TextView divstock2 = (TextView) rv
							.findViewById(R.id.divstock2);
					TextView divstock3 = (TextView) rv
							.findViewById(R.id.divstock3);

					TextView divgrade1 = (TextView) rv
							.findViewById(R.id.stock1_div);
					TextView divgrade2 = (TextView) rv
							.findViewById(R.id.stock2_div);
					TextView divgrade3 = (TextView) rv
							.findViewById(R.id.stock3_div);

					divstock1.setText(stocks[0].getTicker());
					divstock2.setText(stocks[1].getTicker());
					divstock3.setText(stocks[2].getTicker());

					divgrade1.setText(String.valueOf(stocks[0].getDivScore()));
					divgrade2.setText(String.valueOf(stocks[1].getDivScore()));
					divgrade3.setText(String.valueOf(stocks[2].getDivScore()));

					addClick(divstock1, stocks[0]);
					addClick(divstock2, stocks[1]);
					addClick(divstock3, stocks[2]);

				}
			}

			@Override
			public void onFailure(Throwable error) {
				Log.d("fail", "failed to fetchByFilterCriteria: "
						+ error.getCause().getMessage());
			}

		});

	}

	// method to fetch specific Query from Kinvey
	private void kinveyFetchGrowthQuery(Query fetchQuery, final View rv) {
		AsyncAppData<Stock> myData = mKinveyClient.appData(StockDataTableName,
				Stock.class);
		myData.get(fetchQuery, new KinveyListCallback<Stock>() {
			@Override
			public void onSuccess(Stock[] arg0) {
				// TODO Auto-generated method stub
				Log.i("success", "got: " + arg0.toString());
				if (arg0.length == 0) {
					stocks = null;
				} else {
					stocks = arg0;
					// displayAutoComplete();
					System.out.println("STOCKS IS: " + stocks[0].getCompany());
					// div score table
					TextView divstock1 = (TextView) rv
							.findViewById(R.id.divstock1);
					TextView divstock2 = (TextView) rv
							.findViewById(R.id.divstock2);
					TextView divstock3 = (TextView) rv
							.findViewById(R.id.divstock3);

					TextView divgrade1 = (TextView) rv
							.findViewById(R.id.stock1_div);
					TextView divgrade2 = (TextView) rv
							.findViewById(R.id.stock2_div);
					TextView divgrade3 = (TextView) rv
							.findViewById(R.id.stock3_div);

					divstock1.setText(stocks[0].getTicker());
					divstock2.setText(stocks[1].getTicker());
					divstock3.setText(stocks[2].getTicker());

					divgrade1.setText(String.valueOf(stocks[0].getGrowthScore()));
					divgrade2.setText(String.valueOf(stocks[1].getGrowthScore()));
					divgrade3.setText(String.valueOf(stocks[2].getGrowthScore()));

					addClick(divstock1, stocks[0]);
					addClick(divstock2, stocks[1]);
					addClick(divstock3, stocks[2]);

				}
			}

			@Override
			public void onFailure(Throwable error) {
				Log.d("fail", "failed to fetchByFilterCriteria: "
						+ error.getCause().getMessage());
			}

		});

	}

	// fetch data
	private void kinveyDataFetcher(View rv) {
		Query fetchRank = mKinveyClient.query();
		Query fetchTopTotal = new Query(); // mKinveyClient.query();
		Query fetchTopDiv = new Query();
		Query fetchTopGrowth = new Query();

		// QUERY MUST BE CHANGED
		// fetchRank.startsWith("PE", "9");

		fetchTopTotal.startsWith("Ticker", "T");
		fetchTopDiv.startsWith("Ticker", "D");
		fetchTopGrowth.startsWith("Ticker", "G");

		fetchTopDiv.setLimit(maxNamesDisplayed);
		fetchTopTotal.setLimit(maxNamesDisplayed);
		fetchTopGrowth.setLimit(maxNamesDisplayed);

		kinveyFetchTotalQuery(fetchTopTotal, rv);
		kinveyFetchGrowthQuery(fetchTopGrowth, rv);
		kinveyFetchDivQuery(fetchTopDiv, rv);

	}

	private void addClick(TextView tv, final Stock s) {
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String[] values = s.values().toString().split(",");
				String[] keySet = s.keySet().toString().split(",");

				Intent i = new Intent(getActivity(),
						DisplayStockRatioData.class);
				Bundle b = new Bundle();
				b.putStringArray("values", values);
				b.putStringArray("keySet", keySet);
				i.putExtra("ratioData", b);
				i.putExtra("activityID", ActivityConstants.SearchStockActivity); // send
																					// the
																					// activity
																					// id
				startActivity(i);

			}

		});

	}

}