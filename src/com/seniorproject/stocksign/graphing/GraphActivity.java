package com.seniorproject.stocksign.graphing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer.FillOutsideLine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.activity.ApplicationConstants;
import com.seniorproject.stocksign.activity.MenuInflator;
import com.seniorproject.stocksign.activity.Utilities;
import com.seniorproject.stocksign.database.PriceData;
import com.seniorproject.stocksign.database.PriceDataStorage;

public class GraphActivity extends Activity {

	private LinearLayout graphView = null;
	private Spinner dateRange = null;
	private Button indicatorField = null;
	//private LinearLayout indicatorField = null;
	private TextView tickerField = null;

	private int startDate = 100;

	private Context context = null;
	private String[] periods = null;

	// Date today = null;

	private float[] priceValues = null;
	private float minPrice = Float.MAX_VALUE;
	private float maxPrice = Float.MIN_VALUE;
	private float minVal_Y = Float.MAX_VALUE;
	private float maxVal_Y = Float.MIN_VALUE;

	private HashMap<String, IndicatorInfo> indicatorMap = null;
	private LinkedHashSet<String> lineTitles = null;
	private ArrayList<Integer> lineColors = null;
	private ArrayList<float[]> lineValues = null;
	private ArrayList<PointStyle> pointStyles = null;

	private MenuInflator mInflator = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph);
		context = this;
		mInflator = new MenuInflator(this);
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		initializeXML();
		setChartPeriod(dateRange.getItemAtPosition(0).toString());
		setupSpinner();
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
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		displayGraph();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		displayGraph();
	}

	private void initializeXML() {
		graphView = (LinearLayout) findViewById(R.id.llGraph);
		dateRange = (Spinner) findViewById(R.id.spChartPeriods);
		indicatorField = (Button) findViewById(R.id.btChartIndicators);
		//indicatorField = (LinearLayout) findViewById(R.id.llChartIndicators);
		tickerField = (TextView) findViewById(R.id.tvGraphTicker);

		periods = context.getResources().getStringArray(R.array.chart_periods);
	}

	private void setChartPeriod(String period) {

		if (period.equals(periods[0])) {
			// 1m
			startDate = 30;
		} else if (period.equals(periods[1])) {
			// 3m
			startDate = 90;
		} else if (period.equals(periods[2])) {
			// 6m
			startDate = 180;
		} else if (period.equals(periods[3])) {
			// 1y
			startDate = 360;
		} else if (period.equals(periods[4])) {
			// 2y
			startDate = 720;
		} else if (period.equals(periods[5])) {
			// 3y
			startDate = 1080;
		}
	}

	private void setGraphData() {
		indicatorMap = PriceDataStorage.getIndicatorMap();
		if (indicatorMap != null) {
			Collection<IndicatorInfo> indicators = indicatorMap.values();

			// CHECK IF NEEDED TO RECREATE EACH TIME OR SHOULD WE CLEAN IT

			lineTitles = new LinkedHashSet<String>();
			lineValues = new ArrayList<float[]>();
			lineColors = new ArrayList<Integer>();
			pointStyles = new ArrayList<PointStyle>();

			// reset mins and maxes
			minPrice = Float.MAX_VALUE;
			maxPrice = Float.MIN_VALUE;
			minVal_Y = Float.MAX_VALUE;
			maxVal_Y = Float.MIN_VALUE;

			if (startDate > PriceDataStorage.getSize()) {
				Log.e("GRAPH", "date larger than price data size");
				startDate = PriceDataStorage.getSize();
			}

			// add indicator, color and point style values
			int i = 0;
			for (IndicatorInfo ind : indicators) {
				if (ind.isChecked()) {
					// add titles names
					lineTitles.add(ind.getName() + "+");
					lineTitles.add(ind.getName() + "-");
					float[] indPosValues = new float[startDate];
					float[] indNegValues = new float[startDate];
					// create positive values
					populateValuesBasedOnDateRange(indPosValues,
							ind.getPositives(), ind.getPositives().length
									- startDate, startDate);
					// create negative values
					populateValuesBasedOnDateRange(indNegValues,
							ind.getNegatives(), ind.getNegatives().length
									- startDate, startDate);
					// add pos and neg values to the line values
					lineValues.add(indPosValues);
					lineValues.add(indNegValues);
					// add colors to line values
					if (i >= GraphingConstants.POS_COLORS.length) {
						Log.e("GRAPH", "missing color values in setGraphData()");
						break;
					}
					lineColors.add(Color
							.parseColor(GraphingConstants.POS_COLORS[i]));
					if (i >= GraphingConstants.NEG_COLORS.length) {
						Log.e("GRAPH", "missing color values in setGraphData()");
						break;
					}
					lineColors.add(Color
							.parseColor(GraphingConstants.NEG_COLORS[i]));
					// add two point styles
					pointStyles.add(PointStyle.POINT);
					pointStyles.add(PointStyle.POINT);
					i++;
				}
			}

			// add the zero line
			lineValues.add(new float[startDate]);
			lineTitles.add("ZERO");
			lineColors.add(GraphingConstants.ZERO_COLOR);
			pointStyles.add(PointStyle.POINT);

			// add the price values
			priceValues = new float[startDate];
			populateValuesBasedOnDateRange(priceValues,
					PriceDataStorage.getPrices(), PriceDataStorage.getPrices()
							.size() - startDate, startDate);

			lineValues.add(priceValues);
			lineTitles.add("PRICE");
			lineColors.add(GraphingConstants.PRICE_COLOR);
			pointStyles.add(PointStyle.POINT);

		} else {
			Log.e("GRAPH", "indicator names are null when building titles");
		}
	}

	private void populateValuesBasedOnDateRange(float[] data, float[] valData,
			int start, int limit) {
		for (int i = start, j = 0; j < limit; i++, j++) {
			data[j] = valData[i];
			if (data[j] < minVal_Y) {
				minVal_Y = data[j];
			}
			if (data[j] > maxVal_Y) {
				maxVal_Y = data[j];
			}
		}
	}

	private void populateValuesBasedOnDateRange(float[] data,
			ArrayList<Float> valData, int start, int limit) {
		for (int i = start, j = 0; j < limit; i++, j++) {
			data[j] = valData.get(i);

			if (data[j] < minPrice) {
				minPrice = data[j];
			}
			if (data[j] > maxPrice) {
				maxPrice = data[j];
			}

			if (data[j] < minVal_Y) {
				minVal_Y = data[j];
			}
			if (data[j] > maxVal_Y) {
				maxVal_Y = data[j];
			}
		}
	}

	private void displayGraph() {
		if (graphView.getChildCount() > 0) {
			graphView.removeAllViews();
		}
		setGraphData();
		View graph = buildGraphView();
		if (graph != null) {
			graphView.addView(graph);
		}
		tickerField.setText(PriceDataStorage.getTicker());
		// graphView.invalidate();
		// graphView.refreshDrawableState();
	}

	private void setupSpinner() {

		dateRange.setOnItemSelectedListener(new PeriodOnItemSelectedListener());
		ArrayList<String> dates = new ArrayList<String>();
		for (int i = 0; i < periods.length; i++) {
			dates.add(periods[i]);
		}
		dateRange.setAdapter(new DatePeriodAdapter(this,
				android.R.layout.simple_spinner_item, dates));

		indicatorField.setOnClickListener(new IndicatorOnClickListener());
		
		/*
		 * indicatorField.setAdapter(new IndicatorAdapter(this,
		 * android.R.layout.simple_spinner_item, indicatorList));
		 */
	}

	/*
	 * private void setupDate() { try { today = new
	 * SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH)
	 * .parse(priceData[0].getDate()); } catch (ParseException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 */

	private float convertYLabelValue(float value) {
		double val = ((100.0 * Math.exp(value)) - 100);
		return Utilities.round((float) val, 2);
	}

	private float getIncrement(float max, float min) {
		float num1 = Math.abs(max);
		float num2 = Math.abs(min);
		return ((num1 + num2) / GraphingConstants.NUMBER_OF_LABELS);
	}

	private void buildYLabels(XYMultipleSeriesRenderer renderer) {
		String label;

		float start = maxPrice;
		float end = minPrice;
		float inc = getIncrement(maxPrice, minPrice);
		for (int i = 0; i <= GraphingConstants.NUMBER_OF_LABELS; i++) {
			float value = convertYLabelValue(start);
			label = (value + "%");
			renderer.addYTextLabel(start * GraphingConstants.PRICE_VALUE_SCALE,
					label);
			start -= inc;
			if (start < end) {
				break;
			}
		}
	}

	private View buildGraphView() {
		// String[] titles = { "BUY", "SELL", "PRICE", "ZERO" };

		int length = PriceDataStorage.getSize();

		Log.d("GRAPH", "Length:\t" + length);

		float screenPaddingX = 10;
		float screenPaddingY = 0;
		float minX = -screenPaddingX, maxX = startDate + screenPaddingX;

		// float minY = (minVal_Y * GraphingConstants.PRICE_VALUE_SCALE) -
		// screenPaddingY;
		// float maxY = (maxVal_Y * GraphingConstants.PRICE_VALUE_SCALE) +
		// screenPaddingY;

		float larger = (Math.abs(minPrice) > Math.abs(maxPrice) ? Math
				.abs(minPrice) : Math.abs(maxPrice));
		
		GraphingConstants.PRICE_VALUE_SCALE = (GraphingConstants.MAX_Y/larger);
		
		float minY = GraphingConstants.MIN_Y;
		float maxY = GraphingConstants.MAX_Y;

		XYMultipleSeriesRenderer renderer = ChartMethods.buildRenderer(
				lineColors, pointStyles);
		ChartMethods.setChartSettings(renderer, "GRAPH", "Time", "Values",
				minX, maxX, minY, maxY, Color.WHITE, Color.WHITE);
		renderer.setXLabels(12);
		renderer.setYLabels(1);
		renderer.setBarWidth(10.0f);
		renderer.setChartTitleTextSize(20);
		renderer.setTextTypeface("sans_serif", Typeface.BOLD);
		renderer.setLabelsTextSize(14f);
		renderer.setAxisTitleTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setBackgroundColor(Color.BLACK);
		renderer.setApplyBackgroundColor(true);
		renderer.setShowGrid(true);

		buildYLabels(renderer);

		length = renderer.getSeriesRendererCount();

		for (int i = 0; i < length; i++) {
			XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer
					.getSeriesRendererAt(i);
			/*
			 * if (i == length - 1) { FillOutsideLine fill = new
			 * FillOutsideLine( FillOutsideLine.Type.BOUNDS_ALL);
			 * fill.setColor(Color.CYAN);
			 * seriesRenderer.addFillOutsideLine(fill); }
			 */
			seriesRenderer.setLineWidth(3.5f);
			// seriesRenderer.setDisplayChartValues(true);
			// seriesRenderer.setChartValuesTextSize(10f);
		}

		String[] lineTitlesArray = lineTitles.toArray(new String[0]);
		/*
		 * for(int i = 0; i < lineValues.size(); i++) { String values = "";
		 * for(int j = 0; j < lineValues.get(i).length; j++) { values += " ";
		 * values += String.valueOf(lineValues.get(i)[j]); } Log.d("GRAPH",
		 * "Title = " + lineTitlesArray[i]); Log.d("GRAPH", "Color = " +
		 * lineColors.get(i)); Log.d("GRAPH", "Values = " + values); }
		 */

		return ChartFactory.getCubeLineChartView(this,
				ChartMethods.buildBarDataset(lineTitlesArray, lineValues),
				renderer, 0.5f);
	}

	private class PeriodOnItemSelectedListener implements
			OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			setChartPeriod(parent.getItemAtPosition(pos).toString());
			displayGraph();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// arg0.set
		}

	}

	private class IndicatorOnClickListener implements OnClickListener {

		
		public void onClick(View arg0) {
			Intent intent = new Intent(context, IndicatorPickerActivity.class);
			startActivityForResult(intent, 0);
		}


	}
}
