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


public class HistIncomeTaxReportHandler {
	
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
		header.createCell(3).setCellValue("Unique Asset Number");
		header.createCell(4).setCellValue("FA Company");		
		header.createCell(5).setCellValue("SAP BU");
		header.createCell(6).setCellValue("Asset Number Primary");
		//header.createCell(7).setCellValue("Asset Number Primary");
		header.createCell(7).setCellValue("Asset Component");
		header.createCell(8).setCellValue("Internal Asset");
		header.createCell(9).setCellValue("Asset Description");
		header.createCell(10).setCellValue("Entered Date");
		header.createCell(11).setCellValue("Tax Install Date");
		header.createCell(12).setCellValue("Tax Asset Life");
		header.createCell(13).setCellValue("Tax Remaining Life");
		header.createCell(14).setCellValue("Tax Cost Basis");
		header.createCell(15).setCellValue("Tax Depreciable basis");
		header.createCell(16).setCellValue("Tax Accumulated Reserve");
		header.createCell(17).setCellValue("Tax Ytd Depreciation");
		header.createCell(18).setCellValue("Tax Net book Value");
		header.createCell(19).setCellValue("Bonus Depreciation");
		header.createCell(20).setCellValue("Ytd Bonus Depreciation");
		header.createCell(21).setCellValue("Elect out Bonus");
		header.createCell(22).setCellValue("Bonus Flag");
		header.createCell(23).setCellValue("Bonus");
		header.createCell(24).setCellValue("Ytd amount Dprr");		
		header.createCell(25).setCellValue("Adr Additional Depr ");
		header.createCell(26).setCellValue("ACRS Class");
		header.createCell(27).setCellValue("Tax Regulation Code");
		header.createCell(28).setCellValue("Tax Depr Table");
		header.createCell(29).setCellValue("Section_1245_1250_Property");
		header.createCell(30).setCellValue("Listed Property");
		header.createCell(31).setCellValue("Depr Intangible");
		header.createCell(32).setCellValue("First Depr Year");
		header.createCell(33).setCellValue("Corp Internal Asset");
		header.createCell(34).setCellValue("Tax Amount table");
		header.createCell(35).setCellValue("Tax New or Used");
		header.createCell(36).setCellValue("Tax Auto");
		header.createCell(37).setCellValue("Tax Lease hold Impr");
		header.createCell(38).setCellValue("Tax Depr Method");
		header.createCell(39).setCellValue("Tax Depr Convention");
		header.createCell(40).setCellValue("Corp Install Date");
		header.createCell(41).setCellValue("Corp Asset Life");
		header.createCell(42).setCellValue("Corp Book Cost Basis");
		header.createCell(43).setCellValue("Corp book Acum Reserve");
		header.createCell(44).setCellValue("Corp Book Current Depr");
		header.createCell(45).setCellValue("Corp Last Year Depricated");
		header.createCell(46).setCellValue("Corp Last Month Depricated");
		header.createCell(47).setCellValue("Corp Nbv");
		header.createCell(48).setCellValue("Corp Fully Retired");
		header.createCell(49).setCellValue("Project");
		header.createCell(50).setCellValue("Category");
		header.createCell(51).setCellValue("Category Desc");
		header.createCell(52).setCellValue("Tax installation year");
		header.createCell(53).setCellValue("Tax Curr Acc year");
		header.createCell(54).setCellValue("Instance_1");
		header.createCell(55).setCellValue("Year_1");
		header.createCell(56).setCellValue("Creation Date");
		header.createCell(57).setCellValue("Created User");
		
		
	
		int rowNum = 1;
		for (List<String> list : resultDataList) {
			
			int cellNum = 0;
			// create the row data
			Row row = reportSheet.createRow(rowNum++);
			for (String entry : list) {
				if(StringUtils.isNotBlank(entry) && (cellNum==14 || cellNum==15 || cellNum==16 || cellNum==17|| cellNum==18 
						|| cellNum==19 || cellNum==20 || cellNum==24 || cellNum==25 || cellNum==42 || cellNum==43 || cellNum==44 || cellNum==47 )) {
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
