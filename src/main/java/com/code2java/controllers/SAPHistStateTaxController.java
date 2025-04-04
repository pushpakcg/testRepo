package com.code2java.controllers;

import org.springframework.stereotype.Controller;
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
import com.code2java.service.AssetRetirementServiceImpl;
import com.code2java.service.DTServiceImpl;
import com.code2java.service.SAPHistStateTaxService;
import com.code2java.service.SAPHistStateTaxServiceImpl;
import com.code2java.service.StateTaxService;
import com.code2java.service.StateTaxServiceImpl;
import com.faweb.domain.saml.Response;
import com.google.gson.Gson;


@Controller
public class SAPHistStateTaxController {

	
	
	@Autowired
	private SAPHistStateTaxServiceImpl statetaxservice;
	
	
	@RequestMapping(value = "/SAPHistStateTax")
	public String loadSAPHistDatatableStateTax(Model model)
	{
		model.addAttribute("companyGroupsMap", getSAPHistStateTaxCompanyGroups());
		model.addAttribute("stateMap", getSAPHistStateNames());
		model.addAttribute("instanceMap", getSAPHistStateTaxTaxInstances());
		model.addAttribute("yearMap", getSAPHistStateTaxYears());
		return "SAPHistStateTax";
	}	
		
	
		//ServerSide Data	
		@ResponseBody
		@RequestMapping("/loadSAPHistStateTaxserverSideData")
		public String loadSAPHistStateTaxServerSideData(HttpServletResponse response, HttpServletRequest request)
		{
					
			String jsonResponse = statetaxservice.getSAPHistStateTaxDataTableResponse(request);
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");

			return jsonResponse;
		}
		
		@RequestMapping("/SAPHistStateTaxdownloadExcelData")
		public void downloadSAPHistExcelData(HttpServletResponse response, HttpServletRequest request) throws IOException {
			System.out.println("Temp path:"+System.getProperty("java.io.tmpdir"));
			
			SXSSFWorkbook workBook = statetaxservice.getSAPHistStateTaxExcelData(request);

			//Workbook workBook = null;
//			SXSSFWorkbook workBook = null;
//			SAPHistStateTaxReportHandler sapStateTaxReportHandler = new SAPHistStateTaxReportHandler();
//			workBook = sapStateTaxReportHandler.writeFile(result);
			response.setHeader("Content-Disposition",
					"attachment; filename=" + "SAP_Hist_State_Tax_Asset_Listing.xlsx");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.addCookie(new Cookie("downloadStarted", "1"));
			ServletOutputStream outputStream = response.getOutputStream();
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
			workBook.dispose();
			System.out.println("excel file downloaded");
		}
		
		//PropertyTax CompanyGropus
		
		public Map<String,String> getSAPHistStateTaxCompanyGroups(){
			
			Map<String, String> groupsMap = new LinkedHashMap<String, String>();		
			List<String[]> list=statetaxservice.getSAPHistStateTaxCompanyGroups();
			for(String[] s:list) {
				groupsMap.put(s[0], s[1]);
			}
			return groupsMap;
		}
		
		//state group
		
		public Map<String,String> getSAPHistStateNames(){
			
			Map<String, String> stateMap = new LinkedHashMap<String, String>();		
			List<String[]> list=statetaxservice.getSAPHistStateNames();
			for(String[] s:list) {
				
				stateMap.put(s[0], s[0]+" - "+s[1]);
				//stateMap.put(s[0], s[1]);
			}
			return stateMap;
		}
		
		
		
		
		
		//PropertyTax Instance
		public Map<String,String> getSAPHistStateTaxTaxInstances(){
			Map<String, String> instanceMap = new LinkedHashMap<String, String>();
			List<String[]> list=statetaxservice.getSAPHistStateTaxInstances();	
			for(String[] s:list) {
				instanceMap.put(s[0], s[1]);
			}
			return instanceMap;
		}
		
		//PropertyTax YEAR
		public Map<String,String> getSAPHistStateTaxYears(){
			Map<String, String> yearMap = new LinkedHashMap<String, String>();	
			List<String[]> list=statetaxservice.getSAPHistStateTaxYears();
			for(String[] s:list) {
				yearMap.put(s[0], s[0]);
			}
			return yearMap;
		}
		
		@RequestMapping(value = "getSAPHistStateTaxReportNames", method = RequestMethod.GET)
	public @ResponseBody String getSAPHistStateTaxReportNames(
		   @RequestParam(value = "reportyear", required = true) String reportyear) {
		List<String> reportList = new LinkedList<String>();
		//System.out.println("report Murli====="+reportyear);	
		reportList.add(reportyear);
		reportList.add("SHSTAREG");
		//System.out.println("report Murli1111111====="+reportList.get(0));
		//System.out.println("report Murl22222222222i====="+reportList.get(1));
		List<String[]> list=statetaxservice.getSAPHistStateTaxReportNames(reportList);
		Map<String, String> reportNamesMap = new LinkedHashMap<String, String>();
		for(String[] s:list) {
			reportNamesMap.put(s[0], s[1]);
			
//			reportNamesMap.put(s[0], s[0]+" - "+s[1]);
		}
		JSONObject json = new JSONObject(reportNamesMap);
		return json.toString();
	}
		
		//PropertyTax GroupCompanies  
		
		@RequestMapping(value = "getSAPHistStateTaxCompanyNames", method = RequestMethod.GET)
		public @ResponseBody String getSAPHistStateTaxCompanyNames(
		        @RequestParam(value = "groupName", required = true) String groupName) {
			Map<String, String> companyNamesMap = new TreeMap<String, String>();
			List<String[]> list=statetaxservice.getSAPHistStateTaxCompanyNames(groupName);					
			for(String[] s:list) {
				companyNamesMap.put(s[0], s[0]+" - "+s[1]);			}
			//JSONObject json = new JSONObject(companyNamesMap);
			Gson gson = new Gson();
			String json = gson.toJson(companyNamesMap, TreeMap.class);
			//return json.toString();
			return json;
		
	}
		
		@RequestMapping("/getSapHistStateTaxHeadings")
		public @ResponseBody String getHeadingNames(HttpServletRequest request)
		{
			List<String> headings=new ArrayList<>();
			List<String[]> headingList=statetaxservice.getTableHeadings(request);
			for(String[] h: headingList)
			{
				headings.add(h[0]);
			}
			Gson gson=new Gson();
			String jsonH=gson.toJson(headings, ArrayList.class);
			return jsonH;
		}
	
	
}
