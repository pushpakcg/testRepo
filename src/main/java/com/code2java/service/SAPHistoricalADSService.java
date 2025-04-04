package com.code2java.service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface SAPHistoricalADSService {
	
	
	 public String getSAPHistoricalADSDataTableResponse(HttpServletRequest request);
		
		public List<String[]> getSAPHistoricalADSCompanyGroups();
		
		public List<String[]> getSAPHistoricalADSCompanyNames(String COMPANYGROUP);
		
		public SXSSFWorkbook getSAPHistoricalADSExcelData(HttpServletRequest request);
		
		public List<String[]> getSAPHistoricalADSInstances();
		
		public List<String[]> getSAPHistoricalADSYears();
		
		public List<String[]> getSAPHistoricalADSReportNames(List<String> reportList);

		List<String[]> getTableHeadings(HttpServletRequest request);
		

}
