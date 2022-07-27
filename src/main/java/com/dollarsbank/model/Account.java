package com.dollarsbank.model;

import com.dollarsbank.utility.ColorsUtility;

public class Account {

	private int id;
	private String account_number;
	private float amount;
	private Customer customer;
	
	private static int idCounter = 0;
	
	public Account(int id, String account_number, float amount, Customer customer) {
		super();
		this.id = id;
		this.account_number = account_number;
		this.amount = amount;
		this.setCustomer(customer);
	}
	
	public Account(String account_number, float amount, Customer customer) {
		super();
		this.id = this.createID();
		this.account_number = account_number;
		this.amount = amount;
		this.setCustomer(customer);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		if (customer== null){return;}
		if(this.customer != null) {
			this.customer.removeAccount(this);
		}
		customer.addAccount(this);
		this.customer = customer;
	}
	public static void setIdCounter(int idCounter) {
		Account.idCounter = idCounter;
	}
	public static synchronized int createID(){
		idCounter += 2;
	    return idCounter;
	}
	public String toString(int i) {
		return "--------------\n" + "\t".repeat(i) + "Checking Account\n" 
					+ "\t".repeat(i+1)+ "Account Number:\t" + this.account_number+ "\n"
					+ "\t".repeat(i+1) + ColorsUtility.toColor("pink", "Owner: " + customer.getName() )+ "\n"
					+ "\t".repeat(i+1) + "Balance: " + amount + " \n--------------\n";
	}
}
