package com.example.detaysoftmobil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.example.aktivite.BelirliAktiviteDetay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;  
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Login extends Activity{
	
	static Calendar calendar = Calendar.getInstance();
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static String today_ = dateFormat.format(calendar.getTime());
	
	public static String user;
	public static String password;
	public static String user_no;
	
	private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
	
    String user_name, psswrd;
	
	String msg, msg2, servis_cevabi;
	String control_xml;
	
	public static String CLASS_NAME = "Login";
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_ekran);
		
		final ImageView login;
		final EditText kullanici_adi;
		final EditText parola;
		final CheckBox remember;	
		
		login = (ImageView) findViewById(R.id.imageView1);
		kullanici_adi = (EditText) findViewById(R.id.editText1);
		parola = (EditText) findViewById(R.id.editText2);
		remember = (CheckBox) findViewById(R.id.checkBox1);

		kullanici_adi.setInputType(InputType.TYPE_NULL);
//		parola.setInputType(InputType.TYPE_NULL);
		
		
		//beni hatýrla kýsmý
		try {
			loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
	        loginPrefsEditor = loginPreferences.edit();
	        
	        saveLogin = loginPreferences.getBoolean("saveLogin", false);
	        if (saveLogin == true) {
	            kullanici_adi.setText(loginPreferences.getString("username", ""));
	            parola.setText(loginPreferences.getString("password", ""));
	            remember.setChecked(true);
	        }
		} catch(Exception e) {
			Log.e(CLASS_NAME, "onCreate-loginPreferences");
		}
		

        parola.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				try {
					parola.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD); //also available from designer
					parola.setTransformationMethod(new PasswordTransformationMethod());
			  } catch(Exception e) {
					Log.e(CLASS_NAME, "onCreate-parola.setOnClickListener");
			 }
			}			
		});			
		
		login.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(login.getWindowToken(), 0);
				
				user = kullanici_adi.getText().toString();
				password = parola.getText().toString();
				
				InputStream is_login;				
				control_xml = create_loginXML(user, password);
				try {
					is_login = ServiceHttPost.callLoginControlService(ServiceUrl.login, control_xml);
					servis_cevabi = parse_cevap(is_login);
		    		Toast.makeText(getApplicationContext(), servis_cevabi, Toast.LENGTH_LONG).show(); 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}									
 			
				user_no = convertUsername(user);				
				
				if(servis_cevabi.equals("Baþarýlý")){
					Intent i = new Intent(Login.this, MainActivity.class);
					i.putExtra("key_kullanici", user);
					i.putExtra("key_parola", password);
					startActivity(i);		
				}
			}			
		});			
		
		remember.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	            imm.hideSoftInputFromWindow(kullanici_adi.getWindowToken(), 0);
	            user_name = kullanici_adi.getText().toString();
	            psswrd = parola.getText().toString();
				
	            if (remember.isChecked()) {
	                loginPrefsEditor.putBoolean("saveLogin", true);
	                loginPrefsEditor.putString("username", user);
	                loginPrefsEditor.putString("password", password);
	                loginPrefsEditor.commit();
	            } else {
	                loginPrefsEditor.clear();
	                loginPrefsEditor.commit();
	            }
			}
		});				
	}  
	
	
	 String create_loginXML(String username, String password) {
			// TODO Auto-generated method stub
			 String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:sap-com:document:sap:soap:functions:mc-style\">"
					 +"<soapenv:Header/>"
					 	+"<soapenv:Body><urn:_-dsl_-akF01604>"
					 		+"<IPassword>"+password+"</IPassword>"
					 		+"<IUser>"+username+"</IUser>"
					 	+"</urn:_-dsl_-akF01604>"
					 +"</soapenv:Body></soapenv:Envelope>";
			 return xml;
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
//		            	temp = " hatali geldi ";
		              	Node node=nodeList.item(0);
		                Element elementMain=(Element) node;
		                temp = elementMain.getChildNodes().item(0).getNodeValue();
		            } else  if(nodeList.getLength() == 0)
		            	temp = "Proje Adý Boþ Geçilemez";         
		 
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
		
		
	public static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
		

}
