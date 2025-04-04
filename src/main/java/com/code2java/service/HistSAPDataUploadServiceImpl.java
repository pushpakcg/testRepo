package com.code2java.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.code2java.dao.DataTableDAO;
import com.code2java.model.UploadFile;
@Service("histSAPDataService")
public class HistSAPDataUploadServiceImpl implements HistSAPDataUploadService{
//arjun code starts
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		}
//Arjun code ends	
	@Autowired
	private DataTableDAO dataTableDao;

		@Override
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

	@Override
	public List<String[]> getSAPDataUploadReportNames() {
		List<String[]> resultList = new ArrayList<String[]>();
		Connection conn;
		PreparedStatement ps;
		try {
			
			String query = "select * from REPORT_INDEX_TABLE where VERSION LIKE '%Prod%' order by REPORT_NAME asc";
			conn = dataTableDao.getConnection();
			ps = conn.prepareStatement(query);
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

	@Override
	public String getSAPSSOId(String loginSSO) {
		String flag = "N";
		Connection conn;
		PreparedStatement ps;
		if(loginSSO!=null && !loginSSO.equals(""))
		{
		try {
			String query = "select * from ADMIN_DETAILS WHERE SSO LIKE ?";
			conn = dataTableDao.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, "%" + loginSSO + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				flag=StringEscapeUtils.escapeHtml4(rs.getString("STATUS"));	
			}
			
			conn.close();
			ps.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		}
		return flag;
	}
	
	
	@Override
	public String saveSAP1TableData(HttpServletRequest request) {

		String rptId=StringEscapeUtils.escapeHtml4(request.getParameter("reportId"));
		String hisreportId=StringEscapeUtils.escapeHtml4(request.getParameter("hisreportId"));
		String hisreportNm=StringEscapeUtils.escapeHtml4(request.getParameter("hisreportNm"));
		String hisreportVersion=StringEscapeUtils.escapeHtml4(request.getParameter("hisreportVersion"));
		String hissystem=StringEscapeUtils.escapeHtml4(request.getParameter("hissystem"));
		String hisyear=StringEscapeUtils.escapeHtml4(request.getParameter("hisyear"));
		String hisdbTable=StringEscapeUtils.escapeHtml4(request.getParameter("hisdbTable"));
		String hiscreationDate=StringEscapeUtils.escapeHtml4(request.getParameter("hiscreationDate"));
		String hisupdateDate=StringEscapeUtils.escapeHtml4(request.getParameter("hisupdateDate"));
		String hisamendmentDate=StringEscapeUtils.escapeHtml4(request.getParameter("hisamendmentDate"));
		String hislayout=StringEscapeUtils.escapeHtml4(request.getParameter("hislayout"));
		
		//Arjun code starts
		String insertquery = "INSERT INTO REPORT_INDEX_TABLE"
				+ "(REPORT_ID, REPORT_NAME, VERSION, SYSTEM, LAYOUT, YEAR, DB_TABLE, CREATION_DATE, UPDATE_DATE, AMENDMENT_DATE) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				jdbcTemplate.update(insertquery, hisreportId, hisreportNm,hisreportVersion,hissystem,hislayout,
				hisyear,hisdbTable,hiscreationDate,hisupdateDate,hisamendmentDate);
		//Arjun code ends		

		/*
		 * Connection conn1; PreparedStatement ps1; try { String query =
		 * "INSERT INTO REPORT_INDEX_TABLE" +
		 * "(REPORT_ID, REPORT_NAME, VERSION, SYSTEM, LAYOUT, YEAR, DB_TABLE, CREATION_DATE, UPDATE_DATE, AMENDMENT_DATE) "
		 * + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; conn1 =
		 * dataTableDao.getConnection(); ps1 = conn1.prepareStatement(query);
		 * ps1.setString(1, StringEscapeUtils.escapeHtml4(hisreportId));
		 * ps1.setString(2, StringEscapeUtils.escapeHtml4(hisreportNm));
		 * ps1.setString(3, StringEscapeUtils.escapeHtml4(hisreportVersion));
		 * ps1.setString(4, StringEscapeUtils.escapeHtml4(hissystem)); ps1.setString(5,
		 * StringEscapeUtils.escapeHtml4(hislayout)); ps1.setString(6,
		 * StringEscapeUtils.escapeHtml4(hisyear)); ps1.setString(7,
		 * StringEscapeUtils.escapeHtml4(hisdbTable)); ps1.setString(8,
		 * StringEscapeUtils.escapeHtml4(hiscreationDate)); ps1.setString(9,
		 * StringEscapeUtils.escapeHtml4(hisupdateDate)); ps1.setString(10,
		 * StringEscapeUtils.escapeHtml4(hisamendmentDate)); ps1.executeQuery();
		 * conn1.close(); ps1.close(); } catch (Exception e) { System.out.println(e); }
		 */
		
		Connection conn;
		PreparedStatement ps;
		PreparedStatement ps1;
		//saveSAPTableData(reportList);
		String Reportid=StringEscapeUtils.escapeHtml4(rptId);
		String table=hisdbTable.replaceAll("[^a-zA-Z0-9_]", "");
		String ReportName=StringEscapeUtils.escapeHtml4(hisreportNm);
		String year=StringEscapeUtils.escapeHtml4(hisyear);
		String status="";
		if(Reportid!=null && Reportid.trim().equals("PRD-TAXFAR"))
		{
		try {
			//String query = "CREATE TABLE $tableName AS(SELECT * FROM INC_TAX_ASSET_REG_REPORT)";
			String toaQuery="select column_name from all_tab_columns where table_name='TAX_ONLY_ASSETS_TEST' order by column_id";
			Connection connection1 = dataTableDao.getConnection();
			ps1 = connection1.prepareStatement(toaQuery);
			ResultSet rs1 = ps1.executeQuery();
			StringBuilder sb =new StringBuilder();
			List<String> excludeCols=new ArrayList<>();
			excludeCols.add("TOA_LASTUPDATEDBY");
			excludeCols.add("TOA_LASTUPDATESDATE");
			excludeCols.add("TOA_STATUS");
			
			StringJoiner toa = new StringJoiner(", ");
			while(rs1.next())
			{
				String val=rs1.getString("column_name");
				if(!excludeCols.contains(val))
				{
					toa.add(val);
				}
				
				System.out.println(val);
			}
			System.out.println(toa.toString());
			String query = "create table $tableName as (select * from key_fields, (select * from INC_TAX_ASSET_REG_REPORT union all select "+toa.toString()+" from TAX_ONLY_ASSETS_TEST))";
			conn = dataTableDao.getConnection();
			String query1 =query.replace("$tableName",StringEscapeUtils.escapeHtml4(table));
			String query2=query1.replace("'", "");
			ps = conn.prepareStatement(query2);
			System.out.println(query2);
			ResultSet rs = ps.executeQuery();
			status=Integer.toString(rs.getRow());
			conn.close();
			ps.close();
		} catch (Exception e) {
			//System.out.println(e);
		}
//		}
		}
		else if(Reportid!=null && Reportid.trim().equals("PRD-TAXRET"))
		{
			try {
				//String query = "CREATE TABLE $tableName AS(SELECT * FROM INC_TAX_ASSET_RETIRE_REPORT)";
				String query = "create table $tableName as (select * from key_fields, INC_TAX_ASSET_RETIRE_REPORT)";
				conn = dataTableDao.getConnection();
				String query1 =query.replace("$tableName",StringEscapeUtils.escapeHtml4(table));
				String query2=query1.replace("'", "");
				ps = conn.prepareStatement(query2);
				ResultSet rs = ps.executeQuery();
				status=Integer.toString(rs.getRow());
				conn.close();
				ps.close();
			} catch (Exception e) {
				System.out.println(e);
			}	
		}
		else if(Reportid!=null && Reportid.trim().equals("PRD-TAXPRJ"))
		{
			try {
				//String query = "CREATE TABLE $tableName AS(SELECT * FROM INC_TAX_PROJ_REPORT)";
				String query = "create table $tableName as (select * from key_fields, INC_TAX_PROJ_REPORT)";
				conn = dataTableDao.getConnection();
				String query1 =query.replace("$tableName",StringEscapeUtils.escapeHtml4(table));
				String query2=query1.replace("'", "");
				ps = conn.prepareStatement(query2);
				ResultSet rs = ps.executeQuery();
				status=Integer.toString(rs.getRow());
				conn.close();
				ps.close();
			} catch (Exception e) {
				System.out.println(e);
			}	
		}
		else if(Reportid!=null && Reportid.trim().equals("PRD-ADSFAR"))
		{
			try {
				//String query = "CREATE TABLE $tableName AS (SELECT * FROM ADS_TAX_ASSET_REGISTER_REPORT)";
				String query = "create table $tableName as (select * from key_fields, ADS_TAX_ASSET_REGISTER_REPORT)";
				conn = dataTableDao.getConnection();
				String query1 =query.replace("$tableName",StringEscapeUtils.escapeHtml4(table));
				String query2=query1.replace("'", "");
				ps = conn.prepareStatement(query2);
				ResultSet rs = ps.executeQuery();
				status=Integer.toString(rs.getRow());
				conn.close();
				ps.close();
			} catch (Exception e) {
				System.out.println(e);
			}	
		}
		else if(Reportid!=null && Reportid.trim().equals("PRD-PPTFAR"))
		{
			try {
				//String query = "CREATE TABLE $tableName AS (SELECT * FROM PROP_TAX_ASSET_LIST_REPORT)";
				String query = "create table $tableName as (select * from key_fields, PROP_TAX_ASSET_LIST_REPORT)";
				conn = dataTableDao.getConnection();
				String query1 =query.replace("$tableName",StringEscapeUtils.escapeHtml4(table));
				String query2=query1.replace("'", "");
				ps = conn.prepareStatement(query2);
				ResultSet rs = ps.executeQuery();
				status=Integer.toString(rs.getRow());
				conn.close();
				ps.close();
			} catch (Exception e) {
				System.out.println(e);
			}	
		}
		else if(Reportid!=null && Reportid.trim().equals("PRD-PPTRET"))
		{
			try {
				//String query = "CREATE TABLE $tableName AS (SELECT * FROM PROP_TAX_ASSET_RETIREMENT_REPORT)";
				String query = "create table $tableName as (select * from key_fields, PROP_TAX_ASSET_RETIREMENT_REPORT)";
				conn = dataTableDao.getConnection();
				String query1 =query.replace("$tableName",StringEscapeUtils.escapeHtml4(table));
				String query2=query1.replace("'", "");
				ps = conn.prepareStatement(query2);
				ResultSet rs = ps.executeQuery();
				status=Integer.toString(rs.getRow());
				conn.close();
				ps.close();
			} catch (Exception e) {
				System.out.println(e);
			}	
		}
		else if(Reportid!=null && Reportid.trim().equals("PRD-STAFAR"))
		{
			try {
				//String query = "CREATE TABLE $tableName AS (SELECT * FROM STATE_TAX_ASSET_LISTING_REPORT)";
				String query = "create table $tableName as (select * from key_fields, STATE_TAX_ASSET_LISTING_REPORT)";
				conn = dataTableDao.getConnection();
				String query1 =query.replace("$tableName",StringEscapeUtils.escapeHtml4(table));
				String query2=query1.replace("'", "");
				ps = conn.prepareStatement(query2);
				ResultSet rs = ps.executeQuery();
				status=Integer.toString(rs.getRow());
				conn.close();
				ps.close();
			} catch (Exception e) {
				System.out.println(e);
			}	
		}
		else if(Reportid!=null && Reportid.trim().equals("PRD-ASTTR1"))
		{
			try {
				//String query = "CREATE TABLE $tableName AS (SELECT * FROM STATE_TAX_ASSET_LISTING_REPORT)";
				String query = "create table $tableName as (select * from key_fields, ASSET_TRANSFER_REPORT_PHYSICAL_LOCATION)";
				conn = dataTableDao.getConnection();
				String query1 =query.replace("$tableName",StringEscapeUtils.escapeHtml4(table));
				ps = conn.prepareStatement(query1);
				ResultSet rs = ps.executeQuery();
				status=Integer.toString(rs.getRow());
				conn.close();
				ps.close();
			} catch (Exception e) {
				System.out.println(e);
			}	
		}
		else if(Reportid!=null && Reportid.trim().equals("PRD-ASTTR2"))
		{
			try {
				//String query = "CREATE TABLE $tableName AS (SELECT * FROM STATE_TAX_ASSET_LISTING_REPORT)";
				String query = "create table $tableName as (select * from key_fields, ASSET_TRANSFER_ALL_TRANSACTION_REPORT)";
				conn = dataTableDao.getConnection();
				String query1 =query.replace("$tableName",StringEscapeUtils.escapeHtml4(table));
				ps = conn.prepareStatement(query1);
				ResultSet rs = ps.executeQuery();
				status=Integer.toString(rs.getRow());
				conn.close();
				ps.close();
			} catch (Exception e) {
				System.out.println(e);
			}	
		}
		else if(Reportid!=null && Reportid.trim().equals("PRD-SITR"))
		{
			try {
				String query = "create table $tableName as (select * from key_fields, STATE_INCOME_TAX_REPORT)";
				conn = dataTableDao.getConnection();
				String query1 =query.replace("$tableName",StringEscapeUtils.escapeHtml4(table));
				ps = conn.prepareStatement(query1);
				ResultSet rs = ps.executeQuery();
				status=Integer.toString(rs.getRow());
				conn.close();
				ps.close();
			} catch (Exception e) {
				System.out.println(e);
			}	
		}
		else
		{
			System.out.println("table not exist");
		}
		
//Adding Additional columns
		
		/*
		 * Connection conn2; PreparedStatement ps2; try { String query =
		 * "ALTER TABLE $tableName ADD(REPORT_NAME varchar(100),YEAR varchar(4))"; conn2
		 * = dataTableDao.getConnection(); String query1
		 * =query.replace("$tableName",StringEscapeUtils.escapeHtml4(table)); String
		 * query2=query1.replace("'", ""); ps2 = conn2.prepareStatement(query2);
		 * ps2.executeQuery(); conn2.close(); ps2.close(); } catch (Exception e) {
		 * System.out.println(e); }
		 */
		

//Insert report name and Year data into reference table
		Connection conn3;
		PreparedStatement ps3;
		try {
			String query = "UPDATE $tableName SET REPORT_NAME= ?, YEAR= ?";
			conn3 = dataTableDao.getConnection();
			String query1 =query.replace("$tableName",StringEscapeUtils.escapeHtml4(table));
			String query2=query1.replace("'", "");
			ps3 = conn3.prepareStatement(query2);
			ps3.setString(1, ReportName);
			ps3.setString(2, year);
			ps3.executeQuery();
			conn3.close();
			ps3.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		if(status!="")
		{
			status="Data saved Sucessfully";
		}
		else {
			status="Data Not saved Sucessfully";
		}
		
		return status;
	}
		
	
	
	@Override
	public List<String> getSAPTableData(UploadFile file) {
		String reportid=file.getReportName();
		List<String> tableList = new ArrayList<String>();
		PreparedStatement ps;
		Connection conn;
	    try {
	    	String query = "select *from REPORT_INDEX_TABLE where REPORT_ID LIKE ? and VERSION LIKE '%Prod%' order by DB_TABLE asc";
	    	conn = dataTableDao.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, "%" + reportid + "%");   	
	      ResultSet rs = ps.executeQuery();
	      while (rs.next()) {
	        String reportId = StringEscapeUtils.escapeHtml4(rs.getString("REPORT_ID"));
	        String version = StringEscapeUtils.escapeHtml4(rs.getString("VERSION"));
	        String system = StringEscapeUtils.escapeHtml4(rs.getString("SYSTEM"));
	        String dbTable = StringEscapeUtils.escapeHtml4(rs.getString("DB_TABLE"));
	        String createDate =StringEscapeUtils.escapeHtml4(rs.getString("CREATION_DATE"));
	        String updateDate = StringEscapeUtils.escapeHtml4(rs.getString("UPDATE_DATE"));
	        String amendentDate = StringEscapeUtils.escapeHtml4(rs.getString("AMENDMENT_DATE"));
	        String layOut = StringEscapeUtils.escapeHtml4(rs.getString("LAYOUT"));
	        String reportName = StringEscapeUtils.escapeHtml4(rs.getString("REPORT_NAME"));
	        String year = StringEscapeUtils.escapeHtml4(rs.getString("YEAR"));
	        tableList.add(reportId);
	        tableList.add(version);
	        tableList.add(system);
	        tableList.add(dbTable);
	        tableList.add(createDate);
	        tableList.add(updateDate);
	        tableList.add(amendentDate);
	        tableList.add(layOut);
	        tableList.add(reportName);
	        tableList.add(year);
	      }
	      conn.close();
	      ps.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return tableList;
	  }

	

}