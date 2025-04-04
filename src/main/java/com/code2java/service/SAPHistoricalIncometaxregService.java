package com.code2java.service;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;
import java.util.Map;


public interface SAPHistoricalIncometaxregService {

	   public String getSAPHistoricalIncometaxDataTableResponse(HttpServletRequest request);
		
		public List<String[]> getSAPHistoricalIncometaxCompanyGroups();
		
		public List<String[]> getSAPHistoricalIncometaxCompanyNames(String COMPANYGROUP);
		
		public SXSSFWorkbook getSAPHistoricalIncometaxExcelData(HttpServletRequest request);
		
		public List<String[]> getSAPHistoricalIncometaxInstances();
		
		public List<String[]> getSAPHistoricalIncometaxYears();
		
		public List<String[]> getSAPHistoricalIncometaxReportNames(List<String> reportList);
		
		public List<String[]> getTableHeadings(HttpServletRequest request);
		
		//public String callProcedure(String reportTableName);
		
	}

	
	
	
	
	

