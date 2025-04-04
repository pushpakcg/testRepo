package com.code2java.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.code2java.dao.DataTableDAO;
import com.code2java.model.UploadFile;
@Service("histUploadFileService")
public class HistFileUploadServiceImpl implements HistFileUploadService{
	
	@Autowired
	private DataTableDAO dataTableDao;

	@Override
	public List<String> getTableData(UploadFile file) {
		String year=file.getYear();
		String reportid=file.getReportName();
		//newly added starts
		String version1="";
		Connection conn1;
		PreparedStatement ps1;
		try {
		String query = "select * from REPORT_INDEX_TABLE where REPORT_ID LIKE ? and YEAR=?";
		conn1 = dataTableDao.getConnection();
		ps1 = conn1.prepareStatement(query);
		ps1.setString(1, "%" + reportid + "%");
		ps1.setString(2, year);
		ResultSet rs = ps1.executeQuery();
		while (rs.next()) {
		String[] arr = new String[1];
		arr[0] = rs.getString("VERSION");
		version1=arr[0];
		System.out.println("version==="+version1);

		}
		conn1.close();
		ps1.close();
		} catch (Exception e) {
		System.out.println(e);
		}
		//newly added ends
		List<String> tableList = new ArrayList<String>();
		PreparedStatement ps;
		Connection conn;
	    try {
	    	String query = "select * from REPORT_INDEX_TABLE where REPORT_ID LIKE ? and YEAR=? and VERSION LIKE ? order by DB_TABLE asc";
	    	conn = dataTableDao.getConnection();
	    	ps = conn.prepareStatement(query);
	    	ps.setString(1, "%" + reportid + "%");
	    	ps.setString(2, year);
	    	ps.setString(3, "%" + version1 + "%");
	    	//System.out.println(query);    	
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
	       // System.out.println(reportId + "," + version);
	        tableList.add(reportId);
	        tableList.add(version);
	        tableList.add(system);
	        tableList.add(dbTable);
	        tableList.add(createDate);
	        tableList.add(updateDate);
	        tableList.add(amendentDate);
	        tableList.add(layOut);
	      }
	      conn.close();
	      ps.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return tableList;
	  }

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
	public List<String[]> getUploadFileReportNames(String reportyear) {
		System.out.println("report upload====="+reportyear);
		List<String[]> resultList = new ArrayList<String[]>();
		Connection conn;
		PreparedStatement ps;
		try {
			
			String query = "select * from REPORT_INDEX_TABLE where YEAR=? order by REPORT_NAME asc";
			
		//	String query = "select * from REPORT_INDEX_TABLE where YEAR='2018' AND LAYOUT='ASTREG' order by REPORT_NAME asc";
			conn = dataTableDao.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, reportyear);
		//	ps.setString(2, reportList.get(1));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				
				String[] arr = new String[2];
				//arr[0] = StringEscapeUtils.escapeHtml4(rs.getString("REPORT_NAME"));
			//arr[0]=	rs.getString("REPORT_NAME");
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
	public String getSSOId(String loginSSO) {
		List<String[]> resultList = new ArrayList<String[]>();
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
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int saveBatchRecords(int totalrecords, String sql, List<Object[]> objectList) {
		int count = 0;
		for (int i = 0; i < totalrecords; i = i + 1000) {
			int lastindex = totalrecords - i > 1000 ? i + 1000 : totalrecords;
			System.out.println("update batch from : " + i + " - " + (lastindex));
			int[] updated =dataTableDao.getJdbcTemplate().batchUpdate(sql, objectList.subList(i, lastindex));
			count = count + updated.length;
		}
		return count;
	}

}
