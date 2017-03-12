package com.example.aktivite;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.DatePicker.OnDateChangedListener;

public class YeniAktivite extends Activity{	
	String today_ = Login.today_;
	
	String aktivite_kaydi_xml;
	String servis_cevabi = "";
	String yeni_tarih = "";	
	
	private ImageView kaydet;	
	private EditText proName;
	private EditText lokasyon;
	private EditText faturaSaati;
	private EditText aktiviteSaati;
	private EditText projeYeri;
	private EditText tarih;
	private EditText aciklama;	
	private DatePicker date_picker;	
	private ListView list_alert;
	
	final CharSequence[] items = {"Detaysoft", "Musteri"};
	public String fname, fno, fcname, fcno, fwhour, fahour, fplace, faciklama, seqNo, akti_tarih, locNo = "";
	
	aktivite temp = new aktivite();
	
	
	private String projectName, fatura_saati, aktivite_saati, proje_yeri, aktivite_tarih, aciklama_metni, customerNo;
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
	private ArrayList<String> favoriler_seqNo = new ArrayList<String>();
	
	private ArrayList<String> favori_lokasyonlar = new ArrayList<String>();
	private ArrayList<String> favori_lokasyonNolar = new ArrayList<String>();

	private ArrayList<ArrayList<String>> favoriler_lokasyonlar = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> favoriler_lokasyonNolar = new ArrayList<ArrayList<String>>();
	
	ArrayList<aktivite> proje_listesi = new ArrayList<aktivite>();
	
	ArrayAdapter<String> adapter;	
	boolean control;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yeni_aktivite);		
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		date_picker = (DatePicker) findViewById(R.id.datePicker1);		
		kaydet = (ImageView) findViewById(R.id.imageView1);		
		proName = (EditText) findViewById(R.id.editText1);
		lokasyon = (EditText) findViewById(R.id.editText2);
		projeYeri = (EditText) findViewById(R.id.editText3);
		aktiviteSaati= (EditText) findViewById(R.id.editText4);
		faturaSaati= (EditText) findViewById(R.id.editText5);
		tarih = (EditText) findViewById(R.id.editText6);
		aciklama = (EditText) findViewById(R.id.editText7);
				 
		proName.setInputType(InputType.TYPE_NULL);
		lokasyon.setInputType(InputType.TYPE_NULL);
		projeYeri.setInputType(InputType.TYPE_NULL);
		faturaSaati.setInputType(InputType.TYPE_NULL);
		aktiviteSaati.setInputType(InputType.TYPE_NULL);
		tarih.setInputType(InputType.TYPE_NULL);
		aciklama.setInputType(InputType.TYPE_NULL);
		
		aktiviteSaati.setText("8");
		faturaSaati.setText("0");
	 		
		final AlertDialog.Builder builder_proYeri = new AlertDialog.Builder(this);
		builder_proYeri.setTitle("Proje Yeri");
		
		builder_proYeri.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int item) {
	        	if(item == 0){
	            	projeYeri.setText("Detaysoft");
	               	dialog.cancel();
	            	}
	            else if(item == 1){
	            	projeYeri.setText("Musteri");
	            	dialog.cancel();
	            	}
	            }});
			
		projeYeri.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				date_picker.setVisibility(View.INVISIBLE);
				AlertDialog alert = builder_proYeri.create();
				alert.show();
				projeYeri.setText("");
			}
		});  
				
		proName.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {	
			    projeYeri.setText("Musteri");
				date_picker.setVisibility(View.INVISIBLE);
				if (!control) {
					new StartService_favori().execute();					 
				    proName.setText(fname);
					control = !control;
				}
				else
 					show_alert();
				    proName.setText(fname);
				    
			}						
		});		
		
		
		tarih.setText(today_);
		tarih.setOnClickListener(new View.OnClickListener() {
			@Override
	        public void onClick(View v) {
				date_picker.setVisibility(1);
	        	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	    		imm.hideSoftInputFromWindow(tarih.getWindowToken(), 0);
	    		
	    		date_picker.init(2012, 0, 1, new OnDateChangedListener() {
	    			@Override
	    			public void onDateChanged(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
	    				if(date_picker.getDayOfMonth() < 10 &&  date_picker.getDayOfMonth() > 0)
	    					tarih.setText("0"+date_picker.getDayOfMonth()+"/"+(date_picker.getMonth()+1)+"/"+date_picker.getYear());
	    				if(date_picker.getMonth() < 9 &&  date_picker.getMonth() > -1)
	    					tarih.setText(date_picker.getDayOfMonth()+"/0"+(date_picker.getMonth()+1)+"/"+date_picker.getYear());
	    			}
	    			});	    		
			}		
	    });
				
		lokasyon.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				if(favori_lokasyonlar.size() > 0)
					show_locations();  
				
				date_picker.setVisibility(View.INVISIBLE);
			}
		});
		
		aciklama.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				date_picker.setVisibility(View.INVISIBLE);
			}
		});		
		
		faturaSaati.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				date_picker.setVisibility(View.INVISIBLE);
			}
		});	
		
		aktiviteSaati.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				date_picker.setVisibility(View.INVISIBLE);
			}
		});	
		    
			
		kaydet.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				projectName = proName.getText().toString();
				fatura_saati = faturaSaati.getText().toString();
				aktivite_saati = aktiviteSaati.getText().toString();
				
				if(projeYeri.getText().toString().equals("Detaysoft")){
					proje_yeri ="D";				
				}
				
				else if(projeYeri.getText().toString().equals("Musteri")){
					proje_yeri ="M";
					if(fatura_saati.equals(""))
						Toast.makeText(getApplicationContext(), "L�tfen Fatura Saati Giriniz", Toast.LENGTH_LONG).show();
				}
				
									
				aktivite_tarih  = tarih.getText().toString();				  
				aciklama_metni = aciklama.getText().toString();	
				customerNo = fcno;						
								
				yeni_tarih = tarih_duzenle(aktivite_tarih);						
				if((projeYeri.getText().toString().equals("Musteri") && !fatura_saati.equals("")) || (projeYeri.getText().toString().equals("Detaysoft") && !aktivite_saati.equals(""))){
				try {
					yeni_aktivite_kaydi(Login.user_no, yeni_tarih, customerNo, fno,  aktivite_saati, fatura_saati, proje_yeri, aciklama_metni, locNo, " ", " ");
					if(servis_cevabi.equals("Basarili"))YeniAktivite.this.finish();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 				Toast.makeText(getApplicationContext(), servis_cevabi, Toast.LENGTH_LONG).show();
				}
			}
			});		
		
		
		
		}
   
	
	private class StartService_favori extends AsyncTask<Void, Void, ArrayList<aktivite>>{	
		@Override
		protected ArrayList<aktivite> doInBackground(Void... arg0) {
			try {
		
			String xml =  create_favoriXML(Login.user_no, edit_tarih(today_));
			InputStream is = ServiceHttPost.callLoginControlService(ServiceUrl.favori_projeler, xml);
			proje_listesi_yeni =  parseActivityList(is);	
			
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
	        dialog = new ProgressDialog(YeniAktivite.this);
	        dialog.setMessage("Yukleniyor...");
	        dialog.show();
		}
		 
		
		protected void onPostExecute(ArrayList<aktivite> proje_listesi_yeni) {
			show_alert();   
			dialog.dismiss();	      			
	     }		
	}
	
	
	public void yeni_aktivite_kaydi(String per_no, String date_,  String customer_no, String Project_No, String whour, String ahour, String Place, String aciklm, String loc_no, String cust_name, String pro_name ) throws SAXException{
		aktivite_kaydi_xml =  kaydet_XML( per_no,  date_,  customer_no,  Project_No,  whour,  ahour,  Place,  aciklm, loc_no,  cust_name,  pro_name );
		try{
			InputStream is_yeni_kayit = ServiceHttPost.callLoginControlService(ServiceUrl.aktivite_kaydi, aktivite_kaydi_xml);	
			servis_cevabi = parse_cevap(is_yeni_kayit);
		} catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static String tarih_duzenle(String ex_date){
		String ed_date = "*";
		
		String[] test_ = ex_date.split("/");
		if(Integer.parseInt(test_[1]) > 0 && Integer.parseInt(test_[1]) < 10 )
			ed_date = test_[2]+"-"+test_[1]+"-"+test_[0];	
		if(Integer.parseInt(test_[0]) > 0 && Integer.parseInt(test_[0]) < 10 )
			ed_date = test_[2]+"-"+test_[1]+"-"+test_[0];	
		
		return ed_date;
	}
	
	public static String edit_tarih(String ex_date){
		String ed_date = "*";
		
		String[] test_ = ex_date.split("/");
		if(Integer.parseInt(test_[1]) > 0 && Integer.parseInt(test_[1]) < 10 )
			ed_date = test_[2]+test_[1]+test_[0];	
		if(Integer.parseInt(test_[0]) > 0 && Integer.parseInt(test_[0]) < 10 )
			ed_date = test_[2]+test_[1]+test_[0];	
		
		return ed_date;
	}
	
	
	String parse_cevap(InputStream is){
		
       String temp = "*" ;
        
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
	
	
	
	public void show_alert() {
        // TODO Auto-generated method stub
        final Dialog dia = new Dialog(this);
        dia.setContentView(R.layout.alert);
        dia.setTitle("PROJE SECINIZ");
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
                
                for(int k=0; k < proje_listesi_yeni.get(pos).location_names.size(); k++){
                	favori_lokasyonlar.add(proje_listesi_yeni.get(pos).location_names.get(k));      
                	favori_lokasyonNolar.add(proje_listesi_yeni.get(pos).location_nos.get(k)); 
                }
                
                if(favori_lokasyonlar.size() > 0)
			    	lokasyon.setText(favori_lokasyonlar.get(0));
                
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
        dia.setTitle("LOKASYON SECINIZ");
//      dia.setCancelable(true);   
        
        
        list_alert = (ListView) dia.findViewById(R.id.alert_list);
        list_alert.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, favori_lokasyonlar));
        
        list_alert.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                lokasyon.setText(favori_lokasyonlar.get(pos));
                locNo = favori_lokasyonNolar.get(pos);
                dia.dismiss();  
            }
        });
        dia.show();        
    }	
	
	
	
	
	public static String create_favoriXML(String personel_id, String tarih){		
		String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:sap-com:document:sap:soap:functions:mc-style\">"
	    +"<soapenv:Header/>"
		   +"<soapenv:Body>"
				+"<urn:_-dsl_-akF01601>"
					+"<IDate>"+tarih+"</IDate>"
					+"<IPersonnel>"+personel_id+"</IPersonnel>"
				+"</urn:_-dsl_-akF01601>"
		   +"</soapenv:Body>"
		+"</soapenv:Envelope>";
		return xml;
	}
	
	String kaydet_XML(String personel_id, String tarih, String customer_no, String proje_no,  String whour, String ahour, String place, String aciklm,  String loc_no, String customer_name, String project_name){
		String kaydet_xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:sap-com:document:sap:soap:functions:mc-style\">"
		+"<soapenv:Header/>"
		   +"<soapenv:Body>"
		   +"<urn:_-dsl_-akF01605>"		      
		   +"<IActivity>"
		         +"<Pernr>"+personel_id+"</Pernr>"
		         +"<Begda>"+tarih+"</Begda>"
		         +"<Seqno></Seqno>"
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
	
	
	public static ArrayList<aktivite> parseActivityList(InputStream is) {    	
	    ArrayList<aktivite> list=new ArrayList<aktivite>();       
	    
	    try {                         
	        DocumentBuilderFactory dFactory=DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder=dFactory.newDocumentBuilder();
	       
	        Document document=dBuilder.parse(is);      		
	        document.getDocumentElement().normalize();                   
	                            
	        NodeList nodeListaktivite=document.getElementsByTagName("item");
	        for (int i = 0; i < nodeListaktivite.getLength(); i++) {
	        	
	        	aktivite temp = new aktivite();
	              
	        	Node node=nodeListaktivite.item(i);
	            Element elementMain=(Element) node;
	                              
	            NodeList nodeListId=elementMain.getElementsByTagName("ProjectName");            	
	            if(nodeListId.getLength() > 0){            					
	            	Element elementId=(Element) nodeListId.item(0);                
	            	temp.projectName = elementId.getChildNodes().item(0).getNodeValue();    
	            }
	            else
	            	temp.projectName = " ";    
	            
	            if(!temp.projectName.equals(" ")){
	            
	        
	        NodeList nodeListNo=elementMain.getElementsByTagName("Project");            	
            if(nodeListNo.getLength() > 0){            	
			
            	Element elementId=(Element) nodeListNo.item(0);                
            	temp.proje_no = elementId.getChildNodes().item(0).getNodeValue();    
            }
            else
            	temp.proje_no = " ";    
            
            
            NodeList nodeListCustName=elementMain.getElementsByTagName("CustomerName");            	
            if(nodeListNo.getLength() > 0){            	
			
            	Element elementCustName=(Element) nodeListCustName.item(0);                
            	temp.customerName = elementCustName.getChildNodes().item(0).getNodeValue();    
            }
            else
            	temp.customerName = " ";
            
            
            NodeList nodeListCustomer=elementMain.getElementsByTagName("Customer");            	
            if(nodeListNo.getLength() > 0){            	
			
            	Element elementCustomer=(Element) nodeListCustomer.item(0);                
            	temp.kunnr = elementCustomer.getChildNodes().item(0).getNodeValue();    
            }
            else
            	temp.kunnr = " ";
            
            
            NodeList nodeListLoc_= elementMain.getElementsByTagName("Locations");
            if(nodeListLoc_.getLength() > 0){            
            NodeList nodeLocation = elementMain.getElementsByTagName("item");
            for (int k = 1; k < nodeLocation.getLength(); k++) {
            	location loc = new location();
            	Node node_loc=nodeLocation.item(k);
                Element element_loc=(Element) node_loc;
                NodeList nodeListADR01=element_loc.getElementsByTagName("ADR01");
                if(nodeListADR01.getLength() > 0){
                	Element elementADR01=(Element) nodeListADR01.item(0);
                	if(elementADR01.getChildNodes().getLength() > 0){
                		loc.adr01 = elementADR01.getChildNodes().item(0).getNodeValue();
                		temp.location_names.add(elementADR01.getChildNodes().item(0).getNodeValue());
                	}
                	else {
                		loc.adr01 = " ";
                		temp.location_names.add(" ");
                	}
                	
                }
                else{
                	loc.adr01 = " ";
                	temp.location_names.add(" ");
            }
                
                NodeList nodeListLOCNO=element_loc.getElementsByTagName("LOCNO");            	
                if(nodeListLOCNO.getLength() > 0){
                Element elementLOCNO=(Element) nodeListLOCNO.item(0);   
                if(elementLOCNO.getChildNodes().getLength() > 0){
                	loc.locno = elementLOCNO.getChildNodes().item(0).getNodeValue();
                	temp.location_nos.add(elementLOCNO.getChildNodes().item(0).getNodeValue());
                }
                else{
                	loc.locno = " ";
                	temp.location_nos.add(" ");
                }
                }
                else {
                	loc.locno = " ";
                	temp.location_nos.add(" ");
            }
                
                
                
                
                
                
                
                
            }
            
            }
            
            if(!temp.proje_no.equals(" ") && !temp.customerName.equals(" ") && !temp.kunnr.equals(" "))
        	  list.add(temp);
            }
	            
            }
	        
	        } catch (SAXParseException err) {
	        	System.err.println("\n** Parsing error"
	        	+ ", line " + err.getLineNumber()
	        	+ ", uri " + err.getSystemId());
	        	System.err.println("   " + err.getMessage());
	        	Exception	x = err;
	            if (err.getException () != null)
	                x = err.getException ();
	            	x.printStackTrace ();
	            	
	       } catch (Exception t) {
	    	   Log.e("*************", t.getMessage());
//	        	t.printStackTrace();
	       	}  
	 
	        return list;
	        } 
	
	
	public static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
	}
