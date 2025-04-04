package com.code2java.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface IDTService {

	public String getDataTableResponse(HttpServletRequest request);

	public List<String[]> getCompanyGroups();

	public List<String[]> getCompanyNames(String COMPANYGROUP);

	public SXSSFWorkbook getExcelData(HttpServletRequest request);

	public List<String[]> getInstances();

	public List<String[]> getYears();

	// CSV files process related - starts

	List<Map<String, Object>> getInterfaceFilesControl() throws SQLException;

	int saveBatchRecords(int totalrecords, String targetInterimTable, List<Object[]> objectList);

	//int deleteRecords(String tableName) throws SQLException;

	int saveStatusControlTable(Object[] args);
	
	public int saveToRegisterTables();
	
	public Integer getRunCount();
	
	public int clearReportsTables(String ssoid);
	
	public List<String[]> getTableHeadings();

}