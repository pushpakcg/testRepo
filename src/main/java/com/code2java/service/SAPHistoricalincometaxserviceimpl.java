package com.code2java.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
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

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

@Service("SAPhistIncometaxRegService")
public class SAPHistoricalincometaxserviceimpl implements SAPHistoricalIncometaxregService{

	@Autowired
	private DataTableDAO dataTableDao;
	
		public SXSSFWorkbook getSAPHistoricalIncometaxExcelData(HttpServletRequest request){
	   
		//report handeling//
			
			SXSSFWorkbook workbook = null;
			Sheet reportSheet = null;
			workbook = new SXSSFWorkbook(100);
			reportSheet = workbook.createSheet("Reports");
			Row header = reportSheet.createRow(0);
			header.setHeight((short) 500);
			Row dataRow = null;
			
//			header.createCell(0).setCellValue("Year");
//			header.createCell(1).setCellValue("Report_name");
//			header.createCell(2).setCellValue("Unique Asset Number");
//			header.createCell(3).setCellValue("Company Code");
//			header.createCell(4).setCellValue("Asset Number");
//			header.createCell(5).setCellValue("Sub Number");
//			header.createCell(6).setCellValue("Legacy Unique Asset Number");
//			header.createCell(7).setCellValue("Asset Description");
//			header.createCell(8).setCellValue("Asset Class");
//			header.createCell(9).setCellValue("Asset Class Description");
//			header.createCell(10).setCellValue("Legacy Category Code");
//			header.createCell(11).setCellValue("Entered Date");
//			header.createCell(12).setCellValue("Install Date");
//			header.createCell(13).setCellValue("Tax InstallationYr");
//			header.createCell(14).setCellValue("Asset Life");
//			header.createCell(15).setCellValue("Remaining Life");
//			header.createCell(16).setCellValue("Tax APC");
//			header.createCell(17).setCellValue("Depreciable Basis");
//			header.createCell(18).setCellValue("Accumulated Deprecation");
//			header.createCell(19).setCellValue("Year to Date Depreciation");
//			header.createCell(20).setCellValue("Tax NBV");
//			header.createCell(21).setCellValue("Bonus Depreciation");
//			header.createCell(22).setCellValue("Tax YTD Bonus Depreciation");
//			header.createCell(23).setCellValue("Elect Out Bonus");
//			header.createCell(24).setCellValue("Tax Bonus Percentage");
//			header.createCell(25).setCellValue("First Year Depreciated");
//			header.createCell(26).setCellValue("Last Year Depreciated");
//			header.createCell(27).setCellValue("Last Month Depreciated");
//			header.createCell(28).setCellValue("New or Used");
//			header.createCell(29).setCellValue("Tax Depreciation Method");
//			header.createCell(30).setCellValue("Tax Depreciation Convention");
//			header.createCell(31).setCellValue("Park Asset Property Tax");
//			header.createCell(32).setCellValue("Current Accounting Year");		
//			header.createCell(33).setCellValue("Post Capitalization");
//			header.createCell(34).setCellValue("Corp Install Date");
//			header.createCell(35).setCellValue("Corp Life");
//			header.createCell(36).setCellValue("Corp Book APC");
//			header.createCell(37).setCellValue("Corp Book Accumulated Depreciation");
//			header.createCell(38).setCellValue("Corp NBV");
//			header.createCell(39).setCellValue("Record Creation Date");
//			header.createCell(40).setCellValue("Record Creation Time");
//			header.createCell(41).setCellValue("Record Creation User");
//			
			String[] headers=null;
			List<String[]> headerList=getTableHeadings(request);
			int count=0;
			for (String[] strings : headerList) {
			    headers=strings;
			    header.createCell(count).setCellValue(headers[0]);
			   count++;
			}
			
			int rowNum = 1;
			
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
		List<String[]> tableNameList=this.getReportTables(histproptaxreportId,histproptaxyear);
		for (String[] strings : tableNameList) {
			String tb=strings[2];
			tableName=tb;
		}		
		//String table = tableName.replace("'", "");
		String table = tableName.replaceAll("[^a-zA-Z0-9_]", "");
		
		List<String> sArray = new ArrayList<String>();
		
		int mapi=0;
		Map<String,String> prepareParamMap=new LinkedHashMap<String, String>(); 
		
		if (null!=histproptaxfacompany && !histproptaxfacompany.equals("")) {			
			sArray.add(" INC_COMPANYCODE like ?");
			prepareParamMap.put(++mapi+"_"+"like",histproptaxfacompany);
		}
		
		if (null!=histproptaxfacompanygroup && !histproptaxfacompanygroup.equals("")) {			
			sArray.add(" INC_COMPANYCODE in (select company from FACOMPANY where companygroup like ?)");
			prepareParamMap.put(++mapi+"_"+"like",histproptaxfacompanygroup);
		}
		
		if (null!=histproptaxInstallDateFrom && !histproptaxInstallDateFrom.equals("")) {			
			String taxInstallQuery=" to_date(case when INC_INSTALLDATE= '00000000' then null else INC_INSTALLDATE end,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxInstallDateFrom);
			sArray.add(taxInstallQuery);
		}
		
		if (null!=histproptaxInstallDateTo && !histproptaxInstallDateTo.equals("")) {			
			String taxInstallQuery=" to_date(case when INC_INSTALLDATE= '00000000' then null else INC_INSTALLDATE end,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxInstallDateTo);
			sArray.add(taxInstallQuery);
		}
		
		if (null!=histproptaxCreateDateFrom && !histproptaxCreateDateFrom.equals("")) {
			String taxCreateQuery=" to_date(INC_ENTEREDDATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') " ;
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxCreateDateFrom);
			sArray.add(taxCreateQuery);
		}
		
		if (null!=histproptaxCreateDateTo && !histproptaxCreateDateTo.equals("")) {
			String taxCreateQuery=" to_date(INC_ENTEREDDATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";			
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
		
		
		
//		String searchSQL="select YEAR,REPORT_NAME,INC_UNIQUEASSET,INC_COMPANYCODE,INC_ASSETNUMBER,INC_SUBNUMBER,INC_LEGUNIQUE,INC_ASSETDESCRIPTION,INC_ASSETCLASSS,INC_ASSETCLASSDESCRIPTION,INC_LEGACYCATEGORYCODE,INC_ENTEREDDATE,INC_INSTALLDATE,(select CASE when INC_INSTALLDATE <> '00000000' then EXTRACT(YEAR FROM TO_DATE(INC_INSTALLDATE, 'MM-DD-YYYY')) else 0 end FROM dual) AS INC_TAXINSTALLATIONYR,INC_ASSETLIFE,INC_REMAININGLIFE,INC_TAXAPC, (SELECT CASE WHEN to_char(INC_ASSETCLASSS)='0000' THEN '0' ELSE to_char(INC_TAXAPC) END from dual) AS INC_DEPRECIABLEBASIS,INC_ACCUMULATEDDEPRECIATION,INC_YEARTODATEDEPREC,\r\n"+
//				 "INC_TAXNBV,INC_BONUSDEPRECIATION,INC_TAXYTDBONUSDEPR,INC_ELECTOUTBONUS,(select case when (inc_bonusdepreciation) <> 0  AND (inc_taxapc) <> 0  then TRUNC(abs(inc_bonusdepreciation)/abs(inc_taxapc)*100,0) else 0  end from dual)AS INC_TAXBONUSPERCENTAGE,INC_FIRSTYEARDEPRECIATED,INC_LASTYEARDEPRECIATED,INC_LASTMONTHDEPRECIATED,INC_NEWORUSED,INC_TAXDEPRMETHOD,INC_TAXDEPRCONVENTION,INC_PPTAX,INC_CURRENTACCOUNTINGYEAR,INC_POSTCAPITALIZATION,INC_CORPINSTALLDATE,INC_CORPLIFE,INC_CORPBOOKAPC,INC_CORPBOOKACCUMRESERVE,\r\n"+
//		          "INC_CORPNBV,INC_CREATIONDATE,INC_CREATIONTIME,INC_CREATIONUSER from $tablename";
		
		String searchSQL= callProcedure(table.trim());

//		String searchSQL="select INC_UNIQUEASSET,INC_COMPANYCODE,INC_ASSETNUMBER,INC_SUBNUMBER,INC_LEGUNIQUE,INC_ASSETDESCRIPTION,INC_ASSETCLASSS,INC_ASSETCLASSDESCRIPTION,INC_LEGACYCATEGORYCODE,INC_ENTEREDDATE,INC_INSTALLDATE, INC_TAXINSTALLATIONYR,INC_ASSETLIFE,INC_REMAININGLIFE,INC_TAXAPC,  INC_DEPRECIABLEBASIS,INC_ACCUMULATEDDEPRECIATION,INC_YEARTODATEDEPREC,INC_TAXNBV,INC_BONUSDEPRECIATION,INC_TAXYTDBONUSDEPR,INC_ELECTOUTBONUS,(select case when (inc_bonusdepreciation) <> 0  AND (inc_taxapc) <> 0  then TRUNC(abs(inc_bonusdepreciation)/abs(inc_taxapc)*100,0) else 0  end from dual)AS INC_TAXBONUSPERCENTAGE,INC_FIRSTYEARDEPRECIATED,INC_LASTYEARDEPRECIATED,INC_LASTMONTHDEPRECIATED,INC_NEWORUSED,INC_TAXDEPRMETHOD,INC_TAXDEPRCONVENTION,INC_CURRENTACCOUNTINGYEAR,INC_POSTCAPITALIZATION,INC_CORPINSTALLDATE,INC_CORPLIFE,INC_CORPBOOKAPC,INC_CORPBOOKACCUMRESERVE,\r\n"+
//		          "INC_CORPNBV,INC_CREATIONDATE,INC_CREATIONTIME,INC_CREATIONUSER,REPORT_NAME,YEAR from $tablename";
		
		
		if(individualSearch!=null && individualSearch!=""){
			searchSQL = searchSQL+" where " + individualSearch;
			
		}
		
		searchSQL += " order by INC_UNIQUEASSET asc";
		

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
			//System.out.println(colId);
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
					
					if (StringUtils.isNotBlank(entry) && colId.contains(cellNum)) {
						
						row.createCell(cellNum++).setCellValue(Double.parseDouble(entry));
					} else {
						row.createCell(cellNum++).setCellValue(entry);
					}
					// row.createCell(cellNum++).setCellValue(entry);

				}
			}
			
			
			
			resultDataList=null;
		 resultDataList = new ArrayList<List<String>>(1);
			//ends//
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
public String getSAPHistoricalIncometaxDataTableResponse(HttpServletRequest request) {

		
//		String[] cols = {"INC_UNIQUEASSET","INC_COMPANYCODE","INC_ASSETNUMBER","INC_SUBNUMBER","INC_ASSETDESCRIPTION","INC_ASSETCLASSS","INC_ASSETCLASSDESCRIPTION","INC_LEGACYCATEGORYCODE","INC_ENTEREDDATE","INC_INSTALLDATE","INC_TAXINSTALLATIONYR","INC_ASSETLIFE","INC_REMAININGLIFE","INC_TAXAPC","INC_DEPRECIABLEBASIS","INC_ACCUMULATEDDEPRECIATION","INC_YEARTODATEDEPREC","INC_TAXNBV","INC_BONUSDEPRECIATION","INC_TAXYTDBONUSDEPR","INC_ELECTOUTBONUS","INC_TAXBONUSPERCENTAGE","INC_FIRSTYEARDEPRECIATED","INC_LASTYEARDEPRECIATED",
//				"INC_LASTMONTHDEPRECIATED","INC_NEWORUSED","INC_TAXDEPRMETHOD","INC_TAXDEPRCONVENTION","INC_CURRENTACCOUNTINGYEAR","INC_CORPINSTALLDATE","INC_CORPLIFE","INC_CORPBOOKAPC","INC_CORPBOOKACCUMRESERVE","INC_CORPNBV","INC_CREATIONDATE","INC_CREATIONTIME","INC_CREATIONUSER"};
//		String[] cols = {"YEAR","REPORT_NAME","INC_UNIQUEASSET","INC_COMPANYCODE","INC_ASSETNUMBER","INC_SUBNUMBER","INC_ASSETDESCRIPTION","INC_ASSETCLASSS","INC_ASSETCLASSDESCRIPTION","INC_LEGACYCATEGORYCODE","INC_ENTEREDDATE","INC_INSTALLDATE","INC_TAXINSTALLATIONYR","INC_ASSETLIFE","INC_REMAININGLIFE","INC_TAXAPC","INC_DEPRECIABLEBASIS","INC_ACCUMULATEDDEPRECIATION","INC_YEARTODATEDEPREC","INC_TAXNBV","INC_BONUSDEPRECIATION","INC_TAXYTDBONUSDEPR","INC_ELECTOUTBONUS","INC_TAXBONUSPERCENTAGE","INC_FIRSTYEARDEPRECIATED","INC_LASTYEARDEPRECIATED",
//				"INC_LASTMONTHDEPRECIATED","INC_NEWORUSED","INC_TAXDEPRMETHOD","INC_TAXDEPRCONVENTION","INC_PPTAX","INC_CURRENTACCOUNTINGYEAR","INC_CORPINSTALLDATE","INC_CORPLIFE","INC_CORPBOOKAPC","INC_CORPBOOKACCUMRESERVE","INC_CORPNBV","INC_CREATIONDATE","INC_CREATIONTIME","INC_CREATIONUSER"};
//		
		
		String histproptaxyear=request.getParameter("year");
		String histproptaxreportId=request.getParameter("reportId");
		String tableName="";
		List<String[]> tableNameList=this.getReportTables(histproptaxreportId,histproptaxyear);
		for (String[] strings : tableNameList) {
			String tb=strings[2];
			tableName=tb;
		}
		
		
		//String table = tableName.replace("'", "");
		String table = tableName.replaceAll("[^a-zA-Z0-9_]", "");
		
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
			sArray.add(" INC_COMPANYCODE like ?");
			prepareParamMap.put(++mapi+"_"+"like",histproptaxfacompany);
		}
		
		if (null!=histproptaxfacompanygroup && !histproptaxfacompanygroup.equals("")) {
			//sArray.add(" INC_COMPANYCODE in (select company from FACOMPANY where companygroup like '%" + facompanygroup + "%')");	
			sArray.add(" INC_COMPANYCODE in (select company from FACOMPANY where companygroup like ?)");
			prepareParamMap.put(++mapi+"_"+"like",histproptaxfacompanygroup);
		}
		
		if (null!=histproptaxInstallDateFrom && !histproptaxInstallDateFrom.equals("")) {
			String taxInstallQuery=" to_date(INC_INSTALLDATE ,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
			
			//taxInstallQuery=taxInstallQuery.replace("taxInsFromParam", taxInstallDateFrom);
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxInstallDateFrom);
			sArray.add(taxInstallQuery);
		}
		
		if (null!=histproptaxInstallDateTo && !histproptaxInstallDateTo.equals("")) {
			String taxInstallQuery=" to_date( INC_INSTALLDATE ,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			
			//taxInstallQuery=taxInstallQuery.replace("taxInsToParam", taxInstallDateTo);
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxInstallDateTo);
			sArray.add(taxInstallQuery);
		}
		
		if (null!=histproptaxCreateDateFrom && !histproptaxCreateDateFrom.equals("")) {
			String taxCreateQuery=" to_date(INC_ENTEREDDATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') " ;
			
			//taxInstallQuery=taxInstallQuery.replace("taxCreFromParam", taxCreateDateFrom);
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxCreateDateFrom);
			sArray.add(taxCreateQuery);
		}
		
		if (null!=histproptaxCreateDateTo && !histproptaxCreateDateTo.equals("")) {
			String taxCreateQuery=" to_date(INC_ENTEREDDATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			
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
			String sql = procedureQ;
			String searchTerm = request.getParameter("sSearch");
			String globeSearch = ""; 
//					" where ( upper(YEAR) like upper(?)"
//			+ " or upper(REPORT_NAME) like upper(?)"
//			+ " or upper(INC_UNIQUEASSET) like upper(?)"				
//			+ " or upper(INC_COMPANYCODE) like upper(?)"
//			+ " or upper(INC_ASSETNUMBER) like upper(?)"
//			+ " or upper(INC_SUBNUMBER) like upper(?)"
//			+ " or upper(INC_LEGUNIQUE) like upper(?)"
//			+ " or upper(INC_ASSETDESCRIPTION) like upper(?)"
//			+ " or upper(INC_ASSETCLASSS) like upper(?)"
//			+ " or upper(INC_ASSETCLASSDESCRIPTION) like upper(?)"
//			+ " or upper(INC_LEGACYCATEGORYCODE) like upper(?)"
//		    + " or upper(INC_ENTEREDDATE) like upper(?)"
//		    + " or upper(INC_INSTALLDATE) like upper(?)"
//		    + " or upper(INC_TAXINSTALLATIONYR) like upper(?)"
//			+ " or upper(INC_ASSETLIFE) like upper(?)"
//			+ " or upper(INC_REMAININGLIFE) like upper(?)"
//			+ " or upper(INC_TAXAPC) like upper(?)"
//			+ " or upper(INC_DEPRECIABLEBASIS) like upper(?)"
//			+ " or upper(INC_ACCUMULATEDDEPRECIATION) like upper(?)"
//			+ " or upper(INC_YEARTODATEDEPREC) like upper(?)"
//			+ " or upper(INC_TAXNBV) like upper(?)"
//			+ " or upper(INC_BONUSDEPRECIATION) like upper(?)"
//			+ " or upper(INC_TAXYTDBONUSDEPR) like upper(?)"
//			+ " or upper(INC_ELECTOUTBONUS) like upper(?)"
//			+ " or upper(INC_TAXBONUSPERCENTAGE) like upper(?)"
//			+ " or upper(INC_FIRSTYEARDEPRECIATED) like upper(?)"
//			+ " or upper(INC_LASTYEARDEPRECIATED) like upper(?)"
//			+ " or upper(INC_LASTMONTHDEPRECIATED) like upper(?)"
//			+ " or upper(INC_NEWORUSED) like upper(?)"
//			+ " or upper(INC_TAXDEPRMETHOD) like upper(?)"
//			+ " or upper(INC_TAXDEPRCONVENTION) like upper(?)"
//			+ " or upper(INC_PPTAX) like upper(?)"
//			+ " or upper(INC_CURRENTACCOUNTINGYEAR) like upper(?)"			
//			+ " or upper(INC_CORPINSTALLDATE) like upper(?)"
//			+ " or upper(INC_CORPLIFE) like upper(?)"
//			+ " or upper(INC_CORPBOOKAPC) like upper(?)"
//			+ " or upper(INC_CORPBOOKACCUMRESERVE) like upper(?)"
//			+ " or upper(INC_CORPNBV) like upper(?)"
//			+ " or upper(INC_CREATIONDATE) like upper(?)"
//			+ " or upper(INC_CREATIONTIME) like upper(?)"
//			+ " or upper(INC_CREATIONUSER) like upper(?))";

			
			
			
			
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
			 * sql=sql+" and ROWNUM <= 200"; //
			 * +"and REGEXP_LIKE (TXADD_TAX_INSTALL_DATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
			 * }else { sql=sql+" WHERE ROWNUM <= 200"; //
			 * +"and REGEXP_LIKE (TXADD_TAX_INSTALL_DATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
			 * }
			 */
			 			
			
			if (sql.contains("WHERE") || sql.contains("where")) {
				sql = sql + " and ROWNUM <= 200 ";
//				and REGEXP_LIKE (INC_INSTALLDATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
			} else {
				sql = sql + " WHERE ROWNUM <= 200 ";
//						+ "and REGEXP_LIKE (INC_INSTALLDATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
			}
			 
			
			//sql += " order by " + colName + " " + dir;
			sql += " order by INC_UNIQUEASSET asc";
			
			//System.out.println("sqlqsql==="+sql);
			String sql1 = sql.replace("$tablename", table);
			//String sql2=sql1.replace("'","");
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
			int i=0;
				ResultSetMetaData rsmd = rs.getMetaData(); 
				int columnCount = rsmd.getColumnCount();
				while (rs.next()) {   
					JSONArray ja = new JSONArray();
				   int i1 = 1;
				   while(i1 <= columnCount) {
				        ja.put(rs.getString(i1++));
				   }
				   array.put(ja);
				}
				
				//Date date1;
				//while (rs.next()) {
//				//System.out.println("enter report==="+rs.getString("PRADD_YEAR"));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("YEAR")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("REPORT_NAME")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_UNIQUEASSET")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_COMPANYCODE")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_ASSETNUMBER")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_SUBNUMBER")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_LEGUNIQUE")));			
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_ASSETDESCRIPTION")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_ASSETCLASSS")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_ASSETCLASSDESCRIPTION")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_LEGACYCATEGORYCODE")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_ENTEREDDATE")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_INSTALLDATE")));
//				if(StringEscapeUtils.escapeHtml4(rs.getString("INC_INSTALLDATE"))!=null && !StringEscapeUtils.escapeHtml4(rs.getString("INC_INSTALLDATE")).equals("") )
//				 {
//				 Date d = new Date(StringEscapeUtils.escapeHtml4(rs.getString("INC_INSTALLDATE")));
//		         SimpleDateFormat formatNowYear = new SimpleDateFormat("YYYY");
//		        String currentYear = formatNowYear.format(d); 
//				ja.put((currentYear));
//				 }
//				 else
//				 {
//					 ja.put(""); 
//				 }
//				
//				
//				
//				
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_ASSETLIFE")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_REMAININGLIFE")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_TAXAPC")));				
//				
//				if(StringEscapeUtils.escapeHtml4(rs.getString("INC_ASSETCLASSS")).equals("0000")) {
//					ja.put("0");	
//				}
//				else {
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_TAXAPC")));
//				}				
//				
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_ACCUMULATEDDEPRECIATION"))); 
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_YEARTODATEDEPREC")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_TAXNBV")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_BONUSDEPRECIATION")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_TAXYTDBONUSDEPR")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_ELECTOUTBONUS")));
//				if(StringEscapeUtils.escapeHtml4(rs.getString("INC_BONUSDEPRECIATION"))!=null && !StringEscapeUtils.escapeHtml4(rs.getString("INC_BONUSDEPRECIATION")).equals("") &&StringEscapeUtils.escapeHtml4(rs.getString("INC_TAXAPC"))!=null && !StringEscapeUtils.escapeHtml4(rs.getString("INC_TAXAPC")).equals(""))
//				{
//				double bonusdepr=Double.parseDouble(StringEscapeUtils.escapeHtml4(rs.getString("INC_BONUSDEPRECIATION")));
//				double taxapc=Double.parseDouble(StringEscapeUtils.escapeHtml4(rs.getString("INC_TAXAPC")));
//				int bd=(int)bonusdepr;
//				int tp=(int)taxapc;
//				if(bd!=0 & tp!=0 )
//				{
//				
//					double per = bonusdepr/taxapc*100;
//				   int i1=(int)per;
//					String per1 = Integer.toString(i1);
//					  String[] arrOfStr = per1.split("-");	
//					  ja.put(arrOfStr[1]);
//				}
//				
//				else {
//					ja.put("0");
//				}	
//				}
//				else {
//					ja.put("");
//				}
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_FIRSTYEARDEPRECIATED")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_LASTYEARDEPRECIATED")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_LASTMONTHDEPRECIATED")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_NEWORUSED")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_TAXDEPRMETHOD")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_TAXDEPRCONVENTION")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_PPTAX")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_CURRENTACCOUNTINGYEAR")));				
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_POSTCAPITALIZATION")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_CORPINSTALLDATE")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_CORPLIFE")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_CORPBOOKAPC")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_CORPBOOKACCUMRESERVE")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_CORPNBV")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_CREATIONDATE")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_CREATIONTIME")));
//				ja.put(StringEscapeUtils.escapeHtml4(rs.getString("INC_CREATIONUSER")));
//				
//				
//				array.put(ja);
//			}
			result.put("iTotalRecords", total);
			result.put("iTotalDisplayRecords", totalAfterFilter);
			result.put("aaData", array);
			result.put("sEcho", echo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	public List<String[]> getSAPHistoricalIncometaxCompanyGroups() {
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
	public List<String[]> getSAPHistoricalIncometaxInstances() {
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
	public List<String[]> getSAPHistoricalIncometaxYears() {
		List<String[]> resultList = new ArrayList<String[]>();
		Connection conn;
		PreparedStatement ps;
		try {
			String query = "select distinct(YEAR) from REPORT_INDEX_TABLE WHERE LAYOUT='SHASTREG' AND VERSION = 'SAP_HIST' order by YEAR asc";
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
	
	public List<String[]> getSAPHistoricalIncometaxReportNames(List<String> reportList) {
		List<String[]> resultList = new ArrayList<String[]>();
		Connection conn;
		PreparedStatement ps;
		try {
			
			String query = "select * from REPORT_INDEX_TABLE where YEAR=? AND LAYOUT='SHASTREG' AND VERSION = 'SAP_HIST' order by REPORT_NAME asc";
			
		//	String query = "select * from REPORT_INDEX_TABLE where YEAR='2018' AND LAYOUT='ASTREG' order by REPORT_NAME asc";
			conn = dataTableDao.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, reportList.get(0));
			
		//	ps.setString(2, reportList.get(1));
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				String[] arr = new String[2];
				//arr[0] = StringEscapeUtils.escapeHtml4(rs.getString("REPORT_NAME"));
			//arr[0]=	rs.getString("REPORT_NAME");
				arr[0] = StringEscapeUtils.escapeHtml4(rs.getString("REPORT_ID"));
				arr[1] = StringEscapeUtils.escapeHtml4(rs.getString("REPORT_NAME"));
				//
			
				resultList.add(arr);
			}
			conn.close();
			ps.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return resultList;
	}
	
	
	

	
	
	
	
	
	
	
	public List<String[]> getSAPHistoricalIncometaxCompanyNames(String COMPANYGROUP) {
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
		//	System.out.println("Hist income tax reg table name"+reportTableName);
			stm.setString(1, reportTableName);
		    stm.registerOutParameter(2,Types.VARCHAR);
		    stm.execute();
		    m_count = stm.getString(2);
		//    System.out.println("Hist income tax reg store procedure query=="+m_count.toString());
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
		List<String[]> tableNameList=this.getReportTables(rid,yy);
		for (String[] strings : tableNameList) {
			String tb=strings[2];
			tableName=tb;
		}
		 List<String[]> headingList = new ArrayList<String[]>();
			Connection conn;
			PreparedStatement ps;
			try {
			String query="select COLUMN_NAME,COLUMN_ID,HIST_INC_TAXREG_UI from SYS.all_tab_columns,all_tables_column_names where table_name = ? and COLUMN_NAME=HIST_INC_TAXREG_DB ORDER BY COLUMN_ID";
			conn = dataTableDao.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, tableName.trim());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
			String[] arr = new String[1];
			arr[0] = StringEscapeUtils.escapeHtml4(rs.getString("HIST_INC_TAXREG_UI"));
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
