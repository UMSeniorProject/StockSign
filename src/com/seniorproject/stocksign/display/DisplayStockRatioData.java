package com.seniorproject.stocksign.display;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer.FillOutsideLine;

import android.R.drawable;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.kinvey.java.query.AbstractQuery.SortOrder;
import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.activity.ApplicationConstants;
import com.seniorproject.stocksign.activity.MenuInflator;
import com.seniorproject.stocksign.activity.MySpinner;
import com.seniorproject.stocksign.activity.Utilities;
import com.seniorproject.stocksign.database.PriceData;
import com.seniorproject.stocksign.database.PriceDataStorage;
import com.seniorproject.stocksign.database.SectorData;
import com.seniorproject.stocksign.database.Stock;
import com.seniorproject.stocksign.graphing.ChartMethods;
import com.seniorproject.stocksign.graphing.GraphActivity;
import com.seniorproject.stocksign.kinveyconnection.ConnectToKinveyTask;
import com.seniorproject.stocksign.kinveyconnection.ConnectionDetector;
import com.seniorproject.stocksign.kinveyconnection.KinveyConnectionSingleton;
import com.seniorproject.stocksign.searching.SearchStockActivity;

public class DisplayStockRatioData extends Activity {

	private Context context = null;
	private Client mKinveyClient = null;

	private TextView stockName = null;
	private TextView stockTicker = null;
	private TextView stockInfo = null;
	private TextView stockTotalScore = null;
	private TextView stockDivScore = null;
	private TextView stockGrowthScore = null;

	private TableLayout ratioTable = null;

	private Button portfolioButton = null;
	private Button graphButton = null;

	private TableRow totalScoreRow = null;
	private TableRow divScoreRow = null;
	private TableRow growthScoreRow = null;

	private LinearLayout mainDisplayLayout = null;
	private RelativeLayout progressBarLayout = null;

	private SharedPreferences portfolioSettings = null;
	private SharedPreferences indicatorSettings = null;
	private Stock theStock = null;
	private boolean gotRatioData;

	private MenuInflator mInflator = null;

	// MySpinnerDialog dialog = null;

	private void initializeXML() {
		stockName = (TextView) findViewById(R.id.tvStockname);
		stockTicker = (TextView) findViewById(R.id.tvStockticker);
		stockInfo = (TextView) findViewById(R.id.tvStockInfoValue);
		stockTotalScore = (TextView) findViewById(R.id.tvStockTotalScoreValue);
		stockDivScore = (TextView) findViewById(R.id.tvStockDivScoreValue);
		stockGrowthScore = (TextView) findViewById(R.id.tvStockGrowthScoreValue);
		graphButton = (Button) findViewById(R.id.bGraph);
		graphButton.setEnabled(false);
		ratioTable = (TableLayout) findViewById(R.id.tlStockratios);
		portfolioButton = (Button) findViewById(R.id.bPortfolio);
		totalScoreRow = (TableRow) findViewById(R.id.trStockTotalScore);
		divScoreRow = (TableRow) findViewById(R.id.trStockDivScore);
		growthScoreRow = (TableRow) findViewById(R.id.trStockGrowthScore);
		mainDisplayLayout = (LinearLayout) findViewById(R.id.llDisplayStockData);
		progressBarLayout = (RelativeLayout) findViewById(R.id.rlRatioData);
	}

	private void fetchKinvey(String ticker) {
		if (!ticker.equals("missing")
				|| ConnectionDetector.isConnectingToInternet(this)) {
			/*
			 * obtain the kinvey client and send a query to get data for this
			 * ticker
			 */
			mKinveyClient = KinveyConnectionSingleton.getKinveyClient();
			kinveyStockDataFetcher(ticker);
			kinveyPriceDataFetcher(ticker);
		} else {
			Utilities.displayToast(this, "No Internet Connection", "");
		}
	}

	private void initialize(Intent intent) {
		/* remember the context of this activity */
		context = this;

		/* initialize the ratio data flag */
		gotRatioData = false;

		/* initialize the menu */
		mInflator = new MenuInflator(this);

		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		/* get ticker name sent to this activity */
		Bundle b = intent.getBundleExtra(ApplicationConstants.RATIO_BUNDLE);
		String ticker = b.getString(ApplicationConstants.TICKER_SINGLE);

		fetchKinvey(ticker);

		/* initialize variables relating to XML elements on this page */
		initializeXML();

		/* setup the listeners and actions taken for the add to portfolio button */
		setupPortfolioButtonActions();

		/* setup the listeners and actions taken for the display graph button */
		setupDisplayGraphActions();
	}

	private void setupPortfolioButtonActions() {
		portfolioButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				/* error check to make sure ticker exists */
				if (stockTicker == null || stockTicker.getText().length() == 0) {
					return;
				}

				String buttonText = portfolioButton.getText().toString();
				String portfolioTicker = stockTicker.getText().toString();

				portfolioSettings = getSharedPreferences(
						ApplicationConstants.PORTFOLIO_PREFERENCES, 0);

				SharedPreferences.Editor editor = portfolioSettings.edit();

				if (buttonText.equals(ApplicationConstants.ADD_PORTFOLIO)) {

					/* Adding stock to portfolio */
					portfolioButton.setText(ApplicationConstants.REM_PORTFOLIO);
					portfolioButton.setTextColor(Color
							.parseColor(ApplicationConstants.COLOR_RED));

					Set<String> scores = new LinkedHashSet<String>();
					/*
					 * add a character corresponding to the score name for two
					 * reasons: 1) to check if the score order matches on the
					 * portfolio page 2) to distinguish equal scores since a set
					 * doesn't allow duplicates
					 */
					scores.add(String.valueOf(stockTotalScore.getText()) + "t");
					scores.add(String.valueOf(stockDivScore.getText()) + "d");
					scores.add(String.valueOf(stockGrowthScore.getText()) + "g");

					Utilities.displayToast(context,
							ApplicationConstants.PF_ADD, portfolioTicker);
					editor.putStringSet(portfolioTicker, scores);

					Log.d("PORTFOLIO", "added");

				} else if (buttonText
						.equals(ApplicationConstants.REM_PORTFOLIO)) {

					/* Removing stock from portfolio */
					portfolioButton.setText(ApplicationConstants.ADD_PORTFOLIO);
					portfolioButton.setTextColor(Color
							.parseColor(ApplicationConstants.COLOR_GREEN));
					editor.remove(portfolioTicker);
					Utilities.displayToast(context,
							ApplicationConstants.PF_REM, portfolioTicker);
					Log.d("PORTFOLIO", "removed");

				} else {
					// Error
					portfolioButton.setTextColor(Color.CYAN);
					return;
				}

				// commit the edits
				editor.commit();

			}

		});
	}

	private void setupDisplayGraphActions() {
		graphButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					graphButton.setBackgroundResource(R.drawable.best_192x96_b);
					// call graphing method
					break;
				case MotionEvent.ACTION_UP:
					graphButton.setBackgroundResource(R.drawable.best_192x96);
					break;
				}
				// TODO Auto-generated method stub
				return false;
			}

		});

	}

	private boolean portfolioContainsStock() {
		String portfolioTicker = stockTicker.getText().toString();
		portfolioSettings = getSharedPreferences(
				ApplicationConstants.PORTFOLIO_PREFERENCES, 0);
		if (portfolioSettings.contains(portfolioTicker)) {
			return true;
		}
		return false;
	}

	// fetch data
	private void kinveyStockDataFetcher(String searchString) {
		Query fetchTicker = mKinveyClient.query();
		fetchTicker.equals(ApplicationConstants.TICKER_COLUMN, searchString);
		ConnectToKinveyTask.kinveyFetchStockQuery(
				ApplicationConstants.RATIO_TABLE, fetchTicker, this);
	}

	private void kinveyPriceDataFetcher(String searchString) {
		// DATE IS HARD CODED FOR NOW
		ConnectToKinveyTask.kinveyFetchPriceQuery(searchString, 20110404, this);
	}

	/* ratio data responce method */
	public void kinveyResponceStockMethod(Object object) {

		progressBarLayout.setVisibility(View.GONE);
		gotRatioData = true;
		if (object != null) {
			portfolioButton.setEnabled(true);
			// stock data
			theStock = (Stock) object;
			String[] ratioDataArray = theStock.values().toString().split(",");
			String[] ratioDataNames = theStock.keySet().toString().split(",");

			// sector data
			String[] sectorDataArray = null;
			Stock sector = SectorData.getSectorAvgs(theStock.getSector());
			if (sector != null) {
				sectorDataArray = sector.values().toString().split(",");
			}

			displayRatioData(theStock, ratioDataArray, ratioDataNames,
					sectorDataArray);

			if (portfolioContainsStock()) {
				portfolioButton.setText(ApplicationConstants.REM_PORTFOLIO);
				portfolioButton.setTextColor(Color
						.parseColor(ApplicationConstants.COLOR_RED));
			} else {
				portfolioButton.setText(ApplicationConstants.ADD_PORTFOLIO);
				portfolioButton.setTextColor(Color
						.parseColor(ApplicationConstants.COLOR_GREEN));
			}

			// setElementsVisibility();
		} else {
			Utilities.displayToastNoInternet(this);
			Log.d("DISPLAY", "kinvey responce got null");
		}
	}

	/* price data responce method */
	public void kinveyResponcePriceMethod(final Object[] objects) {
		/*
		 * Utilities.displayToast(this, "Got price data for ", stockTicker
		 * .getText().toString());
		 */

		if (objects != null) {
			Utilities.displayToastPositionaly(this,
					Math.round(graphButton.getPivotX() + 120),
					Math.round(graphButton.getPivotY()) - 150, "Graph Loaded");

			graphButton.setEnabled(true);
			graphButton.setBackgroundResource(R.drawable.best_192x96);

			indicatorSettings = getSharedPreferences(
					ApplicationConstants.INDICATOR_PREFERENCES, 0);

			PriceDataStorage.setPriceData((PriceData[]) objects, stockTicker
					.getText().toString(), context, indicatorSettings.getAll());

			graphButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(context, GraphActivity.class);
					startActivity(intent);
				}

			});
		} else {
			Utilities.displayToast(context, "Check Internet", "Connection");
			Log.d("DISPLAY", "kinvey responce got null");
		}
	}

	private void setElementsVisibility() {
		// loadingDone = true;
		mainDisplayLayout.setVisibility(View.VISIBLE);
	}

	/*
	 * private void startProgressBar() {
	 * setContentView(R.layout.progress_loader); progressBar = (ProgressBar)
	 * findViewById(R.id.pbDisplayStockData);; progressHandler = new Handler();
	 * progressBar.setIndeterminate(true); // Start lengthy operation in a
	 * background thread new Thread(new Runnable() { public void run() { while
	 * (!loadingDone) {
	 * 
	 * // Update the progress bar progressHandler.post(new Runnable() { public
	 * void run() { progressBar.setProgress(5); } }); } } }).start(); }
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaystockdata);
		// fix the screen orientation to portrait
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// startProgressBar();
		initialize(getIntent());
		// I can also get the calling activity ID but its unnecessary at this
		// time
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return mInflator.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return mInflator.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!gotRatioData) {
			progressBarLayout.setVisibility(View.VISIBLE);
		} else {
			progressBarLayout.setVisibility(View.GONE);
		}
		// fetchKinvey(stockName.getText().toString());
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// fetchKinvey(stockName.getText().toString());
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		progressBarLayout.setVisibility(View.VISIBLE);
		// mainDisplayLayout.setVisibility(View.INVISIBLE);
	}

	public void displayRatioData(Stock stock, String[] ratioDataArray,
			String[] ratioDataNames, String[] sectorDataArray) {

		stockName.setText(stock.getCompany());
		stockName.setTextColor(Color.BLACK);
		stockTicker.setText(stock.getTicker());
		stockInfo.setText(stock.getCountry() + " | " + stock.getSector()
				+ " | " + stock.getIndustry());
		stockInfo.setTextSize(12); // CHANGE NUMBER TO A CONSTANT

		stockTotalScore.setText(String.valueOf(stock.getTotalscore()));
		stockDivScore.setText(String.valueOf(stock.getDivscore()));
		stockGrowthScore.setText(String.valueOf(stock.getGrowthscore()));

		int[] alternatingRGBColor = new int[3];
		int alternator = 0;

		// Go through each item in the array
		for (int current = 1; current < ratioDataArray.length; current++) {
			// skip all ratios that are not needed
			// TODO THIS NEEDS TO BE FIXED IN A MORE PROGRAMMABLE WAY
			if (current != 2 && current != 3 && current != 4 && current != 5
					&& current != 7 && current != 14 && current != 15
					&& current != 16 && current != 22 && current != 31
					&& current != 32 && current != 34 && current != 37
					&& current != 38 && current < 40) {

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
				TableRow tr = new TableRow(this);
				tr.setId(100 + current);
				tr.setBackgroundColor(Color.rgb(alternatingRGBColor[0],
						alternatingRGBColor[1], alternatingRGBColor[2]));
				tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT));

				// Create a TextView to hold the label of the ratio
				TextView labelTV = new TextView(this);
				labelTV.setId(200 + current);
				labelTV.setText(ratioDataNames[current].replace('_', ' '));
				labelTV.setTextColor(Color.BLACK);
				labelTV.setTextSize(ApplicationConstants.RATIO_DATA_TEXT_SIZE);
				labelTV.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT));
				labelTV.setPadding(5, 0, 0, 0);
				tr.addView(labelTV);

				// Create a TextView to hold the value of the ratio
				TextView valueTV = new TextView(this);
				valueTV.setId(300 + current);
				valueTV.setText(ratioDataArray[current]);
				valueTV.setTextColor(Color.BLACK);
				valueTV.setTextSize(ApplicationConstants.RATIO_DATA_TEXT_SIZE);
				valueTV.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT));
				valueTV.setPadding(5, 0, 0, 0);
				tr.addView(valueTV);

				// Create a TextView to hold the value of the ratio
				TextView sectorTV = new TextView(this);
				sectorTV.setId(400 + current);
				sectorTV.setTextColor(Color.BLACK);
				if (sectorDataArray != null) {
					sectorTV.setText(sectorDataArray[current]);
				} else {
					sectorTV.setText(ApplicationConstants.SECTOR_MISSING);
					sectorTV.setTextColor(Color.RED);
				}
				sectorTV.setTextSize(ApplicationConstants.RATIO_DATA_TEXT_SIZE);
				sectorTV.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT));
				sectorTV.setPadding(5, 0, 0, 0);
				tr.addView(sectorTV);

				// HAS NULL POINTER ERROR POSSIBILITY IF SECTOR DATA IS NULL
				// OTHER ARRAY SHOULD ALSO PROBABLY BE CHECKED FOR NULL VALUES
				setRatioValueColor(labelTV.getText().toString().trim(),
						valueTV, ratioDataArray[current], sectorTV,
						sectorDataArray[current]);

				// Add the TableRow to the TableLayout
				ratioTable.addView(tr, new TableLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			}
		}
	}

	private void setRatioValueColor(String ratioName, TextView tvValue,
			String value, TextView tvSector, String sector) {
		float fValue = Float.valueOf(value);
		float sValue = Float.valueOf(sector);

		if (sValue > fValue) {
			if (SectorData.goodSectorData.contains(ratioName)) {
				tvValue.setTextColor(Color
						.parseColor(ApplicationConstants.COLOR_GREEN));
			} else if (SectorData.badSectorData.contains(ratioName)) {
				tvValue.setTextColor(Color
						.parseColor(ApplicationConstants.COLOR_RED));
			}
		} else if (sValue < fValue) {
			if (SectorData.badSectorData.contains(ratioName)) {
				tvValue.setTextColor(Color
						.parseColor(ApplicationConstants.COLOR_GREEN));
			} else if (SectorData.goodSectorData.contains(ratioName)) {
				tvValue.setTextColor(Color
						.parseColor(ApplicationConstants.COLOR_RED));
			}
		}
	}
}
