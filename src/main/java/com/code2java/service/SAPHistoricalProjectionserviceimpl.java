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

@Service("SAPHistoricalProjectionregService")
public class SAPHistoricalProjectionserviceimpl implements SAPHistoricalProjectionregService{

	@Autowired
	private DataTableDAO dataTableDao;
	
	 public SXSSFWorkbook getSAPHistoricalProjectionExcelData(HttpServletRequest request){
		 
		 SXSSFWorkbook workbook = null;
			Sheet reportSheet = null;
			workbook = new SXSSFWorkbook(100);
			reportSheet = workbook.createSheet("Reports");
			Row header = reportSheet.createRow(0);
			header.setHeight((short) 500);
			Row dataRow = null;
			
			String[] headers=null;
			List<String[]> headerList=getTableHeadings(request);
			int count=0;
			for (String[] strings : headerList) {
			    headers=strings;
			    header.createCell(count).setCellValue(headers[0]);
			   count++;
			}
			int rowNum = 1;
		
	    String histprojtaxfacompanygroup=request.getParameter("facompanygroup");
		String histprojtaxfacompany=request.getParameter("facompany");
		String histprojtaxInstallDateFrom=request.getParameter("projInstallDateFrom");
		String histprojtaxInstallDateTo=request.getParameter("projInstallDateTo");
		String histprojtaxCreateDateFrom=request.getParameter("projCreateDateFrom");
		String histprojtaxCreateDateTo=request.getParameter("projCreateDateTo");		
		String histprojtaxyear=request.getParameter("year");
		String histprojtaxreportId=request.getParameter("reportId");
		String tt=histprojtaxreportId.trim();
	
			
		String tableName="";
		//List<String[]> tableNameList=this.getReportTables(request.getParameter("reportId"),request.getParameter("year"));
		List<String[]> tableNameList=this.getReportTables(histprojtaxreportId,histprojtaxyear);
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
		
		if (null!=histprojtaxfacompany && !histprojtaxfacompany.equals("")) {			
			sArray.add(" FOR_COMPANYCODE like ?");
			prepareParamMap.put(++mapi+"_"+"like",histprojtaxfacompany);
		}
		
		if (null!=histprojtaxfacompanygroup && !histprojtaxfacompanygroup.equals("")) {			
			sArray.add(" FOR_COMPANYCODE in (select company from FACOMPANY where companygroup like ?)");
			prepareParamMap.put(++mapi+"_"+"like",histprojtaxfacompanygroup);
		}
		
		if (null!=histprojtaxInstallDateFrom && !histprojtaxInstallDateFrom.equals("")) {			
			String taxInstallQuery=" to_date(case when FOR_INSTALLDATE= '00000000' then null else FOR_INSTALLDATE end,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
			prepareParamMap.put(++mapi+"_"+"eq",histprojtaxInstallDateFrom);
			sArray.add(taxInstallQuery);
		}
		
	if (null!=histprojtaxInstallDateTo && !histprojtaxInstallDateTo.equals("")) {			
			String taxInstallQuery=" to_date(case when FOR_INSTALLDATE= '00000000' then null else FOR_INSTALLDATE end,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			prepareParamMap.put(++mapi+"_"+"eq",histprojtaxInstallDateTo);
			sArray.add(taxInstallQuery);
		}
		
		if (null!=histprojtaxCreateDateFrom && !histprojtaxCreateDateFrom.equals("")) {
			String taxCreateQuery=" to_date(FOR_ENTEREDDATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') " ;
			prepareParamMap.put(++mapi+"_"+"eq",histprojtaxCreateDateFrom);
			sArray.add(taxCreateQuery);
		}
		
		if (null!=histprojtaxCreateDateTo && !histprojtaxCreateDateTo.equals("")) {
			String taxCreateQuery=" to_date(FOR_ENTEREDDATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";			
			prepareParamMap.put(++mapi+"_"+"eq",histprojtaxCreateDateTo);
			sArray.add(taxCreateQuery);
		}
		
		if (null!=histprojtaxyear && !histprojtaxyear.equals("")) {			
			sArray.add(" YEAR like ?");
			prepareParamMap.put(++mapi+"_"+"like",histprojtaxyear);
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
		
//		String searchSQL="select YEAR,REPORT_NAME,FOR_UNIQUEASSET,FOR_COMPANYCODE,FOR_ASSETNUMBER,FOR_SUBNUMBER,FOR_LEGUNIQUE,FOR_ASSETDESCRIPTION,FOR_ASSETCLASSS,FOR_ASSETCLASSDESCRIPTION,FOR_LEGACYCATEGORYCODE,FOR_ENTEREDDATE,FOR_INSTALLDATE,\r\n" + 
//		        "(select CASE when FOR_INSTALLDATE <> '00000000' then EXTRACT(YEAR FROM TO_DATE(FOR_INSTALLDATE, 'MM-DD-YYYY')) else 0 end FROM dual) AS FOR_TAXINSTALLATIONYR, FOR_ASSETLIFE,FOR_REMAININGLIFE,FOR_TAXAPC,\r\n" + 
//		        "(SELECT CASE WHEN to_char(FOR_ASSETCLASSS)='0000' THEN '0' ELSE to_char(FOR_TAXAPC) END from dual) AS FOR_DEPRECIABLEBASIS, FOR_ACCUMULATEDRESERVE,FOR_YEARTODATEDEPREC,\r\n" + 
//		        "FOR_TAXNBV,FOR_BONUSDEPRECIATION,FOR_TAXYTDBONUSDEPR,FOR_PROJECTION_YEAR1,FOR_PROJECTION_YEAR2,FOR_PROJECTION_YEAR3,FOR_PROJECTION_YEAR4,FOR_PROJECTION_YEAR5,FOR_ELECTOUTBONUS,\r\n" + 
//		        " (select case when (FOR_BONUSDEPRECIATION) <> 0 AND (FOR_TAXAPC) <> 0 then TRUNC(abs(FOR_BONUSDEPRECIATION)/abs(FOR_TAXAPC)*100,0) else 0 end from dual)AS FOR_TAXBONUSPERCENTAGE,\r\n" + 
//		        "FOR_FIRSTYEARDEPRECIATED,FOR_LASTYEARDEPRECIATED,FOR_LASTMONTHDEPRECIATED,FOR_NEWORUSED,FOR_TAXDEPRMETHOD,FOR_TAXDEPRCONVENTION,\r\n" + 
//		        "FOR_CURRENTACCOUNTINGYEAR,FOR_CORPINSTALLDATE,FOR_CORPLIFE,FOR_CORPBOOKAPC,FOR_CORPBOOKACCUMRESERVE,FOR_CORPNBV,FOR_RECORDCREATIONDATE,FOR_RECORDCREATIONTIME,FOR_RECORDCREATIONUSER,FOR_POSTCAPITALIZATION  from $tablename";  
//
//		
		String searchSQL= callProcedure(table.trim());
		
		if(individualSearch!=null && individualSearch!=""){
			searchSQL = searchSQL+" where " + individualSearch;
			
		}
		
		searchSQL += " order by FOR_UNIQUEASSET asc";

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
			//System.out.println("count: "+ct);
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
							//if(cellNum==14 && StringUtils.isNotBlank(entry)) {
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
					if (StringUtils.isNotBlank(entry) && (colId.contains(cellNum))) {
						
						row.createCell(cellNum++).setCellValue(Double.parseDouble(entry));
					} else {
						row.createCell(cellNum++).setCellValue(entry);
					}
						
					
				}
			}	
			
			resultDataList=null;
		 resultDataList = new ArrayList<List<String>>(1);//10
			ps.close();
			conn.close();
			rs2.close();
		}catch(Exception e) {
			System.out.println("Exception"+e);
		}
		return workbook;
	}


	@Override
public String getSAPHistoricalProjectionDataTableResponse(HttpServletRequest request) {
		
//		String[] cols = {"YEAR","REPORT_NAME","FOR_UNIQUEASSET","FOR_COMPANYCODE","FOR_ASSETNUMBER","FOR_SUBNUMBER","FOR_LEGUNIQUE","FOR_ASSETDESCRIPTION","FOR_ASSETCLASSS","FOR_ASSETCLASSDESCRIPTION","FOR_LEGACYCATEGORYCODE","FOR_ENTEREDDATE", "FOR_INSTALLDATE","FOR_TAXINSTALLATIONYR","FOR_ASSETLIFE","FOR_REMAININGLIFE","FOR_TAXAPC","FOR_DEPRECIABLEBASIS","FOR_ACCUMULATEDRESERVE","FOR_YEARTODATEDEPREC",
//				"FOR_TAXNBV","FOR_BONUSDEPRECIATION","FOR_TAXYTDBONUSDEPR","FOR_PROJECTION_YEAR1","FOR_PROJECTION_YEAR2","FOR_PROJECTION_YEAR3","FOR_PROJECTION_YEAR4","FOR_PROJECTION_YEAR5","FOR_ELECTOUTBONUS","FOR_TAXBONUSPERCENTAGE","FOR_FIRSTYEARDEPRECIATED","FOR_LASTYEARDEPRECIATED",
//				"FOR_LASTMONTHDEPRECIATED","FOR_NEWORUSED","FOR_TAXDEPRMETHOD","FOR_TAXDEPRCONVENTION","FOR_CURRENTACCOUNTINGYEAR","FOR_CORPINSTALLDATE","FOR_CORPLIFE","FOR_CORPBOOKAPC","FOR_CORPBOOKACCUMRESERVE","FOR_CORPNBV","FOR_RECORDCREATIONDATE","FOR_RECORDCREATIONTIME","FOR_RECORDCREATIONUSER","FOR_POSTCAPITALIZATION"};
//				
		
		String histproptaxyear=request.getParameter("year");
		String histproptaxreportId=request.getParameter("reportId");
		String tableName="";		
		List<String[]> tableNameList=this.getReportTables(histproptaxreportId,histproptaxyear);
		for (String[] strings : tableNameList) {
			//System.out.println("query======"+strings[2]);
			String tb=strings[2];
			tableName=tb;
		}
		//System.out.println("tableName==="+tableName);
		
		//String table = tableName.replace("'", "");
		//table=table.replaceAll("[^a-zA-Z0-9_]", "");
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
		String histproptaxInstallDateFrom=request.getParameter("projInstallDateFrom");
		String histproptaxInstallDateTo=request.getParameter("projInstallDateTo");
		String histproptaxCreateDateFrom=request.getParameter("projCreateDateFrom");
		String histproptaxCreateDateTo=request.getParameter("projCreateDateTo");
		

		List<String> sArray = new ArrayList<String>();
		
		int mapi=0;
		Map<String,String> prepareParamMap=new LinkedHashMap<String, String>();
		
		if (null!=histproptaxfacompany && !histproptaxfacompany.equals("")) {
			//sArray.add(" INC_COMPANYCODE like '%" + facompany + "%'");
			sArray.add(" FOR_COMPANYCODE like ?");
			prepareParamMap.put(++mapi+"_"+"like",histproptaxfacompany);
		}
		
		if (null!=histproptaxfacompanygroup && !histproptaxfacompanygroup.equals("")) {
			//sArray.add(" INC_COMPANYCODE in (select company from FACOMPANY where companygroup like '%" + facompanygroup + "%')");	
			sArray.add(" FOR_COMPANYCODE in (select company from FACOMPANY where companygroup like ?)");
			prepareParamMap.put(++mapi+"_"+"like",histproptaxfacompanygroup);
		}
		
		if (null!=histproptaxInstallDateFrom && !histproptaxInstallDateFrom.equals("")) {
			String taxInstallQuery=" to_date(FOR_INSTALLDATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
			
			//taxInstallQuery=taxInstallQuery.replace("taxInsFromParam", taxInstallDateFrom);
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxInstallDateFrom);
			sArray.add(taxInstallQuery);
		}
		
		if (null!=histproptaxInstallDateTo && !histproptaxInstallDateTo.equals("")) {
			String taxInstallQuery=" to_date(FOR_INSTALLDATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			
			//taxInstallQuery=taxInstallQuery.replace("taxInsToParam", taxInstallDateTo);
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxInstallDateTo);
			sArray.add(taxInstallQuery);
		}
		
		if (null!=histproptaxCreateDateFrom && !histproptaxCreateDateFrom.equals("")) {
			String taxCreateQuery=" to_date(FOR_ENTEREDDATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') " ;
			
			//taxInstallQuery=taxInstallQuery.replace("taxCreFromParam", taxCreateDateFrom);
			prepareParamMap.put(++mapi+"_"+"eq",histproptaxCreateDateFrom);
			sArray.add(taxCreateQuery);
		}
		
		if (null!=histproptaxCreateDateTo && !histproptaxCreateDateTo.equals("")) {
			String taxCreateQuery=" to_date(FOR_ENTEREDDATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			
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
			String globeSearch =  "";
			
			
			
			
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
				sql=sql+" and ROWNUM <= 200 and REGEXP_LIKE (FOR_INSTALLDATE, '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
				}else {
				sql=sql+" and ROWNUM <= 200 and REGEXP_LIKE (FOR_INSTALLDATE, '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
				}
			
			//sql += " order by " + colName + " " + dir;
			sql += " order by FOR_UNIQUEASSET asc";
			
			//System.out.println("sqlqsql==="+sql);
			String sql1 = sql.replace("$tablename", table);
		//	System.out.println(sql1);
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
	//getSAPHistoricalProjectionCompanyGroups
	public List<String[]> getSAPHistoricalProjectionCompanyGroups() {
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
			String query = "select distinct(YEAR) from REPORT_INDEX_TABLE WHERE LAYOUT='SHASTPRJ' AND VERSION = 'SAP_HIST' order by YEAR asc";
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
		//System.out.println("report Murli====="+reportList.get(0));
		//System.out.println("report Murli====="+reportList.get(1));
		List<String[]> resultList = new ArrayList<String[]>();
		Connection conn;
		PreparedStatement ps;
		try {
			
			String query = "select * from REPORT_INDEX_TABLE where YEAR=? AND LAYOUT='SHASTPRJ' AND VERSION = 'SAP_HIST' order by REPORT_NAME asc";
			
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
	
	
	public List<String[]> getSAPHistoricalProjectionCompanyNames(String COMPANYGROUP) {
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


//	@Override
	//public String getSAPHistoricalProjectionDataTableResponse(HttpServletRequest request) {
		// TODO Auto-generated method stub
	//	return null;
//	}


	

	//@Override
	//public List<String[]> getSAPHistoricalProjectionCompanyNames(String COMPANYGROUP) {
		// TODO Auto-generated method stub
	//	return null;
//	}


	//@Override
	//public List<List<String>> getSAPHistoricalProjectionExcelData(HttpServletRequest request) {
		// TODO Auto-generated method stub
	//	return null;
	//}


	@Override
	//public List<String[]> getSAPHistoricalProjectioinInstances() {
		// TODO Auto-generated method stub
		//Instance
		public List<String[]> getSAPHistoricalProjectioinInstances() {
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
		
		
		
		
		
	
	       @Override
			public List<String[]> getSAPHistoricalProjectionYears() {
			List<String[]> resultList = new ArrayList<String[]>();
			Connection conn;
			PreparedStatement ps;
			try {
				String query = "select distinct(YEAR) from REPORT_INDEX_TABLE WHERE LAYOUT='SHASTPRJ' AND VERSION = 'SAP_HIST' order by YEAR asc";
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
		
		

	         @Override
			public List<String[]> getSAPHistoricalProjectionReportNames(List<String> reportList) {
			//System.out.println("report Murli====="+reportList.get(0));
			//System.out.println("report Murli====="+reportList.get(1));
			List<String[]> resultList = new ArrayList<String[]>();
			Connection conn;
			PreparedStatement ps;
			try {
				
				String query = "select * from REPORT_INDEX_TABLE where YEAR=? AND LAYOUT='SHASTPRJ' AND VERSION = 'SAP_HIST' order by REPORT_NAME asc";
				
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

			public String callProcedure(String reportTableName)
			{

				
				
				String m_count="";
				

				try {
					Connection	conn1 = dataTableDao.getConnection();

					
					CallableStatement stm = conn1.prepareCall("{ call DYNAMICOLUMN(?,?) }");
					//System.out.println("ggg"+reportTableName);
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
