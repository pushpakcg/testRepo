package com.code2java.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;



public interface AssetRegisterSummaryReportService {

	
	  public String getAssetRegisterSummaryDataTableResponse(HttpServletRequest request);
	  
	  public List<String[]> getAssetRegisterSummaryCompanyGroups();
	  
	  public List<String[]> getAssetRegisterSummarycompanyNames(String COMPANYGROUP);
	  
	  public SXSSFWorkbook getAssetRegisterSummarydownloadExcelData(HttpServletRequest request);
	  
	  public List<String[]> getAssetRegisterSummaryInstances();
	  
	  public List<String[]> getAssetRegisterSummaryYears();
	 
//	  public List<String[]> getTableHeadings();
	
	
}
