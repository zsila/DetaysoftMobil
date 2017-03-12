package com.example.detaysoftmobil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends Activity {

	@Override    
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anasayfa);	
		
		Intent intent = getIntent();
		final String k_kullanici = intent.getExtras().getString("key_kullanici");
		final String k_parola = intent.getExtras().getString("key_parola");
		
		
		final ImageView Pers_Liste = (ImageView) findViewById(R.id.imageView1);
		
		Pers_Liste.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	Intent i = new Intent(MainActivity.this, PersonelListesi.class);
	        	startActivity(i);
            }
        });		
		
		final ImageView Aktivitelerim = (ImageView) findViewById(R.id.imageView2);
		
		Aktivitelerim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {    
                // Perform action on click
            	Intent i = new Intent(MainActivity.this, Aktiviteler.class);
	    		i.putExtra("key_kullanici", k_kullanici);
	    		i.putExtra("key_parola", k_parola);
	        	startActivity(i);
            }
        });
		
	}
}

