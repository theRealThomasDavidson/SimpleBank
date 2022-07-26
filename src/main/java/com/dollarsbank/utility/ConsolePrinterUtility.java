package com.dollarsbank.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import com.dollarsbank.controller.DollarsBankController;
import com.dollarsbank.model.Account;
import com.dollarsbank.model.Customer;
import com.dollarsbank.model.SavingsAccount;

interface ProcessingMethod {
	   public void method();
	}

public class ConsolePrinterUtility {
	
	private static Scanner sc = new Scanner(System.in);
	
	
	public static String inputString(String message) {
		System.out.print(message);
		String input = sc.nextLine();
		return input;
	}
	
	
	public static Integer inputInt(String message) {
		while(true) {
			System.out.print(message);
			String input = sc.nextLine();
			try {
				int returnValue = Integer.parseInt(input);
				System.out.print("\n");
				return returnValue;
			}
			catch (Exception e) {
				System.out.println("Error. Please try again.\n" + message);
				continue;
			}
		}
	}
	
	
	public static void menu(Map<String, ProcessingMethod> options) {
		int max = options.size();
		Map<Integer, String> choices = new HashMap<Integer, String>();
		Integer ndx = 1;
		for (String i : options.keySet()) {
			  choices.put(ndx, i);
			  ndx += 1;  
		}
		choices.put(0, "exit");
		
		String message = "Please enter the number of your choice below.\n";
		for (Integer i : choices.keySet()) {
			  message += choices.get(i)+":\t"+ i+"\n";
		}
		
		int choicenum = -1;
		while(true) {
			do{
				choicenum = inputInt(message);
			}
			while(!choices.containsKey(choicenum));
			if(choicenum == 0 ) {
				return;
			}
			options.get(choices.get(choicenum)).method();
		}
	
	}
	
	
	public static void main_menu() {
	Map<String, ProcessingMethod> main_menu = new HashMap<String, ProcessingMethod>();
		main_menu.put("See Users", new ProcessingMethod() {
		   public void method() { 
			   List<Customer> custs =  DollarsBankController.getAllCustomers();
			   for(Customer cust: custs) {
				   System.out.println(cust.toString(0));
			   }
		   }
		});
		main_menu.put("New Users", new ProcessingMethod() {
			   public void method() { 
				   Customer newGuy = inputCustomer();
				   System.out.println(newGuy.toString(0));
				   }
			});
		main_menu.put("Login", new ProcessingMethod() {
			   public void method() {
				   String name = inputString("Name:\t");
				   String pass = inputString("Password:\t");
				   Customer cust = DollarsBankController.findCustomerByName(name);
				   if (cust == null){return;}
				   if (cust.getPassword().equals( pass)) {
					   System.out.println("Logged in.");
					   home_menu(cust);
				   }
				   System.out.println("Didn't log in.");
			   }
			});
	menu(main_menu);
	System.out.println("Good Bye!");
	}
	
	
	public static void home_menu(Customer cust) {
		System.out.println("Welcome " + cust.getName()+ "!");
		System.out.println(cust.toString(0));
		
		Map<String, ProcessingMethod> home_menu = new HashMap<String, ProcessingMethod>();
			
		menu(home_menu);
		System.out.println("Logging out!");
		}
		
	
	public static Customer inputCustomer() {
		System.out.print("New Customer Form");
		
		String name = inputString("Name:\t");
		String pass = inputString("Password:\t");
		Customer newGuy = new Customer(name, pass, new HashMap<Integer, SavingsAccount>(),new HashMap<Integer, Account>());
		DollarsBankController.newCustomer(newGuy);
		return newGuy;
	}
	
}
