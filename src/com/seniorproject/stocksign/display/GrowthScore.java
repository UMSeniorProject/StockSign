package com.seniorproject.stocksign.display;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.activity.ActivityConstants;
import com.seniorproject.stocksign.database.Stock;
import com.seniorproject.stocksign.kinveyconnection.KinveyConnectionSingleton;

public class GrowthScore extends Activity {

	Client mKinveyClient = null;
	String StockDataTableName = "StockRatioDataTable";

	static Stock[] stocks = null;
	int maxNamesDisplayed = 50;
	private TableLayout ratioTable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.growthscore);

		mKinveyClient = KinveyConnectionSingleton.getKinveyClient();
		initialize(getIntent());
		kinveyDataFetcher();

	}

	private void kinveyDataFetcher() {
		// TODO Auto-generated method stub
		Query fetchRank = mKinveyClient.query();
		Query fetchRequest = new Query(); // mKinveyClient.query();

		// QUERY MUST BE CHANGED
		// fetchRank.startsWith("PE", "9");

		fetchRequest.startsWith("Ticker", "G");

		fetchRequest.setLimit(maxNamesDisplayed);

		kinveyFetchQuery(fetchRequest);
	}

	// method to fetch specific Query from Kinvey
	private void kinveyFetchQuery(Query fetchQuery) {
		AsyncAppData<Stock> myData = mKinveyClient.appData(StockDataTableName,
				Stock.class);
		myData.get(fetchQuery, new KinveyListCallback<Stock>() {
			@Override
			public void onSuccess(Stock[] arg0) {
				// TODO Auto-generated method stub
				Log.i("success", "got: " + arg0.toString());
				if (arg0.length == 0) {
					stocks = null;
					System.out.println("STOCKS IS: NULL");
				} else {
					stocks = arg0;
					int[] alternatingRGBColor = new int[3];
					int alternator = 0;

					// Go through each item in the array
					for (int current = 0; current < maxNamesDisplayed; current++) {
						// display all ratios besides Sector and Industry

						if (alternator == 0) {
							alternatingRGBColor[0] = 209;// red
							alternatingRGBColor[1] = 209;// green
							alternatingRGBColor[2] = 209;// blue
							alternator = 1;
						} else {
							alternatingRGBColor[0] = 232;// red
							alternatingRGBColor[1] = 232;// green
							alternatingRGBColor[2] = 232;// blue
							alternator = 0;
						}

						// Create a TableRow and give it an ID
						TableRow tr = new TableRow(GrowthScore.this);
						tr.setId(100 + current);
						tr.setBackgroundColor(Color.rgb(alternatingRGBColor[0],
								alternatingRGBColor[1], alternatingRGBColor[2]));
						tr.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT));

						// Create a TextView to hold the label of the ratio
						TextView labelTV = new TextView(GrowthScore.this);
						labelTV.setId(200 + current);
						labelTV.setText(stocks[current].getTicker());
						labelTV.setTextColor(Color.BLACK);
						labelTV.setTextSize(17);
						labelTV.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT));
						labelTV.setGravity(Gravity.CENTER);
						tr.addView(labelTV);

						// Create a TextView to hold the value of the ratio
						TextView valueTV = new TextView(GrowthScore.this);
						valueTV.setId(300 + current);
						valueTV.setText(stocks[current].getRsi());
						valueTV.setTextColor(Color.BLACK);
						valueTV.setTextSize(17);
						valueTV.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT));
						valueTV.setGravity(Gravity.CENTER);
						tr.addView(valueTV);

						// Add the TableRow to the TableLayout
						ratioTable.addView(tr, new TableLayout.LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						addClick(tr, stocks[current]);

					}
					// displayAutoComplete();
					System.out.println("STOCKS IS: " + stocks[0].getCompany());

				}
			}

			private void addClick(TableRow tr, final Stock stock) {
				// TODO Auto-generated method stub
				tr.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						String[] values = stock.values().toString().split(",");
						String[] keySet = stock.keySet().toString().split(",");

						Intent i = new Intent(GrowthScore.this,
								DisplayStockRatioData.class);
						Bundle b = new Bundle();
						b.putStringArray("values", values);
						b.putStringArray("keySet", keySet);
						i.putExtra("ratioData", b);
						i.putExtra("activityID",
								ActivityConstants.SearchStockActivity); // send
																		// the
																		// activity
																		// id
						startActivity(i);

					}

				});
			}

			@Override
			public void onFailure(Throwable error) {
				Log.d("fail", "failed to fetchByFilterCriteria: "
						+ error.getCause().getMessage());
			}

		});

	}

	private void initialize(Intent i) {
		// TODO Auto-generated method stub
		TextView hotTextView = (TextView) findViewById(R.id.section_label);
		hotTextView.setText("Growth Score");

		ratioTable = (TableLayout) findViewById(R.id.tlGrowthScores);

	}
}