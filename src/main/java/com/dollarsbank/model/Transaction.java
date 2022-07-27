package com.dollarsbank.model;

import java.util.Map;

import com.dollarsbank.utility.ColorsUtility;

public class Transaction {

	private int id;
	private int cust_id;
	private int acc_id;
	private float amount;

	private static int idCounter = 0;
	public static synchronized int createID(){
		idCounter += 1;
	    return idCounter;
	}
	
	public Transaction(int cust_id, int acc_id, float amount) {
		super();
		this.cust_id = cust_id;
		this.acc_id = acc_id;
		this.amount = amount;
		this.id = Transaction.createID();
	}

	public Transaction(int id, int cust_id, int acc_id, float amount) {
		super();
		this.id = id;
		this.cust_id = cust_id;
		this.acc_id = acc_id;
		this.amount = amount;
	}

	public static void setCounter(int id) {
		Transaction.idCounter = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCust_id() {
		return cust_id;
	}
	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}
	public int getAcc_id() {
		return acc_id;
	}
	public void setAcc_id(int acc_id) {
		this.acc_id = acc_id;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String toString(int indent, Customer cust, Account acc) {
		String message = "##############\n" + "\t".repeat(indent) + "Transaction \n" 
				+ "\t".repeat(indent+1)+ "Customer:\t" + cust.getName() + "\n"
				+ "\t".repeat(indent+1)+ "Account:\t" + acc.getAccount_number()+ "\n"
				+ "\t".repeat(indent+1)+ "Amount:\t"
				+ ColorsUtility.toColor(this.amount >= 0? "green" : "red", String.valueOf(this.amount)) + "\n";
		message += "##############";
		return message;
	}
	
}
