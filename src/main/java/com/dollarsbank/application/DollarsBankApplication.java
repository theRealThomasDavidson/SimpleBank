package com.dollarsbank.application;

import java.sql.Connection;
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

public class DollarsBankApplication {
	static Connection conn = ConnectionManager.getConnection();
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
			System.out.println("Could NOT retrieve list of movies from D/B :(");
		}
		
		return null;
	}
	public static void main(String args[]) {
		
		
		for(Customer cust : getAllCustomers()) {
			System.out.println(cust.toString(0));
		};
		/*Customer george = new Customer("george", "pass", new HashMap<Integer, SavingsAccount>(), new HashMap<Integer, Account>());
		System.out.println(george.toString(0));
		SavingsAccount acc1 = new SavingsAccount("S234533",28.65,george); 
		SavingsAccount acc2 = new SavingsAccount("S2324533",24.65,george); 
		Account acc3 = new Account("C2324533",35.65,george); 
		System.out.println(george.toString(0));*/
	}
	
	

	
}
