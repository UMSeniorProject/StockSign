package com.seniorproject.stocksign.graphing;

import android.graphics.Color;

public interface GraphingConstants {

	int INDICATOR_VALUE_SCALE = 10;
	int PRICE_VALUE_SCALE = 50;
	int[] COLOR_VALUES = {Color.YELLOW, Color.GREEN, 
										Color.RED, Color.CYAN,
										Color.MAGENTA, Color.WHITE,
										Color.BLUE, Color.GRAY};
	
	static String TITLE_PRICE = "PRICE";
}
