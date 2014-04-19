package com.seniorproject.stocksign.graphing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.activity.Utilities;
import com.seniorproject.stocksign.database.PriceData;
import com.seniorproject.stocksign.database.PriceDataStorage;

public class GraphActivity extends Activity {

	LinearLayout graphView = null;
	Spinner dateRange = null;
	int startDate = 100;
	Context context = null;
	//Date today = null;

	private void initializeXML() {
		graphView = (LinearLayout) findViewById(R.id.llGraph);
		dateRange = (Spinner) findViewById(R.id.spChartPeriods);
	}

	private void displayGraph() {
		if(graphView.getChildCount() > 0) {
			graphView.removeAllViews();
		}
   		View graph = buildGraphView();
		if(graph != null) {
			graphView.addView(graph);
		}
		// graphView.invalidate();
		// graphView.refreshDrawableState();
	}

	private void setupSpinnerListener() {	
		dateRange.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}

	/*private void setupDate() {
		try {
			today = new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH)
					.parse(priceData[0].getDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph);
		context = this;
		initializeXML();
		setChartPeriod(dateRange.getItemAtPosition(0).toString());
		displayGraph();
		setupSpinnerListener();
	}

	public View buildGraphView() {
		String[] titles = { "BUY", "SELL", "PRICE", "ZERO" };
		List<float[]> values = new ArrayList<float[]>();

		if(startDate > PriceDataStorage.priceData.length) {
			Log.e("GRAPH", "date larger than price data size");
			startDate = PriceDataStorage.priceData.length;
		}
		
		int length = PriceDataStorage.priceData.length;	
		
		Log.d("GRAPH","Length:\t" + length);
		
		float screenScaleX = 10;
		float screenScaleY = 20;
		float minX = -screenScaleX, maxX = startDate + screenScaleX;
		float minY = Float.MAX_VALUE, maxY = Float.MIN_VALUE;
		float[] data1_pos = new float[startDate];
		float[] data1_neg = new float[startDate];
		float[] data2 = new float[startDate];
		
		int start = length - startDate;
		for (int i = 0, j = start; j < length; j++, i++) {
			float current = PriceDataStorage.priceData[j].getVortex();
			if(current > 0) {
				data1_pos[i] = 10 * current;
				data1_neg[i] = 0;
			} else {
				data1_neg[i] = 10 * current;
				data1_pos[i] = 0;
			}
			data2[i] = 10 * PriceDataStorage.priceData[j].getCmfInd();		
			
			//Log.d("GRAPH","Date:\t" + PriceDataStorage.priceData[j].getDate());
			
			/*
			if(data1[i] < data2[i]) {
				if (minY > data1[i]) {
					minY = data1[i];
				}
				if (maxY < data2[i]) {
					maxY = data2[i];
				}
			} else {
				if (minY > data2[i]) {
					minY = data2[i];
				}
				if (maxY < data1[i]) {
					maxY = data1[i];
				}
			}	*/
		}

		values.add(data1_pos);
		values.add(data1_neg);
		values.add(data2);

		float[] diff = new float[startDate];
		for (int i = 0; i < startDate; i++) {
			diff[i] = 0; //open[i] - close[i];
			if (diff[i] > maxY) {
				maxY = diff[i];
			}
			if (diff[i] < minY) {
				minY = diff[i];
			}
		}
		values.add(diff);

		minY = 0 - screenScaleY;
		maxY = 0 + screenScaleY;

		int[] colors = new int[] { Color.GREEN, Color.RED, Color.YELLOW, Color.WHITE };
		PointStyle[] styles = new PointStyle[] { PointStyle.POINT,
				PointStyle.POINT, PointStyle.POINT, PointStyle.POINT };
		XYMultipleSeriesRenderer renderer = ChartMethods.buildRenderer(colors,
				styles);
		ChartMethods.setChartSettings(renderer, "Open vs Close", "Month",
				"Price", minX, maxX, minY, maxY, Color.GRAY, Color.GRAY);
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
		length = renderer.getSeriesRendererCount();

		for (int i = 0; i < length; i++) {
			XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer
					.getSeriesRendererAt(i);
			/*if (i == length - 1) {
				FillOutsideLine fill = new FillOutsideLine(
						FillOutsideLine.Type.BOUNDS_ALL);
				fill.setColor(Color.CYAN);
				seriesRenderer.addFillOutsideLine(fill);
			}*/
			seriesRenderer.setLineWidth(3.5f);
			//seriesRenderer.setDisplayChartValues(true);
			//seriesRenderer.setChartValuesTextSize(10f);
		}
		return ChartFactory.getCubeLineChartView(this,
				ChartMethods.buildBarDataset(titles, values), renderer, 0.5f);
	}

	private void setChartPeriod(String period) {
		String[] periods = context.getResources().getStringArray(
				R.array.chart_periods);
		if(period.equals(periods[0])) {
			//1m
			startDate = 30;
		} else if(period.equals(periods[1])) {
			//3m
			startDate = 90;
		} else if(period.equals(periods[2])) {
			//6m
			startDate = 180;
		} else if(period.equals(periods[3])) {
			//1y
			startDate = 360;
		} else if(period.equals(periods[4])) {
			//2y
			startDate = 720;
		} else if(period.equals(periods[5])) {
			//3y
			startDate = 1080;
		}
	}

	private class CustomOnItemSelectedListener implements
			OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			setChartPeriod(parent.getItemAtPosition(pos).toString());
			displayGraph();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}

	}
}
