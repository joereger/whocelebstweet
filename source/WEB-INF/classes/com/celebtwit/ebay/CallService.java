package com.celebtwit.ebay;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

public class CallService 
{
	
	public List<Map<String, String>> executeAPICall(String strQuery, String strItemsPerPage, String strSite)
	{
		String strURL = null;
		List<Map<String, String>> arlResponse = new ArrayList<Map <String, String>>();

		Properties config = new Properties();
		try{
            InputStream stream = this.getClass().getResourceAsStream("config.properties"); 
            config.load(stream);
        } catch (FileNotFoundException e) {
            System.out.println("Properties file not found..." + e.getMessage());           
        }  catch (IOException e) {
        	System.out.println("I/O exception occured..." + e.getMessage());
		}
 
        if (config != null) {
            //Constructing the request URL for GET method
        	StringBuilder urlBuilder = new StringBuilder(config.getProperty("ENDPOINT_URL"));
	    	urlBuilder.append("appid=");
	    	urlBuilder.append(config.getProperty("APP_ID"));
	    	urlBuilder.append("&version=");
	    	urlBuilder.append(config.getProperty("VERSION"));
	    	urlBuilder.append("&callname=");
	    	urlBuilder.append(config.getProperty("CALL_NAME"));
	    	urlBuilder.append("&responseencoding=");	    
	    	urlBuilder.append(config.getProperty("RESPONSE_ENCODING"));
	    	urlBuilder.append("&siteid=");
	    	urlBuilder.append(strSite);	  
	    	urlBuilder.append("&MaxEntries=");
	    	urlBuilder.append(strItemsPerPage);
	    	urlBuilder.append("&ItemSort=BestMatchPlusPrice");
	    	urlBuilder.append("&SortOrder=Ascending");
	    	urlBuilder.append("&ItemType=AllItemTypes");
	    	urlBuilder.append("&QueryKeywords=");
	    	urlBuilder.append(strQuery);
	    	
	    	strURL = urlBuilder.toString();
        }
		System.out.println(strURL);
		try  {
			URL server = new URL(strURL);
			HttpURLConnection connection = (HttpURLConnection) (server.openConnection());
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			InputStream input = connection.getInputStream();
			
			Document xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
			
			if(xmlDocument == null)
			{
				System.out.println("No data found.");
				return null;
			}
			
			arlResponse = parseXMLResponse(xmlDocument);
		
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return arlResponse;
	}
	
	private static List<Map<String, String>> parseXMLResponse(Document xmlDocument)
	{
		ArrayList<Map<String, String>> arlResponse = new ArrayList<Map<String, String>>();
		
        if(xmlDocument == null)
        {
           System.out.println("No data found.");
        	return null;
        }
        
        NodeList errors = xmlDocument.getElementsByTagName("Errors");
        if (errors.getLength() > 0) 
        {
            Element errorElement = (Element) errors.item(0);
            //Print Error Code
            Node errCodeNode = errorElement.getElementsByTagName("ErrorCode").item(0).getChildNodes().item(0);
            System.out.println("No data found: " + errCodeNode.getNodeValue());
            //Print Error Message
            Node errShortNode = errorElement.getElementsByTagName("ShortMessage").item(0).getChildNodes().item(0);
            System.out.println(errShortNode.getNodeValue());
        }
        else 
        {        	
        	for (int i = 0; i < xmlDocument.getElementsByTagName("Item").getLength(); i++) {
				
        		NodeList itemsList = xmlDocument.getElementsByTagName("Item").item(i).getChildNodes();
        		Map<String, String> hmpItem = new HashMap<String, String>();
        		
	        	for (int j = 0; j < itemsList.getLength(); j++) 
	        	{
	                if(itemsList.item(j).getNodeType() == Node.ELEMENT_NODE)
	                {
	                	String nodeName = itemsList.item(j).getNodeName();
	                	String nodeValue = itemsList.item(j).getChildNodes().item(0).getNodeValue();
	                	
	                	if ("GalleryURL".equals(nodeName))
	                	{
	                		hmpItem.put("GalleryURL", nodeValue);
	                	}
	                	else if ("Title".equals(nodeName)) 
	                	{
	                		hmpItem.put("Title", nodeValue);
	                	}
	                	else if ("ViewItemURLForNaturalSearch".equals(nodeName))
	                	{
	                		hmpItem.put("ItemURL", nodeValue);
	                	}
	                	else if ("ConvertedCurrentPrice".equals(nodeName))
	                	{
	                		hmpItem.put("ConvertedCurrentPrice", nodeValue);
	                		hmpItem.put("Currency", itemsList.item(j).getAttributes().getNamedItem("currencyID").getTextContent());                 		
	                	}
	                	else if ("ShippingCostSummary".equals(nodeName))
	                	{	                		
	                		//Get the Shipping Service Cost
	                		String strShipping = "0.0";
	                		if ("ShippingServiceCost".equals(itemsList.item(j).getChildNodes().item(1).getNodeName()))
	                		{
	                			strShipping = itemsList.item(j).getChildNodes().item(1).getTextContent();
	                		}
	                		hmpItem.put("ShippingServiceCost", strShipping);
	                	}
	                	else if ("TimeLeft".equals(nodeName))
	                	{
	                		hmpItem.put("TimeLeft", nodeValue);
	                	}
	                	else if ("EndTime".equals(nodeName))
	                	{
	                		hmpItem.put("EndTime", nodeValue);
	                	}	                	
	                }
				}
                if (!hmpItem.isEmpty())
                {
                	//Calculate Total cost
                	Double totalCost = Double.parseDouble(hmpItem.get("ConvertedCurrentPrice")) 
                			+ Double.parseDouble(hmpItem.get("ShippingServiceCost"));
                	
                	DecimalFormat df = new DecimalFormat();
                	df.setMinimumFractionDigits(2);
                	df.setMaximumFractionDigits(2);
                	df.setMinimumIntegerDigits(1);
                	df.format(totalCost);
                	
                	hmpItem.put("Total", totalCost.toString());
                	
                	arlResponse.add(hmpItem);
                }
        	}
        }
		return arlResponse;
	}
	
}
