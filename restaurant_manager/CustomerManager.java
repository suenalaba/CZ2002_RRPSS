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
	private static ArrayList<Customer> customerList=new ArrayList<Customer>();
	
	
	/**
	 * Retrieval of a particular customer's details
	 * 
	 * @param Customer Parameter to search for Customer details.
	 * @return null if customer not found, else return customer itself.
	 */
	public static Customer retrieveCustomerdetails(Customer customer) {
		ArrayList customerlist = customerList;

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
		
		ArrayList customerlist = customerList;
		
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

		Customer validcustomer = new Customer();

		// Checking validity of Customer ID
		do {

			System.out.println("Please enter Customer ID to update: ");
			Scanner sc = new Scanner(System.in);
			custID = sc.nextLine();
			validcustomer = retrieveCustomerbyIDinput(custID);
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
	 * Retrieval of all Customer details.
	 * 
	 */
	public static void printallCustomerDetails() throws IOException {
		ArrayList readfileasstring = (ArrayList) FileRead.fread(textfilename);
		//ArrayList<CreditCard> creditArray = CreditController.retrieveAllCredit();
		System.out.println("\n==================================================");
		System.out.println(" Customer Details: ");
		System.out.println("==================================================");
		System.out.printf("%-8s %-15s %-8s %-10s %-5s %-5s\n", "CustomerID", "Name",
				"Gender", "Phone Number", "Restaurant Membership", "Partner Membership");
		for (int i = 0; i < readfileasstring.size(); i++) {
			String data = (String) readfileasstring.get(i);
			// get individual 'fields' of the string separated by delimiter
			StringTokenizer str_tokenizer = new StringTokenizer(data, delimiter); // pass in the string to the string tokenizer
																		// using delimiter ","

			String customerid = str_tokenizer.nextToken().trim();
			String name = str_tokenizer.nextToken().trim();
			String gender = str_tokenizer.nextToken().trim();
			String phonenumber = str_tokenizer.nextToken().trim();
			String restaurantmember = str_tokenizer.nextToken().trim();
			String partnermember = str_tokenizer.nextToken().trim();



			System.out.printf("%-8s %-15s %-8s %-10s %-5s %-5s\n", customerid, name, gender,phonenumber,
					restaurantmember, partnermember);

		}
	}
	
	public static void setCustomerList(ArrayList<Customer> newListOfCustomers) {
		customerList=newListOfCustomers;
	}
	
	public static ArrayList<Customer> getCustomerList(){
		return customerList;
	}
	
	public static void saveDB(String textFileName) {
		CustomerDatabase cusDB=new CustomerDatabase();
		try {
			cusDB.fwrite(textFileName);
		} catch (IOException e) {
			System.out.println("Failed to save to "+textFileName);
			return;
		}
	}
	public static void loadDB(String textFileName) { 
		ArrayList<Customer> newListOfCustomers = null;
		try {
			// read file containing Guest records
			CustomerDatabase customerdatabase = new CustomerDatabase();
			newListOfCustomers = customerdatabase.fread(textfilename); //create read function from database

		} catch (IOException e) {
			System.out.println("Failed to load "+textFileName);
			return;
		}
		customerList=newListOfCustomers;
	}
}
