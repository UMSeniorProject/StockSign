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
import com.seniorproject.stocksign.activity.ApplicationConstants;
import com.seniorproject.stocksign.database.Stock;
import com.seniorproject.stocksign.display.DisplayStockRatioData;
import com.seniorproject.stocksign.display.DividendScore;
import com.seniorproject.stocksign.display.GrowthScore;
import com.seniorproject.stocksign.display.TotalScore;
import com.seniorproject.stocksign.kinveyconnection.ConnectToKinveyTask;
import com.seniorproject.stocksign.kinveyconnection.KinveyConnectionSingleton;

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

		kinveyDataFetcher(rootView, ApplicationConstants.TOTALSCORE_COLUMN,
				ApplicationConstants.TOTALSCORE_LIMIT);
		kinveyDataFetcher(rootView, ApplicationConstants.DIVSCORE_COLUMN,
				ApplicationConstants.DIVSCORE_LIMIT);
		kinveyDataFetcher(rootView, ApplicationConstants.GROWTHSCORE_COLUMN,
				ApplicationConstants.GROWTHSCORE_LIMIT);
		loadSectorData();

		TextView hotTextView = (TextView) rootView
				.findViewById(R.id.section_label);
		hotTextView.setText(ApplicationConstants.TOP_RATED_STOCKS_TITLE);

		TextView gscore = (TextView) rootView.findViewById(R.id.tvHotGrowthScoreTitle);
		TextView tscore = (TextView) rootView.findViewById(R.id.tvHotTotalScoreTitle);
		TextView dscore = (TextView) rootView.findViewById(R.id.tvHotDivScoreTitle);

		gscore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				/* make sure there are scores to show */
				if (bestGrow != null) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(), GrowthScore.class);
					String[] tickers = new String[bestGrow.length];
					String[] scores = new String[bestGrow.length];
					for (int i = 0; i < bestGrow.length; i++) {
						tickers[i] = bestGrow[i].getTicker();
						scores[i] = String.valueOf(bestGrow[i].getGrowthscore());
					}
					Bundle bundle = new Bundle();
					bundle.putStringArray(ApplicationConstants.TICKER_ARRAY,
							tickers);
					bundle.putStringArray(ApplicationConstants.SCORES_ARRAY,
							scores);
					intent.putExtra(ApplicationConstants.SCORES_BUNDLE, bundle);
					startActivity(intent);
				}
			}
		});
		tscore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				/* make sure there are scores to show */
				if (bestTotal != null) {
					Intent intent = new Intent(getActivity(), TotalScore.class);
					String[] tickers = new String[bestTotal.length];
					String[] scores = new String[bestTotal.length];
					for (int i = 0; i < bestTotal.length; i++) {
						tickers[i] = bestTotal[i].getTicker();
						scores[i] = String.valueOf(bestTotal[i].getTotalscore());
					}
					Bundle bundle = new Bundle();
					bundle.putStringArray(ApplicationConstants.TICKER_ARRAY,
							tickers);
					bundle.putStringArray(ApplicationConstants.SCORES_ARRAY,
							scores);
					intent.putExtra(ApplicationConstants.SCORES_BUNDLE, bundle);
					startActivity(intent);
				}
			}
		});
		dscore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				/* make sure there are scores to show */
				if (bestDiv != null) {
					Intent intent = new Intent(getActivity(),
							DividendScore.class);
					String[] tickers = new String[bestDiv.length];
					String[] scores = new String[bestDiv.length];
					for (int i = 0; i < bestDiv.length; i++) {
						tickers[i] = bestDiv[i].getTicker();
						scores[i] = String.valueOf(bestDiv[i].getDivscore());
					}
					Bundle bundle = new Bundle();
					bundle.putStringArray(ApplicationConstants.TICKER_ARRAY,
							tickers);
					bundle.putStringArray(ApplicationConstants.SCORES_ARRAY,
							scores);
					intent.putExtra(ApplicationConstants.SCORES_BUNDLE, bundle);
					startActivity(intent);
				}
			}
		});
		return rootView;
	}

	protected void loadSectorData() {
		ConnectToKinveyTask.kinveyFetchSectorData();
	}

	public void kinveyResponceMethod(Stock[] stocks, View rv, String scoreType) {

		// ERROR CHECKS
		// CHECK STOCKS SIZE
		// CHECK NULL INPUTS

		String missingTicker = getString(R.string.data_missing);
		String missingScore = getString(R.string.data_null);

		if (scoreType.equals(ApplicationConstants.TOTALSCORE_COLUMN)) {
			// total score table
			bestTotal = stocks;

			TextView tstock1 = (TextView) rv.findViewById(R.id.tvHotTotalScoreTicker1);
			TextView tstock2 = (TextView) rv.findViewById(R.id.tvHotTotalScoreTicker2);
			TextView tstock3 = (TextView) rv.findViewById(R.id.tvHotTotalScoreTicker3);

			TextView tgrade1 = (TextView) rv.findViewById(R.id.tvHotTotalScore1);
			TextView tgrade2 = (TextView) rv.findViewById(R.id.tvHotTotalScore2);
			TextView tgrade3 = (TextView) rv.findViewById(R.id.tvHotTotalScore3);

			if (stocks != null) {
				tstock1.setText(stocks[0].getTicker());
				tstock2.setText(stocks[1].getTicker());
				tstock3.setText(stocks[2].getTicker());

				tgrade1.setText(String.valueOf(stocks[0].getTotalscore()));
				tgrade2.setText(String.valueOf(stocks[1].getTotalscore()));
				tgrade3.setText(String.valueOf(stocks[2].getTotalscore()));

				addClick(tstock1, stocks[0]);
				addClick(tstock2, stocks[1]);
				addClick(tstock3, stocks[2]);
			} else {
				tstock1.setText(missingTicker);
				tstock2.setText(missingTicker);
				tstock3.setText(missingTicker);

				tgrade1.setText(missingScore);
				tgrade2.setText(missingScore);
				tgrade3.setText(missingScore);
			}

			return;
		}

		if (scoreType.equals(ApplicationConstants.DIVSCORE_COLUMN)) {
			// div score table
			bestDiv = stocks;

			TextView dstock1 = (TextView) rv.findViewById(R.id.tvHotDivScoreTicker1);
			TextView dstock2 = (TextView) rv.findViewById(R.id.tvHotDivScoreTicker2);
			TextView dstock3 = (TextView) rv.findViewById(R.id.tvHotDivScoreTicker3);

			TextView dgrade1 = (TextView) rv.findViewById(R.id.tvHotDivScore1);
			TextView dgrade2 = (TextView) rv.findViewById(R.id.tvHotDivScore2);
			TextView dgrade3 = (TextView) rv.findViewById(R.id.tvHotDivScore3);

			if (stocks != null) {
				dstock1.setText(stocks[3].getTicker());
				dstock2.setText(stocks[4].getTicker());
				dstock3.setText(stocks[5].getTicker());

				dgrade1.setText(String.valueOf(stocks[3].getDivscore()));
				dgrade2.setText(String.valueOf(stocks[4].getDivscore()));
				dgrade3.setText(String.valueOf(stocks[5].getDivscore()));

				addClick(dstock1, stocks[3]);
				addClick(dstock2, stocks[4]);
				addClick(dstock3, stocks[5]);
			} else {
				dstock1.setText(missingTicker);
				dstock2.setText(missingTicker);
				dstock3.setText(missingTicker);

				dgrade1.setText(missingScore);
				dgrade2.setText(missingScore);
				dgrade3.setText(missingScore);
			}

			return;
		}

		if (scoreType.equals(ApplicationConstants.GROWTHSCORE_COLUMN)) {
			// growth score table
			bestGrow = stocks;

			TextView gstock1 = (TextView) rv.findViewById(R.id.tvHotGrowthScoreTicker1);
			TextView gstock2 = (TextView) rv.findViewById(R.id.tvHotGrowthScoreTicker2);
			TextView gstock3 = (TextView) rv.findViewById(R.id.tvHotGrowthScoreTicker3);

			TextView ggrade1 = (TextView) rv.findViewById(R.id.tvHotGrowthScore1);
			TextView ggrade2 = (TextView) rv.findViewById(R.id.tvHotGrowthScore2);
			TextView ggrade3 = (TextView) rv.findViewById(R.id.tvHotGrowthScore3);

			if (stocks != null) {
				gstock1.setText(stocks[6].getTicker());
				gstock2.setText(stocks[7].getTicker());
				gstock3.setText(stocks[8].getTicker());

				ggrade1.setText(String.valueOf(stocks[6].getGrowthscore()));
				ggrade2.setText(String.valueOf(stocks[7].getGrowthscore()));
				ggrade3.setText(String.valueOf(stocks[8].getGrowthscore()));

				addClick(gstock1, stocks[6]);
				addClick(gstock2, stocks[7]);
				addClick(gstock3, stocks[8]);
			} else {
				gstock1.setText(missingTicker);
				gstock2.setText(missingTicker);
				gstock3.setText(missingTicker);

				ggrade1.setText(missingScore);
				ggrade2.setText(missingScore);
				ggrade3.setText(missingScore);
			}
			return;
		}
	}

	// fetch data
	private void kinveyDataFetcher(View rv, String scoreType, float scoreLimit) {
		Query fetchRank = mKinveyClient.query();
		fetchRank.greaterThanEqualTo(scoreType, scoreLimit);
		fetchRank.addSort(scoreType, SortOrder.DESC);
		fetchRank.setLimit(ApplicationConstants.SCORES_SECTION_LIMIT);
		ConnectToKinveyTask.kinveyFetchFragmentQuery(this, fetchRank, rv,
				scoreType);

	}

	private void addClick(TextView tv, final Stock s) {
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						DisplayStockRatioData.class);
				Bundle bundle = new Bundle();
				bundle.putString(ApplicationConstants.TICKER_SINGLE,
						s.getTicker());
				intent.putExtra(ApplicationConstants.RATIO_BUNDLE, bundle);
				startActivity(intent);
			}

		});

	}

}