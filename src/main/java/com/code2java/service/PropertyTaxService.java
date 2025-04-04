package com.code2java.service;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;
import java.util.Map;


public interface PropertyTaxService {

	   public String getPropertyTaxDataTableResponse(HttpServletRequest request);
		
		public List<String[]> getPropertyTaxCompanyGroups();
		
		public List<String[]> getPropertyTaxCompanyNames(String COMPANYGROUP);
		
		public SXSSFWorkbook getPropertyTaxExcelData(HttpServletRequest request);
		
		public List<String[]> getPropertyTaxInstances();
		
		public List<String[]> getPropertyTaxYears();

		List<String[]> getTableHeadings();
		
	}

	
	
	
	
	

