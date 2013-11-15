/**
 * 
 */
package com.seniorproject.stocksign.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seniorproject.stocksign.R;

/**
 * @author Sean
 *
 */
public class NewsSectionFragment extends Fragment {
	

	/**Should not be instantiated, empty constructor */
	public NewsSectionFragment() {
	}

	/**
	 * Called when fragment is starting, where most inistialization should go
	 * @param 
	 * @return rootView The view to be displayed
	 * */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_news,
				container, false);
		
		TextView homeTextView = (TextView) rootView.findViewById(R.id.section_label);

		homeTextView.setText("Section to display news");
		



		return rootView;
	}
}