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
	String StockDataTableName = "StockRatioDataTable";

	static Stock[] stocks = null;
	int maxNamesDisplayed = 9;

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
	private void kinveyFetchQuery(Query fetchQuery, final View rv) {
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

					tgrade1.setText(stocks[0].getRsi());
					tgrade2.setText(stocks[1].getRsi());
					tgrade3.setText(stocks[2].getRsi());

					addClick(tstock1, stocks[0]);
					addClick(tstock2, stocks[1]);
					addClick(tstock3, stocks[2]);

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

					divstock1.setText(stocks[3].getTicker());
					divstock2.setText(stocks[4].getTicker());
					divstock3.setText(stocks[5].getTicker());

					divgrade1.setText(stocks[3].getRsi());
					divgrade2.setText(stocks[4].getRsi());
					divgrade3.setText(stocks[5].getRsi());

					addClick(divstock1, stocks[3]);
					addClick(divstock2, stocks[4]);
					addClick(divstock3, stocks[5]);

					// growth score table
					// div score table
					TextView gstock1 = (TextView) rv
							.findViewById(R.id.growthstock1);
					TextView gstock2 = (TextView) rv
							.findViewById(R.id.growthstock2);
					TextView gstock3 = (TextView) rv
							.findViewById(R.id.growthstock3);

					TextView ggrade1 = (TextView) rv
							.findViewById(R.id.stock1_growth);
					TextView ggrade2 = (TextView) rv
							.findViewById(R.id.stock2_growth);
					TextView ggrade3 = (TextView) rv
							.findViewById(R.id.stock3_growth);

					gstock1.setText(stocks[6].getTicker());
					gstock2.setText(stocks[7].getTicker());
					gstock3.setText(stocks[8].getTicker());

					ggrade1.setText(stocks[6].getRsi());
					ggrade2.setText(stocks[7].getRsi());
					ggrade3.setText(stocks[8].getRsi());

					addClick(gstock1, stocks[6]);
					addClick(gstock2, stocks[7]);
					addClick(gstock3, stocks[8]);

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
		Query fetchLastLetter = new Query(); // mKinveyClient.query();

		// QUERY MUST BE CHANGED
		// fetchRank.startsWith("PE", "9");

		fetchLastLetter.startsWith("Ticker", "M");

		fetchRank.setLimit(maxNamesDisplayed);
		fetchLastLetter.setLimit(maxNamesDisplayed);

		kinveyFetchQuery(fetchLastLetter, rv);

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