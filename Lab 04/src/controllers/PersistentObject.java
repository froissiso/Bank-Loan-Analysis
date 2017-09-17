package controllers;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import bank.BankRecords;

/**
 * The class Persistent encapsulates a map of bank records and a date.
 * The class Persistent implements java.io.Serializable.
 * 
 * @author Francisco Rois Siso
 *
 */
public class PersistentObject implements Serializable{
	private static final long serialVersionUID = 8711159212803491294L;
	
	private Map<String,BankRecords> bankRecords;
	private Date date;
	
	/**
	 * Constructor with no input parameters.
	 */
	public PersistentObject(){}
	
	/**
	 * Constructor with two parameters: bankRecords and date.
	 * @param bankRecords Map with the bank records stored by id (key)
	 * @param date
	 */
	public PersistentObject(Map<String,BankRecords> bankRecords, Date date){
		this.setBankRecords(bankRecords);
		bankRecords = new HashMap<>();
		this.setDate(date);
	}
	/**
	 * Getters and setters for bankRecords and date.
	 */
	public Map<String,BankRecords> getBankRecords() {
		return bankRecords;
	}
	public void setBankRecords(Map<String,BankRecords> bankRecords) {
		this.bankRecords = bankRecords;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
