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
import com.code2java.service.IncomeTaxProjectionService;
import com.code2java.service.IncomeTaxProjectionServiceImpl;
import com.faweb.domain.saml.Response;
import com.google.gson.Gson;


@Controller
public class IncomeTaxProjectionController {

	
	
	@Autowired
	private IncomeTaxProjectionServiceImpl incometaxprojservice;
	
	
	@RequestMapping(value = "/IncomeTaxProjectionReg")
	public String loadDatatableProperty(Model model)
	{
		model.addAttribute("companyGroupsMap", getIncomeTaxProjectionCompanyGroups());
		model.addAttribute("instanceMap", getIncomeTaxProjectionInstances());
		model.addAttribute("yearMap", getIncomeTaxProjectionYears());
		return "IncomeTaxProjectionReg";
	}	
				
		//ServerSide Data	
		@ResponseBody
		@RequestMapping("/loadIncomeTaxProjectionserverSideData")
		public String loadIncomeTaxProjectionServerSideData(HttpServletResponse response, HttpServletRequest request)
		{
					
			String jsonResponse = incometaxprojservice.getIncomeTaxProjectionDataTableResponse(request);
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");

			return jsonResponse;
		}
		
		@RequestMapping("/IncomeTaxProjectiondownloadExcelData")
		public void downloadExcelData(HttpServletResponse response, HttpServletRequest request) throws IOException {
			System.out.println("Temp path:"+System.getProperty("java.io.tmpdir"));
			
			SXSSFWorkbook workBook = incometaxprojservice.getIncomeTaxProjectionExcelData(request);
			
			//Workbook workBook = null;
			//SXSSFWorkbook workBook = null;
			//IncomeTaxProjectionReportHandler inctaxprojreporthandler = new IncomeTaxProjectionReportHandler();
			//workBook = inctaxprojreporthandler.writeFile(result);
			response.setHeader("Content-Disposition",
					"attachment; filename=" + "IncomeTax_Projection_Report.xlsx");
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
		
		public Map<String,String> getIncomeTaxProjectionCompanyGroups(){
			Map<String, String> groupsMap = new LinkedHashMap<String, String>();		
			List<String[]> list=incometaxprojservice.getIncomeTaxProjectionCompanyGroups();
			for(String[] s:list) {
				groupsMap.put(s[0], s[1]);
			}
			return groupsMap;
		}
		//PropertyTax Instance
		public Map<String,String> getIncomeTaxProjectionInstances(){
			Map<String, String> instanceMap = new LinkedHashMap<String, String>();
			List<String[]> list=incometaxprojservice.getIncomeTaxProjectionInstances();	
			for(String[] s:list) {
				instanceMap.put(s[0], s[1]);
			}
			return instanceMap;
		}
		
		//PropertyTax YEAR
		public Map<String,String> getIncomeTaxProjectionYears(){
			Map<String, String> yearMap = new LinkedHashMap<String, String>();	
			List<String[]> list=incometaxprojservice.getIncomeTaxProjectionYears();
			for(String[] s:list) {
				yearMap.put(s[0], s[1]);
			}
			return yearMap;
		}
		//PropertyTax GroupCompanies  
		
		@RequestMapping(value = "getIncomeTaxProjectionCompanyNames", method = RequestMethod.GET)
		public @ResponseBody String getIncomeTaxProjectionCompanyNames(
		        @RequestParam(value = "groupName", required = true) String groupName) {
			Map<String, String> companyNamesMap = new TreeMap<String, String>();
			List<String[]> list=incometaxprojservice.getIncomeTaxProjectionCompanyNames(groupName);					
			for(String[] s:list) {
				companyNamesMap.put(s[0], s[0]+" - "+s[1]);			}
			Gson gson = new Gson();
			String json = gson.toJson(companyNamesMap, TreeMap.class);
			//return json.toString();
			return json;
		
	}
		
		@RequestMapping("/getIncomeTaxProjectionHeadings")
		public @ResponseBody String getHeadingNames()
		{
			List<String> headings=new ArrayList<>();
			List<String[]> headingList=incometaxprojservice.getTableHeadings();
			for(String[] h: headingList)
			{
				//System.out.println("getIncomeTaxProjectionHeadings--"+h[0]);
				headings.add(h[0]);
			}
			Gson gson=new Gson();
			String jsonH=gson.toJson(headings, ArrayList.class);
			return jsonH;
		}
	
	
}
