package com.code2java.controllers;

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

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.code2java.service.SAPHistAssetTransferService;

import com.google.gson.Gson;

@Controller
public class SAPHistAssetTransferReportController{

	
	
	@Autowired
	private SAPHistAssetTransferService sapHistAssetTransferService;
	
	
	@RequestMapping(value = "/SAPHistAssetTransfer")
	public String loadDatatableAssetTransfer(Model model)
	{
		model.addAttribute("companyGroupsMap", getSAPHistoricalAssetTransferCompanyGroups());
//		model.addAttribute("instanceMap", getSAPHistoricalAssetTransferInstances());
		model.addAttribute("yearMap", getSAPHistoricalAssetTransferYears());
		return "SAPHistAssetTransferReport";
	}	
		
		
		//ServerSide Data	
		@ResponseBody
		@RequestMapping("/loadSAPHistAssetTransferserverSideData")
		public String loAssetTransferAPHistAssetTransferServerSideData(HttpServletResponse response, HttpServletRequest request)
		{
			
			//String jsonResponse = assetretirementservice.getAssetRetirementDataTableResponse(request);		
			String jsonResponse = sapHistAssetTransferService.getSAPHistAssetTransferDataTableResponse(request);
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");
			
			return jsonResponse;
		}
		
		@RequestMapping("/SAPHistAssetTransferdownloadExcelData")
		public void downloadExcelData(HttpServletResponse response, HttpServletRequest request) throws IOException {
			System.out.println("Temp path:"+System.getProperty("java.io.tmpdir"));
			
			SXSSFWorkbook workBook = sapHistAssetTransferService.getSAPHistAssetTransferExcelData(request);			
			//Workbook workBook = null;
			//SXSSFWorkbook workBook = null;
			//SAPHistAssetTransferReportHandler AssetTransferReportHandler = new SAPHistAssetTransferReportHandler();
			//workBook = AssetTransferReportHandler.writeFile(result);
			response.setHeader("Content-Disposition",
					"attachment; filename=" + "SAP_Historical_Asset_Transfer_Report.xlsx");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreAssetTransferheetml.sheet");
			response.addCookie(new Cookie("downloadStarted", "1"));
			ServletOutputStream outputStream = response.getOutputStream();
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
			workBook.dispose();
			System.out.println("excel file downloaded");
		}
		
		//PropertyTax CompanyGropus
		
		public Map<String,String> getSAPHistoricalAssetTransferCompanyGroups(){
			Map<String, String> groupsMap = new LinkedHashMap<String, String>();		
			List<String[]> list=sapHistAssetTransferService.getSAPHistAssetTransferCompanyGroups();
			for(String[] s:list) {
				groupsMap.put(s[0], s[1]);
			}
			return groupsMap;
		}
		//PropertyTax Instance
//		public Map<String,String> getSAPHistoricalAssetTransferInstances(){
//			Map<String, String> instanceMap = new LinkedHashMap<String, String>();
//			List<String[]> list=saphistAssetTransferservice.getSAPHistoricalAssetTransferInstances();	
//			for(String[] s:list) {
//				instanceMap.put(s[0], s[1]);
//			}
//			return instanceMap;
//		}
		
		//PropertyTax YEAR
		public Map<String,String> getSAPHistoricalAssetTransferYears(){
			Map<String, String> yearMap = new LinkedHashMap<String, String>();	
			List<String[]> list=sapHistAssetTransferService.getSAPHistAssetTransferYears();
			for(String[] s:list) {
				yearMap.put(s[0], s[0]);
			}
			return yearMap;
		}
		//PropertyTax GroupCompanies
		@RequestMapping(value = "getSAPHistoricalAssetTransferReportNames", method = RequestMethod.GET)
		public @ResponseBody String getSAPHistoricalAssetTransferReportNames(
			   @RequestParam(value = "reportyear", required = true) String reportyear) {
			List<String> reportList = new LinkedList<String>();
			//System.out.println("report Murli====="+reportyear);	
			reportList.add(reportyear);
			reportList.add("SHASTAssetTransfer");
			//System.out.println("report Murli1111111====="+reportList.get(0));
			//System.out.println("report Murl22222222222i====="+reportList.get(1));
			List<String[]> list=sapHistAssetTransferService.getSAPHistAssetTransferReportNames(reportList);
			Map<String, String> reportNamesMap = new LinkedHashMap<String, String>();
			for(String[] s:list) {
				reportNamesMap.put(StringEscapeUtils.escapeHtml4(s[0]), StringEscapeUtils.escapeHtml4(s[1]));
				
//				reportNamesMap.put(s[0], s[0]+" - "+s[1]);
			}
			JSONObject json = new JSONObject(reportNamesMap);
			return json.toString();
		}
		
		@RequestMapping(value = "getSAPHistoricalAssetTransferCompanyNames", method = RequestMethod.GET)
		public @ResponseBody String getSAPHistoricalAssetTransferCompanyNames(
		        @RequestParam(value = "groupName", required = true) String groupName) {
			Map<String, String> companyNamesMap = new TreeMap<String, String>();
			List<String[]> list=sapHistAssetTransferService.getSAPHistAssetTransferCompanyNames(groupName);					
			for(String[] s:list) {
				companyNamesMap.put(StringEscapeUtils.escapeHtml4(s[0]), StringEscapeUtils.escapeHtml4(s[0])+" - "+StringEscapeUtils.escapeHtml4(s[1]));			}
			Gson gson = new Gson();
			String json = gson.toJson(companyNamesMap, TreeMap.class);
			//return json.toString();
			return json;
		
	}
		
		@RequestMapping("/getSapHistAssetTransferHeadings")
		public @ResponseBody String getHeadingNames(HttpServletRequest request)
		{
			List<String> headings=new ArrayList<>();
			List<String[]> headingList=sapHistAssetTransferService.getTableHeadings(request);
			for(String[] h: headingList)
			{
				headings.add(h[0]);
			}
			Gson gson=new Gson();
			String jsonH=gson.toJson(headings, ArrayList.class);
			return jsonH;
		}
	
	
}
