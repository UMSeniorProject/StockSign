package com.seniorproject.stocksign.graphing;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.seniorproject.stocksign.database.PriceData;
import com.seniorproject.stocksign.database.PriceDataLists;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

public class LineGraph{

	public Intent getIntent(Context context) {

		ArrayList<Float> openPrices = PriceDataLists.getOpenPrices();
		ArrayList<Float> closePrices = PriceDataLists.getClosePrices();
		ArrayList<Float> highPrices = PriceDataLists.getHighPrices();
		ArrayList<Float> lowPrices = PriceDataLists.getLowPrices();
		
		if(openPrices == null && closePrices == null && 
		   highPrices == null && lowPrices == null) {
			openPrices = new ArrayList<Float>();
			closePrices = new ArrayList<Float>();
			highPrices = new ArrayList<Float>();
			lowPrices = new ArrayList<Float>();
		}
		
		// Our first data
		//int[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }; // x values!
		//int[] y =  { 30, 34, 45, 57, 77, 89, 100, 111 ,123 ,145 }; // y values!
		
		TimeSeries openSeries = new TimeSeries("Open"); 
		for( int i = 0; i < openPrices.size(); i++) {
			openSeries.add(openPrices.get(i), i);
		}
		
		TimeSeries closeSeries = new TimeSeries("Close"); 
		for( int i = 0; i < closePrices.size(); i++) {
			closeSeries.add(closePrices.get(i), i);
		}
		
		TimeSeries highSeries = new TimeSeries("High"); 
		for( int i = 0; i < highPrices.size(); i++) {
			highSeries.add(highPrices.get(i), i);
		}
		
		TimeSeries lowSeries = new TimeSeries("Low"); 
		for( int i = 0; i < lowPrices.size(); i++) {
			lowSeries.add(lowPrices.get(i), i);
		}
		
		// Our second data
		/*int[] x2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }; // x values!
		int[] y2 =  { 145, 123, 111, 100, 89, 77, 57, 45, 34, 30}; // y values!
		TimeSeries series2 = new TimeSeries("Line2"); 
		for( int i = 0; i < x2.length; i++) {
			series2.add(x2[i], y2[i]);
		}*/
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(openSeries);
		dataset.addSeries(closeSeries);
		dataset.addSeries(highSeries);
		dataset.addSeries(lowSeries);
		//dataset.addSeries(series2);
		
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRenderer and customizes the graph
		XYSeriesRenderer renderer1 = new XYSeriesRenderer(); // This will be used to customize line 1
		XYSeriesRenderer renderer2 = new XYSeriesRenderer(); // This will be used to customize line 2
		XYSeriesRenderer renderer3 = new XYSeriesRenderer(); // This will be used to customize line 1
		XYSeriesRenderer renderer4 = new XYSeriesRenderer(); // This will be used to customize line 2
		
		mRenderer.addSeriesRenderer(renderer1);
		mRenderer.addSeriesRenderer(renderer2);
		mRenderer.addSeriesRenderer(renderer3);
		mRenderer.addSeriesRenderer(renderer4);
		
		// Customization time for line 1!
		renderer1.setColor(Color.WHITE);
		renderer1.setPointStyle(PointStyle.SQUARE);
		renderer1.setFillPoints(true);
		renderer1.setLineWidth(10);
		// Customization time for line 1!
		renderer2.setColor(Color.BLUE);
		renderer2.setPointStyle(PointStyle.SQUARE);
		renderer2.setFillPoints(true);
		renderer2.setLineWidth(10);
		// Customization time for line 1!
		renderer3.setColor(Color.GREEN);
		renderer3.setPointStyle(PointStyle.SQUARE);
		renderer3.setFillPoints(true);
		renderer3.setLineWidth(10);
		// Customization time for line 1!
		renderer4.setColor(Color.RED);
		renderer4.setPointStyle(PointStyle.SQUARE);
		renderer4.setFillPoints(true);
		renderer4.setLineWidth(10);
		// Customization time for line 2!
		//renderer2.setColor(Color.YELLOW);
		//renderer2.setPointStyle(PointStyle.DIAMOND);
		//renderer2.setFillPoints(true);
		
		Intent intent = ChartFactory.getLineChartIntent(context, dataset, mRenderer, "Price Graph");
		return intent;
		
	}

}
