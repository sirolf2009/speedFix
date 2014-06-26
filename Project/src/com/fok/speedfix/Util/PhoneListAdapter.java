package com.fok.speedfix.util;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fok.speedfix.R;

public class PhoneListAdapter extends ArrayAdapter<String> {

	private Context context;
	private List<Map<String, String>> phones;

	public PhoneListAdapter(Context context, int textViewResourceId, List<String> brands, List<Map<String, String>> phones) {
		super(context, textViewResourceId, brands);
		this.context = context;
		this.phones = phones;
	}

	public View getView(int position, View view, ViewGroup parent) {
		//String brand = phoneIDs.get(position);
		
		Log.i("inflating phone list");
		
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.phone_list_item, null);
		}
		
		TextView itemView = (TextView) view.findViewById(R.id.phoneID);
		if (itemView != null) {
			itemView.setText(phones.get(position).get("device_id"));
		}

		itemView = (TextView) view.findViewById(R.id.companyDetailNameDescriptor);
		if (itemView != null) {
			itemView.setText(phones.get(position).get("device_brand"));
		}
		
		itemView = (TextView) view.findViewById(R.id.companyName);
		if (itemView != null) {
			itemView.setText(phones.get(position).get("device_component"));
		}
		
		itemView = (TextView) view.findViewById(R.id.textView3);
		if (itemView != null) {
			if(phones.get(position).containsKey("lowestBid")) {
				itemView.setText("Lowest Bid: "+phones.get(position).get("lowestBid"));
				view.findViewById(R.id.moar_info).setVisibility(View.GONE);
			} else {
				itemView.setText("Retrieving bids, please wait");
			}
		}

		ImageView image = (ImageView) view.findViewById(R.id.imageAvatar);
		if(phones.get(position).get("device_brand").equalsIgnoreCase("samsung")) {
			image.setImageResource(R.drawable.samsung_logo);
		} else if(phones.get(position).get("device_brand").equalsIgnoreCase("acer")) {
			image.setImageResource(R.drawable.acer_logo);
		} else if(phones.get(position).get("device_brand").equalsIgnoreCase("asus")) {
			image.setImageResource(R.drawable.asus_logo);
		} else if(phones.get(position).get("device_brand").equalsIgnoreCase("htc")) {
			image.setImageResource(R.drawable.htc_logo);
		} else if(phones.get(position).get("device_brand").equalsIgnoreCase("huawei")) {
			image.setImageResource(R.drawable.huawei_logo);
		} else if(phones.get(position).get("device_brand").equalsIgnoreCase("lg")) {
			image.setImageResource(R.drawable.lg_logo);
		} else if(phones.get(position).get("device_brand").equalsIgnoreCase("motorola")) {
			image.setImageResource(R.drawable.motorola_logo);
		} else if(phones.get(position).get("device_brand").equalsIgnoreCase("sony")) {
			image.setImageResource(R.drawable.sony_logo);
		} else if(phones.get(position).get("device_brand").equalsIgnoreCase("kyocera")) {
			image.setImageResource(R.drawable.kyocera_logo);
		} else if(phones.get(position).get("device_brand").equalsIgnoreCase("zte")) {
			image.setImageResource(R.drawable.zte_logo);
		} else if(phones.get(position).get("device_brand").equalsIgnoreCase("nokia")) {
			image.setImageResource(R.drawable.nokia_logo);
		} else if(phones.get(position).get("device_brand").equalsIgnoreCase("google")) {
			image.setImageResource(R.drawable.google_logo);
		} else if(phones.get(position).get("device_brand").equalsIgnoreCase("alcatel")) {
			image.setImageResource(R.drawable.alcatel_logo);
		} else {
			image.setImageResource(R.drawable.unknown_logo);
		}
		
		itemView = (TextView) view.findViewById(R.id.textView9);
		if (itemView != null) {
			itemView.setText(phones.get(position).get("device_board"));
		}
		itemView = (TextView) view.findViewById(R.id.textView11);
		if (itemView != null) {
			itemView.setText(phones.get(position).get("device_brand"));
		}
		itemView = (TextView) view.findViewById(R.id.textView12);
		if (itemView != null) {
			itemView.setText(phones.get(position).get("device_device"));
		}
		itemView = (TextView) view.findViewById(R.id.textView10);
		if (itemView != null) {
			itemView.setText(phones.get(position).get("device_display"));
		}
		itemView = (TextView) view.findViewById(R.id.textView19);
		if (itemView != null) {
			itemView.setText(phones.get(position).get("device_hardware"));
		}
		itemView = (TextView) view.findViewById(R.id.textView20);
		if (itemView != null) {
			itemView.setText(phones.get(position).get("device_software"));
		}
		itemView = (TextView) view.findViewById(R.id.textView21);
		if (itemView != null) {
			itemView.setText(phones.get(position).get("device_manufacturer"));
		}
		itemView = (TextView) view.findViewById(R.id.textView22);
		if (itemView != null) {
			itemView.setText(phones.get(position).get("device_model"));
		}
		itemView = (TextView) view.findViewById(R.id.textView23);
		if (itemView != null) {
			itemView.setText(phones.get(position).get("device_product"));
		}
		itemView = (TextView) view.findViewById(R.id.textView24);
		if (itemView != null) {
			itemView.setText(phones.get(position).get("device_description"));
		}

		return view;
	}

}
