package com.code2java.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface HistPropTaxRetirementIncomeTaxService {

	public String getHistPropTaxRetirementDataTableResponse(HttpServletRequest request);

	public List<String[]> getHistPropTaxRetirementCompanyGroups();

	public List<String[]> getHistPropTaxRetirementCompanyNames(String COMPANYGROUP);

	public SXSSFWorkbook getHistPropTaxRetirementExcelData(HttpServletRequest request);

	public List<String[]> getHistPropTaxRetirementInstances();

	public List<String[]> getHistPropTaxRetirementYears();
	
	public List<String[]> getHistPropTaxRetirementReportNames(List<String> reportList);
	
	//public List<String[]> getLoadReportNames();
	
	
	

	// CSV files process related - starts

	

}