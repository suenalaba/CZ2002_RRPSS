package restaurant_manager;

//import java packages.... to be updated as we go
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;


//import other class.....
import restaurant_entity.Customer;
import restaurant_database.FileRead;
import restaurant_database.CustomerDatabase;

public class CustomerManager {
	
	private static String textfilename = "Customer.txt";
	public static final String delimiter = ",";
	
	
	/**
	 * Retrieval of a particular customer's details
	 * 
	 * @param Customer Parameter to search for Customer details.
	 * @return null if customer not found, else return customer itself.
	 */
	public static Customer retrieveCustomerdetails(Customer customer) {
		ArrayList customerlist = retrieveallcustomerdetailsfromdatabase();

		for (int i = 0; i < customerlist.size(); i++) {
			Customer validcustomer = (Customer) customerlist.get(i);

			if (validcustomer.getcustomerID() != customer.getcustomerID()) {
				return null;
			}
			else if (validcustomer.getcustomerID() != customer.getcustomerID()) {
				customer = validcustomer;
				return customer;
			}
		}
		return null;
	}

	/**
	 * Return Customer object if found.
	 * 
	 * @param Search for customer details with ID
	 * @return if customer don't exist null, else customer
	 */
	public static Customer retrieveCustomerbyIDinput(String ID) {
		
		int i;
		
		ArrayList customerlist = retrieveallcustomerdetailsfromdatabase();
		
		for (i = 0; i < customerlist.size(); i++) {
			Customer validcustomer = (Customer) customerlist.get(i);

			if (ID.equals(validcustomer.getcustomerID())) {
				return validcustomer;
			}
		}
		return null;
	}

	

	

	/**
	 * Retrieval of Customer's details by ID number
	 * 
	 * @return Customer details.
	 */

	public static Customer retrieveCustomerDetailsbyID() {
		String custID;

		Customer customer = new Customer();
		Customer validcustomer = new Customer();

		// Checking validity of Customer ID
		do {

			System.out.println("Please enter Customer ID to update: ");
			Scanner sc = new Scanner(System.in);
			custID = sc.nextLine();
			customer.setcustomerID(custID);
			validcustomer = retrieveCustomerdetails(customer);
			sc.close();
			if (validcustomer != null) {
				System.out.printf("Proceeding to update details for CustomerID: %s\n",custID);
				break;
			}
			else {
				System.out.println("Invalid Customer ID entered, please try again. ");
			}
		}while(validcustomer == null);
		return validcustomer;
	}
			

	/**
	 * Retrieval of customer details.
	 * 
	 * @return ArrayList of all customers from the database
	 */
	public static ArrayList<Customer> retrieveallcustomerdetailsfromdatabase() { //Jacques - specified data type of ArrayList to <Customer>
		ArrayList<Customer> customerlist = null;
		try {
			// read file containing Guest records
			CustomerDatabase customerdatabase = new CustomerDatabase();
			customerlist = customerdatabase.fread(textfilename); //create read function from database

		} catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}
		return customerlist;
	}
}
