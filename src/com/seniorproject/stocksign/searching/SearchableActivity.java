package com.seniorproject.stocksign.searching;

import com.seniorproject.stocksign.R;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


/*
When the user wants to search, we have to call the search dialog
from within our activity. This is done by calling onSearchRequested().
This method is part of Android's Activity class and calls startSearch()
Android's search framework uses onSearchRequested() when the dialog
should be started. Thus we should use it as well.
 */

public class SearchableActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		handleIntent(getIntent());
	}
	
	public void onNewIntent(Intent i) {
		setIntent(i);
		handleIntent(i);
	}
	
	public void onListItemClick(ListView l, View v, int position, long id) {
		//call detail activity for clicked entry
	}
	
	private void handleIntent(Intent i) {
		if(i.ACTION_SEARCH.equals(i.getAction())) {
			String query = i.getStringExtra(SearchManager.QUERY);
			doSearch(query);
		}
	}
	
	private void doSearch(String queryStr) {
		//get a Cursos, prepare the List Adapter and set it
	}
	      
	/*
	When the user executes a search in the search dialog or widget,
	 the system starts your searchable activity and delivers it the
	  search query in an Intent with the ACTION_SEARCH action. Your
	   searchable activity retrieves the query from the intent's 
	   QUERY extra, then searches your data and presents the results.
	 */
	
	      /*
	     If your data is stored online, then the perceived search 
	     performance might be inhibited by the user's data connection.
	     You might want to display a spinning progress wheel until 
	     your search returns. See android.net for a reference of 
	     network APIs and Creating a Progress Dialog for information
	     about how to display a progress wheel.
	       */
	      
	      /*
	       If your data is stored in a SQLite database on the device,
	       performing a full-text search (using FTS3, rather than a
	       LIKE query) can provide a more robust search across text
	       data and can produce results significantly faster. 
	       */
	      
	      /*
	      Regardless of where your data lives and how you search it, 
	      we recommend that you return search results to your searchable
	      activity with an Adapter. This way, you can easily present all
	      the search results in a ListView. If your data comes from a 
	      SQLite database query, you can apply your results to a ListView
	      using a CursorAdapter. If your data comes in some other type of
	      format, then you can create an extension of BaseAdapter.
	       */
	      
	      /*
	      As discussed above, the recommended UI for your search results 
	      is a ListView, so you might want your searchable activity to 
	      extend ListActivity. You can then call setListAdapter(), passing
	      it an Adapter that is bound to your data. This injects all the
	      search results into the activity ListView.
	       */
}