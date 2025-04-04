package com.code2java.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.code2java.dao.DataTableDAO;

@Service("histRetService")
public class HistIncomeTaxRetServiceImpl implements HistIncomeTaxRetService{

	@Autowired
	private DataTableDAO dataTableDao;
	
		public SXSSFWorkbook getHistRetExcelData(HttpServletRequest request){
			
			SXSSFWorkbook workbook = null;
			Sheet reportSheet = null;
			workbook = new SXSSFWorkbook(100);
			reportSheet = workbook.createSheet("Reports");
			Row header = reportSheet.createRow(0);
			header.setHeight((short) 500);
			Row dataRow = null;	
			header.createCell(0).setCellValue("Year");
			header.createCell(1).setCellValue("Report name");
			header.createCell(2).setCellValue("Instance");
			header.createCell(3).setCellValue("Unique Asset Number");		
			header.createCell(4).setCellValue("FA Company");
			header.createCell(5).setCellValue("SAP BU");
			header.createCell(6).setCellValue("Asset Number Primary");
			header.createCell(7).setCellValue("Asset Component");
			header.createCell(8).setCellValue("Internal Asset");
			header.createCell(9).setCellValue("Asset Description");
			header.createCell(10).setCellValue("Entered Date");
			header.createCell(11).setCellValue("Tax Install Date");
			header.createCell(12).setCellValue("Tax Asset Life");
			header.createCell(13).setCellValue("Tax Remaining Life");
			header.createCell(14).setCellValue("Tax Cost Basis");
			header.createCell(15).setCellValue("Tax Depreciable basis");
			header.createCell(16).setCellValue("Tax Accumulated Reserve");
			header.createCell(17).setCellValue("Tax Ytd Depreciation");
			header.createCell(18).setCellValue("Tax Net book Value");
			header.createCell(19).setCellValue("Bonus Depreciation");
			header.createCell(20).setCellValue("Ytd Bonus Depreciation");
			header.createCell(21).setCellValue("Elect out Bonus");
			header.createCell(22).setCellValue("Bonus % Flag");
			header.createCell(23).setCellValue("Bonus %");
			header.createCell(24).setCellValue("YTD Amt Depr");		
			header.createCell(25).setCellValue("ADR Additional Depr");
			header.createCell(26).setCellValue("ACRS Class");
			header.createCell(27).setCellValue("Tax Regulation Code");
			header.createCell(28).setCellValue("Tax Depr Table");
			header.createCell(29).setCellValue("Section 1245/1250 Property");
			header.createCell(30).setCellValue("Listed Property");
			header.createCell(31).setCellValue("Depr Intangible");
			header.createCell(32).setCellValue("First Depr Year");
			header.createCell(33).setCellValue("Corp Internal Asset");
			header.createCell(34).setCellValue("Tax Amt Table");
			header.createCell(35).setCellValue("Tax New Or Used");
			header.createCell(36).setCellValue("Tax Auto");
			header.createCell(37).setCellValue("Tax Leasehold Impr");
			header.createCell(38).setCellValue("Tax Depr Method");
			header.createCell(39).setCellValue("Tax Depr Convention");
			header.createCell(40).setCellValue("Corp Install Date");
			header.createCell(41).setCellValue("Corp Asset Life");
			header.createCell(42).setCellValue("Corp Book Cost Basis");
			header.createCell(43).setCellValue("Corp Book Accum Reserve");
			header.createCell(44).setCellValue("Corp Book Current Depr");
			header.createCell(45).setCellValue("Corp Last Year Depreciated");
			header.createCell(46).setCellValue("Corp Last Month Depreciated");
			header.createCell(47).setCellValue("Corp NBV");
			header.createCell(48).setCellValue("Corp Fully Retired");
			header.createCell(49).setCellValue("Project");
			header.createCell(50).setCellValue("Category");
			header.createCell(51).setCellValue("Category Desc");
			header.createCell(52).setCellValue("Tax Installation Yr");
			header.createCell(53).setCellValue("Tax Curr Acc Year");
			header.createCell(54).setCellValue("Book Name");
			header.createCell(55).setCellValue("Retirement Code");
			header.createCell(56).setCellValue("Retirement Date");
			header.createCell(57).setCellValue("Retirement Cost");
			header.createCell(58).setCellValue("Retirement Proceeds");
			header.createCell(59).setCellValue("Gain/Loss Amt");
			header.createCell(60).setCellValue("Retirement Desc");
			header.createCell(61).setCellValue("Retirement AD");
			header.createCell(62).setCellValue("Retirement YTD");
			header.createCell(63).setCellValue("Crt Date");
			header.createCell(64).setCellValue("Crt User");		
			int rowNum = 1;
			
	    String facompanygroup=request.getParameter("facompanygroup");
		String facompany=request.getParameter("facompany");
		String taxInstallDateFrom=request.getParameter("taxInstallDateFrom");
		String taxInstallDateTo=request.getParameter("taxInstallDateTo");			
		String year=request.getParameter("year");
		String reportId=request.getParameter("reportId");
		String rptId=reportId.trim();
		
			
		String tableName="";
		List<String[]> tableNameList=this.getReportTables(reportId,year);
		for (String[] strings : tableNameList) {
			
			String tb=strings[2];
			tableName=tb;
		}
		
		
		String table = tableName;


		List<String> sArray = new ArrayList<String>();
		
		int mapi=0;
		Map<String,String> prepareParamMap=new LinkedHashMap<String, String>(); 
		
		if (null!=facompany && !facompany.equals("")) {			
			sArray.add(" TXRET_SAP_BU like ?");
			prepareParamMap.put(++mapi+"_"+"like",facompany);
		}
		
		if (null!=facompanygroup && !facompanygroup.equals("")) {			
			sArray.add(" TXRET_SAP_BU in (select company from FACOMPANY where companygroup like ?)");
			prepareParamMap.put(++mapi+"_"+"like",facompanygroup);
		}
		
		if (null!=taxInstallDateFrom && !taxInstallDateFrom.equals("")) {			
			String taxInstallQuery=" to_date(TXRET_RETIREMENT_DATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
			prepareParamMap.put(++mapi+"_"+"eq",taxInstallDateFrom);
			sArray.add(taxInstallQuery);
		}
		
		if (null!=taxInstallDateTo && !taxInstallDateTo.equals("")) {			
			String taxInstallQuery=" to_date(TXRET_RETIREMENT_DATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			prepareParamMap.put(++mapi+"_"+"eq",taxInstallDateTo);
			sArray.add(taxInstallQuery);
		}
		
		if (null!=year && !year.equals("")) {
			//sArray.add(" INC_COMPANYCODE like '%" + facompany + "%'");
			sArray.add(" TXRET_YEAR like ?");
			prepareParamMap.put(++mapi+"_"+"like",year);
		}
		
		if (null!=rptId && !rptId.equals("")) {			
			sArray.add(" TXRET_REPORT_NAME like ?");
			prepareParamMap.put(++mapi+"_"+"like",rptId);
		}
				
		String individualSearch = "";
		if(sArray.size()==1)
		{
			individualSearch = sArray.get(0);
		}
		else if(sArray.size()>1)
		{
			for(int i=0;i<sArray.size()-1;i++)
			{
				individualSearch += sArray.get(i)+ " and ";
			}
			individualSearch += sArray.get(sArray.size()-1);
		}
		
		
		
		String searchSQL="select TXRET_YEAR,TXRET_REPORT_NAME,TXRET_INSTANCE,TXRET_UNIQUE_ASSET,TXRET_FA_COMPANY,TXRET_SAP_BU,TXRET_ASSET_NUMBER_PRIMARY,TXRET_ASSET_COMPONENT,TXRET_INTERNAL_ASSET,TXRET_ASSET_DESCRIPTION,TXRET_ENTERED_DATE,TXRET_TAX_INSTALL_DATE,TXRET_TAX_ASSET_LIFE,TXRET_TAX_REMAINING_LIFE,TXRET_TAX_COST_BASIS,TXRET_TAX_DEPRECIABLE_BASIS,TXRET_TAX_ACCUMULATED_RESERVE,TXRET_TAX_YTD_DEPRECIATION,TXRET_TAX_NET_BOOK_VALUE,TXRET_BONUS_DEPRECIATION,TXRET_YTD_BONUS_DEPRECIATION,TXRET_ELECT_OUT_BONUS,TXRET_BONUS_FLAG,TXRET_BONUS,TXRET_YTD_AMT_DEPR,TXRET_ADR_ADDITIONAL_DEPR,TXRET_ACRS_CLASS,TXRET_TAX_REGULATION_CODE,TXRET_TAX_DEPR_TABLE,TXRET_SECTION_1245_1250_PROPERTY,TXRET_LISTED_PROPERTY,TXRET_DEPR_INTANGIBLE,TXRET_FIRST_DEPR_YEAR,TXRET_CORP_INTERNAL_ASSET,TXRET_TAX_AMT_TABLE,TXRET_TAX_NEW_OR_USED,TXRET_TAX_AUTO,TXRET_TAX_LEASEHOLD_IMPR,TXRET_TAX_DEPR_METHOD,TXRET_TAX_DEPR_CONVENTION,TXRET_CORP_INSTALL_DATE,TXRET_CORP_ASSET_LIFE,TXRET_CORP_BOOK_COST_BASIS,TXRET_CORP_BOOK_ACCUM_RESERVE,TXRET_CORP_BOOK_CURRENT_DEPR,TXRET_CORP_LAST_YEAR_DEPRECIATED,TXRET_CORP_LAST_MONTH_DEPRECIATED,TXRET_CORP_NBV,TXRET_CORP_FULLY_RETIRED,TXRET_PROJECT,TXRET_CATEGORY,TXRET_CATEGORY_DESC,TXRET_TAX_INSTALLATION_YR,TXRET_TAX_CURR_ACC_YEAR,TXRET_BOOK_NAME,TXRET_RETIREMENT_CODE,TXRET_RETIREMENT_DATE,TXRET_RETIREMENT_COST,TXRET_RETIREMENT_PROCEEDS,TXRET_GAIN_LOSS_AMT,TXRET_RETIREMENT_DESC,TXRET_RETIREMENT_AD,TXRET_RETIREMENT_YTD,TXRET_CRTDATE,TXRET_CRTUSER from HIST_LEGACY_INCTAX_RET"; 				   				  
      
		
		
		
		if(individualSearch!=null && individualSearch!=""){
			searchSQL = searchSQL+" where " + individualSearch;
			
		}
		
		searchSQL += " order by TXRET_UNIQUE_ASSET asc";

		List<List<String>> resultDataList = new ArrayList<List<String>>();
		try {
			Connection conn = dataTableDao.getConnection();
			PreparedStatement ps = conn.prepareStatement(searchSQL);
			
			int mapj = 1;
			for (Map.Entry<String, String> m : prepareParamMap.entrySet()) {
				if (m.getKey().contains("like")) {
					ps.setString(mapj, "%" + m.getValue() + "%");
				} else {
					ps.setString(mapj, m.getValue());
				}
				mapj++;
			}
			
			ps.setFetchSize(2000);
			ResultSet rs2 = ps.executeQuery();
			System.out.println("resultset Start Time: "+new Date());
			int counter=0;
			while (rs2.next()){
				counter++;
				List<String> resultRowData = new ArrayList<String>();
				for (int i = 1; i <= 65; i++) {	
					
					 					resultRowData.add(rs2.getString(i));
				}
				resultDataList.add(resultRowData);
				resultRowData=new ArrayList<String>(1);
				if(counter % 30000==0)
				{
					for (List<String> list : resultDataList) {
						
						int cellNum = 0;
						// create the row data
						Row row = reportSheet.createRow(rowNum++);
						for (String entry : list) {
							if(StringUtils.isNotBlank(entry) && (cellNum==14 || cellNum==15 || cellNum==16 || cellNum==17|| cellNum==18 
									|| cellNum==19 || cellNum==20 || cellNum==24 || cellNum==25 || cellNum==42 || cellNum==43 || cellNum==44 || cellNum==47 || cellNum==57 || cellNum==58 || cellNum==59 || cellNum==61 || cellNum==62 )) {
												row.createCell(cellNum++).setCellValue(Double.parseDouble(entry));
											}
							else {
							row.createCell(cellNum++).setCellValue(entry);
							}
						}
					}
				//	System.out.println("cleared"+counter);
				//	System.out.println("size before"+resultDataList.size());
					
					//resultDataList.clear();//size 3000
					resultDataList=null;
					
					 resultDataList = new ArrayList<List<String>>(1);//10
					// System.out.println("size after"+resultDataList.size());
					
				}
			}
				for (List<String> list : resultDataList) {
					
					int cellNum = 0;
					// create the row data
					Row row = reportSheet.createRow(rowNum++);
					for (String entry : list) {
						if(StringUtils.isNotBlank(entry) && (cellNum==14 || cellNum==15 || cellNum==16 || cellNum==17|| cellNum==18 
								|| cellNum==19 || cellNum==20 || cellNum==24 || cellNum==25 || cellNum==42 || cellNum==43 || cellNum==44 || cellNum==47 || cellNum==57 || cellNum==58 || cellNum==59 || cellNum==61 || cellNum==62 )) {
											row.createCell(cellNum++).setCellValue(Double.parseDouble(entry));
										}
						else {
						row.createCell(cellNum++).setCellValue(entry);
						}
					}
				}
			//	System.out.println("cleared"+counter);
			//	System.out.println("size before"+resultDataList.size());
				
			//	System.out.println("size after"+resultDataList.size());
				resultDataList=null;
			 resultDataList = new ArrayList<List<String>>(1);//10
			System.out.println("resultset End Time: "+new Date());
			ps.close();
			conn.close();
			rs2.close();
		}catch(Exception e) {
			System.out.println("Exception"+e);
		}
		return workbook;
	}


	@Override
public String getHistRetDataTableResponse(HttpServletRequest request) {

		
//		String[] cols = {"INC_UNIQUEASSET","INC_COMPANYCODE","INC_ASSETNUMBER","INC_SUBNUMBER","INC_ASSETDESCRIPTION","INC_ASSETCLASSS","INC_ASSETCLASSDESCRIPTION","INC_LEGACYCATEGORYCODE","INC_ENTEREDDATE","INC_INSTALLDATE","INC_TAXINSTALLATIONYR","INC_ASSETLIFE","INC_REMAININGLIFE","INC_TAXAPC","INC_DEPRECIABLEBASIS","INC_ACCUMULATEDDEPRECIATION","INC_YEARTODATEDEPREC","INC_TAXNBV","INC_BONUSDEPRECIATION","INC_TAXYTDBONUSDEPR","INC_ELECTOUTBONUS","INC_TAXBONUSPERCENTAGE","INC_FIRSTYEARDEPRECIATED","INC_LASTYEARDEPRECIATED",
//				"INC_LASTMONTHDEPRECIATED","INC_NEWORUSED","INC_TAXDEPRMETHOD","INC_TAXDEPRCONVENTION","INC_CURRENTACCOUNTINGYEAR","INC_CORPINSTALLDATE","INC_CORPLIFE","INC_CORPBOOKAPC","INC_CORPBOOKACCUMRESERVE","INC_CORPNBV","INC_CREATIONDATE","INC_CREATIONTIME","INC_CREATIONUSER"};
		String[] cols = {"TXRET_YEAR","TXRET_REPORT_NAME","TXRET_INSTANCE","TXRET_UNIQUE_ASSET","TXRET_FA_COMPANY","TXRET_SAP_BU","TXRET_ASSET_NUMBER_PRIMARY","TXRET_ASSET_COMPONENT","TXRET_INTERNAL_ASSET","TXRET_ASSET_DESCRIPTION",
				"TXRET_ENTERED_DATE","TXRET_TAX_INSTALL_DATE","TXRET_TAX_ASSET_LIFE","TXRET_TAX_REMAINING_LIFE","TXRET_TAX_COST_BASIS","TXRET_TAX_DEPRECIABLE_BASIS","TXRET_TAX_ACCUMULATED_RESERVE","TXRET_TAX_YTD_DEPRECIATION",
				"TXRET_TAX_NET_BOOK_VALUE","TXRET_BONUS_DEPRECIATION","TXRET_YTD_BONUS_DEPRECIATION","TXRET_ELECT_OUT_BONUS","TXRET_BONUS_FLAG","TXRET_BONUS","TXRET_YTD_AMT_DEPR","TXRET_ADR_ADDITIONAL_DEPR","TXRET_ACRS_CLASS","TXRET_TAX_REGULATION_CODE",
				"TXRET_TAX_DEPR_TABLE","TXRET_SECTION_1245_1250_PROPERTY","TXRET_LISTED_PROPERTY","TXRET_DEPR_INTANGIBLE","TXRET_FIRST_DEPR_YEAR","TXRET_CORP_INTERNAL_ASSET","TXRET_TAX_AMT_TABLE","TXRET_TAX_NEW_OR_USED","TXRET_TAX_AUTO",
				"TXRET_TAX_LEASEHOLD_IMPR","TXRET_TAX_DEPR_METHOD","TXRET_TAX_DEPR_CONVENTION","TXRET_TAX_DEPR_CONVENTION","TXRET_CORP_ASSET_LIFE","TXRET_CORP_BOOK_COST_BASIS","TXRET_CORP_BOOK_ACCUM_RESERVE","TXRET_CORP_BOOK_CURRENT_DEPR","TXRET_CORP_LAST_YEAR_DEPRECIATED",
				"TXRET_CORP_LAST_MONTH_DEPRECIATED","TXRET_CORP_NBV","TXRET_CORP_FULLY_RETIRED","TXRET_PROJECT","TXRET_CATEGORY","TXRET_CATEGORY_DESC","TXRET_TAX_INSTALLATION_YR","TXRET_TAX_CURR_ACC_YEAR","TXRET_BOOK_NAME","TXRET_RETIREMENT_CODE","TXRET_RETIREMENT_DATE","TXRET_RETIREMENT_COST",
				"TXRET_RETIREMENT_PROCEEDS","TXRET_GAIN_LOSS_AMT","TXRET_RETIREMENT_DESC","TXRET_RETIREMENT_AD","TXRET_RETIREMENT_YTD","TXRET_CRTDATE","TXRET_CRTUSER"};		
		
		String year=request.getParameter("year");
		String reportId=request.getParameter("reportId");
		String tableName="";
		List<String[]> tableNameList=this.getReportTables(reportId,year);

		for (String[] strings : tableNameList) {
			
			String tb=strings[2];
			tableName=tb;
		}
		
		
		String table = tableName;

		/* Response will be a String of JSONObject type */
		JSONObject result = new JSONObject();

		/* JSON Array to store each row of the data table */
		JSONArray array = new JSONArray();

		int amount = 5; /* Amount in Show Entry drop down */
		int start = 0; /* Counter for Paging, initially 0 */
		int echo = 0; /* Maintains the request number, initially 0 */
		int col = 0; /* Column number in the datatable */
		

		/* Below variables store the options to create the Query */
		String dir = "asc";
		String sStart = request.getParameter("iDisplayStart");
		String sAmount = request.getParameter("iDisplayLength");
		String sEcho = request.getParameter("sEcho");
		String sCol = request.getParameter("iSortCol_0");
		String sdir = request.getParameter("sSortDir_0");
		
		String facompanygroup=request.getParameter("facompanygroup");
		String facompany=request.getParameter("facompany");
		String taxInstallDateFrom=request.getParameter("taxInstallDateFrom");
		String taxInstallDateTo=request.getParameter("taxInstallDateTo");
		
		List<String> sArray = new ArrayList<String>();
		
		int mapi=0;
		Map<String,String> prepareParamMap=new LinkedHashMap<String, String>();
		
		if (null!=facompany && !facompany.equals("")) {
			//sArray.add(" INC_COMPANYCODE like '%" + facompany + "%'");
			sArray.add(" TXRET_SAP_BU like ?");
			prepareParamMap.put(++mapi+"_"+"like",facompany);
		}
		
		if (null!=facompanygroup && !facompanygroup.equals("")) {
			//sArray.add(" INC_COMPANYCODE in (select company from FACOMPANY where companygroup like '%" + facompanygroup + "%')");	
			sArray.add(" TXRET_SAP_BU in (select company from FACOMPANY where companygroup like ?)");
			prepareParamMap.put(++mapi+"_"+"like",facompanygroup);
		}
		
		if (null!=taxInstallDateFrom && !taxInstallDateFrom.equals("")) {
			String taxInstallQuery=" to_date(TXRET_RETIREMENT_DATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
			
			//taxInstallQuery=taxInstallQuery.replace("taxInsFromParam", taxInstallDateFrom);
			prepareParamMap.put(++mapi+"_"+"eq",taxInstallDateFrom);
			sArray.add(taxInstallQuery);
		}
		
		if (null!=taxInstallDateTo && !taxInstallDateTo.equals("")) {
			String taxInstallQuery=" to_date(TXRET_RETIREMENT_DATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			
			//taxInstallQuery=taxInstallQuery.replace("taxInsToParam", taxInstallDateTo);
			prepareParamMap.put(++mapi+"_"+"eq",taxInstallDateTo);
			sArray.add(taxInstallQuery);
		}
		if (null!=year && !year.equals("")) {
			//sArray.add(" INC_COMPANYCODE like '%" + facompany + "%'");
			sArray.add(" TXRET_YEAR like ?");
			prepareParamMap.put(++mapi+"_"+"like",year);
		}
		
		if (null!=reportId && !reportId.equals("")) {			
			sArray.add(" TXRET_REPORT_NAME like ?");
			prepareParamMap.put(++mapi+"_"+"like",reportId);
		}
		
		
		String individualSearch = "";
		if(sArray.size()==1)
		{
			individualSearch = sArray.get(0);
		}
		else if(sArray.size()>1)
		{
			for(int i=0;i<sArray.size()-1;i++)
			{
				individualSearch += sArray.get(i)+ " and ";
			}
			individualSearch += sArray.get(sArray.size()-1);
		}

		/* Start value from which the records need to be fetched */
		if (sStart != null) {
			start = Integer.parseInt(sStart);
			if (start < 0)
				start = 0;
		}

		/* Total number of records to be fetched */
		if (sAmount != null) {
			amount = Integer.parseInt(sAmount);
			if (amount < 5 || amount > 100)
				amount = 5;
		}

		/* Counter of the request sent from Data table */
		if (sEcho != null) {
			echo = Integer.parseInt(sEcho);
		}

		/* Column number */
		if (sCol != null) {
			col = Integer.parseInt(sCol);
			if (col < 0 || col > 5)
				col = 0;
		}

		/* Sorting order */
		if (sdir != null) {
			if (!sdir.equals("asc"))
				dir = "desc";
		}
		String colName = cols[col];

		/* This is show the total count of records in Data base table */
		int total = 0;
		Connection conn = dataTableDao.getConnection();

		/* This is total number of records that is available for the specific search query */
		int totalAfterFilter = total;

		try {
			String searchSQL = "";
			String sql = "SELECT * FROM HIST_LEGACY_INCTAX_RET";			
			String searchTerm = request.getParameter("sSearch");
			String globeSearch =  " where ( upper(TXRET_YEAR) like upper(?)"
					+ " or upper(TXRET_REPORT_NAME) like upper(?)"
					+ " or upper(TXRET_INSTANCE) like upper(?)"
					+ " or upper(TXRET_UNIQUE_ASSET) like upper(?)"
					+ " or upper(TXRET_FA_COMPANY) like upper(?)"
					+ " or upper(TXRET_SAP_BU) like upper(?)"
					+ " or upper(TXRET_ASSET_NUMBER_PRIMARY) like upper(?)"
					+ " or upper(TXRET_ASSET_COMPONENT) like upper(?)"
					+ " or upper(TXRET_INTERNAL_ASSET) like upper(?)"
				    + " or upper(TXRET_ASSET_DESCRIPTION) like upper(?)"
				    + " or upper(TXRET_ENTERED_DATE) like upper(?)"
				    + " or upper(TXRET_TAX_INSTALL_DATE) like upper(?)"
					+ " or upper(TXRET_TAX_ASSET_LIFE) like upper(?)"
					+ " or upper(TXRET_TAX_REMAINING_LIFE) like upper(?)"
					+ " or upper(TXRET_TAX_COST_BASIS) like upper(?)"
					+ " or upper(TXRET_TAX_DEPRECIABLE_BASIS) like upper(?)"
					+ " or upper(TXRET_TAX_ACCUMULATED_RESERVE) like upper(?)"
					+ " or upper(TXRET_TAX_YTD_DEPRECIATION) like upper(?)"
					+ " or upper(TXRET_TAX_NET_BOOK_VALUE) like upper(?)"
					+ " or upper(TXRET_BONUS_DEPRECIATION) like upper(?)"
					+ " or upper(TXRET_YTD_BONUS_DEPRECIATION) like upper(?)"
					+ " or upper(TXRET_ELECT_OUT_BONUS) like upper(?)"
					+ " or upper(TXRET_BONUS_FLAG) like upper(?)"
					+ " or upper(TXRET_BONUS) like upper(?)"
					+ " or upper(TXRET_YTD_AMT_DEPR) like upper(?)"
					+ " or upper(TXRET_ADR_ADDITIONAL_DEPR) like upper(?)"
					+ " or upper(TXRET_ACRS_CLASS) like upper(?)"
					+ " or upper(TXRET_TAX_REGULATION_CODE) like upper(?)"
					+ " or upper(TXRET_TAX_DEPR_TABLE) like upper(?)"
					+ " or upper(TXRET_SECTION_1245_1250_PROPERTY) like upper(?)"			
					+ " or upper(TXRET_LISTED_PROPERTY) like upper(?)"
					+ " or upper(TXRET_DEPR_INTANGIBLE) like upper(?)"
					+ " or upper(TXRET_FIRST_DEPR_YEAR) like upper(?)"
					+ " or upper(TXRET_CORP_INTERNAL_ASSET) like upper(?)"
					+ " or upper(TXRET_TAX_AMT_TABLE) like upper(?)"
					+ " or upper(TXRET_TAX_NEW_OR_USED) like upper(?)"
					+ " or upper(TXRET_TAX_AUTO) like upper(?)"
					+ " or upper(TXRET_TAX_LEASEHOLD_IMPR) like upper(?)"
					+ " or upper(TXRET_TAX_DEPR_METHOD) like upper(?)"
					+ " or upper(TXRET_TAX_DEPR_CONVENTION) like upper(?)"
					+ " or upper(TXRET_CORP_INSTALL_DATE) like upper(?)"
					+ " or upper(TXRET_CORP_ASSET_LIFE) like upper(?)"
					+ " or upper(TXRET_CORP_BOOK_COST_BASIS) like upper(?)"
					+ " or upper(TXRET_CORP_BOOK_ACCUM_RESERVE) like upper(?)"
					+ " or upper(TXRET_CORP_BOOK_CURRENT_DEPR) like upper(?)"
					+ " or upper(TXRET_CORP_LAST_YEAR_DEPRECIATED) like upper(?)"
					+ " or upper(TXRET_CORP_LAST_MONTH_DEPRECIATED) like upper(?)"
					+ " or upper(TXRET_CORP_NBV) like upper(?)"
					+ " or upper(TXRET_CORP_FULLY_RETIRED) like upper(?)"
					+ " or upper(TXRET_PROJECT) like upper(?)"
					+ " or upper(TXRET_CATEGORY) like upper(?)"
					+ " or upper(TXRET_CATEGORY_DESC) like upper(?)"
					+ " or upper(TXRET_TAX_INSTALLATION_YR) like upper(?)"
					+ " or upper(TXRET_TAX_CURR_ACC_YEAR) like upper(?)"
					+ " or upper(TXRET_BOOK_NAME) like upper(?)"
					+ " or upper(TXRET_RETIREMENT_CODE) like upper(?)"
					+ " or upper(TXRET_RETIREMENT_DATE) like upper(?)"
					+ " or upper(TXRET_RETIREMENT_COST) like upper(?)"
					+ " or upper(TXRET_RETIREMENT_PROCEEDS) like upper(?)"
					+ " or upper(TXRET_GAIN_LOSS_AMT) like upper(?)"
					+ " or upper(TXRET_RETIREMENT_DESC) like upper(?)"
					+ " or upper(TXRET_RETIREMENT_AD) like upper(?)"
					+ " or upper(TXRET_RETIREMENT_YTD) like upper(?))"
					+ " or upper(TXRET_CRTDATE) like upper(?)"
					+ " or upper(TXRET_CRTUSER) like upper(?))";
;
			
			
			
			
			if(searchTerm!=null && searchTerm!="" && individualSearch!=null && individualSearch!=""){
				searchSQL = globeSearch + " and " + individualSearch;
			}
			else if(individualSearch!=null && individualSearch!=""){
				searchSQL = " where " + individualSearch;
			}else if(null!=searchTerm && searchTerm!=""){
				searchSQL=globeSearch;
			}
			sql += searchSQL;			
			if(sql.contains("WHERE")||sql.contains("where")){
				sql=sql+" and ROWNUM <= 200";
						//+ "and REGEXP_LIKE (TXRET_TAX_INSTALL_DATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
			}else {
				sql=sql+" WHERE ROWNUM <= 200";
					//	+ "and REGEXP_LIKE (TXRET_TAX_INSTALL_DATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
			}
			
			//sql += " order by " + colName + " " + dir;
			sql += " order by TXRET_UNIQUE_ASSET asc";
		//	System.out.println("sqlqsql==="+sql);
			PreparedStatement ps = conn.prepareStatement(sql);
			
			int mapj = 1;
			for (Map.Entry<String, String> m : prepareParamMap.entrySet()) {
				if (m.getKey().contains("like")) {
					ps.setString(mapj, "%" + m.getValue() + "%");
				} else {
					ps.setString(mapj, m.getValue());
				}
				mapj++;
			}
			
			if(null!=searchTerm && searchTerm!=""){
				mapj = mapj-1;
				int sSearchCount=39;
		        for(int sSearchC=mapj;sSearchC<mapj+sSearchCount;sSearchC++) {
		            ps.setString(sSearchC, "%" + searchTerm + "%");
		        }
			}
			
			ps.setFetchSize(200);
			ResultSet rs = ps.executeQuery();
			
			int i=0;
			Date date1;
			while (rs.next()) {
				JSONArray ja = new JSONArray();		
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_YEAR")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_REPORT_NAME")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_INSTANCE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_UNIQUE_ASSET")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_FA_COMPANY")));			
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_SAP_BU")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_ASSET_NUMBER_PRIMARY")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_ASSET_COMPONENT")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_INTERNAL_ASSET")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_ASSET_DESCRIPTION")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_ENTERED_DATE")));				
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_TAX_INSTALL_DATE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_TAX_ASSET_LIFE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_TAX_REMAINING_LIFE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_TAX_COST_BASIS"))); 
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_TAX_DEPRECIABLE_BASIS")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_TAX_ACCUMULATED_RESERVE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_TAX_YTD_DEPRECIATION")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_TAX_NET_BOOK_VALUE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_BONUS_DEPRECIATION")));				
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_YTD_BONUS_DEPRECIATION")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_ELECT_OUT_BONUS")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_BONUS_FLAG")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_BONUS")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_YTD_AMT_DEPR")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_ADR_ADDITIONAL_DEPR")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_ACRS_CLASS")));				
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_TAX_REGULATION_CODE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_TAX_DEPR_TABLE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_SECTION_1245_1250_PROPERTY")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_LISTED_PROPERTY")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_DEPR_INTANGIBLE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_FIRST_DEPR_YEAR")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_CORP_INTERNAL_ASSET")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_TAX_AMT_TABLE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_TAX_NEW_OR_USED")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_TAX_AUTO")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_TAX_LEASEHOLD_IMPR")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_TAX_DEPR_METHOD")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_TAX_DEPR_CONVENTION")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_CORP_INSTALL_DATE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_CORP_ASSET_LIFE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_CORP_BOOK_COST_BASIS")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_CORP_BOOK_ACCUM_RESERVE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_CORP_BOOK_CURRENT_DEPR")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_CORP_LAST_YEAR_DEPRECIATED")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_CORP_LAST_MONTH_DEPRECIATED")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_CORP_NBV")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_CORP_FULLY_RETIRED")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_PROJECT")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_CATEGORY")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_CATEGORY_DESC")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_TAX_INSTALLATION_YR")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_TAX_CURR_ACC_YEAR")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_BOOK_NAME")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_RETIREMENT_CODE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_RETIREMENT_DATE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_RETIREMENT_COST")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_RETIREMENT_PROCEEDS")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_GAIN_LOSS_AMT")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_RETIREMENT_DESC")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_RETIREMENT_AD")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_RETIREMENT_YTD")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_CRTDATE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("TXRET_CRTUSER")));
				array.put(ja);
			}
			result.put("iTotalRecords", total);
			result.put("iTotalDisplayRecords", totalAfterFilter);
			result.put("aaData", array);
			result.put("sEcho", echo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	public List<String[]> getCompanyGroups() {
		List<String[]> resultList = new ArrayList<String[]>();
		Connection conn;
		PreparedStatement ps;
		try {
			String query = "select distinct(COMPANYGROUP) from FACOMPANY  order by COMPANYGROUP asc";
			conn = dataTableDao.getConnection();
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String[] arr = new String[2];
				arr[0] = rs.getString("COMPANYGROUP");
				arr[1] = rs.getString("COMPANYGROUP");
				resultList.add(arr);
			}
			conn.close();
			ps.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return resultList;
	}
	
	//Instance
	public List<String[]> getInstances() {
		List<String[]> resultList = new ArrayList<String[]>();
		Connection conn;
		PreparedStatement ps;
		try {
			String query = "select distinct(INSTANCE) from INSTANCE  where INSTANCE is not NULL order by INSTANCE asc";
			conn = dataTableDao.getConnection();
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String[] arr = new String[2];
				arr[0] = rs.getString("INSTANCE");
				arr[1] = rs.getString("INSTANCE");
				resultList.add(arr);
			}
			conn.close();
			ps.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return resultList;
	}
	
	//Year
	public List<String[]> getYears() {
		List<String[]> resultList = new ArrayList<String[]>();
		Connection conn;
		PreparedStatement ps;
		try {
			String query = "select distinct(YEAR) from REPORT_INDEX_TABLE  order by YEAR asc";
			conn = dataTableDao.getConnection();
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String[] arr = new String[1];
				arr[0] = rs.getString("YEAR");
				
				resultList.add(arr);
			}
			conn.close();
			ps.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return resultList;
	}
	
	public List<String[]> getHistRetReportNames(List<String> reportList) {		
		List<String[]> resultList = new ArrayList<String[]>();
		Connection conn;
		PreparedStatement ps;
		try {
			
			String query = "select * from REPORT_INDEX_TABLE where YEAR=? AND LAYOUT='ASTRET' order by REPORT_NAME asc";		
		
			conn = dataTableDao.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, reportList.get(0));
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				String[] arr = new String[2];
				
				arr[0] = StringEscapeUtils.escapeHtml4(rs.getString("REPORT_ID"));
				arr[1] = StringEscapeUtils.escapeHtml4(rs.getString("REPORT_NAME"));
				
			
				resultList.add(arr);
			}
			conn.close();
			ps.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return resultList;
	}
	
	
	
	
	
	
	
	
	
	
	public List<String[]> getHistRetCompanyNames(String COMPANYGROUP) {
		List<String[]> resultList = new ArrayList<String[]>();
		Connection conn;
		PreparedStatement ps;
		try {
			//String query = "select *from FACOMPANY where COMPANYGROUP='"+COMPANYGROUP+"' order by COMPANY asc";
			String query = "select *from FACOMPANY where COMPANYGROUP=? order by COMPANYNAME asc";
			conn = dataTableDao.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, COMPANYGROUP);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String[] arr = new String[2];
				arr[0] = StringEscapeUtils.escapeHtml4(rs.getString("COMPANY"));
				arr[1] = StringEscapeUtils.escapeHtml4(rs.getString("COMPANYNAME"));
				resultList.add(arr);
			}
			conn.close();
			ps.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return resultList;
	}
	
	// CSV files process related - starts

	public List<Map<String, Object>> getInterfaceFilesControl() {
		String sql = "select INTERFACE_FILE_TYPE, TARGET_INTERIM_TABLE from INTERFACE_FILES_CONTROLR";
		List<Map<String, Object>> list = dataTableDao.getJdbcTemplate().queryForList(sql);
		System.out.println(list);
		return list;
	}

	public int deleteRecords(String tableName) {
		return dataTableDao.getJdbcTemplate().update("TRUNCATE table FAWEB." + tableName);
	}

	
//	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	//public int saveBatchRecords(int totalrecords, String targetInterimTable, List<Object[]> objectList) {
		/*
		 * int count = 0; for (int i = 0; i < totalrecords; i = i + 1000) { int
		 * lastindex = totalrecords - i > 1000 ? i + 1000 : totalrecords;
		 * System.out.println("update batch from : " + i + " - " + (lastindex)); int[]
		 * updated =dataTableDao.getJdbcTemplate().batchUpdate(interimTableMap.get(
		 * targetInterimTable), objectList.subList(i, lastindex)); count = count +
		 * updated.length; } return count;
		 */

	public int saveStatusControlTable(Object[] args) {
		String sql = "insert into FAWEB.interface_files_control_status (STATUS_DATE,KEY,FILE_NAME,STATUS,INTERFACE_FILE_FOUND,INTERIM_TABLE_POPULATED,INTERFACE_FILE_ARCHIVED,ERROR_MESSAGE,REPROCESS,ID) values(?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
		return dataTableDao.getJdbcTemplate().update(sql, args);
	}
	
	public int saveToRegisterTables() {
		String sql = "";
		int count1 = 0;
		
		sql = Queries.query1;
		int count=dataTableDao.getJdbcTemplate().update(sql);
		System.out.println("Total records moved for query1: "+count);
		count1=count1+count;
		
		
		
		  sql = Queries.query2; count=dataTableDao.getJdbcTemplate().update(sql);
		  System.out.println("Total records moved for query2: "+count);
		  count1=count1+count;
		  
		  sql = Queries.query3; count=dataTableDao.getJdbcTemplate().update(sql);
		  System.out.println("Total records moved for query3: "+count);
		  count1=count1+count;
		  
		  sql = Queries.query4; count=dataTableDao.getJdbcTemplate().update(sql);
		  System.out.println("Total records moved for query4: "+count);
		  count1=count1+count;
		  
		  sql = Queries.query5; count=dataTableDao.getJdbcTemplate().update(sql);
		  System.out.println("Total records moved for query5: "+count);
		  count1=count1+count;
		  
		  sql = Queries.query6; count=dataTableDao.getJdbcTemplate().update(sql);
		  System.out.println("Total records moved for query6: "+count);
		  count1=count1+count;
		  
		  sql = Queries.query7; count=dataTableDao.getJdbcTemplate().update(sql);
		  System.out.println("Total records moved for query7: "+count);
		  count1=count1+count;
		 	
		return count1;
		
	}
	
	public Integer getRunCount() {
		String sql = "select max(id) from INTERFACE_FILES_CONTROL_STATUS";
		Integer i = dataTableDao.getJdbcTemplate().queryForInt(sql);
		return i;
	}
	
	public int clearReportsTables() {
		int count1= dataTableDao.getJdbcTemplate().update("TRUNCATE table FAWEB.INC_TAX_ASSET_REG_REPORT");
		System.out.println("Records deleted from reports table:"+count1);
		
		int count2= dataTableDao.getJdbcTemplate().update("TRUNCATE table FAWEB.INC_TAX_ASSET_RETIRE_REPORT");
		System.out.println("Records deleted from reports table:"+count2);
		
		int count3= dataTableDao.getJdbcTemplate().update("TRUNCATE table FAWEB.ADS_TAX_ASSET_REGISTER_REPORT");
		//int count4= dataTableDao.getJdbcTemplate().update("alter session set ddl_lock_timeout= 60");
		
		System.out.println("Records deleted from reports table:"+count3);
		
		int count4= dataTableDao.getJdbcTemplate().update("TRUNCATE table FAWEB.INC_TAX_PROJ_REPORT");
		System.out.println("Records deleted from reports table:"+count4);
		
		int count5= dataTableDao.getJdbcTemplate().update("TRUNCATE table FAWEB.PROP_TAX_ASSET_LIST_REPORT");
		System.out.println("Records deleted from reports table:"+count4);
		
		int count6= dataTableDao.getJdbcTemplate().update("TRUNCATE table FAWEB.PROP_TAX_ASSET_RETIREMENT_REPORT");
		System.out.println("Records deleted from reports table:"+count5);
		
		int count7= dataTableDao.getJdbcTemplate().update("TRUNCATE table FAWEB.STATE_TAX_ASSET_LISTING_REPORT");
		System.out.println("Records deleted from reports table:"+count6);
		
			return count1;
	}
	public List<String[]>  getReportTables(String id,String year) {		
		List<String[]> resultList = new ArrayList<String[]>();
		Connection conn;
		PreparedStatement ps;
		try {
			
			//String query = "select * from REPORT_INDEX_TABLE where REPORT_ID=? and YEAR=? order by DB_TABLE asc";
			String query = "select *from REPORT_INDEX_TABLE where REPORT_ID LIKE ? and YEAR=? order by DB_TABLE asc";
			conn = dataTableDao.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, "%" + id + "%");
			ps.setString(2, year);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
			String[] arr = new String[3];
			arr[0] = StringEscapeUtils.escapeHtml4(rs.getString("REPORT_ID"));
			arr[1] = StringEscapeUtils.escapeHtml4(rs.getString("YEAR"));
			arr[2] = StringEscapeUtils.escapeHtml4(rs.getString("DB_TABLE"));
			resultList.add(arr);
			}
			conn.close();
			ps.close();
			} catch (Exception e) {
			System.out.println(e);
			}
			return resultList;
			}
	}


	

