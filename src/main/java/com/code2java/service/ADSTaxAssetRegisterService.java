package com.code2java.service;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.Map;
import java.util.List;



public interface ADSTaxAssetRegisterService {

	
	  public String getADSTaxAssetRegisterDataTableResponse(HttpServletRequest request);
	  
	  public List<String[]> getADSAssetCompanyGroups();
	  
	  public List<String[]> getADSTaxAssetRegistercompanyNames(String COMPANYGROUP);
	  
	  public SXSSFWorkbook getADSTaxAssetRegisterdownloadExcelData(HttpServletRequest request);
	  
	  public List<String[]> getADSAssetInstances();
	  
	  public List<String[]> getADSAssetYears();
	 
	  public List<String[]> getTableHeadings();
	
	
}
