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
	 DownloadNewsTask(Activity current_callingActivity){
		 callingActivity = current_callingActivity;
		
	 }
	protected void getURL(){
		
		try {
		    URL url = new URL("http://feeds.marketwatch.com/marketwatch/topstories");
		    //http://feeds.marketwatch.com/marketwatch/topstories
		 
		    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		    factory.setNamespaceAware(false);
		    XmlPullParser xpp = factory.newPullParser();
		 
		        // We will get the XML from an input stream
		    
		    xpp.setInput(getInputStream(url), "UTF_8");
		 
		        /* We will parse the XML content looking for the "<title>" tag which appears inside the "<item>" tag.
		         * However, we should take in consideration that the rss feed name also is enclosed in a "<title>" tag.
		         * As we know, every feed begins with these lines: "<channel><title>Feed_Name</title>...."
		         * so we should skip the "<title>" tag which is a child of "<channel>" tag,
		         * and take in consideration only "<title>" tag which is a child of "<item>"
		         *
		         * In order to achieve this, we will make use of a boolean variable.
		         */
		    boolean insideItem = false;
		 
		        // Returns the type of current event: START_TAG, END_TAG, etc..
		    int eventType = xpp.getEventType();
		    while (eventType != XmlPullParser.END_DOCUMENT) {
		        if (eventType == XmlPullParser.START_TAG) {
		 
		            if (xpp.getName().equalsIgnoreCase("item")) {
		                insideItem = true;
		            } else if (xpp.getName().equalsIgnoreCase("title")) {
		                if (insideItem){
		                    NewsSectionFragment.headlines.add(xpp.nextText()); //extract the headline
		                	//Debugger.info("bg headlines ", NewsSectionFragment.headlines.toString());
		                }
		            } else if (xpp.getName().equalsIgnoreCase("link")) {
		                if (insideItem){
		                	NewsSectionFragment.links.add(xpp.nextText()); //extract the link of article
		                	//Debugger.info("bg link ", NewsSectionFragment.links.toString());

		                }
		            }
		        }else if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
		            insideItem=false;
		        }
		 
		        eventType = xpp.next(); //move to next element
		    }
		 
		} catch (MalformedURLException e) {
		    e.printStackTrace();
		} catch (XmlPullParserException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public InputStream getInputStream(URL url) {
		   try {
			   Debugger.info("getInputStream url ", url.toString());
			   Debugger.info("Get inputstream ", url.openConnection().getInputStream().toString());
		       return url.openConnection().getInputStream();
		   } catch (IOException e) {
		       return null;
		     }
		}
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		getURL();
		return null;
	}

}
