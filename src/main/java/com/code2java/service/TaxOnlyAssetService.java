package com.code2java.service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.code2java.dao.DataTableDAO;

@Service
public class TaxOnlyAssetService {
	
	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(TaxOnlyAssetService.class);
	
	@Autowired
	private DataTableDAO dataTableDao;

	
	public String updateAndInsertRecords(CommonsMultipartFile file, HttpServletRequest request)
	{
		List<Object[]> rows=fileToObjArray(file);
		int totalRecords=rows.size();
		System.out.println("reached here"+totalRecords);
		String columnNames="select column_name from all_tab_columns where table_name='TAX_ONLY_ASSETS' order by column_id";
		Connection con=dataTableDao.getConnection();
		PreparedStatement ps;
		List<String> columns=new ArrayList<>();
		String masterQuery=" ";
		try {
			ps = con.prepareStatement(columnNames);
			ResultSet rs= ps.executeQuery();
			while(rs.next())
			{
				columns.add(rs.getString("COLUMN_NAME"));
			}
			masterQuery=buildInsertUpdateQuery(columns,"TAX_ONLY_ASSETS",request);
			
		
		System.out.println(masterQuery);
		int recordsUpdated=saveBatchRecords(totalRecords,masterQuery,rows);
		System.out.println("records updated: "+recordsUpdated);
		return "records updated: "+recordsUpdated;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(e);
		}
		return "records updation failed";
	}
	public String buildInsertUpdateQuery(List<String> columns,String tableName,HttpServletRequest request)
	{
		
		StringBuilder query=new StringBuilder("merge into ");
		query.append(tableName).append(" target using").append("( select ");
		StringJoiner columnNames=new StringJoiner(", ");
		StringJoiner columnNamesB=new StringJoiner(", ");
		StringJoiner placeHolders=new StringJoiner(", ");
		StringJoiner sourceTargetEquator=new StringJoiner(", ");
		StringJoiner sourceEquator= new StringJoiner(",");
		List<String> excludeCols=new ArrayList<>();
		excludeCols.add("TOA_LASTUPDATEDBY");
		excludeCols.add("TOA_LASTUPDATESDATE");
		excludeCols.add("TOA_STATUS");
//		excludeCols.add("TOA_CREATIONDATE");
//		excludeCols.add("TOA_CREATIONTIME");
//		excludeCols.add("TOA_CREATIONUSER");
		try
		{
//			HttpSession sessionData= request.getSession();
//			String val=sessionData.getAttribute("FirstName").toString();
			String val="";
			
			logger.info(val);
		
		
		
		for(String column: columns)
		{
			if(excludeCols.contains(column)) {
				continue;
			}
			
			placeHolders.add("? "+"as "+column);
			columnNames.add(column);
			
			
		}
		List<String> excludeColsB=new ArrayList<>();
		excludeColsB.add("TOA_LASTUPDATEDBY");
		excludeColsB.add("TOA_LASTUPDATESDATE");
		excludeColsB.add("TOA_STATUS");
//		excludeColsB.add("TOA_CREATIONDATE");
//		excludeColsB.add("TOA_CREATIONTIME");
//		excludeColsB.add("TOA_CREATIONUSER");
		for(String column: columns)
		{	
			if(excludeColsB.contains(column)) {
				continue;
			}
			sourceEquator.add("source."+column);
			
		}
		for(String column: columns)
		{		
			columnNamesB.add(column);
			
		}
//		for(int i=1;i<columns.size()-5;i++)
//		{
//			sourceTargetEquator.add("target."+columns.get(i)+" ="+" source."+columns.get(i));
//		}
		for(String column: columns)
		{
			if(column.equals("TOA_UNIQUEASSET"))
			{
				continue;
			}
//			if(column.equals("TOA_CREATIONDATE"))
//			{
//				sourceTargetEquator.add("target."+column+" ="+"null ");
//				continue;
//			}
//			if(column.equals("TOA_CREATIONTIME"))
//			{
//				sourceTargetEquator.add("target."+column+" ="+"null ");
//				continue;
//			}
//			if(column.equals("TOA_CREATIONUSER"))
//			{
//				sourceTargetEquator.add("target."+column+" ="+"null");
//				continue;
//			}
			if(column.equals("TOA_LASTUPDATEDBY"))
			{
				sourceTargetEquator.add("target."+column+" ='"+val+"'");
				continue;
			}
			if(column.equals("TOA_LASTUPDATESDATE"))
			{
				sourceTargetEquator.add("target."+column+" =(SELECT SYSDATE FROM dual)");
				continue;
			}
			if(column.equals("TOA_STATUS"))
			{
				sourceTargetEquator.add("target."+column+" ="+"'active'");
				continue;
			}
			sourceTargetEquator.add("target."+column+" ="+" source."+column);
		}
		
		
		query.append(placeHolders.toString());
		query.append(" from dual ) source on (");
		query.append("target.TOA_UNIQUEASSET=source.TOA_UNIQUEASSET) when matched then update set ");
		query.append(sourceTargetEquator.toString());
		query.append(" when not matched then insert (");
		query.append(columnNamesB.toString());
		query.append(") VALUES (");
		query.append(sourceEquator.toString());
		query.append(",'"+val+"',(SELECT SYSDATE FROM dual),'active'");
		query.append(")");
		
		
		return query.toString();
		}catch(Exception e)
		{
			logger.info(e);
		}
		return "error";
	}
	
	public List<Object[]> fileToObjArray(CommonsMultipartFile file)
	{
		try {
			
			XSSFWorkbook wb= new XSSFWorkbook(file.getInputStream());
			FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
			XSSFSheet sheet=wb.getSheetAt(0);
			
			List<Object[]> objectList = new ArrayList<>();
			Row header=sheet.getRow(0);
			int lastCellNum = header.getLastCellNum();
			for(Row row:sheet)
			{
				
				if(row.getRowNum()==0)continue;
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
				
			}
			return objectList;
		} catch (IOException e) {
			
			logger.info(e);
			
		}
		return new ArrayList<Object[]>();
		
	}
	
	
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
	
	
	public String deleteAndUpdateRecords(CommonsMultipartFile file)
	{
		List<Object[]> rows=fileToObjArray(file);
		List<Object[]> uniqueAssetNo=new ArrayList<>();
		for(Object[] row:rows)
		{
			uniqueAssetNo.add(new Object[] {row[0]});
		}
		int totalRecords=rows.size();
		
		
//		String columnNames="select column_name from all_tab_columns where table_name='TAX_ONLY_ASSETS_TEST' order by column_id";
//		Connection con=dataTableDao.getConnection();
//		PreparedStatement ps;
//		List<String> columns=new ArrayList<>();
		String masterQuery="update tax_only_assets set toa_status='inActive' where toa_uniqueasset=?";
//		try {
//			ps = con.prepareStatement(columnNames);
//			ResultSet rs= ps.executeQuery();
//			while(rs.next())
//			{
//				columns.add(rs.getString("COLUMN_NAME"));
//			}
//			masterQuery=buildInsertUpdateQuery(columns,"TAX_ONLY_ASSETS_TEST");
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			logger.info(e);
//		}
		System.out.println(masterQuery);
		int recordsUpdated=saveBatchRecords(totalRecords,masterQuery,uniqueAssetNo);
		System.out.println("records updated: "+recordsUpdated);
		return "records deleted: "+recordsUpdated;
		
		
	}
}
