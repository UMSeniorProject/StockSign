package com.seniorproject.stocksign.searching;

import android.app.Activity;

public class DisplaySearchableActivity extends Activity{

	@Override
	public boolean onSearchRequested() {
		// TODO Auto-generated method stub
		return super.onSearchRequested();
	}

	
	/*
	Because the OtherActivity now includes a <meta-data>
	element to declare which searchable activity to use 
	for searches, the activity has enabled the search dialog.
	While the user is in this activity, the onSearchRequested()
	method activates the search dialog. When the user executes 
	the search, the system starts SearchableActivity and delivers 
	it the ACTION_SEARCH intent.

	Note: The searchable activity itself provides the search 
	dialog by default, so you don't need to add this declaration 
	to SearchableActivity.
	
	If you want every activity in your application to provide the 
	search dialog, insert the above <meta-data> element as a child 
	of the <application> element, instead of each <activity>. This 
	way, every activity inherits the value, provides the search 
	dialog, and delivers searches to the same searchable activity. 
	(If you have multiple searchable activities, you can override 
	the default searchable activity by placing a different <meta-data>
	declaration inside individual activities.)

	With the search dialog now enabled for your activities, your 
	application is ready to perform searches.


	 */
	
}
