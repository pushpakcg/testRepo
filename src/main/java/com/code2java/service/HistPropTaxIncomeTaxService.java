package com.code2java.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface HistPropTaxIncomeTaxService {

	public String getHistPropTaxDataTableResponse(HttpServletRequest request);

	public List<String[]> getHistPropTaxCompanyGroups();

	public List<String[]> getHistPropTaxCompanyNames(String COMPANYGROUP);

	public SXSSFWorkbook getHistPropTaxExcelData(HttpServletRequest request);

	public List<String[]> getHistPropTaxInstances();

	public List<String[]> getHistPropTaxYears();
	
	public List<String[]> getHistPropTaxReportNames(List<String> reportList);
	
	//public List<String[]> getLoadReportNames();
	
	
	

	// CSV files process related - starts

	

}