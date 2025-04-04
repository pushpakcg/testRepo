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
import com.code2java.service.AssetRetirementServiceImpl;
import com.code2java.service.DTServiceImpl;
import com.code2java.service.PropertyTaxService;
import com.code2java.service.PropertyTaxServiceImpl;
import com.code2java.service.SAPHistoricalADSService;
import com.code2java.service.SAPHistoricalIncometaxregService;
import com.code2java.service.SAPHistoricalpropertytaxregService;
import com.faweb.domain.saml.Response;
import com.google.gson.Gson;


@Controller
public class SAPHistoricalADScontroller {

	
	
	@Autowired
	private SAPHistoricalADSService saphistADSservice;
	
	
	@RequestMapping(value = "/SAPHistoricalADS")
	public String loadDatatableADS(Model model)
	{
		model.addAttribute("companyGroupsMap", getSAPHistoricalADSCompanyGroups());
		model.addAttribute("instanceMap", getSAPHistoricalADSInstances());
		model.addAttribute("yearMap", getSAPHistoricalADSYears());
		return "SAPHistoricalADS";
	}	
		
		
		//ServerSide Data	
		@ResponseBody
		@RequestMapping("/loadSAPHistADSserverSideData")
		public String loadSAPHistADSServerSideData(HttpServletResponse response, HttpServletRequest request)
		{
			//String jsonResponse = assetretirementservice.getAssetRetirementDataTableResponse(request);		
			String jsonResponse = saphistADSservice.getSAPHistoricalADSDataTableResponse(request);
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");

			return jsonResponse;
		}
		
		@RequestMapping("/SAPHistADSdownloadExcelData")
		public void downloadExcelData(HttpServletResponse response, HttpServletRequest request) throws IOException {
			System.out.println("Temp path:"+System.getProperty("java.io.tmpdir"));
			
			SXSSFWorkbook workBook = saphistADSservice.getSAPHistoricalADSExcelData(request);			
			//Workbook workBook = null;
			//SXSSFWorkbook workBook = null;
			//SAPHistADSReportHandler ADSReportHandler = new SAPHistADSReportHandler();
			//workBook = ADSReportHandler.writeFile(result);
			response.setHeader("Content-Disposition",
					"attachment; filename=" + "SAP_Historical_Federal_Tax_ADS_Asset_Register.xlsx");
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
		
		public Map<String,String> getSAPHistoricalADSCompanyGroups(){
			Map<String, String> groupsMap = new LinkedHashMap<String, String>();		
			List<String[]> list=saphistADSservice.getSAPHistoricalADSCompanyGroups();
			for(String[] s:list) {
				groupsMap.put(s[0], s[1]);
			}
			return groupsMap;
		}
		//PropertyTax Instance
		public Map<String,String> getSAPHistoricalADSInstances(){
			Map<String, String> instanceMap = new LinkedHashMap<String, String>();
			List<String[]> list=saphistADSservice.getSAPHistoricalADSInstances();	
			for(String[] s:list) {
				instanceMap.put(s[0], s[1]);
			}
			return instanceMap;
		}
		
		//PropertyTax YEAR
		public Map<String,String> getSAPHistoricalADSYears(){
			Map<String, String> yearMap = new LinkedHashMap<String, String>();	
			List<String[]> list=saphistADSservice.getSAPHistoricalADSYears();
			for(String[] s:list) {
				yearMap.put(s[0], s[0]);
			}
			return yearMap;
		}
		//PropertyTax GroupCompanies
		@RequestMapping(value = "getSAPHistoricalADSReportNames", method = RequestMethod.GET)
		public @ResponseBody String getSAPHistoricalADSReportNames(
			   @RequestParam(value = "reportyear", required = true) String reportyear) {
			List<String> reportList = new LinkedList<String>();
			//System.out.println("report Murli====="+reportyear);	
			reportList.add(reportyear);
			reportList.add("SHASTADS");
			//System.out.println("report Murli1111111====="+reportList.get(0));
			//System.out.println("report Murl22222222222i====="+reportList.get(1));
			List<String[]> list=saphistADSservice.getSAPHistoricalADSReportNames(reportList);
			Map<String, String> reportNamesMap = new LinkedHashMap<String, String>();
			for(String[] s:list) {
				reportNamesMap.put(StringEscapeUtils.escapeHtml4(s[0]), StringEscapeUtils.escapeHtml4(s[1]));
				
//				reportNamesMap.put(s[0], s[0]+" - "+s[1]);
			}
			JSONObject json = new JSONObject(reportNamesMap);
			return json.toString();
		}
		
		@RequestMapping(value = "getSAPHistoricalADSCompanyNames", method = RequestMethod.GET)
		public @ResponseBody String getSAPHistoricalADSCompanyNames(
		        @RequestParam(value = "groupName", required = true) String groupName) {
			Map<String, String> companyNamesMap = new TreeMap<String, String>();
			List<String[]> list=saphistADSservice.getSAPHistoricalADSCompanyNames(groupName);					
			for(String[] s:list) {
				companyNamesMap.put(StringEscapeUtils.escapeHtml4(s[0]), StringEscapeUtils.escapeHtml4(s[0])+" - "+StringEscapeUtils.escapeHtml4(s[1]));			}
			Gson gson = new Gson();
			String json = gson.toJson(companyNamesMap, TreeMap.class);
			//return json.toString();
			return json;
		
	}
		
		@RequestMapping("/getSapHistFederalTaxADSHeadings")
		public @ResponseBody String getHeadingNames(HttpServletRequest request)
		{
			List<String> headings=new ArrayList<>();
			List<String[]> headingList=saphistADSservice.getTableHeadings(request);
			for(String[] h: headingList)
			{
				headings.add(h[0]);
			}
			Gson gson=new Gson();
			String jsonH=gson.toJson(headings, ArrayList.class);
			return jsonH;
		}
	
	
}
