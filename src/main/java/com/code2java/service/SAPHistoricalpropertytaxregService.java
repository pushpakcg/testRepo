package com.code2java.service;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;
import java.util.Map;


public interface SAPHistoricalpropertytaxregService {

	   public String getSAPHistoricalPropertyTaxDataTableResponse(HttpServletRequest request);
		
		public List<String[]> getSAPHistoricalPropertyTaxCompanyGroups();
		
		public List<String[]> getSAPHistoricalPropertyTaxCompanyNames(String COMPANYGROUP);
		
		public SXSSFWorkbook getSAPHistoricalPropertyTaxExcelData(HttpServletRequest request);
		
		public List<String[]> getSAPHistoricalPropertyTaxInstances();
		
		public List<String[]> getSAPHistoricalPropertyTaxYears();
		
		public List<String[]> getSAPHistoricalPropTaxReportNames(List<String> reportList);
		
		public List<String[]> getTableHeadings(HttpServletRequest request);
		
	}

	
	
	
	
	

