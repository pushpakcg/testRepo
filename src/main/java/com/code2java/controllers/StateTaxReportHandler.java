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

public class StateTaxReportHandler {
	
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
		System.out.println("excel Header====================");
		header.createCell(0).setCellValue("BUSINESS UNIT");
		header.createCell(1).setCellValue("UNIQUE ASSET KEY");
		header.createCell(2).setCellValue("ESTIMATED DATE OF ASSET");
		header.createCell(3).setCellValue("ASSET DESCRIPTION");
		header.createCell(4).setCellValue("GEOCODE-STATE");
		header.createCell(5).setCellValue("GEOCODE-COUNTRY");
		header.createCell(6).setCellValue("BOOK COST");
		header.createCell(7).setCellValue("ACCUMULATED BOOK DEPR");
		
	
		int rowNum = 1;
		for (List<String> list : resultDataList) {

			int cellNum = 0;
			// create the row data
			Row row = reportSheet.createRow(rowNum++);
			for (String entry : list) {
				// if(cellNum==14 && StringUtils.isNotBlank(entry)) {
				if (StringUtils.isNotBlank(entry)
						&& (cellNum == 6 || cellNum == 7 )) {
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


