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

@Service("assetretirementservice")


public class AssetRetirementServiceImpl implements AssetRetirementService{

	@Autowired
	private DataTableDAO dataTableDao;
	
	@Override
	public SXSSFWorkbook getAssetRetirementExcelData(HttpServletRequest request){
	
		//Report handler starts//
		
		SXSSFWorkbook workbook = null;
		Sheet reportSheet = null;
		workbook = new SXSSFWorkbook(100);
		reportSheet = workbook.createSheet("Reports");
		Row header = reportSheet.createRow(0);
		header.setHeight((short) 500);
		Row dataRow = null;
		
//		header.createCell(0).setCellValue("Unique Asset Number");
//		header.createCell(1).setCellValue("Company Code");
//		header.createCell(2).setCellValue("Asset Number");
//		header.createCell(3).setCellValue("Sub Number");
//		header.createCell(4).setCellValue("Legacy Unique Asset Number");
//		header.createCell(5).setCellValue("Asset Description");
//		header.createCell(6).setCellValue("Asset Class");
//		header.createCell(7).setCellValue("Asset Class Description");
//		header.createCell(8).setCellValue("Legacy Category Code");
//		header.createCell(9).setCellValue("Entered Date");
//		header.createCell(10).setCellValue("Install Date");
//		header.createCell(11).setCellValue("Tax Installation Yr");
//		header.createCell(12).setCellValue("Asset Life");
//		header.createCell(13).setCellValue("Remaining Life");
//		header.createCell(14).setCellValue("Tax APC");
//		header.createCell(15).setCellValue("Depreciable Basis");		
//		header.createCell(16).setCellValue("Bonus Depreciation");
//		header.createCell(17).setCellValue("Tax Ytd Bonus Depreciation");
//		header.createCell(18).setCellValue("Elect out Bonus");
//		header.createCell(19).setCellValue("Tax Bonus Percentage");
//		header.createCell(20).setCellValue("First Year Depreciated");
//		header.createCell(21).setCellValue("Last Year Depreciated");
//		header.createCell(22).setCellValue("Last Month Depreciated");
//		header.createCell(23).setCellValue("New or Used");
//		header.createCell(24).setCellValue("Tax Depr Method");
//		header.createCell(25).setCellValue("Tax Depr Convention");
//		header.createCell(26).setCellValue("Park Asset Property Tax");
//		header.createCell(27).setCellValue("Current Accounting Year");		
//		header.createCell(28).setCellValue("Corp InstallDate");
//		header.createCell(29).setCellValue("Corp Life");
//		header.createCell(30).setCellValue("Corp Book APC");
//		header.createCell(31).setCellValue("Corp Book Accum Reserve");
//		header.createCell(32).setCellValue("Corp NBV");		
//		header.createCell(33).setCellValue("Retirement Date");
//		header.createCell(34).setCellValue("Assignment");
//		header.createCell(35).setCellValue("Retirement Description");
//		header.createCell(36).setCellValue("Retirement Cost");
//		header.createCell(37).setCellValue("Retirement AD");
//		header.createCell(38).setCellValue("Retirement YTD");
//		header.createCell(39).setCellValue("RETIREMENT NBV");
//		header.createCell(40).setCellValue("Retirement Proceeds");
//		header.createCell(41).setCellValue("Gain");
//		header.createCell(42).setCellValue("Loss");
//		header.createCell(43).setCellValue("Bonus");
//		header.createCell(44).setCellValue("Evalution");
	
		String[] headers=null;
		List<String[]> headerList=getTableHeadings();
		int count=0;
		for (String[] strings : headerList) {
		    headers=strings;
		    header.createCell(count).setCellValue(headers[0]);
		   count++;
		}
		
		int rowNum = 1;
		
		String retiredcompanygroup=request.getParameter("retiredcompanygroup");
		String retiredcompany=request.getParameter("retiredcompany");
		String retdasstDateFrom =request.getParameter("retiredAssetDateFrom");
		String retdasstDateTo =request.getParameter("retiredAssetDateTo");
		
		List<String> sArray = new ArrayList<String>();
		int mapi=0;
		Map<String,String> prepareParamMap=new LinkedHashMap<String, String>();
		
			if (null!=retiredcompanygroup && !retiredcompanygroup.equals("")) {
				//sArray.add(" COMPANYCODE in (select company from FACOMPANY where companygroup like '%" + retiredcompanygroup + "%')");
				sArray.add(" COMPANYCODE in (select company from FACOMPANY where companygroup like ?)");
				prepareParamMap.put(++mapi+"_"+"like",retiredcompanygroup);
			}
		
		if (null!=retiredcompany && !retiredcompany.equals("")) {
			//sArray.add(" COMPANYCODE like '%" + retiredcompany + "%'");
			sArray.add(" COMPANYCODE like ?");
			prepareParamMap.put(++mapi+"_"+"like",retiredcompany);
			
		}
		
		if (null!=retdasstDateFrom && !retdasstDateFrom.equals("")) {
			String retdasstInstallQuery=" to_date(RETIREMENTDATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
			
			
			//retdasstInstallQuery=retdasstInstallQuery.replace("retdAsstFromParam", retdasstDateFrom);
			prepareParamMap.put(++mapi+"_"+"eq",retdasstDateFrom);
			sArray.add(retdasstInstallQuery);
			
		}
		
		if (null!=retdasstDateTo && !retdasstDateTo.equals("")) {
			String retdasstInstallQuery=" to_date(RETIREMENTDATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			
			//retdasstInstallQuery=retdasstInstallQuery.replace("retdAsstToParam", retdasstDateTo);
			prepareParamMap.put(++mapi+"_"+"eq",retdasstDateTo);
			sArray.add(retdasstInstallQuery);
			
			
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
		
		//String searchSQL="select * from INC_TAX_ASSET_RETIRE_REPORT";
		
		
//		String searchSQL="select UNIQUEASSET,COMPANYCODE,ASSETNUMBER,SUBNUMBER,LEGUNIQUE,ASSETDESCRIPTION,ASSETCLASSS,ASSETCLASSDESCRIPTION,LEGACYCATEGORYCODE,ENTEREDDATE,INSTALLDATE,(SELECT  EXTRACT(YEAR FROM TO_DATE(INSTALLDATE, 'MM-DD-YYYY')) FROM dual) AS TAXINSTALLATIONYR,ASSETLIFE,REMAININGLIFE,TAXAPC,(SELECT CASE WHEN to_char(ASSETCLASSS)='0000' THEN '0' ELSE to_char(TAXAPC) END from dual) AS DEPRECIABLEBASIS,BONUSDEPRECIATION,\r\n"+
//		"TAXYTDBONUSDEPR,ELECTOUTBONUS,(select case when (bonusdepreciation) <> 0  AND (taxapc) <> 0  then TRUNC(abs(bonusdepreciation)/abs(taxapc)*100,0) else 0  end from dual)AS TAXBONUSPERCENTAGE,FIRSTYEARDEPRECIATED,LASTYEARDEPRECIATED,LASTMONTHDEPRECIATED,NEWORUSED,TAXDEPRMETHOD,TAXDEPRCONVENTION,PPTAX,CURRENTACCOUNTINGYEAR,CORPINSTALLDATE,CORPLIFE,CORPBOOKAPC,CORPBOOKACCUMRESERVE,CORPNBV,RETIREMENTDATE,ASSIGNMENT,RETIREMENTDESCRIPTION,\r\n"+
//		 "RETIREMENTCOST,RETIREMENTAD,RETIREMENTYTD,RETIREMENTNBV,RETIREMENTPROCEEDS,GAIN,LOSS,BONUS,EVALUATION from INC_TAX_ASSET_RETIRE_REPORT";

		String tName=getReportTables();
		tName=tName.replaceAll("[^a-zA-Z0-9_]", "");
		String searchSQL= callProcedure(tName.trim());
		
				
		if(individualSearch!=null && individualSearch!=""){
			searchSQL = searchSQL+" where " + individualSearch;
		}
		
		searchSQL += " order by UNIQUEASSET asc";

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
		//	System.out.println(colId);
			String searchSQL1 = searchSQL.replace("$tablename", tName.trim());
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
							//if (cellNum == 14) {
							if (StringUtils.isNotBlank(entry) && colId.contains(cellNum)) {
								
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
					if (StringUtils.isNotBlank(entry) && colId.contains(cellNum)) {
						
						row.createCell(cellNum++).setCellValue(Double.parseDouble(entry));
					} else {
						row.createCell(cellNum++).setCellValue(entry);
					}
				}
			}
			
			
			resultDataList=null;
		 resultDataList = new ArrayList<List<String>>(1);
		 
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
	public String getAssetRetirementDataTableResponse(HttpServletRequest request) {
		
//		
//		String[] cols = {"UNIQUEASSET","COMPANYCODE","ASSETNUMBER","SUBNUMBER","ASSETDESCRIPTION","ASSETCLASSS","ASSETCLASSDESCRIPTION","LEGACYCATEGORYCODE","ENTEREDDATE","INSTALLDATE","TAXINSTALLATIONYR","ASSETLIFE","REMAININGLIFE","TAXAPC","DEPRECIABLEBASIS",
//		"BONUSDEPRECIATION","TAXYTDBONUSDEPR","ELECTOUTBONUS","TAXBONUSPERCENTAGE","FIRSTYEARDEPRECIATED","LASTYEARDEPRECIATED","LASTMONTHDEPRECIATED","NEWORUSED","TAXDEPRMETHOD",
//		"TAXDEPRCONVENTION","PPTAX","CURRENTACCOUNTINGYEAR","CORPINSTALLDATE","CORPLIFE","CORPBOOKAPC","CORPBOOKACCUMRESERVE","CORPNBV","RETIREMENTDATE","ASSIGNMENT","RETIREMENTDESCRIPTION","RETIREMENTCOST",
//		"RETIREMENTAD","RETIREMENTYTD","RETIREMENTNBV","RETIREMENTPROCEEDS","GAIN","LOSS","BONUS","EVALUATION"};
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
		
		
		String retiredcompany=request.getParameter("retiredcompany");
		String retiredcompanygroup=request.getParameter("retiredcompanygroup");			
		String retdasstDateFrom =request.getParameter("retiredAssetDateFrom");
		String retdasstDateTo =request.getParameter("retiredAssetDateTo");    
		
				
		List<String> sArray = new ArrayList<String>();
		int mapi=0;
		Map<String,String> prepareParamMap=new LinkedHashMap<String, String>();
		
		if (null!=retiredcompany && !retiredcompany.equals("")) {
			//sArray.add(" COMPANYCODE like '%" + retiredcompany + "%'");
			sArray.add(" COMPANYCODE like ?");
			prepareParamMap.put(++mapi+"_"+"like",retiredcompany);
		}
				
		if (null!=retiredcompanygroup && !retiredcompanygroup.equals("")) {
			//sArray.add(" COMPANYCODE in (select company from FACOMPANY where companygroup like '%" + retiredcompanygroup + "%')");
			sArray.add(" COMPANYCODE in (select company from FACOMPANY where companygroup like ?)");
			prepareParamMap.put(++mapi+"_"+"like",retiredcompanygroup);
		}
		
		if (null!=retdasstDateFrom && !retdasstDateFrom.equals("")) {
			String retdasstInstallQuery=" to_date(RETIREMENTDATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
			
			//retdasstInstallQuery=retdasstInstallQuery.replace("retdAsstFromParam", retdasstDateFrom);
			prepareParamMap.put(++mapi+"_"+"eq",retdasstDateFrom);
			sArray.add(retdasstInstallQuery);
		}
		
		if (null!=retdasstDateTo && !retdasstDateTo.equals("")) {
			String retdasstInstallQuery=" to_date(RETIREMENTDATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			
			//retdasstInstallQuery=retdasstInstallQuery.replace("retdAsstToParam", retdasstDateTo);
			prepareParamMap.put(++mapi+"_"+"eq",retdasstDateTo);
			sArray.add(retdasstInstallQuery);
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
			
			//sql += " order by " + colName + " " + dir;
			sql += " order by UNIQUEASSET asc";
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
			Date date1;
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
	
	public List<String[]> getAssetRetirementCompanyGroups() {
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
	public List<String[]> getAssetRetirementInstances() {
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
	public List<String[]> getAssetRetirementYears() {
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
	
	public List<String[]> getAssetRetirementCompanyNames(String COMPANYGROUP) {
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
		//System.out.println("tb= "+tName.trim());
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
		//	System.out.print(arr[0]);
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
		String query = "select * from report_index_table where layout='SHASTRET' and report_id='PRD-TAXRET'";
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
		   // System.out.println("rrto"+m_count.toString());
		    stm.close();
			conn1.close();
			
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		return m_count;
		
	}
}