package com.seniorproject.stocksign.searching;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.database.Companies;

public class SearchAdapter extends ArrayAdapter<String> {
	private Context context = null;
	private List<String> values = null;

	public SearchAdapter(Context context, List<String> values) {
		super(context, R.layout.search_item_two_line_row, values);
		this.context = context;
		this.values = values;
	}

	public void setValues(List<String> vals) {
		values = vals;
	}

	public void clearValues() {
		values.clear();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.search_item_two_line_row,
				parent, false);
		TextView ticker = (TextView) rowView.findViewById(R.id.tvSearchTicker);
		TextView name = (TextView) rowView.findViewById(R.id.tvSearchName);
		if (values != null) {
			ticker.setText(values.get(position));
			name.setText(Companies.getNames().get(ticker.getText().toString()));
		}

		return rowView;
	}
}
