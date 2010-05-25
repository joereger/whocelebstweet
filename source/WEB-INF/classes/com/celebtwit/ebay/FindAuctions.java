package com.celebtwit.ebay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FindAuctions {


	public List<Map<String, String>> find(String query) {
		List<Map<String, String>> arlResponse = new ArrayList<Map<String, String>>();
		CallService callService = new CallService();
		
		
		String strQuery = query.replaceAll(" ", "%20");
		String strNoOfItems = "10";
		String strSiteID = "0";
		
		arlResponse = callService.executeAPICall(strQuery, strNoOfItems, strSiteID);

		
		return arlResponse;
	}
}
