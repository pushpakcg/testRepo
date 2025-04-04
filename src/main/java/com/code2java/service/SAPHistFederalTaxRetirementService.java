package com.code2java.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface SAPHistFederalTaxRetirementService {
	
	
	
	public String getSAPHistFederalTaxRetirementDataTableResponse(HttpServletRequest request);
	
	public List<String[]> getSAPHistFederalTaxRetirementCompanyGroups();
	
	public List<String[]> getSAPHistFederalTaxRetirementCompanyNames(String COMPANYGROUP);
	
	public SXSSFWorkbook downloadExcelSAPHistFedtaxRetData(HttpServletRequest request);
	
	public List<String[]> getSAPHistFederalTaxRetirementInstances();
	
	public List<String[]> getSAPHistFederalTaxRetirementYears();
	
	public List<String[]> getSAPHistoricalFederaltaxretReportNames(List<String> reportList);

	List<String[]> getTableHeadings(HttpServletRequest request);

}