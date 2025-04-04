package com.code2java.controllers;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.code2java.service.AssetRegisterSummaryReportServiceImpl;
import com.google.gson.Gson;

@Controller
public class AssetRegisterSummaryReportController {
	
	@Autowired	
	private AssetRegisterSummaryReportServiceImpl assetRegisterSummaryReportService;
	
	//@Autowired
	//private PropertyTaxRetirementServiceImpl propertytaxretirementService;
	
	
	
	@RequestMapping(value = "/AssetRegisterSummaryReport")
	public String AssetRegisterSummaryView(Model model)
	{
		model.addAttribute("companyGroupsMap", getAssetRegisterSummaryCompanyGroups());
		model.addAttribute("instanceMap", getAssetRegisterSummaryInstances());
		model.addAttribute("yearMap", getAssetRegisterSummaryYears());
		return "AssetRegisterSummaryReport";
		
		 
	}	
	@ResponseBody
	@RequestMapping("/getAssetRegisterSummaryReportServerSideData")
	public String AssetRegisterSummaryReportServiceResgisterServerSideData(HttpServletResponse response, HttpServletRequest request)
	{			 
		String jsonResponse = 	assetRegisterSummaryReportService.getAssetRegisterSummaryDataTableResponse(request);
		//jsonResponse = StringEscapeUtils.escapeHtml4(jsonResponse);
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		return jsonResponse;
	}
	
	@RequestMapping("/getAssetRegisterSummaryReportDownloadExcelData")
	public void downloadExcelData(HttpServletResponse response, HttpServletRequest request) throws IOException {
		System.out.println("Temp path:"+System.getProperty("java.io.tmpdir"));
		
		//List<List<String>> result = assetRegisterSummaryReportService.getADSTaxAssetRegisterdownloadExcelData(request);
		
		SXSSFWorkbook workBook = assetRegisterSummaryReportService.getAssetRegisterSummarydownloadExcelData(request);
		
		//Workbook workBook = null;
		//SXSSFWorkbook workBook = null;
		//ADSTaxAssetReportHandler ADSTaxAssetReportHandler = new ADSTaxAssetReportHandler();
		//workBook = ADSTaxAssetReportHandler.writeFile(result);
		response.setHeader("Content-Disposition",
				"attachment; filename=" + "Asset_Register_Summary_Report.xlsx");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.addCookie(new Cookie("downloadStarted", "1"));
		ServletOutputStream outputStream = response.getOutputStream();
		workBook.write(outputStream);
		outputStream.flush();
		outputStream.close();
		workBook.dispose();
		System.out.println("excel file downloaded");
	}
	
	
			public Map<String,String> getAssetRegisterSummaryCompanyGroups(){
				Map<String, String> groupsMap = new LinkedHashMap<String, String>();		
				List<String[]> list=assetRegisterSummaryReportService.getAssetRegisterSummaryCompanyGroups();
				
				for(String[] s:list) {
					groupsMap.put(s[0], s[1]);
				}
				return groupsMap;
			}
			
			
			public Map<String,String> getAssetRegisterSummaryInstances(){
				Map<String, String> instanceMap = new LinkedHashMap<String, String>();				
				List<String[]> list=assetRegisterSummaryReportService.getAssetRegisterSummaryInstances();
		
				for(String[] s:list) {
					instanceMap.put(s[0], s[1]);
				}
				return instanceMap;
			}
			
			
			public Map<String,String> getAssetRegisterSummaryYears(){
				Map<String, String> yearMap = new LinkedHashMap<String, String>();	
				List<String[]> list=assetRegisterSummaryReportService.getAssetRegisterSummaryYears();
				for(String[] s:list) {
					yearMap.put(s[0], s[1]);
				}
				return yearMap;
			}
			
			@RequestMapping(value = "getAssetRegisterSummaryReportCompanyNames", method = RequestMethod.POST)
			public @ResponseBody String getADSTaxAssetRegistercompanyNames(
			        @RequestParam(value = "groupName", required = true) String groupName) {
				Map<String, String> companyNamesMap = new TreeMap<String, String>();
				List<String[]> list=assetRegisterSummaryReportService.getAssetRegisterSummarycompanyNames(groupName);
											
				for(String[] s:list) {
					companyNamesMap.put(s[0], s[0]+" - "+s[1]);			}
				//JSONObject json = new JSONObject(companyNamesMap);
				Gson gson = new Gson();
				String json = gson.toJson(companyNamesMap, TreeMap.class);
				//return json.toString();
				return json;
			
		}

//			@RequestMapping("/getFederalTaxSummaryReportHeadings")
//			public @ResponseBody String getHeadingNames()
//			{
//				List<String> headings=new ArrayList<>();
//				
//				List<String[]> headingList=assetRegisterSummaryReportService.getTableHeadings();
//				System.out.println("wwww DT Controller= data===");
//				for(String[] h: headingList)
//				{
//					//System.out.println("DT Refresh--"+h[0]);
//					headings.add(h[0]);
//				}
//				Gson gson=new Gson();
//				String jsonH=gson.toJson(headings, ArrayList.class);
//				return jsonH;
//			}

}



