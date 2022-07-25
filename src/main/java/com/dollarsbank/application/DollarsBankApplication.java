package com.dollarsbank.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.dollarsbank.model.Account;
import com.dollarsbank.model.Customer;
import com.dollarsbank.model.SavingsAccount;

public class DollarsBankApplication {

	public static void main(String args[]) {
		String url = "jdbc:mysql://localhost:3306/bank";
		String user = "root";
		String password = "Root@123";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url, user,password);
			System.out.println("Connected to database: "+url);
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		Customer george = new Customer("george", "pass", new HashMap<Integer, SavingsAccount>(), new HashMap<Integer, Account>());
		System.out.println(george.toString(0));
		SavingsAccount acc1 = new SavingsAccount("S234533",28.65,george); 
		SavingsAccount acc2 = new SavingsAccount("S2324533",24.65,george); 
		Account acc3 = new Account("C2324533",35.65,george); 
		System.out.println(george.toString(0));
	}
	
}
