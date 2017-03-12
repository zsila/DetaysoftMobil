package com.example.detaysoftmobil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Aktiviteler extends Activity{
	
	AktiviteAdapter aktivite_adapter = null;
	private ListView mylist;
	private ArrayList<String> aktivite_liste=new ArrayList<String>(); 
  
	private EditText edit_1, edit_2;
	private DatePicker date_picker;
	private Button git_butonu;
	
	private String ilk_tarih, son_tarih;
	  
	Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");  
    SimpleDateFormat complex_date_format = new SimpleDateFormat("dd/MM/yyyy");

     
    public static String CLASS_NAME = "Aktiviteler";
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aktivitelerim);
		

		Intent intent = getIntent();
		final String k_kullanici = intent.getExtras().getString("key_kullanici");
		final String k_parola = intent.getExtras().getString("key_parola");
		 
		
		edit_1 = (EditText) findViewById(R.id.editText_1);
		edit_2 = (EditText) findViewById(R.id.editText_2);
		date_picker = (DatePicker) findViewById(R.id.datePicker1);
		git_butonu = (Button) findViewById(R.id.git_butonu);
		
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);	
		
		edit_1.setOnClickListener(new View.OnClickListener() {
			@Override
	        public void onClick(View v) {
	        	date_picker.setVisibility(1);
	        	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	    		imm.hideSoftInputFromWindow(edit_1.getWindowToken(), 0);
	    		
	    		date_picker.init(2012, 0, 1, new OnDateChangedListener() {
	    			@Override
	    			public void onDateChanged(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
	    				edit_1.setText(date_picker.getDayOfMonth()+"/"+(date_picker.getMonth()+1)+"/"+date_picker.getYear());	
	    				ilk_tarih = date_picker.getDayOfMonth()+"/"+(date_picker.getMonth()+1)+"/"+date_picker.getYear();
	    			}
	    			});	    		
			} 
	    });

	
		edit_2.setOnClickListener(new View.OnClickListener() {
			@Override
	        public void onClick(View v) {
	        	date_picker.setVisibility(1);
	        	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	    		imm.hideSoftInputFromWindow(edit_2.getWindowToken(), 0);
	    		
	    		date_picker.init(2012, 0, 1, new OnDateChangedListener() {
	    			@Override
	    			public void onDateChanged(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
	    				edit_2.setText(date_picker.getDayOfMonth()+"/"+(date_picker.getMonth()+1)+"/"+date_picker.getYear());			    				 
	    				//Toast.makeText(getApplicationContext(), Integer.toString(dayOfMonth), Toast.LENGTH_LONG).show(); 
//	    				son_tarih_send = Integer.toString(date_picker.getYear()+(date_picker.getMonth()+1)+date_picker.getDayOfMonth());
	    				son_tarih = date_picker.getDayOfMonth()+"/"+(date_picker.getMonth()+1)+"/"+date_picker.getYear();
	    			}
	    			});    		
	    		
	        }
	    });
			
		
		git_butonu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {          	
            	
            	Intent i = new Intent(Aktiviteler.this, Aktivite_Detay.class);
            	i.putExtra("key_kullanici", k_kullanici);
            	i.putExtra("key_parola", k_parola);
            	
            	i.putExtra("key_baslangic", ilk_tarih);
            	i.putExtra("key_bitis", son_tarih);
            	
	        	startActivity(i);           	
            }
        });
		
		
		
		mylist = (ListView) findViewById(R.id.listView_1);			
		mylist.setClickable(true);
		
		mylist.setOnItemClickListener(new  ListView.OnItemClickListener(){
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    		if(Integer.toString(position).equals("0")){
	    			ilk_tarih = complex_date_format.format(calendar.getTime());
	    			son_tarih = complex_date_format.format(calendar.getTime());
	    			edit_1.setText(complex_date_format.format(calendar.getTime()));
	    		    edit_2.setText(" ");	    			
	    		    //Toast.makeText(getApplicationContext(),dateFormat.format(calendar.getTime()), Toast.LENGTH_LONG).show();
	    		}
	    		else if(Integer.toString(position).equals("1")){
	    			yesterday();
	    			//Toast.makeText(getApplicationContext(), yesterday(), Toast.LENGTH_LONG).show();
	    		}
	    		
	    		else if(Integer.toString(position).equals("2")){
	    			this_week();
	    			//Toast.makeText(getApplicationContext(), this_week(), Toast.LENGTH_LONG).show();
	    		}
	    		else if(Integer.toString(position).equals("3")){
	    			this_month();
	    			//Toast.makeText(getApplicationContext(), this_month(), Toast.LENGTH_LONG).show();
	    		}
	    		else if(Integer.toString(position).equals("4")){
	    			last_week();
	    			//Toast.makeText(getApplicationContext(), last_week(), Toast.LENGTH_LONG).show();
	    		}
	    		else if(Integer.toString(position).equals("5")){
	    			last_month();
	    			//Toast.makeText(getApplicationContext(), last_month(), Toast.LENGTH_LONG).show();
	    		}
	    		else if(Integer.toString(position).equals("6")){
	    			edit_1.setEnabled(true);
	    			edit_2.setEnabled(true);
	    			
	    			 
	    			//Toast.makeText(getApplicationContext(), last_month(), Toast.LENGTH_LONG).show();
	    		}   		
	    			
	    	}		    	  
	   }); 
		
		
		aktivite_liste.add("Bugün");
		aktivite_liste.add("Önceki Gün");
		aktivite_liste.add("Bu Hafta");
		aktivite_liste.add("Bu Ay");
		aktivite_liste.add("Geçen Hafta");
		aktivite_liste.add("Geçen Ay");
		aktivite_liste.add("Diðer");		
		
		
		aktivite_adapter=new AktiviteAdapter(getApplicationContext(), aktivite_liste);
		mylist.setAdapter(aktivite_adapter);
		
	}
	
	String yesterday() {
		   
	      // create a calendar
	      Calendar cal = Calendar.getInstance();	  

	       cal.add(Calendar.DATE, -1);
	       
	       edit_1.setText(complex_date_format.format(cal.getTime()));
	       edit_2.setText(" ");
	       
	       ilk_tarih = complex_date_format.format(cal.getTime());
	       son_tarih = complex_date_format.format(cal.getTime());
	       
	       return dateFormat.format(cal.getTime());
	      
   
	   }
	
	
	String this_week() {
		   	   
	      // create a calendar
	      Calendar cal_head = Calendar.getInstance();
	      Calendar cal_tail = Calendar.getInstance();

	      cal_head.set(Calendar.DAY_OF_WEEK, cal_head.getFirstDayOfWeek());
	      cal_tail.set(Calendar.DAY_OF_WEEK, cal_tail.getFirstDayOfWeek());
	      cal_tail.add(cal_head.DATE, 6);
	      String head_of_week = dateFormat.format(cal_head.getTime());
	      String tail_of_week = dateFormat.format(cal_tail.getTime());
	      
	      edit_1.setText(complex_date_format.format(cal_head.getTime()));
	      edit_2.setText(complex_date_format.format(cal_tail.getTime()));
	      
	      ilk_tarih = complex_date_format.format(cal_head.getTime());
	      
	      son_tarih = complex_date_format.format(cal_tail.getTime());
	      
	       
	      
	      return head_of_week+"-"+tail_of_week;
	      

	   }
	
	String this_month() {
		   
	      // create a calendar
	      Calendar cal_head = Calendar.getInstance();
	      Calendar cal_tail = Calendar.getInstance();     
	    
	      int lastDate = cal_tail.getActualMaximum(Calendar.DATE);
	      cal_tail.set(Calendar.DATE, lastDate);
	      cal_head.set(Calendar.DAY_OF_MONTH, 1);

	      String head_of_month = dateFormat.format(cal_head.getTime());
	      String tail_of_month = dateFormat.format(cal_tail.getTime());
	      
	      edit_1.setText(complex_date_format.format(cal_head.getTime()));
	      edit_2.setText(complex_date_format.format(cal_tail.getTime()));
	      
	      ilk_tarih = complex_date_format.format(cal_head.getTime());
	      
	      son_tarih = complex_date_format.format(cal_tail.getTime());
	      
	      return head_of_month+"-"+tail_of_month;         //dateFormat.format(cal_head.getTime());
	      

	   }
	
	String last_week() {
		   
	      // create a calendar
	      Calendar cal_head = Calendar.getInstance();
	      Calendar cal_tail = Calendar.getInstance();   

	      cal_head.add(Calendar.WEEK_OF_MONTH, -1);
	      
	      cal_head.set(Calendar.DAY_OF_WEEK, cal_head.getFirstDayOfWeek());
	      cal_tail.set(Calendar.DAY_OF_WEEK, cal_tail.getFirstDayOfWeek());

	      String head_of_week = dateFormat.format(cal_head.getTime());
	      String tail_of_week = dateFormat.format(cal_tail.getTime());
	      
	      edit_1.setText(complex_date_format.format(cal_head.getTime()));
	      edit_2.setText(complex_date_format.format(cal_tail.getTime()));
	      
	      ilk_tarih = complex_date_format.format(cal_head.getTime());
	      son_tarih = complex_date_format.format(cal_head.getTime());
	      
	      
	      return head_of_week+"-"+tail_of_week;
	      

	   }
	
	
	String last_month() {
		   
	      // create a calendar
	      Calendar cal_head = Calendar.getInstance();
	      Calendar cal_tail = Calendar.getInstance();   
	      
	      cal_head.set(Calendar.DAY_OF_MONTH, 1);
	      cal_head.add(Calendar.MONTH, -1);
	      
	      cal_tail.set(Calendar.DAY_OF_MONTH, 1);
	      cal_tail.add(cal_tail.DATE, -1);
	      
	      String head_of_month = dateFormat.format(cal_head.getTime());
	      String tail_of_month = dateFormat.format(cal_tail.getTime());
	      
	      edit_1.setText(complex_date_format.format(cal_head.getTime()));
	      edit_2.setText(complex_date_format.format(cal_tail.getTime()));
	      
	      ilk_tarih = complex_date_format.format(cal_head.getTime());
	      son_tarih = complex_date_format.format(cal_tail.getTime());
	      
	      return head_of_month+"-"+tail_of_month;         //dateFormat.format(cal_head.getTime());
	      

	   }
	
	
	 
}
