package com.code2java.service;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;
import java.util.Map;


public interface AssetTransferProdService {

	   public String getAssetTransferProdDataTableResponse(HttpServletRequest request);
		
		public List<String[]> getAssetTransferProdCompanyGroups();
		
		public List<String[]> getAssetTransferProdCompanyNames(String COMPANYGROUP);
		
		public SXSSFWorkbook getAssetTransferProdExcelData(HttpServletRequest request);
		
		public List<String[]> getAssetTransferProdInstances();
		
		public List<String[]> getAssetTransferProdYears();

		List<String[]> getTableHeadings();
		
	}

	
	
	
	
	

