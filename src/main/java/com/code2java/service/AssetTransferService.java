package com.code2java.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Service
public interface AssetTransferService {
	
	public List<String[]> getTableNameAndCount();

	public int saveBatchRecords(int size, String saveSql, List<Object[]> objectList);
	
	public String uploadFile(CommonsMultipartFile file, Model model, String assetVal, String assetTxt) throws IOException;

}
