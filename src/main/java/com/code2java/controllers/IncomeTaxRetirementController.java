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
import com.faweb.domain.saml.Response;
import com.google.gson.Gson;
import com.code2java.service.AssetRetirementServiceImpl;

@Controller
public class IncomeTaxRetirementController {
	
	
	@Autowired
	private AssetRetirementServiceImpl assetretirementservice;
	
	@RequestMapping(value = "/taxRetirementReport")
	public String loadDatatableRetirement(Model model)
	{
		model.addAttribute("companyGroupsMap", getAssetRetirementCompanyGroups());
		model.addAttribute("instanceMap", getAssetRetirementInstances());
		model.addAttribute("yearMap", getAssetRetirementYears());
		return "taxRetirement";
		
	}
	
	//ServerSide Data	
		@ResponseBody
		@RequestMapping("/loadAssetRetirementServerSideData")
		public String loadAssetRetirementServerSideData(HttpServletResponse response, HttpServletRequest request)
		{
			String jsonResponse = assetretirementservice.getAssetRetirementDataTableResponse(request);		
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");

			return jsonResponse;
		}
		//AssetRetirement Download Excel
		@RequestMapping("/assetRetirementdownloadExcelData")
		public void assetRetirementdownloadExcelData(HttpServletResponse response, HttpServletRequest request) throws IOException {
			System.out.println("Temp path:"+System.getProperty("java.io.tmpdir"));		
			
			SXSSFWorkbook workBook = assetretirementservice.getAssetRetirementExcelData(request);
			
			//Workbook workBook = null;
			//SXSSFWorkbook workBook = null;
			
		//	AssetRetirementReportsHandler AssetRetirementReportsHandler = new AssetRetirementReportsHandler();
			
			//workBook = AssetRetirementReportsHandler.writeFile(result);
			response.setHeader("Content-Disposition",
					"attachment; filename=" + "AssetRetirment_Reg_Report.xlsx");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.addCookie(new Cookie("downloadStarted", "1"));
			ServletOutputStream outputStream = response.getOutputStream();
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
			workBook.dispose();
			System.out.println("excel file downloaded");
		}
		
		//AssetRetirement CompanyGropus
		
		public Map<String,String> getAssetRetirementCompanyGroups(){
			Map<String, String> groupsMap = new LinkedHashMap<String, String>();		
			List<String[]> list=assetretirementservice.getAssetRetirementCompanyGroups();
			for(String[] s:list) {
				groupsMap.put(s[0], s[1]);
			}
			return groupsMap;
		}
		//AssetRetirement Instance
		public Map<String,String> getAssetRetirementInstances(){
			Map<String, String> instanceMap = new LinkedHashMap<String, String>();
			List<String[]> list=assetretirementservice.getAssetRetirementInstances();		
			for(String[] s:list) {
				instanceMap.put(s[0], s[1]);
			}
			return instanceMap;
		}
		
		//AssetRetirement YEAR
		public Map<String,String> getAssetRetirementYears(){
			Map<String, String> yearMap = new LinkedHashMap<String, String>();	
			List<String[]> list=assetretirementservice.getAssetRetirementYears();
			for(String[] s:list) {
				yearMap.put(s[0], s[1]);
			}
			return yearMap;
		}
		
		//Asset Retirement GroupCompanies
		
		@RequestMapping(value = "getAssetRetirementCompanyNames", method = RequestMethod.GET)
		public @ResponseBody String getAssetRetirementCompanyNames(
		        @RequestParam(value = "groupName", required = true) String groupName) {
			Map<String, String> companyNamesMap = new TreeMap<String, String>();
			List<String[]> list=assetretirementservice.getAssetRetirementCompanyNames(groupName);
			for(String[] s:list) {
				companyNamesMap.put(s[0], s[0]+" - "+s[1]);
			}
			Gson gson = new Gson();
			String json = gson.toJson(companyNamesMap, TreeMap.class);
			//return json.toString();
			return json;
		}
		
	
		@RequestMapping("/getAssetRetirementHeadings")
		public @ResponseBody String getHeadingNames()
		{
			List<String> headings=new ArrayList<>();
			
			List<String[]> headingList=assetretirementservice.getTableHeadings();
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
