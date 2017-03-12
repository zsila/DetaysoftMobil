package com.example.aktivite;
import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXParseException;

import com.example.aktivite.aktivite;

public class ParseActivityList {
	
	public static String createXML(String personel_no, String baslangic_tarihi,String bitis_tarihi){
		
		String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:sap-com:document:sap:soap:functions:mc-style\">"
				+ "<soapenv:Header/>"
				+ "<soapenv:Body>"
				+ " <urn:_-dsl_-akF01603>"
				+ "<IDateHigh>"+bitis_tarihi+"</IDateHigh>"
				+ "<IDateLow>"+baslangic_tarihi+"</IDateLow>"
				+ "<IPersonnel>"+personel_no+"</IPersonnel>"
				+ "</urn:_-dsl_-akF01603>"
				+ "</soapenv:Body>" + "</soapenv:Envelope>";	
		
		return xml;
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
                                  
                NodeList nodeListId=elementMain.getElementsByTagName("Pernr");            	
                if(nodeListId.getLength() > 0){
                	Element elementId=(Element) nodeListId.item(0);                
                	temp.personel_no = elementId.getChildNodes().item(0).getNodeValue();              
                              
                	
                NodeList nodeListBaslangic=elementMain.getElementsByTagName("Begda");    
                if(nodeListBaslangic.getLength() > 0){
                	Element elementBaslangic=(Element) nodeListBaslangic.item(0);
                	temp.baslangic_tarihi = elementBaslangic.getChildNodes().item(0).getNodeValue();
                	//System.out.println(temp.baslangic_tarihi);
                }
                else
                	temp.baslangic_tarihi = " ";
                
                
                NodeList nodeListSeqNo=elementMain.getElementsByTagName("Seqno");
                if(nodeListSeqNo.getLength() > 0){
                	Element elementSeqNo=(Element) nodeListSeqNo.item(0);
                	temp.seqno = elementSeqNo.getChildNodes().item(0).getNodeValue();
                }
                else
                	temp.seqno = " ";
                
                
                NodeList nodeListKunnr=elementMain.getElementsByTagName("Kunnr");
                if(nodeListKunnr.getLength() > 0){
                	Element elementKunnr=(Element) nodeListKunnr.item(0);
                	if(elementKunnr.getChildNodes().getLength() > 0){
                		temp.kunnr = elementKunnr.getChildNodes().item(0).getNodeValue();
                	}
                	else 
                		temp.kunnr = "";        	  
                		  
                	} else               		 
                		temp.kunnr = "";          	     
                
                  
                
                NodeList nodeListPrjno=elementMain.getElementsByTagName("Prjno");
                if(nodeListPrjno.getLength() > 0){
                	  Element elementPrjno=(Element) nodeListPrjno.item(0);
                	  if(elementPrjno.getChildNodes().getLength() > 0)
                		  temp.proje_no = elementPrjno.getChildNodes().item(0).getNodeValue();
                	  else
                		  temp.proje_no = "";
                	  }
                  else
                	  temp.proje_no = "";     
                
                
              
                
                NodeList nodeListWrhour=elementMain.getElementsByTagName("Wrhour");
                if(nodeListWrhour.getLength() > 0){
                	Element elementWrhour=(Element) nodeListWrhour.item(0);
                	if(elementWrhour.getChildNodes().getLength() > 0)
                		temp.work_hour = elementWrhour.getChildNodes().item(0).getNodeValue();
                	else
                    	temp.work_hour = "";
                	}
                
                else
                	temp.work_hour = " ";           
                                          
            
            
            NodeList nodeListAkhour= elementMain.getElementsByTagName("Akhour");
            if(nodeListAkhour.getLength() > 0){
            	Element elementAkhour=(Element) nodeListAkhour.item(0);
            	if(elementAkhour.getChildNodes().getLength() > 0)
            		temp.akhour = elementAkhour.getChildNodes().item(0).getNodeValue();
            	else
                	temp.akhour = "";
            	}
            
            else
            	temp.akhour = " ";           
            
          
            
            
            NodeList nodeListPlace= elementMain.getElementsByTagName("Place");
            if(nodeListPlace.getLength() > 0){
            	Element elementPlace=(Element) nodeListPlace.item(0);
            	if(elementPlace.getChildNodes().getLength() > 0)
            		temp.place = elementPlace.getChildNodes().item(0).getNodeValue();
            	else
                	temp.place = "";
            	}
            
            else
            	temp.place = " ";      
            
            
            NodeList nodeListArfno= elementMain.getElementsByTagName("Arfno");
            if(nodeListArfno.getLength() > 0){
            	Element elementArfno=(Element) nodeListArfno.item(0);
            	if(elementArfno.getChildNodes().getLength() > 0)
            		temp.arfNo = elementArfno.getChildNodes().item(0).getNodeValue();
            	else
                	temp.arfNo = "";
            	}
            
            else
            	temp.arfNo = " ";
            
            
            NodeList nodeListTxt01= elementMain.getElementsByTagName("Txt01");
            if(nodeListTxt01.getLength() > 0){
            	Element elementTxt01=(Element) nodeListTxt01.item(0);
            	if(elementTxt01.getChildNodes().getLength() > 0)
            		temp.Txt01 = elementTxt01.getChildNodes().item(0).getNodeValue();
            	else
                	temp.Txt01 = "";
            	}
            
            else
            	temp.Txt01 = " ";
            
            
            NodeList nodeListTxt02= elementMain.getElementsByTagName("Txt02");
            if(nodeListTxt02.getLength() > 0){
            	Element elementTxt02=(Element) nodeListTxt02.item(0);
            	if(elementTxt02.getChildNodes().getLength() > 0)
            		temp.Txt02 = elementTxt02.getChildNodes().item(0).getNodeValue();
            	else
                	temp.Txt02 = "";
            	}
            
            else
            	temp.Txt02 = " ";
            
           
            
            NodeList nodeListTxt03= elementMain.getElementsByTagName("Txt03");
            if(nodeListTxt03.getLength() > 0){
            	Element elementTxt03=(Element) nodeListTxt03.item(0);
            	if(elementTxt03.getChildNodes().getLength() > 0)
            		temp.Txt03 = elementTxt03.getChildNodes().item(0).getNodeValue();
            	else
                	temp.Txt03 = "";
            	}
            
            else
            	temp.Txt03 = " ";
            
            
            
            
            NodeList nodeListTxt04= elementMain.getElementsByTagName("Txt04");
            if(nodeListTxt04.getLength() > 0){
            	Element elementTxt04=(Element) nodeListTxt04.item(0);
            	if(elementTxt04.getChildNodes().getLength() > 0)
            		temp.Txt04 = elementTxt04.getChildNodes().item(0).getNodeValue();
            	else
                	temp.Txt04 = "";
            	}
            
            else
            	temp.Txt04 = " ";            
            
            
            NodeList nodeListTxt05= elementMain.getElementsByTagName("Txt05");
            if(nodeListTxt05.getLength() > 0){
            	Element elementTxt05=(Element) nodeListTxt05.item(0);
            	if(elementTxt05.getChildNodes().getLength() > 0)
            		temp.Txt05 = elementTxt05.getChildNodes().item(0).getNodeValue();
            	else
                	temp.Txt05 = "";
            	}
            
            else
            	temp.Txt05 = " ";
                      
            
            NodeList nodeListStatus= elementMain.getElementsByTagName("Status");
            if(nodeListStatus.getLength() > 0){
            	Element elementStatus=(Element) nodeListStatus.item(0);
            	if(elementStatus.getChildNodes().getLength() > 0)
            		temp.Status = elementStatus.getChildNodes().item(0).getNodeValue();
            	else
                	temp.Status = "";
            	}
            
            else
            	temp.Status = " ";
            
            
            NodeList nodeListStat2= elementMain.getElementsByTagName("Stat2");
            if(nodeListStat2.getLength() > 0){
            	Element elementStat2=(Element) nodeListStat2.item(0);
            	if(elementStat2.getChildNodes().getLength() > 0)
            		temp.Stat2 = elementStat2.getChildNodes().item(0).getNodeValue();
            	else
                	temp.Stat2 = "";
            	}
            
            else
            	temp.Stat2 = " ";
            
            
            NodeList nodeListStat3= elementMain.getElementsByTagName("Stat3");
            if(nodeListStat3.getLength() > 0){
            	Element elementStat3=(Element) nodeListStat3.item(0);
            	if(elementStat3.getChildNodes().getLength() > 0)
            		temp.Stat3 = elementStat3.getChildNodes().item(0).getNodeValue();
            	else
                	temp.Stat3 = "";
            	}
            
            else
            	temp.Stat3 = " ";
            
            
            
            NodeList nodeListStat4= elementMain.getElementsByTagName("Stat4");
            if(nodeListStat4.getLength() > 0){
            	Element elementStat4=(Element) nodeListStat4.item(0);
            	if(elementStat4.getChildNodes().getLength() > 0)
            		temp.Stat4 = elementStat4.getChildNodes().item(0).getNodeValue();
            	else
                	temp.Stat4 = "";
            	}
            
            else
            	temp.Stat4 = " ";
              
            
            
            NodeList nodeListStat5= elementMain.getElementsByTagName("Stat5");
            if(nodeListStat5.getLength() > 0){
            	Element elementStat5=(Element) nodeListStat5.item(0);
            	if(elementStat5.getChildNodes().getLength() > 0)
            		temp.Stat5 = elementStat5.getChildNodes().item(0).getNodeValue();
            	else
                	temp.Stat5 = "";
            	}
                else
            	temp.Stat5 = " ";
            
            
            
            NodeList nodeListLocno= elementMain.getElementsByTagName("Locno");
            if(nodeListLocno.getLength() > 0){
            	Element elementLocno=(Element) nodeListLocno.item(0);
            	if(elementLocno.getChildNodes().getLength() > 0)
            		temp.locNo = elementLocno.getChildNodes().item(0).getNodeValue();
            	else
                	temp.locNo = "";
            	}
                else
            	temp.locNo = " ";
            
            
            NodeList nodeListCustomerName= elementMain.getElementsByTagName("CustomerName");
            if(nodeListCustomerName.getLength() > 0){
            	Element elementCustomerName=(Element) nodeListCustomerName.item(0);
            	if(elementCustomerName.getChildNodes().getLength() > 0)
            		temp.customerName = elementCustomerName.getChildNodes().item(0).getNodeValue();
            	else
                	temp.customerName = "";
            	}
                else
            	temp.customerName = " ";
            
            
            
            NodeList nodeListProjectName= elementMain.getElementsByTagName("ProjectName");
            if(nodeListProjectName.getLength() > 0){
            	Element elementProjectName=(Element) nodeListProjectName.item(0);
            	if(elementProjectName.getChildNodes().getLength() > 0)
            		temp.projectName = elementProjectName.getChildNodes().item(0).getNodeValue();
            	else
                	temp.projectName = "";
            	}  
                else
            	temp.projectName = " ";
            
            
            NodeList nodeListInptp= elementMain.getElementsByTagName("Inptp");
            if(nodeListInptp.getLength() > 0){
            	Element elementInptp=(Element) nodeListInptp.item(0);
            	if(elementInptp.getChildNodes().getLength() > 0)
            		temp.Inptp = elementInptp.getChildNodes().item(0).getNodeValue();
            	else
                	temp.Inptp = "";
            	}
                else
            	temp.Inptp = " ";
            
            
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
                
                
                NodeList nodeListADR02=element_loc.getElementsByTagName("ADR02");
                if(nodeListADR02.getLength() > 0){
                Element elementADR02=(Element) nodeListADR02.item(0);
                if(elementADR02.getChildNodes().getLength() > 0)
                	loc.adr02 = elementADR02.getChildNodes().item(0).getNodeValue();
                    else
                    	loc.adr02 = " ";
                }
                else
                	loc.adr02 = " ";
                
                NodeList nodeListMANDT=element_loc.getElementsByTagName("MANDT");            	
                if(nodeListMANDT.getLength() > 0){
                Element elementMANDT=(Element) nodeListMANDT.item(0);   
                if(elementMANDT.getChildNodes().getLength() > 0)
                	loc.mandt = elementMANDT.getChildNodes().item(0).getNodeValue();
                else
                	loc.mandt = " ";
                }
                else
                	loc.mandt = " ";
                
                NodeList nodeListKUNNR=element_loc.getElementsByTagName("KUNNR");            	
                if(nodeListKUNNR.getLength() > 0){
                Element elementKUNNR=(Element) nodeListKUNNR.item(0);   
                if(elementKUNNR.getChildNodes().getLength() > 0)
                	loc.kunnr = elementKUNNR.getChildNodes().item(0).getNodeValue();
                else
                	loc.kunnr = " ";
                }
                else
                	loc.kunnr = " ";
                
                
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
                
                NodeList nodeListMTYPE=element_loc.getElementsByTagName("MTYPE");            	
                if(nodeListMTYPE.getLength() > 0){
                Element elementMTYPE=(Element) nodeListMTYPE.item(0);   
                if(elementMTYPE.getChildNodes().getLength() > 0)
                	loc.mtype = elementMTYPE.getChildNodes().item(0).getNodeValue();
                else
                	loc.mtype = " ";
                }
                else
                	loc.mtype = " ";
                
                NodeList nodeListCUNAM=element_loc.getElementsByTagName("CUNAM");            	
                if(nodeListCUNAM.getLength() > 0){
                Element elementCUNAM=(Element) nodeListCUNAM.item(0);   
                if(elementCUNAM.getChildNodes().getLength() > 0)
                	loc.cunam = elementCUNAM.getChildNodes().item(0).getNodeValue();
                else
                	loc.cunam = " ";
                }
                else
                	loc.cunam = " ";
                
                
                NodeList nodeListCDATE=element_loc.getElementsByTagName("CDATE");            	
                if(nodeListCDATE.getLength() > 0){
                Element elementCDATE=(Element) nodeListCDATE.item(0);   
                if(elementCDATE.getChildNodes().getLength() > 0)
                	loc.cdate = elementCDATE.getChildNodes().item(0).getNodeValue();
                else
                	loc.cdate = " ";
                }
                else
                	loc.cdate = " ";
                
                
                NodeList nodeListCUZET=element_loc.getElementsByTagName("CUZET");            	
                if(nodeListCUZET.getLength() > 0){
                Element elementCUZET=(Element) nodeListCUZET.item(0);   
                if(elementCUZET.getChildNodes().getLength() > 0)
                	loc.cuzet = elementCUZET.getChildNodes().item(0).getNodeValue();
                else
                	loc.cuzet = " ";
                }
                else
                	loc.cuzet = " ";
                
                NodeList nodeListDISTN=element_loc.getElementsByTagName("DISTN");            	
                if(nodeListDISTN.getLength() > 0){
                Element elementDISTN=(Element) nodeListDISTN.item(0);   
                if(elementDISTN.getChildNodes().getLength() > 0)
                	loc.distn = elementDISTN.getChildNodes().item(0).getNodeValue();
                else
                	loc.distn = " ";
                }
                else
                	loc.distn = " ";
                
                
                temp.locations.add(loc);
            }
                
            }
            
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
            	
       } catch (Throwable t) {
        	t.printStackTrace();
       	}  
 
        return list;
    }

		
}
