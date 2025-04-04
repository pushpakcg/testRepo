package com.code2java.service;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
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

@Service("SAPstatetaxservice")

public class SAPHistStateTaxServiceImpl implements SAPHistStateTaxService{
	
	
		@Autowired
		private DataTableDAO dataTableDao;
		
		public SXSSFWorkbook getSAPHistStateTaxExcelData(HttpServletRequest request){
			SXSSFWorkbook workbook = null;
			Sheet reportSheet = null;
			workbook = new SXSSFWorkbook(100);
			reportSheet = workbook.createSheet("Reports");
			Row header = reportSheet.createRow(0);
			header.setHeight((short) 500);
			Row dataRow = null;
			
//			header.createCell(0).setCellValue("YEAR");
//			header.createCell(1).setCellValue("REPORT_NAME");
//			header.createCell(2).setCellValue("BUSINESS UNIT");
//			header.createCell(3).setCellValue("UNIQUE ASSET KEY");
//			header.createCell(4).setCellValue("ESTIMATED DATE OF ASSET");
//			header.createCell(5).setCellValue("ASSET DESCRIPTION");
//			header.createCell(6).setCellValue("GEOCODE-STATE");
//			header.createCell(7).setCellValue("GEOCODE-COUNTRY");
//			header.createCell(8).setCellValue("BOOK COST");
//			header.createCell(9).setCellValue("ACCUMULATED BOOK DEPR");
			
			String[] headers=null;
			List<String[]> headerList=getTableHeadings(request);
			int count=0;
			for (String[] strings : headerList) {
			    headers=strings;
			    header.createCell(count).setCellValue(headers[0]);
			   count++;
			}
			
			int rowNum = 1;
			
			String statetaxassetcompanygroup=request.getParameter("facompanygroup");
			String statetaxassetcompany=request.getParameter("facompany");
			String statetaxinstalldatefrom =request.getParameter("taxInstallDateFrom");
			String statetaxinstalldateto =request.getParameter("taxInstallDateTo");
			String stategroup =request.getParameter("stategroup");
			String stateyear=request.getParameter("year");
			String statereportId=request.getParameter("reportId");
			String tt=statereportId.trim();
			
			String tableName="";
		List<String[]> tableNameList=this.getReportTables(statereportId,stateyear);
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
         			
			if (null!=statetaxassetcompanygroup && !statetaxassetcompanygroup.equals("")) {
				sArray.add(" ST_BUSINESSUNIT in (select company from FACOMPANY where companygroup like ? )");
				prepareParamMap.put(++mapi+"_"+"like",statetaxassetcompanygroup);
			}

		if (null!=statetaxassetcompany && !statetaxassetcompany.equals("")) {
			sArray.add(" ST_BUSINESSUNIT like ? ");
			prepareParamMap.put(++mapi+"_"+"like",statetaxassetcompany);
			
		}
	
	  if (null!=stategroup && !stategroup.equals("")) {
	  sArray.add(" ST_GEOCODESTATE like ? ");
	  prepareParamMap.put(++mapi+"_"+"like",stategroup);
	  }
	 
					
		//STATE_ENTERED_DATE  PROP_INSTALL_DATE
		
		if (null!=statetaxinstalldatefrom && !statetaxinstalldatefrom.equals("")) {
			String statetaxInstallQuery=" to_date(case when ST_ESTIMATEDDATEOFASSET= '00000000' then null else ST_ESTIMATEDDATEOFASSET end,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
			
			
			//statetaxInstallQuery=statetaxInstallQuery.replace("statetaxInstallDateFrom", statetaxinstalldatefrom);
			prepareParamMap.put(++mapi+"_"+"eq",statetaxinstalldatefrom);
			sArray.add(statetaxInstallQuery);
		}
		
		if (null!=statetaxinstalldateto && !statetaxinstalldateto.equals("")) {
			String statetaxInstallQuery=" to_date(case when ST_ESTIMATEDDATEOFASSET= '00000000' then null else ST_ESTIMATEDDATEOFASSET end,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			
			//statetaxInstallQuery=statetaxInstallQuery.replace("statetaxInstallDateTo", statetaxinstalldateto);
			prepareParamMap.put(++mapi+"_"+"eq",statetaxinstalldateto);
			sArray.add(statetaxInstallQuery);
		}
	if (null!=stateyear && !stateyear.equals("")) {
		//sArray.add(" INC_COMPANYCODE like '%" + facompany + "%'");
		sArray.add("YEAR like ?");
		prepareParamMap.put(++mapi+"_"+"like",stateyear);
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
			
//			String searchSQL="select YEAR,REPORT_NAME,ST_BUSINESSUNIT,ST_UNIQUEASSETKEY,ST_ESTIMATEDDATEOFASSET,ST_ASSETDESCRIPTION,ST_GEOCODESTATE,ST_GEOCODECOUNTRY,ST_BOOKCOST,ST_ACCUMULATEDBOOKDEPR from $tablename";
			String searchSQL= callProcedure(table.trim());
					
			if(individualSearch!=null && individualSearch!=""){
				searchSQL = searchSQL+" where " + individualSearch;
			}
			
			searchSQL += " order by ST_UNIQUEASSETKEY asc";

			List<List<String>> resultDataList1 = new ArrayList<List<String>>();
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
					for (int i = 1; i <= ct; i++) {
							resultRowData.add(rs2.getString(i));
						
						
						
					}
					
					resultDataList1.add(resultRowData);
					resultRowData=new ArrayList<String>(1);
					if(counter % 30000==0)
					{
						for (List<String> list : resultDataList1) {

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
						resultDataList1=null;
						
						 resultDataList1 = new ArrayList<List<String>>(1);//10
						 
					}
					
					
				}
				for (List<String> list : resultDataList1) {

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
				resultDataList1=null;
			 resultDataList1 = new ArrayList<List<String>>(1);//10
				System.out.println("resultset End Time: "+new Date());
				
				ps.close();
				conn.close();
				rs2.close();
				//return resultDataList;
			}catch(Exception e) {
				System.out.println("Exception"+e);
			}
			return workbook;
		}

		public String getSAPHistStateTaxDataTableResponse(HttpServletRequest request) {
			
			
//		String[] cols = {"YEAR","REPORT_NAME","ST_BUSINESSUNIT","ST_UNIQUEASSETKEY","ST_ESTIMATEDDATEOFASSET","ST_ASSETDESCRIPTION","ST_GEOCODESTATE","ST_GEOCODECOUNTRY","ST_BOOKCOST","ST_ACCUMULATEDBOOKDEPR"};
			
			
			
		String stateyear=request.getParameter("year");
		String statereportId=request.getParameter("reportId");	
		String tableName="";
		//List<String[]> tableNameList=this.getReportTables(request.getParameter("reportId"),request.getParameter("year"));
		List<String[]> tableNameList=this.getReportTables(statereportId,stateyear);
		for (String[] strings : tableNameList) {
			//System.out.println("query======"+strings[2]);
			String tb=strings[2];
			tableName=tb;
		}
		//System.out.println("tableName==="+tableName);
		
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
			
			
			String statetaxassetcompanygroup=request.getParameter("facompanygroup");
			String statetaxassetcompany=request.getParameter("facompany");
			String statetaxinstalldatefrom =request.getParameter("taxInstallDateFrom");
			String statetaxinstalldateto =request.getParameter("taxInstallDateTo");
			//String statetaxstate =request.getParameter("stateTaxState");
			String stategroup =request.getParameter("stategroup");
			
			
			
			List<String> sArray = new ArrayList<String>();
			
			int mapi=0;
			Map<String,String> prepareParamMap=new LinkedHashMap<String, String>();
				
				if (null!=statetaxassetcompanygroup && !statetaxassetcompanygroup.equals("")) {
					sArray.add(" ST_BUSINESSUNIT in (select company from FACOMPANY where companygroup like ? )");
					prepareParamMap.put(++mapi+"_"+"like",statetaxassetcompanygroup);
				}

			if (null!=statetaxassetcompany && !statetaxassetcompany.equals("")) {
				sArray.add(" ST_BUSINESSUNIT like ? ");
				prepareParamMap.put(++mapi+"_"+"like",statetaxassetcompany);
				
			}
		
		  if (null!=stategroup && !stategroup.equals("")) {
		  sArray.add(" ST_GEOCODESTATE like ? ");
		  prepareParamMap.put(++mapi+"_"+"like",stategroup);
		  }
		 
						
			//STATE_ENTERED_DATE  PROP_INSTALL_DATE
			
			if (null!=statetaxinstalldatefrom && !statetaxinstalldatefrom.equals("")) {
				String statetaxInstallQuery=" to_date(ST_ESTIMATEDDATEOFASSET,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
				
				
				//statetaxInstallQuery=statetaxInstallQuery.replace("statetaxInstallDateFrom", statetaxinstalldatefrom);
				prepareParamMap.put(++mapi+"_"+"eq",statetaxinstalldatefrom);
				sArray.add(statetaxInstallQuery);
			}
			
			if (null!=statetaxinstalldateto && !statetaxinstalldateto.equals("")) {
				String statetaxInstallQuery=" to_date(ST_ESTIMATEDDATEOFASSET,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
				
				//statetaxInstallQuery=statetaxInstallQuery.replace("statetaxInstallDateTo", statetaxinstalldateto);
				prepareParamMap.put(++mapi+"_"+"eq",statetaxinstalldateto);
				sArray.add(statetaxInstallQuery);
			}
		if (null!=stateyear && !stateyear.equals("")) {
			//sArray.add(" INC_COMPANYCODE like '%" + facompany + "%'");
			sArray.add("YEAR like ?");
			prepareParamMap.put(++mapi+"_"+"like",stateyear);
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
			//String colName = cols[col];

			/* This is show the total count of records in Data base table */
			int total = 0;
			Connection conn = dataTableDao.getConnection();
//			try 
//			{
//				String sql = "SELECT count(*) FROM "+ table +  " WHERE REGEXP_LIKE (ST_ESTIMATEDDATEOFASSET,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$') and ROWNUM <= 200";
//				PreparedStatement ps = conn.prepareStatement(sql);
//				ResultSet rs = ps.executeQuery();
//				if(rs.next())
//				{
//					total = rs.getInt("count(*)");
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//			}

			/* This is total number of records that is available for the specific search query */
			int totalAfterFilter = total;

			try {
				String searchSQL = "";
				String sql = procedureQ;
				String searchTerm = request.getParameter("sSearch");
				String globeSearch = ""; 
//						" where ( upper(YEAR) like upper(?)"
//				+ " or upper(REPORT_NAME) like upper(?)"
//				+ " or upper(ST_BUSINESSUNIT) like upper(?)"		
//				+ " or upper(ST_UNIQUEASSETKEY) like upper(?)"
//				+ " or upper(ST_ESTIMATEDDATEOFASSET) like upper(?)"
//				+ " or upper(ST_ASSETDESCRIPTION) like upper(?)"
//				+ " or upper(ST_GEOCODESTATE) like upper(?)"
//				+ " or upper(ST_GEOCODECOUNTRY) like upper(?)"
//				+ " or upper(ST_BOOKCOST) like upper(?)"
//				+ " or upper(ST_ACCUMULATEDBOOKDEPR) like upper(?)";
				
				
				if(searchTerm!=null && searchTerm!="" && individualSearch!=null && individualSearch!=""){
					searchSQL = globeSearch + " and " + individualSearch;
				}
				else if(individualSearch!=null && individualSearch!=""){
					searchSQL = " where " + individualSearch;
				}else if(null!=searchTerm && searchTerm!=""){
					searchSQL=globeSearch;
				}
				sql += searchSQL;
				
			/*
			 * if(sql.contains("WHERE")||sql.contains("where")){
			 * sql=sql+" and ROWNUM <= 200 "; }else { sql=sql+" and ROWNUM <= 200 "; }
			 */
				
				if(sql.contains("WHERE")||sql.contains("where")){
					sql=sql+" and ROWNUM <= 200 and REGEXP_LIKE (ST_ESTIMATEDDATEOFASSET, '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
					}else {
					sql=sql+" and ROWNUM <= 200 and REGEXP_LIKE (ST_ESTIMATEDDATEOFASSET, '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
					}
				
				sql += " order by ST_UNIQUEASSETKEY asc" ;
				
				String sql1 = sql.replace("$tablename", table);
				
				PreparedStatement ps = conn.prepareStatement(sql1);
			//	System.out.println("test "+sql1);
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
//                String uniqassetkey="";
//				while (rs.next()) {
//					JSONArray ja = new JSONArray();
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("YEAR")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("REPORT_NAME")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("ST_BUSINESSUNIT")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("ST_UNIQUEASSETKEY")));
//                   
//					//ja.put(rs.getString("ST_UNIQUEASSETKEY"));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("ST_ESTIMATEDDATEOFASSET")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("ST_ASSETDESCRIPTION")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("ST_GEOCODESTATE")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("ST_GEOCODECOUNTRY")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("ST_BOOKCOST")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("ST_ACCUMULATEDBOOKDEPR")));
//					
//					array.put(ja);
//				}
				result.put("iTotalRecords", total);
				result.put("iTotalDisplayRecords", totalAfterFilter);
				result.put("aaData", array);
				result.put("sEcho", echo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result.toString();
		}
		
		public List<String[]> getSAPHistStateTaxCompanyGroups() {
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
		public List<String[]> getSAPHistStateTaxInstances() {
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
		public List<String[]> getSAPHistStateTaxYears() {
			List<String[]> resultList = new ArrayList<String[]>();
			Connection conn;
			PreparedStatement ps;
			try {
				String query = "select distinct(YEAR) from REPORT_INDEX_TABLE WHERE LAYOUT='SHSTAREG' AND VERSION = 'SAP_HIST' order by YEAR asc";
				conn = dataTableDao.getConnection();
				ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					String[] arr = new String[1];
					arr[0] = rs.getString("YEAR");
//					arr[1] = rs.getString("YEAR");
					resultList.add(arr);
				}
				conn.close();
				ps.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			return resultList;
		}
		
		public List<String[]> getSAPHistStateTaxReportNames(List<String> reportList) {
		//System.out.println("report Murli====="+reportList.get(0));
		//System.out.println("report Murli====="+reportList.get(1));
		List<String[]> resultList = new ArrayList<String[]>();
		Connection conn;
		PreparedStatement ps;
		try {
			
			String query = "select * from REPORT_INDEX_TABLE where YEAR=? AND LAYOUT='SHSTAREG' AND VERSION = 'SAP_HIST' order by REPORT_NAME asc";
			
		//	String query = "select * from REPORT_INDEX_TABLE where YEAR='2018' AND LAYOUT='ASTREG' order by REPORT_NAME asc";
			conn = dataTableDao.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, reportList.get(0));
			//System.out.println("report Murli====="+reportList.get(0));
		//	ps.setString(2, reportList.get(1));
			ResultSet rs = ps.executeQuery();
		//	System.out.println("report Murli array 1111++++====="+rs.getFetchSize());
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
	
		
		public List<String[]> getSAPHistStateTaxCompanyNames(String COMPANYGROUP) {
			List<String[]> resultList = new ArrayList<String[]>();
			Connection conn;
			PreparedStatement ps;
			try {
				//String query = "select *from FACOMPANY where COMPANYGROUP='"+COMPANYGROUP+"' order by COMPANYNAME asc";
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
		
		public List<String[]> getSAPHistStateNames() {
			
			List<String[]> resultList = new ArrayList<String[]>();
			Connection conn;
			PreparedStatement ps;
			try {
				
				//String query = "select *from FACOMPANY where COMPANYGROUP='"+COMPANYGROUP+"' order by COMPANYNAME asc";
				
				String query = "select STATE,STATE_DESC from STATECODE order by STATE asc";
				
				
				conn = dataTableDao.getConnection();
				ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					String[] arr = new String[2];
					arr[0] = rs.getString("STATE");
					arr[1] = rs.getString("STATE_DESC");
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
			String query = "select * from REPORT_INDEX_TABLE where REPORT_ID LIKE ? and YEAR=? order by DB_TABLE asc";
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
				//System.out.println("Hist prop tax tax ret table name"+reportTableName);
				stm.setString(1, reportTableName);
			    stm.registerOutParameter(2,Types.VARCHAR);
			    stm.execute();
			    m_count = stm.getString(2);
			 //   System.out.println("Hist prop tax tax ret store procedure query=="+m_count.toString());
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


