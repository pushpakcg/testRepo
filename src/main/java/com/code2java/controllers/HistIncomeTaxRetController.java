package com.code2java.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.code2java.service.DTServiceImpl;
import com.code2java.service.HistIncomeTaxRetService;
import com.code2java.service.HistIncomeTaxService;
import com.code2java.service.HistIncomeTaxServiceImpl;
import com.code2java.service.AssetRetirementServiceImpl;
import com.faweb.domain.saml.Response;
import com.google.gson.Gson;


@Controller
public class HistIncomeTaxRetController {
	
	@Autowired
	private HistIncomeTaxRetService histRetService;
	
	
	
	
	
	/**
	 * @author code2java
	 * This method will be called when user clicks on link showed on welcome.jsp page
	 * The request is mapped using @RequestMapping annotation. 
	 * @return
	 */
	@RequestMapping(value = "/HistoricalTaxRet")
	public String loadDatatable(Model model)
	{
		
		model.addAttribute("companyGroupsMap", getCompanyGroups());
		model.addAttribute("instanceMap", getInstances());
		model.addAttribute("yearMap", getYears());
		//model.addAttribute("reportNamesMap", getLoadReportNames());
		return "HistoricalTaxRet";
		
	}
	
	
	
	/**
	 * This method will be called from AJAX callback of Data Tables 
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loadServerSideHistRetData")
	public String loadServerSideHistRetData(HttpServletResponse response, HttpServletRequest request)
	{
		/* getting the JSON response to load in data table*/
		String jsonResponse = histRetService.getHistRetDataTableResponse(request);
		
		/* Setting the response type as JSON */
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");

		return jsonResponse;
	}
	
			
	@RequestMapping("/downloadExcelHistRetData")
	public void downloadExcelHistRetData(HttpServletResponse response, HttpServletRequest request) throws IOException {
		System.out.println("Temp path:"+System.getProperty("java.io.tmpdir"));
		
		SXSSFWorkbook workBook = histRetService.getHistRetExcelData(request);
//		SXSSFWorkbook workBook = null;
//		HistIncomeTaxRetReportHandler HistIncTaxRetReportHandler = new HistIncomeTaxRetReportHandler();
//		workBook = HistIncTaxRetReportHandler.writeFile(result);
		response.setHeader("Content-Disposition",
				"attachment; filename=" + "Legacy_Federal_Tax_Asset_Retirement_Report_FA250.xlsx");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.addCookie(new Cookie("downloadStarted", "1"));
		ServletOutputStream outputStream = response.getOutputStream();
		workBook.write(outputStream);
		outputStream.flush();
		outputStream.close();
		workBook.dispose();
		System.out.println("excel file downloaded");
	}
	
	
	
		
	public Map<String,String> getCompanyGroups(){
		Map<String, String> groupsMap = new LinkedHashMap<String, String>();
		List<String[]> list=histRetService.getCompanyGroups();
		for(String[] s:list) {
			groupsMap.put(s[0], s[1]);
		}
		return groupsMap;
	}
	//Instance
	public Map<String,String> getInstances(){
		Map<String, String> instanceMap = new LinkedHashMap<String, String>();
		List<String[]> list=histRetService.getInstances();
		for(String[] s:list) {
			instanceMap.put(s[0], s[1]);
		}
		return instanceMap;
	}
	
	//YEAR
	public Map<String,String> getYears(){
		Map<String, String> yearMap = new LinkedHashMap<String, String>();
		List<String[]> list=histRetService.getYears();
		for(String[] s:list) {
			yearMap.put(s[0], s[0]);
		}
		return yearMap;
	}
	
	@RequestMapping(value = "getReportHistRetNames", method = RequestMethod.GET)
	public @ResponseBody String getReportNames(
		   @RequestParam(value = "reportyear", required = true) String reportyear) {
		List<String> reportList = new LinkedList<String>();			
		reportList.add(reportyear);
		reportList.add("ASTREG");
		
		List<String[]> list=histRetService.getHistRetReportNames(reportList);
		Map<String, String> reportNamesMap = new LinkedHashMap<String, String>();
		for(String[] s:list) {
			reportNamesMap.put(StringEscapeUtils.escapeHtml4(s[0]), StringEscapeUtils.escapeHtml4(s[1]));			

		}
		JSONObject json = new JSONObject(reportNamesMap);
		return json.toString();
	}
	
	
	
	@RequestMapping(value = "getHistRetCompanyNames", method = RequestMethod.GET)
	public @ResponseBody String getCompanyNames(
	        @RequestParam(value = "groupName", required = true) String groupName) {
		Map<String, String> companyNamesMap = new TreeMap<String, String>();
		List<String[]> list=histRetService.getHistRetCompanyNames(groupName);
		for(String[] s:list) {
			companyNamesMap.put(StringEscapeUtils.escapeHtml4(s[0]), StringEscapeUtils.escapeHtml4(s[0])+" - "+StringEscapeUtils.escapeHtml4(s[1]));
		}
		Gson gson = new Gson();
		String json = gson.toJson(companyNamesMap, TreeMap.class);
		return json.toString();
	}
	
	
}