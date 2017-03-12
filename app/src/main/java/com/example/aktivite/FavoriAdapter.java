package com.example.aktivite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.detaysoftmobil.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FavoriAdapter extends ArrayAdapter<aktivite>{
	
	private final Context context;
	private final ArrayList<aktivite> myList;
	
	public FavoriAdapter(Context context, ArrayList<aktivite> liste) {
		super(context, R.layout.favori_listesi, liste);
		this.context = context;
		this.myList =liste;
	}  
	
	
	@Override    
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);				
		View rowView = inflater.inflate(R.layout.simple_row, parent, false);
		TextView text = (TextView) rowView.findViewById(R.id.text_akt_1 );
		text.setText(myList.get(position).projectName);								
		return rowView;
		
	}

}
