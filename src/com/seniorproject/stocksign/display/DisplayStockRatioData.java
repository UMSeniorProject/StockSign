package com.seniorproject.stocksign.display;

import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.database.Stock;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DisplayStockRatioData extends Activity{

	TextView stockName;
	TextView stockTicker;
	TableLayout ratioTable;
	
	public void initialize() {
		stockName = (TextView) findViewById(R.id.tvStockname);
		stockTicker = (TextView) findViewById(R.id.tvStockticker);
		ratioTable = (TableLayout) findViewById(R.id.tlStockratios); 
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaystockdata);
		initialize();
	}
	
	public void displayRatios() {

		/*Stock currentStock = CommonStockClass.getStock();
        // Go through each item in the array
        for (int current = 0; current < numProvinces; current++)
        {
            // Create a TableRow and give it an ID
            TableRow tr = new TableRow(this);
            tr.setId(100+current);
            tr.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));   

            // Create a TextView to house the name of the province
            TextView labelTV = new TextView(this);
            labelTV.setId(200+current);
            labelTV.setText(provinces[current]);
            labelTV.setTextColor(Color.BLACK);
            labelTV.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            tr.addView(labelTV);

            // Create a TextView to house the value of the after-tax income
            TextView valueTV = new TextView(this);
            valueTV.setId(current);
            valueTV.setText("$0");
            valueTV.setTextColor(Color.BLACK);
            valueTV.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            tr.addView(valueTV);

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }*/
	}

}
