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
import javax.servlet.http.HttpServletRequest;
import com.code2java.service.ADSTaxAssetRegisterService;

@Service("adstaxassetregisterservice")

public class ADSTaxAssetRegisterServiceImpl  implements ADSTaxAssetRegisterService {
	
	@Autowired
	private DataTableDAO dataTableDao;
	
	@Override
	public SXSSFWorkbook getADSTaxAssetRegisterdownloadExcelData(HttpServletRequest request){
		//excel starts
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
//		header.createCell(11).setCellValue("Tax Installation YR");
//		header.createCell(12).setCellValue(" Asset Life");
//		header.createCell(13).setCellValue("Remaining Life");
//		header.createCell(14).setCellValue("Tax APC");
//		header.createCell(15).setCellValue("Depreciable Basis");
//		header.createCell(16).setCellValue("Accumulated Reserve");
//		header.createCell(17).setCellValue("Year To Date Deprec");
//		header.createCell(18).setCellValue("Tax NBV");
//		header.createCell(19).setCellValue("First year Depreciated");
//		header.createCell(20).setCellValue("Last Year Depreciated");
//		header.createCell(21).setCellValue("last Month Depreciated ");
//		header.createCell(22).setCellValue("New or Used");
//		header.createCell(23).setCellValue("Tax Depr Method");
//		header.createCell(24).setCellValue("Tax Depr Convention");
//		header.createCell(25).setCellValue("Current Accounting Year");		
//		header.createCell(26).setCellValue("Corp Install Date ");
//		header.createCell(27).setCellValue("Corp Life");
//		header.createCell(28).setCellValue("Corp Book APC");
//		header.createCell(29).setCellValue("Corp Book Accum Reserve");
//		header.createCell(30).setCellValue("Corp NBV");
//		header.createCell(31).setCellValue("Record Creation Date");
//		header.createCell(32).setCellValue("Record Creation Time");
//		header.createCell(33).setCellValue("Record Creation User ");
//		header.createCell(34).setCellValue("Post Capitalization");
		
		String[] headers=null;
		List<String[]> headerList=getTableHeadings();
		int count=0;
		for (String[] strings : headerList) {
		    headers=strings;
		    header.createCell(count).setCellValue(headers[0]);
		   count++;
		}
		
		int rowNum = 1;
		
		//excel ends
		
		String adscompanygroup=request.getParameter("adscompanygroup");
		String adscompany=request.getParameter("adstaxassetcompany");
		String adstaxassetDateFrom =request.getParameter("adstaxassetDateFrom");
		String adstaxassetDateTo =request.getParameter("adstaxassetDateTo");
		String adstaxassetCreateDateFrom =request.getParameter("adstaxassetCreateDateFrom");		
		String adstaxassetCreateDateTo =request.getParameter("adstaxassetCreateDateTo");
		
		
		List<String> sArray = new ArrayList<String>();
		int mapi=0;
		Map<String,String> prepareParamMap=new LinkedHashMap<String, String>();
		
		if (null!=adscompanygroup && !adscompanygroup.equals("")) {
				//sArray.add(" ADS_COMPANYCODE in (select company from FACOMPANY where companygroup like '%" + adscompanygroup + "%')");
				sArray.add(" ADS_COMPANYCODE in (select company from FACOMPANY where companygroup like ?)");
				prepareParamMap.put(++mapi+"_"+"like",adscompanygroup);
			}
		
		
		if (null!=adscompany && !adscompany.equals("")) {
			//sArray.add(" ADS_COMPANYCODE like '%" + adscompany + "%'");
			sArray.add(" ADS_COMPANYCODE like ?");
			prepareParamMap.put(++mapi+"_"+"like",adscompany);
			
		}
		
		if (null!=adstaxassetDateFrom && !adstaxassetDateFrom.equals("")) {
			String proptaxInstallQuery=" to_date(case when ADS_INSTALLDATE= '00000000' then null else ADS_INSTALLDATE end,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
			
			
			//proptaxInstallQuery=proptaxInstallQuery.replace("adstaxassetDateFrom", adstaxassetDateFrom);
			prepareParamMap.put(++mapi+"_"+"eq",adstaxassetDateFrom);
			sArray.add(proptaxInstallQuery);
		}
		
		if (null!=adstaxassetDateTo && !adstaxassetDateTo.equals("")) {
			String proptaxInstallQuery=" to_date(case when ADS_INSTALLDATE= '00000000' then null else ADS_INSTALLDATE end,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			
			//proptaxInstallQuery=proptaxInstallQuery.replace("adstaxassetDateTo", adstaxassetDateTo);
			prepareParamMap.put(++mapi+"_"+"eq",adstaxassetDateTo);
			sArray.add(proptaxInstallQuery);
		}
		if (null!=adstaxassetCreateDateFrom && !adstaxassetCreateDateFrom.equals("")) {
				String proptaxInstallQuery=" to_date(ADS_ENTEREDDATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
				
				
				//proptaxInstallQuery=proptaxInstallQuery.replace("adstaxassetCreateDateFrom", adstaxassetCreateDateFrom);
				prepareParamMap.put(++mapi+"_"+"eq",adstaxassetCreateDateFrom);
				sArray.add(proptaxInstallQuery);
				
			}
			if (null!=adstaxassetCreateDateTo && !adstaxassetCreateDateTo.equals("")) {
				String proptaxInstallQuery=" to_date(ADS_ENTEREDDATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
				
				//proptaxInstallQuery=proptaxInstallQuery.replace("adstaxassetCreateDateTo", adstaxassetCreateDateTo);
				prepareParamMap.put(++mapi+"_"+"eq",adstaxassetCreateDateTo);
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
		
	//	String searchSQL="select * from ADS_TAX_ASSET_REGISTER_REPORT";
		
//		String searchSQL="select ADS_UNIQUEASSET,ADS_COMPANYCODE,ADS_ASSETNUMBER,ADS_SUBNUMBER,ADS_LEGUNIQUE,ADS_ASSETDESCRIPTION,ADS_ASSETCLASSS,ADS_ASSETCLASSDESCRIPTION,ADS_LEGACYCATEGORYCODE,\n" + 
//				"ADS_ENTEREDDATE,ADS_INSTALLDATE,(select CASE when ADS_INSTALLDATE <> '00000000' then EXTRACT(YEAR FROM TO_DATE(ADS_INSTALLDATE, 'MM-DD-YYYY')) else 0 end FROM dual) AS ADS_TAXINSTALLATIONYR,ADS_ASSETLIFE,ADS_REMAININGLIFE,\n" + 
//				"ADS_TAXAPC,(SELECT CASE WHEN to_char(ADS_ASSETCLASSS)='0000' THEN '0' ELSE to_char(ADS_TAXAPC) END from dual) AS ADS_DEPRECIABLEBASIS,ADS_ACCUMULATEDRESERVE,\n" + 
//				"ADS_YEARTODATEDEPREC,ADS_TAXNBV,ADS_FIRSTYEARDEPRECIATED,ADS_LASTYEARDEPRECIATED,ADS_LASTMONTHDEPRECIATED,ADS_NEWORUSED,ADS_TAXDEPRMETHOD,ADS_TAXDEPRCONVENTION,\n" + 
//				"ADS_CURRENTACCOUNTINGYEAR,ADS_CORPINSTALLDATE,ADS_CORPLIFE,ADS_CORPBOOKAPC,ADS_CORPBOOKACCUMRESERVE,ADS_CORPNBV,ADS_RECORDCREATIONDATE,\n" + 
//				"ADS_RECORDCREATIONTIME,ADS_RECORDCREATIONUSER,ADS_POSTCAPITALIZATION from ADS_TAX_ASSET_REGISTER_REPORT";
		String tName=getReportTables();
		tName=tName.replaceAll("[^a-zA-Z0-9_]", "");
		String searchSQL= callProcedure(tName.trim());
		
				
		if(individualSearch!=null && individualSearch!=""){
			searchSQL = searchSQL+" where " + individualSearch;
			//searchSQL = searchSQL+" and REGEXP_LIKE (ADS_INSTALLDATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
		}
		
		searchSQL += " order by ADS_UNIQUEASSET asc";

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
			//excel starts
			int counter=0;
			//excel ends
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
					//System.out.println("cleared"+counter);
				//	System.out.println("size before"+resultDataList.size());
					
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
					//if (cellNum == 14) {
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
	public String getADSTaxAssetRegisterDataTableResponse(HttpServletRequest request) {
		
		
//		String[] cols = {"ADS_UNIQUEASSET","ADS_COMPANYCODE","ADS_ASSETNUMBER","ADS_SUBNUMBER","ADS_ASSETDESCRIPTION","ADS_ASSETCLASSS","ADS_ASSETCLASSDESCRIPTION","ADS_LEGACYCATEGORYCODE","ADS_ENTEREDDATE", "ADS_INSTALLDATE","ADS_TAXINSTALLATIONYR","ADS_ASSETLIFE","ADS_REMAININGLIFE","ADS_TAXAPC","ADS_DEPRECIABLEBASIS","ADS_ACCUMULATEDRESERVE","ADS_YEARTODATEDEPREC",
//		"ADS_TAXNBV","ADS_BONUSDEPRECIATION","ADS_TAXYTDBONUSDEPR","ADS_ELECTOUTBONUS","ADS_TAXBONUSPERCENTAGE","ADS_FIRSTYEARDEPRECIATED","ADS_LASTYEARDEPRECIATED",
//		"ADS_LASTMONTHDEPRECIATED","ADS_NEWORUSED","ADS_TAXDEPRMETHOD","ADS_TAXDEPRCONVENTION","ADS_CURRENTACCOUNTINGYEAR","ADS_CONSOLIDATEDBU","ADS_CORPINSTALLDATE",
//		"ADS_CORPLIFE","ADS_CORPBOOKAPC","ADS_CORPBOOKACCUMRESERVE","ADS_CORPNBV","ADS_RECORDCREATIONDATE","ADS_RECORDCREATIONTIME","ADS_RECORDCREATIONUSER","ADS_POSTCAPITALIZATION"};
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
		
		String adscompanygroup=request.getParameter("adscompanygroup");
		String adscompany=request.getParameter("adstaxassetcompany");
		String adsDateFrom =request.getParameter("adstaxassetDateFrom");		
		String adsDateTo =request.getParameter("adstaxassetDateTo");		
		String adstaxassetCreateDateFrom =request.getParameter("adstaxassetCreateDateFrom");		
		String adstaxassetCreateDateTo =request.getParameter("adstaxassetCreateDateTo");
		
		
		
		
		List<String> sArray = new ArrayList<String>();
		int mapi=0;
		Map<String,String> prepareParamMap=new LinkedHashMap<String, String>();
		
		
		if (null!=adscompanygroup && !adscompanygroup.equals("")) {
			//sArray.add(" ADS_COMPANYCODE in (select company from FACOMPANY where companygroup like '%" + adscompanygroup + "%')");
			sArray.add(" ADS_COMPANYCODE in (select company from FACOMPANY where companygroup like ?)");
			prepareParamMap.put(++mapi+"_"+"like",adscompanygroup);
		}
	
		if (null!=adscompany && !adscompany.equals("")) {
			//sArray.add(" ADS_COMPANYCODE like '%" + adscompany + "%'");
			sArray.add(" ADS_COMPANYCODE like ?");
			prepareParamMap.put(++mapi+"_"+"like",adscompany);

			
		}
		
		if (null!=adsDateFrom && !adsDateFrom.equals("")) {
			//String proptaxInstallQuery=" to_date(ADS_INSTALLDATE,'MM/DD/YYYY')>=to_date('adstaxassetDateFrom','MM/DD/YYYY') ";
			String proptaxInstallQuery=" to_date(ADS_INSTALLDATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
			
			
			//proptaxInstallQuery=proptaxInstallQuery.replace("adstaxassetDateFrom", adsDateFrom);
			prepareParamMap.put(++mapi+"_"+"eq",adsDateFrom);
			sArray.add(proptaxInstallQuery);
			
		}
		
		if (null!=adsDateTo && !adsDateTo.equals("")) {
			//String proptaxInstallQuery=" to_date(ADS_INSTALLDATE,'MM/DD/YYYY')<=to_date('adstaxassetDateTo','MM/DD/YYYY') ";
			String proptaxInstallQuery=" to_date(ADS_INSTALLDATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
			
			//proptaxInstallQuery=proptaxInstallQuery.replace("adstaxassetDateTo", adsDateTo);
			prepareParamMap.put(++mapi+"_"+"eq",adsDateTo);
			sArray.add(proptaxInstallQuery);
			
		}
		if (null!=adstaxassetCreateDateFrom && !adstaxassetCreateDateFrom.equals("")) {
				String proptaxInstallQuery=" to_date(ADS_ENTEREDDATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
				
				
				//proptaxInstallQuery=proptaxInstallQuery.replace("adstaxassetCreateDateFrom", adstaxassetCreateDateFrom);
				prepareParamMap.put(++mapi+"_"+"eq",adstaxassetCreateDateFrom);
				sArray.add(proptaxInstallQuery);
				
			}
			if (null!=adstaxassetCreateDateTo && !adstaxassetCreateDateTo.equals("")) {
				String proptaxInstallQuery=" to_date(ADS_ENTEREDDATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
				
				//proptaxInstallQuery=proptaxInstallQuery.replace("adstaxassetCreateDateTo", adstaxassetCreateDateTo);
				prepareParamMap.put(++mapi+"_"+"eq",adstaxassetCreateDateTo);
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
//			//String sql = "SELECT count(*) FROM "+ table +  " WHERE ROWNUM <= 200";
//			String sql = "SELECT count(*) FROM "+ table +  " WHERE REGEXP_LIKE (ADS_INSTALLDATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$') and ROWNUM <= 200";
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
			String globeSearch = "";
			
			
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
//						+ "and REGEXP_LIKE (ADS_INSTALLDATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
			}else {
				sql=sql+" and ROWNUM <= 200 ";
//						+ "and REGEXP_LIKE (ADS_INSTALLDATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
			}
			
			//sql += " order by " + colName + " " + dir;
			sql += " order by ADS_UNIQUEASSET asc";
			PreparedStatement ps = conn.prepareStatement(sql);
			System.out.println("test "+sql);
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

	
	
	
	public List<String[]> getADSAssetCompanyGroups() {
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
	
	public List<String[]> getADSAssetInstances() {
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
	
		public List<String[]> getADSAssetYears() {
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
		public List<String[]> getADSTaxAssetRegistercompanyNames(String COMPANYGROUP) {
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
		
		@Override
		public List<String[]> getTableHeadings() {
		
			String tableName="";
			
		  String tName=this.getReportTables();
			System.out.println("tb= "+tName.trim());
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
			//	System.out.println("testing=== "+rs.getFetchSize());
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
			String query = "select * from report_index_table where layout='SHASTADS' and report_id='PRD-ADSFAR'";
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
