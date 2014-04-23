package com.seniorproject.stocksign.graphing;

import java.util.ArrayList;

import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.activity.ApplicationConstants;

import android.app.Activity;
import android.graphics.Color;
import android.text.Layout.Alignment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class IndicatorAdapter extends ArrayAdapter<IndicatorInfo>{
	private Activity context;
    private ArrayList<IndicatorInfo> data = null;

    public IndicatorAdapter(Activity context, int resource, ArrayList<IndicatorInfo> data)
    {
        super(context, resource, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {   // Ordinary view in Spinner, we use android.R.layout.simple_spinner_item
        View v = super.getView(position, convertView, parent);   
        ((TextView) v).setTextColor(Color.WHITE);
        ((TextView) v).setTextSize(ApplicationConstants.SPINNER_GRAPH_TEXT_SIZE);
        ((TextView) v).setGravity(Gravity.LEFT);
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {   // This view starts when we click the spinner.
        View row = convertView;
        if(row == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_graph, parent, false);
        }

        IndicatorInfo item = data.get(position);

        if(item != null)
        {   // Parse the data from each object and set it.
            TextView graphPeriod = (TextView) row.findViewById(R.id.tvGraphPeriod);
            if(graphPeriod != null) {
            	graphPeriod.setText(item.toString());
            	graphPeriod.setTextColor(Color.BLACK);
            }
        }

        return row;
    }
}
