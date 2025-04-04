package com.code2java.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.code2java.dao.DataTableDAO;
import com.code2java.model.UploadFile;
import com.code2java.service.AssetTransferService;
import com.google.gson.Gson;

@Controller
public class AssetTransfer {
	
	@Autowired
	AssetTransferService assetTransferService;
	
	@Autowired
	private DataTableDAO dataTableDao;
	
	@RequestMapping(value="/AssetTransfer",method=RequestMethod.GET)
	public String assetTransfer(ModelMap modelMap)
	{
		List<String[]> assetTransfer=assetTransferService.getTableNameAndCount();
		Map<String, String> assetTransferMap = new LinkedHashMap<String, String>();
		for (String[] asset : assetTransfer) {
			assetTransferMap.put(asset[0], asset[1]);

		}
		
		modelMap.addAttribute("tableNameAndCountList", assetTransferMap);
		return "assetTransfer";
	}
	
	@RequestMapping(value = "/uploadAssetTransferFiles", method = RequestMethod.POST)
	public String uploadDoc(@RequestParam CommonsMultipartFile file, Model model, @RequestParam("assetVal") String assetVal, @RequestParam("assetTxt") String assetTxt) throws IOException {
		System.out.println(assetTxt);
      
		String statusMsg=assetTransferService.uploadFile(file, model, StringEscapeUtils.escapeHtml4(assetVal), StringEscapeUtils.escapeHtml4(assetTxt));
		model.addAttribute("statusMsg",statusMsg);
		return "assetTransfer";

	}

}
