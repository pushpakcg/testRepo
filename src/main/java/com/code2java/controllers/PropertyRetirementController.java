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
import com.code2java.service.PropertyTaxRetirementServiceImpl;
import com.code2java.service.PropertyTaxRetirementService;
import com.faweb.domain.saml.Response;
import com.google.gson.Gson;

@Controller
public class PropertyRetirementController {
	
	@Autowired
	private PropertyTaxRetirementServiceImpl propertytaxretirementService;
	
	
	@RequestMapping(value = "/PropTaxRetirement")
	public String loadDatatableProperty(Model model)
	{
		model.addAttribute("companyGroupsMap", getPropertyRetirementCompanyGroups());
		model.addAttribute("instanceMap", getPropertyRetirementInstances());
		model.addAttribute("yearMap", getPropertyRetirementYears());
		return "PropTaxRetirement";
	}	
	@ResponseBody
	@RequestMapping("/loadPropertyRetirementserverSideData")
	public String loadPropertyRetirementServerSideData(HttpServletResponse response, HttpServletRequest request)
	{
			
		String jsonResponse = propertytaxretirementService.getPropertyRetirementDataTableResponse(request);
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");

		return jsonResponse;
	}
	
	@RequestMapping("/PropertyRetirementdownloadExcelData")
	public void downloadExcelData(HttpServletResponse response, HttpServletRequest request) throws IOException {
		System.out.println("Temp path:"+System.getProperty("java.io.tmpdir"));
		
		SXSSFWorkbook workBook = propertytaxretirementService.getPropertyRetirementExcelData(request);		
		//Workbook workBook = null;
//		SXSSFWorkbook workBook = null;
//		PropertyRetirementReportHandler PropertyRetirementReportHandler = new PropertyRetirementReportHandler();
//		workBook = PropertyRetirementReportHandler.writeFile(result);
		response.setHeader("Content-Disposition",
				"attachment; filename=" + "PropertyTax_Retirement_Report.xlsx");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.addCookie(new Cookie("downloadStarted", "1"));
		ServletOutputStream outputStream = response.getOutputStream();
		workBook.write(outputStream);
		outputStream.flush();
		outputStream.close();
		workBook.dispose();
		System.out.println("excel file downloaded");
	}
	
	
			public Map<String,String> getPropertyRetirementCompanyGroups(){
				Map<String, String> groupsMap = new LinkedHashMap<String, String>();		
				List<String[]> list=propertytaxretirementService.getPropertyRetirementCompanyGroups();
				for(String[] s:list) {
					groupsMap.put(s[0], s[1]);
				}
				return groupsMap;
			}
			
			
			public Map<String,String> getPropertyRetirementInstances(){
				Map<String, String> instanceMap = new LinkedHashMap<String, String>();
				List<String[]> list=propertytaxretirementService.getPropertyRetirementInstances();	
				for(String[] s:list) {
					instanceMap.put(s[0], s[1]);
				}
				return instanceMap;
			}
			
			
			public Map<String,String> getPropertyRetirementYears(){
				Map<String, String> yearMap = new LinkedHashMap<String, String>();	
				List<String[]> list=propertytaxretirementService.getPropertyRetirementYears();
				for(String[] s:list) {
					yearMap.put(s[0], s[1]);
				}
				return yearMap;
			}
			
			@RequestMapping(value = "getPropertyTaxRetirementCompanyNames", method = RequestMethod.GET)
			public @ResponseBody String getPropertyRetirementCompanyNames(
			        @RequestParam(value = "groupName", required = true) String groupName) {
				Map<String, String> companyNamesMap = new TreeMap<String, String>();
				List<String[]> list=propertytaxretirementService.getPropertyTaxRetirementCompanyNames(groupName);					
				for(String[] s:list) {
					companyNamesMap.put(s[0], s[0]+" - "+s[1]);			}
				Gson gson = new Gson();
				String json = gson.toJson(companyNamesMap, TreeMap.class);
				//return json.toString();
				return json;
			
		}
		

			@RequestMapping("/getPropertyTaxRetirementHeadings")
			public @ResponseBody String getHeadingNames()
			{
				List<String> headings=new ArrayList<>();
				
				List<String[]> headingList=propertytaxretirementService.getTableHeadings();
				//System.out.println("wwww DT Controller= "+request.getParameter("reportId"));
				for(String[] h: headingList)
				{
					//System.out.println("DT Refresh--"+h[0]);
					headings.add(h[0]);
				}
				Gson gson=new Gson();
				String jsonH=gson.toJson(headings, ArrayList.class);
				return jsonH;
			}


}
