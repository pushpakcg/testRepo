package com.code2java.service;

import java.security.PKCS12Attribute;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.code2java.controllers.HistFileUploadController;
import com.code2java.dao.DataTableDAO;
import com.code2java.model.FMVModel;

@Component
public class FMVServiceImpl implements FMVService {

	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(FMVServiceImpl.class);
	
	@Autowired
	private DataTableDAO dataTableDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<String> getYears() {
		
		List<String> yearList = new ArrayList<String>();
		Connection con;
		PreparedStatement ps;
		try
		{
			String query="select distinct(FM_YEAR) from OC_TPP_TAX_LIFE order by fm_year asc";
			con=dataTableDao.getConnection();
			ps=con.prepareStatement(query);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				String value=rs.getString("FM_YEAR");
				yearList.add(value);
			}
			ps.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return yearList;
	}

	@Override
	public FMVModel getModelValues(String Year) {
		
		List<String[]> modelList=new ArrayList<>();
		Connection con;
		PreparedStatement ps;
		try
		{
			String query="select FM_category, FM_value from OC_TPP_TAX_LIFE where fm_year=? order by fm_category desc";
			con=dataTableDao.getConnection();
			ps=con.prepareStatement(query);
			ps.setString(1, Year);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				String[] s1=new String[2];
				 s1[0]=rs.getString("FM_category");
				s1[1]=rs.getString("FM_value");
				modelList.add(s1);
			}
			ps.close();
			con.close();
		}
		catch(Exception e)
		{
			logger.info(e);
			e.printStackTrace();
		}
		FMVModel fmvModel=new FMVModel();
		String[] c6=modelList.get(0);
		String[] computers=modelList.get(1);
		String[] nine=modelList.get(2);
		String[] eight=modelList.get(3);
		String[] seven=modelList.get(4);
		String[] six=modelList.get(5);
		String[] five=modelList.get(6);
		String[] twenty=modelList.get(7);
		String[] fifteen=modelList.get(8);
		String[] twelve=modelList.get(9);
		String[] eleven=modelList.get(10);
		String[] ten=modelList.get(11);
		fmvModel.setUntrended6year(c6[1]);
		fmvModel.setComputers(computers[1]);
		fmvModel.setYear9(nine[1]);
		fmvModel.setYear8(eight[1]);
		fmvModel.setYear7(seven[1]);
		fmvModel.setYear6(six[1]);
		fmvModel.setYear5(five[1]);
		fmvModel.setYear20(twenty[1]);
		fmvModel.setYear15(fifteen[1]);
		fmvModel.setYear12(twelve[1]);
		fmvModel.setYear11(eleven[1]);
		fmvModel.setYear10(ten[1]);
		fmvModel.setModelyear(Year);
		System.out.println(fmvModel);
		return fmvModel;
	}

	@Transactional
	@Override
	public String updateModel(FMVModel fmvModel) {
		// TODO Auto-generated method stub
		System.out.println("update"+fmvModel);
		FMVModel archiveModel=getModelValues(fmvModel.getModelyear());
		String archiveStatus=archiveRecords(archiveModel);
		try {
		int totalUpdatedRows=0;
		Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
		totalUpdatedRows+=jdbcTemplate.update("update OC_TPP_TAX_LIFE set fm_value=? , fm_creationdate=? where fm_year=? and fm_category=?", fmvModel.getYear5(),timestamp,fmvModel.getModelyear(),"5");
		totalUpdatedRows+=jdbcTemplate.update("update OC_TPP_TAX_LIFE set fm_value=? , fm_creationdate=? where fm_year=? and fm_category=?", fmvModel.getYear6(),timestamp,fmvModel.getModelyear(),"6");	
		totalUpdatedRows+=jdbcTemplate.update("update OC_TPP_TAX_LIFE set fm_value=? , fm_creationdate=? where fm_year=? and fm_category=?", fmvModel.getYear7(),timestamp,fmvModel.getModelyear(),"7");
		totalUpdatedRows+=jdbcTemplate.update("update OC_TPP_TAX_LIFE set fm_value=? , fm_creationdate=? where fm_year=? and fm_category=?", fmvModel.getYear8(),timestamp,fmvModel.getModelyear(),"8");	
		totalUpdatedRows+=jdbcTemplate.update("update OC_TPP_TAX_LIFE set fm_value=? , fm_creationdate=? where fm_year=? and fm_category=?", fmvModel.getYear9(),timestamp,fmvModel.getModelyear(),"9");
		totalUpdatedRows+=jdbcTemplate.update("update OC_TPP_TAX_LIFE set fm_value=? , fm_creationdate=? where fm_year=? and fm_category=?", fmvModel.getYear10(),timestamp,fmvModel.getModelyear(),"10");	
		totalUpdatedRows+=jdbcTemplate.update("update OC_TPP_TAX_LIFE set fm_value=? , fm_creationdate=? where fm_year=? and fm_category=?", fmvModel.getYear11(),timestamp,fmvModel.getModelyear(),"11");
		totalUpdatedRows+=jdbcTemplate.update("update OC_TPP_TAX_LIFE set fm_value=? , fm_creationdate=? where fm_year=? and fm_category=?", fmvModel.getYear12(),timestamp,fmvModel.getModelyear(),"12");	
		totalUpdatedRows+=jdbcTemplate.update("update OC_TPP_TAX_LIFE set fm_value=? , fm_creationdate=? where fm_year=? and fm_category=?", fmvModel.getYear15(),timestamp,fmvModel.getModelyear(),"15");
		totalUpdatedRows+=jdbcTemplate.update("update OC_TPP_TAX_LIFE set fm_value=? , fm_creationdate=? where fm_year=? and fm_category=?", fmvModel.getYear20(),timestamp,fmvModel.getModelyear(),"20");	
		totalUpdatedRows+=jdbcTemplate.update("update OC_TPP_TAX_LIFE set fm_value=? , fm_creationdate=? where fm_year=? and fm_category=?", fmvModel.getComputers(),timestamp,fmvModel.getModelyear(),"COMP");
		totalUpdatedRows+=jdbcTemplate.update("update OC_TPP_TAX_LIFE set fm_value=? , fm_creationdate=? where fm_year=? and fm_category=?", fmvModel.getUntrended6year(),timestamp,fmvModel.getModelyear(),"U6");	
		
		return totalUpdatedRows+" records updated successfully"+" "+archiveStatus;
		}
		catch(Exception e)
		{
			logger.info(e);
			return " encountered error in updating record! Please raise a request to FAIT";
		}
		
	}

	public String archiveRecords(FMVModel fm) {
		
		int rowStatus=jdbcTemplate.update("insert into OC_TPP_ARCHIVE_TABLE values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",fm.getModelyear(),fm.getYear5(),fm.getYear6(),fm.getYear7(),fm.getYear8(),fm.getYear9(),fm.getYear10(),fm.getYear11(),fm.getYear12(),fm.getYear15(),fm.getYear20(),fm.getComputers(),fm.getUntrended6year(),new Timestamp(new java.util.Date().getTime()));
		return rowStatus+" row archived";
	}

	@Transactional
	@Override
	public String insertModel(FMVModel fmvModel) {
		
		System.out.println("insert"+fmvModel);
		
		try {
		int totalUpdatedRows=0;
		 Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
		totalUpdatedRows+=jdbcTemplate.update("insert into OC_TPP_TAX_LIFE values (?,?,?,?)", fmvModel.getModelyear(),"5", fmvModel.getYear5(),timestamp);
		totalUpdatedRows+=jdbcTemplate.update("insert into OC_TPP_TAX_LIFE values (?,?,?,?)", fmvModel.getModelyear(),"6",fmvModel.getYear6(),timestamp);	
		totalUpdatedRows+=jdbcTemplate.update("insert into OC_TPP_TAX_LIFE values (?,?,?,?)", fmvModel.getModelyear(),"7",fmvModel.getYear7(),timestamp);
		totalUpdatedRows+=jdbcTemplate.update("insert into OC_TPP_TAX_LIFE values (?,?,?,?)", fmvModel.getModelyear(),"8",fmvModel.getYear8(),timestamp);	
		totalUpdatedRows+=jdbcTemplate.update("insert into OC_TPP_TAX_LIFE values (?,?,?,?)", fmvModel.getModelyear(),"9",fmvModel.getYear9(),timestamp);
		totalUpdatedRows+=jdbcTemplate.update("insert into OC_TPP_TAX_LIFE values (?,?,?,?)", fmvModel.getModelyear(),"10",fmvModel.getYear10(),timestamp);	
		totalUpdatedRows+=jdbcTemplate.update("insert into OC_TPP_TAX_LIFE values (?,?,?,?)", fmvModel.getModelyear(),"11",fmvModel.getYear11(),timestamp);
		totalUpdatedRows+=jdbcTemplate.update("insert into OC_TPP_TAX_LIFE values (?,?,?,?)", fmvModel.getModelyear(),"12",fmvModel.getYear12(),timestamp);	
		totalUpdatedRows+=jdbcTemplate.update("insert into OC_TPP_TAX_LIFE values (?,?,?,?)", fmvModel.getModelyear(),"15",fmvModel.getYear15(),timestamp);
		totalUpdatedRows+=jdbcTemplate.update("insert into OC_TPP_TAX_LIFE values (?,?,?,?)", fmvModel.getModelyear(),"20",fmvModel.getYear20(),timestamp);	
		totalUpdatedRows+=jdbcTemplate.update("insert into OC_TPP_TAX_LIFE values (?,?,?,?)", fmvModel.getModelyear(),"COMP",fmvModel.getComputers(),timestamp);
		totalUpdatedRows+=jdbcTemplate.update("insert into OC_TPP_TAX_LIFE values (?,?,?,?)", fmvModel.getModelyear(),"U6",fmvModel.getUntrended6year(),timestamp);	
		
		return totalUpdatedRows+" records inserted successfully";
		}
		catch(Exception e)
		{
			logger.info(e);
			return " encountered error in inserting record! Please raise a request to FAIT";
		}
	}

	@Override
	public SXSSFWorkbook excelExport() {
		
		Connection con;
		PreparedStatement ps;
		Map<String,List<String[]>> dataMap=new LinkedHashMap<>();
		
		try
		{
			String tabData="select * from oc_tpp_tax_life order by fm_year,LPAD(fm_category, 10)";
			con=dataTableDao.getConnection();
			ps=con.prepareStatement(tabData);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				String year=rs.getString("fm_year");
				
				if(dataMap.containsKey(year))
				{
					String[] sArr=new String[2];
					sArr[0]=rs.getString("fm_category");
					sArr[1]=rs.getString("fm_value");
					List<String[]> local=dataMap.get(year);
					local.add(sArr);
					dataMap.put(year, local);
				}
				else
				{
					List<String[]> catVal=new ArrayList<>();
					String[] sArr=new String[2];
					sArr[0]=rs.getString("fm_category");
					sArr[1]=rs.getString("fm_value");
					catVal.add(sArr);
					dataMap.put(year, catVal);
					
				}
			}
			
			ps.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		SXSSFWorkbook workbook=new SXSSFWorkbook();
		Sheet sheet=workbook.createSheet("OC_Tax_Rates");
		Row header=sheet.createRow(0);
		CellStyle createCellStyle = workbook.createCellStyle();
		Font headerFont =workbook.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		createCellStyle.setFont(headerFont);
		Cell hCell=header.createCell(0);
		hCell.setCellValue("Year");
		hCell.setCellStyle(createCellStyle);
		Map.Entry<String, List<String[]>> headerData=dataMap.entrySet().iterator().next();
		int cell=1;
		for(String[] headerValue:headerData.getValue())
		{
			Cell rCell=header.createCell(cell++);
			rCell.setCellValue(headerValue[0]);
			rCell.setCellStyle(createCellStyle);
		}
		int rowIndex=1;
		Row rowData=null;
		for(Map.Entry<String, List<String[]>> cellData:dataMap.entrySet())
		{
			
			int dataCellIndex=1;
			rowData=sheet.createRow(rowIndex++);
			rowData.createCell(0).setCellValue(cellData.getKey());
			for(String[] indiVal:cellData.getValue())
			{
				rowData.createCell(dataCellIndex++).setCellValue(indiVal[1]);
			}
		}
		
		return workbook;
		
		
	}

	
}
