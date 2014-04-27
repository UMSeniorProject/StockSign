package com.seniorproject.stocksign.graphing;

import android.graphics.Color;

public interface GraphingConstants {

	float INDICATOR_VALUE_SCALE = 10.0f;
	float PRICE_VALUE_SCALE = 50.0f;
	int PRICE_COLOR = Color.YELLOW;
	int ZERO_COLOR = Color.BLACK;
	String[] POS_COLORS = {"#006600", "#009900", "#00CC00", "#00FF00"};
	String[] NEG_COLORS = {"#990000", "#CC0000", "#DD0000", "#FF0000"};

	
	static String TITLE_PRICE = "PRICE";
}
