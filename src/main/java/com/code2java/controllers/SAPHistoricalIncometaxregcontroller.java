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
import com.code2java.service.SAPHistoricalIncometaxregService;
import com.code2java.service.SAPHistoricalpropertytaxregService;
import com.faweb.domain.saml.Response;
import com.google.gson.Gson;


@Controller
public class SAPHistoricalIncometaxregcontroller {

	
	
	@Autowired
	private SAPHistoricalIncometaxregService saphistincometaxservice;
	
	
	@RequestMapping(value = "/SAPhistoricalIncometaxreg")
	public String loadDatatableProperty(Model model)
	{
		model.addAttribute("companyGroupsMap", getSAPHistoricalIncometaxCompanyGroups());
		model.addAttribute("instanceMap", getSAPHistoricalIncometaxInstances());
		model.addAttribute("yearMap", getSAPHistoricalIncometaxYears());
		return "SAPhistoricalIncometaxreg";
	}	
		
		
		//ServerSide Data	
		@ResponseBody
		@RequestMapping("/loadSAPHistIncomeTaxserverSideData")
		public String loadSAPHistIncomeTaxServerSideData(HttpServletResponse response, HttpServletRequest request)
		{
			//String jsonResponse = assetretirementservice.getAssetRetirementDataTableResponse(request);		
			String jsonResponse = saphistincometaxservice.getSAPHistoricalIncometaxDataTableResponse(request);
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");

			return jsonResponse;
		}
		
		@RequestMapping("/SAPHistIncomeTaxdownloadExcelData")
		public void downloadExcelData(HttpServletResponse response, HttpServletRequest request) throws IOException {
			System.out.println("Temp path:"+System.getProperty("java.io.tmpdir"));
			
			SXSSFWorkbook workBook = saphistincometaxservice.getSAPHistoricalIncometaxExcelData(request);			
			//Workbook workBook = null;
			//SXSSFWorkbook workBook = null;
		//	SAPHistIncometaxReportHandler PropertyReportHandler = new SAPHistIncometaxReportHandler();
			//workBook = PropertyReportHandler.writeFile(result);
			response.setHeader("Content-Disposition",
					"attachment; filename=" + "SAP_Historical_Income_Tax_Asset_Register.xlsx");
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
		
		public Map<String,String> getSAPHistoricalIncometaxCompanyGroups(){
			Map<String, String> groupsMap = new LinkedHashMap<String, String>();		
			List<String[]> list=saphistincometaxservice.getSAPHistoricalIncometaxCompanyGroups();
			for(String[] s:list) {
				groupsMap.put(s[0], s[1]);
			}
			return groupsMap;
		}
		//PropertyTax Instance
		public Map<String,String> getSAPHistoricalIncometaxInstances(){
			Map<String, String> instanceMap = new LinkedHashMap<String, String>();
			List<String[]> list=saphistincometaxservice.getSAPHistoricalIncometaxInstances();	
			for(String[] s:list) {
				instanceMap.put(s[0], s[1]);
			}
			return instanceMap;
		}
		
		//PropertyTax YEAR
		public Map<String,String> getSAPHistoricalIncometaxYears(){
			Map<String, String> yearMap = new LinkedHashMap<String, String>();	
			List<String[]> list=saphistincometaxservice.getSAPHistoricalIncometaxYears();
			for(String[] s:list) {
				yearMap.put(s[0], s[0]);
			}
			return yearMap;
		}
		//PropertyTax GroupCompanies
		@RequestMapping(value = "getSAPHistoricalIncometaxReportNames", method = RequestMethod.GET)
		public @ResponseBody String getSAPHistoricalIncometaxReportNames(
			   @RequestParam(value = "reportyear", required = true) String reportyear) {
			List<String> reportList = new LinkedList<String>();
			//System.out.println("report Murli====="+reportyear);	
			reportList.add(reportyear);
			reportList.add("SHASTREG");
			//System.out.println("report Murli1111111====="+reportList.get(0));
			//System.out.println("report Murl22222222222i====="+reportList.get(1));
			List<String[]> list=saphistincometaxservice.getSAPHistoricalIncometaxReportNames(reportList);
			Map<String, String> reportNamesMap = new LinkedHashMap<String, String>();
			for(String[] s:list) {
				reportNamesMap.put(StringEscapeUtils.escapeHtml4(s[0]), StringEscapeUtils.escapeHtml4(s[1]));
				
//				reportNamesMap.put(s[0], s[0]+" - "+s[1]);
			}
			JSONObject json = new JSONObject(reportNamesMap);
			return json.toString();
		}
		
		@RequestMapping(value = "getSAPHistoricalIncometaxCompanyNames", method = RequestMethod.GET)
		public @ResponseBody String getSAPHistoricalIncometaxCompanyNames(
		        @RequestParam(value = "groupName", required = true) String groupName) {
			Map<String, String> companyNamesMap = new TreeMap<String, String>();
			List<String[]> list=saphistincometaxservice.getSAPHistoricalIncometaxCompanyNames(groupName);					
			for(String[] s:list) {
				companyNamesMap.put(StringEscapeUtils.escapeHtml4(s[0]), StringEscapeUtils.escapeHtml4(s[0])+" - "+StringEscapeUtils.escapeHtml4(s[1]));			}
			Gson gson = new Gson();
			String json = gson.toJson(companyNamesMap, TreeMap.class);
			//return json.toString();
			return json;
		
	}
		@RequestMapping("/getHistIncomeTaxRegHeadings")
		public @ResponseBody String getHeadingNames(HttpServletRequest request)
		{
			List<String> headings=new ArrayList<>();
			List<String[]> headingList=saphistincometaxservice.getTableHeadings(request);
			//System.out.println("report id= "+request.getParameter("reportId"));
			for(String[] h: headingList)
			{
				//System.out.println("getHistIncomeTaxRegHeadings=="+h[0]);
				headings.add(h[0]);
			}
			Gson gson=new Gson();
			String jsonH=gson.toJson(headings, ArrayList.class);
			return jsonH;
		}
		
//		@RequestMapping(value="/gethyear", method= RequestMethod.POST)
//		public @ResponseBody void  yearReportId(HttpServletRequest request, HttpServletResponse response)
//		{
//			
////			String val= request.getParameter("dynamicYear");
////			String val2= request.getParameter("dynamicReportName");
////			System.out.println(val);
////			System.out.println(val2);
//			saphistincometaxservice.callProcedure(String reportTableName);
//			System.out.println("triggered");
//			
//		}
	
}
