package com.example.detaysoftmobil;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Detail extends Activity{
	TextView isim;
	TextView email;
	TextView kisakod;
	TextView telSirket;
	TextView telCep;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		
		Intent intent = getIntent();
		String k_id = intent.getExtras().getString("key_id");
		String k_adi = intent.getExtras().getString("key_adi");
		String k_soyadi = intent.getExtras().getString("key_soyadi");
		final String k_kisakod = intent.getExtras().getString("key_kisakod");
		String k_email = intent.getExtras().getString("key_email");
		final String k_telSirket = intent.getExtras().getString("key_telSirket");
		
		isim =(TextView) findViewById(R.id.textView1);	
		isim.setText(k_adi+" "+k_soyadi);
		
		
		email =(TextView) findViewById(R.id.textView2);	
		email.setText(k_email);
			
		
		kisakod = (TextView) findViewById(R.id.textView3);
		kisakod.setText(k_kisakod);
		kisakod.setOnClickListener(new OnClickListener() {

	        public void onClick(View v) {
	            try {
	                    Intent callIntent = new Intent(Intent.ACTION_CALL);
	                    callIntent.setData(Uri.parse("tel:"+k_kisakod));
	                    startActivity(callIntent);
	                } catch (ActivityNotFoundException activityException) {
	                    Log.e("Calling a Phone Number", "Call failed", activityException);
	                }
	        }
	          
		});
		
		
		
		telSirket = (TextView) findViewById(R.id.textView4);
		telSirket.setText(k_telSirket);
		telSirket.setOnClickListener(new OnClickListener() {

	        public void onClick(View v) {
	            try {
	                    Intent callIntent = new Intent(Intent.ACTION_CALL);
	                    callIntent.setData(Uri.parse("tel:"+k_telSirket));
	                    startActivity(callIntent);
	                } catch (ActivityNotFoundException activityException) {
	                    Log.e("Calling a Phone Number", "Call failed", activityException);
	                }
	        }
	        
		});
		}
	}

			

