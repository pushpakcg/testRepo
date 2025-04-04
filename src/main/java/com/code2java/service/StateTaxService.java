package com.code2java.service;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;
import java.util.Map;


public interface StateTaxService {

	   public String getStateTaxDataTableResponse(HttpServletRequest request);
		
		public List<String[]> getStateTaxCompanyGroups();
		
		public List<String[]> getStateTaxCompanyNames(String COMPANYGROUP);
		
		public SXSSFWorkbook getStateTaxExcelData(HttpServletRequest request);
		
		public List<String[]> getStateTaxInstances();
		
		public List<String[]> getStateTaxYears();
		
		public List<String[]> getTableHeadings();
		
	}

	
	
	
	
	

