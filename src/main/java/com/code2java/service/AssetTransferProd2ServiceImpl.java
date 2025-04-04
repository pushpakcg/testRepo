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
import java.util.HashMap;
import java.util.Iterator;
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

@Service("assetTransferService2")

public class AssetTransferProd2ServiceImpl implements AssetTransferProd2Service{
	
	
		@Autowired
		private DataTableDAO dataTableDao;
		
		@Override
		public SXSSFWorkbook getAssetTransferProd2ExcelData(HttpServletRequest request){
			SXSSFWorkbook workbook = null;
			Sheet reportSheet = null;
			workbook = new SXSSFWorkbook(100);
			reportSheet = workbook.createSheet("Reports");
			Row header = reportSheet.createRow(0);
			header.setHeight((short) 500);
			Row dataRow = null;
			

			
			String[] headers=null;
			List<String[]> headerList=getTableHeadings();
			int count=0;
			for (String[] strings : headerList) {
			    headers=strings;
			    header.createCell(count).setCellValue(headers[0]);
			   count++;
			}
			
			int rowNum = 1;
			
			String companygroup=request.getParameter("companygroup");
			String company=request.getParameter("company");
			String ChangeDateFrom =request.getParameter("ChangeDateFrom");
			String ChangeDateTo =request.getParameter("ChangeDateTo");
//			String proptaxcreatedatefrom =request.getParameter("proptaxCreateDateFrom");
//			String proptaxcreatedateto = request.getParameter("proptaxCreateDateTo");
			

			List<String> sArray = new ArrayList<String>();
			
			int mapi=0;
			Map<String,String> prepareParamMap=new LinkedHashMap<String, String>();
			
			if (null!=companygroup && !companygroup.equals("")) {
				sArray.add(" AT_COMPANY_CODE in (select company from FACOMPANY where companygroup like ?)");
				prepareParamMap.put(++mapi+"_"+"like",companygroup);
			}
			if (null!=company && !company.equals("")) {
				sArray.add(" AT_COMPANY_CODE like ? ");
				prepareParamMap.put(++mapi+"_"+"like",company);
				
			}
			//PROP_ENTERED_DATE  PROP_INSTALL_DATE
			
			if (null!=ChangeDateFrom && !ChangeDateFrom.equals("")) {
				String InstallQuery=" to_date(AT_POSTING_DATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY')   ";
				//proptaxInstallQuery=proptaxInstallQuery.replace("proptaxInstallDateFrom", proptaxinstalldatefrom);
				prepareParamMap.put(++mapi+"_"+"eq",ChangeDateFrom);
				sArray.add(InstallQuery);
			}
			
			if (null!=ChangeDateTo && !ChangeDateTo.equals("")) {
				String InstallQuery=" to_date(AT_POSTING_DATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY')  ";		
				//proptaxInstallQuery=proptaxInstallQuery.replace("proptaxInstallDateTo", proptaxinstalldateto);
				prepareParamMap.put(++mapi+"_"+"eq",ChangeDateTo);
				sArray.add(InstallQuery);
			}
			//ProptaxcreateDateFrom
//			if (null!=proptaxcreatedatefrom && !proptaxcreatedatefrom.equals("")) {
//				String proptaxCreateQuery=" to_date(PROP_ENTERED_DATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
//				
//				
//				//proptaxCreateQuery=proptaxCreateQuery.replace("proptaxCreateDateFrom", proptaxcreatedatefrom);
//				prepareParamMap.put(++mapi+"_"+"eq",proptaxcreatedatefrom);
//				sArray.add(proptaxCreateQuery);
//			}
//			//proptaxCreateDateTo
//			if (null!=proptaxcreatedateto && !proptaxcreatedateto.equals("")) {
//				String proptaxCreateQuery=" to_date(PROP_ENTERED_DATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
//				
//				//proptaxCreateQuery=proptaxCreateQuery.replace("proptaxCreateDateTo", proptaxcreatedateto);
//				prepareParamMap.put(++mapi+"_"+"eq",proptaxcreatedateto);
//				sArray.add(proptaxCreateQuery);
//			}
							
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
			
//			String searchSQL="select PROP_ENTITY,PROP_UNIQUE_ASSTNO,PROP_ASSET_NUMBER,PROP_ASSET_SUB_NUMBER,PROP_INTERNAL_ASST_NO,PROP_LEGACY_ASSET_NUMBER,PROP_LEGACY_ASSET_SUBNUMBER,PROP_ASSET_DESCRIPTION,PROP_COUNTRY,\r\n" + 
//					"PROP_STATE,PROP_COUNTY,PROP_CITY,PROP_STREET,PROP_ZIP_CODE,PROP_BUILDING,PROP_PL_DESCRIPTION,PROP_ENTERED_DATE,(select CASE when PROP_ENTERED_DATE <> '00000000' then EXTRACT(YEAR FROM TO_DATE(PROP_ENTERED_DATE, 'MM-DD-YYYY')) else 0 end FROM dual) AS PROP_ENTERED_YEAR,\r\n" + 
//					"PROP_SAP_ASSET_CLASS,PROP_SAP_ASSET_CLASS_DES,PROP_GL_CATEGORY_DESCRIPTION,PROP_CORPORATE_LIFE,PROP_INSTALL_DATE,(select CASE when PROP_INSTALL_DATE <> '00000000' then EXTRACT(YEAR FROM TO_DATE(PROP_INSTALL_DATE, 'MM-DD-YYYY')) else 0 end FROM dual) AS PROP_INSTALL_YEAR,\r\n" + 
//					"PROP_CORP_BOOK_COST_BASIS,PROP_CORP_BOOK_ACCUM_RSRV,PROP_NET_BOOK_VALUE,PROP_ACCOUNTING_YEAR,PROP_TAX_INSTALL_DATE,PROP_TAX_COST_BASIS,PROP_TAX_NTV,PROP_PPTAX,PROP_INVOICE_NUMBER,PROP_PROJECT_NUMBER,PROP_PURCHASE_ORDER,PROP_WBSE,PROP_PROFIT_CENTER,PROP_COST_CENTER,\r\n" + 
//					"PROP_MANUFACTURE,PROP_VENDOR,PROP_ATTRACTION_ID,PROP_GEO_LOCATION,PROP_DEPARTMENT,PROP_COMPANY_NAME,PROP_SERIAL_NUMBER,PROP_INVENTORY_NUMBER,PROP_QUANTITY,PROP_PHYSICAL_LOCATION_CO_NAME,PROP_TYPE_NAME,PROP_LEGACY_PROJECT_NO,PROP_TRACKING_NUMBER from PROP_TAX_ASSET_LIST_REPORT";
			 
			String tName=getReportTables();
			String table = tName.replaceAll("[^a-zA-Z0-9_]", "");
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
				//custom sorting of data in excel
				//sortedSheet(rs2);
				List<List<String>> jqn=sortedSheet(rs2);
				System.out.println("rowCount "+jqn.size());
//				for(int i=0;i<10;i++)
//				{
//					System.out.println("key:"+jqn.get(i).get(1)+" value: "+jqn.get(i).get(4));
//				}
				
				System.out.println("resultset Start Time: "+new Date());
//				int counter=0;
//				ResultSetMetaData rsMeta=rs2.getMetaData();
//				int ct=rsMeta.getColumnCount();
//				while (rs2.next()) {
//					counter++;
//					List<String> resultRowData = new ArrayList<String>();
//					for (int i = 1; i <= ct; i++) {	
//						
//	            resultRowData.add(rs2.getString(i));
//					}
//					resultDataList.add(resultRowData);
//					resultRowData=new ArrayList<String>(1);
//					if(counter % 30000==0)
//					{
						for (List<String> list : jqn) {

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
//						resultDataList=null;
//						
//						 resultDataList = new ArrayList<List<String>>(1);//10
						
					//}
					
				//}
//				for (List<String> list : resultDataList) {
//
//					int cellNum = 0;
//					// create the row data
//					Row row = reportSheet.createRow(rowNum++);
//					for (String entry : list) {
//						if (StringUtils.isNotBlank(entry) && (colId.contains(cellNum)))
//                        {
//					       row.createCell(cellNum++).setCellValue(Double.parseDouble(entry));
//						} else {
//							row.createCell(cellNum++).setCellValue(entry);
//						}
//
//					}
//				}
//				resultDataList=null;
//			 resultDataList = new ArrayList<List<String>>(1);//10
				System.out.println("resultset End Time: "+new Date());
				ps.close();
				conn.close();
				rs2.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			return workbook;
		}
		//custom sorting of data in excel
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
		public String getAssetTransferProd2DataTableResponse(HttpServletRequest request) {   
			
			
//			String[] cols = {"PROP_ENTITY","PROP_UNIQUE_ASSTNO","PROP_ASSET_NUMBER","PROP_ASSET_SUB_NUMBER","PROP_INTERNAL_ASST_NO","PROP_LEGACY_ASSET_NUMBER","PROP_LEGACY_ASSET_SUBNUMBER","PROP_ASSET_DESCRIPTION","PROP_COUNTRY","PROP_STATE","PROP_COUNTY", "PROP_CITY","PROP_STREET","PROP_ZIP_CODE","PROP_BUILDING","PROP_PL_DESCRIPTION","PROP_ENTERED_DATE","PROP_ENTERED_YEAR","PROP_SAP_ASSET_CLASS",
//			"PROP_SAP_ASSET_CLASS_DES","PROP_GL_CATEGORY_DESCRIPTION","PROP_CORPORATE_LIFE","PROP_INSTALL_DATE","PROP_INSTALL_YEAR","PROP_CORP_BOOK_COST_BASIS","PROP_CORP_BOOK_ACCUM_RSRV","PROP_NET_BOOK_VALUE","PROP_ACCOUNTING_YEAR","PROP_TAX_INSTALL_DATE",
//			"PROP_TAX_COST_BASIS","PROP_TAX_NTV","PROP_PPTAX","PROP_INVOICE_NUMBER","PROP_PROJECT_NUMBER","PROP_PURCHASE_ORDER","PROP_WBSE","PROP_PROFIT_CENTER","PROP_COST_CENTER","PROP_MANUFACTURE","PROP_VENDOR","PROP_ATTRACTION_ID","PROP_GEO_LOCATION","PROP_DEPARTMENT","PROP_COMPANY_NAME","PROP_SERIAL_NUMBER","PROP_INVENTORY_NUMBER","PROP_QUANTITY","PROP_PHYSICAL_LOCATION_CO_NAME","PROP_TYPE_NAME","PROP_LEGACY_PROJECT_NO","PROP_TRACKING_NUMBER"};
			
//			String table = "PROP_TAX_ASSET_LIST_REPORT";
			
			String tName=getReportTables();
			String table = tName.replaceAll("[^a-zA-Z0-9_]", "");
			String TableName= callProcedure(table.trim());

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
			
			
			String companygroup=request.getParameter("companygroup");
			String company=request.getParameter("company");
			String ChangeDateFrom =request.getParameter("ChangeDateFrom");
			String ChangeDateTo =request.getParameter("ChangeDateTo");
//			String proptaxcreatedatefrom =request.getParameter("proptaxCreateDateFrom");
//			String proptaxcreatedateto = request.getParameter("proptaxCreateDateTo");
			
			List<String> sArray = new ArrayList<String>();
			
			int mapi=0;
			Map<String,String> prepareParamMap=new LinkedHashMap<String, String>();
			
			if (null!=companygroup && !companygroup.equals("")) {
				sArray.add(" AT_COMPANY_CODE in (select company from FACOMPANY where companygroup like ?)");
				prepareParamMap.put(++mapi+"_"+"like",companygroup);
			}
			if (null!=company && !company.equals("")) {
				sArray.add(" AT_COMPANY_CODE like ? ");
				prepareParamMap.put(++mapi+"_"+"like",company);
			}
			//PROP_ENTERED_DATE  PROP_INSTALL_DATE
			
			if (null!=ChangeDateFrom && !ChangeDateFrom.equals("")) {
				String InstallQuery=" to_date(AT_POSTING_DATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
					
				//proptaxInstallQuery=proptaxInstallQuery.replace("proptaxInstallDateFrom", proptaxinstalldatefrom);
				prepareParamMap.put(++mapi+"_"+"eq",ChangeDateFrom);
				sArray.add(InstallQuery);
			}
			
			if (null!=ChangeDateTo && !ChangeDateTo.equals("")) {
				String InstallQuery=" to_date(AT_POSTING_DATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY')  ";
				
				//proptaxInstallQuery=proptaxInstallQuery.replace("proptaxInstallDateTo", proptaxinstalldateto);
				prepareParamMap.put(++mapi+"_"+"eq",ChangeDateTo);
				sArray.add(InstallQuery);
			}
			//ProptaxcreateDateFrom
//			if (null!=proptaxcreatedatefrom && !proptaxcreatedatefrom.equals("")) {
//				String proptaxInstallQuery=" to_date(PROP_ENTERED_DATE,'MM/DD/YYYY')>=to_date(?,'MM/DD/YYYY') ";
//				
//				
//				//proptaxInstallQuery=proptaxInstallQuery.replace("proptaxCreateDateFrom", proptaxcreatedatefrom);
//				prepareParamMap.put(++mapi+"_"+"eq",proptaxcreatedatefrom);
//				sArray.add(proptaxInstallQuery);
//			}
//			//proptaxCreateDateTo
//			if (null!=proptaxcreatedateto && !proptaxcreatedateto.equals("")) {
//				String proptaxInstallQuery=" to_date(PROP_ENTERED_DATE,'MM/DD/YYYY')<=to_date(?,'MM/DD/YYYY') ";
//				
//				//proptaxInstallQuery=proptaxInstallQuery.replace("proptaxCreateDateTo", proptaxcreatedateto);
//				prepareParamMap.put(++mapi+"_"+"eq",proptaxcreatedateto);
//				sArray.add(proptaxInstallQuery);
//			}
			
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
//				String sql = "SELECT count(*) FROM "+ table +  " WHERE REGEXP_LIKE (PROP_INSTALL_DATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$') and ROWNUM <= 200";
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
					sql=sql;//+" and ROWNUM <= 200 ";
//							+ "and REGEXP_LIKE (PROP_INSTALL_DATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
				}else {
					sql=sql;//+" and ROWNUM <= 200 ";
//							+ "and REGEXP_LIKE (PROP_INSTALL_DATE,  '^[0-9]{2}/[0-9]{2}/[0-9]{4}$')";
				}
				
				//sql += " order by " + colName + " " + dir;
				sql += "order by AT_ASSET asc";
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
				
				//ps.setFetchSize(200);
				ResultSet rs = ps.executeQuery();
				List<List<String>> sortedSheet = sortedSheet(rs);
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
		
		public List<String[]> getAssetTransferProd2CompanyGroups() {
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
		public List<String[]> getAssetTransferProd2Instances() {
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
		public List<String[]> getAssetTransferProd2Years() {
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
		
		public List<String[]> getAssetTransferProd2CompanyNames(String COMPANYGROUP) {
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
					arr[1] =StringEscapeUtils.escapeHtml4( rs.getString("COMPANYNAME"));
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
			//System.out.println("this is simpl== "+tName.trim());
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
			String query = "select * from report_index_table where layout='ASTTR2' and report_id='PRD-ASTTR2' and version='Prod'";
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
			//	System.out.println("Prop tax reg table name=="+reportTableName);
				stm.setString(1, reportTableName);
			    stm.registerOutParameter(2,Types.VARCHAR);
			    stm.execute();
			    m_count = stm.getString(2);
			 //   System.out.println("Prop tax reg Store procedure column=="+m_count.toString());
			    stm.close();
				conn1.close();
				
				
			}
			catch(Exception e){
				System.out.println(e);
			}
			return m_count;
			
		}

	}


