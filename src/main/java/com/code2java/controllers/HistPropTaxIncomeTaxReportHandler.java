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


public class HistPropTaxIncomeTaxReportHandler {
	
	public SXSSFWorkbook writeFile(List<List<String>> resultDataList) {
		System.out.println("Chunk Size: " + resultDataList.size());
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
			
		
		header.createCell(0).setCellValue("Year");
		header.createCell(1).setCellValue("Report name");
		header.createCell(2).setCellValue("Instance");
		header.createCell(3).setCellValue("Entity");
		header.createCell(4).setCellValue("Company");		
		header.createCell(5).setCellValue("Unique Asst No");
		header.createCell(6).setCellValue("Instance No");
		header.createCell(7).setCellValue("Internal Asst No");
		header.createCell(8).setCellValue("Asset Primary");
		header.createCell(9).setCellValue("Component");
		header.createCell(10).setCellValue("Asset Description");
		header.createCell(11).setCellValue("State");
		header.createCell(12).setCellValue("Physical Location");
		header.createCell(13).setCellValue("Country");
		header.createCell(14).setCellValue("State");
		header.createCell(15).setCellValue("County");
		header.createCell(16).setCellValue("City");
		header.createCell(17).setCellValue("Street");
		header.createCell(18).setCellValue("Zip Code");
		header.createCell(19).setCellValue("Pl Description");
		header.createCell(20).setCellValue("Entered Date");
		header.createCell(21).setCellValue("Entered Year");
		header.createCell(22).setCellValue("Accounting Location");
		header.createCell(23).setCellValue("Al Description");
		header.createCell(24).setCellValue("Gl Description");
		header.createCell(25).setCellValue("Corporate Life");		
		header.createCell(26).setCellValue("Install Date");
		header.createCell(27).setCellValue("Install Year");
		header.createCell(28).setCellValue("Prorate Code");
		header.createCell(29).setCellValue("Depreciation Table");
		header.createCell(30).setCellValue("Remaining Life");
		header.createCell(31).setCellValue("Org First Cost");
		header.createCell(32).setCellValue("Corp Book Cost Basis");
		header.createCell(33).setCellValue("Corp Book Accum Rsrv ");
		header.createCell(34).setCellValue("Net Book Value");
		header.createCell(35).setCellValue("Corp Ytd Depr");
		header.createCell(36).setCellValue("Corp Book Cur Depr");
		header.createCell(37).setCellValue("Last Yr Depreciated");
		header.createCell(38).setCellValue("Last Mth Depreciated");
		header.createCell(39).setCellValue("Total Retirement Cost");
		header.createCell(40).setCellValue("Fully Retired");
		header.createCell(41).setCellValue("Accounting Year");
		header.createCell(42).setCellValue("Accounting Period");
		header.createCell(43).setCellValue("Tax Install Date");
		header.createCell(44).setCellValue("Tax Cost Basis");
		header.createCell(45).setCellValue("Depr Expense Acct");
		header.createCell(46).setCellValue("Asset Acct");
		header.createCell(47).setCellValue("Accm Reserve Acct");
		header.createCell(48).setCellValue("Purchases Acct");
		header.createCell(49).setCellValue("Net Proceeds Acct");
		header.createCell(50).setCellValue("Ord Gain/Loss Acct");
		header.createCell(51).setCellValue("Extraord Gl Acct");
		header.createCell(52).setCellValue("Transfers In Acct");
		header.createCell(53).setCellValue("Transfers Out Acct");
		header.createCell(54).setCellValue("Trnsr Acct For Asset");
		header.createCell(55).setCellValue("Trnsr Acct For AD");
		header.createCell(56).setCellValue("Ret Acct For Asset");
		header.createCell(57).setCellValue("Ret Acct For AD");
		header.createCell(58).setCellValue("Quantity");
		header.createCell(59).setCellValue("Org Internal Asst No");
		header.createCell(60).setCellValue("Org Company");
		header.createCell(61).setCellValue("Asset Status");
		header.createCell(62).setCellValue("User Feild 1");
		header.createCell(63).setCellValue("User Feild 2");
		header.createCell(64).setCellValue("User Feild 3");
		header.createCell(65).setCellValue("User Feild 4");
		header.createCell(66).setCellValue("User Feild 5");
		header.createCell(67).setCellValue("User Feild 6");
		header.createCell(68).setCellValue("User Feild 7");
		header.createCell(69).setCellValue("User Feild 8");
		header.createCell(70).setCellValue("User Feild 9");
		header.createCell(71).setCellValue("User Feild 10");
		header.createCell(72).setCellValue("User Feild 11");
		header.createCell(73).setCellValue("User Feild 12");
		header.createCell(74).setCellValue("User Feild 13");
		header.createCell(75).setCellValue("User Feild 14");
		
		
		
		
		
		
		
		int rowNum = 1;
		for (List<String> list : resultDataList) {
					
			int cellNum = 0;
			// create the row data
			Row row = reportSheet.createRow(rowNum++);
			for (String entry : list) {
				if(StringUtils.isNotBlank(entry) && (cellNum==31 || cellNum==32 || cellNum==33 || cellNum==34|| cellNum==35 
						|| cellNum==36 || cellNum==39 || cellNum==44 )) {
									row.createCell(cellNum++).setCellValue(Double.parseDouble(entry));
								}
				else {
				row.createCell(cellNum++).setCellValue(entry);
				}
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
