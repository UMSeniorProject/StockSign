/**
 * 
 */
package com.seniorproject.stocksign.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kinvey.android.Client;
import com.kinvey.java.Query;
import com.kinvey.java.query.AbstractQuery.SortOrder;
import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.activity.Utilities;
import com.seniorproject.stocksign.database.Stock;
import com.seniorproject.stocksign.display.DisplayStockRatioData;
import com.seniorproject.stocksign.display.DividendScore;
import com.seniorproject.stocksign.display.GrowthScore;
import com.seniorproject.stocksign.display.TotalScore;
import com.seniorproject.stocksign.kinveyconnection.ConnectToKinveyTask;
import com.seniorproject.stocksign.kinveyconnection.KinveyConnectionSingleton;
import com.seniorproject.stocksign.kinveyconnection.KinveyConstants;

/**
 * @author Sean
 * 
 */
public class HotStocksSectionFragment extends Fragment {

	Client mKinveyClient = null;
	Stock[] bestTotal = null;
	Stock[] bestDiv = null;
	Stock[] bestGrow = null;

	/** Should not be instantiated, empty constructor */
	public HotStocksSectionFragment() {
	}

	/**
	 * Called when fragment is starting, where most inistialization should go
	 * 
	 * @param
	 * @return rootView The view to be displayed
	 * */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_hotstocks,
				container, false);

		mKinveyClient = KinveyConnectionSingleton.getKinveyClient();
		
		kinveyDataFetcher(rootView, KinveyConstants.TOTALSCORE_COLUMN, KinveyConstants.TOTALSCORE_LIMIT);
		kinveyDataFetcher(rootView, KinveyConstants.DIVSCORE_COLUMN, KinveyConstants.DIVSCORE_LIMIT);
		kinveyDataFetcher(rootView, KinveyConstants.GROWTHSCORE_COLUMN, KinveyConstants.GROWTHSCORE_LIMIT);

		TextView hotTextView = (TextView) rootView
				.findViewById(R.id.section_label);
		hotTextView.setText(KinveyConstants.TOP_RATED_STOCKS_TITLE);

		TextView gscore = (TextView) rootView.findViewById(R.id.growthscore);
		TextView tscore = (TextView) rootView.findViewById(R.id.totalscore);
		TextView dscore = (TextView) rootView.findViewById(R.id.dividendscore);

		gscore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), GrowthScore.class);
				String[] tickers = new String[bestGrow.length];
				String[] scores = new String[bestGrow.length];
				for(int i = 0; i < bestGrow.length; i++) {
					tickers[i] = bestGrow[i].getTicker();
					scores[i] = String.valueOf(bestGrow[i].getGrowthscore());
				}
				Bundle bundle = new Bundle();
				bundle.putStringArray(KinveyConstants.TICKER_ARRAY, tickers);
				bundle.putStringArray(KinveyConstants.SCORES_ARRAY, scores);
				intent.putExtra(KinveyConstants.SCORES_BUNDLE, bundle);
				startActivity(intent);
			}
		});
		tscore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), TotalScore.class);
				String[] tickers = new String[bestTotal.length];
				String[] scores = new String[bestTotal.length];
				for(int i = 0; i < bestTotal.length; i++) {
					tickers[i] = bestTotal[i].getTicker();
					scores[i] = String.valueOf(bestTotal[i].getTotalscore());
				}
				Bundle bundle = new Bundle();
				bundle.putStringArray(KinveyConstants.TICKER_ARRAY, tickers);
				bundle.putStringArray(KinveyConstants.SCORES_ARRAY, scores);
				intent.putExtra(KinveyConstants.SCORES_BUNDLE, bundle);
				startActivity(intent);
			}
		});
		dscore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), DividendScore.class);
				String[] tickers = new String[bestDiv.length];
				String[] scores = new String[bestDiv.length];
				for(int i = 0; i < bestDiv.length; i++) {
					tickers[i] = bestDiv[i].getTicker();
					scores[i] = String.valueOf(bestDiv[i].getDivscore());
				}
				Bundle bundle = new Bundle();
				bundle.putStringArray(KinveyConstants.TICKER_ARRAY, tickers);
				bundle.putStringArray(KinveyConstants.SCORES_ARRAY, scores);
				intent.putExtra(KinveyConstants.SCORES_BUNDLE, bundle);
				startActivity(intent);
			}
		});
		return rootView;
	}
	
	public void kinveyResponceMethod(Stock[] stocks, View rv, String scoreType) {
		
		//ERROR CHECKS
		//CHECK STOCKS SIZE
		//CHECK NULL INPUTS
		
		if(scoreType.equals(KinveyConstants.TOTALSCORE_COLUMN)) {
			// total score table
			bestTotal = stocks;
			
			TextView tstock1 = (TextView) rv
					.findViewById(R.id.totalstock1);
			TextView tstock2 = (TextView) rv
					.findViewById(R.id.totalstock2);
			TextView tstock3 = (TextView) rv
					.findViewById(R.id.totalstock3);
	
			TextView tgrade1 = (TextView) rv
					.findViewById(R.id.stock1_total);
			TextView tgrade2 = (TextView) rv
					.findViewById(R.id.stock2_total);
			TextView tgrade3 = (TextView) rv
					.findViewById(R.id.stock3_total);
	
			tstock1.setText(stocks[0].getTicker());
			tstock2.setText(stocks[1].getTicker());
			tstock3.setText(stocks[2].getTicker());
	
			tgrade1.setText(String.valueOf(stocks[0].getTotalscore()));
			tgrade2.setText(String.valueOf(stocks[1].getTotalscore()));
			tgrade3.setText(String.valueOf(stocks[2].getTotalscore()));
	
			addClick(tstock1, stocks[0]);
			addClick(tstock2, stocks[1]);
			addClick(tstock3, stocks[2]);
			
			return;
		}
		
		if(scoreType.equals(KinveyConstants.DIVSCORE_COLUMN)) {
			// div score table
			bestDiv = stocks;
			
			TextView divstock1 = (TextView) rv
					.findViewById(R.id.divstock1);
			TextView divstock2 = (TextView) rv
					.findViewById(R.id.divstock2);
			TextView divstock3 = (TextView) rv
					.findViewById(R.id.divstock3);
	
			TextView divgrade1 = (TextView) rv
					.findViewById(R.id.stock1_div);
			TextView divgrade2 = (TextView) rv
					.findViewById(R.id.stock2_div);
			TextView divgrade3 = (TextView) rv
					.findViewById(R.id.stock3_div);
	
			divstock1.setText(stocks[3].getTicker());
			divstock2.setText(stocks[4].getTicker());
			divstock3.setText(stocks[5].getTicker());
	
			divgrade1.setText(String.valueOf(stocks[3].getDivscore()));
			divgrade2.setText(String.valueOf(stocks[4].getDivscore()));
			divgrade3.setText(String.valueOf(stocks[5].getDivscore()));
	
			addClick(divstock1, stocks[3]);
			addClick(divstock2, stocks[4]);
			addClick(divstock3, stocks[5]);
	
			return;
		}
		
		if(scoreType.equals(KinveyConstants.GROWTHSCORE_COLUMN)) {
			// growth score table
			bestGrow = stocks;
			
			TextView gstock1 = (TextView) rv
					.findViewById(R.id.growthstock1);
			TextView gstock2 = (TextView) rv
					.findViewById(R.id.growthstock2);
			TextView gstock3 = (TextView) rv
					.findViewById(R.id.growthstock3);
	
			TextView ggrade1 = (TextView) rv
					.findViewById(R.id.stock1_growth);
			TextView ggrade2 = (TextView) rv
					.findViewById(R.id.stock2_growth);
			TextView ggrade3 = (TextView) rv
					.findViewById(R.id.stock3_growth);
	
			gstock1.setText(stocks[6].getTicker());
			gstock2.setText(stocks[7].getTicker());
			gstock3.setText(stocks[8].getTicker());
	
			ggrade1.setText(String.valueOf(stocks[6].getGrowthscore()));
			ggrade2.setText(String.valueOf(stocks[7].getGrowthscore()));
			ggrade3.setText(String.valueOf(stocks[8].getGrowthscore()));
	
			addClick(gstock1, stocks[6]);
			addClick(gstock2, stocks[7]);
			addClick(gstock3, stocks[8]);
			
			return;
		}
	}


	// fetch data
	private void kinveyDataFetcher(View rv, String scoreType, float scoreLimit) {
		Query fetchRank = mKinveyClient.query();
		fetchRank.greaterThanEqualTo(scoreType, scoreLimit);
		fetchRank.addSort(scoreType, SortOrder.DESC);
		fetchRank.setLimit(KinveyConstants.SCORES_SECTION_LIMIT);
		ConnectToKinveyTask.kinveyFetchFragmentQuery(this, fetchRank, rv, scoreType);

	}

	private void addClick(TextView tv, final Stock s) {
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						DisplayStockRatioData.class);
				Bundle bundle = new Bundle();
				bundle.putString(KinveyConstants.TICKER_SINGLE, s.getTicker());
				intent.putExtra(KinveyConstants.RATIO_BUNDLE, bundle);
				startActivity(intent);
			}

		});

	}

}