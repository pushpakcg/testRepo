package com.code2java.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface HistIncomeTaxRetService {

	public String getHistRetDataTableResponse(HttpServletRequest request);

	public List<String[]> getCompanyGroups();

	public List<String[]> getHistRetCompanyNames(String COMPANYGROUP);

	public SXSSFWorkbook getHistRetExcelData(HttpServletRequest request);

	public List<String[]> getInstances();

	public List<String[]> getYears();
	
	public List<String[]> getHistRetReportNames(List<String> reportList);
	
	
	

	
}