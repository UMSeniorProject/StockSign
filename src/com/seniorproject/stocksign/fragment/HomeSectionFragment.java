package com.seniorproject.stocksign.fragment;

import com.seniorproject.stocksign.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeSectionFragment extends Fragment {
	
	public HomeSectionFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_home,
				container, false);
		TextView homeTextView = (TextView) rootView.findViewById(R.id.section_label);
		// ummyTextView.setText(Integer.toString(getArguments().getInt(
		//		ARG_SECTION_NUMBER)));
		homeTextView.setText("HELLO WORLD!");
		
		return rootView;
	}
}


