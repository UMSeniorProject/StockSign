package com.seniorproject.stocksign.fragment;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.zip.Inflater;

import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.database.Stock;
import com.seniorproject.stocksign.database.StockDataSource;
import com.seniorproject.stocksign.debugging.Debugger;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Fragment to be displayed on the Home section of the Main Activity
 * 
 * @author Sean Wilkinson
 * @since 1.0
 *
 */
public class MarketSectionFragment extends Fragment {
	

	/**Should not be instantiated, empty constructor */
	public MarketSectionFragment() {
	}
	  static String change;
	  static String close;
	private static ImageView graph;



	/**
	 * Called when fragment is starting, where most inistialization should go
	 * @param 
	 * @return rootView The view to be displayed
	 * */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_market,
				container, false);

		//Graph
		TextView marketTextView = (TextView) rootView.findViewById(R.id.section_label);
		marketTextView.setText("Market Summary");
		 graph = (ImageView) rootView.findViewById(R.id.MarketChart);
		new DownloadImageTask(graph)
        .execute("http://chart.finance.yahoo.com/z?s=%5eGSPC&t=1d&q=l&l=on&z=l&c=%5EIXIC,%5EDJI&a=v&p=s&lang=en-US&region=US");
		
		registerForContextMenu(graph); 
		
		
		//Table info
		TextView dowTextView = (TextView) rootView.findViewById(R.id.dow_name);
		dowTextView.setText("Dow");
		
		TextView spTextView = (TextView) rootView.findViewById(R.id.sp_name);
		spTextView.setText("S&P 500");
		
		TextView nasdaqTextView = (TextView) rootView.findViewById(R.id.nasdaq_name);
		nasdaqTextView.setText("Nasdaq");

		
		new DownloadMarketDataTask().execute("DJI");
		String dowchange = change;
		String dowclose = close;
		TextView dowChangeView = (TextView) rootView.findViewById(R.id.dow_change);
		System.out.println(dowclose+" here");
		dowChangeView.setText(dowclose);
		
		TextView dowCloseView = (TextView) rootView.findViewById(R.id.dow_close);
		dowCloseView.setText(dowchange);
		

				
		new DownloadMarketDataTask().execute("IXIC");
		TextView nasChangeView = (TextView) rootView.findViewById(R.id.nasdaq_change);
		System.out.println(close+" here");
		nasChangeView.setText(close);
		
		TextView nasCloseView = (TextView) rootView.findViewById(R.id.nasdaq_close);
		nasCloseView.setText(change);
		
		
		
		new DownloadMarketDataTask().execute("INX");
		TextView spChangeView = (TextView) rootView.findViewById(R.id.sp_change);
		System.out.println(close+" here");
		spChangeView.setText(close);
		
		TextView spCloseView = (TextView) rootView.findViewById(R.id.sp_close);
		spCloseView.setText(change);
		
		return rootView;
	}
	
    @Override  
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
    super.onCreateContextMenu(menu, v, menuInfo);  
        menu.setHeaderTitle("Choose Chart Period");  
        menu.add(0, v.getId(), 0, "1 day");  
        menu.add(0, v.getId(), 0, "5 day");  
        menu.add(0, v.getId(), 0, "1 month");  
        menu.add(0, v.getId(), 0, "6 month"); 
        menu.add(0, v.getId(), 0, "1 year");  
        menu.add(0, v.getId(), 0, "5 year");
        menu.add(0, v.getId(), 0, "Max to date");  
        
    }
    
    @Override  
    public boolean onContextItemSelected(MenuItem item) {  
        if(item.getTitle()=="1 day"){oneday(item.getItemId());}  
        else if(item.getTitle()=="5 day"){fiveday(item.getItemId());} 
        else if(item.getTitle()=="1 month"){onemonth(item.getItemId());}
        else if(item.getTitle()=="6 month"){sixmonth(item.getItemId());}
        else if(item.getTitle()=="1 year"){oneyear(item.getItemId());}
        else if(item.getTitle()=="5 year"){fiveyear(item.getItemId());}
        else if(item.getTitle()=="Max to date"){max(item.getItemId());}
        else {return false;}  
    return true;  
    }  
      
    public void oneday(int id){  
        new DownloadImageTask(graph)
        .execute("http://chart.finance.yahoo.com/z?s=%5eGSPC&t=1d&q=l&l=on&z=l&c=%5EIXIC,%5EDJI&a=v&p=s&lang=en-US&region=US");
    }  
    public void fiveday(int id){   
    	new DownloadImageTask(MarketSectionFragment.graph)
        .execute("http://chart.finance.yahoo.com/z?s=%5eGSPC&t=5d&q=l&l=on&z=l&c=%5EIXIC,%5EDJI&a=v&p=s&lang=en-US&region=US");
    }  
    public void onemonth(int id){  
    	new DownloadImageTask(MarketSectionFragment.graph)
        .execute("http://chart.finance.yahoo.com/z?s=%5eGSPC&t=1m&q=l&l=on&z=l&c=%5EIXIC,%5EDJI&a=v&p=s&lang=en-US&region=US");
    }  
    public void sixmonth(int id){  
    	new DownloadImageTask(MarketSectionFragment.graph)
        .execute("http://chart.finance.yahoo.com/z?s=%5eGSPC&t=6m&q=l&l=on&z=l&c=%5EIXIC,%5EDJI&a=v&p=s&lang=en-US&region=US");
    }  
    public void oneyear(int id){  
    	new DownloadImageTask(MarketSectionFragment.graph)
        .execute("http://chart.finance.yahoo.com/z?s=%5eGSPC&t=1y&q=l&l=on&z=l&c=%5EIXIC,%5EDJI&a=v&p=s&lang=en-US&region=US");
    }  
    public void fiveyear(int id){  
    	new DownloadImageTask(MarketSectionFragment.graph)
        .execute("http://chart.finance.yahoo.com/z?s=%5eGSPC&t=5y&q=l&l=on&z=l&c=%5EIXIC,%5EDJI&a=v&p=s&lang=en-US&region=US");
    } 
    public void max(int id){  
    	new DownloadImageTask(MarketSectionFragment.graph)
        .execute("http://chart.finance.yahoo.com/z?s=%5eGSPC&t=my&q=l&l=on&z=l&c=%5EIXIC,%5EDJI&a=v&p=s&lang=en-US&region=US");
    } 
}