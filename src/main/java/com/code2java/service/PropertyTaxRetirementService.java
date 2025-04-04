package com.code2java.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface PropertyTaxRetirementService {
	
	public String getPropertyRetirementDataTableResponse(HttpServletRequest request);

	public List<String[]> getPropertyRetirementCompanyGroups();

	public List<String[]> getPropertyTaxRetirementCompanyNames(String COMPANYGROUP);

	public SXSSFWorkbook getPropertyRetirementExcelData(HttpServletRequest request);

	public List<String[]> getPropertyRetirementInstances();

	public List<String[]> getPropertyRetirementYears();

	public List<String[]> getTableHeadings();
}
