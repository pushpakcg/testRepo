package com.code2java.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
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

@Service("SAPhistproptaxRegService")
public class SAPHistoricalproptaxserviceimpl implements SAPHistoricalpropertytaxregService{

	@Autowired
	private DataTableDAO dataTableDao;
	//changed returntype to workbook
		//public List<List<String>> getSAPHistoricalPropertyTaxExcelData(HttpServletRequest request){
	public SXSSFWorkbook getSAPHistoricalPropertyTaxExcelData(HttpServletRequest request){
		
		//adding report handler files here
		SXSSFWorkbook workbook = null;
		Sheet reportSheet = null;
		workbook = new SXSSFWorkbook(100);
		reportSheet = workbook.createSheet("Reports");
		Row header = reportSheet.createRow(0);
		header.setHeight((short) 500);
		Row dataRow = null;
		
		/*
		 * header.createCell(0).setCellValue("Year");
		 * header.createCell(1).setCellValue("Report_Name");
		 * header.createCell(2).setCellValue("Entity");
		 * header.createCell(3).setCellValue("Unique Asset Number");
		 * header.createCell(4).setCellValue("Asset Number");
		 * header.createCell(5).setCellValue("Asset Sub Number");
		 * header.createCell(6).setCellValue("Internal Asset Number");
		 * header.createCell(7).setCellValue("Legacy Asset Number");
		 * header.createCell(8).setCellValue("Legacy Asset Sub Number");
		 * 
		 * header.createCell(9).setCellValue("Asset Description");
		 * header.createCell(10).setCellValue("Country");
		 * header.createCell(11).setCellValue("State");
		 * header.createCell(12).setCellValue("County");
		 * header.createCell(13).setCellValue("City");
		 * header.createCell(14).setCellValue("Street");
		 * header.createCell(15).setCellValue("ZIP Code");
		 * header.createCell(16).setCellValue("Building Number");
		 * header.createCell(17).setCellValue("Physical Location Desc");
		 * header.createCell(18).setCellValue("Entered Date");
		 * header.createCell(19).setCellValue("Entered Year");
		 * header.createCell(20).setCellValue("Asset Class");
		 * header.createCell(21).setCellValue("Asset Class Description");
		 * header.createCell(22).setCellValue("GL Category Desc");
		 * header.createCell(23).setCellValue("Corporate Life");
		 * header.createCell(24).setCellValue("Install Date");
		 * header.createCell(25).setCellValue("Install Year");
		 * header.createCell(26).setCellValue("Corp Book Cost Basis");
		 * header.createCell(27).setCellValue("Corp Book Accumulated Depr");
		 * header.createCell(28).setCellValue("Corp Book NBV");
		 * //header.createCell25).setCellValue("PROP_LAST_YR_DEPRECIATED");
		 * //header.createCell(26).setCellValue("PROP_LAST_MTH_DEPRECIATED");
		 * header.createCell(29).setCellValue("Accounting Year");
		 * header.createCell(30).setCellValue("Tax Install Date");
		 * header.createCell(31).setCellValue("Tax Cost Basis");
		 * header.createCell(32).setCellValue("Tax NTV");
		 * header.createCell(33).setCellValue("Park Asset_Property Tax");
		 * //header.createCell(30).setCellValue("PROP_ACCOUNTING_PERIOD");
		 * header.createCell(34).setCellValue("Invoice Number");
		 * header.createCell(35).setCellValue("Project Number");
		 * header.createCell(36).setCellValue("Purchase Order");
		 * header.createCell(37).setCellValue("WBSE");
		 * header.createCell(38).setCellValue("Profit Center");
		 * header.createCell(39).setCellValue("Cost Center");
		 * header.createCell(40).setCellValue("Manufacture");
		 * header.createCell(41).setCellValue("Vendor");
		 * header.createCell(42).setCellValue("Attraction ID");
		 * header.createCell(43).setCellValue("Geo Location");
		 * header.createCell(44).setCellValue("Department");
		 * header.createCell(45).setCellValue("Company Name");
		 * header.createCell(46).setCellValue("Serial number");
		 * header.createCell(47).setCellValue("Inventory number");
		 * header.createCell(48).setCellValue("Quantity");
		 * //header.createCell(49).setCellValue("Physical Location C/O Name");
		 * header.createCell(49).setCellValue("Original Asset Number");
		 * header.createCell(50).setCellValue("Type name");
		 * header.createCell(51).setCellValue("Legacy Project No");
		 * header.createCell(52).setCellValue("Tracking Number");
		 */
		
		String[] headers=null;
		List<String[]> headerList=getTableHeadings(request);
		int count=0;
		for (String[] strings : headerList) {
		    headers=strings;
		    header.createCell(count).setCellValue(headers[0]);
		   count++;
		}
		
		int rowNum = 1;
		
		// seperate section --process data from frontend
		String histproptaxfacompanygroup=request.getParameter("facompanygroup");
		String histproptaxfacompany=request.getParameter("facompany");
		String histproptaxInstallDateFrom=request.getParameter("taxInstallDateFrom");
		String histproptaxInstallDateTo=request.getParameter("taxInstallDateTo");
		String histproptaxCreateDateFrom=request.getParameter("taxCreateDateFrom");
		String histproptaxCreateDateTo=request.getParameter("taxCreateDateTo");		
		String histproptaxyear=request.getParameter("year");
		String histproptaxreportId=request.getParameter("reportId");
		String tt=histproptaxreportId.trim();
			
		String tableName="";
		//List<String[]> tableNameList=this.getReportTables(request.getParameter("reportId"),request.getParameter("year"));
		List<String[]> tableNameList=this.getReportTables(histproptaxreportId,histproptaxyear);
		for (String[] strings : tableNameList) {
			//System.out.println("query======"+strings[2]);
			String tb=strings[2];
			tableName=tb;
		}
		//System.out.println("tableName==="+tableName);
		
		//String table = tableName.replace("'", "");
		String table = tableName.replaceAll("[^a-zA-Z0-9_]", "");

		List<String> sArray = new ArrayList<String>();
		
		int mapi=0;
		Map<String,String> prepareParamMap=new LinkedHashMap<String, String>(); 
		
		if (null!=histproptaxfacompany && !histproptaxfacompany.equals("")) {			
			sArray.add(" PROP_ENTITY like ?");
			prepareParamMap.put(++mapi+"_"+"like",histproptaxfacompany);
		}
		
		if (null!=histproptaxfacompanygroup && !histproptaxfacompanygroup.equals("")) {			
			sArray.add(" PROP_ENTITY in (select company from FACOMPANY where companygroup like ?)");
			prepareParamMap.put(++mapi+"_"+"like",histproptaxfacompanygroup);
		}
		
		if (null!=histproptaxInstallDateFrom && !histproptaxInstallDateFrom.equals("")) {			
			String taxInstallQuery=" to_date(case when PROP_INSTALL_DATE= '00000000' then null else PROP_INSTALL_DATE end,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxInstallDateFrom);
			sArray.add(taxInstallQuery);
		}
		
		if (null!=histproptaxInstallDateTo && !histproptaxInstallDateTo.equals("")) {			
			String taxInstallQuery=" to_date(case when PROP_INSTALL_DATE= '00000000' then null else PROP_INSTALL_DATE end,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxInstallDateTo);
			sArray.add(taxInstallQuery);
		}
		
		if (null!=histproptaxCreateDateFrom && !histproptaxCreateDateFrom.equals("")) {
			String taxCreateQuery=" to_date(PROP_ENTERED_DATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') " ;
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxCreateDateFrom);
			sArray.add(taxCreateQuery);
		}
		
		if (null!=histproptaxCreateDateTo && !histproptaxCreateDateTo.equals("")) {
			String taxCreateQuery=" to_date(PROP_ENTERED_DATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";			
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxCreateDateTo);
			sArray.add(taxCreateQuery);
		}
		
		if (null!=histproptaxyear && !histproptaxyear.equals("")) {
			//sArray.add(" INC_COMPANYCODE like '%" + facompany + "%'");
			sArray.add(" YEAR like ?");
			prepareParamMap.put(++mapi+"_"+"like",histproptaxyear);
		}
		
//		if (null!=tt && !tt.equals("")) {
//			
//			sArray.add(" REPORT_NAME like ?");
//			prepareParamMap.put(++mapi+"_"+"like",tt);
//			//prepareParamMap.put(++mapi+"_"+"like","%" + histproptaxreportId + "%");
//		}
//		
		
				
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
		
		
		
//		String searchSQL="select YEAR, REPORT_NAME,PROP_ENTITY,PROP_UNIQUE_ASSTNO,PROP_ASSET_NUMBER,PROP_ASSET_SUB_NUMBER,PROP_INTERNAL_ASST_NO,PROP_LEGACY_ASSET_NUMBER,PROP_LEGACY_ASSET_SUBNUMBER,PROP_ASSET_DESCRIPTION,PROP_COUNTRY,\r\n" + 
//					"PROP_STATE,PROP_COUNTY,PROP_CITY,PROP_STREET,PROP_ZIP_CODE,PROP_BUILDING,PROP_PL_DESCRIPTION,PROP_ENTERED_DATE,(select CASE when PROP_ENTERED_DATE <> '00000000' then EXTRACT(YEAR FROM TO_DATE(PROP_ENTERED_DATE, 'MM-DD-YYYY')) else 0 end FROM dual) AS PROP_ENTERED_YEAR,\r\n" + 
//					"PROP_SAP_ASSET_CLASS,PROP_SAP_ASSET_CLASS_DES,PROP_GL_CATEGORY_DESCRIPTION,PROP_CORPORATE_LIFE,PROP_INSTALL_DATE,(select CASE when PROP_INSTALL_DATE <> '00000000' then EXTRACT(YEAR FROM TO_DATE(PROP_INSTALL_DATE, 'MM-DD-YYYY')) else 0 end FROM dual) AS PROP_INSTALL_YEAR,\r\n" + 
//					"PROP_CORP_BOOK_COST_BASIS,PROP_CORP_BOOK_ACCUM_RSRV,PROP_NET_BOOK_VALUE,PROP_ACCOUNTING_YEAR,PROP_TAX_INSTALL_DATE,PROP_TAX_COST_BASIS,PROP_TAX_NTV,PROP_PPTAX,PROP_INVOICE_NUMBER,PROP_PROJECT_NUMBER,PROP_PURCHASE_ORDER,PROP_WBSE,PROP_PROFIT_CENTER,PROP_COST_CENTER,\r\n" + 
//					"PROP_MANUFACTURE,PROP_VENDOR,PROP_ATTRACTION_ID,PROP_GEO_LOCATION,PROP_DEPARTMENT,PROP_COMPANY_NAME,PROP_SERIAL_NUMBER,PROP_INVENTORY_NUMBER,PROP_QUANTITY,PROP_PHYSICAL_LOCATION_CO_NAME,PROP_TYPE_NAME,PROP_LEGACY_PROJECT_NO,PROP_TRACKING_NUMBER  from tb"; 
//		

		String searchSQL= callProcedure(table.trim());
		
		
		if(individualSearch!=null && individualSearch!=""){
			searchSQL = searchSQL+" where " + individualSearch;
			
		}
		
		searchSQL += " order by PROP_UNIQUE_ASSTNO asc";

		List<List<String>> resultDataList = new ArrayList<List<String>>();
		try {
			Connection conn = dataTableDao.getConnection();
			String getNumericCol="select column_name,data_type,column_id from sys.all_tab_columns where table_name= ? and data_type= 'NUMBER' order by column_id";
			PreparedStatement ps1=conn.prepareStatement(getNumericCol);
			ps1.setString(1, tableName.trim());
			ResultSet numColRes = ps1.executeQuery();
			List<Integer> colId=new ArrayList<Integer>();
			while(numColRes.next())
			{
				int parsed=Integer.parseInt(numColRes.getString("column_id"));
				parsed=parsed-1;
				colId.add(parsed);
			}
		//	System.out.println(colId);
			String searchSQL1 = searchSQL.replace("$tablename", table);
			//String searchSQL2= searchSQL1.replace("'", "");
			PreparedStatement ps = conn.prepareStatement(searchSQL1);
			
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
			ResultSetMetaData rsMeta=rs2.getMetaData();
			int ct=rsMeta.getColumnCount();
		//	System.out.println("count: "+ct);
			while (rs2.next()) {
				counter++;
				List<String> resultRowData = new ArrayList<String>();
				//System.out.println(resultRowData);
				for (int i = 1; i <=ct; i++) {	
					
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
							if (StringUtils.isNotBlank(entry) && (colId.contains(cellNum))) {
								
								row.createCell(cellNum++).setCellValue(Double.parseDouble(entry));
							} else {
								row.createCell(cellNum++).setCellValue(entry);
							}
							// row.createCell(cellNum++).setCellValue(entry);

						}
					}
					
					
					
					//resultDataList.clear();//size 3000
					resultDataList=null;
					
					 resultDataList = new ArrayList<List<String>>(1);//10
					 
				}
				
			}
			for (List<String> list : resultDataList) {

				int cellNum = 0;
				// create the row data
				Row row = reportSheet.createRow(rowNum++);
				for (String entry : list) {
					// if(cellNum==14 && StringUtils.isNotBlank(entry)) {
					if (StringUtils.isNotBlank(entry) && (colId.contains(cellNum))) {
						
						row.createCell(cellNum++).setCellValue(Double.parseDouble(entry));
					} else {
						row.createCell(cellNum++).setCellValue(entry);
					}

				}
			}
			
			resultDataList=null;
		 resultDataList = new ArrayList<List<String>>(1);//10
			
			System.out.println("resultset End Time: "+new Date());
			ps.close();
			conn.close();
			rs2.close();
		}catch(Exception e) {
			System.out.println("Exception"+e);
		}
		//return resultDataList;
		return workbook;
	}


	@Override
public String getSAPHistoricalPropertyTaxDataTableResponse(HttpServletRequest request) {

		
//		String[] cols = {"INC_UNIQUEASSET","INC_COMPANYCODE","INC_ASSETNUMBER","INC_SUBNUMBER","INC_ASSETDESCRIPTION","INC_ASSETCLASSS","INC_ASSETCLASSDESCRIPTION","INC_LEGACYCATEGORYCODE","INC_ENTEREDDATE","INC_INSTALLDATE","INC_TAXINSTALLATIONYR","INC_ASSETLIFE","INC_REMAININGLIFE","INC_TAXAPC","INC_DEPRECIABLEBASIS","INC_ACCUMULATEDDEPRECIATION","INC_YEARTODATEDEPREC","INC_TAXNBV","INC_BONUSDEPRECIATION","INC_TAXYTDBONUSDEPR","INC_ELECTOUTBONUS","INC_TAXBONUSPERCENTAGE","INC_FIRSTYEARDEPRECIATED","INC_LASTYEARDEPRECIATED",
//				"INC_LASTMONTHDEPRECIATED","INC_NEWORUSED","INC_TAXDEPRMETHOD","INC_TAXDEPRCONVENTION","INC_CURRENTACCOUNTINGYEAR","INC_CORPINSTALLDATE","INC_CORPLIFE","INC_CORPBOOKAPC","INC_CORPBOOKACCUMRESERVE","INC_CORPNBV","INC_CREATIONDATE","INC_CREATIONTIME","INC_CREATIONUSER"};
//		String[] cols = {"YEAR", "REPORT_NAME","PROP_ENTITY","PROP_UNIQUE_ASSTNO","PROP_ASSET_NUMBER","PROP_ASSET_SUB_NUMBER","PROP_INTERNAL_ASST_NO","PROP_LEGACY_ASSET_NUMBER","PROP_LEGACY_ASSET_SUBNUMBER","PROP_ASSET_DESCRIPTION","PROP_COUNTRY","PROP_STATE","PROP_COUNTY", "PROP_CITY","PROP_STREET","PROP_ZIP_CODE","PROP_BUILDING","PROP_PL_DESCRIPTION","PROP_ENTERED_DATE","PROP_ENTERED_YEAR","PROP_SAP_ASSET_CLASS",
//			"PROP_SAP_ASSET_CLASS_DES","PROP_GL_CATEGORY_DESCRIPTION","PROP_CORPORATE_LIFE","PROP_INSTALL_DATE","PROP_INSTALL_YEAR","PROP_CORP_BOOK_COST_BASIS","PROP_CORP_BOOK_ACCUM_RSRV","PROP_NET_BOOK_VALUE","PROP_ACCOUNTING_YEAR","PROP_TAX_INSTALL_DATE",
//			"PROP_TAX_COST_BASIS","PROP_TAX_NTV","PROP_PPTAX","PROP_INVOICE_NUMBER","PROP_PROJECT_NUMBER","PROP_PURCHASE_ORDER","PROP_WBSE","PROP_PROFIT_CENTER","PROP_COST_CENTER","PROP_MANUFACTURE","PROP_VENDOR","PROP_ATTRACTION_ID","PROP_GEO_LOCATION","PROP_DEPARTMENT","PROP_COMPANY_NAME","PROP_SERIAL_NUMBER","PROP_INVENTORY_NUMBER","PROP_QUANTITY","PROP_PHYSICAL_LOCATION_CO_NAME","PROP_TYPE_NAME","PROP_LEGACY_PROJECT_NO","PROP_TRACKING_NUMBER"};	
//		
		
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
		
		//String table = tableName.replace("'", "");
		String table = tableName.replaceAll("[^a-zA-Z0-9_]", "");
	//	System.out.println(tableName);
		String procedureQ=callProcedure(table.trim());

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
		
	
		List<String> sArray = new ArrayList<String>();
		
		int mapi=0;
		Map<String,String> prepareParamMap=new LinkedHashMap<String, String>();
		
		if (null!=histproptaxfacompany && !histproptaxfacompany.equals("")) {
			//sArray.add(" INC_COMPANYCODE like '%" + facompany + "%'");
			sArray.add(" PROP_ENTITY like ?");
			prepareParamMap.put(++mapi+"_"+"like",histproptaxfacompany);
		}
		
		if (null!=histproptaxfacompanygroup && !histproptaxfacompanygroup.equals("")) {
			//sArray.add(" INC_COMPANYCODE in (select company from FACOMPANY where companygroup like '%" + facompanygroup + "%')");	
			sArray.add(" PROP_ENTITY in (select company from FACOMPANY where companygroup like ?)");
			prepareParamMap.put(++mapi+"_"+"like",histproptaxfacompanygroup);
		}
		
		if (null!=histproptaxInstallDateFrom && !histproptaxInstallDateFrom.equals("")) {
			String taxInstallQuery=" to_date(PROP_INSTALL_DATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
			
			//taxInstallQuery=taxInstallQuery.replace("taxInsFromParam", taxInstallDateFrom);
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxInstallDateFrom);
			sArray.add(taxInstallQuery);
		}
		
		if (null!=histproptaxInstallDateTo && !histproptaxInstallDateTo.equals("")) {
			String taxInstallQuery=" to_date(PROP_INSTALL_DATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			
			//taxInstallQuery=taxInstallQuery.replace("taxInsToParam", taxInstallDateTo);
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxInstallDateTo);
			sArray.add(taxInstallQuery);
		}
		
		if (null!=histproptaxCreateDateFrom && !histproptaxCreateDateFrom.equals("")) {
			String taxCreateQuery=" to_date(PROP_ENTERED_DATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') " ;
			
			//taxInstallQuery=taxInstallQuery.replace("taxCreFromParam", taxCreateDateFrom);
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxCreateDateFrom);
			sArray.add(taxCreateQuery);
		}
		
		if (null!=histproptaxCreateDateTo && !histproptaxCreateDateTo.equals("")) {
			String taxCreateQuery=" to_date(PROP_ENTERED_DATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			
			//taxInstallQuery=taxInstallQuery.replace("taxCreToParam", taxCreateDateTo);
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxCreateDateTo);
			sArray.add(taxCreateQuery);
		}
		
		///murali
		
		if (null!=histproptaxyear && !histproptaxyear.equals("")) {
			//sArray.add(" INC_COMPANYCODE like '%" + facompany + "%'");
			sArray.add(" YEAR like ?");
			prepareParamMap.put(++mapi+"_"+"like",histproptaxyear);
		}
		
//	if (null!=histproptaxreportId && !histproptaxreportId.equals("")) {
//			
//			
//			sArray.add(" REPORT_NAME like ?");
//			prepareParamMap.put(++mapi+"_"+"like",histproptaxreportId);
//			//prepareParamMap.put(++mapi+"_"+"like","%" + histproptaxreportId + "%");
//		}
		
		
		
		
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
		//String colName = cols[col];

		/* This is show the total count of records in Data base table */
		int total = 0;
		Connection conn = dataTableDao.getConnection();

		int totalAfterFilter = total;

		try {
			String searchSQL = "";
			String sql = procedureQ;
			//System.out.println("ENTER SEARCH==="+sql);
			String searchTerm = request.getParameter("sSearch");
			String globeSearch = ""; 
//					" where ( upper(YEAR) like upper(?)"
//				+ " or upper(REPORT_NAME) like upper(?))"
//				+ " or upper(PROP_ENTITY) like upper(?))"	
//				+ " or upper(PROP_UNIQUE_ASSTNO) like upper(?)"
//				+ " or upper(PROP_ASSET_NUMBER) like upper(?)"
//				+ " or upper(PROP_ASSET_SUB_NUMBER) like upper(?)"
//				+ " or upper(PROP_INTERNAL_ASST_NO) like upper(?)"
//				+ " or upper(PROP_LEGACY_ASSET_NUMBER) like upper(?)"
//				+ " or upper(PROP_LEGACY_ASSET_SUBNUMBER) like upper(?)"
//				+ " or upper(PROP_ASSET_DESCRIPTION) like upper(?)"
//				+ " or upper(PROP_COUNTRY) like upper(?)"
//				+ " or upper(PROP_STATE) like upper(?)"
//			    + " or upper(PROP_COUNTY) like upper(?)"
//			    + " or upper(PROP_CITY) like upper(?)"
//			    + " or upper(PROP_STREET) like upper(?)"
//				+ " or upper(PROP_ZIP_CODE) like upper(?)"
//				+ " or upper(PROP_BUILDING) like upper(?)"
//				+ " or upper(PROP_PL_DESCRIPTION) like upper(?)"
//				+ " or upper(PROP_ENTERED_DATE) like upper(?)"
//				+ " or upper(PROP_SAP_ASSET_CLASS) like upper(?)"
//				+ " or upper(PROP_SAP_ASSET_CLASS_DES) like upper(?)"
//				+ " or upper(PROP_GL_CATEGORY_DESCRIPTION) like upper(?)"
//				+ " or upper(PROP_CORPORATE_LIFE) like upper(?)"
//				+ " or upper(PROP_INSTALL_DATE) like upper(?)"
//				+ " or upper(PROP_INSTALL_YEAR) like upper(?)"
//				+ " or upper(PROP_CORP_BOOK_COST_BASIS) like upper(?)"
//				+ " or upper(PROP_CORP_BOOK_ACCUM_RSRV) like upper(?)"
//				+ " or upper(PROP_NET_BOOK_VALUE) like upper(?)"
//				//+ " or upper(PROP_LAST_YR_DEPRECIATED) like upper('%"+searchTerm+"%')"
//				//+ " or upper(PROP_LAST_MTH_DEPRECIATED) like upper('%"+searchTerm+"%')"
//				+ " or upper(PROP_ACCOUNTING_YEAR) like upper(?)"
//				+ " or upper(PROP_TAX_INSTALL_DATE) like upper(?)"
//				+ " or upper(PROP_TAX_COST_BASIS) like upper(?)"
//				+ " or upper(PROP_TAX_NTV) like upper(?)"
//				+ " or upper(PROP_PPTAX) like upper(?)"
//				//+ " or upper(PROP_ACCOUNTING_PERIOD) like upper('%"+searchTerm+"%')"
//				+ " or upper(PROP_INVOICE_NUMBER) like upper(?)"
//				+ " or upper(PROP_PROJECT_NUMBER) like upper(?)"
//				+ " or upper(PROP_PURCHASE_ORDER) like upper(?)"
//				+ " or upper(PROP_WBSE) like upper(?)"
//				+ " or upper(PROP_PROFIT_CENTER) like upper(?)"
//				+ " or upper(PROP_COST_CENTER) like upper(?)"
//				+ " or upper(PROP_MANUFACTURE) like upper(?)"
//				+ " or upper(PROP_VENDOR) like upper(?)"
//				+ " or upper(PROP_ATTRACTION_ID) like upper(?)"
//				+ " or upper(PROP_GEO_LOCATION) like upper(?)"
//				+ " or upper(PROP_DEPARTMENT) like upper(?))"
//				+ " or upper(PROP_COMPANY_NAME) like upper(?))"
//				+ " or upper(PROP_SERIAL_NUMBER) like upper(?))"
//				+ " or upper(PROP_INVENTORY_NUMBER) like upper(?))"
//				+ " or upper(PROP_QUANTITY) like upper(?))"
//				+ " or upper(PROP_PHYSICAL_LOCATION_CO_NAME) like upper(?))"
//				+ " or upper(PROP_TYPE_NAME) like upper(?))"
//				+ " or upper(PROP_LEGACY_PROJECT_NO) like upper(?))"
//				+ " or upper(PROP_TRACKING_NUMBER) like upper(?))";

			
			
			
			
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
			/*
			 * if(sql.contains("WHERE")||sql.contains("where")){
			 * sql=sql+" and ROWNUM <= 200"; //+
			 * "and REGEXP_LIKE (TXADD_TAX_INSTALL_DATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
			 * }else { sql=sql+" WHERE ROWNUM <= 200"; // +
			 * "and REGEXP_LIKE (TXADD_TAX_INSTALL_DATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
			 * }
			 */
			
			if(sql.contains("WHERE")||sql.contains("where")){
				sql=sql+" and ROWNUM <= 200 ";
//						+ "and REGEXP_LIKE (PROP_INSTALL_DATE, '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
				}else {
				sql=sql+" and ROWNUM <= 200 ";
//						+ "and REGEXP_LIKE (PROP_INSTALL_DATE, '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
				}
			
			//sql += " order by " + colName + " " + dir;
			sql += " order by PROP_UNIQUE_ASSTNO asc";
			
			
			String sql1 = sql.replace("tb", table);
			
			//String sql2=sql1.replace("'", "");
			//System.out.println("sqlqsql==="+sql1);
			PreparedStatement ps = conn.prepareStatement(sql1);
			
			int mapj = 1;
			for (Map.Entry<String, String> m : prepareParamMap.entrySet()) {
				if (m.getKey().contains("like")) {
					ps.setString(mapj, "%" + m.getValue() + "%");
				} else {
					ps.setString(mapj, m.getValue());
				}
				mapj++;
			}
			
//			if(null!=searchTerm && searchTerm!=""){
//				mapj = mapj-1;
//				int sSearchCount=39;
//		        for(int sSearchC=mapj;sSearchC<mapj+sSearchCount;sSearchC++) {
//		            ps.setString(sSearchC, "%" + searchTerm + "%");
//		        }
//			}
			
			ps.setFetchSize(200);
			ResultSet rs = ps.executeQuery();
			//System.out.println("size====="+rs.getFetchSize());
			int i=0;
			Date date1;
			ResultSetMetaData rsmd = rs.getMetaData(); 
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				JSONArray ja = new JSONArray();
				int i1 = 1;
				   while(i1 <= columnCount) {
				        ja.put(rs.getString(i1++));
				   }
				//System.out.println("enter report==="+rs.getString("PRADD_YEAR"));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("YEAR")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("REPORT_NAME")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_ENTITY")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_UNIQUE_ASSTNO")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_ASSET_NUMBER")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_ASSET_SUB_NUMBER")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_INTERNAL_ASST_NO")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_LEGACY_ASSET_NUMBER")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_LEGACY_ASSET_SUBNUMBER")));									
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_ASSET_DESCRIPTION")));					
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_COUNTRY")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_STATE")));				
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_COUNTY")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_CITY")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_STREET")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_ZIP_CODE")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_BUILDING")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_PL_DESCRIPTION")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_ENTERED_DATE")));
//				   Date d = new Date(StringEscapeUtils.escapeHtml4(rs.getString("PROP_ENTERED_DATE")));
//			         SimpleDateFormat formatNowYear = new SimpleDateFormat("YYYY");
//			         String currentYear = formatNowYear.format(d); 
//					ja.put((currentYear));
//					//ja.put(rs.getString("PROP_ENTERED_YEAR"));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_SAP_ASSET_CLASS")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_SAP_ASSET_CLASS_DES")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_GL_CATEGORY_DESCRIPTION")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_CORPORATE_LIFE")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_INSTALL_DATE")));
//					 Date d1 = new Date(StringEscapeUtils.escapeHtml4(rs.getString("PROP_INSTALL_DATE")));
//			         SimpleDateFormat formatNowYear1 = new SimpleDateFormat("YYYY");
//			         String currentYear1 = formatNowYear.format(d1); 
//					ja.put((currentYear1));
//					//ja.put(rs.getString("PROP_INSTALL_YEAR"));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_CORP_BOOK_COST_BASIS")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_CORP_BOOK_ACCUM_RSRV")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_NET_BOOK_VALUE")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_ACCOUNTING_YEAR")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_TAX_INSTALL_DATE")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_TAX_COST_BASIS")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_TAX_NTV")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_PPTAX")));
//					//ja.put(rs.getString("PROP_ACCOUNTING_PERIOD"));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_INVOICE_NUMBER")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_PROJECT_NUMBER")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_PURCHASE_ORDER")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_WBSE")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_PROFIT_CENTER")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_COST_CENTER")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_MANUFACTURE")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_VENDOR")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_ATTRACTION_ID")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_GEO_LOCATION")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_DEPARTMENT")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_COMPANY_NAME")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_SERIAL_NUMBER")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_INVENTORY_NUMBER")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_QUANTITY")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_PHYSICAL_LOCATION_CO_NAME")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_TYPE_NAME")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_LEGACY_PROJECT_NO")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("PROP_TRACKING_NUMBER")));
				
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
	
	public List<String[]> getSAPHistoricalPropertyTaxCompanyGroups() {
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
	public List<String[]> getSAPHistoricalPropertyTaxInstances() {
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
	public List<String[]> getSAPHistoricalPropertyTaxYears() {
		List<String[]> resultList = new ArrayList<String[]>();
		Connection conn;
		PreparedStatement ps;
		try {
			String query = "select distinct(YEAR) from REPORT_INDEX_TABLE where LAYOUT='SHPPTREG' AND VERSION = 'SAP_HIST' order by YEAR asc";
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
	
	public List<String[]> getSAPHistoricalPropTaxReportNames(List<String> reportList) {
		//System.out.println("report Murli====="+reportList.get(0));
		//System.out.println("report Murli====="+reportList.get(1));
		List<String[]> resultList = new ArrayList<String[]>();
		Connection conn;
		PreparedStatement ps;
		try {
			
			String query = "select * from REPORT_INDEX_TABLE where YEAR=? AND LAYOUT='SHPPTREG' AND VERSION = 'SAP_HIST' order by REPORT_NAME asc";
			
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
	
	
	

	
	
	
	
	
	
	
	public List<String[]> getSAPHistoricalPropertyTaxCompanyNames(String COMPANYGROUP) {
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

	public String callProcedure(String reportTableName)
	{

		
		
		String m_count="";
		

		try {
			Connection	conn1 = dataTableDao.getConnection();

			
			CallableStatement stm = conn1.prepareCall("{ call DYNAMICOLUMN(?,?) }");
		//	System.out.println("ggg"+reportTableName);
			stm.setString(1, reportTableName);
		    stm.registerOutParameter(2,Types.VARCHAR);
		    stm.execute();
		    m_count = stm.getString(2);
		//    System.out.println("rrto"+m_count.toString());
		    stm.close();
			conn1.close();
			
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		return m_count;
		
	}
	
	@Override
	public List<String[]> getTableHeadings(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String yy=request.getParameter("year");
		String rid=request.getParameter("reportId");
		String tableName="";
		//List<String[]> tableNameList=this.getReportTables(request.getParameter("reportId"),request.getParameter("year"));
		List<String[]> tableNameList=this.getReportTables(rid,yy);
		for (String[] strings : tableNameList) {
			//System.out.println("query======"+strings[2]);
			String tb=strings[2];
			tableName=tb;
		}
	//	System.out.println("tb== "+tableName.trim());
		 List<String[]> headingList = new ArrayList<String[]>();
			Connection conn;
			PreparedStatement ps;
			try {
			//String query = "select * from REPORT_INDEX_TABLE where REPORT_ID=? and YEAR=? order by DB_TABLE asc";
			//String query = "select COLUMN_NAME from SYS.all_tab_columns where table_name = ?";
			String query="select COLUMN_NAME,COLUMN_ID,HIST_INC_TAXREG_UI from SYS.all_tab_columns,all_tables_column_names where table_name = ? and COLUMN_NAME=HIST_INC_TAXREG_DB ORDER BY COLUMN_ID";
			conn = dataTableDao.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, tableName.trim());
			ResultSet rs = ps.executeQuery();
			//System.out.println("testing=== "+rs.getFetchSize());
			while (rs.next()) {
			String[] arr = new String[1];
			arr[0] = StringEscapeUtils.escapeHtml4(rs.getString("HIST_INC_TAXREG_UI"));
			//System.out.print(arr[0]);
			headingList.add(arr);
			}
			conn.close();
			ps.close();
			} catch (Exception e) {
			System.out.println(e);
			}
			return headingList;
	}
}
