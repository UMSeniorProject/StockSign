/**
 * 
 */
package com.seniorproject.stocksign.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.activity.MainActivity;
import com.seniorproject.stocksign.debugging.Debugger;

/**
 * @author Sean
 *
 */
public class NewsSectionFragment extends ListFragment  {
	
	   static List<String> headlines;
	   static List<String> links;
	   
	   private Context context;

	/**Should not be instantiated, empty constructor */
	public NewsSectionFragment() {
		
		
		
	}

	/**
	 * Called when fragment is starting, where most inistialization should go
	 * @param 
	 * @return rootView The view to be displayed
	 * */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_news,
				container, false);
		//setContentView(R.layout.fragment_news);
		
		
		//TextView homeTextView = (TextView) rootView.findViewById(R.id.section_label);
		//homeTextView.setText("Section to display news");
		
		// Initializing instance variables
		headlines = new ArrayList<String>();
		links = new ArrayList<String>();
		new DownloadNewsTask(this.getActivity()).execute();
		 
    	//Debugger.info("frag headlines ", headlines.toString());

		context = getActivity();
		// Binding data
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, headlines);
		
		setListAdapter(adapter);



		return rootView;
	}
	

	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	   Uri uri = Uri.parse((String) links.get(position));
	   Intent intent = new Intent(Intent.ACTION_VIEW, uri);
	   startActivity(intent);
	}
}