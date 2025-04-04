package com.code2java.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface SAPHistAssetTransferService {
	
	 public String getSAPHistAssetTransferDataTableResponse(HttpServletRequest request);
		
		public List<String[]> getSAPHistAssetTransferCompanyGroups();
		
		public List<String[]> getSAPHistAssetTransferCompanyNames(String COMPANYGROUP);
		
		public SXSSFWorkbook getSAPHistAssetTransferExcelData(HttpServletRequest request);
		
		//public List<String[]> getSAPHistAssetTransferInstances();
		
		public List<String[]> getSAPHistAssetTransferYears();
		
		public List<String[]> getSAPHistAssetTransferReportNames(List<String> reportList);

		List<String[]> getTableHeadings(HttpServletRequest request);

}
