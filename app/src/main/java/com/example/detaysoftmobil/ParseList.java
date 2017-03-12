package com.example.detaysoftmobil;

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

public class ParseList {
	
	public static String createXML(){
		String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:sap-com:document:sap:soap:functions:mc-style\">" 
				+"<soapenv:Header/>"
				+"<soapenv:Body>"
				+"<urn:_-dsl_-akF01608/>"
				+"</soapenv:Body>"
				+"</soapenv:Envelope>";
		
		return xml;
	}
	
	
	public static ArrayList<personel> parseList(InputStream is) {    	
        ArrayList<personel> list=new ArrayList<personel>();
        
        try {          
                            
            DocumentBuilderFactory dFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder=dFactory.newDocumentBuilder();
           
            Document document=dBuilder.parse(is);      		
            document.getDocumentElement().normalize();                   
                                
            NodeList nodeListPersonel=document.getElementsByTagName("item");
            for (int i = 0; i < nodeListPersonel.getLength(); i++) {
            	
            	personel temp = new personel();
                  
            	Node node=nodeListPersonel.item(i);
                Element elementMain=(Element) node;
                               
                NodeList nodeListId=elementMain.getElementsByTagName("Personel");            	
                if(nodeListId != null){
                	Element elementId=(Element) nodeListId.item(0);                
                	temp.id = elementId.getChildNodes().item(0).getNodeValue();
                	System.out.println(temp.id);
                }
                else
                	temp.id = " ";
                	
                
                NodeList nodeListAdi=elementMain.getElementsByTagName("Adi");    
                if(nodeListId != null){
                	Element elementAdi=(Element) nodeListAdi.item(0);
                	temp.adi = elementAdi.getChildNodes().item(0).getNodeValue();
                	System.out.println(temp.adi);
                }
                else
                	temp.adi = " ";
                
                
                NodeList nodeListSoyadi=elementMain.getElementsByTagName("Soyadi");
                if(nodeListSoyadi != null){
                	Element elementSoyadi=(Element) nodeListSoyadi.item(0);
                	temp.soyadi = elementSoyadi.getChildNodes().item(0).getNodeValue();
                }
                else
                	temp.soyadi = " ";
                
                             
                
                  NodeList nodeListKisakod=elementMain.getElementsByTagName("Kisakod");                  
                  if(nodeListKisakod.getLength() > 0){
                	  Element elementKisakod=(Element) nodeListKisakod.item(0);
                	  if(elementKisakod.getChildNodes().getLength() > 0)
                		  temp.kisakod = elementKisakod.getChildNodes().item(0).getNodeValue();
                	  	
                	  else 
                		  temp.kisakod = "";
                	  }
                  else
                	  temp.kisakod = "";     
                
                  
                
                  NodeList nodeListEmail=elementMain.getElementsByTagName("Email");
                  if(nodeListEmail.getLength() > 0){
                	  Element elementEmail=(Element) nodeListEmail.item(0);
                	  if(elementEmail.getChildNodes().getLength() > 0)
                		  temp.email = elementEmail.getChildNodes().item(0).getNodeValue();
                	  else
                		  temp.email = "";
                	  }
                  else
                	  temp.email = "";     
                
                
              
                
                NodeList nodeListTelsirket=elementMain.getElementsByTagName("TelSirket");
                if(nodeListTelsirket.getLength() > 0){
                	Element elementTelsirket=(Element) nodeListTelsirket.item(0);
                	if(elementTelsirket.getChildNodes().getLength() > 0)
                		temp.telSirket = elementTelsirket.getChildNodes().item(0).getNodeValue();
                	else
                    	temp.telSirket = "";
                	}
                
                else
                	temp.telSirket = " ";           
                
                list.add(temp);              
            
                }            
                   
 
        } catch (MalformedURLException e) {
        	System.err.println(e);
        }catch (IOException e) {
        	System.err.println(e); 
        } catch (ParserConfigurationException e) {
        	System.err.println(e);
		} catch (SAXException e) {
			System.err.println(e);
		}
 
        return list;
    }
	
}
