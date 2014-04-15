package com.seniorproject.stocksign.display;



import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.seniorproject.stocksign.activity.MySpinner;
import com.seniorproject.stocksign.activity.Utilities;
import com.seniorproject.stocksign.database.PriceData;
import com.seniorproject.stocksign.database.SectorData;
import com.seniorproject.stocksign.database.Stock;
import com.seniorproject.stocksign.graphing.LineGraph;
import com.seniorproject.stocksign.kinveyconnection.ConnectToKinveyTask;
import com.seniorproject.stocksign.kinveyconnection.KinveyConnectionSingleton;
import com.seniorproject.stocksign.searching.SearchStockActivity;

public class DisplayStockRatioData extends Activity {

	Context context = null;
	Client mKinveyClient = null;
	
	TextView stockName = null;
	TextView stockTicker = null;
	TextView stockInfo = null;
	TextView stockTotalScore = null;
	TextView stockDivScore = null;
	TextView stockGrowthScore = null;
	
	TableLayout ratioTable = null;
	
	Button portfolioButton = null;
	
	TableRow totalScoreRow = null;
	TableRow divScoreRow = null;
	TableRow growthScoreRow = null;
	
	View redLineSeparator = null;
	
	LinearLayout mainDisplayLayout = null;

	private GestureDetectorCompat mDetector;
	SharedPreferences settings = null;
	//MySpinnerDialog dialog = null;
	
	public void initialize(Intent intent) {
		context = this;
		
		mKinveyClient = KinveyConnectionSingleton.getKinveyClient();

		mDetector = new GestureDetectorCompat(this, new MyGestureListener());
		
		stockName = (TextView) findViewById(R.id.tvStockname);
		stockTicker = (TextView) findViewById(R.id.tvStockticker);
		stockInfo = (TextView) findViewById(R.id.tvStockInfoValue);
		stockTotalScore = (TextView) findViewById(R.id.tvStockTotalScoreValue);
		stockDivScore = (TextView) findViewById(R.id.tvStockDivScoreValue);
		stockGrowthScore = (TextView) findViewById(R.id.tvStockGrowthScoreValue);
		ratioTable = (TableLayout) findViewById(R.id.tlStockratios);
		portfolioButton = (Button) findViewById(R.id.bPortfolio);
		totalScoreRow = (TableRow) findViewById(R.id.trStockTotalScore);
		divScoreRow = (TableRow) findViewById(R.id.trStockDivScore);
		growthScoreRow = (TableRow) findViewById(R.id.trStockGrowthScore);
		redLineSeparator = findViewById(R.id.vRedLine_DisplayStockData);
		mainDisplayLayout = (LinearLayout) findViewById(R.id.llDisplayStockData);
		// get data
		Bundle b = intent.getBundleExtra(ApplicationConstants.RATIO_BUNDLE);
		String ticker = b.getString(ApplicationConstants.TICKER_SINGLE);
		
		kinveyDataFetcher(ticker);	
		addListeners();	
	}

		private void addListeners() {
			portfolioButton.setOnClickListener(new OnClickListener () {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					
					/* error check to make sure th*/
					if(stockTicker == null || stockTicker.getText().length() == 0) {
						return;
					}
					
					String buttonText = portfolioButton.getText().toString();
					String portfolioTicker = stockTicker.getText().toString();
					
					settings = getSharedPreferences(
							ApplicationConstants.USER_PORTFOLIO_TITLE, 0);
				    
					SharedPreferences.Editor editor = settings.edit();
					
					if(buttonText.equals(ApplicationConstants.ADD_PORTFOLIO)) {
						
						/* Adding stock to portfolio */
						portfolioButton.setText(ApplicationConstants.REM_PORTFOLIO);
						portfolioButton.setTextColor(Color.parseColor(ApplicationConstants.COLOR_RED));

						Set<String> scores = new LinkedHashSet<String>();
						/* add a character corresponding to the score name for two reasons:
						 * 1) to check if the score order matches on the portfolio page
						 * 2) to distinguish equal scores since a set doesn't allow duplicates
						 */
						scores.add(String.valueOf(stockTotalScore.getText()) + "t");
						scores.add(String.valueOf(stockDivScore.getText()) + "d");
						scores.add(String.valueOf(stockGrowthScore.getText()) + "g");
						
						Utilities.displayToast(context, ApplicationConstants.PF_ADD, portfolioTicker);
						editor.putStringSet(portfolioTicker, scores);
						
						Log.d("PORTFOLIO", "added");
						
					} else if(buttonText.equals(ApplicationConstants.REM_PORTFOLIO)) {
						
						/* Removing stock from portfolio */
						portfolioButton.setText(ApplicationConstants.ADD_PORTFOLIO);
						portfolioButton.setTextColor(Color.parseColor(ApplicationConstants.COLOR_GREEN));
					    editor.remove(portfolioTicker);
						Utilities.displayToast(context, ApplicationConstants.PF_REM, portfolioTicker);
					    Log.d("PORTFOLIO", "removed");

					} else {
						//Error
						portfolioButton.setTextColor(Color.CYAN);
						return;
					}
					
					//commit the edits
					editor.commit();
				
				}
				
			});
		}
		
		private boolean portfolioContainsStock() {
			String portfolioTicker = stockTicker.getText().toString();
			settings = getSharedPreferences(
					ApplicationConstants.USER_PORTFOLIO_TITLE, 0);
			if(settings.contains(portfolioTicker)) {
				return true;
			}
			return false;
		}
 		
	//fetch data
		private void kinveyDataFetcher(String searchString) {
			Query fetchTicker = mKinveyClient.query();
			fetchTicker.equals(ApplicationConstants.TICKER_COLUMN,searchString);
			ConnectToKinveyTask.kinveyFetchStockQuery(ApplicationConstants.RATIO_TABLE, fetchTicker, this);
		}
	
	/* ratio data responce method */
	public void kinveyResponceMethod(Object object) {
		
		//ratio
		Stock stock = (Stock) object;
		String[] ratioDataArray = stock.values().toString().split(",");
		String[] ratioDataNames = stock.keySet().toString().split(",");
		
		//sector
		String[] sectorDataArray = null;
		Stock sector = SectorData.getSectorAvgs(stock.getSector());
		if(sector != null) {
			sectorDataArray = sector.values().toString().split(",");
		}

		displayRatioData(stock, ratioDataArray, ratioDataNames, sectorDataArray);
		
		if(portfolioContainsStock()) {
			portfolioButton.setText(ApplicationConstants.REM_PORTFOLIO);
			portfolioButton.setTextColor(Color.parseColor(ApplicationConstants.COLOR_RED));
		} else {
			portfolioButton.setText(ApplicationConstants.ADD_PORTFOLIO);
			portfolioButton.setTextColor(Color.parseColor(ApplicationConstants.COLOR_GREEN));
		}
		
		setElementsVisibility();
	}
	
	private void setElementsVisibility() {
		//loadingDone = true;
		mainDisplayLayout.setVisibility(View.VISIBLE);
		/*
		stockName.setVisibility(View.VISIBLE);
		stockTicker.setVisibility(View.VISIBLE);
		stockInfo.setVisibility(View.VISIBLE);
		stockTotalScore.setVisibility(View.VISIBLE);
		stockDivScore.setVisibility(View.VISIBLE);
		stockGrowthScore.setVisibility(View.VISIBLE);
		ratioTable.setVisibility(View.VISIBLE);
		portfolioButton.setVisibility(View.VISIBLE);
		totalScoreRow.setVisibility(View.VISIBLE);
		divScoreRow.setVisibility(View.VISIBLE);
		growthScoreRow.setVisibility(View.VISIBLE);
		redLineSeparator.setVisibility(View.VISIBLE);*/
	}
	
	/*private void startProgressBar() {
		setContentView(R.layout.progress_loader);
		progressBar = (ProgressBar) findViewById(R.id.pbDisplayStockData);;
		progressHandler = new Handler();
		progressBar.setIndeterminate(true);
		// Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (!loadingDone) {

                    // Update the progress bar
                    progressHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(5);
                        }
                    });
                }
            }
        }).start();
	}*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaystockdata);
		//startProgressBar();
		initialize(getIntent());
		// I can also get the calling activity ID but its unnecessary at this
		// time
	}
	
	
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mainDisplayLayout.setVisibility(View.INVISIBLE);
	}

	public void displayRatioData(Stock stock, String[] ratioDataArray, 
			String[] ratioDataNames, String[] sectorDataArray) {

		stockName.setText(stock.getCompany());
		stockTicker.setText(stock.getTicker());
		stockInfo.setText(stock.getCountry() + " | " +
				 stock.getSector() + " | " +
				 stock.getIndustry());
		stockInfo.setTextSize(12); 			//CHANGE NUMBER TO A CONSTANT
		
		stockTotalScore.setText(String.valueOf(stock.getTotalscore()));
		stockDivScore.setText(String.valueOf(stock.getDivscore()));
		stockGrowthScore.setText(String.valueOf(stock.getGrowthscore()));

		int[] alternatingRGBColor = new int[3];
		int alternator = 0;

		// Go through each item in the array
		for (int current = 1; current < ratioDataArray.length; current++) {
			//skip all ratios that are not needed
			//TODO THIS NEEDS TO BE FIXED IN A MORE PROGRAMMABLE WAY
			if (current != 2 && current != 3 && current != 4 && current != 5 &&
				current != 7 && current != 14 && current != 15 && current != 16 && 
				current != 22 && current != 31 && current != 32 && current != 34 && 
				current != 37 && current != 38 && current < 40) {

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
				if(sectorDataArray != null) {
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
				
				//HAS NULL POINTER ERROR POSSIBILITY IF SECTOR DATA IS NULL
				//OTHER ARRAY SHOULD ALSO PROBABLY BE CHECKED FOR NULL VALUES
				setRatioValueColor(labelTV.getText().toString().trim(), valueTV, 
										 ratioDataArray[current], sectorTV, 
										 sectorDataArray[current]);
				
				// Add the TableRow to the TableLayout
				ratioTable.addView(tr, new TableLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			}
		}
	}
	
	private void setRatioValueColor(String ratioName, TextView tvValue, String value,
											TextView tvSector, String sector) {
		float fValue = Float.valueOf(value);
		float sValue = Float.valueOf(sector);
		
		if(sValue > fValue) {
			if(SectorData.goodSectorData.contains(ratioName)) {
				tvValue.setTextColor(Color.parseColor(ApplicationConstants.COLOR_GREEN));
			} else if(SectorData.badSectorData.contains(ratioName)) {
				tvValue.setTextColor(Color.parseColor(ApplicationConstants.COLOR_RED));
			}
		} else if(sValue < fValue) {
			if(SectorData.badSectorData.contains(ratioName)) {
				tvValue.setTextColor(Color.parseColor(ApplicationConstants.COLOR_GREEN));
			} else if(SectorData.goodSectorData.contains(ratioName)) {
				tvValue.setTextColor(Color.parseColor(ApplicationConstants.COLOR_RED));
			}
		} 
	}
	
	@Override 
    public boolean onTouchEvent(MotionEvent event){ 
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    
    public void lineGraphHandler () {
    	LineGraph line = new LineGraph();
    	Intent lineIntent = line.getIntent(this);
        startActivity(lineIntent);
    }
	
	class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures"; 
        
        @Override
        public boolean onDown(MotionEvent event) { 
            //Log.d(DEBUG_TAG,"onDown: " + event.toString()); 
            return true;
        }
        
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, 
                float velocityX, float velocityY) {
        		lineGraphHandler();
        	return true;
        }
    }
}
