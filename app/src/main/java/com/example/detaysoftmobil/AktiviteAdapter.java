
package com.example.detaysoftmobil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AktiviteAdapter extends ArrayAdapter<String> {
			
	final Map<String, Drawable> drawableMap=new HashMap<String, Drawable>();		
	private final Context context;
	private final ArrayList<String> myList;
	
	public AktiviteAdapter(Context context, ArrayList<String> liste) {
		super(context, R.layout.list_row, liste);
		this.context = context;
		this.myList =liste;
	}

	@Override    
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);				
		View rowView = inflater.inflate(R.layout.list_row, parent, false);
		TextView tarih = (TextView) rowView.findViewById(R.id.textView_row );						 
		tarih.setText(myList.get(position));								
		return rowView;
		
	}
	
}



