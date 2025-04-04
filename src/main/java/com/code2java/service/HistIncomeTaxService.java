package com.code2java.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface HistIncomeTaxService {

	public String getDataTableResponse(HttpServletRequest request);

	public List<String[]> getCompanyGroups();

	public List<String[]> getCompanyNames(String COMPANYGROUP);

	public SXSSFWorkbook getExcelData(HttpServletRequest request);

	public List<String[]> getInstances();

	public List<String[]> getYears();
	
	public List<String[]> getReportNames(List<String> reportList);
	
	//public List<String[]> getLoadReportNames();
	
	
	

	
}