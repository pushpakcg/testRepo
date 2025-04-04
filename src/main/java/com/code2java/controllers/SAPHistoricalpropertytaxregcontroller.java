package com.code2java.controllers;

import org.springframework.stereotype.Controller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import com.code2java.service.SAPHistoricalpropertytaxregService;
import com.code2java.service.SAPHistoricalproptaxserviceimpl;
import com.faweb.domain.saml.Response;
import com.google.gson.Gson;


@Controller
public class SAPHistoricalpropertytaxregcontroller {

	
	
	@Autowired
	private SAPHistoricalpropertytaxregService saphistpropertytaxservice;
	
	
	@RequestMapping(value = "/SAPhistoricalPropertytaxreg")
	public String loadSAPHistDatatableProperty(Model model)
	{
		model.addAttribute("companyGroupsMap", getSAPHistPropertyTaxCompanyGroups());
		model.addAttribute("instanceMap", getSAPHistPropertyTaxInstances());
		model.addAttribute("yearMap", getSAPHistPropertyTaxYears());
		return "SAPhistoricalPropertytaxreg";
	}	
		
		
		//ServerSide Data	
		@ResponseBody
		@RequestMapping("/loadSAPHistPropertyTaxserverSideData")
		public String loadSAPHistPropertyTaxServerSideData(HttpServletResponse response, HttpServletRequest request)
		{
			//String jsonResponse = assetretirementservice.getAssetRetirementDataTableResponse(request);		
			String jsonResponse = saphistpropertytaxservice.getSAPHistoricalPropertyTaxDataTableResponse(request);
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");

			return jsonResponse;
		}
		
		@RequestMapping("/SAPHistPropertyTaxdownloadExcelData")
		public void downloadExcelData(HttpServletResponse response, HttpServletRequest request) throws IOException {
			System.out.println("Temp path:"+System.getProperty("java.io.tmpdir"));
			
			SXSSFWorkbook workbook = saphistpropertytaxservice.getSAPHistoricalPropertyTaxExcelData(request);			
			//Workbook workBook = null;
			//SXSSFWorkbook workBook = null;
			//SAPHistPropertyReportHandler PropertyReportHandler = new SAPHistPropertyReportHandler();
			//workBook = PropertyReportHandler.writeFile(result);
			response.setHeader("Content-Disposition",
					"attachment; filename=" + "SAP_Historical_Property_Tax_Asset_Register.xlsx");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.addCookie(new Cookie("downloadStarted", "1"));
			ServletOutputStream outputStream = response.getOutputStream();
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
			workbook.dispose();
			System.out.println("excel file downloaded");
			
		}
		
		//PropertyTax CompanyGropus
		
		public Map<String,String> getSAPHistPropertyTaxCompanyGroups(){
			Map<String, String> groupsMap = new LinkedHashMap<String, String>();		
			List<String[]> list=saphistpropertytaxservice.getSAPHistoricalPropertyTaxCompanyGroups();
			for(String[] s:list) {
				groupsMap.put(s[0], s[1]);
			}
			return groupsMap;
		}
		//PropertyTax Instance
		public Map<String,String> getSAPHistPropertyTaxInstances(){
			Map<String, String> instanceMap = new LinkedHashMap<String, String>();
			List<String[]> list=saphistpropertytaxservice.getSAPHistoricalPropertyTaxInstances();	
			for(String[] s:list) {
				instanceMap.put(s[0], s[1]);
			}
			return instanceMap;
		}
		
		//PropertyTax YEAR
		public Map<String,String> getSAPHistPropertyTaxYears(){
			Map<String, String> yearMap = new LinkedHashMap<String, String>();	
			List<String[]> list=saphistpropertytaxservice.getSAPHistoricalPropertyTaxYears();
			for(String[] s:list) {
				yearMap.put(s[0], s[0]);
			}
			return yearMap;
		}
		//PropertyTax GroupCompanies
		@RequestMapping(value = "getSAPHistPropTaxReportNames", method = RequestMethod.GET)
		public @ResponseBody String getSAPHistPropTaxReportNames(
			   @RequestParam(value = "reportyear", required = true) String reportyear) {
			List<String> reportList = new LinkedList<String>();
			//System.out.println("report Murli====="+reportyear);	
			reportList.add(reportyear);
			reportList.add("SHPPTREG");
			//System.out.println("report Murli1111111====="+reportList.get(0));
			//System.out.println("report Murl22222222222i====="+reportList.get(1));
			List<String[]> list=saphistpropertytaxservice.getSAPHistoricalPropTaxReportNames(reportList);
			Map<String, String> reportNamesMap = new LinkedHashMap<String, String>();
			for(String[] s:list) {
				reportNamesMap.put(StringEscapeUtils.escapeHtml4(s[0]), StringEscapeUtils.escapeHtml4(s[1]));
				
//				reportNamesMap.put(s[0], s[0]+" - "+s[1]);
			}
			JSONObject json = new JSONObject(reportNamesMap);
			return json.toString();
		}
		
		@RequestMapping(value = "getSAPHistPropertyTaxCompanyNames", method = RequestMethod.GET)
		public @ResponseBody String getSAPHistPropertyTaxCompanyNames(
		        @RequestParam(value = "groupName", required = true) String groupName) {
			Map<String, String> companyNamesMap = new TreeMap<String, String>();
			List<String[]> list=saphistpropertytaxservice.getSAPHistoricalPropertyTaxCompanyNames(groupName);					
			for(String[] s:list) {
				companyNamesMap.put(StringEscapeUtils.escapeHtml4(s[0]), StringEscapeUtils.escapeHtml4(s[0])+" - "+StringEscapeUtils.escapeHtml4(s[1]));			}
			Gson gson = new Gson();
			String json = gson.toJson(companyNamesMap, TreeMap.class);
			//return json.toString();
			return json;
		
	}
	
		
		@RequestMapping("/getSAPHistPropertyTaxHeadings")
		public @ResponseBody String getHeadingNames(HttpServletRequest request)
		{
			List<String> headings=new ArrayList<>();
			List<String[]> headingList=saphistpropertytaxservice.getTableHeadings(request);
		//	System.out.println("wwww= "+request.getParameter("reportId"));
			for(String[] h: headingList)
			{
				//System.out.println("working--"+h[0]);
				headings.add(h[0]);
			}
			Gson gson=new Gson();
			String jsonH=gson.toJson(headings, ArrayList.class);
			return jsonH;
		}
	
}
