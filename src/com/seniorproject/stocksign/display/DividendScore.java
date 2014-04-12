package com.seniorproject.stocksign.display;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.kinvey.android.Client;
import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.activity.ApplicationConstants;
import com.seniorproject.stocksign.kinveyconnection.KinveyConnectionSingleton;

public class DividendScore extends Activity {

	Client mKinveyClient = null;
	private TableLayout ratioTable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dividendscore);

		mKinveyClient = KinveyConnectionSingleton.getKinveyClient();
		initialize();
		displayData(getIntent());

		TextView title = (TextView) findViewById(R.id.section_label);

		title.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DividendScore.this,
						DividendScoreDescription.class);
				startActivity(intent);
			}
		});

	}

	private void initialize() {
		// TODO Auto-generated method stub
		TextView hotTextView = (TextView) findViewById(R.id.section_label);
		hotTextView.setText(ApplicationConstants.DIV_SCORE_STOCKS_TITLE);
		ratioTable = (TableLayout) findViewById(R.id.tlDivScores);
	}
	
	public void displayData(Intent intent) {
		Bundle bundle = intent.getBundleExtra(ApplicationConstants.SCORES_BUNDLE);
		String[] tickers = bundle.getStringArray(ApplicationConstants.TICKER_ARRAY);
		String[] scores = bundle.getStringArray(ApplicationConstants.SCORES_ARRAY);
		int[] alternatingRGBColor = new int[3];
		int alternator = 0;

		//ERROR CHECK FOR TICKERS.LENGTH VS SCORE.LENGTH??
		
		// Go through each item in the array
		for (int current = 0; current < tickers.length; current++) {
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
			TableRow tr = new TableRow(DividendScore.this);
			tr.setId(100 + current);
			tr.setBackgroundColor(Color.rgb(alternatingRGBColor[0],
					alternatingRGBColor[1], alternatingRGBColor[2]));
			tr.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT));

			// Create a TextView to hold the label of the ratio
			TextView labelTV = new TextView(DividendScore.this);
			labelTV.setId(200 + current);
			labelTV.setText(tickers[current]);
			labelTV.setTextColor(Color.BLACK);
			labelTV.setTextSize(17);
			labelTV.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT));
			labelTV.setGravity(Gravity.CENTER);
			tr.addView(labelTV);

			// Create a TextView to hold the value of the ratio
			TextView valueTV = new TextView(DividendScore.this);
			valueTV.setId(300 + current);
			valueTV.setText(scores[current]);
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
			addClick(tr, tickers[current]);

		}
	}
	
	public void kinveyResponceMethod(Object[] data) {
		
	}
	
	private void addClick(TableRow tr, final String ticker) {
		// TODO Auto-generated method stub
		tr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DividendScore.this,
						DisplayStockRatioData.class);
				Bundle b = new Bundle();
				b.putString(ApplicationConstants.TICKER_SINGLE, ticker);
				intent.putExtra(ApplicationConstants.RATIO_BUNDLE, b);
				startActivity(intent);
			}

		});
	}
}