package com.code2java.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface StateIncomeTaxService {
	
	public void updateStateIncomeTax();
	
	public String getStateIncomeTaxDataTableResponse(HttpServletRequest request);
	
	public List<String[]> getStateIncomeTaxCompanyGroups();
	
	public List<String[]> getStateIncomeTaxCompanyNames(String COMPANYGROUP);
	
	 public SXSSFWorkbook getStateIncomeTaxExcelData(HttpServletRequest request);
	
	public List<String[]> getStateIncomeTaxInstances();
	
	public List<String[]> getStateIncomeTaxYears();

	List<String[]> getTableHeadings();

}
