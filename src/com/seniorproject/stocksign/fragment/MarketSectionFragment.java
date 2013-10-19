package com.seniorproject.stocksign.fragment;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.database.Stock;
import com.seniorproject.stocksign.database.StockDataSource;

import android.graphics.drawable.Drawable;
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
		
		
		
		
		


		return rootView;
	}
	

}