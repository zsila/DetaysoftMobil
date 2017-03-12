package com.example.detaysoftmobil;

	import java.io.IOException;
	import java.io.InputStream;
	import java.util.ArrayList;
	import android.os.Bundle;
	import android.text.Editable;
	import android.text.TextWatcher;
	import android.util.Log;
	import android.view.LayoutInflater;
	import android.view.View;
	import android.widget.AdapterView;
	import android.widget.AdapterView.OnItemClickListener;
	import android.widget.EditText;
	import android.widget.ListView;
	import android.app.Activity;  
	import android.content.Intent;

	public class PersonelListesi extends Activity{

	private ListView personel_liste;
	static private ArrayList<personel> list=new ArrayList<personel>();
	int textlength=0;
	ListAdapter araAdapter = null;

	//ArrayList thats going to hold the search results
	ArrayList<personel> searchResults;

	LayoutInflater inflater;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);	

			
			personel_liste=(ListView) findViewById(R.id.listView1);
			
			personel_liste.setOnItemClickListener(new OnItemClickListener(){
			    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			    		
			    		Intent i = new Intent(PersonelListesi.this, Detail.class);
			    		i.putExtra("key_id", list.get(position).id);
			    		i.putExtra("key_adi", list.get(position).adi);
			    		i.putExtra("key_soyadi", list.get(position).soyadi);
			    		i.putExtra("key_kisakod", list.get(position).kisakod);
			    		i.putExtra("key_email", list.get(position).email);
			    		i.putExtra("key_telSirket", list.get(position).telSirket);   	
			    		
			    		startActivity(i);
			    	}		    	  
			   }); 
		
			 EditText myFilter = (EditText) findViewById(R.id.editText1);
			  myFilter.addTextChangedListener(new TextWatcher() {
			 
			  public void afterTextChanged(Editable s) {
			  }
			 
			  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			  }
			 
			  public void onTextChanged(CharSequence s, int start, int before, int count) {
				 
				  final ArrayList<personel> newList;
				  newList= searchList(s.toString());
				  personel_liste=(ListView) findViewById(R.id.listView1);
					
					personel_liste.setOnItemClickListener(new OnItemClickListener(){
					    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					    		
					    		Intent i = new Intent(PersonelListesi.this, Detail.class);
					    		i.putExtra("key_id", newList.get(position).id);
					    		i.putExtra("key_adi", newList.get(position).adi);
					    		i.putExtra("key_soyadi", newList.get(position).soyadi);
					    		i.putExtra("key_kisakod", newList.get(position).kisakod);
					    		i.putExtra("key_email", newList.get(position).email);
					    		i.putExtra("key_telSirket", newList.get(position).telSirket);   	
					    		
					    		startActivity(i);
					    	}		    	  
					   }); 
				  
				  
				  
				  araAdapter=new ListAdapter(getApplicationContext(), newList);
				  personel_liste.setAdapter(araAdapter);
				  personel_liste.setTextFilterEnabled(true);
			  }
			  });
			  
			   new Thread(new Runnable() { 
		            public void run(){
		            	getData();
		            }
		    }).start();
			   
	}
		
		public void getData() {
			String xml = ParseList.createXML();
			try {
				InputStream is = ServiceHttPost.callLoginControlService(ServiceUrl.userControl, xml);
				list = ParseList.parseList(is);
				araAdapter=new ListAdapter(getApplicationContext(), list);
				personel_liste.setTextFilterEnabled(true);
			
				runOnUiThread(returnRes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e("err", e.getMessage());
			}
		}
		
		private Runnable returnRes = new Runnable() {

			public void run() {
				personel_liste.setAdapter(araAdapter);
				araAdapter.notifyDataSetChanged();
			}
		};

		
		public ArrayList<personel> searchList(String s){
			ArrayList<personel> sorted=new ArrayList<personel>();
			for(int i=0; i<list.size(); i++){
				if((list.get(i).adi + " " + list.get(i).soyadi).toLowerCase().contains(s.toLowerCase()))
					sorted.add(list.get(i));
				}
			return sorted;
			}
		
		
	}




