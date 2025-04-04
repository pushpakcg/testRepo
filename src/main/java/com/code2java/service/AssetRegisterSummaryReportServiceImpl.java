package com.code2java.service;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.code2java.dao.DataTableDAO;

@Service("assetRegisterSummaryReportService")

public class AssetRegisterSummaryReportServiceImpl  implements AssetRegisterSummaryReportService {
	
	@Autowired
	private DataTableDAO dataTableDao;
	
	@Override
	public SXSSFWorkbook getAssetRegisterSummarydownloadExcelData(HttpServletRequest request){
		
		SXSSFWorkbook workbook = null;
		Sheet reportSheet = null;
		workbook = new SXSSFWorkbook(100);
		reportSheet = workbook.createSheet("Summary by Company Code");
		Row header = reportSheet.createRow(0);
		header.setHeight((short) 500);
		Row dataRow = null;
		
		Sheet reportSheet2 = null;
		//workbook = new SXSSFWorkbook(100);
		reportSheet2 = workbook.createSheet("Summary by Asset Class");
		Row header2 = reportSheet2.createRow(0);
		header2.setHeight((short) 500);
		Row dataRow2 = null;
		
        header.createCell(0).setCellValue("Company Code");
        header.createCell(1).setCellValue("Company Name");
		header.createCell(2).setCellValue("Number of Assets");
		header.createCell(3).setCellValue("Total Tax Apc");
		header.createCell(4).setCellValue("Total Depreciable Basis");		
		header.createCell(5).setCellValue("Total Year to Date Depreciation");
		header.createCell(6).setCellValue("Total Tax NBV");
		header.createCell(7).setCellValue("Total Bonus Depreciation");
		header.createCell(8).setCellValue("Total Corp Book APC");
		header.createCell(9).setCellValue("Total Corp Book Accumulated Depreciation");
		header.createCell(10).setCellValue("Total Corp NBV");
		int rowNum = 1;
		
		header2.createCell(0).setCellValue("Company Code");
		header2.createCell(1).setCellValue("Asset Class");
		header2.createCell(2).setCellValue("Asset Class Description");
		header2.createCell(3).setCellValue("Number of Assets");
		header2.createCell(4).setCellValue("Total Tax Apc");
		header2.createCell(5).setCellValue("Total Depreciable Basis");		
		header2.createCell(6).setCellValue("Total Year to Date Depreciation");
		header2.createCell(7).setCellValue("Total Tax NBV");
		header2.createCell(8).setCellValue("Total Bonus Depreciation");
		header2.createCell(9).setCellValue("Total Corp Book APC");
		header2.createCell(10).setCellValue("Total Corp Book Accumulated Depreciation");
		header2.createCell(11).setCellValue("Total Corp NBV");
		int rowNum2 = 1;
		
		//excel ends
		String sumcompanygroup=request.getParameter("sumgroup");
		String sumcompany=request.getParameter("sumcompany");
		
		String searchSQL="SELECT INC_COMPANYCODE,COMPANYNAME,COUNT(INC_UNIQUEASSET)AS INC_UNIQUEASSET,SUM(INC_TAXAPC)AS INC_TAXAPC,SUM((CASE WHEN to_char(INC_ASSETCLASSS)='0000' THEN '0' ELSE to_char(INC_TAXAPC) END)) AS INC_DEPRECIABLEBASIS,SUM(INC_YEARTODATEDEPREC)AS INC_YEARTODATEDEPREC,SUM(INC_TAXNBV)AS INC_TAXNBV,SUM(INC_BONUSDEPRECIATION)AS INC_BONUSDEPRECIATION,SUM(INC_CORPBOOKAPC)AS INC_CORPBOOKAPC,SUM(INC_CORPBOOKACCUMRESERVE)AS INC_CORPBOOKACCUMRESERVE,SUM(INC_CORPNBV)AS INC_CORPNBV FROM INC_TAX_ASSET_REG_REPORT,FACOMPANY";
		String searchSQL2="SELECT INC_COMPANYCODE,INC_ASSETCLASSS,INC_ASSETCLASSDESCRIPTION,COUNT(INC_UNIQUEASSET)AS INC_UNIQUEASSET,SUM(INC_TAXAPC)AS INC_TAXAPC,SUM((CASE WHEN to_char(INC_ASSETCLASSS)='0000' THEN '0' ELSE to_char(INC_TAXAPC) END)) AS INC_DEPRECIABLEBASIS,SUM(INC_YEARTODATEDEPREC)AS INC_YEARTODATEDEPREC,SUM(INC_TAXNBV)AS INC_TAXNBV,SUM(INC_BONUSDEPRECIATION)AS INC_BONUSDEPRECIATION,SUM(INC_CORPBOOKAPC)AS INC_CORPBOOKAPC,SUM(INC_CORPBOOKACCUMRESERVE)AS INC_CORPBOOKACCUMRESERVE,SUM(INC_CORPNBV)AS INC_CORPNBV FROM INC_TAX_ASSET_REG_REPORT";
	
		List<List<String>> resultDataList = new ArrayList<List<String>>();
		List<List<String>> resultDataList2 = new ArrayList<List<String>>();
		try {
			Connection conn = dataTableDao.getConnection();
			
			if (null!=sumcompanygroup && !sumcompanygroup.equals("")) {
				searchSQL=searchSQL+" WHERE INC_COMPANYCODE=COMPANY AND COMPANYGROUP=? AND INC_COMPANYCODE in (select company from FACOMPANY where companygroup=?)";
				searchSQL2=searchSQL2+" WHERE INC_COMPANYCODE in (select company from FACOMPANY where companygroup=?)";
				
			}
			if (null!=sumcompany && !sumcompany.equals("")) {
				searchSQL=searchSQL+" AND INC_COMPANYCODE=?";
				searchSQL2=searchSQL2+" AND INC_COMPANYCODE=?";
			}
			
			searchSQL=searchSQL+" group by INC_COMPANYCODE,COMPANYNAME order by INC_COMPANYCODE asc";
			searchSQL2=searchSQL2+" group by INC_COMPANYCODE,INC_ASSETCLASSS,INC_ASSETCLASSDESCRIPTION order by INC_COMPANYCODE asc";
			System.out.println("SQL=="+searchSQL);
			System.out.println("SQL=="+searchSQL2);
			PreparedStatement ps = conn.prepareStatement(searchSQL);
			PreparedStatement ps2 = conn.prepareStatement(searchSQL2);
			if (null!=sumcompanygroup && !sumcompanygroup.equals("")) {
				ps.setString(1, sumcompanygroup);
				ps.setString(2, sumcompanygroup);
				ps2.setString(1, sumcompanygroup);
				}
				if (null!=sumcompany && !sumcompany.equals("")) {
				ps.setString(3, sumcompany);
				ps2.setString(2, sumcompany);
				}
			ps.setFetchSize(2000);
			ps2.setFetchSize(2000);
			ResultSet rs = ps.executeQuery();
			ResultSet rs2 = ps2.executeQuery();
			System.out.println("resultset Start Time: "+new Date());
			//excel starts
			int counter=0;
			//excel ends
			ResultSetMetaData rsMeta=rs.getMetaData();
			ResultSetMetaData rsMeta2=rs2.getMetaData();
			int ct=rsMeta.getColumnCount();
			int ct2=rsMeta2.getColumnCount();
			CellStyle numberStyle=workbook.createCellStyle();
			DataFormat format=workbook.createDataFormat();
			numberStyle.setDataFormat(format.getFormat("0.00"));
			while (rs.next()) {
				counter++;
				List<String> resultRowData = new ArrayList<String>();
				for (int i = 1; i <= ct; i++) {					
					resultRowData.add(rs.getString(i));
					
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
							if(StringUtils.isNotBlank(entry) && (cellNum!=0 && cellNum!=1)) {
								row.createCell(cellNum).setCellValue(Double.parseDouble(entry));
								row.getCell(cellNum++).setCellStyle(numberStyle);
							}
			               else {
								row.createCell(cellNum++).setCellValue(entry);
							}
						}
					}
					//resultDataList.clear();//size 3000
					resultDataList=null;
					 resultDataList = new ArrayList<List<String>>(1);//10
					// System.out.println("size after"+resultDataList.size());
				}
				
			}
			while (rs2.next()) {
				counter++;
				List<String> resultRowData2 = new ArrayList<String>();
				for (int i = 1; i <= ct2; i++) {					
					resultRowData2.add(rs2.getString(i));
					
				}
				
				resultDataList2.add(resultRowData2);
				resultRowData2=new ArrayList<String>(1);
				if(counter % 30000==0)
				{
					for (List<String> list : resultDataList2) {
						
						int cellNum2 = 0;
						// create the row data
						Row row2 = reportSheet2.createRow(rowNum2++);
						for (String entry : list) {
							if(StringUtils.isNotBlank(entry) && (cellNum2!=0  && cellNum2!=1 && cellNum2!=2)) { 
								row2.createCell(cellNum2).setCellValue(Double.parseDouble(entry));
								row2.getCell(cellNum2++).setCellStyle(numberStyle);
							} else {
								row2.createCell(cellNum2++).setCellValue(entry);
							}
						}
					}
					//resultDataList.clear();//size 3000
					resultDataList2=null;
					 resultDataList2 = new ArrayList<List<String>>(1);//10
				}
				
			}
			
			for (List<String> list : resultDataList) {
				
				int cellNum = 0;
				// create the row data
				Row row = reportSheet.createRow(rowNum++);
				for (String entry : list) {
					//if (cellNum == 14) {
					if(StringUtils.isNotBlank(entry) && (cellNum!=0 && cellNum!=1)) {
						row.createCell(cellNum).setCellValue(Double.parseDouble(entry));
						row.getCell(cellNum++).setCellStyle(numberStyle);
				}else {
						row.createCell(cellNum++).setCellValue(entry);
					}
				}
			}
			//New Code Arjun Added
               for (List<String> list : resultDataList2) {
				
				int cellNum2 = 0;
				// create the row data
				Row row2 = reportSheet2.createRow(rowNum2++);
				for (String entry : list) {
					//if (cellNum == 14) {
					if(StringUtils.isNotBlank(entry) && (cellNum2!=0  && cellNum2!=1 && cellNum2!=2)) { 
						row2.createCell(cellNum2).setCellValue(Double.parseDouble(entry));
						row2.getCell(cellNum2++).setCellStyle(numberStyle);
				}
				else {
						row2.createCell(cellNum2++).setCellValue(entry);
					}
				}
			}
			
			resultDataList=null;
			resultDataList2=null;
		 resultDataList = new ArrayList<List<String>>(1);//10
		 resultDataList2 = new ArrayList<List<String>>(1);//10
		 System.out.println("resultset End Time: "+new Date());
			ps.close();
			ps2.close();
			conn.close();
			//conn2.close();
			rs.close();
			rs2.close();
			//return resultDataList;
		}catch(Exception e) {
			System.out.println("Exception"+e);
		}
		return workbook;
	}

	@Override
	public String getAssetRegisterSummaryDataTableResponse(HttpServletRequest request) {
		String sumcompanygroup=request.getParameter("sumgroup");
		String sumcompany=request.getParameter("sumcompany");
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			//String searchSQL = "";
			String sql = "SELECT INC_COMPANYCODE,COMPANYNAME,COUNT(INC_UNIQUEASSET)AS INC_UNIQUEASSET,SUM(INC_TAXAPC)AS INC_TAXAPC,SUM((CASE WHEN to_char(INC_ASSETCLASSS)='0000' THEN '0' ELSE to_char(INC_TAXAPC) END)) AS INC_DEPRECIABLEBASIS,SUM(INC_YEARTODATEDEPREC)AS INC_YEARTODATEDEPREC,SUM(INC_TAXNBV)AS INC_TAXNBV,SUM(INC_BONUSDEPRECIATION)AS INC_BONUSDEPRECIATION,SUM(INC_CORPBOOKAPC)AS INC_CORPBOOKAPC,SUM(INC_CORPBOOKACCUMRESERVE)AS INC_CORPBOOKACCUMRESERVE,SUM(INC_CORPNBV)AS INC_CORPNBV FROM INC_TAX_ASSET_REG_REPORT,FACOMPANY";
			if (null!=sumcompanygroup && !sumcompanygroup.equals("")) {
				sql=sql+" WHERE INC_COMPANYCODE=COMPANY AND COMPANYGROUP=? AND INC_COMPANYCODE in (select company from FACOMPANY where companygroup=?)";
				
			}
			if (null!=sumcompany && !sumcompany.equals("")) {
				sql=sql+" AND INC_COMPANYCODE=?";
			}
			
			sql=sql+" group by INC_COMPANYCODE,COMPANYNAME order by INC_COMPANYCODE asc";
			
			System.out.println("sql2=="+sql);
			Connection conn = dataTableDao.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			if (null!=sumcompanygroup && !sumcompanygroup.equals("")) {
			ps.setString(1, sumcompanygroup);
			ps.setString(2, sumcompanygroup);
			}
			if (null!=sumcompany && !sumcompany.equals("")) {
			ps.setString(3, sumcompany);
			}
			ps.setFetchSize(200);
			ResultSet rs = ps.executeQuery();
			int i=0;
			ResultSetMetaData rsmd = rs.getMetaData(); 
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				JSONArray ja = new JSONArray();
				 int i1 = 1;
				 DecimalFormat df=new DecimalFormat("0.00");
				   while(i1 <= columnCount) {
				       if(i1!=1 && i1!=2 && rs.getString(i1)!=null)
				       {
				        ja.put(df.format(Double.parseDouble(rs.getString(i1++))));
				       }
				       else {
				    	   ja.put(rs.getString(i1++));
				       }
				        
				   }
				array.put(ja);
			}
			//result.put("iTotalRecords", total);
			//result.put("iTotalDisplayRecords", totalAfterFilter);
			result.put("aaData", array);
			//result.put("sEcho", echo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	
	public List<String[]> getAssetRegisterSummaryCompanyGroups() {
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
	
	public List<String[]> getAssetRegisterSummaryInstances() {
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
	
		public List<String[]> getAssetRegisterSummaryYears() {
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
		public List<String[]> getAssetRegisterSummarycompanyNames(String COMPANYGROUP) {
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
					arr[0] =StringEscapeUtils.escapeHtml4( rs.getString("COMPANY"));
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
		
//		@Override
//		public List<String[]> getTableHeadings() {
//		
//			String tableName="";
//			
//		  String tName=this.getReportTables();
//			System.out.println("tb Updated= "+tName.trim());
//			 List<String[]> headingList = new ArrayList<String[]>();
//				Connection conn;
//				PreparedStatement ps;
//				try {
//				//String query = "select * from REPORT_INDEX_TABLE where REPORT_ID=? and YEAR=? order by DB_TABLE asc";
//				//String query = "select COLUMN_NAME from SYS.all_tab_columns where table_name = ?";
////				String query="select COLUMN_NAME,COLUMN_ID,HIST_INC_TAXREG_UI from SYS.all_tab_columns,all_tables_column_names where table_name = ? and COLUMN_NAME=HIST_INC_TAXREG_DB ORDER BY COLUMN_ID";
//				String query="select COLUMN_NAME,(case when COLUMN_ID=2 then 1 when COLUMN_ID=1 then 2 else COLUMN_ID end)as COLUMN_ID,HIST_INC_TAXREG_UI from SYS.all_tab_columns,all_tables_column_names where table_name = ? and COLUMN_NAME=HIST_INC_TAXREG_DB and COLUMN_ID in(35,36,15,16,19,20,37,18,1,2) order by COLUMN_ID";
//				conn = dataTableDao.getConnection();
//				ps = conn.prepareStatement(query);
//				ps.setString(1, tName.trim());
//				ResultSet rs = ps.executeQuery();
//				System.out.println("testingUpdate=== "+rs.getFetchSize());
//				while (rs.next()) {
//				String[] arr = new String[1];
//				arr[0] = StringEscapeUtils.escapeHtml4(rs.getString("HIST_INC_TAXREG_UI"));
//			//	System.out.print(arr[0]);
//				headingList.add(arr);
//				}
//				conn.close();
//				ps.close();
//				} catch (Exception e) {
//				System.out.println(e);
//				}
//				return headingList;
//		}
		
		public String getReportTables() {
			String resultList = "";
			Connection conn;
			PreparedStatement ps;
			try {
			//String query = "select * from REPORT_INDEX_TABLE where REPORT_ID=? and YEAR=? order by DB_TABLE asc";
			String query = "select * from report_index_table where layout='SHASTREG' and report_id='PRD-TAXFAR'";
			conn = dataTableDao.getConnection();
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
			String[] arr = new String[1];
			arr[0] = StringEscapeUtils.escapeHtml4(rs.getString("DB_TABLE"));
		//	System.out.println("getReportTables== "+arr[0]);
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
			 //   System.out.println("rrto"+m_count.toString());
			    stm.close();
				conn1.close();
				
				
			}
			catch(Exception e){
				System.out.println(e);
			}
			
			return m_count;
			
		}
}
