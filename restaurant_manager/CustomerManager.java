package restaurant_manager;

//import java packages.... to be updated as we go
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
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
	 * Creation of new Customer
	 * 
	 */

	
	public static void createCustomer() throws IOException {
		String Name = "";
		String Gender = "";
		String phone_number = "";
		String rest_membership = ""; //default is no restaurant membership
		String partner_membership = ""; //default is no partner membership

		String createcustomer = "";


		// To be used for data validation
		String alpha = "[a-zA-Z.*\\s+.]+";
		String phonenumber = "\\d{8}";

		
		// input customer details 
		//all the details will go to customer object
		do {
			Customer customer = new Customer();

			System.out.println("\n==================================================");
			System.out.println(" Enter Customer Details ");
			System.out.println("==================================================");
			Scanner sc = new Scanner(System.in);
			// input customer name
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

			// input customer gender
			do {
				System.out.println("\nPlease choose Gender");
				System.out.println("(1) Female");
				System.out.println("(2) Male ");
				Gender = sc.nextLine();
				if (Gender == "1") {
					customer.setcustomerGender("Female");
					break;
				}
				else if(Gender == "2") {
					customer.setcustomerGender("Male");
					break;
				}
				else {
					System.out.println("Invalid Gender!");
				}
			} while (Gender != "1" || Gender != "2");
			
			// Guest PhoneNumber
			do {
				System.out.print("Enter customer's Contact Number: ");
				phone_number = sc.nextLine();
				if (phone_number.matches(phonenumber) && !phone_number.equals("")) {
					customer.setphoneNumber(phone_number);
					break;
				}
				else {
					System.out.println("Invalid contact number!");
				}
			} while (phone_number.equals("") || !phone_number.matches(phonenumber));
			
			// input whether customer has restaurant membership
			do {
				System.out.println("\nDoes customer have restaurant membership?");
				System.out.println("(1) Yes");
				System.out.println("(2) No ");
				rest_membership = sc.nextLine();
				if (rest_membership == "1") {
					customer.setrestaurantMembership(true);
					break;
				}
				else if(rest_membership == "2") {
					customer.setrestaurantMembership(false);
					break;
				}
				else {
					System.out.println("Invalid entry, depending on membership status enter 1 or 2");
				}
			} while (rest_membership != "1" || rest_membership != "2");

			// input whether customer has partner membership
			do {
				System.out.println("\nDoes customer have partner membership?");
				System.out.println("(1) Yes");
				System.out.println("(2) No ");
				partner_membership = sc.nextLine();
				if (partner_membership == "1") {
					customer.setpartnerMembership(true);
					break;
				}
				else if(partner_membership == "2") {
					customer.setpartnerMembership(false);
					break;
				}
				else {
					System.out.println("Invalid entry, depending on membership status enter 1 or 2");
				}
			} while (partner_membership != "1" || partner_membership != "2");
			
			
			CustomerDatabase customerDatabase = new CustomerDatabase();
			ArrayList customerinfo = customerDatabase.fread(textfilename); //read form database 
			customerinfo.add(customer);

			try {
				// Write Customer records to file
				customerDatabase.fwrite(textfilename, customerinfo);

				System.out.println("Customer information succesfully entered! ");

			} catch (IOException e) {
				System.out.println("IOException > " + e.getMessage());
			}
			System.out.println("Do you want to add another customer?");
			System.out.println("(1) Yes");
			System.out.println("(2) No");
			createcustomer = sc.nextLine(); 
			sc.close();
		} while (createcustomer == "1"); 

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

	/**
	 * Retrieve customer details by customerID
	 * 
	 */
	public static void printCustomerdetailsbyID() throws IOException {
		ArrayList readfileasstring = (ArrayList)FileRead.fread(textfilename);
		System.out.println("\n==================================================");
		System.out.println(" Search Customer Record");
		System.out.println("==================================================");

		System.out.print("Enter Customer ID: ");
		Scanner sc = new Scanner(System.in);
		String id_entered;
		id_entered = sc.nextLine();
		sc.close();

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

			if (customerid.contains(id_entered)) {

				System.out.printf("%-8s %-15s %-8s %-10s %-5s %-5s\n", customerid, name, gender,phonenumber,
						restaurantmember, partnermember);
			}
		}
	}

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
	 * Update Customer Details by ID
	 * 
	 */
	public static void updateCustomerdetailsbyID() throws IOException {
		System.out.println("\n==================================================");
		System.out.println(" Update Customer Details: ");
		System.out.println("==================================================");
		String Name = "";
		String Gender = "";
		String phone_number = "";
		String rest_membership = "";
		String partner_membership = "";



		// To be used for data validation
		String alpha = "[a-zA-Z.*\\s+.]+";
		String phonenumber = "\\d{8}";
		int updatetype;
		Customer updateCustomer = new Customer();
		updateCustomer = retrieveCustomerDetailsbyID();

		do {
			System.out.println("\nPlease choose Guest Detail to update");
			System.out.println("(1) Name");
			System.out.println("(2) Gender");
			System.out.println("(3) Phone Number");
			System.out.println("(4) Restaurant Membership Status");
			System.out.println("(5) Partner Membership Status");
			System.out.println("(6) Done updating");
			Scanner sc = new Scanner(System.in);
			updatetype = sc.nextInt();
			sc.nextLine();
			sc.close();
			switch (updatetype) {

			case 1:
				// Updating customer name
				do {
					System.out.print("\nUpdate Customer Name: ");
					Name = sc.nextLine();
					
					if(Name.equals("") || !Name.matches(alpha)) {
						updateCustomer.setcustomerName(Name);
						break;
					}
					else {
						System.out.print("Invalid name, please try again!\n");
					}
					
				} while (!Name.matches(alpha) || Name.equals(""));
				break;
			case 2:
				// Updating gender of customer
				do {
					System.out.println("\nPlease choose Gender");
					System.out.println("(1) Female");
					System.out.println("(2) Male ");
					Gender = sc.nextLine();
					if (Gender == "1") {
						updateCustomer.setcustomerGender("Female");
						break;
					}
					else if(Gender == "2") {
						updateCustomer.setcustomerGender("Male");
						break;
					}
					else {
						System.out.println("Invalid Gender!");
					}
				} while (Gender != "1" || Gender != "2");
				break;
			case 3:
				// Update customer phone number
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
				// Update Customer's restaurant membership status
				do {
					System.out.println("\nDoes customer have restaurant membership?");
					System.out.println("(1) Yes");
					System.out.println("(2) No ");
					rest_membership = sc.nextLine();
					if (rest_membership == "1") {
						updateCustomer.setrestaurantMembership(true);
						break;
					}
					else if(rest_membership == "2") {
						updateCustomer.setrestaurantMembership(false);
						break;
					}
					else {
						System.out.println("Invalid entry, depending on membership status enter 1 or 2");
					}
				} while (rest_membership != "1" || rest_membership != "2");
				break;
			case 5:
				// Update Customer's partner membership status
				do {
					System.out.println("\nDoes customer have partner membership?");
					System.out.println("(1) Yes");
					System.out.println("(2) No ");
					partner_membership = sc.nextLine();
					if (partner_membership == "1") {
						updateCustomer.setpartnerMembership(true);
						break;
					}
					else if(partner_membership == "2") {
						updateCustomer.setpartnerMembership(false);
						break;
					}
					else {
						System.out.println("Invalid entry, depending on membership status enter 1 or 2");
					}
				} while (partner_membership != "1" || partner_membership != "2");
				break;
			case 6:
				System.out.println("No more updates.");
				break;
			default:
				break;
			}

		} while (updatetype >= 1 && updatetype  <=5 );
		try
		{
			ArrayList customerlist = retrieveallcustomerdetailsfromdatabase();
			for (int i = 0; i < customerlist.size(); i++) {
				Customer findcustomer = (Customer) customerlist.get(i);

				if (updateCustomer.getcustomerID().equals(findcustomer.getcustomerID())) {
					customerlist.set(i, updateCustomer);
				}
			}

			// Write Customer records to file
			CustomerDatabase customerdatabase = new CustomerDatabase();
			customerdatabase.fwrite(textfilename, customerlist); //implement write function in database

			System.out.println("Customer Details successfully updated!");
		} catch (

		IOException e)

		{
			System.out.println("IOException > " + e.getMessage());
		}
	}

	public static void deleteCustomerdetailsbyID() throws IOException {
		
		String custid;

		
		System.out.println("\n==================================================");
		System.out.println(" CAUTION! Deleting Customer Record");
		System.out.println("==================================================");
		
		System.out.println("Enter Customer ID of the customer to be removed: ");
		Scanner sc = new Scanner(System.in);
		custid = sc.nextLine();
		
		//file objects, one for reading one for file storage
		File database = new File(textfilename);
		File tempstorage = new File("deleterecord.txt"); //rename this file name
		
		//read and write objects
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempstorage));
		BufferedReader reader = new BufferedReader(new FileReader(database));
		
		String databasedetail;
		
		while ((databasedetail = reader.readLine()) != null) {

			String record[] = databasedetail.split(",");
			if (record[0].contains(custid))
				continue;

			writer.write(databasedetail);
			writer.flush();
			writer.newLine();

		}
		writer.close();
		reader.close();

		database.delete();
		
		System.out.println("Customer Details have been successfully removed!");
		tempstorage.renameTo(database);
		

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
	public static ArrayList retrieveallcustomerdetailsfromdatabase() {
		ArrayList customerlist = null;
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
