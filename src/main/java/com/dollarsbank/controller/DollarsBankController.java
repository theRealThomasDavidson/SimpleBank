package com.dollarsbank.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dollarsbank.model.Account;
import com.dollarsbank.model.Customer;
import com.dollarsbank.model.SavingsAccount;
import com.dollarsbank.utility.ConnectionManager;

public class DollarsBankController {
	
	private static Connection conn = ConnectionManager.getConnection();
	private static List<Customer> allCust = getAllCustomers();
	static {
		Customer.setCounter(getCustIdRange());
		int max = getAccountIdRange();
		SavingsAccount.setIdCounter(max);
		Account.setIdCounter(max+1);
	}
			
	public static List<Customer> getAllCustomers() {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM customer");
			
			List<Customer> custList = new ArrayList<Customer>();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("username");
				String password = rs.getString("pass");
				Customer tempCust = new Customer( id, name, password,
						new HashMap<Integer, SavingsAccount>(),  new HashMap<Integer, Account>() );
				custList.add(tempCust);
			}
			
			return custList;
			
			
		} catch(SQLException e) {
			System.out.println("Could NOT retrieve list of Users from D/B :(");
		}
		
		return null;
	}
	public static Customer newCustomer(Customer customer) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into customer"
					+ "(username, pass)"
					+ "VALUES(?, ?)");

			pstmt.setString(1, customer.getName());
			pstmt.setString(2, customer.getPassword());
			int count = pstmt.executeUpdate();
			if(count > 0) {
				return customer;
			}
			return null;
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Could NOT create new customer in D/B :(");
		}
		return null;
	}
	public static List<Account> findAccountsByCustomer(Customer cust) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from account a where a.cust_id = ? and a.account_type = 'checking'");
			pstmt.setInt(1, cust.getId());
			ResultSet rs = pstmt.executeQuery();
			List<Account> ret = new ArrayList<Account>();
			while(rs.next()) {
				ret.add(new Account(rs.getInt("id"),rs.getString("account_number"), rs.getFloat("balance"), cust));
			}
			return ret;
		}
		catch(SQLException e) {
			System.out.println("Could NOT find accounts in D/B :(");
		}
		return null;
		
	}
	public static List<SavingsAccount> findSavingsAccountsByCustomer(Customer cust) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from account a where a.cust_id = ? and a.account_type = 'savings'");
			pstmt.setInt(1, cust.getId());
			ResultSet rs = pstmt.executeQuery();
			List<SavingsAccount> ret = new ArrayList<SavingsAccount>();
			while(rs.next()) {
				ret.add(new SavingsAccount(rs.getInt("id"),rs.getString("account_number"), rs.getFloat("balance"), cust));
			}
			return ret;
		}
		catch(SQLException e) {
			System.out.println("Could NOT find accounts in D/B :(");
		}
		return null;
		
	}
	public static Customer findCustomerByName(String name) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from customer c where c.username = ?");
			int id;
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			Customer cust;
			if(rs.next()) {
				cust = new Customer(
						rs.getInt("id"), 
						rs.getString("username"), 
						rs.getString("pass"), 
						new HashMap<Integer, SavingsAccount>(), 
						new HashMap<Integer, Account>());
			}
			else {return null;}
			findSavingsAccountsByCustomer(cust);
			findAccountsByCustomer(cust);
			return cust;
			
		} catch(SQLException e) {
			System.out.println("Could NOT find customer in D/B :(");
		}
		return null;
	}
	public static int getCustIdRange() {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select max(id) as max from customer");
			int max = 0;
			if(rs.next()) {
				max = rs.getInt("max");
			}
			return max;
		} catch(SQLException e) {
			System.out.println("Could NOT set customer ids in D/B :(");
		}
		
		return 0;
	}
	public static int getAccountIdRange() {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select max(id) as max from `account`");
			int max = 0;
			if(rs.next()) {
				max = rs.getInt("max");
			}
			return max;
		} catch(SQLException e) {
			System.out.println("Could NOT set account Ids in D/B :(");
		}
		
		return 0;
	}
}
