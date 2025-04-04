package com.code2java.controllers;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.code2java.service.DashboardService;
import com.code2java.service.DashboardServiceImpl;
import com.google.gson.Gson;
@Controller
public class DashboardController {
	
	@Autowired
	private DashboardService dashboardservice;
	
	@RequestMapping(value = "/dashBoard")
	public String loadDatatable(Model model)
	{
		
		model.addAttribute("data", "India");
		//model.addAttribute("instanceMap", getInstances());
		//model.addAttribute("yearMap", getYears());
		//model.addAttribute("reportNamesMap", getLoadReportNames());
		return "Dashboard";
		
	}
	
	@RequestMapping(value = "getChartData" , method = RequestMethod.GET)
	public String getSSOId(HttpServletResponse response,HttpServletRequest request) throws JSONException, IOException{
		Map<String, Object> ssoMap = new LinkedHashMap<String, Object>();
		List<String> dashlist = new ArrayList<String>();
		List<String> dashlist2 = new ArrayList<String>();  
		List<String> dashlist3 = new ArrayList<String>();  
		List<String[]> dashvalues=dashboardservice.getDashboardDesAndValues();
		for (String[] s : dashvalues) {
			dashlist.add(s[1].trim());
////			dashlist.add("India");
////			dashlist.add("India");
////			dashlist.add("India");
////			dashlist.add("India");
////			dashlist.add("USA");
////			dashlist.add("USA");
		//	long number = Math.round(s[0].trim());
		    DecimalFormat df = new DecimalFormat("0.00");
			double number=Double.parseDouble(s[0].trim());
			String number2 = "";
			if(number >= 1000000000)
			{
				number2=df.format(number/1000000000)+"B";
			}
			else if(number >= 1000000)
			{
				number2=df.format(number/1000000)+"M";
			}
			else if(number >= 1000)
			{
				number2=df.format(number/1000)+"K";
			}
			else
			{
				number2=String.valueOf((int)(number));
			}
			dashlist2.add(s[0].trim());
			dashlist3.add(number2);
////			dashlist2.add("55000");
////			dashlist2.add("55000");
////			dashlist2.add("60000");
////			dashlist2.add("65000");
////			dashlist2.add("70000");
////			dashlist2.add("75000");
			//Collections.sort(dashlist);  
			//Collections.sort(dashlist2);
			ssoMap.put("c",dashlist);
			ssoMap.put("p",dashlist2);
			ssoMap.put("B",dashlist3);
		}
	//	System.out.println(ssoMap);
	//	System.out.println(dashlist3);
//		List<String> list1 = new ArrayList<String>();
//		List<String> list2 = new ArrayList<String>();
//		list1.add("Italy");
//		list1.add("France");
//		list1.add("Spain");
//		list1.add("USA");
//		list1.add("Argentina");
//		list1.add("India");
//		list1.add("ABC");
//		list1.add("BCD");
//		list1.add("CDE");
//		list1.add("DEF");
//		list1.add("EFG");
//		list1.add("FGH");
//		list1.add("ABC");
//		list1.add("BCD");
//		list1.add("CDE");
//		list1.add("DEF");
//		list1.add("EFG");
//		list1.add("FGH");
//		list1.add("Italy");
//		list1.add("France");
//		list1.add("Spain");
//		list1.add("USA");
//		list1.add("Argentina");
//		list1.add("India");
//		list1.add("ABC");
//		list1.add("BCD");
//		list1.add("CDE");
//		list1.add("DEF");
//		list1.add("EFG");
//		list1.add("FGH");
//		list1.add("ABC");
//		list1.add("BCD");
//		list1.add("CDE");
//		list1.add("DEF");
//		list1.add("EFG");
//		list1.add("FGH");
//		ssoMap.put("c",list1);
//		
//		System.out.println(ssoMap);;
//		
//		list2.add("55");
//		list2.add("49");
//		list2.add("35");
//		list2.add("25");
//		list2.add("20");
//		list2.add("50");
//		list2.add("65");
//		list2.add("70");
//		list2.add("75");
//		list2.add("80");
//		list2.add("85");
//		list2.add("90");
//		list2.add("95");
//		list2.add("100");
//		list2.add("105");
//		list2.add("110");
//		list2.add("115");
//		list2.add("120");
//		list2.add("125");
//		list2.add("130");
//		list2.add("135");
//		list2.add("140");
//		list2.add("145");
//		list2.add("150");
//		list2.add("155");
//		list2.add("160");
//		list2.add("165");
//		list2.add("170");
//		list2.add("175");
//		list2.add("180");
//		list2.add("185");
//		list2.add("190");
//		list2.add("195");
//		list2.add("200");
//		list2.add("205");
//		list2.add("210");
//		ssoMap.put("p",list2);
//		System.out.println(ssoMap);
		
		response.setHeader("Cache-Control", "no-cache"); 
 		 response.setContentType("application/json");
 		  String json = new Gson().toJson(ssoMap);
 		  response.getWriter().write(json);
 		   return null;
	}
	

}
