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


import javax.servlet.http.HttpServletRequest;

@Service("propertytaxretirementService")

public class PropertyTaxRetirementServiceImpl implements PropertyTaxRetirementService{
	
	@Autowired
	private DataTableDAO dataTableDao;
	
	@Override
	public SXSSFWorkbook getPropertyRetirementExcelData(HttpServletRequest request){
		
		SXSSFWorkbook workbook = null;
		Sheet reportSheet = null;
		workbook = new SXSSFWorkbook(100);
		reportSheet = workbook.createSheet("Reports");
		Row header = reportSheet.createRow(0);
		header.setHeight((short) 500);
		Row dataRow = null;
		
//		header.createCell(0).setCellValue("State");
//		header.createCell(1).setCellValue("Entity Name");
//		header.createCell(2).setCellValue("Entity");
//		header.createCell(3).setCellValue("Room & Rack");
//		header.createCell(4).setCellValue("Country");
//		header.createCell(5).setCellValue("State");
//		header.createCell(6).setCellValue("County");
//		header.createCell(7).setCellValue("City");
//		header.createCell(8).setCellValue("Street");
//		header.createCell(9).setCellValue("ZIP Code");
//		header.createCell(10).setCellValue("Building Number");
//		header.createCell(11).setCellValue("Unique Asset Number");
//		header.createCell(12).setCellValue("Legacy Asset Number");
//		header.createCell(13).setCellValue("Legacy Asset Sub Number");
//		header.createCell(14).setCellValue("Asset Description");
//		header.createCell(15).setCellValue("Asset Class");
//		header.createCell(16).setCellValue("Class Description");
//		header.createCell(17).setCellValue("GL Category Description");
//		header.createCell(18).setCellValue("Entered Date");
//		header.createCell(19).setCellValue("Install Date");
//		header.createCell(20).setCellValue("Disposal Date");
//		header.createCell(21).setCellValue("Assignment");
//		header.createCell(22).setCellValue("First Cost");
//		header.createCell(23).setCellValue("Disposal Cost");
//		header.createCell(24).setCellValue("Tax Install Date");
//		header.createCell(25).setCellValue("Tax Cost Basis");	
		
		
		String[] headers=null;
		List<String[]> headerList=getTableHeadings();
		int count=0;
		for (String[] strings : headerList) {
		    headers=strings;
		    header.createCell(count).setCellValue(headers[0]);
		   count++;
		}
		int rowNum = 1;
		
		String propertytaxretirementcompanygroup=request.getParameter("propertytaxretirementcompanygroup");
		String propertytaxretirementcompany=request.getParameter("proptaxretirementcompany");
		String propertytaxRetirementDateFrom =request.getParameter("proptaxRetirementDateFrom");
		String propertytaxRetirementDateTo =request.getParameter("proptaxRetirementDateTo");
		
		List<String> sArray = new ArrayList<String>();
		
		int mapi=0;
		Map<String,String> prepareParamMap=new LinkedHashMap<String, String>();
		
		
		if (null!=propertytaxretirementcompanygroup && !propertytaxretirementcompanygroup.equals("")) {
			sArray.add(" PRET_ENTITY in (select company from FACOMPANY where companygroup like ?)");
			prepareParamMap.put(++mapi+"_"+"like",propertytaxretirementcompanygroup);
		}
	
		if (null!=propertytaxretirementcompany && !propertytaxretirementcompany.equals("")) {
			sArray.add(" PRET_ENTITY like ? ");
			prepareParamMap.put(++mapi+"_"+"like",propertytaxretirementcompany);
		}
		
		if (null!=propertytaxRetirementDateFrom && !propertytaxRetirementDateFrom.equals("")) {
			String proptaxInstallQuery=" to_date(PRET_DISPOSAL_DATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
			
			
			//proptaxInstallQuery=proptaxInstallQuery.replace("proptaxRetirementDateFrom", propertytaxRetirementDateFrom);
			prepareParamMap.put(++mapi+"_"+"eq",propertytaxRetirementDateFrom);
			sArray.add(proptaxInstallQuery);
		}
		
		if (null!=propertytaxRetirementDateTo && !propertytaxRetirementDateTo.equals("")) {
			String proptaxInstallQuery=" to_date(PRET_DISPOSAL_DATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			
			//proptaxInstallQuery=proptaxInstallQuery.replace("proptaxRetirementDateTo", propertytaxRetirementDateTo);
			prepareParamMap.put(++mapi+"_"+"eq",propertytaxRetirementDateTo);
			sArray.add(proptaxInstallQuery);
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
		
		String tName=getReportTables();
		tName=tName.replaceAll("[^a-zA-Z0-9_]", "");
		String searchSQL= callProcedure(tName.trim());
		
				
		if(individualSearch!=null && individualSearch!=""){
			searchSQL = searchSQL+" where " + individualSearch;
		}
		
		searchSQL += " order by PRET_UNIQUE_ASSET asc";

		List<List<String>> resultDataList = new ArrayList<List<String>>();
		try {
			Connection conn = dataTableDao.getConnection();
			String getNumericCol="select column_name,data_type,column_id from sys.all_tab_columns where table_name= ? and data_type= 'NUMBER' order by column_id";
			PreparedStatement ps1=conn.prepareStatement(getNumericCol);
			ps1.setString(1, tName.trim());
			ResultSet numColRes = ps1.executeQuery();
			List<Integer> colId=new ArrayList<Integer>();
			while(numColRes.next())
			{
				int parsed=Integer.parseInt(numColRes.getString("column_id"));
				parsed=parsed-1;
				colId.add(parsed);
			}
			//System.out.println(colId);
			String searchSQL1 = searchSQL.replace("$tablename", tName.trim());
			//String searchSQL2= searchSQL1.replace("'", "");
			PreparedStatement ps = conn.prepareStatement(searchSQL1);
			System.out.println("this----"+searchSQL1);
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
			//List<String> resultRowData = new ArrayList<String>();
			int counter=0;
			ResultSetMetaData rsMeta=rs2.getMetaData();
			int ct=rsMeta.getColumnCount();
			while (rs2.next()) {
				counter++;
				List<String> resultRowData = new ArrayList<String>();
				for (int i = 1; i <= ct; i++) {					
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
						if (StringUtils.isNotBlank(entry) && colId.contains(cellNum)) {
							
							row.createCell(cellNum++).setCellValue(Double.parseDouble(entry));
						} else {
							row.createCell(cellNum++).setCellValue(entry);
						}

					}
				}
				
				resultDataList=null;
				 resultDataList = new ArrayList<List<String>>(1);//10
				
			}
			}
			for (List<String> list : resultDataList) {

				int cellNum = 0;
				// create the row data
				Row row = reportSheet.createRow(rowNum++);
				for (String entry : list) {
					if (StringUtils.isNotBlank(entry) && colId.contains(cellNum)) {
						
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
			//return resultDataList;
		}catch(Exception e) {
			System.out.println("Exception"+e);
		}
		return workbook;
	}

	@Override
	public String getPropertyRetirementDataTableResponse(HttpServletRequest request) {
		
		
//		String[] cols = {"PRET_STATESTATE","PRET_ENTITY_NAME","PRET_ENTITY","PRET_ROOM_AND_RACK","PRET_COUNTRY","PRET_STATE","PRET_COUNTY","PRET_CITY","PRET_STREET", "PRET_ZIP_CODE","PRET_BUILDING","PRET_UNIQUE_ASSET","PRET_ASSET_DESCRIPTION","PRET_ASSET","PRET_CLASS_DESCRIPTION","PRET_GL_CATEGORY_DESCRIPTION","PRET_ENTERED_DATE",
//		"PRET_INSTALL_DATE","PRET_DISPOSAL_DATE","PRET_ASSIGNMENT","PRET_FIRST_COST","PRET_DSIPOSAL_COST","PRET_TAX_INSTALL_DATE","PRET_TAX_COST_BASIS","FACOMPANY"};
//	
		String tName=getReportTables();
		tName=tName.replaceAll("[^a-zA-Z0-9_]", "");
		String table = callProcedure(tName.trim());

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
		
		String propertytaxretirementcompanygroup=request.getParameter("proptaxretirementcompanygroup");
		String propertytaxretirementcompany=request.getParameter("proptaxretirementcompany");
		String propertytaxRetirementDateFrom =request.getParameter("proptaxRetirementDateFrom");
		String propertytaxRetirementDateTo =request.getParameter("proptaxRetirementDateTo");
		
		
		List<String> sArray = new ArrayList<String>();
       
		int mapi=0;
		Map<String,String> prepareParamMap=new LinkedHashMap<String, String>();
		
		if (null!=propertytaxretirementcompanygroup && !propertytaxretirementcompanygroup.equals("")) {
			sArray.add(" PRET_ENTITY in (select company from FACOMPANY where companygroup like ?)");
			prepareParamMap.put(++mapi+"_"+"like",propertytaxretirementcompanygroup);
		}
	
		if (null!=propertytaxretirementcompany && !propertytaxretirementcompany.equals("")) {
			sArray.add(" PRET_ENTITY like ? ");
			prepareParamMap.put(++mapi+"_"+"like",propertytaxretirementcompany);
		}
		
		if (null!=propertytaxRetirementDateFrom && !propertytaxRetirementDateFrom.equals("")) {
			String proptaxInstallQuery=" to_date(PRET_DISPOSAL_DATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
			
			
			//proptaxInstallQuery=proptaxInstallQuery.replace("proptaxRetirementDateFrom", propertytaxRetirementDateFrom);
			prepareParamMap.put(++mapi+"_"+"eq",propertytaxRetirementDateFrom);
			sArray.add(proptaxInstallQuery);
		}
		
		if (null!=propertytaxRetirementDateTo && !propertytaxRetirementDateTo.equals("")) {
			String proptaxInstallQuery=" to_date(PRET_DISPOSAL_DATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			
			//proptaxInstallQuery=proptaxInstallQuery.replace("proptaxRetirementDateTo", propertytaxRetirementDateTo);
			prepareParamMap.put(++mapi+"_"+"eq",propertytaxRetirementDateTo);
			sArray.add(proptaxInstallQuery);
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
//		try 
//		{
//			String sql = "SELECT count(*) FROM "+ table +  " WHERE ROWNUM <= 200";
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
			String sql = table;
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
				sql=sql+" and ROWNUM <= 200 ";
			}else {
				sql=sql+" WHERE ROWNUM <= 200 ";
			}
			
//			sql += " order by " + colName + " " + dir;
			sql += " order by PRET_UNIQUE_ASSET asc";
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

	public List<String[]> getPropertyRetirementCompanyGroups() {
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
	
	public List<String[]> getPropertyRetirementInstances() {
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
	
		public List<String[]> getPropertyRetirementYears() {
			List<String[]> resultList = new ArrayList<String[]>();
			Connection conn;
			PreparedStatement ps;
			try {
				String query = "select distinct(YEAR) from INSTANCE  order by YEAR asc";
				conn = dataTableDao.getConnection();
				ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					String[] arr = new String[2];
					arr[0] = rs.getString("YEAR");
					arr[1] = rs.getString("YEAR");
					resultList.add(arr);
				}
				conn.close();
				ps.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			return resultList;
		}
		public List<String[]> getPropertyTaxRetirementCompanyNames(String COMPANYGROUP) {
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
	
		@Override
		public List<String[]> getTableHeadings() {
		
			String tableName="";
			
		  String tName=this.getReportTables();
		//	System.out.println("tb= "+tName.trim()+"this");
			
			 List<String[]> headingList = new ArrayList<String[]>();
				Connection conn;
				PreparedStatement ps;
				try {
				//String query = "select * from REPORT_INDEX_TABLE where REPORT_ID=? and YEAR=? order by DB_TABLE asc";
				//String query = "select COLUMN_NAME from SYS.all_tab_columns where table_name = ?";
				String query="select COLUMN_NAME,COLUMN_ID,HIST_INC_TAXREG_UI from SYS.all_tab_columns,all_tables_column_names where table_name = ? and COLUMN_NAME=HIST_INC_TAXREG_DB ORDER BY COLUMN_ID";
				conn = dataTableDao.getConnection();
				ps = conn.prepareStatement(query);
				ps.setString(1, tName.trim());
				ResultSet rs = ps.executeQuery();
				
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
		
		public String getReportTables() {
			String resultList = "";
			Connection conn;
			PreparedStatement ps;
			try {
			//String query = "select * from REPORT_INDEX_TABLE where REPORT_ID=? and YEAR=? order by DB_TABLE asc";
			String query = "select * from report_index_table where layout='SHPPTRET' and report_id='PRD-PPTRET'";
			conn = dataTableDao.getConnection();
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
			String[] arr = new String[1];
			arr[0] = StringEscapeUtils.escapeHtml4(rs.getString("DB_TABLE"));
			
			resultList=arr[0];
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
}
