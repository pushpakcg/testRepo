package com.code2java.service;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;
import java.util.Map;


public interface SAPHistoricalProjectionregService {

	   public String getSAPHistoricalProjectionDataTableResponse(HttpServletRequest request);
		
		public List<String[]> getSAPHistoricalProjectionCompanyGroups();
		
		public List<String[]> getSAPHistoricalProjectionCompanyNames(String COMPANYGROUP);
		
		public SXSSFWorkbook getSAPHistoricalProjectionExcelData(HttpServletRequest request);
		
		public List<String[]> getSAPHistoricalProjectioinInstances();
		
		public List<String[]> getSAPHistoricalProjectionYears();
		
		public List<String[]> getSAPHistoricalProjectionReportNames(List<String> reportList);
		
		public List<String[]> getTableHeadings(HttpServletRequest request);
		
	}

	
	
	
	
	

