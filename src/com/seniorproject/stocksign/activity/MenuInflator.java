package com.seniorproject.stocksign.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.searching.SearchStockActivity;

public class MenuInflator {

	private Activity activity = null;
	
	public MenuInflator(Activity activity) {
		this.activity = activity;
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		if(activity == null) {
			Log.e("MENU","activity is null");
			return false;
		}
		// Inflate the menu; this adds items to the action bar if it is present.
		activity.getMenuInflater().inflate(R.menu.main, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);
		// Configure the search info and add any event listeners

		// Get the SearchView and set the searchable configuration
		// SearchManager searchManager = (SearchManager)
		// getSystemService(Context.SEARCH_SERVICE);
		// SearchView searchView = (SearchView)
		// menu.findItem(R.id.action_search).getActionView();
		// Assumes current activity is the searchable activity
		// searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		// searchView.setIconifiedByDefault(false); // Do not iconify the
		// widget; expand it by default
		// searchView.setSubmitButtonEnabled(true);
		return true;
	}

	private void performSearch() {
		if(activity != null) {
			Intent doSearch = new Intent(activity, SearchStockActivity.class);
			activity.startActivity(doSearch);
		}
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle item selection
		if(item == null) {
			Log.e("MENU","menu item is null");
			return false;
		}
		
		if(item.getItemId() == R.id.action_search) {
			performSearch();
			return true;
		} else {
			return false;
		}
		
		/*switch (item.getItemId()) {
		case R.id.action_settings:
			showSettings();
			return true;
		case R.id.action_refresh:
			refresh();
			return true;
		case R.id.action_search:
			performSearch();
			return true;
		case R.id.action_aboutus:
			showAboutUs();
			return true;
		case R.id.action_exit:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}*/
	}
	
}
