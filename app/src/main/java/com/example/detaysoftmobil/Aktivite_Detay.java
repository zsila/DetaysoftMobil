package com.example.detaysoftmobil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import com.example.aktivite.AktiviteAdapter2;
import com.example.aktivite.BelirliAktiviteDetay;
import com.example.aktivite.ParseActivityList;
import com.example.aktivite.YeniAktivite;
import com.example.aktivite.aktivite;
import com.example.aktivite.location;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class Aktivite_Detay extends Activity{
	
	private ListView list;
    private ImageView yeni;
	private ArrayList<aktivite> aktivite_listesi=new ArrayList<aktivite>();
	private ArrayList<String> empty_=new ArrayList<String>();
	
	AktiviteAdapter2 adapter_;
	String req_xml;
	
	private ProgressDialog dialog;   	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.aktivite_detay);
		
		Intent intent = getIntent();
		final String k_kullanici = intent.getExtras().getString("key_kullanici");	
		final String k_parola = intent.getExtras().getString("key_parola");

		new StartService().execute();  
		
		empty_.add(" ");
		
		list = (ListView) findViewById(R.id.aktivite_list);	
	 
		list.setOnItemClickListener(new OnItemClickListener(){
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	 
	    		String sila = "";
	    		for (int i = 0; i < aktivite_listesi.get(position).locations.size(); i++) {
					if (aktivite_listesi.get(position).locations.get(i).locno.equals(aktivite_listesi.get(position).locNo)) {
						sila = aktivite_listesi.get(position).locations.get(i).adr01;
					}
				}
	    			
	  		  
	    		Intent i = new Intent(Aktivite_Detay.this, BelirliAktiviteDetay.class);
	    		i.putExtra("key_kullanici", k_kullanici);
	    		i.putExtra("key_parola", k_parola);
	    		
	    		i.putExtra("key_projeAdi", aktivite_listesi.get(position).projectName);
	    		i.putExtra("key_proNo", aktivite_listesi.get(position).proje_no);
	    		i.putExtra("key_perNo", aktivite_listesi.get(position).personel_no);
	    		i.putExtra("key_cusNo", aktivite_listesi.get(position).kunnr);
	    		i.putExtra("key_date", aktivite_listesi.get(position).baslangic_tarihi);
	    		i.putExtra("key_locNo", aktivite_listesi.get(position).locNo);
	    		
	    		if(aktivite_listesi.get(position).locations.size() > 0)
	  				i.putStringArrayListExtra("key_lokasyonlar", (ArrayList<String>) aktivite_listesi.get(position).location_names);
	    		else
	    			i.putStringArrayListExtra("key_lokasyonlar", (ArrayList<String>) empty_);
	    		
	    		
	    		
	    		if(aktivite_listesi.get(position).locations.size() > 0)
	  				i.putStringArrayListExtra("key_nolar", (ArrayList<String>) aktivite_listesi.get(position).location_nos);
	    		else
	    			i.putStringArrayListExtra("key_nolar", (ArrayList<String>) empty_);
	    		
	    		
	    		i.putExtra("key_loc", sila);
	    		  		
	    		i.putExtra("key_seqNo", aktivite_listesi.get(position).seqno);
	    		i.putExtra("key_faturaSaati", aktivite_listesi.get(position).akhour);
	    		i.putExtra("key_baslangicTarihi", aktivite_listesi.get(position).baslangic_tarihi);
	    		i.putExtra("key_aktiviteSaati", aktivite_listesi.get(position).work_hour);
	    		i.putExtra("key_projeYeri", aktivite_listesi.get(position).place);
	    		i.putExtra("key_aciklama", aktivite_listesi.get(position).Txt01 + aktivite_listesi.get(position).Txt02 + aktivite_listesi.get(position).Txt03 + aktivite_listesi.get(position).Txt04 + aktivite_listesi.get(position).Txt05);
	    		startActivity(i);    		
	    		}	    	  
	   }); 				  
		 
		
		yeni = (ImageView) findViewById(R.id.imageView1);
		
		yeni.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Aktivite_Detay.this, YeniAktivite.class);				
				startActivity(i);				
			}
		});
	}
	
	@Override
	public void onResume()
	    {  // After a pause OR at startup
	    super.onResume();
	    Intent intent = getIntent();
		String k_baslangic = intent.getExtras().getString("key_baslangic");
		String k_bitis = intent.getExtras().getString("key_bitis");		
		
		  
		String[] test_bas = k_baslangic.split("/");
		String[] test_bit = k_bitis.split("/");
		
		String baslangic = test_bas[2]+test_bas[1]+test_bas[0]; 
		String bitis = test_bit[2]+test_bit[1]+test_bit[0];
	    String xml = ParseActivityList.createXML(Login.user_no, baslangic, bitis);
		InputStream is;
		try {
			is = ServiceHttPost.callLoginControlService(ServiceUrl.belirli_tarih_aktiviteleri, xml);
			 aktivite_listesi = ParseActivityList.parseActivityList(is);
			 
			 
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		adapter_=new AktiviteAdapter2(getApplicationContext(), aktivite_listesi);
		list.setAdapter(adapter_);
		
	}

	
	private class StartService extends AsyncTask<Void, Void, ArrayList<aktivite>>{		

		@Override
		protected ArrayList<aktivite> doInBackground(Void... arg0) {		
			Intent intent = getIntent();
			String k_baslangic = intent.getExtras().getString("key_baslangic");
			String k_bitis = intent.getExtras().getString("key_bitis");
			  
			String[] test_bas = k_baslangic.split("/");
			String[] test_bit = k_bitis.split("/");
			
			String baslangic = test_bas[2]+test_bas[1]+test_bas[0]; 
			String bitis = test_bit[2]+test_bit[1]+test_bit[0];	
			
				  
			   try {
               	    String xml = ParseActivityList.createXML(Login.user_no, baslangic, bitis);
					InputStream is = ServiceHttPost.callLoginControlService(ServiceUrl.belirli_tarih_aktiviteleri, xml);
					aktivite_listesi = ParseActivityList.parseActivityList(is);				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();					
				}
               return aktivite_listesi;
		}
		
				
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
	        dialog = new ProgressDialog(Aktivite_Detay.this);
	        dialog.setMessage("Yükleniyor...");
	        dialog.show();
		}
		 
		
		protected void onPostExecute(ArrayList<aktivite> aktivite_listesi) {			
			adapter_=new AktiviteAdapter2(getApplicationContext(), aktivite_listesi);
			list.setAdapter(adapter_);
	        dialog.dismiss();	      			
	     }		
	}	
	
	
	public static String convertUsername(String username){
		String user = "";
		if(username.charAt(1) != '0')
			user = username.substring(1);
		else if(username.charAt(1) == '0' && username.charAt(2) != '0')
			user = username.substring(2);
		else if(username.charAt(1) == '0' && username.charAt(2) == '0' && username.charAt(3) != '0')
			user = username.substring(3);
		else if(username.charAt(1) == '0' && username.charAt(2) == '0' && username.charAt(3) == '0' && username.charAt(4) != '0')
			user = username.substring(3);
		return user;
	}	
}