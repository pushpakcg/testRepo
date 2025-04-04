package com.code2java.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
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

import com.code2java.dao.DataTableDAO;

@Service
public class SAPHistAssetTransferAllTransServiceImpl implements SAPHistAssetTransferAllTransService{

	
		@Autowired
		private DataTableDAO dataTableDao;
		
		 public SXSSFWorkbook getSAPHistAssetTransferAllTransExcelData(HttpServletRequest request){
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
				
				
		    String histAssetTransferfacompanygroup=request.getParameter("facompanygroup");
			String histAssetTransferfacompany=request.getParameter("facompany");
			String histAssetTransferInstallDateFrom=request.getParameter("taxInstallDateFrom");
			String histAssetTransferInstallDateTo=request.getParameter("taxInstallDateTo");
					
			String histAssetTransferyear=request.getParameter("year");
			String histAssetTransferreportId=request.getParameter("reportId");
			String tt=histAssetTransferreportId.trim();
				
			String tableName="";
			//List<String[]> tableNameList=this.getReportTables(request.getParameter("reportId"),request.getParameter("year"));
			List<String[]> tableNameList=this.getReportTables(histAssetTransferreportId,histAssetTransferyear);
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
			
			if (null!=histAssetTransferfacompany && !histAssetTransferfacompany.equals("")) {			
				sArray.add(" AT_COMPANY_CODE like ?");
				prepareParamMap.put(++mapi+"_"+"like",histAssetTransferfacompany);
			}
			
			if (null!=histAssetTransferfacompanygroup && !histAssetTransferfacompanygroup.equals("")) {			
				sArray.add(" AT_COMPANY_CODE in (select company from FACOMPANY where companygroup like ?)");
				prepareParamMap.put(++mapi+"_"+"like",histAssetTransferfacompanygroup);
			}
			
			if (null!=histAssetTransferInstallDateFrom && !histAssetTransferInstallDateFrom.equals("")) {			
				String taxInstallQuery=" to_date(AT_POSTING_DATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
				prepareParamMap.put(++mapi+"_"+"eq",histAssetTransferInstallDateFrom);
				sArray.add(taxInstallQuery);
			}
			System.out.println("test "+histAssetTransferInstallDateFrom);
			
			if (null!=histAssetTransferInstallDateTo && !histAssetTransferInstallDateTo.equals("")) {			
				String taxInstallQuery=" to_date(AT_POSTING_DATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
				prepareParamMap.put(++mapi+"_"+"eq",histAssetTransferInstallDateTo);
				sArray.add(taxInstallQuery);
			}
			
			
			
			if (null!=histAssetTransferyear && !histAssetTransferyear.equals("")) {
				//sArray.add(" INC_COMPANYCODE like '%" + facompany + "%'");
				sArray.add(" YEAR like ?");
				prepareParamMap.put(++mapi+"_"+"like",histAssetTransferyear);
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
			
			
			

	//String searchSQL="select YEAR,REPORT_NAME,ADS_UNIQUEASSET,ADS_COMPANYCODE,ADS_ASSETNUMBER,ADS_SUBNUMBER,ADS_LEGUNIQUE,ADS_ASSETDESCRIPTION,ADS_ASSETCLASSS,ADS_ASSETCLASSDESCRIPTION,ADS_LEGACYCATEGORYCODE,\n" + 
//					"ADS_ENTEREDDATE,ADS_INSTALLDATE,(select CASE when ADS_INSTALLDATE <> '00000000' then EXTRACT(YEAR FROM TO_DATE(ADS_INSTALLDATE, 'MM-DD-YYYY')) else 0 end FROM dual) AS ADS_TAXINSTALLATIONYR,ADS_ASSETLIFE,ADS_REMAININGLIFE,\n" + 
//					"ADS_TAXAPC,(SELECT CASE WHEN to_char(ADS_ASSETCLASSS)='0000' THEN '0' ELSE to_char(ADS_TAXAPC) END from dual) AS ADS_DEPRECIABLEBASIS,ADS_ACCUMULATEDRESERVE,\n" + 
//					"ADS_YEARTODATEDEPREC,ADS_TAXNBV,ADS_FIRSTYEARDEPRECIATED,ADS_LASTYEARDEPRECIATED,ADS_LASTMONTHDEPRECIATED,ADS_NEWORUSED,ADS_TAXDEPRMETHOD,ADS_TAXDEPRCONVENTION,\n" + 
//					"ADS_CURRENTACCOUNTINGYEAR,ADS_CORPINSTALLDATE,ADS_CORPLIFE,ADS_CORPBOOKAPC,ADS_CORPBOOKACCUMRESERVE,ADS_CORPNBV,ADS_RECORDCREATIONDATE,\n" + 
//					"ADS_RECORDCREATIONTIME,ADS_RECORDCREATIONUSER,ADS_POSTCAPITALIZATION from $tablename";	
			
			String searchSQL= callProcedure(table.trim());
			if(individualSearch!=null && individualSearch!=""){
				searchSQL = searchSQL+" where " + individualSearch;
				
			}
			
			searchSQL += " order by AT_ASSET asc";

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
				System.out.println(searchSQL1);
				
				ps.setFetchSize(2000);
				ResultSet rs2 = ps.executeQuery();
				
				List<List<String>> jqn=sortedSheet(rs2);
				System.out.println("rowCount "+jqn.size());
				System.out.println("resultset Start Time: "+new Date());
//				int counter=0;
//				ResultSetMetaData rsMeta=rs2.getMetaData();
//				int ct=rsMeta.getColumnCount();
//				while (rs2.next()) {
//					counter++;
//					List<String> resultRowData = new ArrayList<String>();
//					//System.out.println(resultRowData);
//					for (int i = 1; i <=ct; i++) {	
//						
//						resultRowData.add(rs2.getString(i));
//					}
//					resultDataList.add(resultRowData);
//					resultRowData=new ArrayList<String>(1);
//					if(counter % 30000==0)
//					{
						for (List<String> list : jqn) {
							int cellNum = 0;
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
//						resultDataList=null;
//						
//						 resultDataList = new ArrayList<List<String>>(1);//10
						
					
				
				
//				for (List<String> list : resultDataList) {
//					int cellNum = 0;
//					Row row = reportSheet.createRow(rowNum++);
//					for (String entry : list) {
//						if (StringUtils.isNotBlank(entry) && (colId.contains(cellNum))) {
//							
//							row.createCell(cellNum++).setCellValue(Double.parseDouble(entry));
//						} else {
//							row.createCell(cellNum++).setCellValue(entry);
//						}
//				}
//				}
//				
//				resultDataList=null;
//			 resultDataList = new ArrayList<List<String>>(1);//10
				System.out.println("resultset End Time: "+new Date());
				ps.close();
				conn.close();
				rs2.close();
			}catch(Exception e) {
				System.out.println("Exception"+e);
			}
			return workbook;
		}

		 List<List<String>> sortedSheet(ResultSet rs)
			{
				
				HashMap<List<String>,List<List<String>>> m1=new HashMap<>();
				List<List<String>> l1=new ArrayList<>();
				try {
					ResultSetMetaData rsMeta=rs.getMetaData();
					int ct=rsMeta.getColumnCount();
				while(rs.next())
				{
					List<String> thisKey=new ArrayList<>();
					List<String> thisData=new ArrayList<>();
					List<String> thisBool=new ArrayList<>();
					List<List<String>> thisVal=new ArrayList<>();
					thisKey.add(rs.getString("AT_ASSET"));
					thisKey.add(rs.getString("AT_ACQ_OR_RETR_ASSET_NO"));
					for (int i = 1; i <= ct; i++) {	
						
						thisData.add(rs.getString(i));
							}
					thisBool.add("f");
					thisVal.add(thisData);
					thisVal.add(thisBool);
					
					m1.put(thisKey,thisVal);
				}
				System.out.println("thiss"+m1.size());
				}
				catch(Exception e)
				{
					System.out.println("exception: "+e);
				}
				for(List<String> r:m1.keySet())
				{
					//System.out.println(m1.get(r));
					if(m1.get(r).get(1).get(0)=="t")
					{
						//System.out.println(m1.get(r).get(1).get(0));
						continue;
					}
					else
					{
						l1.add(m1.get(r).get(0));
		//null check-- if values are null just add them to the list and don't perform reverse operation
						if(r.get(1)==null ||r.get(0)==null)
						{
							continue;
						}
						List<String> tmp=new ArrayList<>();
						tmp.add(r.get(1));
						tmp.add(r.get(0));
						if(m1.containsKey(tmp))
						{
							m1.get(tmp).get(1).add(0, "t");
							l1.add(m1.get(tmp).get(0));
						}
						

					}
				}
				return l1;
			}

		@Override
	public String getSAPHistAssetTransferAllTransDataTableResponse(HttpServletRequest request) {

			//String[] cols = {"INC_UNIQUEASSET","INC_COMPANYCODE","INC_ASSETNUMBER","INC_SUBNUMBER","INC_ASSETDESCRIPTION","INC_ASSETCLASSS","INC_ASSETCLASSDESCRIPTION","INC_LEGACYCATEGORYCODE","INC_ENTEREDDATE","INC_INSTALLDATE","INC_TAXINSTALLATIONYR","INC_ASSETLIFE","INC_REMAININGLIFE","INC_TAXAPC","INC_DEPRECIABLEBASIS","INC_ACCUMULATEDDEPRECIATION","INC_YEARTODATEDEPREC","INC_TAXNBV","INC_BONUSDEPRECIATION","INC_TAXYTDBONUSDEPR","INC_ELECTOUTBONUS","INC_TAXBONUSPERCENTAGE","INC_FIRSTYEARDEPRECIATED","INC_LASTYEARDEPRECIATED",
				//	"INC_LASTMONTHDEPRECIATED","INC_NEWORUSED","INC_TAXDEPRMETHOD","INC_TAXDEPRCONVENTION","INC_CURRENTACCOUNTINGYEAR","INC_CORPINSTALLDATE","INC_CORPLIFE","INC_CORPBOOKAPC","INC_CORPBOOKACCUMRESERVE","INC_CORPNBV","INC_CREATIONDATE","INC_CREATIONTIME","INC_CREATIONUSER","REPORT_NAME","YEAR"};
			
			
//			String[] cols = {"YEAR","REPORT_NAME","ADS_UNIQUEASSET","ADS_COMPANYCODE","ADS_ASSETNUMBER","ADS_SUBNUMBER","ADS_ASSETDESCRIPTION","ADS_ASSETCLASSS","ADS_ASSETCLASSDESCRIPTION","ADS_LEGACYCATEGORYCODE","ADS_ENTEREDDATE", "ADS_INSTALLDATE","ADS_TAXINSTALLATIONYR","ADS_ASSETLIFE","ADS_REMAININGLIFE","ADS_TAXAPC","ADS_DEPRECIABLEBASIS","ADS_ACCUMULATEDRESERVE","ADS_YEARTODATEDEPREC",
//					"ADS_TAXNBV","ADS_BONUSDEPRECIATION","ADS_TAXYTDBONUSDEPR","ADS_ELECTOUTBONUS","ADS_TAXBONUSPERCENTAGE","ADS_FIRSTYEARDEPRECIATED","ADS_LASTYEARDEPRECIATED",
//					"ADS_LASTMONTHDEPRECIATED","ADS_NEWORUSED","ADS_TAXDEPRMETHOD","ADS_TAXDEPRCONVENTION","ADS_CURRENTACCOUNTINGYEAR","ADS_CONSOLIDATEDBU","ADS_CORPINSTALLDATE",
//					"ADS_CORPLIFE","ADS_CORPBOOKAPC","ADS_CORPBOOKACCUMRESERVE","ADS_CORPNBV","ADS_RECORDCREATIONDATE","ADS_RECORDCREATIONTIME","ADS_RECORDCREATIONUSER","ADS_POSTCAPITALIZATION"};
				
			
			String histAssetTransferyear=request.getParameter("year");
			String histAssetTransferreportId=request.getParameter("reportId");
			String tableName="";
			//List<String[]> tableNameList=this.getReportTables(request.getParameter("reportId"),request.getParameter("year"));
			List<String[]> tableNameList=this.getReportTables(histAssetTransferreportId,histAssetTransferyear);
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
			
			String histAssetTransferfacompanygroup=request.getParameter("facompanygroup");
			String histAssetTransferfacompany=request.getParameter("facompany");
			String histAssetTransferInstallDateFrom=request.getParameter("taxInstallDateFrom");
			String histAssetTransferInstallDateTo=request.getParameter("taxInstallDateTo");
					
			

			List<String> sArray = new ArrayList<String>();
			
			int mapi=0;
			Map<String,String> prepareParamMap=new LinkedHashMap<String, String>();
			
			if (null!=histAssetTransferfacompany && !histAssetTransferfacompany.equals("")) {
				//sArray.add(" INC_COMPANYCODE like '%" + facompany + "%'");
				sArray.add(" AT_COMPANY_CODE like ?");
				prepareParamMap.put(++mapi+"_"+"like",histAssetTransferfacompany);
			}
			
			if (null!=histAssetTransferfacompanygroup && !histAssetTransferfacompanygroup.equals("")) {
				//sArray.add(" INC_COMPANYCODE in (select company from FACOMPANY where companygroup like '%" + facompanygroup + "%')");	
				sArray.add(" AT_COMPANY_CODE in (select company from FACOMPANY where companygroup like ?)");
				prepareParamMap.put(++mapi+"_"+"like",histAssetTransferfacompanygroup);
			}
			
			if (null!=histAssetTransferInstallDateFrom && !histAssetTransferInstallDateFrom.equals("")) {
				String taxInstallQuery=" to_date(AT_POSTING_DATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
				
				//taxInstallQuery=taxInstallQuery.replace("taxInsFromParam", taxInstallDateFrom);
				prepareParamMap.put(++mapi+"_"+"eq",histAssetTransferInstallDateFrom);
				sArray.add(taxInstallQuery);
			}
			
			if (null!=histAssetTransferInstallDateTo && !histAssetTransferInstallDateTo.equals("")) {
				String taxInstallQuery=" to_date(AT_POSTING_DATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
				
				//taxInstallQuery=taxInstallQuery.replace("taxInsToParam", taxInstallDateTo);
				prepareParamMap.put(++mapi+"_"+"eq",histAssetTransferInstallDateTo);
				sArray.add(taxInstallQuery);
			}
			
		
			if (null!=histAssetTransferyear && !histAssetTransferyear.equals("")) {
				//sArray.add(" INC_COMPANYCODE like '%" + facompany + "%'");
				sArray.add(" YEAR like ?");
				prepareParamMap.put(++mapi+"_"+"like",histAssetTransferyear);
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

			/* This is total number of records that is available for the specific search query */
			int totalAfterFilter = total;

			try {
				String searchSQL = "";
				String sql = procedureQ;
				//System.out.println("ENTER SEARCH==="+sql);
				String searchTerm = request.getParameter("sSearch");
				String globeSearch = "" ; 
//						" where ( upper(YEAR) like upper(?)"
//						+ " or upper(REPORT_NAME) like upper(?)"
//						+ " or upper(ADS_UNIQUEASSET) like upper(?)"
//						+ " or upper(ADS_COMPANYCODE) like upper(?)"
//						+ " or upper(ADS_ASSETNUMBER) like upper(?)"
//						+ " or upper(ADS_SUBNUMBER) like upper(?)"
//						+ " or upper(ADS_ASSETDESCRIPTION) like upper(?)"
//						+ " or upper(ADS_ASSETCLASSS) like upper(?)"
//						+ " or upper(ADS_ASSETCLASSDESCRIPTION) like upper(?)"
//						+ " or upper(ADS_LEGACYCATEGORYCODE) like upper(?)"
//					    + " or upper(ADS_ENTEREDDATE) like upper(?)"
//					    + " or upper(ADS_INSTALLDATE) like upper(?)"
//					    + " or upper(ADS_TAXINSTALLATIONYR) like upper(?)"
//						+ " or upper(ADS_ASSETLIFE) like upper(?)"
//						+ " or upper(ADS_REMAININGLIFE) like upper(?)"
//						+ " or upper(ADS_TAXAPC) like upper(?)"
//						+ " or upper(ADS_DEPRECIABLEBASIS) like upper(?)"
//						+ " or upper(ADS_ACCUMULATEDRESERVE) like upper(?)"
//						+ " or upper(ADS_YEARTODATEDEPREC) like upper(?)"
//						+ " or upper(ADS_TAXNBV) like upper(?)"
//						+ " or upper(ADS_BONUSDEPRECIATION) like upper(?)"
//						+ " or upper(ADS_TAXYTDBONUSDEPR) like upper(?)"
//						+ " or upper(ADS_ELECTOUTBONUS) like upper(?)"
//						+ " or upper(ADS_TAXBONUSPERCENTAGE) like upper(?)"
//						+ " or upper(ADS_FIRSTYEARDEPRECIATED) like upper(?)"
//					    + " or upper(ADS_LASTYEARDEPRECIATED) like upper(?)"
//					    + " or upper(ADS_LASTMONTHDEPRECIATED) like upper(?)"
//						+ " or upper(ADS_NEWORUSED) like upper(?)"
//						+ " or upper(ADS_TAXDEPRMETHOD) like upper(?)"
//						+ " or upper(ADS_TAXDEPRCONVENTION) like upper(?)"
//						+ " or upper(ADS_CURRENTACCOUNTINGYEAR) like upper(?)"
//						+ " or upper(ADS_CONSOLIDATEDBU) like upper(?)"
//						+ " or upper(ADS_CORPINSTALLDATE) like upper(?)"
//						+ " or upper(ADS_CORPLIFE) like upper(?)"
//						+ " or upper(ADS_CORPBOOKAPC) like upper(?)"
//						+ " or upper(ADS_CORPBOOKACCUMRESERVE) like upper(?)"
//						+ " or upper(ADS_CORPNBV) like upper(?)"
//						+ " or upper(ADS_RECORDCREATIONDATE) like upper(?)"
//						+ " or upper(ADS_RECORDCREATIONTIME) like upper(?)"
//						+ " or upper(ADS_RECORDCREATIONUSER) like upper(?)"
//						+ " or upper(ADS_POSTCAPITALIZATION) like upper(?))";

						
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
				 * sql=sql+" and ROWNUM <= 200"; //+
				 * "and REGEXP_LIKE (TXADD_TAX_INSTALL_DATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
				 * }else { sql=sql+" WHERE ROWNUM <= 200"; // +
				 * "and REGEXP_LIKE (TXADD_TAX_INSTALL_DATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
				 * }
				 */
				
				if(sql.contains("WHERE")||sql.contains("where")){
					sql=sql+" and ROWNUM <= 200 ";
//							+ "and REGEXP_LIKE (ADS_INSTALLDATE, '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
					}else {
					sql=sql+" and ROWNUM <= 200 ";
//							+ "and REGEXP_LIKE (ADS_INSTALLDATE, '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
					}
				
				//sql += " order by " + colName + " " + dir;
				sql += " order by AT_ASSET asc";
				
				
				String sql1 = sql.replace("$tablename", table);
				
				System.out.println("sqlqsql==="+sql1);
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
				
				if(null!=searchTerm && searchTerm!=""){
					mapj = mapj-1;
					int sSearchCount=39;
			        for(int sSearchC=mapj;sSearchC<mapj+sSearchCount;sSearchC++) {
			            ps.setString(sSearchC, "%" + searchTerm + "%");
			        }
				}
				
				ps.setFetchSize(200);
				ResultSet rs = ps.executeQuery();
				List<List<String>> sortedSheet = sortedSheet(rs);
				//System.out.println("size====="+rs.getFetchSize());
				int i=0;
				ResultSetMetaData rsmd = rs.getMetaData(); 
				int columnCount = rsmd.getColumnCount();
//				while (rs.next()) {   
//					JSONArray ja = new JSONArray();
//				   int i1 = 1;
//				   while(i1 <= columnCount) {
//				        ja.put(rs.getString(i1++));
//				   }
//				   array.put(ja);
//				}
				System.out.println("thisss out"+sortedSheet.size());
				for (List<String> rows:sortedSheet) {   
					JSONArray ja = new JSONArray();

				   for(String row: rows) {
				        ja.put(row);
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
		
		public List<String[]> getSAPHistAssetTransferAllTransCompanyGroups() {
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
//		public List<String[]> getSAPHistAssetTransferInstances() {
//			List<String[]> resultList = new ArrayList<String[]>();
//			Connection conn;
//			PreparedStatement ps;
//			try {
//				String query = "select distinct(INSTANCE) from INSTANCE  where INSTANCE is not NULL order by INSTANCE asc";
//				conn = dataTableDao.getConnection();
//				ps = conn.prepareStatement(query);
//				ResultSet rs = ps.executeQuery();
//				while (rs.next()) {
//					String[] arr = new String[2];
//					arr[0] = rs.getString("INSTANCE");
//					arr[1] = rs.getString("INSTANCE");
//					resultList.add(arr);
//				}
//				conn.close();
//				ps.close();
//			} catch (Exception e) {
//				System.out.println(e);
//			}
//			return resultList;
//		}
		
		//Year
		public List<String[]> getSAPHistAssetTransferAllTransYears() {
			List<String[]> resultList = new ArrayList<String[]>();
			Connection conn;
			PreparedStatement ps;
			try {
				String query = "select distinct(YEAR) from REPORT_INDEX_TABLE WHERE LAYOUT='ASTTR2' AND VERSION = 'SAP_HIST' order by YEAR asc";
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
		
		public List<String[]> getSAPHistAssetTransferAllTransReportNames(List<String> reportList) {		
			List<String[]> resultList = new ArrayList<String[]>();
			Connection conn;
			PreparedStatement ps;
			try {
				
				String query = "select * from REPORT_INDEX_TABLE where YEAR=? AND LAYOUT='ASTTR2' AND VERSION = 'SAP_HIST' order by REPORT_NAME asc";
				
				conn = dataTableDao.getConnection();
				ps = conn.prepareStatement(query);
				ps.setString(1, reportList.get(0));
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
		
		
		public List<String[]> getSAPHistAssetTransferAllTransCompanyNames(String COMPANYGROUP) {
			List<String[]> resultList = new ArrayList<String[]>();
			Connection conn;
			PreparedStatement ps;
			try {
				
				String query = "select * from FACOMPANY where COMPANYGROUP=? order by COMPANYNAME asc";
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
			//	System.out.println("Hist ADS tax table name"+reportTableName);
				stm.setString(1, reportTableName);
			    stm.registerOutParameter(2,Types.VARCHAR);
			    stm.execute();
			    m_count = stm.getString(2);
			 //   System.out.println("Hist ADS tax  store procedure query=="+m_count.toString());
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


