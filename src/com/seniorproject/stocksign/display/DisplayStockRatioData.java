package com.seniorproject.stocksign.display;



import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.kinvey.java.query.AbstractQuery.SortOrder;
import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.activity.ApplicationConstants;
import com.seniorproject.stocksign.database.PriceData;
import com.seniorproject.stocksign.database.SectorData;
import com.seniorproject.stocksign.database.Stock;
import com.seniorproject.stocksign.graphing.LineGraph;
import com.seniorproject.stocksign.kinveyconnection.ConnectToKinveyTask;
import com.seniorproject.stocksign.kinveyconnection.KinveyConnectionSingleton;
import com.seniorproject.stocksign.searching.SearchStockActivity;

public class DisplayStockRatioData extends Activity {

	Client mKinveyClient = null;
	
	TextView stockName;
	TextView stockTicker;
	TextView stockCountry;
	TextView stockSector;
	TextView stockIndustry;
	TableLayout ratioTable;

	View currentView;

	private GestureDetectorCompat mDetector;
	
	public void initialize(Intent intent) {
		mKinveyClient = KinveyConnectionSingleton.getKinveyClient();
		
		currentView = findViewById(R.layout.displaystockdata);
		mDetector = new GestureDetectorCompat(this, new MyGestureListener());
		
		stockName = (TextView) findViewById(R.id.tvStockname);
		stockTicker = (TextView) findViewById(R.id.tvStockticker);
		stockCountry = (TextView) findViewById(R.id.tvStockCountryValue);
		stockSector = (TextView) findViewById(R.id.tvStockSectorValue);
		stockIndustry = (TextView) findViewById(R.id.tvStockIndustryValue);
		ratioTable = (TableLayout) findViewById(R.id.tlStockratios);
		
		// get data
		Bundle b = intent.getBundleExtra(ApplicationConstants.RATIO_BUNDLE);
		String ticker = b.getString(ApplicationConstants.TICKER_SINGLE);
		kinveyDataFetcher(ticker);	
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
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaystockdata);
		initialize(getIntent());
		// I can also get the calling activity ID but its unnecessary at this
		// time
	}
	
	public void displayRatioData(Stock stock, String[] ratioDataArray, 
			String[] ratioDataNames, String[] sectorDataArray) {

		stockName.setText(stock.getCompany());
		stockTicker.setText(stock.getTicker());
		stockCountry.setText(stock.getCountry());
		stockSector.setText(stock.getSector());
		stockIndustry.setText(stock.getIndustry());

		int[] alternatingRGBColor = new int[3];
		int alternator = 0;

		// Go through each item in the array
		for (int current = 1; current < ratioDataArray.length; current++) {
			//skip all ratios that are not needed
			if (current != 2 && current != 3 && current != 4 && current != 7 &&
				current != 14 && current != 16 && current != 22 && current != 31 &&
				current != 32 && current != 34 && current != 37 && current != 38 &&
				current != 40 && current != 41 && current != 42 && current != 43 && 
				current < 45) {

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
				tvValue.setTextColor(Color.parseColor("#009933"));
			} else if(SectorData.badSectorData.contains(ratioName)) {
				tvValue.setTextColor(Color.parseColor("#CC0000"));
			}
		} else if(sValue < fValue) {
			if(SectorData.badSectorData.contains(ratioName)) {
				tvValue.setTextColor(Color.parseColor("#009933"));
			} else if(SectorData.goodSectorData.contains(ratioName)) {
				tvValue.setTextColor(Color.parseColor("#CC0000"));
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
