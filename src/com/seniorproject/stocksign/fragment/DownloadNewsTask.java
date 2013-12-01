package com.seniorproject.stocksign.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.seniorproject.stocksign.debugging.Debugger;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;

public class DownloadNewsTask extends AsyncTask<String, Integer, String> {
	
	static Activity callingActivity;
	 DownloadNewsTask(){
		 //callingActivity = current_callingActivity;
		
	 }

	@Override
	protected String doInBackground(String... arg0) {
	
		String u = arg0[0];
		URL url;
		try {
			url = new URL(u);
			NewsSectionFragment.xpp.setInput(getInputStream(url), "UTF_8");
			
			 boolean insideItem = false;

	            // Returns the type of current event: START_TAG, END_TAG, etc..
	        int eventType = NewsSectionFragment.xpp.getEventType();
	        while (eventType != XmlPullParser.END_DOCUMENT) {
	            if (eventType == XmlPullParser.START_TAG) {

	                if (NewsSectionFragment.xpp.getName().equalsIgnoreCase("item")) {
	                    insideItem = true;
	                } else if (NewsSectionFragment.xpp.getName().equalsIgnoreCase("title")) {
	                    if (insideItem)
	                    	NewsSectionFragment.headlines.add(NewsSectionFragment.xpp.nextText()); //extract the headline
	                } else if (NewsSectionFragment.xpp.getName().equalsIgnoreCase("link")) {
	                    if (insideItem)
	                    	NewsSectionFragment.links.add(NewsSectionFragment.xpp.nextText()); //extract the link of article
	                }
	            }else if(eventType==XmlPullParser.END_TAG && NewsSectionFragment.xpp.getName().equalsIgnoreCase("item")){
	                insideItem=false;
	            }

	            eventType = NewsSectionFragment.xpp.next(); //move to next element
	        }
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		NewsSectionFragment.h = NewsSectionFragment.headlines;
		NewsSectionFragment.l= NewsSectionFragment.links;
		
	}

	public InputStream getInputStream(URL url) {
	       try {
	    	   Debugger.info("background "," get inputstream");
	           return url.openConnection().getInputStream();
	       } catch (IOException e) {
	           return null;
	         }
	    }
}
