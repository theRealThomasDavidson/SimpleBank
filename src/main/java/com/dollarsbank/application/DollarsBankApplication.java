package com.dollarsbank.application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dollarsbank.controller.DollarsBankController;
import com.dollarsbank.model.Account;
import com.dollarsbank.model.Customer;
import com.dollarsbank.model.SavingsAccount;
import com.dollarsbank.utility.ConnectionManager;
import com.dollarsbank.utility.ConsolePrinterUtility;

public class DollarsBankApplication {
	static Connection conn = ConnectionManager.getConnection();

	public static void main(String args[]) {
		
		ConsolePrinterUtility.main_menu();
		
	}
		
}
