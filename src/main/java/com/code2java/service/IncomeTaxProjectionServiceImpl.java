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

@Service("incometaxprojservice")

public class IncomeTaxProjectionServiceImpl implements IncomeTaxProjectionService{
	
	
		@Autowired
		private DataTableDAO dataTableDao;
		
		@Override
		public SXSSFWorkbook getIncomeTaxProjectionExcelData(HttpServletRequest request){
			SXSSFWorkbook workbook = null;
			Sheet reportSheet = null;
			workbook = new SXSSFWorkbook(100);
			reportSheet = workbook.createSheet("Reports");
			Row header = reportSheet.createRow(0);
			header.setHeight((short) 500);
			Row dataRow = null;
			
//			header.createCell(0).setCellValue("Unique Asset");
//			header.createCell(1).setCellValue("Compnay Code");
//			header.createCell(2).setCellValue("Asset Number");
//			header.createCell(3).setCellValue("Sub Number");
//			header.createCell(4).setCellValue("Legacy Unique Asset Number");
//			header.createCell(5).setCellValue("Asset Description");
//			header.createCell(6).setCellValue("Asset Class");
//			header.createCell(7).setCellValue("Asset Class Description");
//			header.createCell(8).setCellValue("Legacy Category Code");
//			header.createCell(9).setCellValue("Entered Date");
//			header.createCell(10).setCellValue("Install Date");
//			header.createCell(11).setCellValue("Tax Installation YR");
//			header.createCell(12).setCellValue("Asset Life (YY-MM)");
//			header.createCell(13).setCellValue("Remaining Life (YY-MM)");
//			header.createCell(14).setCellValue("Tax Apc");
//			header.createCell(15).setCellValue("Depreciable Basis");
//			header.createCell(16).setCellValue("Accmulated Reserve");
//			header.createCell(17).setCellValue("Year To Date Deprec");
//			header.createCell(18).setCellValue("Tax Nbv");
//			header.createCell(19).setCellValue("Bonus Depreciation");
//			header.createCell(20).setCellValue("Tax YTD Bonus Depr");
//			header.createCell(21).setCellValue("Projection_Year 1");
//			header.createCell(22).setCellValue("Projection_Year 2");
//			header.createCell(23).setCellValue("Projection_Year 3");
//			header.createCell(24).setCellValue("Projection_Year 4");
//			header.createCell(25).setCellValue("Projection_Year 5");
//			header.createCell(26).setCellValue("Elect Out Bonus");
//			header.createCell(27).setCellValue("Tax Bonus Percentage");
//			header.createCell(28).setCellValue("Fisrt Year Depreciated");
//			header.createCell(29).setCellValue("Last Year Depreciated");
//			header.createCell(30).setCellValue("Last Month Depreciated");
//			header.createCell(31).setCellValue("New Or Used");		
//			header.createCell(32).setCellValue("Tax Depr Method");
//			header.createCell(33).setCellValue("Tax Depr Convention");
//			header.createCell(34).setCellValue("Current Accounting Year");
//			header.createCell(35).setCellValue("Corp Install Date");
//			header.createCell(36).setCellValue("Corp Life (YY MM)");
//			header.createCell(37).setCellValue("Corp Book APC");
//			header.createCell(38).setCellValue("Corp Book Accum Reserve");
//			header.createCell(39).setCellValue("Corp NBV");
//			header.createCell(40).setCellValue("Record Creation Date");
//			header.createCell(41).setCellValue("Record Creation Time");
//			header.createCell(42).setCellValue("Record Creation User");
//			header.createCell(43).setCellValue("Post Capitalization");
			
			String[] headers=null;
			List<String[]> headerList=getTableHeadings();
			int count=0;
			for (String[] strings : headerList) {
			    headers=strings;
			    header.createCell(count).setCellValue(headers[0]);
			   count++;
			}
			
			int rowNum = 1;
			
			String incometaxprojcompanygroup=request.getParameter("incometaxprojcompanygroup");
			String incometaxprojcompany=request.getParameter("incometaxprojcompany");
			String incometaxprojinstalldatefrom =request.getParameter("incometaxprojInstallDateFrom");
			String incometaxprojinstalldateto =request.getParameter("incometaxprojInstallDateTo");
			String incometaxprojcreatedatefrom =request.getParameter("incometaxprojCreateDateFrom");
			String incometaxprojcreatedateto = request.getParameter("incometaxprojCreateDateTo");
			

			List<String> sArray = new ArrayList<String>();
			
			int mapi=0;
			Map<String,String> prepareParamMap=new LinkedHashMap<String, String>(); 
			
				
				if (null!=incometaxprojcompanygroup && !incometaxprojcompanygroup.equals("")) {
					//sArray.add(" FOR_COMPANYCODE in (select company from FACOMPANY where companygroup like '%" + incometaxprojcompanygroup + "%')");
					sArray.add(" FOR_COMPANYCODE in (select company from FACOMPANY where companygroup like ?)");
					prepareParamMap.put(++mapi+"_"+"like",incometaxprojcompanygroup);
				}
				
			
			if (null!=incometaxprojcompany && !incometaxprojcompany.equals("")) {
				//sArray.add(" FOR_COMPANYCODE like '%" + incometaxprojcompany + "%'");
				sArray.add(" FOR_COMPANYCODE like ? ");
				prepareParamMap.put(++mapi+"_"+"like",incometaxprojcompany);
				
			}
			
			
			if (null!=incometaxprojinstalldatefrom && !incometaxprojinstalldatefrom.equals("")) {
				String incometaxprojInstallQuery=" to_date(case when FOR_INSTALLDATE= '00000000' then null else FOR_INSTALLDATE end,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
				
				
				//incometaxprojInstallQuery=incometaxprojInstallQuery.replace("incometaxprojInstallDateFrom", incometaxprojinstalldatefrom);
				prepareParamMap.put(++mapi+"_"+"eq",incometaxprojinstalldatefrom);
				sArray.add(incometaxprojInstallQuery);
			}
			
			if (null!=incometaxprojinstalldateto && !incometaxprojinstalldateto.equals("")) {
				String incometaxprojInstallQuery=" to_date(case when FOR_INSTALLDATE= '00000000' then null else FOR_INSTALLDATE end,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
				
				//incometaxprojInstallQuery=incometaxprojInstallQuery.replace("incometaxprojInstallDateTo", incometaxprojinstalldateto);
				prepareParamMap.put(++mapi+"_"+"eq",incometaxprojinstalldateto);
				sArray.add(incometaxprojInstallQuery);
			}
			//ProptaxcreateDateFrom
			if (null!=incometaxprojcreatedatefrom && !incometaxprojcreatedatefrom.equals("")) {
				String incometaxprojInstallQuery=" to_date(FOR_ENTEREDDATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
				
				
				//incometaxprojInstallQuery=incometaxprojInstallQuery.replace("incometaxprojCreateDateFrom", incometaxprojcreatedatefrom);
				prepareParamMap.put(++mapi+"_"+"eq",incometaxprojcreatedatefrom);
				sArray.add(incometaxprojInstallQuery);
			}
			//proptaxCreateDateTo
			if (null!=incometaxprojcreatedateto && !incometaxprojcreatedateto.equals("")) {
				String incometaxprojInstallQuery=" to_date(FOR_ENTEREDDATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
				
				//incometaxprojInstallQuery=incometaxprojInstallQuery.replace("incometaxprojCreateDateTo", incometaxprojcreatedateto);
				prepareParamMap.put(++mapi+"_"+"eq",incometaxprojcreatedateto);
				sArray.add(incometaxprojInstallQuery);
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
			
//			String searchSQL="select FOR_UNIQUEASSET,FOR_COMPANYCODE,FOR_ASSETNUMBER,FOR_SUBNUMBER,FOR_LEGUNIQUE,FOR_ASSETDESCRIPTION,FOR_ASSETCLASSS,FOR_ASSETCLASSDESCRIPTION,FOR_LEGACYCATEGORYCODE,FOR_ENTEREDDATE,FOR_INSTALLDATE,\r\n" + 
//					        "(select CASE when FOR_INSTALLDATE <> '00000000' then EXTRACT(YEAR FROM TO_DATE(FOR_INSTALLDATE, 'MM-DD-YYYY')) else 0 end FROM dual) AS FOR_TAXINSTALLATIONYR, FOR_ASSETLIFE,FOR_REMAININGLIFE,FOR_TAXAPC,\r\n" + 
//					        "(SELECT CASE WHEN to_char(FOR_ASSETCLASSS)='0000' THEN '0' ELSE to_char(FOR_TAXAPC) END from dual) AS FOR_DEPRECIABLEBASIS, FOR_ACCUMULATEDRESERVE,FOR_YEARTODATEDEPREC,\r\n" + 
//					        "FOR_TAXNBV,FOR_BONUSDEPRECIATION,FOR_TAXYTDBONUSDEPR,FOR_PROJECTION_YEAR1,FOR_PROJECTION_YEAR2,FOR_PROJECTION_YEAR3,FOR_PROJECTION_YEAR4,FOR_PROJECTION_YEAR5,FOR_ELECTOUTBONUS,\r\n" + 
//					        " (select case when (FOR_BONUSDEPRECIATION) <> 0 AND (FOR_TAXAPC) <> 0 then TRUNC(abs(FOR_BONUSDEPRECIATION)/abs(FOR_TAXAPC)*100,0) else 0 end from dual)AS FOR_TAXBONUSPERCENTAGE,\r\n" + 
//					        "FOR_FIRSTYEARDEPRECIATED,FOR_LASTYEARDEPRECIATED,FOR_LASTMONTHDEPRECIATED,FOR_NEWORUSED,FOR_TAXDEPRMETHOD,FOR_TAXDEPRCONVENTION,\r\n" + 
//					        "FOR_CURRENTACCOUNTINGYEAR,FOR_CORPINSTALLDATE,FOR_CORPLIFE,FOR_CORPBOOKAPC,FOR_CORPBOOKACCUMRESERVE,FOR_CORPNBV,FOR_RECORDCREATIONDATE,FOR_RECORDCREATIONTIME,FOR_RECORDCREATIONUSER,FOR_POSTCAPITALIZATION from INC_TAX_PROJ_REPORT";
			
			String tName=getReportTables();
			String searchSQL= callProcedure(tName.trim());
			
			if(individualSearch!=null && individualSearch!=""){
				searchSQL = searchSQL+" where " + individualSearch;
				//searchSQL = searchSQL+" and REGEXP_LIKE (FOR_INSTALLDATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
			}
			
			searchSQL += " order by FOR_UNIQUEASSET asc";

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
								if (StringUtils.isNotBlank(entry) && (colId.contains(cellNum)))
                                {
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
						if (StringUtils.isNotBlank(entry) && (colId.contains(cellNum)))
                        {
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
			return workbook;
		}
		@Override
		public String getIncomeTaxProjectionDataTableResponse(HttpServletRequest request) {
			
			
//			String[] cols = {"FOR_UNIQUEASSET","FOR_COMPANYCODE","FOR_ASSETNUMBER","FOR_SUBNUMBER","FOR_LEGUNIQUE","FOR_ASSETDESCRIPTION","FOR_ASSETCLASSS","FOR_ASSETCLASSDESCRIPTION","FOR_LEGACYCATEGORYCODE","FOR_ENTEREDDATE", "FOR_INSTALLDATE","FOR_TAXINSTALLATIONYR","FOR_ASSETLIFE","FOR_REMAININGLIFE","FOR_TAXAPC","FOR_DEPRECIABLEBASIS","FOR_ACCUMULATEDRESERVE","FOR_YEARTODATEDEPREC",
//			"FOR_TAXNBV","FOR_BONUSDEPRECIATION","FOR_TAXYTDBONUSDEPR","FOR_PROJECTION_YEAR1","FOR_PROJECTION_YEAR2","FOR_PROJECTION_YEAR3","FOR_PROJECTION_YEAR4","FOR_PROJECTION_YEAR5","FOR_ELECTOUTBONUS","FOR_TAXBONUSPERCENTAGE","FOR_FIRSTYEARDEPRECIATED","FOR_LASTYEARDEPRECIATED",
//			"FOR_LASTMONTHDEPRECIATED","FOR_NEWORUSED","FOR_TAXDEPRMETHOD","FOR_TAXDEPRCONVENTION","FOR_CURRENTACCOUNTINGYEAR","FOR_CORPINSTALLDATE","FOR_CORPLIFE","FOR_CORPBOOKAPC","FOR_CORPBOOKACCUMRESERVE","FOR_CORPNBV","FOR_RECORDCREATIONDATE","FOR_RECORDCREATIONTIME","FOR_RECORDCREATIONUSER","FOR_POSTCAPITALIZATION"};
//			
//			String table = "INC_TAX_PROJ_REPORT";
			
			String tName=getReportTables();
			String TableName= callProcedure(tName.trim());

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
			
			String incometaxprojcompanygroup=request.getParameter("incometaxprojcompanygroup");
			String incometaxprojcompany=request.getParameter("incometaxprojcompany");
			String incometaxprojinstalldatefrom =request.getParameter("incometaxprojInstallDateFrom");
			String incometaxprojinstalldateto =request.getParameter("incometaxprojInstallDateTo");
			String incometaxprojcreatedatefrom =request.getParameter("incometaxprojCreateDateFrom");
			String incometaxprojcreatedateto = request.getParameter("incometaxprojCreateDateTo");
			
			List<String> sArray = new ArrayList<String>();
			
			int mapi=0;
			Map<String,String> prepareParamMap=new LinkedHashMap<String, String>();
				
				if (null!=incometaxprojcompanygroup && !incometaxprojcompanygroup.equals("")) {
					sArray.add(" FOR_COMPANYCODE in (select company from FACOMPANY where companygroup like ?)");
					prepareParamMap.put(++mapi+"_"+"like",incometaxprojcompanygroup);
				}
			
			
			
			if (null!=incometaxprojcompany && !incometaxprojcompany.equals("")) {
				sArray.add(" FOR_COMPANYCODE like ? ");
				prepareParamMap.put(++mapi+"_"+"like",incometaxprojcompany);
			}
			
			
			if (null!=incometaxprojinstalldatefrom && !incometaxprojinstalldatefrom.equals("")) {
				String incometaxprojInstallQuery=" to_date(FOR_INSTALLDATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
				
				
				//incometaxprojInstallQuery=incometaxprojInstallQuery.replace("incometaxprojInstallDateFrom", incometaxprojinstalldatefrom);
				prepareParamMap.put(++mapi+"_"+"eq",incometaxprojinstalldatefrom);
				sArray.add(incometaxprojInstallQuery);
			}
			
			if (null!=incometaxprojinstalldateto && !incometaxprojinstalldateto.equals("")) {
				String incometaxprojInstallQuery=" to_date(FOR_INSTALLDATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
				
				//incometaxprojInstallQuery=incometaxprojInstallQuery.replace("incometaxprojInstallDateTo", incometaxprojinstalldateto);
				prepareParamMap.put(++mapi+"_"+"eq",incometaxprojinstalldateto);
				sArray.add(incometaxprojInstallQuery);
			}
			//ProptaxcreateDateFrom
			if (null!=incometaxprojcreatedatefrom && !incometaxprojcreatedatefrom.equals("")) {
				String incometaxprojInstallQuery=" to_date(FOR_ENTEREDDATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
				
				
				//incometaxprojInstallQuery=incometaxprojInstallQuery.replace("incometaxprojCreateDateFrom", incometaxprojcreatedatefrom);
				prepareParamMap.put(++mapi+"_"+"eq",incometaxprojcreatedatefrom);
				sArray.add(incometaxprojInstallQuery);
			}
			//proptaxCreateDateTo
			if (null!=incometaxprojcreatedateto && !incometaxprojcreatedateto.equals("")) {
				String incometaxprojInstallQuery=" to_date(FOR_ENTEREDDATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
				
				//incometaxprojInstallQuery=incometaxprojInstallQuery.replace("incometaxprojCreateDateTo", incometaxprojcreatedateto);
				prepareParamMap.put(++mapi+"_"+"eq",incometaxprojcreatedateto);
				sArray.add(incometaxprojInstallQuery);
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
//				String sql = "SELECT count(*) FROM "+ table +  " WHERE REGEXP_LIKE (FOR_INSTALLDATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$') and ROWNUM <= 200";
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
				String sql = TableName;
				String searchTerm = request.getParameter("sSearch");
				String globeSearch = ""; 
//						" where ( upper(FOR_UNIQUEASSET) like upper(?)"
//				+ " or upper(FOR_COMPANYCODE) like upper(?)"
//				+ " or upper(FOR_ASSETNUMBER) like upper(?)"
//				+ " or upper(FOR_SUBNUMBER) like upper(?)"
//				+ " or upper(FOR_LEGUNIQUE) like upper(?)"
//				+ " or upper(FOR_ASSETDESCRIPTION) like upper(?)"
//				+ " or upper(FOR_ASSETCLASSS) like upper(?)"
//				+ " or upper(FOR_ASSETCLASSDESCRIPTION) like upper(?)"
//				+ " or upper(FOR_LEGACYCATEGORYCODE) like upper(?)"
//			    + " or upper(FOR_ENTEREDDATE) like upper(?)"
//			    + " or upper(FOR_INSTALLDATE) like upper(?)"
//			    + " or upper(FOR_TAXINSTALLATIONYR) like upper(?)"
//				+ " or upper(FOR_ASSETLIFE) like upper(?)"
//				+ " or upper(FOR_REMAININGLIFE) like upper(?)"
//				+ " or upper(FOR_TAXAPC) like upper(?)"
//				+ " or upper(FOR_DEPRECIABLEBASIS) like upper(?)"
//				+ " or upper(FOR_ACCUMULATEDRESERVE) like upper(?)"
//				+ " or upper(FOR_YEARTODATEDEPREC) like upper(?)"
//				+ " or upper(FOR_TAXNBV) like upper(?)"
//				+ " or upper(FOR_BONUSDEPRECIATION) like upper(?)"
//				+ " or upper(FOR_TAXYTDBONUSDEPR) like upper(?)"
//				+ " or upper(FOR_PROJECTION_YEAR1) like upper(?)"
//				+ " or upper(FOR_PROJECTION_YEAR2) like upper(?)"
//				+ " or upper(FOR_PROJECTION_YEAR3) like upper(?)"
//				+ " or upper(FOR_PROJECTION_YEAR4) like upper(?)"
//				+ " or upper(FOR_PROJECTION_YEAR5) like upper(?)"
//				+ " or upper(FOR_ELECTOUTBONUS) like upper(?)"
//				+ " or upper(FOR_TAXBONUSPERCENTAGE) like upper(?)"
//				+ " or upper(FOR_FIRSTYEARDEPRECIATED) like upper(?)"
//				+ " or upper(FOR_LASTYEARDEPRECIATED) like upper(?)"
//				+ " or upper(FOR_LASTMONTHDEPRECIATED) like upper(?)"
//				+ " or upper(FOR_NEWORUSED) like upper(?)"
//				+ " or upper(FOR_TAXDEPRMETHOD) like upper(?)"
//				+ " or upper(FOR_TAXDEPRCONVENTION) like upper(?)"
//				+ " or upper(FOR_CURRENTACCOUNTINGYEAR) like upper(?)"
//				//+ " or upper(FOR_CONSOLIDATEDBU) like upper('%"+searchTerm+"%')"
//				+ " or upper(FOR_CORPINSTALLDATE) like upper(?)"
//				+ " or upper(FOR_CORPLIFE) like upper(?)"
//				+ " or upper(FOR_CORPBOOKAPC) like upper(?)"
//				+ " or upper(FOR_CORPBOOKACCUMRESERVE) like upper(?)"
//				+ " or upper(FOR_CORPNBV) like upper(?)"
//				+ " or upper(FOR_RECORDCREATIONDATE) like upper(?)"
//				+ " or upper(FOR_RECORDCREATIONTIME) like upper(?)"
//				+ " or upper(FOR_RECORDCREATIONUSER) like upper(?)"
//				+ " or upper(FOR_POSTCAPITALIZATION) like upper(?))";
				
				
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
					sql=sql+" and ROWNUM <= 200 and REGEXP_LIKE (FOR_INSTALLDATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
				}else {
					sql=sql+" and ROWNUM <= 200 and REGEXP_LIKE (FOR_INSTALLDATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
				}
				
				//sql += " order by " + colName + " " + dir;
				sql += "order by FOR_UNIQUEASSET asc";
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
//				Date date1;
//				while (rs.next()) {
//					JSONArray ja = new JSONArray();
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_UNIQUEASSET")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_COMPANYCODE")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_ASSETNUMBER")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_SUBNUMBER"))); 
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_LEGUNIQUE")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_ASSETDESCRIPTION")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_ASSETCLASSS")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_ASSETCLASSDESCRIPTION")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_LEGACYCATEGORYCODE")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_ENTEREDDATE")));				
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_INSTALLDATE")));
//					if(StringEscapeUtils.escapeHtml4(rs.getString("FOR_INSTALLDATE"))!=null && !StringEscapeUtils.escapeHtml4(rs.getString("FOR_INSTALLDATE")).equals(""))
//					 {
//					 Date d = new Date(StringEscapeUtils.escapeHtml4(rs.getString("FOR_INSTALLDATE")));
//			         SimpleDateFormat formatNowYear = new SimpleDateFormat("YYYY");
//			         String currentYear = formatNowYear.format(d); 
//					ja.put((currentYear));
//					 }
//					 else
//					 {
//						 ja.put(""); 
//					 }
//								
//					//ja.put(rs.getString("FOR_TAXINSTALLATIONYR"));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_ASSETLIFE")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_REMAININGLIFE")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_TAXAPC")));
//					if(StringEscapeUtils.escapeHtml4(rs.getString("FOR_ASSETCLASSS")).equals("0000")) {
//						ja.put("0");	
//					}
//					else {
//						ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_TAXAPC")));
//					}
//										
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_ACCUMULATEDRESERVE")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_YEARTODATEDEPREC")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_TAXNBV")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_BONUSDEPRECIATION")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_TAXYTDBONUSDEPR")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_PROJECTION_YEAR1")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_PROJECTION_YEAR2")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_PROJECTION_YEAR3")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_PROJECTION_YEAR4")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_PROJECTION_YEAR5")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_ELECTOUTBONUS")));
//					if(StringEscapeUtils.escapeHtml4(rs.getString("FOR_BONUSDEPRECIATION"))!=null && !StringEscapeUtils.escapeHtml4(rs.getString("FOR_BONUSDEPRECIATION")).equals("") && StringEscapeUtils.escapeHtml4(rs.getString("FOR_TAXAPC"))!=null && !StringEscapeUtils.escapeHtml4(rs.getString("FOR_TAXAPC")).equals(""))
//					{
//					double bonusdepr=Double.parseDouble(StringEscapeUtils.escapeHtml4(rs.getString("FOR_BONUSDEPRECIATION")));
//					double taxapc=Double.parseDouble(StringEscapeUtils.escapeHtml4(rs.getString("FOR_TAXAPC")));
//					int bd=(int)bonusdepr;
//					int tp=(int)taxapc;
//					if(bd!=0 & tp!=0 )
//					{
//						double per = bonusdepr / taxapc * 100;
//						  int i1=(int)per;
//						String per1 = Double.toString(i1);
//						String[] arrOfStr = per1.split("-");
//						ja.put(arrOfStr[1]);
//						}
//						
//						else {
//							ja.put("0");
//						}	
//						}
//						else {
//							ja.put("");
//								}			
//				/*
//				 * 
//				 * double bonusdepr=Double.parseDouble(rs.getString("FOR_BONUSDEPRECIATION"));
//				 * double taxapc=Double.parseDouble(rs.getString("FOR_TAXAPC")); int
//				 * bd=(int)bonusdepr; int tp=(int)taxapc; if(bd!=0 & tp!=0 ) { double per =
//				 * bonusdepr/taxapc*100; String per1 = Double.toString(per); String[] arrOfStr =
//				 * per1.split("-"); ja.put(arrOfStr[1]); } else { ja.put("0"); }
//				 */					
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_FIRSTYEARDEPRECIATED")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_LASTYEARDEPRECIATED")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_LASTMONTHDEPRECIATED")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_NEWORUSED")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_TAXDEPRMETHOD")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_TAXDEPRCONVENTION")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_CURRENTACCOUNTINGYEAR")));
//					//ja.put(rs.getString("FOR_CONSOLIDATEDBU"));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_CORPINSTALLDATE")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_CORPLIFE")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_CORPBOOKAPC")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_CORPBOOKACCUMRESERVE")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_CORPNBV")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_RECORDCREATIONDATE")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_RECORDCREATIONTIME")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_RECORDCREATIONUSER")));
//					ja.put(StringEscapeUtils.escapeHtml4(rs.getString("FOR_POSTCAPITALIZATION")));
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
		
		public List<String[]> getIncomeTaxProjectionCompanyGroups() {
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
		public List<String[]> getIncomeTaxProjectionInstances() {
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
		public List<String[]> getIncomeTaxProjectionYears() {
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
		
		public List<String[]> getIncomeTaxProjectionCompanyNames(String COMPANYGROUP) {
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
			//System.out.println("Inc projection headings simpl== "+tName.trim());
			 List<String[]> headingList = new ArrayList<String[]>();
				Connection conn;
				PreparedStatement ps;
				try {
				String query="select COLUMN_NAME,COLUMN_ID,HIST_INC_TAXREG_UI from SYS.all_tab_columns,all_tables_column_names where table_name = ? and COLUMN_NAME=HIST_INC_TAXREG_DB ORDER BY COLUMN_ID";
				conn = dataTableDao.getConnection();
				ps = conn.prepareStatement(query);
				ps.setString(1, tName.trim());
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
		
		public String getReportTables() {
			String resultList = "";
			Connection conn;
			PreparedStatement ps;
			try {
			String query = "select * from report_index_table where layout='SHASTPRJ' and report_id='PRD-TAXPRJ' and version='Prod'";
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
			//	System.out.println("Inc projection store procedure table name=="+reportTableName);
				stm.setString(1, reportTableName);
			    stm.registerOutParameter(2,Types.VARCHAR);
			    stm.execute();
			    m_count = stm.getString(2);
			//    System.out.println("Inc projection store procedure columns"+m_count.toString());
			    stm.close();
				conn1.close();	
			}
			catch(Exception e){
				System.out.println(e);
			}
			return m_count;
			
		}

	}


