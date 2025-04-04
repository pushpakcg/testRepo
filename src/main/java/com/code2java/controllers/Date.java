package com.code2java.controllers;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

	public class Date {

	public static void main(String[] args) {
	String fileName="dataupload.csv";
	fileName=fileName.substring(0, fileName.indexOf(".csv"));
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	String time=sdf.format(timestamp);
	fileName=fileName+"."+time+".csv";
	System.out.println(fileName);

	}

	}


