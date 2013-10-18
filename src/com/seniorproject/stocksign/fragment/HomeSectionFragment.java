package com.seniorproject.stocksign.fragment;

import java.util.List;

import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.database.Stock;
import com.seniorproject.stocksign.database.StockDataSource;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Fragment to be displayed on the Home section of the Main Activity
 * 
 * @author Sean Wilkinson
 * @since 1.0
 *
 */
public class HomeSectionFragment extends Fragment {
	
	private StockDataSource datasource;
	/**Should not be instantiated, empty constructor */
	public HomeSectionFragment() {
	}

	/**
	 * Called when fragment is starting, where most inistialization should go
	 * @param 
	 * @return rootView The view to be displayed
	 * */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_home,
				container, false);
		TextView homeTextView = (TextView) rootView.findViewById(R.id.section_label);
		// ummyTextView.setText(Integer.toString(getArguments().getInt(
		//		ARG_SECTION_NUMBER)));
		homeTextView.setText("HELLO WORLD!");
		

	  
		//fails when it tried to access stocks


	   // List<Stock> values = datasource.getAllStocks();

	    // Use the SimpleCursorAdapter to show the
	    // elements in a ListView
	   // ListAdapter adapter =  ListAdapter(this,
	     //   android.R.layout.simple_list_item_1, values);
	    //setListAdapter(adapter);
	   // TextView testText = (TextView) rootView.findViewById(R.id.section_label);
	   // testText.setText(values.toString());
		
		return rootView;
	}
}


