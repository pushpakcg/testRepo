package com.code2java.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface SAPHistPropTaxRetirementTaxService {

	public String getSAPHistPropTaxRetirementDataTableResponse(HttpServletRequest request);

	public List<String[]> getSAPHistPropTaxRetirementCompanyGroups();

	public List<String[]> getSAPHistPropTaxRetirementCompanyNames(String COMPANYGROUP);

	public SXSSFWorkbook getSAPHistPropTaxRetirementExcelData(HttpServletRequest request);

	public List<String[]> getSAPHistPropTaxRetirementInstances();

	public List<String[]> getSAPHistPropTaxRetirementYears();
	
	public List<String[]> getSAPHistPropTaxRetirementReportNames(List<String> reportList);

	List<String[]> getTableHeadings(HttpServletRequest request);
	
	//public List<String[]> getLoadReportNames();
	
	
	

	// CSV files process related - starts

	

}