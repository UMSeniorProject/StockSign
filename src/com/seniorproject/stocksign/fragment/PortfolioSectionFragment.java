package com.seniorproject.stocksign.fragment;

import com.kinvey.android.Client;
import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.activity.ApplicationConstants;
import com.seniorproject.stocksign.kinveyconnection.KinveyConnectionSingleton;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PortfolioSectionFragment extends Fragment{

	Client mKinveyClient = null;
	
	/** Should not be instantiated, empty constructor */
	public PortfolioSectionFragment() {
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_portfolio,
				container, false);

		mKinveyClient = KinveyConnectionSingleton.getKinveyClient();
		
		TextView hotTextView = (TextView) rootView
				.findViewById(R.id.section_label);
		hotTextView.setText(ApplicationConstants.USER_PORTFOLIO_TITLE);
		
		return rootView;
	}

}
