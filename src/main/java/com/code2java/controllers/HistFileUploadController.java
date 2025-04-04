package com.code2java.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.code2java.dao.DataTableDAO;
import com.code2java.model.UploadFile;
import com.code2java.service.HistFileUploadService;
import com.google.gson.Gson;

@Controller
public class HistFileUploadController {
	
	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(HistFileUploadController.class);

	@Autowired
	private HistFileUploadService histUploadFileService;

	@Autowired
	private DataTableDAO dataTableDao;

	/*
	 * private static final Map<String, String> reportTableMap = prepareMap();
	 * 
	 * private static Map<String, String> prepareMap() { Map<String, String> hashMap
	 * = new HashMap<>(); hashMap.put("ASTRET",
	 * "INSERT INTO FAWEB.HIST_LEGACY_INCTAX_RET VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
	 * ); hashMap.put("PSTRET",
	 * "INSERT INTO FAWEB.HIST_LEGACY_PROPTAX_REG_PRRET VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
	 * ); hashMap.put("ASTREG",
	 * "INSERT INTO FAWEB.HIST_LEGACY_INCTAX_REGADD VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
	 * ); hashMap.put("PSTREG",
	 * "INSERT INTO FAWEB.HIST_LEGACY_PROPTAX_REG_PRADD VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
	 * );
	 * 
	 * return hashMap; }
	 */
	
	private static final Map<String, String[]> reportTableMap = prepareMap();
	private static final Map<String, String[]> saphistreportTableMap = preparesaphistreportMap();
   
	/*
	 * private static Map<String, String[]> prepareMap() { Map<String, String[]>
	 * hashMap = new HashMap<>(); hashMap.put("ASTRET", new String[] {
	 * "INSERT INTO FAWEB.HIST_LEGACY_INCTAX_RET VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
	 * ,
	 * "select TXRET_UNIQUE_ASSET from HIST_LEGACY_INCTAX_RET where TXRET_YEAR=? and rtrim(TXRET_REPORT_NAME)=? group by TXRET_UNIQUE_ASSET having count(TXRET_UNIQUE_ASSET) > 1"
	 * }); hashMap.put("PSTRET", new String[] {
	 * "INSERT INTO FAWEB.HIST_LEGACY_PROPTAX_REG_PRRET VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
	 * ,
	 * "select PRRET_UNIQUE_ASST_NO from HIST_LEGACY_PROPTAX_REG_PRRET where PRRET_YEAR=? and rtrim(PRRET_REPORT_NAME)=? group by PRRET_UNIQUE_ASST_NO having count(PRRET_UNIQUE_ASST_NO) > 1"
	 * }); hashMap.put("ASTREG", new String[] {
	 * "INSERT INTO FAWEB.HIST_LEGACY_INCTAX_REGADD VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
	 * ,
	 * "select txadd_unique_asset from hist_legacy_inctax_regadd where txadd_year=? and rtrim(txadd_report_name)=? group by txadd_unique_asset having count(txadd_unique_asset) > 1"
	 * }); hashMap.put("PSTREG", new String[] {
	 * "INSERT INTO FAWEB.HIST_LEGACY_PROPTAX_REG_PRADD VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
	 * ,
	 * "select PRADD_UNIQUE_ASST_NO from HIST_LEGACY_PROPTAX_REG_PRADD where PRADD_YEAR=? and rtrim(PRADD_REPORT_NAME)=? group by PRADD_UNIQUE_ASST_NO having count(PRADD_UNIQUE_ASST_NO) > 1"
	 * });
	 * 
	 * return hashMap; }
	 */
	private static Map<String, String[]> prepareMap() {
		Map<String, String[]> hashMap = new HashMap<>();
		hashMap.put("ASTRET", new String[] {
				"INSERT INTO FAWEB.DB_TABLE VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				"select TXRET_UNIQUE_ASSET from DB_TABLE where TXRET_YEAR=? and rtrim(TXRET_REPORT_NAME)=? group by TXRET_UNIQUE_ASSET having count(TXRET_UNIQUE_ASSET) > 1" });
		hashMap.put("PSTRET", new String[] {
				"INSERT INTO FAWEB.DB_TABLE VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				"select PRRET_UNIQUE_ASST_NO from DB_TABLE where PRRET_YEAR=? and rtrim(PRRET_REPORT_NAME)=? group by PRRET_UNIQUE_ASST_NO having count(PRRET_UNIQUE_ASST_NO) > 1" });
		hashMap.put("ASTREG", new String[] {
				"INSERT INTO FAWEB.DB_TABLE VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				"select txadd_unique_asset from DB_TABLE where txadd_year=? and rtrim(txadd_report_name)=? group by txadd_unique_asset having count(txadd_unique_asset) > 1" });
		hashMap.put("PSTREG", new String[] {
				"INSERT INTO FAWEB.DB_TABLE VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				"select PRADD_UNIQUE_ASST_NO from DB_TABLE where PRADD_YEAR=? and rtrim(PRADD_REPORT_NAME)=? group by PRADD_UNIQUE_ASST_NO having count(PRADD_UNIQUE_ASST_NO) > 1" });
		return hashMap;
	}	
	
	private static Map<String, String[]> preparesaphistreportMap() {
		Map<String, String[]> hashMap = new HashMap<>();
		hashMap.put("SHASTREG", new String[] {
				 "INSERT INTO FAWEB.DB_TABLE VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
			     "select INC_UNIQUEASSET from DB_TABLE where YEAR=? and rtrim(REPORT_NAME)=? group by INC_UNIQUEASSET having count(INC_UNIQUEASSET) > 1" });
		hashMap.put("SHASTRET", new String[] {
				"INSERT INTO FAWEB.DB_TABLE VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				"select UNIQUEASSET from DB_TABLE where YEAR=? and rtrim(REPORT_NAME)=? group by UNIQUEASSET having count(UNIQUEASSET) > 1" });
		hashMap.put("SHPPTREG", new String[] {
				"INSERT INTO FAWEB.DB_TABLE VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				"select PROP_UNIQUE_ASSTNO from DB_TABLE where YEAR=? and rtrim(REPORT_NAME)=? group by PROP_UNIQUE_ASSTNO having count(PROP_UNIQUE_ASSTNO) > 1" });
		hashMap.put("SHPPTRET", new String[] {
				"INSERT INTO FAWEB.DB_TABLE VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				"select PRET_UNIQUE_ASSET from DB_TABLE where YEAR=? and rtrim(REPORT_NAME)=? group by PRET_UNIQUE_ASSET having count(PRET_UNIQUE_ASSET) > 1" });
		return hashMap;
	}
	
	/**
	 * @author code2java This method will be called when user clicks on link showed
	 *         on welcome.jsp page The request is mapped using @RequestMapping
	 *         annotation.
	 * @return
	 */
	@RequestMapping(value = "/uploadFile")
	public String loadDatatable(Model model) {
		// model.addAttribute("companyGroupsMap", getCompanyGroups());
		// model.addAttribute("instanceMap", getInstances());
		model.addAttribute("yearMap", getYears());
		// model.addAttribute("reportNamesMap", getLoadReportNames());
		return "UploadFile";

	}

	/**
	 * This method will be called from AJAX callback of Data Tables
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "loadFileUploadTableData", method = RequestMethod.POST)
	public ModelAndView loadFileUploadTableData(HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		UploadFile fileupload = new UploadFile();
		fileupload.setYear(request.getParameter("year"));
		fileupload.setReportName(request.getParameter("reportId"));
		List<String> tableData = histUploadFileService.getTableData(fileupload);
		Map<String, String> tableDataMap = new LinkedHashMap<String, String>();
		tableDataMap.put("reportId", tableData.get(0));
		tableDataMap.put("reportVersion", tableData.get(1));
		tableDataMap.put("system", tableData.get(2));
		tableDataMap.put("dbTable", tableData.get(3));
		tableDataMap.put("creationDate", tableData.get(4));
		tableDataMap.put("updateDate", tableData.get(5));
		tableDataMap.put("amendmentDate", tableData.get(6));
        response.setHeader("Cache-Control", "no-cache"); 
 		 response.setContentType("application/json");
 		  String json = new Gson().toJson(tableDataMap);
 		  response.getWriter().write(json);
 		   return null;
	}

	// YEAR
	public Map<String, String> getYears() {
		Map<String, String> yearMap = new LinkedHashMap<String, String>();
		List<String[]> list = histUploadFileService.getYears();
		for (String[] s : list) {
			yearMap.put(s[0], s[0]);
		}
		return yearMap;
	}

	@RequestMapping(value = "loadSSOData", method = RequestMethod.GET)
	public String getSSOId(HttpServletResponse response,HttpServletRequest request) throws JSONException, IOException{
		//String loginSSO=request.getParameter("loginSSO");
		HttpSession session = request.getSession();
		String ssoid=(null!=session && null!=session.getAttribute("uid"))?session.getAttribute("uid").toString():null;
		logger.info("ssoid: "+ssoid);
		if(null!=ssoid) {
			String flag=histUploadFileService.getSSOId(ssoid);
			Map<String, String> ssoMap = new LinkedHashMap<String, String>();
			ssoMap.put("flag", flag);
			response.setHeader("Cache-Control", "no-cache"); 
	 		 response.setContentType("application/json");
	 		  String json = new Gson().toJson(ssoMap);
	 		  response.getWriter().write(json);	
		}
		
 		   return null;
	}
	@RequestMapping(value = "getFileUploadReportNames", method = RequestMethod.GET)
	public @ResponseBody String getReportNames(@RequestParam(value = "reportyear", required = true) String reportyear) {
		List<String[]> list = histUploadFileService.getUploadFileReportNames(reportyear);
		Map<String, String> reportNamesMap = new LinkedHashMap<String, String>();
		for (String[] s : list) {
			reportNamesMap.put(StringEscapeUtils.escapeHtml4(s[0]), StringEscapeUtils.escapeHtml4(s[1]));

		}
		JSONObject json = new JSONObject(reportNamesMap);
		return json.toString();
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadDoc(@RequestParam CommonsMultipartFile file, Model model,@RequestParam("reportId1") String reportId,@RequestParam("year1") String year) throws IOException {
		//System.out.println("welcome111===" + StringUtils.cleanPath(file.getOriginalFilename()));
      System.out.println("reportId  : "+reportId);
		File f1 = new File(file.getOriginalFilename());
		String statusMsg="";
		Connection conn;
		PreparedStatement ps;
		try {
			
			UploadFile fileupload = new UploadFile();
			fileupload.setYear(year);
			fileupload.setReportName(reportId);
			List<String> tableData = histUploadFileService.getTableData(fileupload);
			String layOut = tableData.get(7);
			//naidu newly added
			String dbTableUnsan=tableData.get(3).trim();
			String dbTable =dbTableUnsan.replace("'", "");
			//String dbTable = tableData.get(3).trim();
			//naidu newly added
			String version=tableData.get(1).trim();
          System.out.println("layOut"+layOut);
			// FileInputStream fis = new FileInputStream(f);

			// creating workbook instance that refers to .xls file
			XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
			// creating a Sheet object to retrieve the object
			XSSFSheet sheet = wb.getSheetAt(0);
			// evaluating cell type
			FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
			List<Object[]> objectList = new ArrayList<>();
			Map<String,String[]> tableMap=new HashMap<String, String[]>();
			if(version.equalsIgnoreCase("Hist")) {                                                              
				tableMap=reportTableMap;
			}else if(version.equalsIgnoreCase("SAP_HIST")) {
				tableMap=saphistreportTableMap;
			}
			int totalExpectedColumns=StringUtils.countMatches(tableMap.get(layOut.trim())[0], "?");
			for (Row row : sheet)
			// iteration over row using for each loop
			{
				// Object obj[] = new Object[63];
				List<Object> obj = new ArrayList<Object>();
				for (int cn=0; cn<totalExpectedColumns; cn++) {
				//for (Cell cell : row) // iteration over cell using for each loop
				//{
					Cell cell = row.getCell(cn);
					if (cell == null) {
						obj.add("");
					}else {
						switch (formulaEvaluator.evaluateInCell(cell).getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
							obj.add(cell.getNumericCellValue());
							break;
						case Cell.CELL_TYPE_STRING:
							obj.add(cell.getStringCellValue());
							break;
						}
					}

				}
				if (obj.size() > 0) {

					objectList.add(obj.toArray());
				}

			}
			
			// Removing header row
			objectList.remove(0);
			// check if the last record has all null values
			int lastrecord = objectList.size() - 1;
			if (objectList.get(lastrecord)[1] == null) {
				objectList.remove(lastrecord);
			}
			
			String reportname = "";
			if(version.equalsIgnoreCase("Hist")) {                                                              
				reportname=(String) objectList.get(0)[1];
			}else if(version.equalsIgnoreCase("SAP_HIST")) {
				//int rowlength=objectList.get(0).length;
				//reportname=(String) objectList.get(0)[rowlength-2];
				reportname=(String) objectList.get(0)[1];
			}
			reportname = reportname.trim();
			reportId = reportId.trim();
			if(reportId.equals(reportname)) {
				
				//int[] updated = dataTableDao.getJdbcTemplate().batchUpdate(reportTableMap.get(layOut.trim())[0],objectList);
				//naidu newly added
				String saveSql = tableMap.get(layOut.trim())[0].replace("DB_TABLE",dbTable);
				System.out.println("new savesql:"+saveSql);
				//System.out.println("old savesql:"+tableMap.get(layOut.trim())[0]);
				int updated = histUploadFileService.saveBatchRecords(objectList.size(), saveSql, objectList);
				//naidu newly added
				//int updated = histUploadFileService.saveBatchRecords(objectList.size(), reportTableMap.get(layOut.trim())[0], objectList);
				System.out.println("count" + updated);
				if(updated>0) {
					statusMsg=statusMsg+"Excel data uploaded successfully , new records added - "+updated;
				}
				//naidu newly added
				String duplicatecheckquery = tableMap.get(layOut.trim())[1].replace("DB_TABLE",dbTable); 
				System.out.println("new duplicate check query: "+duplicatecheckquery);
				//naidu newly added
				//String duplicatecheckquery = reportTableMap.get(layOut.trim())[1];
				//System.out.println(" old duplicatecheckquery:"+tableMap.get(layOut.trim())[1]);
				conn = dataTableDao.getConnection();
				ps = conn.prepareStatement(duplicatecheckquery);
				ps.setString(1, year);
				System.out.println("year:"+year);
				ps.setString(2, reportId);
				System.out.println("reportId:"+reportId);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					statusMsg=statusMsg+" , Duplicate Found";
                   System.out.println("Duplicate Found");
				} else {
					System.out.println("No Duplicate");
				}

			}else {
				statusMsg=statusMsg+"Selected Report ID is not matching with excel data uploaded";
				System.out.println("Selected Report ID is not matching with excel data uploaded");
			}
			
		} catch (Exception e) {
			System.out.println(e);
			//statusMsg=statusMsg+" File Upload Failed : "+e.getMessage();
			statusMsg=statusMsg+" File Upload failed, check column format and number of columns";
		}
		model.addAttribute("statusMsg",statusMsg);
		return "UploadFile";

	}

}