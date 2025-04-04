package com.code2java.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.code2java.dao.DataTableDAO;

@Service("histproptaxRegService")
public class HistPropTaxIncomeTaxServiceImpl implements HistPropTaxIncomeTaxService{

	@Autowired
	private DataTableDAO dataTableDao;
	
		public SXSSFWorkbook getHistPropTaxExcelData(HttpServletRequest request){
			
			//report handling starts//
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
			header.createCell(3).setCellValue("Entity");
			header.createCell(4).setCellValue("Company");		
			header.createCell(5).setCellValue("Unique Asst No");
			header.createCell(6).setCellValue("Instance No");
			header.createCell(7).setCellValue("Internal Asst No");
			header.createCell(8).setCellValue("Asset Primary");
			header.createCell(9).setCellValue("Component");
			header.createCell(10).setCellValue("Asset Description");
			header.createCell(11).setCellValue("State");
			header.createCell(12).setCellValue("Physical Location");
			header.createCell(13).setCellValue("Country");
			header.createCell(14).setCellValue("State");
			header.createCell(15).setCellValue("County");
			header.createCell(16).setCellValue("City");
			header.createCell(17).setCellValue("Street");
			header.createCell(18).setCellValue("Zip Code");
			header.createCell(19).setCellValue("Pl Description");
			header.createCell(20).setCellValue("Entered Date");
			header.createCell(21).setCellValue("Entered Year");
			header.createCell(22).setCellValue("Accounting Location");
			header.createCell(23).setCellValue("Al Description");
			header.createCell(24).setCellValue("Gl Description");
			header.createCell(25).setCellValue("Corporate Life");		
			header.createCell(26).setCellValue("Install Date");
			header.createCell(27).setCellValue("Install Year");
			header.createCell(28).setCellValue("Prorate Code");
			header.createCell(29).setCellValue("Depreciation Table");
			header.createCell(30).setCellValue("Remaining Life");
			header.createCell(31).setCellValue("Org First Cost");
			header.createCell(32).setCellValue("Corp Book Cost Basis");
			header.createCell(33).setCellValue("Corp Book Accum Rsrv ");
			header.createCell(34).setCellValue("Net Book Value");
			header.createCell(35).setCellValue("Corp Ytd Depr");
			header.createCell(36).setCellValue("Corp Book Cur Depr");
			header.createCell(37).setCellValue("Last Yr Depreciated");
			header.createCell(38).setCellValue("Last Mth Depreciated");
			header.createCell(39).setCellValue("Total Retirement Cost");
			header.createCell(40).setCellValue("Fully Retired");
			header.createCell(41).setCellValue("Accounting Year");
			header.createCell(42).setCellValue("Accounting Period");
			header.createCell(43).setCellValue("Tax Install Date");
			header.createCell(44).setCellValue("Tax Cost Basis");
			header.createCell(45).setCellValue("Depr Expense Acct");
			header.createCell(46).setCellValue("Asset Acct");
			header.createCell(47).setCellValue("Accm Reserve Acct");
			header.createCell(48).setCellValue("Purchases Acct");
			header.createCell(49).setCellValue("Net Proceeds Acct");
			header.createCell(50).setCellValue("Ord Gain/Loss Acct");
			header.createCell(51).setCellValue("Extraord Gl Acct");
			header.createCell(52).setCellValue("Transfers In Acct");
			header.createCell(53).setCellValue("Transfers Out Acct");
			header.createCell(54).setCellValue("Trnsr Acct For Asset");
			header.createCell(55).setCellValue("Trnsr Acct For AD");
			header.createCell(56).setCellValue("Ret Acct For Asset");
			header.createCell(57).setCellValue("Ret Acct For AD");
			header.createCell(58).setCellValue("Quantity");
			header.createCell(59).setCellValue("Org Internal Asst No");
			header.createCell(60).setCellValue("Org Company");
			header.createCell(61).setCellValue("Asset Status");
			header.createCell(62).setCellValue("User Feild 1");
			header.createCell(63).setCellValue("User Feild 2");
			header.createCell(64).setCellValue("User Feild 3");
			header.createCell(65).setCellValue("User Feild 4");
			header.createCell(66).setCellValue("User Feild 5");
			header.createCell(67).setCellValue("User Feild 6");
			header.createCell(68).setCellValue("User Feild 7");
			header.createCell(69).setCellValue("User Feild 8");
			header.createCell(70).setCellValue("User Feild 9");
			header.createCell(71).setCellValue("User Feild 10");
			header.createCell(72).setCellValue("User Feild 11");
			header.createCell(73).setCellValue("User Feild 12");
			header.createCell(74).setCellValue("User Feild 13");
			header.createCell(75).setCellValue("User Feild 14");
			

			int rowNum = 1;
			
			//report handling ends//
	    String histproptaxfacompanygroup=request.getParameter("facompanygroup");
		String histproptaxfacompany=request.getParameter("facompany");
		String histproptaxInstallDateFrom=request.getParameter("taxInstallDateFrom");
		String histproptaxInstallDateTo=request.getParameter("taxInstallDateTo");
		String histproptaxCreateDateFrom=request.getParameter("taxCreateDateFrom");
		String histproptaxCreateDateTo=request.getParameter("taxCreateDateTo");		
		String histproptaxyear=request.getParameter("year");
		String histproptaxreportId=request.getParameter("reportId");
		String tt=histproptaxreportId.trim();
		
	//	System.out.println("Test excel report Id===="+histproptaxreportId);
		
	//	System.out.println("Test year Id===="+histproptaxyear);
			
		String tableName="";
		//List<String[]> tableNameList=this.getReportTables(request.getParameter("reportId"),request.getParameter("year"));
		List<String[]> tableNameList=this.getReportTables(histproptaxreportId,histproptaxyear);
		for (String[] strings : tableNameList) {
			//System.out.println("query======"+strings[2]);
			String tb=strings[2];
			tableName=tb;
		}
		//System.out.println("tableName==="+tableName);
		
		String table = tableName;


		List<String> sArray = new ArrayList<String>();
		
		int mapi=0;
		Map<String,String> prepareParamMap=new LinkedHashMap<String, String>(); 
		
		if (null!=histproptaxfacompany && !histproptaxfacompany.equals("")) {			
			sArray.add(" PRADD_ENTITY like ?");
			prepareParamMap.put(++mapi+"_"+"like",histproptaxfacompany);
		}
		
		if (null!=histproptaxfacompanygroup && !histproptaxfacompanygroup.equals("")) {			
			sArray.add(" PRADD_ENTITY in (select company from FACOMPANY where companygroup like ?)");
			prepareParamMap.put(++mapi+"_"+"like",histproptaxfacompanygroup);
		}
		
		if (null!=histproptaxInstallDateFrom && !histproptaxInstallDateFrom.equals("")) {			
			String taxInstallQuery=" to_date(PRADD_INSTALL_DATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxInstallDateFrom);
			sArray.add(taxInstallQuery);
		}
		
		if (null!=histproptaxInstallDateTo && !histproptaxInstallDateTo.equals("")) {			
			String taxInstallQuery=" to_date(PRADD_INSTALL_DATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxInstallDateTo);
			sArray.add(taxInstallQuery);
		}
		
		if (null!=histproptaxCreateDateFrom && !histproptaxCreateDateFrom.equals("")) {
			String taxCreateQuery=" to_date(PRADD_ENTERED_DATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') " ;
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxCreateDateFrom);
			sArray.add(taxCreateQuery);
		}
		
		if (null!=histproptaxCreateDateTo && !histproptaxCreateDateTo.equals("")) {
			String taxCreateQuery=" to_date(PRADD_ENTERED_DATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";			
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxCreateDateTo);
			sArray.add(taxCreateQuery);
		}
		
		if (null!=histproptaxyear && !histproptaxyear.equals("")) {
			//sArray.add(" INC_COMPANYCODE like '%" + facompany + "%'");
			sArray.add(" PRADD_YEAR like ?");
			prepareParamMap.put(++mapi+"_"+"like",histproptaxyear);
		}
		
		if (null!=tt && !tt.equals("")) {
			
			sArray.add(" PRADD_REPORT_NAME like ?");
			prepareParamMap.put(++mapi+"_"+"like",tt);
			//prepareParamMap.put(++mapi+"_"+"like","%" + histproptaxreportId + "%");
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
		
		
		
		String searchSQL="select PRADD_YEAR,PRADD_REPORT_NAME,PRADD_INSTANCE,PRADD_ENTITY,PRADD_COMPANY,PRADD_UNIQUE_ASST_NO,PRADD_INSTANCE_NO,PRADD_INTERNAL_ASST_NO,PRADD_ASSET_PRIMARY,PRADD_COMPONENT,PRADD_ASSET_DESCRIPTION,PRADD_STATE,PRADD_PHYSICAL_LOCATION,PRADD_COUNTRY,PRADD_STATE_1,PRADD_COUNTY,PRADD_CITY,PRADD_STREET,PRADD_ZIP_CODE,PRADD_PL_DESCRIPTION,PRADD_ENTERED_DATE,PRADD_ENTERED_YEAR,PRADD_ACCOUNTING_LOCATION,PRADD_AL_DESCRIPTION,PRADD_GL_DESCRIPTION,PRADD_CORPORATE_LIFE,PRADD_INSTALL_DATE,PRADD_INSTALL_YEAR,PRADD_PRORATE_CODE,PRADD_DEPRECIATION_TABLE,PRADD_REMAINING_LIFE,PRADD_ORG_FIRST_COST,PRADD_CORP_BOOK_COST_BASIS,PRADD_CORP_BOOK_ACCUM_RSRV,PRADD_NET_BOOK_VALUE,PRADD_CORP_YTD_DEPR,PRADD_CORP_BOOK_CUR_DEPR,PRADD_LAST_YR_DEPRECIATED,PRADD_LAST_MTH_DEPRECIATED,PRADD_TOTAL_RETIREMNT_COST,PRADD_FULLY_RETIRED,PRADD_ACCOUNTING_YEAR,PRADD_ACCOUNTING_PERIOD,PRADD_TAX_INSTALL_DATE, PRADD_TAX_COST_BASIS,PRADD_DEPR_EXPENSE_ACCT,PRADD_ASSET_ACCT,PRADD_ACCM_RESERVE_ACCT,PRADD_PURCHASES_ACCT,PRADD_NET_PROCEEDS_ACCT,PRADD_ORD_GAIN_OR_LOSS_ACCT,PRADD_EXTRAORD_GL_ACCT,PRADD_TRANSFERS_IN_ACCT,PRADD_TRANSFERS_OUT_ACCT,PRADD_TRNSR_ACCT_FOR_ASSET,PRADD_TRNSR_ACCT_FOR_AD,PRADD_RET_ACCT_FOR_ASSET,PRADD_RET_ACCT_FOR_AD,PRADD_QUANTITY,PRADD_ORG_INTERNAL_ASST_NO,PRADD_ORG_COMPANY,PRADD_ASSET_STATUS,PRADD_USER_FIELD_1,PRADD_USER_FIELD_2,PRADD_USER_FIELD_3,PRADD_USER_FIELD_4,PRADD_USER_FIELD_5,PRADD_USER_FIELD_6,PRADD_USER_FIELD_7,PRADD_USER_FIELD_8,PRADD_USER_FIELD_9,PRADD_USER_FIELD_10,PRADD_USER_FIELD_11,PRADD_USER_FIELD_12,PRADD_USER_FIELD_13,PRADD_USER_FIELD_14 from HIST_LEGACY_PROPTAX_REG_PRADD"; 				  

		
		
		
		if(individualSearch!=null && individualSearch!=""){
			searchSQL = searchSQL+" where " + individualSearch;
			
		}
		
		searchSQL += " order by PRADD_UNIQUE_ASST_NO asc";

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
			while (rs2.next()) {
				counter++;
				List<String> resultRowData = new ArrayList<String>();
				//System.out.println(resultRowData);
				for (int i = 1; i <=76; i++) {	
					
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
							if(StringUtils.isNotBlank(entry) && (cellNum==31 || cellNum==32 || cellNum==33 || cellNum==34|| cellNum==35 
									|| cellNum==36 || cellNum==39 || cellNum==44 )) {
												row.createCell(cellNum++).setCellValue(Double.parseDouble(entry));
											}
							else {
							row.createCell(cellNum++).setCellValue(entry);
							}
						}
					}
				//	System.out.println("cleared"+counter);
			//		System.out.println("size before"+resultDataList.size());
					
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
					if(StringUtils.isNotBlank(entry) && (cellNum==31 || cellNum==32 || cellNum==33 || cellNum==34|| cellNum==35 
							|| cellNum==36 || cellNum==39 || cellNum==44 )) {
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
		 resultDataList = new ArrayList<List<String>>(1);
		 //excel download code ends//
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
public String getHistPropTaxDataTableResponse(HttpServletRequest request) {

		
//		String[] cols = {"INC_UNIQUEASSET","INC_COMPANYCODE","INC_ASSETNUMBER","INC_SUBNUMBER","INC_ASSETDESCRIPTION","INC_ASSETCLASSS","INC_ASSETCLASSDESCRIPTION","INC_LEGACYCATEGORYCODE","INC_ENTEREDDATE","INC_INSTALLDATE","INC_TAXINSTALLATIONYR","INC_ASSETLIFE","INC_REMAININGLIFE","INC_TAXAPC","INC_DEPRECIABLEBASIS","INC_ACCUMULATEDDEPRECIATION","INC_YEARTODATEDEPREC","INC_TAXNBV","INC_BONUSDEPRECIATION","INC_TAXYTDBONUSDEPR","INC_ELECTOUTBONUS","INC_TAXBONUSPERCENTAGE","INC_FIRSTYEARDEPRECIATED","INC_LASTYEARDEPRECIATED",
//				"INC_LASTMONTHDEPRECIATED","INC_NEWORUSED","INC_TAXDEPRMETHOD","INC_TAXDEPRCONVENTION","INC_CURRENTACCOUNTINGYEAR","INC_CORPINSTALLDATE","INC_CORPLIFE","INC_CORPBOOKAPC","INC_CORPBOOKACCUMRESERVE","INC_CORPNBV","INC_CREATIONDATE","INC_CREATIONTIME","INC_CREATIONUSER"};
		String[] cols = {"PRADD_YEAR","PRADD_REPORT_NAME","PRADD_INSTANCE","PRADD_ENTITY","PRADD_COMPANY","PRADD_UNIQUE_ASST_NO","PRADD_INSTANCE_NO","PRADD_INTERNAL_ASST_NO","PRADD_ASSET_PRIMARY","PRADD_COMPONENT","PRADD_ASSET_DESCRIPTION","PRADD_STATE","PRADD_PHYSICAL_LOCATION","PRADD_COUNTRY","PRADD_STATE_1","PRADD_COUNTY","PRADD_CITY","PRADD_STREET","PRADD_ZIP_CODE","PRADD_PL_DESCRIPTION","PRADD_ENTERED_DATE","PRADD_ENTERED_YEAR","PRADD_ACCOUNTING_LOCATION","PRADD_AL_DESCRIPTION","PRADD_GL_DESCRIPTION","PRADD_CORPORATE_LIFE","PRADD_INSTALL_DATE","PRADD_INSTALL_YEAR","PRADD_PRORATE_CODE","PRADD_DEPRECIATION_TABLE","PRADD_REMAINING_LIFE","PRADD_ORG_FIRST_COST","PRADD_CORP_BOOK_COST_BASIS","PRADD_CORP_BOOK_ACCUM_RSRV","PRADD_NET_BOOK_VALUE","PRADD_CORP_YTD_DEPR","PRADD_CORP_BOOK_CUR_DEPR","PRADD_LAST_YR_DEPRECIATED","PRADD_LAST_MTH_DEPRECIATED","PRADD_TOTAL_RETIREMNT_COST","PRADD_FULLY_RETIRED","PRADD_ACCOUNTING_YEAR","PRADD_ACCOUNTING_PERIOD","PRADD_TAX_INSTALL_DATE"," PRADD_TAX_COST_BASIS","PRADD_DEPR_EXPENSE_ACCT","PRADD_ASSET_ACCT","PRADD_ACCM_RESERVE_ACCT","PRADD_PURCHASES_ACCT","PRADD_NET_PROCEEDS_ACCT","PRADD_ORD_GAIN_OR_LOSS_ACCT","PRADD_EXTRAORD_GL_ACCT","PRADD_TRANSFERS_IN_ACCT","PRADD_TRANSFERS_OUT_ACCT","PRADD_TRNSR_ACCT_FOR_ASSET","PRADD_TRNSR_ACCT_FOR_AD","PRADD_RET_ACCT_FOR_ASSET","PRADD_RET_ACCT_FOR_AD","PRADD_QUANTITY","PRADD_ORG_INTERNAL_ASST_NO","PRADD_ORG_COMPANY","PRADD_ASSET_STATUS","PRADD_USER_FIELD_1","PRADD_USER_FIELD_2","PRADD_USER_FIELD_3","PRADD_USER_FIELD_4","PRADD_USER_FIELD_5","PRADD_USER_FIELD_6","PRADD_USER_FIELD_7","PRADD_USER_FIELD_8","PRADD_USER_FIELD_9","PRADD_USER_FIELD_10","PRADD_USER_FIELD_11","PRADD_USER_FIELD_12","PRADD_USER_FIELD_13","PRADD_USER_FIELD_14"};	
		String histproptaxyear=request.getParameter("year");
		String histproptaxreportId=request.getParameter("reportId");
		String tableName="";
		//List<String[]> tableNameList=this.getReportTables(request.getParameter("reportId"),request.getParameter("year"));
		List<String[]> tableNameList=this.getReportTables(histproptaxreportId,histproptaxyear);
		for (String[] strings : tableNameList) {
			//System.out.println("query======"+strings[2]);
			String tb=strings[2];
			tableName=tb;
		}
		//System.out.println("tableName==="+tableName);
		
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
		
		String histproptaxfacompanygroup=request.getParameter("facompanygroup");
		String histproptaxfacompany=request.getParameter("facompany");
		String histproptaxInstallDateFrom=request.getParameter("taxInstallDateFrom");
		String histproptaxInstallDateTo=request.getParameter("taxInstallDateTo");
		String histproptaxCreateDateFrom=request.getParameter("taxCreateDateFrom");
		String histproptaxCreateDateTo=request.getParameter("taxCreateDateTo");
		
		
		
		
		//System.out.println("Murali  Screen year======"+histproptaxyear);
		//System.out.println("Murali Screen year======"+histproptaxreportId);
		
		

		List<String> sArray = new ArrayList<String>();
		
		int mapi=0;
		Map<String,String> prepareParamMap=new LinkedHashMap<String, String>();
		
		if (null!=histproptaxfacompany && !histproptaxfacompany.equals("")) {
			//sArray.add(" INC_COMPANYCODE like '%" + facompany + "%'");
			sArray.add(" PRADD_ENTITY like ?");
			prepareParamMap.put(++mapi+"_"+"like",histproptaxfacompany);
		}
		
		if (null!=histproptaxfacompanygroup && !histproptaxfacompanygroup.equals("")) {
			//sArray.add(" INC_COMPANYCODE in (select company from FACOMPANY where companygroup like '%" + facompanygroup + "%')");	
			sArray.add(" PRADD_ENTITY in (select company from FACOMPANY where companygroup like ?)");
			prepareParamMap.put(++mapi+"_"+"like",histproptaxfacompanygroup);
		}
		
		if (null!=histproptaxInstallDateFrom && !histproptaxInstallDateFrom.equals("")) {
			String taxInstallQuery=" to_date(PRADD_INSTALL_DATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
			
			//taxInstallQuery=taxInstallQuery.replace("taxInsFromParam", taxInstallDateFrom);
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxInstallDateFrom);
			sArray.add(taxInstallQuery);
		}
		
		if (null!=histproptaxInstallDateTo && !histproptaxInstallDateTo.equals("")) {
			String taxInstallQuery=" to_date(PRADD_INSTALL_DATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			
			//taxInstallQuery=taxInstallQuery.replace("taxInsToParam", taxInstallDateTo);
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxInstallDateTo);
			sArray.add(taxInstallQuery);
		}
		
		if (null!=histproptaxCreateDateFrom && !histproptaxCreateDateFrom.equals("")) {
			String taxCreateQuery=" to_date(PRADD_ENTERED_DATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') " ;
			
			//taxInstallQuery=taxInstallQuery.replace("taxCreFromParam", taxCreateDateFrom);
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxCreateDateFrom);
			sArray.add(taxCreateQuery);
		}
		
		if (null!=histproptaxCreateDateTo && !histproptaxCreateDateTo.equals("")) {
			String taxCreateQuery=" to_date(PRADD_ENTERED_DATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			
			//taxInstallQuery=taxInstallQuery.replace("taxCreToParam", taxCreateDateTo);
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxCreateDateTo);
			sArray.add(taxCreateQuery);
		}
		
		///murali
		
		if (null!=histproptaxyear && !histproptaxyear.equals("")) {
			//sArray.add(" INC_COMPANYCODE like '%" + facompany + "%'");
			sArray.add(" PRADD_YEAR like ?");
			prepareParamMap.put(++mapi+"_"+"like",histproptaxyear);
		}
		
	if (null!=histproptaxreportId && !histproptaxreportId.equals("")) {
			
			
			sArray.add(" PRADD_REPORT_NAME like ?");
			prepareParamMap.put(++mapi+"_"+"like",histproptaxreportId);
			//prepareParamMap.put(++mapi+"_"+"like","%" + histproptaxreportId + "%");
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
//		try 
//		{
//			String sql = "SELECT count(*) FROM "+ table +  " WHERE REGEXP_LIKE (INC_INSTALLDATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$') and ROWNUM <= 200";
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ResultSet rs = ps.executeQuery();
//			if(rs.next())
//			{
//				total = rs.getInt("count(*)");
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}

		/* This is total number of records that is available for the specific search query */
		int totalAfterFilter = total;

		try {
			String searchSQL = "";
			String sql = "SELECT * FROM HIST_LEGACY_PROPTAX_REG_PRADD";
			//System.out.println("ENTER SEARCH==="+sql);
			String searchTerm = request.getParameter("sSearch");
			String globeSearch =  "where ( upper(PRADD_YEAR) like upper(?)"
					+" or upper(PRADD_REPORT_NAME) like upper(?)"
					+" or upper(PRADD_INSTANCE) like upper(?)"
					+" or upper(PRADD_ENTITY) like upper(?)"
					+" or upper(PRADD_COMPANY) like upper(?)"
					+" or upper(PRADD_UNIQUE_ASST_NO) like upper(?)"
					+" or upper(PRADD_INSTANCE_NO) like upper(?)"
					+" or upper(PRADD_INTERNAL_ASST_NO) like upper(?)"
					+" or upper(PRADD_ASSET_PRIMARY) like upper(?)"
					+" or upper(PRADD_COMPONENT) like upper(?)"
					+" or upper(PRADD_ASSET_DESCRIPTION) like upper(?)"
					+" or upper(PRADD_STATE) like upper(?)"
					+" or upper(PRADD_PHYSICAL_LOCATION) like upper(?)"
					+" or upper(PRADD_COUNTRY) like upper(?)"
					+" or upper(PRADD_STATE_1) like upper(?)"
					+" or upper(PRADD_COUNTY) like upper(?)"
					+" or upper(PRADD_CITY) like upper(?)"
					+" or upper(PRADD_STREET) like upper(?)"
					+" or upper(PRADD_ZIP_CODE) like upper(?)"
					+" or upper(PRADD_PL_DESCRIPTION) like upper(?)"
					+" or upper(PRADD_ENTERED_DATE) like upper(?)"
					+" or upper(PRADD_ENTERED_YEAR) like upper(?)"
					+" or upper(PRADD_ACCOUNTING_LOCATION) like upper(?)"
					+" or upper(PRADD_AL_DESCRIPTION) like upper(?)"
					+" or upper(PRADD_GL_DESCRIPTION) like upper(?)"
					+" or upper(PRADD_CORPORATE_LIFE) like upper(?)"
					+" or upper(PRADD_INSTALL_DATE) like upper(?)"
					+" or upper(PRADD_INSTALL_YEAR) like upper(?)"
					+" or upper(PRADD_PRORATE_CODE) like upper(?)"
					+" or upper(PRADD_DEPRECIATION_TABLE) like upper(?)"
					+" or upper(PRADD_REMAINING_LIFE) like upper(?)"
					+" or upper(PRADD_ORG_FIRST_COST) like upper(?)"
					+" or upper(PRADD_CORP_BOOK_COST_BASIS) like upper(?)"
					+" or upper(PRADD_CORP_BOOK_ACCUM_RSRV) like upper(?)"
					+" or upper(PRADD_NET_BOOK_VALUE) like upper(?)"
					+" or upper(PRADD_CORP_YTD_DEPR) like upper(?)"
					+" or upper(PRADD_CORP_BOOK_CUR_DEPR) like upper(?)"
					+" or upper(PRADD_LAST_YR_DEPRECIATED) like upper(?)"
					+" or upper(PRADD_LAST_MTH_DEPRECIATED) like upper(?)"
					+" or upper(PRADD_TOTAL_RETIREMNT_COST) like upper(?)"
					+" or upper(PRADD_FULLY_RETIRED) like upper(?)"
					+" or upper(PRADD_ACCOUNTING_YEAR) like upper(?)"
					+" or upper(PRADD_ACCOUNTING_PERIOD) like upper(?)"
					+" or upper(PRADD_TAX_INSTALL_DATE) like upper(?)"
					+" or upper(PRADD_TAX_COST_BASIS) like upper(?)"
					+" or upper(PRADD_DEPR_EXPENSE_ACCT) like upper(?)"
					+" or upper(PRADD_ASSET_ACCT) like upper(?)"
					+" or upper(PRADD_ACCM_RESERVE_ACCT) like upper(?)"
					+" or upper(PRADD_PURCHASES_ACCT) like upper(?)"
					+" or upper(PRADD_NET_PROCEEDS_ACCT) like upper(?)"
					+" or upper(PRADD_ORD_GAIN_OR_LOSS_ACCT) like upper(?)"
					+" or upper(PRADD_EXTRAORD_GL_ACCT) like upper(?)"
					+" or upper(PRADD_TRANSFERS_IN_ACCT) like upper(?)"
					+" or upper(PRADD_TRANSFERS_OUT_ACCT) like upper(?)"
					+" or upper(PRADD_TRNSR_ACCT_FOR_ASSET) like upper(?)"
					+" or upper(PRADD_TRNSR_ACCT_FOR_AD) like upper(?)"
					+" or upper(PRADD_RET_ACCT_FOR_ASSET) like upper(?)"
					+" or upper(PRADD_RET_ACCT_FOR_AD) like upper(?)"
					+" or upper(PRADD_QUANTITY) like upper(?)"
					+" or upper(PRADD_ORG_INTERNAL_ASST_NO) like upper(?)"
					+" or upper(PRADD_ORG_COMPANY) like upper(?)"
					+" or upper(PRADD_ASSET_STATUS) like upper(?)"
					+" or upper(PRADD_USER_FIELD_1) like upper(?)"
					+" or upper(PRADD_USER_FIELD_2) like upper(?)"
					+" or upper(PRADD_USER_FIELD_3) like upper(?)"
					+" or upper(PRADD_USER_FIELD_4) like upper(?)"
					+" or upper(PRADD_USER_FIELD_5) like upper(?)"
					+" or upper(PRADD_USER_FIELD_6) like upper(?)"
					+" or upper(PRADD_USER_FIELD_7) like upper(?)"
					+" or upper(PRADD_USER_FIELD_8) like upper(?)"
					+" or upper(PRADD_USER_FIELD_9) like upper(?)"
					+" or upper(PRADD_USER_FIELD_10) like upper(?)"
					+" or upper(PRADD_USER_FIELD_11) like upper(?)"
					+" or upper(PRADD_USER_FIELD_12) like upper(?)"
					+" or upper(PRADD_USER_FIELD_13) like upper(?)"
					+" or upper(PRADD_USER_FIELD_14) like upper(?))";

			
			
			
			
			if(searchTerm!=null && searchTerm!="" && individualSearch!=null && individualSearch!=""){
				searchSQL = globeSearch + " and " + individualSearch;
			}
			else if(individualSearch!=null && individualSearch!=""){
				searchSQL = " where " + individualSearch;
			}else if(null!=searchTerm && searchTerm!=""){
				searchSQL=globeSearch;
			}
			sql += searchSQL;
			//System.out.println("search sql==="+searchSQL);
			if(sql.contains("WHERE")||sql.contains("where")){
				sql=sql+" and ROWNUM <= 200";
						//+ "and REGEXP_LIKE (TXADD_TAX_INSTALL_DATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
			}else {
				sql=sql+" WHERE ROWNUM <= 200";
					//	+ "and REGEXP_LIKE (TXADD_TAX_INSTALL_DATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
			}
			
			//sql += " order by " + colName + " " + dir;
			sql += " order by PRADD_UNIQUE_ASST_NO asc";
			
			//System.out.println("sqlqsql==="+sql);
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
			//System.out.println("size====="+rs.getFetchSize());
			int i=0;
			Date date1;
			while (rs.next()) {
				JSONArray ja = new JSONArray();
				//System.out.println("enter report==="+rs.getString("PRADD_YEAR"));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_YEAR")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_REPORT_NAME")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_INSTANCE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_ENTITY")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_COMPANY")));			
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_UNIQUE_ASST_NO")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_INSTANCE_NO")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_INTERNAL_ASST_NO")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_ASSET_PRIMARY")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_COMPONENT")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_ASSET_DESCRIPTION")));				
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_STATE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_PHYSICAL_LOCATION")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_COUNTRY")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_STATE_1"))); 
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_COUNTY")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_CITY")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_STREET")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_ZIP_CODE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_PL_DESCRIPTION")));				
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_ENTERED_DATE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_ENTERED_YEAR")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_ACCOUNTING_LOCATION")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_AL_DESCRIPTION")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_GL_DESCRIPTION")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_CORPORATE_LIFE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_INSTALL_DATE")));				
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_INSTALL_YEAR")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_PRORATE_CODE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_DEPRECIATION_TABLE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_REMAINING_LIFE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_ORG_FIRST_COST")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_CORP_BOOK_COST_BASIS")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_CORP_BOOK_ACCUM_RSRV")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_NET_BOOK_VALUE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_CORP_YTD_DEPR")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_CORP_BOOK_CUR_DEPR")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_LAST_YR_DEPRECIATED")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_LAST_MTH_DEPRECIATED")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_TOTAL_RETIREMNT_COST")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_FULLY_RETIRED")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_ACCOUNTING_YEAR")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_ACCOUNTING_PERIOD")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_TAX_INSTALL_DATE")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_TAX_COST_BASIS")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_DEPR_EXPENSE_ACCT")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_ASSET_ACCT")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_ACCM_RESERVE_ACCT")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_PURCHASES_ACCT")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_NET_PROCEEDS_ACCT")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_ORD_GAIN_OR_LOSS_ACCT")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_EXTRAORD_GL_ACCT")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_TRANSFERS_IN_ACCT")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_TRANSFERS_OUT_ACCT")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_TRNSR_ACCT_FOR_ASSET")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_TRNSR_ACCT_FOR_AD")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_RET_ACCT_FOR_ASSET")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_RET_ACCT_FOR_AD")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_QUANTITY")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_ORG_INTERNAL_ASST_NO")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_ORG_COMPANY")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_ASSET_STATUS")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_USER_FIELD_1")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_USER_FIELD_2")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_USER_FIELD_3")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_USER_FIELD_4")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_USER_FIELD_5")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_USER_FIELD_6")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_USER_FIELD_7")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_USER_FIELD_8")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_USER_FIELD_9")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_USER_FIELD_10")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_USER_FIELD_11")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_USER_FIELD_12")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_USER_FIELD_13")));
				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PRADD_USER_FIELD_14")));
				
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
	
	public List<String[]> getHistPropTaxCompanyGroups() {
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
	public List<String[]> getHistPropTaxInstances() {
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
	public List<String[]> getHistPropTaxYears() {
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
	
	public List<String[]> getHistPropTaxReportNames(List<String> reportList) {
		//System.out.println("report Murli====="+reportList.get(0));
		//System.out.println("report Murli====="+reportList.get(1));
		List<String[]> resultList = new ArrayList<String[]>();
		Connection conn;
		PreparedStatement ps;
		try {
			
			String query = "select * from REPORT_INDEX_TABLE where YEAR=? AND LAYOUT='PSTREG' order by REPORT_NAME asc";
			
		//	String query = "select * from REPORT_INDEX_TABLE where YEAR='2018' AND LAYOUT='ASTREG' order by REPORT_NAME asc";
			conn = dataTableDao.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, reportList.get(0));
			//System.out.println("report Murli====="+reportList.get(0));
		//	ps.setString(2, reportList.get(1));
			ResultSet rs = ps.executeQuery();
			//System.out.println("report Murli array 1111++++====="+rs.getFetchSize());
			while (rs.next()) {
				
				String[] arr = new String[2];
				//arr[0] = StringEscapeUtils.escapeHtml4(rs.getString("REPORT_NAME"));
			//arr[0]=	rs.getString("REPORT_NAME");
				arr[0] = StringEscapeUtils.escapeHtml4(rs.getString("REPORT_ID"));
				arr[1] = StringEscapeUtils.escapeHtml4(rs.getString("REPORT_NAME"));
				//System.out.println("report Murli array ++++====="+arr[0]);
			
				resultList.add(arr);
			}
			conn.close();
			ps.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return resultList;
	}
	
	
	
//	public List<String[]> getLoadReportNames() {
//		System.out.println("DAO Imal Murali=====");
//	//	System.out.println("report Murli====="+Layout.get(1));
//		List<String[]> resultList = new ArrayList<String[]>();
//		Connection conn;
//		PreparedStatement ps;
//		try {
//			
//			String query = "select * from REPORT_INDEX_TABLE where LAYOUT='ASTREG' order by REPORT_NAME asc";
//			
//		//	String query = "select * from REPORT_INDEX_TABLE where YEAR='2018' AND LAYOUT='ASTREG' order by REPORT_NAME asc";
//			conn = dataTableDao.getConnection();
//			ps = conn.prepareStatement(query);
//			//ps.setString(1, "ALL");
//			//System.out.println("report Layout====="Layout);
//		//	ps.setString(2, reportList.get(1));
//			ResultSet rs = ps.executeQuery();
//			System.out.println("report Murli array 1111++++====="+rs.getFetchSize());
//			while (rs.next()) {
//				
//				String[] arr = new String[2];
//				//arr[0] = StringEscapeUtils.escapeHtml4(rs.getString("REPORT_NAME"));
//			//arr[0]=	rs.getString("REPORT_NAME");
//				arr[0] = StringEscapeUtils.escapeHtml4(rs.getString("REPORT_ID"));
//				arr[1] = StringEscapeUtils.escapeHtml4(rs.getString("REPORT_NAME"));
//				System.out.println("report Murli array ++++====="+arr[0]);
//				System.out.println("report Murli array ++++====="+arr[1]);
//			
//				resultList.add(arr);
//			}
//			conn.close();
//			ps.close();
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		return resultList;
//	}
//	
	
	
	
	
	
	
	
	public List<String[]> getHistPropTaxCompanyNames(String COMPANYGROUP) {
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
	public List<String[]> getReportTables(String id,String year) {
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
