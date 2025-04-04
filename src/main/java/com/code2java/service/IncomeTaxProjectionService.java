package com.code2java.service;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;
import java.util.Map;


public interface IncomeTaxProjectionService {

	   public String getIncomeTaxProjectionDataTableResponse(HttpServletRequest request);
		
		public List<String[]> getIncomeTaxProjectionCompanyGroups();
		
		public List<String[]> getIncomeTaxProjectionCompanyNames(String COMPANYGROUP);
		
		 public SXSSFWorkbook getIncomeTaxProjectionExcelData(HttpServletRequest request);
		
		public List<String[]> getIncomeTaxProjectionInstances();
		
		public List<String[]> getIncomeTaxProjectionYears();

		List<String[]> getTableHeadings();
		
	}

	
	
	
	
	

