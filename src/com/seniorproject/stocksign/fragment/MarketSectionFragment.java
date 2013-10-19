package com.seniorproject.stocksign.fragment;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.zip.Inflater;

import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.database.Stock;
import com.seniorproject.stocksign.database.StockDataSource;
import com.seniorproject.stocksign.debugging.Debugger;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Fragment to be displayed on the Home section of the Main Activity
 * 
 * @author Sean Wilkinson
 * @since 1.0
 *
 */
public class MarketSectionFragment extends Fragment {
	

	/**Should not be instantiated, empty constructor */
	public MarketSectionFragment() {
	}
	  static String change;
	  static String close;



	/**
	 * Called when fragment is starting, where most inistialization should go
	 * @param 
	 * @return rootView The view to be displayed
	 * */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		
		View rootView = inflater.inflate(R.layout.fragment_market,
				container, false);

		TextView marketTextView = (TextView) rootView.findViewById(R.id.section_label);
		marketTextView.setText("Market Summary");
		
		new DownloadImageTask((ImageView) rootView.findViewById(R.id.MarketChart))
        .execute("http://chart.finance.yahoo.com/z?s=%5eGSPC&t=1d&q=l&l=on&z=l&c=%5EIXIC,%5EDJI&a=v&p=s&lang=en-US&region=US");
		
		
		TextView dowTextView = (TextView) rootView.findViewById(R.id.dow_name);
		dowTextView.setText("Dow");
		
		TextView spTextView = (TextView) rootView.findViewById(R.id.sp_name);
		spTextView.setText("S&P 500");
		
		TextView nasdaqTextView = (TextView) rootView.findViewById(R.id.nasdaq_name);
		nasdaqTextView.setText("Nasdaq");
		
		
		
		
		
		new DownloadMarketDataTask().execute("DJI");
		String dowchange = change;
		String dowclose = close;
		TextView dowChangeView = (TextView) rootView.findViewById(R.id.dow_change);
		System.out.println(dowclose+" here");
		dowChangeView.setText(dowclose);
		
		TextView dowCloseView = (TextView) rootView.findViewById(R.id.dow_close);
		dowCloseView.setText(dowchange);
		

				
		new DownloadMarketDataTask().execute("IXIC");
		TextView nasChangeView = (TextView) rootView.findViewById(R.id.nasdaq_change);
		System.out.println(close+" here");
		nasChangeView.setText(close);
		
		TextView nasCloseView = (TextView) rootView.findViewById(R.id.nasdaq_close);
		nasCloseView.setText(change);
		
		
		
		new DownloadMarketDataTask().execute("INX");
		TextView spChangeView = (TextView) rootView.findViewById(R.id.sp_change);
		System.out.println(close+" here");
		spChangeView.setText(close);
		
		TextView spCloseView = (TextView) rootView.findViewById(R.id.sp_close);
		spCloseView.setText(change);
		
		return rootView;
	}
	
	public static void getMarketData(){
		
		
		
		
	}
	
	public void displayMarketData(){
		
		
	}
	

}