package com.code2java.controllers;

import java.text.SimpleDateFormat;
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


public class SAPHistFederalTaxRetReportsHandler {

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
		header.createCell(0).setCellValue("Year");	
		header.createCell(1).setCellValue("Report_Name");
		header.createCell(2).setCellValue("Unique Asset Number");
		header.createCell(3).setCellValue("Company Code");
		header.createCell(4).setCellValue("Asset Number");
		header.createCell(5).setCellValue("Sub Number");
		header.createCell(6).setCellValue("Legacy Unique Asset Number");
		header.createCell(7).setCellValue("Asset Description");
		header.createCell(8).setCellValue("Asset Class");
		header.createCell(9).setCellValue("Asset Class Description");
		header.createCell(10).setCellValue("Legacy Category Code");
		header.createCell(11).setCellValue("Entered Date");
		header.createCell(12).setCellValue("Install Date");
		header.createCell(13).setCellValue("Tax Installation Yr");
		header.createCell(14).setCellValue("Asset Life");
		header.createCell(15).setCellValue("Remaining Life");
		header.createCell(16).setCellValue("Tax APC");
		header.createCell(17).setCellValue("Depreciable Basis");		
		header.createCell(18).setCellValue("Bonus Depreciation");
		header.createCell(19).setCellValue("Tax Ytd Bonus Depreciation");
		header.createCell(20).setCellValue("Elect out Bonus");
		header.createCell(21).setCellValue("Tax Bonus Percentage");
		header.createCell(22).setCellValue("First Year Depreciated");
		header.createCell(23).setCellValue("Last Year Depreciated");
		header.createCell(24).setCellValue("Last Month Depreciated");
		header.createCell(25).setCellValue("New or Used");
		header.createCell(26).setCellValue("Tax Depr Method");
		header.createCell(27).setCellValue("Tax Depr Convention");
		header.createCell(28).setCellValue("Park Asset Property Tax");
		header.createCell(29).setCellValue("Current Accounting Year");		
		header.createCell(30).setCellValue("Corp InstallDate");
		header.createCell(31).setCellValue("Corp Life");
		header.createCell(32).setCellValue("Corp Book APC");
		header.createCell(33).setCellValue("Corp Book Accum Reserve");
		header.createCell(34).setCellValue("Corp NBV");		
		header.createCell(35).setCellValue("Retirement Date");
		header.createCell(36).setCellValue("Assignment");
		header.createCell(37).setCellValue("Retirement Description");
		header.createCell(38).setCellValue("Retirement Cost");
		header.createCell(39).setCellValue("Retirement AD");
		header.createCell(40).setCellValue("Retirement YTD");
		header.createCell(41).setCellValue("RETIREMENT NBV");
		header.createCell(42).setCellValue("Retirement Proceeds");
		header.createCell(43).setCellValue("Gain");
		header.createCell(44).setCellValue("Loss");
		header.createCell(45).setCellValue("Evalution");
	
		
		
		
		int rowNum = 1;
		for (List<String> list : resultDataList) {
			
			int cellNum = 0;
			// create the row data
			Row row = reportSheet.createRow(rowNum++);
			for (String entry : list) {
				//if (cellNum == 14) {
	if(StringUtils.isNotBlank(entry) && (cellNum==16 || cellNum==17 || cellNum==18 || cellNum==19|| cellNum==32
			|| cellNum==33 || cellNum==34 || cellNum==38 || cellNum==39 || cellNum==40 || cellNum==41 || cellNum==42 || cellNum==43 || cellNum==44 || cellNum==45 )) {
						row.createCell(cellNum++).setCellValue(Double.parseDouble(entry));
					}
				//}
			else {
					row.createCell(cellNum++).setCellValue(entry);
				}
				// row.createCell(cellNum++).setCellValue( (entry);
			}
		}
		
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