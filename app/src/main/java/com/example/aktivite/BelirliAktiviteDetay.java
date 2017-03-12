package com.example.aktivite;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.example.detaysoftmobil.Aktivite_Detay;
import com.example.detaysoftmobil.Login;
import com.example.detaysoftmobil.R;
import com.example.detaysoftmobil.ServiceHttPost;
import com.example.detaysoftmobil.ServiceUrl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BelirliAktiviteDetay extends Activity {
	
	String today_ = Login.today_;
	
	private ImageView kaydet;
	private ImageView duzenle;
	private ImageView sil;
	private EditText proName;
	private EditText faturaSaati;
	private EditText aktiviteSaati;
	private EditText projeYeri;
	private EditText tarih;
	private EditText aciklama;
	private EditText location;
	private ListView list_alert;
	
	String servis_cevabi;
	
	public String fname, fno, fcname, fcno, fwhour, fahour, fplace, faciklama, akti_tarih, customerNo, f_lok, locNo = "";
	
	String pro_name, loc, seqNo, proNo, date, place, fat_saat, akt_saat, aciklama_metni = null;

	String aktivite_kaydi_xml, silme_xml;
	
	
	private ProgressDialog dialog; 
	private ArrayList<aktivite> proje_listesi_yeni = new ArrayList<aktivite>();	
	private ArrayList<String> favoriler = new ArrayList<String>();	
	private ArrayList<String> favoriler_project_id = new ArrayList<String>();
	private ArrayList<String> favoriler_tarih = new ArrayList<String>();
	private ArrayList<String> favoriler_customerName= new ArrayList<String>();
	private ArrayList<String> favoriler_customerNo = new ArrayList<String>();
	private ArrayList<String> favoriler_whour = new ArrayList<String>();
	private ArrayList<String> favoriler_ahour = new ArrayList<String>();
	private ArrayList<String> favoriler_place = new ArrayList<String>();
	private ArrayList<String> favoriler_aciklama = new ArrayList<String>();
	private ArrayList<String> favoriler_custName = new ArrayList<String>();
	private ArrayList<String> favoriler_proName = new ArrayList<String>();	
	private ArrayList<String> favoriler_seqNo = new ArrayList<String>();
	
	private ArrayList<String> lokasyon_listesi = new ArrayList<String>();
 
	private ArrayList<String> lokasyonNo_listesi = new ArrayList<String>();
	
	final CharSequence[] items = {"Detaysoft", "Müþteri"};
	
	CharSequence[] items_lokasyonlar ; 
	
	ArrayList<aktivite> proje_listesi = new ArrayList<aktivite>();
	
	ArrayAdapter<String> adapter;	
	boolean control;
	
	aktivite temp = new aktivite();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aktivite_sayfasi);		
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		  
		Intent intent = getIntent();
		String k_kullanici = intent.getExtras().getString("key_kullanici");
		String k_parola = intent.getExtras().getString("key_parola");
		
		final String k_projectName = intent.getExtras().getString("key_projeAdi");
		String k_perNo = intent.getExtras().getString("key_perNo");
		final String k_date = intent.getExtras().getString("key_date");
		
		final String k_locNo = intent.getExtras().getString("key_locNo");
		
		final String k_cusNo = intent.getExtras().getString("key_cusNo");
		customerNo = k_cusNo;
		
		final ArrayList<String> k_nolar = intent.getStringArrayListExtra("key_nolar");
		
		for(int k=0; k<k_nolar.size(); k++){
			lokasyonNo_listesi.add(k_nolar.get(k));
		}
		
		final ArrayList<String> k_lokasyonlar = intent.getStringArrayListExtra("key_lokasyonlar");
		
		for(int k=0; k<k_lokasyonlar.size(); k++){
			lokasyon_listesi.add(k_lokasyonlar.get(k));
		}	
		
		items_lokasyonlar = k_lokasyonlar.toArray(new CharSequence[k_lokasyonlar.size()]);
		
		final String k_loc = intent.getExtras().getString("key_loc");		
		
		final String k_proNo = intent.getExtras().getString("key_proNo");
		final String k_projeYeri = intent.getExtras().getString("key_projeYeri");
		final String k_seqNo = intent.getExtras().getString("key_seqNo");
		String k_baslangicTarihi= intent.getExtras().getString("key_baslangicTarihi");
		final String k_faturaSaati = intent.getExtras().getString("key_faturaSaati");
		final String k_aktiviteSaati = intent.getExtras().getString("key_aktiviteSaati");
		final String k_aciklama = intent.getExtras().getString("key_aciklama");
		
		final String user = Aktivite_Detay.convertUsername(k_kullanici);
		
		kaydet =  (ImageView) findViewById(R.id.imageView2);
		duzenle = (ImageView) findViewById(R.id.imageView3);
		sil = (ImageView) findViewById(R.id.imageView1);
		proName = (EditText) findViewById(R.id.editText1);
		location = (EditText) findViewById(R.id.editText2);
		projeYeri = (EditText) findViewById(R.id.editText3);
		faturaSaati= (EditText) findViewById(R.id.editText5);
		aktiviteSaati= (EditText) findViewById(R.id.editText4);
		tarih = (EditText) findViewById(R.id.editText6);
		aciklama = (EditText) findViewById(R.id.editText7);
		
		proName.setInputType(InputType.TYPE_NULL);
		location.setInputType(InputType.TYPE_NULL);
		projeYeri.setInputType(InputType.TYPE_NULL);
		faturaSaati.setInputType(InputType.TYPE_NULL);
		aktiviteSaati.setInputType(InputType.TYPE_NULL);
		tarih.setInputType(InputType.TYPE_NULL);
		aciklama.setInputType(InputType.TYPE_NULL);		
		
		proName.setEnabled(false); 
		location.setEnabled(false);
		projeYeri.setEnabled(false);
		faturaSaati.setEnabled(false);
		aktiviteSaati.setEnabled(false);
		tarih.setEnabled(false);
		aciklama.setEnabled(false);		
		
	
		
		proName.setText(k_projectName);
		location.setText(k_loc);
		
		if(k_projeYeri.equals("D"))
			projeYeri.setText("Detaysoft");
		if(k_projeYeri.equals("M"))
			projeYeri.setText("Müþteri");	
		
		faturaSaati.setText(k_faturaSaati);     
		aktiviteSaati.setText(k_aktiviteSaati);     
		tarih.setText(k_baslangicTarihi);
		aciklama.setText(k_aciklama);	
		
		final AlertDialog.Builder builder_proYeri = new AlertDialog.Builder(this);
		builder_proYeri.setTitle("Proje Yeri");
		
		builder_proYeri.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int item) {
	        	if(item == 0){
	            	projeYeri.setText("Detaysoft");
	               	dialog.cancel();
	            	}
	            else if(item == 1){
	            	projeYeri.setText("Müþteri");
	            	dialog.cancel();
	            	}
	            }});			
		
		duzenle.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub						
				proName.setEnabled(true); 
				location.setEnabled(true);
				projeYeri.setEnabled(true);
				faturaSaati.setEnabled(true);
				aktiviteSaati.setEnabled(true);
				aciklama.setEnabled(true);				
			}
		});			
		   
		
		proName.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {			
				if (!control) {
					new StartService_favori2().execute();	
//					customerNo = fcno;
				    proName.setText(fname);
					control = !control;
				}
				else {
 					show_alert();
//					customerNo = k_cusNo;
				    proName.setText(fname);
				}				
			}			
		});	
		
		location.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				if(!lokasyon_listesi.get(0).equals(" "))
					show_locations();
				
			}
		});	
		

		projeYeri.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {				
				AlertDialog alert = builder_proYeri.create();
				alert.show();
				projeYeri.setText("");
			}
		});
	
		kaydet.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				pro_name = proName.getText().toString();
//				loc = location.getText().toString();
				
				if(projeYeri.getText().toString().equals("Detaysoft"))
					place ="D";
				else if(projeYeri.getText().toString().equals("Müþteri")){
					place ="M";
					if(faturaSaati.getText().toString().equals(""))
						Toast.makeText(getApplicationContext(), "Lütfen Fatura Saati Giriniz", Toast.LENGTH_LONG).show();
				}			
								
				loc = location.getText().toString();
//				place = projeYeri.getText().toString();
				fat_saat = faturaSaati.getText().toString();
				akt_saat = aktiviteSaati.getText().toString(); 
				aciklama_metni = aciklama.getText().toString();	
				
				customerNo = fcno;
				if(customerNo == null)
					customerNo = k_cusNo;
				 					
				// TODO Auto-generated method stub	
				if((projeYeri.getText().toString().equals("Müþteri") && !faturaSaati.getText().toString().equals("")) || (projeYeri.getText().toString().equals("Detaysoft") && !akt_saat.equals(""))){
				try {
					yeni_aktivite_kaydi(Login.user_no, k_date, k_seqNo, customerNo, k_proNo,  akt_saat, fat_saat, place, aciklama_metni, locNo, " ", " ");
            		Toast.makeText(getApplicationContext(), servis_cevabi, Toast.LENGTH_LONG).show(); 
             		if(servis_cevabi.equals("Baþarýlý"))
            			BelirliAktiviteDetay.this.finish();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}			
				}
			}
		});	
		
		sil.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				try {
					aktivite_sil(Login.user_no, k_date, k_seqNo, k_cusNo);
            		Toast.makeText(getApplicationContext(), servis_cevabi, Toast.LENGTH_LONG).show(); 
					BelirliAktiviteDetay.this.finish();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}			
			}
		});		
	}	
	
	
	private class StartService_favori2 extends AsyncTask<Void, Void, ArrayList<aktivite>>{	
		@Override
		protected ArrayList<aktivite> doInBackground(Void... arg0) {
			try {
				String xml =  YeniAktivite.create_favoriXML(Login.user_no, YeniAktivite.edit_tarih(today_));
				InputStream is = ServiceHttPost.callLoginControlService(ServiceUrl.favori_projeler, xml);
				proje_listesi_yeni =  YeniAktivite.parseActivityList(is);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); 
				}
			return proje_listesi_yeni;
			}
		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
	        dialog = new ProgressDialog(BelirliAktiviteDetay.this);
	        dialog.setMessage("Yükleniyor...");
	        dialog.show();
		}
		 
		
		protected void onPostExecute(ArrayList<aktivite> proje_listesi_yeni) {
			show_alert();   
			dialog.dismiss();	      			
	     }		
	}
	
	
	public void yeni_aktivite_kaydi(String per_no, String date_, String seqNo, String customer_no, String Project_No, String whour, String ahour, String Place, String aciklm, String loc_no, String cust_name, String pro_name ) throws SAXException{
		aktivite_kaydi_xml =  kaydet_XML( per_no,  date_, seqNo, customer_no,  Project_No,  whour,  ahour,  Place,  aciklm, loc_no, cust_name,  pro_name );
		try{
			InputStream is_yeni_kayit = ServiceHttPost.callLoginControlService(ServiceUrl.aktivite_kaydi, aktivite_kaydi_xml);	
			servis_cevabi = parse_cevap(is_yeni_kayit);	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	public void aktivite_sil(String per_no, String date_, String seqNo, String customer_no) throws SAXException{
		silme_xml =  sil_XML( per_no,  date_, seqNo, customer_no);
		try{
			InputStream silme_ = ServiceHttPost.callLoginControlService(ServiceUrl.aktivite_kaydi, silme_xml);	
     		servis_cevabi = parse_cevap(silme_);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void show_alert() {
        // TODO Auto-generated method stub
        final Dialog dia = new Dialog(this);
        dia.setContentView(R.layout.alert);
        dia.setTitle("PROJE SEÇÝNÝZ");
//      dia.setCancelable(true);
        
        for(int k = 0; k < proje_listesi_yeni.size(); k++){
        	favoriler.add(proje_listesi_yeni.get(k).projectName); 
        	favoriler_project_id.add(proje_listesi_yeni.get(k).proje_no);   
        	favoriler_tarih.add(proje_listesi_yeni.get(k).baslangic_tarihi);
        	favoriler_customerName.add(proje_listesi_yeni.get(k).customerName);
        	favoriler_customerNo.add(proje_listesi_yeni.get(k).kunnr);        
        	favoriler_whour.add(proje_listesi_yeni.get(k).work_hour);
        	favoriler_ahour.add(proje_listesi_yeni.get(k).akhour);
        	favoriler_place.add(proje_listesi_yeni.get(k).place);
        	favoriler_aciklama.add(aciklama_metni);
        	favoriler_seqNo.add(proje_listesi_yeni.get(k).seqno);
        }

        list_alert = (ListView) dia.findViewById(R.id.alert_list);
        list_alert.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, favoriler));
        
        list_alert.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
            	
            	fname = favoriler.get(pos);
            	fno = favoriler_project_id.get(pos);
            	akti_tarih = favoriler_tarih.get(pos);
                fcno   = favoriler_customerNo.get(pos);
                fcname = favoriler_customerName.get(pos);
                fwhour = favoriler_whour.get(pos);
                fahour = favoriler_ahour.get(pos);
                fplace = favoriler_place.get(pos);
                faciklama =  favoriler_aciklama.get(pos); 
                seqNo =  favoriler_seqNo.get(pos);
                proName.setText(fname);
                dia.dismiss();  
            }
        });
        dia.show();        
    }	
	
	
	public void show_locations() {
        // TODO Auto-generated method stub
        final Dialog dia = new Dialog(this);
        dia.setContentView(R.layout.alert);
        dia.setTitle("LOKASYON SEÇÝNÝZ");

        list_alert = (ListView) dia.findViewById(R.id.alert_list);
        list_alert.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, lokasyon_listesi));
        
        list_alert.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3){            	
                location.setText(lokasyon_listesi.get(pos));
                locNo = lokasyonNo_listesi.get(pos);
                dia.dismiss();  
            }
        });
        dia.show();
        }
	
	
	String parse_cevap(InputStream is){		
	       String temp = " " ;	        
	        try {          	                          
	            DocumentBuilderFactory dFactory=DocumentBuilderFactory.newInstance();
	            DocumentBuilder dBuilder=dFactory.newDocumentBuilder();
	           
	            Document document=dBuilder.parse(is);      		
	            document.getDocumentElement().normalize();                   
	                                
	            NodeList nodeList=document.getElementsByTagName("EMessage");
	            if(nodeList.getLength() > 0){
	              	Node node=nodeList.item(0);
	                Element elementMain=(Element) node;
	                temp = elementMain.getChildNodes().item(0).getNodeValue();
	            } else  if(nodeList.getLength() == 0)
	            	temp = "Kaydedilemedi!";         
	 
	        } catch (MalformedURLException e) {
	        	System.err.println(e);
	        }catch (IOException e) {
	        	System.err.println(e); 
	        } catch (ParserConfigurationException e) {
	        	System.err.println(e);
			} catch (SAXException e) {
				System.err.println(e);
			}
			
			return temp;
		}
	
	String kaydet_XML(String personel_id, String tarih, String seqNo, String customer_no, String proje_no,  String whour, String ahour, String place, String aciklm, String loc_no, String customer_name, String project_name){
		String kaydet_xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:sap-com:document:sap:soap:functions:mc-style\">"
		+"<soapenv:Header/>"
		   +"<soapenv:Body>"
		   +"<urn:_-dsl_-akF01605>"		      
		   +"<IActivity>"
		         +"<Pernr>"+personel_id+"</Pernr>"
		         +"<Begda>"+tarih+"</Begda>"
		         +"<Seqno>"+seqNo+"</Seqno>"
		         +"<Kunnr>"+customer_no+"</Kunnr>"
		         +"<Prjno>"+proje_no+"</Prjno>"
		         +"<Wrhour>"+whour+"</Wrhour>"
		         +"<Akhour>"+ahour+"</Akhour>"
		         +"<Place>"+place+"</Place>"
		         +"<Arfno></Arfno>"
		         +"<Txt01>"+aciklm+"</Txt01>"
		         +"<Txt02></Txt02>"
		         +"<Txt03></Txt03>"
		         +"<Txt04></Txt04>"
		         +"<Txt05></Txt05>"
		         +"<Status></Status>"
		         +"<Stat2></Stat2>"
		         +"<Stat3></Stat3>"
		         +"<Stat4></Stat4>"
		         +"<Stat5></Stat5>"
		         +"<Locno>"+loc_no+"</Locno>"
		         +"<CustomerName>"+customer_name+"</CustomerName>"
		         +"<ProjectName>"+project_name+"</ProjectName>"
		         +"<Inptp></Inptp>"
		         +"</IActivity>"		          
		         +"<IMode></IMode>"
		         +"</urn:_-dsl_-akF01605>"
		         +"</soapenv:Body>"
		+"</soapenv:Envelope>";
		return kaydet_xml;		
	}
	
	String sil_XML(String personel_id, String tarih, String seqNo, String customer_no){
		String sil_xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:sap-com:document:sap:soap:functions:mc-style\">"
		+"<soapenv:Header/>"
		   +"<soapenv:Body>"
		   +"<urn:_-dsl_-akF01605>"		      
		   +"<IActivity>"
		         +"<Pernr>"+personel_id+"</Pernr>"
		         +"<Begda>"+tarih+"</Begda>"
		         +"<Seqno>"+seqNo+"</Seqno>"
		         +"<Kunnr>"+customer_no+"</Kunnr>"
		         +"<Prjno></Prjno>"
		         +"<Wrhour></Wrhour>"
		         +"<Akhour></Akhour>"
		         +"<Place></Place>"
		         +"<Arfno></Arfno>"
		         +"<Txt01></Txt01>"
		         +"<Txt02></Txt02>"
		         +"<Txt03></Txt03>"
		         +"<Txt04></Txt04>"
		         +"<Txt05></Txt05>"
		         +"<Status></Status>"
		         +"<Stat2></Stat2>"
		         +"<Stat3></Stat3>"
		         +"<Stat4></Stat4>"
		         +"<Stat5></Stat5>"
		         +"<Locno></Locno>"
		         +"<CustomerName></CustomerName>"
		         +"<ProjectName></ProjectName>"
		         +"<Inptp></Inptp>"
		         +"</IActivity>"		          
		         +"<IMode>D</IMode>"
		         +"</urn:_-dsl_-akF01605>"
		         +"</soapenv:Body>"
		+"</soapenv:Envelope>";
		return sil_xml;		
	}

	public static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}	
}
