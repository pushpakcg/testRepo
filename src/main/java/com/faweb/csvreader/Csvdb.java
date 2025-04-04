package com.faweb.csvreader;

import java.io.*;
//import au.com.bytecode.opencsv.CSVReader;
import java.util.*;
import java.sql.*;

public class Csvdb {
	public static void main(String[] args) throws Exception {
		/*
		 * Create Connection objects Class.forName ("oracle.jdbc.OracleDriver");
		 * Connection conn = DriverManager.getConnection(
		 * "jdbc:oracle:thin:@//ecldbld00008.tfayd.com:16646/d590.tfayd.com", "FAWEB",
		 * "Faweb#123"); PreparedStatement sql_statement = null; Create the insert
		 * statement String jdbc_insert_sql = "INSERT INTO CSV_2_ORACLE" +
		 * "(USER_ID, USER_AGE) VALUES" + "(?,?)"; sql_statement =
		 * conn.prepareStatement(jdbc_insert_sql); Read CSV file in OpenCSV String
		 * inputCSVFile = "C:\\Users\\kn115852\\Documents\\soft\\inputdata.csv";
		 * CSVReader reader = new CSVReader(new FileReader(inputCSVFile)); Variables to
		 * loop through the CSV File String [] nextLine; for every line in the file int
		 * lnNum = 1; line number while ((nextLine = reader.readNext()) != null) {
		 * lnNum++; Bind CSV file input to table columns sql_statement.setString(1,
		 * nextLine[0]); Bind Age as double Need to convert string to double here
		 * System.out.println("jjjj"); sql_statement.setString(2, nextLine[1]);
		 * System.out.println("kkkk"); execute the insert statement
		 * sql_statement.executeUpdate(); } Close prepared statement
		 * sql_statement.close(); COMMIT transaction conn.commit(); Close connection
		 * conn.close();
		 */}
}