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
import android.view.View;
import android.view.View.OnClickListener;
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
import com.seniorproject.stocksign.activity.Utilities;
import com.seniorproject.stocksign.database.PriceData;
import com.seniorproject.stocksign.database.PriceDataStorage;

public class GraphActivity extends Activity {

	private LinearLayout graphView = null;
	private Spinner dateRange = null;
	private Button indicatorField = null;
	private TextView tickerField = null;

	private int startDate = 100;

	private Context context = null;
	private String[] periods = null;

	// Date today = null;

	private float[] priceValues = null;
	private float minVal_Y = Float.MAX_VALUE;
	private float maxVal_Y = Float.MIN_VALUE;

	private HashMap<String, IndicatorInfo> indicatorMap = null;
	private LinkedHashSet<String> lineTitles = null;
	private ArrayList<Integer> lineColors = null;
	private ArrayList<float[]> lineValues = null;
	private ArrayList<PointStyle> pointStyles = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph);
		context = this;
		initializeXML();
		setChartPeriod(dateRange.getItemAtPosition(0).toString());
		setupSpinner();
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
		indicatorField = (Button) findViewById(R.id.btChartLines);
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

			if (startDate > PriceDataStorage.getSize()) {
				Log.e("GRAPH", "date larger than price data size");
				startDate = PriceDataStorage.getSize();
			}

			// add indicator, color and point style values
			int i = 1;
			for (IndicatorInfo ind : indicators) {
				if (ind.isChecked()) {
					lineTitles.add(ind.getName());
					float[] indValues = new float[startDate];
					populateValuesBasedOnDateRange(indValues, ind.getValues(),
							ind.getValues().length - startDate, startDate);
					lineValues.add(indValues);
					if (i >= GraphingConstants.COLOR_VALUES.length) {
						Log.e("GRAPH", "missing color values in setGraphData()");
						break;
					}
					lineColors.add(GraphingConstants.COLOR_VALUES[i]);
					pointStyles.add(PointStyle.POINT);
					i++;
				}
			}

			// add the price values
			priceValues = new float[startDate];
			populateValuesBasedOnDateRange(priceValues,
					PriceDataStorage.getPrices(), PriceDataStorage.getPrices()
							.size() - startDate, startDate);

			lineValues.add(priceValues);
			lineTitles.add("PRICE");
			lineColors.add(GraphingConstants.COLOR_VALUES[0]);
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

	private View buildGraphView() {
		// String[] titles = { "BUY", "SELL", "PRICE", "ZERO" };

		int length = PriceDataStorage.getSize();

		Log.d("GRAPH", "Length:\t" + length);

		float screenScaleX = 10;
		float screenScaleY = 20;
		float minX = -screenScaleX, maxX = startDate + screenScaleX;

		float minY = minVal_Y - screenScaleY;
		float maxY = maxVal_Y + screenScaleY;

		XYMultipleSeriesRenderer renderer = ChartMethods.buildRenderer(
				lineColors, pointStyles);
		ChartMethods.setChartSettings(renderer, "GRAPH", "Time", "Values",
				minX, maxX, minY, maxY, Color.WHITE, Color.WHITE);
		renderer.setXLabels(12);
		renderer.setYLabels(10);
		renderer.setBarWidth(10.0f);
		renderer.setChartTitleTextSize(20);
		renderer.setTextTypeface("sans_serif", Typeface.BOLD);
		renderer.setLabelsTextSize(14f);
		renderer.setAxisTitleTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setBackgroundColor(Color.BLACK);
		renderer.setApplyBackgroundColor(true);
		renderer.setShowGrid(true);
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

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(context, IndicatorPickerActivity.class);
			startActivityForResult(intent, 0);
		}

	}
}
