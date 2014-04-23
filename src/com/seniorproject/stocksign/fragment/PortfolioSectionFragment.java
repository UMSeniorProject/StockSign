package com.seniorproject.stocksign.fragment;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.kinvey.android.Client;
import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.activity.ApplicationConstants;
import com.seniorproject.stocksign.activity.Utilities;
import com.seniorproject.stocksign.display.DisplayStockRatioData;
import com.seniorproject.stocksign.display.GrowthScore;
import com.seniorproject.stocksign.kinveyconnection.KinveyConnectionSingleton;
import com.seniorproject.stocksign.searching.SearchStockActivity;

import android.R.drawable;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

public class PortfolioSectionFragment extends Fragment {

	Client mKinveyClient = null;
	Activity fragmentActivity = null;
	SharedPreferences portfolioData = null;
	TableLayout portfolioTable = null;
	Button addStock = null;

	/** Should not be instantiated, empty constructor */
	public PortfolioSectionFragment() {
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_portfolio,
				container, false);

		fragmentActivity = this.getActivity();
		portfolioData = fragmentActivity.getSharedPreferences(
				ApplicationConstants.PORTFOLIO_PREFERENCES, 0);

		// portfolioData.edit().clear().commit();

		portfolioTable = (TableLayout) rootView
				.findViewById(R.id.tlPortfolioScores);
		addStock = (Button) rootView.findViewById(R.id.bAddStock);

		addStock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (fragmentActivity != null) {
					Intent doSearch = new Intent(fragmentActivity,
							SearchStockActivity.class);
					startActivity(doSearch);
				} else {
					Log.e("PORTFOLIO",
							"fragment activity is null when addStock button is clicked");
				}
			}

		});

		TextView hotTextView = (TextView) rootView
				.findViewById(R.id.section_label);
		hotTextView.setText(ApplicationConstants.PORTFOLIO_TITLE);

		return rootView;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getAndDisplayTable();
	}

	private void getAndDisplayTable() {
		/*
		 * Map<String, Set<String>> pStocks = (Map<String, Set<String>>)
		 * portfolioData.getAll();
		 */

		TreeMap<String, ?> stocks = new TreeMap<String, Object>(
				portfolioData.getAll());
		displayData(stocks);
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	private void deleteAllPortfolioRows() {
		portfolioTable.removeAllViews();
		portfolioTable.invalidate();
	}

	private void deletePortfolioRow(String tag) {
		View removedRow = portfolioTable.findViewWithTag(tag);
		portfolioTable.removeView(removedRow);
		portfolioTable.invalidate();
	}

	//functio to add the initial row containing column names
	private void addColumnNames() {
		int mainId = 999;
		int tickerId = 555;
		int totalScoreId = 666;
		int divScoreId = 777;
		int growthScoreId = 888;
		int dummyId = 0;
		
		// Create a TableRow and give it an ID
		TableRow tr = new TableRow(fragmentActivity);
		tr.setId(mainId);
		//tr.setBackgroundColor(Color.rgb(232, 232, 232));
		TableRow.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 0, 0, 3);
		tr.setLayoutParams(params);

		// Create a TextView to hold the ticker column name
		TextView tickerTV = new TextView(fragmentActivity);
		tickerTV.setId(tickerId);
		tickerTV.setText(getString(R.string.title_ticker));
		tickerTV.setTextColor(Color.BLACK);
		tickerTV.setTextSize(11);
		tickerTV.setTypeface(null, Typeface.BOLD);
		tickerTV.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT));
		tickerTV.setGravity(Gravity.LEFT);
		tr.addView(tickerTV);
		
		// Create a TextView to hold total score column name
		TextView totalTV = new TextView(fragmentActivity);
		totalTV.setId(totalScoreId);
		totalTV.setText(getString(R.string.score_total));
		totalTV.setTextColor(Color.BLACK);
		totalTV.setTextSize(11);
		totalTV.setTypeface(null, Typeface.BOLD);
		totalTV.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT));
		totalTV.setGravity(Gravity.LEFT);
		tr.addView(totalTV);
		
		// Create a TextView to hold dividend score column name
		TextView divTV = new TextView(fragmentActivity);
		divTV.setId(divScoreId);
		divTV.setText(getString(R.string.score_div));
		divTV.setTextColor(Color.BLACK);
		divTV.setTextSize(11);
		divTV.setTypeface(null, Typeface.BOLD);
		divTV.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT));
		divTV.setGravity(Gravity.LEFT);
		tr.addView(divTV);
		
		// Create a TextView to hold growth score column name
		TextView growthTV = new TextView(fragmentActivity);
		growthTV.setId(growthScoreId);
		growthTV.setText(getString(R.string.score_growth));
		growthTV.setTextColor(Color.BLACK);
		growthTV.setTextSize(11);
		growthTV.setTypeface(null, Typeface.BOLD);
		growthTV.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT));
		growthTV.setGravity(Gravity.LEFT);
		tr.addView(growthTV);
		
		// Create a TextView to hold the dummy column
		TextView dummyTV = new TextView(fragmentActivity);
		dummyTV.setId(dummyId);
		dummyTV.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT));
		dummyTV.setGravity(Gravity.LEFT);
		tr.addView(dummyTV);
		
		portfolioTable.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	}

	private void displayData(TreeMap<String, ?> stocks) {
		if (stocks == null) {
			Log.e("PORTFOLIO", "portfolio fragment stocks is null");
			return;
		}

		Set<String> tickers = stocks.keySet();

		if (tickers == null) {
			Log.e("PORTFOLIO", "keys don't exist");
			return;
		}

		Log.d("REMOVINGCHILDREN", "newline");
		for (String tick : tickers) {
			Log.d("REMOVINGCHILDREN", "ticker = " + tick);
		}
		deleteAllPortfolioRows();
		
		addColumnNames();

		int[] alternatingRGBColor = new int[3];
		int alternator = 0;
		int idCounter = 0;

		// Go through each item in the array
		for (String ticker : tickers) {

			@SuppressWarnings("unchecked")
			Set<String> scores = (Set<String>) stocks.get(ticker);

			if (scores == null || scores.size() < 3) {
				Log.e("PORTFOLIO", "missing scores for ticker " + ticker);
				return;
			}

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
			TableRow tr = new TableRow(fragmentActivity);
			tr.setId(100 + idCounter);
			tr.setTag(ticker);
			tr.setBackgroundColor(Color.rgb(alternatingRGBColor[0],
					alternatingRGBColor[1], alternatingRGBColor[2]));
			tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT));

			// Create a TextView to hold the label of the ticker
			TextView tickerTV = new TextView(fragmentActivity);
			tickerTV.setId(200 + idCounter);
			tickerTV.setText(ticker);
			tickerTV.setTextColor(Color.BLACK);
			tickerTV.setTextSize(17);
			tickerTV.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT));
			tickerTV.setGravity(Gravity.LEFT);
			tickerTV.setBackgroundResource(drawable.list_selector_background);
			tr.addView(tickerTV);

			// Create TextViews to hold values of each score
			for (String score : scores) {
				TextView scoreTV = new TextView(fragmentActivity);
				scoreTV.setId(300 + idCounter);
				/*
				 * we use a substring for score value due to the extra character
				 * sent from the display ratio page do distinguish scores that
				 * are equivalent
				 */
				scoreTV.setText(score.substring(0, score.length() - 1));
				scoreTV.setTextColor(Color.BLACK);
				scoreTV.setTextSize(17);
				scoreTV.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT));
				scoreTV.setGravity(Gravity.LEFT);
				tr.addView(scoreTV);
				idCounter++;
			}

			TextView deleteRowTV = new TextView(fragmentActivity);
			deleteRowTV.setId(400 + idCounter);
			deleteRowTV.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT));
			deleteRowTV.setGravity(Gravity.RIGHT);
			deleteRowTV.setBackgroundResource(R.drawable.selector_discard);
			tr.addView(deleteRowTV);
			addDeleteClick(deleteRowTV, ticker);

			// Add the TableRow to the TableLayout
			portfolioTable.addView(tr, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			addClick(tickerTV, ticker);

			idCounter++;
		}
	}

	private void addDeleteClick(TextView tv, final String ticker) {
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.d("PORTFOLIO", "ticker is " + ticker);
				deletePortfolioRow(ticker);
				Utilities.displayToast(fragmentActivity,
						ApplicationConstants.PF_REM, ticker);
				portfolioData.edit().remove(ticker).commit();
				getAndDisplayTable();
			}

		});
	}

	private void addClick(TextView tv, final String ticker) {
		// TODO Auto-generated method stub
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(fragmentActivity,
						DisplayStockRatioData.class);
				Bundle bundle = new Bundle();
				bundle.putString(ApplicationConstants.TICKER_SINGLE, ticker);
				intent.putExtra(ApplicationConstants.RATIO_BUNDLE, bundle);
				startActivity(intent);
			}

		});
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewStateRestored(savedInstanceState);
	}

}
