package com.code2java.controllers;

import java.io.IOException;
import java.util.ArrayList;
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

import com.code2java.service.StateIncomeTaxServiceImpl;
import com.code2java.service.StateTaxServiceImpl;
import com.google.gson.Gson;

@Controller
public class StateIncomeTaxController {
	
	@Autowired
	private StateIncomeTaxServiceImpl stateincometaxservice;
	
	@RequestMapping(value = "/StateIncomeTax")
	public String loadDatatableProperty(Model model)
	{
		model.addAttribute("companyGroupsMap", getStateIncomeTaxCompanyGroups());
		model.addAttribute("instanceMap", getStateIncomeTaxTaxTaxInstances());
		model.addAttribute("yearMap", getStateIncomeTaxYears());
		return "StateIncomeTax";
	} 


	//ServerSide Data	
	@ResponseBody
	@RequestMapping("/loadStateIncomeTaxserverSideData")
	public String loadStateIncomeTaxServerSideData(HttpServletResponse response, HttpServletRequest request)
	{
				
		String jsonResponse = stateincometaxservice.getStateIncomeTaxDataTableResponse(request);
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");

		return jsonResponse;
	}
	
	@RequestMapping("/StateIncomeTaxdownloadExcelData")
	public void downloadExcelData(HttpServletResponse response, HttpServletRequest request) throws IOException {
		System.out.println("Temp path:"+System.getProperty("java.io.tmpdir"));
		
		SXSSFWorkbook workBook = stateincometaxservice.getStateIncomeTaxExcelData(request);

		//Workbook workBook = null;
//		SXSSFWorkbook workBook = null;
//		StateTaxReportHandler StateTaxReportHandler = new StateTaxReportHandler();
//		workBook = StateTaxReportHandler.writeFile(result);
		response.setHeader("Content-Disposition",
				"attachment; filename=" + "StateIncomeTax_Register_Report.xlsx");
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
	
	public Map<String,String> getStateIncomeTaxCompanyGroups(){
		
		Map<String, String> groupsMap = new LinkedHashMap<String, String>();		
		List<String[]> list=stateincometaxservice.getStateIncomeTaxCompanyGroups();
		for(String[] s:list) {
			groupsMap.put(s[0], s[1]);
		}
		return groupsMap;
	}
	
	//state group
	
//	public Map<String,String> getStateNames(){
//		
//		Map<String, String> stateMap = new LinkedHashMap<String, String>();		
//		List<String[]> list=stateincometaxservice.getStateNames();
//		for(String[] s:list) {
//			
//			stateMap.put(s[0], s[0]+" - "+s[1]);
//			//stateMap.put(s[0], s[1]);
//		}
//		return stateMap;
//	}
	
	
	
	
	
	//PropertyTax Instance
	public Map<String,String> getStateIncomeTaxTaxTaxInstances(){
		Map<String, String> instanceMap = new LinkedHashMap<String, String>();
		List<String[]> list=stateincometaxservice.getStateIncomeTaxInstances();	
		for(String[] s:list) {
			instanceMap.put(s[0], s[1]);
		}
		return instanceMap;
	}
	
	//PropertyTax YEAR
	public Map<String,String> getStateIncomeTaxYears(){
		Map<String, String> yearMap = new LinkedHashMap<String, String>();	
		List<String[]> list=stateincometaxservice.getStateIncomeTaxYears();
		for(String[] s:list) {
			yearMap.put(s[0], s[1]);
		}
		return yearMap;
	}
	//PropertyTax GroupCompanies  
	
	@RequestMapping(value = "getStateIncomeTaxCompanyNames", method = RequestMethod.GET)
	public @ResponseBody String getStateIncomeTaxCompanyNames(
	        @RequestParam(value = "groupName", required = true) String groupName) {
		Map<String, String> companyNamesMap = new TreeMap<String, String>();
		List<String[]> list=stateincometaxservice.getStateIncomeTaxCompanyNames(groupName);					
		for(String[] s:list) {
			companyNamesMap.put(s[0], s[0]+" - "+s[1]);			}
		//JSONObject json = new JSONObject(companyNamesMap);
		Gson gson = new Gson();
		String json = gson.toJson(companyNamesMap, TreeMap.class);
		//return json.toString();
		return json;
	
}

	@RequestMapping("/getStateIncomeTaxHeadings")
	public @ResponseBody String getHeadingNames()
	{
		List<String> headings=new ArrayList<>();
		
		List<String[]> headingList=stateincometaxservice.getTableHeadings();
		//System.out.println("wwww DT Controller= "+request.getParameter("reportId"));
		for(String[] h: headingList)
		{
			
			headings.add(h[0]);
		}
		Gson gson=new Gson();
		String jsonH=gson.toJson(headings, ArrayList.class);
		return jsonH;
	}
}
