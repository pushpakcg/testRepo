package com.code2java.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.code2java.model.UploadFile;
import com.code2java.service.HistSAPDataUploadService;
import com.google.gson.Gson;

@Controller
public class HistSAPDataUploadController {
	
	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(HistSAPDataUploadController.class);

	@Autowired
	private HistSAPDataUploadService histSAPDataService;

		@RequestMapping(value = "/UploadSapData")
	public String loadDatatable(Model model) {
		model.addAttribute("yearMap", getYears());
		model.addAttribute("reportNamesMap", getReportNames());
		return "UploadSapData";

	}


	// YEAR
	public Map<String, String> getYears() {
		Map<String, String> yearMap = new LinkedHashMap<String, String>();
		List<String[]> list = histSAPDataService.getYears();
		for (String[] s : list) {
			yearMap.put(s[0], s[0]);
		}
		return yearMap;
	}

	@RequestMapping(value = "loadSAPSSOData", method = RequestMethod.GET)
	public String getSSOId(HttpServletResponse response,HttpServletRequest request) throws JSONException, IOException{
		//String loginSSO=request.getParameter("loginSSO");
		HttpSession session = request.getSession();
		String ssoid=(null!=session && null!=session.getAttribute("uid"))?session.getAttribute("uid").toString():null;
		logger.info("ssoid: "+ssoid);
		if(null!=ssoid) {
			String flag=histSAPDataService.getSAPSSOId(ssoid);
			Map<String, String> ssoMap = new LinkedHashMap<String, String>();
			ssoMap.put("flag", flag);
			response.setHeader("Cache-Control", "no-cache"); 
	 		 response.setContentType("application/json");
	 		  String json = new Gson().toJson(ssoMap);
	 		  response.getWriter().write(json);
		}
		
 		   return null;
	}
	//@RequestMapping(value = "getSAPDataUploadReportNames", method = RequestMethod.GET)
	public Map<String, String> getReportNames() {
		List<String[]> list = histSAPDataService.getSAPDataUploadReportNames();
		Map<String, String> reportNamesMap = new LinkedHashMap<String, String>();
		for (String[] s : list) {
			reportNamesMap.put(s[0], s[1]);

		}
	//	JSONObject json = new JSONObject(reportNamesMap);
		return reportNamesMap;
	}
	
	@RequestMapping(value = "getSAPTableData", method = RequestMethod.POST)
	public ModelAndView getSAPTableData(HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		LocalDate date = LocalDate.now(); // Gets the current date
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dd=date.format(formatter);
		String dd1=date.format(formatter1);
		UploadFile fileupload = new UploadFile();
		fileupload.setReportName(request.getParameter("reportId"));
		List<String> tableData = histSAPDataService.getSAPTableData(fileupload);
		Map<String, String> tableDataMap = new LinkedHashMap<String, String>();		
		tableDataMap.put("reportId", tableData.get(0));
		tableDataMap.put("reportVersion", tableData.get(1));
		tableDataMap.put("system", tableData.get(2));
		tableDataMap.put("dbTable", tableData.get(3));
		tableDataMap.put("creationDate", tableData.get(4));
		tableDataMap.put("updateDate", tableData.get(5));
		tableDataMap.put("amendmentDate", tableData.get(6));
		tableDataMap.put("layout", tableData.get(7));
		tableDataMap.put("reportName", tableData.get(8));
		tableDataMap.put("year", tableData.get(9));
		tableDataMap.put("date", dd);
		tableDataMap.put("date1", dd1);
        response.setHeader("Cache-Control", "no-cache"); 
 		 response.setContentType("application/json");
 		  String json = new Gson().toJson(tableDataMap);
 		  response.getWriter().write(json);
 		   return null;
	}
	
	
	@RequestMapping(value = "saveSAPChildTableData", method = RequestMethod.POST)
	public ModelAndView saveSAPChildTableData(HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		String status = histSAPDataService.saveSAP1TableData(request);
		Map<String, String> tableDataMap = new LinkedHashMap<String, String>();
		tableDataMap.put("status", status);
        response.setHeader("Cache-Control", "no-cache"); 
 		 response.setContentType("application/json");
 		  String json = new Gson().toJson(tableDataMap);
 		  response.getWriter().write(json);
 		   return null;
	}



	
}