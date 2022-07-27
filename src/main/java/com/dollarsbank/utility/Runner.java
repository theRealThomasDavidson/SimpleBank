package com.dollarsbank.utility;

import java.sql.Connection;
import java.sql.SQLException;

public class Runner {
	public static void main(String[] args) {
		
		Connection conn = ConnectionManager.getConnection();
		System.out.println("MySQL D/B connection established :)");
		
		
		try {
			conn.close();
			System.out.println("MySQL D/B Connection successfully closed :)");
		} catch(SQLException e) {
			System.out.println("DID NOT close connection object :(");
			e.printStackTrace();
		}
		
		
	

	}
}
