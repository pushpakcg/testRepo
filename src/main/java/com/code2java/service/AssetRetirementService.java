package com.code2java.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface AssetRetirementService {
	
	
	
	public String getAssetRetirementDataTableResponse(HttpServletRequest request);
	
	public List<String[]> getAssetRetirementCompanyGroups();
	
	public List<String[]> getAssetRetirementCompanyNames(String COMPANYGROUP);
	
	public SXSSFWorkbook getAssetRetirementExcelData(HttpServletRequest request);
	
	public List<String[]> getAssetRetirementInstances();
	
	public List<String[]> getAssetRetirementYears();
	
	public List<String[]> getTableHeadings();

}