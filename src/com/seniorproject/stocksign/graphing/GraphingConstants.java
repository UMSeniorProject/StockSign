package com.seniorproject.stocksign.graphing;

import android.graphics.Color;

public class GraphingConstants {
	static float INDICATOR_VALUE_SCALE = 1.0f;//10.0f;
	static float PRICE_VALUE_SCALE = 1.0f;//50.0f;
	static int PRICE_COLOR = Color.YELLOW;
	static int ZERO_COLOR = Color.BLACK;
	static String[] POS_COLORS = {"#006600", "#009900", "#00CC00", "#00FF00"};
	static String[] NEG_COLORS = {"#990000", "#CC0000", "#DD0000", "#FF0000"};
	static int NUMBER_OF_Y_LABELS = 4;
	static int NUMBER_OF_X_LABELS = 4;
	static int MAX_Y = 2;
	static int MIN_Y = -2;
	
	static String TITLE_PRICE = "PRICE";
}
