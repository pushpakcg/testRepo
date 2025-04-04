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
import com.code2java.service.PropertyTaxService;
import com.code2java.service.PropertyTaxServiceImpl;
import com.faweb.domain.saml.Response;
import com.google.gson.Gson;


@Controller
public class PropertyController {

	
	
	@Autowired
	private PropertyTaxServiceImpl propertytaxservice;
	
	
	@RequestMapping(value = "/PropTaxReg")
	public String loadDatatableProperty(Model model)
	{
		model.addAttribute("companyGroupsMap", getPropertyTaxCompanyGroups());
		model.addAttribute("instanceMap", getPropertyTaxInstances());
		model.addAttribute("yearMap", getPropertyTaxYears());
		return "PropTaxReg";
	}	
		
		
		//ServerSide Data	
		@ResponseBody
		@RequestMapping("/loadPropertyTaxserverSideData")
		public String loadPropertyTaxServerSideData(HttpServletResponse response, HttpServletRequest request)
		{
			//String jsonResponse = assetretirementservice.getAssetRetirementDataTableResponse(request);		
			String jsonResponse = propertytaxservice.getPropertyTaxDataTableResponse(request);
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");

			return jsonResponse;
		}
		
		@RequestMapping("/PropertyTaxdownloadExcelData")
		public void downloadExcelData(HttpServletResponse response, HttpServletRequest request) throws IOException {
			System.out.println("Temp path:"+System.getProperty("java.io.tmpdir"));
			
			SXSSFWorkbook workBook = propertytaxservice.getPropertyTaxExcelData(request);			
			//Workbook workBook = null;
//			SXSSFWorkbook workBook = null;
//			PropertyReportHandler PropertyReportHandler = new PropertyReportHandler();
//			workBook = PropertyReportHandler.writeFile(result);
			response.setHeader("Content-Disposition",
					"attachment; filename=" + "PropertyTax_Register_Report.xlsx");
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
		
		public Map<String,String> getPropertyTaxCompanyGroups(){
			Map<String, String> groupsMap = new LinkedHashMap<String, String>();		
			List<String[]> list=propertytaxservice.getPropertyTaxCompanyGroups();
			for(String[] s:list) {
				groupsMap.put(s[0], s[1]);
			}
			return groupsMap;
		}
		//PropertyTax Instance
		public Map<String,String> getPropertyTaxInstances(){
			Map<String, String> instanceMap = new LinkedHashMap<String, String>();
			List<String[]> list=propertytaxservice.getPropertyTaxInstances();	
			for(String[] s:list) {
				instanceMap.put(s[0], s[1]);
			}
			return instanceMap;
		}
		
		//PropertyTax YEAR
		public Map<String,String> getPropertyTaxYears(){
			Map<String, String> yearMap = new LinkedHashMap<String, String>();	
			List<String[]> list=propertytaxservice.getPropertyTaxYears();
			for(String[] s:list) {
				yearMap.put(s[0], s[1]);
			}
			return yearMap;
		}
		//PropertyTax GroupCompanies  
		
		@RequestMapping(value = "getPropertyTaxCompanyNames", method = RequestMethod.GET)
		public @ResponseBody String getPropertyTaxCompanyNames(
		        @RequestParam(value = "groupName", required = true) String groupName) {
			Map<String, String> companyNamesMap = new TreeMap<String, String>();
			List<String[]> list=propertytaxservice.getPropertyTaxCompanyNames(groupName);					
			for(String[] s:list) {
				companyNamesMap.put(s[0], s[0]+" - "+s[1]);			}
			Gson gson = new Gson();
			String json = gson.toJson(companyNamesMap, TreeMap.class);
			//return json.toString();
			return json;
		
	}
		
		@RequestMapping("/getPropertyTaxAssetRegHeadings")
		public @ResponseBody String getHeadingNames()
		{
			List<String> headings=new ArrayList<>();
			List<String[]> headingList=propertytaxservice.getTableHeadings();
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
