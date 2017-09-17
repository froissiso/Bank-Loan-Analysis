package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import bank.BankRecords;
import models.*;
import views.*;

/*
 * Program to read bank records data from a file, process it, store it in a MySQL database and present it as a GUI.
 * After showing the data on the screen, the application serializes bank records data, sleeps for 5 seconds and deserializes 
 * it again, showing in console the time elapsed between both processes. 
 * 
 * - Programmer: Francisco Rois Siso
 * - Date: 03/20/2017
 * - Source File Name: LoanController.java
 * - Lab 4
 * - ITMD510 Object-Oriented Application Development
 */

/**
 * The class LoanController contains the main and therefore is the driver file of the program.
 * 
 * @author Francisco Rois Siso
 *
 */
public class LoanController{
	// data needed to connect to the database
	final static String DB_URL = "jdbc:mysql://www.papademas.net:3306/510labs?autoReconnect=true&useSSL=false";
	final static String USERNAME = "db510";
	final static String PASSWORD = "510";
	
	// name of the table to create
	static String table_name = "f_rois_tab";
	
	// map with the bank records read from a file
	static Map<String,BankRecords> bankRecords;
	
	public static void main(String[] args) {
		// create a new object LoanConnector, which will be used to read data from a file
		LoanConnector lc = new LoanConnector();
		// read data from the default csv file and process it (storage)
		lc.readData("default");
		
		// Collect BankRecords into a HashMap
		bankRecords = new HashMap<>();
		for(BankRecords b: lc.bankRecords_list){
			bankRecords.put(b.getId(), b);
		}
		
		// create connection to the database using the access data
		Connector connector = new Connector();
		Connection connection = connector.createConnection(DB_URL, USERNAME, PASSWORD);
		
		// daoModel object created in order to execute CRUD functions
		daoModel dm = new daoModel();
		dm.createTable(connection, table_name);
		//dm.dropTable(connection, table_name);
		dm.inserts(connection, lc.bankRecords_list, table_name);
		//dm.deleteAllFromTable(connection, table_name);
		
		// Extract data from result set
		try {
			ResultSet rs = dm.getResultSet(connection, table_name);
			while(rs.next()){
				//Retrieve by column name
				String id  = rs.getString("id");
				double income = rs.getDouble("income");
				String pep = rs.getString("pep");

				//Display values
				System.out.print("ID: " + id);
				System.out.print(", Income: " + String.valueOf(income));
				System.out.println(", Pep: " + pep);
				
				// create object LoanView for graphic interface display
				LoanView lw = new LoanView(rs);
			}
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}

		// Close connection to database
		try {
			connection.close();
			System.out.println("\nConnection to db closed");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		// Create an instance of PersistentObject with the map of BankRecords and the current timestamp
		PersistentObject po = new PersistentObject(bankRecords,new Date());
		//Serialize PersistentObject to a file
		FileOutputStream outStream = null;
		ObjectOutputStream objectOutputFile = null;
		try {
			outStream = new FileOutputStream("bankrecords.ser");
			objectOutputFile = new ObjectOutputStream(outStream);
			objectOutputFile.writeObject(po);
		} catch (FileNotFoundException e2) {
			System.out.println(e2.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		// Make application sleep for 5 seconds
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		// De-serialize the persistent object into a date object and a new map object
		FileInputStream inStream = null;
		ObjectInputStream objectInputStream = null;
		try {
			inStream = new FileInputStream("bankRecords.ser");
			objectInputStream = new ObjectInputStream(inStream);
		} catch (FileNotFoundException e1) {
			System.out.println(e1.getMessage());
		} catch (IOException e2) {
			System.out.println(e2.getMessage());
		}
		
		// new PersistentObject object, to store deserialized information
		PersistentObject new_po = null;
		try {
			new_po = (PersistentObject) objectInputStream.readObject();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println(e.getMessage());
		}
		// new map to store the bank records obtained from deserialization of file
		Map<String,BankRecords> newBankRecords = new_po.getBankRecords();
		// new date object obtained from deserialization of file
		Date newDate = new_po.getDate();
		
//		System.out.println("\nDESERIALIZED MAP");
//		for(String key: newBankRecords.keySet()){
//			System.out.println(newBankRecords.get(key));
//		}
//
//		System.out.println("\nDESERIALIZED DATE");
//		System.out.println(newDate);
				
		// Display to the console the time difference between serialization and de-serialization
		System.out.println("\nTime difference between serialization and deserialization: "+(((new Date()).getTime()-newDate.getTime())/1000.00) +" seconds");
	}
}
