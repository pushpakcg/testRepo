package com.code2java.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import com.code2java.service.SAPHistPropTaxRetirementTaxService;
import com.code2java.service.AssetRetirementServiceImpl;
import com.faweb.domain.saml.Response;
import com.google.gson.Gson;


@Controller
public class SAPHistPropTaxRetirementTaxController {
	
	@Autowired
	private SAPHistPropTaxRetirementTaxService SAPhistRegService;
	
	
	
	
	
	/**
	 * @author code2java
	 * This method will be called when user clicks on link showed on welcome.jsp page
	 * The request is mapped using @RequestMapping annotation. 
	 * @return
	 */
	@RequestMapping(value = "/SAPHistoricalPropertyTaxRetirementReg")
	public String loadDatatable(Model model)
	{
		
		model.addAttribute("companyGroupsMap", getSAPHistPropTaxRetirementCompanyGroups());
		model.addAttribute("instanceMap", getSAPHistPropTaxRetirementInstances());
		model.addAttribute("yearMap", getSAPHistPropTaxRetirementYears());
		//model.addAttribute("reportNamesMap", getLoadReportNames());
		return "SAPHistoricalPropertyTaxRetirementReg";
		
	}
	
	
	
	/**
	 * This method will be called from AJAX callback of Data Tables 
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loadServerSideSAPHistPropTaxRetirementRegData")
	public String loadServerSideSAPHistPropTaxRetirementRegData(HttpServletResponse response, HttpServletRequest request)
	{
		/* getting the JSON response to load in data table*/
		String jsonResponse = SAPhistRegService.getSAPHistPropTaxRetirementDataTableResponse(request);
		
		/* Setting the response type as JSON */
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");

		return jsonResponse;
	}
	
			
	@RequestMapping("/downloadExcelSAPHistPropTaxRetirementRegData")
	public void downloadExcelSAPHistPropTaxRetirementRegData(HttpServletResponse response, HttpServletRequest request) throws IOException {
		System.out.println("Temp path:"+System.getProperty("java.io.tmpdir"));
		
		SXSSFWorkbook workBook = SAPhistRegService.getSAPHistPropTaxRetirementExcelData(request);
		//Workbook workBook = null;
//		SXSSFWorkbook workBook = null;
//		SAPHistPropTaxRetirementTaxReportHandler SAPHistTaxReportHandler = new SAPHistPropTaxRetirementTaxReportHandler();
//		workBook = SAPHistTaxReportHandler.writeFile(result);
		response.setHeader("Content-Disposition",
				"attachment; filename=" + "SAP_Historical_Property_Tax_Retirement_Report.xlsx");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.addCookie(new Cookie("downloadStarted", "1"));
		ServletOutputStream outputStream = response.getOutputStream();
		workBook.write(outputStream);
		outputStream.flush();
		outputStream.close();
		workBook.dispose();
		System.out.println("excel file downloaded");
	}
	
	
	
		
	public Map<String,String> getSAPHistPropTaxRetirementCompanyGroups(){
		Map<String, String> groupsMap = new LinkedHashMap<String, String>();
		List<String[]> list=SAPhistRegService.getSAPHistPropTaxRetirementCompanyGroups();
		for(String[] s:list) {
			groupsMap.put(s[0], s[1]);
		}
		return groupsMap;
	}
	//Instance
	public Map<String,String> getSAPHistPropTaxRetirementInstances(){
		Map<String, String> instanceMap = new LinkedHashMap<String, String>();
		List<String[]> list=SAPhistRegService.getSAPHistPropTaxRetirementInstances();
		for(String[] s:list) {
			instanceMap.put(s[0], s[1]);
		}
		return instanceMap;
	}
	
	//YEAR
	public Map<String,String> getSAPHistPropTaxRetirementYears(){
		Map<String, String> yearMap = new LinkedHashMap<String, String>();
		List<String[]> list=SAPhistRegService.getSAPHistPropTaxRetirementYears();
		for(String[] s:list) {
			yearMap.put(s[0], s[0]);
		}
		return yearMap;
	}
	
	@RequestMapping(value = "getSAPHistPropTaxRetirementReportNames", method = RequestMethod.GET)
	public @ResponseBody String getSAPHistPropTaxRetirementReportNames(
		   @RequestParam(value = "reportyear", required = true) String reportyear) {
		List<String> reportList = new LinkedList<String>();
		//System.out.println("report Murli====="+reportyear);	
		reportList.add(reportyear);
		reportList.add("SHPPTRET");
		//System.out.println("report Murli1111111====="+reportList.get(0));
		//System.out.println("report Murl22222222222i====="+reportList.get(1));
		List<String[]> list=SAPhistRegService.getSAPHistPropTaxRetirementReportNames(reportList);
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
	
	
	@RequestMapping(value = "getSAPHistPropTaxRetirementRegCompanyNames", method = RequestMethod.GET)
	public @ResponseBody String getSAPHistPropTaxRetirementCompanyNames(
	        @RequestParam(value = "groupName", required = true) String groupName) {
		Map<String, String> companyNamesMap = new TreeMap<String, String>();
		List<String[]> list=SAPhistRegService.getSAPHistPropTaxRetirementCompanyNames(groupName);
		for(String[] s:list) {
			companyNamesMap.put(StringEscapeUtils.escapeHtml4(s[0]), StringEscapeUtils.escapeHtml4(s[0])+" - "+StringEscapeUtils.escapeHtml4(s[1]));
		}
		Gson gson = new Gson();
		String json = gson.toJson(companyNamesMap, TreeMap.class);
		return json;
	}
	
	@RequestMapping("/getSapHistPropTaxRetHeadings")
	public @ResponseBody String getHeadingNames(HttpServletRequest request)
	{
		List<String> headings=new ArrayList<>();
		List<String[]> headingList=SAPhistRegService.getTableHeadings(request);
		for(String[] h: headingList)
		{
			headings.add(h[0]);
		}
		Gson gson=new Gson();
		String jsonH=gson.toJson(headings, ArrayList.class);
		return jsonH;
	}
	
}