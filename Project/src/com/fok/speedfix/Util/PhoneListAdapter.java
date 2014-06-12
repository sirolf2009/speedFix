package com.fok.speedfix.Util;

import java.util.List;

import com.fok.speedfix.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PhoneListAdapter extends ArrayAdapter<String> {

	private Context context;
	private List<String> objects;

	public PhoneListAdapter(Context context, int textViewResourceId, List<String> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.objects = objects;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.phone_list_item, null);
		}

		TextView itemView = (TextView) view.findViewById(R.id.textView1);
		if (itemView != null) {
			itemView.setText(objects.get(position));
		}

		return view;
	}

}
