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

	public class ListAdapter extends ArrayAdapter<personel> {
				
		final Map<String, Drawable> drawableMap=new HashMap<String, Drawable>();		
		private final Context context;
		private final ArrayList<personel> myList;
		
		public ListAdapter(Context context, ArrayList<personel> liste) {
			super(context, R.layout.rowlayout, liste);
			this.context = context;
			this.myList =liste;
		}

		@Override    
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);				
			View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
			TextView PersonelAdi = (TextView) rowView.findViewById(R.id.textView1 );						 
			PersonelAdi.setText(myList.get(position).adi + " " + myList.get(position).soyadi);								
			return rowView;
			
		}
		
}
