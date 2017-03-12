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

public class AktiviteAdapter2 extends ArrayAdapter<aktivite>{
	
	final Map<String, Drawable> drawableMap=new HashMap<String, Drawable>();		
	private final Context context;
	private final ArrayList<aktivite> myList;
	
	public AktiviteAdapter2(Context context, ArrayList<aktivite> liste) {
		super(context, R.layout.aktivite_row, liste);
		this.context = context;
		this.myList =liste;
	}  
	
	
	@Override    
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);				
		View rowView = inflater.inflate(R.layout.aktivite_row, parent, false);
		TextView text = (TextView) rowView.findViewById(R.id.text_akt_1 );
		text.setText(myList.get(position).projectName);								
		return rowView;
		
	}

}
