package com.code2java.service;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;
import java.util.Map;


public interface SAPHistStateTaxService {

	   public String getSAPHistStateTaxDataTableResponse(HttpServletRequest request);
		
		public List<String[]> getSAPHistStateTaxCompanyGroups();
		
		public List<String[]> getSAPHistStateTaxCompanyNames(String COMPANYGROUP);
		
		public SXSSFWorkbook getSAPHistStateTaxExcelData(HttpServletRequest request);
		
		public List<String[]> getSAPHistStateTaxInstances();
		
		public List<String[]> getSAPHistStateTaxYears();
		
		public List<String[]> getSAPHistStateTaxReportNames(List<String> reportList);

		public List<String[]> getSAPHistStateNames();

		List<String[]> getTableHeadings(HttpServletRequest request);
		
	}

	
	
	
	
	

