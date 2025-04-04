package com.code2java.service;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;
import java.util.Map;


public interface AssetTransferProd2Service {

	   public String getAssetTransferProd2DataTableResponse(HttpServletRequest request);
		
		public List<String[]> getAssetTransferProd2CompanyGroups();
		
		public List<String[]> getAssetTransferProd2CompanyNames(String COMPANYGROUP);
		
		public SXSSFWorkbook getAssetTransferProd2ExcelData(HttpServletRequest request);
		
		public List<String[]> getAssetTransferProd2Instances();
		
		public List<String[]> getAssetTransferProd2Years();

		List<String[]> getTableHeadings();
		
	}

	
	
	
	
	

