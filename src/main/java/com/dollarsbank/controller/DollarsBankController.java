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
import com.dollarsbank.model.Transaction;
import com.dollarsbank.utility.ConnectionManager;

public class DollarsBankController {
	
	private static Connection conn = ConnectionManager.getConnection();
	private static Customer loggedIn;
	static {
		Customer.setCounter(getCustIdRange());
		int max = getAccountIdRange();
		SavingsAccount.setIdCounter(max);
		Account.setIdCounter(max+1);
		Transaction.setCounter(getTransIdRange());
	}
	public static void logIn(Customer cust) {
		DollarsBankController.loggedIn = cust;
	}
	public static void logOut() {
		DollarsBankController.loggedIn = null;
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
	
	public static Account newAccount(Account account) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into `account` "
					+ "(id, cust_id, account_number, balance, account_type) "
					+ "values (?, ? ,?, ?, 'checking')");

			pstmt.setInt(1, account.getId());
			pstmt.setInt(2, account.getCustomer().getId());
			pstmt.setString(3, account.getAccount_number());
			pstmt.setFloat(4, account.getAmount());
			int count = pstmt.executeUpdate();
			if(count > 0) {
				return account;
			}
			return null;
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Could NOT create new account in D/B :(");
		}
		return null;
	}
	public static SavingsAccount newSavingsAccount(SavingsAccount account) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into `account` "
					+ "(id, cust_id, account_number, balance, account_type) "
					+ "values (?, ? ,?, ?, 'savings')");

			pstmt.setInt(1, account.getId());
			pstmt.setInt(2, account.getCustomer().getId());
			pstmt.setString(3, account.getAccount_number());
			pstmt.setFloat(4, account.getAmount());
			int count = pstmt.executeUpdate();
			if(count > 0) {
				return account;
			}
			return null;
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Could NOT create new account in D/B :(");
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
	
	
	public static boolean updateAccountBalance(SavingsAccount account) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("update `account` a set a.balance = ? where a.id = ?");
			pstmt.setFloat(1, account.getAmount());
			pstmt.setFloat(2, account.getId());
			
			int count = pstmt.executeUpdate();
			
			if(count > 0) {
				return true;
			}
		}
		catch(SQLException e) {
			System.out.println("Could NOT find account in D/B :(");
		}
		return false;
		
	}
	
	public static boolean updateAccountBalance(Account account) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("update `account` a set a.balance = ? where a.id = ?");
			pstmt.setFloat(1, account.getAmount());
			pstmt.setFloat(2, account.getId());
			
			int count = pstmt.executeUpdate();
			
			if(count > 0) {
				return true;
			}
		}
		catch(SQLException e) {
			System.out.println("Could NOT find account in D/B :(");
		}
		return false;
	}	
		
	
	public static Customer findCustomerByName(String name) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from customer c where c.username = ?");
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
	public static int getTransIdRange() {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select max(id) as max from `transaction`");
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
	public static Boolean newTransaction(Transaction trans) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into transaction "
					+ "(id, cust_id, acc_id, amount) "
					+ "VALUES(?, ?, ?, ?)");

			pstmt.setInt(1, trans.getId());
			pstmt.setInt(2, trans.getCust_id());
			pstmt.setInt(3, trans.getAcc_id());
			pstmt.setFloat(4, trans.getAmount());
			int count = pstmt.executeUpdate();
			if(count > 0) {
				return true;
			}
			return null;
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Could NOT create new customer in D/B :(");
		}
		return false;
	}
	
	public static List<Transaction>findTransByCustomer(Customer cust, int limit) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from `transaction` t "
					+ "where t.cust_id = ? "
					+ "order by t.id desc limit ?");
			pstmt.setInt(1, cust.getId());
			pstmt.setInt(2, limit);
			ResultSet rs = pstmt.executeQuery();
			List<Transaction> ret = new ArrayList<Transaction>();
			while(rs.next()) {
				ret.add(new Transaction(rs.getInt("id"), rs.getInt("cust_id"), rs.getInt("acc_id"), rs.getFloat("amount")));
			}
			return ret;
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Could NOT find transactions in D/B :(");
		}
		return new ArrayList<Transaction>();
	}
	
	public static Account findAccountById(int id) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from `account` a "
					+ "where a.id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			Account ret;
			if(rs.next()) {
				if (!rs.getString("account_type").equalsIgnoreCase("checking")) {
					return null;
				}
				ret = new Account(
						rs.getInt("id"), 
						rs.getString("account_number"), 
						rs.getFloat("balance"),
						DollarsBankController.loggedIn);
			}
			else {return null;}
			return ret;
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Could NOT find Account in D/B :(");
		}
		return null;
	}
	public static SavingsAccount findSavingAccountById(int id) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from `account` a "
					+ "where a.id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			SavingsAccount ret;
			if(rs.next()) {
				if (!rs.getString("account_type").equalsIgnoreCase("savings")) {
					return null;
				}
				ret = new SavingsAccount(
						rs.getInt("id"), 
						rs.getString("account_number"), 
						rs.getFloat("balance"),
						DollarsBankController.loggedIn);
			}
			else {return null;}
			return ret;
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Could NOT find Account in D/B :(");
		}
		return null;
	}
}
