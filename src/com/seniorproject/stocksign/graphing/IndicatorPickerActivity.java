package com.seniorproject.stocksign.graphing;

import java.util.Set;

import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.activity.ApplicationConstants;
import com.seniorproject.stocksign.database.PriceDataStorage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class IndicatorPickerActivity extends Activity {

	private CheckBox indicator1 = null;
	private CheckBox indicator2 = null;
	private CheckBox indicator3 = null;
	private CheckBox indicator4 = null;
	private Button displayBtn = null;

	private Context context = null;
	private SharedPreferences indicatorSettings = null;
	
	private void initializeXML() {
		indicator1 = (CheckBox) findViewById(R.id.cbInd0);
		indicator2 = (CheckBox) findViewById(R.id.cbInd1);
		indicator3 = (CheckBox) findViewById(R.id.cbInd2);
		indicator4 = (CheckBox) findViewById(R.id.cbInd3);
		displayBtn = (Button) findViewById(R.id.btDisplay);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.indicator_picker);
		context = this;
		initializeXML();
		setupIndicatorText();
		setupIndicatorCheckBoxes();
		setupIndicatorListeners();
		setupDisplayButton();
	}

	// TODO: indicator names should be added programmatically
	private void setupIndicatorText() {
		if (PriceDataStorage.getIndicatorMap().size() == ApplicationConstants.INDICATORS_SIZE) {
			indicator1.setText(PriceDataStorage.getIndicatorMap()
					.get("MACDsum").toString());
			indicator2.setText(PriceDataStorage.getIndicatorMap()
					.get("MACDslope").toString());
			indicator3.setText(PriceDataStorage.getIndicatorMap().get("CMFInd")
					.toString());
			indicator4.setText(PriceDataStorage.getIndicatorMap().get("Vortex")
					.toString());
		} else {
			Log.e("GRAPH", "size of indicators doesn't match");
		}
	}

	private void setupIndicatorCheckBoxes() {
		if (PriceDataStorage.getIndicatorMap()
				.get(indicator1.getText().toString()).isChecked()) {
			indicator1.setChecked(true);
		}
		if (PriceDataStorage.getIndicatorMap()
				.get(indicator2.getText().toString()).isChecked()) {
			indicator2.setChecked(true);
		}
		if (PriceDataStorage.getIndicatorMap()
				.get(indicator3.getText().toString()).isChecked()) {
			indicator3.setChecked(true);
		}
		if (PriceDataStorage.getIndicatorMap()
				.get(indicator4.getText().toString()).isChecked()) {
			indicator4.setChecked(true);
		}

	}

	private void setupIndicatorListeners() {
		
		indicator1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//get shared preferences
				indicatorSettings = getSharedPreferences(
						ApplicationConstants.INDICATOR_PREFERENCES, 0);
				SharedPreferences.Editor editor = indicatorSettings.edit();
				
				//update checked status of this indicator
				String indName = indicator1.getText().toString();
				PriceDataStorage.getIndicatorMap()
						.get(indName).setCheckedValue(indicator1.isChecked());
				
				//if indicator is checked and not in the sharedprefs, add it
				if(indicator1.isChecked() && !indicatorSettings.contains(indName)) {
					editor.putBoolean(indName, true).commit();
				}
				//if indicators isn't checked and it is the sharedprefs, remove it
				if(!indicator1.isChecked() && indicatorSettings.contains(indName)) {
					editor.remove(indName).commit();
				}
			}

		});

		indicator2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//get shared preferences
				indicatorSettings = getSharedPreferences(
						ApplicationConstants.INDICATOR_PREFERENCES, 0);
				SharedPreferences.Editor editor = indicatorSettings.edit();
				
				//update checked status of this indicator
				String indName = indicator2.getText().toString();
				PriceDataStorage.getIndicatorMap()
						.get(indName).setCheckedValue(indicator2.isChecked());
				
				//if indicator is checked and not in the sharedprefs, add it
				if(indicator2.isChecked() && !indicatorSettings.contains(indName)) {
					editor.putBoolean(indName, true).commit();
				}
				//if indicators isn't checked and it is the sharedprefs, remove it
				if(!indicator2.isChecked() && indicatorSettings.contains(indName)) {
					editor.remove(indName).commit();
				}
			}

		});

		indicator3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//get shared preferences
				indicatorSettings = getSharedPreferences(
						ApplicationConstants.INDICATOR_PREFERENCES, 0);
				SharedPreferences.Editor editor = indicatorSettings.edit();
				
				//update checked status of this indicator
				String indName = indicator3.getText().toString();
				PriceDataStorage.getIndicatorMap()
						.get(indName).setCheckedValue(indicator3.isChecked());
				
				//if indicator is checked and not in the sharedprefs, add it
				if(indicator3.isChecked() && !indicatorSettings.contains(indName)) {
					editor.putBoolean(indName, true).commit();
				}
				//if indicators isn't checked and it is the sharedprefs, remove it
				if(!indicator3.isChecked() && indicatorSettings.contains(indName)) {
					editor.remove(indName).commit();
				}
			}

		});

		indicator4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//get shared preferences
				indicatorSettings = getSharedPreferences(
						ApplicationConstants.INDICATOR_PREFERENCES, 0);
				SharedPreferences.Editor editor = indicatorSettings.edit();
				
				//update checked status of this indicator
				String indName = indicator4.getText().toString();
				PriceDataStorage.getIndicatorMap()
						.get(indName).setCheckedValue(indicator4.isChecked());
				
				//if indicator is checked and not in the sharedprefs, add it
				if(indicator4.isChecked() && !indicatorSettings.contains(indName)) {
					editor.putBoolean(indName, true).commit();
				}
				//if indicators isn't checked and it is the sharedprefs, remove it
				if(!indicator4.isChecked() && indicatorSettings.contains(indName)) {
					editor.remove(indName).commit();
				}
			}

		});
	}

	private void setupDisplayButton() {
		displayBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*Intent intent = new Intent(context, GraphActivity.class);
				startActivity(intent);*/
				finish();
				// to get rid of the animation restart
				overridePendingTransition(0, 0);
			}

		});
	}

}
