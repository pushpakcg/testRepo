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
import com.code2java.service.HistPropTaxRetirementIncomeTaxService;
import com.code2java.service.AssetRetirementServiceImpl;
import com.faweb.domain.saml.Response;
import com.google.gson.Gson;


@Controller
public class HistPropTaxRetirementIncomeTaxController {
	
	@Autowired
	private HistPropTaxRetirementIncomeTaxService histRegService;
	
	
	
	
	
	/**
	 * @author code2java
	 * This method will be called when user clicks on link showed on welcome.jsp page
	 * The request is mapped using @RequestMapping annotation. 
	 * @return
	 */
	@RequestMapping(value = "/HistoricalPropertyTaxRetirementReg")
	public String loadDatatable(Model model)
	{
		
		model.addAttribute("companyGroupsMap", getHistPropTaxRetirementCompanyGroups());
		model.addAttribute("instanceMap", getHistPropTaxRetirementInstances());
		model.addAttribute("yearMap", getHistPropTaxRetirementYears());
		//model.addAttribute("reportNamesMap", getLoadReportNames());
		return "HistoricalPropertyTaxRetirementReg";
		
	}
	
	
	
	/**
	 * This method will be called from AJAX callback of Data Tables 
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loadServerSideHistPropTaxRetirementRegData")
	public String loadServerSideHistPropTaxRetirementRegData(HttpServletResponse response, HttpServletRequest request)
	{
		/* getting the JSON response to load in data table*/
		String jsonResponse = histRegService.getHistPropTaxRetirementDataTableResponse(request);
		
		/* Setting the response type as JSON */
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");

		return jsonResponse;
	}
	
			
	@RequestMapping("/downloadExcelHistPropTaxRetirementRegData")
	public void downloadExcelHistPropTaxRetirementRegData(HttpServletResponse response, HttpServletRequest request) throws IOException {
		System.out.println("Temp path:"+System.getProperty("java.io.tmpdir"));
		
		SXSSFWorkbook workBook = histRegService.getHistPropTaxRetirementExcelData(request);
		//Workbook workBook = null;
//		SXSSFWorkbook workBook = null;
//		HistPropTaxRetirementIncomeTaxReportHandler HistIncTaxReportHandler = new HistPropTaxRetirementIncomeTaxReportHandler();
//		workBook = HistIncTaxReportHandler.writeFile(result);
		response.setHeader("Content-Disposition",
				"attachment; filename=" + "Legacy_Property_Tax_Asset_Retirement_Report_FA250.xlsx");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.addCookie(new Cookie("downloadStarted", "1"));
		ServletOutputStream outputStream = response.getOutputStream();
		workBook.write(outputStream);
		outputStream.flush();
		outputStream.close();
		workBook.dispose();
		System.out.println("excel file downloaded");
	}
	
	
	
		
	public Map<String,String> getHistPropTaxRetirementCompanyGroups(){
		Map<String, String> groupsMap = new LinkedHashMap<String, String>();
		List<String[]> list=histRegService.getHistPropTaxRetirementCompanyGroups();
		for(String[] s:list) {
			groupsMap.put(s[0], s[1]);
		}
		return groupsMap;
	}
	//Instance
	public Map<String,String> getHistPropTaxRetirementInstances(){
		Map<String, String> instanceMap = new LinkedHashMap<String, String>();
		List<String[]> list=histRegService.getHistPropTaxRetirementInstances();
		for(String[] s:list) {
			instanceMap.put(s[0], s[1]);
		}
		return instanceMap;
	}
	
	//YEAR
	public Map<String,String> getHistPropTaxRetirementYears(){
		Map<String, String> yearMap = new LinkedHashMap<String, String>();
		List<String[]> list=histRegService.getHistPropTaxRetirementYears();
		for(String[] s:list) {
			yearMap.put(s[0], s[0]);
		}
		return yearMap;
	}
	
	@RequestMapping(value = "getHistPropTaxRetirementReportNames", method = RequestMethod.GET)
	public @ResponseBody String getHistPropTaxRetirementReportNames(
		   @RequestParam(value = "reportyear", required = true) String reportyear) {
		List<String> reportList = new LinkedList<String>();
		//System.out.println("report Murli====="+reportyear);	
		reportList.add(reportyear);
		reportList.add("PSTRET");
		//System.out.println("report Murli1111111====="+reportList.get(0));
		//System.out.println("report Murl22222222222i====="+reportList.get(1));
		List<String[]> list=histRegService.getHistPropTaxRetirementReportNames(reportList);
		Map<String, String> reportNamesMap = new LinkedHashMap<String, String>();
		for(String[] s:list) {
			reportNamesMap.put(StringEscapeUtils.escapeHtml4(s[0]), StringEscapeUtils.escapeHtml4(s[1]));
			
//			reportNamesMap.put(s[0], s[0]+" - "+s[1]);
		}
		JSONObject json = new JSONObject(reportNamesMap);
		return json.toString();
	}
	
	
	
//	public  List<String> getLoadReportNames() {
//		
//		
//		   System.out.println("report Murli controller=====");
//		//Map<String, String> reportNamesMap = new LinkedHashMap<String, String>();
//		   List<String> reportNameList = new LinkedList<String>();
//			List<String[]> list=histRegService.getLoadReportNames();
//			System.out.println("report Murli controller====="+list.size());
//			for(String[] s:list) {
//	   //reportNamesMap.put(s[0], s[1]);
//				reportNameList.add(s[0]+" - "+s[1]);
//				//mv.addObject(s[0], s[0]+" - "+s[1]);
//			}
//			return reportNameList;
//	}
			
			
	/*
	 * } JSONObject json = new JSONObject(reportNamesMap); return json.toString(); }
	 */	
	
	
	@RequestMapping(value = "getHistPropTaxRetirementRegCompanyNames", method = RequestMethod.GET)
	public @ResponseBody String getHistPropTaxRetirementCompanyNames(
	        @RequestParam(value = "groupName", required = true) String groupName) {
		Map<String, String> companyNamesMap = new TreeMap<String, String>();
		List<String[]> list=histRegService.getHistPropTaxRetirementCompanyNames(groupName);
		for(String[] s:list) {
			companyNamesMap.put(StringEscapeUtils.escapeHtml4(s[0]), StringEscapeUtils.escapeHtml4(s[0])+" - "+StringEscapeUtils.escapeHtml4(s[1]));
		}
		Gson gson = new Gson();
		String json = gson.toJson(companyNamesMap, TreeMap.class);
		return json;
	}
	
	
	
}