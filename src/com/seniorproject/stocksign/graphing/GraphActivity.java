package com.seniorproject.stocksign.graphing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import org.joda.time.DateTime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
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
	// private LinearLayout indicatorField = null;
	private TextView tickerField = null;

	private int workingDays = 100;
	private DateTime todayDate = null;
	private DateTime startDate = null;
	private ArrayList<DateTime> dateValues = null;

	private Context context = null;
	private String[] periods = null;
	private String currentPeriod = null;

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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph);
		context = this;
		mInflator = new MenuInflator(this);
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		initializeXML();
		setupTodayDate();
		setChartPeriod(dateRange.getItemAtPosition(0).toString());
		setupSpinner();
	}

	private void setupTodayDate() {
		int todayInt = PriceDataStorage.getDates().get(
				PriceDataStorage.getDates().size() - 1);
		todayDate = createDateTime(todayInt);
	}

	private DateTime createDateTime(int date) {
		String t = String.valueOf(date);
		int year = Integer.valueOf(t.substring(0, 4));
		int month = Integer.valueOf(t.substring(4, 6));
		int day = Integer.valueOf(t.substring(6));
		return new DateTime(year, month, day, 0, 0);
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
		super.onStart();
		// displayGraph();
	}

	@Override
	protected void onResume() {
		super.onResume();
		displayGraph();
	}

	private void initializeXML() {
		graphView = (LinearLayout) findViewById(R.id.llGraph);
		dateRange = (Spinner) findViewById(R.id.spChartPeriods);
		indicatorField = (Button) findViewById(R.id.btChartIndicators);
		// indicatorField = (LinearLayout) findViewById(R.id.llChartIndicators);
		tickerField = (TextView) findViewById(R.id.tvGraphTicker);

		periods = context.getResources().getStringArray(R.array.chart_periods);
	}

	private void setChartPeriod(String period) {

		currentPeriod = period;
		if (period.equals(periods[0])) {
			// 1m
			startDate = todayDate.minusMonths(1);
		} else if (period.equals(periods[1])) {
			// 3m
			startDate = todayDate.minusMonths(3);
		} else if (period.equals(periods[2])) {
			// 6m
			startDate = todayDate.minusMonths(6);
		} else if (period.equals(periods[3])) {
			// 1y
			startDate = todayDate.minusYears(1);
		} else if (period.equals(periods[4])) {
			// 2y
			startDate = todayDate.minusYears(2);
		} else if (period.equals(periods[5])) {
			// 3y
			startDate = todayDate.minusYears(3);
		}
		workingDays = Utilities.calculateWorkDays(startDate.toDate(),
				todayDate.toDate());
		Log.d("DATE",
				period + " working days from " + todayDate.getYear() + "-"
						+ todayDate.getMonthOfYear() + "-"
						+ todayDate.getDayOfMonth() + " to "
						+ startDate.getYear() + "-"
						+ startDate.getMonthOfYear() + "-"
						+ startDate.getDayOfMonth() + " is " + workingDays);
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

			if (workingDays > PriceDataStorage.getSize()) {
				Log.e("GRAPH", "date larger than price data size");
				workingDays = PriceDataStorage.getSize();
			}

			// add indicator, color and point style values
			int i = 0;
			for (IndicatorInfo ind : indicators) {
				if (ind.isChecked()) {
					// add titles names
					lineTitles.add(ind.getName() + "+");
					lineTitles.add(ind.getName() + "-");
					float[] indPosValues = new float[workingDays];
					float[] indNegValues = new float[workingDays];
					// create positive values
					populateValuesBasedOnDateRange(indPosValues,
							ind.getPositives(), ind.getPositives().length
									- workingDays, workingDays);
					// create negative values
					populateValuesBasedOnDateRange(indNegValues,
							ind.getNegatives(), ind.getNegatives().length
									- workingDays, workingDays);
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
			lineValues.add(new float[workingDays]);
			lineTitles.add("ZERO");
			lineColors.add(GraphingConstants.ZERO_COLOR);
			pointStyles.add(PointStyle.POINT);

			// add the price values
			priceValues = new float[workingDays];
			populateValuesBasedOnDateRange(priceValues,
					PriceDataStorage.getPrices(), PriceDataStorage.getPrices()
							.size() - workingDays, workingDays);

			lineValues.add(priceValues);
			lineTitles.add("PRICE");
			lineColors.add(GraphingConstants.PRICE_COLOR);
			pointStyles.add(PointStyle.POINT);

			// populate date values
			dateValues = new ArrayList<DateTime>();
			populateValuesBasedOnDateRange(dateValues,
					PriceDataStorage.getDates(), PriceDataStorage.getDates()
							.size() - workingDays, workingDays);

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

	private void populateValuesBasedOnDateRange(ArrayList<DateTime> data,
			ArrayList<Integer> valData, int start, int limit) {
		for (int i = start, j = 0; j < limit; i++, j++) {
			data.add(createDateTime(valData.get(i)));
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
	 * .parse(priceData[0].getDate()); } catch (ParseException e) { //
	 * Auto-generated catch block e.printStackTrace(); } }
	 */

	private float convertYLabelValue(float value) {
		double val = ((100.0 * Math.exp(value)) - 100);
		return Utilities.round((float) val, 2);
	}

	private float getYIncrement(float max, float min) {
		float num1 = Math.abs(max);
		float num2 = Math.abs(min);
		return ((num1 + num2) / GraphingConstants.NUMBER_OF_Y_LABELS);
	}

	private void buildYLabels(XYMultipleSeriesRenderer renderer) {
		String label;
		float start = maxPrice;
		float end = minPrice;
		float inc = getYIncrement(maxPrice, minPrice);
		for (int i = 0; i <= GraphingConstants.NUMBER_OF_Y_LABELS; i++) {
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

	private String createDateString(int dateInt) {
		String date = createDateTime(dateInt).toString("yyyy/MM/dd");
		if (currentPeriod.equals(periods[0])) {
			// 1m
			date = date.substring(5);
		} else if (currentPeriod.equals(periods[1])) {
			// 3m
			date = date.substring(5);
		} else if (currentPeriod.equals(periods[2])) {
			// 6m
			date = date.substring(5);
		} else if (currentPeriod.equals(periods[3])) {
			// 1y
			date = date.substring(5);
		} else if (currentPeriod.equals(periods[4])) {
			// 2y
			date = date.substring(5);
		} else if (currentPeriod.equals(periods[5])) {
			// 3y
			date = date.substring(5);
		}
		return date;
	}

	private int getXIncrement(int max, int min) {
		return (max + min) / GraphingConstants.NUMBER_OF_X_LABELS;
	}

	private void buildXLabels(XYMultipleSeriesRenderer renderer) {
		// TODO: ADD DATES
		int inc = getXIncrement(workingDays, 0);

		if (currentPeriod.equals(periods[0])) {
			// 1m
			String label = startDate.toString("MMM");
			int currMonth = startDate.getMonthOfYear();
			renderer.addXTextLabel(0, label);
			for (int i = inc; i < dateValues.size(); i += inc) {
				DateTime currentDate = dateValues.get(i);
				label = "";
				if (currentDate.getMonthOfYear() != currMonth) {
					label += currentDate.toString("MMM ");
					currMonth = currentDate.getMonthOfYear();
				}
				label += currentDate.toString("dd");
				renderer.addXTextLabel(i, label);
			}
		} else if (currentPeriod.equals(periods[1])) {
			// 3m
			int currMonth = startDate.getMonthOfYear();
			for (int i = 0; i < dateValues.size(); i++) {
				DateTime currentDate = dateValues.get(i);
				if (currentDate.getMonthOfYear() != currMonth) {
					String label = currentDate.toString("MMM yyyy");
					currMonth = currentDate.getMonthOfYear();
					renderer.addXTextLabel(i, label);
				}
			}
		} else if (currentPeriod.equals(periods[2])) {
			// 6m
			int currMonth = startDate.getMonthOfYear();
			int skipper = 0;
			for (int i = 0; i < dateValues.size(); i++) {
				DateTime currentDate = dateValues.get(i);
				if (currentDate.getMonthOfYear() != currMonth) {
					skipper++;
					if (skipper % 2 == 0) {
						String month = currentDate.toString("MMM");
						String year2digs = currentDate.toString("yyyy")
								.substring(2);
						currMonth = currentDate.getMonthOfYear();
						renderer.addXTextLabel(i, month + "'" + year2digs);
					}
				}
			}
		} else if (currentPeriod.equals(periods[3])) {
			// 1y
			int currMonth = startDate.getMonthOfYear();
			int skipper = 0;
			for (int i = 0; i < dateValues.size(); i++) {
				DateTime currentDate = dateValues.get(i);
				if (currentDate.getMonthOfYear() != currMonth) {
					skipper++;
					currMonth = currentDate.getMonthOfYear();
					if (skipper % 2 == 0) {
						String month = currentDate.toString("MMM");
						String year2digs = currentDate.toString("yyyy")
								.substring(2);
						renderer.addXTextLabel(i, month + "'" + year2digs);
					}
				}
			}

		} else if (currentPeriod.equals(periods[4])) {
			// 2y
			
			String label = startDate.toString("yyyy");
			int currYear = startDate.getYear();
			renderer.addXTextLabel(0, label);
			for (int i = inc; i < dateValues.size(); i += inc) {
				DateTime currentDate = dateValues.get(i);
				label = "";
				if (currentDate.getYear() != currYear) {
					label += currentDate.toString("yyyy ");
					currYear = currentDate.getYear();
				}
				label += currentDate.toString("MMM");
				renderer.addXTextLabel(i, label);
			}
			
			/*int currMonth = startDate.getMonthOfYear();
			int skipper = 0;
			for (int i = 0; i < dateValues.size(); i++) {
				DateTime currentDate = dateValues.get(i);
				if (currentDate.getMonthOfYear() != currMonth) {
					skipper++;
					currMonth = currentDate.getMonthOfYear();
					if (skipper % 4 == 0) {
						String month = currentDate.toString("MMM");
						String year2digs = currentDate.toString("yyyy")
								.substring(2);
						Log.d("DATEYR", month + "'" + year2digs);
						renderer.addXTextLabel(i, month + "'" + year2digs);
					}
				}
			}*/

		} else if (currentPeriod.equals(periods[5])) {
			// 3y
			
			String label = startDate.toString("yyyy");
			int currYear = startDate.getYear();
			renderer.addXTextLabel(0, label);
			for (int i = inc; i < dateValues.size(); i += inc) {
				DateTime currentDate = dateValues.get(i);
				label = "";
				if (currentDate.getYear() != currYear) {
					label += currentDate.toString("yyyy ");
					currYear = currentDate.getYear();
				}
				label += currentDate.toString("MMM");
				renderer.addXTextLabel(i, label);
			}
			
			/*int currMonth = startDate.getMonthOfYear();
			int skipper = 0;
			for (int i = 0; i < dateValues.size(); i++) {
				DateTime currentDate = dateValues.get(i);
				if (currentDate.getMonthOfYear() != currMonth) {
					skipper++;
					currMonth = currentDate.getMonthOfYear();
					if (skipper % 6 == 0) {
						String month = currentDate.toString("MMM");
						String year2digs = currentDate.toString("yyyy")
								.substring(2);
						Log.d("DATEYR", month + "'" + year2digs);
						renderer.addXTextLabel(i, month + "'" + year2digs);
					}
				}
			}*/
		}
	}

	private void setupRendererData(XYMultipleSeriesRenderer renderer) {
		renderer.setPointSize(5f);
		renderer.setMargins(new int[] { 25, 20, 30, 10 });
		renderer.setXLabels(0);
		renderer.setYLabels(0);
		renderer.setBarWidth(10.0f);
		renderer.setChartTitleTextSize(20);
		renderer.setTextTypeface("sans_serif", Typeface.BOLD);
		renderer.setYLabelsAlign(Paint.Align.LEFT);
		renderer.setLabelsTextSize(16.5f);
		renderer.setFitLegend(true);
		renderer.setAxisTitleTextSize(17f);
		renderer.setLegendTextSize(16.5f);
		renderer.setBackgroundColor(Color.BLACK);
		renderer.setApplyBackgroundColor(true);
		renderer.setShowCustomTextGrid(true);

		/*
		 * renderer.setZoomButtonsVisible(true); renderer.setPanLimits(new
		 * double[] { -10, 20, -10, 40 }); renderer.setZoomLimits(new double[] {
		 * -10, 20, -10, 40 }); renderer.setZoomRate(1.05f);
		 */
	}

	private View buildGraphView() {
		// String[] titles = { "BUY", "SELL", "PRICE", "ZERO" };

		int length = PriceDataStorage.getSize();

		Log.d("GRAPH", "Length:\t" + length);

		float screenPaddingX = 10f;
		float screenPaddingY = 0;
		float minX = -screenPaddingX, maxX = workingDays + screenPaddingX;

		// float minY = (minVal_Y * GraphingConstants.PRICE_VALUE_SCALE) -
		// screenPaddingY;
		// float maxY = (maxVal_Y * GraphingConstants.PRICE_VALUE_SCALE) +
		// screenPaddingY;

		float larger = (Math.abs(minPrice) > Math.abs(maxPrice) ? Math
				.abs(minPrice) : Math.abs(maxPrice));

		GraphingConstants.PRICE_VALUE_SCALE = (GraphingConstants.MAX_Y / larger);

		float minY = GraphingConstants.MIN_Y;
		float maxY = GraphingConstants.MAX_Y;

		XYMultipleSeriesRenderer renderer = ChartMethods.buildRenderer(
				lineColors, pointStyles);
		ChartMethods.setChartSettings(renderer, "", "",
				"% Change From Today", minX, maxX, minY, maxY, Color.WHITE,
				Color.WHITE);

		buildYLabels(renderer);
		buildXLabels(renderer);
		setupRendererData(renderer);

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
