package com.code2java.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;

import com.code2java.model.FMVModel;

@Component
public interface FMVService {
	public List<String> getYears(); 
	
	public FMVModel getModelValues(String year);

	public String updateModel(FMVModel fmvModel);

	public String insertModel(FMVModel fmvModel );
	
	public SXSSFWorkbook excelExport();
}
