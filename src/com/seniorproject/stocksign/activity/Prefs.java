package com.seniorproject.stocksign.activity;

import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.database.DownloadRatioDataTask;
import com.seniorproject.stocksign.debugging.Debugger;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.widget.Toast;

public class Prefs extends PreferenceActivity {


	
	
    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragment()).commit();


    }

    public static class PrefsFragment extends PreferenceFragment
    {
    	
        
    
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs);
            
        }

		@Override
		public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
				Preference preference) {
			// TODO Auto-generated method stub
			
			SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
			boolean offlinem = getPrefs.getBoolean("modeswitch", false);
			
			String key = preference.getKey();
			Debugger.info("Preference click: ", key);
			
			if (key.compareToIgnoreCase("modeswitch") == 0){
				//Debugger.info("modeswitch: ", "HERE");
				if(offlinem == true)
				{
					Toast.makeText(getActivity(),  "Downloading data..." , Toast.LENGTH_SHORT).show();
					Debugger.info("Offline Mode ", "DOWNLOADING DATA");
					//new DownloadRatioDataTask().execute();
					Toast.makeText(getActivity(),  "Done" , Toast.LENGTH_SHORT).show();
				}
				else if(offlinem == false)
				{
					//Toast.makeText(getActivity(),  "Switch was already on, turning off" , Toast.LENGTH_SHORT).show();

				}
			}
			
			else if (key.compareToIgnoreCase("deletedata") == 0){
				Toast.makeText(getActivity(),  "Deleting data..." , Toast.LENGTH_SHORT).show();
				Debugger.info("Offline Mode ", "DELETING DATA");
				//droptables
				Toast.makeText(getActivity(),  "Done" , Toast.LENGTH_SHORT).show();
			}
			else if (key.compareToIgnoreCase("updatedata") == 0){
				Toast.makeText(getActivity(),  "Updating data..." , Toast.LENGTH_SHORT).show();
				Debugger.info("Offline Mode ", "UPDATING DATA");
				//droptable
				//download data
				Toast.makeText(getActivity(),  "Done" , Toast.LENGTH_SHORT).show();
			}
			else if (key.compareToIgnoreCase("autoupdate") == 0){
				//set to auto update
			}

			
			return super.onPreferenceTreeClick(preferenceScreen, preference);
		}
		


    }
    
    
    
}