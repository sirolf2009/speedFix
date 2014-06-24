package com.fok.speedfix.util;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fok.speedfix.R;

public class EngieListAdapter extends ArrayAdapter<String> {

	private Context context;
	private List<Map<String, String>> engineers;

	public EngieListAdapter(Context context, int textViewResourceId, List<String> brands, List<Map<String, String>> engineers) {
		super(context, textViewResourceId, brands);
		this.context = context;
		this.engineers = engineers;
	}

	public View getView(int position, View view, ViewGroup parent) {
		//String brand = phoneIDs.get(position);
		
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.map_engineer, null);
		}
		
		Map<String, String> engineer = engineers.get(position);
	
		TextView itemView = (TextView) view.findViewById(R.id.companyDetailName);
		if (itemView != null) {
			itemView.setText(engineer.get("zak_bedrijfsnaam"));
		}

		itemView = (TextView) view.findViewById(R.id.companyDetailStreet);
		if (itemView != null) {
			itemView.setText(engineer.get("zak_straat")+" "+engineer.get("zak_huisnummer")+engineer.get("zak_toevoeging"));
		}
		
		itemView = (TextView) view.findViewById(R.id.pos);
		if (itemView != null) {
			itemView.setText(position+"");
		}
		
		/*itemView = (TextView) view.findViewById(R.id.companyDetailPostal);
		if (itemView != null) {
			itemView.setText(engineer.get("zak_postcode")+" "+engineer.get("zak_plaats"));
		}
		
		itemView = (TextView) view.findViewById(R.id.companyDetailTelephone);
		if (itemView != null) {
			itemView.setText(engineer.get("zak_telefoon"));
		}
		itemView = (TextView) view.findViewById(R.id.companyDetailEmail);
		if (itemView != null) {
			itemView.setText(engineer.get("zak_email"));
		}
		itemView = (TextView) view.findViewById(R.id.companyDetailKVK);
		if (itemView != null) {
			itemView.setText(engineers.get(position).get("zak_kvk"));
		}*/

		return view;
	}

}
