package com.seniorproject.stocksign.activity;

import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.debugging.Debugger;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
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
       
    }
    
    
    
}