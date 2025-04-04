package com.code2java.controllers;

import java.util.Date;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;

public class CsvFileReaderJob extends TimerTask {
	
	@Autowired
	private DTController dTController;

	@Override
	public void run() {

		System.out.println("Job run time : " + new Date());
		dTController.csvFilesProcess("no-ssoid");
	}

}
