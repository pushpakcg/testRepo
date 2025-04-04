package com.code2java.controllers;

import org.springframework.stereotype.Controller;
import java.io.ByteArrayInputStream;
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
import com.code2java.service.StateTaxService;
import com.code2java.service.StateTaxServiceImpl;
import com.faweb.domain.saml.Response;
import com.google.gson.Gson;


@Controller
public class StateTaxController {

	
	
	@Autowired
	private StateTaxServiceImpl statetaxservice;
	
	
	@RequestMapping(value = "/StateTaxReg")
	public String loadDatatableStateTax(Model model)
	{
		model.addAttribute("companyGroupsMap", getStateTaxCompanyGroups());
		model.addAttribute("stateMap", getStateNames());
		model.addAttribute("instanceMap", getStateTaxTaxInstances());
		model.addAttribute("yearMap", getStateTaxYears());
		return "StateTaxReg";
	}	
		
	
		//ServerSide Data	
		@ResponseBody
		@RequestMapping("/loadStateTaxserverSideData")
		public String loadStateTaxServerSideData(HttpServletResponse response, HttpServletRequest request)
		{
					
			String jsonResponse = statetaxservice.getStateTaxDataTableResponse(request);
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");

			return jsonResponse;
		}
		
		@RequestMapping("/StateTaxdownloadExcelData")
		public void downloadExcelData(HttpServletResponse response, HttpServletRequest request) throws IOException {
			System.out.println("Temp path:"+System.getProperty("java.io.tmpdir"));
			
			SXSSFWorkbook workBook = statetaxservice.getStateTaxExcelData(request);

			//Workbook workBook = null;
//			SXSSFWorkbook workBook = null;
//			StateTaxReportHandler StateTaxReportHandler = new StateTaxReportHandler();
//			workBook = StateTaxReportHandler.writeFile(result);
			response.setHeader("Content-Disposition",
					"attachment; filename=" + "StateTax_Register_Report.xlsx");
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
		
		public Map<String,String> getStateTaxCompanyGroups(){
			
			Map<String, String> groupsMap = new LinkedHashMap<String, String>();		
			List<String[]> list=statetaxservice.getStateTaxCompanyGroups();
			for(String[] s:list) {
				groupsMap.put(s[0], s[1]);
			}
			return groupsMap;
		}
		
		//state group
		
		public Map<String,String> getStateNames(){
			
			Map<String, String> stateMap = new LinkedHashMap<String, String>();		
			List<String[]> list=statetaxservice.getStateNames();
			for(String[] s:list) {
				
				stateMap.put(s[0], s[0]+" - "+s[1]);
				//stateMap.put(s[0], s[1]);
			}
			return stateMap;
		}
		
		
		
		
		
		//PropertyTax Instance
		public Map<String,String> getStateTaxTaxInstances(){
			Map<String, String> instanceMap = new LinkedHashMap<String, String>();
			List<String[]> list=statetaxservice.getStateTaxInstances();	
			for(String[] s:list) {
				instanceMap.put(s[0], s[1]);
			}
			return instanceMap;
		}
		
		//PropertyTax YEAR
		public Map<String,String> getStateTaxYears(){
			Map<String, String> yearMap = new LinkedHashMap<String, String>();	
			List<String[]> list=statetaxservice.getStateTaxYears();
			for(String[] s:list) {
				yearMap.put(s[0], s[1]);
			}
			return yearMap;
		}
		//PropertyTax GroupCompanies  
		
		@RequestMapping(value = "getStateTaxCompanyNames", method = RequestMethod.GET)
		public @ResponseBody String getStateTaxCompanyNames(
		        @RequestParam(value = "groupName", required = true) String groupName) {
			Map<String, String> companyNamesMap = new TreeMap<String, String>();
			List<String[]> list=statetaxservice.getStateTaxCompanyNames(groupName);					
			for(String[] s:list) {
				companyNamesMap.put(s[0], s[0]+" - "+s[1]);			}
			//JSONObject json = new JSONObject(companyNamesMap);
			Gson gson = new Gson();
			String json = gson.toJson(companyNamesMap, TreeMap.class);
			//return json.toString();
			return json;
		
	}
	
		@RequestMapping("/getStateTaxHeadings")
		public @ResponseBody String getHeadingNames()
		{
			List<String> headings=new ArrayList<>();
			
			List<String[]> headingList=statetaxservice.getTableHeadings();
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
