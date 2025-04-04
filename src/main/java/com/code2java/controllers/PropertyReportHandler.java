package com.code2java.controllers;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class PropertyReportHandler {
	
	//public Workbook writeFile(List<List<String>> resultDataList) {
		public SXSSFWorkbook writeFile(List<List<String>> resultDataList) {
		System.out.println("Chunk Size: " + resultDataList.size());
		//Workbook workbook = null;
		SXSSFWorkbook workbook = null;
		Sheet reportSheet = null;
		workbook = new SXSSFWorkbook(100);
		reportSheet = workbook.createSheet("Reports");

		/**
		 * Create rows using Row class, where headerRow denotes the header
		 * and the dataRow denotes the cell data.
		 */
		Row header = reportSheet.createRow(0);
		header.setHeight((short) 500);
		Row dataRow = null;
		
		CellStyle headerCellStyle = setHeaderStyle(workbook);
		CellStyle bodyCellStyle = setBodyStyle(workbook);
		CellStyle styleDateFormat = setBodyStyle(workbook);

		CreationHelper createHelper = workbook.getCreationHelper();
		styleDateFormat.setDataFormat(createHelper.createDataFormat().getFormat("MM/dd/yyyy"));

		CellStyle styleCurrencyFormat = setBodyStyle(workbook);
	    styleCurrencyFormat.setDataFormat(createHelper.createDataFormat().getFormat("$#,##"));
	    styleCurrencyFormat.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
	    
		CellStyle styleNumericFormat = setBodyStyle(workbook);
		styleNumericFormat.setDataFormat(createHelper.createDataFormat().getFormat("##"));
		styleNumericFormat.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		
		CellStyle styleFloatFormat = setBodyStyle(workbook);
		styleFloatFormat.setDataFormat(createHelper.createDataFormat().getFormat("##.00"));
		styleFloatFormat.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		
		CellStyle styleCommaFormat = setBodyStyle(workbook);
		styleCommaFormat.setDataFormat(createHelper.createDataFormat().getFormat("#,##"));
		styleCommaFormat.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		
		CellStyle styleRightAlignFormat = setBodyStyle(workbook);
		styleRightAlignFormat.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		
		CellStyle styleCenterAlignFormat = setBodyStyle(workbook);
		styleCenterAlignFormat.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		CellStyle styleNormalFormat = setBodyStyle(workbook);
		
		
		int headerCellCnt = 0;
		/**
		 * Call the setHeaderStyle method and set the styles for the all the
		 * header cells.
		 */
			
		/*
		 * List<ResultField> resultFieldsList = report.getResultFieldList();
		 * 
		 * for (Iterator<ResultField> iterator = resultFieldsList.iterator(); iterator
		 * .hasNext();) { ResultField resultField = (ResultField) iterator.next(); Cell
		 * headerCell_headerCellCnt = headerRow.createCell(headerCellCnt);
		 * headerCell_headerCellCnt.setCellStyle(headerCellStyle);
		 * headerCell_headerCellCnt.setCellValue(new
		 * XSSFRichTextString(resultField.getFieldName()));
		 * reportSheet.setColumnWidth(headerCellCnt, 8000); headerCellCnt++; }
		 */
		//XSSFRow header = sheet.createRow(0);
		
		header.createCell(0).setCellValue("Entity");
		header.createCell(1).setCellValue("Unique Asset Number");
		header.createCell(2).setCellValue("Asset Number");
		header.createCell(3).setCellValue("Asset Sub Number");
		header.createCell(4).setCellValue("Internal Asset Number");		
		header.createCell(5).setCellValue("Legacy Asset Number");
		header.createCell(6).setCellValue("Legacy Asset Sub Number");
		
		header.createCell(7).setCellValue("Asset Description");
		header.createCell(8).setCellValue("Country");
		header.createCell(9).setCellValue("State");
		header.createCell(10).setCellValue("County");
		header.createCell(11).setCellValue("City");
		header.createCell(12).setCellValue("Street");
		header.createCell(13).setCellValue("ZIP Code");
		header.createCell(14).setCellValue("Building Number");
		header.createCell(15).setCellValue("Physical Location Desc");
		header.createCell(16).setCellValue("Entered Date");
		header.createCell(17).setCellValue("Entered Year");
		header.createCell(18).setCellValue("Asset Class");
		header.createCell(19).setCellValue("Asset Class Description");
		header.createCell(20).setCellValue("GL Category Desc");
		header.createCell(21).setCellValue("Corporate Life");
		header.createCell(22).setCellValue("Install Date");
		header.createCell(23).setCellValue("Install Year");
		header.createCell(24).setCellValue("Corp Book Cost Basis");
		header.createCell(25).setCellValue("Corp Book Accumulated Depr");
		header.createCell(26).setCellValue("Corp Book NBV");
		//header.createCell(25).setCellValue("PROP_LAST_YR_DEPRECIATED");
		//header.createCell(26).setCellValue("PROP_LAST_MTH_DEPRECIATED");
		header.createCell(27).setCellValue("Accounting Year");
		header.createCell(28).setCellValue("Tax Install Date");
		header.createCell(29).setCellValue("Tax Cost Basis");
		header.createCell(30).setCellValue("Tax NTV");
		header.createCell(31).setCellValue("Park Asset_Property Tax");
		//header.createCell(30).setCellValue("PROP_ACCOUNTING_PERIOD");
		header.createCell(32).setCellValue("Invoice Number");
		header.createCell(33).setCellValue("Project Number");
		header.createCell(34).setCellValue("Purchase Order");
		header.createCell(35).setCellValue("WBSE");
		header.createCell(36).setCellValue("Profit Center");
		header.createCell(37).setCellValue("Cost Center");
		header.createCell(38).setCellValue("Manufacture");
		header.createCell(39).setCellValue("Vendor");
		header.createCell(40).setCellValue("Attraction ID");
		header.createCell(41).setCellValue("Geo Location");
		header.createCell(42).setCellValue("Department");
		header.createCell(43).setCellValue("Company Name");	
		header.createCell(44).setCellValue("Serial number");	
		header.createCell(45).setCellValue("Inventory number");	
		header.createCell(46).setCellValue("Quantity");	
		//header.createCell(47).setCellValue("Physical Location C/O Name");
		header.createCell(47).setCellValue("Original Asset Number");
		header.createCell(48).setCellValue("Type name");	
		header.createCell(49).setCellValue("Legacy Project No");	
		header.createCell(50).setCellValue("Tracking Number");
		
		/*
		 * header.createCell(43).setCellValue("CORPLASTMONTHDEPRECIATED");
		 * header.createCell(44).setCellValue("CORPNBV");
		 * header.createCell(45).setCellValue("CORPFULLYRETIRED");
		 * header.createCell(46).setCellValue("PROJECT");
		 * header.createCell(47).setCellValue("CATEGORY");
		 * header.createCell(48).setCellValue("CATEGORYDESC");
		 * header.createCell(49).setCellValue("TAXINSTALLATIONYR");
		 * header.createCell(50).setCellValue("TAXCURRACCYEAR");
		 */
		
		int rowNum = 1;
		for (List<String> list : resultDataList) {

			int cellNum = 0;
			// create the row data
			Row row = reportSheet.createRow(rowNum++);
			for (String entry : list) {
				// if(cellNum==14 && StringUtils.isNotBlank(entry)) {
				if (StringUtils.isNotBlank(entry)
						&& (cellNum == 24 || cellNum == 25 || cellNum == 26 || cellNum == 29)) {
					row.createCell(cellNum++).setCellValue(Double.parseDouble(entry));
				} else {
					row.createCell(cellNum++).setCellValue(entry);
				}
				// row.createCell(cellNum++).setCellValue(entry);

			}
		}
		/*
		 * for (Iterator<List<String>> iterator = resultDataList.iterator(); iterator
		 * .hasNext();) { List<String> rowDataList = (List<String>) iterator.next();
		 * dataRow = reportSheet.createRow(rowNum); if(resultFieldsList != null){ for
		 * (int colNum = 0; colNum<resultFieldsList.size(); colNum++){ String data = "";
		 * if(rowDataList.get(colNum) != null) data = rowDataList.get(colNum).trim();
		 * Cell dataCell = dataRow.createCell(colNum); ResultField resultField =
		 * resultFieldsList.get(colNum); if(resultField.getDataType().equals("DATE")){
		 * Date tempDate = new Date(data); dataCell.setCellValue(tempDate); }else
		 * if(resultField.getDataType().equals("INTEGER") &&
		 * !data.equalsIgnoreCase("")){
		 * //dataCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		 * dataCell.setCellStyle(styleNumericFormat);
		 * dataCell.setCellValue(Integer.parseInt(data)); }else
		 * if(resultField.getDataType().equals("FLOAT") && !data.equalsIgnoreCase("")){
		 * //dataCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		 * dataCell.setCellStyle(styleFloatFormat);
		 * dataCell.setCellValue(Float.parseFloat(data)); }else
		 * if(resultField.getDataType().equals("CURRENCY") &&
		 * !data.equalsIgnoreCase("")){
		 * dataCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		 * dataCell.setCellValue(Float.parseFloat(data)); }else
		 * if(resultField.getAlignment().equals("RIGHT")){
		 * dataCell.setCellStyle(styleRightAlignFormat); dataCell.setCellValue(data);
		 * }else if(resultField.getAlignment().equals("CENTER")){
		 * dataCell.setCellStyle(styleCenterAlignFormat); dataCell.setCellValue(data);
		 * }else{ dataCell.setCellStyle(styleNormalFormat); dataCell.setCellValue(data);
		 * } } } rowNum++; }
		 */
			
		return workbook;
	}
	
	private CellStyle setHeaderStyle(Workbook workbook) {
		Font font = workbook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setFontHeightInPoints((short) 12);
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setWrapText(false);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		return cellStyle;
	}

	private CellStyle setBodyStyle(Workbook workbook) {
		Font font = workbook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setFontHeightInPoints((short) 10);
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyle.setWrapText(true);
		return cellStyle;
	}
}


