package com.code2java.service;

import java.util.List;

import com.code2java.model.UploadFile;



public interface HistFileUploadService {
	
	public List<String> getTableData(UploadFile file);

	public List<String[]> getYears();

	public List<String[]> getUploadFileReportNames(String reportyear);

	public String getSSOId(String loginSSO);
	
	public int saveBatchRecords(int totalrecords, String sql, List<Object[]> objectList);

}
