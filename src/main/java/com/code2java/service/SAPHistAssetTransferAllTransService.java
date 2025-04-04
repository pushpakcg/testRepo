package com.code2java.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface SAPHistAssetTransferAllTransService {
	
	 public String getSAPHistAssetTransferAllTransDataTableResponse(HttpServletRequest request);
		
		public List<String[]> getSAPHistAssetTransferAllTransCompanyGroups();
		
		public List<String[]> getSAPHistAssetTransferAllTransCompanyNames(String COMPANYGROUP);
		
		public SXSSFWorkbook getSAPHistAssetTransferAllTransExcelData(HttpServletRequest request);
		
		//public List<String[]> getSAPHistAssetTransferInstances();
		
		public List<String[]> getSAPHistAssetTransferAllTransYears();
		
		public List<String[]> getSAPHistAssetTransferAllTransReportNames(List<String> reportList);

		List<String[]> getTableHeadings(HttpServletRequest request);

}
