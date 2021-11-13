package restaurant_application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

import restaurant_database.CustomerDatabase;
import restaurant_database.FileRead;
import restaurant_entity.Customer;
import restaurant_manager.CustomerManager;

/**
 * define methods to create customer, Retrieve customer details, Update Customer Details and Delete Customer Details.
 * @author Joshua
 * @version 4.5
 * @since 13-11-2021
 *
 */
public class CustomerApp {
	/**
	 * fixed delimiter used to split tokens
	 */
	public static final String delimiter = ",";
	
	/**
	 * Creation of new Customer
	 * customer details include name, contact number, memberships status and a customer ID. 
	 * @param multiEntry
	 * @param walkIn
	 * @throws IOException
	 */

	public void createCustomer(boolean multiEntry,int walkIn) throws IOException { 
		CustomerManager customerM=CustomerManager.getInstance();
		String Name = "";
		int Gender = 0; 
		String phone_number = ""; 
		int rest_membership = 0; 
		int partner_membership = 0; 
		int createcustomer = 0;
		String alpha = "[a-zA-Z.*\\s+.]+";
		String phonenumber = "\\d{8}";
		do {
			Customer customer = new Customer();

			System.out.println("\n==================================================");
			System.out.println(" Enter Customer Details ");
			System.out.println("==================================================");
			Scanner sc = new Scanner(System.in);
			do {
				
				System.out.print("\nEnter Customer Name: "); 
				Name = sc.nextLine();
				if(Name.matches(alpha) && !Name.equals("")) {
					customer.setcustomerName(Name);
					break;
				}
				else {
					System.out.print("Please enter a valid name.\n");
				}
			} while (!Name.matches(alpha) || Name.equals(""));
			do {
				System.out.println("\nPlease choose Gender");
				System.out.println("(1) Female");
				System.out.println("(2) Male ");
					try {
						Gender = sc.nextInt();
						sc.nextLine();
					}catch(InputMismatchException e) {
						System.out.println("Invalid Input. Try Again: ");
						sc.nextLine();
					}
				if (Gender == 1) { 
					customer.setcustomerGender("Female");
					break;
				}
				else if(Gender == 2) {
					customer.setcustomerGender("Male");
					break;
				}
			} while (Gender != 1 && Gender != 2);
			do {
				if (walkIn==1) {
					System.out.println("Does customer want to provide Contact Number?");
					System.out.println("(1) Yes");
					System.out.println("(2) No ");
					int anonChoice=0;
					while (anonChoice==0) {
						try {
							anonChoice=sc.nextInt();
							sc.nextLine();
							if(anonChoice!=1&&anonChoice!=2) {
								anonChoice=0;
								System.out.println("Invalid Input. Try Again: ");
							}
						}catch(InputMismatchException e) {
							System.out.println("Invalid Input. Try Again: ");
							sc.nextLine();
						}
					}
					if (anonChoice==2) { 
						customer.setphoneNumber("XXXXXXXX");
						break;
					}
				}
				System.out.print("Enter customer's Contact Number (8 Digits): ");
				phone_number = sc.nextLine();
				if (phone_number.matches(phonenumber) && !phone_number.equals("")) {
					customer.setphoneNumber(phone_number);
					break;
				}
				else {
					System.out.println("Invalid contact number!");
				}
			} while (phone_number.equals("") || !phone_number.matches(phonenumber));
			do {
				System.out.println("\nDoes customer have restaurant membership?");
				System.out.println("(1) Yes");
				System.out.println("(2) No ");
				try {
					rest_membership = sc.nextInt();
					sc.nextLine();
				}catch(InputMismatchException e) {
					System.out.println("Invalid Input. Try Again: ");
					sc.nextLine();
				}
			
				if (rest_membership == 1) {
					customer.setrestaurantMembership(true);
					break;
				}
				else if(rest_membership == 2) {
					customer.setrestaurantMembership(false);
					break;
				}
				else {
					System.out.println("Invalid entry, depending on membership status enter 1 or 2");
				}
			} while (rest_membership != 1 && rest_membership != 2);
			do {
				System.out.println("\nDoes customer have partner membership?");
				System.out.println("(1) Yes");
				System.out.println("(2) No ");
				try {
					partner_membership = sc.nextInt();
					sc.nextLine();
				}catch(InputMismatchException e) {
					System.out.println("Invalid Input. Try Again: ");
					sc.nextLine();
				}
				if (partner_membership == 1) {
					customer.setpartnerMembership(true);
					break;
				}
				else if(partner_membership == 2) {
					customer.setpartnerMembership(false);
					break;
				}
				else {
					System.out.println("Invalid entry, depending on membership status enter 1 or 2");
				}
			} while (partner_membership != 1 || partner_membership != 2);
			Random rnd = new Random();
			int n;
			ArrayList<Integer> listOfCustomerId=new ArrayList<Integer>(); 
			for (Customer o:customerM.getCustomerList()) { 
				listOfCustomerId.add(Integer.parseInt(o.getcustomerID()));
			}
			do {
				n= 10000 + rnd.nextInt(90000); 
			}while(listOfCustomerId.contains(n));
			customer.setcustomerID(String.valueOf(n));
			System.out.println("CustomerID of new customer is: "+n);
			customerM.getCustomerList().add(customer);
			if (multiEntry==true) { 
				System.out.println("Do you want to add another customer?");
				System.out.println("(1) Yes");
				System.out.println("(2) No");
				createcustomer = sc.nextInt();
			} 
		} while (createcustomer == 1); 

	}
	
	

	/**
	 * Retrieve customer details by customerID
	 * @throws IOException
	 */
	public void printCustomerdetailsbyID() throws IOException {
		System.out.println("\n==================================================");
		System.out.println(" Search Customer Record");
		System.out.println("==================================================");
		System.out.print("Enter Customer ID: ");
		Scanner sc = new Scanner(System.in);
		ArrayList<String> customerIDs=new ArrayList<String>();
		CustomerManager customerM=CustomerManager.getInstance();
		for (Customer o:customerM.getCustomerList()) {
			customerIDs.add(o.getcustomerID());
		}
		String customerID="";
		customerID=sc.nextLine();
		if (customerIDs.contains(customerID)) {
			Customer aCustomer=customerM.getCustomer(customerID);
			System.out.printf("%-8s %-15s %-8s %-10s %-5s %-5s\n", aCustomer.getcustomerID(), aCustomer.getcustomerName(),
					aCustomer.getcustomerGender(), aCustomer.getphoneNumber(), aCustomer.getrestaurantMembership(), aCustomer.getpartnerMembership());
		}
		else {
			System.out.println("Customer not in records. Returning to main menu.");
			return;
		}
	}

	
	
	
	
	
	/**
	 * Update Customer Details by customer ID
	 * customer details include Name, Gender, Phone number, Restaurant Membership Status, Partner Membership Status
	 * @throws IOException
	 */
	
	public void updateCustomerdetailsbyID() throws IOException {
		CustomerManager customerM=CustomerManager.getInstance();
		System.out.println("\n==================================================");
		System.out.println(" Update Customer Details: ");
		System.out.println("==================================================");
		String Name = "";
		String Gender = "";
		String phone_number = "";
		String rest_membership = "";
		String partner_membership = "";
		String alpha = "[a-zA-Z.*\\s+.]+";
		String phonenumber = "\\d{8}";
		int updatetype;
		Customer updateCustomer = new Customer();
		updateCustomer = customerM.retrieveCustomerDetailsbyID();
		do {
			System.out.println("\nPlease choose Guest Detail to update");
			System.out.println("(1) Name");
			System.out.println("(2) Gender");
			System.out.println("(3) Phone Number");
			System.out.println("(4) Restaurant Membership Status");
			System.out.println("(5) Partner Membership Status");
			System.out.println("(6) Done updating");
			Scanner sc = new Scanner(System.in);
			try {
				
				updatetype = sc.nextInt();
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid number. You will be returned to main menu");
				return;
			}
			switch (updatetype) {
			case 1:
				do {
					System.out.print("\nUpdate Customer Name: ");
					Name = sc.nextLine();
					if(!Name.equals("") && Name.matches(alpha)) {
						updateCustomer.setcustomerName(Name);
						break;
					}
					else {
						System.out.print("Invalid name, please try again!\n");
					}
					
				} while (!Name.matches(alpha) || Name.equals(""));
				break;
			case 2:
				do {
					System.out.println("\nPlease choose Gender");
					System.out.println("(1) Female");
					System.out.println("(2) Male ");
					Gender = sc.nextLine();
					if (Gender.equals("1")) {
						updateCustomer.setcustomerGender("Female");
						break;
					}
					else if(Gender.equals( "2")) {
						updateCustomer.setcustomerGender("Male");
						break;
					}
					else {
						System.out.println("Invalid Gender!");
					}
				} while (!Gender.equals("1") || !Gender.equals("2"));
				break;
			case 3:
				do {
					System.out.print("Enter customer's Contact Number: ");
					phone_number = sc.nextLine();
					if (phone_number.matches(phonenumber) && !phone_number.equals("")) {
						updateCustomer.setphoneNumber(phone_number);
						break;
					}
					else {
						System.out.println("Invalid contact number!");
					}
				} while (phone_number.equals("") || !phone_number.matches(phonenumber));
				break;
			case 4:
				do {
					System.out.println("\nDoes customer have restaurant membership?");
					System.out.println("(1) Yes");
					System.out.println("(2) No ");
					rest_membership = sc.nextLine();
					if (rest_membership.equals( "1")) {
						updateCustomer.setrestaurantMembership(true);
						break;
					}
					else if(rest_membership.equals("2")) {
						updateCustomer.setrestaurantMembership(false);
						break;
					}
					else {
						System.out.println("Invalid entry, depending on membership status enter 1 or 2");
					}
				} while (!rest_membership.equals("1") || !rest_membership.equals("2"));
				break;
			case 5:
				do {
					System.out.println("\nDoes customer have partner membership?");
					System.out.println("(1) Yes");
					System.out.println("(2) No ");
					partner_membership = sc.nextLine();
					if (partner_membership.equals("1")) {
						updateCustomer.setpartnerMembership(true);
						break;
					}
					else if(partner_membership.equals("2")) {
						updateCustomer.setpartnerMembership(false);
						break;
					}
					else {
						System.out.println("Invalid entry, depending on membership status enter 1 or 2");
					}
				} while (!partner_membership.equals("1") || !partner_membership.equals("2"));
				break;
			case 6:
				System.out.println("No more updates.");
				break;
			default:
				return;
			}

		} while (updatetype >= 1 && updatetype  <=5 );

			ArrayList<Customer> customerlist = customerM.getCustomerList();
			for (int i = 0; i < customerlist.size(); i++) {
				Customer findcustomer = (Customer) customerlist.get(i);
				if (updateCustomer.getcustomerID().equals(findcustomer.getcustomerID())) {
					customerM.getCustomerList().set(i, updateCustomer);
				}
			}
			System.out.println("Customer Details successfully updated!");

	}
	
	
	/**
	 *  Delete Customer from system by customer ID
	 */
	public void deleteCustomerdetailsbyID() {
		System.out.println("\n==================================================");
		System.out.println(" Delete Customer Record");
		System.out.println("==================================================");
		System.out.print("Enter Customer ID: ");
		Scanner sc = new Scanner(System.in);
		ArrayList<String> customerIDs=new ArrayList<String>();
		CustomerManager customerM=CustomerManager.getInstance();
		for (Customer o:customerM.getCustomerList()) {
			customerIDs.add(o.getcustomerID());
		}
		String customerID="";
		customerID=sc.nextLine();
		if (customerIDs.contains(customerID)) {
			Customer toRemove=customerM.getCustomer(customerID);
			customerM.getCustomerList().remove(toRemove);
		}
		else {
			System.out.println("Customer not in records. Returning to main menu.");
			return;
		}
	}
}
