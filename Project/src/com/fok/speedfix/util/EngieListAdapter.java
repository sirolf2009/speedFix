package com.fok.speedfix.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.fok.speedfix.R;

public class EngieListAdapter extends ArrayAdapter<String> {

	private Context context;
	private List<Map<String, String>> engineers;
	private static List<View> items = new ArrayList<View>();
	public static EngieListAdapter instance;

	public EngieListAdapter(Context context, int textViewResourceId, List<String> brands, List<Map<String, String>> engineers) {
		super(context, textViewResourceId, brands);
		instance = this;
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
			itemView.setText(engineer.get("zak_straat")+" "+engineer.get("zak_huisnummer")+engineer.get("zak_toevoeging")+", "+engineer.get("zak_postcode"));
		}

		itemView = (TextView) view.findViewById(R.id.pos);
		if (itemView != null) {
			itemView.setText(position+"");
		}
		
		itemView = (TextView) view.findViewById(R.id.posIndicator);
		if (itemView != null) {
			itemView.setText(""+(position+1));
		}
		
		final TextView priceView = (TextView) view.findViewById(R.id.companyDetailPrice);
		if (priceView != null) {
		}
		return view;
	}

	public static class GetPhoneInfo implements IJsonResponse {

		private List<Map<String, String>> devices, companies, proposals;
		private Context context;
		private TextView v;

		public GetPhoneInfo(Context context, View v) {
			this.context = context;
			this.v = (TextView) v;
			new Helper.GetAllDevices(this).execute();
			new Helper.GetAllProposals(this).execute();
			new Helper.GetAllBusinesses(this).execute();
		}

		@Override
		public void getResponse(List<Map<String, String>> data, String tag) {
			if(tag.equals(Helper.GetAllDevices.tag)) {
				devices = data;
			} else if(tag.equals(Helper.GetAllProposals.tag)) {
				proposals = data;
			} else if(tag.equals(Helper.GetAllBusinesses.tag)) {
				companies = data;
			}
			if(devices == null || companies == null || proposals == null) {
				return;
			}
			String phoneID = Storage.readPhoneID(context);
			if(phoneID == null) {
				return;
			}
			for(Map<String, String> phone : devices) {
				if(phone.get("device_id").equals(phoneID)) {
					for(Map<String, String> prop : proposals) {
						if(prop.get("device_id").equals(phoneID)) {
							String price = prop.get("prop_bod");
							for(Map<String, String> bizz : companies) {
								if(bizz.get("zak_id").equals(prop.get("zak_id"))) {
									v.setText(price);
									Log.i(((TextView)v.findViewById(R.id.companyDetailName)).getText().toString()+" "+(bizz.get("zak_bedrijfsnaam")));
									if( ((TextView)v.findViewById(R.id.companyDetailName)).getText().toString().equals(bizz.get("zak_bedrijfsnaam"))) {
										((TextView)v.findViewById(R.id.companyDetailPrice)).setText(price);
									}
									v.postInvalidate();
								}
							}
						}
					}
				}
			}
		}
	}


}
