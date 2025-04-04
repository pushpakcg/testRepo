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

import com.code2java.service.AssetTransferProd2ServiceImpl;
import com.code2java.service.AssetTransferProdServiceImpl;
import com.google.gson.Gson;


@Controller
public class AssetTransferProd2Controller {

	
	
	@Autowired
	private AssetTransferProd2ServiceImpl assetTransferService2;
	
	
	@RequestMapping(value = "/AssetTransferProd2")
	public String loadDatatableAssetTranfer2(Model model)
	{
		model.addAttribute("companyGroupsMap", getAssetTransferProd2CompanyGroups());
		model.addAttribute("instanceMap", getAssetTransferProd2Instances());
		model.addAttribute("yearMap", getAssetTransferProd2Years());
		return "AssetTransferProd2";
	}	
		
		
		//ServerSide Data	
		@ResponseBody
		@RequestMapping("/loadAssetTransferProd2serverSideData")
		public String loadAssetTransferProd2ServerSideData(HttpServletResponse response, HttpServletRequest request)
		{
			//String jsonResponse = assetretirementservice.getAssetRetirementDataTableResponse(request);		
			String jsonResponse = assetTransferService2.getAssetTransferProd2DataTableResponse(request);
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");

			return jsonResponse;
		}
		
		@RequestMapping("/AssetTransferProd2downloadExcelData")
		public void AssetTransferProd2downloadExcelData(HttpServletResponse response, HttpServletRequest request) throws IOException {
			System.out.println("Temp path:"+System.getProperty("java.io.tmpdir"));
			
			SXSSFWorkbook workBook = assetTransferService2.getAssetTransferProd2ExcelData(request);			
			//Workbook workBook = null;
//			SXSSFWorkbook workBook = null;
//			PropertyReportHandler PropertyReportHandler = new PropertyReportHandler();
//			workBook = PropertyReportHandler.writeFile(result);
			response.setHeader("Content-Disposition",
					"attachment; filename=" + "Asset_Transfer_All_Transactions_Report.xlsx");
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
		
		public Map<String,String> getAssetTransferProd2CompanyGroups(){
			Map<String, String> groupsMap = new LinkedHashMap<String, String>();		
			List<String[]> list=assetTransferService2.getAssetTransferProd2CompanyGroups();
			for(String[] s:list) {
				groupsMap.put(s[0], s[1]);
			}
			return groupsMap;
		}
		//PropertyTax Instance
		public Map<String,String> getAssetTransferProd2Instances(){
			Map<String, String> instanceMap = new LinkedHashMap<String, String>();
			List<String[]> list=assetTransferService2.getAssetTransferProd2Instances();	
			for(String[] s:list) {
				instanceMap.put(s[0], s[1]);
			}
			return instanceMap;
		}
		
		//PropertyTax YEAR
		public Map<String,String> getAssetTransferProd2Years(){
			Map<String, String> yearMap = new LinkedHashMap<String, String>();	
			List<String[]> list=assetTransferService2.getAssetTransferProd2Years();
			for(String[] s:list) {
				yearMap.put(s[0], s[1]);
			}
			return yearMap;
		}
		//PropertyTax GroupCompanies  
		
		@RequestMapping(value = "getAssetTransferProd2CompanyNames", method = RequestMethod.GET)
		public @ResponseBody String getAssetTransferProd2CompanyNames(
		        @RequestParam(value = "groupName", required = true) String groupName) {
			Map<String, String> companyNamesMap = new TreeMap<String, String>();
			List<String[]> list=assetTransferService2.getAssetTransferProd2CompanyNames(groupName);					
			for(String[] s:list) {
				companyNamesMap.put(s[0], s[0]+" - "+s[1]);			}
			Gson gson = new Gson();
			String json = gson.toJson(companyNamesMap, TreeMap.class);
			//return json.toString();
			return json;
		
	}
		
		@RequestMapping("/getAssetTransferProd2Headings")
		public @ResponseBody String getHeadingNames()
		{
			List<String> headings=new ArrayList<>();
			List<String[]> headingList=assetTransferService2.getTableHeadings();
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
