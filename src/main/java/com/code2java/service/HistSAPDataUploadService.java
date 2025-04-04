package com.code2java.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.code2java.model.UploadFile;

//import com.code2java.model.UploadFile;

public interface HistSAPDataUploadService {
	
	public List<String[]> getYears();

	public List<String[]> getSAPDataUploadReportNames();

	public String getSAPSSOId(String loginSSO);

	public String saveSAP1TableData(HttpServletRequest request);

	public List<String> getSAPTableData(UploadFile fileupload);

}