package com.code2java.service;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.code2java.dao.DataTableDAO;

@Component
public class AssetTransferServiceImpl implements AssetTransferService {

	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(AssetTransferServiceImpl.class);
	
	@Autowired
	private DataTableDAO dataTableDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<String[]> getTableNameAndCount() {

		List<String[]> assetTransfer=new ArrayList<>();
		Connection con;
		PreparedStatement ps;
		try
		{
			String query="select TABLE_NAME, COLUMNS from ASSET_TRANSFER";
			con=dataTableDao.getConnection();
			ps=con.prepareStatement(query);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				String[] s1=new String[2];
				 s1[0]=rs.getString("TABLE_NAME");
				s1[1]=rs.getString("COLUMNS");
				assetTransfer.add(s1);
			}
						
			ps.close();
			con.close();
			
		}
		catch(Exception e)
		{
			logger.info(e);
			e.printStackTrace();
		}
		return assetTransfer;
	}

	@Override
	public int saveBatchRecords(int totalrecords, String sql, List<Object[]> objectList) {
		int count = 0;
		for (int i = 0; i < totalrecords; i = i + 10000) {
			int lastindex = totalrecords - i > 10000 ? i + 10000 : totalrecords;
			System.out.println("update batch from : " + i + " - " + (lastindex));
			int[] updated =dataTableDao.getJdbcTemplate().batchUpdate(sql, objectList.subList(i, lastindex));
			count = count + updated.length;
		}
		return count;
	}

	
	
	
	@Override
	public String uploadFile(CommonsMultipartFile file, Model model, String assetVal, String assetTxt)  {
		// TODO Auto-generated method stub
		Map<String[],String[]> joinedTableDetails=new LinkedHashMap<String[],String[]>();
		joinedTableDetails.put(new String[]{"INTERIM_ASSET_TRANSFER_PL","PHYSICAL_LOCATION_PL"},new String[]{"ASSET_TRANSFER_REPORT_PHYSICAL_LOCATION",Queries.query8});
		joinedTableDetails.put(new String[]{"INTERIM_ASSET_TRANSFER_ALL_TXN","PHYSICAL_LOCATION_AT"},new String[]{"ASSET_TRANSFER_ALL_TRANSACTION_REPORT",Queries.query9});
		String tableToBeDropped="";
		String queryToUse="";
		 
		Connection conn=null;
		try {
			conn = dataTableDao.getConnection();
		conn.setAutoCommit(false);
		outerLoop:{
		for(String[] key: joinedTableDetails.keySet())
		{
			for(String k:key)
			{
				
				if(k.equals(assetTxt))
				{
					
					
						
						if(k.contains("PHYSICAL_LOCATION"))
						{
							k="PHYSICAL_LOCATION";
							assetTxt="PHYSICAL_LOCATION";
						}
						String dynQ="delete from "+k.replaceAll("[^a-zA-Z0-9_]", "");
						PreparedStatement q1;
					 q1=conn.prepareStatement(dynQ);
					 logger.info("joinedTableName"+joinedTableDetails.get(key));
					String[] val=joinedTableDetails.get(key);
					tableToBeDropped=val[0];
					queryToUse=val[1];
					 q1.executeQuery();
					 String dynQ2="delete from "+tableToBeDropped.replaceAll("[^a-zA-Z0-9_]", "");
					 q1=conn.prepareStatement(dynQ2);
					 q1.executeQuery();
					 logger.info("reached here file deleted"); 
					 q1.close();
					 
						break outerLoop;
			
				}
			}
		}
		}
		File f1 = new File(file.getOriginalFilename());
		logger.info("uploaded filename"+file.getOriginalFilename());
		String statusMsg="";
		
		
		
	
			XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
		
			FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
			XSSFSheet sheet = wb.getSheetAt(0);
			List<Object[]> objectList = new ArrayList<>();
			Row header=sheet.getRow(0);
			
			
			//if table has id then set asset val as column count
			// else set table count +1 in db
			int assetValInt=Integer.parseInt(assetVal);
			logger.info("from db val"+assetValInt);
			int lastCellNum = header.getLastCellNum();
			logger.info("from exl val"+lastCellNum);
			if(lastCellNum==assetValInt-1)
			{
				logger.info("inside if");
				int counter=0;
			int buffer=0;
			int updated=0;
				for (Row row : sheet)
					
					{
					
					
					if(row.getRowNum()==0)continue;
					buffer++;
					counter++;
					
					List<Object> obj = new ArrayList<Object>();
					for (int cn=0; cn<lastCellNum; cn++) {
						
							Cell cell = row.getCell(cn);
							if (cell == null) {
								obj.add(" ");
							}else {
								switch (formulaEvaluator.evaluateInCell(cell).getCellType()) {
								case Cell.CELL_TYPE_NUMERIC:
									if(DateUtil.isCellInternalDateFormatted(cell))
									{
										SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/YYYY");
										//String formattedDate="to_date("+dateFormat.format(cell.getDateCellValue())+"),'MM/DD/YYYY')";
										String formattedDate=dateFormat.format(cell.getDateCellValue());
										
										obj.add(formattedDate);
									}
									else
									{
									obj.add(cell.getNumericCellValue());
									}
									break;
								case Cell.CELL_TYPE_STRING:
									obj.add(cell.getStringCellValue());
									break;
								
									
								}
							}

						}
					objectList.add(obj.toArray());
					if(buffer%10000==0)
					{
						
						String[] saveSql={"INSERT into FAWEB.dynamicTable (itc_cols) VALUES (paramMarks)","for duplicate validation"};
						String sQuery1=saveSql[0].replace("dynamicTable", assetTxt.replaceAll("[^a-zA-Z0-9_]",""));
						String sQuery2=sQuery1.replace("itc_cols", getColsNames(assetTxt).replaceAll("[^a-zA-Z0-9_,\"]", ""));
						
						StringBuilder sb= new StringBuilder();
						
						for(int i=0;i<assetValInt-1;i++)
						{
							sb.append("?,");
						}
						if(sb.length()>0)
						{
							sb.deleteCharAt(sb.length()-1);
						}
						String sQuery=sQuery2.replace("paramMarks", sb.toString().replaceAll("[^?,]", ""));
						 updated+= saveBatchRecords(10000, sQuery, objectList);
						logger.info("insert query to upload file" + sQuery);
						 objectList.clear();
						 obj=null;
						counter=0;
						
					}
					}
			
				
				//objectList.add(obj.toArray());
				String[] saveSql={"INSERT into FAWEB.dynamicTable (itc_cols) VALUES (paramMarks)","for duplicate validation"};
				String sQuery1=saveSql[0].replace("dynamicTable", assetTxt.replaceAll("[^a-zA-Z0-9_]",""));
				String sQuery2=sQuery1.replace("itc_cols", getColsNames(assetTxt).replaceAll("[^a-zA-Z0-9_,\"]", ""));
				
				StringBuilder sb= new StringBuilder();
				
				for(int i=0;i<assetValInt-1;i++)
				{
					sb.append("?,");
				}
				if(sb.length()>0)
				{
					sb.deleteCharAt(sb.length()-1);
				}
				String sQuery=sQuery2.replace("paramMarks", sb.toString().replaceAll("[^?,]", ""));
				logger.info("insert query to upload file" + sQuery);
				 updated+= saveBatchRecords(counter, sQuery, objectList);
				
				objectList.clear();
							
				logger.info(queryToUse);
				
				PreparedStatement ps2=conn.prepareStatement(queryToUse);
					
					 int executeUpdate = ps2.executeUpdate();
					logger.info("value "+executeUpdate);
					
		
				
				if(updated>0) {
					statusMsg=statusMsg+"Excel data uploaded successfully , new records added - "+updated;
				}
				
					// Removing header row
					
					
					// check if the last record has all null values
					int lastrecord = objectList.size() - 1;
										
			}
			else
			{
				statusMsg=statusMsg+" File Upload failed, check column format and number of columns";
				conn.rollback();
			}
			
			

			conn.commit();
		return statusMsg;
		}
		catch(Exception e)
		{
			logger.debug(e);
			try {
			if(conn!=null)
			{
				conn.rollback();
			}
			}
			catch(Exception ec)
			{
				logger.info(ec);
			}
			return "error occured";
		}
		finally
		{
			try {
		        if (conn != null) {
		            conn.close();
		        }
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }
		}
		
	}
	
	public String getColsNames(String tablename) {
		
		
		//System.out.println("Inc projection headings simpl== "+tName.trim());
		
			Connection conn;
			PreparedStatement ps;
			String colStr="";
			try {
			String query="select COLUMN_NAME from SYS.all_tab_columns where table_name = ?  ORDER BY COLUMN_ID";
			conn = dataTableDao.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, tablename.trim());
			ResultSet rs = ps.executeQuery();
			StringBuilder sb1=new StringBuilder();
	
			while (rs.next()) {
				sb1.append("\"");
			sb1.append(rs.getString("COLUMN_NAME"));
			sb1.append("\"");
			sb1.append(",");
			}
			
			if(sb1.length()>0)
			{
				
				sb1.deleteCharAt(sb1.length()-1);
			}
			if(sb1.toString().contains("id"))
			{
				 colStr=sb1.toString().replace("\"id\",","");
			}
			else colStr=sb1.toString();
			
			conn.close();
			ps.close();
			} catch (Exception e) {
			logger.info(e);
			}
			logger.info(colStr);
			return colStr;
	}

}
